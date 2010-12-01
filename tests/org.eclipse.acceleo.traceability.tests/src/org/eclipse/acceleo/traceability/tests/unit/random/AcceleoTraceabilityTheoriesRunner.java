/*******************************************************************************
 * Copyright (c) 2009, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.traceability.tests.unit.random;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

public class AcceleoTraceabilityTheoriesRunner extends BlockJUnit4ClassRunner {

	private List<FrameworkField> parameters;

	public AcceleoTraceabilityTheoriesRunner(Class<?> clazz, List<FrameworkField> parameters)
			throws InitializationError {
		super(clazz);
		this.parameters = parameters;
	}

	@Override
	protected Object createTest() throws Exception {

		Constructor<?> onlyConstructor = this.getTestClass().getOnlyConstructor();

		List<Object> params = new ArrayList<Object>();
		for (FrameworkField frameworkField : this.parameters) {
			Object field = frameworkField.get(null);
			params.add(field);
		}

		Object instance = onlyConstructor.newInstance(params.toArray());

		return instance;
	}

	@Override
	protected String testName(FrameworkMethod method) {
		String paramName = "["; //$NON-NLS-1$

		int size = this.parameters.size();
		for (int i = 0; i < size; i++) {
			FrameworkField frameworkField = this.parameters.get(i);
			if (i < size - 1) {
				paramName += frameworkField.getField().getName() + ", "; //$NON-NLS-1$
			} else {
				paramName += frameworkField.getField().getName();
			}

		}

		paramName += "]"; //$NON-NLS-1$

		return super.testName(method) + "-" + paramName; //$NON-NLS-1$
	}

	@Override
	protected String getName() {
		String paramName = "["; //$NON-NLS-1$

		int size = this.parameters.size();
		for (int i = 0; i < size; i++) {
			FrameworkField frameworkField = this.parameters.get(i);
			if (i < size - 1) {
				paramName += frameworkField.getField().getName() + ", "; //$NON-NLS-1$
			} else {
				paramName += frameworkField.getField().getName();
			}

		}

		paramName += "]"; //$NON-NLS-1$
		return this.getTestClass().getJavaClass().getSimpleName() + "-" + paramName; //$NON-NLS-1$
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
