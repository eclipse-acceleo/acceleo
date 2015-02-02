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
package org.eclipse.acceleo.query.collections.test;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.query.collections.LazySet;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Ignore
public class LazySetTests {

	private static final List<String> testList = Lists.newArrayList("elt0", "elt1", "elt2", "elt3", "elt4",
			"elt5", "elt6", "elt7", "elt8", "elt9", "elt10", "elt11", "elt12", "elt13", "elt14", "elt15",
			"elt16", "elt17", "elt18", "elt19");

	/**
	 * Test method for {@link org.eclipse.acceleo.query.collections.LazyList#iterator()}.
	 */
	@Test
	public void testIterator() {
		LazySet<String> set = new LazySet<String>(testList, 5);
		Iterator<String> iterator = set.iterator();
		assertTrue(iterator.hasNext());
		assertEquals("elt0", iterator.next());
		assertEquals("elt1", iterator.next());
		assertEquals("elt2", iterator.next());
		assertEquals("elt3", iterator.next());
		assertEquals("elt4", iterator.next());
		assertEquals("elt5", iterator.next());
		assertEquals("elt6", iterator.next());
		assertEquals("elt7", iterator.next());
		assertEquals("elt8", iterator.next());
		assertEquals("elt9", iterator.next());
		iterator.next();
		iterator.next();
		iterator.next();
		iterator.next();
		assertEquals("elt14", iterator.next());
		iterator.next();
		iterator.next();
		iterator.next();
		iterator.next();
		assertEquals("elt19", iterator.next());
		assertFalse(iterator.hasNext());
	}

	@Test
	public void testSetIsSet() {
		List<String> list = Lists.newArrayList("elt0", "elt1", "elt2", "elt3", "elt4", "elt5", "elt6",
				"elt7", "elt8", "elt0", "elt1", "elt2", "elt3", "elt4", "elt5", "elt6", "elt7", "elt8");
		LazySet<String> set = new LazySet<String>(list, 5);
		assertEquals(9, set.size());
	}

	@Test
	public void testIteratorIsSet() {
		List<String> list = Lists.newArrayList("elt0", "elt1", "elt2", "elt3", "elt4", "elt5", "elt6",
				"elt7", "elt8", "elt0", "elt1", "elt2", "elt3", "elt4", "elt5", "elt6", "elt7", "elt8");
		LazySet<String> set = new LazySet<String>(list, 5);

		Iterator<String> iterator = set.iterator();

		assertTrue(iterator.hasNext());
		assertEquals("elt0", iterator.next());
		assertEquals("elt1", iterator.next());
		assertEquals("elt2", iterator.next());
		assertEquals("elt3", iterator.next());
		assertEquals("elt4", iterator.next());
		assertEquals("elt5", iterator.next());
		assertEquals("elt6", iterator.next());
		assertEquals("elt7", iterator.next());
		assertEquals("elt8", iterator.next());
		assertFalse(iterator.hasNext());
	}

}
