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
package org.eclipse.acceleo.query.services;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.impl.JavaMethodService;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;

/**
 * {@link org.eclipse.acceleo.query.runtime.IService#validateAllType(org.eclipse.acceleo.query.runtime.impl.ValidationServices, org.eclipse.acceleo.query.runtime.impl.EPackageProvider, java.util.Map)
 * Validates} type according to the second argument of the service.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class FilterService extends JavaMethodService {

	/**
	 * The index of the filtering {@link IType}.
	 */
	final int filterIndex;

	/**
	 * Creates a new service instance given a method and an instance.
	 * 
	 * @param serviceMethod
	 *            the method that realizes the service
	 * @param serviceInstance
	 *            the instance on which the service must be called
	 */
	public FilterService(Method serviceMethod, Object serviceInstance) {
		this(serviceMethod, serviceInstance, 1);
	}

	/**
	 * Creates a new service instance given a method and an instance.
	 * 
	 * @param serviceMethod
	 *            the method that realizes the service
	 * @param serviceInstance
	 *            the instance on which the service must be called
	 * @param filterIndex
	 *            the index of the filtering {@link IType}
	 * @since 4.0.0
	 */
	public FilterService(Method serviceMethod, Object serviceInstance, int filterIndex) {
		super(serviceMethod, serviceInstance);
		this.filterIndex = filterIndex;
	}

	@Override
	public Set<IType> validateAllType(ValidationServices services, IReadOnlyQueryEnvironment queryEnvironment,
			Map<List<IType>, Set<IType>> allTypes) {
		final Set<IType> result = new LinkedHashSet<IType>();
		final StringBuilder builder = new StringBuilder();

		for (Entry<List<IType>, Set<IType>> entry : allTypes.entrySet()) {
			for (IType possibleType : entry.getValue()) {
				final IType rawType;
				if (possibleType instanceof ICollectionType) {
					rawType = ((ICollectionType)possibleType).getCollectionType();
				} else {
					rawType = possibleType;
				}
				if (rawType instanceof NothingType) {
					builder.append("\n");
					builder.append(((NothingType)rawType).getMessage());
				} else {
					result.add(possibleType);
				}
			}
		}

		if (result.isEmpty()) {
			final NothingType nothing = services.nothing("Nothing will be left after calling %s:" + builder
					.toString(), getName());
			if (List.class.isAssignableFrom(getOrigin().getReturnType())) {
				result.add(new SequenceType(queryEnvironment, nothing));
			} else if (Set.class.isAssignableFrom(getOrigin().getReturnType())) {
				result.add(new SetType(queryEnvironment, nothing));
			} else {
				result.add(nothing);
			}
		}

		return result;
	}

}
