/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.common.utils.CompactLinkedHashSet;
import org.eclipse.acceleo.engine.internal.utils.AcceleoDynamicModulesEclipseUtil;
import org.eclipse.acceleo.engine.internal.utils.DynamicModuleContribution;
import org.eclipse.emf.common.EMFPlugin;

/**
 * This will allow Acceleo to dynamically resolve template overrides within the modules registered by this
 * registry.
 * <p>
 * Take note that all modules will be loaded in the registry's resource set if loaded from the registry
 * through {@link #addModulesFrom(File)}.
 * </p>
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.2
 */
public final class AcceleoDynamicModulesRegistry {
	/** Singleton instance of the registry. */
	public static final AcceleoDynamicModulesRegistry INSTANCE = new AcceleoDynamicModulesRegistry();

	/**
	 * This class is a singleton. Access the instance through {@link #INSTANCE}.
	 */
	private AcceleoDynamicModulesRegistry() {
		// hides default constructor
	}

	/**
	 * Returns all registered modules that are contributing to the generation of the generator with the given
	 * ID. The returned set is a copy of this instance's.
	 * 
	 * @param generatorID
	 *            The generator ID.
	 * @return A copy of the registered modules set.
	 */
	public Set<File> getRegisteredModules(String generatorID) {
		final Set<File> compound = new CompactLinkedHashSet<File>();
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			CompactLinkedHashSet<DynamicModuleContribution> modules = AcceleoDynamicModulesEclipseUtil
					.getRegisteredModules();
			for (DynamicModuleContribution dynamicModuleContribution : modules) {
				List<String> generatorIDs = dynamicModuleContribution.getGeneratorIDs();
				for (String genID : generatorIDs) {
					if (genID.equals(generatorID)) {
						compound.addAll(dynamicModuleContribution.getFiles());
					}
				}
			}
		}
		return compound;
	}
}
