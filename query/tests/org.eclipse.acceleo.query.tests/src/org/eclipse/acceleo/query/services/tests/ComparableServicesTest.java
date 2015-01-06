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

import org.eclipse.acceleo.query.services.ComparableServices;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ComparableServicesTest {
	ComparableServices comparableServices;

	@Before
	public void setup() {
		comparableServices = new ComparableServices();
	}

	@Test
	public void testLower() {
		Integer comparable1 = new Integer(1);
		Integer comparable2 = new Integer(2);

		assertTrue(comparableServices.lower(comparable1, comparable2));
		assertFalse(comparableServices.lower(comparable2, comparable1));
		assertFalse(comparableServices.lower(comparable1, comparable1));
		assertFalse(comparableServices.lower(comparable2, comparable2));
	}

	@Test
	public void testLowerEqual() {
		Integer comparable1 = new Integer(1);
		Integer comparable2 = new Integer(2);

		assertTrue(comparableServices.lowerEqual(comparable1, comparable2));
		assertFalse(comparableServices.lowerEqual(comparable2, comparable1));
		assertTrue(comparableServices.lowerEqual(comparable1, comparable1));
		assertTrue(comparableServices.lowerEqual(comparable2, comparable2));
	}

	@Test
	public void testGreater() {
		Integer comparable1 = new Integer(1);
		Integer comparable2 = new Integer(2);

		assertFalse(comparableServices.greater(comparable1, comparable2));
		assertTrue(comparableServices.greater(comparable2, comparable1));
		assertFalse(comparableServices.greater(comparable1, comparable1));
		assertFalse(comparableServices.greater(comparable2, comparable2));
	}

	@Test
	public void testGreaterEqual() {
		Integer comparable1 = new Integer(1);
		Integer comparable2 = new Integer(2);

		assertFalse(comparableServices.greaterEqual(comparable1, comparable2));
		assertTrue(comparableServices.greaterEqual(comparable2, comparable1));
		assertTrue(comparableServices.greaterEqual(comparable1, comparable1));
		assertTrue(comparableServices.greaterEqual(comparable2, comparable2));
	}

	@Test
	public void testEquals() {
		Integer comparable1 = new Integer(1);
		Integer comparable2 = new Integer(2);

		assertFalse(comparableServices.equals(comparable1, comparable2));
		assertFalse(comparableServices.equals(comparable2, comparable1));
		assertTrue(comparableServices.equals(comparable1, comparable1));
		assertTrue(comparableServices.equals(comparable2, comparable2));
	}

	@Test
	public void testDiffers() {
		Integer comparable1 = new Integer(1);
		Integer comparable2 = new Integer(2);

		assertTrue(comparableServices.differs(comparable1, comparable2));
		assertTrue(comparableServices.differs(comparable2, comparable1));
		assertFalse(comparableServices.differs(comparable1, comparable1));
		assertFalse(comparableServices.differs(comparable2, comparable2));
	}

}
