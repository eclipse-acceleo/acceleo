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

import java.lang.reflect.Method;

import org.eclipse.acceleo.query.runtime.impl.AbstractService;

//CHECKSTYLE:OFF
class Service extends AbstractService {
	private final Method serviceMethod;

	private final Object serviceInstance;

	Service(Method serviceMethod, Object serviceInstance) {
		this.serviceInstance = serviceInstance;
		this.serviceMethod = serviceMethod;
	}

	@Override
	public Object getServiceInstance() {
		return serviceInstance;
	}

	@Override
	public Method getServiceMethod() {
		return serviceMethod;
	}

}
