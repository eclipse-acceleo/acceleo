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
package org.eclipse.acceleo.aql.evaluation;

import java.util.Map;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.aql.IAcceleoEnvironment;

/**
 * Listen to the {@link AcceleoEvaluator}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IAcceleoEvaluationListener {

	/**
	 * Starts the evaluation of the given {@link ASTNode}.
	 * 
	 * @param node
	 *            the {@link ASTNode}
	 * @param environment
	 *            the {@link IAcceleoEnvironment}
	 * @param variables
	 *            the mapping from variable name to its value
	 */
	public void startEvaluation(ASTNode node, IAcceleoEnvironment environment, Map<String, Object> variables);

	/**
	 * Ends the evaluation of the given {@link ASTNode}.
	 * 
	 * @param node
	 *            the {@link ASTNode}
	 * @param environment
	 *            the {@link IAcceleoEnvironment}
	 * @param variables
	 *            the mapping from variable name to its value
	 * @param result
	 *            the evaluated result if any, <code>null</code> otherwise
	 */
	public void endEvaluation(ASTNode node, IAcceleoEnvironment environment, Map<String, Object> variables,
			String result);

}
