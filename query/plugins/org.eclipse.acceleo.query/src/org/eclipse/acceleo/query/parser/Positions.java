/*******************************************************************************
 * Copyright (c) 2020, 2021 Obeo.
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
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EObject;

/**
 * Keep track of positions.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class Positions {

	/**
	 * Mapping from an {@link EObject} to its identifier start position in the parsed text.
	 */
	private final Map<EObject, Integer> identifierStartPositions = new HashMap<EObject, Integer>();

	/**
	 * Mapping from an {@link EObject} to its identifier start line in the parsed text.
	 */
	private final Map<EObject, Integer> identifierStartLines = new HashMap<EObject, Integer>();

	/**
	 * Mapping from an {@link EObject} to its identifier start column in the parsed text.
	 */
	private final Map<EObject, Integer> identifierStartColumns = new HashMap<EObject, Integer>();

	/**
	 * Mapping from an {@link EObject} to its identifier end position in the parsed text.
	 */
	private final Map<EObject, Integer> identifierEndPositions = new HashMap<EObject, Integer>();

	/**
	 * Mapping from an {@link EObject} to its identifier end line in the parsed text.
	 */
	private final Map<EObject, Integer> identifierEndLines = new HashMap<EObject, Integer>();

	/**
	 * Mapping from an {@link EObject} to its identifier end column in the parsed text.
	 */
	private final Map<EObject, Integer> identifierEndColumns = new HashMap<EObject, Integer>();

	/**
	 * Mapping from an {@link EObject} to its start position in the parsed text.
	 */
	private final Map<EObject, Integer> startPositions = new HashMap<EObject, Integer>();

	/**
	 * Mapping from an {@link EObject} to its start line in the parsed text.
	 */
	private final Map<EObject, Integer> startLines = new HashMap<EObject, Integer>();

	/**
	 * Mapping from an {@link EObject} to its start column in the parsed text.
	 */
	private final Map<EObject, Integer> startColumns = new HashMap<EObject, Integer>();

	/**
	 * Mapping from an {@link EObject} to its end position in the parsed text.
	 */
	private final Map<EObject, Integer> endPositions = new HashMap<EObject, Integer>();

	/**
	 * Mapping from an {@link EObject} to its end line in the parsed text.
	 */
	private final Map<EObject, Integer> endLines = new HashMap<EObject, Integer>();

	/**
	 * Mapping from an {@link EObject} to its end column in the parsed text.
	 */
	private final Map<EObject, Integer> endColumns = new HashMap<EObject, Integer>();

	/**
	 * Gets the identifier start position in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @return the identifier start position in the parsed text
	 */
	public Integer getIdentifierStartPositions(EObject node) {
		return identifierStartPositions.get(node);
	}

	/**
	 * Gets the identifier start line in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @return the identifier start line in the parsed text
	 */
	public Integer getIdentifierStartLines(EObject node) {
		return identifierStartLines.get(node);
	}

	/**
	 * Gets the identifier start column in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @return the identifier start column in the parsed text
	 */
	public Integer getIdentifierStartColumns(EObject node) {
		return startColumns.get(node);
	}

	/**
	 * Gets the identifier end position in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @return the identifier end position in the parsed text
	 */
	public Integer getIdentifierEndPositions(EObject node) {
		return identifierEndPositions.get(node);
	}

	/**
	 * Gets the end line in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @return the identifier end line in the parsed text
	 */
	public Integer getIdentifierEndLines(EObject node) {
		return identifierEndLines.get(node);
	}

	/**
	 * Gets the identifier end column in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @return the identifier end column in the parsed text
	 */
	public Integer getIdentifierEndColumns(EObject node) {
		return identifierEndColumns.get(node);
	}

	/**
	 * Sets the identifier start position in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @param position
	 *            the position
	 */
	public void setIdentifierStartPositions(EObject node, Integer position) {
		identifierStartPositions.put(node, position);
	}

	/**
	 * Sets the identifier start line in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @param line
	 *            the line
	 */
	public void setIdentifierStartLines(EObject node, Integer line) {
		identifierStartLines.put(node, line);
	}

	/**
	 * Sets the identifier start column in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @param column
	 *            the column
	 */
	public void setIdentifierStartColumns(EObject node, Integer column) {
		identifierStartColumns.put(node, column);
	}

	/**
	 * Sets the identifier end position in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @param position
	 *            the position
	 */
	public void setIdentifierEndPositions(EObject node, Integer position) {
		identifierEndPositions.put(node, position);
	}

	/**
	 * Sets the identifier end line in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @param line
	 *            the line
	 */
	public void setIdentifierEndLines(EObject node, Integer line) {
		identifierEndLines.put(node, line);
	}

	/**
	 * Sets the identifier end column in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @param column
	 *            the column
	 */
	public void setIdentifierEndColumns(EObject node, Integer column) {
		identifierEndColumns.put(node, column);
	}

	/**
	 * Gets the start position in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @return the start position in the parsed text
	 */
	public Integer getStartPositions(EObject node) {
		return startPositions.get(node);
	}

	/**
	 * Gets the start line in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @return the start line in the parsed text
	 */
	public Integer getStartLines(EObject node) {
		return startLines.get(node);
	}

	/**
	 * Gets the start column in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @return the start column in the parsed text
	 */
	public Integer getStartColumns(EObject node) {
		return startColumns.get(node);
	}

	/**
	 * Gets the end position in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @return the end position in the parsed text
	 */
	public Integer getEndPositions(EObject node) {
		return endPositions.get(node);
	}

	/**
	 * Gets the end line in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @return the end line in the parsed text
	 */
	public Integer getEndLines(EObject node) {
		return endLines.get(node);
	}

	/**
	 * Gets the end column in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @return the end column in the parsed text
	 */
	public Integer getEndColumns(EObject node) {
		return endColumns.get(node);
	}

	/**
	 * Sets the start position in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @param position
	 *            the position
	 */
	public void setStartPositions(EObject node, Integer position) {
		startPositions.put(node, position);
	}

	/**
	 * Sets the start line in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @param line
	 *            the line
	 */
	public void setStartLines(EObject node, Integer line) {
		startLines.put(node, line);
	}

	/**
	 * Sets the start column in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @param column
	 *            the column
	 */
	public void setStartColumns(EObject node, Integer column) {
		startColumns.put(node, column);
	}

	/**
	 * Sets the end position in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @param position
	 *            the position
	 */
	public void setEndPositions(EObject node, Integer position) {
		endPositions.put(node, position);
	}

	/**
	 * Sets the end line in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @param line
	 *            the line
	 */
	public void setEndLines(EObject node, Integer line) {
		endLines.put(node, line);
	}

	/**
	 * Sets the end column in the parsed text for the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 * @param column
	 *            the column
	 */
	public void setEndColumns(EObject node, Integer column) {
		endColumns.put(node, column);
	}

	/**
	 * Removes the positions of the given {@link EObject}.
	 * 
	 * @param node
	 *            the {@link EObject}
	 */
	public void remove(EObject node) {
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
	public void addAll(Positions others, int offsetPosition, int offsetLine, int offsetColumn) {
		for (Entry<EObject, Integer> entry : others.identifierStartPositions.entrySet()) {
			identifierStartPositions.put(entry.getKey(), entry.getValue() + offsetPosition);
		}
		for (Entry<EObject, Integer> entry : others.identifierStartLines.entrySet()) {
			identifierStartLines.put(entry.getKey(), entry.getValue() + offsetLine);
		}
		for (Entry<EObject, Integer> entry : others.identifierStartColumns.entrySet()) {
			identifierStartColumns.put(entry.getKey(), entry.getValue() + offsetColumn);
		}
		for (Entry<EObject, Integer> entry : others.identifierEndPositions.entrySet()) {
			identifierEndPositions.put(entry.getKey(), entry.getValue() + offsetPosition);
		}
		for (Entry<EObject, Integer> entry : others.identifierEndLines.entrySet()) {
			identifierEndLines.put(entry.getKey(), entry.getValue() + offsetLine);
		}
		for (Entry<EObject, Integer> entry : others.identifierEndColumns.entrySet()) {
			identifierEndColumns.put(entry.getKey(), entry.getValue() + offsetColumn);
		}

		for (Entry<EObject, Integer> entry : others.startPositions.entrySet()) {
			startPositions.put(entry.getKey(), entry.getValue() + offsetPosition);
		}
		for (Entry<EObject, Integer> entry : others.startLines.entrySet()) {
			startLines.put(entry.getKey(), entry.getValue() + offsetLine);
		}
		for (Entry<EObject, Integer> entry : others.startColumns.entrySet()) {
			startColumns.put(entry.getKey(), entry.getValue() + offsetColumn);
		}
		for (Entry<EObject, Integer> entry : others.endPositions.entrySet()) {
			endPositions.put(entry.getKey(), entry.getValue() + offsetPosition);
		}
		for (Entry<EObject, Integer> entry : others.endLines.entrySet()) {
			endLines.put(entry.getKey(), entry.getValue() + offsetLine);
		}
		for (Entry<EObject, Integer> entry : others.endColumns.entrySet()) {
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
	public boolean isInRange(EObject node, int position) {
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
	public boolean isInRange(EObject node, int line, int column) {
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
	public EObject getNodeAt(EObject node, int position) {
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
	public EObject getNodeAt(EObject node, int line, int column) {
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
	protected EObject getNodeAt(EObject node, int position, int line, int column) {
		EObject res = null;

		if (!isInRange(node, position, line, column)) {
			throw new IllegalArgumentException("the root node can't contain the given position.");
		}
		for (EObject child : node.eContents()) {
			if (getStartPositions(child) != null && getEndPositions(child) != null) {
				// When parsing an [elseif clause of an IfStatement, the AST constructed is instead a
				// "virtual" [else] clause with an IfStatement inside.
				// This "virtual" clause has no actual positions in the source text, so we want to ignore
				// these candidates.
				if (isInRange(child, position, line, column)) {
					res = getNodeAt(child, position, line, column);
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
	protected boolean isInRange(EObject node, int position, int line, int column) {
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
	protected int compareStart(EObject node, int position, int line, int column) {
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
	protected int compareEnd(EObject node, int position, int line, int column) {
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
