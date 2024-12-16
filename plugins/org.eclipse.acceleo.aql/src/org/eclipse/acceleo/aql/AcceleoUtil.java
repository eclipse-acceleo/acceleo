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
package org.eclipse.acceleo.aql;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluationCancelledException;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.evaluation.GenerationResult;
import org.eclipse.acceleo.aql.evaluation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoWriter;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.ast.ASTNode;
import org.eclipse.acceleo.query.ast.EClassifierTypeLiteral;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.services.EObjectServices;
import org.eclipse.acceleo.query.services.configurator.IServicesConfigurator;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * Utility class for Acceleo.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class AcceleoUtil {

	/**
	 * The language name for Acceleo {@link IServicesConfigurator}.
	 */
	public static final String LANGUAGE_NAME = "org.eclipse.acceleo.aql";

	/**
	 * The new line {@link String} option name.
	 */
	public static final String NEW_LINE_OPTION = "newLine";

	/**
	 * The log URI {@link URI} {@link String} option name.
	 */
	public static final String LOG_URI_OPTION = "logURI";

	/**
	 * "self".
	 */
	private static final String SELF = "self";

	/**
	 * Constructor.
	 */
	private AcceleoUtil() {
		// utility class can't be instantiated
	}

	/**
	 * Provides the name of the implicit variable in an Acceleo {@link Template} that represents the
	 * {@link Template} itself.
	 * 
	 * @return the name of the implicit variable of an Acceleo {@link Template}.
	 */
	public static String getTemplateImplicitVariableName() {
		return SELF;
	}

	/**
	 * Gets the {@link Template#isMain() main} {@link Template} of the given {@link Module}.
	 * 
	 * @param module
	 *            the {@link Module}
	 * @return the {@link Template#isMain() main} {@link Template} of the given {@link Module} if any,
	 *         <code>null</code> otherwise
	 */
	public static List<Template> getMainTemplates(Module module) {
		List<Template> res = new ArrayList<>();

		for (ModuleElement moduleElement : module.getModuleElements()) {
			if (moduleElement instanceof Template && ((Template)moduleElement).isMain()) {
				res.add((Template)moduleElement);
			}
		}

		return res;
	}

	/**
	 * Generates with the given {@link AcceleoEvaluator} and {@link IAcceleoEnvironment}.
	 * 
	 * @param evaluator
	 *            the {@link AcceleoEvaluator}
	 * @param queryEnvironment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 * @param module
	 *            the {@link Module}
	 * @param model
	 *            the {@link Resource} containing the model
	 * @param generationStrategy
	 *            the {@link IAcceleoGenerationStrategy}
	 * @param destination
	 *            destination {@link URI}
	 * @param logURI
	 *            the {@link URI} for logging if any, <code>null</code> otherwise
	 * @param monitor
	 *            the progress {@link Monitor}
	 */
	public static void generate(AcceleoEvaluator evaluator, IQualifiedNameQueryEnvironment queryEnvironment,
			Module module, Resource model, IAcceleoGenerationStrategy generationStrategy, URI destination,
			URI logURI, Monitor monitor) {
		generate(evaluator, queryEnvironment, module, Collections.singletonList(model), generationStrategy,
				destination, logURI, monitor);
	}

	/**
	 * Generates with the given {@link AcceleoEvaluator} and {@link IQueryEnvironment}.
	 * 
	 * @param evaluator
	 *            the {@link AcceleoEvaluator}
	 * @param queryEnvironment
	 *            the {@link IQueryEnvironment}
	 * @param module
	 *            the {@link Module}
	 * @param resourceSet
	 *            the {@link ResourceSet} containing the input model(s)
	 * @param generationStrategy
	 *            the {@link IAcceleoGenerationStrategy}
	 * @param destination
	 *            the destination {@link URI}
	 * @param logURI
	 *            the {@link URI} for logging if any, <code>null</code> otherwise
	 * @param monitor
	 *            the progress {@link Monitor}
	 */
	public static void generate(AcceleoEvaluator evaluator, IQualifiedNameQueryEnvironment queryEnvironment,
			Module module, ResourceSet resourceSet, IAcceleoGenerationStrategy generationStrategy,
			URI destination, URI logURI, Monitor monitor) {
		generate(evaluator, queryEnvironment, module, resourceSet.getResources(), generationStrategy,
				destination, logURI, monitor);
	}

	/**
	 * @param evaluator
	 *            the {@link AcceleoEvaluator}
	 * @param queryEnvironment
	 *            the {@link IQueryEnvironment}
	 * @param module
	 *            the {@link Module}
	 * @param resources
	 *            the {@link List} of {@link Resource} containing the input model(s)
	 * @param generationStrategy
	 *            the {@link IAcceleoGenerationStrategy}
	 * @param destination
	 *            the destination {@link URI}
	 * @param logURI
	 *            the {@link URI} for logging if any, <code>null</code> otherwise
	 * @param monitor
	 *            the progress {@link Monitor}
	 */
	public static void generate(AcceleoEvaluator evaluator, IQualifiedNameQueryEnvironment queryEnvironment,
			Module module, List<Resource> resources, IAcceleoGenerationStrategy generationStrategy,
			URI destination, URI logURI, Monitor monitor) {

		final Map<EClass, List<EObject>> valuesCache = new HashMap<>();
		final List<Template> mainTemplates = getMainTemplates(module);
		monitor.beginTask("Generating", mainTemplates.size() * (resources.size() + 1));
		try {
			for (Template main : mainTemplates) {
				monitor.subTask("Loading model elements for " + main.getName());
				// TODO more than one parameter is allowed ?
				// TODO not EClass type ?
				// TODO more than one EClass type ?
				// TODO use IType ?
				// TODO this is really quick and dirty
				final EClassifierTypeLiteral eClassifierTypeLiteral = (EClassifierTypeLiteral)main
						.getParameters().get(0).getType().getAst();
				final List<EObject> values = getValues(eClassifierTypeLiteral, queryEnvironment, resources,
						valuesCache, monitor);

				monitor.subTask("Generating for " + main.getName());
				final String parameterName = main.getParameters().get(0).getName();
				Map<String, Object> variables = new LinkedHashMap<>();
				for (EObject value : values) {
					variables.put(parameterName, value);
					generate(main, variables, evaluator, queryEnvironment, generationStrategy, destination,
							logURI, monitor);
					if (monitor.isCanceled()) {
						break;
					}
				}
				monitor.worked(1);
				if (monitor.isCanceled()) {
					break;
				}
			}
		} finally {
			monitor.done();
		}

	}

	/**
	 * Generates the given {@link Template} for the given {@link List} of {@link EObject}.
	 * 
	 * @param template
	 *            the {@link Template} to generate
	 * @param variables
	 *            the variables
	 * @param evaluator
	 *            the {@link AcceleoEvaluator}
	 * @param queryEnvironment
	 *            the {@link IQueryEnvironment}
	 * @param module
	 *            the {@link Module}
	 * @param resources
	 *            the {@link List} of {@link Resource} containing the input model(s)
	 * @param generationStrategy
	 *            the {@link IAcceleoGenerationStrategy}
	 * @param destination
	 *            the destination {@link URI}
	 * @param logURI
	 *            the {@link URI} for logging if any, <code>null</code> otherwise
	 * @param monitor
	 *            the progress {@link Monitor}
	 */
	public static void generate(Template template, Map<String, Object> variables, AcceleoEvaluator evaluator,
			IQualifiedNameQueryEnvironment queryEnvironment, IAcceleoGenerationStrategy generationStrategy,
			URI destination, URI logURI, Monitor monitor) {
		generationStrategy.start(destination);
		final String moduleQualifiedName = queryEnvironment.getLookupEngine().getResolver().getQualifiedName(
				getContainingModule(template));
		queryEnvironment.getLookupEngine().pushImportsContext(moduleQualifiedName, moduleQualifiedName);
		monitor.subTask(moduleQualifiedName);
		try {
			evaluator.generate(template, variables, generationStrategy, destination, monitor);
		} catch (AcceleoEvaluationCancelledException e) {
			// nothing to do here
		} finally {
			queryEnvironment.getLookupEngine().popContext(moduleQualifiedName);
		}

		if (logURI != null && evaluator.getGenerationResult().getDiagnostic()
				.getSeverity() != Diagnostic.OK) {
			// TODO provide Charset
			try (final IAcceleoWriter logWriter = generationStrategy.createWriterForLog(logURI,
					StandardCharsets.UTF_8, evaluator.getNewLine());) {
				printDiagnostic(logWriter, evaluator.getGenerationResult().getDiagnostic(), "", evaluator
						.getNewLine());
				printSummary(logWriter, evaluator.getGenerationResult(), evaluator.getNewLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		generationStrategy.terminate();
	}

	/**
	 * Gets the {@link List} of {@link EObject} that can be used as values for the given {@link TypeLiteral}
	 * in the given {@link List} of {@link EReference}.
	 * 
	 * @param type
	 *            the type we are searching values for
	 * @param queryEnvironment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 * @param resources
	 *            the {@link List} of {@link Resource} to search
	 * @param valuesCache
	 *            the cache of any previous values
	 * @param monitor
	 *            the progress {@link Monitor}, it must consumes the resources.size()
	 * @return the {@link List} of {@link EObject} that can be used as values for the given
	 *         {@link TypeLiteral} in the given {@link List} of {@link EReference}
	 */
	public static List<EObject> getValues(TypeLiteral type, IQualifiedNameQueryEnvironment queryEnvironment,
			List<Resource> resources, Map<EClass, List<EObject>> valuesCache, Monitor monitor) {
		final List<EObject> res;

		// TODO handle other types
		final EClassifierTypeLiteral eClassifierTypeLiteral = (EClassifierTypeLiteral)type;
		final Set<EClassifier> eClassifiers = queryEnvironment.getEPackageProvider().getTypes(
				eClassifierTypeLiteral.getEPackageName(), eClassifierTypeLiteral.getEClassifierName());
		if (!eClassifiers.isEmpty()) {
			final EObjectServices services = new EObjectServices(queryEnvironment, null, null);
			final EClass parameterType = (EClass)eClassifiers.iterator().next();
			res = valuesCache.computeIfAbsent(parameterType, t -> {
				final List<EObject> values = new ArrayList<EObject>();
				for (Resource model : resources) {
					monitor.subTask("Loading model elements form " + model.getURI());
					for (EObject root : model.getContents()) {
						if (parameterType.isInstance(root)) {
							values.add(root);
						}
						values.addAll(services.eAllContents(root, t));
					}
					monitor.worked(1);
					if (monitor.isCanceled()) {
						break;
					}
				}
				return values;
			});
		} else {
			monitor.worked(resources.size());
			res = Collections.emptyList();
		}

		return res;
	}

	private static void printDiagnostic(IAcceleoWriter writer, Diagnostic diagnostic, String indentation,
			String newLine) {
		String nextIndentation = indentation;
		if (diagnostic.getMessage() != null) {
			writer.append(indentation);
			switch (diagnostic.getSeverity()) {
				case Diagnostic.INFO:
					writer.append("INFO ");
					break;

				case Diagnostic.WARNING:
					writer.append("WARNING ");
					break;

				case Diagnostic.ERROR:
					writer.append("ERROR ");
					break;
			}
			if (!diagnostic.getData().isEmpty() && diagnostic.getData().get(0) instanceof ASTNode) {
				writer.append(getLocation((ASTNode)diagnostic.getData().get(0)));
			}
			writer.append(": ");
			writer.append(diagnostic.getMessage().replaceAll("\n", "\n" + nextIndentation) + newLine);
			nextIndentation += "\t";
		}
		for (Diagnostic child : diagnostic.getChildren()) {
			printDiagnostic(writer, child, nextIndentation, newLine);
		}
	}

	/**
	 * Prints the summary of the generation.
	 * 
	 * @param writer
	 *            the {@link IAcceleoWriter}
	 * @param result
	 *            the {@link GenerationResult}
	 * @param newLine
	 *            the new line {@link String}
	 */
	private static void printSummary(IAcceleoWriter writer, GenerationResult result, String newLine) {
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
		writer.append(message + newLine);
	}

	/**
	 * Gets the qualified name and line number of the given {@link ASTNode}.
	 * 
	 * @param astNode
	 *            the {@link ASTNode}
	 * @return the qualified name and line number of the given {@link ASTNode}
	 */
	public static String getLocation(ASTNode astNode) {
		final Module module = getContainingModule(astNode);

		return module.eResource().getURI().toString().substring(AcceleoParser.ACCELEOENV_URI_PROTOCOL
				.length()) + " L" + (module.getAst().getStartLine(astNode) + 1);
	}

	/**
	 * Gets the content of the given {@link InputStream}.
	 * 
	 * @param stream
	 *            the {@link InputStream}
	 * @param charsetName
	 *            The name of a supported {@link java.nio.charset.Charset
	 *            </code>charset<code>}, <code>null</code> will default to {@link StandardCharsets#UTF_8}
	 * @return a {@link CharSequence} of the content of the given {@link InputStream}
	 * @throws IOException
	 *             if the {@link InputStream} can't be read
	 */
	public static String getContent(InputStream stream, String charsetName) throws IOException {
		final int len = 8192;
		StringBuilder res = new StringBuilder(len);
		final String localCharsetName;
		if (charsetName != null) {
			localCharsetName = charsetName;
		} else {
			localCharsetName = StandardCharsets.UTF_8.name();
		}
		if (len != 0) {
			try (InputStreamReader input = new InputStreamReader(new BufferedInputStream(stream),
					localCharsetName)) {
				char[] buffer = new char[len];
				int length = input.read(buffer);
				while (length != -1) {
					res.append(buffer, 0, length);
					length = input.read(buffer);
				}
				input.close();
			}
		}
		return res.toString();
	}

	/**
	 * Gets the containing {@link ModuleElement} of the given {@link ASTNode}.
	 * 
	 * @param node
	 *            the {@link ASTNode}
	 * @return the containing {@link ModuleElement} of the given {@link ASTNode} if any, <code>null</code>
	 *         otherwise
	 */
	public static ModuleElement getContainingModuleElement(ASTNode node) {
		return (ModuleElement)getContainer(node, n -> n instanceof ModuleElement);
	}

	/**
	 * Gets the containing {@link Statement} of the given {@link ASTNode}.
	 * 
	 * @param node
	 *            the {@link ASTNode}
	 * @return the containing {@link Statement} of the given {@link ASTNode} if any, <code>null</code>
	 *         otherwise
	 */
	public static Statement getContainingStatement(ASTNode node) {
		return (Statement)getContainer(node, n -> n instanceof Statement);
	}

	/**
	 * Gets the containing {@link Module} of the given {@link ASTNode}.
	 * 
	 * @param node
	 *            the {@link ASTNode}
	 * @return the containing {@link Module} of the given {@link ASTNode} if any, <code>null</code> otherwise
	 */
	public static Module getContainingModule(ASTNode node) {
		return (Module)getContainer(node, n -> n instanceof Module);
	}

	/**
	 * Gets the first {@link EObject container} of the given {@link EObject} matching the given
	 * {@link Predicate}.
	 * 
	 * @param eObject
	 *            the {@link EObject}
	 * @param predicate
	 *            the {@link Predicate}
	 * @return the first {@link EObject container} of the given {@link EObject} matching the given
	 *         {@link Predicate} if any, <code>null</code> otherwise
	 */
	private static EObject getContainer(EObject eObject, Predicate<EObject> predicate) {
		EObject res = null;

		EObject current = eObject.eContainer();
		while (current != null) {
			if (predicate.test(current)) {
				res = current;
				break;
			} else {
				current = current.eContainer();
			}
		}

		return res;
	}

	/**
	 * Creates a {@link IQualifiedNameQueryEnvironment} for Acceleo.
	 * 
	 * @param options
	 *            the {@link Map} of options
	 * @param resolver
	 *            the {@link IQualifiedNameResolver}
	 * @param resourceSetForModels
	 *            the {@link ResourceSet} for models
	 * @param forWorkspace
	 *            tells if the {@link IService} will be used in a workspace
	 * @return {@link IQualifiedNameQueryEnvironment} for Acceleo
	 * @see #cleanServices(IReadOnlyQueryEnvironment, ResourceSet)
	 */
	public static IQualifiedNameQueryEnvironment newAcceleoQueryEnvironment(Map<String, String> options,
			IQualifiedNameResolver resolver, ResourceSet resourceSetForModels, boolean forWorkspace) {
		return AQLUtils.newQualifiedNameEnvironmentDefaultServices(LANGUAGE_NAME, options, resolver,
				resourceSetForModels, forWorkspace);
	}

	/**
	 * Registers {@link EPackage} needed to launch the given main {@link Module} to the given
	 * {@link IQualifiedNameQueryEnvironment}.
	 * 
	 * @param environment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 * @param resolver
	 *            the {@link IQualifiedNameResolver}
	 * @param main
	 *            the main {@link Module}
	 */
	public static void registerEPackage(IQualifiedNameQueryEnvironment environment,
			IQualifiedNameResolver resolver, Module main) {
		for (EPackage ePkg : AcceleoUtil.getAllNeededEPackages(resolver, main)) {
			registerEPackage(environment, ePkg);
		}
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
	 * Cleans the services for the given Acceleo {@link IReadOnlyQueryEnvironment}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param resourceSetForModels
	 *            the {@link ResourceSet} for models
	 */
	public static void cleanServices(IReadOnlyQueryEnvironment queryEnvironment,
			ResourceSet resourceSetForModels) {
		AQLUtils.cleanServices(LANGUAGE_NAME, queryEnvironment, resourceSetForModels);
	}

	/**
	 * Gets the log {@link URI} for the given target {@link URI} and log {@link String}.
	 * 
	 * @param targetURI
	 *            the target {@link URI}
	 * @param log
	 *            the log {@link String}
	 * @return the log {@link URI} for the given target {@link URI} and log {@link String}
	 * @throws IllegalArgumentException
	 *             if {@link URI} convertion fails
	 */
	public static URI getlogURI(URI targetURI, String log) throws IllegalArgumentException {
		final URI res;

		if (log != null) {
			final URI uri = URI.createURI(log);
			if (uri.isRelative()) {
				if (targetURI.isRelative()) {
					res = targetURI.appendSegments(uri.segments());
				} else {
					res = uri.resolve(targetURI);
				}
			} else {
				res = uri;
			}
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Gets the {@link Set} of all needed {@link Module} for the given {@link Module} and its direct and
	 * indirect extends and imports in the given {@link IQualifiedNameResolver}.
	 * 
	 * @param resolver
	 *            the {@link IQualifiedNameResolver}
	 * @param module
	 *            the {@link Module}
	 * @return the {@link Set} of all needed {@link Module} for the given {@link Module} and its direct and
	 *         indirect extends and imports in the given {@link IQualifiedNameResolver}.
	 */
	public static Set<Module> getAllNeededModules(IQualifiedNameResolver resolver, Module module) {
		final Set<Module> res = new LinkedHashSet<>();

		Set<Module> currentModules = new LinkedHashSet<>();
		res.add(module);
		currentModules.add(module);
		do {
			final Set<Module> addedModules = new LinkedHashSet<>();
			for (Module currentModule : currentModules) {
				final ModuleReference extds = currentModule.getExtends();
				if (extds != null) {
					Object resolved = resolver.resolve(extds.getQualifiedName());
					if (resolved instanceof Module && res.add(currentModule)) {
						addedModules.add((Module)resolved);
					}
				}
				for (Import imprt : currentModule.getImports()) {
					final Object resolved = resolver.resolve(imprt.getModule().getQualifiedName());
					if (resolved instanceof Module && res.add((Module)resolved)) {
						addedModules.add((Module)resolved);
					}
				}
			}
			currentModules = addedModules;
		} while (!currentModules.isEmpty());

		return res;
	}

	/**
	 * Gets the {@link Set} all {@link EPackage} used by the given {@link Module} and direct and indirect
	 * {@link Module} dependencies.
	 * 
	 * @param resolver
	 *            the {@link IQualifiedNameResolver}
	 * @param module
	 *            the {@link Module}
	 * @return the {@link Set} all {@link EPackage} used by the given {@link Module} and direct and indirect
	 *         {@link Module} dependencies.
	 */
	public static Set<EPackage> getAllNeededEPackages(IQualifiedNameResolver resolver, Module module) {
		final Set<EPackage> res = new LinkedHashSet<>();

		for (Module mod : getAllNeededModules(resolver, module)) {
			for (Metamodel medamodel : mod.getMetamodels()) {
				res.add(medamodel.getReferencedPackage());
			}
		}

		return res;
	}

}
