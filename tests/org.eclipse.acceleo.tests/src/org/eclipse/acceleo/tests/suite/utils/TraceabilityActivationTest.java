/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.tests.suite.utils;

import junit.framework.TestCase;

import org.eclipse.acceleo.common.preference.AcceleoPreferences;

/**
 * This test's only purpose is to enable the traceability visitor for all subsequent tests.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class TraceabilityActivationTest extends TestCase {
	/**
	 * {@inheritDoc}
	 * 
	 * @see junit.framework.TestCase#getName()
	 */
	@Override
	public String getName() {
		return "Traceability Activation"; //$NON-NLS-1$
	}

	/**
	 * Activates the traceability visitor for all subsequent tests.
	 */
	public void testActivateTraceability() {
		AcceleoPreferences.switchTraceability(true);
		assertTrue(AcceleoPreferences.isTraceabilityEnabled());
	}
}
