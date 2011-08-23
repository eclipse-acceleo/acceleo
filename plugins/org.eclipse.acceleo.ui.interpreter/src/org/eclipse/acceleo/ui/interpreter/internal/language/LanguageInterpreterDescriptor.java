/*******************************************************************************
 * Copyright (c) 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.internal.language;

import org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

/**
 * Describes a language interpreter as contributed to the extension point.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">laurent Goubet</a>
 */
public final class LanguageInterpreterDescriptor {
	/** Name of the extension point's languageInterpreter tag "class" attribute. */
	public static final String LANGUAGE_INTERPRETER_ATTRIBUTE_CLASS = "class"; //$NON-NLS-1$

	/** Name of the extension point's languageInterpreter tag "label" atribute. */
	private static final String LANGUAGE_INTERPRETER_ATTRIBUTE_LABEL = "label"; //$NON-NLS-1$

	/** Configuration element of this descriptor. */
	private final IConfigurationElement element;

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
	 * Returns the label of this interpreter.
	 * 
	 * @return The label of this interpreter.
	 */
	public String getLabel() {
		return label;
	}
}
