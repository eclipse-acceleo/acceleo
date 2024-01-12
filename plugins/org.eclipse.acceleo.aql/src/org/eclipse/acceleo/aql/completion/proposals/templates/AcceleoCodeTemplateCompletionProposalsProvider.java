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
package org.eclipse.acceleo.aql.completion.proposals.templates;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.BlockComment;
import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.Documentation;
import org.eclipse.acceleo.Error;
import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleDocumentation;
import org.eclipse.acceleo.ModuleElementDocumentation;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.aql.completion.AcceleoAstCompletor;
import org.eclipse.acceleo.aql.completion.AcceleoCompletor;
import org.eclipse.acceleo.aql.completion.proposals.AcceleoCompletionProposal;
import org.eclipse.acceleo.aql.completion.proposals.AcceleoCompletionProposalsProvider;
import org.eclipse.acceleo.util.AcceleoSwitch;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * Part of {@link AcceleoCompletionProposalsProvider} in charge of providing code template completion
 * proposals. The argument passed to this provider represents the Acceleo type(s) syntactically valid at the
 * completion location.
 * 
 * @author Florent Latombe
 * @see AcceleoCodeTemplates
 * @see AcceleoAstCompletor
 * @see AcceleoCompletor
 */
public class AcceleoCodeTemplateCompletionProposalsProvider extends AcceleoSwitch<List<AcceleoCompletionProposal>> {

	/**
	 * A new line in the description of an {@link AcceleoCompletionProposal}.
	 */
	private static final String NEWLINE = AcceleoCompletionProposal.DESCRIPTION_NEWLINE;

	/**
	 * Opening tag for a code sample in the description of an {@link AcceleoCompletionProposal}.
	 */
	private static final String CODE_OPEN = AcceleoCompletionProposal.DESCRIPTION_CODE_OPEN;

	/**
	 * Closing tag for a code sample in the description of an {@link AcceleoCompletionProposal}.
	 */
	private static final String CODE_CLOSE = AcceleoCompletionProposal.DESCRIPTION_CODE_CLOSE;

	/**
	 * Code template completion proposal for a new {@link Import}.
	 */
	public static final AcceleoCodeTemplateCompletionProposal NEW_IMPORT = new AcceleoCodeTemplateCompletionProposal(
			"New Import", "Inserts the following sample Import:" + NEWLINE + CODE_OPEN
					+ AcceleoCodeTemplates.NEW_IMPORT + CODE_CLOSE, AcceleoCodeTemplates.NEW_IMPORT,
			AcceleoPackage.Literals.IMPORT);

	/**
	 * Code template completion proposal for a new {@link Query}.
	 */
	public static final AcceleoCodeTemplateCompletionProposal NEW_QUERY = new AcceleoCodeTemplateCompletionProposal(
			"New Query", "Inserts the following sample Acceleo Query:" + NEWLINE + CODE_OPEN
					+ AcceleoCodeTemplates.NEW_QUERY + CODE_CLOSE, AcceleoCodeTemplates.NEW_QUERY,
			AcceleoPackage.Literals.QUERY);

	/**
	 * Code template completion proposal for a new {@link Comment}.
	 */
	public static final AcceleoCodeTemplateCompletionProposal NEW_COMMENT = new AcceleoCodeTemplateCompletionProposal(
			"New Comment", "Inserts the following sample Comment:" + NEWLINE + CODE_OPEN
					+ AcceleoCodeTemplates.NEW_COMMENT + CODE_CLOSE, AcceleoCodeTemplates.NEW_COMMENT,
			AcceleoPackage.Literals.COMMENT);

	/**
	 * Code template completion proposal for a new '@main' {@link Comment}.
	 */
	public static final AcceleoCodeTemplateCompletionProposal NEW_COMMENT_MAIN = new AcceleoCodeTemplateCompletionProposal(
			"@Main Annotation Comment", "Inserts the following Comment:" + NEWLINE + CODE_OPEN
					+ AcceleoCodeTemplates.NEW_COMMENT_MAIN + CODE_CLOSE,
			AcceleoCodeTemplates.NEW_COMMENT_MAIN, AcceleoPackage.Literals.COMMENT);

	/**
	 * The computed module name.
	 */
	private String computedModuleName;

	/**
	 * The new line {@link String}.
	 */
	private final String newLine;

	/**
	 * The {@link AcceleoCodeTemplates} with the {@link #newLine}.
	 */
	private final AcceleoCodeTemplates acceleoCodeTemplates;

	/**
	 * Constructor.
	 * 
	 * @param newLine
	 *            the new line {@link String}
	 */
	public AcceleoCodeTemplateCompletionProposalsProvider(String newLine) {
		this.newLine = newLine;
		this.acceleoCodeTemplates = new AcceleoCodeTemplates(newLine);
	}

	/**
	 * Provides the code template completion proposals for a position where the given Acceleo type is
	 * syntactically allowed.
	 * 
	 * @param computedModuleName
	 *            the computed module name
	 * @param acceleoEClass
	 *            the (non-{@code null}) Acceleo {@link EClass}.
	 * @return the {@link List} of {@link AcceleoCompletionProposal}.
	 * @see AcceleoPackage
	 */
	public List<AcceleoCompletionProposal> getProposalsFor(String computedModuleName, EClass acceleoEClass) {
		this.computedModuleName = computedModuleName;
		return this.doSwitch(acceleoEClass, null);
	}

	/**
	 * Code template completion proposal for a new {@link ModuleElementDocumentation}.
	 */
	public AcceleoCodeTemplateCompletionProposal newModuleElementDocumentation() {
		return new AcceleoCodeTemplateCompletionProposal("New Module Element Documentation",
				"Inserts the following sample Module Element Documentation:" + newLine + acceleoCodeTemplates
						.newModuleElementDocumentation(), acceleoCodeTemplates
								.newModuleElementDocumentation(),
				AcceleoPackage.Literals.MODULE_ELEMENT_DOCUMENTATION);
	}

	/**
	 * Code template completion proposal for a new {@link ModuleDocumentation}.
	 */
	public AcceleoCodeTemplateCompletionProposal newModuleDocumentation() {
		return new AcceleoCodeTemplateCompletionProposal("New Module Documentation",
				"Inserts the following sample Module Documentation:" + newLine + acceleoCodeTemplates
						.newModuleDocumentation(), acceleoCodeTemplates.newModuleDocumentation(),
				AcceleoPackage.Literals.MODULE_DOCUMENTATION);
	}

	/**
	 * Code template completion proposal for a new {@link Template}.
	 */
	public AcceleoCodeTemplateCompletionProposal newTemplate() {
		return new AcceleoCodeTemplateCompletionProposal("New Template",
				"Inserts the following sample Acceleo Template:" + NEWLINE + CODE_OPEN + acceleoCodeTemplates
						.newTemplate() + CODE_CLOSE, acceleoCodeTemplates.newTemplate(),
				AcceleoPackage.Literals.TEMPLATE);
	}

	/**
	 * Code template completion proposal for a new {@link BlockComment}.
	 */
	public AcceleoCodeTemplateCompletionProposal newBlockComment() {
		return new AcceleoCodeTemplateCompletionProposal("New Block Comment",
				"Inserts the following sample Block Comment:" + NEWLINE + CODE_OPEN + acceleoCodeTemplates
						.newBlockComment() + CODE_CLOSE, acceleoCodeTemplates.newBlockComment(),
				AcceleoPackage.Literals.BLOCK_COMMENT);
	}

	@Override
	public List<AcceleoCompletionProposal> defaultCase(EObject object) {
		return new ArrayList<>();
	}

	@Override
	public List<AcceleoCompletionProposal> caseImport(Import object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(NEW_IMPORT);

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseQuery(Query object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(NEW_QUERY);

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseTemplate(Template object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(newTemplate());

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseModule(Module object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(getModuleCodeTemplate(computedModuleName));

		return completionProposals;
	}

	/**
	 * Gets the module {@link AcceleoCompletionProposal} for the given computed module name.
	 * 
	 * @param computedModuleName
	 *            the computed module name
	 * @return the module {@link AcceleoCompletionProposal} for the given computed module name
	 */
	public static AcceleoCompletionProposal getModuleCodeTemplate(String computedModuleName) {
		final String moduleCodeTemplate = AcceleoCodeTemplates.getModuleCodeTemplate(computedModuleName);
		return new AcceleoCodeTemplateCompletionProposal("New Module",
				"Inserts the following sample Acceleo Module:" + NEWLINE + CODE_OPEN + moduleCodeTemplate
						+ CODE_CLOSE, moduleCodeTemplate, AcceleoPackage.Literals.MODULE);

	}

	@Override
	public List<AcceleoCompletionProposal> caseComment(Comment object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(NEW_COMMENT);

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseBlockComment(BlockComment object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(newBlockComment());

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseDocumentation(Documentation object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseModuleDocumentation(ModuleDocumentation object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(newModuleDocumentation());

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseModuleElementDocumentation(ModuleElementDocumentation object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(newModuleElementDocumentation());

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseError(Error object) {
		throw new IllegalArgumentException("This provider must no be used on the Error part of the AST.");
	}
}
