/*******************************************************************************
 * Copyright (c) 2020, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.services.workspace;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.acceleo.aql.ls.AcceleoLanguageServer;
import org.eclipse.acceleo.aql.ls.services.textdocument.AcceleoTextDocument;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;

/**
 * A representation, in the {@link AcceleoLanguageServer} of a container of {@link AcceleoTextDocument
 * AcceleoTextDocuments} that share a same {@link IQualifiedNameQueryEnvironment}. It may or may not
 * correspond to a physical element in the client.
 * 
 * @author Florent Latombe
 */
public class AcceleoProject {

	/**
	 * The {@link Map} that indexes the {@link AcceleoTextDocument} of this project by their {@link URI}.
	 */
	private final Map<URI, AcceleoTextDocument> acceleoTextDocumentsIndex = new TreeMap<>();

	/**
	 * The {@link AcceleoWorkspace} that contains this {@link AcceleoProject}.
	 */
	private AcceleoWorkspace workspace;

	/**
	 * The {@link String label} for this {@link AcceleoProject}.
	 */
	private String label;

	/**
	 * The {@link IQualifiedNameResolver} of this {@link AcceleoProject}.
	 */
	private IQualifiedNameResolver resolver;

	/**
	 * The constructor.
	 *
	 * @param label
	 *            the (non-{@code null}) {@link String label} for the project.
	 * @param resolver
	 *            the (non-{@code null}) {@link IQualifiedNameResolver} for the project.
	 */
	public AcceleoProject(String label, IQualifiedNameResolver resolver) {
		this.label = Objects.requireNonNull(label);
		this.resolver = Objects.requireNonNull(resolver);
	}

	/**
	 * Provides the {@link String label} of this {@link AcceleoProject}.
	 * 
	 * @return the (non-{@code null}) {@link String label} of this {@link AcceleoProject}.
	 */
	public String getLabel() {
		return this.label;
	}

	/**
	 * Modifes the {@link String label} of this {@link AcceleoProject}.
	 * 
	 * @param newLabel
	 *            the (non-{@code null}) new {@link String label} of the project.
	 */
	public void setLabel(String newLabel) {
		this.label = newLabel;
	}

	/**
	 * Provides the owning {@link AcceleoLanguageServer}.
	 * 
	 * @return the (maybe-{@code null}) owning {@link AcceleoLanguageServer}.
	 */
	public AcceleoLanguageServer getLanguageServer() {
		if (this.getWorkspace() != null) {
			return this.getWorkspace().getOwner();
		} else {
			return null;
		}
	}

	/**
	 * Affects this {@link AcceleoProject} to a container {@link AcceleoWorkspace}.
	 * 
	 * @param acceleoWorkspace
	 *            the (maybe-{@code null}) container {@link AcceleoWorkspace}.
	 */
	public void setWorkspace(AcceleoWorkspace acceleoWorkspace) {
		this.workspace = acceleoWorkspace;

		// The workspace has changed so the environment has implicitly changed.
		this.getTextDocuments().forEach(textDocument -> textDocument.resolverChanged());
	}

	/**
	 * Provides the {@link IQualifiedNameResolver} of this {@link AcceleoProject}.
	 * 
	 * @return the (non-{@code null}) {@link IQualifiedNameResolver} of this {@link AcceleoProject}.
	 */
	public IQualifiedNameResolver getResolver() {
		return this.resolver;
	}

	/**
	 * Provides the {@link List} of all {@link AcceleoTextDocument} in this {@link AcceleoProject}.
	 * 
	 * @return the (non-{@code null}) unmodifiable {@link List} of all {@link AcceleoTextDocument} in this
	 *         {@link AcceleoProject}.
	 */
	public List<AcceleoTextDocument> getTextDocuments() {
		return Collections.unmodifiableList(new ArrayList<>(this.acceleoTextDocumentsIndex.values()));
	}

	/**
	 * Sets the {@link IQualifiedNameResolver} of this {@link AcceleoProject}.
	 * 
	 * @param resolver
	 *            the new {@link IQualifiedNameResolver} of this {@link AcceleoProject}.
	 */
	public void setResolver(IQualifiedNameResolver resolver) {
		this.resolver = resolver;

		// The resolver has changed so we notify our documents.
		this.getTextDocuments().forEach(textDocument -> textDocument.resolverChanged());
	}

	/**
	 * Adds an {@link AcceleoTextDocument} to this {@link AcceleoProject}.
	 * 
	 * @param textDocumentToAdd
	 *            the (non-{@code null}) {@link AcceleoTextDocument} to add to this {@link AcceleoProject}.
	 */
	public void addTextDocument(AcceleoTextDocument textDocumentToAdd) {
		if (this.acceleoTextDocumentsIndex.containsKey(textDocumentToAdd.getUri())) {
			throw new IllegalArgumentException("There was an issue while trying to add text document "
					+ textDocumentToAdd.getUri() + " to project " + this.getLabel()
					+ ": there is already a known text document with this URI.");
		}
		textDocumentToAdd.setProject(this);
		this.acceleoTextDocumentsIndex.put(textDocumentToAdd.getUri(), textDocumentToAdd);
		// The impact of adding a document to this project is similar as if we had just saved it.
		this.workspace.documentSaved(textDocumentToAdd);
	}

	/**
	 * Removes an {@link AcceleoTextDocument} from this {@link AcceleoProject}.
	 * 
	 * @param textDocumentToRemove
	 *            the (non-{@code null}) {@link AcceleoTextDocument} to remove from this
	 *            {@link AcceleoProject}.
	 */
	public void removeTextDocument(AcceleoTextDocument textDocumentToRemove) {
		if (!this.acceleoTextDocumentsIndex.containsKey(textDocumentToRemove.getUri())) {
			throw new IllegalArgumentException("There was an issue while trying to remove text document "
					+ textDocumentToRemove.getUri() + " from project " + this.getLabel()
					+ ": there is no known text document with this URI.");
		}
		textDocumentToRemove.setProject(null);
		this.acceleoTextDocumentsIndex.remove(textDocumentToRemove.getUri());
		this.workspace.documentRemoved(textDocumentToRemove);
	}

	/**
	 * Provides the {@link AcceleoTextDocument} found at the given {@link URI}.
	 * 
	 * @param uri
	 *            the (non-{@code null}) {@link URI} of the {@link AcceleoTextDocument} we want to retrieve.
	 * @return the corresponding {@link AcceleoTextDocument}. {@code null} if there was none.
	 */
	public AcceleoTextDocument getTextDocument(URI uri) {
		return this.acceleoTextDocumentsIndex.get(uri);
	}

	/**
	 * Provides the owner {@link AcceleoWorkspace} of this {@link AcceleoProject}.
	 * 
	 * @return the (maybe-{@code null}) owner {@link AcceleoWorkspace} of this {@link AcceleoProject}.
	 */
	public AcceleoWorkspace getWorkspace() {
		return this.workspace;
	}

	/**
	 * We receive this notification when an {@link AcceleoTextDocument} in our
	 * {@link IQualifiedNameQueryEnvironment} has changed and saved. We want to re-validate any of our modules
	 * that depended on it.
	 * 
	 * @param savedTextDocument
	 *            the (non-{@code null}) {@link AcceleoTextDocument} that was saved.
	 */
	public void documentSaved(AcceleoTextDocument savedTextDocument) {
		final String qualifiedNameOfSavedModule = savedTextDocument.getModuleQualifiedName();
		final IQualifiedNameLookupEngine lookupEngine = savedTextDocument.getQueryEnvironment()
				.getLookupEngine();

		Set<AcceleoTextDocument> consumers = getTextDocumentsThatDependOn(qualifiedNameOfSavedModule);
		// First clear the environment for the document that was changed.
		lookupEngine.clearContext(qualifiedNameOfSavedModule);
		for (AcceleoProject project : getWorkspace().getProjects()) {
			project.getResolver().clear(Collections.singleton(qualifiedNameOfSavedModule));
		}
		for (AcceleoTextDocument consumer : consumers) {
			consumer.getQueryEnvironment().getLookupEngine().clearContext(qualifiedNameOfSavedModule);
		}

		// Then update the environment with the new version of the module from the saved document.
		for (AcceleoProject project : getWorkspace().getProjects()) {
			project.getResolver().register(qualifiedNameOfSavedModule, savedTextDocument.getAcceleoAstResult()
					.getModule());
		}

		// Re-validate all modules that depend on the changed module.
		for (AcceleoTextDocument consumer : consumers) {
			consumer.validateAndPublishResults();
		}

		// If the saved document belongs to us, we propagate the notification up to the workspace.
		if (this.getTextDocuments().contains(savedTextDocument) && this.getWorkspace() != null) {
			this.getWorkspace().documentSaved(savedTextDocument);
		}
	}

	/**
	 * We receive this notification when an {@link AcceleoTextDocument} in our
	 * {@link IQualifiedNameQueryEnvironment} has been removed. We want to unregister any services it has
	 * contributed and re-parse and re-validate any of our modules that depended on it.
	 * 
	 * @param removedTextDocument
	 *            the (non-{@code null}) {@link AcceleoTextDocument} that has been removed.
	 */
	public void documentRemoved(AcceleoTextDocument removedTextDocument) {
		final IQualifiedNameLookupEngine lookupEngine = removedTextDocument.getQueryEnvironment()
				.getLookupEngine();
		String removedModuleQualifiedName = lookupEngine.getResolver().getQualifiedName(removedTextDocument
				.getUri());

		Set<AcceleoTextDocument> consumers = getTextDocumentsThatDependOn(removedModuleQualifiedName);
		// Since the qualified name of a module depends on its environment, we want the qualified name
		// according to our environment.

		// First unregister it from the environment.
		for (AcceleoProject project : getWorkspace().getProjects()) {
			project.getResolver().clear(Collections.singleton(removedModuleQualifiedName));
		}
		for (AcceleoTextDocument consumer : consumers) {
			consumer.getQueryEnvironment().getLookupEngine().clearContext(removedModuleQualifiedName);
		}

		lookupEngine.clearContext(removedModuleQualifiedName);

		// Re-validate all modules that depend on the changed module.
		for (AcceleoTextDocument consumer : consumers) {
			consumer.resolverChanged();
		}

		// Unlike in documentSaved, we do not need to propagate the notification as it cannot come from the
		// document itself.
	}

	/**
	 * Gets the {@link Set} of {@link AcceleoTextDocument text document} of this project that depend on the
	 * given qualified name.
	 * 
	 * @param qualifiedName
	 *            the (non-{@code null}) qualified name.
	 * @return the {@link Set} of {@link AcceleoTextDocument text document} of this project that depend on the
	 *         given qualified name
	 */
	public Set<AcceleoTextDocument> getTextDocumentsThatDependOn(String qualifiedName) {
		Set<AcceleoTextDocument> res = new LinkedHashSet<>();

		final Set<String> dependOn = getResolver().getDependOn(qualifiedName);
		for (AcceleoTextDocument acceleoTextDocument : getTextDocuments()) {
			if (dependOn.contains(acceleoTextDocument.getModuleQualifiedName())) {
				res.add(acceleoTextDocument);
			}
		}

		return res;
	}
}
