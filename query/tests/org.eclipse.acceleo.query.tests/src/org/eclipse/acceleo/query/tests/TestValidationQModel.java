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
package org.eclipse.acceleo.query.tests;

import com.google.common.collect.Iterators;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.acceleo.query.tests.qmodel.Query;
import org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult;
import org.eclipse.acceleo.query.tests.qmodel.QueryValidationResultExpectation;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public abstract class TestValidationQModel {

	private QueryValidationResultExpectation expectation;

	protected InterpreterUnderTest underTest;

	public TestValidationQModel(QueryValidationResultExpectation expect, String name) {
		this.expectation = expect;
	}

	@Test
	public void test() {
		if (expectation instanceof QueryValidationResultExpectation) {
			QueryValidationResultExpectation qExpect = expectation;
			Query q = (Query)qExpect.eContainer();
			underTest = new AcceleoQueryInterpreter(q);
			underTest.compileQuery(q);
			QueryValidationResult actualResult = underTest.validateQuery(q);
			QueryResultAssert.printValidation(q, actualResult);
			QueryResultAssert.assertEquivalentValidation(qExpect.getExpectedResult(), actualResult);
		}
	}

	protected static Collection<Object[]> expectationsFrom(Resource reverse) throws URISyntaxException,
			IOException {
		Collection<Object[]> parameters = new ArrayList<Object[]>();
		Iterator<QueryValidationResultExpectation> it = Iterators.filter(reverse.getAllContents(),
				QueryValidationResultExpectation.class);
		int i = 0;
		while (it.hasNext()) {
			QueryValidationResultExpectation cur = it.next();
			if (cur.getExpectedResult() != null) {
				parameters.add(new Object[] {cur, ++i + " " + ((Query)cur.eContainer()).getExpression() });
			}
		}
		return parameters;
	}
}
