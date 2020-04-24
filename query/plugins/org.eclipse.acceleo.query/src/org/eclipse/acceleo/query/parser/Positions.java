/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * Keep track of positions.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class Positions {

	/**
	 * Mapping from an {@link Object} to its start position in the parsed text.
	 */
	private final Map<Object, Integer> startPositions = new HashMap<Object, Integer>();

	/**
	 * Mapping from an {@link Object} to its start line in the parsed text.
	 */
	private final Map<Object, Integer> startLines = new HashMap<Object, Integer>();

	/**
	 * Mapping from an {@link Object} to its start column in the parsed text.
	 */
	private final Map<Object, Integer> startColumns = new HashMap<Object, Integer>();

	/**
	 * Mapping from an {@link Object} to its end position in the parsed text.
	 */
	private final Map<Object, Integer> endPositions = new HashMap<Object, Integer>();

	/**
	 * Mapping from an {@link Object} to its end line in the parsed text.
	 */
	private final Map<Object, Integer> endLines = new HashMap<Object, Integer>();

	/**
	 * Mapping from an {@link Object} to its end column in the parsed text.
	 */
	private final Map<Object, Integer> endColumns = new HashMap<Object, Integer>();

	/**
	 * Gets the start position in the parsed text for the given {@link Object}.
	 * 
	 * @param object
	 *            the {@link Object}
	 * @return the start position in the parsed text
	 */
	public Integer getStartPositions(Object object) {
		return startPositions.get(object);
	}

	/**
	 * Gets the start line in the parsed text for the given {@link Object}.
	 * 
	 * @param object
	 *            the {@link Object}
	 * @return the start line in the parsed text
	 */
	public Integer getStartLines(Object object) {
		return startLines.get(object);
	}

	/**
	 * Gets the start column in the parsed text for the given {@link Object}.
	 * 
	 * @param object
	 *            the {@link Object}
	 * @return the start column in the parsed text
	 */
	public Integer getStartColumns(Object object) {
		return startColumns.get(object);
	}

	/**
	 * Gets the end position in the parsed text for the given {@link Object}.
	 * 
	 * @param object
	 *            the {@link Object}
	 * @return the end position in the parsed text
	 */
	public Integer getEndPositions(Object object) {
		return endPositions.get(object);
	}

	/**
	 * Gets the end line in the parsed text for the given {@link Object}.
	 * 
	 * @param object
	 *            the {@link Object}
	 * @return the end line in the parsed text
	 */
	public Integer getEndLines(Object object) {
		return endLines.get(object);
	}

	/**
	 * Gets the end column in the parsed text for the given {@link Object}.
	 * 
	 * @param object
	 *            the {@link Object}
	 * @return the end column in the parsed text
	 */
	public Integer getEndColumns(Object object) {
		return endColumns.get(object);
	}

	/**
	 * Sets the start position in the parsed text for the given {@link Object}.
	 * 
	 * @param object
	 *            the {@link Object}
	 * @param position
	 *            the position
	 */
	public void setStartPositions(Object object, Integer position) {
		startPositions.put(object, position);
	}

	/**
	 * Sets the start line in the parsed text for the given {@link Object}.
	 * 
	 * @param object
	 *            the {@link Object}
	 * @param line
	 *            the line
	 */
	public void setStartLines(Object object, Integer line) {
		startLines.put(object, line);
	}

	/**
	 * Sets the start column in the parsed text for the given {@link Object}.
	 * 
	 * @param object
	 *            the {@link Object}
	 * @param column
	 *            the column
	 */
	public void setStartColumns(Object object, Integer column) {
		startColumns.put(object, column);
	}

	/**
	 * Sets the end position in the parsed text for the given {@link Object}.
	 * 
	 * @param object
	 *            the {@link Object}
	 * @param position
	 *            the position
	 */
	public void setEndPositions(Object object, Integer position) {
		endPositions.put(object, position);
	}

	/**
	 * Sets the end line in the parsed text for the given {@link Object}.
	 * 
	 * @param object
	 *            the {@link Object}
	 * @param line
	 *            the line
	 */
	public void setEndLines(Object object, Integer line) {
		endLines.put(object, line);
	}

	/**
	 * Sets the end column in the parsed text for the given {@link Object}.
	 * 
	 * @param object
	 *            the {@link Object}
	 * @param column
	 *            the column
	 */
	public void setEndColumns(Object object, Integer column) {
		endColumns.put(object, column);
	}

	/**
	 * Removes the positions of the given {@link Object}.
	 * 
	 * @param object
	 *            the {@link Object}
	 */
	public void remove(Object object) {
		startPositions.remove(object);
		startLines.remove(object);
		startColumns.remove(object);
		endPositions.remove(object);
		endLines.remove(object);
		endColumns.remove(object);
	}

}
