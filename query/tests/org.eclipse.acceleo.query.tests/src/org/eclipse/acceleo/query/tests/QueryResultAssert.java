/*******************************************************************************
 * Copyright (c) 2015, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests;

import java.io.Serializable;
import java.util.Iterator;

import org.eclipse.acceleo.query.tests.qmodel.BooleanResult;
import org.eclipse.acceleo.query.tests.qmodel.EObjectResult;
import org.eclipse.acceleo.query.tests.qmodel.EObjectVariable;
import org.eclipse.acceleo.query.tests.qmodel.EmptyResult;
import org.eclipse.acceleo.query.tests.qmodel.EnumeratorResult;
import org.eclipse.acceleo.query.tests.qmodel.ErrorResult;
import org.eclipse.acceleo.query.tests.qmodel.IntegerResult;
import org.eclipse.acceleo.query.tests.qmodel.InvalidResult;
import org.eclipse.acceleo.query.tests.qmodel.ListResult;
import org.eclipse.acceleo.query.tests.qmodel.Query;
import org.eclipse.acceleo.query.tests.qmodel.QueryEvaluationResult;
import org.eclipse.acceleo.query.tests.qmodel.QueryValidationResult;
import org.eclipse.acceleo.query.tests.qmodel.SerializableResult;
import org.eclipse.acceleo.query.tests.qmodel.SetResult;
import org.eclipse.acceleo.query.tests.qmodel.StringResult;
import org.eclipse.acceleo.query.tests.qmodel.ValidationMessage;
import org.eclipse.acceleo.query.tests.qmodel.Variable;
import org.eclipse.emf.ecore.EObject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class QueryResultAssert {

	public static void assertEquivalentEvaluation(QueryEvaluationResult expectedResult,
			QueryEvaluationResult actualResult) {

		assertSame(expectedResult.eClass().getName(), actualResult.eClass().getName());

		if (expectedResult instanceof ListResult) {
			assertTrue(actualResult instanceof ListResult);
			assertEquals(((ListResult)expectedResult).getValues().size(), ((ListResult)actualResult)
					.getValues().size());
			Iterator<QueryEvaluationResult> expectedIt = ((ListResult)expectedResult).getValues().iterator();
			Iterator<QueryEvaluationResult> actualIt = ((ListResult)actualResult).getValues().iterator();
			while (expectedIt.hasNext()) {
				assertEquivalentEvaluation(expectedIt.next(), actualIt.next());
			}
		} else if (expectedResult instanceof SetResult) {
			assertTrue(actualResult instanceof SetResult);
			assertEquals(((SetResult)expectedResult).getValues().size(), ((SetResult)actualResult).getValues()
					.size());
			Iterator<QueryEvaluationResult> expectedIt = ((SetResult)expectedResult).getValues().iterator();
			Iterator<QueryEvaluationResult> actualIt = ((SetResult)actualResult).getValues().iterator();
			while (expectedIt.hasNext()) {
				assertEquivalentEvaluation(expectedIt.next(), actualIt.next());
			}
		} else if (expectedResult instanceof EObjectResult) {
			assertTrue(actualResult instanceof EObjectResult);
			EObject expectedValue = ((EObjectResult)expectedResult).getValue();
			EObject actualValue = ((EObjectResult)actualResult).getValue();
			assertEquals(expectedValue, actualValue);
		} else if (expectedResult instanceof StringResult) {
			assertTrue(actualResult instanceof StringResult);
			String expectedValue = ((StringResult)expectedResult).getValue();
			String actualValue = ((StringResult)actualResult).getValue();
			assertEquals(expectedValue, actualValue);
		} else if (expectedResult instanceof BooleanResult) {
			assertTrue(actualResult instanceof BooleanResult);
			Boolean expectedValue = ((BooleanResult)expectedResult).isValue();
			Boolean actualValue = ((BooleanResult)actualResult).isValue();
			assertEquals(expectedValue, actualValue);
		} else if (expectedResult instanceof InvalidResult) {
			assertTrue(actualResult instanceof InvalidResult);
		} else if (expectedResult instanceof ErrorResult) {
			/*
			 * we don't discriminate errors yet, we'll assume as long as it is an error its good.
			 */
		} else if (expectedResult instanceof EmptyResult) {
			assertTrue(actualResult instanceof EmptyResult);
		} else if (expectedResult instanceof EnumeratorResult) {
			assertTrue(actualResult instanceof EnumeratorResult);
			String expectedValue = ((EnumeratorResult)expectedResult).getValue();
			String actualValue = ((EnumeratorResult)actualResult).getValue();
			assertEquals(expectedValue, actualValue);
		} else if (expectedResult instanceof SerializableResult) {
			assertTrue(actualResult instanceof SerializableResult);
			Serializable expectedValue = ((SerializableResult)expectedResult).getValue();
			Serializable actualValue = ((SerializableResult)actualResult).getValue();
			assertEquals(expectedValue, actualValue);
		} else if (expectedResult instanceof IntegerResult) {
			assertTrue(actualResult instanceof IntegerResult);
			Integer expectedValue = ((IntegerResult)expectedResult).getValue();
			Integer actualValue = ((IntegerResult)actualResult).getValue();
			assertEquals(expectedValue, actualValue);
		}

		else {
			fail("not supported yet in the test framework:" + expectedResult.eClass().getName());
		}
	}

	public static void assertEquivalentValidation(QueryValidationResult expectedResult,
			QueryValidationResult actualResult) {
		assertEquals(expectedResult.getPossibleTypes(), actualResult.getPossibleTypes());
		assertEquals(expectedResult.getValidationMessages().size(), actualResult.getValidationMessages()
				.size());
		for (int i = 0; i < expectedResult.getValidationMessages().size(); ++i) {
			final ValidationMessage expectedMessage = expectedResult.getValidationMessages().get(i);
			final ValidationMessage actualMessage = actualResult.getValidationMessages().get(i);
			assertEquals(expectedMessage.getSeverity(), actualMessage.getSeverity());
			assertEquals(expectedMessage.getStartPosition(), actualMessage.getStartPosition());
			assertEquals(expectedMessage.getEndPosition(), actualMessage.getEndPosition());
			assertEquals(expectedMessage.getMessage(), actualMessage.getMessage());
		}
	}

	/**
	 * Prints a {@link QueryValidationResult} to standard output stream.
	 * 
	 * @param query
	 *            the {@link Query}
	 * @param result
	 *            the {@link QueryValidationResult} to print
	 */
	public static void printValidation(Query query, QueryValidationResult result) {
		System.out.println("Interpreter: " + result.getInterpreter());
		printQuery(query);
		System.out.println("Possible types:");
		for (String possibleType : result.getPossibleTypes()) {
			System.out.println("\t" + possibleType);
		}
		System.out.println("Validation messages:");
		for (ValidationMessage message : result.getValidationMessages()) {
			System.out.println("\t" + message.getSeverity() + " " + message.getMessage());
		}
		System.out.flush();
	}

	/**
	 * Prints a {@link Query} to standard output stream.
	 * 
	 * @param query
	 *            the {@link Query} to print
	 */
	private static void printQuery(Query query) {
		System.out.println("Expression: " + query.getExpression());
		System.out.println("Self: " + query.getStartingPoint().getName() + " - " + query.getStartingPoint()
				.getTarget());
		for (Variable variable : query.getVariables()) {
			if (variable instanceof EObjectVariable) {
				System.out.println(variable.getName() + ": " + ((EObjectVariable)variable).getValue()
						.getTarget());
			}
		}
	}

}
