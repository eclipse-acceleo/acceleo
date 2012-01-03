/*******************************************************************************
 * Copyright (c) 2006, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.parser.tests;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Locale;

import org.eclipse.acceleo.internal.parser.AcceleoParserMessages;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests Messages class. These tests' successful completion heavily depends on
 * org.eclipse.acceleo.internal.parser.acceleoparsermessages.properties file contents.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoParserMessagesTests {
	/** Expected result of the parameterisable keys (only used if locale is en). */
	private final String[] expectedForParameterisable = {
			"Parent environment must be an Acceleo environment: {0}.", //$NON-NLS-1$
			"Invalid URL Type: {0} should be String", "Invalid Expression Type: {0} should be Boolean", //$NON-NLS-1$ //$NON-NLS-2$
			"Parsing of {0} files", "Create the concrete syntax of the file {0}", }; //$NON-NLS-1$ //$NON-NLS-2$

	/** Contains the expected results for the valid keys (only used if locale is en). */
	private final String[] expectedForValidKeys = {"Parenthesis required on this expression", //$NON-NLS-1$
			"Closing parenthesis ')' missing in expression", "Invalid text", "Invalid Type: ", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"Invalid identifier name: ", }; //$NON-NLS-1$

	/** These will be used when testing message retrieval with invalid keys. */
	private final String[] invalidKeys = {"invalidKey", "AcceleoEvaluationContext.CleanUpError1", "", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			"\u00ec", }; //$NON-NLS-1$

	/** Contains possible parameters for the messages. */
	private final Object[] messageParameters = {null, "", "Foehn", -1, new Long(10), '\u0043', //$NON-NLS-1$ //$NON-NLS-2$
			new HashSet<Object>(), "0x6c9a.^\\/", }; //$NON-NLS-1$

	/**
	 * These two are valid, parameterisable keys. See
	 * org.eclipse.acceleo.internal.parser.acceleoparsermessages.properties
	 */
	private final String[] parameterisableKeys = {"AcceleoEnvironmentFactory.IllegalParent", //$NON-NLS-1$
			"IAcceleoParserProblemsConstants.InvalidUrlType", //$NON-NLS-1$
			"IAcceleoParserProblemsConstants.InvalidExprType", "AcceleoParser.ParseFiles", //$NON-NLS-1$ //$NON-NLS-2$
			"AcceleoParser.ParseFileCST", }; //$NON-NLS-1$

	/**
	 * These are valid, un-parameterisable keys. See
	 * org.eclipse.acceleo.internal.parser.acceleoparsermessages.properties
	 */
	private final String[] validKeys = {"IAcceleoParserProblemsConstants.MissingParenthesis", //$NON-NLS-1$
			"IAcceleoParserProblemsConstants.MissingCloseParenthesis", //$NON-NLS-1$
			"IAcceleoParserProblemsConstants.InvalidText", "IAcceleoParserProblemsConstants.InvalidType", //$NON-NLS-1$ //$NON-NLS-2$
			"IAcceleoParserProblemsConstants.InvalidIdentifier", }; //$NON-NLS-1$

	/**
	 * Tests {@link AcceleoParserMessages#getString(String, Object...)} with an invalid key. Expects the
	 * String
	 * 
	 * <pre>
	 * &quot;!&quot; + key + &quot;!&quot;
	 * </pre>
	 * 
	 * to be returned. Parameters won't affect result here.
	 */
	@Test
	public void testFormattedGetStringInvalidKey() {
		for (int i = 0; i < messageParameters.length; i++) {
			for (int j = i; j < messageParameters.length; j++) {
				final Object[] parameters = partialArrayCopy(messageParameters, i, j);
				for (String invalidKey : invalidKeys) {
					assertEquals("Unexpected result of getString() with an invalid key.", //$NON-NLS-1$
							'!' + invalidKey + '!', AcceleoParserMessages.getString(invalidKey, parameters));
				}
			}
		}
	}

	/**
	 * Tests {@link AcceleoParserMessages#getString(String, Object...)} with <code>null</code> key. Expects a
	 * NullPointerException to be thrown. Parameters won't affect result here.
	 */
	@Test
	public void testFormattedGetStringNullKey() {
		for (int i = 0; i < messageParameters.length; i++) {
			for (int j = i; j < messageParameters.length; j++) {
				final Object[] parameters = partialArrayCopy(messageParameters, i, j);
				try {
					AcceleoParserMessages.getString(null, parameters);
					fail("Calling getString() with null key did not throw NullPointerException."); //$NON-NLS-1$
				} catch (NullPointerException e) {
					// This was expected
				}
			}
		}
	}

	/**
	 * Tests {@link AcceleoParserMessages#getString(String, Object...)} with valid keys.
	 * <p>
	 * If the System locale is configured for english language, expects the String associated to the key in
	 * the properties file to be returned with all occurences of <code>&quot;{[0-9]*}&quot;</code> replaced by
	 * the correct parameter if any. Otherwise, expects the key to have been found, and the parameters to be
	 * correctly substituted.
	 * </p>
	 */
	@Test
	public void testFormattedGetStringValidKey() {
		for (int i = 0; i < messageParameters.length; i++) {
			for (int j = i; j < messageParameters.length; j++) {
				final Object[] parameters = partialArrayCopy(messageParameters, i, j);
				for (int k = 0; k < parameterisableKeys.length; k++) {
					String expectedResult = expectedForParameterisable[k];
					int parameterCount = 0;
					while (expectedResult.matches(".*\\{[0-9]+\\}.*") && parameterCount < parameters.length) { //$NON-NLS-1$
						if (parameters[parameterCount] == null) {
							expectedResult = expectedResult.replaceFirst("\\{[0-9]+\\}", "null"); //$NON-NLS-1$ //$NON-NLS-2$
						} else {
							expectedResult = expectedResult.replaceFirst("\\{[0-9]+\\}", //$NON-NLS-1$
									parameters[parameterCount].toString());
						}
						parameterCount++;
					}
					Locale previousLocale = null;
					if (Locale.getDefault() != Locale.ENGLISH) {
						previousLocale = Locale.getDefault();
					}
					Locale.setDefault(Locale.ENGLISH);
					assertEquals("Unexpected formatted String returned by getString(String, Object...).", //$NON-NLS-1$
							expectedResult, AcceleoParserMessages.getString(parameterisableKeys[k],
									parameters));
					if (previousLocale != null) {
						Locale.setDefault(previousLocale);
					} else {
						Locale.setDefault(Locale.FRENCH);
					}
					final String result = AcceleoParserMessages.getString(parameterisableKeys[k], parameters);
					assertFalse("Message class did not find an existing parameterisable key.", result //$NON-NLS-1$
							.equals('!' + parameterisableKeys[k] + '!'));
					for (int l = 0; l < parameterCount; l++) {
						if (parameters[l] != null) {
							assertTrue("Message class did not substitute the parameter in the result.", //$NON-NLS-1$
									result.contains(parameters[l].toString()));
						} else {
							assertTrue("Message class did not substitute the parameter in the result.", //$NON-NLS-1$
									result.contains("null")); //$NON-NLS-1$
						}
					}
					if (previousLocale == null) {
						Locale.setDefault(Locale.ENGLISH);
					}
				}
			}
		}
	}

	/**
	 * Tests {@link AcceleoParserMessages#getString(String, Object...)} with valid keys and <code>null</code>
	 * as formatting parameter. Expects the result to be the same as the
	 * {@link AcceleoParserMessages#getString(String)}.
	 */
	@Test
	public void testFormattedGetStringValidKeyNullParameter() {
		for (int i = 0; i < parameterisableKeys.length; i++) {
			assertEquals("Unexpected formatted String returned by getString(String, Object...).", //$NON-NLS-1$
					AcceleoParserMessages.getString(parameterisableKeys[i]), AcceleoParserMessages.getString(
							parameterisableKeys[i], (Object[])null));
		}
	}

	/**
	 * Tests {@link AcceleoParserMessages#getString(String)} with an invalid key. Expects the String
	 * 
	 * <pre>
	 * &quot;!&quot; + key + &quot;!&quot;
	 * </pre>
	 * 
	 * to be returned.
	 */
	@Test
	public void testUnFormattedGetStringInvalidKey() {
		for (String invalidKey : invalidKeys) {
			assertEquals("Unexpected result of getString() with an invalid key.", '!' + invalidKey + '!', //$NON-NLS-1$
					AcceleoParserMessages.getString(invalidKey));
		}
	}

	/**
	 * Tests {@link AcceleoParserMessages#getString(String)} with <code>null</code> argument. Expects a
	 * NullPointerException to be thrown.
	 */
	@Test
	public void testUnFormattedGetStringNullKey() {
		try {
			AcceleoParserMessages.getString(null);
			fail("Calling getString() with null argument did not throw NullPointerException."); //$NON-NLS-1$
		} catch (NullPointerException e) {
			// This was expected
		}
	}

	/**
	 * Tests {@link AcceleoParserMessages#getString(String)} with valid keys. Expects the String associated to
	 * the key in the properties file to be returned.
	 */
	@Test
	public void testUnFormattedGetStringValidKey() {
		for (int i = 0; i < validKeys.length; i++) {
			Locale previousLocale = null;
			if (Locale.getDefault() != Locale.ENGLISH) {
				previousLocale = Locale.getDefault();
			}
			Locale.setDefault(Locale.ENGLISH);
			String result = AcceleoParserMessages.getString(validKeys[i]);
			assertEquals("Unexpected String returned by getString(String).", expectedForValidKeys[i], result); //$NON-NLS-1$
			if (previousLocale != null) {
				Locale.setDefault(previousLocale);
			} else {
				Locale.setDefault(Locale.FRENCH);
			}
			assertFalse("Message class did not find an existing parameterisable key.", result //$NON-NLS-1$
					.equals('!' + validKeys[i] + '!'));
			if (previousLocale == null) {
				Locale.setDefault(Locale.ENGLISH);
			}
		}
	}

	/**
	 * This will return a partial copy of an array.
	 * 
	 * @param <T>
	 *            Type of the copied array.
	 * @param original
	 *            Array to be copied.
	 * @param start
	 *            starting index of the copy.
	 * @param end
	 *            end index of the copy.
	 * @return Array containing a copy of the given range from <code>original</code>.
	 */
	@SuppressWarnings("unchecked")
	private <T> T[] partialArrayCopy(T[] original, int start, int end) {
		final int range = end - start;
		if (range < 0) {
			throw new IllegalArgumentException("Illegal copy range."); //$NON-NLS-1$
		}
		final T[] copy = (T[])Array.newInstance(original.getClass().getComponentType(), range);
		System.arraycopy(original, start, copy, 0, Math.min(original.length - start, range));
		return copy;
	}
}
