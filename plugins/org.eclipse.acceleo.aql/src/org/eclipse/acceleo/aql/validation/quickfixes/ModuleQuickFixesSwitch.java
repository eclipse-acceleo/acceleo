/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.validation.quickfixes;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.acceleo.aql.parser.AcceleoAstSerializer;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.ast.ASTNode;
import org.eclipse.acceleo.query.parser.Positions;
import org.eclipse.acceleo.query.parser.quickfixes.AstQuickFix;
import org.eclipse.acceleo.query.parser.quickfixes.AstTextReplacement;
import org.eclipse.acceleo.query.parser.quickfixes.CreateResource;
import org.eclipse.acceleo.query.parser.quickfixes.IAstQuickFix;
import org.eclipse.acceleo.query.parser.quickfixes.IAstResourceChange;
import org.eclipse.acceleo.query.runtime.impl.namespace.JavaLoader;
import org.eclipse.acceleo.query.runtime.namespace.ILoader;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.util.AcceleoSwitch;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Provides a {@link List} of {@link IAstQuickFix} for a given {@link ASTNode}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ModuleQuickFixesSwitch extends AcceleoSwitch<List<IAstQuickFix>> {

	/**
	 * The {@link IQualifiedNameQueryEnvironment}.
	 */
	private final IQualifiedNameQueryEnvironment queryEnvironment;

	/**
	 * The {@link IAcceleoValidationResult}.
	 */
	private IAcceleoValidationResult validationResult;

	/**
	 * the {@link Module}.
	 */
	private final Module module;

	/**
	 * The {@link Module} qualified name.
	 */
	private String moduleQualifiedName;

	/**
	 * the text representation of the {@link Module}.
	 */
	private String moduleText;

	/**
	 * Line and column for each offset in {@link #moduleText}.
	 */
	final int[][] linesAndColumns;

	/**
	 * The {@link Positions}.
	 */
	private final Positions<ASTNode> positions;

	/**
	 * The module {@link URI}.
	 */
	private final URI uri;

	/**
	 * The new line {@link String}.
	 */
	private final String newLine;

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IQualifiedNameQueryEnvironment}.
	 * @param validationResult
	 *            the {@link IAcceleoValidationResult}
	 * @param moduleQualifiedName
	 *            the {@link Module} qualified name
	 * @param moduleText
	 *            the text representation of the {@link Module}
	 * @param newLine
	 *            the new line {@link String}
	 */
	public ModuleQuickFixesSwitch(IQualifiedNameQueryEnvironment queryEnvironment,
			IAcceleoValidationResult validationResult, String moduleQualifiedName, String moduleText,
			String newLine) {
		this.queryEnvironment = queryEnvironment;
		this.validationResult = validationResult;
		this.module = validationResult.getAcceleoAstResult().getModule();
		this.moduleQualifiedName = moduleQualifiedName;
		this.moduleText = moduleText;
		this.linesAndColumns = AQLUtils.getLinesAndColumns(moduleText);
		this.newLine = newLine;

		this.positions = validationResult.getAcceleoAstResult().getPositions();

		final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
		this.uri = resolver.getSourceURI(moduleQualifiedName);
	}

	@Override
	public List<IAstQuickFix> caseModuleReference(ModuleReference moduleReference) {
		final List<IAstQuickFix> res = new ArrayList<>();

		if (moduleReference.getQualifiedName() != null) {
			final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
			final URI uri = resolver.getSourceURI(moduleQualifiedName);
			final String pathFragment = moduleQualifiedName.replace(AcceleoParser.QUALIFIER_SEPARATOR,
					ILoader.SLASH);
			final URI baseURI = URI.create(uri.toString().substring(0, uri.toString().lastIndexOf(
					pathFragment)));
			if (isServiceQualifiedName(moduleReference.getQualifiedName())) {
				if (moduleReference.eContainingFeature() != AcceleoPackage.eINSTANCE.getModule_Extends()) {
					res.add(getCreateClassQuickFix(baseURI, moduleReference));
				}
			} else {
				res.add(getCreateModuleQuickFix(baseURI, moduleReference));
			}
		}

		return res;
	}

	/**
	 * Tells if the given qualified name should contain java services.
	 * 
	 * @param qualifiedName
	 *            the qualified name
	 * @return <code>true</code> if the given qualified name should contain java services, <code>false</code>
	 *         otherwise
	 */
	private boolean isServiceQualifiedName(String qualifiedName) {
		return qualifiedName.contains(AcceleoParser.QUALIFIER_SEPARATOR + "services"
				+ AcceleoParser.QUALIFIER_SEPARATOR);
	}

	/**
	 * Gets the create module {@link IAstQuickFix} for the given {@link ModuleReference}.
	 * 
	 * @param baseURI
	 *            the base {@link URI}
	 * @param moduleReference
	 *            the {@link ModuleReference}
	 * @return the create module {@link IAstQuickFix} for the given {@link ModuleReference}
	 */
	private IAstQuickFix getCreateModuleQuickFix(URI baseURI, ModuleReference moduleReference) {
		final IAstQuickFix fix = new AstQuickFix("Create Module");

		final URI moduleURI = baseURI.resolve(moduleReference.getQualifiedName().replace(
				AcceleoParser.QUALIFIER_SEPARATOR, ILoader.SLASH) + ILoader.DOT
				+ AcceleoParser.MODULE_FILE_EXTENSION);
		final IAstResourceChange createModule = new CreateResource(moduleURI);
		fix.getResourceChanges().add(createModule);

		final Module newModule = AcceleoPackage.eINSTANCE.getAcceleoFactory().createModule();
		final String moduleName = moduleReference.getQualifiedName().substring(moduleReference
				.getQualifiedName().lastIndexOf(AcceleoParser.QUALIFIER_SEPARATOR)
				+ AcceleoParser.QUALIFIER_SEPARATOR.length(), moduleReference.getQualifiedName().length());
		newModule.setName(moduleName);
		final Collection<Metamodel> metamodels = EcoreUtil.copyAll(module.getMetamodels());
		newModule.getMetamodels().addAll(metamodels);
		final String replacement = new AcceleoAstSerializer(newLine).serialize(newModule);
		final AstTextReplacement contentReplacement = new AstTextReplacement(moduleURI, replacement, 0, 0, 0,
				0, 0, 0);
		fix.getTextReplacements().add(contentReplacement);

		return fix;
	}

	/**
	 * Gets the create class {@link IAstQuickFix} for the given {@link ModuleReference}.
	 * 
	 * @param baseURI
	 *            the base {@link URI}
	 * @param moduleReference
	 *            the {@link ModuleReference}
	 * @return the create class {@link IAstQuickFix} for the given {@link ModuleReference}
	 */
	private IAstQuickFix getCreateClassQuickFix(URI baseURI, ModuleReference moduleReference) {
		final IAstQuickFix fix = new AstQuickFix("Create Class");

		final URI classURI = baseURI.resolve(moduleReference.getQualifiedName().replace(
				AcceleoParser.QUALIFIER_SEPARATOR, ILoader.SLASH) + ILoader.DOT + JavaLoader.JAVA);
		final IAstResourceChange createClass = new CreateResource(classURI);
		fix.getResourceChanges().add(createClass);

		final int lastQualifierSeparatorIndex = moduleReference.getQualifiedName().lastIndexOf(
				AcceleoParser.QUALIFIER_SEPARATOR);
		final String packageName = moduleReference.getQualifiedName().substring(0,
				lastQualifierSeparatorIndex).replace(AcceleoParser.QUALIFIER_SEPARATOR, ILoader.DOT);
		final String className = moduleReference.getQualifiedName().substring(lastQualifierSeparatorIndex
				+ AcceleoParser.QUALIFIER_SEPARATOR.length(), moduleReference.getQualifiedName().length());
		final String replacement = "package " + packageName + ";" + newLine + newLine + "public class "
				+ className + " {" + newLine + newLine + "}" + newLine;
		final AstTextReplacement contentReplacement = new AstTextReplacement(classURI, replacement, 0, 0, 0,
				0, 0, 0);
		fix.getTextReplacements().add(contentReplacement);

		return fix;
	}

}
