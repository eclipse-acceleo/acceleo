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

import org.eclipse.acceleo.query.ast.Error;
import org.eclipse.acceleo.query.ast.Expression;

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
		 * Constructor.
		 * 
		 * @param ast
		 *            the built {@link Expression}
		 * @param errors
		 *            the {@link List} of {@link Error}
		 */
		public AstResult(Expression ast, List<Error> errors) {
			this.ast = ast;
			this.errors = errors;
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
