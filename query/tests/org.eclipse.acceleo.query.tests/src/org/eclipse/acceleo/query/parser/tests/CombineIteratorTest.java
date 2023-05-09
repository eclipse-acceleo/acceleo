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
package org.eclipse.acceleo.query.parser.tests;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.parser.CombineIterator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests {@link CombineIterator}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class CombineIteratorTest {

	@Test(expected = java.lang.NullPointerException.class)
	public void combineNull() {
		new CombineIterator<Void>(null);
	}

	@Test
	public void combineEmpty() {
		final List<Set<String>> objects = new ArrayList<Set<String>>();
		CombineIterator<String> it = new CombineIterator<String>(objects);

		assertEquals(false, it.hasNext());

		final Set<String> set1 = new LinkedHashSet<String>();
		objects.add(set1);
		it = new CombineIterator<String>(objects);

		assertEquals(false, it.hasNext());
	}

	@Test
	public void combineOne() {
		final List<Set<String>> objects = new ArrayList<Set<String>>();
		final Set<String> set1 = new LinkedHashSet<String>();
		objects.add(set1);
		set1.add("1");
		set1.add("2");
		set1.add("3");

		CombineIterator<String> it = new CombineIterator<String>(objects);

		assertEquals(true, it.hasNext());
		List<String> value = it.next();
		assertEquals(1, value.size());
		assertEquals("1", value.get(0));
		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(1, value.size());
		assertEquals("2", value.get(0));
		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(1, value.size());
		assertEquals("3", value.get(0));

		assertEquals(false, it.hasNext());
	}

	@Test
	public void combineTwo32() {
		final List<Set<String>> objects = new ArrayList<Set<String>>();
		final Set<String> set1 = new LinkedHashSet<String>();
		objects.add(set1);
		set1.add("1");
		set1.add("2");
		set1.add("3");
		final Set<String> set2 = new LinkedHashSet<String>();
		objects.add(set2);
		set2.add("a");
		set2.add("b");

		CombineIterator<String> it = new CombineIterator<String>(objects);

		assertEquals(true, it.hasNext());
		List<String> value = it.next();
		assertEquals(2, value.size());
		assertEquals("1", value.get(0));
		assertEquals("a", value.get(1));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(2, value.size());
		assertEquals("1", value.get(0));
		assertEquals("b", value.get(1));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(2, value.size());
		assertEquals("2", value.get(0));
		assertEquals("a", value.get(1));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(2, value.size());
		assertEquals("2", value.get(0));
		assertEquals("b", value.get(1));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(2, value.size());
		assertEquals("3", value.get(0));
		assertEquals("a", value.get(1));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(2, value.size());
		assertEquals("3", value.get(0));
		assertEquals("b", value.get(1));

		assertEquals(false, it.hasNext());
	}

	@Test
	public void combineTwo23() {
		final List<Set<String>> objects = new ArrayList<Set<String>>();
		final Set<String> set1 = new LinkedHashSet<String>();
		objects.add(set1);
		set1.add("a");
		set1.add("b");
		final Set<String> set2 = new LinkedHashSet<String>();
		objects.add(set2);
		set2.add("1");
		set2.add("2");
		set2.add("3");

		CombineIterator<String> it = new CombineIterator<String>(objects);

		assertEquals(true, it.hasNext());
		List<String> value = it.next();
		assertEquals(2, value.size());
		assertEquals("a", value.get(0));
		assertEquals("1", value.get(1));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(2, value.size());
		assertEquals("a", value.get(0));
		assertEquals("2", value.get(1));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(2, value.size());
		assertEquals("a", value.get(0));
		assertEquals("3", value.get(1));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(2, value.size());
		assertEquals("b", value.get(0));
		assertEquals("1", value.get(1));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(2, value.size());
		assertEquals("b", value.get(0));
		assertEquals("2", value.get(1));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(2, value.size());
		assertEquals("b", value.get(0));
		assertEquals("3", value.get(1));

		assertEquals(false, it.hasNext());
	}

	@Test
	public void combineThree243() {
		final List<Set<String>> objects = new ArrayList<Set<String>>();
		final Set<String> set1 = new LinkedHashSet<String>();
		objects.add(set1);
		set1.add("a");
		set1.add("b");
		final Set<String> set2 = new LinkedHashSet<String>();
		objects.add(set2);
		set2.add("w");
		set2.add("x");
		set2.add("y");
		set2.add("z");
		final Set<String> set3 = new LinkedHashSet<String>();
		objects.add(set3);
		set3.add("1");
		set3.add("2");
		set3.add("3");

		CombineIterator<String> it = new CombineIterator<String>(objects);

		assertEquals(true, it.hasNext());
		List<String> value = it.next();
		assertEquals(3, value.size());
		assertEquals("a", value.get(0));
		assertEquals("w", value.get(1));
		assertEquals("1", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("a", value.get(0));
		assertEquals("w", value.get(1));
		assertEquals("2", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("a", value.get(0));
		assertEquals("w", value.get(1));
		assertEquals("3", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("a", value.get(0));
		assertEquals("x", value.get(1));
		assertEquals("1", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("a", value.get(0));
		assertEquals("x", value.get(1));
		assertEquals("2", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("a", value.get(0));
		assertEquals("x", value.get(1));
		assertEquals("3", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("a", value.get(0));
		assertEquals("y", value.get(1));
		assertEquals("1", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("a", value.get(0));
		assertEquals("y", value.get(1));
		assertEquals("2", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("a", value.get(0));
		assertEquals("y", value.get(1));
		assertEquals("3", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("a", value.get(0));
		assertEquals("z", value.get(1));
		assertEquals("1", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("a", value.get(0));
		assertEquals("z", value.get(1));
		assertEquals("2", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("a", value.get(0));
		assertEquals("z", value.get(1));
		assertEquals("3", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("b", value.get(0));
		assertEquals("w", value.get(1));
		assertEquals("1", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("b", value.get(0));
		assertEquals("w", value.get(1));
		assertEquals("2", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("b", value.get(0));
		assertEquals("w", value.get(1));
		assertEquals("3", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("b", value.get(0));
		assertEquals("x", value.get(1));
		assertEquals("1", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("b", value.get(0));
		assertEquals("x", value.get(1));
		assertEquals("2", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("b", value.get(0));
		assertEquals("x", value.get(1));
		assertEquals("3", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("b", value.get(0));
		assertEquals("y", value.get(1));
		assertEquals("1", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("b", value.get(0));
		assertEquals("y", value.get(1));
		assertEquals("2", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("b", value.get(0));
		assertEquals("y", value.get(1));
		assertEquals("3", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("b", value.get(0));
		assertEquals("z", value.get(1));
		assertEquals("1", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("b", value.get(0));
		assertEquals("z", value.get(1));
		assertEquals("2", value.get(2));

		assertEquals(true, it.hasNext());
		value = it.next();
		assertEquals(3, value.size());
		assertEquals("b", value.get(0));
		assertEquals("z", value.get(1));
		assertEquals("3", value.get(2));

		assertEquals(false, it.hasNext());
	}

}
