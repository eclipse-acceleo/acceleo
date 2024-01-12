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
package org.eclipse.acceleo.aql.completion.proposals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Binding;
import org.eclipse.acceleo.BlockComment;
import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.Documentation;
import org.eclipse.acceleo.Error;
import org.eclipse.acceleo.ExpressionStatement;
import org.eclipse.acceleo.FileStatement;
import org.eclipse.acceleo.ForStatement;
import org.eclipse.acceleo.IfStatement;
import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.LetStatement;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleDocumentation;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.ModuleElementDocumentation;
import org.eclipse.acceleo.ProtectedArea;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.aql.completion.proposals.syntax.AcceleoSyntacticCompletionProposals;
import org.eclipse.acceleo.aql.completion.proposals.templates.AcceleoCodeTemplateCompletionProposal;
import org.eclipse.acceleo.aql.completion.proposals.templates.AcceleoCodeTemplateCompletionProposalsProvider;
import org.eclipse.acceleo.aql.completion.proposals.templates.AcceleoCodeTemplates;
import org.eclipse.acceleo.util.AcceleoSwitch;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * Provides both the syntactic and code templates {@link AcceleoCompletionProposal completion proposals} for a
 * location where the passed argument is syntactically valid.
 * 
 * @author Florent Latombe
 */
public class AcceleoCompletionProposalsProvider extends AcceleoSwitch<List<AcceleoCompletionProposal>> {

	/**
	 * The {@link AcceleoCodeTemplateCompletionProposalsProvider} that provides code template completion
	 * proposals.
	 */
	private final AcceleoCodeTemplateCompletionProposalsProvider acceleoCodeTemplatesProvider;

	/**
	 * The computed module name.
	 */
	private String computedModuleName;

	/**
	 * Constructor.
	 * 
	 * @param newLine
	 *            the new line {@link String}
	 */
	public AcceleoCompletionProposalsProvider(String newLine) {
		this.acceleoCodeTemplatesProvider = new AcceleoCodeTemplateCompletionProposalsProvider(newLine);
	}

	/**
	 * Provides the syntactic and code template completion proposals for a position where the given Acceleo
	 * type is syntactically allowed.
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

	@Override
	public List<AcceleoCompletionProposal> defaultCase(EObject object) {
		return new ArrayList<>();
	}

	@Override
	public List<AcceleoCompletionProposal> caseComment(Comment object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(AcceleoSyntacticCompletionProposals.COMMENT_START);
		completionProposals.addAll(this.acceleoCodeTemplatesProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.COMMENT));

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseBlockComment(BlockComment object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(AcceleoSyntacticCompletionProposals.BLOCK_COMMENT_START);
		completionProposals.addAll(this.acceleoCodeTemplatesProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.BLOCK_COMMENT));

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseImport(Import object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(AcceleoSyntacticCompletionProposals.IMPORT_START);
		completionProposals.addAll(this.acceleoCodeTemplatesProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.IMPORT));

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseDocumentation(Documentation object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(AcceleoSyntacticCompletionProposals.DOCUMENTATION_START);

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseModuleDocumentation(ModuleDocumentation object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.addAll(this.caseDocumentation(object));
		completionProposals.addAll(this.acceleoCodeTemplatesProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.MODULE_DOCUMENTATION));

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseModuleElementDocumentation(ModuleElementDocumentation object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.addAll(this.caseDocumentation(object));
		completionProposals.addAll(this.acceleoCodeTemplatesProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.MODULE_ELEMENT_DOCUMENTATION));

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseModule(Module object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(AcceleoSyntacticCompletionProposals.MODULE_HEADER_START);
		completionProposals.addAll(this.acceleoCodeTemplatesProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.MODULE));

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseModuleElement(ModuleElement object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.addAll(this.caseQuery(null));
		completionProposals.addAll(this.caseTemplate(null));

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseQuery(Query object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(AcceleoSyntacticCompletionProposals.QUERY_START);
		completionProposals.addAll(this.acceleoCodeTemplatesProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.QUERY));

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseTemplate(Template object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(AcceleoSyntacticCompletionProposals.TEMPLATE_HEADER_START);
		completionProposals.addAll(this.acceleoCodeTemplatesProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.TEMPLATE));

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseStatement(Statement object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.addAll(this.caseIfStatement(null));
		completionProposals.addAll(this.caseForStatement(null));
		completionProposals.addAll(this.caseLetStatement(null));
		completionProposals.addAll(this.caseExpressionStatement(null));
		completionProposals.addAll(this.caseFileStatement(null));
		completionProposals.addAll(this.caseProtectedArea(null));

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseFileStatement(FileStatement object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(AcceleoSyntacticCompletionProposals.STATEMENT_FILE_HEADER_START);
		completionProposals.addAll(this.acceleoCodeTemplatesProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.FILE_STATEMENT));

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseIfStatement(IfStatement object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(AcceleoSyntacticCompletionProposals.STATEMENT_IF_HEADER_START);
		completionProposals.addAll(this.acceleoCodeTemplatesProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.IF_STATEMENT));

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseLetStatement(LetStatement object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(AcceleoSyntacticCompletionProposals.STATEMENT_LET_HEADER_START);
		completionProposals.addAll(this.acceleoCodeTemplatesProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.LET_STATEMENT));

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseBinding(Binding object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		String sampleVariableName = AcceleoCodeTemplates.DEFAULT_NEW_BINDING_VARIABLE_NAME;
		completionProposals.add(new AcceleoCodeTemplateCompletionProposal(sampleVariableName,
				sampleVariableName, AcceleoPackage.Literals.BINDING));
		completionProposals.addAll(this.acceleoCodeTemplatesProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.BINDING));

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseForStatement(ForStatement object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(AcceleoSyntacticCompletionProposals.STATEMENT_FOR_HEADER_START);
		completionProposals.addAll(this.acceleoCodeTemplatesProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.FOR_STATEMENT));

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseProtectedArea(ProtectedArea object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(AcceleoSyntacticCompletionProposals.STATEMENT_PROTECTED_AREA_HEADER_START);
		completionProposals.addAll(this.acceleoCodeTemplatesProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.PROTECTED_AREA));

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseExpressionStatement(ExpressionStatement object) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		completionProposals.add(AcceleoSyntacticCompletionProposals.STATEMENT_EXPRESSION_START);
		completionProposals.addAll(this.acceleoCodeTemplatesProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.EXPRESSION_STATEMENT));

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseError(Error object) {
		throw new IllegalArgumentException("This provider must no be used on the Error part of the AST.");
	}

}
