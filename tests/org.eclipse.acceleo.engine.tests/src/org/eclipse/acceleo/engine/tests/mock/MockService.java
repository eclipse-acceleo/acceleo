/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.tests.mock;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * This will be used throughout the unit tests to check java services can properly be parsed and called.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class MockService {
	/**
	 * This service can be called with an EClass and will return this EClass' name.
	 * 
	 * @param clazz
	 *            Receiver of the service.
	 * @return Name of the given EClass.
	 */
	public String serviceClass(EClass clazz) {
		return clazz.getName();
	}

	/**
	 * This service can be called with an EClass and a String, it will return <code>true</code> if this
	 * EClass' name is equal to <code>expectedName</code>.
	 * 
	 * @param clazz
	 *            Receiver of the service.
	 * @param expectedName
	 *            expected name of the class.
	 * @return <code>true</code> if this EClass' name is equal to <code>expectedName</code>
	 */
	public boolean serviceClassString(EClass clazz, String expectedName) {
		return clazz.getName().equals(expectedName);
	}

	/**
	 * This service can be called with two EObjects. it will allow us to check that <code>instanceof</code>s
	 * work as expected.
	 * 
	 * @param clazz
	 *            Receiver of the service.
	 * @param expectedContainer
	 *            Expected container of the class.
	 * @return <code>true</code> if the container of <code>clazz</code> is <code>expectedContainer</code>. It
	 *         will require instanceofs to work.
	 */
	public boolean serviceClassInstanceOf(EObject clazz, EObject expectedContainer) {
		boolean result = false;
		if (clazz instanceof EClass && expectedContainer instanceof EPackage) {
			result = clazz.eContainer() == expectedContainer;
		}
		return result;
	}
}
