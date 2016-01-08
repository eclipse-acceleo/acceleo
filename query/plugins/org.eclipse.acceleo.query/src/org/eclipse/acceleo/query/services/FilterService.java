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

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.impl.JavaMethodService;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.AbstractCollectionType;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierSetLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EClassifier;

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
	public Set<IType> validateAllType(ValidationServices services,
			IReadOnlyQueryEnvironment queryEnvironment, Map<List<IType>, Set<IType>> allTypes) {
		final Set<IType> result = new LinkedHashSet<IType>();
		final StringBuilder builder = new StringBuilder();

		for (Entry<List<IType>, Set<IType>> entry : allTypes.entrySet()) {
			final Set<EClassifier> eClassEClasses = ImmutableSet.copyOf(queryEnvironment
					.getEPackageProvider().getTypes("ecore", "EClass"));
			if (entry.getKey().size() > filterIndex) {
				final Set<IType> filterTypes = Sets.newLinkedHashSet();
				if (entry.getKey().get(filterIndex) instanceof EClassifierSetLiteralType) {
					for (EClassifier eClassifier : ((EClassifierSetLiteralType)entry.getKey()
							.get(filterIndex)).getEClassifiers()) {
						filterTypes.add(new EClassifierType(queryEnvironment, eClassifier));
					}
				} else if (entry.getKey().get(filterIndex) instanceof EClassifierLiteralType) {
					filterTypes.add(entry.getKey().get(filterIndex));
				} else if (eClassEClasses.contains(entry.getKey().get(filterIndex).getType())
						|| ((entry.getKey().get(filterIndex) instanceof SetType && eClassEClasses
								.contains(((AbstractCollectionType)entry.getKey().get(filterIndex))
										.getCollectionType().getType())))) {
					final Collection<EClassifier> eObjectEClasses = queryEnvironment.getEPackageProvider()
							.getTypes("ecore", "EObject");
					for (EClassifier eObjectEClass : eObjectEClasses) {
						filterTypes.add(new EClassifierType(queryEnvironment, eObjectEClass));
					}
				} else {
					filterTypes.add(entry.getKey().get(filterIndex));
				}
				for (IType filterType : filterTypes) {
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
							final IType loweredType = services.lower(filterType, rawType);
							if (loweredType != null) {
								result.add(unrawType(queryEnvironment, possibleType, loweredType));
							}
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
					+ builder.toString(), getName());
			if (List.class.isAssignableFrom(getMethod().getReturnType())) {
				result.add(new SequenceType(queryEnvironment, nothing));
			} else if (Set.class.isAssignableFrom(getMethod().getReturnType())) {
				result.add(new SetType(queryEnvironment, nothing));
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
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param originalType
	 *            the original {@link IType}
	 * @param rawType
	 *            the raw {@link IType}
	 * @return the given raw type into an {@link ICollectionType} or leave it raw according to the given
	 *         original {@link IType}
	 */
	private IType unrawType(IReadOnlyQueryEnvironment queryEnvironment, IType originalType, IType rawType) {
		final IType result;

		if (originalType instanceof SequenceType) {
			result = new SequenceType(queryEnvironment, rawType);
		} else if (originalType instanceof SetType) {
			result = new SetType(queryEnvironment, rawType);
		} else {
			result = rawType;
		}

		return result;
	}

}
