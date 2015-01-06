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

import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.QueryEnvironment;
import org.eclipse.acceleo.query.runtime.lookup.basic.BasicLookupEngine;

import static org.junit.Assert.assertEquals;

public abstract class AbstractServicesTest {

	private BasicLookupEngine lookupEngine;

	IQueryEnvironment queryEnvironment;

	public AbstractServicesTest() {
		queryEnvironment = new QueryEnvironment(null);
		this.lookupEngine = queryEnvironment.getLookupEngine();
	}

	public BasicLookupEngine getLookupEngine() {
		return lookupEngine;
	}

	public void runTest(String serviceName, Object expectedResult, Object[] arguments)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> argTypes[] = new Class<?>[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			argTypes[i] = arguments[i].getClass();
		}
		IService service = lookupEngine.lookup(serviceName, argTypes);
		assertEquals(expectedResult, service.getServiceMethod().invoke(service.getServiceInstance(),
				arguments));
	}
}
