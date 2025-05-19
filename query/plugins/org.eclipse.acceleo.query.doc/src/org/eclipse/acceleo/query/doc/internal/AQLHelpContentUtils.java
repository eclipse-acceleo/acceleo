/*******************************************************************************
 * Copyright (c) 2015, 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.doc.internal;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.acceleo.annotations.api.documentation.Documentation;
import org.eclipse.acceleo.annotations.api.documentation.Example;
import org.eclipse.acceleo.annotations.api.documentation.Other;
import org.eclipse.acceleo.annotations.api.documentation.Param;
import org.eclipse.acceleo.annotations.api.documentation.ServiceProvider;
import org.eclipse.emf.ecore.EClass;

/**
 * Utility class used to compute the html of the documentation.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
@SuppressWarnings({"checkstyle:multiplestringliterals" })
public final class AQLHelpContentUtils {

	/**
	 * 2016 method signature generator.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static final class MethodSignatureGenerator2016 implements Function<Method, StringBuffer> {
		@Override
		public StringBuffer apply(Method method) {
			Map<String, String> methodnamesToOperator = new LinkedHashMap<String, String>();
			methodnamesToOperator.put("add", " + ");
			methodnamesToOperator.put("sub", " - ");
			methodnamesToOperator.put("equals", " = ");
			methodnamesToOperator.put("differs", " <> ");

			methodnamesToOperator.put("greaterThan", " > ");
			methodnamesToOperator.put("greaterThanEqual", " >= ");
			methodnamesToOperator.put("lessThan", " < ");
			methodnamesToOperator.put("lessThanEqual", " <= ");

			StringBuffer result = new StringBuffer();
			boolean isOperator = false;

			boolean first = true;

			List<String> parameterNames = new ArrayList<String>();
			Documentation documentation = method.getAnnotation(Documentation.class);
			Param[] params = documentation.params();
			if (params != null) {
				for (Param param : params) {
					parameterNames.add(param.name());
				}
			}

			Class<?>[] parameterTypes = method.getParameterTypes();
			Type[] genericParameterTypes = method.getGenericParameterTypes();
			for (int i = 0; i < parameterTypes.length; i = i + 1) {
				Object argType = parameterTypes[i];

				String typeName = "";
				if (argType instanceof Class<?>) {
					typeName = getPrettyGenericTypename(genericParameterTypes[i], (Class<?>)argType);

				} else if (argType instanceof EClass) {
					typeName = "EClass=" + ((EClass)argType).getName();
				} else {
					typeName = "Object=" + argType.toString();
				}

				if (first) {
					String methodName = method.getName();
					/*
					 * check for operator names
					 */
					if (methodnamesToOperator.get(methodName) != null) {
						isOperator = true;
						methodName = methodnamesToOperator.get(methodName);
					}

					result.append(typeName);

					if (!isOperator) {
						if (isCollection((Class<?>)argType)) {
							result.append("->");
						} else {
							result.append(".");
						}
					}

					result.append(methodName);
					if (!isOperator) {
						result.append('(');
					}
				}

				if (!first) {
					if (i > 1) {
						result.append(", ");
					}
					result.append(typeName);
				} else {
					first = false;
				}

			}
			if (!isOperator) {
				result.append(')');
			}

			Type returnType = method.getGenericReturnType();
			if (Void.class.equals(returnType)) {
				result.append(" : void");
			} else {
				result.append(" : ");
				result.append(getPrettyGenericTypename(returnType, method.getReturnType()));
			}
			return result;
		}
	}

	/**
	 * 2016 method signature generator.
	 */
	public static final Function<Method, StringBuffer> METHOD_SIGNATURE_GENERATOR_2016 = new MethodSignatureGenerator2016();

	/**
	 * The line separator.
	 */
	public static final String LS = System.getProperty("line.separator");

	/**
	 * The constructor.
	 */
	private AQLHelpContentUtils() {
		// Prevent instantiation
	}

	/**
	 * Computes the sections for a service provider.
	 * 
	 * @param serviceProviderClass
	 *            The service provider
	 * @param titleLevel
	 *            the title level
	 * @param signatureGenerator
	 *            the signature generator {@link Function}
	 * @return The sections to display in the HTML page
	 */
	public static List<StringBuffer> computeServiceSections(Class<?> serviceProviderClass,
			Function<Method, StringBuffer> signatureGenerator) {
		List<StringBuffer> buffers = new ArrayList<StringBuffer>();

		ServiceProvider serviceProvider = serviceProviderClass.getAnnotation(ServiceProvider.class);
		if (serviceProvider == null) {
			return buffers;
		}

		StringBuffer servicesSection = new StringBuffer();
		servicesSection.append("=== ").append(serviceProvider.value()).append(LS).append(LS);

		Method[] methods = serviceProviderClass.getMethods();

		Method[] sortedMethods = Arrays.copyOf(methods, methods.length);
		Comparator<Method> comparator = new Comparator<Method>() {
			@Override
			public int compare(Method o1, Method o2) {
				return getSortSignature(o1).compareTo(getSortSignature(o2));
			}
		};
		Arrays.sort(sortedMethods, 0, sortedMethods.length, comparator);

		for (Method method : sortedMethods) {
			if (method.isAnnotationPresent(Documentation.class)) {
				Documentation serviceDocumentation = method.getAnnotation(Documentation.class);

				servicesSection.append(LS);

				servicesSection.append("==== ").append(signatureGenerator.apply(method)).append(LS).append(
						LS);
				servicesSection.append(serviceDocumentation.value()).append(LS).append(LS);
				servicesSection.append("Parameters:").append(LS).append(LS);
				if (serviceDocumentation.params().length > 0) {
					for (Param param : serviceDocumentation.params()) {
						servicesSection.append("* *").append(param.name()).append("*: ").append(param.value())
								.append(LS);
					}
					servicesSection.append(LS);
				}
				if (serviceDocumentation.examples().length > 0) {
					servicesSection.append("|===").append(LS);
					servicesSection.append("| *Expression* | *Result*").append(LS);

					List<Other> others = new ArrayList<Other>();

					Example[] examples = serviceDocumentation.examples();
					for (Example example : examples) {
						servicesSection.append("| `").append(example.expression().replace("|", "\\|")).append(
								"` | `").append(example.result().replace("|", "\\|")).append("`").append(LS);

						Other[] otherExamples = example.others();
						for (Other otherExample : otherExamples) {
							others.add(otherExample);
						}
					}
					servicesSection.append("|===").append(LS).append(LS);

					if (others.size() > 0) {
						servicesSection.append("*In other languages*").append(LS).append(LS);
						servicesSection.append("|===").append(LS);
						servicesSection.append("| *Language* | *Expression* | *Result* |").append(LS);
						for (Other alternate : others) {
							servicesSection.append("| `").append(alternate.language().replace("|", "\\|"))
									.append("` | `").append(alternate.expression().replace("|", "\\|"))
									.append("` | `").append(alternate.result().replace("|", "\\|")).append(
											"`").append(LS);
						}
						servicesSection.append("|===").append(LS).append(LS);
					}
				}
			}
		}

		buffers.add(servicesSection);

		return buffers;
	}

	/**
	 * Gets the signature of the given {@link Method} for sorting purpose.
	 * 
	 * @param method
	 *            the {@link Method}
	 * @return the signature of the given {@link Method} for sorting purpose
	 */
	private static String getSortSignature(Method method) {
		final StringBuilder res = new StringBuilder();

		res.append(method.getName());
		for (Parameter parameter : method.getParameters()) {
			res.append(parameter.getName() + parameter.getType().getSimpleName());
		}

		return res.toString();
	}

	/**
	 * Tells if the given {@link Class argument type} is a collection.
	 * 
	 * @param argType
	 *            the argument type
	 * @return <code>true</code> if the given {@link Class argument type} is a collection, <code>false</code>
	 *         otherwise
	 */
	private static boolean isCollection(Class<?> argType) {
		final boolean res;

		String typeName = ((Class<?>)argType).getCanonicalName();
		if ("java.util.Set".equals(typeName)) {
			res = true;
		} else if ("java.util.List".equals(typeName) || "java.util.Collection".equals(typeName)) {
			res = true;
		} else {
			res = false;
		}

		return res;

	}

	/**
	 * Gets the pretty simple name of the given {@link Class argument type}.
	 * 
	 * @param argType
	 *            the argument type
	 * @return the pretty simple name of the given {@link Class argument type}
	 */
	public static String prettySimpleName(Class<?> argType) {
		String typeName = argType.getCanonicalName();
		if ("org.eclipse.acceleo.query.runtime.impl.LambdaValue".equals(typeName)) {
			typeName = " x | ... ";
		}
		if (typeName.startsWith("java.lang") || typeName.startsWith("java.util")) {
			typeName = argType.getSimpleName();
		}
		if (typeName.startsWith("org.eclipse.emf")) {
			typeName = argType.getSimpleName();
		}
		if ("List".equals(typeName)) {
			typeName = "Sequence";
		}
		if ("Set".equals(typeName)) {
			typeName = "OrderedSet";
		}
		return typeName;
	}

	/**
	 * Gets the pretty generic name of the given {@link Class argument type}.
	 * 
	 * @param type
	 *            the {@link Type}
	 * @param argType
	 *            the argument type
	 * @return the pretty generic name of the given {@link Class argument type}
	 */
	public static String getPrettyGenericTypename(Type type, Class<?> argType) {
		String typename = prettySimpleName(argType);
		if (type instanceof Class<?>) {
			typename = prettySimpleName((Class<?>)type);
		} else if (type instanceof ParameterizedType) {
			String canonical = ((Class<?>)argType).getCanonicalName();
			Type t = ((ParameterizedType)type).getActualTypeArguments()[0];
			if (t instanceof Class<?>) {
				if ("java.util.Set".equals(canonical)) {
					typename = "OrderedSet{" + prettySimpleName((Class<?>)t) + "}";
				} else if ("java.util.List".equals(canonical) || "java.util.Collection".equals(canonical)) {
					typename = "Sequence{" + prettySimpleName((Class<?>)t) + "}";
				} else {
					typename = "{" + prettySimpleName((Class<?>)t) + "}";
				}

			}
		}
		return typename;

	}
}
