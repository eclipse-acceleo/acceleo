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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.services.PropertiesServices;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PropertiesServicesTest extends AbstractServicesTest {

	private PropertiesServices propertiesServices;

	@Override
	public void before() throws Exception {
		super.before();
		final Properties properties = new Properties();

		properties.setProperty("property1", "this is property one.");
		properties.setProperty("property2", "this is property two: {0}.");

		propertiesServices = new PropertiesServices(properties);
		final Set<IService<?>> servicesToRegister = ServiceUtils.getServices(getQueryEnvironment(),
				propertiesServices);
		ServiceUtils.registerServices(getQueryEnvironment(), servicesToRegister);
	}

	@Test(expected = NullPointerException.class)
	public void getPropertyStringNull() {
		propertiesServices.getProperty(null);
	}

	@Test
	public void getPropertyStringNotExising() {
		assertEquals("", propertiesServices.getProperty("notExisting"));
	}

	@Test
	public void getPropertyString() {
		assertEquals("this is property one.", propertiesServices.getProperty("property1"));
		assertEquals("this is property two: {0}.", propertiesServices.getProperty("property2"));
	}

	@Test(expected = NullPointerException.class)
	public void getPropertyStringListNullNull() {
		propertiesServices.getProperty(null, null);
	}

	@Test(expected = NullPointerException.class)
	public void getPropertyStringListStringNull() {
		propertiesServices.getProperty("", null);
	}

	@Test(expected = NullPointerException.class)
	public void getPropertyStringListNullList() {
		propertiesServices.getProperty(null, new ArrayList<Object>());
	}

	@Test
	public void getPropertyStringListNotExising() {
		assertEquals("", propertiesServices.getProperty("notExisting", new ArrayList<Object>()));
	}

	@Test
	public void getPropertyStrinListg() {
		List<Object> parameters = new ArrayList<Object>();

		assertEquals("this is property one.", propertiesServices.getProperty("property1", parameters));
		assertEquals("this is property two: {0}.", propertiesServices.getProperty("property2", parameters));

		parameters.add("param1");
		parameters.add("param2");
		parameters.add("param3");

		assertEquals("this is property one.", propertiesServices.getProperty("property1", parameters));
		assertEquals("this is property two: param1.", propertiesServices.getProperty("property2",
				parameters));
	}

}
