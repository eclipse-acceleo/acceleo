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

	/**
	 * A Test {@link Comparable}. When {@link Comparable#compareTo(Object) compared to} <code>null</code> the
	 * value is returned.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private final static class TestComparable implements Comparable<TestComparable> {

		private final int value;

		public TestComparable(int value) {
			this.value = value;
		}

		@Override
		public int compareTo(TestComparable o) {
			final int result;

			if (o == null) {
				result = value;
			} else {
				result = value - o.value;
			}

			return result;
		}

	}

	ComparableServices comparableServices;

	@Before
	public void setup() {
		comparableServices = new ComparableServices();
	}

	@Test
	public void testLessThanNullNull() {
		assertFalse(comparableServices.lessThan(null, null));
	}

	@Test
	public void testLessThanNullTestComparable() {
		TestComparable comparable1 = new TestComparable(1);

		assertTrue(comparableServices.lessThan(null, comparable1));
	}

	@Test
	public void testLessThanTestComparableNull() {
		TestComparable comparable1 = new TestComparable(1);

		assertFalse(comparableServices.lessThan(comparable1, null));
	}

	@Test
	public void testLessThan() {
		TestComparable comparable1 = new TestComparable(1);
		TestComparable comparable2 = new TestComparable(2);

		assertTrue(comparableServices.lessThan(comparable1, comparable2));
		assertFalse(comparableServices.lessThan(comparable2, comparable1));
		assertFalse(comparableServices.lessThan(comparable1, comparable1));
		assertFalse(comparableServices.lessThan(comparable2, comparable2));
	}

	@Test
	public void testLessThanEqualNullNull() {
		assertTrue(comparableServices.lessThanEqual(null, null));
	}

	@Test
	public void testLessThanEqualNullTestComparable() {
		TestComparable comparable1 = new TestComparable(1);

		assertTrue(comparableServices.lessThanEqual(null, comparable1));
	}

	@Test
	public void testLessThanEqualTestComparableNull() {
		TestComparable comparable1 = new TestComparable(1);

		assertFalse(comparableServices.lessThanEqual(comparable1, null));
	}

	@Test
	public void testLessThanEqual() {
		TestComparable comparable1 = new TestComparable(1);
		TestComparable comparable2 = new TestComparable(2);

		assertTrue(comparableServices.lessThanEqual(comparable1, comparable2));
		assertFalse(comparableServices.lessThanEqual(comparable2, comparable1));
		assertTrue(comparableServices.lessThanEqual(comparable1, comparable1));
		assertTrue(comparableServices.lessThanEqual(comparable2, comparable2));
	}

	@Test
	public void testGreaterThanNullNull() {
		assertFalse(comparableServices.greaterThan(null, null));
	}

	@Test
	public void testGreaterThanNullTestComparable() {
		TestComparable comparable1 = new TestComparable(1);

		assertFalse(comparableServices.greaterThan(null, comparable1));
	}

	@Test
	public void testGreaterThanTestComparableNull() {
		TestComparable comparable1 = new TestComparable(1);

		assertTrue(comparableServices.greaterThan(comparable1, null));
	}

	@Test
	public void testGreaterThan() {
		TestComparable comparable1 = new TestComparable(1);
		TestComparable comparable2 = new TestComparable(2);

		assertFalse(comparableServices.greaterThan(comparable1, comparable2));
		assertTrue(comparableServices.greaterThan(comparable2, comparable1));
		assertFalse(comparableServices.greaterThan(comparable1, comparable1));
		assertFalse(comparableServices.greaterThan(comparable2, comparable2));
	}

	@Test
	public void testGreaterThanEqualNullNull() {
		assertTrue(comparableServices.greaterThanEqual(null, null));
	}

	@Test
	public void testGreaterThanEqualNullTestComparable() {
		TestComparable comparable1 = new TestComparable(1);

		assertFalse(comparableServices.greaterThanEqual(null, comparable1));
	}

	@Test
	public void testGreaterThanEqualTestComparableNull() {
		TestComparable comparable1 = new TestComparable(1);

		assertTrue(comparableServices.greaterThanEqual(comparable1, null));
	}

	@Test
	public void testGreaterThanEqual() {
		TestComparable comparable1 = new TestComparable(1);
		TestComparable comparable2 = new TestComparable(2);

		assertFalse(comparableServices.greaterThanEqual(comparable1, comparable2));
		assertTrue(comparableServices.greaterThanEqual(comparable2, comparable1));
		assertTrue(comparableServices.greaterThanEqual(comparable1, comparable1));
		assertTrue(comparableServices.greaterThanEqual(comparable2, comparable2));
	}

}
