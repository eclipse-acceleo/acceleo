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
package org.eclipse.acceleo.query.runtime.test;

import com.google.common.collect.Maps;

import java.util.HashMap;

import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IQueryEvaluationEngine;
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.QueryEvaluation;
import org.eclipse.acceleo.query.runtime.QueryParsing;
import org.eclipse.acceleo.query.runtime.servicelookup.ServicesCountCalls;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShortcutEvaluationTest {

	IQueryEvaluationEngine evaluator;

	IQueryBuilderEngine parser;

	private HashMap<String, Object> variables;

	@Before
	public void setUp() throws InvalidAcceleoPackageException {
		IQueryEnvironment env = Query.newEnvironmentWithDefaultServices(null);
		env.registerServicePackage(ServicesCountCalls.class);
		parser = QueryParsing.newBuilder(env);
		evaluator = QueryEvaluation.newEngine(env);
		variables = Maps.newHashMap();
		variables.put("self", EcorePackage.eINSTANCE);
		evaluator.eval(parser.build("self.reset()"), variables);

	}

	/**
	 * This test only make sure that the org.eclipse.acceleo.query.runtime.servicelookup.ServicesCountCalls
	 * service provides the expected behavior of counting calls and returning the count.
	 */
	@Test
	public void serviceIsCounting() {
		assertEquals(0, evaluator.eval(parser.build("self.getCallCounts()"), variables).getResult());
		assertEquals(Boolean.TRUE, evaluator.eval(parser.build("self.checkAlwaysTrue()"), variables)
				.getResult());
		assertEquals(1, evaluator.eval(parser.build("self.getCallCounts()"), variables).getResult());
		assertEquals(Boolean.FALSE, evaluator.eval(parser.build("self.checkAlwaysFalse()"), variables)
				.getResult());
		assertEquals(2, evaluator.eval(parser.build("self.getCallCounts()"), variables).getResult());

	}

	@Test
	public void andPrematureStopWhenFalse() {
		assertEquals(0, evaluator.eval(parser.build("self.getCallCounts()"), variables).getResult());
		assertEquals(Boolean.FALSE, evaluator.eval(
				parser.build("self.checkAlwaysFalse() and self.checkAlwaysTrue()"), variables).getResult());
		assertEquals(1, evaluator.eval(parser.build("self.getCallCounts()"), variables).getResult());
	}

	@Test
	public void andNoStopWhenTrue() {
		assertEquals(0, evaluator.eval(parser.build("self.getCallCounts()"), variables).getResult());
		assertEquals(Boolean.TRUE, evaluator.eval(
				parser.build("self.checkAlwaysTrue() and self.checkAlwaysTrue()"), variables).getResult());
		assertEquals(2, evaluator.eval(parser.build("self.getCallCounts()"), variables).getResult());
	}

	@Test
	public void orPrematureStopWhenTrue() {
		assertEquals(0, evaluator.eval(parser.build("self.getCallCounts()"), variables).getResult());
		assertEquals(Boolean.TRUE, evaluator.eval(
				parser.build("self.checkAlwaysTrue() or self.checkAlwaysTrue()"), variables).getResult());
		assertEquals(1, evaluator.eval(parser.build("self.getCallCounts()"), variables).getResult());

	}

	@Test
	public void orNoStopWhenFalse() {
		assertEquals(0, evaluator.eval(parser.build("self.getCallCounts()"), variables).getResult());
		assertEquals(Boolean.TRUE, evaluator.eval(
				parser.build("self.checkAlwaysFalse() or self.checkAlwaysTrue()"), variables).getResult());
		assertEquals(2, evaluator.eval(parser.build("self.getCallCounts()"), variables).getResult());
	}

	@Test
	public void impliesPrematureStopWhenFalse() {
		assertEquals(0, evaluator.eval(parser.build("self.getCallCounts()"), variables).getResult());
		assertEquals(Boolean.TRUE, evaluator.eval(
				parser.build("self.checkAlwaysFalse() implies self.checkAlwaysTrue()"), variables)
				.getResult());
		assertEquals(1, evaluator.eval(parser.build("self.getCallCounts()"), variables).getResult());
	}

	@Test
	public void impliesNoStopWhenTrue() {
		assertEquals(0, evaluator.eval(parser.build("self.getCallCounts()"), variables).getResult());
		assertEquals(Boolean.TRUE, evaluator.eval(
				parser.build("self.checkAlwaysTrue() implies self.checkAlwaysTrue()"), variables).getResult());
		assertEquals(2, evaluator.eval(parser.build("self.getCallCounts()"), variables).getResult());
	}

}
