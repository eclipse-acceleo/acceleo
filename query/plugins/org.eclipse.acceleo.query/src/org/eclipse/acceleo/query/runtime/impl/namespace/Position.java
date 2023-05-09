/*******************************************************************************
 * Copyright (c) 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl.namespace;

import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation.IPosition;

/**
 * {@link IPosition} implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class Position implements IPosition {

	/**
	 * The line number.
	 */
	private final int line;

	/**
	 * The column number.
	 */
	private final int column;

	/**
	 * The position offset.
	 */
	private final int position;

	/**
	 * Constructor.
	 * 
	 * @param line
	 *            the line number
	 * @param column
	 *            the column number
	 * @param position
	 *            the position offset
	 */
	public Position(int line, int column, int position) {
		this.line = line;
		this.column = column;
		this.position = position;
	}

	@Override
	public int getLine() {
		return line;
	}

	@Override
	public int getColumn() {
		return column;
	}

	@Override
	public int getPosition() {
		return position;
	}

}
