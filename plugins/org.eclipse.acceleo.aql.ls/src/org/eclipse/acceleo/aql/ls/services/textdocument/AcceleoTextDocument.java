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

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.location.AcceleoLocator;
import org.eclipse.acceleo.aql.location.common.AbstractLocationLink;
import org.eclipse.acceleo.aql.ls.AcceleoLanguageServer;
import org.eclipse.acceleo.aql.ls.common.AcceleoLanguageServerPositionUtils;
import org.eclipse.acceleo.aql.ls.common.LocationUtils;
import org.eclipse.acceleo.aql.ls.services.workspace.AcceleoProject;
import org.eclipse.acceleo.aql.ls.services.workspace.AcceleoWorkspace;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.validation.AcceleoValidator;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.query.ast.Binding;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.impl.ECrossReferenceAdapterCrossReferenceProvider;
import org.eclipse.acceleo.query.runtime.impl.ResourceSetRootEObjectProvider;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.services.TextDocumentService;

/**
 * Represents an Acceleo Text Document as known by the Language Server. It is maintained consistent with the
 * contents of the client's editor thanks to the LSP-compliant notifications sent to the Language Server by
 * the client. It is used to provide the language services back to the client.
 * 
 * @author Florent Latombe
 */
public class AcceleoTextDocument {

	/**
	 * FIXME: move somewhere else.
	 */
	private static final String VALIDATION_NAMESPACE = "_reserved_::to::validate";

	/**
	 * The {@link URI} that uniquely identifies this document.
	 */
	private final URI uri;

	/**
	 * The {@link AcceleoProject} that contains this text document.
	 */
	private AcceleoProject ownerProject;

	/**
	 * The {@link IQualifiedNameQueryEnvironment} for this text document.
	 */
	private IQualifiedNameQueryEnvironment queryEnvironment;

	/**
	 * The {@link String} contents of the text document.
	 */
	private String contents;

	// Cached values that depend on the contents.
	/**
	 * The cached {@link AcceleoAstResult} resulting form parsing the contents of the text document.
	 */
	private AcceleoAstResult acceleoAstResult;

	/**
	 * The cached {@link IAcceleoValidationResult}, updated upon any changes in the contents of this document.
	 */
	private IAcceleoValidationResult acceleoValidationResult;

	/**
	 * The module qualified name.
	 */
	private String qualifiedName;

	/**
	 * Tells if the document is
	 * {@link TextDocumentService#didOpen(org.eclipse.lsp4j.DidOpenTextDocumentParams) opened}.
	 */
	private boolean isOpened;

	/**
	 * Creates a new {@link AcceleoTextDocument} corresponding to the given URI and with the given initial
	 * contents.
	 * 
	 * @param textDocumentUri
	 *            the (non-{@code null}) {@link URI} of this text document.
	 * @param textDocumentContents
	 *            the (non-{@code null}) initial contents of this text document.
	 * @param project
	 *            the owner project
	 */
	public AcceleoTextDocument(URI textDocumentUri, String textDocumentContents, AcceleoProject project) {
		Objects.requireNonNull(textDocumentUri);
		Objects.requireNonNull(textDocumentContents);
		this.ownerProject = project;
		this.uri = textDocumentUri;
		qualifiedName = getProject().getResolver().getQualifiedName(getUri());

		this.setContents(textDocumentContents);
	}

	/**
	 * Behavior triggered when the contents or environment of this document have changed. We update the cached
	 * values of the parsing and validation results.
	 */
	private void documentChanged() {
		// Retrieve the parsing result first, as other services depend on it.
		this.parseContents();

		// And validation second, as some other services depend on it.
		this.validateAndPublishResults();
	}

	/**
	 * This is called when this document is saved. Notifies external parties which may rely on this document.
	 */
	public void documentSaved() {
		// TODO: we probably only want to send this notification out when the "publicly-accessible" parts of
		// the Module have changed, like a public/protected Template/Query signature.
		if (this.getProject() != null) {
			// This text document belongs to an AcceleoProject which holds the resolver in which
			// this module is registered.
			this.getProject().documentSaved(this);
		}
	}

	/**
	 * Sets the owner {@link AcceleoProject} of this document.
	 * 
	 * @param acceleoProject
	 *            the new (maybe-{@code null}) owner {@link AcceleoProject}.
	 */
	public void setProject(AcceleoProject acceleoProject) {
		AcceleoProject oldProject = ownerProject;
		this.ownerProject = acceleoProject;
		qualifiedName = getProject().getResolver().getQualifiedName(getUri());
		if ((acceleoProject == null && oldProject != null) || !acceleoProject.equals(oldProject)) {
			// When the project changes, the environment changes.
			this.resolverChanged();
		}
	}

	/**
	 * This is called to notify this {@link AcceleoTextDocument} that its contextual
	 * {@link IQualifiedNameResolver} has changed. As a result, it needs to be re-parsed and re-validated.
	 */
	public void resolverChanged() {
		this.documentChanged();
	}

	/**
	 * Provides the owner {@link AcceleoProject} of this document.
	 * 
	 * @return the (maybe-{@code null}) owner {@link AcceleoProject}.
	 */
	public AcceleoProject getProject() {
		return this.ownerProject;
	}

	/**
	 * Provides the file name without its extension.
	 * 
	 * @return the file name of this text document.
	 */
	public String getFileNameWithoutExtension() {
		return this.uri.toString().substring(this.uri.toString().lastIndexOf('/'), this.uri.toString()
				.lastIndexOf('.'));
	}

	/**
	 * Parses the contents of this document and updates the cached AST.
	 */
	private void parseContents() {
		AcceleoAstResult parsingResult = null;
		parsingResult = doParsing(this.getModuleQualifiedName(), this.contents);

		final ResourceSet resourceSetForModels = new ResourceSetImpl(); // this will not be used
		final ECrossReferenceAdapterCrossReferenceProvider crossReferenceProvider = new ECrossReferenceAdapterCrossReferenceProvider(
				ECrossReferenceAdapter.getCrossReferenceAdapter(resourceSetForModels));
		final ResourceSetRootEObjectProvider rootProvider = new ResourceSetRootEObjectProvider(
				resourceSetForModels);
		queryEnvironment = Query.newQualifiedNameEnvironmentWithDefaultServices(getProject().getResolver(),
				crossReferenceProvider, rootProvider);

		for (Metamodel metamodel : parsingResult.getModule().getMetamodels()) {
			if (metamodel.getReferencedPackage() != null) {
				queryEnvironment.registerEPackage(metamodel.getReferencedPackage());
			}
		}
		this.acceleoAstResult = parsingResult;
	}

	/**
	 * Performs the parsing.
	 * 
	 * @param moduleQualifiedName
	 *            the (non-{@code null}) qualified name of the document we are parsing.
	 * @param documentContents
	 *            the (non-{@code null}) contents of the document we are parsing.
	 * @return the resulting {@link AcceleoAstResult}.
	 */
	private AcceleoAstResult doParsing(String moduleQualifiedName, String documentContents) {
		final AcceleoAstResult res;

		if (isOpened()) {
			Objects.requireNonNull(moduleQualifiedName);
			Objects.requireNonNull(documentContents);
			AcceleoParser acceleoParser = new AcceleoParser();
			res = acceleoParser.parse(documentContents, moduleQualifiedName);
		} else {
			res = ((Module)ownerProject.getResolver().resolve(moduleQualifiedName)).getAst();
		}

		return res;
	}

	/**
	 * Validates the contents of this document, and caches its results.
	 */
	public void validateContents() {
		IAcceleoValidationResult validationResults = null;
		if (this.acceleoAstResult != null && this.getQueryEnvironment() != null) {
			validationResults = doValidation(this.acceleoAstResult, this.getQueryEnvironment(),
					getModuleQualifiedName());
		}
		this.acceleoValidationResult = validationResults;
	}

	/**
	 * Validates the contents of this document using the {@link AcceleoValidator}. As a side effect, it
	 * registers the resulting
	 * 
	 * @param acceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult} to validate.
	 * @param queryEnvironment
	 *            the (non-{@code null}) {@link IQualifiedNameQueryEnvironment} of the document being
	 *            validated.
	 * @param qualifiedName
	 *            the context qualified name
	 * @return the {@link IAcceleoValidationResult}.
	 */
	private static IAcceleoValidationResult doValidation(AcceleoAstResult acceleoAstResult,
			IQualifiedNameQueryEnvironment queryEnvironment, String qualifiedName) {
		Objects.requireNonNull(acceleoAstResult);
		Objects.requireNonNull(queryEnvironment);

		return validate(queryEnvironment, acceleoAstResult, qualifiedName);
	}

	/**
	 * Provides the owning {@link AcceleoLanguageServer}.
	 * 
	 * @return the (maybe-{@code null}) owning {@link AcceleoLanguageServer}.
	 */
	public AcceleoLanguageServer getLanguageServer() {
		if (this.getProject() != null) {
			return this.getProject().getLanguageServer();
		} else {
			return null;
		}
	}

	/**
	 * Provides the {@link AcceleoTextDocumentService} of the owning {@link AcceleoLanguageServer}.
	 * 
	 * @return the (maybe-{@code null}) {@link AcceleoTextDocumentService}.
	 */
	public AcceleoTextDocumentService getTextDocumentService() {
		AcceleoLanguageServer languageServer = this.getLanguageServer();
		if (languageServer != null) {
			return languageServer.getTextDocumentService();
		} else {
			return null;
		}
	}

	/**
	 * Publishes the cached validation results.
	 */
	public void publishValidationResults() {
		AcceleoTextDocumentService service = this.getTextDocumentService();
		if (service != null) {
			service.publishValidationResults(this);
		}
	}

	/**
	 * Validates this document and publishes the validation results.
	 */
	public void validateAndPublishResults() {
		this.validateContents();
		this.publishValidationResults();
	}

	/**
	 * Provides the qualified name of the Acceleo {@link Module} represented by this document, as computed by
	 * the {@link IQualifiedNameResolver}.
	 * 
	 * @return the qualified name of the {@link Module}. {@code null} if this document has no
	 *         {@link IQualifiedNameResolver}.
	 */
	public String getModuleQualifiedName() {
		return qualifiedName;
	}

	/**
	 * Provides the links from the given position to the location(s) defining the element at the given
	 * position.
	 * 
	 * @param position
	 *            the (positive) position in the source contents.
	 * @return the {@link List} of {@link AbstractLocationLink} corresponding to the definition location(s) of
	 *         the Acceleo element found at the given position in the source contents.
	 */
	public List<AbstractLocationLink<?, ?>> getDefinitionLocations(int position) {
		final IQualifiedNameLookupEngine lookupEngine = getQueryEnvironment().getLookupEngine();
		lookupEngine.pushImportsContext(getModuleQualifiedName(), getModuleQualifiedName());
		List<AbstractLocationLink<?, ?>> definitionLocations = new AcceleoLocator(getQueryEnvironment(),
				getModuleQualifiedName(), acceleoValidationResult).getDefinitionLocations(
						this.acceleoAstResult, position);
		lookupEngine.popContext(getModuleQualifiedName());
		return definitionLocations;
	}

	/**
	 * Provides the links from the given position to the location(s) declaring the element at the given
	 * position.
	 * 
	 * @param position
	 *            the (positive) position in the source contents.
	 * @return the {@link List} of {@link AbstractLocationLink} corresponding to the declaration location(s)
	 *         of the Acceleo element found at the given position in the source contents.
	 */
	public List<AbstractLocationLink<?, ?>> getDeclarationLocations(int position) {
		return new AcceleoLocator(getQueryEnvironment(), getModuleQualifiedName(), acceleoValidationResult)
				.getDeclarationLocations(this.acceleoAstResult, position);
	}

	/**
	 * Provides the links from the location(s) defining the element at the given position to its references.
	 * 
	 * @param position
	 *            the (positive) position in the source contents.
	 * @return the {@link List} of {@link AbstractLocationLink} corresponding to the references to the
	 *         definition location(s) of the Acceleo element found at the given position in the source
	 *         contents.
	 */
	public List<Location> getReferencesLocations(int position) {
		final List<Location> referencesLocations = new ArrayList<>();

		final List<AbstractLocationLink<?, ?>> definitionLocations = getDefinitionLocations(position);
		for (AbstractLocationLink<?, ?> declarationLocation : definitionLocations) {
			final Object declaration = declarationLocation.getDestination();
			if (declaration instanceof Binding) {
				for (VarRef varRef : acceleoValidationResult.getResolvedVarRef((Binding)declaration)) {
					final Location location = LocationUtils.identifierLocation(queryEnvironment,
							getModuleQualifiedName(), acceleoValidationResult, varRef);
					referencesLocations.add(location);
				}
			} else if (declaration instanceof VariableDeclaration) {
				for (VarRef varRef : acceleoValidationResult.getResolvedVarRef(
						(VariableDeclaration)declaration)) {
					final Location location = LocationUtils.identifierLocation(queryEnvironment,
							getModuleQualifiedName(), acceleoValidationResult, varRef);
					referencesLocations.add(location);
				}
			} else if (declaration instanceof Variable) {
				for (VarRef varRef : acceleoValidationResult.getResolvedVarRef((Variable)declaration)) {
					final Location location = LocationUtils.identifierLocation(queryEnvironment,
							getModuleQualifiedName(), acceleoValidationResult, varRef);
					referencesLocations.add(location);
				}
			} else if (declaration instanceof IService<?>) {
				final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
				final Set<String> qualifiedNames = new LinkedHashSet<>();
				final String serviceContextQualifiedName = resolver.getContextQualifiedName(
						(IService<?>)declaration);
				qualifiedNames.add(serviceContextQualifiedName);
				qualifiedNames.addAll(resolver.getDependOn(serviceContextQualifiedName));
				final AcceleoWorkspace workspace = getProject().getWorkspace();
				for (String dependentQualifiedName : qualifiedNames) {
					final URI sourceURI = resolver.getSourceURI(dependentQualifiedName);
					final AcceleoTextDocument document = workspace.getTextDocument(sourceURI);
					if (document != null) {
						final List<Call> resolvedCalls = document.getAcceleoValidationResults()
								.getResolvedCalls((IService<?>)declaration);
						for (Call call : resolvedCalls) {
							final Location location = LocationUtils.identifierLocation(queryEnvironment,
									dependentQualifiedName, document.getAcceleoValidationResults(), call);
							referencesLocations.add(location);
						}
					} else {
						// TODO not a module but a class or something else
					}
				}
			} else if (declaration instanceof Module) {
				final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
				final AcceleoWorkspace workspace = getProject().getWorkspace();
				for (String dependentQualifiedName : resolver.getDependOn(getModuleQualifiedName())) {
					final URI sourceURI = resolver.getSourceURI(dependentQualifiedName);
					final AcceleoTextDocument document = workspace.getTextDocument(sourceURI);
					final Module module = document.getAcceleoAstResult().getModule();
					final Location location = LocationUtils.identifierLocation(queryEnvironment,
							dependentQualifiedName, document.getAcceleoValidationResults(), module);
					referencesLocations.add(location);
				}

			} else {
				// TODO metamodel ?
				// nothing to do here
			}
		}

		return referencesLocations;
	}

	/**
	 * Provides the URI of this {@link AcceleoTextDocument}.
	 * 
	 * @return the {@link URI} of this {@link AcceleoTextDocument}.
	 */
	public URI getUri() {
		return this.uri;
	}

	/**
	 * Applies {@link TextDocumentContentChangeEvent TextDocumentContentChangeEvents} to the contents of this
	 * text document.
	 * 
	 * @param textDocumentContentchangeEvents
	 *            the {@link Iterable} of {@link TextDocumentContentChangeEvent} to apply, in the same order.
	 * @return this {@link AcceleoTextDocument}, with the new contents resulting from applying the changes.
	 */
	public AcceleoTextDocument applyChanges(
			Iterable<TextDocumentContentChangeEvent> textDocumentContentchangeEvents) {
		String newTextDocumentContents = this.contents;
		for (TextDocumentContentChangeEvent textDocumentContentChangeEvent : textDocumentContentchangeEvents) {
			newTextDocumentContents = apply(textDocumentContentChangeEvent, newTextDocumentContents);
		}
		this.setContents(newTextDocumentContents);

		return this;
	}

	/**
	 * Updates the known contents of the text document, which triggers the parsing of the new contents.
	 * 
	 * @param newTextDocumentContents
	 *            the new (non-{@code null}) contents of the text document.
	 */
	public void setContents(String newTextDocumentContents) {
		this.contents = newTextDocumentContents;
		this.documentChanged();
	}

	/**
	 * Provides the Abstract Syntax Tree (AST) corresponding to the contents of this text document.
	 * 
	 * @return the (non-{@code null}) {@link AcceleoAstResult} of this document.
	 */
	public AcceleoAstResult getAcceleoAstResult() {
		return acceleoAstResult;
	}

	/**
	 * Provides the results of the Acceleo validation of the contents of this text document.
	 * 
	 * @return the (non-{@code null}) {@link IAcceleoValidationResult} of this document.
	 */
	public IAcceleoValidationResult getAcceleoValidationResults() {
		return this.acceleoValidationResult;
	}

	/**
	 * Provides the {@link IQualifiedNameQueryEnvironment} associated to this text document.
	 * 
	 * @return the (maybe-{@code null}) {@link IQualifiedNameQueryEnvironment} associated to this
	 *         {@link AcceleoTextDocument}.
	 */
	public IQualifiedNameQueryEnvironment getQueryEnvironment() {
		return queryEnvironment;
	}

	/**
	 * Provides the current {@link String text contents} of this text document.
	 * 
	 * @return the (non-{@code null}) {@link String} contents of this {@link AcceleoTextDocument}.
	 */
	public String getContents() {
		return this.contents;
	}

	/**
	 * Performs the Acceleo validation.
	 * 
	 * @param queryEnvironment
	 *            the (non-{@code null}) {@link IQualifiedNameQueryEnvironment}.
	 * @param acceleoAstResult
	 *            the (non-{@code null}) {@link AcceleoAstResult}.
	 * @param qualifiedName
	 *            the context qualified name
	 * @return the {@link IAcceleoValidationResult}.
	 */
	// FIXME the "synchronized" here is an ugly but convenient way to ensure that a validation finishes before
	// any other is triggered. Otherwise a validation can push imports which invalidates the services lookup
	// for another validation
	private static synchronized IAcceleoValidationResult validate(
			IQualifiedNameQueryEnvironment queryEnvironment, AcceleoAstResult acceleoAstResult,
			String qualifiedName) {
		String moduleQualifiedNameForValidation = VALIDATION_NAMESPACE + AcceleoParser.QUALIFIER_SEPARATOR
				+ qualifiedName;
		final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
		resolver.register(moduleQualifiedNameForValidation, acceleoAstResult.getModule());
		try {
			final AcceleoValidator acceleoValidator = new AcceleoValidator(queryEnvironment);
			final IAcceleoValidationResult validationResults = acceleoValidator.validate(acceleoAstResult,
					moduleQualifiedNameForValidation);
			return validationResults;
		} finally {
			resolver.clear(Collections.singleton(moduleQualifiedNameForValidation));
		}
	}

	/**
	 * Applies a {@link TextDocumentContentChangeEvent} to the {@link String} representing the text document
	 * contents.
	 * 
	 * @param textDocumentContentChangeEvent
	 *            the (non-{@code null}) {@link TextDocumentContentChangeEvent} to apply.
	 * @param inText
	 *            the (non-{@code null}) current {@link String text document contents}.
	 * @return the new {@link String text document contents}.
	 */
	private static String apply(TextDocumentContentChangeEvent textDocumentContentChangeEvent,
			String inText) {
		String newTextExcerpt = textDocumentContentChangeEvent.getText();
		Range changeRange = textDocumentContentChangeEvent.getRange();
		// We can safely ignore the range length, which gets deprecated in the next version of LSP.
		// cf. https://github.com/Microsoft/language-server-protocol/issues/9

		if (changeRange == null) {
			// The whole text was replaced.
			return newTextExcerpt;
		} else {
			return AcceleoLanguageServerPositionUtils.replace(inText, changeRange.getStart(), changeRange
					.getEnd(), newTextExcerpt);
		}
	}

	/**
	 * Tells if the document is
	 * {@link TextDocumentService#didOpen(org.eclipse.lsp4j.DidOpenTextDocumentParams) opened}.
	 * 
	 * @return <code>true</code> if the document is
	 *         {@link TextDocumentService#didOpen(org.eclipse.lsp4j.DidOpenTextDocumentParams) opened},
	 *         <code>false</code> otherwise
	 */
	public boolean isOpened() {
		return isOpened;
	}

	/**
	 * Sets if the document is {@link TextDocumentService#didOpen(org.eclipse.lsp4j.DidOpenTextDocumentParams)
	 * opened}.
	 * 
	 * @param opened
	 *            the new value
	 */
	public void setOpened(boolean opened) {
		this.isOpened = opened;
	}

}
