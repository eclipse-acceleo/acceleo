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
package org.eclipse.acceleo.query.compat.tests.unit;

import org.eclipse.acceleo.query.compat.tests.AcceleoMTLLegacyInterpreter;
import org.eclipse.acceleo.query.tests.InterpreterUnderTest;
import org.eclipse.acceleo.query.tests.qmodel.Query;
import org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResultExpectation;
import org.eclipse.acceleo.query.tests.unit.AnyDSLEvaluationTests;

public class LegacyAnyDSLEvaluationTests extends AnyDSLEvaluationTests {

	public LegacyAnyDSLEvaluationTests(QueryEvaluationResultExpectation expect, String name) {
		super(expect, name);
	}

	@Override
	protected InterpreterUnderTest getInterpreter(Query q) {
		// TODO Auto-generated method stub
		return new AcceleoMTLLegacyInterpreter(q);
	}

}
