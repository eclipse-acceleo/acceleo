/*******************************************************************************
 * Copyright (c) 2020, 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.services.textdocument;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.ls.AcceleoLanguageServer;
import org.eclipse.acceleo.aql.ls.common.AcceleoLanguageServerPositionUtils;
import org.eclipse.acceleo.aql.ls.common.LocationUtils;
import org.eclipse.acceleo.aql.ls.services.workspace.AcceleoProject;
import org.eclipse.acceleo.aql.ls.services.workspace.AcceleoWorkspace;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.validation.AcceleoValidator;
import org.eclipse.acceleo.aql.validation.DeclarationSwitch;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.aql.validation.quickfixes.AcceleoQuickFixesSwitch;
import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.ast.ASTNode;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.Declaration;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.StringLiteral;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.parser.AstBuilderListener;
import org.eclipse.acceleo.query.parser.quickfixes.CreateResource;
import org.eclipse.acceleo.query.parser.quickfixes.DeleteResource;
import org.eclipse.acceleo.query.parser.quickfixes.IAstQuickFix;
import org.eclipse.acceleo.query.parser.quickfixes.IAstResourceChange;
import org.eclipse.acceleo.query.parser.quickfixes.IAstTextReplacement;
import org.eclipse.acceleo.query.parser.quickfixes.MoveResource;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation;
import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryWorkspaceQualifiedNameResolver;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.lsp4j.CodeAction;
import org.eclipse.lsp4j.CodeActionContext;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.CreateFile;
import org.eclipse.lsp4j.DeleteFile;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PrepareRenameResult;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.RenameFile;
import org.eclipse.lsp4j.ResourceOperation;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextDocumentEdit;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
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
	 * The {@link ResourceSet} for models.
	 */
	private ResourceSet resourceSetForModels;

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
	 * @param project
	 *            the (non-{@code null}) owner {@link AcceleoProject}
	 * @param textDocumentUri
	 *            the (non-{@code null}) {@link URI} of this text document.
	 * @param qualifiedName
	 *            the (non-{@code null}) module qualified name
	 * @param textDocumentContents
	 *            the (non-{@code null}) initial contents of this text document.
	 * @param module
	 *            the {@link Module}
	 */
	public AcceleoTextDocument(AcceleoProject project, URI textDocumentUri, String qualifiedName,
			String textDocumentContents, Module module) {
		Objects.requireNonNull(project);
		Objects.requireNonNull(textDocumentUri);
		Objects.requireNonNull(textDocumentContents);
		Objects.requireNonNull(qualifiedName);
		this.ownerProject = project;
		this.uri = textDocumentUri;
		this.qualifiedName = qualifiedName;
		this.contents = textDocumentContents;
		this.acceleoAstResult = module.getAst();
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
		return this.uri.toString().substring(this.uri.toString().lastIndexOf('/') + 1, this.uri.toString()
				.lastIndexOf('.'));
	}

	/**
	 * Parses the contents of this document and updates the cached AST.
	 */
	public void parseContents() {
		AcceleoAstResult parsingResult = null;

		if (isOpened()) {
			AcceleoParser acceleoParser = new AcceleoParser();
			final String encoding;
			try (InputStream is = new ByteArrayInputStream(this.contents.getBytes())) {
				encoding = acceleoParser.parseEncoding(is);
				parsingResult = acceleoParser.parse(this.contents, encoding, this.getModuleQualifiedName());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			final Module resolvedModule = (Module)getProject().getResolver().resolve(qualifiedName);
			parsingResult = resolvedModule.getAst();
		}

		this.acceleoAstResult = parsingResult;
	}

	/**
	 * Validates the contents of this document, and caches its results.
	 */
	private void validateContents() {
		final IQualifiedNameResolver resolver = getProject().getResolver();
		// TODO get options form ??? or list all possible options ?
		// don't add any options ?
		final Map<String, String> options = new LinkedHashMap<>();
		final ArrayList<Exception> exceptions = new ArrayList<>();
		// the ResourceSet will not be used
		resourceSetForModels = AQLUtils.createResourceSetForModels(exceptions, this, new ResourceSetImpl(),
				options);
		// TODO report exceptions
		queryEnvironment = AcceleoUtil.newAcceleoQueryEnvironment(options, resolver, resourceSetForModels,
				true);

		for (Metamodel metamodel : getAcceleoAstResult().getModule().getMetamodels()) {
			if (metamodel.getReferencedPackage() != null) {
				final EPackage ePkg = resolver.getEPackage(metamodel.getReferencedPackage());
				if (ePkg != null) {
					queryEnvironment.registerEPackage(ePkg);
				}
			}
		}
		try {
			final IAcceleoValidationResult validationResults;
			if (isOpened()) {
				String moduleQualifiedNameForValidation = VALIDATION_NAMESPACE
						+ AcceleoParser.QUALIFIER_SEPARATOR + qualifiedName;
				resolver.register(moduleQualifiedNameForValidation, acceleoAstResult.getModule());
				try {
					validationResults = validate(queryEnvironment, this.acceleoAstResult,
							moduleQualifiedNameForValidation);
				} finally {
					resolver.clear(Collections.singleton(moduleQualifiedNameForValidation));
				}
			} else {
				validationResults = validate(queryEnvironment, acceleoAstResult, qualifiedName);
			}
			this.acceleoValidationResult = validationResults;
		} finally {
			AQLUtils.cleanResourceSetForModels(this, resourceSetForModels);
			AcceleoUtil.cleanServices(queryEnvironment, resourceSetForModels);
		}
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
	 * Validates this document and publishes the validation results.
	 */
	public void validateAndPublishResults() {
		this.validateContents();
		final AcceleoTextDocumentService service = this.getTextDocumentService();
		if (service != null) {
			service.publishValidationResults(this);
		}
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
	 * @return the {@link List} of {@link LocationLink} corresponding to the definition location(s) of the
	 *         Acceleo element found at the given position in the source contents.
	 */
	public List<LocationLink> getDefinitionLocations(int position) {
		// In the case of Acceleo and AQL all definition are located at the declaration.
		return getDeclarationLocations(position, true);
	}

	/**
	 * Provides the links from the given position to the location(s) declaring the element at the given
	 * position.
	 * 
	 * @param position
	 *            the (positive) position in the source contents.
	 * @param withCompatibleServices
	 *            tells if we should also return all compatible {@link IService}
	 * @return the {@link List} of {@link LocationLink} corresponding to the declaration location(s) of the
	 *         Acceleo element found at the given position in the source contents.
	 */
	public List<LocationLink> getDeclarationLocations(int position, boolean withCompatibleServices) {
		final List<LocationLink> declarationLocations = new ArrayList<>();

		final ASTNode acceleoOrAqlNodeUnderCursor = acceleoAstResult.getAstNode(position);
		final Range originSelectionRange;
		if (acceleoOrAqlNodeUnderCursor instanceof VarRef) {
			originSelectionRange = LocationUtils.identifierRange(acceleoValidationResult,
					(VarRef)acceleoOrAqlNodeUnderCursor);
		} else if (acceleoOrAqlNodeUnderCursor instanceof Call) {
			originSelectionRange = LocationUtils.identifierRange(acceleoValidationResult,
					(Call)acceleoOrAqlNodeUnderCursor);
		} else if (acceleoOrAqlNodeUnderCursor instanceof ModuleReference) {
			originSelectionRange = LocationUtils.range(acceleoValidationResult,
					(ModuleReference)acceleoOrAqlNodeUnderCursor);
		} else {
			originSelectionRange = null;
		}

		final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
		final List<Object> declarations = getDeclaration(acceleoOrAqlNodeUnderCursor, withCompatibleServices);

		for (Object declaration : declarations) {
			if (declaration instanceof Declaration) {
				final Declaration aqlDeclaration = (Declaration)declaration;
				declarationLocations.add(getDeclarationLocation(originSelectionRange, aqlDeclaration));
			} else if (declaration instanceof Variable) {
				final Variable variable = (Variable)declaration;
				declarationLocations.add(getDeclarationLocation(originSelectionRange, variable));
			} else if (declaration instanceof IService<?>) {
				final IService<?> service = (IService<?>)declaration;
				final ISourceLocation sourceLocation = resolver.getSourceLocation(service);
				if (sourceLocation != null) {
					final URI sourceURI = getProject().getResolver().getSourceURI(getModuleQualifiedName());
					final LocationLink locationLink = new LocationLink(sourceURI.toASCIIString(),
							LocationUtils.range(sourceLocation), LocationUtils.identifierRange(
									sourceLocation));
					locationLink.setOriginSelectionRange(originSelectionRange);
					locationLink.setTargetUri(sourceLocation.getSourceURI().toASCIIString());
					declarationLocations.add(locationLink);
				}
			} else {
				final String declarationQualifiedName;
				if (declaration instanceof Module) {
					// TODO this case could fall under the last else if we didn't use VALIDATION_NAMESPACE
					declarationQualifiedName = ((Module)declaration).eResource().getURI().toString()
							.substring(AcceleoParser.ACCELEOENV_URI_PROTOCOL.length());
				} else {
					declarationQualifiedName = resolver.getQualifiedName(declaration);
				}
				final ISourceLocation sourceLocation = resolver.getSourceLocation(declarationQualifiedName);
				final URI sourceURI = getProject().getResolver().getSourceURI(getModuleQualifiedName());
				final LocationLink locationLink = new LocationLink(sourceURI.toASCIIString(), LocationUtils
						.range(sourceLocation), LocationUtils.identifierRange(sourceLocation));
				locationLink.setOriginSelectionRange(originSelectionRange);
				locationLink.setTargetUri(sourceLocation.getSourceURI().toASCIIString());
				declarationLocations.add(locationLink);
			}
		}

		return declarationLocations;
	}

	/**
	 * Gets the declaration {@link LocationLink} for the given {@link Variable}.
	 * 
	 * @param originSelectionRange
	 *            the original selection {@link Range}
	 * @param variable
	 *            the {@link Variable}
	 * @return the declaration {@link LocationLink} for the given {@link Variable}
	 */
	private LocationLink getDeclarationLocation(final Range originSelectionRange, final Variable variable) {
		final Range identifierRange = LocationUtils.identifierRange(acceleoValidationResult, variable);
		final Range range = LocationUtils.range(acceleoValidationResult, variable);
		final URI sourceURI = getProject().getResolver().getSourceURI(getModuleQualifiedName());
		final LocationLink locationLink = new LocationLink(sourceURI.toASCIIString(), range, identifierRange);
		locationLink.setOriginSelectionRange(originSelectionRange);
		locationLink.setTargetUri(sourceURI.toASCIIString());
		return locationLink;
	}

	/**
	 * Gets the declaration {@link LocationLink} for the given {@link Declaration}.
	 * 
	 * @param originSelectionRange
	 *            the original selection {@link Range}
	 * @param declaration
	 *            the {@link Declaration}
	 * @return the declaration {@link LocationLink} for the given {@link Declaration}
	 */
	private LocationLink getDeclarationLocation(final Range originSelectionRange, Declaration declaration) {
		final Range identifierRange = LocationUtils.identifierRange(acceleoValidationResult, declaration);
		final Range range = LocationUtils.range(acceleoValidationResult, declaration);
		final URI sourceURI = getProject().getResolver().getSourceURI(getModuleQualifiedName());
		final LocationLink locationLink = new LocationLink(sourceURI.toASCIIString(), range, identifierRange);
		locationLink.setOriginSelectionRange(originSelectionRange);
		locationLink.setTargetUri(sourceURI.toASCIIString());
		return locationLink;
	}

	/**
	 * Gets the {@link List} of declaration {@link Object} for the given {@link EObject}.
	 * 
	 * @param eObject
	 *            the {@link EObject}
	 * @param withCompatibleServices
	 *            tells if we should also return all compatible {@link IService}
	 * @return the {@link List} of declaration {@link Object} for the given {@link EObject}
	 */
	private List<Object> getDeclaration(EObject eObject, boolean withCompatibleServices) {
		final DeclarationSwitch declarationSwitch = new DeclarationSwitch(acceleoValidationResult,
				queryEnvironment, withCompatibleServices);

		return declarationSwitch.getDeclarations(getModuleQualifiedName(), eObject);
	}

	/**
	 * Provides the links from the location(s) defining the element at the given position to its references.
	 * 
	 * @param position
	 *            the (positive) position in the source contents.
	 * @param withCompatibleServices
	 *            tells if we should also return all compatible {@link IService}
	 * @return the {@link List} of {@link Location} corresponding to the references to the definition
	 *         location(s) of the Acceleo element found at the given position in the source contents.
	 */
	public List<Location> getReferencesLocations(int position, boolean withCompatibleServices) {
		final List<Location> referencesLocations = new ArrayList<>();

		final ASTNode acceleoOrAqlNodeUnderCursor = acceleoAstResult.getAstNode(position);
		final List<Object> firstDeclarations = getDeclaration(acceleoOrAqlNodeUnderCursor,
				withCompatibleServices);
		final AcceleoWorkspace workspace = getProject().getWorkspace();
		boolean needDeclarationsRefesh = false;
		for (Object declaration : firstDeclarations) {
			if (declaration instanceof IService<?>) {
				final IQueryWorkspaceQualifiedNameResolver resolver = getProject().getResolver();
				final String serviceContextQualifiedName = resolver.getContextQualifiedName(
						(IService<?>)declaration);
				final URI serviceContextURI = resolver.getURI(serviceContextQualifiedName);
				final AcceleoTextDocument document = workspace.getDocument(serviceContextURI);
				if (document != null && document.isOpened()) {
					workspace.registerAndPropagateChanges(document.getProject(), serviceContextQualifiedName,
							document.getAcceleoAstResult().getModule());
					needDeclarationsRefesh = true;
				}
			}
		}
		final List<Object> declarations;
		if (needDeclarationsRefesh) {
			declarations = getDeclaration(acceleoOrAqlNodeUnderCursor, withCompatibleServices);
		} else {
			declarations = firstDeclarations;
		}
		for (Object declaration : declarations) {
			if (declaration instanceof Declaration) {
				for (VarRef varRef : acceleoValidationResult.getResolvedVarRef((Declaration)declaration)) {
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
				final IService<?> service = (IService<?>)declaration;
				referencesLocations.addAll(getServiceReferenceLocations(acceleoOrAqlNodeUnderCursor,
						service));
			} else {
				final IQueryWorkspaceQualifiedNameResolver resolver = getProject().getResolver();
				final String declarationQualifiedName;
				if (declaration instanceof Module) {
					// TODO this case could fall under the last else if we didn't use VALIDATION_NAMESPACE
					declarationQualifiedName = ((Module)declaration).eResource().getURI().toString()
							.substring(AcceleoParser.ACCELEOENV_URI_PROTOCOL.length());
				} else {
					declarationQualifiedName = resolver.getQualifiedName(declaration);
				}
				if (declarationQualifiedName != null) {
					final Set<URI> uris = new LinkedHashSet<>();
					final IQueryWorkspaceQualifiedNameResolver declarationResolver = resolver
							.getDeclarationResolver(declarationQualifiedName);
					for (String dependentQualifiedName : declarationResolver.getDependOn(
							declarationQualifiedName)) {
						uris.add(declarationResolver.getSourceURI(dependentQualifiedName));
					}
					for (IQueryWorkspaceQualifiedNameResolver dependentResolver : declarationResolver
							.getResolversDependOn()) {
						for (String dependOn : dependentResolver.getDependOn(declarationQualifiedName)) {
							uris.add(dependentResolver.getSourceURI(dependOn));
						}
					}

					for (URI uri : uris) {
						final AcceleoTextDocument document = workspace.getDocument(uri);
						if (document != null) {
							final Module module = document.getAcceleoAstResult().getModule();
							if (module.getExtends() != null && declarationQualifiedName.equals(module
									.getExtends().getQualifiedName())) {
								final Location location = LocationUtils.location(document
										.getQueryEnvironment(), document.getModuleQualifiedName(), document
												.getAcceleoValidationResults(), module.getExtends());
								referencesLocations.add(location);
							} else {
								for (Import imported : module.getImports()) {
									if (declarationQualifiedName.equals(imported.getModule()
											.getQualifiedName())) {
										final Location location = LocationUtils.location(document
												.getQueryEnvironment(), document.getModuleQualifiedName(),
												document.getAcceleoValidationResults(), imported.getModule());
										referencesLocations.add(location);
									}
								}
							}
						} else {
							// TODO not a module but a class or something else
						}
					}
				}
			}
		}

		return referencesLocations;
	}

	/**
	 * Gets the {@link List} of reference {@link Location} for the given {@link IService} declaration.
	 * 
	 * @param acceleoOrAqlNodeUnderCursor
	 *            the {@link ASTNode} at the cursor position
	 * @param service
	 *            the {@link IService} declaration
	 * @return the {@link List} of reference {@link Location} for the given {@link IService} declaration
	 */
	private final List<Location> getServiceReferenceLocations(ASTNode acceleoOrAqlNodeUnderCursor,
			IService<?> service) {
		final List<Location> res = new ArrayList<>();

		final IQueryWorkspaceQualifiedNameResolver resolver = getProject().getResolver();
		final AcceleoWorkspace workspace = getProject().getWorkspace();
		final Set<URI> uris = new LinkedHashSet<>();
		String serviceContextQualifiedName = resolver.getContextQualifiedName(service);

		// aqlFeatureAccess
		final Set<IType> featuresPossibleTypes;
		final String featureName;
		if (AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME.equals(service.getName())) {
			final Call originalCall = (Call)acceleoOrAqlNodeUnderCursor.eContainer();
			final Expression receiver = originalCall.getArguments().get(0);
			featuresPossibleTypes = acceleoValidationResult.getPossibleTypes(receiver);
			featureName = ((StringLiteral)originalCall.getArguments().get(1)).getValue();
		} else {
			featuresPossibleTypes = null;
			featureName = null;
		}

		if (serviceContextQualifiedName != null) {
			if (serviceContextQualifiedName.startsWith(VALIDATION_NAMESPACE
					+ AcceleoParser.QUALIFIER_SEPARATOR)) {
				serviceContextQualifiedName = serviceContextQualifiedName.substring((VALIDATION_NAMESPACE
						+ AcceleoParser.QUALIFIER_SEPARATOR).length());

			}

			final IQueryWorkspaceQualifiedNameResolver declarationResolver = resolver.getDeclarationResolver(
					serviceContextQualifiedName);
			final URI declarationContextSourceURI = declarationResolver.getSourceURI(
					serviceContextQualifiedName);
			uris.add(declarationContextSourceURI);
			for (String dependOn : declarationResolver.getDependOn(serviceContextQualifiedName)) {
				uris.add(resolver.getSourceURI(dependOn));
			}
			for (IQueryWorkspaceQualifiedNameResolver dependentResolver : declarationResolver
					.getResolversDependOn()) {
				for (String dependOn : dependentResolver.getDependOn(serviceContextQualifiedName)) {
					uris.add(dependentResolver.getSourceURI(dependOn));
				}
			}
		} else {
			// probably a standard service (select, oclIsKindOf, ...)
			// we need to scan all modules
			uris.addAll(workspace.getAllTextDocuments().stream().map(d -> d.getUri()).collect(Collectors
					.toList()));
		}
		for (URI uri : uris) {
			final AcceleoTextDocument document = workspace.getDocument(uri);
			if (document != null) {
				res.addAll(document.getLocalCallLocations(featuresPossibleTypes, featureName, service));
			} else {
				// TODO not a module but a class or something else
			}
		}

		return res;
	}

	/**
	 * Tells if the given {@link Call} is compatible with the given {@link Set} of possible {@link IType} and
	 * the given feature name.
	 * 
	 * @param featuresPossibleTypes
	 *            the {@link Set} of possible {@link IType}
	 * @param featureName
	 *            the feature name
	 * @param call
	 *            the {@link Call} to check
	 * @return <code>true</code> if the given {@link Call} is compatible with the given {@link Set} of
	 *         possible {@link IType} and the given feature name, <code>false</code> otherwise
	 */
	private boolean isCompatibleAqlFeatureAccess(Set<IType> featuresPossibleTypes, String featureName,
			Call call) {
		boolean res = false;
		final Expression receiver = call.getArguments().get(0);
		final Set<IType> receiverTypes = acceleoValidationResult.getPossibleTypes(receiver);
		if (call.getArguments().size() == 2 && featureName.equals(((StringLiteral)call.getArguments().get(1))
				.getValue())) {
			compatible: for (IType featuresPossibleType : featuresPossibleTypes) {
				for (IType receiverType : receiverTypes) {
					if (featuresPossibleType.isAssignableFrom(receiverType)) {
						res = true;
						break compatible;
					}
				}
			}
		}
		return res;
	}

	/**
	 * Gets the local {@link Location} of {@link Call} resolved to the given {@link IService}.
	 * 
	 * @param featuresPossibleTypes
	 *            the {@link Set} of possible {@link IType} in the case of an aql feature access,
	 *            <code>null</code> otherwise
	 * @param featureName
	 *            the feature name in the case of an aql feature access, <code>null</code> otherwise
	 * @param service
	 *            the {@link IService}
	 * @return the local {@link Location} of {@link Call} resolved to the given {@link IService}
	 */
	public List<Location> getLocalCallLocations(Set<IType> featuresPossibleTypes, String featureName,
			IService<?> service) {
		final List<Location> referencesLocations = new ArrayList<>();

		final List<Call> resolvedCalls = acceleoValidationResult.getResolvedCalls(service);
		for (Call call : resolvedCalls) {
			if (featureName != null && featuresPossibleTypes != null) {
				if (isCompatibleAqlFeatureAccess(featuresPossibleTypes, featureName, call)) {
					final Location location = LocationUtils.identifierLocation(queryEnvironment,
							getModuleQualifiedName(), acceleoValidationResult, call);
					referencesLocations.add(location);
				} else {
					// not compatible aql feature access
				}
			} else {
				final Location location = LocationUtils.identifierLocation(queryEnvironment,
						getModuleQualifiedName(), acceleoValidationResult, call);
				referencesLocations.add(location);
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
		Objects.requireNonNull(newTextDocumentContents);
		this.contents = newTextDocumentContents;
		this.parseContents();
		this.validateAndPublishResults();
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
	 * @param moduleQualifiedNameForValidation
	 *            the context qualified name
	 * @return the {@link IAcceleoValidationResult}.
	 */
	// FIXME the "synchronized" here is an ugly but convenient way to ensure that a validation finishes before
	// any other is triggered. Otherwise a validation can push imports which invalidates the services lookup
	// for another validation
	private static synchronized IAcceleoValidationResult validate(
			IQualifiedNameQueryEnvironment queryEnvironment, AcceleoAstResult acceleoAstResult,
			String moduleQualifiedNameForValidation) {
		final AcceleoValidator acceleoValidator = new AcceleoValidator(queryEnvironment);
		final IAcceleoValidationResult validationResults = acceleoValidator.validate(acceleoAstResult,
				moduleQualifiedNameForValidation);

		return validationResults;
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
	 * Opens this document with the given contents.
	 * 
	 * @param contents
	 *            the text contents
	 */
	public void open(String contents) {
		isOpened = true;
		setContents(contents);
	}

	/**
	 * Closes this document.
	 */
	public void close() {
		isOpened = false;
		parseContents();
		validateAndPublishResults();
	}

	/**
	 * Gets the {@link PrepareRenameResult} for the given index.
	 * 
	 * @param position
	 *            the (positive) position in the source contents.
	 * @return the {@link PrepareRenameResult} for the given index if any, <code>null</code> otherwise
	 */
	public PrepareRenameResult getPrepareRenameResult(int position) {
		final PrepareRenameResult res;

		final ASTNode astNode = acceleoAstResult.getAstNode(position);
		if (astNode != null) {
			final int start = acceleoAstResult.getIdentifierStartPosition(astNode);
			final int end = acceleoAstResult.getIdentifierEndPosition(astNode);
			if (start <= position && position <= end) {
				final Range range = LocationUtils.identifierRange(acceleoValidationResult, astNode);
				res = new PrepareRenameResult(range, contents.substring(start, end));
			} else {
				res = null;
			}
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Gets the mapping from a {@link URI} to its {@link List} of {@link TextEdit}.
	 * 
	 * @param position
	 *            the (positive) position in the source contents.
	 * @param newName
	 *            the new name
	 * @return the mapping from a {@link URI} to its {@link List} of {@link TextEdit}
	 */
	public Map<String, List<TextEdit>> getRenames(int position, String newName) {
		final Map<String, List<TextEdit>> res = new HashMap<>();

		for (LocationLink locationLink : getDeclarationLocations(position, true)) {
			final List<TextEdit> edits = res.computeIfAbsent(locationLink.getTargetUri(),
					uri -> new ArrayList<>());
			edits.add(new TextEdit(locationLink.getTargetSelectionRange(), newName));
		}

		for (Location location : getReferencesLocations(position, true)) {
			final List<TextEdit> edits = res.computeIfAbsent(location.getUri(), uri -> new ArrayList<>());
			edits.add(new TextEdit(location.getRange(), newName));
		}

		return res;
	}

	public List<Either<Command, CodeAction>> getCodeActions(int atStartIndex, int atEndIndex,
			CodeActionContext context) {
		final List<Either<Command, CodeAction>> res;

		final ASTNode astNode = acceleoAstResult.getAstNode(atEndIndex);
		if (astNode != null) {
			final AcceleoQuickFixesSwitch quickFixesSwitch = new AcceleoQuickFixesSwitch(
					getQueryEnvironment(), acceleoValidationResult, getModuleQualifiedName(), getContents(),
					System.lineSeparator());
			final List<IAstQuickFix> quickFixes = quickFixesSwitch.getQuickFixes(astNode);
			res = quickFixes.stream().map(qf -> transform(qf)).collect(Collectors.toList());
		} else {
			res = Collections.emptyList();
		}
		return res;
	}

	private Either<Command, CodeAction> transform(IAstQuickFix quickFix) {
		final CodeAction res = new CodeAction(quickFix.getName());
		final WorkspaceEdit workspaceEdit = new WorkspaceEdit();
		res.setEdit(workspaceEdit);
		final List<Either<TextDocumentEdit, ResourceOperation>> documentChanges = new ArrayList<>();
		workspaceEdit.setDocumentChanges(documentChanges);

		for (IAstResourceChange change : quickFix.getResourceChanges()) {
			final ResourceOperation resourceOperation;
			if (change instanceof CreateResource) {
				resourceOperation = new CreateFile(((CreateResource)change).getUri().toString());
			} else if (change instanceof DeleteResource) {
				resourceOperation = new DeleteFile(((DeleteResource)change).getUri().toString());
			} else if (change instanceof MoveResource) {
				resourceOperation = new RenameFile(((MoveResource)change).getSource().toString(),
						((MoveResource)change).getTarget().toString());
			} else {
				throw new IllegalStateException("unknown resource change.");
			}
			documentChanges.add(Either.forRight(resourceOperation));
		}

		for (IAstTextReplacement replacement : quickFix.getTextReplacements()) {
			final TextEdit textEdit = new TextEdit(getRange(replacement), replacement.getReplacement());
			// TODO version
			final VersionedTextDocumentIdentifier version = new VersionedTextDocumentIdentifier(replacement
					.getURI().toString(), null);
			final TextDocumentEdit textDocumentEdit = new TextDocumentEdit(version, new ArrayList<>());
			textDocumentEdit.getEdits().add(textEdit);
			documentChanges.add(Either.forLeft(textDocumentEdit));
		}

		return Either.forRight(res);
	}

	/**
	 * Gets the {@link Range} corresponding to the given {@link IAstTextReplacement}.
	 * 
	 * @param replacement
	 *            the {@link IAstTextReplacement}
	 * @return the {@link Range} corresponding to the given {@link IAstTextReplacement}
	 */
	private Range getRange(IAstTextReplacement replacement) {
		final Position start = new Position(replacement.getStartLine(), replacement.getStartColumn());
		final Position end = new Position(replacement.getEndLine(), replacement.getEndColumn());

		return new Range(start, end);
	}

}
