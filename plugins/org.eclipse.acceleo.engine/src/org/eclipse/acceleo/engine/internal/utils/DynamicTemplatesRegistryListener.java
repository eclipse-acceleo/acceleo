/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.internal.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * This listener will allow us to be aware of contribution changes against the dynamic templates extension
 * point.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class DynamicTemplatesRegistryListener implements IRegistryEventListener {
	/** Name of the extension point to parse for template locations. */
	public static final String DYNAMIC_TEMPLATES_EXTENSION_POINT = "org.eclipse.acceleo.engine.dynamic.templates"; //$NON-NLS-1$

	/** Name of the extension point's templates tag "path" atribute. */
	private static final String DYNAMIC_TEMPLATES_ATTRIBUTE_PATH = "path"; //$NON-NLS-1$

	/** Name of the extension point's "templates" tag. */
	private static final String DYNAMIC_TEMPLATES_TAG_TEMPLATES = "templates"; //$NON-NLS-1$

	/** Name of the extension point's "generator" tag. */
	private static final String DYNAMIC_TEMPLATES_TAG_GENERATOR = "generator"; //$NON-NLS-1$

	/** Name of the extension point's "generator ID" tag. */
	private static final String DYNAMIC_MODULES_TAG_GENERATOR_ID = "generatorID"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtension[])
	 */
	public void added(IExtension[] extensions) {
		for (IExtension extension : extensions) {
			parseExtension(extension);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtensionPoint[])
	 */
	public void added(IExtensionPoint[] extensionPoints) {
		// no need to listen to this event
	}

	/**
	 * Though this listener reacts to the extension point changes, there could have been contributions before
	 * it's been registered. This will parse these extensions.
	 */
	public void parseInitialContributions() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();

		// Dynamic templates
		for (IExtension extension : registry.getExtensionPoint(DYNAMIC_TEMPLATES_EXTENSION_POINT)
				.getExtensions()) {
			parseExtension(extension);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtension[])
	 */
	public void removed(IExtension[] extensions) {
		/*
		 * Extensions will be removed on the fly by AcceleoDynamicTemplatesEclipseUtil when trying to access
		 * uninstalled bundles
		 */
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtensionPoint[])
	 */
	public void removed(IExtensionPoint[] extensionPoints) {
		// no need to listen to this event
	}

	/**
	 * Parses a single extension contribution.
	 * 
	 * @param extension
	 *            Parses the given extension and adds its contribution to the registry.
	 */
	private void parseExtension(IExtension extension) {
		final IConfigurationElement[] configElements = extension.getConfigurationElements();
		final List<String> paths = new ArrayList<String>(configElements.length);
		final List<String> generators = new ArrayList<String>(configElements.length);
		for (IConfigurationElement elem : configElements) {
			if (DYNAMIC_TEMPLATES_TAG_TEMPLATES.equals(elem.getName())) {
				String path = elem.getAttribute(DYNAMIC_TEMPLATES_ATTRIBUTE_PATH);
				if (path != null) {
					paths.add(path);
				}
			} else if (DYNAMIC_TEMPLATES_TAG_GENERATOR.equals(elem.getName())) {
				String generator = elem.getAttribute(DYNAMIC_MODULES_TAG_GENERATOR_ID);
				if (generator != null) {
					generators.add(generator);
				}
			}
		}
		final Bundle bundle = Platform.getBundle(extension.getContributor().getName());
		// If bundle is null, the bundle id is different than its name.
		if (bundle != null) {
			if (paths.size() == 0) {
				paths.add("/"); //$NON-NLS-1$
			}
			AcceleoDynamicModulesDescriptor acceleoDynamicModulesDescriptor = new AcceleoDynamicModulesDescriptor(
					generators, paths);
			AcceleoDynamicTemplatesEclipseUtil.addExtendingBundle(bundle, acceleoDynamicModulesDescriptor);
		}
	}
}
