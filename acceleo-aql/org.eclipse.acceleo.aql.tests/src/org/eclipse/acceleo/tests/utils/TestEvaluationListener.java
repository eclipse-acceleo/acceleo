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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

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
	 * The {@link StringBuilder} accumulating the generated text.
	 */
	private final StringBuilder builder = new StringBuilder();

	/**
	 * The mapping from an {@link ASTNode} to the {@link List} of sub string offsets.
	 */
	private final Map<ASTNode, List<int[]>> fragments = new LinkedHashMap<ASTNode, List<int[]>>();

	@Override
	public void startEvaluation(ASTNode node, IAcceleoEnvironment environment,
			Map<String, Object> variables) {
		List<int[]> offsets = fragments.get(node);
		if (offsets == null) {
			offsets = new ArrayList<int[]>();
			fragments.put(node, offsets);
		}
		final int[] positions = new int[2];
		positions[0] = builder.length();
		offsets.add(positions);
	}

	@Override
	public void endEvaluation(ASTNode node, IAcceleoEnvironment environment, Map<String, Object> variables,
			Object result) {
		toString(builder, result);
		final List<int[]> offsets = fragments.get(node);
		final int[] positions = offsets.get(offsets.size() - 1);
		positions[1] = builder.length();
	}

	public String getTextFragments(ASTNode node) {
		final StringJoiner res = new StringJoiner(
				"\n-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n");

		for (int[] fragmentoffsets : fragments.get(node)) {
			res.add(builder.subSequence(fragmentoffsets[0], fragmentoffsets[1]));
		}

		return res.toString();
	}

	/**
	 * Converts the given {@link Object} to a {@link String}.
	 * 
	 * @param builder
	 *            the {@link StringBuilder}
	 * @param object
	 *            the {@link Object} to convert
	 * @return the {@link String} representation of the given {@link Object}
	 */
	private void toString(StringBuilder builder, Object object) {

		if (object instanceof Collection<?>) {
			final Iterator<?> childrenIterator = ((Collection<?>)object).iterator();
			while (childrenIterator.hasNext()) {
				toString(builder, childrenIterator.next());
			}
		} else if (object != null) {
			builder.append(object.toString());
		}
	}

}
