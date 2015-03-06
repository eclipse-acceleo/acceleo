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
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;

/**
 * {@link org.eclipse.acceleo.query.runtime.IService#validateAllType(org.eclipse.acceleo.query.runtime.impl.ValidationServices, org.eclipse.acceleo.query.runtime.impl.EPackageProvider, java.util.Map)
 * Validates} type according to the second argument of the service.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class FilterService extends Service {

	/**
	 * The index of the filtering {@link IType}.
	 */
	final int filterIndex = 1;

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
		final StringBuilder builder = new StringBuilder();

		for (Entry<List<IType>, Set<IType>> entry : allTypes.entrySet()) {
			if (entry.getKey().size() > filterIndex) {
				final IType filterType = entry.getKey().get(filterIndex);
				for (IType possibleType : entry.getValue()) {
					if (possibleType instanceof NothingType) {
						builder.append("\n");
						builder.append(((NothingType)possibleType).getMessage());
					} else {
						final IType rawType;
						if (possibleType instanceof ICollectionType) {
							rawType = ((ICollectionType)possibleType).getCollectionType();
						} else {
							rawType = possibleType;
						}
						final IType loweredType = services.lower(filterType, rawType);
						if (loweredType != null) {
							result.add(unrawType(possibleType, loweredType));
							break;
						}
					}
				}
			} else {
				for (IType possibleType : entry.getValue()) {
					if (possibleType instanceof NothingType) {
						builder.append("\n");
						builder.append(((NothingType)possibleType).getMessage());
					} else {
						result.add(possibleType);
					}
				}
			}
		}

		if (result.isEmpty()) {
			final NothingType nothing = services.nothing("Nothing will be left after calling %s:"
					+ builder.toString(), getServiceMethod().getName());
			if (List.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SequenceType(nothing));
			} else if (Set.class.isAssignableFrom(getServiceMethod().getReturnType())) {
				result.add(new SetType(nothing));
			} else {
				result.add(nothing);
			}
		}

		return result;
	}

	/**
	 * Puts the given raw type into an {@link ICollectionType} or leave it raw according to the given original
	 * {@link IType}.
	 * 
	 * @param originalType
	 *            the original {@link IType}
	 * @param rawType
	 *            the raw {@link IType}
	 * @return the given raw type into an {@link ICollectionType} or leave it raw according to the given
	 *         original {@link IType}
	 */
	private IType unrawType(IType originalType, IType rawType) {
		final IType result;

		if (originalType instanceof SequenceType) {
			result = new SequenceType(rawType);
		} else if (originalType instanceof SetType) {
			result = new SetType(rawType);
		} else {
			result = rawType;
		}

		return result;
	}

}
