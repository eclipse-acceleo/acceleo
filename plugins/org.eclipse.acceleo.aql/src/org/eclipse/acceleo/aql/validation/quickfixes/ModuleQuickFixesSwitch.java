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
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.ErrorMargin;
import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.parser.AcceleoAstSerializer;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.validation.AcceleoValidator;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.ast.ASTNode;
import org.eclipse.acceleo.query.parser.quickfixes.AstQuickFix;
import org.eclipse.acceleo.query.parser.quickfixes.AstTextReplacement;
import org.eclipse.acceleo.query.parser.quickfixes.CreateResource;
import org.eclipse.acceleo.query.parser.quickfixes.IAstQuickFix;
import org.eclipse.acceleo.query.parser.quickfixes.IAstResourceChange;
import org.eclipse.acceleo.query.parser.quickfixes.IAstTextReplacement;
import org.eclipse.acceleo.query.parser.quickfixes.MoveResource;
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

		final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
		this.uri = resolver.getSourceURI(moduleQualifiedName);
	}

	@Override
	public List<IAstQuickFix> caseModule(Module object) {
		final List<IAstQuickFix> res = new ArrayList<>();

		if (needRename(module)) {
			final String[] segments = uri.getPath().split("/");
			if (segments.length > 0) {
				final String lastSegment = segments[segments.length - 1];
				final String newModuleName = lastSegment.substring(0, lastSegment.length()
						- AcceleoParser.MODULE_FILE_EXTENSION.length() - 1);
				if (isJavaIdentifier(newModuleName)) {
					res.add(getRenameModuleQuickFix(uri, module, newModuleName));
				}
			}
			res.add(getRenameModuleResourceQuickFix(uri, module));
		}

		return res;
	}

	/**
	 * Tells if the given {@link Module} needs renaming.
	 * 
	 * @param module
	 *            the {@link Module}
	 * @return <code>true</code> if the given {@link Module} needs renaming, <code>false</code> otherwise
	 */
	private boolean needRename(Module module) {
		return validationResult.getValidationMessages(module).stream().anyMatch(m -> m.getMessage().contains(
				AcceleoValidator.DOESN_T_MATCH_RESOURCE_NAME));
	}

	/**
	 * Tells if the given name is a valid Java identifier.
	 * 
	 * @param name
	 *            the name to check
	 * @return <code>true</code> if the given name is a valid Java identifier, <code>false</code>
	 */
	private boolean isJavaIdentifier(String name) {
		final boolean res;

		if (name != null && !name.isBlank()) {
			final char[] chars = name.toCharArray();
			if (Character.isJavaIdentifierStart(chars[0])) {
				boolean hasNotIdentifierPart = false;
				for (int i = 1; i < chars.length; i++) {
					if (!Character.isJavaIdentifierPart(chars[i])) {
						hasNotIdentifierPart = true;
						break;
					}
				}
				res = !hasNotIdentifierPart;
			} else {
				res = false;
			}
		} else {
			res = false;
		}

		return res;
	}

	/**
	 * Get the rename {@link Module} {@link IAstQuickFix} for the given {@link Module} to match the given
	 * {@link URI} resource name.
	 * 
	 * @param uri
	 *            the {@link Module} {@link URI}
	 * @param module
	 *            the {@link Module} to rename
	 * @param newModuleName
	 *            the new {@link Module} {@link Module#getName() name}
	 * @return the rename {@link Module} {@link IAstQuickFix} for the given {@link Module} to match the given
	 *         {@link URI} resource name
	 */
	private IAstQuickFix getRenameModuleQuickFix(URI uri, Module module, String newModuleName) {
		final IAstQuickFix res = new AstQuickFix("Rename module " + module.getName() + " to "
				+ newModuleName);

		final AcceleoAstResult acceleoAstResult = validationResult.getAcceleoAstResult();
		final int startOffset = acceleoAstResult.getIdentifierStartPosition(module);
		final int startLine = acceleoAstResult.getIdentifierStartLine(module);
		final int startColumn = acceleoAstResult.getIdentifierStartColumn(module);
		final int endOffset = acceleoAstResult.getIdentifierEndPosition(module);
		final int endLine = acceleoAstResult.getIdentifierEndLine(module);
		final int endColumn = acceleoAstResult.getIdentifierEndColumn(module);
		res.getTextReplacements().add(new AstTextReplacement(uri, newModuleName, startOffset, startLine,
				startColumn, endOffset, endLine, endColumn));

		return res;
	}

	/**
	 * Get the rename {@link Module}'s resource {@link IAstQuickFix} for the given {@link Module} to match its
	 * {@link Module#getName() names}.
	 * 
	 * @param uri
	 *            the {@link Module} {@link URI}
	 * @param module
	 *            the {@link Module}
	 * @return the rename {@link Module}'s resource {@link IAstQuickFix} for the given {@link Module} to match
	 *         its {@link Module#getName() names}
	 */
	private IAstQuickFix getRenameModuleResourceQuickFix(URI uri, Module module) {
		final URI newUri = uri.resolve(module.getName() + "." + AcceleoParser.MODULE_FILE_EXTENSION);
		final IAstQuickFix res = new AstQuickFix("Rename module resource " + uri + " to " + newUri);

		res.getResourceChanges().add(new MoveResource(uri, newUri));

		final IQualifiedNameResolver resolver = queryEnvironment.getLookupEngine().getResolver();
		final String oldQualifiedName = moduleQualifiedName;
		final int lastIndexOfQualifierSeparator = oldQualifiedName.lastIndexOf(
				AcceleoParser.QUALIFIER_SEPARATOR);
		final String newQualifiedName;
		if (lastIndexOfQualifierSeparator >= 0) {
			newQualifiedName = oldQualifiedName.substring(0, lastIndexOfQualifierSeparator)
					+ AcceleoParser.QUALIFIER_SEPARATOR + module.getName();
		} else {
			newQualifiedName = module.getName();
		}
		// change extends and imports in dependent modules (ILoaders should probably handle this at some point
		// to be able to also change imports from non module)
		for (String dependent : resolver.getDependOn(oldQualifiedName)) {
			final Object resolved = resolver.resolve(dependent);
			if (resolved instanceof Module) {
				final Module dependentModule = (Module)resolved;
				final URI dependentModuleURI = resolver.getSourceURI(dependent);
				final List<ModuleReference> dependentModuleReferences = new ArrayList<>();
				if (dependentModule.getExtends() != null) {
					dependentModuleReferences.add(dependentModule.getExtends());
				}
				for (Import imp : dependentModule.getImports()) {
					dependentModuleReferences.add(imp.getModule());
				}
				for (ModuleReference dependentModuleReference : dependentModuleReferences) {
					if (oldQualifiedName.equals(dependentModuleReference.getQualifiedName())) {
						final AcceleoAstResult acceleoAstResult = dependentModule.getAst();
						final int startOffset = acceleoAstResult.getStartPosition(dependentModuleReference);
						final int startLine = acceleoAstResult.getStartLine(dependentModuleReference);
						final int startColumn = acceleoAstResult.getStartColumn(dependentModuleReference);
						final int endOffset = acceleoAstResult.getEndPosition(dependentModuleReference);
						final int endLine = acceleoAstResult.getEndLine(dependentModuleReference);
						final int endColumn = acceleoAstResult.getEndColumn(dependentModuleReference);
						res.getTextReplacements().add(new AstTextReplacement(dependentModuleURI,
								newQualifiedName, startOffset, startLine, startColumn, endOffset, endLine,
								endColumn));
					}
				}
			}
		}

		return res;
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
		final IAstTextReplacement contentReplacement = new AstTextReplacement(moduleURI, replacement, 0, 0, 0,
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
		final IAstTextReplacement contentReplacement = new AstTextReplacement(classURI, replacement, 0, 0, 0,
				0, 0, 0);
		fix.getTextReplacements().add(contentReplacement);

		return fix;
	}

	@Override
	public List<IAstQuickFix> caseErrorMargin(ErrorMargin errorMargin) {
		final List<IAstQuickFix> res = new ArrayList<>();

		final IAstQuickFix fix = new AstQuickFix("Indent text");
		final AcceleoAstResult acceleoAstResult = validationResult.getAcceleoAstResult();
		final int startOffset = acceleoAstResult.getStartPosition(errorMargin);
		final int startLine = acceleoAstResult.getStartLine(errorMargin);
		final int startColumn = acceleoAstResult.getStartColumn(errorMargin);
		final int blockIndentationColumn = getBlockIndentationColumn(acceleoAstResult, moduleText,
				(Block)errorMargin.eContainer());
		final StringBuilder indentation = new StringBuilder();
		for (int i = 0; i < blockIndentationColumn - startColumn; i++) {
			indentation.append(" ");
		}
		fix.getTextReplacements().add(new AstTextReplacement(uri, indentation.toString(), startOffset,
				startLine, startColumn, startOffset, startLine, startColumn));
		res.add(fix);

		return res;
	}

	/**
	 * Gets the given {@link Block} indentation column.
	 * 
	 * @param acceleoAstResult
	 *            the {@link AcceleoAstResult}
	 * @param text
	 *            the module text
	 * @param block
	 *            the {@link Block}
	 * @return the given {@link Block} indentation column
	 */
	private int getBlockIndentationColumn(AcceleoAstResult acceleoAstResult, String text, Block block) {
		int res = acceleoAstResult.getStartColumn(block) + AcceleoParser.INDENTATION;

		int currentPosition = acceleoAstResult.getStartPosition(block);
		while (text.charAt(currentPosition) != '[' && currentPosition > 0) {
			currentPosition--;
			res--;
		}

		return res;
	}

}
