/*******************************************************************************
 * Copyright (c) 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl;

import com.google.common.collect.Sets;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.runtime.AcceleoQueryValidationException;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.completion.EOperationServiceCompletionProposal;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IJavaType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EcorePackage;

/**
 * Abstract implementation of an {@link org.eclipse.acceleo.query.runtime.IService IService} for
 * {@link EOperation}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 * @since 4.1
 */
public class EOperationService extends AbstractService {

	/**
	 * The {@link org.eclipse.acceleo.query.runtime.IService#getPriority() priority} for
	 * {@link EOperationService}.
	 */
	public static final int PRIORITY = 100;

	/**
	 * Log message used when a called EOperation can't be invoked.
	 */
	protected static final String COULDN_T_INVOKE_EOPERATION = "Couldn't invoke the %s EOperation (%s)";

	/**
	 * The {@link EOperation} that realizes the service.
	 */
	private final EOperation eOperation;

	/**
	 * The Java method which actually implements the EOperation.
	 */
	private final Method method;

	/**
	 * Creates a new service instance given a method and an instance.
	 * 
	 * @param eOperation
	 *            the {@link EOperation} that realizes the service
	 */
	public EOperationService(EOperation eOperation) {
		this.eOperation = eOperation;
		this.method = lookupMethod(this.eOperation);
	}

	/**
	 * Finds the Java {@link Method} which implements a given {@link EOperation}.
	 * 
	 * @param operation
	 *            the {@link EOperation} to look for.
	 * @return the Java method which implements the {@link EOperation}, or <code>null</code> if none could be
	 *         found.
	 */
	private Method lookupMethod(EOperation operation) {
		Method result;

		final Class<?> containerClass = operation.getEContainingClass().getInstanceClass();
		if (containerClass != null) {
			final Class<?>[] argumentClasses = new Class<?>[operation.getEParameters().size()];
			for (int i = 0; i < argumentClasses.length; i++) {
				EParameter param = operation.getEParameters().get(i);
				if (param.isMany()) {
					argumentClasses[i] = EList.class;
				} else {
					argumentClasses[i] = param.getEType().getInstanceClass();
				}
			}
			try {
				result = containerClass.getMethod(operation.getName(), argumentClasses);
			} catch (SecurityException e) {
				result = null;
			} catch (NoSuchMethodException e) {
				result = null;
			}
		} else {
			result = null;
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getName()
	 */
	@Override
	public String getName() {
		return eOperation.getName();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getParameterTypes(org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment)
	 */
	@Override
	public List<IType> getParameterTypes(IReadOnlyQueryEnvironment queryEnvironment) {
		final List<IType> result = new ArrayList<IType>();

		result.add(new EClassifierType(queryEnvironment, getEOperation().getEContainingClass()));
		for (EParameter parameter : getEOperation().getEParameters()) {
			final EClassifierType rawType = new EClassifierType(queryEnvironment, parameter.getEType());
			if (parameter.isMany()) {
				result.add(new SequenceType(queryEnvironment, rawType));
			} else {
				result.add(rawType);
			}
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
		return eOperation.getEParameters().size() + 1;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.AbstractService#internalInvoke(java.lang.Object[])
	 */
	@Override
	protected Object internalInvoke(Object[] arguments) throws Exception {
		final Object result;
		final EObject receiver = (EObject)arguments[0];
		final Object[] localArguments = new Object[arguments.length];
		for (int i = 1; i < arguments.length; ++i) {
			if (eOperation.getEParameters().get(i - 1).isMany()) {
				localArguments[i] = new BasicEList<Object>((Collection<?>)arguments[i]);
			} else {
				localArguments[i] = arguments[i];
			}
		}

		if (!eOperation.getEContainingClass().isSuperTypeOf(receiver.eClass())) {
			if (method != null) {
				final Object[] parameters = Arrays.copyOfRange(localArguments, 1, localArguments.length);
				result = eOperationJavaInvoke(method, receiver, parameters);
			} else {
				throw new IllegalStateException(String.format(
						"EOperation %s not in %s type hierarchy of %s and no %s method in %s", getName(),
						getEOperation().getEContainingClass().getName(), receiver.eClass().getName(),
						getName(), receiver.getClass().getName()));
			}
		} else if (hasEInvoke(receiver)) {
			final EList<Object> eArguments = new BasicEList<Object>(localArguments.length);
			for (int i = 1; i < localArguments.length; ++i) {
				eArguments.add(localArguments[i]);
			}
			result = receiver.eInvoke(eOperation, eArguments);
		} else if (method != null) {
			final Object[] parameters = Arrays.copyOfRange(localArguments, 1, localArguments.length);
			result = eOperationJavaInvoke(method, receiver, parameters);
		} else {
			throw new IllegalStateException(String.format("No eInvoke nor %s methods in %s", getName(),
					receiver.getClass().getName()));
		}

		return result;
	}

	/**
	 * Try to find out if the Operation reflection is enable for the given {@link Object}.
	 * 
	 * @param object
	 *            the {@link Object} to test.
	 * @return <code>true</code> if the Operation reflection is enable for the given {@link Object},
	 *         <code>false</code> otherwise
	 */
	private boolean hasEInvoke(Object object) {
		Method eInvokeMethod = null;

		try {
			eInvokeMethod = object.getClass().getDeclaredMethod("eInvoke", int.class, EList.class);
		} catch (NoSuchMethodException e) {
			// nothing to do here
		} catch (SecurityException e) {
			// nothing to do here
		}

		return eInvokeMethod != null;
	}

	/**
	 * Call the {@link EOperation} thru a Java invoke.
	 * 
	 * @param eInvokeMethod
	 *            the {@link Method}
	 * @param receiver
	 *            the receiver
	 * @param arguments
	 *            arguments
	 * @return the {@link EOperation} result if any, {@link Nothing} otherwise
	 * @throws Exception
	 *             if the invoked {@link EOperation} fail
	 */
	private Object eOperationJavaInvoke(Method eInvokeMethod, final Object receiver, final Object[] arguments)
			throws Exception {
		if (eInvokeMethod != null && receiver != null) {
			return eInvokeMethod.invoke(receiver, arguments);
		} else {
			throw new IllegalArgumentException();
		}
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

		final IType eClassifierType = new EClassifierType(queryEnvironment, eOperation.getEType());
		if (eOperation.isMany()) {
			result.add(new SequenceType(queryEnvironment, eClassifierType));
		} else {
			result.add(eClassifierType);
		}

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
		final List<Set<IType>> eClassifierTypes = new ArrayList<Set<IType>>(argumentTypes.length);

		boolean canMatch = true;
		for (int i = 0; i < argumentTypes.length; ++i) {
			Set<EClassifier> eClassifiers;
			final IType iType = argumentTypes[i];
			if (iType instanceof EClassifierLiteralType) {
				eClassifiers = new LinkedHashSet<EClassifier>();
				eClassifiers.add(EcorePackage.eINSTANCE.getEClass());
			} else if (iType instanceof EClassifierType) {
				eClassifiers = new LinkedHashSet<EClassifier>();
				eClassifiers.add(((EClassifierType)iType).getType());
			} else if (iType instanceof SequenceType) {
				eClassifiers = new LinkedHashSet<EClassifier>();
				eClassifiers.add(EcorePackage.eINSTANCE.getEEList());
			} else if (iType instanceof IJavaType) {
				if (iType.getType() == null) {
					eClassifiers = new LinkedHashSet<EClassifier>();
					eClassifiers.add(null);
				} else if (List.class.isAssignableFrom(((IJavaType)iType).getType())) {
					eClassifiers = new LinkedHashSet<EClassifier>();
					eClassifiers.add(EcorePackage.eINSTANCE.getEEList());
				} else {
					eClassifiers = queryEnvironment.getEPackageProvider().getEClassifiers(
							((IJavaType)iType).getType());
					if (eClassifiers == null) {
						canMatch = false;
						break;
					}
				}
			} else {
				throw new AcceleoQueryValidationException(iType.getClass().getCanonicalName());
			}
			final Set<IType> types = new LinkedHashSet<IType>();
			for (EClassifier eClassifier : eClassifiers) {
				if (eClassifier != null) {
					types.add(new EClassifierType(queryEnvironment, eClassifier));
				} else {
					types.add(new ClassType(queryEnvironment, null));
				}
			}
			eClassifierTypes.add(types);
		}

		if (canMatch) {
			final Set<List<IType>> product = Sets.cartesianProduct(eClassifierTypes);
			boolean matched = false;
			for (List<IType> parameterTypes : product) {
				if (super.matches(queryEnvironment, parameterTypes.toArray(new IType[parameterTypes.size()]))) {
					matched = true;
					break;
				}
			}
			canMatch = matched;
		}

		return canMatch;
	}

	/**
	 * Gets the {@link EOperation}.
	 * 
	 * @return the {@link EOperation}
	 */
	public EOperation getEOperation() {
		return eOperation;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getShortSignature()
	 */
	@Override
	public String getShortSignature() {
		final List<IType> parameterTypes = getParameterTypes(null);
		final IType[] argumentTypes = parameterTypes.toArray(new IType[parameterTypes.size()]);

		return serviceShortSignature(argumentTypes);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IService#getLongSignature()
	 */
	@Override
	public String getLongSignature() {
		final String ePkgNsURI;
		final String eCLassName;

		final EClass eContainingClass = getEOperation().getEContainingClass();
		if (eContainingClass != null) {
			eCLassName = eContainingClass.getName();
			final EPackage ePackage = eContainingClass.getEPackage();
			if (ePackage != null) {
				ePkgNsURI = ePackage.getNsURI();
			} else {
				ePkgNsURI = null;
			}
		} else {
			ePkgNsURI = null;
			eCLassName = null;
		}

		return ePkgNsURI + " " + eCLassName + " " + getShortSignature();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof EOperationService
				&& ((EOperationService)obj).getEOperation().equals(getEOperation());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getEOperation().hashCode();
	}

	@Override
	public List<ICompletionProposal> getProposals(IReadOnlyQueryEnvironment queryEnvironment,
			Set<IType> receiverTypes) {
		final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

		result.add(new EOperationServiceCompletionProposal(getEOperation()));

		return result;
	}

}
