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
package org.eclipse.acceleo.query.services.tests;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.AbstractLanguageServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.LambdaType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Abstract class with validation test utilities.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractServicesValidationTest extends AbstractServicesTest {

	protected void assertNoService(String serviceName, IType parameterTypes[]) {
		final IService service = serviceLookUp(serviceName, parameterTypes);

		assertNull(service);
	}

	protected void assertValidation(IType expectedReturnTypes[], String serviceName, IType parameterTypes[]) {
		assertValidation(expectedReturnTypes, expectedReturnTypes, serviceName, parameterTypes);
	}

	protected void assertValidation(IType expectedReturnTypes[], IType expectedAllReturnTypes[],
			String serviceName, IType parameterTypes[]) {
		final IService service = serviceLookUp(serviceName, parameterTypes);

		assertNotNull("Service not found.", service != null);

		Set<IType> types = service.getType(getValidationServices(), getQueryEnvironment(), Arrays
				.asList(parameterTypes));
		assertEquals(expectedReturnTypes.length, types.size());
		int i = 0;
		for (IType type : types) {
			assertEqualsITypes(expectedReturnTypes[i++], type);
		}

		final Map<List<IType>, Set<IType>> allTypes = new LinkedHashMap<List<IType>, Set<IType>>();
		allTypes.put(Arrays.asList(parameterTypes), types);

		types = service.validateAllType(getValidationServices(), getQueryEnvironment(), allTypes);
		assertEquals(expectedAllReturnTypes.length, types.size());
		i = 0;
		for (IType type : types) {
			assertEqualsITypes(expectedAllReturnTypes[i++], type);
		}
	}

	protected void assertEqualsITypes(IType expected, IType actual) {
		if (expected instanceof SequenceType && actual instanceof SequenceType) {
			assertEqualsITypes(((SequenceType)expected).getCollectionType(), ((SequenceType)actual)
					.getCollectionType());
		} else if (expected instanceof SetType && actual instanceof SetType) {
			assertEqualsITypes(((SetType)expected).getCollectionType(), ((SetType)actual).getCollectionType());
		} else if (expected instanceof NothingType && actual instanceof NothingType) {
			assertEquals(((NothingType)expected).getMessage(), ((NothingType)actual).getMessage());
		} else {
			assertEquals(expected, actual);
		}
	}

	protected IService serviceLookUp(String serviceName, IType[] parameterTypes) {
		final Class<?>[] argumentClasses = new Class<?>[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; ++i) {
			argumentClasses[i] = getClass(parameterTypes[i]);
		}
		return getLookupEngine().lookup(serviceName, argumentClasses);
	}

	protected ClassType classType(Class<?> cls) {
		return new ClassType(getQueryEnvironment(), cls);
	}

	protected EClassifierType eClassifierType(EClassifier eClassifier) {
		return new EClassifierType(getQueryEnvironment(), eClassifier);
	}

	protected EClassifierType eClassifierLiteralType(EClassifier eClassifier) {
		return new EClassifierLiteralType(getQueryEnvironment(), eClassifier);
	}

	protected SequenceType sequenceType(IType type) {
		return new SequenceType(getQueryEnvironment(), type);
	}

	protected SetType setType(IType type) {
		return new SetType(getQueryEnvironment(), type);
	}

	protected NothingType nothingType(String message) {
		return new NothingType(message);
	}

	protected LambdaType lambdaType(IType lambdaEvaluatorType, IType lambdaExpressionType) {
		return new LambdaType(getQueryEnvironment(), lambdaEvaluatorType, lambdaExpressionType);
	}

	/**
	 * This method should be used only for testing purpose. See {@link AbstractLanguageServices} for the
	 * proper implementation.
	 * 
	 * @param type
	 *            the {@link IType}
	 * @return the {@link Class} corresponding to the given {@link IType} without in boxing
	 */
	private Class<?> getClass(IType type) {
		final Class<?> result;

		final Object t = type.getType();
		if (t instanceof Class<?>) {
			result = (Class<?>)t;
		} else if (type instanceof EClassifierLiteralType) {
			result = EClass.class;
		} else if (t instanceof EClassifier) {
			result = getQueryEnvironment().getEPackageProvider().getClass((EClassifier)t);
		} else {
			throw new IllegalStateException("what is the class of " + t);
		}

		return result;
	}
}