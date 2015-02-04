/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.internal.utils.workspace;

import java.util.ResourceBundle;

import org.eclipse.core.resources.IProject;

/**
 * Interface exposing all the methods used by the Acceleo runtime to support specific class loading
 * mechanisms.
 * 
 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
 * @since 3.6
 */
public interface ClassLoadingCompanion {

	/**
	 * This will refresh the list of contributions to the registry by either installing the given plugins in
	 * the current running Eclipse or refresh their packages. Keep this synchronized as it will be called by
	 * each of the utilities and these calls can come from multiple threads.
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	void refreshContributions();

	/**
	 * Retrieves the singleton instance of the given service class after refreshing it if needed.
	 * 
	 * @param serviceClass
	 *            The service class we need an instance of.
	 * @return The singleton instance of the given service class if any.
	 */
	Object getServiceInstance(Class<?> serviceClass);

	/**
	 * This will install or refresh the given workspace contribution if needed, then search through it for a
	 * class corresponding to <code>qualifiedName</code>.
	 * 
	 * @param project
	 *            The project that is to be dynamically installed when Acceleo searches for workspace
	 *            contributions.
	 * @param qualifiedName
	 *            The qualified name of the class we seek to load.
	 * @return An instance of the class <code>qualifiedName</code> if it could be found in the workspace
	 *         bundles, <code>null</code> otherwise.
	 */
	Class<?> getClass(IProject project, String qualifiedName);

	/**
	 * This will search through the workspace for a plugin defined with the given symbolic name and return it
	 * if any.
	 * 
	 * @param bundleName
	 *            Symbolic name of the plugin we're searching a workspace project for.
	 * @return The workspace project of the given symbolic name, <code>null</code> if none could be found.
	 */
	IProject getProject(String bundleName);

	/**
	 * Adds model listeners to all workspace-defined bundles. This will be called at plugin starting and is
	 * not intended to be called by clients.
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	void initialize();

	/**
	 * This can be used to uninstall all manually loaded bundles from the registry and remove all listeners.
	 * It will be called on plugin stopping and is not intended to be called by clients.
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	void dispose();

	/**
	 * This can be used to check whether the given class is located in a dynamically installed bundle.
	 * 
	 * @param clazz
	 *            The class of which we need to determine the originating bundle.
	 * @return <code>true</code> if the given class has been loaded from a dynamic bundle, <code>false</code>
	 *         otherwise.
	 */
	boolean isInDynamicBundle(Class<?> clazz);

	/**
	 * This will try and load a ResourceBundle for the given qualified name.
	 * 
	 * @param qualifiedName
	 *            Name if the resource bundle we need to load.
	 * @return The loaded resource bundle.
	 */
	ResourceBundle getResourceBundle(String qualifiedName);

}
