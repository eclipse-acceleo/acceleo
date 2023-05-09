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

import java.util.ArrayList;
import java.util.List;

/**
 * This will contain and allow access to all of the language interpreter that have been parsed from the
 * extension point.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class LanguageInterpreterRegistry {
	/** List of language interpreters created from the extension point contributions. */
	private static final List<LanguageInterpreterDescriptor> INTERPRETERS = new ArrayList<LanguageInterpreterDescriptor>();

	/**
	 * Utility classes don't need a default contructor.
	 */
	private LanguageInterpreterRegistry() {
		// hides default constructor.
	}

	/**
	 * Adds a language interpreter to the registry.
	 * 
	 * @param interpreter
	 *            The language interpreter that is to be added to the registry.
	 */
	public static void addInterpreter(LanguageInterpreterDescriptor interpreter) {
		INTERPRETERS.add(interpreter);
	}

	/**
	 * Clears the registry of all parsed extensions.
	 */
	public static void clearRegistry() {
		INTERPRETERS.clear();
	}

	/**
	 * Returns the list of all parsed interpreters extensions.
	 * 
	 * @return The list of all parsed interpreters extensions.
	 */
	public static List<LanguageInterpreterDescriptor> getRegisteredInterpreters() {
		return new ArrayList<LanguageInterpreterDescriptor>(INTERPRETERS);
	}

	/**
	 * Removes a language interpreter from the registry.
	 * 
	 * @param interpreterClassName
	 *            Fully qualified class name of the language interpreter that is to be removed from the
	 *            registry.
	 */
	public static void removeInterpreter(String interpreterClassName) {
		for (LanguageInterpreterDescriptor interpreter : getRegisteredInterpreters()) {
			if (interpreter.getClassName().equals(interpreterClassName)) {
				INTERPRETERS.remove(interpreter);
				break;
			}
		}
	}
}
