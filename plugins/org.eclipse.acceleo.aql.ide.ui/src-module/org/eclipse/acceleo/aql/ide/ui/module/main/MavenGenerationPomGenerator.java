/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui.module.main;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.evaluation.GenerationResult;
import org.eclipse.acceleo.aql.evaluation.strategy.DefaultGenerationStrategy;
import org.eclipse.acceleo.aql.evaluation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.aql.evaluation.strategy.IWriterFactory;
import org.eclipse.acceleo.aql.ide.ui.AcceleoUIPlugin;
import org.eclipse.acceleo.aql.ide.ui.evaluation.strategy.AcceleoUIWorkspaceWriterFactory;
import org.eclipse.acceleo.aql.ide.ui.module.services.Services;
import org.eclipse.acceleo.aql.ide.ui.wizard.GenerationPomConfiguration;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.parser.ModuleLoader;
import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.ast.ASTNode;
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
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Eclipse launcher for org::eclipse::acceleo::aql::ide::ui::module::main::mavenGenerationPom.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class MavenGenerationPomGenerator extends AbstractGenerator {

	/**
	 * The main {@link Module} {@link IFile}.
	 */
	private final IFile moduleFile;

	/**
	 * The model {@link Module} qualified name.
	 */
	protected String modelModuleQualifiedName;

	/**
	 * The model {@link Module}.
	 */
	protected Module modelModule;

	/**
	 * The generated Maven generation POM file path.
	 */
	protected String generatePomFilePath;

	/**
	 * The Maven generation POM folder {@link URI}.
	 */
	protected final URI pomFoldertargetURI;

	/**
	 * The generator pom group Id.
	 */
	private String generatorGroupId;

	/**
	 * The generator pom artifact Id.
	 */
	private String generatorArtifactId;

	/**
	 * The generator pom version.
	 */
	private String generatorVersion;

	/**
	 * The Maven generation POM group Id.
	 */
	private String generationGroupId;

	/**
	 * The Maven generation POM artifact Id.
	 */
	private String generationArtifactId;

	/**
	 * The Maven generation POM version.
	 */
	private String generationVersion;

	/**
	 * The generation model path relative to the Maven generation POM.
	 */
	private String modelPath;

	/**
	 * The generation target path relative to the Maven generation POM.
	 */
	private String targetFolderPath;

	/**
	 * Constructor.
	 * 
	 * @param moduleFile
	 *            the module {@link IFile}
	 * @param configuration
	 *            the {@link GenerationPomConfiguration}
	 */
	public MavenGenerationPomGenerator(IFile moduleFile, GenerationPomConfiguration configuration) {
		this.moduleFile = moduleFile;
		generatePomFilePath = new File(configuration.getPomFolder()).getAbsolutePath() + File.separator
				+ "pom.xml";
		pomFoldertargetURI = configuration.getPomFolderURI();
		generatePomFilePath = URI.createURI("pom.xml").resolve(pomFoldertargetURI).path();
		this.generatorGroupId = configuration.getGeneratorGroupId();
		this.generatorArtifactId = configuration.getGeneratorArtifactId();
		this.generatorVersion = configuration.getGeneratorVersion();
		this.generationGroupId = configuration.getGenerationGroupId();
		this.generationArtifactId = configuration.getGenerationArtifactId();
		this.generationVersion = configuration.getGenerationVersion();

		this.modelPath = configuration.getGenerationModelPathURI().deresolve(pomFoldertargetURI).toString();
		this.targetFolderPath = configuration.getGenerationTargetPathURI().deresolve(pomFoldertargetURI)
				.toString();
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
		final Set<String> nsURIs = AQLUtils.getAllNeededEPackages(resolver, moduleQualifiedName);
		AQLUtils.registerEPackages(queryEnvironment, EPackage.Registry.INSTANCE, nsURIs);
		final URI logURI = AcceleoUtil.getlogURI(pomFoldertargetURI, options.get(AcceleoUtil.LOG_URI_OPTION));

		final IQualifiedNameResolver workspaceResolver = QueryPlugin.getPlugin().createQualifiedNameResolver(
				getClass().getClassLoader(), moduleFile.getProject(), AcceleoParser.QUALIFIER_SEPARATOR,
				true);
		workspaceResolver.addLoader(new ModuleLoader(new AcceleoParser(), null));
		modelModuleQualifiedName = getQualifiedNameFromSourceFile(moduleFile);
		final String moduleAbsolutePath = moduleFile.getLocation().toFile().getAbsolutePath();
		modelModule = loadModelModule(URI.createFileURI(moduleAbsolutePath), modelModuleQualifiedName);

		synchronized(this) {
			beforeGeneration(queryEnvironment, workspaceResolver);
			try {
				final List<Template> mainTemplates = AcceleoUtil.getMainTemplates(module);
				final Template main = mainTemplates.get(0);
				Map<String, Object> variables = new HashMap<>();
				variables.put("module", modelModule);
				variables.put("generatorGroupId", generatorGroupId);
				variables.put("generatorArtifactId", generatorArtifactId);
				variables.put("generatorVersion", generatorVersion);
				variables.put("generationGroupId", generationGroupId);
				variables.put("generationArtifactId", generationArtifactId);
				variables.put("generationVersion", generationVersion);
				variables.put("modelPath", modelPath);
				variables.put("targetFolderPath", targetFolderPath);

				AcceleoUtil.generate(main, variables, evaluator, queryEnvironment, strategy,
						pomFoldertargetURI, logURI, monitor);
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
		return "org::eclipse::acceleo::aql::ide::ui::module::main::mavenGenerationPom";
	}

	protected String getGeneratorClassName() {
		String res = modelModuleQualifiedName.replace(AcceleoParser.QUALIFIER_SEPARATOR, JavaLoader.DOT)
				+ "Generator";

		final int lastQualifierSeparatorIndex = res.lastIndexOf(JavaLoader.DOT);
		if (lastQualifierSeparatorIndex < 0) {
			res = Character.toUpperCase(res.charAt(0)) + res.substring(1, res.length());
		} else {
			res = res.substring(0, lastQualifierSeparatorIndex + 1) + Character.toUpperCase(res.charAt(
					lastQualifierSeparatorIndex + 1)) + res.substring(lastQualifierSeparatorIndex + 2, res
							.length());
		}

		return res;
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
				.getURIConverter(), getWriterFactory());
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
			final String location;
			if (!diagnostic.getData().isEmpty() && diagnostic.getData().get(0) instanceof ASTNode) {
				location = AcceleoUtil.getLocation((ASTNode)diagnostic.getData().get(0)) + ": ";
			} else {
				location = "";
			}
			switch (diagnostic.getSeverity()) {
				case Diagnostic.INFO:
					AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.INFO, diagnostic.getSource(),
							location + diagnostic.getMessage(), diagnostic.getException()));
					break;

				case Diagnostic.WARNING:
					AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.WARNING, diagnostic
							.getSource(), location + diagnostic.getMessage(), diagnostic.getException()));
					break;

				case Diagnostic.ERROR:
					AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, diagnostic
							.getSource(), location + diagnostic.getMessage(), diagnostic.getException()));
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
		try {
			moduleFile.getParent().refreshLocal(IResource.DEPTH_ONE, new NullProgressMonitor());
			if (isInMavenProject(moduleFile)) {
				updateMavenPom(generatePomFilePath);
			}
		} catch (CoreException e) {
			AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, getClass(),
					"could not refresh " + moduleFile.getParent().getFullPath(), e));
		}
	}

	/**
	 * Updates (dependencies, repository, and exec) the Maven pom.xml file for the given pom file path.
	 * 
	 * @param pomPath
	 *            the pom file path
	 */
	private void updateMavenPom(String pomPath) {
		final File pomFile = new File(pomPath);
		if (pomFile.exists() && pomFile.canRead()) {
			final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try (InputStream input = new FileInputStream(pomFile)) {
				final DocumentBuilder db = dbf.newDocumentBuilder();
				final Document pom = db.parse(input);
				final NodeList projectList = pom.getElementsByTagName("project");
				if (projectList.getLength() > 0) {
					final Node pomProject = projectList.item(0);
					if (pomProject instanceof Element) {
						final List<MavenDependency> dependencies = new ArrayList<>();
						dependencies.add(new MavenDependency(generatorGroupId, generatorArtifactId,
								generatorVersion));
						final boolean repositoriesUpdated = updateRepositories(pom, (Element)pomProject);
						final boolean dependenciesUpdated = addMavenDependencies(pom, (Element)pomProject,
								dependencies);
						final boolean execUpdated = updateExec(pom, (Element)pomProject);
						if (repositoriesUpdated || dependenciesUpdated || execUpdated) {
							try {
								final Transformer tr = TransformerFactory.newInstance().newTransformer();
								final ByteArrayOutputStream ouputStream = new ByteArrayOutputStream();
								tr.transform(new DOMSource(pom), new StreamResult(ouputStream));
								getPreview().put(URI.createFileURI(pomPath), new String(ouputStream
										.toByteArray()).toString());
							} catch (TransformerException te) {
								AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR,
										getClass(), "can't write " + generatePomFilePath, te));
							}
						}
					}
				}
			} catch (ParserConfigurationException | SAXException | IOException e) {
				AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, getClass(),
						"can't load pom.xml file for " + moduleFile.getParent().getFullPath(), e));
			}
		}
	}

	@Override
	protected IWriterFactory createWriterFactory() {
		final IWriterFactory res;

		if (new File(generatePomFilePath).exists()) {
			res = super.createWriterFactory();
		} else {
			res = new AcceleoUIWorkspaceWriterFactory();
		}

		return res;
	}

	/**
	 * Updates the given pom {@link Document} to add the exec {@link Node}.
	 * 
	 * @param pom
	 *            the pom {@link Document}
	 * @param project
	 *            the project {@link Node}
	 * @return <code>true</code> if the {@link Document} has been modified, <code>false</code> otherwise
	 */
	private boolean updateExec(Document pom, Element project) {
		// build
		final NodeList buildList = project.getElementsByTagName("build");
		final Element build;
		final boolean buildCreated;
		if (buildList.getLength() > 0 && buildList.item(0) instanceof Element) {
			build = (Element)buildList.item(0);
			buildCreated = false;
		} else {
			build = pom.createElement("build");
			project.appendChild(pom.createTextNode(System.lineSeparator() + System.lineSeparator()
					+ MAVEN_INDENTATION));
			build.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION));
			project.appendChild(build);
			project.appendChild(pom.createTextNode(System.lineSeparator()));
			buildCreated = true;
		}
		// plugins
		final NodeList pluginsList = build.getElementsByTagName("plugins");
		final Element plugins;
		final boolean pluginsCreated;
		final boolean needPluginNode;
		if (pluginsList.getLength() > 0 && pluginsList.item(0) instanceof Element) {
			plugins = (Element)pluginsList.item(0);
			boolean needNewPlugin = true;
			found: for (int i = 0; i < pluginsList.getLength(); i++) {
				if (pluginsList.item(i) instanceof Element) {
					final Element currentResources = (Element)pluginsList.item(i);
					final NodeList mainClassList = currentResources.getElementsByTagName("mainClass");
					for (int j = 0; j < mainClassList.getLength(); j++) {
						if (mainClassList.item(j) instanceof Element) {
							final Element mainClass = (Element)mainClassList.item(j);
							if (mainClass.getTextContent().equals(getGeneratorClassName()) && sameParameters(
									mainClass)) {
								needNewPlugin = false;
								// TODO test parameters
								break found;
							}
						}
					}
				}
			}
			needPluginNode = needNewPlugin;
			pluginsCreated = false;
		} else {
			plugins = pom.createElement("plugins");
			build.appendChild(pom.createTextNode(MAVEN_INDENTATION));
			build.appendChild(plugins);
			build.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION));
			pluginsCreated = true;
			needPluginNode = true;
		}

		if (needPluginNode) {
			// plugin
			final Element plugin = pom.createElement("plugin");
			plugins.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION));
			plugins.appendChild(plugin);
			plugins.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION));
			// groupId
			final Element groupId = pom.createElement("groupId");
			plugin.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION));
			groupId.setTextContent("org.codehaus.mojo");
			plugin.appendChild(groupId);
			// artifactId
			final Element artifactId = pom.createElement("artifactId");
			plugin.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION));
			artifactId.setTextContent("exec-maven-plugin");
			plugin.appendChild(artifactId);
			// version
			final Element version = pom.createElement("version");
			plugin.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION));
			version.setTextContent("3.5.0");
			plugin.appendChild(version);
			// executions
			final Element executions = pom.createElement("executions");
			plugin.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION));
			plugin.appendChild(executions);
			// execution
			final Element execution = pom.createElement("execution");
			executions.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION));
			executions.appendChild(execution);
			executions.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION));
			// goals
			final Element goals = pom.createElement("goals");
			execution.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION
					+ MAVEN_INDENTATION));
			execution.appendChild(goals);
			execution.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION));
			// goal
			final Element goal = pom.createElement("goal");
			goal.setTextContent("java");
			goals.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION));
			goals.appendChild(goal);
			goals.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION
					+ MAVEN_INDENTATION));
			// configuration
			final Element configuration = pom.createElement("configuration");
			plugin.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION));
			plugin.appendChild(configuration);
			plugin.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION));
			// mainClass
			final Element mainClass = pom.createElement("mainClass");
			mainClass.setTextContent(getGeneratorClassName());
			configuration.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION));
			configuration.appendChild(mainClass);
			// arguments
			final Element arguments = pom.createElement("arguments");
			configuration.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION));
			configuration.appendChild(arguments);
			configuration.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION));
			// argument (model path)
			final Element modelPathArgument = pom.createElement("argument");
			modelPathArgument.setTextContent(modelPath);
			arguments.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION
					+ MAVEN_INDENTATION));
			arguments.appendChild(modelPathArgument);
			// argument (target path)
			final Element targetPathArgument = pom.createElement("argument");
			targetPathArgument.setTextContent(targetFolderPath);
			arguments.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION
					+ MAVEN_INDENTATION));
			arguments.appendChild(targetPathArgument);
			arguments.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION));
		}

		return buildCreated || pluginsCreated || needPluginNode;
	}

	/**
	 * Checks the arguments (modelPath and targetFolderPath) are present for the given mainClass
	 * {@link Element}.
	 * 
	 * @param mainClass
	 *            the mainClass {@link Element}
	 * @return <code>true</code> if the arguments (modelPath and targetFolderPath) are present for the given
	 *         mainClass {@link Element}, <code>false</code> otherwise
	 */
	private boolean sameParameters(Element mainClass) {
		final boolean res;

		final Node parent = mainClass.getParentNode();
		if (parent instanceof Element) {
			final List<String> arguments = new ArrayList<>();
			final Element configuration = (Element)parent;
			final NodeList argumentList = configuration.getElementsByTagName("argument");
			for (int j = 0; j < argumentList.getLength(); j++) {
				if (argumentList.item(j) instanceof Element) {
					final Element argument = (Element)argumentList.item(j);
					arguments.add(argument.getTextContent().trim());
				}
			}
			res = arguments.containsAll(Arrays.asList(modelPath, targetFolderPath));
		} else {
			res = false;
		}

		return res;
	}

	/**
	 * Updates the given pom {@link Document} to add the repositories {@link Node}.
	 * 
	 * @param pom
	 *            the pom {@link Document}
	 * @param project
	 *            the project {@link Node}
	 * @return <code>true</code> if the {@link Document} has been modified, <code>false</code> otherwise
	 */
	private boolean updateRepositories(Document pom, Element project) {
		// repositories
		final NodeList repositoriesList = project.getElementsByTagName("repositories");
		final Element repositories;
		final boolean repositoriesCreated;
		final boolean needRepositoriesNode;
		if (repositoriesList.getLength() > 0 && repositoriesList.item(0) instanceof Element) {
			repositories = (Element)repositoriesList.item(0);
			boolean needNewRepositories = true;
			found: for (int i = 0; i < repositoriesList.getLength(); i++) {
				if (repositoriesList.item(i) instanceof Element) {
					final Element currentResources = (Element)repositoriesList.item(i);
					final NodeList urlList = currentResources.getElementsByTagName("url");
					for (int j = 0; j < urlList.getLength(); j++) {
						if (urlList.item(j) instanceof Element) {
							final Element url = (Element)urlList.item(j);
							if (url.getTextContent().startsWith(
									"https://download.eclipse.org/acceleo/updates/")) {
								needNewRepositories = false;
								break found;
							}
						}
					}
				}
			}
			needRepositoriesNode = needNewRepositories;
			repositoriesCreated = false;
		} else {
			repositories = pom.createElement("repositories");
			project.appendChild(pom.createTextNode(MAVEN_INDENTATION));
			project.appendChild(repositories);
			project.appendChild(pom.createTextNode(System.lineSeparator()));
			repositoriesCreated = true;
			needRepositoriesNode = true;
		}

		if (needRepositoriesNode) {
			// repository
			final Element repository = pom.createElement("repository");
			repositories.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION));
			repositories.appendChild(repository);
			repositories.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION));
			// id
			final Element id = pom.createElement("id");
			repository.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION));
			id.setTextContent("acceleo-repository");
			repository.appendChild(id);
			// name
			final Element name = pom.createElement("name");
			repository.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION));
			name.setTextContent("Acceleo Repository");
			repository.appendChild(name);
			// url
			final Element url = pom.createElement("url");
			repository.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION));
			url.setTextContent("https://download.eclipse.org/acceleo/updates/releases/4.1/R202502130921/");
			repository.appendChild(url);
			repository.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION));
		}

		return repositoriesCreated || needRepositoriesNode;
	}

}
