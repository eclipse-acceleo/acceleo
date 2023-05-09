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

import java.util.List;

import org.eclipse.acceleo.query.services.StringServices;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * StringServices class tests.
 * 
 * @author <a href="mailto:pierre.guilet@obeo.fr">Pierre Guilet</a>
 */
public class StringServicesTest extends AbstractServicesTest {

	private StringServices stringServices;

	@Override
	public void before() throws Exception {
		super.before();
		stringServices = new StringServices();
	}

	@Test
	public void addNullNull() {
		assertEquals("", stringServices.add(null, null));
	}

	@Test
	public void addNullEmpty() {
		assertEquals("", stringServices.add(null, ""));
	}

	@Test
	public void addEmptyNull() {
		assertEquals("", stringServices.add("", null));
	}

	@Test
	public void addEmptyEmpty() {
		assertEquals("", stringServices.add("", ""));
	}

	@Test
	public void addEmptyString() {
		assertEquals("string", stringServices.add("", "string"));
	}

	@Test
	public void addStringEmpty() {
		assertEquals("string", stringServices.add("string", ""));
	}

	@Test
	public void addNullString() {
		assertEquals("string", stringServices.add(null, "string"));
	}

	@Test
	public void addStringNull() {
		assertEquals("string", stringServices.add("string", null));
	}

	@Test
	public void addStringString() {
		assertEquals("stringstring", stringServices.add("string", "string"));
	}

	@Test
	public void concatNullNull() {
		assertEquals("", stringServices.concat(null, null));
	}

	@Test
	public void concatNullEmpty() {
		assertEquals("", stringServices.concat(null, ""));
	}

	@Test
	public void concatEmptyNull() {
		assertEquals("", stringServices.concat("", null));
	}

	@Test
	public void concatEmptyEmpty() {
		assertEquals("", stringServices.concat("", ""));
	}

	@Test
	public void concatEmptyString() {
		assertEquals("string", stringServices.concat("", "string"));
	}

	@Test
	public void concatStringEmpty() {
		assertEquals("string", stringServices.concat("string", ""));
	}

	@Test
	public void concatNullString() {
		assertEquals("string", stringServices.concat(null, "string"));
	}

	@Test
	public void concatStringNull() {
		assertEquals("string", stringServices.concat("string", null));
	}

	@Test
	public void concatStringString() {
		assertEquals("stringstring", stringServices.concat("string", "string"));
	}

	@Test
	public void containsNullNull() {
		assertEquals(true, stringServices.contains(null, null));
	}

	@Test
	public void containsNullEmpty() {
		assertEquals(true, stringServices.contains(null, ""));
	}

	@Test
	public void containsEmptyNull() {
		assertEquals(true, stringServices.contains("", null));
	}

	@Test
	public void containsEmptyEmpty() {
		assertEquals(true, stringServices.contains("", ""));
	}

	@Test
	public void containsEmptyString() {
		assertEquals(false, stringServices.contains("", "string"));
	}

	@Test
	public void containsStringEmpty() {
		assertEquals(true, stringServices.contains("string", ""));
	}

	@Test
	public void containsNullString() {
		assertEquals(false, stringServices.contains(null, "string"));
	}

	@Test
	public void containsStringNull() {
		assertEquals(true, stringServices.contains("string", null));
	}

	@Test
	public void containsAllString() {
		assertEquals(true, stringServices.contains("string", "string"));
	}

	@Test
	public void containsSubString() {
		assertEquals(true, stringServices.contains("string", "in"));
	}

	@Test
	public void containsFlase() {
		assertEquals(false, stringServices.contains("string", "fr"));
	}

	@Test
	public void endsWithNullNull() {
		assertEquals(true, stringServices.endsWith(null, null));
	}

	@Test
	public void endsWithNullEmpty() {
		assertEquals(true, stringServices.endsWith(null, ""));
	}

	@Test
	public void endsWithEmptyNull() {
		assertEquals(true, stringServices.endsWith("", null));
	}

	@Test
	public void endsWithEmptyEmpty() {
		assertEquals(true, stringServices.endsWith("", ""));
	}

	@Test
	public void endsWithEmptyString() {
		assertEquals(false, stringServices.endsWith("", "string"));
	}

	@Test
	public void endsWithStringEmpty() {
		assertEquals(true, stringServices.endsWith("string", ""));
	}

	@Test
	public void endsWithNullString() {
		assertEquals(false, stringServices.endsWith(null, "string"));
	}

	@Test
	public void endsWithStringNull() {
		assertEquals(true, stringServices.endsWith("string", null));
	}

	@Test
	public void endsWithStringString() {
		assertEquals(true, stringServices.endsWith("string", "string"));
	}

	@Test
	public void endsWithFalse() {
		assertEquals(false, stringServices.endsWith("stringa", "string"));
	}

	@Test
	public void equalsIgnoreCaseNullNull() {
		assertEquals(true, stringServices.equalsIgnoreCase(null, null));
	}

	@Test
	public void equalsIgnoreCaseNullEmpty() {
		assertEquals(true, stringServices.equalsIgnoreCase(null, ""));
	}

	@Test
	public void equalsIgnoreCaseEmptyNull() {
		assertEquals(true, stringServices.equalsIgnoreCase("", null));
	}

	@Test
	public void equalsIgnoreCaseEmptyEmpty() {
		assertEquals(true, stringServices.equalsIgnoreCase("", ""));
	}

	@Test
	public void equalsIgnoreCaseEmptyString() {
		assertEquals(false, stringServices.equalsIgnoreCase("", "string"));
	}

	@Test
	public void equalsIgnoreCaseStringEmpty() {
		assertEquals(false, stringServices.equalsIgnoreCase("string", ""));
	}

	@Test
	public void equalsIgnoreCaseNullString() {
		assertEquals(false, stringServices.equalsIgnoreCase(null, "string"));
	}

	@Test
	public void equalsIgnoreCaseStringNull() {
		assertEquals(false, stringServices.equalsIgnoreCase("string", null));
	}

	@Test
	public void equalsIgnoreCaseStringString() {
		assertEquals(true, stringServices.equalsIgnoreCase("strIng", "strinG"));
	}

	@Test
	public void firstNullNull() {
		assertEquals(null, stringServices.first(null, null));
	}

	@Test(expected = NullPointerException.class)
	public void firstEmptyNull() {
		stringServices.first("", null);
	}

	@Test(expected = NullPointerException.class)
	public void firstStringNull() {
		stringServices.first("string", null);
	}

	@Test(expected = StringIndexOutOfBoundsException.class)
	public void firstStringNegative() {
		stringServices.first("string", -10);
	}

	@Test
	public void firstStringInRange() {
		assertEquals("str", stringServices.first("string", 3));
	}

	@Test
	public void firstStringOverRange() {
		assertEquals("string", stringServices.first("string", 300));
	}

	@Test
	public void indexNullNull() {
		assertEquals(Integer.valueOf(1), stringServices.index(null, null));
	}

	@Test
	public void indexNullEmpty() {
		assertEquals(Integer.valueOf(1), stringServices.index(null, ""));
	}

	@Test
	public void indexEmptyNull() {
		assertEquals(Integer.valueOf(1), stringServices.index("", null));
	}

	@Test
	public void indexEmptyEmpty() {
		assertEquals(Integer.valueOf(1), stringServices.index("", ""));
	}

	@Test
	public void indexEmptyString() {
		assertEquals(Integer.valueOf(-1), stringServices.index("", "string"));
	}

	@Test
	public void indexStringEmpty() {
		assertEquals(Integer.valueOf(1), stringServices.index("string", ""));
	}

	@Test
	public void indexNullString() {
		assertEquals(Integer.valueOf(-1), stringServices.index(null, "string"));
	}

	@Test
	public void indexStringNull() {
		assertEquals(Integer.valueOf(1), stringServices.index("string", null));
	}

	@Test
	public void indexStringString() {
		assertEquals(Integer.valueOf(1), stringServices.index("string", "string"));
	}

	@Test
	public void index() {
		assertEquals(Integer.valueOf(4), stringServices.index("string", "ing"));
	}

	@Test(expected = NullPointerException.class)
	public void indexNullNullNull() {
		stringServices.index(null, null, null);
	}

	@Test(expected = NullPointerException.class)
	public void indexNullEmptyNull() {
		stringServices.index(null, "", null);
	}

	@Test(expected = NullPointerException.class)
	public void indexEmptyNullNull() {
		stringServices.index("", null, null);
	}

	@Test(expected = NullPointerException.class)
	public void indexEmptyEmptyNull() {
		stringServices.index("", "", null);
	}

	@Test(expected = NullPointerException.class)
	public void indexEmptyStringNull() {
		stringServices.index("", "string", null);
	}

	@Test(expected = NullPointerException.class)
	public void indexStringEmptyNull() {
		stringServices.index("string", "", null);
	}

	@Test(expected = NullPointerException.class)
	public void indexNullStringNull() {
		stringServices.index(null, "string", null);
	}

	@Test(expected = NullPointerException.class)
	public void indexStringNullNull() {
		stringServices.index("string", null, null);
	}

	@Test(expected = NullPointerException.class)
	public void indexStringStringNull() {
		stringServices.index("string", "string", null);
	}

	@Test(expected = NullPointerException.class)
	public void indexNull() {
		stringServices.index("string", "ing", null);
	}

	@Test
	public void indexNegativeStartPosition() {
		assertEquals(Integer.valueOf(4), stringServices.index("string", "ing", -10));
	}

	@Test
	public void indexOutOfRangeStartPosition() {
		assertEquals(Integer.valueOf(-1), stringServices.index("string", "ing", 100));
	}

	@Test
	public void indexStartPosition() {
		assertEquals(Integer.valueOf(10), stringServices.index("stringstring", "ing", 4));
	}

	@Test
	public void isAlphaNull() {
		assertEquals(false, stringServices.isAlpha(null));
	}

	@Test
	public void isAlphaEmpty() {
		assertEquals(true, stringServices.isAlpha(""));
	}

	@Test
	public void isAlphaTrue() {
		assertEquals(true, stringServices.isAlpha("abcD"));
	}

	@Test
	public void isAlphaFalse() {
		assertEquals(false, stringServices.isAlpha("abcD1"));
	}

	@Test
	public void isAlphaNumNull() {
		assertEquals(false, stringServices.isAlphaNum(null));
	}

	@Test
	public void isAlphaNumEmpty() {
		assertEquals(true, stringServices.isAlphaNum(""));
	}

	@Test
	public void isAlphaNumTrue() {
		assertEquals(true, stringServices.isAlphaNum("abcD1"));
	}

	@Test
	public void isAlphaNumFalse() {
		assertEquals(false, stringServices.isAlphaNum("abcD1_"));
	}

	@Test
	public void lastNullNull() {
		assertEquals(null, stringServices.last(null, null));
	}

	@Test(expected = NullPointerException.class)
	public void lastEmptyNull() {
		stringServices.last("", null);
	}

	@Test(expected = NullPointerException.class)
	public void lastStringNull() {
		stringServices.last("string", null);
	}

	@Test(expected = StringIndexOutOfBoundsException.class)
	public void lastStringNegative() {
		stringServices.last("string", -10);
	}

	@Test
	public void lastStringInRange() {
		assertEquals("ng", stringServices.last("string", 2));
	}

	@Test
	public void lastStringOverRange() {
		assertEquals("string", stringServices.last("string", 300));
	}

	@Test
	public void lastIndexNullNull() {
		assertEquals(Integer.valueOf(1), stringServices.lastIndex(null, null));
	}

	@Test
	public void lastIndexNullEmpty() {
		assertEquals(Integer.valueOf(1), stringServices.lastIndex(null, ""));
	}

	@Test
	public void lastIndexEmptyNull() {
		assertEquals(Integer.valueOf(1), stringServices.lastIndex("", null));
	}

	@Test
	public void lastIndexEmptyEmpty() {
		assertEquals(Integer.valueOf(1), stringServices.lastIndex("", ""));
	}

	@Test
	public void lastIndexEmptyString() {
		assertEquals(Integer.valueOf(-1), stringServices.lastIndex("", "string"));
	}

	@Test
	public void lastIndexStringEmpty() {
		assertEquals(Integer.valueOf(7), stringServices.lastIndex("string", ""));
	}

	@Test
	public void lastIndexNullString() {
		assertEquals(Integer.valueOf(-1), stringServices.lastIndex(null, "string"));
	}

	@Test
	public void lastIndexStringNull() {
		assertEquals(Integer.valueOf(7), stringServices.lastIndex("string", null));
	}

	@Test
	public void lastIndexStringString() {
		assertEquals(Integer.valueOf(1), stringServices.lastIndex("string", "string"));
	}

	@Test
	public void lastIndex() {
		assertEquals(Integer.valueOf(4), stringServices.lastIndex("string", "ing"));
	}

	@Test(expected = NullPointerException.class)
	public void lastIndexNullNullNull() {
		stringServices.lastIndex(null, null, null);
	}

	@Test(expected = NullPointerException.class)
	public void lastIndexNullEmptyNull() {
		stringServices.lastIndex(null, "", null);
	}

	@Test(expected = NullPointerException.class)
	public void lastIndexEmptyNullNull() {
		stringServices.lastIndex("", null, null);
	}

	@Test(expected = NullPointerException.class)
	public void lastIndexEmptyEmptyNull() {
		stringServices.lastIndex("", "", null);
	}

	@Test(expected = NullPointerException.class)
	public void lastIndexEmptyStringNull() {
		stringServices.lastIndex("", "string", null);
	}

	@Test(expected = NullPointerException.class)
	public void lastIndexStringEmptyNull() {
		stringServices.lastIndex("string", "", null);
	}

	@Test(expected = NullPointerException.class)
	public void lastIndexNullStringNull() {
		stringServices.lastIndex(null, "string", null);
	}

	@Test(expected = NullPointerException.class)
	public void lastIndexStringNullNull() {
		stringServices.lastIndex("string", null, null);
	}

	@Test(expected = NullPointerException.class)
	public void lastIndexStringStringNull() {
		stringServices.lastIndex("string", "string", null);
	}

	@Test(expected = NullPointerException.class)
	public void lastIndexNull() {
		stringServices.lastIndex("string", "ing", null);
	}

	@Test
	public void lastIndexNegativeStartPosition() {
		assertEquals(Integer.valueOf(-1), stringServices.lastIndex("string", "ing", -10));
	}

	@Test
	public void lastIndexOutOfRangeStartPosition() {
		assertEquals(Integer.valueOf(4), stringServices.lastIndex("string", "ing", 100));
	}

	@Test
	public void lastIndexStartPosition() {
		assertEquals(Integer.valueOf(4), stringServices.lastIndex("stringstring", "ing", 4));
	}

	@Test
	public void matchesNullNull() {
		assertEquals(true, stringServices.matches(null, null));
	}

	@Test
	public void matchesNullEmpty() {
		assertEquals(true, stringServices.matches(null, ""));
	}

	@Test
	public void matchesEmptyNull() {
		assertEquals(true, stringServices.matches("", null));
	}

	@Test
	public void matchesEmptyEmpty() {
		assertEquals(true, stringServices.matches("", ""));
	}

	@Test
	public void matchesEmptyString() {
		assertEquals(false, stringServices.matches("", "string"));
	}

	@Test
	public void matchesStringEmpty() {
		assertEquals(false, stringServices.matches("string", ""));
	}

	@Test
	public void matchesNullString() {
		assertEquals(false, stringServices.matches(null, "string"));
	}

	@Test
	public void matchesStringNull() {
		assertEquals(false, stringServices.matches("string", null));
	}

	@Test
	public void matchesStringString() {
		assertEquals(true, stringServices.matches("string", "string"));
	}

	@Test
	public void matchesTrue() {
		assertEquals(true, stringServices.matches("string", "s.ri(n|g)+"));
	}

	@Test
	public void matchesFalse() {
		assertEquals(false, stringServices.matches("string", "s.ri(t|g)+"));
	}

	@Test
	public void prefixNullNull() {
		assertEquals("", stringServices.prefix(null, null));
	}

	@Test
	public void prefixNullEmpty() {
		assertEquals("", stringServices.prefix(null, ""));
	}

	@Test
	public void prefixEmptyNull() {
		assertEquals("", stringServices.prefix("", null));
	}

	@Test
	public void prefixEmptyEmpty() {
		assertEquals("", stringServices.prefix("", ""));
	}

	@Test
	public void prefixEmptyString() {
		assertEquals("string", stringServices.prefix("", "string"));
	}

	@Test
	public void prefixStringEmpty() {
		assertEquals("string", stringServices.prefix("string", ""));
	}

	@Test
	public void prefixNullString() {
		assertEquals("string", stringServices.prefix(null, "string"));
	}

	@Test
	public void prefixStringNull() {
		assertEquals("string", stringServices.prefix("string", null));
	}

	@Test
	public void prefixStringString() {
		assertEquals("bbbaaa", stringServices.prefix("aaa", "bbb"));
	}

	@Test
	public void replaceNullNullNull() {
		assertEquals("", stringServices.replace(null, null, null));
	}

	@Test
	public void replaceNullEmptyNull() {
		assertEquals("", stringServices.replace(null, "", null));
	}

	@Test
	public void replaceEmptyNullNull() {
		assertEquals("", stringServices.replace("", null, null));
	}

	@Test
	public void replaceEmptyEmptyNull() {
		assertEquals("", stringServices.replace("", "", null));
	}

	@Test
	public void replaceEmptyStringNull() {
		assertEquals("", stringServices.replace("", "string", null));
	}

	@Test
	public void replaceStringEmptyNull() {
		assertEquals("string", stringServices.replace("string", "", null));
	}

	@Test
	public void replaceNullStringNull() {
		assertEquals("", stringServices.replace(null, "string", null));
	}

	@Test
	public void replaceStringNullNull() {
		assertEquals("string", stringServices.replace("string", null, null));
	}

	@Test
	public void replaceStringStringNull() {
		assertEquals("", stringServices.replace("string", "string", null));
	}

	@Test
	public void replaceNull() {
		assertEquals("str", stringServices.replace("string", "ing", null));
	}

	@Test
	public void replaceNullNullEmpty() {
		assertEquals("", stringServices.replace(null, null, ""));
	}

	@Test
	public void replaceNullEmptyEmpty() {
		assertEquals("", stringServices.replace(null, "", ""));
	}

	@Test
	public void replaceEmptyNullEmpty() {
		assertEquals("", stringServices.replace("", null, ""));
	}

	@Test
	public void replaceEmptyEmptyEmpty() {
		assertEquals("", stringServices.replace("", "", ""));
	}

	@Test
	public void replaceEmptyStringEmpty() {
		assertEquals("", stringServices.replace("", "string", ""));
	}

	@Test
	public void replaceStringEmptyEmpty() {
		assertEquals("string", stringServices.replace("string", "", ""));
	}

	@Test
	public void replaceNullStringEmpty() {
		assertEquals("", stringServices.replace(null, "string", ""));
	}

	@Test
	public void replaceStringNullEmpty() {
		assertEquals("string", stringServices.replace("string", null, ""));
	}

	@Test
	public void replaceStringStringEmpty() {
		assertEquals("", stringServices.replace("string", "string", ""));
	}

	@Test
	public void replaceEmpty() {
		assertEquals("str", stringServices.replace("string", "ing", ""));
	}

	@Test
	public void replace() {
		assertEquals("strremplacement and string", stringServices.replace("string and string", "i[nz]g",
				"remplacement"));
	}

	@Test
	public void replaceAllNullNullNull() {
		assertEquals("", stringServices.replaceAll(null, null, null));
	}

	@Test
	public void replaceAllNullEmptyNull() {
		assertEquals("", stringServices.replaceAll(null, "", null));
	}

	@Test
	public void replaceAllEmptyNullNull() {
		assertEquals("", stringServices.replaceAll("", null, null));
	}

	@Test
	public void replaceAllEmptyEmptyNull() {
		assertEquals("", stringServices.replaceAll("", "", null));
	}

	@Test
	public void replaceAllEmptyStringNull() {
		assertEquals("", stringServices.replaceAll("", "string", null));
	}

	@Test
	public void replaceAllStringEmptyNull() {
		assertEquals("string", stringServices.replaceAll("string", "", null));
	}

	@Test
	public void replaceAllNullStringNull() {
		assertEquals("", stringServices.replaceAll(null, "string", null));
	}

	@Test
	public void replaceAllStringNullNull() {
		assertEquals("string", stringServices.replaceAll("string", null, null));
	}

	@Test
	public void replaceAllStringStringNull() {
		assertEquals("", stringServices.replaceAll("string", "string", null));
	}

	@Test
	public void replaceAllNull() {
		assertEquals("str", stringServices.replaceAll("string", "ing", null));
	}

	@Test
	public void replaceAllNullNullEmpty() {
		assertEquals("", stringServices.replaceAll(null, null, ""));
	}

	@Test
	public void replaceAllNullEmptyEmpty() {
		assertEquals("", stringServices.replaceAll(null, "", ""));
	}

	@Test
	public void replaceAllEmptyNullEmpty() {
		assertEquals("", stringServices.replaceAll("", null, ""));
	}

	@Test
	public void replaceAllEmptyEmptyEmpty() {
		assertEquals("", stringServices.replaceAll("", "", ""));
	}

	@Test
	public void replaceAllEmptyStringEmpty() {
		assertEquals("", stringServices.replaceAll("", "string", ""));
	}

	@Test
	public void replaceAllStringEmptyEmpty() {
		assertEquals("string", stringServices.replaceAll("string", "", ""));
	}

	@Test
	public void replaceAllNullStringEmpty() {
		assertEquals("", stringServices.replaceAll(null, "string", ""));
	}

	@Test
	public void replaceAllStringNullEmpty() {
		assertEquals("string", stringServices.replaceAll("string", null, ""));
	}

	@Test
	public void replaceAllStringStringEmpty() {
		assertEquals("", stringServices.replaceAll("string", "string", ""));
	}

	@Test
	public void replaceAllEmpty() {
		assertEquals("str", stringServices.replaceAll("string", "ing", ""));
	}

	@Test
	public void replaceAll() {
		assertEquals("strremplacement and strremplacement", stringServices.replaceAll("string and string",
				"i[nz]g", "remplacement"));
	}

	@Test
	public void sizeNull() {
		assertEquals(Integer.valueOf(0), stringServices.size(null));
	}

	@Test
	public void sizeEmpty() {
		assertEquals(Integer.valueOf(0), stringServices.size(""));
	}

	@Test
	public void size() {
		assertEquals(Integer.valueOf(5), stringServices.size("acbde"));
	}

	@Test
	public void startsWithNullNull() {
		assertEquals(true, stringServices.startsWith(null, null));
	}

	@Test
	public void startsWithNullEmpty() {
		assertEquals(true, stringServices.startsWith(null, ""));
	}

	@Test
	public void startsWithEmptyNull() {
		assertEquals(true, stringServices.startsWith("", null));
	}

	@Test
	public void startsWithEmptyEmpty() {
		assertEquals(true, stringServices.startsWith("", ""));
	}

	@Test
	public void startsWithEmptyString() {
		assertEquals(false, stringServices.startsWith("", "string"));
	}

	@Test
	public void startsWithStringEmpty() {
		assertEquals(true, stringServices.startsWith("string", ""));
	}

	@Test
	public void startsWithNullString() {
		assertEquals(false, stringServices.startsWith(null, "string"));
	}

	@Test
	public void startsWithStringNull() {
		assertEquals(true, stringServices.startsWith("string", null));
	}

	@Test
	public void startsWithStringString() {
		assertEquals(true, stringServices.startsWith("string", "string"));
	}

	@Test
	public void startsWithFalse() {
		assertEquals(false, stringServices.startsWith("astring", "string"));
	}

	@Test
	public void strcmpNullNull() {
		assertEquals(Integer.valueOf(0), stringServices.strcmp(null, null));
	}

	@Test
	public void strcmpNullEmpty() {
		assertEquals(Integer.valueOf(0), stringServices.strcmp(null, ""));
	}

	@Test
	public void strcmpEmptyNull() {
		assertEquals(Integer.valueOf(0), stringServices.strcmp("", null));
	}

	@Test
	public void strcmpEmptyEmpty() {
		assertEquals(Integer.valueOf(0), stringServices.strcmp("", ""));
	}

	@Test
	public void strcmpEmptyString() {
		assertEquals(Integer.valueOf(-6), stringServices.strcmp("", "string"));
	}

	@Test
	public void strcmpStringEmpty() {
		assertEquals(Integer.valueOf(6), stringServices.strcmp("string", ""));
	}

	@Test
	public void strcmpNullString() {
		assertEquals(Integer.valueOf(-6), stringServices.strcmp(null, "string"));
	}

	@Test
	public void strcmpStringNull() {
		assertEquals(Integer.valueOf(6), stringServices.strcmp("string", null));
	}

	@Test
	public void strcmpZero() {
		assertEquals(Integer.valueOf(0), stringServices.strcmp("string", "string"));
	}

	@Test
	public void strcmp() {
		assertEquals(Integer.valueOf(-18), stringServices.strcmp("astring", "string"));
	}

	@Test
	public void strstrNullNull() {
		assertEquals(true, stringServices.strstr(null, null));
	}

	@Test
	public void strstrNullEmpty() {
		assertEquals(true, stringServices.strstr(null, ""));
	}

	@Test
	public void strstrEmptyNull() {
		assertEquals(true, stringServices.strstr("", null));
	}

	@Test
	public void strstrEmptyEmpty() {
		assertEquals(true, stringServices.strstr("", ""));
	}

	@Test
	public void strstrEmptyString() {
		assertEquals(false, stringServices.strstr("", "string"));
	}

	@Test
	public void strstrStringEmpty() {
		assertEquals(true, stringServices.strstr("string", ""));
	}

	@Test
	public void strstrNullString() {
		assertEquals(false, stringServices.strstr(null, "string"));
	}

	@Test
	public void strstrStringNull() {
		assertEquals(true, stringServices.strstr("string", null));
	}

	@Test
	public void strstrStringString() {
		assertEquals(true, stringServices.strstr("string", "string"));
	}

	@Test
	public void strstrFalse() {
		assertEquals(false, stringServices.strstr("astrrng", "string"));
	}

	@Test
	public void substituteNullNullNull() {
		assertEquals("", stringServices.substitute(null, null, null));
	}

	@Test
	public void substituteNullEmptyNull() {
		assertEquals("", stringServices.substitute(null, "", null));
	}

	@Test
	public void substituteEmptyNullNull() {
		assertEquals("", stringServices.substitute("", null, null));
	}

	@Test
	public void substituteEmptyEmptyNull() {
		assertEquals("", stringServices.substitute("", "", null));
	}

	@Test
	public void substituteEmptyStringNull() {
		assertEquals("", stringServices.substitute("", "string", null));
	}

	@Test
	public void substituteStringEmptyNull() {
		assertEquals("string", stringServices.substitute("string", "", null));
	}

	@Test
	public void substituteNullStringNull() {
		assertEquals("", stringServices.substitute(null, "string", null));
	}

	@Test
	public void substituteStringNullNull() {
		assertEquals("string", stringServices.substitute("string", null, null));
	}

	@Test
	public void substituteStringStringNull() {
		assertEquals("", stringServices.substitute("string", "string", null));
	}

	@Test
	public void substituteNull() {
		assertEquals("str", stringServices.substitute("string", "ing", null));
	}

	@Test
	public void substituteNullNullEmpty() {
		assertEquals("", stringServices.substitute(null, null, ""));
	}

	@Test
	public void substituteNullEmptyEmpty() {
		assertEquals("", stringServices.substitute(null, "", ""));
	}

	@Test
	public void substituteEmptyNullEmpty() {
		assertEquals("", stringServices.substitute("", null, ""));
	}

	@Test
	public void substituteEmptyEmptyEmpty() {
		assertEquals("", stringServices.substitute("", "", ""));
	}

	@Test
	public void substituteEmptyStringEmpty() {
		assertEquals("", stringServices.substitute("", "string", ""));
	}

	@Test
	public void substituteStringEmptyEmpty() {
		assertEquals("string", stringServices.substitute("string", "", ""));
	}

	@Test
	public void substituteNullStringEmpty() {
		assertEquals("", stringServices.substitute(null, "string", ""));
	}

	@Test
	public void substituteStringNullEmpty() {
		assertEquals("string", stringServices.substitute("string", null, ""));
	}

	@Test
	public void substituteStringStringEmpty() {
		assertEquals("", stringServices.substitute("string", "string", ""));
	}

	@Test
	public void substituteEmpty() {
		assertEquals("str", stringServices.substitute("string", "ing", ""));
	}

	@Test
	public void substitute() {
		assertEquals("strremplacement and string", stringServices.substitute("string and string", "ing",
				"remplacement"));
	}

	@Test
	public void substituteAllNullNullNull() {
		assertEquals("", stringServices.substituteAll(null, null, null));
	}

	@Test
	public void substituteAllNullEmptyNull() {
		assertEquals("", stringServices.substituteAll(null, "", null));
	}

	@Test
	public void substituteAllEmptyNullNull() {
		assertEquals("", stringServices.substituteAll("", null, null));
	}

	@Test
	public void substituteAllEmptyEmptyNull() {
		assertEquals("", stringServices.substituteAll("", "", null));
	}

	@Test
	public void substituteAllEmptyStringNull() {
		assertEquals("", stringServices.substituteAll("", "string", null));
	}

	@Test
	public void substituteAllStringEmptyNull() {
		assertEquals("string", stringServices.substituteAll("string", "", null));
	}

	@Test
	public void substituteAllNullStringNull() {
		assertEquals("", stringServices.substituteAll(null, "string", null));
	}

	@Test
	public void substituteAllStringNullNull() {
		assertEquals("string", stringServices.substituteAll("string", null, null));
	}

	@Test
	public void substituteAllStringStringNull() {
		assertEquals("", stringServices.substituteAll("string", "string", null));
	}

	@Test
	public void substituteAllNull() {
		assertEquals("str", stringServices.substituteAll("string", "ing", null));
	}

	@Test
	public void substituteAllNullNullEmpty() {
		assertEquals("", stringServices.substituteAll(null, null, ""));
	}

	@Test
	public void substituteAllNullEmptyEmpty() {
		assertEquals("", stringServices.substituteAll(null, "", ""));
	}

	@Test
	public void substituteAllEmptyNullEmpty() {
		assertEquals("", stringServices.substituteAll("", null, ""));
	}

	@Test
	public void substituteAllEmptyEmptyEmpty() {
		assertEquals("", stringServices.substituteAll("", "", ""));
	}

	@Test
	public void substituteAllEmptyStringEmpty() {
		assertEquals("", stringServices.substituteAll("", "string", ""));
	}

	@Test
	public void substituteAllStringEmptyEmpty() {
		assertEquals("string", stringServices.substituteAll("string", "", ""));
	}

	@Test
	public void substituteAllNullStringEmpty() {
		assertEquals("", stringServices.substituteAll(null, "string", ""));
	}

	@Test
	public void substituteAllStringNullEmpty() {
		assertEquals("string", stringServices.substituteAll("string", null, ""));
	}

	@Test
	public void substituteAllStringStringEmpty() {
		assertEquals("", stringServices.substituteAll("string", "string", ""));
	}

	@Test
	public void substituteAllEmpty() {
		assertEquals("str", stringServices.substituteAll("string", "ing", ""));
	}

	@Test
	public void substituteAll() {
		assertEquals("strremplacement and strremplacement", stringServices.substituteAll("string and string",
				"ing", "remplacement"));
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
	public void substringNullNull() {
		assertEquals(null, stringServices.substring(null, null));
	}

	@Test(expected = NullPointerException.class)
	public void substringEmptyNull() {
		assertEquals("", stringServices.substring("", null));
	}

	@Test
	public void substringNullNullNull() {
		assertEquals(null, stringServices.substring(null, null, null));
	}

	@Test(expected = NullPointerException.class)
	public void substringEmptyNullNull() {
		assertEquals("", stringServices.substring("", null, null));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void substringNegative() {
		assertEquals("", stringServices.substring("", -100, -10));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void substringOverRange() {
		assertEquals("", stringServices.substring("", 10, 100));
	}

	@Test
	public void substring() {
		String result = stringServices.substring("some string", 4, 7);
		assertEquals("some string".substring(3, 7), result);

		result = stringServices.substring("some string", 4);
		assertEquals("some string".substring(3), result);
	}

	@Test
	public void substringUpperBound() {
		String result = stringServices.substring("substring operation", 11, 19);
		assertEquals("operation", result);
	}

	@Test
	public void substringLowerBound() {
		String result = stringServices.substring("substring operation", 1, 1);
		assertEquals("s", result);
	}

	@Test(expected = java.lang.NumberFormatException.class)
	public void toIntegerNull() {
		stringServices.toInteger(null);
	}

	@Test(expected = java.lang.NumberFormatException.class)
	public void toIntegerRoundedReal() {
		stringServices.toInteger("3.0");
	}

	@Test(expected = java.lang.NumberFormatException.class)
	public void toIntegerReal() {
		stringServices.toInteger("3.14");
	}

	@Test(expected = java.lang.NumberFormatException.class)
	public void toIntegerInvalid() {
		stringServices.toInteger("toInteger");
	}

	@Test
	public void toInteger() {
		Integer result = stringServices.toInteger("4");
		assertEquals(Integer.valueOf(4), result);
	}

	@Test
	public void tokenizeSpaceNull() {
		assertArrayEquals(new String[] {}, stringServices.tokenize(null).toArray());
	}

	@Test
	public void tokenizeSpaceEmpty() {
		assertArrayEquals(new String[] {}, stringServices.tokenize("").toArray());
	}

	@Test
	public void tokenizeSpace() {
		assertArrayEquals(new String[] {"a", "b", "c", "d" }, stringServices.tokenize("a b c d").toArray());
	}

	@Test
	public void tokenizeNullNull() {
		assertArrayEquals(new String[] {}, stringServices.tokenize(null, null).toArray());
	}

	@Test
	public void tokenizeNullEmpty() {
		assertArrayEquals(new String[] {}, stringServices.tokenize(null, "").toArray());
	}

	@Test
	public void tokenizeEmptyNull() {
		assertArrayEquals(new String[] {}, stringServices.tokenize("", null).toArray());
	}

	@Test
	public void tokenizeEmptyEmpty() {
		assertArrayEquals(new String[] {}, stringServices.tokenize("", "").toArray());
	}

	@Test
	public void tokenizeEmptyString() {
		assertArrayEquals(new String[] {}, stringServices.tokenize("", "string").toArray());
	}

	@Test
	public void tokenizeStringEmpty() {
		assertArrayEquals(new String[] {"string" }, stringServices.tokenize("string", "").toArray());
	}

	@Test
	public void tokenizeNullString() {
		assertArrayEquals(new String[] {}, stringServices.tokenize(null, "string").toArray());
	}

	@Test
	public void tokenizeStringNull() {
		assertArrayEquals(new String[] {"string" }, stringServices.tokenize("string", null).toArray());
	}

	@Test
	public void tokenizeStringString() {
		assertArrayEquals(new String[] {"a", "b", "c", "d" }, stringServices.tokenize("a|b|c|d", "|")
				.toArray());
	}

	@Test
	public void toLowerNull() {
		assertEquals(null, stringServices.toLower(null));
	}

	@Test
	public void toLowerEmpty() {
		assertEquals("", stringServices.toLower(""));
	}

	@Test
	public void toLower() {
		assertEquals("abcd", stringServices.toLower("ABCD"));
	}

	@Test
	public void toLowerNullFirst() {
		assertEquals(null, stringServices.toLowerFirst(null));
	}

	@Test
	public void toLowerEmptyFirst() {
		assertEquals("", stringServices.toLowerFirst(""));
	}

	@Test
	public void toLowerFirst() {
		assertEquals("aBCD", stringServices.toLowerFirst("ABCD"));
	}

	@Test(expected = java.lang.NumberFormatException.class)
	public void toRealNull() {
		stringServices.toReal(null);
	}

	@Test(expected = java.lang.NumberFormatException.class)
	public void toRealEmpty() {
		stringServices.toReal("");
	}

	@Test
	public void toRealRoundedReal() {
		Double result = stringServices.toReal("3.0");
		assertEquals(Double.valueOf(3.0), result);
	}

	@Test(expected = java.lang.NumberFormatException.class)
	public void toRealInvalid() {
		stringServices.toReal("toInteger");
	}

	@Test
	public void toReal() {
		Double result = stringServices.toReal("3.14");
		assertEquals(Double.valueOf(3.14), result);
	}

	@Test
	public void toUpperNull() {
		assertEquals(null, stringServices.toUpper(null));
	}

	@Test
	public void toUpperEmpty() {
		assertEquals("", stringServices.toUpper(""));
	}

	@Test
	public void toUpper() {
		assertEquals("ABCD", stringServices.toUpper("abcd"));
	}

	@Test
	public void toUpperNullFirst() {
		assertEquals(null, stringServices.toUpperFirst(null));
	}

	@Test
	public void toUpperEmptyFirst() {
		assertEquals("", stringServices.toUpperFirst(""));
	}

	@Test
	public void toUpperFirst() {
		assertEquals("Abcd", stringServices.toUpperFirst("abcd"));
	}

	@Test
	public void trimNull() {
		assertEquals(null, stringServices.trim(null));
	}

	@Test
	public void trimEmpty() {
		assertEquals("", stringServices.trim(""));
	}

	@Test
	public void trim() {
		assertEquals("azerty", stringServices.trim("\n\t    \razerty \t\t"));
	}

	@Test
	public void atNullNull() {
		assertEquals(null, stringServices.at(null, null));
	}

	@Test(expected = StringIndexOutOfBoundsException.class)
	public void atOutOfBound() {
		assertEquals(null, stringServices.at("cat", -1));
	}

	@Test
	public void at() {
		assertEquals("a", stringServices.at("cat", 2));
	}

	@Test
	public void charactersNull() {
		assertEquals(0, stringServices.characters(null).size());
	}

	@Test
	public void charactersEmpty() {
		assertEquals(0, stringServices.characters("").size());
	}

	@Test
	public void characters() {
		final List<String> characters = stringServices.characters("cat");

		assertEquals(3, characters.size());
		assertEquals("c", characters.get(0));
		assertEquals("a", characters.get(1));
		assertEquals("t", characters.get(2));
	}

	@Test
	public void lineSeparatorNull() {
		assertEquals(System.getProperty("line.separator"), stringServices.lineSeparator(null));
	}

	@Test
	public void lineSeparator() {
		assertEquals(System.getProperty("line.separator"), stringServices.lineSeparator(new Object()));
	}

}
