/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.internal.utils.workspace;

import org.eclipse.acceleo.common.AcceleoCommonMessages;
import org.eclipse.acceleo.common.AcceleoCommonPlugin;

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

	/** Class Object of this instance. */
	private Class<?> clazz;

	/** Actual instance of the loaded class. */
	private Object instance;

	/**
	 * This will be set to <code>true</code> if the instance needs to be re-created. This will happen if the
	 * containing bundle has been reloaded or if the class has been rebuilt since last accessed.
	 */
	private boolean stale;

	/**
	 * Instantiates a {@link WorkspaceClassInstance} given the actual wrapped class and the symbolic name of
	 * the bundle from which the class had been loaded.
	 * 
	 * @param clazz
	 *            Actual instance of the loaded Class.
	 * @param bundle
	 *            Symbolic name of the bundle from which this class has been loaded.
	 */
	public WorkspaceClassInstance(Class<?> clazz, String bundle) {
		this.clazz = clazz;
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
		return clazz.getName();
	}

	/**
	 * Returns the wrapped class instance.
	 * 
	 * @return The wrapped class instance.
	 */
	public Class<?> getClassInstance() {
		return clazz;
	}

	/**
	 * Returns the currently wrapped instance.
	 * 
	 * @return The currently wrapped instance.
	 */
	public Object getInstance() {
		if (instance == null) {
			try {
				instance = clazz.newInstance();
			} catch (InstantiationException e) {
				AcceleoCommonPlugin.log(AcceleoCommonMessages.getString("BundleClassInstantiationFailure", //$NON-NLS-1$
						clazz.getName(), bundle), e, false);
			} catch (IllegalAccessException e) {
				AcceleoCommonPlugin.log(AcceleoCommonMessages.getString("BundleClassConstructorFailure", //$NON-NLS-1$
						clazz.getName(), bundle), e, false);
			}
		}
		return instance;
	}

	/**
	 * Sets the Class instance after a refresh.
	 * 
	 * @param newClass
	 *            The new Class instance.
	 */
	public void setClass(Class<?> newClass) {
		clazz = newClass;
		instance = null;
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
