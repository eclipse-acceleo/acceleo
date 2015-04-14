/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.parser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Error;
import org.eclipse.acceleo.query.ast.ErrorCollectionCall;
import org.eclipse.acceleo.query.ast.ErrorExpression;
import org.eclipse.acceleo.query.ast.ErrorFeatureAccessOrCall;
import org.eclipse.acceleo.query.ast.ErrorTypeLiteral;
import org.eclipse.acceleo.query.ast.ErrorVariableDeclaration;
import org.eclipse.acceleo.query.ast.util.AstSwitch;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.CompletionServices;
import org.eclipse.acceleo.query.runtime.impl.completion.ServiceCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.TextCompletionProposal;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;

/**
 * Gives {@link ICompletionProposal} for a given {@link IValidationResult}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AstCompletor extends AstSwitch<List<ICompletionProposal>> {

	/**
	 * A space.
	 */
	private static final String SPACE = " ";

	/**
	 * The {@link CompletionServices}.
	 */
	private final CompletionServices services;

	/**
	 * The {@link Set} of variable names.
	 */
	private List<String> variableNames;

	/**
	 * The {@link IValidationResult}.
	 */
	private IValidationResult validationResult;

	/**
	 * Constructor.
	 * 
	 * @param services
	 *            the {@link CompletionServices}.
	 */
	public AstCompletor(CompletionServices services) {
		this.services = services;
	}

	/**
	 * Gets the {@link List} of {@link ICompletionProposal} for the given {@link IValidationResult}.
	 * 
	 * @param varNames
	 *            the {@link Set} of variable names
	 * @param validationRes
	 *            the {@link IValidationResult} to complete.
	 * @return the {@link List} of {@link ICompletionProposal}
	 */
	public List<ICompletionProposal> getProposals(Set<String> varNames, IValidationResult validationRes) {
		final List<ICompletionProposal> result;

		this.validationResult = validationRes;
		this.variableNames = new ArrayList<String>(varNames);
		Collections.sort(variableNames);
		final List<Error> errors = validationRes.getAstResult().getErrors();
		if (errors.size() > 0) {
			result = doSwitch(errors.get(0));
		} else {
			final Set<IType> possibleTypes = validationResult.getPossibleTypes(validationResult
					.getAstResult().getAst());
			result = getExpressionTextFollows(possibleTypes);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorExpression(org.eclipse.acceleo.query.ast.ErrorExpression)
	 */
	@Override
	public List<ICompletionProposal> caseErrorExpression(ErrorExpression object) {
		final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

		result.addAll(getExpressionTextPrefixes());
		result.addAll(services.getVariableProposals(variableNames));
		result.addAll(services.getEClassifierProposals());
		result.addAll(services.getEEnumLiteralProposals());

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorFeatureAccessOrCall(org.eclipse.acceleo.query.ast.ErrorFeatureAccessOrCall)
	 */
	@Override
	public List<ICompletionProposal> caseErrorFeatureAccessOrCall(ErrorFeatureAccessOrCall object) {
		final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

		final Set<IType> possibleTypes = validationResult.getPossibleTypes(object.getTarget());
		result.addAll(services.getEStructuralFeatureProposals(possibleTypes));
		result.addAll(services.getServiceProposals(possibleTypes, false));
		result.addAll(services.getEOperationProposals(possibleTypes));

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorTypeLiteral(org.eclipse.acceleo.query.ast.ErrorTypeLiteral)
	 */
	@Override
	public List<ICompletionProposal> caseErrorTypeLiteral(ErrorTypeLiteral object) {
		final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

		if (object.getSegments().size() == 0) {
			result.addAll(services.getEClassifierProposals());
			result.addAll(services.getEEnumLiteralProposals());
		} else if (object.getSegments().size() == 1) {
			result.addAll(services.getEClassifierProposals(object.getSegments().get(0)));
			result.addAll(services.getEEnumLiteralProposals(object.getSegments().get(0)));
		} else if (object.getSegments().size() == 2) {
			result.addAll(services.getEEnumLiteralProposals(object.getSegments().get(0), object.getSegments()
					.get(1)));
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorCollectionCall(org.eclipse.acceleo.query.ast.ErrorCollectionCall)
	 */
	@Override
	public List<ICompletionProposal> caseErrorCollectionCall(ErrorCollectionCall object) {
		final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

		final Set<IType> collectionTypes = new LinkedHashSet<IType>();
		for (IType type : validationResult.getPossibleTypes(object.getTarget())) {
			collectionTypes.add(new SequenceType(services.getQueryEnvironment(), type));
			collectionTypes.add(new SetType(services.getQueryEnvironment(), type));
		}
		result.addAll(services.getServiceProposals(collectionTypes, false));

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorVariableDeclaration(org.eclipse.acceleo.query.ast.ErrorVariableDeclaration)
	 */
	@Override
	public List<ICompletionProposal> caseErrorVariableDeclaration(ErrorVariableDeclaration object) {
		final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

		result.addAll(services.getVariableDeclarationProposals(validationResult.getPossibleTypes(object
				.getExpression())));

		return result;
	}

	/**
	 * Gets the {@link TextCompletionProposal} prefixes of an {@link org.eclipse.acceleo.query.ast.Expression
	 * Expression}.
	 * 
	 * @return the {@link TextCompletionProposal} prefixes of an
	 *         {@link org.eclipse.acceleo.query.ast.Expression Expression}
	 */
	public List<ICompletionProposal> getExpressionTextPrefixes() {
		final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

		result.add(new TextCompletionProposal(AstBuilderListener.NOT_OPERATOR + SPACE, 0));
		result.add(new TextCompletionProposal(AstBuilderListener.UNARY_MIN_OPERATOR + SPACE, 0));
		result.add(new TextCompletionProposal("()", 1));
		result.add(new TextCompletionProposal("true", 0));
		result.add(new TextCompletionProposal("false", 0));
		result.add(new TextCompletionProposal("null", 0));
		result.add(new TextCompletionProposal("{}", 1));
		result.add(new TextCompletionProposal("[]", 1));
		result.add(new TextCompletionProposal("Sequence{}", 1));
		result.add(new TextCompletionProposal("OrderedSet{}", 1));
		result.add(new TextCompletionProposal(AstBuilderListener.LET_OPERATOR + SPACE, 0));

		return result;
	}

	/**
	 * Gets the {@link TextCompletionProposal} following an {@link org.eclipse.acceleo.query.ast.Expression
	 * Expression}.
	 * 
	 * @param possibleTypes
	 *            possible types of the {@link org.eclipse.acceleo.query.ast.Expression Expression}
	 * @return the {@link TextCompletionProposal} following an
	 *         {@link org.eclipse.acceleo.query.ast.Expression Expression}
	 */
	private List<ICompletionProposal> getExpressionTextFollows(Set<IType> possibleTypes) {
		final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

		final List<ServiceCompletionProposal> servicesProposal = services.getServiceProposals(possibleTypes,
				true);
		final Set<String> serviceNames = new HashSet<String>();
		for (ServiceCompletionProposal proposal : servicesProposal) {
			serviceNames.add(proposal.getObject().getServiceMethod().getName());
		}

		if (serviceNames.contains(AstBuilderListener.ADD_SERVICE_NAME)) {
			result.add(new TextCompletionProposal(AstBuilderListener.ADD_OPERATOR + SPACE, 0));
		}
		if (serviceNames.contains(AstBuilderListener.SUB_SERVICE_NAME)) {
			result.add(new TextCompletionProposal(AstBuilderListener.SUB_OPERATOR + SPACE, 0));
		}
		if (serviceNames.contains(AstBuilderListener.LESS_THAN_EQUAL_SERVICE_NAME)) {
			result.add(new TextCompletionProposal(AstBuilderListener.LESS_THAN_EQUAL_OPERATOR + SPACE, 0));
		}
		if (serviceNames.contains(AstBuilderListener.GREATER_THAN_EQUAL_SERVICE_NAME)) {
			result.add(new TextCompletionProposal(AstBuilderListener.GREATER_THAN_EQUAL_OPERATOR + SPACE, 0));
		}
		if (serviceNames.contains(AstBuilderListener.DIFFERS_SERVICE_NAME)) {
			result.add(new TextCompletionProposal(AstBuilderListener.DIFFERS_OPERATOR + SPACE, 0));
		}
		if (serviceNames.contains(AstBuilderListener.EQUALS_SERVICE_NAME)) {
			result.add(new TextCompletionProposal(AstBuilderListener.EQUALS_OPERATOR + SPACE, 0));
		}
		if (serviceNames.contains(AstBuilderListener.LESS_THAN_SERVICE_NAME)) {
			result.add(new TextCompletionProposal(AstBuilderListener.LESS_THAN_OPERATOR + SPACE, 0));
		}
		if (serviceNames.contains(AstBuilderListener.GREATER_THAN_SERVICE_NAME)) {
			result.add(new TextCompletionProposal(AstBuilderListener.GREATER_THAN_OPERATOR + SPACE, 0));
		}
		if (serviceNames.contains(AstBuilderListener.MULT_SERVICE_NAME)) {
			result.add(new TextCompletionProposal(AstBuilderListener.MULT_OPERATOR + SPACE, 0));
		}
		if (serviceNames.contains(AstBuilderListener.DIV_SERVICE_NAME)) {
			result.add(new TextCompletionProposal(AstBuilderListener.DIV_OPERATOR + SPACE, 0));
		}
		if (serviceNames.contains(AstBuilderListener.AND_SERVICE_NAME)) {
			result.add(new TextCompletionProposal(AstBuilderListener.AND_OPERATOR + SPACE, 0));
		}
		if (serviceNames.contains(AstBuilderListener.OR_SERVICE_NAME)) {
			result.add(new TextCompletionProposal(AstBuilderListener.OR_OPERATOR + SPACE, 0));
		}
		if (serviceNames.contains(AstBuilderListener.XOR_SERVICE_NAME)) {
			result.add(new TextCompletionProposal(AstBuilderListener.XOR_OPERATOR + SPACE, 0));
		}
		if (serviceNames.contains(AstBuilderListener.IMPLIES_SERVICE_NAME)) {
			result.add(new TextCompletionProposal(AstBuilderListener.IMPLIES_OPERATOR + SPACE, 0));
		}
		return result;
	}

}
