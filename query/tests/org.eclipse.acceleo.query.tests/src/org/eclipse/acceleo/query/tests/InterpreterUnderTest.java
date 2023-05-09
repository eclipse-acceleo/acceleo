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

import org.eclipse.acceleo.query.tests.qmodel.Query;
import org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResult;
import org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult;

public interface InterpreterUnderTest {

	/**
	 * Compiles the given {@link Query}.
	 * 
	 * @param query
	 *            the {@link Query}
	 */
	public void compileQuery(Query query);

	/**
	 * Evaluates the given {@link Query}.
	 * 
	 * @param query
	 *            the {@link Query}
	 * @return the {@link QueryEvaluationResult}
	 */
	public QueryEvaluationResult computeQuery(Query query);

	/**
	 * Validates the given {@link Query}.
	 * 
	 * @param query
	 *            the {@link Query}
	 * @return the {@link QueryValidationResult}
	 */
	public QueryValidationResult validateQuery(Query query);

}
