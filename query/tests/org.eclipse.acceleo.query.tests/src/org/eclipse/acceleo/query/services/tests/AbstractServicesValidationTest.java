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
package org.eclipse.acceleo.query.services.tests;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IQueryValidationEngine;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.AbstractLanguageServices;
import org.eclipse.acceleo.query.runtime.impl.QueryValidationEngine;
import org.eclipse.acceleo.query.validation.type.ClassLiteralType;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierSetLiteralType;
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

	protected static class VariableBuilder {
		private Map<String, Set<IType>> variables;

		public VariableBuilder() {
			variables = new LinkedHashMap<String, Set<IType>>();
		}

		public VariableBuilder addVar(String name, IType... types) {
			variables.put(name, new LinkedHashSet<>(Arrays.asList(types)));
			return this;
		}

		public Map<String, Set<IType>> build() {
			return variables;
		}
	}

	protected void assertNoService(String serviceName, IType parameterTypes[]) {
		final IService<?> service = serviceLookUp(serviceName, parameterTypes);

		assertNull(service);
	}

	protected void assertValidation(IType expectedReturnTypes[], String serviceName, IType parameterTypes[]) {
		assertValidation(expectedReturnTypes, expectedReturnTypes, serviceName, parameterTypes);
	}

	protected void assertValidation(IType expectedReturnTypes[], IType expectedAllReturnTypes[],
			String serviceName, IType parameterTypes[]) {
		final IService<?> service = serviceLookUp(serviceName, parameterTypes);

		assertValidation(service, expectedReturnTypes, expectedAllReturnTypes, parameterTypes);
	}

	protected void assertValidation(IService<?> service, IType expectedReturnTypes[],
			IType expectedAllReturnTypes[], IType parameterTypes[]) {
		assertNotNull("Service not found.", service);

		Set<IType> types = service.getType(null, getValidationServices(), null, getQueryEnvironment(), Arrays
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
			assertEqualsITypes(((SetType)expected).getCollectionType(), ((SetType)actual)
					.getCollectionType());
		} else if (expected instanceof NothingType && actual instanceof NothingType) {
			assertEquals(((NothingType)expected).getMessage(), ((NothingType)actual).getMessage());
		} else {
			assertEquals(expected, actual);
		}
	}

	protected IService<?> serviceLookUp(String serviceName, IType[] parameterTypes) {
		final IType[] argumentClasses = new IType[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; ++i) {
			argumentClasses[i] = new ClassType(getQueryEnvironment(), getClass(parameterTypes[i]));
		}
		return getLookupEngine().lookup(serviceName, argumentClasses);
	}

	protected ClassType classType(Class<?> cls) {
		return new ClassType(getQueryEnvironment(), cls);
	}

	protected ClassType classLiteralType(Class<?> cls) {
		return new ClassLiteralType(getQueryEnvironment(), cls);
	}

	protected EClassifierType eClassifierType(EClassifier eClassifier) {
		return new EClassifierType(getQueryEnvironment(), eClassifier);
	}

	protected EClassifierType eClassifierLiteralType(EClassifier eClassifier) {
		return new EClassifierLiteralType(getQueryEnvironment(), eClassifier);
	}

	protected EClassifierSetLiteralType eClassifierSetLiteralType(EClassifier... eClassifiers) {
		final Set<EClassifier> eClss = new LinkedHashSet<EClassifier>(eClassifiers.length);
		for (EClassifier eCls : eClassifiers) {
			eClss.add(eCls);
		}
		return new EClassifierSetLiteralType(getQueryEnvironment(), eClss);
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

	protected LambdaType lambdaType(String evaluatorName, IType lambdaEvaluatorType,
			IType lambdaExpressionType) {
		return new LambdaType(getQueryEnvironment(), evaluatorName, lambdaEvaluatorType,
				lambdaExpressionType);
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
		if (type instanceof ClassLiteralType) {
			result = Class.class;
		} else if (t instanceof Class<?>) {
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

	protected IValidationResult validate(String expression, Map<String, Set<IType>> vars) {
		final Map<String, Set<IType>> variableTypes = new LinkedHashMap<String, Set<IType>>();
		if (vars != null) {
			variableTypes.putAll(vars);
		}

		final IQueryValidationEngine builder = new QueryValidationEngine(getQueryEnvironment());
		return builder.validate(expression, variableTypes);
	}

	protected IValidationResult validate(String expression) {
		return validate(expression, null);
	}

}
