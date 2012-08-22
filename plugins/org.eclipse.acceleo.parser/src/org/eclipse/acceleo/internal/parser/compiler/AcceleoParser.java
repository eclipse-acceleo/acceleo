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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.internal.parser.AcceleoParserMessages;
import org.eclipse.acceleo.internal.parser.cst.utils.Sequence;
import org.eclipse.acceleo.model.mtl.resource.AcceleoResourceSetImpl;
import org.eclipse.acceleo.parser.AcceleoFile;
import org.eclipse.acceleo.parser.AcceleoParserInfo;
import org.eclipse.acceleo.parser.AcceleoParserProblem;
import org.eclipse.acceleo.parser.AcceleoParserWarning;
import org.eclipse.acceleo.parser.AcceleoSourceBuffer;
import org.eclipse.acceleo.parser.cst.ModuleExtendsValue;
import org.eclipse.acceleo.parser.cst.ModuleImportsValue;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.ocl.ecore.OperationCallExp;

/**
 * The Acceleo Parser is the main class used to compile Acceleo projects. The parser uses
 * {@link AcceleoProject} in order to obtain the structure of the projects to compile. This class provides to
 * methods to compile files in an Acceleo project:
 * <ul>
 * <li>Build one file: build the given file and all its dependencies (if they have not been built previously)
 * and all the files affected by the build of the file (if the changes in the built file compared to the
 * previous version of the file can impact any other file).</li>
 * <li>Build all: build the first file of the project (with all its dependencies and all the files that it
 * impact if needed) and then if they are other files that have not been built, build them. If you want to
 * force the build of all the files, you should clean the project first. This method ensure that all files are
 * built, not that all files are rebuilt.</li>
 * </ul>
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.2
 */
public class AcceleoParser {

	/**
	 * The {@link AcceleoProject} containing the file to build.
	 */
	private AcceleoProject acceleoProject;

	/**
	 * Indicates if the serialization will use binary reources.
	 */
	private boolean usebinaryResources;

	/**
	 * Indicates if the parser should report errors againsts the full OMG compilance.
	 */
	private boolean checkOMGCompliance;

	/**
	 * The resource set used during the compilation.
	 */
	private ResourceSet resourceSet = new AcceleoResourceSetImpl();

	/**
	 * The listeners of the compilation.
	 */
	private Set<IParserListener> listeners = new LinkedHashSet<IParserListener>();

	/**
	 * The file that has trigger the build of the current file. For example, if A needs B to build, building A
	 * trigger the build of B, so this variable would contain A (to ensure that the build of B does not
	 * trigger a build of A).
	 */
	private File dependingBuiltFile;

	/**
	 * This object is used to change the uris of the loaded dependencies in order, for example, to change
	 * absolute paths of emtl files to platform:/plugin paths.
	 */
	private IAcceleoParserURIHandler uriHandler;

	/**
	 * The files with a main tag.
	 */
	private Set<File> mainFiles = new LinkedHashSet<File>();

	/**
	 * The problems computed during the build.
	 */
	private Multimap<File, AcceleoParserProblem> problems = ArrayListMultimap.create();

	/**
	 * The warning computed during the build.
	 */
	private Multimap<File, AcceleoParserWarning> warnings = ArrayListMultimap.create();

	/**
	 * The information computed during the build.
	 */
	private Multimap<File, AcceleoParserInfo> infos = ArrayListMultimap.create();

	/**
	 * The set of file that have to be built because they are a needed dependency of the "first" file buit.
	 * For example, if A needs B and B needs C and C needs A, building A would make this collection contain:
	 * <ol>
	 * <li>B</li>
	 * <li>B, C</li>
	 * <li>B, C, A</li>
	 * </ol>
	 * Thus it will let us detect circular dependencis during the build.
	 */
	private Set<File> dependenciesToBuild = new LinkedHashSet<File>();

	/**
	 * Indicates that we will use platform:/resource uris for the dependencies.
	 */
	private boolean usePlatformResourcePath;

	/**
	 * Cache of the input file to output file mapping.
	 */
	private Map<File, File> input2output = new HashMap<File, File>();

	/**
	 * Constructs a new instance of an AcceleoParser for the given project.
	 * 
	 * @param acceleoProject
	 *            The project in which we will build files.
	 * @param useBinaryResources
	 *            Indicates if we will use binary resources.
	 * @param usePlatformResourcePath
	 *            Indicates if we will use platform:/resource paths to load our dependencies.
	 */
	public AcceleoParser(AcceleoProject acceleoProject, boolean useBinaryResources,
			boolean usePlatformResourcePath) {
		this.resourceSet.setPackageRegistry(AcceleoPackageRegistry.INSTANCE);
		AcceleoParserUtils.registerResourceFactories(resourceSet);
		AcceleoParserUtils.registerPackages(resourceSet);
		AcceleoParserUtils.registerLibraries(resourceSet);
		this.acceleoProject = acceleoProject;
		this.usebinaryResources = useBinaryResources;
		this.usePlatformResourcePath = usePlatformResourcePath;
	}

	/**
	 * Adds a dependency in the set of files that need to be built (use to detect circular dependencies). This
	 * operation is protected in order to be used in this package only. It would be useless as a public
	 * operation for users.
	 * 
	 * @param file
	 *            The dependency that needs to be built.
	 */
	protected void addDependencyToBuild(File file) {
		this.dependenciesToBuild.add(file);
	}

	/**
	 * Clears the dependency used to calculate the possible occurrence of a circular dependency. This
	 * operation is protected in order to be used in this package only. It would be useless as a public
	 * operation for users.
	 */
	protected void clearDependencyToBuild() {
		this.dependenciesToBuild.clear();
	}

	/**
	 * Clears the given file from the dependency used to calculate the possible occurrence of a circular
	 * dependency. This operation is protected in order to be used in this package only. It would be useless
	 * as a public operation for users.
	 * 
	 * @param file
	 *            A dependency built.
	 */
	protected void removeBuiltDependency(File file) {
		this.dependenciesToBuild.remove(file);
	}

	/**
	 * Adds the given listeners.
	 * 
	 * @param parserListeners
	 *            The listeners.
	 */
	public void addListeners(IParserListener... parserListeners) {
		for (IParserListener iParserListener : parserListeners) {
			this.listeners.add(iParserListener);
		}
	}

	/**
	 * Sets the given URI handler used to change the uri of a loaded resource is order to affect the
	 * serialization.
	 * 
	 * @param resolver
	 *            The URI resolver.
	 */
	public void setURIHandler(IAcceleoParserURIHandler resolver) {
		this.uriHandler = resolver;
	}

	/**
	 * Sets if the full OMG compilance is required.
	 * 
	 * @param fullOMGCompilance
	 *            The full OMG compliance.
	 */
	public void setFullOMGCompliance(boolean fullOMGCompilance) {
		this.checkOMGCompliance = fullOMGCompilance;
	}

	/**
	 * Returns the set of file with the main tag.
	 * 
	 * @return The set of file with the main tag.
	 */
	public Set<File> getMainFiles() {
		return this.mainFiles;
	}

	/**
	 * Build all the non built modules in the project (for a full build, clean the project before).
	 * 
	 * @param monitor
	 *            The monitor.
	 * @return The files built.
	 */
	public Set<File> buildAll(Monitor monitor) {
		Set<File> allAcceleoModules = this.acceleoProject.getAllAcceleoModules();
		Set<File> built = new LinkedHashSet<File>();
		if (!monitor.isCanceled()) {
			for (File file : allAcceleoModules) {
				if (!built.contains(file)) {
					built.addAll(this.build(file, monitor));
				}
			}
		}
		return built;
	}

	/**
	 * Build the given file in the project.
	 * 
	 * @param file
	 *            The file to build.
	 * @param monitor
	 *            The monitor.
	 * @return The files built.
	 */
	public Set<File> buildFile(File file, Monitor monitor) {
		Set<File> built = Collections.emptySet();
		try {
			built = this.build(file, monitor);
		} finally {
			Thread unloadThread = new Thread() {
				/**
				 * {@inheritDoc}
				 * 
				 * @see java.lang.Thread#run()
				 */
				@Override
				public void run() {
					List<Resource> resources = new ArrayList<Resource>();
					resources.addAll(resourceSet.getResources());
					Iterator<Resource> it = resources.iterator();
					while (it.hasNext()) {
						it.next().unload();
					}
				}
			};
			unloadThread.start();
		}

		return built;
	}

	/**
	 * Build the given file.
	 * 
	 * @param file
	 *            The file to build.
	 * @param fileCausingTheBuild
	 *            The file causing the build (if A needs B, building A trigger the build of B with A as the
	 *            fileCausingTheBuild).
	 * @param monitor
	 *            The monitor.
	 * @return The files built.
	 */
	private Set<File> build(File file, File fileCausingTheBuild, Monitor monitor) {
		this.dependingBuiltFile = fileCausingTheBuild;
		return this.build(file, monitor);
	}

	/**
	 * Build the given file.
	 * 
	 * @param file
	 *            The file to build.
	 * @param monitor
	 *            the monitor.
	 * @return The files built.
	 */
	private Set<File> build(File file, Monitor monitor) {
		for (IParserListener listener : this.listeners) {
			listener.startBuild(file);
		}

		monitor.subTask(AcceleoParserMessages
				.getString("AcceleoParser.StartingBuild", file.getAbsolutePath())); //$NON-NLS-1$

		Set<File> filesBuilt = new LinkedHashSet<File>();
		Set<AcceleoProject> dependingAcceleoProjects = this.acceleoProject.getProjectDependencies();

		monitor.subTask(AcceleoParserMessages.getString("AcceleoParser.ParseFileCST", file.getAbsolutePath())); //$NON-NLS-1$
		AcceleoFile acceleoFile = new AcceleoFile(file, this.acceleoProject.getModuleQualifiedName(file));
		// Compute the imported / extended modules
		AcceleoSourceBuffer acceleoSourceBuffer = new AcceleoSourceBuffer(acceleoFile);
		acceleoSourceBuffer.createCST();
		org.eclipse.acceleo.parser.cst.Module module = acceleoSourceBuffer.getCST();
		monitor.worked(1);

		boolean isImpactingBuild = false;
		List<String> signature = Collections.emptyList();
		File outputFile = this.acceleoProject.getOutputFile(file);
		if (outputFile != null && !outputFile.exists()) {
			isImpactingBuild = true;
		}
		if (outputFile != null && !outputFile.exists() && file.lastModified() < outputFile.lastModified()) {
			isImpactingBuild = false;
		} else {
			ResourceSet resourceSetTMP = new AcceleoResourceSetImpl();
			AcceleoParserUtils.registerLibraries(resourceSetTMP);
			AcceleoParserUtils.registerResourceFactories(resourceSetTMP);
			AcceleoParserUtils.registerPackages(resourceSetTMP);
			signature = AcceleoParserSignatureUtils.signature(outputFile, resourceSetTMP);

			List<String> newSignature = AcceleoParserSignatureUtils.signature(module);
			isImpactingBuild = isImpactingBuild || !signature.equals(newSignature);
		}

		if (outputFile == null || monitor.isCanceled()) {
			return filesBuilt;
		}

		List<org.eclipse.acceleo.parser.cst.ModuleImportsValue> importedModules = module.getImports();
		List<org.eclipse.acceleo.parser.cst.ModuleExtendsValue> extendedModules = module.getExtends();
		Set<String> moduleDependencies = new LinkedHashSet<String>();
		for (ModuleImportsValue moduleImportsValue : importedModules) {
			moduleDependencies.add(moduleImportsValue.getName());
		}
		for (ModuleExtendsValue moduleExtendsValue : extendedModules) {
			moduleDependencies.add(moduleExtendsValue.getName());
		}

		// Find the depending modules
		Set<URI> dependingModulesURI = new LinkedHashSet<URI>(moduleDependencies.size());
		Set<File> dependingModulesFiles = new LinkedHashSet<File>(moduleDependencies.size());

		// Compute the signature of the previous compilation
		AcceleoParserSignatureUtils.signature(file, new ResourceSetImpl());

		monitor.subTask(AcceleoParserMessages.getString(
				"AcceleoParser.ResolvingDependencies", file.getAbsolutePath())); //$NON-NLS-1$

		// Check if they are already built and if they are not, do so
		for (String moduleDependency : moduleDependencies) {
			boolean found = false;
			File acceleoModule = this.acceleoProject.getFileDependency(moduleDependency);
			if (acceleoModule != null && !monitor.isCanceled()) {
				int originalSize = dependingModulesFiles.size();
				dependingModulesFiles = computeModuleFileDependency(file, filesBuilt, dependingModulesFiles,
						acceleoModule, monitor);
				found = dependingModulesFiles.size() > originalSize;
			}

			// Find the dependencies in another project
			if (!found && !monitor.isCanceled()) {
				int originalSize = dependingModulesFiles.size();
				dependingModulesFiles = computeModuleFileDependencyInOtherProjects(file, filesBuilt,
						dependingAcceleoProjects, dependingModulesFiles, moduleDependency, monitor);
				found = dependingModulesFiles.size() > originalSize;
			}

			// Find the dependencies in the jars
			if (!found && !monitor.isCanceled()) {
				URI moduleURI = this.acceleoProject.getURIDependency(moduleDependency);
				if (moduleURI != null) {
					dependingModulesURI.add(moduleURI);
					found = true;
				}
			}
		}

		if (!filesBuilt.contains(file) && !monitor.isCanceled()) {
			// Check for the main tag
			if (this.hasMainTag(acceleoSourceBuffer.getBuffer())) {
				this.mainFiles.add(file);
			}

			monitor.subTask(AcceleoParserMessages.getString(
					"AcceleoParser.ParseFileAST", file.getAbsolutePath())); //$NON-NLS-1$

			doBuild(file, filesBuilt, outputFile, acceleoSourceBuffer, dependingModulesURI,
					dependingModulesFiles, monitor);

			// If there are major changes, build the modules depending on the file
			if (isImpactingBuild) {
				filesBuilt.addAll(buildImpactedModules(file, monitor, filesBuilt));
			}

		}
		for (IParserListener listener : this.listeners) {
			listener.endBuild(file);
		}
		return filesBuilt;
	}

	/**
	 * Build the file that we are depending on in the project of the currently built file. Returns the
	 * collection of the depending modules files found.
	 * 
	 * @param file
	 *            The file that we are building
	 * @param filesBuilt
	 *            The files already built.
	 * @param dependingModulesFiles
	 *            The depending modules files.
	 * @param acceleoModule
	 *            The Acceleo module that we are depending on.
	 * @param monitor
	 *            The progress monitor.
	 * @return The collection of the modules files dependencies.
	 */
	private Set<File> computeModuleFileDependency(File file, Set<File> filesBuilt,
			Set<File> dependingModulesFiles, File acceleoModule, Monitor monitor) {
		File output = this.acceleoProject.getOutputFile(acceleoModule);
		if (output != null && !output.exists() && !this.dependenciesToBuild.contains(acceleoModule)) {
			// The depending module has not been compiled yet
			this.addDependencyToBuild(acceleoModule);
			filesBuilt.addAll(this.build(acceleoModule, file, monitor));
			filesBuilt.add(acceleoModule);
			this.removeBuiltDependency(acceleoModule);
		}
		// If cyclic dependencies, log error
		if (output != null && !output.exists() && this.dependenciesToBuild.contains(acceleoModule)) {
			this.problems.put(file, new AcceleoParserProblem(AcceleoParserMessages.getString(
					"AcceleoParser.CircularDependency", this.acceleoProject //$NON-NLS-1$
							.getModuleQualifiedName(file), this.acceleoProject
							.getModuleQualifiedName(acceleoModule)), 0, 0, 0));
		}
		if (output != null && output.exists()) {
			this.input2output.put(acceleoModule, output);
			dependingModulesFiles.add(output);
		}
		return dependingModulesFiles;
	}

	/**
	 * Compute the location of the dependency we are looking for in the other projects. Returns the collection
	 * of the depending modules files found.
	 * 
	 * @param file
	 *            The file that we are building.
	 * @param filesBuilt
	 *            The files already built.
	 * @param dependingAcceleoProjects
	 *            The depending acceleo projects.
	 * @param dependingModulesFiles
	 *            The depending modules files.
	 * @param moduleDependency
	 *            The dependency (ie: org::eclipse::acceleo::module::sample::myModule)
	 * @param monitor
	 *            The progress monitor.
	 * @return The new depending modules files (including, if found, the resolved dependency)
	 */
	private Set<File> computeModuleFileDependencyInOtherProjects(File file, Set<File> filesBuilt,
			Set<AcceleoProject> dependingAcceleoProjects, Set<File> dependingModulesFiles,
			String moduleDependency, Monitor monitor) {
		boolean found = false;
		Iterator<AcceleoProject> iterator = dependingAcceleoProjects.iterator();
		while (iterator.hasNext() && !found) {
			AcceleoProject dependingAcceleoProject = iterator.next();
			File dependingModule = dependingAcceleoProject.getFileDependency(moduleDependency);
			if (dependingModule != null) {
				File output = dependingAcceleoProject.getOutputFile(dependingModule);
				if (output != null && !output.exists() && !this.dependenciesToBuild.contains(dependingModule)) {
					AcceleoParser parser = new AcceleoParser(dependingAcceleoProject,
							this.usebinaryResources, this.usePlatformResourcePath);
					parser.addListeners(this.listeners.toArray(new IParserListener[this.listeners.size()]));
					parser.setURIHandler(this.uriHandler);
					parser.addDependencyToBuild(dependingModule);
					filesBuilt.addAll(parser.build(dependingModule, file, monitor));
					filesBuilt.add(dependingModule);
					parser.removeBuiltDependency(dependingModule);

					// Save the errors encountered
					this.problems.putAll(dependingModule, parser.getProblems(dependingModule));
					this.warnings.putAll(dependingModule, parser.getWarnings(dependingModule));
					this.infos.putAll(dependingModule, parser.getInfos(dependingModule));
				}
				output = dependingAcceleoProject.getOutputFile(dependingModule);
				if (output != null && output.exists()) {
					this.input2output.put(dependingModule, output);
					dependingModulesFiles.add(output);
					found = true;
				}
			}
		}
		return dependingModulesFiles;
	}

	/**
	 * Build the file.
	 * 
	 * @param file
	 *            The file to build.
	 * @param filesBuilt
	 *            The files already built.
	 * @param outputFile
	 *            The output file created after building the file.
	 * @param acceleoSourceBuffer
	 *            The source buffer of the file.
	 * @param dependingModulesURI
	 *            The module URI dependencies.
	 * @param dependingModulesFiles
	 *            The modules files dependencies.
	 * @param monitor
	 *            The progress monitor.
	 */
	private void doBuild(File file, Set<File> filesBuilt, File outputFile,
			AcceleoSourceBuffer acceleoSourceBuffer, Set<URI> dependingModulesURI,
			Set<File> dependingModulesFiles, Monitor monitor) {
		// If the file has been built and all its dependencies too, do nothing
		boolean shouldBuild = false;
		if (outputFile.exists() && file.lastModified() < outputFile.lastModified()) {
			Set<Entry<File, File>> entrySet = this.input2output.entrySet();
			for (Entry<File, File> entry : entrySet) {
				File input = entry.getKey();
				File output = entry.getValue();
				if (input.lastModified() > output.lastModified()
						|| output.lastModified() > outputFile.lastModified()) {
					shouldBuild = true;
					break;
				}
			}
		} else {
			shouldBuild = true;
		}

		if (!shouldBuild) {
			return;
		}

		// Create the AST
		URI fileURI = URI.createFileURI(outputFile.getAbsolutePath());
		Resource oResource = createResource(fileURI, resourceSet);
		acceleoSourceBuffer.createAST(oResource);

		monitor.worked(1);

		// Load the other modules in jars in the resource set
		for (URI uri : dependingModulesURI) {
			try {
				for (IParserListener listener : this.listeners) {
					listener.loadDependency(uri);
				}
				ModelUtils.load(uri, resourceSet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Load the other modules compiled in the resource set
		for (File dependingModule : dependingModulesFiles) {
			try {
				for (IParserListener listener : this.listeners) {
					listener.loadDependency(dependingModule);
				}
				if (EMFPlugin.IS_ECLIPSE_RUNNING && this.usePlatformResourcePath) {
					AcceleoProject dependingModuleAcceleoProject = null;
					if (this.acceleoProject.getAllCompiledAcceleoModules().contains(dependingModule)) {
						dependingModuleAcceleoProject = this.acceleoProject;
					} else {
						// Look in project dependencies
						for (AcceleoProject anAcceleoProject : this.acceleoProject.getProjectDependencies()) {
							if (anAcceleoProject.getAllCompiledAcceleoModules().contains(dependingModule)) {
								dependingModuleAcceleoProject = anAcceleoProject;
								break;
							}
						}
					}

					if (dependingModuleAcceleoProject != null) {
						URI dependingModuleURI = URI.createPlatformResourceURI(dependingModuleAcceleoProject
								.getProjectRoot().getName()
								+ dependingModule.getAbsolutePath().substring(
										dependingModuleAcceleoProject.getProjectRoot().getAbsolutePath()
												.length()), true);
						ModelUtils.load(dependingModuleURI, resourceSet);
					}
				} else {
					ModelUtils.load(dependingModule, resourceSet);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// Resolve the AST
		acceleoSourceBuffer.resolveAST();
		acceleoSourceBuffer.resolveASTDocumentation();

		// Convert the uris if necessary
		for (Resource resource : resourceSet.getResources()) {
			URI resourceURI = resource.getURI();
			if (this.uriHandler != null && resourceURI != null) {
				URI reusableURI = this.uriHandler.transform(resourceURI);
				if (reusableURI != null) {
					resource.setURI(reusableURI);
				}
			}
		}

		// Trim the modules built
		AcceleoParserTrimUtils.getInstance().trimEnvironment(resourceSet);

		// Save the file
		Map<String, String> options = new HashMap<String, String>();
		if (!this.usebinaryResources) {
			String encoding = acceleoSourceBuffer.getEncoding();
			if (encoding == null) {
				encoding = "UTF-8"; //$NON-NLS-1$
			}
			options.put(XMLResource.OPTION_ENCODING, encoding);
		}

		// Check OMG Compilance
		if (this.checkOMGCompliance && oResource.getContents().size() > 0) {
			checkOMGCompilance(file, acceleoSourceBuffer, oResource);
		}

		monitor.subTask(AcceleoParserMessages.getString("AcceleoParser.SaveAST", file.getAbsolutePath())); //$NON-NLS-1$

		try {
			for (IParserListener listener : this.listeners) {
				listener.fileSaved(file);
			}
			
			oResource.save(options);
			monitor.worked(10);
			filesBuilt.add(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Saving the errors encountered
		this.problems.putAll(file, acceleoSourceBuffer.getProblems().getList());
		this.warnings.putAll(file, acceleoSourceBuffer.getWarnings().getList());
		this.infos.putAll(file, acceleoSourceBuffer.getInfos().getList());
	}

	/**
	 * Checks if the file has a main tag.
	 * 
	 * @param contents
	 *            The contents of the file.
	 * @return <code>true</code> if there is a main tag, <code>false</code> otherwise.
	 */
	private boolean hasMainTag(StringBuffer contents) {
		Sequence pattern = new Sequence(IAcceleoConstants.TAG_MAIN);
		if (pattern.search(contents).b() > -1) {
			return true;
		}
		return false;
	}

	/**
	 * Checks the compilance with the OMG specification.
	 * 
	 * @param file
	 *            The file currently built.
	 * @param acceleoSourceBuffer
	 *            The source buffer used.
	 * @param oResource
	 *            The resource containing the AST.
	 */
	private void checkOMGCompilance(File file, AcceleoSourceBuffer acceleoSourceBuffer, Resource oResource) {
		EObject eObject = oResource.getContents().get(0);
		TreeIterator<EObject> oAllContents = eObject.eAllContents();
		while (oAllContents.hasNext()) {
			EObject oNext = oAllContents.next();
			if (oNext instanceof OperationCallExp) {
				OperationCallExp oOperationCallExp = (OperationCallExp)oNext;
				if (oOperationCallExp.getReferredOperation() != null
						&& oOperationCallExp.getReferredOperation().getEAnnotation("MTL non-standard") != null) { //$NON-NLS-1$
					String message = AcceleoParserMessages.getString(
							"AcceleoParser.NotFullyCompliant", oOperationCallExp //$NON-NLS-1$
									.getReferredOperation().getName());
					int line = acceleoSourceBuffer.getLineOfOffset(oOperationCallExp.getStartPosition());
					int posBegin = oOperationCallExp.getStartPosition();
					int posEnd = oOperationCallExp.getEndPosition();
					AcceleoParserProblem problem = new AcceleoParserProblem(message, line, posBegin, posEnd);
					this.problems.put(file, problem);
				}
			}
		}
	}

	/**
	 * Build the Acceleo modules depending on the given file that may have been impacted by the build of the
	 * file.
	 * 
	 * @param file
	 *            The built file.
	 * @param monitor
	 *            The monitor
	 * @param filesBuilt
	 *            The set of files already built.
	 * @return The set of files built (includes filesBuilt).
	 */
	private Set<File> buildImpactedModules(File file, Monitor monitor, Set<File> filesBuilt) {
		Set<File> filesToBuild = Sets.newLinkedHashSet();
		if (!monitor.isCanceled()) {
			// Compute the files that are depending on "file".
			List<Sequence> sequencesToSearch = AcceleoParserUtils.getImportSequencesToSearch(
					this.acceleoProject, file);
			filesToBuild = this.acceleoProject.getFilesDependingOn(sequencesToSearch);

			Set<File> fileBuiltByPropagation = new LinkedHashSet<File>();
			for (File fileToBuild : filesToBuild) {
				if (!fileToBuild.equals(dependingBuiltFile) && !fileBuiltByPropagation.contains(fileToBuild)
						&& !monitor.isCanceled()) {
					fileBuiltByPropagation.clear();
					this.clearDependencyToBuild();
					filesBuilt.addAll(this.build(fileToBuild, monitor));
					filesBuilt.add(fileToBuild);

					fileBuiltByPropagation.addAll(filesBuilt);
				}
			}

			filesToBuild = new LinkedHashSet<File>();

			// Same thing but for the dependent projects
			for (AcceleoProject dependentAcceleoProject : this.acceleoProject.getDependentProjects()) {
				filesToBuild.addAll(dependentAcceleoProject.getFilesDependingOn(sequencesToSearch));
				fileBuiltByPropagation = new LinkedHashSet<File>();
				for (File fileToBuild : filesToBuild) {
					if (!fileToBuild.equals(dependingBuiltFile)
							&& !fileBuiltByPropagation.contains(fileToBuild) && !monitor.isCanceled()) {
						fileBuiltByPropagation.clear();

						AcceleoParser parser = new AcceleoParser(dependentAcceleoProject,
								this.usebinaryResources, this.usePlatformResourcePath);
						parser.addListeners(this.listeners
								.toArray(new IParserListener[this.listeners.size()]));
						parser.setURIHandler(this.uriHandler);
						parser.clearDependencyToBuild();
						filesBuilt.addAll(parser.build(fileToBuild, monitor));
						filesBuilt.add(fileToBuild);

						fileBuiltByPropagation.addAll(filesBuilt);
					}
				}
			}

		}

		return filesBuilt;
	}

	/**
	 * Create the resource that will be used for the serialization.
	 * 
	 * @param oURI
	 *            The URI of the resource.
	 * @param oResourceSet
	 *            The resource set
	 * @return The resource that will be used for the serialization.
	 */
	private Resource createResource(URI oURI, ResourceSet oResourceSet) {
		if (this.usebinaryResources) {
			return ModelUtils.createBinaryResource(oURI, oResourceSet);
		}
		return ModelUtils.createResource(oURI, oResourceSet);
	}

	/**
	 * Returns the problems found for the given file.
	 * 
	 * @param file
	 *            The acceleo module.
	 * @return The problems found for the given file.
	 */
	public Collection<AcceleoParserProblem> getProblems(File file) {
		return Collections.unmodifiableCollection(this.problems.get(file));
	}

	/**
	 * Returns the warnings found for the given file.
	 * 
	 * @param file
	 *            The acceleo module.
	 * @return The warnings found for the given file.
	 */
	public Collection<AcceleoParserWarning> getWarnings(File file) {
		return Collections.unmodifiableCollection(this.warnings.get(file));
	}

	/**
	 * Returns the information found for the given file.
	 * 
	 * @param file
	 *            The acceleo module.
	 * @return The information found for the given file.
	 */
	public Collection<AcceleoParserInfo> getInfos(File file) {
		return Collections.unmodifiableCollection(this.infos.get(file));
	}
}
