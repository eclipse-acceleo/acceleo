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
package org.eclipse.acceleo.query.services.tests;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.services.BooleanServices;
import org.junit.Before;
import org.junit.Test;

public class BooleanServicesTest extends AbstractServicesTest {
	@Before
	public void setup() throws InvalidAcceleoPackageException {
		getLookupEngine().addServices(BooleanServices.class);
	}

	@Test
	public void testOr() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		runTest("or", true, new Boolean[] {true, false });
		runTest("or", true, new Boolean[] {false, true });
		runTest("or", true, new Boolean[] {true, true });
		runTest("or", false, new Boolean[] {false, false });
	}

	@Test
	public void testAnd() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		runTest("and", false, new Boolean[] {true, false });
		runTest("and", false, new Boolean[] {false, true });
		runTest("and", true, new Boolean[] {true, true });
		runTest("and", false, new Boolean[] {false, false });
	}

	@Test
	public void testNot() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		runTest("not", false, new Boolean[] {true });
		runTest("not", true, new Boolean[] {false });
	}

}
