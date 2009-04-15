/*******************************************************************************
 * Copyright (c) 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.internal.utils.workspace;

/**
 * This will allow us to hold information about a workspace-loaded class' instance. Instances of this class
 * will mostly be used to determine if the loaded instance is stale (this will happen if its containing bundle
 * has been uninstalled or the class has been rebuilt).
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
final class WorkspaceClassInstance {
	/** Symbolic name of the bundle from which this class has been loaded. */
	private final String bundle;

	/** Actual instance of the loaded class. */
	private Object instance;

	/**
	 * This will be set to <code>true</code> if the instance needs to be re-created. This will happen if the
	 * containing bundle has been reloaded or if the class has been rebuilt since last accessed.
	 */
	private boolean stale;

	/**
	 * Instantiates a {@link WorkspaceClassInstance} given the actual wrapped instance and the symbolic name
	 * of the bundle from which the class had been loaded.
	 * 
	 * @param instance
	 *            Actual instance of the loaded class.
	 * @param bundle
	 *            Symbolic name of the bundle from which this class has been loaded.
	 */
	public WorkspaceClassInstance(Object instance, String bundle) {
		this.instance = instance;
		this.bundle = bundle;
	}

	/**
	 * Returns <code>true</code> if this instance should be reloaded, <code>false</code> otherwise.
	 * 
	 * @return <code>true</code> if this instance should be reloaded, <code>false</code> otherwise.
	 */
	public boolean isStale() {
		return stale;
	}

	/**
	 * Returns the symbolic name of the bundle from which this class has been loaded.
	 * 
	 * @return The symbolic name of the bundle from which this class has been loaded.
	 */
	public String getBundle() {
		return bundle;
	}

	/**
	 * Returns the qualified name of the currently wrapped instance.
	 * 
	 * @return The qualified name of the wrapped instance.
	 */
	public String getQualifiedName() {
		return instance.getClass().getName();
	}

	/**
	 * Returns the currently wrapped instance.
	 * 
	 * @return The currently wrapped instance.
	 */
	public Object getInstance() {
		return instance;
	}

	/**
	 * Swaps the wrapped instance to the given value.
	 * 
	 * @param instance
	 *            New instance of the wrapped class.
	 */
	public void setInstance(Object instance) {
		this.instance = instance;
	}

	/**
	 * Sets this instance as stale. This will indicate clients that it needs to be reloaded.
	 * 
	 * @param stale
	 *            <code>true</code> if this instance should be set as needing a reload, <code>false</code>
	 *            otherwise.
	 */
	public void setStale(boolean stale) {
		this.stale = stale;
	}
}
