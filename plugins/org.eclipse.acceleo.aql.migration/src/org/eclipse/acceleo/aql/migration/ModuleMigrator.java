/*******************************************************************************
 * Copyright (c) 2017, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.migration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.Documentation;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.migration.converters.ModuleConverter;
import org.eclipse.acceleo.aql.migration.parser.DocumentationParser;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.resource.EMtlResourceFactoryImpl;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.expressions.ExpressionsPackage;

/**
 * The migrator entrypoint, which loads the EMTL resource and converts its content.
 * 
 * @author <a href="mailto:william.piers@obeo.fr">William Piers</a>
 */
public final class ModuleMigrator {

	/**
	 * The EMTL files extension.
	 */
	private static final String EMTL_EXTENSION = "emtl";

	/**
	 * The OCL Standard Library URI.
	 */
	private static final String OCL_STD_LIB_URI = "http://www.eclipse.org/ocl/1.1.0/oclstdlib.ecore";

	/**
	 * The resource set.
	 */
	private ResourceSet resourceSet;

	/**
	 * A resolver able to retrieve qualified names of proxy modules (imports, extends).
	 */
	private IModuleResolver moduleResolver;

	/**
	 * The target folder {@link Path}.
	 */
	private final Path targetFolderPath;

	/**
	 * Constructor.
	 * 
	 * @param moduleResolver
	 *            a resolver able to retrieve qualified names of proxy modules (imports, extends)
	 * @param targetFolderPath
	 *            the target folder {@link Path}
	 */
	public ModuleMigrator(IModuleResolver moduleResolver, Path targetFolderPath) {
		this.moduleResolver = moduleResolver;
		this.targetFolderPath = targetFolderPath;
		this.resourceSet = createA3ResourceSet();
	}

	/**
	 * Converts the given EMTL to an Acceleo 4 AST module.
	 * 
	 * @param emtlFile
	 *            the emtl file
	 * @param originMTLFile
	 *            optional, allow to retrieve any comments above the module declaration
	 * @param newLine
	 *            the new line {@link String}
	 * @return the AST module
	 * @throws IOException
	 */
	public Module migrate(File emtlFile, File originMTLFile, String newLine) throws IOException {
		org.eclipse.acceleo.model.mtl.Module legacyModule = (org.eclipse.acceleo.model.mtl.Module)ModelUtils
				.load(emtlFile, resourceSet);
		ModuleConverter moduleConverter = new ModuleConverter(moduleResolver, targetFolderPath, newLine);
		Module convertedModule = (Module)moduleConverter.convert(legacyModule);
		if (originMTLFile != null) {
			parseModuleDocumentation(convertedModule, originMTLFile);
		}

		return convertedModule;
	}

	/**
	 * Inits the {@link ResourceSet} to make it able to load EMTL files.
	 * 
	 * @return an initialized {@link ResourceSet}
	 */
	private static ResourceSet createA3ResourceSet() {
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(org.eclipse.ocl.ecore.EcorePackage.eINSTANCE.getNsURI(),
				org.eclipse.ocl.ecore.EcorePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(ExpressionsPackage.eINSTANCE.getNsURI(),
				ExpressionsPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(MtlPackage.eINSTANCE.getNsURI(), MtlPackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(OCL_STD_LIB_URI, getOCLStdLibPackage());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(EMTL_EXTENSION,
				new EMtlResourceFactoryImpl());
		return resourceSet;
	}

	private static void parseModuleDocumentation(Module convertedModule, File originMTLFile)
			throws IOException {
		String originContent = new String(Files.readAllBytes(originMTLFile.toPath()));
		String beforeModule = originContent.substring(0, originContent.indexOf(
				DocumentationParser.MODULE_HEADER_START));
		List<Comment> allComments = new DocumentationParser().parse(beforeModule);
		List<Comment> comments = new ArrayList<>();
		for (Comment comment : allComments) {
			if (comment instanceof Documentation) {
				convertedModule.setDocumentation((Documentation)comment);
			} else {
				comments.add(comment);
			}
		}
		convertedModule.getModuleElements().addAll(0, comments);
	}

	/**
	 * Returns the package containing the OCL standard library.
	 * 
	 * @return The package containing the OCL standard library.
	 */
	private static EPackage getOCLStdLibPackage() {
		EcoreEnvironmentFactory factory = new EcoreEnvironmentFactory();
		EcoreEnvironment environment = (EcoreEnvironment)factory.createEnvironment();
		EPackage oclStdLibPackage = (EPackage)EcoreUtil.getRootContainer(environment.getOCLStandardLibrary()
				.getBag());
		environment.dispose();
		return oclStdLibPackage;
	}
}
