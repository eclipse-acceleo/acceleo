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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public abstract class TestEvaluationPerfQModel {

	/**
	 * The number of loop.
	 */
	private final static int LOOP = 10000;

	private QueryEvaluationResultExpectation expectation;

	protected InterpreterUnderTest underTest;

	public TestEvaluationPerfQModel(QueryEvaluationResultExpectation expect, String name) {
		this.expectation = expect;
	}

	private static OutputStreamWriter out;

	@BeforeClass
	public static void beforeClass() {
		try {
			File tmpLogFile = File.createTempFile("log.txt", "");
			out = new OutputStreamWriter(new FileOutputStream(tmpLogFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void afterClass() {
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test() {
		if (expectation instanceof QueryEvaluationResultExpectation) {
			QueryEvaluationResultExpectation qExpect = expectation;
			Query q = (Query)qExpect.eContainer();
			underTest = getInterpreter(q);
			QueryEvaluationResult actualResult = null;

			long start = System.currentTimeMillis();
			for (int i = 0; i < LOOP; ++i) {
				underTest.compileQuery(q);
			}
			try {
				out.write(q.getExpression() + " " + (System.currentTimeMillis() - start) + "]");
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			start = System.currentTimeMillis();
			for (int i = 0; i < LOOP; ++i) {
				actualResult = underTest.computeQuery(q);
			}
			try {
				out.write((System.currentTimeMillis() - start) + "\n");
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

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
