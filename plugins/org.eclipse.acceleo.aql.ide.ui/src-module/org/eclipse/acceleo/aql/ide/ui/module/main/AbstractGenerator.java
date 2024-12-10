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

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringJoiner;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.query.parser.AstValidator;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.eclipse.pde.internal.core.ibundle.IBundle;
import org.eclipse.pde.internal.core.ibundle.IBundlePluginModelBase;
import org.eclipse.pde.internal.core.ibundle.IManifestHeader;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleReference;
import org.osgi.framework.Constants;

public abstract class AbstractGenerator {

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

		return res;
	}

	/**
	 * Adds needed plugin dependencies to the given {@link IProject}.
	 * 
	 * @param project
	 *            the {@link IProject}
	 * @param dependencyBundleNames
	 *            the {@link Set} of dependency bundle names
	 */
	@SuppressWarnings("restriction")
	protected void addPluginDependencies(IProject project, Set<String> dependencyBundleNames) {
		final IPluginModelBase model = PluginRegistry.findModel(project);
		if (model instanceof IBundlePluginModelBase) {
			final IBundle bundle = ((IBundlePluginModelBase)model).getBundleModel().getBundle();
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
					boolean foundInRequirement = false;
					for (String requirement : requiredBundleString.split(",")) {
						if (requirement.contains(dependency)) {
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

			((IBundlePluginModelBase)model).save();
		}
	}

	/**
	 * Adds needed plugin dependencies to the given {@link IProject}.
	 * 
	 * @param project
	 *            the {@link IProject}
	 * @param packages
	 *            the {@link Set} of package names
	 */
	@SuppressWarnings("restriction")
	protected void addExportPackages(IProject project, Set<String> packages) {
		final IPluginModelBase model = PluginRegistry.findModel(project);
		if (model instanceof IBundlePluginModelBase) {
			final IBundle bundle = ((IBundlePluginModelBase)model).getBundleModel().getBundle();
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

			((IBundlePluginModelBase)model).save();
		}
	}

}
