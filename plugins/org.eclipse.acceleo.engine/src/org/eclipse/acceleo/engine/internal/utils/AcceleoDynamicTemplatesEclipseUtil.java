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
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.core.runtime.FileLocator;
import org.osgi.framework.Bundle;

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
	private static final Set<File> REGISTERED_MODULES = new LinkedHashSet<File>();

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
	public static Set<File> getRegisteredModules() {
		refreshModules();
		return new LinkedHashSet<File>(REGISTERED_MODULES);
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
	 * This will be called prior to all invocations of getRegisteredModules so as to be aware of new additions
	 * or removals of dynamic templates.
	 */
	@SuppressWarnings("unchecked")
	private static void refreshModules() {
		REGISTERED_MODULES.clear();
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
				// no dynamic templates here
				if (emtlFiles == null) {
					AcceleoEnginePlugin.log(AcceleoEngineMessages.getString(
							"AcceleoDynamicTemplatesEclipseUtil.MissingDynamicTemplates", entry.getKey() //$NON-NLS-1$
									.getSymbolicName(), path), false);
					return;
				}
				try {
					while (emtlFiles.hasMoreElements()) {
						final URL next = emtlFiles.nextElement();
						final File moduleFile = new File(FileLocator.toFileURL(next).getFile());
						if (moduleFile.exists() && moduleFile.canRead()) {
							REGISTERED_MODULES.add(moduleFile);
						}
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
		REGISTERED_MODULES.clear();
	}
}
