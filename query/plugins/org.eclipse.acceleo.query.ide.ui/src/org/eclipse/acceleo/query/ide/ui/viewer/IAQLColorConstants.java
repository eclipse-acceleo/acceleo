/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.ui.viewer;

import org.eclipse.swt.graphics.RGB;

/**
 * AQL color constants.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IAQLColorConstants {

	/**
	 * Keyword color.
	 */
	RGB KEYWORD = new RGB(0, 128, 0);

	/**
	 * {@link org.eclipse.acceleo.query.ast.StringLiteral StringLiteral} color.
	 */
	RGB STRING = new RGB(128, 128, 128);

	/**
	 * {@link org.eclipse.acceleo.query.ast.TypeLiteral TypeLiteral}.
	 */
	RGB TYPE_LITERAL = new RGB(0, 0, 128);
}
