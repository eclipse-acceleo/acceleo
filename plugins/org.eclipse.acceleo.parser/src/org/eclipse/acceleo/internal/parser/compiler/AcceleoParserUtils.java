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
package org.eclipse.acceleo.internal.parser.compiler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.internal.parser.cst.utils.Sequence;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.resource.EMtlBinaryResourceFactoryImpl;
import org.eclipse.acceleo.model.mtl.resource.EMtlResourceFactoryImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.expressions.ExpressionsPackage;

/**
 * Utility class used during the compilation.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.2
 */
public final class AcceleoParserUtils {

	/**
	 * The constructor.
	 */
	private AcceleoParserUtils() {
		// Hides the constructor.
	}

	/**
	 * Gets all the possible sequences to search when we use the given file in other files : '[import myGen',
	 * '[import org::eclipse::myGen', 'extends myGen', 'extends org::eclipse::myGen'...
	 * 
	 * @param acceleoProject
	 *            is the project
	 * @param file
	 *            is the MTL file
	 * @return all the possible sequences to search for the given file
	 */
	public static List<Sequence> getImportSequencesToSearch(AcceleoProject acceleoProject, File file) {
		List<Sequence> result = new ArrayList<Sequence>();
		String simpleModuleName = file.getName();
		if (simpleModuleName.endsWith(IAcceleoConstants.MTL_FILE_EXTENSION)) {
			simpleModuleName = simpleModuleName.substring(0, simpleModuleName.length()
					- (IAcceleoConstants.MTL_FILE_EXTENSION.length() + 1));
		}
		String[] tokens = new String[] {IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.IMPORT,
				simpleModuleName, };
		result.add(new Sequence(tokens));
		tokens = new String[] {IAcceleoConstants.EXTENDS, simpleModuleName, };
		result.add(new Sequence(tokens));
		String fullModuleName = acceleoProject.getModuleQualifiedName(file);
		tokens = new String[] {IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.IMPORT, fullModuleName, };
		result.add(new Sequence(tokens));
		tokens = new String[] {IAcceleoConstants.EXTENDS, fullModuleName, };
		result.add(new Sequence(tokens));
		return result;
	}

	/**
	 * Returns the set of uri of the emtls contained in the jar with the given URI.
	 * 
	 * @param jar
	 *            The URI of the jar file.
	 * @return The set of uri of the emtls contained in the jar with the given URI.
	 */
	public static Set<URI> getAllModules(URI jar) {
		Set<URI> modulesURIs = new LinkedHashSet<URI>();
		try {
			String jarPath = jar.toString();
			String osName = System.getProperty("os.name"); //$NON-NLS-1$
			final String file = "file:"; //$NON-NLS-1$

			if (osName.indexOf("win") >= 0) { //$NON-NLS-1$
				// Windows
				if (jarPath.startsWith("file:\\") || jarPath.startsWith("file:/")) { //$NON-NLS-1$ //$NON-NLS-2$
					jarPath = jarPath.substring(6);
				}
			} else if (osName.indexOf("Mac") >= 0) { //$NON-NLS-1$
				// Mac
				if (jarPath.startsWith(file)) {
					jarPath = jarPath.substring(5);
				}
			} else if (osName.indexOf("nux") >= 0 || osName.indexOf("nix") >= 0) { //$NON-NLS-1$ //$NON-NLS-2$
				// Unix / Linux
				if (jarPath.startsWith(file)) {
					jarPath = jarPath.substring(5);
				}
			} else {
				// Unix / Linux
				if (jarPath.startsWith(file)) {
					jarPath = jarPath.substring(5);
				}
			}
			JarFile jarFile = new JarFile(jarPath);
			Enumeration<JarEntry> entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				JarEntry nextElement = entries.nextElement();
				String name = nextElement.getName();
				if (!nextElement.isDirectory() && name.endsWith(IAcceleoConstants.EMTL_FILE_EXTENSION)) {
					URI jarFileURI = URI.createFileURI(jarPath);
					URI entryURI = URI.createURI(name);
					URI uri = URI.createURI("jar:" + jarFileURI.toString() + "!/" + entryURI.toString()); //$NON-NLS-1$//$NON-NLS-2$
					modulesURIs.add(uri);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return modulesURIs;
	}

	/**
	 * Returns the module name (a::b::c::d) of the resources at the given URI.
	 * 
	 * @param moduleURI
	 *            The URI of an emtl file.
	 * @return The module name (a::b::c::d) of the resources at the given URI.
	 */
	public static String getModuleName(URI moduleURI) {
		String modulePath = moduleURI.toString();
		if (modulePath.startsWith("jar:file:")) { //$NON-NLS-1$
			int indexOf = modulePath.indexOf(".jar!/"); //$NON-NLS-1$
			if (indexOf != -1) {
				String moduleName = modulePath.substring(indexOf + ".jar!/".length()); //$NON-NLS-1$
				if (moduleName.endsWith(IAcceleoConstants.EMTL_FILE_EXTENSION)) {
					moduleName = moduleName.substring(0, moduleName.length()
							- (IAcceleoConstants.EMTL_FILE_EXTENSION.length() + 1));
					moduleName = moduleName.replace("/", IAcceleoConstants.NAMESPACE_SEPARATOR); //$NON-NLS-1$
					return moduleName;
				}
			}
		}
		return null;
	}

	/**
	 * Delete the given directory.
	 * 
	 * @param directory
	 *            The directory.
	 * @return <code>true</code> if the directory has been deleted along with its content, <code>false</code>
	 *         otherwise.
	 */
	public static boolean removeDirectory(File directory) {
		boolean result = false;

		if (!directory.exists()) {
			result = true;
		}

		String[] list = directory.list();

		// Some JVMs return null for File.list() when the
		// directory is empty.
		if (list != null) {
			for (int i = 0; i < list.length; i++) {
				File entry = new File(directory, list[i]);
				// System.out.println("\tremoving entry " + entry);
				if (entry.isDirectory()) {
					if (!removeDirectory(entry)) {
						result = false;
					}
				} else {
					if (!entry.delete()) {
						result = false;
					}
				}
			}
		}
		result = directory.delete();
		return result;
	}

	/**
	 * Register the necessary resource factories.
	 * 
	 * @param resourceSet
	 *            The resource set.
	 */
	public static void registerResourceFactories(ResourceSet resourceSet) {
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", //$NON-NLS-1$
				new EcoreResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getContentTypeToFactoryMap().put(
				IAcceleoConstants.BINARY_CONTENT_TYPE, new EMtlBinaryResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getContentTypeToFactoryMap().put(
				IAcceleoConstants.XMI_CONTENT_TYPE, new EMtlResourceFactoryImpl());

		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", //$NON-NLS-1$
				new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getContentTypeToFactoryMap().put(
				IAcceleoConstants.BINARY_CONTENT_TYPE, new EMtlBinaryResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getContentTypeToFactoryMap().put(
				IAcceleoConstants.XMI_CONTENT_TYPE, new EMtlResourceFactoryImpl());
	}

	/**
	 * This will update the resource set's package registry with all usual EPackages.
	 * 
	 * @param resourceSet
	 *            The resource set.
	 */
	public static void registerPackages(ResourceSet resourceSet) {
		EPackage.Registry.INSTANCE.put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(org.eclipse.ocl.ecore.EcorePackage.eINSTANCE.getNsURI(),
				org.eclipse.ocl.ecore.EcorePackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(ExpressionsPackage.eINSTANCE.getNsURI(), ExpressionsPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put(MtlPackage.eINSTANCE.getNsURI(), MtlPackage.eINSTANCE);
		EPackage.Registry.INSTANCE.put("http://www.eclipse.org/ocl/1.1.0/oclstdlib.ecore", //$NON-NLS-1$
				getOCLStdLibPackage());
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

	/**
	 * Register the libraries.
	 * 
	 * @param resourceSet
	 *            The resource set.
	 */
	public static void registerLibraries(ResourceSet resourceSet) {
		CodeSource acceleoModel = MtlPackage.class.getProtectionDomain().getCodeSource();
		if (acceleoModel != null) {

			String libraryLocation = acceleoModel.getLocation().toString();

			if (libraryLocation.endsWith(".jar")) { //$NON-NLS-1$
				libraryLocation = "jar:" + libraryLocation + '!'; //$NON-NLS-1$
			} else if (libraryLocation.startsWith("file:/") && libraryLocation.endsWith("bin/")) { //$NON-NLS-1$ //$NON-NLS-2$
				libraryLocation = libraryLocation.substring(6, libraryLocation.length() - 4);
			}

			String std = libraryLocation + "model/mtlstdlib.ecore"; //$NON-NLS-1$
			String nonstd = libraryLocation + "model/mtlnonstdlib.ecore"; //$NON-NLS-1$

			URL stdlib = MtlPackage.class.getResource("/model/mtlstdlib.ecore"); //$NON-NLS-1$
			URL resource = MtlPackage.class.getResource("/model/mtlnonstdlib.ecore"); //$NON-NLS-1$

			if (stdlib != null && resource != null) {
				URIConverter.URI_MAP
						.put(URI.createURI("http://www.eclipse.org/acceleo/mtl/3.0/mtlstdlib.ecore"), URI.createURI(stdlib.toString())); //$NON-NLS-1$
				URIConverter.URI_MAP
						.put(URI.createURI("http://www.eclipse.org/acceleo/mtl/3.0/mtlnonstdlib.ecore"), URI.createURI(resource.toString())); //$NON-NLS-1$
			} else {
				URIConverter.URI_MAP
						.put(URI.createURI("http://www.eclipse.org/acceleo/mtl/3.0/mtlstdlib.ecore"), URI.createFileURI(std)); //$NON-NLS-1$
				URIConverter.URI_MAP
						.put(URI.createURI("http://www.eclipse.org/acceleo/mtl/3.0/mtlnonstdlib.ecore"), URI.createFileURI(nonstd)); //$NON-NLS-1$
			}
		} else {
			System.err.println("Coudln't retrieve location of plugin 'org.eclipse.acceleo.model'."); //$NON-NLS-1$
		}
	}

}
