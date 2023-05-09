/*******************************************************************************
 * Copyright (c) 2020, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EObject;

/**
 * Keep track of positions.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class Positions<T extends EObject> {

	/**
	 * Mapping from an {@link T} to its identifier start position in the parsed text.
	 */
	private final Map<T, Integer> identifierStartPositions = new HashMap<T, Integer>();

	/**
	 * Mapping from an {@link T} to its identifier start line in the parsed text.
	 */
	private final Map<T, Integer> identifierStartLines = new HashMap<T, Integer>();

	/**
	 * Mapping from an {@link T} to its identifier start column in the parsed text.
	 */
	private final Map<T, Integer> identifierStartColumns = new HashMap<T, Integer>();

	/**
	 * Mapping from an {@link T} to its identifier end position in the parsed text.
	 */
	private final Map<T, Integer> identifierEndPositions = new HashMap<T, Integer>();

	/**
	 * Mapping from an {@link T} to its identifier end line in the parsed text.
	 */
	private final Map<T, Integer> identifierEndLines = new HashMap<T, Integer>();

	/**
	 * Mapping from an {@link T} to its identifier end column in the parsed text.
	 */
	private final Map<T, Integer> identifierEndColumns = new HashMap<T, Integer>();

	/**
	 * Mapping from an {@link T} to its start position in the parsed text.
	 */
	private final Map<T, Integer> startPositions = new HashMap<T, Integer>();

	/**
	 * Mapping from an {@link T} to its start line in the parsed text.
	 */
	private final Map<T, Integer> startLines = new HashMap<T, Integer>();

	/**
	 * Mapping from an {@link T} to its start column in the parsed text.
	 */
	private final Map<T, Integer> startColumns = new HashMap<T, Integer>();

	/**
	 * Mapping from an {@link T} to its end position in the parsed text.
	 */
	private final Map<T, Integer> endPositions = new HashMap<T, Integer>();

	/**
	 * Mapping from an {@link T} to its end line in the parsed text.
	 */
	private final Map<T, Integer> endLines = new HashMap<T, Integer>();

	/**
	 * Mapping from an {@link T} to its end column in the parsed text.
	 */
	private final Map<T, Integer> endColumns = new HashMap<T, Integer>();

	/**
	 * Gets the identifier start position in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @return the identifier start position in the parsed text
	 */
	public Integer getIdentifierStartPositions(T node) {
		return identifierStartPositions.get(node);
	}

	/**
	 * Gets the identifier start line in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @return the identifier start line in the parsed text
	 */
	public Integer getIdentifierStartLines(T node) {
		return identifierStartLines.get(node);
	}

	/**
	 * Gets the identifier start column in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @return the identifier start column in the parsed text
	 */
	public Integer getIdentifierStartColumns(T node) {
		return identifierStartColumns.get(node);
	}

	/**
	 * Gets the identifier end position in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @return the identifier end position in the parsed text
	 */
	public Integer getIdentifierEndPositions(T node) {
		return identifierEndPositions.get(node);
	}

	/**
	 * Gets the end line in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @return the identifier end line in the parsed text
	 */
	public Integer getIdentifierEndLines(T node) {
		return identifierEndLines.get(node);
	}

	/**
	 * Gets the identifier end column in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @return the identifier end column in the parsed text
	 */
	public Integer getIdentifierEndColumns(T node) {
		return identifierEndColumns.get(node);
	}

	/**
	 * Sets the identifier start position in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @param position
	 *            the position
	 */
	public void setIdentifierStartPositions(T node, Integer position) {
		identifierStartPositions.put(node, position);
	}

	/**
	 * Sets the identifier start line in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @param line
	 *            the line
	 */
	public void setIdentifierStartLines(T node, Integer line) {
		identifierStartLines.put(node, line);
	}

	/**
	 * Sets the identifier start column in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @param column
	 *            the column
	 */
	public void setIdentifierStartColumns(T node, Integer column) {
		identifierStartColumns.put(node, column);
	}

	/**
	 * Sets the identifier end position in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @param position
	 *            the position
	 */
	public void setIdentifierEndPositions(T node, Integer position) {
		identifierEndPositions.put(node, position);
	}

	/**
	 * Sets the identifier end line in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @param line
	 *            the line
	 */
	public void setIdentifierEndLines(T node, Integer line) {
		identifierEndLines.put(node, line);
	}

	/**
	 * Sets the identifier end column in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @param column
	 *            the column
	 */
	public void setIdentifierEndColumns(T node, Integer column) {
		identifierEndColumns.put(node, column);
	}

	/**
	 * Gets the start position in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @return the start position in the parsed text
	 */
	public Integer getStartPositions(T node) {
		return startPositions.get(node);
	}

	/**
	 * Gets the start line in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @return the start line in the parsed text
	 */
	public Integer getStartLines(T node) {
		return startLines.get(node);
	}

	/**
	 * Gets the start column in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @return the start column in the parsed text
	 */
	public Integer getStartColumns(T node) {
		return startColumns.get(node);
	}

	/**
	 * Gets the end position in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @return the end position in the parsed text
	 */
	public Integer getEndPositions(T node) {
		return endPositions.get(node);
	}

	/**
	 * Gets the end line in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @return the end line in the parsed text
	 */
	public Integer getEndLines(T node) {
		return endLines.get(node);
	}

	/**
	 * Gets the end column in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @return the end column in the parsed text
	 */
	public Integer getEndColumns(T node) {
		return endColumns.get(node);
	}

	/**
	 * Sets the start position in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @param position
	 *            the position
	 */
	public void setStartPositions(T node, Integer position) {
		startPositions.put(node, position);
	}

	/**
	 * Sets the start line in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @param line
	 *            the line
	 */
	public void setStartLines(T node, Integer line) {
		startLines.put(node, line);
	}

	/**
	 * Sets the start column in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @param column
	 *            the column
	 */
	public void setStartColumns(T node, Integer column) {
		startColumns.put(node, column);
	}

	/**
	 * Sets the end position in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @param position
	 *            the position
	 */
	public void setEndPositions(T node, Integer position) {
		endPositions.put(node, position);
	}

	/**
	 * Sets the end line in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @param line
	 *            the line
	 */
	public void setEndLines(T node, Integer line) {
		endLines.put(node, line);
	}

	/**
	 * Sets the end column in the parsed text for the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 * @param column
	 *            the column
	 */
	public void setEndColumns(T node, Integer column) {
		endColumns.put(node, column);
	}

	/**
	 * Removes the positions of the given {@link T}.
	 * 
	 * @param node
	 *            the {@link T}
	 */
	public void remove(T node) {
		identifierStartPositions.remove(node);
		identifierStartLines.remove(node);
		identifierStartColumns.remove(node);
		identifierEndPositions.remove(node);
		identifierEndLines.remove(node);
		identifierEndColumns.remove(node);

		startPositions.remove(node);
		startLines.remove(node);
		startColumns.remove(node);
		endPositions.remove(node);
		endLines.remove(node);
		endColumns.remove(node);
	}

	/**
	 * Adds all others {@link Positions} with the given offset shift.
	 * 
	 * @param others
	 *            the others {@link Positions}
	 * @param offsetPosition
	 *            the offset position
	 * @param offsetLine
	 *            the offset line
	 * @param offsetColumn
	 *            the offset column
	 */
	public void addAll(Positions<T> others, int offsetPosition, int offsetLine, int offsetColumn) {
		for (Entry<T, Integer> entry : others.identifierStartPositions.entrySet()) {
			identifierStartPositions.put(entry.getKey(), entry.getValue() + offsetPosition);
		}
		for (Entry<T, Integer> entry : others.identifierStartLines.entrySet()) {
			identifierStartLines.put(entry.getKey(), entry.getValue() + offsetLine);
		}
		for (Entry<T, Integer> entry : others.identifierStartColumns.entrySet()) {
			identifierStartColumns.put(entry.getKey(), entry.getValue() + offsetColumn);
		}
		for (Entry<T, Integer> entry : others.identifierEndPositions.entrySet()) {
			identifierEndPositions.put(entry.getKey(), entry.getValue() + offsetPosition);
		}
		for (Entry<T, Integer> entry : others.identifierEndLines.entrySet()) {
			identifierEndLines.put(entry.getKey(), entry.getValue() + offsetLine);
		}
		for (Entry<T, Integer> entry : others.identifierEndColumns.entrySet()) {
			identifierEndColumns.put(entry.getKey(), entry.getValue() + offsetColumn);
		}

		for (Entry<T, Integer> entry : others.startPositions.entrySet()) {
			startPositions.put(entry.getKey(), entry.getValue() + offsetPosition);
		}
		for (Entry<T, Integer> entry : others.startLines.entrySet()) {
			startLines.put(entry.getKey(), entry.getValue() + offsetLine);
		}
		for (Entry<T, Integer> entry : others.startColumns.entrySet()) {
			startColumns.put(entry.getKey(), entry.getValue() + offsetColumn);
		}
		for (Entry<T, Integer> entry : others.endPositions.entrySet()) {
			endPositions.put(entry.getKey(), entry.getValue() + offsetPosition);
		}
		for (Entry<T, Integer> entry : others.endLines.entrySet()) {
			endLines.put(entry.getKey(), entry.getValue() + offsetLine);
		}
		for (Entry<T, Integer> entry : others.endColumns.entrySet()) {
			endColumns.put(entry.getKey(), entry.getValue() + offsetColumn);
		}
	}

	/**
	 * Tells if the given position is on the range of the given node.
	 * 
	 * @param node
	 *            the node
	 * @param position
	 *            the position
	 * @return <code>true</code> if the given position is on the range of the given node, <code>false</code>
	 *         otherwise
	 */
	public boolean isInRange(T node, int position) {
		return isInRange(node, position, -1, -1);
	}

	/**
	 * Tells if the given line and column position is on the range of the given node.
	 * 
	 * @param node
	 *            the node
	 * @param line
	 *            the line
	 * @param column
	 *            the column
	 * @return <code>true</code> if the given line and column position is on the range of the given node,
	 *         <code>false</code> otherwise
	 */
	public boolean isInRange(T node, int line, int column) {
		return isInRange(node, -1, line, column);
	}

	/**
	 * Gets the node representing the narrowest range at the given position starting from the given node.
	 * 
	 * @param node
	 *            the root node
	 * @param position
	 *            the position
	 * @return the node representing the narrowest range at the given position starting from the given node if
	 *         any, <code>null</code> otherwise
	 */
	public T getNodeAt(T node, int position) {
		return getNodeAt(node, position, -1, -1);
	}

	/**
	 * Gets the node representing the narrowest range at the given line and column position starting from the
	 * given node.
	 * 
	 * @param node
	 *            the root node
	 * @param line
	 *            the line
	 * @param column
	 *            the column
	 * @return the node representing the narrowest range at the given line and column position starting from
	 *         the given node if any, <code>null</code> otherwise
	 */
	public T getNodeAt(T node, int line, int column) {
		return getNodeAt(node, -1, line, column);
	}

	/**
	 * Gets the node representing the narrowest range at the given position starting from the given node.
	 * 
	 * @param node
	 *            the root node
	 * @param position
	 *            the position
	 * @param line
	 *            the line
	 * @param column
	 *            the column
	 * @return the node representing the narrowest range at the given position starting from the given node if
	 *         any, <code>null</code> otherwise
	 */
	protected T getNodeAt(T node, int position, int line, int column) {
		T res = null;

		if (!isInRange(node, position, line, column)) {
			throw new IllegalArgumentException("the root node can't contain the given position.");
		}
		for (EObject child : node.eContents()) {
			if (getStartPositions((T)child) != null && getEndPositions((T)child) != null) {
				// When parsing an [elseif clause of an IfStatement, the AST constructed is instead a
				// "virtual" [else] clause with an IfStatement inside.
				// This "virtual" clause has no actual positions in the source text, so we want to ignore
				// these candidates.
				if (isInRange((T)child, position, line, column)) {
					res = getNodeAt((T)child, position, line, column);
					break;
				}
			}
		}
		if (res == null) {
			res = node;
		}

		return res;
	}

	/**
	 * Tells if the given position is on the range of the given node.
	 * 
	 * @param node
	 *            the node
	 * @param position
	 *            the position
	 * @param line
	 *            the line
	 * @param column
	 *            the column
	 * @return <code>true</code> if the given position is on the range of the given node, <code>false</code>
	 *         otherwise
	 */
	protected boolean isInRange(T node, int position, int line, int column) {
		return compareStart(node, position, line, column) <= 0 && compareEnd(node, position, line,
				column) >= 0;
	}

	/**
	 * Compares the given position with the start position of the given node.
	 * 
	 * @param node
	 *            the node
	 * @param position
	 *            the position
	 * @param line
	 *            the line
	 * @param column
	 *            the column
	 * @return
	 *         <p>
	 *         <ul>
	 *         <li>less than 0 if the position is after the start</li>
	 *         <li>0 if the position is at the start</li>
	 *         <li>more than 0 if the position is after the start</li>
	 *         <ul>
	 *         <p>
	 */
	protected int compareStart(T node, int position, int line, int column) {
		final int res;

		if (position != -1) {
			res = getStartPositions(node) - position;
		} else if (line != -1) {
			final int lineDelta = getStartLines(node) - line;
			if (lineDelta == 0) {
				if (column != -1) {
					res = getStartColumns(node) - column;
				} else {
					res = lineDelta;
				}
			} else {
				res = lineDelta;
			}
		} else {
			throw new IllegalArgumentException("at least one of position or line must be different from -1.");
		}

		return res;
	}

	/**
	 * Compares the given position with the end position of the given node.
	 * 
	 * @param node
	 *            the node
	 * @param position
	 *            the position
	 * @param line
	 *            the line
	 * @param column
	 *            the column
	 * @return
	 *         <p>
	 *         <ul>
	 *         <li>less than 0 if the position is after the end</li>
	 *         <li>0 if the position is at the end</li>
	 *         <li>more than 0 if the position is after the end</li>
	 *         <ul>
	 *         <p>
	 */
	protected int compareEnd(T node, int position, int line, int column) {
		final int res;

		if (position != -1) {
			res = getEndPositions(node) - position;
		} else if (line != -1) {
			final int lineDelta = getEndLines(node) - line;
			if (lineDelta == 0) {
				if (column != -1) {
					res = getEndColumns(node) - column;
				} else {
					res = lineDelta;
				}
			} else {
				res = lineDelta;
			}
		} else {
			throw new IllegalArgumentException("at least one of position or line must be different from -1.");
		}

		return res;
	}

}
