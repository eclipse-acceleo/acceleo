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
package org.eclipse.acceleo.query.runtime.servicelookup;

import java.util.logging.Logger;

import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.lookup.basic.BasicLookupEngine;
import org.eclipse.acceleo.query.services.tests.AbstractEngineInitializationWithCrossReferencer;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class BasicLookupCrossReferencerTest extends AbstractEngineInitializationWithCrossReferencer {
	BasicLookupEngine engine;

	private static final Class<?>[] NO_ARG = {};

	@Before
	public void setup() {
		Logger logger = Logger.getLogger("BasicLookupCrossReferenceTest");
		EClass eClass = EcoreFactory.eINSTANCE.createEClass();

		IQueryEnvironment queryEnvironment = getQueryEnvironnementWithCrossReferencer(eClass, logger);
		engine = queryEnvironment.getLookupEngine();
		try {
			engine.addServices(CrossReferencerClass.class);
		} catch (InvalidAcceleoPackageException e) {
			throw new UnsupportedOperationException("shouldn't happen.", e);
		}
	}

	/**
	 * Tests that we set the cross referencer to any services providing a method with the name
	 * {@link org.eclipse.acceleo.query.runtime.lookup.basic.BasicLookupEngine.SET_CROSS_REFERENCER_METHOD_NAME}
	 */
	@Test
	public void detectionCrossReferencerTest() {
		assertEquals("service0", engine.lookup("service0", NO_ARG).getServiceMethod().getName());
		assertNull(engine.lookup("setCrossReferencer", NO_ARG));

		CrossReferencerClass crossReferencer = (CrossReferencerClass)engine.lookup("service0", NO_ARG)
				.getServiceInstance();
		assertNotNull(crossReferencer.getCrossReferencer());
	}

}
