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

import java.util.HashMap;

import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.impl.EvaluationServices;
import org.eclipse.acceleo.query.runtime.impl.QueryEnvironment;
import org.eclipse.acceleo.query.runtime.impl.ScopedEnvironment;
import org.eclipse.acceleo.query.runtime.lookup.basic.BasicLookupEngine;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class EvaluationServiceStatusTests {

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

	@Before
	public void setup() {
		queryEnvironment = (QueryEnvironment)Query.newEnvironmentWithDefaultServices(null);
		engine = queryEnvironment.getLookupEngine();
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
		services = new EvaluationServices(queryEnvironment);
	}

	/**
	 * Tests that, when an unknown variable is accessed, a warning is emitted with the following message :
	 */
	@Test
	public void variableNotFoundStatusTest() {
		Diagnostic status = new BasicDiagnostic();
		services.getVariableValue(variables, "novariable", status);

		assertEquals(Diagnostic.WARNING, status.getSeverity());
		assertEquals(1, status.getChildren().size());

		Diagnostic child = status.getChildren().iterator().next();
		assertEquals(VARIABLE_NOT_FOUND, child.getMessage());
		assertNull(child.getException());
	}

	@Test
	public void featureNotFoundStatusTest() {
		EAttribute attribute = (EAttribute)EcoreUtil.create(EcorePackage.Literals.EATTRIBUTE);
		attribute.setName("attr0");

		Diagnostic status = new BasicDiagnostic();
		services.featureAccess(attribute, "noname", status);

		assertEquals(Diagnostic.WARNING, status.getSeverity());
		assertEquals(1, status.getChildren().size());

		Diagnostic child = status.getChildren().iterator().next();
		assertEquals(UNKNOWN_FEATURE, child.getMessage());
		assertNull(child.getException());
	}

	@Test
	public void featureAccessOnObjectStatusTest() {
		Diagnostic status = new BasicDiagnostic();
		services.featureAccess(new Integer(1), "containment", status);

		assertEquals(Diagnostic.WARNING, status.getSeverity());
		assertEquals(1, status.getChildren().size());

		Diagnostic child = status.getChildren().iterator().next();
		assertEquals(NON_EOBJECT_FEATURE_ACCESS, child.getMessage());
		assertNull(child.getException());
	}

	@Test
	public void serviceNotFoundStatusTest() {
		Diagnostic status = new BasicDiagnostic();
		services.call("noservice", new Object[] {1 }, status);

		assertEquals(Diagnostic.WARNING, status.getSeverity());
		assertEquals(1, status.getChildren().size());

		Diagnostic child = status.getChildren().iterator().next();
		assertEquals(SERVICE_NOT_FOUND, child.getMessage());
		assertNull(child.getException());
	}

	@Test
	public void serviceReturnsNullStatusTest() {
		Diagnostic status = new BasicDiagnostic();
		services.call("serviceReturnsNull", new Object[] {1 }, status);

		assertEquals(Diagnostic.WARNING, status.getSeverity());
		assertEquals(1, status.getChildren().size());

		Diagnostic child = status.getChildren().iterator().next();
		assertEquals(SERVICE_RETURNS_NULL, child.getMessage());
		assertNull(child.getException());
	}

	@Test
	public void serviceThrowsExceptionStatusTest() {
		Diagnostic status = new BasicDiagnostic();
		services.call("serviceThrowsException", new Object[] {1 }, status);

		// TODO shouldn't this be an error level?
		assertEquals(Diagnostic.WARNING, status.getSeverity());
		assertEquals(1, status.getChildren().size());

		Diagnostic child = status.getChildren().iterator().next();
		assertEquals(SERVICE_THROWS_EXCEPTION, child.getMessage());
		assertTrue(child.getException() instanceof NullPointerException);
	}

}