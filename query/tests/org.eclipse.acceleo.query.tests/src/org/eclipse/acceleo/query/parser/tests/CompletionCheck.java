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
package org.eclipse.acceleo.query.parser.tests;

import static org.junit.Assert.assertTrue;

import com.google.common.collect.Iterators;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
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

public class CompletionCheck {
	private UnitTestModels qmodels;

	@Before
	public void setUp() {
		this.qmodels = new UnitTestModels(Setup.createSetupForCurrentEnvironment());
	}

	@Test
	public void testReverseCompletion() throws Exception {
		checkCompletionOnQueries(Iterators.filter(qmodels.reverse().getAllContents(), Query.class));
	}

	@Test
	public void testAnyDSLCompletion() throws Exception {
		checkCompletionOnQueries(Iterators.filter(qmodels.anydsl().getAllContents(), Query.class));
	}

	@Test
	public void testUMLCompletion() throws Exception {
		checkCompletionOnQueries(Iterators.filter(qmodels.uml().getAllContents(), Query.class));
	}

	@Test
	public void testUMLWithFragmentCompletion() throws Exception {
		checkCompletionOnQueries(Iterators.filter(qmodels.umlWithFragment().getAllContents(), Query.class));
	}

	private void checkCompletionOnQueries(Iterator<Query> queries) throws Exception {
		while (queries.hasNext()) {
			Query next = queries.next();

			IQueryEnvironment queryEnvironment = org.eclipse.acceleo.query.runtime.Query
					.newEnvironmentWithDefaultServices(null);
			queryEnvironment.registerEPackage(QmodelPackage.eINSTANCE);
			QueryCompletionEngine completionEngine = new QueryCompletionEngine(queryEnvironment);

			for (String classToImport : next.getClassesToImport()) {
				queryEnvironment.registerServicePackage(Class.forName(classToImport));
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
}
