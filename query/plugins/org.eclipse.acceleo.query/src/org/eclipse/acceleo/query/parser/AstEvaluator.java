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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.ast.BooleanLiteral;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.FeatureAccess;
import org.eclipse.acceleo.query.ast.IntegerLiteral;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.NullLiteral;
import org.eclipse.acceleo.query.ast.RealLiteral;
import org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral;
import org.eclipse.acceleo.query.ast.SetInExtensionLiteral;
import org.eclipse.acceleo.query.ast.StringLiteral;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.ast.util.AstSwitch;
import org.eclipse.acceleo.query.runtime.impl.EvaluationServices;

/**
 * Evaluates the asts.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class AstEvaluator extends AstSwitch<Object> {
	/**
	 * Variable definitions used during evaluation.
	 */
	private Map<String, Object> variables;

	/**
	 * The evaluation services.
	 */
	private final EvaluationServices services;

	/**
	 * Creates a new AstEvaluator.
	 * 
	 * @param evalServices
	 *            the evaluation services.
	 */
	public AstEvaluator(EvaluationServices evalServices) {
		this.services = evalServices;
	}

	/**
	 * Evaluates the specified expression given the specified variable definitions.
	 * 
	 * @param varDefinitions
	 *            the variable definitions
	 * @param ast
	 *            the ast to evaluate.
	 * @return the evaluation of the specified ast.
	 */
	public Object eval(Map<String, Object> varDefinitions, Expression ast) {
		this.variables = varDefinitions;
		return doSwitch(ast);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseBooleanLiteral(org.eclipse.acceleo.query.ast.BooleanLiteral)
	 */
	@Override
	public Object caseBooleanLiteral(BooleanLiteral object) {
		return object.isValue();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseIntegerLiteral(org.eclipse.acceleo.query.ast.IntegerLiteral)
	 */
	@Override
	public Object caseIntegerLiteral(IntegerLiteral object) {
		return object.getValue();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseRealLiteral(org.eclipse.acceleo.query.ast.RealLiteral)
	 */
	@Override
	public Object caseRealLiteral(RealLiteral object) {
		return object.getValue();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseStringLiteral(org.eclipse.acceleo.query.ast.StringLiteral)
	 */
	@Override
	public Object caseStringLiteral(StringLiteral object) {
		return object.getValue();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseTypeLiteral(org.eclipse.acceleo.query.ast.TypeLiteral)
	 */
	@Override
	public Object caseTypeLiteral(TypeLiteral object) {
		return object.getValue();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseFeatureAccess(org.eclipse.acceleo.query.ast.FeatureAccess)
	 */
	@Override
	public Object caseFeatureAccess(FeatureAccess object) {
		Object target = doSwitch(object.getTarget());
		return services.featureAccess(target, object.getFeatureName());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseCall(org.eclipse.acceleo.query.ast.Call)
	 */
	@Override
	public Object caseCall(Call object) {
		// eval arguments.
		List<Expression> exprArgs = object.getArguments();
		int argc = exprArgs.size();
		Object[] args = new Object[argc];
		for (int i = 0; i < argc; i++) {
			args[i] = doSwitch(exprArgs.get(i));
		}
		// call the service.
		Object result;
		switch (object.getType()) {
			case CALLSERVICE:
				result = services.call(object.getServiceName(), args);
				break;
			case CALLORAPPLY:
				result = services.callOrApply(object.getServiceName(), args);
				break;
			case COLLECTIONCALL:
				result = services.collectionServiceCall(object.getServiceName(), args);
				break;
			default:
				throw new UnsupportedOperationException("should never happen");
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseVarRef(org.eclipse.acceleo.query.ast.VarRef)
	 */
	@Override
	public Object caseVarRef(VarRef object) {
		return services.getVariableValue(variables, object.getVariableName());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseLambda(org.eclipse.acceleo.query.ast.Lambda)
	 */
	@Override
	public Object caseLambda(Lambda object) {
		// We should revive the ILambda and InterpretedLambda as values of the Lambda literal.
		return object;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseNullLiteral(org.eclipse.acceleo.query.ast.NullLiteral)
	 */
	@Override
	public Object caseNullLiteral(NullLiteral object) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseEnumLiteral(org.eclipse.acceleo.query.ast.EnumLiteral)
	 */
	@Override
	public Object caseEnumLiteral(EnumLiteral object) {
		return object.getLiteral().getInstance();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseSetInExtensionLiteral(org.eclipse.acceleo.query.ast.SetInExtensionLiteral)
	 */
	@Override
	public Object caseSetInExtensionLiteral(SetInExtensionLiteral object) {
		// TODO use a LazySet..
		final Set<Object> result = Sets.newLinkedHashSet();

		for (Expression expression : object.getValues()) {
			result.add(doSwitch(expression));
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseSequenceInExtensionLiteral(org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral)
	 */
	@Override
	public Object caseSequenceInExtensionLiteral(SequenceInExtensionLiteral object) {
		// TODO use a LazyList..
		final List<Object> result = Lists.newArrayList();

		for (Expression expression : object.getValues()) {
			result.add(doSwitch(expression));
		}

		return result;
	}

}
