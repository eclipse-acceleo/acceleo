/*******************************************************************************
 * Copyright (c) 2015, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.parser;

import java.util.List;

import org.eclipse.acceleo.query.ast.ASTNode;
import org.eclipse.acceleo.query.ast.Error;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.emf.common.util.Diagnostic;

/**
 * Representation of an ast built.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AstResult {

	/**
	 * The built {@link Expression}.
	 */
	private final Expression ast;

	/**
	 * The {@link List} of {@link Error}.
	 */
	private final List<Error> errors;

	/**
	 * The positions of parsed elements.
	 */
	private final Positions<ASTNode> positions;

	/**
	 * The {@link Diagnostic} of the parsing.
	 */
	private Diagnostic diagnostic;

	/**
	 * Constructor.
	 * 
	 * @param ast
	 *            the built {@link Expression}
	 * @param positions
	 *            the {@link Positions} of parsed elements
	 * @param errors
	 *            the {@link List} of {@link Error}
	 * @param diagnostic
	 *            the {@link Diagnostic} of the parsing
	 */
	public AstResult(Expression ast, Positions<ASTNode> positions, List<Error> errors,
			Diagnostic diagnostic) {
		this.ast = ast;
		this.positions = positions;
		this.errors = errors;
		this.diagnostic = diagnostic;
	}

	/**
	 * Gets the built {@link Expression}.
	 * 
	 * @return the built {@link Expression}
	 */
	public Expression getAst() {
		return ast;
	}

	/**
	 * Gets the {@link List} of {@link Error}.
	 * 
	 * @return the {@link List} of {@link Error}
	 */
	public List<Error> getErrors() {
		return errors;
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
	 * Gets the identifier start position of the given {@link ASTNode} in the parsed text.
	 * 
	 * @param astNode
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
	 * @param astNode
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
	 * @param astNode
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
	 * @param astNode
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
	 * Add all the ast positions to the given {@link Positions} shifted by the given offset.
	 * 
	 * @param pos
	 *            the {@link Positions}
	 * @param offsetPosition
	 *            the offset position
	 * @param offsetLine
	 *            the offset line
	 * @param offsetColumn
	 *            the offset column
	 */
	public void addAllPositonsTo(Positions<ASTNode> pos, int offsetPosition, int offsetLine,
			int offsetColumn) {
		pos.addAll(this.positions, offsetPosition, offsetLine, offsetColumn);
	}

	/**
	 * Gets the {@link Diagnostic} of the parsing.
	 * 
	 * @return the {@link Diagnostic} of the parsing
	 */
	public Diagnostic getDiagnostic() {
		return diagnostic;
	}

	/**
	 * Creates an {@link AstResult} for the given {@link Expression sub AST}.
	 * 
	 * @param subAst
	 *            the sub part of {@link Expression AST}
	 * @return a new {@link AstResult} for the given {@link Expression sub AST}
	 * @since 4.1
	 */
	public AstResult subResult(Expression subAst) {
		return new AstResult(subAst, positions, errors, diagnostic);
	}

	/**
	 * Gets the ast node at the given position.
	 * 
	 * @param position
	 *            the position
	 * @return the ast node at the given position if any, <code>null</code> otherwise
	 */
	public ASTNode getAstNode(int position) {
		return positions.getNodeAt(ast, position);
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
		return positions.getNodeAt(ast, line, column);
	}

}
