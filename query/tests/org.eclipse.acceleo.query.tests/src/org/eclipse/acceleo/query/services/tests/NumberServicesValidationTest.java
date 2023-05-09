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

import java.util.Set;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.services.NumberServices;
import org.eclipse.acceleo.query.validation.type.IType;
import org.junit.Test;

public class NumberServicesValidationTest extends AbstractServicesValidationTest {

	@Override
	public void before() throws Exception {
		super.before();
		final Set<IService<?>> services = ServiceUtils.getServices(getQueryEnvironment(),
				NumberServices.class);
		ServiceUtils.registerServices(getQueryEnvironment(), services);
	}

	@Test
	public void testUnaryMinInteger() {
		final IType[] parameterTypes = new IType[] {classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "unaryMin", parameterTypes);
	}

	@Test
	public void testUnaryMinDouble() {
		final IType[] parameterTypes = new IType[] {classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Double.class) };

		assertValidation(expectedReturnTypes, "unaryMin", parameterTypes);
	}

	@Test
	public void testAddInteger() {
		final IType[] parameterTypes = new IType[] {classType(Integer.class), classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "add", parameterTypes);
	}

	@Test
	public void testAddDouble() {
		final IType[] parameterTypes = new IType[] {classType(Double.class), classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Double.class) };

		assertValidation(expectedReturnTypes, "add", parameterTypes);
	}

	@Test
	public void testSubInteger() {
		final IType[] parameterTypes = new IType[] {classType(Integer.class), classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "sub", parameterTypes);
	}

	@Test
	public void testSubDouble() {
		final IType[] parameterTypes = new IType[] {classType(Double.class), classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Double.class) };

		assertValidation(expectedReturnTypes, "sub", parameterTypes);
	}

	@Test
	public void testMultInteger() {
		final IType[] parameterTypes = new IType[] {classType(Integer.class), classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "mult", parameterTypes);
	}

	@Test
	public void testMultDouble() {
		final IType[] parameterTypes = new IType[] {classType(Double.class), classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Double.class) };

		assertValidation(expectedReturnTypes, "mult", parameterTypes);
	}

	@Test
	public void testDivOpInteger() {
		final IType[] parameterTypes = new IType[] {classType(Integer.class), classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "divOp", parameterTypes);
	}

	@Test
	public void testDivOpDouble() {
		final IType[] parameterTypes = new IType[] {classType(Double.class), classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Double.class) };

		assertValidation(expectedReturnTypes, "divOp", parameterTypes);
	}

	@Test
	public void testLessThanInteger() {
		final IType[] parameterTypes = new IType[] {classType(Integer.class), classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "lessThan", parameterTypes);
	}

	@Test
	public void testLessThanDouble() {
		final IType[] parameterTypes = new IType[] {classType(Double.class), classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "lessThan", parameterTypes);
	}

	@Test
	public void testGreaterThanInteger() {
		final IType[] parameterTypes = new IType[] {classType(Integer.class), classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "greaterThan", parameterTypes);
	}

	@Test
	public void testGreaterThanDouble() {
		final IType[] parameterTypes = new IType[] {classType(Double.class), classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "greaterThan", parameterTypes);
	}

	@Test
	public void testGreaterThanEqualInteger() {
		final IType[] parameterTypes = new IType[] {classType(Integer.class), classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "greaterThanEqual", parameterTypes);
	}

	@Test
	public void testGreaterThanEqualDouble() {
		final IType[] parameterTypes = new IType[] {classType(Double.class), classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "greaterThanEqual", parameterTypes);
	}

	@Test
	public void testEqualsInteger() {
		final IType[] parameterTypes = new IType[] {classType(Integer.class), classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "equals", parameterTypes);
	}

	@Test
	public void testEqualsDouble() {
		final IType[] parameterTypes = new IType[] {classType(Double.class), classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "equals", parameterTypes);
	}

	@Test
	public void testDiffersInteger() {
		final IType[] parameterTypes = new IType[] {classType(Integer.class), classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "differs", parameterTypes);
	}

	@Test
	public void testDiffersDouble() {
		final IType[] parameterTypes = new IType[] {classType(Double.class), classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "differs", parameterTypes);
	}

	@Test
	public void testLessThanEqualInteger() {
		final IType[] parameterTypes = new IType[] {classType(Integer.class), classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "lessThanEqual", parameterTypes);
	}

	@Test
	public void testLessThanEqualDouble() {
		final IType[] parameterTypes = new IType[] {classType(Double.class), classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "lessThanEqual", parameterTypes);
	}

	@Test
	public void testAbsInteger() {
		final IType[] parameterTypes = new IType[] {classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "abs", parameterTypes);
	}

	@Test
	public void testAbsDouble() {
		final IType[] parameterTypes = new IType[] {classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Double.class) };

		assertValidation(expectedReturnTypes, "abs", parameterTypes);
	}

	@Test
	public void testFloorInteger() {
		final IType[] parameterTypes = new IType[] {classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "floor", parameterTypes);
	}

	@Test
	public void testFloorDouble() {
		final IType[] parameterTypes = new IType[] {classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "floor", parameterTypes);
	}

	@Test
	public void testMaxInteger() {
		final IType[] parameterTypes = new IType[] {classType(Integer.class), classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "max", parameterTypes);
	}

	@Test
	public void testMaxDouble() {
		final IType[] parameterTypes = new IType[] {classType(Double.class), classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Double.class) };

		assertValidation(expectedReturnTypes, "max", parameterTypes);
	}

	@Test
	public void testMinInteger() {
		final IType[] parameterTypes = new IType[] {classType(Integer.class), classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "min", parameterTypes);
	}

	@Test
	public void testMinDouble() {
		final IType[] parameterTypes = new IType[] {classType(Double.class), classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Double.class) };

		assertValidation(expectedReturnTypes, "min", parameterTypes);
	}

	@Test
	public void testRoundInteger() {
		final IType[] parameterTypes = new IType[] {classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "round", parameterTypes);
	}

	@Test
	public void testRoundDouble() {
		final IType[] parameterTypes = new IType[] {classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "round", parameterTypes);
	}

	@Test
	public void testDivInteger() {
		final IType[] parameterTypes = new IType[] {classType(Integer.class), classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "div", parameterTypes);
	}

	@Test
	public void testDivDouble() {
		final IType[] parameterTypes = new IType[] {classType(Double.class), classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "div", parameterTypes);
	}

	@Test
	public void testModInteger() {
		final IType[] parameterTypes = new IType[] {classType(Integer.class), classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "mod", parameterTypes);
	}

	@Test
	public void testModDouble() {
		final IType[] parameterTypes = new IType[] {classType(Double.class), classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "mod", parameterTypes);
	}

	@Test
	public void testToDoubleDouble() {
		final IType[] parameterTypes = new IType[] {classType(Double.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Double.class) };

		assertValidation(expectedReturnTypes, "toDouble", parameterTypes);
	}

	@Test
	public void testToDoubleInteger() {
		final IType[] parameterTypes = new IType[] {classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Double.class) };

		assertValidation(expectedReturnTypes, "toDouble", parameterTypes);
	}

	@Test
	public void testToDoubleFloat() {
		final IType[] parameterTypes = new IType[] {classType(Float.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Double.class) };

		assertValidation(expectedReturnTypes, "toDouble", parameterTypes);
	}

	@Test
	public void testToLongLong() {
		final IType[] parameterTypes = new IType[] {classType(Long.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Long.class) };

		assertValidation(expectedReturnTypes, "toLong", parameterTypes);
	}

	@Test
	public void testToLongInteger() {
		final IType[] parameterTypes = new IType[] {classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Long.class) };

		assertValidation(expectedReturnTypes, "toLong", parameterTypes);
	}

}
