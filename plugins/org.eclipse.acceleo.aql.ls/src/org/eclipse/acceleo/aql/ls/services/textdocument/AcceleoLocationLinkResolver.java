/*******************************************************************************
 * Copyright (c) 2020, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.services.textdocument;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.location.AcceleoLocationLinkToAcceleo;
import org.eclipse.acceleo.aql.location.AcceleoLocationLinkToSourceLocation;
import org.eclipse.acceleo.aql.location.aql.AqlLocationLinkToAny;
import org.eclipse.acceleo.aql.location.aql.AqlLocationLinkToAql;
import org.eclipse.acceleo.aql.location.common.AbstractLocationLink;
import org.eclipse.acceleo.aql.ls.common.AcceleoLanguageServerPositionUtils;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.parser.AcceleoAstUtils;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;

/**
 * The part of the {@link AcceleoTextDocumentService} APIs that makes the link between
 * {@link AbstractLocationLink} provided by the Acceleo API, and the {@link LocationLink} required by the
 * LSP4J API.
 * 
 * @author Florent Latombe
 */
public class AcceleoLocationLinkResolver {

	/**
	 * The owning {@link AcceleoTextDocumentService}.
	 */
	private final AcceleoTextDocumentService owner;

	/**
	 * Constructor.
	 * 
	 * @param acceleoTextDocumentService
	 *            the (non-{@code null}) owning {@link AcceleoTextDocumentService}.
	 */
	public AcceleoLocationLinkResolver(AcceleoTextDocumentService acceleoTextDocumentService) {
		this.owner = Objects.requireNonNull(acceleoTextDocumentService);
	}

	/**
	 * Transforms an {@link AbstractLocationLink} from Acceleo into a corresponding {@link LocationLink} for
	 * LSP4J.
	 * 
	 * @param locationLinkToTransform
	 *            the (non-{@code null}) {@link AbstractLocationLink} to transform.
	 * @return the {@link LocationLink} corresponding to {@code locationLink}.
	 */
	public LocationLink transform(AbstractLocationLink<?, ?> locationLinkToTransform) {
		Objects.requireNonNull(locationLinkToTransform);

		final LocationLink locationLink;
		// Dispatch depending on the concrete type of link.
		if (locationLinkToTransform instanceof AcceleoLocationLinkToAcceleo) {
			locationLink = this.transform((AcceleoLocationLinkToAcceleo)locationLinkToTransform);
		} else if (locationLinkToTransform instanceof AqlLocationLinkToAql) {
			locationLink = this.transform((AqlLocationLinkToAql)locationLinkToTransform);
		} else if (locationLinkToTransform instanceof AqlLocationLinkToAny) {
			locationLink = this.transform((AqlLocationLinkToAny)locationLinkToTransform);
		} else if (locationLinkToTransform instanceof AcceleoLocationLinkToSourceLocation) {
			locationLink = this.transform((AcceleoLocationLinkToSourceLocation)locationLinkToTransform);
		} else {
			throw new UnsupportedOperationException("Unsupported " + AbstractLocationLink.class
					.getCanonicalName() + " implementation: " + locationLinkToTransform.toString());
		}
		return locationLink;
	}

	/**
	 * Transforms an {@link AcceleoLocationLinkToAcceleo} from Acceleo into a corresponding
	 * {@link LocationLink} for LSP4J.
	 * 
	 * @param acceleoLocationLinkToAcceleo
	 *            the (non-{@code null}) {@link AcceleoLocationLinkToAcceleo} to transform.
	 * @return the {@link LocationLink} corresponding to {@code acceleoLocationLinkToAcceleo}.
	 */
	private LocationLink transform(AcceleoLocationLinkToAcceleo acceleoLocationLinkToAcceleo) {
		ASTNode linkOrigin = acceleoLocationLinkToAcceleo.getOrigin();
		AcceleoTextDocument originTextDocument = getAcceleoTextDocumentContaining(linkOrigin);
		ASTNode linkOriginEquivalent = AcceleoAstUtils.getSelfOrEquivalentOf(linkOrigin, originTextDocument
				.getAcceleoAstResult());

		Range originSelectionRange = AcceleoLanguageServerPositionUtils.getRangeOf(linkOriginEquivalent,
				originTextDocument.getAcceleoAstResult());

		ASTNode destinationAcceleoNode = acceleoLocationLinkToAcceleo.getDestination();
		return this.createLocationLinkFromRangeToAcceleoDestination(originSelectionRange,
				destinationAcceleoNode);
	}

	/**
	 * Transforms an {@link AcceleoLocationLinkToSourceLocation} from Acceleo into a corresponding
	 * {@link LocationLink} for LSP4J.
	 * 
	 * @param acceleoLocationLinkToSourceLocation
	 *            the (non-{@code null}) {@link AcceleoLocationLinkToSourceLocation} to transform.
	 * @return the {@link LocationLink} corresponding to {@code acceleoLocationLinkToSourceLocation}.
	 */
	private LocationLink transform(AcceleoLocationLinkToSourceLocation acceleoLocationLinkToSourceLocation) {
		ASTNode linkOrigin = acceleoLocationLinkToSourceLocation.getOrigin();
		AcceleoTextDocument originTextDocument = getAcceleoTextDocumentContaining(linkOrigin);
		ASTNode linkOriginEquivalent = AcceleoAstUtils.getSelfOrEquivalentOf(linkOrigin, originTextDocument
				.getAcceleoAstResult());

		Range originSelectionRange = AcceleoLanguageServerPositionUtils.getRangeOf(linkOriginEquivalent,
				originTextDocument.getAcceleoAstResult());

		ISourceLocation targetSourceLocation = acceleoLocationLinkToSourceLocation.getDestination();
		return this.createLocationLinkFromRangeToSourceLocation(originSelectionRange, targetSourceLocation);
	}

	/**
	 * Provides the {@link AcceleoTextDocument} containing the given Acceleo {@link ASTNode}.
	 * 
	 * @param astNode
	 *            the (non-{@code null}) {@link ASTNode}.
	 * @return the {@link AcceleoTextDocument} whose contents define the {@link Module} defining
	 *         {@code astNode}. {@code null} if it could not be determined.
	 */
	private AcceleoTextDocument getAcceleoTextDocumentContaining(ASTNode astNode) {
		Module containingModule = AcceleoAstUtils.getContainerModule(astNode);
		AcceleoTextDocument containingTextDocument = this.getTextDocumentDefining(containingModule);
		return containingTextDocument;
	}

	/**
	 * Provides the {@link AcceleoTextDocument} that defines the given Acceleo {@link Module}.
	 * 
	 * @param acceleoModule
	 *            the (non-{@code null}) {@link Module} we want to find the defining document of.
	 * @return the {@link AcceleoTextDocument} that defines {@code acceleoModule}. {@code null} if it could
	 *         not be determined.
	 */
	private AcceleoTextDocument getTextDocumentDefining(Module acceleoModule) {
		return this.owner.findTextDocumentDefining(acceleoModule);
	}

	/**
	 * Transforms an {@link AqlLocationLinkToAql} from AQL into a corresponding {@link LocationLink} for
	 * LSP4J.
	 * 
	 * @param aqlLocationLinkToAql
	 *            the (non-{@code null}) {@link AqlLocationLinkToAql} to transform.
	 * @return the {@link LocationLink} corresponding to {@code aqlLocationLinkToAql}.
	 */
	private LocationLink transform(AqlLocationLinkToAql aqlLocationLinkToAql) {
		EObject linkOrigin = aqlLocationLinkToAql.getOrigin();
		Range originSelectionRange = getRangeForAqlAstElement(linkOrigin);

		EObject linkDestination = aqlLocationLinkToAql.getDestination();
		return createLocationLinkFromRangeToAqlDestination(originSelectionRange, linkDestination);
	}

	/**
	 * Provides the "selection range" of an {@link EObject AQL AST element}
	 * ({@link org.eclipse.acceleo.query.ast.Expression} or {@link VariableDeclaration}).
	 * 
	 * @param aqlAstElement
	 *            the (non-{@code null}) AQL {@link org.eclipse.acceleo.query.ast.Expression} or
	 *            {@link VariableDeclaration}.
	 * @return the {@link Range} corresponding to {@code aqlAstElement}.
	 */
	private Range getRangeForAqlAstElement(EObject aqlAstElement) {
		final ASTNode acceleoNodeContainingLinkOrigin = getAcceleoAstNodeContainingAqlElement(aqlAstElement);
		AcceleoTextDocument originTextDocument = getAcceleoTextDocumentContaining(
				acceleoNodeContainingLinkOrigin);

		Range originSelectionRange;
		if (aqlAstElement instanceof org.eclipse.acceleo.query.ast.Expression) {
			org.eclipse.acceleo.query.ast.Expression originAqlExpression = (org.eclipse.acceleo.query.ast.Expression)aqlAstElement;
			org.eclipse.acceleo.query.ast.Expression originAqlExpressionEquivalent = AcceleoAstUtils
					.getSelfOrEquivalentOf(originAqlExpression, originTextDocument.getAcceleoAstResult());
			originSelectionRange = AcceleoLanguageServerPositionUtils.getIdentifierRangeOf(
					originAqlExpressionEquivalent, originTextDocument.getAcceleoAstResult());
		} else if (aqlAstElement instanceof org.eclipse.acceleo.query.ast.VariableDeclaration) {
			org.eclipse.acceleo.query.ast.VariableDeclaration originAqlVariableDeclaration = (org.eclipse.acceleo.query.ast.VariableDeclaration)aqlAstElement;
			org.eclipse.acceleo.query.ast.VariableDeclaration originAqlVariableDeclarationEquivalent = AcceleoAstUtils
					.getSelfOrEquivalentOf(originAqlVariableDeclaration, originTextDocument
							.getAcceleoAstResult());
			originSelectionRange = AcceleoLanguageServerPositionUtils.getIdentifierRangeOf(
					originAqlVariableDeclarationEquivalent, originTextDocument.getAcceleoAstResult());
		} else {
			// This should never happen.
			throw new IllegalStateException(
					"Expected link origin to be either an AQL Expression or an AQL Variable Declaration but it was: "
							+ aqlAstElement.toString() + ".");
		}
		return originSelectionRange;
	}

	/**
	 * Provides the {@link ASTNode} containing the given node from an AQL AST.
	 * 
	 * @param aqlElement
	 *            the (non-{@code null}) AQL element ({@link org.eclipse.acceleo.query.ast.Expression} or
	 *            {@link org.eclipse.acceleo.query.ast.VariableDeclaration}).
	 * @return the {@link ASTNode} containing {@code aqlElement}.
	 */
	private static ASTNode getAcceleoAstNodeContainingAqlElement(EObject aqlElement) {
		final ASTNode acceleoNodeContainingAqlElement;
		if (aqlElement instanceof org.eclipse.acceleo.query.ast.Expression) {
			org.eclipse.acceleo.query.ast.Expression originAqlExpression = (org.eclipse.acceleo.query.ast.Expression)aqlElement;
			acceleoNodeContainingAqlElement = AcceleoAstUtils.getContainerOfAqlAstElement(
					originAqlExpression);
		} else if (aqlElement instanceof org.eclipse.acceleo.query.ast.VariableDeclaration) {
			org.eclipse.acceleo.query.ast.VariableDeclaration originAqlVariableDeclaration = (org.eclipse.acceleo.query.ast.VariableDeclaration)aqlElement;
			acceleoNodeContainingAqlElement = AcceleoAstUtils.getContainerOfAqlAstElement(
					originAqlVariableDeclaration);
		} else {
			// This should never happen.
			throw new IllegalStateException(
					"Expected AQL element to be either an AQL Expression or an AQL Variable Declaration but it was: "
							+ aqlElement.toString() + ".");
		}
		return acceleoNodeContainingAqlElement;
	}

	/**
	 * Transforms an {@link AqlLocationLinkToAny} from AQL into a corresponding {@link LocationLink} for
	 * LSP4J.
	 * 
	 * @param aqlLocationLinkToAny
	 *            the (non-{@code null}) {@link AqlLocationLinkToAny} to transform.
	 * @return the {@link LocationLink} corresponding to {@code aqlLocationLinkToAny}.
	 */
	private LocationLink transform(AqlLocationLinkToAny aqlLocationLinkToAny) {
		EObject aqlOrigin = aqlLocationLinkToAny.getOrigin();
		Range originSelectionRange = getRangeForAqlAstElement(aqlOrigin);
		Object destination = aqlLocationLinkToAny.getDestination();
		return createLocationLinkFromRangeToAnyDestination(aqlOrigin, originSelectionRange, destination);
	}

	/**
	 * Creates a {@link LocationLink} from the given {@link Range} to a destination of undetermined nature.
	 * 
	 * @param linkOrigin
	 *            the (non-{@code null}) origin {@link EObject} of the link to create.
	 * @param originSelectionRange
	 *            the (non-{@code null}) origin selection {@link Range} of the link.
	 * @param destination
	 *            the (non-{@code null}) destination of the link.
	 * @return the {@link LocationLink} from that points from {@code originSelectionRange} to
	 *         {@code destination}.
	 */
	private LocationLink createLocationLinkFromRangeToAnyDestination(EObject linkOrigin,
			Range originSelectionRange, Object destination) {
		LocationLink locationLink;
		if (destination instanceof ASTNode) {
			ASTNode destinationNode = (ASTNode)destination;
			locationLink = createLocationLinkFromRangeToAcceleoDestination(originSelectionRange,
					destinationNode);
		} else if (destination instanceof org.eclipse.acceleo.query.ast.Expression
				|| destination instanceof org.eclipse.acceleo.query.ast.VariableDeclaration) {
			// This should probably not happen due to how the relation between Acceleo and AQL is structured.
			// i.e. both Acceleo and AQL are aware of AQL so links with an AQL destination should not be
			// represented as links to "any" destination.
			// Still, support it just in case...
			locationLink = createLocationLinkFromRangeToAqlDestination(originSelectionRange,
					(EObject)destination);
		} else if (destination instanceof IService<?>) {
			locationLink = createLocationLinkFromRangeToServiceDestination(linkOrigin, originSelectionRange,
					(IService<?>)destination);
		} else if (destination instanceof java.lang.Class<?>) {
			locationLink = createLocationLinkFromRangeToJavaClassDestination(linkOrigin, originSelectionRange,
					(Class<?>)destination);
		} else if (destination instanceof Method) {
			locationLink = createLocationLinkFromRangeToJavaMethodDestination(linkOrigin,
					originSelectionRange, (Method)destination);
		} else if (destination instanceof EObject) {
			locationLink = createLocationLinkFromRangeToEObjectDestination(linkOrigin, originSelectionRange,
					(EObject)destination);
		} else {
			// Implement more cases if we have links that link to other types of destination: maybe Java?
			// This depends on how we have bound variables in the environment passed to the AQL evaluator.
			throw new IllegalStateException("Trying to create a link to destination: " + destination
					+ " but this type of destination is not supported.");
		}
		return locationLink;
	}

	/**
	 * Creates a {@link LocationLink} from the given {@link Range} that points to the given AQL element.
	 * 
	 * @param originSelectionRange
	 *            the (non-{@code null}) origin selection {@link Range} of the created link.
	 * @param aqlDestination
	 *            the (non-{@code null}) destination {@link EObject} that must be an AQL element
	 *            ({@link org.eclipse.acceleo.query.ast.Expression} or {@link VariableDeclaration}).
	 * @return the created {@link LocationLink}.
	 */
	private LocationLink createLocationLinkFromRangeToAqlDestination(Range originSelectionRange,
			EObject aqlDestination) {
		final ASTNode acceleoNodeContainingLinkDestination = getAcceleoAstNodeContainingAqlElement(
				aqlDestination);
		AcceleoTextDocument destinationTextDocument = getAcceleoTextDocumentContaining(
				acceleoNodeContainingLinkDestination);

		// Link target parameters.
		String targetDocumentUri = destinationTextDocument.getUri().toString();
		final Range targetRange = getRangeForAqlAstElement(aqlDestination);
		// FIXME: we probably only want to select part of the target.
		Range targetSelectionRange = targetRange;

		LocationLink locationLink = new LocationLink(targetDocumentUri, targetRange, targetSelectionRange,
				originSelectionRange);
		return locationLink;
	}

	/**
	 * Creates a {@link LocationLink} from the given {@link Range} to the given Acceleo {@link ASTNode}
	 * destination.
	 * 
	 * @param originSelectionRange
	 *            the (non-{@code null}) origin selection {@link Range}.
	 * @param destinationNode
	 *            the (non-{@code null}) destination {@link ASTNode}.
	 * @return the {@link LocationLink} from {@code originSelectionRange} to {@code destinationNode}.
	 */
	private LocationLink createLocationLinkFromRangeToAcceleoDestination(Range originSelectionRange,
			ASTNode destinationNode) {
		AcceleoTextDocument destinationTextDocument = this.getAcceleoTextDocumentContaining(destinationNode);
		if (destinationTextDocument == null) {
			// This should never happen because we a transforming a link that was provided by the
			// AcceleoLocator, so if a link destination could not be determined then the locator would not
			// have returned a link.
			throw new IllegalArgumentException("Could not find the Acceleo document that defines "
					+ destinationNode.toString());
		}
		AcceleoAstResult destinationAcceleoAstResult = destinationTextDocument.getAcceleoAstResult();

		// Note: the destination ASTNode comes from a parsing that is not necessarily the one known by the
		// destination text document, therefore we have to be able to locate the equivalent of an ASTNode in
		// another AcceleoAstResult of the same Acceleo file.
		ASTNode destinationNodeInDestinationTextDocument = AcceleoAstUtils.getSelfOrEquivalentOf(
				destinationNode, destinationAcceleoAstResult);
		Range targetRange = AcceleoLanguageServerPositionUtils.getRangeOf(
				destinationNodeInDestinationTextDocument, destinationAcceleoAstResult);
		Range targetSelectionRange = AcceleoLanguageServerPositionUtils.getIdentifierRangeOf(
				destinationNodeInDestinationTextDocument, destinationTextDocument.getAcceleoAstResult());

		// Link target parameters.
		Module destinationModule = destinationTextDocument.getAcceleoAstResult().getModule();
		String qualifiedName = URI.decode(destinationModule.eResource().getURI().toString().replaceFirst(
				AcceleoParser.ACCELEOENV_URI_PROTOCOL, ""));

		// TODO this is a more general matter, it should be performed in the AcceleoWorkspace
		// open source file whenever it's possible
		java.net.URI targetDocumentUri;
		final IQualifiedNameResolver resolver = destinationTextDocument.getQueryEnvironment()
				.getLookupEngine().getResolver();
		targetDocumentUri = resolver.getSourceURI(qualifiedName);
		if (targetDocumentUri == null) {
			targetDocumentUri = destinationTextDocument.getUri();
		}

		LocationLink locationLink = new LocationLink(targetDocumentUri.toString(), targetRange,
				targetSelectionRange, originSelectionRange);
		return locationLink;
	}

	/**
	 * Creates a {@link LocationLink} from the given {@link Range} to the given Acceleo
	 * {@link ISourceLocation} destination.
	 * 
	 * @param originSelectionRange
	 *            the (non-{@code null}) origin selection {@link Range}.
	 * @param targetSourceLocation
	 *            the (non-{@code null}) destination {@link ISourceLocation}.
	 * @return the {@link LocationLink} from {@code originSelectionRange} to {@code targetSourceLocation}.
	 */
	private LocationLink createLocationLinkFromRangeToSourceLocation(Range originSelectionRange,
			ISourceLocation targetSourceLocation) {

		final Position rangeStart = new Position(targetSourceLocation.getRange().getStart().getLine(),
				targetSourceLocation.getRange().getStart().getColumn());
		final Position rangeEnd = new Position(targetSourceLocation.getRange().getEnd().getLine(),
				targetSourceLocation.getRange().getEnd().getColumn());
		final Range targetRange = new Range(rangeStart, rangeEnd);

		final Position identifierStart = new Position(targetSourceLocation.getIdentifierRange().getStart()
				.getLine(), targetSourceLocation.getIdentifierRange().getStart().getColumn());
		final Position identifierEnd = new Position(targetSourceLocation.getIdentifierRange().getEnd()
				.getLine(), targetSourceLocation.getIdentifierRange().getEnd().getColumn());
		final Range targetSelectionRange = new Range(identifierStart, identifierEnd);

		final LocationLink locationLink = new LocationLink(targetSourceLocation.getSourceURI().toString(),
				targetRange, targetSelectionRange, originSelectionRange);
		return locationLink;
	}

	/**
	 * Creates a {@link LocationLink} from the given {@link Range} to the given Java {@link Class}
	 * destination.
	 * 
	 * @param linkOrigin
	 *            the (non-{@code null}) origin {@link EObject} of the link.
	 * @param originSelectionRange
	 *            the (non-{@code null}) origin selection {@link Range}.
	 * @param destinationJavaClass
	 *            the (non-{@code null}) destination Java {@link Class}.
	 * @return the {@link LocationLink} from {@code originSelectionRange} to {@code destinationJavaClass}.
	 */
	private LocationLink createLocationLinkFromRangeToJavaClassDestination(EObject linkOrigin,
			Range originSelectionRange, Class<?> destinationJavaClass) {
		ASTNode acceleoAstNode = getAcceleoAstNodeContainingAqlElement(linkOrigin);
		AcceleoTextDocument acceleoTextDocument = this.getAcceleoTextDocumentContaining(acceleoAstNode);

		// TODO: implement link to Java sources.
		// We probably want to retrieve the Java context (classpath, etc.) of the AcceleoTextDocument, and use
		// it to retrieve the location of the Java class. Most of this mechanism should be implemented for the
		// resolution of modules anyway.

		// FIXME: temporary placeholder so it still sort of works
		String urlPrefix = "https://docs.oracle.com/javase/8/docs/api/";
		String urlPostfix = ".html";
		String classNameUrlPart = destinationJavaClass.getCanonicalName().replace('.', '/');
		String urlToJavadoc = urlPrefix + classNameUrlPart + urlPostfix;

		String targetUri = urlToJavadoc;
		Range targetRange = new Range(new Position(0, 0), new Position(0, 0));
		Range targetSelectionRange = targetRange;

		LocationLink locationLink = new LocationLink(targetUri, targetRange, targetSelectionRange,
				originSelectionRange);
		return locationLink;
	}

	/**
	 * Creates a {@link LocationLink} from the given {@link Range} to the given {@link IService} destination.
	 * 
	 * @param linkOrigin
	 *            the (non-{@code null}) origin {@link EObject} of the link.
	 * @param originSelectionRange
	 *            the (non-{@code null}) origin selection {@link Range}.
	 * @param destinationService
	 *            the (non-{@code null}) destination {@link IService}.
	 * @return the {@link LocationLink} from {@code originSelectionRange} to {@code destinationService}.
	 */
	private LocationLink createLocationLinkFromRangeToServiceDestination(EObject linkOrigin,
			Range originSelectionRange, IService<?> destinationService) {
		ASTNode acceleoAstNode = getAcceleoAstNodeContainingAqlElement(linkOrigin);
		AcceleoTextDocument acceleoTextDocument = this.getAcceleoTextDocumentContaining(acceleoAstNode);

		final IQualifiedNameResolver resolver = acceleoTextDocument.getQueryEnvironment().getLookupEngine()
				.getResolver();

		final ISourceLocation sourceLocation = resolver.getSourceLocation(destinationService);
		final LocationLink locationLink;
		if (sourceLocation != null) {
			Position targetRangeStart = new Position(sourceLocation.getRange().getStart().getLine(),
					sourceLocation.getRange().getStart().getColumn());
			Position targetRangeEnd = new Position(sourceLocation.getRange().getEnd().getLine(),
					sourceLocation.getRange().getEnd().getColumn());
			Range targetRange = new Range(targetRangeStart, targetRangeEnd);

			Position targetSelectionRangeStart = new Position(sourceLocation.getIdentifierRange().getStart()
					.getLine(), sourceLocation.getIdentifierRange().getStart().getColumn());
			Position targetSelectionRangeEnd = new Position(sourceLocation.getIdentifierRange().getEnd()
					.getLine(), sourceLocation.getIdentifierRange().getEnd().getColumn());
			Range targetSelectionRange = new Range(targetSelectionRangeStart, targetSelectionRangeEnd);

			locationLink = new LocationLink(sourceLocation.getSourceURI().toString(), targetRange,
					targetSelectionRange, originSelectionRange);
		} else {
			locationLink = createLocationLinkFromRangeToAnyDestination(linkOrigin, originSelectionRange,
					destinationService.getOrigin());
		}

		return locationLink;
	}

	/**
	 * Creates a {@link LocationLink} from the given {@link Range} to the given Java {@link Method}
	 * destination.
	 * 
	 * @param linkOrigin
	 *            the (non-{@code null}) origin {@link EObject} of the link.
	 * @param originSelectionRange
	 *            the (non-{@code null}) origin selection {@link Range}.
	 * @param destinationJavaMethod
	 *            the (non-{@code null}) destination Java {@link Method}.
	 * @return the {@link LocationLink} from {@code originSelectionRange} to {@code destinationJavaMethod}.
	 */
	private LocationLink createLocationLinkFromRangeToJavaMethodDestination(EObject linkOrigin,
			Range originSelectionRange, Method destinationJavaMethod) {
		ASTNode acceleoAstNode = getAcceleoAstNodeContainingAqlElement(linkOrigin);
		AcceleoTextDocument acceleoTextDocument = this.getAcceleoTextDocumentContaining(acceleoAstNode);

		// TODO: implement link to Java sources.
		// We probably want to retrieve the Java context (classpath, etc.) of the AcceleoTextDocument, and use
		// it to retrieve the location of the Java method. Most of this mechanism should be implemented for
		// the
		// resolution of modules anyway.

		// FIXME: temporary placeholder so it still sort of works
		String urlPrefix = "https://docs.oracle.com/javase/8/docs/api/";
		String urlPostfix = ".html";
		String classNameUrlPart = destinationJavaMethod.getDeclaringClass().getCanonicalName().replace('.',
				'/');
		String methodNameUrlPart = destinationJavaMethod.getName();
		String methodParametersUrlPart = Arrays.asList(destinationJavaMethod.getParameterTypes()).stream()
				.map(parameterType -> parameterType.getSimpleName()).collect(Collectors.joining("-"));
		String urlToJavadoc = urlPrefix + classNameUrlPart + urlPostfix + "#" + methodNameUrlPart + "-"
				+ methodParametersUrlPart;

		String targetUri = urlToJavadoc;
		Range targetRange = new Range(new Position(0, 0), new Position(0, 0));
		Range targetSelectionRange = targetRange;

		LocationLink locationLink = new LocationLink(targetUri, targetRange, targetSelectionRange,
				originSelectionRange);
		return locationLink;
	}

	/**
	 * Creates a {@link LocationLink} from the given {@link Range} to the given EMF {@link EOperation}
	 * destination.
	 * 
	 * @param linkOrigin
	 *            the (non-{@code null}) origin {@link EObject} of the link.
	 * @param originSelectionRange
	 *            the (non-{@code null}) origin selection {@link Range}.
	 * @param destinationEObject
	 *            the (non-{@code null}) destination EMF {@link EObject}.
	 * @return the {@link LocationLink} from {@code originSelectionRange} to {@code destinationEObject}.
	 */
	private LocationLink createLocationLinkFromRangeToEObjectDestination(EObject linkOrigin,
			Range originSelectionRange, EObject destinationEObject) {
		ASTNode acceleoAstNode = getAcceleoAstNodeContainingAqlElement(linkOrigin);
		AcceleoTextDocument acceleoTextDocument = this.getAcceleoTextDocumentContaining(acceleoAstNode);

		// TODO: implement link to EMF metamodel elements.
		// We probably want to retrieve the Java/EMF context (classpath, etc.) of the AcceleoTextDocument, and
		// use
		// it to retrieve the location of the EMF operation. Most of this mechanism should be implemented for
		// the
		// resolution of modules anyway.

		// FIXME: temporary placeholder so it still sort of works
		String url = EcoreUtil.getURI(destinationEObject).toString();

		String targetUri = url;
		Range targetRange = new Range(new Position(0, 0), new Position(0, 0));
		Range targetSelectionRange = targetRange;

		LocationLink locationLink = new LocationLink(targetUri, targetRange, targetSelectionRange,
				originSelectionRange);
		return locationLink;
	}
}
