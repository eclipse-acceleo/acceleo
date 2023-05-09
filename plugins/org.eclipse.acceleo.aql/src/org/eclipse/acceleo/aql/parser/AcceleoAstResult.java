/*******************************************************************************
 * Copyright (c) 2017, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.parser;

import java.util.List;

import org.eclipse.acceleo.Error;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.query.ast.ASTNode;
import org.eclipse.acceleo.query.parser.Positions;

/**
 * AcceleoAST result.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoAstResult {

	/**
	 * The {@link AcceleoParser#parse(String) parsed} {@link Module}.
	 */
	private final Module module;

	/**
	 * {@link Positions}.
	 */
	private final Positions<ASTNode> positions;

	/**
	 * The {@link List} of {@link Error}.
	 */
	private final List<Error> errors;

	/**
	 * Constructor.
	 * 
	 * @param module
	 *            the {@link AcceleoParser#parse(String) parsed} {@link Module}
	 * @param positions
	 *            the {@link Positions}
	 * @param errors
	 *            the {@link List} of {@link Error}
	 */
	public AcceleoAstResult(Module module, Positions<ASTNode> positions, List<Error> errors) {
		this.module = module;
		this.module.setAst(this);
		this.positions = positions;
		this.errors = errors;
	}

	/**
	 * Gets the identifier start position of the given {@link ASTNode} in the parsed text.
	 * 
	 * @param binding
	 *            the {@link ASTNode}
	 * @return the identifier start position of the given {@link ASTNode} in the parsed text if any,
	 *         <code>-1</code> otherwise
	 */
	public int getIdentifierStartPosition(ASTNode astNode) {
		final int res;

		final Integer position = positions.getIdentifierStartPositions(astNode);
		if (position != null) {
			res = position.intValue();
		} else {
			res = -1;
		}

		return res;
	}

	/**
	 * Gets the identifier start line of the given {@link ASTNode} in the parsed text.
	 * 
	 * @param binding
	 *            the {@link ASTNode}
	 * @return the identifier start line of the given {@link ASTNode} in the parsed text if any,
	 *         <code>-1</code> otherwise
	 */
	public int getIdentifierStartLine(ASTNode astNode) {
		final int res;

		final Integer line = positions.getIdentifierStartLines(astNode);
		if (line != null) {
			res = line.intValue();
		} else {
			res = -1;
		}

		return res;
	}

	/**
	 * Gets the identifier start column of the given {@link ASTNode} in the parsed text.
	 * 
	 * @param binding
	 *            the {@link ASTNode}
	 * @return the identifier start column of the given {@link ASTNode} in the parsed text if any,
	 *         <code>-1</code> otherwise
	 */
	public int getIdentifierStartColumn(ASTNode astNode) {
		final int res;

		final Integer column = positions.getIdentifierStartColumns(astNode);
		if (column != null) {
			res = column.intValue();
		} else {
			res = -1;
		}

		return res;
	}

	/**
	 * Gets the identifier end position of the given {@link ASTNode} in the parsed text.
	 * 
	 * @param binding
	 *            the {@link ASTNode}
	 * @return the identifier end position of the given {@link ASTNode} in the parsed text if any,
	 *         <code>-1</code> otherwise
	 */
	public int getIdentifierEndPosition(ASTNode astNode) {
		final int res;

		final Integer position = positions.getIdentifierEndPositions(astNode);
		if (position != null) {
			res = position.intValue();
		} else {
			res = -1;
		}

		return res;
	}

	/**
	 * Gets the identifier end line of the given {@link ASTNode} in the parsed text.
	 * 
	 * @param astNode
	 *            the {@link ASTNode}
	 * @return the identifier end line of the given {@link ASTNode} in the parsed text if any, <code>-1</code>
	 *         otherwise
	 */
	public int getIdentifierEndLine(ASTNode astNode) {
		final int res;

		final Integer line = positions.getIdentifierEndLines(astNode);
		if (line != null) {
			res = line.intValue();
		} else {
			res = -1;
		}

		return res;
	}

	/**
	 * Gets the identifier end column of the given {@link ASTNode} in the parsed text.
	 * 
	 * @param astNode
	 *            the {@link ASTNode}
	 * @return the identifier end column of the given {@link ASTNode} in the parsed text if any,
	 *         <code>-1</code> otherwise
	 */
	public int getIdentifierEndColumn(ASTNode astNode) {
		final int res;

		final Integer column = positions.getIdentifierEndColumns(astNode);
		if (column != null) {
			res = column.intValue();
		} else {
			res = -1;
		}

		return res;
	}

	/**
	 * Gets the start position of the given {@link ASTNode} in the parsed text.
	 * 
	 * @param astNode
	 *            the {@link ASTNode}
	 * @return the start position of the given {@link ASTNode} in the parsed text if any, <code>-1</code>
	 *         otherwise
	 */
	public int getStartPosition(ASTNode astNode) {
		final int res;

		final Integer position = positions.getStartPositions(astNode);
		if (position != null) {
			res = position.intValue();
		} else {
			res = -1;
		}

		return res;
	}

	/**
	 * Gets the start line of the given {@link ASTNode} in the parsed text.
	 * 
	 * @param astNode
	 *            the {@link ASTNode}
	 * @return the start line of the given {@link ASTNode} in the parsed text if any, <code>-1</code>
	 *         otherwise
	 */
	public int getStartLine(ASTNode astNode) {
		final int res;

		final Integer line = positions.getStartLines(astNode);
		if (line != null) {
			res = line.intValue();
		} else {
			res = -1;
		}

		return res;
	}

	/**
	 * Gets the start column of the given {@link ASTNode} in the parsed text.
	 * 
	 * @param astNode
	 *            the {@link ASTNode}
	 * @return the start column of the given {@link ASTNode} in the parsed text if any, <code>-1</code>
	 *         otherwise
	 */
	public int getStartColumn(ASTNode astNode) {
		final int res;

		final Integer column = positions.getStartColumns(astNode);
		if (column != null) {
			res = column.intValue();
		} else {
			res = -1;
		}

		return res;
	}

	/**
	 * Gets the end position of the given {@link ASTNode} in the parsed text.
	 * 
	 * @param astNode
	 *            the {@link ASTNode}
	 * @return the end position of the given {@link ASTNode} in the parsed text if any, <code>-1</code>
	 *         otherwise
	 */
	public int getEndPosition(ASTNode astNode) {
		final int res;

		final Integer position = positions.getEndPositions(astNode);
		if (position != null) {
			res = position.intValue();
		} else {
			res = -1;
		}

		return res;
	}

	/**
	 * Gets the end line of the given {@link ASTNode} in the parsed text.
	 * 
	 * @param astNode
	 *            the {@link ASTNode}
	 * @return the end line of the given {@link ASTNode} in the parsed text if any, <code>-1</code> otherwise
	 */
	public int getEndLine(ASTNode astNode) {
		final int res;

		final Integer line = positions.getEndLines(astNode);
		if (line != null) {
			res = line.intValue();
		} else {
			res = -1;
		}

		return res;
	}

	/**
	 * Gets the end column of the given {@link ASTNode} in the parsed text.
	 * 
	 * @param astNode
	 *            the {@link ASTNode}
	 * @return the end column of the given {@link ASTNode} in the parsed text if any, <code>-1</code>
	 *         otherwise
	 */
	public int getEndColumn(ASTNode astNode) {
		final int res;

		final Integer column = positions.getEndColumns(astNode);
		if (column != null) {
			res = column.intValue();
		} else {
			res = -1;
		}

		return res;
	}

	/**
	 * Gets the ast node at the given position.
	 * 
	 * @param position
	 *            the position
	 * @return the ast node at the given position if any, <code>null</code> otherwise
	 */
	public ASTNode getAstNode(int position) {
		return positions.getNodeAt(module, position);
	}

	/**
	 * Gets the ast node at the given line and column.
	 * 
	 * @param line
	 *            the start line
	 * @param column
	 *            the start column
	 * @return the ast node at the given line and column if nay, <code>null</code> otherwise
	 */
	public ASTNode getAstNode(int line, int column) {
		return positions.getNodeAt(module, line, column);
	}

	/**
	 * Gets the {@link AcceleoParser#parse(String) parsed} {@link Module}.
	 * 
	 * @return the {@link AcceleoParser#parse(String) parsed} {@link Module}
	 */
	public Module getModule() {
		return module;
	}

	/**
	 * Gets the {@link List} of {@link Error}.
	 * 
	 * @return the {@link List} of {@link Error}
	 */
	public List<Error> getErrors() {
		return errors;
	}

}
