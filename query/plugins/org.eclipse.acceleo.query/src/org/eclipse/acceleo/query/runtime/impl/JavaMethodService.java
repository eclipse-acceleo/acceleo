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

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.runtime.AcceleoQueryValidationException;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IJavaType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EClass;

/**
 * Abstract implementation of an {@link org.eclipse.acceleo.query.runtime.IService IService} for
 * {@link Method}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class JavaMethodService extends AbstractService {

	/**
	 * The {@link org.eclipse.acceleo.query.runtime.IService#getPriority() priority} for
	 * {@link JavaMethodService}.
	 */
	public static final int PRIORITY = 10;

	/**
	 * The method that realizes the service.
	 */
	private final Method method;

	/**
	 * The instance on which the service must be called.
	 */
	private final Object instance;

	/**
	 * Creates a new service instance given a method and an instance.
	 * 
	 * @param method
	 *            the method that realizes the service
	 * @param serviceInstance
	 *            the instance on which the service must be called
	 */
	public JavaMethodService(Method method, Object serviceInstance) {
		this.instance = serviceInstance;
		this.method = method;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getName()
	 */
	@Override
	public String getName() {
		return method.getName();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getParameterTypes(org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment)
	 */
	@Override
	public List<IType> getParameterTypes(IReadOnlyQueryEnvironment queryEnvironment) {
		final List<IType> result = new ArrayList<IType>();

		for (Class<?> cls : method.getParameterTypes()) {
			result.add(getClassType(queryEnvironment, cls));
		}

		return result;
	}

	/**
	 * Gets the {@link IJavaType} corresponding to the given {@link Type}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param type
	 *            the {@link Type}
	 * @return the {@link IJavaType} corresponding to the given {@link Type}
	 */
	protected IJavaType getClassType(IReadOnlyQueryEnvironment queryEnvironment, Type type) {
		final IJavaType result;

		if (type instanceof ParameterizedType) {
			final Class<?> cls = (Class<?>)((ParameterizedType)type).getRawType();
			if (List.class.isAssignableFrom(cls)) {
				final IType t = getClassType(queryEnvironment, ((ParameterizedType)type)
						.getActualTypeArguments()[0]);
				result = new SequenceType(queryEnvironment, t);
			} else if (Set.class.isAssignableFrom(cls)) {
				final IType t = getClassType(queryEnvironment, ((ParameterizedType)type)
						.getActualTypeArguments()[0]);
				result = new SetType(queryEnvironment, t);
			} else {
				result = new ClassType(queryEnvironment, cls);
			}
		} else if (type instanceof Class<?>) {
			final Class<?> cls = (Class<?>)type;
			result = new ClassType(queryEnvironment, cls);
		} else {
			result = new ClassType(queryEnvironment, Object.class);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getNumberOfParameters()
	 */
	@Override
	public int getNumberOfParameters() {
		return method.getParameterTypes().length;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.AbstractService#internalInvoke(java.lang.Object[])
	 */
	@Override
	protected Object internalInvoke(Object[] arguments) throws Exception {
		return method.invoke(instance, arguments);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getPriority()
	 */
	@Override
	public int getPriority() {
		return PRIORITY;
	}

	@Override
	public Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
			IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
		final Set<IType> result = new LinkedHashSet<IType>();
		Type returnType = method.getGenericReturnType();

		result.addAll(services.getIType(returnType));

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.AbstractService#matches(org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment,
	 *      org.eclipse.acceleo.query.validation.type.IType[])
	 */
	@Override
	public boolean matches(IReadOnlyQueryEnvironment queryEnvironment, IType[] argumentTypes) {
		final ClassType[] classTypes = getClassTypes(queryEnvironment, argumentTypes);

		return super.matches(queryEnvironment, classTypes);
	}

	/**
	 * Gets the {@link ClassType} argument types from the given {@link IType} argument types.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param iTypes
	 *            the {@link IType} argument types
	 * @return the {@link ClassType} argument types from the given {@link IType} argument types
	 */
	protected ClassType[] getClassTypes(IReadOnlyQueryEnvironment queryEnvironment, IType[] iTypes) {
		ClassType[] result = new ClassType[iTypes.length];

		for (int i = 0; i < iTypes.length; ++i) {
			result[i] = new ClassType(queryEnvironment, getClass(queryEnvironment, iTypes[i]));
		}

		return result;
	}

	/**
	 * Gets an {@link Class} from a {@link IType}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param iType
	 *            the {@link IType}
	 * @return an {@link Class} from a {@link IType}
	 */
	protected Class<?> getClass(IReadOnlyQueryEnvironment queryEnvironment, IType iType) {
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
	 * Gets the {@link Method}.
	 * 
	 * @return the {@link Method}
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * Gets the {@link Object} instance.
	 * 
	 * @return the {@link Object} instance if any, <code>null</code> otherwise
	 */
	public Object getInstance() {
		return instance;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getShortSignature()
	 */
	@Override
	public String getShortSignature() {
		return serviceShortSignature(method.getParameterTypes());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getLongSignature()
	 */
	@Override
	public String getLongSignature() {
		return method.toString();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof JavaMethodService && ((JavaMethodService)obj).getMethod().equals(getMethod());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getMethod().hashCode();
	}

}
