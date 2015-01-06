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
package org.eclipse.acceleo.query.runtime.lookup.opt;

import org.eclipse.acceleo.query.runtime.IService;

//CHECKSTYLE:OFF
public class LeafLookupNode extends LookupNode {

	private IService method;

	public LeafLookupNode() {
	}

	@Override
	public IService lookup(Class<?>[] argumentTypes, int step) {
		assert step == 0;
		return method;
	}

	@Override
	void addMethod(IService method, int step) {
		this.method = method;
	}
}
