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
package org.eclipse.acceleo.query.tests.services;

import org.eclipse.emf.ecore.EClass;

public class EObjectServices {

	public String customToString(EClass any) {
		return any.getName();
	}

	public int someService(EClass any, String value) {
		return 1;
	}

	public static EObjectServices serviceWithNoParameter() {
		return new EObjectServices();
	}

}
