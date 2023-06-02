/*******************************************************************************
 * Copyright (c) 2015, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
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

import org.eclipse.acceleo.query.ast.Binding;
import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.ast.Error;
import org.eclipse.acceleo.query.ast.ErrorBinding;
import org.eclipse.acceleo.query.ast.ErrorCall;
import org.eclipse.acceleo.query.ast.ErrorConditional;
import org.eclipse.acceleo.query.ast.ErrorEClassifierTypeLiteral;
import org.eclipse.acceleo.query.ast.ErrorEnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorExpression;
import org.eclipse.acceleo.query.ast.ErrorStringLiteral;
import org.eclipse.acceleo.query.ast.ErrorTypeLiteral;
import org.eclipse.acceleo.query.ast.ErrorVariableDeclaration;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.Let;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.ast.util.AstSwitch;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.IServiceCompletionProposal;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.CompletionServices;
import org.eclipse.acceleo.query.runtime.impl.completion.TextCompletionProposal;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;

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

		final List<Error> errors = validationRes.getAstResult().getErrors();
		if (errors.size() > 0) {
			final Error errorToComplete = getErrorToComplete(validationRes.getAstResult(), errors);
			completeVariablesNames(errorToComplete);
			result = doSwitch(errorToComplete);
		} else {
			// no need for variables here since "expression variable" can't be valid
			final Set<IType> possibleTypes = validationResult.getPossibleTypes(validationResult.getAstResult()
					.getAst());
			result = getExpressionTextFollows(possibleTypes);
		}

		return result;
	}

	/**
	 * Gets the {@link Error} to use for completion starting point. It's the first error that
	 * {@link AstResult#getEndPosition(Expression) end} at the end of the {@link Expression}.
	 * 
	 * @param astResult
	 *            the {@link AstResult}
	 * @param errors
	 *            the possible {@link Error}
	 * @return the {@link Error} to use for completion starting point
	 */
	private Error getErrorToComplete(AstResult astResult, List<Error> errors) {
		Error result = errors.get(0);

		int currentEnd = astResult.getEndPosition(result);
		for (int i = 1; i < errors.size(); i++) {
			final Error error = errors.get(i);
			int end = astResult.getEndPosition(error);
			if (end > currentEnd) {
				currentEnd = end;
				result = error;
			}
		}

		return result;
	}

	/**
	 * Completes variable names according to the given {@link Expression}. It adds {@link Let} and
	 * {@link Lambda} declarations if needed.
	 * 
	 * @param exp
	 *            the {@link Expression} to use as starting point
	 */
	private void completeVariablesNames(Expression exp) {
		Expression current = exp;
		while (current != null) {
			if (current instanceof Let) {
				final Let let = (Let)current;
				for (Binding binding : let.getBindings()) {
					if (binding.getName() != null) {
						variableNames.add(binding.getName());
					}
				}
			} else if (current instanceof Lambda) {
				final Lambda lambda = (Lambda)current;
				for (VariableDeclaration declaration : lambda.getParameters()) {
					if (declaration.getName() != null) {
						variableNames.add(declaration.getName());
					}
				}
			}
			if (current.eContainer() instanceof Expression) {
				current = (Expression)current.eContainer();
			} else {
				current = null;
			}
		}
		Collections.sort(variableNames);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorExpression(org.eclipse.acceleo.query.ast.ErrorExpression)
	 */
	@Override
	public List<ICompletionProposal> caseErrorExpression(ErrorExpression object) {
		final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

		result.addAll(getExpressionProposals());

		return result;
	}

	/**
	 * Gets {@link org.eclipse.acceleo.query.ast.Expression Expression} proposals.
	 * 
	 * @return result {@link org.eclipse.acceleo.query.ast.Expression Expression} proposals
	 */
	private List<ICompletionProposal> getExpressionProposals() {
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
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorTypeLiteral(org.eclipse.acceleo.query.ast.ErrorTypeLiteral)
	 */
	@Override
	public List<ICompletionProposal> caseErrorTypeLiteral(ErrorTypeLiteral object) {
		final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

		result.add(new TextCompletionProposal(AstSerializer.STRING_TYPE, 0));
		result.add(new TextCompletionProposal(AstSerializer.INTEGER_TYPE, 0));
		result.add(new TextCompletionProposal(AstSerializer.REAL_TYPE, 0));
		result.add(new TextCompletionProposal(AstSerializer.BOOLEAN_TYPE, 0));
		result.add(new TextCompletionProposal("Sequence()", 1));
		result.add(new TextCompletionProposal("OrderedSet()", 1));
		result.add(new TextCompletionProposal("{}", 1));
		result.addAll(getEClassifierCompletion(null, null, null));

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorEClassifierTypeLiteral(org.eclipse.acceleo.query.ast.ErrorEClassifierTypeLiteral)
	 */
	@Override
	public List<ICompletionProposal> caseErrorEClassifierTypeLiteral(ErrorEClassifierTypeLiteral object) {
		return getEClassifierCompletion(object.getEPackageName(), object.getEClassifierName(), null);
	}

	/**
	 * Gets the {@link List} of {@link ICompletionProposal} for the given {@link ErrorTypeLiteral}.
	 * 
	 * @param ePackageName
	 *            the {@link EPackage#getName() ePackageName}
	 * @param eClassifierName
	 *            the {@link EClassifier#getName() ePackageName}
	 * @param eEnumLiteralName
	 *            the {@link ErrorEnumLiteral#getName() ePackageName}
	 * @return the {@link List} of {@link ICompletionProposal} for the given {@link ErrorTypeLiteral}
	 */
	protected List<ICompletionProposal> getEClassifierCompletion(String ePackageName, String eClassifierName,
			String eEnumLiteralName) {
		final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

		if (ePackageName == null) {
			result.addAll(services.getEClassifierProposals());
			result.addAll(services.getEEnumLiteralProposals());
		} else if (eClassifierName == null) {
			result.addAll(services.getEClassifierProposals(ePackageName));
			result.addAll(services.getEEnumLiteralProposals(ePackageName));
		} else if (eEnumLiteralName == null) {
			result.addAll(services.getEEnumLiteralProposals(ePackageName, eClassifierName));
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorEnumLiteral(org.eclipse.acceleo.query.ast.ErrorEnumLiteral)
	 */
	@Override
	public List<ICompletionProposal> caseErrorEnumLiteral(ErrorEnumLiteral object) {
		final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

		result.addAll(services.getEEnumLiteralProposals(object.getEPackageName(), object.getEEnumName()));

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorCollectionCall(org.eclipse.acceleo.query.ast.ErrorCall)
	 */
	@Override
	public List<ICompletionProposal> caseErrorCall(ErrorCall object) {
		final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

		if (!object.isMissingEndParenthesis()) {
			final Set<IType> collectionTypes = new LinkedHashSet<IType>();

			final Set<IType> possibleReceiverTypes = validationResult.getPossibleTypes(object.getArguments()
					.get(0));
			if (possibleReceiverTypes.isEmpty()) {
				collectionTypes.add(new SetType(services.getQueryEnvironment(), new NothingType(
						"Argument collection will be empty for call to " + object.getServiceName())));
			} else {
				for (IType type : possibleReceiverTypes) {
					collectionTypes.add(getCollectionTypes(object, type));
				}
			}
			result.addAll(services.getServiceProposals(collectionTypes, object.getType()));
		} else {
			if (object.getArguments().size() == 1) {
				result.addAll(getExpressionProposals());
			} else {
				final Expression firstArg = object.getArguments().get(1);
				if (firstArg instanceof Lambda) {
					final Lambda lambda = (Lambda)firstArg;
					result.addAll(getExpressionTextFollows(validationResult.getPossibleTypes(lambda
							.getExpression())));
				} else {
					result.add(new TextCompletionProposal(", ", 0));
				}
			}
			result.add(new TextCompletionProposal(")", 0));
		}

		return result;
	}

	/**
	 * Gets collection {@link IType} according to the {@link CallType}.
	 * 
	 * @param errorCall
	 *            the {@link ErrorCall}
	 * @param type
	 *            the {@link IType}
	 * @return the collection {@link IType}
	 */
	private IType getCollectionTypes(ErrorCall errorCall, IType type) {
		final IType result;

		if (type instanceof ICollectionType) {
			if (errorCall.getType() == CallType.CALLORAPPLY) {
				result = ((ICollectionType)type).getCollectionType();
			} else {
				result = type;
			}
		} else {
			if (errorCall.getType() == CallType.COLLECTIONCALL) {
				// the arrow is an implicit set conversion
				result = new SetType(services.getQueryEnvironment(), type);
			} else {
				result = type;
			}
		}

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

		if (object.getName() == null) {
			result.addAll(services.getVariableDeclarationProposals(validationResult.getPossibleTypes(object
					.getExpression())));
		} else if (object.getType() == null) {
			result.add(new TextCompletionProposal(": ", 0));
			result.add(new TextCompletionProposal("| ", 0));
		} else if (object.getType() instanceof ErrorTypeLiteral) {
			result.addAll(doSwitch(object.getType()));
		} else {
			result.add(new TextCompletionProposal("| ", 0));
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorStringLiteral(org.eclipse.acceleo.query.ast.ErrorStringLiteral)
	 */
	@Override
	public List<ICompletionProposal> caseErrorStringLiteral(ErrorStringLiteral object) {
		final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorBinding(org.eclipse.acceleo.query.ast.ErrorBinding)
	 */
	@Override
	public List<ICompletionProposal> caseErrorBinding(ErrorBinding object) {
		final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

		if (object.getName() != null) {
			if (object.getType() instanceof ErrorTypeLiteral) {
				result.addAll(doSwitch(object.getType()));
			} else {
				if (object.getType() == null) {
					result.add(new TextCompletionProposal(": ", 0));
				}
				if (object.getValue() == null) {
					result.add(new TextCompletionProposal("= ", 0));
				}
				if (object.getValue() instanceof ErrorExpression) {
					result.addAll(doSwitch(object.getValue()));
				}
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorConditional(org.eclipse.acceleo.query.ast.ErrorConditional)
	 */
	@Override
	public List<ICompletionProposal> caseErrorConditional(ErrorConditional object) {
		final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

		if (object.getFalseBranch() != null) {
			final Set<IType> possibleTypes = validationResult.getPossibleTypes(object.getFalseBranch());
			result.addAll(getExpressionTextFollows(possibleTypes));
			result.add(new TextCompletionProposal("endif ", 0));
		} else if (object.getTrueBranch() != null) {
			final Set<IType> possibleTypes = validationResult.getPossibleTypes(object.getTrueBranch());
			result.addAll(getExpressionTextFollows(possibleTypes));
			result.add(new TextCompletionProposal("else ", 0));
		} else if (object.getPredicate() != null) {
			final Set<IType> possibleTypes = validationResult.getPossibleTypes(object.getPredicate());
			result.addAll(getExpressionTextFollows(possibleTypes));
			result.add(new TextCompletionProposal("then ", 0));
		}

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
		result.add(new TextCompletionProposal("Sequence{}", 1));
		result.add(new TextCompletionProposal("OrderedSet{}", 1));
		result.add(new TextCompletionProposal(AstBuilderListener.LET_OPERATOR + SPACE, 0));
		result.add(new TextCompletionProposal(AstBuilderListener.CONDITIONAL_OPERATOR + SPACE, 0));

		return result;
	}

	/**
	 * Gets the {@link TextCompletionProposal} following an {@link org.eclipse.acceleo.query.ast.Expression
	 * Expression}.
	 * 
	 * @param possibleTypes
	 *            possible types of the {@link org.eclipse.acceleo.query.ast.Expression Expression}
	 * @return the {@link TextCompletionProposal} following an {@link org.eclipse.acceleo.query.ast.Expression
	 *         Expression}
	 */
	private List<ICompletionProposal> getExpressionTextFollows(Set<IType> possibleTypes) {
		final List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();

		final List<ICompletionProposal> servicesProposal = services.getServiceProposals(possibleTypes, null);
		final Set<String> serviceNames = new HashSet<String>();
		for (ICompletionProposal proposal : servicesProposal) {
			if (proposal instanceof IServiceCompletionProposal) {
				serviceNames.add(((IServiceCompletionProposal)proposal).getObject().getName());
			}
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
