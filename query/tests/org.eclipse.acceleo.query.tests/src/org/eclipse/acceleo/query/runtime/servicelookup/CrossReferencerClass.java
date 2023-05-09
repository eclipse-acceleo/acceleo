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

import org.eclipse.acceleo.query.runtime.CrossReferenceProvider;

public class CrossReferencerClass {
	private CrossReferenceProvider crossReferencer;

	public CrossReferencerClass(CrossReferenceProvider crossReferencer) {
		this.crossReferencer = crossReferencer;
	}

	public String service0(String arg) {
		return "service0";
	}

	public CrossReferenceProvider getCrossReferencer() {
		return crossReferencer;
	}

}
