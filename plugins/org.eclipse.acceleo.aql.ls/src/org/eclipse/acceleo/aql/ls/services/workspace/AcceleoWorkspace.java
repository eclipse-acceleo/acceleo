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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.ls.AcceleoLanguageServer;
import org.eclipse.acceleo.aql.ls.IAcceleoLanguageServerContext;
import org.eclipse.acceleo.aql.ls.services.textdocument.AcceleoTextDocument;
import org.eclipse.acceleo.query.runtime.impl.namespace.workspace.QueryWorkspace;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameLookupEngine;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryWorkspaceQualifiedNameResolver;

/**
 * A representation in the {@link AcceleoLanguageServer} of the client's workspace as it pertains to Acceleo.
 * 
 * @author Florent Latombe
 */
public class AcceleoWorkspace extends QueryWorkspace<AcceleoProject> {

	/**
	 * The owner {@link AcceleoLanguageServer} of this workspace.
	 */
	private AcceleoLanguageServer owner;

	/**
	 * The mapping from URI to the corresponding {@link AcceleoTextDocument}.
	 */
	private final Map<URI, AcceleoTextDocument> uriToDocuments = new LinkedHashMap<>();

	/**
	 * The {@link IAcceleoLanguageServerContext}.
	 */
	private IAcceleoLanguageServerContext context;

	/**
	 * Creates a new {@link AcceleoWorkspace}.
	 * 
	 * @param name
	 *            the (non-{@code null}) name of the {@link AcceleoWorkspace}.
	 */
	public AcceleoWorkspace(String name, IAcceleoLanguageServerContext context) {
		super(name);
		this.context = Objects.requireNonNull(context);
	}

	/**
	 * Sets the given {@link AcceleoLanguageServer} as the owner of this {@link AcceleoWorkspace}.
	 * 
	 * @param newOwner
	 *            the (maybe-{@code null}) owner {@link AcceleoLanguageServer}.
	 */
	public void setOwner(AcceleoLanguageServer newOwner) {
		this.owner = newOwner;
	}

	/**
	 * Gets the {@link AcceleoLanguageServer} owning this workspace.
	 * 
	 * @return
	 */
	public AcceleoLanguageServer getOwner() {
		return owner;
	}

	@Override
	public synchronized void addProject(AcceleoProject project) {
		super.addProject(project);
	}

	@Override
	public synchronized String addResource(AcceleoProject project, URI resource) {
		final IQueryWorkspaceQualifiedNameResolver resolver = getResolver(project);
		final String qualifiedName = resolver.getQualifiedName(resource);
		if (qualifiedName != null) {
			// clean previous resolved values to resolve the new one
			resolver.clear(Collections.singleton(qualifiedName));
			final Object resolved = resolver.resolve(qualifiedName);
			if (resolved instanceof Module) {
				final String textDocumentContents = context.getResourceContents(resource);
				final AcceleoTextDocument acceleoTextDocument = new AcceleoTextDocument(project, resource,
						qualifiedName, textDocumentContents, (Module)resolved);
				final AcceleoTextDocument oldAcceleoTextDocument = uriToDocuments.put(resource,
						acceleoTextDocument);
				if (oldAcceleoTextDocument != null && oldAcceleoTextDocument.isOpened()) {
					final URI sourceURI = resolver.getSourceURI(qualifiedName);
					getOwner().getTextDocumentService().close(sourceURI);
					getOwner().getTextDocumentService().open(sourceURI, acceleoTextDocument);
					acceleoTextDocument.open(textDocumentContents);
				}

				final URI sourceURI = resolver.getSourceURI(qualifiedName);
				if (sourceURI != null) {
					uriToDocuments.put(sourceURI, acceleoTextDocument);
				}
				project.addDocument(acceleoTextDocument);
			}
		}
		return super.addResource(project, resource);
	}

	@Override
	public String removeResource(AcceleoProject project, URI resource) {
		final AcceleoTextDocument removedDocument = uriToDocuments.remove(resource);
		if (removedDocument != null) {
			project.removeDocument(removedDocument);
			uriToDocuments.remove(getResolver(project).getSourceURI(removedDocument
					.getModuleQualifiedName()));
		}
		return super.removeResource(project, resource);
	}

	@Override
	public String moveResource(AcceleoProject sourceProject, URI sourceResource, AcceleoProject targetProject,
			URI targetResource) {
		final AcceleoTextDocument removedDocument = uriToDocuments.remove(sourceResource);
		if (removedDocument != null) {
			sourceProject.removeDocument(removedDocument);
			uriToDocuments.remove(getResolver(sourceProject).getSourceURI(removedDocument
					.getModuleQualifiedName()));
		}

		final IQueryWorkspaceQualifiedNameResolver resolver = getResolver(targetProject);
		final String qualifiedName = resolver.getQualifiedName(targetResource);
		if (qualifiedName != null) {
			final Object resolved = resolver.resolve(qualifiedName);
			if (resolved instanceof Module) {
				final String textDocumentContents = context.getResourceContents(targetResource);
				final AcceleoTextDocument acceleoTextDocument = new AcceleoTextDocument(targetProject,
						targetResource, qualifiedName, textDocumentContents, (Module)resolved);
				uriToDocuments.put(targetResource, acceleoTextDocument);
				final URI sourceURI = resolver.getSourceURI(qualifiedName);
				if (sourceURI != null) {
					uriToDocuments.put(sourceURI, acceleoTextDocument);
				}
				targetProject.addDocument(acceleoTextDocument);
			}
		}

		return super.moveResource(sourceProject, sourceResource, targetProject, targetResource);
	}

	/**
	 * Gets the {@link AcceleoTextDocument} for the given resource {@link URI}.
	 * 
	 * @param resource
	 *            the resource {@link URI}
	 * @return the {@link AcceleoTextDocument} for the given resource {@link URI} if any, <code>null</code>
	 *         otherwise
	 */
	public AcceleoTextDocument getDocument(URI resource) {
		return uriToDocuments.get(resource);
	}

	@Override
	protected void updateResourceContents(AcceleoProject project, IQualifiedNameResolver resolver,
			URI resource) {
		final AcceleoTextDocument acceleoTextDocument = uriToDocuments.get(resource);
		if (acceleoTextDocument != null) {
			acceleoTextDocument.setContents(context.getResourceContents(resource));
		}
	}

	@Override
	protected void validate(AcceleoProject project, IQualifiedNameResolver resolver, String qualifiedName) {
		final AcceleoTextDocument acceleoTextDocument = project.getDocument(qualifiedName);
		if (acceleoTextDocument != null) {
			acceleoTextDocument.validateAndPublishResults();
		}
	}

	@Override
	protected IQualifiedNameLookupEngine getLookupEngine(AcceleoProject project,
			IQualifiedNameResolver resolver, String qualifiedName) {
		final IQualifiedNameLookupEngine res;

		final AcceleoTextDocument acceleoTextDocument = project.getDocument(qualifiedName);
		if (acceleoTextDocument != null && acceleoTextDocument.getQueryEnvironment() != null) {
			res = acceleoTextDocument.getQueryEnvironment().getLookupEngine();
		} else {
			res = null;
		}

		return res;
	}

	@Override
	protected IQueryWorkspaceQualifiedNameResolver createResolver(AcceleoProject project) {
		return context.createResolver(project);
	}

	/**
	 * Gets the {@link Set} of all {@link AcceleoTextDocument} in this workspace.
	 * 
	 * @return the {@link Set} of all {@link AcceleoTextDocument} in this workspace
	 */
	public Set<AcceleoTextDocument> getAllTextDocuments() {
		return new LinkedHashSet<>(uriToDocuments.values());
	}

	/**
	 * Gets the {@link AcceleoProject} containing the given resource {@link URI}.
	 * 
	 * @param resource
	 *            the resource {@link URI}
	 * @return the {@link AcceleoProject} containing the given resource {@link URI} if any, <code>null</code>
	 *         otherwise
	 */
	public AcceleoProject getProject(URI resource) {
		return context.getProject(this, resource);
	}

}
