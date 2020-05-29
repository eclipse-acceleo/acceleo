/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.resolver;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;

/**
 * Resolve using a {@link ClassLoader}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ClassLoaderQualifiedNameResolver implements IQualifiedNameResolver {

	/**
	 * The {@link ClassLoader}.
	 */
	private final ClassLoader classLoader;

	/** The {@link AcceleoParser}. */
	private final AcceleoParser parser;

	/**
	 * The mapping form qualified name to resource {@link URL}.
	 */
	private final Map<String, URL> qualifiedNameToURL = new HashMap<String, URL>();

	/**
	 * The mapping form resource {@link URL} to qualified name.
	 */
	private final Map<URL, String> urlToQualifiedName = new HashMap<URL, String>();

	/**
	 * Constructor.
	 * 
	 * @param classLoader
	 *            the {@link ClassLoader}
	 * @param queryEnvironment
	 *            The AQL environment to use when parsing resolved modules.
	 */
	public ClassLoaderQualifiedNameResolver(ClassLoader classLoader,
			IReadOnlyQueryEnvironment queryEnvironment) {
		Objects.requireNonNull(classLoader);
		Objects.requireNonNull(queryEnvironment);
		this.classLoader = classLoader;
		this.parser = new AcceleoParser(queryEnvironment);
	}

	@Override
	public Module resolveModule(String qualifiedName) throws IOException {
		final Module res;

		try (InputStream input = classLoader.getResourceAsStream(getModuleResourceName(qualifiedName));) {
			if (input != null) {
				final String namespace = qualifiedName.substring(0, qualifiedName.lastIndexOf(
						AcceleoParser.QUALIFIER_SEPARATOR));
				res = parser.parse(input, StandardCharsets.UTF_8, namespace).getModule();
			} else {
				res = null;
			}
		}
		return res;
	}

	/**
	 * Gets the {@link Module} resource name from the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name (e.g. <code>qualified::path::to::module</code>)
	 * @return the {@link Module} resource name from the given qualified name
	 */
	private String getModuleResourceName(String qualifiedName) {
		return qualifiedName.replace(AcceleoParser.QUALIFIER_SEPARATOR, "/") + "."
				+ AcceleoParser.MODULE_FILE_EXTENSION;
	}

	@Override
	public Class<?> resolveClass(String qualifiedName) throws ClassNotFoundException {
		return classLoader.loadClass(getClassName(qualifiedName));
	}

	/**
	 * Gets the {@link Class} name from the given qualified name.
	 * 
	 * @param qualifiedName
	 *            the qualified name (e.g. <code>qualified::path::to::module</code>)
	 * @return the {@link Class} name from the given qualified name
	 */
	private String getClassName(String qualifiedName) {
		return qualifiedName.replace(AcceleoParser.QUALIFIER_SEPARATOR, ".");
	}

	@Override
	public URL getModuleURL(String qualifiedName) {
		// TODO null values are never cached...
		return qualifiedNameToURL.computeIfAbsent(qualifiedName, key -> {
			return classLoader.getResource(getModuleResourceName(key));
		});
	}

	@Override
	public String getQualifierName(URL resource) {
		// TODO null values are never cached...
		return urlToQualifiedName.computeIfAbsent(resource, key -> {
			String res = null;

			final String filePath = resource.getFile();
			final String[] segments = filePath.substring(0, filePath.length()
					- (AcceleoParser.MODULE_FILE_EXTENSION.length() + 1)).split("/");

			final StringBuilder moduleQualifiedNameBuilder = new StringBuilder();
			for (int i = segments.length - 1; i >= 0; i--) {
				moduleQualifiedNameBuilder.insert(0, segments[i]);
				final String qualifiedName = moduleQualifiedNameBuilder.toString();
				if (getModuleURL(qualifiedName) != null) {
					res = qualifiedName;
					break;
				}
				moduleQualifiedNameBuilder.insert(0, AcceleoParser.QUALIFIER_SEPARATOR);
			}

			return res;
		});
	}

}
