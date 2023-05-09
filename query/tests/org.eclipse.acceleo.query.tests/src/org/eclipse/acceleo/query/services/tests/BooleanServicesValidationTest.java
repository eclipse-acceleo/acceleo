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
import org.eclipse.acceleo.query.services.BooleanServices;
import org.eclipse.acceleo.query.validation.type.IType;
import org.junit.Test;

public class BooleanServicesValidationTest extends AbstractServicesValidationTest {

	@Override
	public void before() throws Exception {
		super.before();
		final Set<IService<?>> services = ServiceUtils.getServices(getQueryEnvironment(),
				BooleanServices.class);
		ServiceUtils.registerServices(getQueryEnvironment(), services);
	}

	@Test
	public void testOrPrimitive() {
		final IType[] parameterTypes = new IType[] {classType(boolean.class), classType(boolean.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "or", parameterTypes);
	}

	@Test
	public void testOr() {
		final IType[] parameterTypes = new IType[] {classType(Boolean.class), classType(Boolean.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "or", parameterTypes);
	}

	@Test
	public void testAndPrimitive() {
		final IType[] parameterTypes = new IType[] {classType(boolean.class), classType(boolean.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "and", parameterTypes);
	}

	@Test
	public void testAnd() {
		final IType[] parameterTypes = new IType[] {classType(Boolean.class), classType(Boolean.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "and", parameterTypes);
	}

	@Test
	public void testNotPrimitive() {
		final IType[] parameterTypes = new IType[] {classType(boolean.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "not", parameterTypes);
	}

	@Test
	public void testNot() {
		final IType[] parameterTypes = new IType[] {classType(Boolean.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "not", parameterTypes);
	}

	@Test
	public void testImpliesPrimitive() {
		final IType[] parameterTypes = new IType[] {classType(boolean.class), classType(boolean.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "implies", parameterTypes);
	}

	@Test
	public void testImplies() {
		final IType[] parameterTypes = new IType[] {classType(Boolean.class), classType(Boolean.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "implies", parameterTypes);
	}

	@Test
	public void testXorPrimitive() {
		final IType[] parameterTypes = new IType[] {classType(boolean.class), classType(boolean.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "xor", parameterTypes);
	}

	@Test
	public void testXor() {
		final IType[] parameterTypes = new IType[] {classType(Boolean.class), classType(Boolean.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "xor", parameterTypes);
	}

}
