/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
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
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This class is not intended to be subclassed by clients.
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
	 * Template color.
	 */
	RGB TEMPLATE = new RGB(192, 0, 0);

	/**
	 * Query.
	 */
	RGB QUERY = new RGB(127, 0, 85);

	/**
	 * Macro color.
	 */
	RGB MACRO = new RGB(192, 0, 0);

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
	RGB FIRST_VARIABLE = new RGB(242, 242, 242);

	/**
	 * Default color.
	 */
	RGB DEFAULT = new RGB(0, 0, 0);

}
