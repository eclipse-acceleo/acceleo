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

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import org.antlr.v4.runtime.Lexer;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.evaluation.strategy.IWriterFactory;
import org.eclipse.acceleo.aql.ide.ui.AcceleoUIPlugin;
import org.eclipse.acceleo.aql.ide.ui.evaluation.strategy.AcceleoUIWorkspaceWriterFactory;
import org.eclipse.acceleo.aql.ide.ui.property.AcceleoPropertyTester;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.parser.AstValidator;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.core.expressions.IPropertyTester;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.pde.internal.core.ibundle.IBundle;
import org.eclipse.pde.internal.core.ibundle.IBundleModel;
import org.eclipse.pde.internal.core.ibundle.IManifestHeader;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleReference;
import org.osgi.framework.Constants;
import org.osgi.framework.Version;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractGenerator {

	/**
	 * A Maven dependency.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	protected static class MavenDependency {

		/**
		 * The group ID.
		 */
		private final String groupId;

		/**
		 * The artifact ID.
		 */
		private final String artifactId;

		/**
		 * The version.
		 */
		private final String version;

		/**
		 * Constructor.
		 * 
		 * @param groupId
		 *            the group ID
		 * @param artifactId
		 *            the atrifact ID
		 * @param version
		 *            the version
		 */
		MavenDependency(String groupId, String artifactId, String version) {
			this.groupId = groupId;
			this.artifactId = artifactId;
			this.version = version;
		}

		/**
		 * Gets the group ID.
		 * 
		 * @returnthe group ID
		 */
		public String getGroupId() {
			return groupId;
		}

		/**
		 * Gets the artifact ID.
		 * 
		 * @returnthe artifact ID
		 */
		public String getArtifactId() {
			return artifactId;
		}

		/**
		 * Gets the version.
		 * 
		 * @return the version
		 */
		public String getVersion() {
			return version;
		}

	}

	/**
	 * The Maven indentation.
	 */
	protected static final String MAVEN_INDENTATION = "    ";

	/**
	 * The {@link AcceleoPropertyTester}.
	 */
	private final IPropertyTester propertyTester = new AcceleoPropertyTester();

	/**
	 * The {@link IWriterFactory}.
	 */
	private IWriterFactory writeractory;

	/**
	 * The preview {@link Map}.
	 */
	private final Map<URI, String> preview = new LinkedHashMap<>();

	/**
	 * Gets the Set of dependency bundle names.
	 * 
	 * @param queryEnvironment
	 * @return the Set of dependency bundle names
	 */
	protected Set<String> getDependencyBundleNames(final IQualifiedNameQueryEnvironment queryEnvironment,
			Module module) {
		final Set<String> res = new LinkedHashSet<>();

		final AstValidator validator = new AstValidator(new ValidationServices(queryEnvironment));
		for (Template main : AcceleoUtil.getMainTemplates(module)) {
			for (Variable parameter : main.getParameters()) {
				final IValidationResult validationResult = validator.validate(Collections.emptyMap(),
						parameter.getType());
				for (IType iType : validator.getDeclarationTypes(queryEnvironment, validationResult
						.getPossibleTypes(parameter.getType().getAst()))) {
					final Object type = iType.getType();
					final Class<?> cls;
					if (type instanceof Class<?>) {
						cls = (Class<?>)type;
					} else if (type instanceof EClassifier && ((EClassifier)type)
							.getInstanceClass() != null) {
						cls = ((EClassifier)type).getInstanceClass();
					} else {
						cls = null;
					}
					if (cls != null) {
						if (cls.getClassLoader() instanceof BundleReference) {
							final Bundle bundle = ((BundleReference)cls.getClassLoader()).getBundle();
							res.add(bundle.getSymbolicName());
						}
					}
				}
			}
		}

		// handle UML2 dependencies
		if (res.contains("org.eclipse.uml2.uml")) {
			res.add("org.eclipse.uml2.uml.resources");
		}

		return res;
	}

	/**
	 * Adds needed plugin dependencies to the given {@link IBundleModel}.
	 * 
	 * @param model
	 *            the {@link IBundleModel}
	 * @param dependencyBundleNames
	 *            the {@link Set} of dependency bundle names
	 */
	@SuppressWarnings("restriction")
	protected void addPluginDependencies(IBundleModel model, Set<String> dependencyBundleNames) {
		final IBundle bundle = model.getBundle();
		final IManifestHeader manifestHeader = bundle.getManifestHeader(Constants.REQUIRE_BUNDLE);
		final String requiredBundleString;
		if (manifestHeader != null) {
			requiredBundleString = manifestHeader.getValue();
		} else {
			requiredBundleString = null;
		}
		if (requiredBundleString == null) {
			final StringJoiner joiner = new StringJoiner(",\n  ");
			for (String dependency : dependencyBundleNames) {
				joiner.add(dependency);
			}
			bundle.setHeader(Constants.REQUIRE_BUNDLE, joiner.toString());
		} else {
			final StringJoiner joiner = new StringJoiner(",\n  ");
			joiner.add(requiredBundleString);
			for (String dependency : dependencyBundleNames) {
				final String dependencyBundleName;
				int lastSemiColonIndex = dependency.lastIndexOf(";");
				if (lastSemiColonIndex >= 0) {
					dependencyBundleName = dependency.substring(0, lastSemiColonIndex);
				} else {
					dependencyBundleName = dependency;
				}
				boolean foundInRequirement = false;
				for (String requirement : requiredBundleString.split(",")) {
					if (requirement.contains(dependencyBundleName)) {
						foundInRequirement = true;
						break;
					}
				}
				if (!foundInRequirement) {
					joiner.add(dependency);
				}
				bundle.setHeader(Constants.REQUIRE_BUNDLE, joiner.toString());
			}
		}
	}

	/**
	 * Adds needed plugin dependencies to the given {@link IBundleModel}.
	 * 
	 * @param model
	 *            the {@link IBundleModel}
	 * @param packages
	 *            the {@link Set} of package names
	 */
	@SuppressWarnings("restriction")
	protected void addExportPackages(IBundleModel model, Set<String> packages) {
		final IBundle bundle = model.getBundle();
		final IManifestHeader manifestHeader = bundle.getManifestHeader(Constants.EXPORT_PACKAGE);
		final String exportedPackageString;
		if (manifestHeader != null) {
			exportedPackageString = manifestHeader.getValue();
		} else {
			exportedPackageString = null;
		}
		if (exportedPackageString == null) {
			final StringJoiner joiner = new StringJoiner(",\n  ");
			for (String dependency : packages) {
				joiner.add(dependency);
			}
			bundle.setHeader(Constants.EXPORT_PACKAGE, joiner.toString());
		} else {
			final StringJoiner joiner = new StringJoiner(",\n  ");
			joiner.add(exportedPackageString);
			for (String dependency : packages) {
				boolean foundInExportedPacakges = false;
				for (String requirement : exportedPackageString.split(",")) {
					if (requirement.contains(dependency)) {
						foundInExportedPacakges = true;
						break;
					}
				}
				if (!foundInExportedPacakges) {
					joiner.add(dependency);
				}
				bundle.setHeader(Constants.EXPORT_PACKAGE, joiner.toString());
			}
		}
	}

	/**
	 * Tells if the given {@link IResource} is a plug-in project.
	 * 
	 * @param resource
	 *            the {@link IResource} to test
	 * @return <code>true</code> if the given {@link IResource} is a plug-in project, <code>false</code>
	 *         otherwise
	 */
	protected boolean isInPluginProject(IResource resource) {
		return propertyTester.test(resource, AcceleoPropertyTester.IS_IN_PLUGIN_PROJECT, null, Boolean.TRUE);
	}

	/**
	 * Tells if the given {@link IResource} is a Maven project.
	 * 
	 * @param resource
	 *            the {@link IResource} to test
	 * @return <code>true</code> if the given {@link IResource} is a plug-in project, <code>false</code>
	 *         otherwise
	 */
	protected boolean isInMavenProject(IResource resource) {
		return propertyTester.test(resource, AcceleoPropertyTester.IS_IN_MAVEN_PROJECT, null, Boolean.TRUE);
	}

	/**
	 * Gets the Acceleo version lower bound.
	 * 
	 * @return the Acceleo version lower bound
	 */
	protected String getAcceleoVersionLowerBound() {
		final Version version = AcceleoUIPlugin.getDefault().getBundle().getVersion();

		return new Version(version.getMajor(), version.getMinor(), version.getMicro()).toString();
	}

	/**
	 * Gets the Acceleo version upper bound.
	 * 
	 * @return the Acceleo version upper bound
	 */
	protected String getAcceleoVersionUpperBound() {
		final Version version = AcceleoUIPlugin.getDefault().getBundle().getVersion();

		return new Version(version.getMajor() + 1, 0, 0).toString();
	}

	/**
	 * Gets the Acceleo version lower bound.
	 * 
	 * @return the Acceleo version lower bound
	 */
	protected String getAQLVersionLowerBound() {
		final Version version;

		final ClassLoader aqlClassloader = AQLUtils.class.getClassLoader();
		if (aqlClassloader instanceof BundleReference) {
			version = ((BundleReference)aqlClassloader).getBundle().getVersion();
		} else {
			version = new Version(8, 0, 0);
		}

		return new Version(version.getMajor(), version.getMinor(), version.getMicro()).toString();
	}

	/**
	 * Gets the AQL version upper bound.
	 * 
	 * @return the AQL version upper bound
	 */
	protected String getAQLVersionUpperBound() {
		final Version version;

		final ClassLoader aqlClassloader = AQLUtils.class.getClassLoader();
		if (aqlClassloader instanceof BundleReference) {
			version = ((BundleReference)aqlClassloader).getBundle().getVersion();
		} else {
			version = new Version(8, 0, 0);
		}

		return new Version(version.getMajor() + 1, 0, 0).toString();
	}

	/**
	 * Gets the ANTLR version lower bound.
	 * 
	 * @return the ANTLR version lower bound
	 */
	protected String getANTLRVersionLowerBound() {
		final Version version;

		final ClassLoader aqlClassloader = Lexer.class.getClassLoader();
		if (aqlClassloader instanceof BundleReference) {
			version = ((BundleReference)aqlClassloader).getBundle().getVersion();
		} else {
			version = new Version(4, 10, 0);
		}

		return new Version(version.getMajor(), version.getMinor(), version.getMicro()).toString();
	}

	/**
	 * Gets the ANTLR version upper bound.
	 * 
	 * @return the ANTLR version upper bound
	 */
	protected String getANTLRVersionUpperBound() {
		final Version version;

		final ClassLoader antlrClassloader = Lexer.class.getClassLoader();
		if (antlrClassloader instanceof BundleReference) {
			version = ((BundleReference)antlrClassloader).getBundle().getVersion();
		} else {
			version = new Version(4, 10, 1);
		}

		return new Version(version.getMajor(), version.getMinor(), version.getMicro() + 1).toString();
	}

	/**
	 * Gets the {@link IWriterFactory}.
	 * 
	 * @return the {@link IWriterFactory}
	 */
	protected IWriterFactory getWriterFactory() {
		if (writeractory == null) {
			writeractory = createWriterFactory();
		}
		return writeractory;
	}

	/**
	 * Creates a {@link IWriterFactory}.
	 * 
	 * @return the created {@link IWriterFactory}
	 */
	protected IWriterFactory createWriterFactory() {
		return new AcceleoUIWorkspaceWriterFactory(preview);
	}

	/**
	 * Gets the preview {@link Map}.
	 * 
	 * @return the preview {@link Map}
	 */
	public Map<URI, String> getPreview() {
		final Map<URI, String> res;

		if (preview != null) {
			res = preview;
		} else {
			res = Collections.emptyMap();
		}

		return res;
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
	protected boolean addMavenDependencies(Document pom, Element project,
			List<MavenDependency> dependencies) {
		boolean res = false;

		// get already existing dependencies
		final Set<String> knownDependencies = new HashSet<>();
		final NodeList dependenciesList = project.getElementsByTagName("dependencies");
		final Node dependenciesNode;
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
			dependenciesNode = dependenciesList.item(0);
		} else {
			dependenciesNode = pom.createElement("dependencies");
			project.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION));
			project.appendChild(dependenciesNode);
			dependenciesNode.appendChild(pom.createTextNode(System.lineSeparator() + MAVEN_INDENTATION));
			project.appendChild(pom.createTextNode(System.lineSeparator()));

		}
		for (MavenDependency dependency : dependencies) {
			if (!knownDependencies.contains(dependency.getArtifactId())) {
				addMavenDependencyNode(pom, dependenciesNode, dependency.getGroupId(), dependency
						.getArtifactId(), dependency.getVersion());
				res = true;
			}
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
	protected void addMavenDependencyNode(Document pom, Node dependencies, String groupIdString,
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

	protected String getQualifiedNameFromSourceFile(IFile file) {
		String res = null;

		final IPath filePath = file.getFullPath();
		final IJavaProject project = JavaCore.create(file.getProject());
		try {
			for (IClasspathEntry entry : project.getResolvedClasspath(true)) {
				if (entry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
					final IPath entryPath = entry.getPath();
					final IPath relativePath = filePath.makeRelativeTo(entryPath);
					if (!filePath.toString().equals(relativePath.toString())) {
						res = relativePath.toString().substring(0, relativePath.toString().length()
								- relativePath.getFileExtension().length() - 1).replace("/",
										AcceleoParser.QUALIFIER_SEPARATOR);
						break;
					}
				}
			}
		} catch (JavaModelException e) {
			AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, getClass(),
					"can't get qualified name for " + file.getFullPath(), e));
		}

		return res;
	}

	protected Module loadModelModule(URI moduleURI, String qualifiedName) {
		Module res;

		final AcceleoParser parser = new AcceleoParser();
		try {
			final String encoding;
			try (InputStream is = URIConverter.INSTANCE.createInputStream(moduleURI)) {
				encoding = parser.parseEncoding(is);
			}
			try (InputStream is = URIConverter.INSTANCE.createInputStream(moduleURI)) {
				if (is != null) {
					res = parser.parse(is, encoding, qualifiedName).getModule();
				} else {
					res = null;
				}
			}
		} catch (IOException e) {
			AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, getClass(),
					"can't load model module " + moduleURI, e));
			res = null;
		}

		return res;
	}

}
