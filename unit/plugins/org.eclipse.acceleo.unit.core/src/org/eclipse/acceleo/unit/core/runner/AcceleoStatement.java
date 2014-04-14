/*******************************************************************************
 * Copyright (c) 2006, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.unit.core.runner;

import org.eclipse.acceleo.unit.core.generation.AbstractGenerationHelper;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * The acceleo statement class extend the Junit Statement abstract class.
 * 
 * @author <a href="mailto:hugo.marchadour@obeo.fr">Hugo Marchadour</a>
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoStatement extends Statement {

	/**
	 * The unit method.
	 */
	private FrameworkMethod method;

	/**
	 * The unded test object.
	 */
	private Object testObject;

	/**
	 * The acceleo generation helper.
	 */
	private AbstractGenerationHelper helper;

	/**
	 * The constructor.
	 * 
	 * @param testObject
	 *            under test object.
	 * @param method
	 *            unit method.
	 * @param helper
	 *            acceleo generation helper.
	 */
	public AcceleoStatement(Object testObject, FrameworkMethod method, AbstractGenerationHelper helper) {

		this.helper = helper;
		this.method = method;
		this.testObject = testObject;
	}

	// CHECKSTYLE:OFF
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.junit.runners.model.Statement#evaluate()
	 */
	@Override
	public void evaluate() throws Throwable {
		// CHECKSTYLE:ON
		method.invokeExplosively(testObject, helper);
	}
}
