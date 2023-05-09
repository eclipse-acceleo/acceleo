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

import java.util.Set;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.services.StringServices;
import org.eclipse.acceleo.query.validation.type.IType;
import org.junit.Test;

public class StringServicesValidationTest extends AbstractServicesValidationTest {

	@Override
	public void before() throws Exception {
		super.before();
		final Set<IService<?>> services = ServiceUtils.getServices(getQueryEnvironment(),
				StringServices.class);
		ServiceUtils.registerServices(getQueryEnvironment(), services);
	}

	@Test
	public void testConcat() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "concat", parameterTypes);
	}

	@Test
	public void testAdd() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "add", parameterTypes);
	}

	@Test
	public void testReplace() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(String.class),
				classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "replace", parameterTypes);
	}

	@Test
	public void testReplaceAll() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(String.class),
				classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "replaceAll", parameterTypes);
	}

	@Test
	public void testPrefix() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "prefix", parameterTypes);
	}

	@Test
	public void testContains() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "contains", parameterTypes);
	}

	@Test
	public void testMatches() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "matches", parameterTypes);
	}

	@Test
	public void testEndsWith() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "endsWith", parameterTypes);
	}

	@Test
	public void testStartsWith() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "startsWith", parameterTypes);
	}

	@Test
	public void testEqualsIgnoreCase() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "equalsIgnoreCase", parameterTypes);
	}

	@Test
	public void testFirst() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "first", parameterTypes);
	}

	@Test
	public void testLast() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "last", parameterTypes);
	}

	@Test
	public void testLastIndex2() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "lastIndex", parameterTypes);
	}

	@Test
	public void testIndex2() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "index", parameterTypes);
	}

	@Test
	public void testIndex3() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(String.class),
				classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "index", parameterTypes);
	}

	@Test
	public void testLastIndex3() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(String.class),
				classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "lastIndex", parameterTypes);
	}

	@Test
	public void testToLower() {
		final IType[] parameterTypes = new IType[] {classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "toLower", parameterTypes);
	}

	@Test
	public void testToLowerFirst() {
		final IType[] parameterTypes = new IType[] {classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "toLowerFirst", parameterTypes);
	}

	@Test
	public void testToUpper() {
		final IType[] parameterTypes = new IType[] {classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "toUpper", parameterTypes);
	}

	@Test
	public void testToUpperFirst() {
		final IType[] parameterTypes = new IType[] {classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "toUpperFirst", parameterTypes);
	}

	@Test
	public void testIsAlpha() {
		final IType[] parameterTypes = new IType[] {classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "isAlpha", parameterTypes);
	}

	@Test
	public void testIsAlphaNum() {
		final IType[] parameterTypes = new IType[] {classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Boolean.class) };

		assertValidation(expectedReturnTypes, "isAlphaNum", parameterTypes);
	}

	@Test
	public void testSize() {
		final IType[] parameterTypes = new IType[] {classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "size", parameterTypes);
	}

	@Test
	public void testSubstring() {
		final IType[] parameterTypes = new IType[] {classType(String.class), classType(Integer.class),
				classType(Integer.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(String.class) };

		assertValidation(expectedReturnTypes, "substring", parameterTypes);
	}

	@Test
	public void testToInteger() {
		final IType[] parameterTypes = new IType[] {classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Integer.class) };

		assertValidation(expectedReturnTypes, "toInteger", parameterTypes);
	}

	@Test
	public void testToReal() {
		final IType[] parameterTypes = new IType[] {classType(String.class) };
		final IType[] expectedReturnTypes = new IType[] {classType(Double.class) };

		assertValidation(expectedReturnTypes, "toReal", parameterTypes);
	}

}
