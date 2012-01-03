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
 * The Acceleo Service Listeners Utils manages all the traceability listener register thanks to the
 * "org.eclipse.acceleo.engine.traceabilityListener" extension point.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public final class TraceabilityRegistryListeners implements IRegistryEventListener {

	/**
	 * The ID of the extension point.
	 */
	public static final String TRACEABILITY_LISTENERS_EXTENSION_POINT = "org.eclipse.acceleo.engine.traceabilityListener"; //$NON-NLS-1$

	/**
	 * The name of the extension point "traceabilityListener" tag.
	 */
	public static final String TRACEABILITY_LISTENERS_NAME = "traceabilityListener"; //$NON-NLS-1$

	/**
	 * The name of the extension point "class" tag.
	 */
	public static final String TRACEABILITY_LISTENERS_CLASS = "class"; //$NON-NLS-1$

	/**
	 * The name of the extension point "forceTraceability" tag.
	 */
	public static final String TRACEABILITY_LISTENERS_FORCE_TRACEABILITY = "forceTraceability"; //$NON-NLS-1$

	/**
	 * The name of the extension point "nature" tag.
	 */
	public static final String TRACEABILITY_LISTENERS_NATURE = "nature"; //$NON-NLS-1$

	/**
	 * Though this listener reacts to the extension point changes, there could have been contributions before
	 * it's been registered. This will parse these extensions.
	 */
	public void parseInitialContributions() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();

		for (IExtension extension : registry.getExtensionPoint(TRACEABILITY_LISTENERS_EXTENSION_POINT)
				.getExtensions()) {
			parseExtension(extension);
		}
	}

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
	 * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtension[])
	 */
	public void removed(IExtension[] extensions) {
		for (IExtension extension : extensions) {
			final IConfigurationElement[] configElements = extension.getConfigurationElements();
			for (IConfigurationElement elem : configElements) {
				if (TRACEABILITY_LISTENERS_NAME.equals(elem.getName())) {
					final String className = elem.getAttribute(TRACEABILITY_LISTENERS_CLASS);
					AcceleoTraceabilityRegistryListenerUils.removeListener(className);
				}
			}
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
			if (TRACEABILITY_LISTENERS_NAME.equals(elem.getName())) {
				AcceleoTraceabilityRegistryListenerUils.addListener(new AcceleoListenerDescriptor(elem));
			}
		}
	}
}
