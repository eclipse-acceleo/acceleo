/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
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
	/** Acceleo preference key for the "Comment" color. */
	String ACCELEO_COLOR_COMMENT_PREFERENCE_KEY = "org.eclipse.acceleo.comment.color"; //$NON-NLS-1$

	/** Acceleo preference key for the "Literal" color. */
	String ACCELEO_COLOR_LITERAL_PREFERENCE_KEY = "org.eclipse.acceleo.literal.color"; //$NON-NLS-1$

	/** Acceleo preference key for the "Template" color. */
	String ACCELEO_COLOR_TEMPLATE_PREFERENCE_KEY = "org.eclipse.acceleo.template.color"; //$NON-NLS-1$

	/** Acceleo preference key for the "Query" color. */
	String ACCELEO_COLOR_QUERY_PREFERENCE_KEY = "org.eclipse.acceleo.query.color"; //$NON-NLS-1$

	/** Acceleo preference key for the "Macro" color. */
	String ACCELEO_COLOR_MACRO_PREFERENCE_KEY = "org.eclipse.acceleo.macro.color"; //$NON-NLS-1$

	/** Acceleo preference key for the "Protected area" color. */
	String ACCELEO_COLOR_PROTECTED_PREFERENCE_KEY = "org.eclipse.acceleo.protected.color"; //$NON-NLS-1$

	/** Acceleo preference key for the "If" color. */
	String ACCELEO_COLOR_IF_PREFERENCE_KEY = "org.eclipse.acceleo.if.color"; //$NON-NLS-1$

	/** Acceleo preference key for the "For" color. */
	String ACCELEO_COLOR_FOR_PREFERENCE_KEY = "org.eclipse.acceleo.for.color"; //$NON-NLS-1$

	/** Acceleo preference key for the "Block" color. */
	String ACCELEO_COLOR_BLOCK_PREFERENCE_KEY = "org.eclipse.acceleo.block.color"; //$NON-NLS-1$

	/** Acceleo preference key for the "Keyword" color. */
	String ACCELEO_COLOR_KEYWORD_PREFERENCE_KEY = "org.eclipse.acceleo.keyword.color"; //$NON-NLS-1$

	/** Acceleo preference key for the "first variable" color. */
	String ACCELEO_COLOR_FIRST_VAR_PREFERENCE_KEY = "org.eclipse.acceleo.first.var.color"; //$NON-NLS-1$

	/** Acceleo preference key for the "Default" color. */
	String ACCELEO_COLOR_DEFAULT_PREFERENCE_KEY = "org.eclipse.acceleo.default.color"; //$NON-NLS-1$

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

	/** This color will be used for the hover information. */
	RGB BLACK = new RGB(0, 0, 0);

	/** This will be used wherever we need the default "template-red" color. */
	RGB RED = new RGB(192, 0, 0);

}
