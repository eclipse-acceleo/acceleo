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
package org.eclipse.acceleo.aql.ide.ui.module.services;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.query.ast.EClassifierTypeLiteral;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

/**
 * Java services.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class Services {

	/**
	 * The {@link IQualifiedNameQueryEnvironment}.
	 */
	private static IQualifiedNameQueryEnvironment queryEnvironment;

	/**
	 * The workspace {@link IQualifiedNameResolver}.
	 */
	private static IQualifiedNameResolver workspaceResolver;

	/**
	 * Sets the {@link IQualifiedNameQueryEnvironment}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 * @param workspaceResolver
	 *            the workspace {@link IQualifiedNameQueryEnvironment}
	 */
	public static void initialize(IQualifiedNameQueryEnvironment queryEnvironment,
			IQualifiedNameResolver workspaceResolver) {
		Services.queryEnvironment = queryEnvironment;
		Services.workspaceResolver = workspaceResolver;
	}

	/**
	 * Gets the qualified name for the given {@link Module}.
	 * 
	 * @param module
	 *            the {@link Module}
	 * @return the qualified name for the given {@link Module}
	 */
	public String getQualifiedName(Module module) {
		final String res;

		final String tmpQualifiedName = module.eResource().getURI().toString();
		final int firstSeparatorIndex = tmpQualifiedName.indexOf(AcceleoParser.QUALIFIER_SEPARATOR);
		if (firstSeparatorIndex >= 0) {
			res = tmpQualifiedName.substring(firstSeparatorIndex + AcceleoParser.QUALIFIER_SEPARATOR
					.length());
		} else {
			res = "";
		}

		return res;
	}

	/**
	 * Gets the Java package for the given {@link Module}.
	 * 
	 * @param module
	 *            the {@link Module}
	 * @return the Java package for the given {@link Module}
	 */
	public String getJavaPackage(Module module) {
		final String res;

		final String qualifiedName = getQualifiedName(module);
		final int lastSeparatorIndex = qualifiedName.lastIndexOf(AcceleoParser.QUALIFIER_SEPARATOR);
		if (lastSeparatorIndex >= 0) {
			res = qualifiedName.substring(0, lastSeparatorIndex).replace(AcceleoParser.QUALIFIER_SEPARATOR,
					".");
		} else {
			res = "";
		}

		return res;
	}

	/**
	 * Gets the current user name.
	 * 
	 * @param object
	 *            any object
	 * @return the current user name
	 */
	public String getUserName(Object object) {
		return System.getProperty("user.name");
	}

	/**
	 * Gets the Java import statement for the given {@link EPackage}.
	 * 
	 * @param ePkg
	 *            the {@link EPackage}
	 * @return the Java import statement for the given {@link EPackage}
	 */
	public String getJavaImport(EPackage ePkg) {
		return "import " + ePkg.getClass().getInterfaces()[0].getCanonicalName() + ";";
	}

	/**
	 * Gets the Java initialization statement for the given {@link EPackage}.
	 * 
	 * @param ePkg
	 *            the {@link EPackage}
	 * @return the Java initialization statement for the given {@link EPackage}
	 */
	public String getJavaInitialize(EPackage ePkg) {
		return ePkg.getClass().getInterfaces()[0].getSimpleName() + ".eINSTANCE.getName();";
	}

	/**
	 * Gets the {@link Set} all {@link EPackage} used by the given {@link Module} and direct and indirect
	 * {@link Module} dependencies.
	 * 
	 * @param module
	 *            the {@link Module}
	 * @return the {@link Set} all {@link EPackage} used by the given {@link Module} and direct and indirect
	 *         {@link Module} dependencies
	 */
	public Set<EPackage> getAllEPackages(Module module) {
		final Set<EPackage> res = new LinkedHashSet<>();

		final Set<String> qualifiedNames = new LinkedHashSet<>();
		final String moduleQualifiedName = workspaceResolver.getQualifiedName(module);
		qualifiedNames.add(moduleQualifiedName);
		qualifiedNames.addAll(workspaceResolver.getDependOn(moduleQualifiedName));

		for (String qualifiedName : qualifiedNames) {
			final Object resolved = workspaceResolver.resolve(qualifiedName);
			if (resolved instanceof Module) {
				final Module child = (Module)resolved;
				for (Metamodel metamodel : child.getMetamodels()) {
					if (metamodel.getReferencedPackage() != null) {
						res.add(metamodel.getReferencedPackage());
					}
				}
			}
		}

		return res;
	}

	/**
	 * Gets the receiver qualified {@link Class} name for the given {@link Module}.
	 * 
	 * @param module
	 *            the {@link Module}
	 * @return the receiver qualified {@link Class} name for the given {@link Module}
	 */
	public String getReceiverQualifiedClassName(Module module) {
		final String res;

		final List<Template> mains = AcceleoUtil.getMainTemplates(module);
		if (!mains.isEmpty()) {
			final Template main = mains.get(0);
			if (!main.getParameters().isEmpty()) {
				final Variable receiver = main.getParameters().get(0);
				if (receiver.getTypeAql() instanceof EClassifierTypeLiteral) {
					final EClassifierTypeLiteral type = (EClassifierTypeLiteral)receiver.getTypeAql();
					final EClassifier eClassifier = queryEnvironment.getEPackageProvider().getTypes(type
							.getEPackageName(), type.getEClassifierName()).iterator().next();
					res = eClassifier.getInstanceClassName();
				} else {
					res = "";
				}
			} else {
				res = "";
			}
		} else {
			res = "";
		}

		return res;
	}

	/**
	 * Gets the receiver {@link Class} name for the given {@link Module}.
	 * 
	 * @param module
	 *            the {@link Module}
	 * @return the receiver {@link Class} name for the given {@link Module}
	 */
	public String getReceiverClassName(Module module) {
		final String res;

		final String qualifiedClassName = getReceiverQualifiedClassName(module);
		final int lastDotIndex = qualifiedClassName.lastIndexOf(".");
		if (lastDotIndex >= 0) {
			res = qualifiedClassName.substring(lastDotIndex + 1);
		} else {
			res = "";
		}

		return res;
	}

}
