/*******************************************************************************
 * Copyright (c) 2011, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.internal.language;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.Platform;

/**
 * This listener will allow us to be aware of contribution changes against the language interpreter extension
 * point.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class LanguageInterpreterRegistryListener implements IRegistryEventListener {
	/** ID of the language interpreter extension point. Must be kept in sync with the plugin.xml declaration. */
	public static final String LANGUAGE_INTERPRETER_EXTENSION_POINT_ID = "org.eclipse.acceleo.ui.language.interpreter"; //$NON-NLS-1$

	/** Name of the extension point's "languageInterpreter" tag. */
	private static final String LANGUAGE_INTERPRETER_TAG_INTERPRETER = "languageInterpreter"; //$NON-NLS-1$

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
	 * it's been registered against the registry. This will parse these initial extensions.
	 */
	public void parseInitialContributions() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();

		for (IExtension extension : registry.getExtensionPoint(LANGUAGE_INTERPRETER_EXTENSION_POINT_ID)
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
		for (IExtension extension : extensions) {
			final IConfigurationElement[] configElements = extension.getConfigurationElements();
			for (IConfigurationElement elem : configElements) {
				if (LANGUAGE_INTERPRETER_TAG_INTERPRETER.equals(elem.getName())) {
					final String className = elem
							.getAttribute(LanguageInterpreterDescriptor.LANGUAGE_INTERPRETER_ATTRIBUTE_CLASS);
					LanguageInterpreterRegistry.removeInterpreter(className);
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
			if (LANGUAGE_INTERPRETER_TAG_INTERPRETER.equals(elem.getName())) {
				LanguageInterpreterRegistry.addInterpreter(new LanguageInterpreterDescriptor(elem));
			}
		}
	}
}
