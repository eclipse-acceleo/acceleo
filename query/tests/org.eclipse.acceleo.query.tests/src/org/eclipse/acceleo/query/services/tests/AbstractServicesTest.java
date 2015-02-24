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
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.runtime.lookup.basic.BasicLookupEngine;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractServicesTest {

	private BasicLookupEngine lookupEngine;

	private IQueryEnvironment queryEnvironment;

	private ValidationServices validationServices;

	@Before
	public void before() throws Exception {
		queryEnvironment = new QueryEnvironment(null);
		this.lookupEngine = queryEnvironment.getLookupEngine();
		validationServices = new ValidationServices(queryEnvironment, true);
	}

	public BasicLookupEngine getLookupEngine() {
		return lookupEngine;
	}

	public IQueryEnvironment getQueryEnvironment() {
		return queryEnvironment;
	}

	public ValidationServices getValidationServices() {
		return validationServices;
	}

	protected void runTest(String serviceName, Object expectedResult, Object[] arguments)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		IService service = serviceLookUp(serviceName, arguments);
		assertTrue(service != null);
		assertEquals(expectedResult, service.getServiceMethod().invoke(service.getServiceInstance(),
				arguments));
	}

	/**
	 * Looks up for a service according to the given name and arguments.
	 * 
	 * @param serviceName
	 *            the service name
	 * @param arguments
	 *            arguments
	 * @return the matching service if any, <code>null</code> otherwise
	 */
	protected IService serviceLookUp(String serviceName, Object[] arguments) {
		Class<?> argTypes[] = new Class<?>[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			argTypes[i] = arguments[i].getClass();
		}
		IService service = lookupEngine.lookup(serviceName, argTypes);
		return service;
	}
}
