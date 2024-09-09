/*******************************************************************************
 * Copyright (c) 2015, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

	public Collection<EClass> getCollection(EClass eCls) {
		final List<EClass> res = new ArrayList<>();

		res.add(eCls);

		return res;
	}

}
