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

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.evaluation.GenerationResult;
import org.eclipse.acceleo.aql.evaluation.strategy.DefaultGenerationStrategy;
import org.eclipse.acceleo.aql.evaluation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.aql.ide.evaluation.strategy.AcceleoWorkspaceWriterFactory;
import org.eclipse.acceleo.aql.ide.ui.Activator;
import org.eclipse.acceleo.aql.ide.ui.module.services.Services;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.parser.ModuleLoader;
import org.eclipse.acceleo.query.ide.QueryPlugin;
import org.eclipse.acceleo.query.runtime.impl.namespace.ClassLoaderQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.impl.namespace.JavaLoader;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * Eclipse launcher for org::eclipse::python4capella::ecore::gen::python::main::standalone.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class StandaloneGenerator {

	/**
	 * The main {@link Module} {@link IFile}.
	 */
	private final IFile moduleFile;

	/**
	 * Constructor.
	 * 
	 * @param moduleFile
	 *            the module {@link IFile}
	 */
	public StandaloneGenerator(IFile moduleFile) {
		this.moduleFile = moduleFile;
	}

	/**
	 * Registers the given {@link EPackage} in the given {@link IQualifiedNameQueryEnvironment} recursively.
	 * 
	 * @param environment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 * @param ePackage
	 *            the {@link EPackage}
	 */
	private static void registerEPackage(IQualifiedNameQueryEnvironment environment, EPackage ePackage) {
		environment.registerEPackage(ePackage);
		for (EPackage child : ePackage.getESubpackages()) {
			registerEPackage(environment, child);
		}
	}

	/**
	 * Generates.
	 */
	public void generate() {
		// inputs
		final String moduleQualifiedName = getModuleQualifiedName();
		final URI targetURI = URI.createFileURI(moduleFile.getParent().getLocation().toFile()
				.getAbsolutePath() + "/");
		final Map<String, String> options = getOptions();

		// create the resource set used to load models (not used in this case)
		final List<Exception> exceptions = new ArrayList<>();
		final ResourceSet resourceSetForModels = new ResourceSetImpl();

		// prepare Acceleo environment
		final IQualifiedNameResolver resolver = createResolver();
		final IQualifiedNameQueryEnvironment queryEnvironment = createAcceleoQueryEnvironment(options,
				resolver, resourceSetForModels);
		AcceleoEvaluator evaluator = createAcceleoEvaluator(resolver, queryEnvironment);
		final IAcceleoGenerationStrategy strategy = createGenerationStrategy(resourceSetForModels);

		final Module module = (Module)resolver.resolve(moduleQualifiedName);
		final URI logURI = AcceleoUtil.getlogURI(targetURI, options.get(AcceleoUtil.LOG_URI_OPTION));

		final IQualifiedNameResolver workspaceResolver = QueryPlugin.getPlugin().createQualifiedNameResolver(
				getClass().getClassLoader(), moduleFile.getProject(), AcceleoParser.QUALIFIER_SEPARATOR,
				true);
		final java.net.URI binaryURI = workspaceResolver.getBinaryURI(moduleFile.getLocation().toFile()
				.toURI());
		workspaceResolver.addLoader(new ModuleLoader(new AcceleoParser(), null));
		final String modelModuleQualifiedName = workspaceResolver.getQualifiedName(binaryURI);
		final Module modelModule = (Module)workspaceResolver.resolve(modelModuleQualifiedName);

		synchronized(this) {
			beforeGeneration(queryEnvironment, workspaceResolver);
			try {
				AcceleoUtil.generate(evaluator, queryEnvironment, module, modelModule.eResource(), strategy,
						targetURI, logURI);
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
		return "org::eclipse::acceleo::aql::ide::ui::module::main::standalone";
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
			final IQualifiedNameResolver resolver, ResourceSet resourceSetForModels) {
		final IQualifiedNameQueryEnvironment queryEnvironment = AcceleoUtil.newAcceleoQueryEnvironment(
				options, resolver, resourceSetForModels, false);
		for (String nsURI : new ArrayList<String>(EPackage.Registry.INSTANCE.keySet())) {
			registerEPackage(queryEnvironment, EPackage.Registry.INSTANCE.getEPackage(nsURI));
		}
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
					Activator.getDefault().getLog().log(new Status(IStatus.INFO, diagnostic.getSource(),
							diagnostic.getMessage(), diagnostic.getException()));
					break;

				case Diagnostic.WARNING:
					Activator.getDefault().getLog().log(new Status(IStatus.WARNING, diagnostic.getSource(),
							diagnostic.getMessage(), diagnostic.getException()));
					break;

				case Diagnostic.ERROR:
					Activator.getDefault().getLog().log(new Status(IStatus.ERROR, diagnostic.getSource(),
							diagnostic.getMessage(), diagnostic.getException()));
					break;
			}
		}
		for (Diagnostic child : diagnostic.getChildren()) {
			printDiagnostic(child);
		}
	}

	/**
	 * After the generation finished.
	 * 
	 * @param generationResult
	 *            the {@link GenerationResult}
	 */
	protected void afterGeneration(GenerationResult generationResult) {
		Services.initialize(null, null);
		try {
			moduleFile.getParent().refreshLocal(IResource.DEPTH_ONE, new NullProgressMonitor());
		} catch (CoreException e) {
			Activator.getDefault().getLog().log(new Status(IStatus.ERROR, getClass(), "could not refresh "
					+ moduleFile.getParent().getFullPath(), e));
		}
	}

}
