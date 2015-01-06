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
package org.eclipse.acceleo.query.runtime.lookup.basic;

import java.lang.reflect.Method;

import org.eclipse.acceleo.query.runtime.impl.AbstractService;

/**
 * Implementation of the {@link org.eclipse.acceleo.query.runtime.IService IService} interface.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class Service extends AbstractService {
	/**
	 * The method that realizes the service.
	 */
	private final Method serviceMethod;

	/**
	 * The instance on which the service must be called.
	 */
	private final Object serviceInstance;

	/**
	 * Creates a new service instance given a method and an instance.
	 * 
	 * @param serviceMethod
	 *            the method that realizes the service
	 * @param serviceInstance
	 *            the instance on which the service must be called
	 */
	public Service(Method serviceMethod, Object serviceInstance) {
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
