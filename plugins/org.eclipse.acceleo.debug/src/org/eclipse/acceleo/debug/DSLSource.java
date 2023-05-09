/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.debug;

/**
 * A source location.
 *
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class DSLSource {

	/**
	 * The source path.
	 */
	final String path;

	/**
	 * The start line.
	 */
	final long startLine;

	/**
	 * The start column.
	 */
	final long startColumn;

	/**
	 * The end line.
	 */
	final long endLine;

	/**
	 * The end column.
	 */
	final long endColumn;

	/**
	 * Constructor.
	 * 
	 * @param path
	 *            the source path
	 * @param startLine
	 *            the start line
	 * @param startColumn
	 *            the start column
	 * @param endLine
	 *            the end line
	 * @param endColumn
	 *            the end column
	 */
	public DSLSource(String path, long startLine, long startColumn, long endLine, long endColumn) {
		this.path = path;
		this.startLine = startLine;
		this.startColumn = startColumn;
		this.endLine = endLine;
		this.endColumn = endColumn;
	}

	/**
	 * Gets the source path.
	 * 
	 * @return the source path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Gets the start line.
	 * 
	 * @return the start line
	 */
	public long getStartLine() {
		return startLine;
	}

	/**
	 * Gets the start column.
	 * 
	 * @return the start column
	 */
	public long getStartColumn() {
		return startColumn;
	}

	/**
	 * Gets the end line.
	 * 
	 * @return the end line
	 */
	public long getEndLine() {
		return endLine;
	}

	/**
	 * Gets the end column.
	 * 
	 * @return the end column
	 */
	public long getEndColumn() {
		return endColumn;
	}

}
