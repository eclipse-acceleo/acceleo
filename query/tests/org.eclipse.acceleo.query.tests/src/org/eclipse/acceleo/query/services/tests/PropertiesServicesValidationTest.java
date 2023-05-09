/*******************************************************************************
 * Copyright (c) 2020 Obeo.
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
import org.eclipse.acceleo.query.services.PropertiesServices;
import org.eclipse.acceleo.query.validation.type.IType;
import org.junit.Test;

public class PropertiesServicesValidationTest extends AbstractServicesValidationTest {

	@Override
	public void before() throws Exception {
		super.before();
		final Set<IService<?>> services = ServiceUtils.getServices(getQueryEnvironment(),
				PropertiesServices.class);
		ServiceUtils.registerServices(getQueryEnvironment(), services);
	}

	@Test
	public void getPropertyString() {
		final IType[] parameterTypes = new IType[] {classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "getProperty", parameterTypes);
	}

	@Test
	public void getPropertyStringList() {
		final IType[] parameterTypes = new IType[] {classType(String.class), sequenceType(classType(
				String.class)) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "getProperty", parameterTypes);
	}

}
