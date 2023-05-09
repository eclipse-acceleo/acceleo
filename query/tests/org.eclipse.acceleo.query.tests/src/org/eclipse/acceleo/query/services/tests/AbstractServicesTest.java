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

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.ILookupEngine;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.services.AnyServices;
import org.eclipse.acceleo.query.services.BooleanServices;
import org.eclipse.acceleo.query.services.CollectionServices;
import org.eclipse.acceleo.query.services.ComparableServices;
import org.eclipse.acceleo.query.services.EObjectServices;
import org.eclipse.acceleo.query.services.NumberServices;
import org.eclipse.acceleo.query.services.PropertiesServices;
import org.eclipse.acceleo.query.services.ResourceServices;
import org.eclipse.acceleo.query.services.StringServices;
import org.eclipse.acceleo.query.services.XPathServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class AbstractServicesTest {

	private ILookupEngine lookupEngine;

	private IQueryEnvironment queryEnvironment;

	private ValidationServices validationServices;

	@Before
	public void before() throws Exception {
		queryEnvironment = Query.newEnvironment();
		Set<IService<?>> services = ServiceUtils.getServices(queryEnvironment, new AnyServices(
				queryEnvironment));
		ServiceUtils.registerServices(queryEnvironment, services);
		services = ServiceUtils.getServices(queryEnvironment, new EObjectServices(queryEnvironment, null,
				null));
		ServiceUtils.registerServices(queryEnvironment, services);
		services = ServiceUtils.getServices(queryEnvironment, new XPathServices(queryEnvironment));
		ServiceUtils.registerServices(queryEnvironment, services);
		services = ServiceUtils.getServices(queryEnvironment, ComparableServices.class);
		ServiceUtils.registerServices(queryEnvironment, services);
		services = ServiceUtils.getServices(queryEnvironment, NumberServices.class);
		ServiceUtils.registerServices(queryEnvironment, services);
		services = ServiceUtils.getServices(queryEnvironment, StringServices.class);
		ServiceUtils.registerServices(queryEnvironment, services);
		services = ServiceUtils.getServices(queryEnvironment, BooleanServices.class);
		ServiceUtils.registerServices(queryEnvironment, services);
		services = ServiceUtils.getServices(queryEnvironment, CollectionServices.class);
		ServiceUtils.registerServices(queryEnvironment, services);
		services = ServiceUtils.getServices(queryEnvironment, ResourceServices.class);
		ServiceUtils.registerServices(queryEnvironment, services);
		services = ServiceUtils.getServices(queryEnvironment, new PropertiesServices(new Properties()));
		ServiceUtils.registerServices(queryEnvironment, services);

		this.lookupEngine = queryEnvironment.getLookupEngine();
		validationServices = new ValidationServices(queryEnvironment);
	}

	public ILookupEngine getLookupEngine() {
		return lookupEngine;
	}

	public IQueryEnvironment getQueryEnvironment() {
		return queryEnvironment;
	}

	public void setQueryEnvironment(IQueryEnvironment queryEnvironment) {
		this.queryEnvironment = queryEnvironment;
		this.lookupEngine = queryEnvironment.getLookupEngine();
		validationServices = new ValidationServices(queryEnvironment);
	}

	public ValidationServices getValidationServices() {
		return validationServices;
	}

	protected void runTest(String serviceName, Object expectedResult, Object[] arguments)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		IService<?> service = serviceLookUp(serviceName, arguments);
		assertTrue(service != null);
		assertEquals(expectedResult, service.invoke(arguments));
	}

	/**
	 * Creates a {@link Set} of {@link IType} with the given {@link IType}.
	 * 
	 * @param types
	 *            {@link IType}
	 * @return a new {@link Set} of {@link IType} with the given {@link IType}
	 */
	protected Set<IType> createTypeSet(IType... types) {
		final Set<IType> result = new LinkedHashSet<IType>();

		for (IType type : types) {
			result.add(type);
		}

		return result;
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
	protected IService<?> serviceLookUp(String serviceName, Object[] arguments) {
		IType argTypes[] = new IType[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			argTypes[i] = new ClassType(queryEnvironment, arguments[i].getClass());
		}
		IService<?> service = lookupEngine.lookup(serviceName, argTypes);
		return service;
	}

}
