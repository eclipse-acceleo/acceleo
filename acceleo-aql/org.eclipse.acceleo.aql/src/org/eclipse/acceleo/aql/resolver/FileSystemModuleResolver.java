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

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;

/**
 * A simple module resolver that will resolve qualified names as a hierarchy of folders under a given root.
 * <p>
 * <b>Note</b> that said root is not an expected part of the qualified names.
 * </p>
 * <p>
 * For example, if the specified rootPath is "c:/org.eclipse.acceleo.sample.module/src" then the qualified
 * name <code>org::acceleo::sample::module::main</code> will be resolved against the file
 * "c:/org.eclipse.acceleo.sample.module/src/org/acceleo/sample/module/main.mtl".
 * </p>
 * 
 * @author lgoubet
 */
public class FileSystemModuleResolver implements IModuleResolver {

	/** The file extension for acceleo modules. */
	private static final String MODULE_EXTENSION = "." + AcceleoParser.MODULE_FILE_EXTENSION;

	/** Root of our qualified paths. */
	private final Path rootPath;

	/** The {@link AcceleoParser}. */
	private final AcceleoParser parser;

	/**
	 * Instantiate a resolved given its root path and the AQL environment.
	 * 
	 * @param rootPath
	 *            Root of our qualified paths.
	 * @param queryEnvironment
	 *            The AQL environment to use when parsing resolved modules.
	 */
	public FileSystemModuleResolver(Path rootPath, IReadOnlyQueryEnvironment queryEnvironment) {
		Objects.requireNonNull(rootPath);
		if (!Files.exists(rootPath) || !Files.isDirectory(rootPath)) {
			throw new IllegalArgumentException(rootPath + " doesn't exist or isn't a directory.");
		}
		this.rootPath = rootPath;
		Objects.requireNonNull(queryEnvironment);
		this.parser = new AcceleoParser(queryEnvironment);
	}

	@Override
	public Module resolveModule(String qualifiedName) throws IOException {
		String fsRelativePath = qualifiedName.replace(AcceleoParser.QUALIFIER_SEPARATOR, File.separator)
				.concat(MODULE_EXTENSION);
		Path sought = rootPath.resolve(fsRelativePath);
		if (Files.exists(sought)) {
			String namespace = qualifiedName.substring(0, qualifiedName.lastIndexOf("::"));
			AcceleoAstResult astResult = parser.parse(new String(Files.readAllBytes(sought),
					StandardCharsets.UTF_8), namespace);
			// TODO log astResult.getErrors()
			if (astResult.getModule() != null) {
				return astResult.getModule();
			}
		}
		return null;
	}

}
