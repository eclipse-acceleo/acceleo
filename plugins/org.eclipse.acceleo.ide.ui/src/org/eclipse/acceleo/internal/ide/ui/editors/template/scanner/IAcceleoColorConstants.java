/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template.scanner;

import org.eclipse.swt.graphics.RGB;

/**
 * Color constants for the Acceleo template editor.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public interface IAcceleoColorConstants {

	/**
	 * Comment color.
	 */
	RGB COMMENT = new RGB(63, 127, 95);

	/**
	 * Literal color.
	 */
	RGB LITERAL = new RGB(63, 127, 127);

	/**
	 * Template, Query and Macro color.
	 */
	RGB BEHAVIORAL_FEATURE = new RGB(192, 0, 0);

	/**
	 * Protected Area color.
	 */
	RGB PROTECTED_AREA = new RGB(130, 160, 190);

	/**
	 * If statement color.
	 */
	RGB IF = new RGB(80, 80, 255);

	/**
	 * Let statement color.
	 */
	RGB LET = new RGB(80, 80, 255);

	/**
	 * For statement color.
	 */
	RGB FOR = new RGB(80, 80, 255);

	/**
	 * Block color.
	 */
	RGB BLOCK = new RGB(80, 80, 255);

	/**
	 * Keyword color.
	 */
	RGB KEYWORD = new RGB(127, 0, 85);

	/**
	 * First variable background color.
	 */
	RGB FIRST_VARIABLE = new RGB(239, 239, 239);

	/**
	 * Default color.
	 */
	RGB DEFAULT = new RGB(0, 0, 0);

}
