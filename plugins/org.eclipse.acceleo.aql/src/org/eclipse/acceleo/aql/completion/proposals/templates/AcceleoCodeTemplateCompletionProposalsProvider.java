/*******************************************************************************
 * Copyright (c) 2020, 2021 Obeo.
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
	 * Code template completion proposal for a new {@link ModuleElementDocumentation}.
	 */
	public static final AcceleoCodeTemplateCompletionProposal NEW_MODULE_ELEMENT_DOCUMENTATION = new AcceleoCodeTemplateCompletionProposal(
			"New Module Element Documentation", "Inserts the following sample Module Element Documentation:\n"
					+ AcceleoCodeTemplates.NEW_MODULE_ELEMENT_DOCUMENTATION,
			AcceleoCodeTemplates.NEW_MODULE_ELEMENT_DOCUMENTATION,
			AcceleoPackage.Literals.MODULE_ELEMENT_DOCUMENTATION);

	/**
	 * Code template completion proposal for a new {@link ModuleDocumentation}.
	 */
	public static final AcceleoCodeTemplateCompletionProposal NEW_MODULE_DOCUMENTATION = new AcceleoCodeTemplateCompletionProposal(
			"New Module Documentation", "Inserts the following sample Module Documentation:\n"
					+ AcceleoCodeTemplates.NEW_MODULE_DOCUMENTATION,
			AcceleoCodeTemplates.NEW_MODULE_DOCUMENTATION, AcceleoPackage.Literals.MODULE_DOCUMENTATION);

	/**
	 * Code template completion proposal for a new {@link Query}.
	 */
	public static final AcceleoCodeTemplateCompletionProposal NEW_QUERY = new AcceleoCodeTemplateCompletionProposal(
			"New Query", "Inserts the following sample Acceleo Query:" + NEWLINE + CODE_OPEN
					+ AcceleoCodeTemplates.NEW_QUERY + CODE_CLOSE, AcceleoCodeTemplates.NEW_QUERY,
			AcceleoPackage.Literals.QUERY);

	/**
	 * Code template completion proposal for a new {@link Template}.
	 */
	public static final AcceleoCodeTemplateCompletionProposal NEW_TEMPLATE = new AcceleoCodeTemplateCompletionProposal(
			"New Template", "Inserts the following sample Acceleo Template:" + NEWLINE + CODE_OPEN
					+ AcceleoCodeTemplates.NEW_TEMPLATE + CODE_CLOSE, AcceleoCodeTemplates.NEW_TEMPLATE,
			AcceleoPackage.Literals.TEMPLATE);

	/**
	 * Code template completion proposal for a new {@link Module}.
	 */
	public static final AcceleoCodeTemplateCompletionProposal NEW_MODULE = new AcceleoCodeTemplateCompletionProposal(
			"New Module", "Inserts the following sample Acceleo Module:" + NEWLINE + CODE_OPEN
					+ AcceleoCodeTemplates.NEW_MODULE + CODE_CLOSE, AcceleoCodeTemplates.NEW_MODULE,
			AcceleoPackage.Literals.MODULE);

	/**
	 * Code template completion proposal for a new {@link Comment}.
	 */
	public static final AcceleoCodeTemplateCompletionProposal NEW_COMMENT = new AcceleoCodeTemplateCompletionProposal(
			"New Comment", "Inserts the following sample Comment:" + NEWLINE + CODE_OPEN
					+ AcceleoCodeTemplates.NEW_COMMENT + CODE_CLOSE, AcceleoCodeTemplates.NEW_COMMENT,
			AcceleoPackage.Literals.COMMENT);

	/**
	 * Code template completion proposal for a new {@link BlockComment}.
	 */
	public static final AcceleoCodeTemplateCompletionProposal NEW_BLOCK_COMMENT = new AcceleoCodeTemplateCompletionProposal(
			"New Block Comment", "Inserts the following sample Block Comment:" + NEWLINE + CODE_OPEN
					+ AcceleoCodeTemplates.NEW_BLOCK_COMMENT + CODE_CLOSE,
			AcceleoCodeTemplates.NEW_BLOCK_COMMENT, AcceleoPackage.Literals.BLOCK_COMMENT);

	/**
	 * Code template completion proposal for a new '@main' {@link Comment}.
	 */
	public static final AcceleoCodeTemplateCompletionProposal NEW_COMMENT_MAIN = new AcceleoCodeTemplateCompletionProposal(
			"@Main Annotation Comment", "Inserts the following Comment:" + NEWLINE + CODE_OPEN
					+ AcceleoCodeTemplates.NEW_COMMENT_MAIN + CODE_CLOSE,
			AcceleoCodeTemplates.NEW_COMMENT_MAIN, AcceleoPackage.Literals.COMMENT);

	/**
	 * Provides the code template completion proposals for a position where the given Acceleo type is
	 * syntactically allowed.
	 * 
	 * @param acceleoEClass
	 *            the (non-{@code null}) Acceleo {@link EClass}.
	 * @return the {@link List} of {@link AcceleoCompletionProposal}.
	 * @see AcceleoPackage
	 */
	public List<AcceleoCompletionProposal> getProposalsFor(EClass acceleoEClass) {
		return this.doSwitch(acceleoEClass, null);
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

		completionProposals.add(NEW_TEMPLATE);

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseModule(Module object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(NEW_MODULE);

		return completionProposals;
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

		completionProposals.add(NEW_BLOCK_COMMENT);

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

		completionProposals.add(NEW_MODULE_DOCUMENTATION);

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseModuleElementDocumentation(ModuleElementDocumentation object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(NEW_MODULE_ELEMENT_DOCUMENTATION);

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseError(Error object) {
		throw new IllegalArgumentException("This provider must no be used on the Error part of the AST.");
	}
}
