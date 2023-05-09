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
package org.eclipse.acceleo.query.tests.unit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

import org.eclipse.acceleo.query.tests.AcceleoQueryInterpreter;
import org.eclipse.acceleo.query.tests.InterpreterUnderTest;
import org.eclipse.acceleo.query.tests.Setup;
import org.eclipse.acceleo.query.tests.TestEvaluationPerfQModel;
import org.eclipse.acceleo.query.tests.UnitTestModels;
import org.eclipse.acceleo.query.tests.qmodel.Query;
import org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResultExpectation;
import org.junit.runners.Parameterized.Parameters;

public class EcoreReversePerfTests extends TestEvaluationPerfQModel {

	public EcoreReversePerfTests(QueryEvaluationResultExpectation expect, String name) {
		super(expect, name);
	}

	/**
	 * Gets the {@link InterpreterUnderTest}.
	 * 
	 * @param q
	 *            the {@link Query}
	 * @return the {@link InterpreterUnderTest}
	 */
	protected InterpreterUnderTest getInterpreter(Query q) {
		return new AcceleoQueryInterpreter(q);
	}

	@Parameters(name = "{1}")
	public static Collection<Object[]> retrieveExpectations() throws URISyntaxException, IOException {
		return expectationsFrom(new UnitTestModels(Setup.createSetupForCurrentEnvironment()).reverse());
	}

}
