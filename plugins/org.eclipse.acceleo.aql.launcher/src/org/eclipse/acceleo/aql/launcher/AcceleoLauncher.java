/*******************************************************************************
 * Copyright (c) 2020, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.launcher;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.evaluation.GenerationResult;
import org.eclipse.acceleo.aql.evaluation.strategy.DefaultGenerationStrategy;
import org.eclipse.acceleo.aql.evaluation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.aql.ide.evaluation.strategy.AcceleoWorkspaceWriterFactory;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.parser.ModuleLoader;
import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.ast.ASTNode;
import org.eclipse.acceleo.query.ide.QueryPlugin;
import org.eclipse.acceleo.query.ide.runtime.impl.namespace.OSGiQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.BasicMonitor.Printing;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;
import org.kohsuke.args4j.spi.StringOptionHandler;
import org.osgi.framework.Bundle;

/**
 * Application class for the Acceleo Launcher.
 * <p>
 * Parses the arguments and starts the specified Acceleo generation.
 * </p>
 * 
 * @author lgoubet
 */
public class AcceleoLauncher implements IApplication {

	/** Return code for the application in case of error. */
	private static final Integer APPLICATION_ERROR = Integer.valueOf(-1);

	/**
	 * Input models for the generation. Cannot be empty.
	 */
	@Option(name = "-models", usage = "Specifies the input models for this generation.", metaVar = "INPUT", handler = StringArrayOptionHandler.class, required = true)
	private String[] models = new String[0];

	/**
	 * Identifier of the bundle containing the main module for this generation.
	 */
	@Option(name = "-bundle", usage = "The identifier of the bundle containing the main module of the generation to start.", metaVar = "BUNDLE", handler = StringOptionHandler.class, required = true)
	private String bundleIdentifier;

	/**
	 * Input module for the generation. This needs to the qualified name of the module containing an
	 * "@main"-annotated template.
	 */
	@Option(name = "-module", usage = "Specifies the module which main template will be executed.", metaVar = "MODULE", handler = StringOptionHandler.class, required = true)
	private String moduleQualifiedName;

	/**
	 * The destination URI for this generation. Generated files will use this folder as their root to resolve
	 * against.
	 */
	@Option(name = "-target", usage = "Specifies the destination for the generation.", metaVar = "TARGET", handler = StringOptionHandler.class, required = true)
	private String target;

	/**
	 * Workspace location. This argument is here only to mimic the OSGi applications common arguments so that
	 * they are displayed in usage.
	 */
	@Option(name = "-data", usage = "Specify the folder which will keep the workspace.", metaVar = "FOLDER")
	private File dataFolder;

	/**
	 * consoleLog. This argument is here only to mimic the OSGi applications common arguments so that they are
	 * displayed in usage.
	 */
	@Option(name = "-consoleLog", usage = "Log messages in the console.")
	private boolean consoleLog;

	/**
	 * Output log URI.
	 */
	@Option(name = "-log", usage = "Specifies the module which main template will be executed.", metaVar = "LOG", handler = StringOptionHandler.class, required = false)
	private String log;

	/** List of URIs parsed from the {@link #models} argument. */
	private List<URI> modelURIs;

	/** Bundle containing our main module for this generation. */
	private Bundle bundle;

	/**
	 * The target {@link URI}.
	 */
	private URI targetURI;

	/**
	 * The log {@link URI} if any, <code>null</code> otherwise.
	 */
	private URI logURI;

	@Override
	public Object start(IApplicationContext context) throws Exception {
		String[] args = (String[])context.getArguments().get(IApplicationContext.APPLICATION_ARGS);
		return new AcceleoLauncher().doMain(args);
	}

	/**
	 * Main logic for this launcher.
	 * 
	 * @param args
	 *            Application parameters passed by the Equinox framework.
	 * @return The return value of this application.
	 */
	public Object doMain(String[] args) {
		CmdLineParser parser = new CmdLineParser(this);

		Object applicationResult = IApplication.EXIT_OK;
		try {
			parser.parseArgument(args);
			validateArguments(parser);
			GenerationResult result = launchGeneration();
			if (result.getDiagnostic().getSeverity() > Diagnostic.INFO) {
				PrintStream stream;
				switch (result.getDiagnostic().getSeverity()) {
					case Diagnostic.WARNING:
						stream = System.out;
						stream.println("WARNING");
						break;
					case Diagnostic.ERROR:
						// Fall-through
					default:
						// Shouldn't happen as we only show warnings and errors
						stream = System.err;
						stream.println("ERROR");
						applicationResult = APPLICATION_ERROR;
						break;
				}
				if (consoleLog) {
					printDiagnostic(stream, result.getDiagnostic(), "");
					printSummary(System.out, result);
				}
			}
			Set<URI> generatedFiles = result.getGeneratedFiles();
			System.out.println("Generated " + generatedFiles.size() + " in " + target);
			if (result.getGeneratedFiles().size() <= 10) {
				for (URI generated : result.getGeneratedFiles()) {
					System.out.println(generated);
				}
			}
		} catch (CmdLineException e) {
			parser.printUsage(System.err);
			System.err.println();
			AcceleoLauncherPlugin.INSTANCE.log(e);
			applicationResult = APPLICATION_ERROR;
		}

		return applicationResult;
	}

	private void validateArguments(CmdLineParser parser) throws CmdLineException {
		if (models.length == 0 || models[0].length() == 0) {
			throw new CmdLineException(parser, "The input model path cannot be empty.");
		}
		if (bundleIdentifier.length() == 0) {
			throw new CmdLineException(parser,
					"The main module's containing bundle identifier cannot be empty.");
		}
		if (moduleQualifiedName.length() == 0) {
			throw new CmdLineException(parser, "The main module's qualified name cannot be empty.");
		}
		if (target.length() == 0) {
			throw new CmdLineException(parser, "The target folder path cannot be empty.");
		}

		modelURIs = convertToURIs(parser, models);
		bundle = Platform.getBundle(bundleIdentifier);

		if (bundle == null || bundle.getState() == Bundle.UNINSTALLED) {
			throw new CmdLineException(parser, "The Bundle " + bundleIdentifier
					+ " must be available in the target platform.");
		}
		try {
			targetURI = URI.createURI(target);
		} catch (IllegalArgumentException e) {
			throw new CmdLineException(parser, e);
		}

		try {
			logURI = AcceleoUtil.getlogURI(targetURI, log);
		} catch (IllegalArgumentException e) {
			throw new CmdLineException(parser, e);
		}
	}

	private List<URI> convertToURIs(CmdLineParser parser, String... paths) throws CmdLineException {
		List<URI> uris = new ArrayList<>();
		for (String path : paths) {
			URI pathURI = null;
			try {
				pathURI = URI.createURI(path, true);
			} catch (IllegalArgumentException e) {
				throw new CmdLineException(parser, e);
			}
			if (pathURI != null && !pathURI.hasAbsolutePath()) {
				pathURI = URI.createFileURI(path);
			}
			if (pathURI != null) {
				uris.add(pathURI);
			}
		}
		return uris;
	}

	private GenerationResult launchGeneration() {
		IQualifiedNameResolver resolver = new OSGiQualifiedNameResolver(bundle,
				AcceleoParser.QUALIFIER_SEPARATOR);

		// TODO get options form the launch configuration
		final Map<String, String> options = new LinkedHashMap<>();
		final ArrayList<Exception> exceptions = new ArrayList<>();
		final ResourceSet resourceSetForModels = AQLUtils.createResourceSetForModels(exceptions, resolver,
				new ResourceSetImpl(), options);
		// TODO report exceptions
		for (URI modelURI : modelURIs) {
			resourceSetForModels.getResource(modelURI, true);
		}

		final IQualifiedNameQueryEnvironment queryEnvironment = AcceleoUtil.newAcceleoQueryEnvironment(
				options, resolver, resourceSetForModels, false);

		try {
			final String newLine = options.getOrDefault(AcceleoUtil.NEW_LINE_OPTION, System.lineSeparator());
			AcceleoEvaluator evaluator = new AcceleoEvaluator(queryEnvironment.getLookupEngine(), newLine);

			resolver.addLoader(new ModuleLoader(new AcceleoParser(), evaluator));
			resolver.addLoader(QueryPlugin.getPlugin().createJavaLoader(AcceleoParser.QUALIFIER_SEPARATOR,
					false));

			final Object resolved = resolver.resolve(moduleQualifiedName);
			final Module mainModule;
			if (resolved instanceof Module) {
				mainModule = (Module)resolved;
				AcceleoUtil.registerEPackage(queryEnvironment, resolver, mainModule);
			} else {
				mainModule = null;
			}

			evaluate(evaluator, queryEnvironment, mainModule, resourceSetForModels, targetURI, logURI);
			return evaluator.getGenerationResult();
		} finally {
			AQLUtils.cleanResourceSetForModels(resolver, resourceSetForModels);
			AcceleoUtil.cleanServices(queryEnvironment, resourceSetForModels);
		}
	}

	private void evaluate(AcceleoEvaluator evaluator, IQualifiedNameQueryEnvironment queryEnvironment,
			Module mainModule, ResourceSet modelResourceSet, URI targetURI, URI logURI) {
		final IAcceleoGenerationStrategy strategy = new DefaultGenerationStrategy(modelResourceSet
				.getURIConverter(), new AcceleoWorkspaceWriterFactory());
		final Monitor monitor = new Printing(new PrintStream(System.out));
		AcceleoUtil.generate(evaluator, queryEnvironment, mainModule, modelResourceSet, strategy, targetURI,
				logURI, monitor);
	}

	private void printDiagnostic(PrintStream stream, Diagnostic diagnostic, String indentation) {
		String nextIndentation = indentation;
		if (diagnostic.getMessage() != null) {
			stream.print(indentation);
			switch (diagnostic.getSeverity()) {
				case Diagnostic.INFO:
					stream.print("INFO ");
					break;

				case Diagnostic.WARNING:
					stream.print("WARNING ");
					break;

				case Diagnostic.ERROR:
					stream.print("ERROR ");
					break;
			}
			if (!diagnostic.getData().isEmpty() && diagnostic.getData().get(0) instanceof ASTNode) {
				stream.print(AcceleoUtil.getLocation((ASTNode)diagnostic.getData().get(0)));
			}
			stream.println(": " + diagnostic.getMessage());
			nextIndentation += "\t";
		}
		for (Diagnostic child : diagnostic.getChildren()) {
			printDiagnostic(stream, child, nextIndentation);
		}
	}

	private void printSummary(PrintStream stream, GenerationResult result) {
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

		stream.print("Files: " + result.getGeneratedFiles().size());
		stream.print(", Lost Files: " + result.getLostFiles().size());
		stream.print(", Errors: " + nbErrors);
		stream.print(", Warnings: " + nbWarnings);
		stream.print(", Infos: " + nbInfos);
		stream.println(".");
	}

	@Override
	public void stop() {
		// Nothing to do on application stop
	}

}
