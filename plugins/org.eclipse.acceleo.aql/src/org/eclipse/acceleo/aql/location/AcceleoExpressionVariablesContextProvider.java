/*******************************************************************************
 * Copyright (c) 2020, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.location;

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.acceleo.Binding;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.ForStatement;
import org.eclipse.acceleo.LetStatement;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.TypedElement;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.location.aql.AqlVariablesLocalContext;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.util.AcceleoSwitch;

/**
 * An {@link AcceleoSwitch} implementation that allows us to get, for an Acceleo term, the environment
 * variables it would usually set up for an AQL expression evaluation. Instead of associating a variable name
 * to its value though, we associate it to its definition location in the AST.
 * 
 * @author Florent Latombe
 */
public class AcceleoExpressionVariablesContextProvider extends AcceleoSwitch<AqlVariablesLocalContext> {

	/**
	 * The {@link IAcceleoValidationResult}.
	 */
	private final IAcceleoValidationResult acceleoValidationResult;

	/**
	 * Constructor.
	 * 
	 * @param acceleoValidationResult
	 *            the (non-{@code null}) {@link IAcceleoValidationResult}.
	 */
	public AcceleoExpressionVariablesContextProvider(IAcceleoValidationResult acceleoValidationResult) {
		this.acceleoValidationResult = acceleoValidationResult;
	}

	// Expression and TypedElement are the two entry points into this because these are the only ASTNodes that
	// contain AQL expressions.
	@Override
	public AqlVariablesLocalContext caseExpression(Expression expression) {
		AqlVariablesLocalContext variablesContext = new AqlVariablesLocalContext();
		variablesContext.addAllVariables(this.doSwitch(expression.eContainer()));
		return variablesContext;
	}

	@Override
	public AqlVariablesLocalContext caseTypedElement(TypedElement typedElement) {
		AqlVariablesLocalContext variablesContext = new AqlVariablesLocalContext();
		variablesContext.addAllVariables(this.doSwitch(typedElement.eContainer()));
		return variablesContext;
	}
	////

	// Cases that do not add variables to the environment, simply dispatch to their container.
	@Override
	public AqlVariablesLocalContext caseBlock(Block block) {
		AqlVariablesLocalContext variablesContext = new AqlVariablesLocalContext();
		variablesContext.addAllVariables(this.doSwitch(block.eContainer()));
		return variablesContext;
	}

	@Override
	public AqlVariablesLocalContext caseStatement(Statement statement) {
		AqlVariablesLocalContext variablesContext = new AqlVariablesLocalContext();
		variablesContext.addAllVariables(this.doSwitch(statement.eContainer()));
		return variablesContext;
	}
	////

	// Cases where the AST element brings variables into the context.
	@Override
	public AqlVariablesLocalContext caseQuery(Query query) {
		AqlVariablesLocalContext variablesContext = new AqlVariablesLocalContext();
		for (Variable parameter : query.getParameters()) {
			variablesContext.addAllVariables(this.getVariableStandaloneContext(parameter));
		}
		return variablesContext;
	}

	@Override
	public AqlVariablesLocalContext caseTemplate(Template template) {
		AqlVariablesLocalContext variablesContext = new AqlVariablesLocalContext();

		for (Variable parameter : template.getParameters()) {
			variablesContext.addAllVariables(this.getVariableStandaloneContext(parameter));
		}
		return variablesContext;
	}

	/**
	 * Case of a {@link Variable} that is in the container hierarchy of the initial argument. If that is not
	 * the case, use {@link #getVariableStandaloneContext(Variable)} instead.
	 * 
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseVariable(org.eclipse.acceleo.Variable)
	 */
	@Override
	public AqlVariablesLocalContext caseVariable(Variable variable) {
		AqlVariablesLocalContext variablesContext = new AqlVariablesLocalContext();

		// Order matters probably.
		variablesContext.addAllVariables(this.getVariableStandaloneContext(variable));
		variablesContext.addAllVariables(this.doSwitch(variable.eContainer()));

		return variablesContext;
	}

	@Override
	public AqlVariablesLocalContext caseBinding(Binding binding) {
		AqlVariablesLocalContext variablesContext = new AqlVariablesLocalContext();

		// Order matters probably.
		variablesContext.addAllVariables(this.getBindingStandaloneContext(binding, binding
				.eContainer() instanceof ForStatement));
		variablesContext.addAllVariables(this.doSwitch(binding.eContainer()));

		return variablesContext;
	}

	/**
	 * Provides the {@link AqlVariablesLocalContext} corresponding to the given {@link Variable}. If the
	 * {@link Variable} is supposed to be in the containment hierarchy of the initial argument, use
	 * {@link #caseVariable(Variable)} instead.
	 * 
	 * @param variable
	 *            the (non-{@code null}) {@link Variable}.
	 * @return the {@link AqlVariablesLocalContext} containing the variable defined by {@code variable}.
	 */
	private AqlVariablesLocalContext getVariableStandaloneContext(Variable variable) {
		AqlVariablesLocalContext variablesContext = new AqlVariablesLocalContext();

		final AstResult type = variable.getType();
		final Set<IType> possibleTypes = acceleoValidationResult.getValidationResult(type).getPossibleTypes(
				type.getAst());
		variablesContext.addVariable(variable.getName(), variable, possibleTypes);

		return variablesContext;
	}

	/**
	 * Provides the {@link AqlVariablesLocalContext} corresponding to the given {@link Binding}. If the
	 * {@link Binding} is supposed to be in the containment hierarchy of the initial argument, use
	 * {@link #caseBinding(Binding)} instead.
	 * 
	 * @param binding
	 *            the (non-{@code null}) {@link Binding}.
	 * @param extractCollectionTypes
	 *            <code>true</code> if collection types should be extracted, <code>false</code> otherwise
	 * @return the {@link AqlVariablesLocalContext} containing the variable defined by {@code variable}.
	 */
	private AqlVariablesLocalContext getBindingStandaloneContext(Binding binding,
			boolean extractCollectionTypes) {
		AqlVariablesLocalContext variablesContext = new AqlVariablesLocalContext();

		final AstResult expression = binding.getInitExpression().getAst();
		final Set<IType> possibleTypes = acceleoValidationResult.getValidationResult(expression)
				.getPossibleTypes(expression.getAst());
		if (extractCollectionTypes) {
			final Set<IType> extractedPossibleTypes = new LinkedHashSet<IType>();
			for (IType type : possibleTypes) {
				if (type instanceof ICollectionType) {
					extractedPossibleTypes.add(((ICollectionType)type).getCollectionType());
				} else {
					extractedPossibleTypes.add(type);
				}
			}
			variablesContext.addVariable(binding.getName(), binding, extractedPossibleTypes);
		} else {
			variablesContext.addVariable(binding.getName(), binding, possibleTypes);
		}

		return variablesContext;
	}

	@Override
	public AqlVariablesLocalContext caseForStatement(ForStatement forStatement) {
		AqlVariablesLocalContext variablesContext = new AqlVariablesLocalContext();
		variablesContext.addAllVariables(this.doSwitch(forStatement.eContainer()));
		variablesContext.addAllVariables(this.getBindingStandaloneContext(forStatement.getBinding(), true));
		return variablesContext;
	}

	@Override
	public AqlVariablesLocalContext caseLetStatement(LetStatement letStatement) {
		AqlVariablesLocalContext variablesContext = new AqlVariablesLocalContext();
		variablesContext.addAllVariables(this.doSwitch(letStatement.eContainer()));
		letStatement.getVariables().forEach(variableBinding -> variablesContext.addAllVariables(this
				.getBindingStandaloneContext(variableBinding, false)));
		return variablesContext;
	}
	////

}
