/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.migration.standalone;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import org.eclipse.acceleo.aql.migration.IModuleResolver;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * A standalone modules resolver.
 * 
 * @author <a href="mailto:william.piers@obeo.fr">William Piers</a>
 */
public final class StandaloneModuleResolver implements IModuleResolver {

	/**
	 * The folder where emtl files are.
	 */
	private static final String BIN_FOLDER_NAME = "bin";

	/**
	 * The qualified module names delimiter.
	 */
	private static final String DELIMITER = "::";

	/**
	 * The Acceleo 3 bin path.
	 */
	private Path binFolderPath;

	/**
	 * Creates a resolver using the given bin folder path.
	 * 
	 * @param binFolderPath
	 *            the bin folder path
	 */
	public StandaloneModuleResolver(Path binFolderPath) {
		this.binFolderPath = binFolderPath;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.migration.IModuleResolver#getQualifiedName(org.eclipse.acceleo.model.mtl.Module,
	 *      org.eclipse.acceleo.model.mtl.Module)
	 */
	@Override
	public String getQualifiedName(Module module, Module refModule) {
		URI refModuleURI = EcoreUtil.getURI(refModule);
		Path emtlPath = null;
		if (refModuleURI.isPlatformResource() || refModuleURI.isPlatformPlugin()) {
			emtlPath = Paths.get(binFolderPath.toFile().getParentFile().getParentFile() + refModuleURI
					.toPlatformString(true));
		} else {
			Path modulePath = Paths.get(EcoreUtil.getURI(module).trimFragment().toFileString());
			Path refModulePath = Paths.get(refModuleURI.trimFragment().toFileString());
			emtlPath = modulePath.getParent().resolve(refModulePath).normalize();
		}

		if (emtlPath.toFile().exists()) {
			try {
				return getModuleQualifiedName(emtlPath);
			} catch (IOException e) {
				System.err.println("Unable to resolve " + refModuleURI);
				e.printStackTrace();
			}
		}
		System.err.println("File not found " + emtlPath);
		return null;
	}

	private String getModuleQualifiedName(Path emtlFile) throws IOException {
		String moduleName = readModuleName(emtlFile);
		StringBuffer res = new StringBuffer();
		if (emtlFile.startsWith(binFolderPath)) {
			// "clean" way
			Path relevant = binFolderPath.relativize(emtlFile.getParent());
			Iterator<Path> iterator = relevant.iterator();
			while (iterator.hasNext()) {
				Path path = iterator.next();
				res.append(path);
				res.append(DELIMITER);
			}
		} else {
			// we try to resolve the .emtl inside of its own bin folder
			Iterator<Path> emtlPathIterator = emtlFile.getParent().iterator();
			boolean relevant = false;
			while (emtlPathIterator.hasNext()) {
				Path path = emtlPathIterator.next();
				if (relevant) {
					res.append(path);
					res.append(DELIMITER);
				}
				if (BIN_FOLDER_NAME.equals(path.toString())) {
					relevant = true;
				}

			}
		}
		res.append(moduleName);
		return res.toString();
	}

	private static String readModuleName(Path emtlFile) throws IOException {
		String res = null;
		for (String line : Files.readAllLines(emtlFile)) {
			String prefix = "<mtl:Module name=\"";
			if (line.trim().startsWith(prefix)) {
				res = line.trim().substring(prefix.length());
				res = res.substring(0, res.indexOf("\"")).trim();
				break;
			}
		}
		return res;
	}

}
