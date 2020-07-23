/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.tests.resolver;

public class TestsServices {

	/**
	 * Test service that return a simple message.
	 * 
	 * @param obj
	 *            any object
	 * @return a simple message
	 */
	public String getMessage(Object obj) {
		return "This is the message...";
	}

}
