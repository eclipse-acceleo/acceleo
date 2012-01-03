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
package org.eclipse.acceleo.traceability.tests.unit.random;

import java.util.List;

import org.junit.experimental.theories.Theory;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class AcceleoTraceabilityTheoriesRunner extends BlockJUnit4ClassRunner {

	/**
	 * The method to test.
	 */
	private FrameworkField frameworkMethod;

	public AcceleoTraceabilityTheoriesRunner(Class<?> clazz, FrameworkField method)
			throws InitializationError {
		super(clazz);
		this.frameworkMethod = method;
	}

	@Override
	protected Object createTest() throws Exception {
		return getTestClass().getOnlyConstructor().newInstance(this.frameworkMethod.get(null));
	}

	@Override
	protected String testName(FrameworkMethod method) {
		return "Test-[" + this.frameworkMethod.getField().getName() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	protected String getName() {
		return this.getTestClass().getJavaClass().getSimpleName();
	}

	@Override
	protected List<FrameworkMethod> computeTestMethods() {
		return getTestClass().getAnnotatedMethods(Theory.class);
	}

	@Override
	protected Statement methodBlock(FrameworkMethod method) {
		return new RandomStatement(method, getTestClass());
	}

	@Override
	protected void validateConstructor(List<Throwable> errors) {
		validateOnlyOneConstructor(errors);
	}

	@Override
	protected Statement classBlock(RunNotifier notifier) {
		return childrenInvoker(notifier);
	}

}
