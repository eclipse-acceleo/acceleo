/*******************************************************************************
 * Copyright (c) 2015, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.AcceleoQueryValidationException;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.IEPackageProvider;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.completion.JavaMethodServiceCompletionProposal;
import org.eclipse.acceleo.query.validation.type.ClassLiteralType;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IJavaType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;

/**
 * Implementation of an {@link org.eclipse.acceleo.query.runtime.IService IService} for {@link Method}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class JavaMethodService extends AbstractService<Method> {

	/**
	 * The {@link org.eclipse.acceleo.query.runtime.IService#getPriority() priority} for
	 * {@link JavaMethodService}.
	 */
	public static final int PRIORITY = 200;

	/**
	 * The instance on which the service must be called.
	 */
	private final Object instance;

	/**
	 * Tells if the {@link IService} will be used in a workspace.
	 */
	private boolean forWorkspace;

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
	public JavaMethodService(Method method, Object serviceInstance, boolean forWorkspace) {
		super(method);
		this.instance = serviceInstance;
		this.forWorkspace = forWorkspace;
	}

	@Override
	public String getName() {
		return getOrigin().getName();
	}

	@Override
	public List<Set<IType>> computeParameterTypes(IReadOnlyQueryEnvironment queryEnvironment) {
		final List<Set<IType>> res = new ArrayList<>();

		for (Class<?> cls : getOrigin().getParameterTypes()) {
			res.add(Collections.singleton(getClassType(queryEnvironment, cls)));
		}

		return res;
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
				result = getClassJavaType(queryEnvironment, cls);
			}
		} else if (type instanceof Class<?>) {
			final Class<?> cls = (Class<?>)type;
			result = getClassJavaType(queryEnvironment, cls);
		} else {
			result = new ClassType(queryEnvironment, Object.class);
		}

		return result;
	}

	/**
	 * Gets the {@link IJavaType} for the given {@link IReadOnlyQueryEnvironment} and {@link Class}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param cls
	 *            the {@link Class}
	 * @return the {@link IJavaType} for the given {@link IReadOnlyQueryEnvironment} and {@link Class}
	 */
	protected IJavaType getClassJavaType(IReadOnlyQueryEnvironment queryEnvironment, Class<?> cls) {
		final IJavaType result;
		final IEPackageProvider ePackageProvider = queryEnvironment.getEPackageProvider();
		if (forWorkspace && EObject.class.isAssignableFrom(cls) && ePackageProvider.getEClassifiers(
				cls) == null) {
			final Set<EClassifier> eClassifiers = ePackageProvider.getEClassifiers(cls.getCanonicalName());
			if (eClassifiers != null && !eClassifiers.isEmpty()) {
				final Class<?> eClassifierCls = ePackageProvider.getClass(eClassifiers.iterator().next());
				result = new ClassType(queryEnvironment, eClassifierCls);
			} else {
				result = new ClassType(queryEnvironment, cls);
			}
		} else {
			result = new ClassType(queryEnvironment, cls);
		}
		return result;
	}

	@Override
	public int getNumberOfParameters() {
		return getOrigin().getParameterTypes().length;
	}

	@Override
	protected Object internalInvoke(Object[] arguments) throws Exception {
		return getOrigin().invoke(instance, arguments);
	}

	@Override
	public int getPriority() {
		return PRIORITY;
	}

	@Override
	public Set<IType> computeType(IReadOnlyQueryEnvironment queryEnvironment) {
		final Set<IType> res = new LinkedHashSet<IType>();

		Type returnType = getOrigin().getGenericReturnType();
		res.addAll(getIType(queryEnvironment, returnType));

		return res;
	}

	/**
	 * Gets {@link IType} from a {@link Type}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param type
	 *            the {@link Type}
	 * @return {@link IType} from a {@link Type}
	 * @see ValidationServices#getIType(Class)
	 */
	public Set<IType> getIType(IReadOnlyQueryEnvironment queryEnvironment, Type type) {
		final Set<IType> result = new LinkedHashSet<IType>();

		if (type instanceof ParameterizedType) {
			final Class<?> cls = (Class<?>)((ParameterizedType)type).getRawType();
			if (List.class.isAssignableFrom(cls)) {
				for (IType t : getIType(queryEnvironment, ((ParameterizedType)type)
						.getActualTypeArguments()[0])) {
					result.add(new SequenceType(queryEnvironment, t));
				}
			} else if (Set.class.isAssignableFrom(cls)) {
				for (IType t : getIType(queryEnvironment, ((ParameterizedType)type)
						.getActualTypeArguments()[0])) {
					result.add(new SetType(queryEnvironment, t));
				}
			} else {
				result.add(getClassJavaType(queryEnvironment, cls));
			}
		} else if (type instanceof Class<?>) {
			final Class<?> cls = (Class<?>)type;
			result.addAll(getIType(queryEnvironment, cls));
		} else {
			result.add(new ClassType(queryEnvironment, Object.class));
		}

		return result;
	}

	/**
	 * Gets {@link IType} from a {@link Class}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param cls
	 *            the {@link Class}
	 * @return {@link IType} from a {@link Class}
	 * @see ValidationServices#getIType(Type)
	 */
	private Set<IType> getIType(IReadOnlyQueryEnvironment queryEnvironment, Class<?> cls) {
		final Set<IType> result = new LinkedHashSet<IType>();

		if (List.class.isAssignableFrom(cls)) {
			result.add(new SequenceType(queryEnvironment, new ClassType(queryEnvironment, Object.class)));
		} else if (Set.class.isAssignableFrom(cls)) {
			result.add(new SetType(queryEnvironment, new ClassType(queryEnvironment, Object.class)));
		} else {
			result.add(getClassJavaType(queryEnvironment, cls));
		}

		return result;
	}

	@Override
	public boolean matches(IReadOnlyQueryEnvironment queryEnvironment, IType[] argumentTypes) {
		final ClassType[] classTypes = new ClassType[argumentTypes.length];

		for (int i = 0; i < argumentTypes.length; ++i) {
			Class<?> cls;
			final IType iType = argumentTypes[i];
			if (iType instanceof EClassifierLiteralType) {
				cls = EClass.class;
			} else if (iType instanceof EClassifierType) {
				cls = queryEnvironment.getEPackageProvider().getClass(((EClassifierType)iType).getType());
				if (cls == null) {
					if (iType.getType() instanceof EClass) {
						// instances of EClass are EObjects
						cls = EObject.class;
					} else {
						// other instances can be anything
						cls = Object.class;
					}
				}
			} else if (iType instanceof ClassLiteralType) {
				cls = Class.class;
			} else if (iType instanceof IJavaType) {
				cls = ((IJavaType)iType).getType();
			} else {
				throw new AcceleoQueryValidationException(iType.getClass().getCanonicalName());
			}

			if (cls != null) {
				if ("boolean".equals(cls.getName())) {
					cls = Boolean.class;
				} else if ("int".equals(cls.getName())) {
					cls = Integer.class;
				} else if ("double".equals(cls.getName())) {
					cls = Double.class;
				}
			}

			classTypes[i] = new ClassType(queryEnvironment, cls);
		}

		return super.matches(queryEnvironment, classTypes);
	}

	/**
	 * Gets the {@link Object} instance.
	 * 
	 * @return the {@link Object} instance if any, <code>null</code> otherwise
	 */
	public Object getInstance() {
		return instance;
	}

	@Override
	public String getShortSignature() {
		return serviceShortSignature(getOrigin().getParameterTypes());
	}

	@Override
	public String getLongSignature() {
		return getOrigin().toString();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof JavaMethodService && ((JavaMethodService)obj).getOrigin().equals(getOrigin());
	}

	@Override
	public int hashCode() {
		return getOrigin().hashCode();
	}

	@Override
	public List<ICompletionProposal> getProposals(IReadOnlyQueryEnvironment queryEnvironment,
			Set<IType> receiverTypes) {
		final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

		result.add(new JavaMethodServiceCompletionProposal(this));

		return result;
	}

	/**
	 * Creates a collection corresponding to this java method's {@link Method#getReturnType() return type}
	 * (either Sequence or Set) with the given type as collection type.
	 * 
	 * @param queryEnvironment
	 *            The query environment.
	 * @param collectionType
	 *            The type of the content for the new collection type.
	 * @return The created collection, <code>collectionType</code> itself if the java method's
	 *         {@link Method#getReturnType() return type} was not a collection.
	 */
	protected IType createReturnCollectionWithType(IReadOnlyQueryEnvironment queryEnvironment,
			IType collectionType) {
		IType result = collectionType;
		if (List.class.isAssignableFrom(getOrigin().getReturnType())) {
			result = new SequenceType(queryEnvironment, collectionType);
		} else if (Set.class.isAssignableFrom(getOrigin().getReturnType())) {
			result = new SetType(queryEnvironment, collectionType);
		}
		return result;
	}
}
