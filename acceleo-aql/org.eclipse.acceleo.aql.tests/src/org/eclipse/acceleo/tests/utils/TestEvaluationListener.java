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
package org.eclipse.acceleo.tests.utils;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.aql.evaluation.IAcceleoEvaluationListener;

/**
 * Test {@link IAcceleoEvaluationListener} produce text fragments for each {@link ASTNode}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class TestEvaluationListener implements IAcceleoEvaluationListener {

	/**
	 * Evaluation fragments.
	 */
	private final Map<ASTNode, String> fragments = new HashMap<ASTNode, String>();

	@Override
	public void startEvaluation(ASTNode node, IAcceleoEnvironment environment,
			Map<String, Object> variables) {
		// nothing to do here
	}

	@Override
	public void endEvaluation(ASTNode node, IAcceleoEnvironment environment, Map<String, Object> variables,
			String result) {
		fragments.put(node, result);
	}

	/**
	 * Gets Evaluation fragments.
	 * 
	 * @return evaluation fragments
	 */
	public Map<ASTNode, String> getFragments() {
		return fragments;
	}

}
