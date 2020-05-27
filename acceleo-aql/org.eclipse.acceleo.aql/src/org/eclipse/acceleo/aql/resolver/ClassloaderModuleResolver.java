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
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;

/**
 * Resolve using a {@link ClassLoader}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ClassloaderModuleResolver implements IModuleResolver {

	/**
	 * The {@link ClassLoader}.
	 */
	private final ClassLoader classLoader;

	/** The {@link AcceleoParser}. */
	private final AcceleoParser parser;

	/**
	 * Constructor.
	 * 
	 * @param classLoader
	 *            the {@link ClassLoader}
	 * @param queryEnvironment
	 *            The AQL environment to use when parsing resolved modules.
	 */
	public ClassloaderModuleResolver(ClassLoader classLoader, IReadOnlyQueryEnvironment queryEnvironment) {
		this.classLoader = classLoader;
		Objects.requireNonNull(queryEnvironment);
		this.parser = new AcceleoParser(queryEnvironment);
	}

	@Override
	public Module resolveModule(String qualifiedName) throws IOException {
		final Module res;

		try (InputStream input = classLoader.getResourceAsStream(qualifiedName.replace(
				AcceleoParser.QUALIFIER_SEPARATOR, "/") + "." + AcceleoParser.MODULE_FILE_EXTENSION);) {
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

}
