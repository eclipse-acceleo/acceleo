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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

import org.eclipse.acceleo.query.runtime.AcceleoQueryValidationException;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IJavaType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.ecore.EClass;

/**
 * Abstract implementation of the language services.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractLanguageServices {

	/**
	 * instance of Nothing to be used as a unique instance.
	 */
	public static final Nothing NOTHING = new Nothing();

	/**
	 * Log message used when accessing a feature on a JavaObject.
	 */
	protected static final String NON_EOBJECT_FEATURE_ACCESS = "Attempt to access feature (%s) on a non ModelObject value (%s).";

	/**
	 * Log message used when a called service was not found.
	 */
	protected static final String SERVICE_NOT_FOUND = "Couldn't find the %s service";

	/**
	 * Log message used when a called service or EOperation was not found.
	 */
	protected static final String SERVICE_EOPERATION_NOT_FOUND = "Couldn't find the %s service or EOperation";

	/**
	 * Log message used when a requested variable was not found.
	 */
	protected static final String VARIABLE_NOT_FOUND = "Couldn't find the %s variable";

	/**
	 * Log message used when accessing an unknown feature.
	 */
	protected static final String UNKNOWN_FEATURE = "Feature %s not found in EClass %s";

	/**
	 * The {@link IReadOnlyQueryEnvironment}.
	 */
	protected final IReadOnlyQueryEnvironment queryEnvironment;

	/**
	 * Logger used to emit error and warning messages.
	 */
	protected final Logger logger;

	/**
	 * Flag used to remember whether this instance should emit log messages or not.
	 */
	protected final boolean doLog;

	/**
	 * Creates a new service instance given a {@link IReadOnlyQueryEnvironment} and logging flag.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment} to use
	 * @param doLog
	 *            when <code>true</code> the resulting instance will log error and warning messages.
	 */
	public AbstractLanguageServices(IReadOnlyQueryEnvironment queryEnvironment, boolean doLog) {
		this.queryEnvironment = queryEnvironment;
		this.doLog = doLog;
		logger = queryEnvironment.getLogger();
	}

	public Logger getLogger() {
		return logger;
	}

	/**
	 * Gets the {@link Class} argument types from the given {@link IType} argument types.
	 * 
	 * @param list
	 *            the {@link IType} argument types
	 * @return the {@link Class} argument types from the given {@link IType} argument types
	 */
	protected Class<?>[] getArgumentTypes(Collection<IType> list) {
		Class<?>[] result = new Class<?>[list.size()];

		final List<Class<?>> types = getClasses(list);
		for (int i = 0; i < types.size(); ++i) {
			result[i] = types.get(i);
		}

		return result;
	}

	/**
	 * Converts a {@link Collection} of {@link Class} into a {@link List} of {@link Class}.
	 * 
	 * @param iTypes
	 *            a {@link List} of {@link Class}
	 * @return a {@link List} of {@link Class}
	 */
	protected List<Class<?>> getClasses(Collection<IType> iTypes) {
		final List<Class<?>> result = new ArrayList<Class<?>>(iTypes.size());
		for (IType iType : iTypes) {
			result.add(getClass(iType));
		}
		return result;
	}

	/**
	 * Gets an {@link Class} from a {@link IType}.
	 * 
	 * @param iType
	 *            the {@link IType}
	 * @return an {@link Class} from a {@link IType}
	 */
	protected Class<?> getClass(IType iType) {
		Class<?> result;

		if (iType instanceof EClassifierLiteralType) {
			result = EClass.class;
		} else if (iType instanceof EClassifierType) {
			result = queryEnvironment.getEPackageProvider().getClass(((EClassifierType)iType).getType());
		} else if (iType instanceof IJavaType) {
			result = ((IJavaType)iType).getType();
		} else {
			throw new AcceleoQueryValidationException(iType.getClass().getCanonicalName());
		}

		if (result != null) {
			if ("boolean".equals(result.getName())) {
				result = Boolean.class;
			} else if ("int".equals(result.getName())) {
				result = Integer.class;
			} else if ("double".equals(result.getName())) {
				result = Double.class;
			}
		}

		return result;
	}

	/**
	 * Gets the {@link IReadOnlyQueryEnvironment}.
	 * 
	 * @return the queryEnvironment the {@link IReadOnlyQueryEnvironment}
	 */
	public IReadOnlyQueryEnvironment getQueryEnvironment() {
		return queryEnvironment;
	}

}
