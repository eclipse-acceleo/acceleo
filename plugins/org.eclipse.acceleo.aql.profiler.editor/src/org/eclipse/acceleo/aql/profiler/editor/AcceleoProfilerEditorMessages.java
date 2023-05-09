/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.profiler.editor;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Utility class to access externalized Strings for Acceleo Profiler Editor.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoProfilerEditorMessages {
	/** Full qualified path to the properties file in which to seek the keys. */
	private static final String BUNDLE_NAME = "org.eclipse.acceleo.aql.profiler.editor.acceleoprofilereditormessages"; //$NON-NLS-1$

	/** Contains the locale specific {@link String}s needed by this plug-in. */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	/**
	 * Utility classes don't need to (and shouldn't) be instantiated.
	 */
	private AcceleoProfilerEditorMessages() {
		// prevents instantiation
	}

	/**
	 * Returns a specified {@link String} from the resource bundle.
	 * 
	 * @param key
	 *            Key of the String we seek.
	 * @return The String from the resource bundle associated with <code>key</code>.
	 */
	public static String getString(String key) {
		try {
			return getString(key, new Object[] {});
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	/**
	 * Returns a String from the resource bundle binded with the given arguments.
	 * 
	 * @param key
	 *            Key of the String we seek.
	 * @param arguments
	 *            Arguments for the String formatting.
	 * @return formatted {@link String}.
	 * @see MessageFormat#format(String, Object[])
	 */
	public static String getString(String key, Object[] arguments) {
		if (arguments == null) {
			return MessageFormat.format(RESOURCE_BUNDLE.getString(key), new Object[] {});
		}
		return MessageFormat.format(RESOURCE_BUNDLE.getString(key), arguments);
	}
}
