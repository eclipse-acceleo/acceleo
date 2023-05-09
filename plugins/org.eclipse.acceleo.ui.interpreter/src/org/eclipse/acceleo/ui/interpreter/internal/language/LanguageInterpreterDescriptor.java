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

import org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Describes a language interpreter as contributed to the extension point.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class LanguageInterpreterDescriptor {
	/** Name of the extension point's languageInterpreter tag "class" attribute. */
	public static final String LANGUAGE_INTERPRETER_ATTRIBUTE_CLASS = "class"; //$NON-NLS-1$

	/** Name of the extension point's languageInterpreter tag "icon" atribute. */
	private static final String LANGUAGE_INTERPRETER_ATTRIBUTE_ICON = "icon"; //$NON-NLS-1$

	/** Name of the extension point's languageInterpreter tag "label" atribute. */
	private static final String LANGUAGE_INTERPRETER_ATTRIBUTE_LABEL = "label"; //$NON-NLS-1$

	/** Configuration element of this descriptor. */
	private final IConfigurationElement element;

	/** Icon provided by this interpreter. Could be <code>null</code>. */
	private final ImageDescriptor icon;

	/** Qualified class name of this language interpreter. */
	private final String interpreterClassName;

	/** Label of this particular interpreter. */
	private final String label;

	/**
	 * Instantiates a descriptor with all required information.
	 * 
	 * @param element
	 *            Configuration element from which to create this descriptor.
	 */
	public LanguageInterpreterDescriptor(IConfigurationElement element) {
		this.element = element;
		this.label = element.getAttribute(LANGUAGE_INTERPRETER_ATTRIBUTE_LABEL);
		this.interpreterClassName = element.getAttribute(LANGUAGE_INTERPRETER_ATTRIBUTE_CLASS);

		ImageDescriptor parsedIcon = null;
		final String iconPath = element.getAttribute(LANGUAGE_INTERPRETER_ATTRIBUTE_ICON);
		if (iconPath != null) {
			final String extendingPluginId = element.getContributor().getName();
			if (extendingPluginId != null && extendingPluginId.length() > 0) {
				parsedIcon = AbstractUIPlugin.imageDescriptorFromPlugin(extendingPluginId, iconPath);
			}
		}
		this.icon = parsedIcon;
	}

	/**
	 * Creates an instance of the described language interpreter.
	 * 
	 * @return The created language interpreter instance.
	 */
	public AbstractLanguageInterpreter createLanguageInterpreter() {
		try {
			return (AbstractLanguageInterpreter)element
					.createExecutableExtension(LANGUAGE_INTERPRETER_ATTRIBUTE_CLASS);
		} catch (CoreException e) {
			// FIXME LOG this
		}
		return null;
	}

	/**
	 * Returns the class name of this interpreter, as parsed from the extension contribution.
	 * 
	 * @return The class name of this interpreter.
	 */
	public String getClassName() {
		return interpreterClassName;
	}

	/**
	 * Returns the icon of this interpreter.
	 * 
	 * @return The icon of this interpreter.
	 */
	public ImageDescriptor getIcon() {
		return icon;
	}

	/**
	 * Returns the label of this interpreter.
	 * 
	 * @return The label of this interpreter.
	 */
	public String getLabel() {
		return label;
	}
}
