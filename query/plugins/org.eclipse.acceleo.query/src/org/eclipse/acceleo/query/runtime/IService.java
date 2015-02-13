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
package org.eclipse.acceleo.query.runtime;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.impl.EPackageProvider;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Interface that a service usable in query evaluation engine must implement.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public interface IService {
	/**
	 * returns the instance on which the service will be called.
	 * 
	 * @return the instance on which to call the service.
	 */
	Object getServiceInstance();

	/**
	 * Returns the actual method that realizes the service.
	 * 
	 * @return the method that realizes the service.
	 */
	Method getServiceMethod();

	/**
	 * Gets the {@link IType} of elements returned by the service.
	 * 
	 * @param services
	 *            the {@link ValidationServices}
	 * @param provider
	 *            the {@link EPackageProvider}
	 * @param argTypes
	 *            arguments {@link IType}
	 * @return the {@link IType} of elements returned by the service
	 */
	Set<IType> getType(ValidationServices services, EPackageProvider provider, List<IType> argTypes);

	/**
	 * Validates all couple of arguments {@link IType} and the {@link IType} of elements returned by the
	 * service.
	 * 
	 * @param services
	 *            the {@link ValidationServices}
	 * @param provider
	 *            the {@link EPackageProvider}
	 * @param allTypes
	 *            all couple of arguments {@link IType} and the {@link IType} of elements returned by the
	 *            service
	 * @return validated {@link IType}
	 */
	Set<IType> validateAllType(ValidationServices services, EPackageProvider provider,
			Map<List<IType>, Set<IType>> allTypes);

}
