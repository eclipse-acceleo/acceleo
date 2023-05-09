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
import org.eclipse.acceleo.query.services.ComparableServices;
import org.eclipse.acceleo.query.validation.type.IType;
import org.junit.Test;

public class ComparableServicesValidationTest extends AbstractServicesValidationTest {

	@Override
	public void before() throws Exception {
		super.before();
		final Set<IService<?>> services = ServiceUtils.getServices(getQueryEnvironment(),
				ComparableServices.class);
		ServiceUtils.registerServices(getQueryEnvironment(), services);
	}

	@Test
	public void testLower() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "lessThan", parameterTypes);
	}

	@Test
	public void testLowerEqual() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "lessThanEqual", parameterTypes);
	}

	@Test
	public void testGreater() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "greaterThan", parameterTypes);
	}

	@Test
	public void testGreaterEqual() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "greaterThanEqual", parameterTypes);
	}

}
