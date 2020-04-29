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
package org.eclipse.acceleo.aql.parser;

import java.util.List;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.Error;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.parser.Positions;
import org.eclipse.emf.ecore.EObject;

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
	private Positions positions;

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
	public AcceleoAstResult(Module module, Positions positions, List<Error> errors) {
		this.module = module;
		this.positions = positions;
		this.errors = errors;
	}

	/**
	 * Gets the start position of the given {@link ASTNode} in the parsed text.
	 * 
	 * @param node
	 *            the {@link ASTNode}
	 * @return the start position of the given {@link ASTNode} in the parsed text if any, <code>-1</code>
	 *         otherwise
	 */
	public int getStartPosition(ASTNode node) {
		return getInternalStartPosition(node);
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
	 * Gets the start position of the given {@link EObject} in the parsed text.
	 * 
	 * @param object
	 *            the {@link EObject}
	 * @return the start position of the given {@link EObject} in the parsed text if any, <code>-1</code>
	 *         otherwise
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
	 * Gets the start line of the given {@link ASTNode} in the parsed text.
	 * 
	 * @param node
	 *            the {@link ASTNode}
	 * @return the start line of the given {@link ASTNode} in the parsed text if any, <code>-1</code>
	 *         otherwise
	 */
	public int getStartLine(ASTNode node) {
		return getInternalStartLine(node);
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
	 * Gets the start line of the given {@link EObject} in the parsed text.
	 * 
	 * @param object
	 *            the {@link EObject}
	 * @return the start line of the given {@link EObject} in the parsed text if any, <code>-1</code>
	 *         otherwise
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
	 * Gets the start column of the given {@link ASTNode} in the parsed text.
	 * 
	 * @param node
	 *            the {@link ASTNode}
	 * @return the start column of the given {@link ASTNode} in the parsed text if any, <code>-1</code>
	 *         otherwise
	 */
	public int getStartColumn(ASTNode node) {
		return getInternalStartColumn(node);
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
	 * Gets the start column of the given {@link EObject} in the parsed text.
	 * 
	 * @param object
	 *            the {@link EObject}
	 * @return the start column of the given {@link EObject} in the parsed text if any, <code>-1</code>
	 *         otherwise
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
	 * Gets the end position of the given {@link ASTNode} in the parsed text.
	 * 
	 * @param node
	 *            the {@link ASTNode}
	 * @return the end position of the given {@link ASTNode} in the parsed text if any, <code>-1</code>
	 *         otherwise
	 */
	public int getEndPosition(ASTNode node) {
		return getInternalEndPosition(node);
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
	 * Gets the end position of the given {@link EObject} in the parsed text.
	 * 
	 * @param object
	 *            the {@link EObject}
	 * @return the end position of the given {@link EObject} in the parsed text if any, <code>-1</code>
	 *         otherwise
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
	 * Gets the end line of the given {@link ASTNode} in the parsed text.
	 * 
	 * @param node
	 *            the {@link ASTNode}
	 * @return the end line of the given {@link ASTNode} in the parsed text if any, <code>-1</code> otherwise
	 */
	public int getEndLine(ASTNode node) {
		return getInternalEndLine(node);
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
	 * Gets the end line of the given {@link EObject} in the parsed text.
	 * 
	 * @param object
	 *            the {@link EObject}
	 * @return the end line of the given {@link EObject} in the parsed text if any, <code>-1</code> otherwise
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
	 * Gets the end column of the given {@link ASTNode} in the parsed text.
	 * 
	 * @param node
	 *            the {@link ASTNode}
	 * @return the end column of the given {@link ASTNode} in the parsed text if any, <code>-1</code>
	 *         otherwise
	 */
	public int getEndColumn(ASTNode node) {
		return getInternalEndColumn(node);
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
	 * Gets the end column of the given {@link EObject} in the parsed text.
	 * 
	 * @param object
	 *            the {@link EObject}
	 * @return the end column of the given {@link EObject} in the parsed text if any, <code>-1</code>
	 *         otherwise
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
	 * Gets the ast node at the given position.
	 * 
	 * @param position
	 *            the position
	 * @return the ast node at the given position if any, <code>null</code> otherwise
	 */
	public EObject getAstNode(int position) {
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
	public EObject getAstNode(int line, int column) {
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
