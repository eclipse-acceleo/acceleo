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

import org.eclipse.acceleo.query.runtime.CrossReferenceProvider;

public class CrossReferencerClass {
	private CrossReferenceProvider crossReferencer;

	public String service0() {
		return "service0";
	}

	public void setCrossReferencer(CrossReferenceProvider crossReferencer) {
		this.crossReferencer = crossReferencer;
	}

	public CrossReferenceProvider getCrossReferencer() {
		return crossReferencer;
	}

}
