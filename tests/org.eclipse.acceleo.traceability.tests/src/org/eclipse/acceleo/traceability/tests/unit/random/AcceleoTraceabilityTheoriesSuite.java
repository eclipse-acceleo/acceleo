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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.runner.Runner;
import org.junit.runners.Suite;
import org.junit.runners.model.FrameworkField;

public class AcceleoTraceabilityTheoriesSuite extends Suite {

	private final ArrayList<Runner> runners = new ArrayList<Runner>();

	public AcceleoTraceabilityTheoriesSuite(Class<?> clazz) throws Throwable {
		super(clazz, Collections.<Runner> emptyList());

		// Find the types used by the constructor
		Class<?>[] types = this.getTestClass().getOnlyConstructor().getParameterTypes();

		List<FrameworkField> operations = this.getTestClass().getAnnotatedFields(Operation.class);
		for (FrameworkField operation : operations) {
			if (types.length > 0 && types[0].isAssignableFrom(operation.getField().getType())) {
				this.runners.add(new AcceleoTraceabilityTheoriesRunner(clazz, operation));
			}
		}
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
