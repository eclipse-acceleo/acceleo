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
package org.eclipse.acceleo.engine.internal.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.CompactLinkedHashSet;
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
	protected static final Map<Bundle, AcceleoDynamicModulesDescriptor> EXTENDING_BUNDLES = new HashMap<Bundle, AcceleoDynamicModulesDescriptor>();

	/** This will contain the modules contained by this registry. */
	private static final Set<DynamicModuleContribution> REGISTERED_MODULES = new CompactLinkedHashSet<DynamicModuleContribution>();

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
	 * @param paths
	 *            Paths where dynamic modules are located.
	 */
	public static void addExtendingBundle(Bundle bundle, List<String> paths) {
		AcceleoDynamicModulesDescriptor descriptor = EXTENDING_BUNDLES.get(bundle);
		if (descriptor == null) {
			descriptor = new AcceleoDynamicModulesDescriptor(new ArrayList<String>(), new ArrayList<String>());
		}
		descriptor.getPaths().addAll(paths);
		EXTENDING_BUNDLES.put(bundle, descriptor);
	}

	/**
	 * Adds a bundle in the extending bundles map.
	 * 
	 * @param bundle
	 *            The extending bundle.
	 * @param acceleoDynamicModulesDescriptor
	 *            The extension point descriptor
	 */
	public static void addExtendingBundle(Bundle bundle,
			AcceleoDynamicModulesDescriptor acceleoDynamicModulesDescriptor) {
		AcceleoDynamicModulesDescriptor descriptor = EXTENDING_BUNDLES.get(bundle);
		if (descriptor == null) {
			descriptor = new AcceleoDynamicModulesDescriptor(new ArrayList<String>(), new ArrayList<String>());
		}

		descriptor.getGeneratorIDs().addAll(acceleoDynamicModulesDescriptor.getGeneratorIDs());
		descriptor.getPaths().addAll(acceleoDynamicModulesDescriptor.getPaths());

		EXTENDING_BUNDLES.put(bundle, descriptor);
	}

	/**
	 * Clears the registry from all registered bundles.
	 */
	public static void clearRegistry() {
		EXTENDING_BUNDLES.clear();
		REGISTERED_MODULES.clear();
	}

	/**
	 * Returns <code>true</code> if there are some bundles providing dynamic templates.
	 * 
	 * @return <code>true</code> if there are some bundles providing dynamic templates.
	 */
	public static boolean hasDynamicModulesDescriptors() {
		return EXTENDING_BUNDLES.size() > 0;
	}

	/**
	 * Returns all registered modules. The returned set is a copy of this instance's.
	 * 
	 * @param generatorID
	 *            The generator ID to look for
	 * @return A copy of the registered modules set.
	 */
	public static Set<DynamicModuleContribution> getRegisteredModules(String generatorID) {
		refreshModules(generatorID);
		return new CompactLinkedHashSet<DynamicModuleContribution>(REGISTERED_MODULES);
	}

	/**
	 * Returns all registered modules. The returned set is a copy of this instance's.
	 * 
	 * @return A copy of the registered modules set.
	 */
	public static Set<DynamicModuleContribution> getRegisteredModules() {
		refreshModules(null);
		return new CompactLinkedHashSet<DynamicModuleContribution>(REGISTERED_MODULES);
	}

	/**
	 * This will be called prior to all invocations of getRegisteredModules so as to be aware of new additions
	 * or removals of dynamic templates.
	 * 
	 * @param generatorID
	 *            The generator ID for which we will refresh the modules or null to refresh all the registered
	 *            modules
	 */
	@SuppressWarnings("unchecked")
	private static void refreshModules(String generatorID) {
		REGISTERED_MODULES.clear();

		if (generatorID != null) {
			boolean hasDynamicModulesForGeneratorId = false;
			Collection<AcceleoDynamicModulesDescriptor> values = EXTENDING_BUNDLES.values();
			for (AcceleoDynamicModulesDescriptor acceleoDynamicModulesDescriptor : values) {
				// If a bundle does not specify the list of its generator IDs, it will work for all the
				// generators
				if (acceleoDynamicModulesDescriptor.getGeneratorIDs().size() == 0
						|| acceleoDynamicModulesDescriptor.getGeneratorIDs().contains(generatorID)) {
					hasDynamicModulesForGeneratorId = true;
					break;
				}
			}

			if (!hasDynamicModulesForGeneratorId) {
				return;
			}
		}

		final List<Bundle> uninstalledBundles = new ArrayList<Bundle>();
		final String pathSeparator = "/"; //$NON-NLS-1$
		for (java.util.Map.Entry<Bundle, AcceleoDynamicModulesDescriptor> entry : new CompactLinkedHashSet<java.util.Map.Entry<Bundle, AcceleoDynamicModulesDescriptor>>(
				EXTENDING_BUNDLES.entrySet())) {
			Bundle bundle = entry.getKey();
			if (bundle.getState() == Bundle.UNINSTALLED) {
				uninstalledBundles.add(bundle);
				continue;
			}
			AcceleoDynamicModulesDescriptor descriptor = entry.getValue();
			for (String path : descriptor.getPaths()) {
				String actualPath = path;
				if (actualPath.charAt(0) != '/') {
					actualPath = '/' + actualPath;
				}
				if (actualPath.length() > 1 && actualPath.charAt(actualPath.length() - 1) == '/') {
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
				// We'll seek in the whole bundle and filter out later
				Enumeration<URL> emtlFiles = bundle.findEntries(pathSeparator, filePattern, true);
				// no dynamic templates in this bundle
				if (emtlFiles == null) {
					AcceleoEnginePlugin.log(AcceleoEngineMessages.getString(
							"AcceleoDynamicTemplatesEclipseUtil.MissingDynamicTemplates", bundle //$NON-NLS-1$
									.getSymbolicName(), path), false);
					return;
				}
				try {
					List<URL> modules = new ArrayList<URL>();
					while (emtlFiles.hasMoreElements()) {
						final URL next = emtlFiles.nextElement();
						if (actualPath == pathSeparator) {
							final File moduleFile = new File(FileLocator.toFileURL(next).getFile());
							modules.add(next);
						} else {
							modules.add(next);
						}
					}
					REGISTERED_MODULES.add(new DynamicModuleContribution(descriptor.getGeneratorIDs(),
							modules));
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
