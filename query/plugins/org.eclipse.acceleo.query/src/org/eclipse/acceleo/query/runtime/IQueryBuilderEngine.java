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
package org.eclipse.acceleo.query.runtime;

import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.query.ast.Error;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.VariableDeclaration;

/**
 * / Evaluation Engine is used to evaluate acceleo expressions. The evaluation engine allows to register
 * packages of services that can be called.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public interface IQueryBuilderEngine extends IQueryEngine {

	/**
	 * Representation of an ast built.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	public static class AstResult {

		/**
		 * The built {@link Expression}.
		 */
		private final Expression ast;

		/**
		 * The {@link List} of {@link Error}.
		 */
		private final List<Error> errors;

		/**
		 * Mapping from an {@link Expression} or a {@link VariableDeclaration} to its start position in the
		 * parsed text.
		 */
		private final Map<Object, Integer> startPositions;

		/**
		 * Mapping from an {@link Expression} or a {@link VariableDeclaration} to its end position in the
		 * parsed text.
		 */
		private final Map<Object, Integer> endPositions;

		/**
		 * Constructor.
		 * 
		 * @param ast
		 *            the built {@link Expression}
		 * @param startPositions
		 *            the mapping from an {@link Expression} or a {@link VariableDeclaration} to its start
		 *            position in the parsed text
		 * @param endPositions
		 *            the mapping from an {@link Expression} or a {@link VariableDeclaration} to its end
		 *            position in the parsed text
		 * @param errors
		 *            the {@link List} of {@link Error}
		 */
		public AstResult(Expression ast, Map<Object, Integer> startPositions,
				Map<Object, Integer> endPositions, List<Error> errors) {
			this.ast = ast;
			this.errors = errors;
			this.startPositions = startPositions;
			this.endPositions = endPositions;
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
		 * @return the start position of the given {@link Expression} in the parsed text if any,
		 *         <code>-1</code> otherwise
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
		 * Gets the start position of the given {@link Expression} or a {@link VariableDeclaration} in the
		 * parsed text.
		 * 
		 * @param object
		 *            the {@link Expression} or a {@link VariableDeclaration}
		 * @return the start position of the given {@link Expression} or a {@link VariableDeclaration} in the
		 *         parsed text if any, <code>-1</code> otherwise
		 */
		public int getInternalStartPosition(Object object) {
			final int res;

			final Integer position = startPositions.get(object);
			if (position != null) {
				res = position.intValue();
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
		 * Gets the end position of the given {@link Expression} or a {@link VariableDeclaration} in the
		 * parsed text.
		 * 
		 * @param object
		 *            the {@link Expression} or a {@link VariableDeclaration}
		 * @return the end position of the given {@link Expression} or a {@link VariableDeclaration} in the
		 *         parsed text if any, <code>-1</code> otherwise
		 */
		private int getInternalEndPosition(Object object) {
			final int res;

			final Integer position = endPositions.get(object);
			if (position != null) {
				res = position.intValue();
			} else {
				res = -1;
			}

			return res;
		}

	}

	/**
	 * Builds the specified expression.
	 * 
	 * @param expression
	 *            the expression that must be evaluated
	 * @return the resulting {@link AstResult}.
	 * @throws AcceleoQueryEvaluationException
	 *             if expression's syntax is invalid.
	 */
	AstResult build(String expression) throws AcceleoQueryEvaluationException;

}
