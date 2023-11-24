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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.acceleo.aql.ls.AcceleoLanguageServer;
import org.eclipse.acceleo.aql.ls.services.textdocument.AcceleoTextDocument;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryWorkspaceQualifiedNameResolver;

/**
 * A representation, in the {@link AcceleoLanguageServer} of a container of {@link AcceleoTextDocument
 * AcceleoTextDocuments} that share a same {@link IQualifiedNameQueryEnvironment}. It may or may not
 * correspond to a physical element in the client.
 * 
 * @author Florent Latombe
 */
public class AcceleoProject {

	/**
	 * The project name.
	 */
	private String name;

	/**
	 * The {@link AcceleoWorkspace} containing this project.
	 */
	private final AcceleoWorkspace workspace;

	/**
	 * The mapping form a qualified name to the corresponding {@link AcceleoTextDocument}.
	 */
	private final Map<String, AcceleoTextDocument> qualifiedNameToDocuments = new HashMap<>();

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            the project name
	 * @param workspace
	 *            the {@link AcceleoWorkspace}
	 */
	public AcceleoProject(String name, AcceleoWorkspace workspace) {
		this.name = name;
		this.workspace = workspace;
	}

	/**
	 * Gets the project name.
	 * 
	 * @return the project name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Adds the given {@link AcceleoTextDocument} to this project.
	 * 
	 * @param acceleoTextDocument
	 *            the {@link AcceleoTextDocument} to add
	 */
	public void addDocument(AcceleoTextDocument acceleoTextDocument) {
		qualifiedNameToDocuments.put(acceleoTextDocument.getModuleQualifiedName(), acceleoTextDocument);
	}

	/**
	 * Removes the given {@link AcceleoTextDocument}.
	 * 
	 * @param acceleoTextDocument
	 *            the {@link AcceleoTextDocument}
	 */
	public void removeDocument(AcceleoTextDocument acceleoTextDocument) {
		qualifiedNameToDocuments.remove(acceleoTextDocument.getModuleQualifiedName());
	}

	/**
	 * Gets the {@link AcceleoTextDocument} for the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @return the {@link AcceleoTextDocument} for the given qualified name if any, <code>null</code>
	 *         otherwise
	 */
	public AcceleoTextDocument getDocument(String qualifiedName) {
		return qualifiedNameToDocuments.get(qualifiedName);
	}

	/**
	 * Gets the {@link AcceleoWorkspace} containing this project.
	 * 
	 * @return the {@link AcceleoWorkspace} containing this project
	 */
	public AcceleoWorkspace getWorkspace() {
		return workspace;
	}

	/**
	 * Gets the {@link IQueryWorkspaceQualifiedNameResolver} for this project.
	 * 
	 * @return the {@link IQueryWorkspaceQualifiedNameResolver} for this project
	 */
	public IQueryWorkspaceQualifiedNameResolver getResolver() {
		return workspace.getResolver(this);
	}

	/**
	 * Gets the {@link AcceleoLanguageServer}.
	 * 
	 * @return the {@link AcceleoLanguageServer} if any, <code>null</code> otherwise
	 */
	public AcceleoLanguageServer getLanguageServer() {
		return workspace.getOwner();
	}

	@Override
	public String toString() {
		return "Acceleo Project " + getName();
	}

}
