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
package org.eclipse.acceleo.query.runtime.servicelookup;

import org.eclipse.emf.ecore.EObject;

public class ServicesCountCalls {
	private static int COUNTS = 0;

	public Boolean checkAlwaysTrue(EObject any) {
		COUNTS++;
		return Boolean.TRUE;
	}

	public Boolean checkAlwaysFalse(EObject any) {
		COUNTS++;
		return Boolean.FALSE;
	}

	public int getCallCounts(EObject any) {
		return COUNTS;
	}

	public void reset(EObject any) {
		COUNTS = 0;
	}
}
