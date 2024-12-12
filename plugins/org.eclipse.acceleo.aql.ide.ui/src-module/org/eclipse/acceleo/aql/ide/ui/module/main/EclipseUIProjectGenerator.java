/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui.module.main;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.evaluation.GenerationResult;
import org.eclipse.acceleo.aql.evaluation.strategy.DefaultGenerationStrategy;
import org.eclipse.acceleo.aql.evaluation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.aql.ide.evaluation.strategy.AcceleoWorkspaceWriterFactory;
import org.eclipse.acceleo.aql.ide.ui.AcceleoUIPlugin;
import org.eclipse.acceleo.aql.ide.ui.module.services.Services;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.parser.ModuleLoader;
import org.eclipse.acceleo.query.ide.QueryPlugin;
import org.eclipse.acceleo.query.runtime.impl.namespace.ClassLoaderQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.impl.namespace.JavaLoader;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * Eclipse launcher for org::eclipse::python4capella::ecore::gen::python::main::eclipseUIProject.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class EclipseUIProjectGenerator extends AbstractGenerator {

	/**
	 * The {@link List} of module {@link IFile} for the {@link IProject}.
	 */
	private final List<IFile> projectModuleFiles;

	/**
	 * The Eclipse project UI name.
	 */
	private final String projectUIName;

	/**
	 * The absolute path to the destination folder.
	 */
	private final String destinationFolder;

	/**
	 * The {@link Set} of model {@link Module} parameter bundle dependencies.
	 */
	protected Set<String> dependencyBundleNames;

	/**
	 * Constructor.
	 * 
	 * @param projectModuleFiles
	 *            the {@link List} of module {@link IFile} for the {@link IProject}.
	 */
	public EclipseUIProjectGenerator(List<IFile> projectModuleFiles) {
		this.projectModuleFiles = projectModuleFiles;
		this.projectUIName = projectModuleFiles.get(0).getProject().getName() + ".ide.ui";
		this.destinationFolder = projectModuleFiles.get(0).getProject().getLocation().toFile().getParentFile()
				.getAbsolutePath();
	}

	/**
	 * Generates.
	 * 
	 * @param monitor
	 *            the progress {@link Monitor}
	 */
	public void generate(Monitor monitor) {

		// inputs
		final String moduleQualifiedName = getModuleQualifiedName();
		final URI targetURI = URI.createFileURI(destinationFolder + "/");
		final Map<String, String> options = getOptions();

		// create the resource set used to load models (not used in this case)
		final List<Exception> exceptions = new ArrayList<>();
		final ResourceSet resourceSetForModels = new ResourceSetImpl();

		// prepare Acceleo environment
		final IQualifiedNameResolver resolver = createResolver();
		final IQualifiedNameQueryEnvironment queryEnvironment = createAcceleoQueryEnvironment(options,
				resolver, resourceSetForModels);
		final AcceleoEvaluator evaluator = createAcceleoEvaluator(resolver, queryEnvironment);
		final IAcceleoGenerationStrategy strategy = createGenerationStrategy(resourceSetForModels);

		final Module module = (Module)resolver.resolve(moduleQualifiedName);
		AcceleoUtil.registerEPackage(queryEnvironment, resolver, module);
		final Template main = AcceleoUtil.getMainTemplates(module).iterator().next();
		final URI logURI = AcceleoUtil.getlogURI(targetURI, options.get(AcceleoUtil.LOG_URI_OPTION));

		final IQualifiedNameResolver workspaceResolver = QueryPlugin.getPlugin().createQualifiedNameResolver(
				getClass().getClassLoader(), projectModuleFiles.get(0).getProject(),
				AcceleoParser.QUALIFIER_SEPARATOR, true);

		final List<Module> modelModules = new ArrayList<>();
		for (IFile file : projectModuleFiles) {
			final java.net.URI binaryURI = workspaceResolver.getBinaryURI(file.getLocation().toFile()
					.toURI());
			workspaceResolver.addLoader(new ModuleLoader(new AcceleoParser(), null));
			final String modelModuleQualifiedName = workspaceResolver.getQualifiedName(binaryURI);
			final Module modelModule = (Module)workspaceResolver.resolve(modelModuleQualifiedName);
			modelModules.add(modelModule);
		}
		// We register model modules EPackage for type resolution
		for (Module modelModule : modelModules) {
			AcceleoUtil.registerEPackage(queryEnvironment, resolver, modelModule);
		}
		dependencyBundleNames = new LinkedHashSet<>();
		for (Module modelModule : modelModules) {
			dependencyBundleNames.addAll(getDependencyBundleNames(queryEnvironment, modelModule));
		}
		synchronized(this) {
			beforeGeneration(queryEnvironment, workspaceResolver);
			try {
				Map<String, Object> variables = new LinkedHashMap<>();
				variables.put(main.getParameters().get(0).getName(), modelModules);
				variables.put(main.getParameters().get(1).getName(), projectUIName);
				AcceleoUtil.generate(main, variables, evaluator, queryEnvironment, strategy, targetURI,
						logURI, monitor);
			} finally {
				AcceleoUtil.cleanServices(queryEnvironment, resourceSetForModels);
				printDiagnostics(evaluator.getGenerationResult());
				afterGeneration(evaluator.getGenerationResult());
			}
		}
	}

	/**
	 * Gets the module qualified name.
	 * 
	 * @return the module qualified name
	 */
	protected String getModuleQualifiedName() {
		return "org::eclipse::acceleo::aql::ide::ui::module::main::eclipseUIProject";
	}

	/**
	 * Gets the {@link Map} of options for the generation.
	 * 
	 * @return the {@link Map} of options for the generation
	 */
	protected Map<String, String> getOptions() {
		Map<String, String> res = new LinkedHashMap<>();

		res.put(AcceleoUtil.LOG_URI_OPTION, "acceleo.log");
		res.put(AcceleoUtil.NEW_LINE_OPTION, System.lineSeparator());

		return res;
	}

	/**
	 * Creates the {@link IQualifiedNameResolver}.
	 * 
	 * @return the created {@link IQualifiedNameResolver}
	 */
	protected IQualifiedNameResolver createResolver() {
		final IQualifiedNameResolver resolver = new ClassLoaderQualifiedNameResolver(this.getClass()
				.getClassLoader(), AcceleoParser.QUALIFIER_SEPARATOR);
		return resolver;
	}

	/**
	 * Creates the {@link IQualifiedNameQueryEnvironment}.
	 * 
	 * @param options
	 *            the {@link Map} of options
	 * @param resolver
	 *            the {@link IQualifiedNameResolver}
	 * @param resourceSetForModels
	 *            the {@link ResourceSet} for models
	 * @return the created {@link IQualifiedNameQueryEnvironment}
	 */
	protected IQualifiedNameQueryEnvironment createAcceleoQueryEnvironment(Map<String, String> options,
			IQualifiedNameResolver resolver, ResourceSet resourceSetForModels) {
		final IQualifiedNameQueryEnvironment queryEnvironment = AcceleoUtil.newAcceleoQueryEnvironment(
				options, resolver, resourceSetForModels, false);

		return queryEnvironment;
	}

	/**
	 * Creates the {@link AcceleoEvaluator}
	 * 
	 * @param resolver
	 *            the {@link IQualifiedNameResolver}
	 * @param queryEnvironment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 * @return the created {@link AcceleoEvaluator}
	 */
	protected AcceleoEvaluator createAcceleoEvaluator(final IQualifiedNameResolver resolver,
			IQualifiedNameQueryEnvironment queryEnvironment) {
		AcceleoEvaluator evaluator = new AcceleoEvaluator(queryEnvironment.getLookupEngine(), System
				.lineSeparator());
		resolver.addLoader(new ModuleLoader(new AcceleoParser(), evaluator));
		resolver.addLoader(new JavaLoader(AcceleoParser.QUALIFIER_SEPARATOR, false));
		return evaluator;
	}

	protected IAcceleoGenerationStrategy createGenerationStrategy(final ResourceSet resourceSetForModels) {
		final IAcceleoGenerationStrategy strategy = new DefaultGenerationStrategy(resourceSetForModels
				.getURIConverter(), new AcceleoWorkspaceWriterFactory());
		return strategy;
	}

	/**
	 * Before the generation starts.
	 * 
	 * @param queryEnvironment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 * @param workspaceResolver
	 *            the workspace {@link IQualifiedNameResolver}
	 */
	protected void beforeGeneration(IQualifiedNameQueryEnvironment queryEnvironment,
			IQualifiedNameResolver workspaceResolver) {
		Services.initialize(queryEnvironment, workspaceResolver);
	}

	/**
	 * Prints the diagnostics for the given {@link GenerationResult}.
	 * 
	 * @param generationResult
	 *            the {@link GenerationResult}
	 */
	protected void printDiagnostics(GenerationResult generationResult) {
		if (generationResult.getDiagnostic().getSeverity() > Diagnostic.INFO) {
			printDiagnostic(generationResult.getDiagnostic());
		}
		printSummary(generationResult);
	}

	/**
	 * Prints the given {@link Diagnostic} for the given {@link PrintStream}.
	 * 
	 * @param stream
	 *            the {@link PrintStream}
	 * @param diagnostic
	 *            the {@link Diagnostic}
	 * @param indentation
	 *            the current indentation
	 */
	protected void printDiagnostic(Diagnostic diagnostic) {
		if (diagnostic.getMessage() != null) {
			switch (diagnostic.getSeverity()) {
				case Diagnostic.INFO:
					AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.INFO, diagnostic.getSource(),
							diagnostic.getMessage(), diagnostic.getException()));
					break;

				case Diagnostic.WARNING:
					AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.WARNING, diagnostic
							.getSource(), diagnostic.getMessage(), diagnostic.getException()));
					break;

				case Diagnostic.ERROR:
					AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, diagnostic
							.getSource(), diagnostic.getMessage(), diagnostic.getException()));
					break;
			}
		}
		for (Diagnostic child : diagnostic.getChildren()) {
			printDiagnostic(child);
		}
	}

	/**
	 * Prints the summary of the generation.
	 * 
	 * @param result
	 *            the {@link GenerationResult}
	 */
	protected void printSummary(GenerationResult result) {
		int nbErrors = 0;
		int nbWarnings = 0;
		int nbInfos = 0;
		for (Diagnostic diagnostic : result.getDiagnostic().getChildren()) {
			switch (diagnostic.getSeverity()) {
				case Diagnostic.ERROR:
					nbErrors++;
					break;

				case Diagnostic.WARNING:
					nbWarnings++;
					break;

				case Diagnostic.INFO:
					nbInfos++;
					break;

				default:
					break;
			}
		}

		final String message = "Files: " + result.getGeneratedFiles().size() + ", Lost Files: " + result
				.getLostFiles().size() + ", Errors: " + nbErrors + ", Warnings: " + nbWarnings + ", Infos: "
				+ nbInfos + ".";
		AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.INFO, getClass(), message));
	}

	/**
	 * After the generation finished.
	 * 
	 * @param generationResult
	 *            the {@link GenerationResult}
	 */
	protected void afterGeneration(GenerationResult generationResult) {
		Services.initialize(null, null);

		final IProject existingProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectUIName);
		if (existingProject != null && existingProject.exists()) {
			if (existingProject.isOpen()) {
				try {
					addPluginDependencies(existingProject, dependencyBundleNames);
					existingProject.getParent().refreshLocal(IResource.DEPTH_INFINITE,
							new NullProgressMonitor());
				} catch (CoreException e) {
					AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, getClass(),
							"could not refresh " + existingProject.getParent().getFullPath(), e));
				}
			}
		} else {
			final String projectUIAbsolutePath = destinationFolder + File.separatorChar + projectUIName;
			try {
				final String projectFilePath = projectUIAbsolutePath + File.separatorChar + ".project";
				final IProjectDescription description = ResourcesPlugin.getWorkspace().loadProjectDescription(
						new Path(projectFilePath));
				IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(description.getName());
				project.create(description, null);
				project.open(null);
				addPluginDependencies(project, dependencyBundleNames);
			} catch (CoreException e) {
				AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID,
						"couldn't import project " + projectUIAbsolutePath, e));
			}
		}
	}

}
