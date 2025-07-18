/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.namespace.workspace;

import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;

/**
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IWorkspaceRegistry extends EPackage.Registry {

	/**
	 * Registers an {@link EPackage} {@link Class#isInterface() interface} from the workspace.
	 * 
	 * @param cls
	 *            the {@link EPackage} {@link Class#isInterface() interface}
	 * @param qualifiedName
	 *            the corresponding qualified name
	 */
	void register(Class<? extends EPackage> cls, String qualifiedName);

	/**
	 * Registers the given Ecore {@link URI}.
	 * 
	 * @param uri
	 *            the Eocre {@link URI}
	 * @param qualifiedName
	 *            the corresponding qualified name
	 */
	void register(URI uri, String qualifiedName);

	/**
	 * Sets the {@link Set} of {@link EPackage#getNsURI() nsURI} the given qualified name depends on.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @param nsURIs
	 *            the {@link Set} of {@link EPackage#getNsURI() nsURI} the qualified name depends on
	 */
	void setDependencies(String qualifiedName, Set<String> nsURIs);

	/**
	 * Gets the {@link Set} of qualified names that depend on the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name of the EPackage
	 * @return the {@link Set} of qualified names that depend on the given qualified name
	 */
	Set<String> getDependsOn(String qualifiedName);

	/**
	 * Tells if the given qualified name correspond to an {@link EPackage}.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @return <code>true</code> if the given qualified name correspond to an {@link EPackage}
	 */
	boolean isEPackage(String qualifiedName);

}
