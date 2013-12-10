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
package org.eclipse.acceleo.parser.compiler;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.AcceleoDynamicMetamodelResourceSetImpl;
import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.resource.EMtlBinaryResourceFactoryImpl;
import org.eclipse.acceleo.model.mtl.resource.EMtlResourceFactoryImpl;
import org.eclipse.acceleo.parser.AcceleoFile;
import org.eclipse.acceleo.parser.AcceleoParser;
import org.eclipse.acceleo.parser.AcceleoParserProblem;
import org.eclipse.acceleo.parser.AcceleoParserProblems;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.expressions.ExpressionsPackage;

/**
 * The AbstractAcceleoCompiler should be extended for those looking for a utility class to compile acceleo
 * plugins.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
@Deprecated
public abstract class AbstractAcceleoCompiler {

	/**
	 * The jar file extension.
	 */
	private static final String JAR_EXTENSION = ".jar"; //$NON-NLS-1$

	/**
	 * Indicates if we should use binary resources for the serialization of the EMTL files.
	 */
	protected boolean binaryResource = true;

	/**
	 * The source folder to compile.
	 */
	protected File sourceFolder;

	/**
	 * The output folder to place the compiled files.
	 */
	protected File outputFolder;

	/**
	 * Indicates if we should trim the position.
	 * 
	 * @since 3.2
	 */
	protected boolean trimPosition;

	/**
	 * The dependencies folders.
	 */
	protected List<File> dependencies = new ArrayList<File>();

	/**
	 * The dependencies identifiers.
	 */
	protected List<String> dependenciesIDs = new ArrayList<String>();

	/**
	 * The URIs of the emtl files inside the jars.
	 * 
	 * @since 3.2
	 */
	protected List<URI> jarEmtlsURI = new ArrayList<URI>();

	/**
	 * Sets the source folder to compile.
	 * 
	 * @param theSourceFolder
	 *            are the source folder to compile
	 */
	public void setSourceFolder(String theSourceFolder) {
		this.sourceFolder = new Path(theSourceFolder).toFile();
	}

	/**
	 * Sets the dependencies to load before to compile. They are separated by ';'.
	 * 
	 * @param allDependencies
	 *            are the dependencies identifiers
	 */
	public void setDependencies(String allDependencies) {
		dependencies.clear();
		StringTokenizer st = new StringTokenizer(allDependencies, ";"); //$NON-NLS-1$
		while (st.hasMoreTokens()) {
			String path = st.nextToken().trim();
			if (path.length() > 0 && !path.endsWith(JAR_EXTENSION)) {
				File parent = new Path(path).removeLastSegments(1).toFile();
				if (parent != null && parent.exists() && parent.isDirectory()) {
					String segmentID = new Path(path).lastSegment();
					File[] candidates = parent.listFiles();
					Arrays.sort(candidates, new Comparator<File>() {
						public int compare(File o1, File o2) {
							return -o1.getName().compareTo(o2.getName());
						}
					});
					File bestRequiredFolder = null;
					for (File candidate : candidates) {
						if (candidate.isDirectory() && candidate.getName() != null
								&& candidate.getName().startsWith(segmentID)) {
							bestRequiredFolder = candidate;
							break;
						}
					}
					if (bestRequiredFolder != null && !dependencies.contains(bestRequiredFolder)) {
						dependencies.add(bestRequiredFolder);
						dependenciesIDs.add(segmentID);
					}
				}
			} else if (path.length() > 0 && path.endsWith(JAR_EXTENSION)) {
				// Let's compute the uris of the emtl files inside of the jar
				try {
					JarFile jarFile = new JarFile(path);
					Enumeration<JarEntry> entries = jarFile.entries();
					while (entries.hasMoreElements()) {
						JarEntry nextElement = entries.nextElement();
						String name = nextElement.getName();
						if (!nextElement.isDirectory()
								&& name.endsWith(IAcceleoConstants.EMTL_FILE_EXTENSION)) {
							URI jarFileURI = URI.createFileURI(path);
							URI entryURI = URI.createURI(name);
							URI uri = URI
									.createURI("jar:" + jarFileURI.toString() + "!/" + entryURI.toString()); //$NON-NLS-1$//$NON-NLS-2$
							this.jarEmtlsURI.add(uri);
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * This will update the resource set's package registry with all usual EPackages.
	 */
	protected void registerPackages() {
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
	protected EPackage getOCLStdLibPackage() {
		EcoreEnvironmentFactory factory = new EcoreEnvironmentFactory();
		EcoreEnvironment environment = (EcoreEnvironment)factory.createEnvironment();
		EPackage oclStdLibPackage = (EPackage)EcoreUtil.getRootContainer(environment.getOCLStandardLibrary()
				.getBag());
		environment.dispose();
		return oclStdLibPackage;
	}

	/**
	 * Registers the resource factories.
	 */
	protected void registerResourceFactories() {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(
				IAcceleoConstants.ECORE_FILE_EXTENSION, new EcoreResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getContentTypeToFactoryMap().put(
				IAcceleoConstants.BINARY_CONTENT_TYPE, new EMtlBinaryResourceFactoryImpl());
		Resource.Factory.Registry.INSTANCE.getContentTypeToFactoryMap().put(
				IAcceleoConstants.XMI_CONTENT_TYPE, new EMtlResourceFactoryImpl());
	}

	/**
	 * Registers the libraries.
	 */
	protected void registerLibraries() {
		CodeSource acceleoModel = MtlPackage.class.getProtectionDomain().getCodeSource();
		if (acceleoModel != null) {

			String libraryLocation = acceleoModel.getLocation().toString();

			if (libraryLocation.endsWith(".jar")) { //$NON-NLS-1$
				libraryLocation = "jar:" + libraryLocation + '!'; //$NON-NLS-1$
			}

			URL stdlib = MtlPackage.class.getResource("/model/mtlstdlib.ecore"); //$NON-NLS-1$
			URL resource = MtlPackage.class.getResource("/model/mtlnonstdlib.ecore"); //$NON-NLS-1$

			URIConverter.URI_MAP
					.put(URI.createURI("http://www.eclipse.org/acceleo/mtl/3.0/mtlstdlib.ecore"), URI.createURI(stdlib.toString())); //$NON-NLS-1$
			URIConverter.URI_MAP
					.put(URI.createURI("http://www.eclipse.org/acceleo/mtl/3.0/mtlnonstdlib.ecore"), URI.createURI(resource.toString())); //$NON-NLS-1$
		} else {
			System.err.println("Coudln't retrieve location of plugin 'org.eclipse.acceleo.model'."); //$NON-NLS-1$
		}
	}

	/**
	 * Sets the binary resource attribute.
	 * 
	 * @param binaryResource
	 *            Indicates if we should use a binary resource.
	 */
	public void setBinaryResource(boolean binaryResource) {
		this.binaryResource = binaryResource;
	}

	/**
	 * Sets the output folder.
	 * 
	 * @param theOutputFolder
	 *            The output folder.
	 */
	public void setOutputFolder(String theOutputFolder) {
		this.outputFolder = new Path(theOutputFolder).toFile();
	}

	/**
	 * Sets the boolean indicating if Acceleo should trim the positions from the emtl.
	 * 
	 * @param trimPosition
	 *            The boolean
	 * @since 3.2
	 */
	public void setTrimPosition(boolean trimPosition) {
		this.trimPosition = trimPosition;
	}

	/**
	 * Launches the compilation of the mtl files.
	 * 
	 * @param monitor
	 *            The progress monitor.
	 */
	public void doCompile(Monitor monitor) {
		compile(monitor);
	}

	/**
	 * Launches the compilation of the mtl files.
	 * 
	 * @param monitor
	 *            the progress monitor.
	 */
	protected void compile(Monitor monitor) {
		registerResourceFactories();
		registerPackages();
		registerLibraries();

		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			standaloneInit();
		}
		StringBuffer message = new StringBuffer();
		List<MTLFileInfo> fileInfos = new ArrayList<MTLFileInfo>();
		fileInfos.addAll(computeFileInfos(sourceFolder));
		List<AcceleoFile> acceleoFiles = new ArrayList<AcceleoFile>();
		List<URI> emtlAbsoluteURIs = new ArrayList<URI>();
		for (MTLFileInfo mtlFileInfo : fileInfos) {
			acceleoFiles.add(new AcceleoFile(mtlFileInfo.mtlFile, mtlFileInfo.fullModuleName));
			emtlAbsoluteURIs.add(mtlFileInfo.emtlAbsoluteURI);
		}
		List<URI> dependenciesURIs = new ArrayList<URI>();
		Map<URI, URI> mapURIs = new HashMap<URI, URI>();
		computeDependencies(dependenciesURIs, mapURIs);
		computeJarDependencies(dependenciesURIs, mapURIs);
		loadEcoreFiles();

		createOutputFiles(emtlAbsoluteURIs);

		AcceleoParser parser = new AcceleoParser(binaryResource, trimPosition);
		parser.parse(acceleoFiles, emtlAbsoluteURIs, dependenciesURIs, mapURIs, monitor);
		for (Iterator<AcceleoFile> iterator = acceleoFiles.iterator(); iterator.hasNext();) {
			AcceleoFile acceleoFile = iterator.next();
			AcceleoParserProblems problems = parser.getProblems(acceleoFile);
			if (problems != null) {
				List<AcceleoParserProblem> list = problems.getList();
				if (!list.isEmpty()) {
					message.append(acceleoFile.getMtlFile().getName());
					message.append('\n');
					for (Iterator<AcceleoParserProblem> itProblems = list.iterator(); itProblems.hasNext();) {
						AcceleoParserProblem problem = itProblems.next();
						message.append(problem.getLine());
						message.append(':');
						message.append(problem.getMessage());
						message.append('\n');
					}
					message.append('\n');
				}
			}
		}
		if (message.length() > 0) {
			String log = message.toString();
			throw new RuntimeException(log);
		}
	}

	/**
	 * We may be calling for the compilation in standalone mode. In such a case we need a little more
	 * initialization.
	 */
	private void standaloneInit() {
		Resource.Factory.Registry registry = Resource.Factory.Registry.INSTANCE;
		if (registry.getContentTypeToFactoryMap().get(IAcceleoConstants.BINARY_CONTENT_TYPE) == null) {
			registry.getContentTypeToFactoryMap().put(IAcceleoConstants.BINARY_CONTENT_TYPE,
					new EMtlBinaryResourceFactoryImpl());
		}
		if (registry.getContentTypeToFactoryMap().get(IAcceleoConstants.XMI_CONTENT_TYPE) == null) {
			registry.getContentTypeToFactoryMap().put(IAcceleoConstants.XMI_CONTENT_TYPE,
					new EMtlBinaryResourceFactoryImpl());
		}

		registry.getExtensionToFactoryMap().put(IAcceleoConstants.ECORE_FILE_EXTENSION,
				new EcoreResourceFactoryImpl());
		registerPackages();
	}

	/**
	 * Computes the properties of the MTL files of the given source folder.
	 * 
	 * @param theSourceFolder
	 *            the current source folder
	 * @return the MTL files properties
	 */
	protected List<MTLFileInfo> computeFileInfos(File theSourceFolder) {
		List<MTLFileInfo> fileInfosOutput = new ArrayList<MTLFileInfo>();
		String inputPath = sourceFolder.getAbsolutePath();
		String file = "file:"; //$NON-NLS-1$
		if (inputPath.startsWith(file)) {
			inputPath = inputPath.substring(file.length());
		}

		if (!theSourceFolder.exists()) {
			return fileInfosOutput;
		}
		String sourceFolderAbsolutePath = theSourceFolder.getAbsolutePath();
		List<File> mtlFiles = new ArrayList<File>();
		members(mtlFiles, theSourceFolder, IAcceleoConstants.MTL_FILE_EXTENSION);
		for (File mtlFile : mtlFiles) {
			String mtlFileAbsolutePath = mtlFile.getAbsolutePath();
			if (mtlFileAbsolutePath != null) {
				String relativePath;
				if (mtlFileAbsolutePath.startsWith(sourceFolderAbsolutePath)) {
					relativePath = mtlFileAbsolutePath.substring(sourceFolderAbsolutePath.length());
				} else {
					relativePath = mtlFile.getName();
				}

				URI emtlAbsoluteURI = null;
				if (outputFolder != null) {
					String outputPath = outputFolder.getAbsolutePath();
					if (outputPath.startsWith(file)) {
						outputPath = outputPath.substring(file.length());
					}

					String temp = new Path(mtlFileAbsolutePath).removeFileExtension().addFileExtension(
							IAcceleoConstants.EMTL_FILE_EXTENSION).toString();
					int segments = new Path(temp).matchingFirstSegments(new Path(inputPath));
					IPath path = new Path(temp).removeFirstSegments(segments);
					IPath emtlPath = new Path(outputPath).append(path);
					emtlAbsoluteURI = URI.createFileURI(emtlPath.toString());
				} else {
					emtlAbsoluteURI = URI.createFileURI(new Path(inputPath).removeFileExtension()
							.addFileExtension(IAcceleoConstants.EMTL_FILE_EXTENSION).toString());
				}

				MTLFileInfo fileInfo = new MTLFileInfo();
				fileInfo.mtlFile = mtlFile;
				fileInfo.emtlAbsoluteURI = emtlAbsoluteURI;
				fileInfo.fullModuleName = AcceleoFile.relativePathToFullModuleName(relativePath);
				fileInfosOutput.add(fileInfo);
			}
		}

		return fileInfosOutput;
	}

	/**
	 * Computes recursively the members of the given container that match the given file extension.
	 * 
	 * @param filesOutput
	 *            is the list to create
	 * @param container
	 *            is the container to browse
	 * @param extension
	 *            is the extension to match
	 */
	protected void members(List<File> filesOutput, File container, String extension) {
		if (container != null && container.isDirectory()) {
			File[] children = container.listFiles();
			if (children != null) {
				for (File child : children) {
					if (child.isFile() && child.getName() != null
							&& (extension == null || child.getName().endsWith('.' + extension))) {
						filesOutput.add(child);
					} else {
						members(filesOutput, child, extension);
					}
				}
			}
		}
	}

	/**
	 * Advanced resolution mechanism. There is sometimes a difference between how you want to load/save an
	 * EMTL resource and how you want to make this resource reusable.
	 * 
	 * @param dependenciesURIs
	 *            URIs of the dependencies that need to be loaded before link resolution
	 * @param mapURIs
	 *            Advanced mapping mechanism for the URIs that need to be loaded before link resolution, the
	 *            map key is the loading URI, the map value is the proxy URI (the real way to reuse this
	 *            dependency)
	 */
	protected void computeDependencies(List<URI> dependenciesURIs, Map<URI, URI> mapURIs) {
		Iterator<String> identifiersIt = dependenciesIDs.iterator();
		for (Iterator<File> dependenciesIt = dependencies.iterator(); dependenciesIt.hasNext()
				&& identifiersIt.hasNext();) {
			File requiredFolder = dependenciesIt.next();
			String identifier = identifiersIt.next();
			if (requiredFolder != null && requiredFolder.exists() && requiredFolder.isDirectory()) {
				String requiredFolderAbsolutePath = requiredFolder.getAbsolutePath();
				List<File> emtlFiles = new ArrayList<File>();
				members(emtlFiles, requiredFolder, IAcceleoConstants.EMTL_FILE_EXTENSION);
				for (File emtlFile : emtlFiles) {
					String emtlAbsolutePath = emtlFile.getAbsolutePath();
					URI emtlFileURI = URI.createFileURI(emtlAbsolutePath);
					dependenciesURIs.add(emtlFileURI);
					IPath relativePath = new Path(identifier).append(emtlAbsolutePath
							.substring(requiredFolderAbsolutePath.length()));
					mapURIs.put(emtlFileURI, URI.createPlatformPluginURI(relativePath.toString(), false));
				}
			}
		}
	}

	/**
	 * This method will compute add the emtls from the jar in the list of dependencies to be loaded for the
	 * compilation and map their URIs to the logical URIs used by Acceleo.
	 * 
	 * @param dependenciesURIs
	 *            The dependencies to be loaded
	 * @param mapURIs
	 *            The jars URIs.
	 * @since 3.2
	 */
	protected void computeJarDependencies(List<URI> dependenciesURIs, Map<URI, URI> mapURIs) {
		for (URI uri : this.jarEmtlsURI) {
			String uriStr = uri.toString();
			int i = uriStr.indexOf("!/"); //$NON-NLS-1$
			if (i > 0) {
				String fileURI = uriStr.substring(i + 2);
				String authority = uri.authority();
				int lastIndexOf = authority.lastIndexOf("/"); //$NON-NLS-1$
				int indexOf = authority.lastIndexOf("_"); //$NON-NLS-1$
				if (lastIndexOf > 0 && indexOf > 0) {
					authority = authority.substring(lastIndexOf, indexOf);
				}
				URI platformPluginURI = URI.createPlatformPluginURI(authority + "/" + fileURI, true); //$NON-NLS-1$
				mapURIs.put(uri, platformPluginURI);
				dependenciesURIs.add(uri);
			}
		}
	}

	/**
	 * Register the accessible ecore files.
	 */
	protected void loadEcoreFiles() {
		for (File requiredFolder : dependencies) {
			if (requiredFolder != null && requiredFolder.exists() && requiredFolder.isDirectory()) {
				List<File> ecoreFiles = new ArrayList<File>();
				members(ecoreFiles, requiredFolder, IAcceleoConstants.ECORE_FILE_EXTENSION);
				for (File ecoreFile : ecoreFiles) {
					URI ecoreURI = URI.createFileURI(ecoreFile.getAbsolutePath());
					AcceleoPackageRegistry.INSTANCE.registerEcorePackages(ecoreURI.toString(),
							AcceleoDynamicMetamodelResourceSetImpl.DYNAMIC_METAMODEL_RESOURCE_SET);
				}
			}
		}
	}

	/**
	 * Create the output folders for the output files.
	 * 
	 * @param emtlAbsoluteURIs
	 *            The emtl file uris.
	 */
	protected void createOutputFiles(List<URI> emtlAbsoluteURIs) {
		for (URI uri : emtlAbsoluteURIs) {
			String tmpUri = uri.toString();
			String file = "file:"; //$NON-NLS-1$
			if (tmpUri.startsWith(file)) {
				tmpUri = tmpUri.substring(file.length());
			}

			if (!new File(tmpUri).getParentFile().exists()) {
				new File(tmpUri).getParentFile().mkdirs();
			}
		}
	}

	/**
	 * The MTL file properties.
	 * 
	 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
	 * @since 3.1
	 */
	protected final class MTLFileInfo {

		/**
		 * The IO file.
		 */
		protected File mtlFile;

		/**
		 * The absolute URI.
		 */
		protected URI emtlAbsoluteURI;

		/**
		 * The full qualified module name.
		 */
		protected String fullModuleName;

		/**
		 * Constructor.
		 */
		protected MTLFileInfo() {
			// Hides constructor from anything other than AbstractAcceleoCompiler
		}

	}
}
