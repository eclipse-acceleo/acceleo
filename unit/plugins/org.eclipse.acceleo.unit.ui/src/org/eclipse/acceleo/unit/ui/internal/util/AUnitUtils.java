/*******************************************************************************
 * Copyright (c) 2006, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.unit.ui.internal.util;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;

/**
 * The util class.
 * 
 * @author <a href="mailto:hugo.marchadour@obeo.fr">Hugo Marchadour</a>
 */
public final class AUnitUtils {

	/**
	 * The constructor.
	 */
	private AUnitUtils() {
		// never used
	}

	/**
	 * Convert a string in class indentifier.
	 * 
	 * @param s
	 *            the source string.
	 * @return the class indentifier string.
	 */
	public static String convert2ClassIdent(String s) {

		String tmp = s.trim();
		char[] charArray = tmp.toCharArray();
		charArray[0] = Character.toUpperCase(charArray[0]);
		tmp = String.copyValueOf(charArray);
		return tmp;
	}

	/**
	 * Finds the workspace line separator.
	 * 
	 * @return the current workspace line separator.
	 */
	public static String getLineSeparator() {
		String lineSeparator = System.getProperty("line.separator"); //$NON-NLS-1$

		// line delimiter in workspace preference
		IScopeContext[] scopeContext = new IScopeContext[] {new InstanceScope() };
		String platformLineSeparator = Platform.getPreferencesService().getString(Platform.PI_RUNTIME,
				Platform.PREF_LINE_SEPARATOR, null, scopeContext);
		if (platformLineSeparator != null) {
			lineSeparator = platformLineSeparator;
		}
		return lineSeparator;
	}
}
