/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template.utils;

import java.lang.reflect.Method;

/**
 * Provides utility methods to create and use Java services.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public final class JavaServicesUtils {

	/**
	 * Private constructor.
	 */
	private JavaServicesUtils() {
	}

	/**
	 * Create the query that is able to invoke the given Java method.
	 * 
	 * @param javaMethod
	 *            is the current method of the class
	 * @return the textual format of the query
	 */
	public static String createQuery(Method javaMethod) {
		final String argPrefix = "arg"; //$NON-NLS-1$
		final String parenthesisStart = "("; //$NON-NLS-1$

		StringBuilder buffer = new StringBuilder();
		Class<?>[] javaParameters = javaMethod.getParameterTypes();
		buffer.append("[query public "); //$NON-NLS-1$
		buffer.append(javaMethod.getName());
		buffer.append(parenthesisStart);
		for (int i = 0; i < javaParameters.length; i++) {
			if (i > 0) {
				buffer.append(',');
				buffer.append(' ');
			}
			buffer.append(argPrefix);
			buffer.append(i);
			buffer.append(" : "); //$NON-NLS-1$
			buffer.append(javaClass2OclType(javaParameters[i]));
		}
		buffer.append(") : "); //$NON-NLS-1$
		buffer.append(javaClass2OclType(javaMethod.getReturnType()));
		buffer.append("\n\t= invoke('"); //$NON-NLS-1$
		buffer.append(javaMethod.getDeclaringClass().getName());
		buffer.append("', '"); //$NON-NLS-1$
		buffer.append(javaMethod.getName());
		buffer.append(parenthesisStart);
		for (int i = 0; i < javaParameters.length; i++) {
			if (i > 0) {
				buffer.append(',');
				buffer.append(' ');
			}
			buffer.append(javaParameters[i].getName());
		}
		buffer.append(")', Sequence{"); //$NON-NLS-1$
		for (int i = 0; i < javaParameters.length; i++) {
			if (i > 0) {
				buffer.append(", "); //$NON-NLS-1$
			}
			buffer.append(argPrefix);
			buffer.append(i);
		}
		buffer.append("}) /]\n\n"); //$NON-NLS-1$
		return buffer.toString();
	}

	/**
	 * Create the query that is able to invoke the given Java method.
	 * 
	 * @param javaMethod
	 *            is the current method of the class
	 * @param withDocumentation
	 *            Indicates if we should generate some documentation too
	 * @return the textual format of the query
	 */
	public static String createQuery(Method javaMethod, boolean withDocumentation) {
		final String argPrefix = "arg"; //$NON-NLS-1$
		final String parenthesisStart = "("; //$NON-NLS-1$

		StringBuilder buffer = new StringBuilder();
		Class<?>[] javaParameters = javaMethod.getParameterTypes();
		if (withDocumentation) {
			buffer.append("[**\n * The documentation of the query\n"); //$NON-NLS-1$
			for (int i = 0; i < javaParameters.length; i++) {
				buffer.append(" * @param arg"); //$NON-NLS-1$
				buffer.append(i);
				buffer.append("\n"); //$NON-NLS-1$
			}
			buffer.append(" */]\n"); //$NON-NLS-1$
		}

		buffer.append("[query public "); //$NON-NLS-1$
		buffer.append(javaMethod.getName());
		buffer.append(parenthesisStart);
		for (int i = 0; i < javaParameters.length; i++) {
			if (i > 0) {
				buffer.append(',');
				buffer.append(' ');
			}
			buffer.append(argPrefix);
			buffer.append(i);
			buffer.append(" : "); //$NON-NLS-1$
			buffer.append(javaClass2OclType(javaParameters[i]));
		}
		buffer.append(") : "); //$NON-NLS-1$
		buffer.append(javaClass2OclType(javaMethod.getReturnType()));
		buffer.append("\n\t= invoke('"); //$NON-NLS-1$
		buffer.append(javaMethod.getDeclaringClass().getName());
		buffer.append("', '"); //$NON-NLS-1$
		buffer.append(javaMethod.getName());
		buffer.append(parenthesisStart);
		for (int i = 0; i < javaParameters.length; i++) {
			if (i > 0) {
				buffer.append(',');
				buffer.append(' ');
			}
			buffer.append(javaParameters[i].getName());
		}
		buffer.append(")', Sequence{"); //$NON-NLS-1$
		for (int i = 0; i < javaParameters.length; i++) {
			if (i > 0) {
				buffer.append(", "); //$NON-NLS-1$
			}
			buffer.append(argPrefix);
			buffer.append(i);
		}
		buffer.append("}) /]\n\n"); //$NON-NLS-1$
		return buffer.toString();
	}

	/**
	 * Gets the OCL type name that corresponds to the java class. By default, it means the last segment of the
	 * fully qualified name.
	 * 
	 * @param javaClass
	 *            is the java class
	 * @return the OCL type name
	 */
	private static String javaClass2OclType(Class<?> javaClass) {
		String result;
		if (javaClass != null && javaClass.getName() != null) {
			String type = javaClass.getName();
			if ("java.math.BigDecimal".equals(type) || "java.lang.Double".equals(type) //$NON-NLS-1$ //$NON-NLS-2$
					|| "double".equals(type)) { //$NON-NLS-1$
				result = "Real"; //$NON-NLS-1$
			} else if ("java.math.BigInteger".equals(type) || "java.lang.Integer".equals(type) //$NON-NLS-1$ //$NON-NLS-2$
					|| "int".equals(type)) { //$NON-NLS-1$
				result = "Integer"; //$NON-NLS-1$
			} else if ("java.lang.Short".equals(type) || "short".equals(type)) { //$NON-NLS-1$ //$NON-NLS-2$
				result = "Integer"; //$NON-NLS-1$
			} else if ("java.lang.Boolean".equals(type) || "boolean".equals(type)) { //$NON-NLS-1$ //$NON-NLS-2$
				result = "Boolean"; //$NON-NLS-1$
			} else if ("java.lang.String".equals(type)) { //$NON-NLS-1$
				result = "String"; //$NON-NLS-1$
			} else if ("java.util.List".equals(type) || "java.util.ArrayList".equals(type) //$NON-NLS-1$ //$NON-NLS-2$
					|| "java.util.LinkedList".equals(type)) { //$NON-NLS-1$
				result = "Sequence(OclAny)"; //$NON-NLS-1$
			} else if ("java.util.Set".equals(type) || "java.util.HashSet".equals(type)) { //$NON-NLS-1$ //$NON-NLS-2$
				result = "Set(OclAny)"; //$NON-NLS-1$
			} else if ("java.util.LinkedHashSet".equals(type)) { //$NON-NLS-1$
				result = "OrderedSet(OclAny)"; //$NON-NLS-1$
			} else if ("java.util.Collection".equals(type)) { //$NON-NLS-1$
				result = "Collection(OclAny)"; //$NON-NLS-1$
			} else if ("java.lang.Object".equals(type)) { //$NON-NLS-1$
				result = "OclAny"; //$NON-NLS-1$
			} else if ("void".equals(type)) { //$NON-NLS-1$
				result = "OclVoid"; //$NON-NLS-1$
			} else {
				result = type;
				int lastDot = result.lastIndexOf('.');
				if (lastDot > -1) {
					result = result.substring(lastDot + 1);
				}
				if (result.endsWith(";")) { //$NON-NLS-1$
					result = result.substring(0, result.length() - 1);
				}
			}
		} else {
			result = "OclVoid"; //$NON-NLS-1$
		}
		return result;
	}

}
