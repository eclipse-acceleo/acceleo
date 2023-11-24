/*******************************************************************************
 * Copyright (c) 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.services.collection;

import java.lang.reflect.Method;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.JavaMethodService;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.emf.ecore.EClassifier;

/**
 * Abstract collection service.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractCollectionService extends JavaMethodService {

	/** Externalized here to avoid multiple uses. */
	public static final String ONLY_NUMERIC_ERROR = "%s can only be used on a collection of numbers.";

	/**
	 * Message separator.
	 */
	protected static final String MESSAGE_SEPARATOR = "\n ";

	/**
	 * Creates a new service instance given a method and an instance.
	 * 
	 * @param method
	 *            the method that realizes the service
	 * @param serviceInstance
	 *            the instance on which the service must be called
	 * @param forWorkspace
	 *            tells if the {@link IService} will be used in a workspace
	 */
	public AbstractCollectionService(Method method, Object serviceInstance, boolean forWorkspace) {
		super(method, serviceInstance, forWorkspace);
	}

	/**
	 * Tells if the given {@link Object} is a is a boolean {@link org.eclipse.emf.ecore.EDataType EDataType}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param type
	 *            the {@link org.eclipse.emf.ecore.EDataType EDataType}
	 * @return <code>true</code> if the given {@link Object} is a is a boolean
	 *         {@link org.eclipse.emf.ecore.EDataType EDataType}, <code>false</code> otherwise
	 */
	protected boolean isBooleanType(IReadOnlyQueryEnvironment queryEnvironment, Object type) {
		final Class<?> typeClass;
		if (type instanceof EClassifier) {
			typeClass = queryEnvironment.getEPackageProvider().getClass((EClassifier)type);
		} else if (type instanceof ClassType) {
			typeClass = ((ClassType)type).getType();
		} else if (type instanceof Class<?>) {
			typeClass = (Class<?>)type;
		} else {
			return false;
		}

		return typeClass == Boolean.class || typeClass == boolean.class;
	}

}
