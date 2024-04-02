/*******************************************************************************
 * Copyright (c) 2016, 2024 Obeo.
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.parser.CombineIterator;
import org.eclipse.acceleo.query.runtime.AcceleoQueryValidationException;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
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
public class EOperationService extends AbstractService<EOperation> {

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
		super(eOperation);
		this.method = lookupMethod(eOperation);
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

	@Override
	public String getName() {
		return getOrigin().getName();
	}

	@Override
	public List<Set<IType>> computeParameterTypes(IReadOnlyQueryEnvironment queryEnvironment) {
		final List<Set<IType>> result = new ArrayList<>();

		result.add(Collections.singleton(new EClassifierType(queryEnvironment, getOrigin()
				.getEContainingClass())));
		for (EParameter parameter : getOrigin().getEParameters()) {
			final EClassifierType rawType = new EClassifierType(queryEnvironment, parameter.getEType());
			if (parameter.isMany()) {
				result.add(Collections.singleton(new SequenceType(queryEnvironment, rawType)));
			} else {
				result.add(Collections.singleton(rawType));
			}
		}

		return result;
	}

	@Override
	public int getNumberOfParameters() {
		return getOrigin().getEParameters().size() + 1;
	}

	@Override
	protected Object internalInvoke(Object[] arguments) throws Exception {
		final Object result;
		final EObject receiver = (EObject)arguments[0];
		final Object[] localArguments = new Object[arguments.length];
		for (int i = 1; i < arguments.length; ++i) {
			if (getOrigin().getEParameters().get(i - 1).isMany()) {
				localArguments[i] = new BasicEList<Object>((Collection<?>)arguments[i]);
			} else {
				localArguments[i] = arguments[i];
			}
		}

		if (!getOrigin().getEContainingClass().isSuperTypeOf(receiver.eClass())) {
			if (method != null) {
				final Object[] parameters = Arrays.copyOfRange(localArguments, 1, localArguments.length);
				result = eOperationJavaInvoke(method, receiver, parameters);
			} else {
				throw new IllegalStateException(String.format(
						"EOperation %s not in %s type hierarchy of %s and no %s method in %s", getName(),
						getOrigin().getEContainingClass().getName(), receiver.eClass().getName(), getName(),
						receiver.getClass().getName()));
			}
		} else if (hasEInvoke(receiver)) {
			final EList<Object> eArguments = new BasicEList<Object>(localArguments.length);
			for (int i = 1; i < localArguments.length; ++i) {
				eArguments.add(localArguments[i]);
			}
			result = receiver.eInvoke(getOrigin(), eArguments);
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

	@Override
	public int getPriority() {
		return PRIORITY;
	}

	@Override
	public Set<IType> computeType(IReadOnlyQueryEnvironment queryEnvironment) {
		final Set<IType> result = new LinkedHashSet<IType>();

		final IType eClassifierType = new EClassifierType(queryEnvironment, getOrigin().getEType());
		if (getOrigin().isMany()) {
			result.add(new SequenceType(queryEnvironment, eClassifierType));
		} else {
			result.add(eClassifierType);
		}

		return result;
	}

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
					eClassifiers = queryEnvironment.getEPackageProvider().getEClassifiers(((IJavaType)iType)
							.getType());
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
			CombineIterator<IType> it = new CombineIterator<IType>(eClassifierTypes);
			boolean matched = false;
			while (it.hasNext()) {
				final List<IType> parameterTypes = it.next();
				if (super.matches(queryEnvironment, parameterTypes.toArray(new IType[parameterTypes
						.size()]))) {
					matched = true;
					break;
				}
			}
			canMatch = matched;
		}

		return canMatch;
	}

	@Override
	public String getShortSignature() {
		final List<Set<IType>> parameterTypes = getParameterTypes(null);
		final Object[] argumentTypes = parameterTypes.toArray(new Object[parameterTypes.size()]);

		return serviceShortSignature(argumentTypes);
	}

	@Override
	public String getLongSignature() {
		final String ePkgNsURI;
		final String eCLassName;

		final EClass eContainingClass = getOrigin().getEContainingClass();
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

	@Override
	public boolean equals(Object obj) {
		return obj instanceof EOperationService && ((EOperationService)obj).getOrigin().equals(getOrigin());
	}

	@Override
	public int hashCode() {
		return getOrigin().hashCode();
	}

	@Override
	public List<ICompletionProposal> getProposals(IReadOnlyQueryEnvironment queryEnvironment,
			Set<IType> receiverTypes) {
		final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

		result.add(new EOperationServiceCompletionProposal(getOrigin()));

		return result;
	}

}
