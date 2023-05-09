/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.StreamSupport;

import org.eclipse.acceleo.query.tests.qmodel.Query;
import org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResult;
import org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResultExpectation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public abstract class TestEvaluationQModel {

	private QueryEvaluationResultExpectation expectation;

	protected InterpreterUnderTest underTest;

	public TestEvaluationQModel(QueryEvaluationResultExpectation expect, String name) {
		this.expectation = expect;
	}

	@Test
	public void test() {
		if (expectation instanceof QueryEvaluationResultExpectation) {
			QueryEvaluationResultExpectation qExpect = expectation;
			Query q = (Query)qExpect.eContainer();
			underTest = getInterpreter(q);
			underTest.compileQuery(q);
			QueryEvaluationResult actualResult = underTest.computeQuery(q);
			QueryResultAssert.assertEquivalentEvaluation(qExpect.getExpectedResult(), actualResult);
		}
	}

	protected abstract InterpreterUnderTest getInterpreter(Query q);

	protected static Collection<Object[]> expectationsFrom(Resource reverse) throws URISyntaxException,
			IOException {
		Collection<Object[]> parameters = new ArrayList<Object[]>();
		Iterable<EObject> allContents = () -> reverse.getAllContents();
		int i = 0;
		// @formatter:off
		Iterator<QueryEvaluationResultExpectation> filtered = StreamSupport.stream(allContents.spliterator(), false)
				.filter(QueryEvaluationResultExpectation.class::isInstance)
				.map(QueryEvaluationResultExpectation.class::cast)
				.iterator();
		// @formatter:on
		while (filtered.hasNext()) {
			QueryEvaluationResultExpectation cur = filtered.next();
			if (cur.getExpectedResult() != null) {
				parameters.add(new Object[] {cur, ++i + " " + ((Query)cur.eContainer()).getExpression() });
			}
		}
		return parameters;
	}
}
