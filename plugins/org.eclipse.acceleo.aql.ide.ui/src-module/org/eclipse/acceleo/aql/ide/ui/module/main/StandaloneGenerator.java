/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.evaluation.GenerationResult;
import org.eclipse.acceleo.aql.evaluation.strategy.DefaultGenerationStrategy;
import org.eclipse.acceleo.aql.evaluation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.aql.ide.ui.AcceleoUIPlugin;
import org.eclipse.acceleo.aql.ide.ui.module.services.Services;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.parser.ModuleLoader;
import org.eclipse.acceleo.query.ast.ASTNode;
import org.eclipse.acceleo.query.ide.QueryPlugin;
import org.eclipse.acceleo.query.runtime.impl.namespace.ClassLoaderQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.impl.namespace.JavaLoader;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.pde.internal.core.bundle.WorkspaceBundleModel;
import org.eclipse.pde.internal.core.project.PDEProject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Eclipse launcher for org::eclipse::python4capella::ecore::gen::python::main::standalone.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class StandaloneGenerator extends AbstractGenerator {

	/**
	 * The Maven indentation.
	 */
	private static final String MAVEN_INDENTATION = "    ";

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
	 * The {@link Set} of model {@link Module} parameter bundle dependencies.
	 */
	protected Set<String> dependencyBundleNames;

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
	 * Generates.
	 * 
	 * @param monitor
	 *            the progress {@link Monitor}
	 */
	public void generate(Monitor monitor) {
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
		final AcceleoEvaluator evaluator = createAcceleoEvaluator(resolver, queryEnvironment);
		final IAcceleoGenerationStrategy strategy = createGenerationStrategy(resourceSetForModels);

		final Module module = (Module)resolver.resolve(moduleQualifiedName);
		AcceleoUtil.registerEPackage(queryEnvironment, resolver, module);
		final URI logURI = AcceleoUtil.getlogURI(targetURI, options.get(AcceleoUtil.LOG_URI_OPTION));

		final IQualifiedNameResolver workspaceResolver = QueryPlugin.getPlugin().createQualifiedNameResolver(
				getClass().getClassLoader(), moduleFile.getProject(), AcceleoParser.QUALIFIER_SEPARATOR,
				true);
		final java.net.URI binaryURI = workspaceResolver.getBinaryURI(moduleFile.getLocation().toFile()
				.toURI());
		workspaceResolver.addLoader(new ModuleLoader(new AcceleoParser(), null));
		modelModuleQualifiedName = workspaceResolver.getQualifiedName(binaryURI);
		modelModule = (Module)workspaceResolver.resolve(modelModuleQualifiedName);
		dependencyBundleNames = new LinkedHashSet<>();
		dependencyBundleNames.add("org.eclipse.acceleo.query;bundle-version=\"[" + getAQLVersionLowerBound()
				+ "," + getAQLVersionUpperBound() + ")\"");
		dependencyBundleNames.add("org.eclipse.acceleo.aql;bundle-version=\"[" + getAcceleoVersionLowerBound()
				+ "," + getAcceleoVersionUpperBound() + ")\"");
		dependencyBundleNames.add("org.eclipse.acceleo.aql.profiler;bundle-version=\"["
				+ getAcceleoVersionLowerBound() + "," + getAcceleoVersionUpperBound() + ")\"");
		dependencyBundleNames.add("org.antlr.runtime;bundle-version=\"[" + getANTLRVersionLowerBound() + ","
				+ getANTLRVersionUpperBound() + ")\"");
		AcceleoUtil.registerEPackage(queryEnvironment, workspaceResolver, modelModule);
		dependencyBundleNames.addAll(getDependencyBundleNames(queryEnvironment, modelModule));

		synchronized(this) {
			beforeGeneration(queryEnvironment, workspaceResolver);
			try {
				AcceleoUtil.generate(evaluator, queryEnvironment, module, modelModule.eResource(), strategy,
						targetURI, logURI, monitor);
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
			if (isInPluginProject(moduleFile)) {
				IFile manifest = PDEProject.getManifest(moduleFile.getProject());
				if (manifest.isAccessible()) {
					final WorkspaceBundleModel model = new WorkspaceBundleModel(manifest);
					model.load();
					addPluginDependencies(model, dependencyBundleNames);
					final String packageName = new Services().getJavaPackage(modelModule);
					if (!packageName.isEmpty()) {
						addExportPackages(model, Collections.singleton(packageName));
					}
					final Writer writer = new StringWriter();
					final PrintWriter printWriter = new PrintWriter(writer);
					model.save(printWriter);
					getPreview().put(URI.createFileURI(manifest.getLocation().toFile().getAbsolutePath()),
							writer.toString());
				}
			} else if (isInMavenProject(moduleFile)) {
				updateMavenPom(moduleFile.getProject());
			}
		} catch (CoreException e) {
			AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, getClass(),
					"could not refresh " + moduleFile.getParent().getFullPath(), e));
		}
	}

	/**
	 * Updates (dependencies and resources) the Maven pom.xml file for the given {@link IProject}.
	 * 
	 * @param project
	 *            the {@link IProject}
	 */
	private void updateMavenPom(IProject project) {
		final IFile pomFile = project.getFile("pom.xml");
		if (pomFile.isAccessible()) {
			final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			try {
				final DocumentBuilder db = dbf.newDocumentBuilder();
				final Document pom = db.parse(pomFile.getContents());
				final NodeList projectList = pom.getElementsByTagName("project");
				if (projectList.getLength() > 0) {
					final Node pomProject = projectList.item(0);
					if (pomProject instanceof Element) {
						final boolean dependenciesUpdated = addMavenDependencies(pom, (Element)pomProject);
						final boolean resourcesUpdated = updateResources(pom, (Element)pomProject);
						if (dependenciesUpdated || resourcesUpdated) {
							try {
								final Transformer tr = TransformerFactory.newInstance().newTransformer();
								final ByteArrayOutputStream ouputStream = new ByteArrayOutputStream();
								tr.transform(new DOMSource(pom), new StreamResult(ouputStream));
								getPreview().put(URI.createFileURI(pomFile.getLocation().toFile()
										.getAbsolutePath()), new String(ouputStream.toByteArray())
												.toString());
							} catch (TransformerException te) {
								AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR,
										getClass(), "can't write pom.xml file for " + moduleFile.getParent()
												.getFullPath(), te));
							}
						}
					}
				}
			} catch (ParserConfigurationException | SAXException | IOException | CoreException e) {
				AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, getClass(),
						"can't load pom.xml file for " + moduleFile.getParent().getFullPath(), e));
			}
		}
	}

	/**
	 * Adds the maven dependencies to the given {@link Document} and project {@link Element}.
	 * 
	 * @param pom
	 *            the pom {@link Document}
	 * @param project
	 *            the project {@link Element}
	 * @return <code>true</code> if the {@link Document} has been modified, <code>false</code> otherwise.
	 */
	private boolean addMavenDependencies(Document pom, Element project) {
		boolean res;

		// get already existing dependencies
		final Set<String> knownDependencies = new HashSet<>();
		final NodeList dependenciesList = project.getElementsByTagName("dependencies");
		final Node dependencies;
		if (dependenciesList.getLength() > 0) {
			for (int i = 0; i < dependenciesList.getLength(); i++) {
				final Node currentDependencies = dependenciesList.item(i);
				if (currentDependencies instanceof Element) {
					final NodeList artifactIds = ((Element)currentDependencies).getElementsByTagName(
							"artifactId");
					if (artifactIds != null) {
						for (int j = 0; j < artifactIds.getLength(); j++) {
							knownDependencies.add(artifactIds.item(j).getTextContent());
						}
					}
				}
			}
			dependencies = dependenciesList.item(0);
		} else {
			dependencies = pom.createElement("dependencies");
			project.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION));
			project.appendChild(dependencies);
			dependencies.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION));
			project.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION));

		}
		if (!knownDependencies.contains("acceleo")) {
			addMavenDependencyNode(pom, dependencies, "org.eclipse.acceleo", "acceleo", "["
					+ getAcceleoVersionLowerBound() + "," + getAcceleoVersionUpperBound() + ")");
			res = true;
		} else {
			res = false;
		}

		return res;
	}

	/**
	 * Adds a dependency {@link Node} to the given dependency {@link Node}.
	 * 
	 * @param pom
	 *            the pom {@link Document}
	 * @param dependencies
	 *            the dependencies {@link Node}
	 * @param groupIdString
	 *            the group ID {@link String}
	 * @param artifactIdString
	 *            the artifact ID {@link String}
	 * @param versionString
	 *            the version {@link String}
	 */
	private void addMavenDependencyNode(Document pom, Node dependencies, String groupIdString,
			String artifactIdString, String versionString) {
		if (dependencies instanceof Element) {
			final Element dependency = pom.createElement("dependency");
			dependencies.appendChild(pom.createTextNode(MAVEN_INDENTATION));
			dependencies.appendChild(dependency);
			dependencies.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION));
			// groupId
			final Element groupId = pom.createElement("groupId");
			dependency.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION));
			dependency.appendChild(groupId);
			groupId.setTextContent(groupIdString);
			// groupId
			final Element artifactId = pom.createElement("artifactId");
			dependency.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION));
			dependency.appendChild(artifactId);
			artifactId.setTextContent(artifactIdString);
			// version
			final Element version = pom.createElement("version");
			dependency.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION));
			dependency.appendChild(version);
			dependency.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION));
			version.setTextContent(versionString);
		}
	}

	/**
	 * Updates the given pom {@link Document} to add the resource {@link Node}
	 * 
	 * @param pom
	 *            the pom {@link Document}
	 * @param project
	 *            the project {@link Node}
	 * @return <code>true</code> if the {@link Document} has been modified, <code>false</code> otherwise.
	 */
	private boolean updateResources(Document pom, Element project) {
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
		// resources
		final NodeList resourcesList = build.getElementsByTagName("resources");
		final Element resources;
		final boolean resourcesCreated;
		final boolean needResourceNode;
		if (resourcesList.getLength() > 0 && resourcesList.item(0) instanceof Element) {
			resources = (Element)resourcesList.item(0);
			boolean needNewResource = true;
			found: for (int i = 0; i < resourcesList.getLength(); i++) {
				if (resourcesList.item(i) instanceof Element) {
					final Element currentResources = (Element)resourcesList.item(i);
					final NodeList includeList = currentResources.getElementsByTagName("include");
					for (int j = 0; j < includeList.getLength(); j++) {
						if (includeList.item(j) instanceof Element) {
							final Element include = (Element)includeList.item(j);
							if (include.getTextContent().contains("mtl")) {
								needNewResource = false;
								break found;
							}
						}
					}
				}
			}
			needResourceNode = needNewResource;
			resourcesCreated = false;
		} else {
			resources = pom.createElement("resources");
			build.appendChild(pom.createTextNode(MAVEN_INDENTATION));
			build.appendChild(resources);
			build.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION));
			resourcesCreated = true;
			needResourceNode = true;
		}

		if (needResourceNode) {
			// resource
			final Element resource = pom.createElement("resource");
			resources.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION));
			resources.appendChild(resource);
			resources.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION));
			// directory
			final Element directory = pom.createElement("directory");
			resource.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION));
			directory.setTextContent("${project.basedir}/src/main/java");
			resource.appendChild(directory);
			// includes
			final Element includes = pom.createElement("includes");
			resource.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION));
			resource.appendChild(includes);
			resource.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION));
			// includes
			final Element include = pom.createElement("include");
			include.setTextContent("**/*.mtl");
			includes.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION));
			includes.appendChild(include);
			includes.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION
					+ MAVEN_INDENTATION + MAVEN_INDENTATION + MAVEN_INDENTATION));

		}

		return buildCreated || resourcesCreated || needResourceNode;
	}

}
