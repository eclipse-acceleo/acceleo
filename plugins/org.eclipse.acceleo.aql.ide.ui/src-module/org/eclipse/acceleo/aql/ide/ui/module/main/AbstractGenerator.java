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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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
import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.parser.AstValidator;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.core.expressions.IPropertyTester;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.pde.internal.core.ibundle.IBundle;
import org.eclipse.pde.internal.core.ibundle.IBundleModel;
import org.eclipse.pde.internal.core.ibundle.IManifestHeader;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleReference;
import org.osgi.framework.Constants;
import org.osgi.framework.Version;

public abstract class AbstractGenerator {

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

		IWriterFactory factory = getWriterFactory();
		if (preview != null) {
			res = preview;
		} else {
			res = Collections.emptyMap();
		}

		return res;
	}

}
