/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.tests.unit.evaluation;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AcceleoEvaluationVisitorTestSuite extends TestCase {
	/**
	 * Creates the {@link TestSuite test suite} for all evaluation visitor tests.
	 * 
	 * @return the test suite containing all evaluation visitor tests.
	 */
	public static Test suite() {
		final TestSuite suite = new TestSuite("Tests for the Acceleo evaluation visitor."); //$NON-NLS-1$
		suite.addTestSuite(AcceleoEvaluationVisitorFileBlockTest.class);
		suite.addTestSuite(AcceleoEvaluationVisitorForBlockTest.class);
		suite.addTestSuite(AcceleoEvaluationVisitorIfBlockTest.class);
		suite.addTestSuite(AcceleoEvaluationVisitorLetBlockTest.class);
		suite.addTestSuite(AcceleoEvaluationVisitorProtectedAreaBlockTest.class);
		suite.addTestSuite(AcceleoEvaluationVisitorQueryInvocationTest.class);
		suite.addTestSuite(AcceleoEvaluationVisitorTemplateInvocationTest.class);
		suite.addTestSuite(LineReaderTest.class);
		return suite;
	}
}
