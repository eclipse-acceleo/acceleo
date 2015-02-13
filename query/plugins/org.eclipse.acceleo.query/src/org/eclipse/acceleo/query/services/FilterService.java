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
package org.eclipse.acceleo.query.services;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.impl.EPackageProvider;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.runtime.lookup.basic.Service;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;

/**
 * {@link org.eclipse.acceleo.query.runtime.IService#validateAllType(org.eclipse.acceleo.query.runtime.impl.ValidationServices, org.eclipse.acceleo.query.runtime.impl.EPackageProvider, java.util.Map)
 * Validates} type according to the second argument of the service.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class FilterService extends Service {

	/**
	 * Creates a new service instance given a method and an instance.
	 * 
	 * @param serviceMethod
	 *            the method that realizes the service
	 * @param serviceInstance
	 *            the instance on which the service must be called
	 */
	public FilterService(Method serviceMethod, Object serviceInstance) {
		super(serviceMethod, serviceInstance);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.AbstractService#validateAllType(org.eclipse.acceleo.query.runtime.impl.ValidationServices,
	 *      org.eclipse.acceleo.query.runtime.impl.EPackageProvider, java.util.Map)
	 */
	@Override
	public Set<IType> validateAllType(ValidationServices services, EPackageProvider provider,
			Map<List<IType>, Set<IType>> allTypes) {
		final Set<IType> result = new LinkedHashSet<IType>();

		for (Entry<List<IType>, Set<IType>> entry : allTypes.entrySet()) {
			for (IType possibleType : entry.getValue()) {
				final IType rawType;
				if (possibleType instanceof ICollectionType) {
					rawType = ((ICollectionType)possibleType).getCollectionType();
				} else {
					rawType = possibleType;
				}
				if (entry.getKey().get(1).isAssignableFrom(rawType)
						|| rawType.isAssignableFrom(entry.getKey().get(1))) {
					result.add(possibleType);
					break;
				}
			}
		}

		if (result.isEmpty()) {
			result.add(new NothingType("Nothing will be left after calling " + getServiceMethod().getName()));
		}

		return result;
	}

}
