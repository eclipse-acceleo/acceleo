/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.location.aql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.acceleo.aql.location.common.AbstractLocationLink;
import org.eclipse.acceleo.aql.parser.AcceleoAstUtils;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.Error;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.Literal;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.ast.util.AstSwitch;
import org.eclipse.acceleo.query.parser.AstEvaluator;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.parser.AstValidator;
import org.eclipse.acceleo.query.parser.CombineIterator;
import org.eclipse.acceleo.query.runtime.EvaluationResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.EvaluationServices;
import org.eclipse.acceleo.query.runtime.impl.Nothing;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.IType;

/**
 * An {@link AstSwitch AQL Switch} to provide, for an element from an AQL AST, the {@link List} of
 * {@link AbstractLocationLink} that points to its definition location(s).
 * 
 * @author Florent Latombe
 */
public class AqlDefinitionLocator extends AstSwitch<List<AbstractLocationLink<?, ?>>> {

	/**
	 * The {@link IQueryEnvironment}.
	 */
	private final IQueryEnvironment queryEnvironment;

	/**
	 * The {@link EvaluationServices}. It is able to lookup services and variables.
	 */
	private final EvaluationServices aqlEvaluationServices;

	/**
	 * The {@link AstEvaluator}. It may be required to partially evaluate AQL Expressions so we can determine
	 * their types.
	 */
	private final AstEvaluator aqlEvaluator;

	/**
	 * The {@link AstValidator}, which we need to retrieve the types of the expressions passed as arguments to
	 * service calls.
	 */
	private final AstValidator aqlValidator;

	/**
	 * The {@link AqlVariablesLocalContext} that holds information about the variables.
	 */
	private final AqlVariablesLocalContext aqlVariablesContext;

	/**
	 * The contextual {@link AstResult}.
	 */
	private final AstResult aqlAstResult;

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the (non-{@code null}) {@link IQueryEnvironment}.
	 * @param aqlAstResult
	 *            the (non-{@code null}) {@link AstResult} containing the element(s) that will be passed as
	 *            argument to this.
	 * @param aqlVariablesContext
	 *            the (non-{@code null}) {@link AqlVariablesLocalContext.
	 */
	public AqlDefinitionLocator(IQueryEnvironment queryEnvironment, AstResult aqlAstResult,
			AqlVariablesLocalContext aqlVariablesContext) {
		this.queryEnvironment = Objects.requireNonNull(queryEnvironment);
		this.aqlAstResult = Objects.requireNonNull(aqlAstResult);

		this.aqlEvaluationServices = new EvaluationServices(this.queryEnvironment);
		this.aqlEvaluator = new AstEvaluator(aqlEvaluationServices);

		this.aqlVariablesContext = Objects.requireNonNull(aqlVariablesContext);

		this.aqlValidator = new AstValidator(new ValidationServices(this.queryEnvironment));
	}

	/**
	 * For a {@link VariableDeclaration}, it is its own definition.
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseVariableDeclaration(org.eclipse.acceleo.query.ast.VariableDeclaration)
	 */
	@Override
	public List<AbstractLocationLink<?, ?>> caseVariableDeclaration(VariableDeclaration variableDeclaration) {
		return Collections.singletonList(new AqlLocationLinkToAql(variableDeclaration, variableDeclaration));
	}

	/**
	 * For an AQL {@link Expression}, we 'evaluate' it using a specific runtime that binds not to the intended
	 * value of the expression, but the to the AST elements that define the expression term.
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseExpression(org.eclipse.acceleo.query.ast.Expression)
	 */
	@Override
	public List<AbstractLocationLink<?, ?>> caseExpression(Expression expression) {
		EvaluationResult evaluationResult = aqlEvaluator.eval(this.aqlVariablesContext
				.getVariableDefinitions(), expression);
		Object expressionDefiningElement = evaluationResult.getResult();
		if (expressionDefiningElement == null || expressionDefiningElement instanceof Nothing) {
			return new ArrayList<>();
		} else {
			return Collections.singletonList(new AqlLocationLinkToAny(expression, expressionDefiningElement));
		}
	}

	/**
	 * For most {@link Literal}, there is no "definition" to go to.
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseLiteral(org.eclipse.acceleo.query.ast.Literal)
	 */
	@Override
	public List<AbstractLocationLink<?, ?>> caseLiteral(Literal literal) {
		return new ArrayList<>();
	}

	@Override
	public List<AbstractLocationLink<?, ?>> caseTypeLiteral(TypeLiteral typeLiteral) {
		return Collections.singletonList(new AqlLocationLinkToAny(typeLiteral, typeLiteral.getValue()));
	}

	@Override
	public List<AbstractLocationLink<?, ?>> caseEnumLiteral(EnumLiteral enumLiteral) {
		return Collections.singletonList(new AqlLocationLinkToAny(enumLiteral, enumLiteral.getLiteral()));
	}

	@Override
	public List<AbstractLocationLink<?, ?>> caseCall(Call call) {
		this.aqlValidator.validate(this.aqlVariablesContext.getVariableTypes(), this.aqlAstResult);

		// Retrieve all the IServices which fit the name and argument types.
		List<IService> candidateServices = new ArrayList<>();

		// Name
		String serviceName = call.getServiceName();

		// Argument Types - which are expressions whose type must first be evaluated.
		List<Set<IType>> argumentTypes = call.getArguments().stream().map(argument -> {
			AstResult aqlAstOfArgument = AcceleoAstUtils.getAqlAstResultOfAqlAstElement(argument);
			IValidationResult aqlValidationResultOfArgument = this.aqlValidator.validate(
					this.aqlVariablesContext.getVariableTypes(), aqlAstOfArgument);
			Set<IType> argumentPossibleTypes = aqlValidationResultOfArgument.getPossibleTypes(argument);
			return argumentPossibleTypes;
		}).collect(Collectors.toList());

		CombineIterator<IType> it = new CombineIterator<IType>(argumentTypes);
		while (it.hasNext()) {
			List<IType> currentArgTypes = it.next();
			IService service = this.queryEnvironment.getLookupEngine().lookup(serviceName, currentArgTypes
					.toArray(new IType[currentArgTypes.size()]));
			if (service != null) {
				candidateServices.add(service);
			}
		}

		// Return links to all the candidates.
		return candidateServices.stream().map(service -> new AqlLocationLinkToAny(call, service.getOrigin()))
				.collect(Collectors.toList());
	}

	@Override
	public List<AbstractLocationLink<?, ?>> caseError(Error object) {
		return new ArrayList<>();
	}
}
