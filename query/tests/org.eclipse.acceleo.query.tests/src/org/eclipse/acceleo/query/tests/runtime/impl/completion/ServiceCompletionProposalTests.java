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
package org.eclipse.acceleo.query.tests.runtime.impl.completion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;

import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.completion.ServiceCompletionProposal;
import org.eclipse.acceleo.query.runtime.lookup.basic.Service;
import org.junit.Test;

/**
 * Tests of the service completion proposal.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">St&eacute;phane B&eacute;gaudeau</a>
 */
public class ServiceCompletionProposalTests {

	private static final String LS = System.getProperty("line.separator");

	private void assertJavadocEquals(Class<?> clazz, String methodName, String expectedJavadoc) {
		Method serviceMethod = null;

		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				serviceMethod = method;
			}
		}

		if (serviceMethod != null) {
			try {
				IService service = new Service(serviceMethod, clazz.newInstance());
				ServiceCompletionProposal proposal = new ServiceCompletionProposal(service);
				String description = proposal.getDescription();
				assertEquals(expectedJavadoc, description);
			} catch (InstantiationException e) {
				e.printStackTrace();
				fail();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				fail();
			}
		} else {
			fail();
		}

	}

	@Test
	public void testConcatJavadoc() {
		// @formatter:off
		String javadoc = "concat(self: java.lang.String, b: java.lang.String) = String" + LS +
						 "" + LS +
						 "Returns a string that is the result of a concatenation of a string \"b\" at the end of a self string \"self\"." + LS +
						 "" + LS +
						 "  @param self" + LS +
						 "        The \"self\" string from which we concatenate the string \"b\"." + LS +
						 "  @param b" + LS +
						 "        The string that will be concatenated to the string \"self\"." + LS +
						 "" + LS +
						 "  @return" + LS +
						 "        The concatenated String." + LS +
						 "" + LS;
		// @formatter:on
		this.assertJavadocEquals(StringServicesClone.class, "concat", javadoc);
	}

	@Test
	public void testAddJavadoc() {
		// @formatter:off
		String javadoc = "add(java.lang.String, java.lang.String) = String" + LS +
						 "" + LS +
						 "Concatenates a string b at the end of a self string \"a\"." + LS +
						 "" + LS;
		// @formatter:on
		this.assertJavadocEquals(StringServicesClone.class, "add", javadoc);
	}

	@Test
	public void testReplaceJavadoc() {
		// @formatter:off
		String javadoc = "replace(java.lang.String, java.lang.String, java.lang.String) = String" + LS +
				         "";
		// @formatter:on
		this.assertJavadocEquals(StringServicesClone.class, "replace", javadoc);
	}

	@Test
	public void testReplaceAllJavadoc() {
		// @formatter:off
		String javadoc = "replaceAll(self: java.lang.String, java.lang.String, java.lang.String) = String" + LS +
						 "" + LS +
						 "Returns the resulting string of a substitution of all occurrence of substring" +
						 "<code>subStringRegex</code> in self by substring <code>replacementRegex</code>." +
						 "<code>subStringRegex</code> and <code>replacementRegex</code> are treated as regular expressions." + LS +
						 "" + LS +
						 "  @param self" + LS +
						 "        The \"self\" string from which we concatenate the string \"b\"." + LS +
						 "" + LS;
		// @formatter:on
		this.assertJavadocEquals(StringServicesClone.class, "replaceAll", javadoc);
	}

	@Test
	public void testPrefixJavadoc() {
		// @formatter:off
		String javadoc = "prefix(java.lang.String, java.lang.String) = String" + LS +
						 "" + LS +
						 "Returns true if a \"self\" string contains the string \"b\"." + LS +
						 "" + LS +
						 "  @return" + LS + 
						 "        true if the self string \"self\" contains the string \"b\". False otherwise. Throws" +
						 "NullPointerException if \"self\" or \"b\" is null." + LS +
						 "" + LS;
		// @formatter:on
		this.assertJavadocEquals(StringServicesClone.class, "prefix", javadoc);
	}

	@Test
	public void testContainsJavadoc() {
		// @formatter:off
		String javadoc = "contains(java.lang.String, java.lang.String) = Boolean" + LS +
						 "" + LS +
						 "Returns true if a \"self\" string contains the string \"b\"." + LS +
						 "" + LS +
						 "  @throw java.lang.NullPointerException" + LS +
						 "        If one of the parameter is null." + LS +
						 "  @throw java.lang.IllegalAccessException" + LS +
						 "        If things go wrong." + LS +
						 "" + LS;
		// @formatter:on
		this.assertJavadocEquals(StringServicesClone.class, "contains", javadoc);
	}
}
