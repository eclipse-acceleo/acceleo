/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.tests.unit.environment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.common.utils.AcceleoStandardLibrary;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.internal.environment.AcceleoEnvironment;
import org.eclipse.acceleo.engine.internal.environment.AcceleoEnvironmentFactory;
import org.eclipse.acceleo.engine.internal.environment.AcceleoEvaluationEnvironment;
import org.eclipse.acceleo.engine.internal.environment.AcceleoLibraryOperationVisitor;
import org.eclipse.acceleo.engine.internal.environment.AcceleoPropertiesLookup;
import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.ecore.OCL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This will test the behavior of the Acceleo standard library's operations.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public class AcceleoStandardLibraryTest extends AbstractAcceleoTest {
	/** The evaluation environment to call for standard operations on. */
	private AcceleoEvaluationEnvironment evaluationEnvironment;

	/** This will be created at setUp and disposed of at tearDown time. */
	private AcceleoEnvironmentFactory factory;

	/** EOperations defined in the standard lib. */
	private final Map<String, List<EOperation>> stdLib = new HashMap<String, List<EOperation>>();

	/** Values that will be used to test standard string operations. */
	private final String[] stringValues = new String[] {"a", "\u00e9\u00e8\u0020\u00f1", "", "Foehn12",
			"Standard sentence.", };

	/** Instance of the "oclInvalid" standard library object. */
	protected Object invalidObject;

	{
		AcceleoStandardLibrary lib = new AcceleoStandardLibrary();

		List<EOperation> intOperations = lib
				.getExistingOperations(AcceleoStandardLibrary.PRIMITIVE_INTEGER_NAME);
		List<EOperation> copyOperations = new ArrayList<EOperation>(intOperations.size());
		for (EOperation operation : intOperations) {
			copyOperations.add((EOperation)EcoreUtil.copy(operation));
		}
		stdLib.put(AcceleoStandardLibrary.PRIMITIVE_INTEGER_NAME, copyOperations);

		List<EOperation> realOperations = lib
				.getExistingOperations(AcceleoStandardLibrary.PRIMITIVE_REAL_NAME);
		copyOperations = new ArrayList<EOperation>(realOperations.size());
		for (EOperation operation : realOperations) {
			copyOperations.add((EOperation)EcoreUtil.copy(operation));
		}
		stdLib.put(AcceleoStandardLibrary.PRIMITIVE_REAL_NAME, copyOperations);

		List<EOperation> stringOperations = lib
				.getExistingOperations(AcceleoStandardLibrary.PRIMITIVE_STRING_NAME);
		copyOperations = new ArrayList<EOperation>(stringOperations.size());
		for (EOperation operation : stringOperations) {
			copyOperations.add((EOperation)EcoreUtil.copy(operation));
		}
		stdLib.put(AcceleoStandardLibrary.PRIMITIVE_STRING_NAME, copyOperations);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#setUp()
	 */
	@Before
	@Override
	public void setUp() {
		super.setUp();
		// only used for initialization
		this.init("StdLib");
		factory = new AcceleoEnvironmentFactory(generationRoot, module,
				new ArrayList<IAcceleoTextGenerationListener>(), new AcceleoPropertiesLookup(),
				previewStrategy, new BasicMonitor());
		final OCL ocl = OCL.newInstance(factory);
		evaluationEnvironment = (AcceleoEvaluationEnvironment)ocl.getEvaluationEnvironment();
		invalidObject = ((AcceleoEnvironment)ocl.getEnvironment()).getOCLStandardLibraryReflection()
				.getInvalid();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getModuleLocation()
	 */
	@Override
	public String getModuleLocation() {
		// Reusing the generic engine test template. This is only used for setup.
		return "data/GenericEngine/generic_engine.mtl";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getReferencePath() {
		return "StandardLibrary";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#tearDown()
	 */
	@After
	@Override
	public void tearDown() {
		factory.dispose();
	}

	/**
	 * Tests the behavior of the standard "toString" operation on Integers. Expects the result to be that of
	 * the source's toString method.
	 */
	@Test
	public void testIntegerToString() {
		EOperation operation = getOperation(AcceleoStandardLibrary.PRIMITIVE_INTEGER_NAME,
				AcceleoStandardLibrary.OPERATION_INTEGER_TOSTRING);
		final List<Number> values = new ArrayList<Number>(7);
		values.add(Long.MIN_VALUE);
		values.add(Integer.MIN_VALUE);
		values.add(Long.valueOf(-5));
		values.add(Integer.valueOf(0));
		values.add(Long.valueOf(5));
		values.add(Integer.MAX_VALUE);
		values.add(Long.MAX_VALUE);
		for (Number value : values) {
			Object result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment,
					operation, value);
			assertEquals("Integer.toString() standard operation didn't evaluate to the accurate result.",
					value.toString(), result);
		}
	}

	/**
	 * Tests the behavior of the standard "toString" operation on Reals. Expects the result to be that of the
	 * source's toString method.
	 */
	@Test
	public void testRealToString() {
		EOperation operation = getOperation(AcceleoStandardLibrary.PRIMITIVE_REAL_NAME,
				AcceleoStandardLibrary.OPERATION_REAL_TOSTRING);
		final List<Number> values = new ArrayList<Number>(7);
		values.add(Double.MIN_VALUE);
		values.add(Float.MIN_VALUE);
		values.add(Double.valueOf(-5));
		values.add(Float.valueOf(0));
		values.add(Double.valueOf(5));
		values.add(Float.MAX_VALUE);
		values.add(Double.MAX_VALUE);
		for (Number value : values) {
			Object result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment,
					operation, value);
			assertEquals("Real.toString() standard operation didn't evaluate to the accurate result.", value
					.toString(), result);
		}
	}

	/**
	 * Tests the behavior of the standard "first(int)" operation on Strings. Expects the result to be the same
	 * as source.substring(0, int).
	 */
	@Test
	public void testStringFirst() {
		EOperation operation = getOperation(AcceleoStandardLibrary.PRIMITIVE_STRING_NAME,
				AcceleoStandardLibrary.OPERATION_STRING_FIRST);

		for (String value : stringValues) {
			Object result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment,
					operation, value, new Object[] {Integer.valueOf(-5), });
			assertEquals("Calling the standard operation String.first(int) with a negative "
					+ "parameter should return self.", invalidObject, result);
			result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
					value, Integer.valueOf(0));
			assertEquals("Calling the standard operation String.first(0) should return the empty String.",
					"", result);
			result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
					value, Integer.valueOf(1));
			if (value.length() == 0) {
				assertEquals("calling the standard operation String.first(int) on the empty String "
						+ "should return the empty String.", "", result);
			} else {
				assertEquals("calling the standard operation String.first(1) should return the first "
						+ "character of self.", String.valueOf(value.charAt(0)), result);
			}
			result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
					value, Integer.valueOf(3));
			if (value.length() == 0) {
				assertEquals("calling the standard operation String.first(int) on the empty String should "
						+ "return the empty String.", "", result);
			} else if (value.length() < 3) {
				assertEquals("calling the standard operation String.first(3) on Strings which size is less "
						+ "than 3 should return self.", value, result);
			} else {
				assertEquals("calling the standard operation String.first(3) on Strings which size is more "
						+ "than 3 should return the first three characters of self.", value.substring(0, 3),
						result);
			}
		}

		// Ensure the behavior when passing null as argument doesn't evolve
		try {
			AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
					stringValues[0], (EObject)null);
			fail("The standard String.first operation previously threw NPEs when called with null argument");
		} catch (NullPointerException e) {
			// Expected behavior
		}
	}

	/**
	 * Tests the behavior of the standard "index(String)" operation on Strings. Expects the result to be the
	 * same as source.indexOf(String).
	 */
	@Test
	public void testStringIndex() {
		EOperation operation = getOperation(AcceleoStandardLibrary.PRIMITIVE_STRING_NAME,
				AcceleoStandardLibrary.OPERATION_STRING_INDEX);

		for (String value : stringValues) {
			Object result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment,
					operation, value, "jgfduigelgrkj");
			assertEquals("Calling the standard operation String.index(String) with a String that is "
					+ "not contained by self should have returned -1.", -1, result);
			if (value.length() > 1) {
				result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment,
						operation, value, value.substring(2, value.length() - 1));
				assertEquals("Calling the standard operation String.index(String) with a String that is "
						+ "contained by self should have had the same result as source.indexOf(String) + 1.",
						value.indexOf(value.substring(2, value.length() - 1)), ((Integer)result - 1));
			}
		}

		// Ensure the behavior when passing null as argument doesn't evolve
		try {
			AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
					stringValues[0], (EObject)null);
			fail("The standard String.index operation previously threw NPEs when called with null argument");
		} catch (NullPointerException e) {
			// Expected behavior
		}
	}

	/**
	 * Tests the behavior of the standard "isAlpha()" operation on Strings. Expects the result to be
	 * <code>true</code> if and only if {@link Character#isLetter(char)} returns <code>true</code> for each
	 * and every character of the source value.
	 */
	@Test
	public void testStringIsAlpha() {
		EOperation operation = getOperation(AcceleoStandardLibrary.PRIMITIVE_STRING_NAME,
				AcceleoStandardLibrary.OPERATION_STRING_ISALPHA);

		for (String value : stringValues) {
			Object result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment,
					operation, value);
			boolean isAlpha = true;
			for (char c : value.toCharArray()) {
				if (!Character.isLetter(c)) {
					isAlpha = false;
					break;
				}
			}
			assertEquals("The standard operation String.isAlpha() returned an unexpected result.", isAlpha,
					result);
		}
	}

	/**
	 * Tests the behavior of the standard "isAlphanum()" operation on Strings. Expects the result to be
	 * <code>true</code> if and only if {@link Character#isLetterOrDigit(char)} returns <code>true</code> for
	 * each and every character of the source value.
	 */
	@Test
	public void testStringIsAlphanum() {
		EOperation operation = getOperation(AcceleoStandardLibrary.PRIMITIVE_STRING_NAME,
				AcceleoStandardLibrary.OPERATION_STRING_ISALPHANUM);

		for (String value : stringValues) {
			Object result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment,
					operation, value);
			boolean isAlphanum = true;
			for (char c : value.toCharArray()) {
				if (!Character.isLetterOrDigit(c)) {
					isAlphanum = false;
					break;
				}
			}
			assertEquals("The standard operation String.isAlphanum() returned an unexpected result.",
					isAlphanum, result);
		}
	}

	/**
	 * Tests the behavior of the standard "last(int)" operation on Strings. Expects the result to be the same
	 * as source.substring(source.length() - int, source.length()).
	 */
	@Test
	public void testStringLast() {
		EOperation operation = getOperation(AcceleoStandardLibrary.PRIMITIVE_STRING_NAME,
				AcceleoStandardLibrary.OPERATION_STRING_LAST);

		for (String value : stringValues) {
			Object result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment,
					operation, value, Integer.valueOf(-5));
			assertEquals("Calling the standard operation String.last(int) with a negative "
					+ "parameter should return self.", invalidObject, result);
			result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
					value, Integer.valueOf(0));
			assertEquals("Calling the standard operation String.last(0) should return the empty String.", "",
					result);
			result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
					value, Integer.valueOf(1));
			if (value.length() == 0) {
				assertEquals("calling the standard operation String.last(int) on the empty String "
						+ "should return the empty String.", "", result);
			} else {
				assertEquals("calling the standard operation String.last(1) should return the last "
						+ "character of self.", String.valueOf(value.charAt(value.length() - 1)), result);
			}
			result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
					value, Integer.valueOf(3));
			if (value.length() == 0) {
				assertEquals("calling the standard operation String.last(int) on the empty String should "
						+ "return the empty String.", "", result);
			} else if (value.length() < 3) {
				assertEquals("calling the standard operation String.last(3) on Strings which size is less "
						+ "than 3 should return self.", value, result);
			} else {
				assertEquals("calling the standard operation String.last(3) on Strings which size is more "
						+ "than 3 should return the last three characters of self.", value.substring(value
						.length() - 3, value.length()), result);
			}
		}

		// Ensure the behavior when passing null as argument doesn't evolve
		try {
			AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
					stringValues[0], (EObject)null);
			fail("The standard String.last operation previously threw NPEs when called with null argument");
		} catch (NullPointerException e) {
			// Expected behavior
		}
	}

	/**
	 * Tests the behavior of the standard "strcmp(String)" operation on Strings. Expects the result to be the
	 * same as source.compareTo(String).
	 */
	@Test
	public void testStringStrcmp() {
		EOperation operation = getOperation(AcceleoStandardLibrary.PRIMITIVE_STRING_NAME,
				AcceleoStandardLibrary.OPERATION_STRING_STRCMP);

		for (int i = 0; i < stringValues.length; i++) {
			for (int j = stringValues.length - 1; j >= 0; j--) {
				Object result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment,
						operation, stringValues[i], stringValues[j]);
				assertEquals("Unexpected result of the standard String.strcmp(String) operation.",
						stringValues[i].compareTo(stringValues[j]), result);
			}
		}

		// Ensure the behavior when passing null as argument doesn't evolve
		try {
			AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
					stringValues[0], (EObject)null);
			fail("The standard String.strcmp operation previously threw NPEs when called with null argument");
		} catch (NullPointerException e) {
			// Expected behavior
		}
	}

	/**
	 * Tests the behavior of the standard "strstr(String)" operation on Strings. Expects the result to be the
	 * same as source.contains(String).
	 */
	@Test
	public void testStringStrstr() {
		EOperation operation = getOperation(AcceleoStandardLibrary.PRIMITIVE_STRING_NAME,
				AcceleoStandardLibrary.OPERATION_STRING_STRSTR);

		for (String value : stringValues) {
			// string contains itself
			Object result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment,
					operation, value, value);
			assertTrue("Standard String.strstr(String) operation reports source does not contain itself.",
					result instanceof Boolean && ((Boolean)result).booleanValue());

			// contained string
			String search = "";
			if (value.length() == 1) {
				search = value;
			} else if (value.length() > 1) {
				search = value.substring(1, value.length() - 1);
			}
			result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
					value, search);
			assertTrue("Standard String.strstr(String) operation reports source does not contain its "
					+ "substring.", result instanceof Boolean && ((Boolean)result).booleanValue());

			// not contained String
			result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
					value, "This substring is not contained by any value");
			assertTrue("Standard String.strstr(String) operation reports source contains a random, "
					+ "not contained string.", result instanceof Boolean && !((Boolean)result).booleanValue());
		}

		// Ensure the behavior when passing null as argument doesn't evolve
		try {
			AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
					stringValues[0], (EObject)null);
			fail("The standard String.strstr operation previously threw NPEs when called with null argument");
		} catch (NullPointerException e) {
			// Expected behavior
		}
	}

	/**
	 * Tests the behavior of the standard "strtok(String, int)" operation on Strings.
	 */
	@Test
	public void testStringStrtok() {
		EOperation operation = getOperation(AcceleoStandardLibrary.PRIMITIVE_STRING_NAME,
				AcceleoStandardLibrary.OPERATION_STRING_STRTOK);

		final String value = "Standard english sentence with space-separated words.";
		Object result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment,
				operation, value, " ", Integer.valueOf(0));
		assertEquals("Call to standard operation String.strtok(\" \", 0) did not return the first word "
				+ "of self", "Standard", result);

		final String[] words = value.split(" ");
		for (int i = 1; i < words.length; i++) {
			result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
					value, " ", Integer.valueOf(1));
			assertEquals("Subsequent call to standard operation String.strtok(\" \", 1) did not return the "
					+ "next word of self", words[i], result);
		}

		/*
		 * We've consumed all of the tokenizer's tokens. Any subsequent call is expected to return the empty
		 * String.
		 */
		result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
				value, " ", Integer.valueOf(1));
		assertEquals("Calling the standard operation String.strtok(\" \", 1) is expected to return the "
				+ "empty String when all tokens have been consumed.", "", result);

		/*
		 * Calling strtok on a given String with the flag set to 0 a second time is expected to reset the
		 * tokenizer and return the first word again.
		 */
		result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
				value, " ", Integer.valueOf(0));
		assertEquals("Call to standard operation String.strtok(\" \", 0) did not return the first word "
				+ "of self", "Standard", result);

		// Calling strtok with its flag at 1 on a new String will have the same effect as a "0" flag
		final String newValue = "test sentence.";
		result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
				newValue, " ", Integer.valueOf(1));
		assertEquals("Call to standard operation String.strtok(\" \", 1) on a new source did not return"
				+ " the first word of self", "test", result);

		// Calling strtok with an invalid flag fails
		try {
			result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
					newValue, " ", Integer.valueOf(5));
			fail("The standard String.strtok operation should have failed with an invalid flag");
		} catch (AcceleoEvaluationException e) {
			// Expected behavior
		}

		// Ensure the behavior when passing null as argument doesn't evolve
		try {
			AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
					stringValues[0], (EObject)null, Integer.valueOf(0));
			fail("The standard String.strtok operation previously threw NPEs when called with null argument");
		} catch (NullPointerException e) {
			// Expected behavior
		}
		try {
			AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
					stringValues[0], " ", (EObject)null);
			fail("The standard String.strtok operation previously threw NPEs when called with null argument");
		} catch (NullPointerException e) {
			// Expected behavior
		}
	}

	/**
	 * Tests the behavior of the standard String.substitute(String, String) operation.
	 * <p>
	 * We expect the call to replace the first occurence of the substring by the replacement, not considering
	 * both parameters as regular expressions.
	 * </p>
	 */
	@Test
	public void testStringSubstitute() {
		EOperation operation = getOperation(AcceleoStandardLibrary.PRIMITIVE_STRING_NAME,
				AcceleoStandardLibrary.OPERATION_STRING_SUBSTITUTE);

		final String value = "start .*abc.* - .*abc.* end";
		Object result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment,
				operation, value, ".*abc.*", "\\$1\\1def\\2$2\\");
		assertEquals("standard operation String.substitute(String, String) didn't return the "
				+ "expected result.", "start \\$1\\1def\\2$2\\ - .*abc.* end", result);

		result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
				value, "not contained substring", "random replacement");
		assertEquals("standard operation String.substitute(String, String) didn't return the "
				+ "expected result.", "start .*abc.* - .*abc.* end", result);

		// Ensure the behavior when passing null as argument doesn't evolve
		try {
			AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
					stringValues[0], (EObject)null, "abc");
			fail("The standard String.substitute operation previously threw NPEs when called with null argument");
		} catch (NullPointerException e) {
			// Expected behavior
		}
		try {
			AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation,
					stringValues[0], "abc", (EObject)null);
			fail("The standard String.substitute operation previously threw NPEs when called with null argument");
		} catch (NullPointerException e) {
			// Expected behavior
		}
	}

	/**
	 * Tests the behavior of the standard String.toLowerFirst() operation.
	 */
	@Test
	public void testStringToLowerFirst() {
		EOperation operation = getOperation(AcceleoStandardLibrary.PRIMITIVE_STRING_NAME,
				AcceleoStandardLibrary.OPERATION_STRING_TOLOWERFIRST);

		for (String value : stringValues) {
			final String expected;
			if (value.length() == 0) {
				expected = value;
			} else {
				expected = Character.toLowerCase(value.charAt(0)) + value.substring(1);
			}
			// string contains itself
			Object result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment,
					operation, value);
			assertEquals("Standard String.toLowerFirst() operation did not return the expect result.",
					expected, result);
		}
	}

	/**
	 * Tests the behavior of the standard String.toUpperFirst() operation.
	 */
	@Test
	public void testStringToUpperFirst() {
		EOperation operation = getOperation(AcceleoStandardLibrary.PRIMITIVE_STRING_NAME,
				AcceleoStandardLibrary.OPERATION_STRING_TOUPPERFIRST);

		for (String value : stringValues) {
			// string contains itself
			Object result = AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment,
					operation, value);
			final String expected;
			if (value.length() == 0) {
				expected = value;
			} else {
				expected = Character.toUpperCase(value.charAt(0)) + value.substring(1);
			}
			assertEquals("Standard String.toUpperFirst() operation did not return the expect result.",
					expected, result);
		}
	}

	/**
	 * Tests the behavior of the environment when calling for an undefined operation.
	 * <p>
	 * Expects an {@link UnsupportedOperationException} to be thrown with an accurate error message.
	 * </p>
	 */
	@Test
	public void testUndefinedOperation() {
		final EOperation operation = EcoreFactory.eINSTANCE.createEOperation();
		operation.setName("undefinedOperation");
		operation.setEType(EcorePackage.eINSTANCE.getEString());
		final EAnnotation acceleoAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
		acceleoAnnotation.setSource("MTL");
		operation.getEAnnotations().add(acceleoAnnotation);

		try {
			AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation, "source");
			fail("Expected Unsupported Operation hasn't been thrown by the evaluation environment.");
		} catch (UnsupportedOperationException e) {
			// Expected behavior
			final String expectedErrMsg = "undefinedOperation()";
			assertTrue("Exception hasn't been affected an accurate error message", e.getMessage().contains(
					expectedErrMsg));
		}

		try {
			AcceleoLibraryOperationVisitor.callStandardOperation(evaluationEnvironment, operation, "source",
					"arg1", "arg2");
			fail("Expected Unsupported Operation hasn't been thrown by the evaluation environment.");
		} catch (UnsupportedOperationException e) {
			// Expected behavior
			final String expectedErrMsg = "undefinedOperation(String, String)";
			assertTrue("Exception hasn't been affected an accurate error message", e.getMessage().contains(
					expectedErrMsg));
		}
	}

	/**
	 * Returns the operation named <code>operationName</code> registered against the type
	 * <code>typeName</code> in the standard library.
	 * 
	 * @param typeName
	 *            Name of the classifier we seek an operation of.
	 * @return The sought operation.
	 */
	private EOperation getOperation(String typeName, String operationName) {
		final List<EOperation> candidates = stdLib.get(typeName);
		for (EOperation candidate : candidates) {
			if (candidate.getName().equals(operationName)) {
				return candidate;
			}
		}
		// not guarded
		return null;
	}
}
