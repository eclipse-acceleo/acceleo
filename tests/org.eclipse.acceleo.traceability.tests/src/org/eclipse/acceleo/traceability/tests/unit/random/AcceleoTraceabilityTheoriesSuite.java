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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.experimental.theories.DataPoint;
import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.TestClass;

public class AcceleoTraceabilityTheoriesSuite extends Suite {

	private final ArrayList<Runner> runners = new ArrayList<Runner>();

	public AcceleoTraceabilityTheoriesSuite(Class<?> clazz) throws Throwable {
		super(clazz, Collections.<Runner> emptyList());

		// Find the types used by the constructor
		Class<?>[] types = this.getTestClass().getOnlyConstructor().getParameterTypes();

		// Find all the combinations of fields that can be used with the constructor.
		List<List<FrameworkField>> matchingParametersCouples = new ArrayList<List<FrameworkField>>();
		for (Class<?> type : types) {
			List<FrameworkField> classFields = this.getClassFields(this.getTestClass(), type);
			matchingParametersCouples.add(classFields);
		}

		List<List<FrameworkField>> combinations = AcceleoTraceabilityCombinationUtil
				.combinate(matchingParametersCouples);

		// Create a runner for the class initialized with a combination of parameters
		for (List<FrameworkField> list : combinations) {
			this.runners.add(new AcceleoTraceabilityTheoriesRunner(clazz, list));
		}
	}

	private List<FrameworkField> getClassFields(TestClass testClass, Class<?> clazz) {
		List<FrameworkField> useableAttribute = new ArrayList<FrameworkField>();

		List<FrameworkField> annotatedFields = testClass.getAnnotatedFields(DataPoint.class);
		for (FrameworkField frameworkField : annotatedFields) {
			Class<?> declaringClass = frameworkField.getField().getType();
			if (clazz.isAssignableFrom(declaringClass)) {
				useableAttribute.add(frameworkField);
			}
		}

		return useableAttribute;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.junit.runners.Suite#getChildren()
	 */
	@Override
	protected List<Runner> getChildren() {
		return this.runners;
	}

}
