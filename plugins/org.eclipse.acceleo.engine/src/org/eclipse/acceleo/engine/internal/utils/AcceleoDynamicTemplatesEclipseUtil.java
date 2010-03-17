/*******************************************************************************
 * Copyright (c) 2009, 2010 Obeo.
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
	 * Clears the registry from all registered bundles.
	 */
	public static void clearRegistry() {
		EXTENDING_BUNDLES.clear();
		REGISTERED_MODULES.clear();
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
	 * This will be called prior to all invocations of getRegisteredModules so as to be aware of new additions
	 * or removals of dynamic templates.
	 */
	@SuppressWarnings("unchecked")
	private static void refreshModules() {
		REGISTERED_MODULES.clear();
		final List<Bundle> uninstalledBundles = new ArrayList<Bundle>();
		final String pathSeparator = "/"; //$NON-NLS-1$
		for (java.util.Map.Entry<Bundle, List<String>> entry : new LinkedHashSet<java.util.Map.Entry<Bundle, List<String>>>(
				EXTENDING_BUNDLES.entrySet())) {
			if (entry.getKey().getState() == Bundle.UNINSTALLED) {
				uninstalledBundles.add(entry.getKey());
				continue;
			}
			for (String path : entry.getValue()) {
				String actualPath = path;
				if (actualPath.charAt(0) != '/') {
					actualPath = '/' + actualPath;
				}
				if (actualPath.charAt(actualPath.length() - 1) == '/') {
					actualPath = actualPath.substring(0, actualPath.length() - 1);
				}
				if (actualPath.startsWith("/src") || actualPath.startsWith("/bin")) { //$NON-NLS-1$ //$NON-NLS-2$
					int firstFragmentEnd = actualPath.indexOf('/', 2);
					if (firstFragmentEnd > 0) {
						actualPath = actualPath.substring(firstFragmentEnd);
					} else {
						actualPath = pathSeparator;
					}
				}
				final String filePattern = "*." + IAcceleoConstants.EMTL_FILE_EXTENSION; //$NON-NLS-1$
				Enumeration<URL> emtlFiles = entry.getKey().findEntries(pathSeparator, filePattern, true);
				// no dynamic templates in this bundle
				if (emtlFiles == null) {
					AcceleoEnginePlugin.log(AcceleoEngineMessages.getString(
							"AcceleoDynamicTemplatesEclipseUtil.MissingDynamicTemplates", entry.getKey() //$NON-NLS-1$
									.getSymbolicName(), path), false);
					return;
				}
				try {
					while (emtlFiles.hasMoreElements()) {
						final URL next = emtlFiles.nextElement();
						if (actualPath == pathSeparator) {
							final File moduleFile = new File(FileLocator.toFileURL(next).getFile());
							if (!moduleFile.isDirectory() && moduleFile.exists() && moduleFile.canRead()) {
								REGISTERED_MODULES.add(moduleFile);
							}
						} else {
							String emtlPath = next.getPath();
							if (emtlPath.substring(0, emtlPath.lastIndexOf('/')).contains(actualPath)) {
								final File moduleFile = new File(FileLocator.toFileURL(next).getFile());
								if (!moduleFile.isDirectory() && moduleFile.exists() && moduleFile.canRead()) {
									REGISTERED_MODULES.add(moduleFile);
								}
							}
						}
					}
				} catch (IOException e) {
					AcceleoEnginePlugin.log(e, false);
				}
			}
		}
		for (Bundle uninstalledBundle : uninstalledBundles) {
			EXTENDING_BUNDLES.remove(uninstalledBundle);
		}
	}
}
