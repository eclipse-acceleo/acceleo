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
package org.eclipse.acceleo.query.services.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.acceleo.query.services.ComparableServices;
import org.junit.Before;
import org.junit.Test;

public class ComparableServicesTest {

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

	@Test
	public void testLessThanCatCat() {
		Cat meow = new Cat("Meow");
		Cat nyan = new Cat("Nyan");
		assertTrue(comparableServices.lessThan(meow, nyan));
		assertFalse(comparableServices.lessThan(nyan, meow));
		assertFalse(comparableServices.lessThan(meow, meow));
	}

	@Test
	public void testLessThanCatDog() {
		Cat nyan = new Cat("Nyan");
		Dog bowwow = new Dog("Bow wow");

		assertFalse(comparableServices.lessThan(nyan, bowwow));
		assertTrue(comparableServices.lessThan(bowwow, nyan));
		assertFalse(comparableServices.lessThan(bowwow, bowwow));
	}

	@Test
	public void testLessThanEqualCatCat() {
		Cat meow = new Cat("Meow");
		Cat nyan = new Cat("Nyan");
		assertTrue(comparableServices.lessThanEqual(meow, nyan));
		assertFalse(comparableServices.lessThanEqual(nyan, meow));
		assertTrue(comparableServices.lessThanEqual(meow, meow));
	}

	@Test
	public void testLessThanEqualCatDog() {
		Cat nyan = new Cat("Nyan");
		Dog bowwow = new Dog("Bow wow");

		assertFalse(comparableServices.lessThanEqual(nyan, bowwow));
		assertTrue(comparableServices.lessThanEqual(bowwow, nyan));
		assertTrue(comparableServices.lessThanEqual(bowwow, bowwow));
	}

	@Test
	public void testGreaterThanCatCat() {
		Cat meow = new Cat("Meow");
		Cat nyan = new Cat("Nyan");
		assertFalse(comparableServices.greaterThan(meow, nyan));
		assertTrue(comparableServices.greaterThan(nyan, meow));
		assertFalse(comparableServices.greaterThan(meow, meow));
	}

	@Test
	public void testGreaterThanCatDog() {
		Cat nyan = new Cat("Nyan");
		Dog bowwow = new Dog("Bow wow");

		assertTrue(comparableServices.greaterThan(nyan, bowwow));
		assertFalse(comparableServices.greaterThan(bowwow, nyan));
		assertFalse(comparableServices.greaterThan(bowwow, bowwow));
	}

	@Test
	public void testGreaterThanEqualCatCat() {
		Cat meow = new Cat("Meow");
		Cat nyan = new Cat("Nyan");
		assertFalse(comparableServices.greaterThanEqual(meow, nyan));
		assertTrue(comparableServices.greaterThanEqual(nyan, meow));
		assertTrue(comparableServices.greaterThanEqual(meow, meow));
	}

	@Test
	public void testGreaterThanEqualCatDog() {
		Cat nyan = new Cat("Nyan");
		Dog bowwow = new Dog("Bow wow");

		assertTrue(comparableServices.greaterThanEqual(nyan, bowwow));
		assertFalse(comparableServices.greaterThanEqual(bowwow, nyan));
		assertTrue(comparableServices.greaterThanEqual(bowwow, bowwow));
	}

	/**
	 * A Test {@link Comparable}. When {@link Comparable#compareTo(Object) compared to} <code>null</code> the
	 * value is returned.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	public final static class TestComparable implements Comparable<TestComparable> {

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

	private static interface Animal {
		String getName();
	}

	private static class Cat implements Animal, Comparable<Animal> {
		private String name;

		public Cat(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public int compareTo(Animal o) {
			if (!(o instanceof Cat)) {
				// cats are superior to other animals :)
				return 1;
			}
			return name.compareTo(((Cat)o).getName());
		}
	}

	private static class Dog implements Animal, Comparable<Animal> {
		private String name;

		public Dog(String name) {
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public int compareTo(Animal o) {
			if (!(o instanceof Dog)) {
				return -1;
			}
			return name.compareTo(((Dog)o).getName());
		}
	}
}
