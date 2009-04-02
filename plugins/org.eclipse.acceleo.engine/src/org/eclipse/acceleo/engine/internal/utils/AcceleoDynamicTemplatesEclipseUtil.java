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
package org.eclipse.acceleo.engine.internal.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.osgi.framework.Bundle;

/* FIXME modules shouldn't be loaded at extension point parsing time : keep references to the files and 
 * have the engine dynamically load them in the evaluated modules' resourceSet.
 */
/**
 * Eclipse-specific utilities for Acceleo dynamic templates. It will be initialized with all dynamic templates
 * that could be parsed from the extension point if Eclipse is running and won't be used when outside of
 * Eclipse.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoDynamicTemplatesEclipseUtil {
	/** Keeps track of the bundles extending this extension point. */
	protected static final Map<Bundle, List<String>> EXTENDING_BUNDLES = new HashMap<Bundle, List<String>>();

	/** This will contain the modules contained by this registry. */
	private static final Set<Module> REGISTERED_MODULES = new LinkedHashSet<Module>();

	/** ResourceSet that will be used to load modules. */
	private static final ResourceSet RESOURCE_SET = new ResourceSetImpl();

	/**
	 * Utility classes don't need a default constructor.
	 */
	private AcceleoDynamicTemplatesEclipseUtil() {
		// hides constructor
	}

	/**
	 * Adds a bundle in the extending bundles map.
	 * 
	 * @param bundle
	 *            The extending bundle.
	 * @param pathes
	 *            Pathes where dynamic modules are located.
	 */
	public static void addExtendingBundle(Bundle bundle, List<String> pathes) {
		List<String> currentPathes = EXTENDING_BUNDLES.get(bundle);
		if (currentPathes == null) {
			currentPathes = new ArrayList<String>();
		}
		currentPathes.addAll(pathes);
		EXTENDING_BUNDLES.put(bundle, currentPathes);
	}

	/**
	 * Returns all registered modules. The returned set is a copy of this instance's.
	 * 
	 * @return A copy of the registered modules set.
	 */
	public static Set<Module> getRegisteredModules() {
		refreshModules();
		return new LinkedHashSet<Module>(REGISTERED_MODULES);
	}

	/**
	 * Removes a bundle from the extending bundles map.
	 * 
	 * @param bundle
	 *            The bundle that is to be removed.
	 */
	public static void removeExtendingBundle(Bundle bundle) {
		EXTENDING_BUNDLES.remove(bundle);
	}

	/**
	 * This will register the module represented by <code>file</code>.
	 * 
	 * @param file
	 *            The module to register.
	 */
	private static void addModule(File file) {
		if (file.exists() && file.canRead()) {
			try {
				Resource res = ModelUtils.load(file, RESOURCE_SET).eResource();
				for (EObject child : res.getContents()) {
					if (child instanceof Module) {
						REGISTERED_MODULES.add((Module)child);
					}
				}
			} catch (IOException e) {
				AcceleoEnginePlugin.log(e, false);
			}
		}
	}

	/**
	 * This will be called prior to all invocations of getRegisteredModules so as to be aware of new additions
	 * or removals of dynamic templates.
	 */
	@SuppressWarnings("unchecked")
	private static void refreshModules() {
		// FIXME instead of unloading/reloading everything, load only the new and remove the no longer needed
		for (Module module : REGISTERED_MODULES) {
			final Resource resource = module.eResource();
			if (resource != null) {
				resource.unload();
				RESOURCE_SET.getResources().remove(resource);
			}
		}
		for (java.util.Map.Entry<Bundle, List<String>> entry : new LinkedHashSet<java.util.Map.Entry<Bundle, List<String>>>(
				EXTENDING_BUNDLES.entrySet())) {
			for (String path : entry.getValue()) {
				String actualPath = path;
				if (actualPath.charAt(0) != '/') {
					actualPath = '/' + actualPath;
				}
				Enumeration<URL> emtlFiles = entry.getKey().findEntries(actualPath, "*." //$NON-NLS-1$
						+ IAcceleoConstants.EMTL_FILE_EXTENSION, true);
				if (emtlFiles == null || !emtlFiles.hasMoreElements()) {
					emtlFiles = entry.getKey().findEntries(actualPath.replace("/src", "/bin"), "*." //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							+ IAcceleoConstants.EMTL_FILE_EXTENSION, true);
				}
				try {
					while (emtlFiles.hasMoreElements()) {
						final URL next = emtlFiles.nextElement();
						final File moduleFile = new File(FileLocator.toFileURL(next).getFile());
						addModule(moduleFile);
					}
				} catch (IOException e) {
					AcceleoEnginePlugin.log(e, false);
				}
			}
		}
	}

	/**
	 * Clears the registry from all registered bundles.
	 */
	public static void clearRegistry() {
		EXTENDING_BUNDLES.clear();
		for (Resource resource : RESOURCE_SET.getResources()) {
			resource.unload();
		}
		RESOURCE_SET.getResources().clear();
		REGISTERED_MODULES.clear();
	}
}
