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

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.Platform;

/**
 * This listener will allow us to be aware of contribution changes against the acceleo engine extension point.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class EngineRegistryListener implements IRegistryEventListener {
	/** Name of the extension point to parse for engine creators. */
	public static final String ENGINE_CREATORS_EXTENSION_POINT = "org.eclipse.acceleo.engine.creator"; //$NON-NLS-1$

	/** Name of the extension point's "creator" tag. */
	private static final String ENGINE_CREATORS_TAG_CREATOR = "engineCreator"; //$NON-NLS-1$

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
		for (IExtension extension : registry.getExtensionPoint(ENGINE_CREATORS_EXTENSION_POINT)
				.getExtensions()) {
			parseExtension(extension);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtension[])
	 */
	public synchronized void removed(IExtension[] extensions) {
		for (IExtension extension : extensions) {
			final IConfigurationElement[] configElements = extension.getConfigurationElements();
			for (IConfigurationElement elem : configElements) {
				if (ENGINE_CREATORS_TAG_CREATOR.equals(elem.getName())) {
					final String className = elem
							.getAttribute(AcceleoEngineCreatorDescriptor.ENGINE_CREATORS_ATTRIBUTE_CLASS);
					AcceleoEngineRegistry.removeCreator(className);
				}
			}
		}
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
		for (IConfigurationElement elem : configElements) {
			if (ENGINE_CREATORS_TAG_CREATOR.equals(elem.getName())) {
				AcceleoEngineRegistry.addCreator(new AcceleoEngineCreatorDescriptor(elem));
			}
		}
	}
}
