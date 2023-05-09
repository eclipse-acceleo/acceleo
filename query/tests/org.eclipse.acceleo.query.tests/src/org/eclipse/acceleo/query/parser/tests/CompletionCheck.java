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
package org.eclipse.acceleo.query.parser.tests;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.runtime.impl.QueryCompletionEngine;
import org.eclipse.acceleo.query.tests.Setup;
import org.eclipse.acceleo.query.tests.UnitTestModels;
import org.eclipse.acceleo.query.tests.qmodel.EObjectVariable;
import org.eclipse.acceleo.query.tests.qmodel.QmodelPackage;
import org.eclipse.acceleo.query.tests.qmodel.Query;
import org.eclipse.acceleo.query.tests.qmodel.Variable;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CompletionCheck {
	private UnitTestModels qmodels;

	@Before
	public void setUp() {
		this.qmodels = new UnitTestModels(Setup.createSetupForCurrentEnvironment());
	}

	@Test
	public void testReverseCompletion() throws Exception {
		Stream<Query> queries = toStream(qmodels.reverse().getAllContents()).filter(Query.class::isInstance)
				.map(Query.class::cast);
		checkCompletionOnQueries(queries.iterator());
	}

	@Test
	public void testAnyDSLCompletion() throws Exception {
		Stream<Query> queries = toStream(qmodels.anydsl().getAllContents()).filter(Query.class::isInstance)
				.map(Query.class::cast);
		checkCompletionOnQueries(queries.iterator());
	}

	@Test
	public void testUMLCompletion() throws Exception {
		Stream<Query> queries = toStream(qmodels.uml().getAllContents()).filter(Query.class::isInstance).map(
				Query.class::cast);
		checkCompletionOnQueries(queries.iterator());
	}

	@Test
	public void testUMLWithFragmentCompletion() throws Exception {
		Stream<Query> queries = toStream(qmodels.umlWithFragment().getAllContents()).filter(
				Query.class::isInstance).map(Query.class::cast);
		checkCompletionOnQueries(queries.iterator());
	}

	private void checkCompletionOnQueries(Iterator<Query> queries) throws Exception {
		while (queries.hasNext()) {
			Query next = queries.next();

			IQueryEnvironment queryEnvironment = org.eclipse.acceleo.query.runtime.Query
					.newEnvironmentWithDefaultServices(null);
			queryEnvironment.registerEPackage(QmodelPackage.eINSTANCE);
			QueryCompletionEngine completionEngine = new QueryCompletionEngine(queryEnvironment);

			for (String classToImport : next.getClassesToImport()) {
				final Set<IService<?>> services = ServiceUtils.getServices(queryEnvironment, Class.forName(
						classToImport));
				ServiceUtils.registerServices(queryEnvironment, services);
			}

			Map<String, Set<IType>> variableTypes = new HashMap<String, Set<IType>>();
			Set<IType> selfType = new HashSet<IType>();
			selfType.add(new EClassifierType(queryEnvironment, next.getStartingPoint().eClass()));
			variableTypes.put("self", selfType);

			for (Variable var : next.getVariables()) {
				assertTrue(var instanceof EObjectVariable);
				Set<IType> types = new LinkedHashSet<IType>();
				types.add(new EClassifierType(queryEnvironment, ((EObjectVariable)var).getValue().getTarget()
						.eClass()));
				variableTypes.put(var.getName(), types);
			}

			for (int offset = 0; offset < next.getExpression().length(); offset++) {
				try {
					completionEngine.getCompletion(next.getExpression(), offset, variableTypes);
				} catch (Exception e) {
					// Change error message
					throw new RuntimeException("error getting completion on " + next.getExpression()
							+ " at offset " + offset, e);
				}
			}
		}
	}

	private <T> Stream<T> toStream(final Iterator<T> iterator) {
		Iterable<T> iterable = () -> iterator;
		return StreamSupport.stream(iterable.spliterator(), false);
	}
}
