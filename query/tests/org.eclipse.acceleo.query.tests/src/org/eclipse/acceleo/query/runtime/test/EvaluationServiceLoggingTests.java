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
package org.eclipse.acceleo.query.runtime.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.impl.EvaluationServices;
import org.eclipse.acceleo.query.runtime.impl.QueryEnvironment;
import org.eclipse.acceleo.query.runtime.impl.ScopedEnvironment;
import org.eclipse.acceleo.query.runtime.lookup.basic.BasicLookupEngine;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class EvaluationServiceLoggingTests {

	private static final String NON_EOBJECT_FEATURE_ACCESS = "Attempt to access feature (containment) on a non ModelObject value (java.lang.Integer).";

	private static final String SERVICE_NOT_FOUND = "Couldn't find the noservice(java.lang.Integer) service";

	private static final String VARIABLE_NOT_FOUND = "Couldn't find the novariable variable";

	private static final String SERVICE_THROWS_EXCEPTION = "Exception while calling serviceThrowsException(java.lang.Integer)";

	private static final String UNKNOWN_FEATURE = "Feature noname not found in EClass EAttribute";

	private static final String SERVICE_RETURNS_NULL = "Service serviceReturnsNull(java.lang.Integer) returned a null value";

	ScopedEnvironment variables;

	BasicLookupEngine engine;

	QueryEnvironment queryEnvironment;

	EvaluationServices services;

	private String lastLogMessage;

	private Level lastLogLevel;

	private Throwable lastLogThrowable;

	private LinkedHashSet<Object> createSet(Object... elements) {
		return Sets.newLinkedHashSet(Lists.newArrayList(elements));
	}

	@Before
	public void setup() {
		queryEnvironment = new QueryEnvironment(null);
		engine = queryEnvironment.getLookupEngine();
		TestServiceDefinition instance = new TestServiceDefinition();
		try {
			engine.registerServices(TestServiceDefinition.class);
		} catch (InvalidAcceleoPackageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		variables = new ScopedEnvironment();
		variables.pushScope(new HashMap<String, Object>());
		variables.defineVariable("x", 1);
		variables.defineVariable("y", 2);
		services = new EvaluationServices(queryEnvironment, true);
		services.getLogger().addHandler(new Handler() {

			@Override
			public void close() throws SecurityException {

			}

			@Override
			public void flush() {

			}

			@Override
			public void publish(LogRecord record) {
				lastLogMessage = record.getMessage();
				lastLogLevel = record.getLevel();
				lastLogThrowable = record.getThrown();
			}
		});
		lastLogMessage = null;
		lastLogLevel = null;
		lastLogThrowable = null;

	}

	/**
	 * Tests that, when an unknown variable is accessed, a warning is emitted with the following message :
	 */
	@Test
	public void variableNotFoundLogTest() {
		services.getVariableValue(variables, "novariable");
		assertEquals(Level.WARNING, lastLogLevel);
		assertEquals(VARIABLE_NOT_FOUND, lastLogMessage);
		assertNull(lastLogThrowable);
	}

	@Test
	public void featureNotFoundLogTest() {
		EAttribute attribute = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);
		attribute.setName("attr0");
		services.featureAccess(attribute, "noname");
		assertEquals(Level.WARNING, lastLogLevel);
		assertEquals(UNKNOWN_FEATURE, lastLogMessage);
		assertNull(lastLogThrowable);
	}

	@Test
	public void featureAccessOnObjectLogTest() {
		services.featureAccess(new Integer(1), "containment");
		assertEquals(Level.WARNING, lastLogLevel);
		assertEquals(NON_EOBJECT_FEATURE_ACCESS, lastLogMessage);
		assertNull(lastLogThrowable);
	}

	@Test
	public void serviceNotFoundLogTest() {
		services.call("noservice", new Object[] {1 });
		assertEquals(Level.WARNING, lastLogLevel);
		assertEquals(SERVICE_NOT_FOUND, lastLogMessage);
		assertNull(lastLogThrowable);
	}

	@Test
	public void serviceReturnsNullLogTest() {
		services.call("serviceReturnsNull", new Object[] {1 });
		assertEquals(Level.WARNING, lastLogLevel);
		assertEquals(SERVICE_RETURNS_NULL, lastLogMessage);
		assertNull(lastLogThrowable);
	}

	@Test
	public void serviceThrowsExceptionLogTest() {
		services.call("serviceThrowsException", new Object[] {1 });
		assertEquals(Level.WARNING, lastLogLevel);
		assertEquals(SERVICE_THROWS_EXCEPTION, lastLogMessage);
		assertTrue(lastLogThrowable instanceof NullPointerException);
	}

}
