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
package org.eclipse.acceleo.engine.service;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.CompactLinkedHashSet;
import org.eclipse.acceleo.engine.internal.utils.AcceleoDynamicTemplatesEclipseUtil;
import org.eclipse.acceleo.engine.internal.utils.DynamicModuleContribution;

/**
 * This will allow Acceleo to dynamically resolve template overrides within the modules registered by this
 * registry.
 * <p>
 * Take note that all modules will be loaded in the registry's resource set if loaded from the registry
 * through {@link #addModulesFrom(File)}.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 0.8
 */
public final class AcceleoDynamicTemplatesRegistry {
	/** Singleton instance of the registry. */
	public static final AcceleoDynamicTemplatesRegistry INSTANCE = new AcceleoDynamicTemplatesRegistry();

	/** This will contain references to the modules added to this registry. */
	private final Set<URL> registeredModules = new CompactLinkedHashSet<URL>();

	/**
	 * This class is a singleton. Access the instance through {@link #INSTANCE}.
	 */
	private AcceleoDynamicTemplatesRegistry() {
		// hides default constructor
	}

	/**
	 * Adds a module to the registry.
	 * 
	 * @param module
	 *            Module that is to be registered for dynamic template resolution.
	 * @return <code>true</code> if the set didn't already contain <code>module</code>.
	 * @since 0.8
	 */
	public boolean addModule(URL module) {
		return registeredModules.add(module);
	}

	/**
	 * Adds a set of modules to the registry.
	 * 
	 * @param modules
	 *            Modules that are to be registered for dynamic template resolution.
	 * @return <code>true</code> if the set didn't already contain one of the modules contained by
	 *         <code>modules</code>.
	 */
	public boolean addModules(Collection<URL> modules) {
		return registeredModules.addAll(modules);
	}

	/**
	 * This will register all modules that can be retrieved from <code>file</code>.
	 * <p>
	 * That is, if <code>file</code> is a directory, this will iterate over all of its direct and indirect
	 * children (except for &quot;CVS&quot; and &quot;.svn&quot; named sub-directories), then load and
	 * register all child representing a module. Otherwise, if <code>file</code> itself represents a module,
	 * it will be loaded and registered.
	 * </p>
	 * <p>
	 * If <code>file</code> is neither a module nor a directory, this will have no effect.
	 * </p>
	 * 
	 * @param file
	 *            The module to register or directory to iterate over for modules.
	 */
	public void addModulesFrom(File file) {
		if (file.exists() && file.canRead()) {
			if (file.isDirectory()) {
				File[] children = file.listFiles(new FileFilter() {
					public boolean accept(File pathname) {
						if (!pathname.getAbsolutePath().matches("^.*(CVS|\\\\.svn)$")) { //$NON-NLS-1$
							return true;
						}
						return false;
					}
				});
				for (File child : children) {
					addModulesFrom(child);
				}
			} else if (IAcceleoConstants.EMTL_FILE_EXTENSION.equals(file.getPath().substring(file.getPath()
					.lastIndexOf('.') + 1))) {
				try {
					addModule(file.toURL());
				} catch (MalformedURLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/**
	 * This can be used to clear the registry's resource set from manually loaded Modules. Take note that
	 * modules loaded from extension points will not be cleared by this.
	 */
	public void clearRegistryResourceSet() {
		registeredModules.clear();
	}

	/**
	 * Returns all registered modules. The returned set is a copy of this instance's.
	 * 
	 * @return A copy of the registered modules set.
	 */
	public Set<URL> getRegisteredModules() {
		final Set<URL> compound = new CompactLinkedHashSet<URL>();
		Set<DynamicModuleContribution> modules = AcceleoDynamicTemplatesEclipseUtil.getRegisteredModules();
		for (DynamicModuleContribution dynamicModuleContribution : modules) {
			compound.addAll(dynamicModuleContribution.getFiles());
		}
		compound.addAll(registeredModules);
		return compound;
	}

	/**
	 * Returns all registered modules that are contributing to the generation of the generator with the given
	 * ID. The returned set is a copy of this instance's.
	 * 
	 * @param generatorID
	 *            The generator ID.
	 * @return A copy of the registered modules set.
	 * @since 3.1
	 */
	public Set<URL> getRegisteredModules(String generatorID) {
		final Set<URL> compound = new CompactLinkedHashSet<URL>();
		Set<DynamicModuleContribution> modules = AcceleoDynamicTemplatesEclipseUtil.getRegisteredModules(
				generatorID);
		for (DynamicModuleContribution dynamicModuleContribution : modules) {
			List<String> generatorIDs = dynamicModuleContribution.getGeneratorIDs();
			if (generatorIDs.size() > 0) {
				for (String genID : generatorIDs) {
					if (genID.equals(generatorID)) {
						compound.addAll(dynamicModuleContribution.getFiles());
					}
				}
			} else {
				compound.addAll(dynamicModuleContribution.getFiles());
			}
		}
		compound.addAll(registeredModules);
		return compound;
	}

	/**
	 * Removes a module from the registry.
	 * 
	 * @param module
	 *            Module that is to be removed from dynamic template resolution.
	 * @return <code>true</code> if the set contained <code>module</code>.
	 * @since 0.8
	 */
	public boolean removeModule(File module) {
		return registeredModules.remove(module);
	}

	/**
	 * Removes a set of modules from the registry.
	 * 
	 * @param modules
	 *            Modules that are to be removed from dynamic template resolution.
	 * @return <code>true</code> if the set has been changed.
	 */
	public boolean removeModules(Collection<File> modules) {
		return registeredModules.removeAll(modules);
	}
}
