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
package org.eclipse.acceleo.query.runtime.test;

import org.eclipse.acceleo.query.runtime.impl.BasicFilter;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FilterCamelCaseTest {
	@Test
	public void test1() {
		assertTrue(BasicFilter.startsWithOrMatchCamelCase("thisIsAPotentialResult", "th"));
	}

	@Test
	public void test2() {
		assertTrue(BasicFilter.startsWithOrMatchCamelCase("thisIsAPotentialResult", "thIAPR"));
	}

	@Test
	public void test3() {
		assertTrue(BasicFilter.startsWithOrMatchCamelCase("thisIsAPotentialResult", "tIA"));
	}

	@Test
	public void test4() {
		assertTrue(BasicFilter.startsWithOrMatchCamelCase("thisIsAPotentialResult", "tIAPoR"));
	}

	@Test
	public void test5() {
		assertTrue(BasicFilter.startsWithOrMatchCamelCase("thisIsAPotentialResult", "thisisa"));
	}

	@Test
	public void test6() {
		assertTrue(BasicFilter.startsWithOrMatchCamelCase("thisIsAPotentialResult", "thisisA"));
	}

	@Test
	public void test7() {
		assertFalse(BasicFilter.startsWithOrMatchCamelCase("thisIsAPotentialResult", "tho"));
	}

	@Test
	public void test8() {
		assertFalse(BasicFilter.startsWithOrMatchCamelCase("thisIsAPotentialResult", "tIaP"));
	}

	@Test
	public void test9() {
		assertFalse(BasicFilter.startsWithOrMatchCamelCase("thisIsAPotentialResult", "tIApotential"));
	}

	@Test
	public void test10() {
		assertFalse(BasicFilter.startsWithOrMatchCamelCase("thisIsAPotentialResult", "TIA"));
	}
}
