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
package org.eclipse.acceleo.query.runtime.impl;

import java.lang.reflect.Type;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * Abstract implementation of an {@link IService}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractService implements IService {

	@Override
	public Set<IType> getType(ValidationServices services, EPackageProvider provider, List<IType> argTypes) {
		final Set<IType> result = new LinkedHashSet<IType>();
		Type returnType = getServiceMethod().getGenericReturnType();

		result.addAll(services.getIType(returnType));

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#validateAllType(org.eclipse.acceleo.query.runtime.impl.ValidationServices,
	 *      org.eclipse.acceleo.query.runtime.impl.EPackageProvider, java.util.Map)
	 */
	@Override
	public Set<IType> validateAllType(ValidationServices services, EPackageProvider provider,
			Map<List<IType>, Set<IType>> allTypes) {
		final Set<IType> result = new LinkedHashSet<IType>();

		for (Entry<List<IType>, Set<IType>> entry : allTypes.entrySet()) {
			result.addAll(entry.getValue());
		}

		return result;
	}
}
