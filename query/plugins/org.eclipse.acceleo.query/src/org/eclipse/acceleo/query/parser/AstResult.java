/*******************************************************************************
 * Copyright (c) 2015 Obeo.
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

import org.eclipse.acceleo.query.ast.Error;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;

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
	private final Positions positions;

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
	public AstResult(Expression ast, Positions positions, List<Error> errors, Diagnostic diagnostic) {
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
	 * Gets the start position of the given {@link Expression} in the parsed text.
	 * 
	 * @param expression
	 *            the {@link Expression}
	 * @return the start position of the given {@link Expression} in the parsed text if any, <code>-1</code>
	 *         otherwise
	 */
	public int getStartPosition(Expression expression) {
		return getInternalStartPosition(expression);
	}

	/**
	 * Gets the start position of the given {@link VariableDeclaration} in the parsed text.
	 * 
	 * @param declaration
	 *            the {@link VariableDeclaration}
	 * @return the start position of the given {@link VariableDeclaration} in the parsed text if any,
	 *         <code>-1</code> otherwise
	 */
	public int getStartPosition(VariableDeclaration declaration) {
		return getInternalStartPosition(declaration);
	}

	/**
	 * Gets the start position of the given {@link Expression} or a {@link VariableDeclaration} in the parsed
	 * text.
	 * 
	 * @param object
	 *            the {@link Expression} or a {@link VariableDeclaration}
	 * @return the start position of the given {@link Expression} or a {@link VariableDeclaration} in the
	 *         parsed text if any, <code>-1</code> otherwise
	 */
	private int getInternalStartPosition(EObject object) {
		final int res;

		final Integer position = positions.getStartPositions(object);
		if (position != null) {
			res = position.intValue();
		} else {
			res = -1;
		}

		return res;
	}

	/**
	 * Gets the start line of the given {@link Expression} in the parsed text.
	 * 
	 * @param expression
	 *            the {@link Expression}
	 * @return the start line of the given {@link Expression} in the parsed text if any, <code>-1</code>
	 *         otherwise
	 */
	public int getStartLine(Expression expression) {
		return getInternalStartLine(expression);
	}

	/**
	 * Gets the start line of the given {@link VariableDeclaration} in the parsed text.
	 * 
	 * @param declaration
	 *            the {@link VariableDeclaration}
	 * @return the start line of the given {@link VariableDeclaration} in the parsed text if any,
	 *         <code>-1</code> otherwise
	 */
	public int getStartLine(VariableDeclaration declaration) {
		return getInternalStartLine(declaration);
	}

	/**
	 * Gets the start line of the given {@link Expression} or a {@link VariableDeclaration} in the parsed
	 * text.
	 * 
	 * @param object
	 *            the {@link Expression} or a {@link VariableDeclaration}
	 * @return the start line of the given {@link Expression} or a {@link VariableDeclaration} in the parsed
	 *         text if any, <code>-1</code> otherwise
	 */
	private int getInternalStartLine(EObject object) {
		final int res;

		final Integer line = positions.getStartLines(object);
		if (line != null) {
			res = line.intValue();
		} else {
			res = -1;
		}

		return res;
	}

	/**
	 * Gets the start column of the given {@link Expression} in the parsed text.
	 * 
	 * @param expression
	 *            the {@link Expression}
	 * @return the start column of the given {@link Expression} in the parsed text if any, <code>-1</code>
	 *         otherwise
	 */
	public int getStartColumn(Expression expression) {
		return getInternalStartColumn(expression);
	}

	/**
	 * Gets the start column of the given {@link VariableDeclaration} in the parsed text.
	 * 
	 * @param declaration
	 *            the {@link VariableDeclaration}
	 * @return the start column of the given {@link VariableDeclaration} in the parsed text if any,
	 *         <code>-1</code> otherwise
	 */
	public int getStartColumn(VariableDeclaration declaration) {
		return getInternalStartColumn(declaration);
	}

	/**
	 * Gets the start column of the given {@link Expression} or a {@link VariableDeclaration} in the parsed
	 * text.
	 * 
	 * @param object
	 *            the {@link Expression} or a {@link VariableDeclaration}
	 * @return the start column of the given {@link Expression} or a {@link VariableDeclaration} in the parsed
	 *         text if any, <code>-1</code> otherwise
	 */
	private int getInternalStartColumn(EObject object) {
		final int res;

		final Integer column = positions.getStartColumns(object);
		if (column != null) {
			res = column.intValue();
		} else {
			res = -1;
		}

		return res;
	}

	/**
	 * Gets the end position of the given {@link Expression} in the parsed text.
	 * 
	 * @param expression
	 *            the {@link Expression}
	 * @return the end position of the given {@link Expression} in the parsed text if any, <code>-1</code>
	 *         otherwise
	 */
	public int getEndPosition(Expression expression) {
		return getInternalEndPosition(expression);
	}

	/**
	 * Gets the end position of the given {@link VariableDeclaration} in the parsed text.
	 * 
	 * @param declaration
	 *            the {@link VariableDeclaration}
	 * @return the end position of the given {@link VariableDeclaration} in the parsed text if any,
	 *         <code>-1</code> otherwise
	 */
	public int getEndPosition(VariableDeclaration declaration) {
		return getInternalEndPosition(declaration);
	}

	/**
	 * Gets the end position of the given {@link Expression} or a {@link VariableDeclaration} in the parsed
	 * text.
	 * 
	 * @param object
	 *            the {@link Expression} or a {@link VariableDeclaration}
	 * @return the end position of the given {@link Expression} or a {@link VariableDeclaration} in the parsed
	 *         text if any, <code>-1</code> otherwise
	 */
	private int getInternalEndPosition(EObject object) {
		final int res;

		final Integer position = positions.getEndPositions(object);
		if (position != null) {
			res = position.intValue();
		} else {
			res = -1;
		}

		return res;
	}

	/**
	 * Gets the end line of the given {@link Expression} in the parsed text.
	 * 
	 * @param expression
	 *            the {@link Expression}
	 * @return the end line of the given {@link Expression} in the parsed text if any, <code>-1</code>
	 *         otherwise
	 */
	public int getEndLine(Expression expression) {
		return getInternalEndLine(expression);
	}

	/**
	 * Gets the end line of the given {@link VariableDeclaration} in the parsed text.
	 * 
	 * @param declaration
	 *            the {@link VariableDeclaration}
	 * @return the end line of the given {@link VariableDeclaration} in the parsed text if any,
	 *         <code>-1</code> otherwise
	 */
	public int getEndLine(VariableDeclaration declaration) {
		return getInternalEndLine(declaration);
	}

	/**
	 * Gets the end line of the given {@link Expression} or a {@link VariableDeclaration} in the parsed text.
	 * 
	 * @param object
	 *            the {@link Expression} or a {@link VariableDeclaration}
	 * @return the end line of the given {@link Expression} or a {@link VariableDeclaration} in the parsed
	 *         text if any, <code>-1</code> otherwise
	 */
	private int getInternalEndLine(EObject object) {
		final int res;

		final Integer line = positions.getEndLines(object);
		if (line != null) {
			res = line.intValue();
		} else {
			res = -1;
		}

		return res;
	}

	/**
	 * Gets the end column of the given {@link Expression} in the parsed text.
	 * 
	 * @param expression
	 *            the {@link Expression}
	 * @return the end column of the given {@link Expression} in the parsed text if any, <code>-1</code>
	 *         otherwise
	 */
	public int getEndColumn(Expression expression) {
		return getInternalEndColumn(expression);
	}

	/**
	 * Gets the end column of the given {@link VariableDeclaration} in the parsed text.
	 * 
	 * @param declaration
	 *            the {@link VariableDeclaration}
	 * @return the end column of the given {@link VariableDeclaration} in the parsed text if any,
	 *         <code>-1</code> otherwise
	 */
	public int getEndColumn(VariableDeclaration declaration) {
		return getInternalEndColumn(declaration);
	}

	/**
	 * Gets the end column of the given {@link Expression} or a {@link VariableDeclaration} in the parsed
	 * text.
	 * 
	 * @param object
	 *            the {@link Expression} or a {@link VariableDeclaration}
	 * @return the end column of the given {@link Expression} or a {@link VariableDeclaration} in the parsed
	 *         text if any, <code>-1</code> otherwise
	 */
	private int getInternalEndColumn(EObject object) {
		final int res;

		final Integer column = positions.getEndColumns(object);
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
	public void addAllPositonsTo(Positions pos, int offsetPosition, int offsetLine, int offsetColumn) {
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
	public EObject getAstNode(int position) {
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
	public EObject getAstNode(int line, int column) {
		return positions.getNodeAt(ast, line, column);
	}

}
