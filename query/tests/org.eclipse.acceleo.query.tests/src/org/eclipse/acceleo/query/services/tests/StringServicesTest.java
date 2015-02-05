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

import junit.framework.AssertionFailedError;

import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.services.StringServices;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * StringServices class tests.
 * 
 * @author <a href="mailto:pierre.guilet@obeo.fr">Pierre Guilet</a>
 */
public class StringServicesTest extends AbstractServicesTest {
	private StringServices stringServices;

	@Before
	public void setup() throws InvalidAcceleoPackageException {
		stringServices = new StringServices();
	}

	private void assertException(Exception e, Class<?> class1) throws AssertionFailedError {
		if (e.getClass().isAssignableFrom(class1)) {
			assertTrue(true);
		} else {
			throw new AssertionFailedError();
		}
	}

	@Test
	public void testConcat() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		assertEquals("" + "", stringServices.concat("", ""));
		assertEquals("a" + "", stringServices.concat("a", ""));
		assertEquals("" + "a", stringServices.concat("", "a"));
		assertEquals("aa" + "bb", stringServices.concat("aa", "bb"));

		assertEquals(null + "bb", stringServices.concat(null, "bb"));
		assertEquals("aa" + null, stringServices.concat("aa", null));
		String a = null;
		assertEquals(a + null, stringServices.concat(null, null));
	}

	@Test
	public void testPrefix() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		assertEquals("" + "", stringServices.prefix("", ""));
		assertEquals("" + "a", stringServices.prefix("a", ""));
		assertEquals("a" + "", stringServices.prefix("", "a"));
		assertEquals("bbaa", stringServices.prefix("aa", "bb"));

		assertEquals("bb" + null, stringServices.prefix(null, "bb"));
		assertEquals(null + "aa", stringServices.prefix("aa", null));
		String a = null;
		assertEquals(a + null, stringServices.prefix(null, null));
	}

	@Test
	public void testContains() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		assertEquals("".contains(""), stringServices.contains("", ""));
		assertEquals("a".contains(""), stringServices.contains("a", ""));
		assertEquals("".contains("a"), stringServices.contains("", "a"));
		assertEquals("aa".contains("bb"), stringServices.contains("aa", "bb"));
		assertEquals("aa".contains("aa"), stringServices.contains("aa", "aa"));
		assertEquals("baa".contains("aa"), stringServices.contains("baa", "aa"));
		assertEquals("aab".contains("aa"), stringServices.contains("aab", "aa"));
		assertEquals("baab".contains("aa"), stringServices.contains("baab", "aa"));

		try {
			stringServices.contains(null, "bb");
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
		try {
			stringServices.contains("aa", null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
		try {
			stringServices.contains(null, null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
	}

	@Test
	public void testMatches() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		assertEquals("".matches(""), stringServices.matches("", ""));
		assertEquals("a".matches(""), stringServices.matches("a", ""));
		assertEquals("".matches("a"), stringServices.matches("", "a"));
		assertEquals("aa".matches("bb"), stringServices.matches("aa", "bb"));
		assertEquals("baa".matches("aa"), stringServices.matches("baa", "aa"));
		assertEquals("aab".matches("aa"), stringServices.matches("aab", "aa"));
		assertEquals("baab".matches("aa"), stringServices.matches("baab", "aa"));

		try {
			stringServices.matches(null, "bb");
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
		try {
			stringServices.matches("aa", null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
		try {
			stringServices.matches(null, null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
	}

	@Test
	public void testEndsWith() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		assertEquals("".endsWith(""), stringServices.endsWith("", ""));
		assertEquals("a".endsWith(""), stringServices.endsWith("a", ""));
		assertEquals("".endsWith("a"), stringServices.endsWith("", "a"));
		assertEquals("aa".endsWith("bb"), stringServices.endsWith("aa", "bb"));
		assertEquals("ab".endsWith("ab"), stringServices.endsWith("ab", "ab"));
		assertEquals("ab".endsWith("b"), stringServices.endsWith("ab", "b"));
		assertEquals("aba".endsWith("b"), stringServices.endsWith("aba", "b"));
		assertEquals("ba".endsWith("b"), stringServices.endsWith("ba", "b"));

		try {
			stringServices.endsWith(null, "bb");
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
		try {
			stringServices.endsWith("aa", null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
		try {
			stringServices.endsWith(null, null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
	}

	@Test
	public void testStartsWith() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		assertEquals("".startsWith(""), stringServices.startsWith("", ""));
		assertEquals("a".startsWith(""), stringServices.startsWith("a", ""));
		assertEquals("".startsWith("a"), stringServices.startsWith("", "a"));
		assertEquals("aa".startsWith("bb"), stringServices.startsWith("aa", "bb"));
		assertEquals("ab".startsWith("ab"), stringServices.startsWith("ab", "ab"));
		assertEquals("ab".startsWith("b"), stringServices.startsWith("ab", "b"));
		assertEquals("aba".startsWith("b"), stringServices.startsWith("aba", "b"));
		assertEquals("ba".startsWith("b"), stringServices.startsWith("ba", "b"));

		try {
			stringServices.startsWith(null, "bb");
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
		try {
			stringServices.startsWith("aa", null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
		try {
			stringServices.startsWith(null, null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
	}

	@Test
	public void testEqualsIgnoreCase() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		assertEquals("".equalsIgnoreCase(""), stringServices.equalsIgnoreCase("", ""));
		assertEquals("a".equalsIgnoreCase(""), stringServices.equalsIgnoreCase("a", ""));
		assertEquals("".equalsIgnoreCase("a"), stringServices.equalsIgnoreCase("", "a"));
		assertEquals("a".equalsIgnoreCase("a"), stringServices.equalsIgnoreCase("a", "a"));
		assertEquals("a".equalsIgnoreCase("A"), stringServices.equalsIgnoreCase("a", "A"));
		assertEquals("ab".equalsIgnoreCase("aB"), stringServices.equalsIgnoreCase("ab", "aB"));
		assertEquals("aB".equalsIgnoreCase("aB"), stringServices.equalsIgnoreCase("aB", "aB"));
		assertEquals("A".equalsIgnoreCase("A"), stringServices.equalsIgnoreCase("A", "A"));
		assertEquals("aa".equalsIgnoreCase(null), stringServices.equalsIgnoreCase("aa", null));

		try {
			stringServices.equalsIgnoreCase(null, "bb");
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
		try {
			stringServices.equalsIgnoreCase(null, null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
	}

	@Test
	public void testFirst() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		assertEquals("", stringServices.first("", Integer.valueOf(2)));
		assertEquals("", stringServices.first("", Integer.valueOf(0)));
		assertEquals("", stringServices.first("ab", Integer.valueOf(0)));
		assertEquals("a", stringServices.first("ab", Integer.valueOf(1)));
		assertEquals("ab", stringServices.first("ab", Integer.valueOf(2)));
		assertEquals("ab", stringServices.first("ab", Integer.valueOf(3)));

		try {
			stringServices.first(null, Integer.valueOf(0));
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
		try {
			stringServices.first("aa", null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.first(null, null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.first("ab", Integer.valueOf(-1));
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, StringIndexOutOfBoundsException.class);
		}
	}

	@Test
	public void testLast() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		assertEquals("", stringServices.last("", Integer.valueOf(2)));
		assertEquals("", stringServices.last("", Integer.valueOf(0)));
		assertEquals("", stringServices.last("ab", Integer.valueOf(0)));
		assertEquals("b", stringServices.last("ab", Integer.valueOf(1)));
		assertEquals("ab", stringServices.last("ab", Integer.valueOf(2)));
		assertEquals("ab", stringServices.last("ab", Integer.valueOf(3)));

		try {
			stringServices.last(null, Integer.valueOf(0));
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
		try {
			stringServices.last("aa", null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.last(null, null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.last("ab", Integer.valueOf(-1));
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, IllegalArgumentException.class);
		}
	}

	@Test
	public void testLastIndex() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		assertEquals(Integer.valueOf(1), stringServices.lastIndex("", ""));
		assertEquals(Integer.valueOf(3), stringServices.lastIndex("aa", ""));
		assertEquals(Integer.valueOf(-1), stringServices.lastIndex("", "aa"));
		assertEquals(Integer.valueOf(1), stringServices.lastIndex("aab", "aa"));
		assertEquals(Integer.valueOf(2), stringServices.lastIndex("aab", "ab"));
		assertEquals(Integer.valueOf(3), stringServices.lastIndex("aab", "b"));
		assertEquals(Integer.valueOf(2), stringServices.lastIndex("aab", "a"));
		assertEquals(Integer.valueOf(1), stringServices.lastIndex("abc", "a"));
		assertEquals(Integer.valueOf(3), stringServices.lastIndex("abab", "ab"));

		try {
			stringServices.lastIndex(null, "");
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
		try {
			stringServices.lastIndex("aa", null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.lastIndex(null, null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
	}

	@Test
	public void testLastIndexFromIndex() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		assertEquals(Integer.valueOf(-1), stringServices.lastIndex("", "", -5));
		assertEquals(Integer.valueOf(1), stringServices.lastIndex("", "", 3));
		assertEquals(Integer.valueOf(-1), stringServices.lastIndex("ab", "a", -5));
		assertEquals(Integer.valueOf(1), stringServices.lastIndex("ab", "a", 0));
		assertEquals(Integer.valueOf(-1), stringServices.lastIndex("ab", "b", 0));
		assertEquals(Integer.valueOf(2), stringServices.lastIndex("ab", "b", 1));
		assertEquals(Integer.valueOf(2), stringServices.lastIndex("ab", "b", 2));
		assertEquals(Integer.valueOf(1), stringServices.lastIndex("ab", "a", 1));
		assertEquals(Integer.valueOf(1), stringServices.lastIndex("ab", "a", 2));
		assertEquals(Integer.valueOf(-1), stringServices.lastIndex("", "aa", 0));
		assertEquals(Integer.valueOf(-1), stringServices.lastIndex("", "aa", -1));
		assertEquals(Integer.valueOf(-1), stringServices.lastIndex("", "aa", -2));
		assertEquals(Integer.valueOf(1), stringServices.lastIndex("abab", "ab", 0));
		assertEquals(Integer.valueOf(3), stringServices.lastIndex("abab", "ab", 2));
		try {
			stringServices.lastIndex(null, "", 1);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
		try {
			stringServices.lastIndex("aa", null, 1);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
		try {
			stringServices.lastIndex("aa", "", null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.lastIndex(null, null, 1);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.lastIndex(null, "", null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.lastIndex("", null, null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.lastIndex(null, null, null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

	}

	@Test
	public void testIndex() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		assertEquals(Integer.valueOf(1), stringServices.index("", ""));
		assertEquals(Integer.valueOf(1), stringServices.index("aa", ""));
		assertEquals(Integer.valueOf(-1), stringServices.index("", "aa"));
		assertEquals(Integer.valueOf(1), stringServices.index("aab", "aa"));
		assertEquals(Integer.valueOf(2), stringServices.index("aab", "ab"));
		assertEquals(Integer.valueOf(3), stringServices.index("aab", "b"));

		try {
			stringServices.index(null, "");
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
		try {
			stringServices.index("aa", null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.index(null, null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

	}

	@Test
	public void testIndexFromIndex() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		assertEquals(Integer.valueOf(1), stringServices.index("", "", -5));
		assertEquals(Integer.valueOf(1), stringServices.index("", "", 3));
		assertEquals(Integer.valueOf(1), stringServices.index("ab", "a", -5));
		assertEquals(Integer.valueOf(1), stringServices.index("ab", "a", 0));
		assertEquals(Integer.valueOf(-1), stringServices.index("ab", "a", 1));
		assertEquals(Integer.valueOf(-1), stringServices.index("", "aa", 0));
		assertEquals(Integer.valueOf(-1), stringServices.index("", "aa", -1));
		assertEquals(Integer.valueOf(-1), stringServices.index("", "aa", -2));

		try {
			stringServices.index(null, "", 1);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
		try {
			stringServices.index("aa", null, 1);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
		try {
			stringServices.index("aa", "", null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.index(null, null, 1);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.index(null, "", null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.index("", null, null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.index(null, null, null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

	}

	@Test
	public void testToLower() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		assertEquals("".toLowerCase(), stringServices.toLower(""));
		assertEquals("aa".toLowerCase(), stringServices.toLower("aa"));
		assertEquals("Aa".toLowerCase(), stringServices.toLower("Aa"));
		assertEquals("aA".toLowerCase(), stringServices.toLower("aA"));
		assertEquals("AA".toLowerCase(), stringServices.toLower("AA"));
		assertEquals("A�".toLowerCase(), stringServices.toLower("A�"));

		try {
			stringServices.toLower(null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
	}

	@Test
	public void testToLowerFirst() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		assertEquals("", stringServices.toLowerFirst(""));
		assertEquals("a", stringServices.toLowerFirst("a"));
		assertEquals("a", stringServices.toLowerFirst("A"));
		assertEquals("r", stringServices.toLowerFirst("\u0052"));
		assertEquals("aa", stringServices.toLowerFirst("aa"));
		assertEquals("aa", stringServices.toLowerFirst("Aa"));
		assertEquals("aA", stringServices.toLowerFirst("aA"));
		assertEquals("aA", stringServices.toLowerFirst("AA"));
		assertEquals("a�", stringServices.toLowerFirst("A�"));
		assertEquals("rA", stringServices.toLowerFirst("\u0052A"));

		try {
			stringServices.toLowerFirst(null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
	}

	@Test
	public void testToUpper() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		assertEquals("".toUpperCase(), stringServices.toUpper(""));
		assertEquals("aa".toUpperCase(), stringServices.toUpper("aa"));
		assertEquals("Aa".toUpperCase(), stringServices.toUpper("Aa"));
		assertEquals("aA".toUpperCase(), stringServices.toUpper("aA"));
		assertEquals("AA".toUpperCase(), stringServices.toUpper("AA"));
		assertEquals("A�".toUpperCase(), stringServices.toUpper("A�"));

		try {
			stringServices.toUpper(null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
	}

	@Test
	public void testToUpperFirst() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {

		assertEquals("", stringServices.toUpperFirst(""));
		assertEquals("A", stringServices.toUpperFirst("a"));
		assertEquals("A", stringServices.toUpperFirst("A"));
		assertEquals("R", stringServices.toUpperFirst("\u0052"));
		assertEquals("Aa", stringServices.toUpperFirst("aa"));
		assertEquals("Aa", stringServices.toUpperFirst("Aa"));
		assertEquals("AA", stringServices.toUpperFirst("aA"));
		assertEquals("AA", stringServices.toUpperFirst("AA"));
		assertEquals("A�", stringServices.toUpperFirst("A�"));
		assertEquals("RA", stringServices.toUpperFirst("\u0052A"));

		try {
			stringServices.toUpperFirst(null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
	}

	@Test
	public void testAdd() {
		assertEquals("string1 string2", stringServices.add("string1 ", "string2"));
		assertEquals("nullstring2", stringServices.add(null, "string2"));
		assertEquals("string1 null", stringServices.add("string1 ", null));
	}

	@Test
	public void testIsAlpha() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		assertTrue(stringServices.isAlpha(""));
		assertTrue(stringServices.isAlpha("a"));
		assertTrue(stringServices.isAlpha("aa"));
		assertTrue(stringServices.isAlpha("\u00E0"));
		assertFalse(stringServices.isAlpha("a5"));
		assertFalse(stringServices.isAlpha("5"));
		assertFalse(stringServices.isAlpha("\u0020"));
		assertFalse(stringServices.isAlpha("\u0020a"));

		try {
			stringServices.isAlpha(null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
	}

	@Test
	public void testIsAlphaNum() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		assertTrue(stringServices.isAlphaNum("\u00e9\u00e8"));
		assertTrue(stringServices.isAlphaNum("a"));
		assertTrue(stringServices.isAlphaNum("aa"));
		assertTrue(stringServices.isAlphaNum("\u00E0"));
		assertTrue(stringServices.isAlphaNum("a5"));
		assertTrue(stringServices.isAlphaNum("5"));
		assertFalse(stringServices.isAlphaNum("\u0020"));
		assertFalse(stringServices.isAlphaNum("\u0020a"));

		try {
			stringServices.isAlphaNum(null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
	}

	@Test
	public void testReplace() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		final String value = "start .*abc.* - .*abc.* end";
		String search = "(.*?)abc.*( end)";
		String replace = "$1 -$2";
		assertEquals(value.replaceFirst(search, replace), stringServices.replace(value, search, replace));

		assertEquals("", stringServices.replace("", "", ""));

		try {
			stringServices.replace(null, "", "");
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
		try {
			stringServices.replace("aa", null, "");
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
		try {
			stringServices.replace("aa", "", null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.replace(null, null, "");
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.replace(null, "", null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.replace("", null, null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.replace(null, null, null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
	}

	@Test
	public void testSize() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		assertEquals((Integer)"".length(), stringServices.size(""));
		assertEquals((Integer)"a".length(), stringServices.size("b"));
		assertEquals((Integer)"ab".length(), stringServices.size("cd"));

		try {
			stringServices.size(null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
	}

	@Test
	public void testReplaceAll() throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		final String value = "start .*abc.* - .*abc.* end";
		String search = "(.*?)abc";
		String replace = "$1def";
		assertEquals(value.replaceAll(search, replace), stringServices.replaceAll(value, search, replace));

		assertEquals("", stringServices.replace("", "", ""));

		try {
			stringServices.replace(null, "", "");
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
		try {
			stringServices.replace("aa", null, "");
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
		try {
			stringServices.replace("aa", "", null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.replace(null, null, "");
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.replace(null, "", null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.replace("", null, null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}

		try {
			stringServices.replace(null, null, null);
			throw new AssertionFailedError();
		} catch (Exception e) {
			assertException(e, NullPointerException.class);
		}
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void substringNullString() {
		stringServices.substring(null, 1, 1);
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void substringLowerOutOfRange() {
		stringServices.substring("some string", 0, 1);
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void substringUpperOutOfRange() {
		stringServices.substring("some string", 1, 100);
	}

	@Test(expected = java.lang.IndexOutOfBoundsException.class)
	public void substringInversedBounds() {
		stringServices.substring("some string", 7, 4);
	}

	@Test
	public void substring() {
		String result = stringServices.substring("some string", 4, 7);
		assertEquals("some string".substring(3, 7), result);
	}

	@Test
	public void substring_1() {
		String result = stringServices.substring("substring operation", 11, 19);
		assertEquals("operation", result);
	}

	@Test
	public void substring_2() {
		String result = stringServices.substring("substring operation", 1, 1);
		assertEquals("s", result);
	}
}
