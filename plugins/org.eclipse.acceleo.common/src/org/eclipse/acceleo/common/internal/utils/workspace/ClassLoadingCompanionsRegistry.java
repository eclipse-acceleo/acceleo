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

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.acceleo.common.utils.CompactLinkedHashSet;

/**
 * Class maintaining the list of registered {@link ClassLoadingCompanionProvider}.
 * 
 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
 * @since 3.6
 */
public final class ClassLoadingCompanionsRegistry {

	/** Singleton instance of the registry. */
	public static final ClassLoadingCompanionsRegistry INSTANCE = new ClassLoadingCompanionsRegistry();

	/** This will contain the services registered for Acceleo evaluations. */
	private final Set<ClassLoadingCompanionProvider> registeredCompanions = new CompactLinkedHashSet<ClassLoadingCompanionProvider>();

	/**
	 * This class is a singleton. Access instance through {@link #INSTANCE}.
	 */
	private ClassLoadingCompanionsRegistry() {
		// Hide constructor
	}

	/**
	 * Register a new {@link ClassLoadingCompanionProvider}.
	 * 
	 * @param instance
	 *            the provider to register.
	 */

	public void register(ClassLoadingCompanionProvider instance) {
		this.registeredCompanions.add(instance);
	}

	/**
	 * Unregister a {@link ClassLoadingCompanionProvider} based on its implementation class name. No-op if it
	 * was not registered.
	 * 
	 * @param className
	 *            the implementation class name to unregister.
	 */
	public void unregister(String className) {
		for (ClassLoadingCompanionProvider connector : this.registeredCompanions) {
			if (className.equals(connector.getClass().getName())) {
				this.registeredCompanions.remove(connector);
			}
		}
	}

	/**
	 * return the {@link ClassLoadingCompanion} instances which are registered.
	 * 
	 * @return the {@link ClassLoadingCompanion} instances which are registered.
	 */
	public Collection<ClassLoadingCompanion> getAllRegisteredCompanions() {
		Set<ClassLoadingCompanion> companions = new LinkedHashSet<ClassLoadingCompanion>();
		for (ClassLoadingCompanionProvider provider : registeredCompanions) {
			ClassLoadingCompanion provided = provider.getCompanion();
			if (provided != null) {
				companions.add(provided);
			}
		}
		return companions;
	}

	/**
	 * Clean this registry by releasing all hold resources.
	 */
	public void clearRegistry() {
		this.registeredCompanions.clear();
	}
}
