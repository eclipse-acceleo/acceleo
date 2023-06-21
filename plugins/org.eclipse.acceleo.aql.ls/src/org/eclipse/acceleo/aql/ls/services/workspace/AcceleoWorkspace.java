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
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.acceleo.aql.ls.AcceleoLanguageServer;
import org.eclipse.acceleo.aql.ls.services.textdocument.AcceleoTextDocument;

/**
 * A representation in the {@link AcceleoLanguageServer} of the client's workspace as it pertains to Acceleo.
 * 
 * @author Florent Latombe
 */
public class AcceleoWorkspace {

	/**
	 * The name of this workspace.
	 */
	private final String name;

	/**
	 * The {@link List} of {@link AcceleoProject} of this workspace. Modifications to the list may have an
	 * impact on the validation state of other projects.
	 */
	private final List<AcceleoProject> projects = new ArrayList<>();

	/**
	 * The owner {@link AcceleoLanguageServer} of this workspace.
	 */
	private AcceleoLanguageServer owner;

	/**
	 * Creates a new {@link AcceleoWorkspace}.
	 * 
	 * @param name
	 *            the (non-{@code null}) name of the {@link AcceleoWorkspace}.
	 */
	public AcceleoWorkspace(String name) {
		this.name = Objects.requireNonNull(name);
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

	public AcceleoLanguageServer getOwner() {
		return owner;
	}

	/**
	 * Provides the name of this {@link AcceleoWorkspace}.
	 * 
	 * @return the (non-{@code null}) name of this {@link AcceleoWorkspace}.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Provides the {@link AcceleoProject projects} of this {@link AcceleoWorkspace}.
	 * 
	 * @return the (non-{@code null}) unmodifiable {@link List} of {@link AcceleoProject}. Use
	 *         {@link #addProject(AcceleoProject)} and {@link #removeProject(AcceleoProject)} to modify the
	 *         list.
	 */
	public List<AcceleoProject> getProjects() {
		return Collections.unmodifiableList(this.projects);
	}

	/**
	 * Adds a {@link AcceleoProject} to this {@link AcceleoWorkspace} and updates the other projects
	 * accordingly.
	 * 
	 * @param projectToAdd
	 *            the (non-{@code null}) {@link AcceleoProject} to add.
	 */
	public void addProject(AcceleoProject projectToAdd) {
		Objects.requireNonNull(projectToAdd);

		if (this.projects.contains(projectToAdd)) {
			throw new IllegalArgumentException("Workspace \"" + this.name + "\" already contains project "
					+ projectToAdd.toString() + " so we cannot add it to it.");
		}

		projectToAdd.setWorkspace(this);
		this.projects.add(projectToAdd);
		// TODO: we probably want to validate all projects so they can take into account the newly-added
		// project.
	}

	/**
	 * Removes an {@link AcceleoProject} from this {@link AcceleoWorkspace} and updates the other projects
	 * accordingly.
	 * 
	 * @param projectToRemove
	 *            the (non-{@code null}) {@link AcceleoProject} to remove.
	 */
	public void removeProject(AcceleoProject projectToRemove) {
		Objects.requireNonNull(projectToRemove);

		if (!this.projects.contains(projectToRemove)) {
			throw new IllegalArgumentException("Workspace \"" + this.name + "\" does not contain project "
					+ projectToRemove.toString() + " so it cannot be removed from it.");
		}

		this.projects.remove(projectToRemove);
		// TODO: we probably want to find other projects of the workspace that depended on the removed project
		// and re-validate them.
	}

	/**
	 * Collects all the {@link AcceleoTextDocument} contained in all the {@link AcceleoProject} of this
	 * {@link AcceleoWorkspace}.
	 * 
	 * @return the {@link List} of all {@link AcceleoTextDocument AcceleoTextDocuments} in this
	 *         {@link AcceleoWorkspace}.
	 */
	public List<AcceleoTextDocument> getAllTextDocuments() {
		return this.projects.stream().map(AcceleoProject::getTextDocuments).flatMap(List::stream).collect(
				Collectors.toList());
	}

	/**
	 * Provides the {@link AcceleoTextDocument} found at the given {@link URI}.
	 * 
	 * @param uri
	 *            the (non-{@code null}) {@link URI} of the {@link AcceleoTextDocument} we are looking for.
	 * @return the {@link AcceleoTextDocument} found at {@code uri}. {@code null} if there was no such
	 *         document.
	 */
	public AcceleoTextDocument getTextDocument(URI uri) {
		for (AcceleoProject acceleoProject : this.projects) {
			AcceleoTextDocument textDocument = acceleoProject.getTextDocument(uri);
			if (textDocument != null) {
				return textDocument;
			}
		}
		return null;
	}

	/**
	 * This method is called by an {@link AcceleoProject} to notify us that one of its
	 * {@link AcceleoTextDocument} has been saved. We notify the other projects of the workspace so they can
	 * update their environments accordingly.
	 * 
	 * @param savedAcceleoTextDocument
	 *            the (non-{@code null}) {@link AcceleoTextDocument} which has been saved..
	 */
	public void documentSaved(AcceleoTextDocument savedAcceleoTextDocument) {
		this.getProjects().stream().filter(project -> !project.getTextDocuments().contains(
				savedAcceleoTextDocument)).forEach(project -> project.documentSaved(
						savedAcceleoTextDocument));
	}

	/**
	 * This method is called by an {@link AcceleoProject} to notify us that one of its
	 * {@link AcceleoTextDocument} has been removed. We notify all projects so that they can update their
	 * environments accordingly.
	 * 
	 * @param removedAcceleoTextDocument
	 *            the (non-{@code null}) {@link AcceleoTextDocument} which has been removed.
	 */
	public void documentRemoved(AcceleoTextDocument removedAcceleoTextDocument) {
		this.getProjects().stream().forEach(acceleoProject -> acceleoProject.documentRemoved(
				removedAcceleoTextDocument));
	}

}
