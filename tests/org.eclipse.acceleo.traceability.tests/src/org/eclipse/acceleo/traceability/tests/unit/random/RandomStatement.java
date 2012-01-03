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
package org.eclipse.acceleo.traceability.tests.unit.random;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.experimental.theories.PotentialAssignment;
import org.junit.experimental.theories.PotentialAssignment.CouldNotGenerateValueException;
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.internal.Assignments;
import org.junit.experimental.theories.internal.ParameterizedAssertionError;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.junit.runners.model.TestClass;

public class RandomStatement extends Statement {
	private int successes = 0;

	private FrameworkMethod fTestMethod;

	private List<AssumptionViolatedException> fInvalidParameters = new ArrayList<AssumptionViolatedException>();

	private TestClass clazz;

	public RandomStatement(FrameworkMethod method, TestClass clazz) {
		fTestMethod = method;
		this.clazz = clazz;
	}

	@Override
	public void evaluate() throws Throwable {
		runWithAssignment(Assignments.allUnassigned(fTestMethod.getMethod(), this.clazz));

		if (successes == 0) {
			Assert.fail("Never found parameters that satisfied method assumptions.  Violated assumptions: "
					+ fInvalidParameters);
		}
	}

	protected void runWithAssignment(Assignments parameterAssignment) throws Throwable {
		if (!parameterAssignment.isComplete()) {
			runWithIncompleteAssignment(parameterAssignment);
		} else {
			runWithCompleteAssignment(parameterAssignment);
		}
	}

	protected void runWithIncompleteAssignment(Assignments incomplete) throws InstantiationException,
			IllegalAccessException, Throwable {
		for (PotentialAssignment source : incomplete.potentialsForNextUnassigned()) {
			runWithAssignment(incomplete.assignNext(source));
		}
	}

	protected void runWithCompleteAssignment(final Assignments complete) throws InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, Throwable {
		new BlockJUnit4ClassRunner(this.clazz.getJavaClass()) {
			@Override
			protected void collectInitializationErrors(List<Throwable> errors) {
				// do nothing
			}

			@Override
			public Statement methodBlock(FrameworkMethod method) {
				final Statement statement = super.methodBlock(method);
				return new Statement() {
					@Override
					public void evaluate() throws Throwable {
						try {
							statement.evaluate();
							handleDataPointSuccess();
						} catch (AssumptionViolatedException e) {
							handleAssumptionViolation(e);
						} catch (Throwable e) {
							reportParameterizedError(e, complete.getArgumentStrings(nullsOk()));
						}
					}

				};
			}

			@Override
			protected Statement methodInvoker(FrameworkMethod method, Object test) {
				return methodCompletesWithParameters(method, complete, test);
			}

			@Override
			public Object createTest() throws Exception {
				return getTestClass().getOnlyConstructor().newInstance(
						complete.getConstructorArguments(nullsOk()));
			}
		}.methodBlock(fTestMethod).evaluate();
	}

	private Statement methodCompletesWithParameters(final FrameworkMethod method, final Assignments complete,
			final Object freshInstance) {
		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				try {
					final Object[] values = complete.getMethodArguments(nullsOk());
					method.invokeExplosively(freshInstance, values);
				} catch (CouldNotGenerateValueException e) {
					// ignore
				}
			}
		};
	}

	protected void handleAssumptionViolation(AssumptionViolatedException e) {
		fInvalidParameters.add(e);
	}

	protected void reportParameterizedError(Throwable e, Object... params) throws Throwable {
		if (params.length == 0) {
			throw e;
		}
		throw new ParameterizedAssertionError(e, fTestMethod.getName(), params);
	}

	private boolean nullsOk() {
		Theory annotation = fTestMethod.getMethod().getAnnotation(Theory.class);
		if (annotation == null) {
			return false;
		}
		return annotation.nullsAccepted();
	}

	protected void handleDataPointSuccess() {
		successes++;
	}
}
