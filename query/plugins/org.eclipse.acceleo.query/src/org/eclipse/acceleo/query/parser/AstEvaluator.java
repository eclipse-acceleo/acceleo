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
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.eclipse.acceleo.query.ast.And;
import org.eclipse.acceleo.query.ast.Binding;
import org.eclipse.acceleo.query.ast.BooleanLiteral;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.Conditional;
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.Implies;
import org.eclipse.acceleo.query.ast.IntegerLiteral;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.Let;
import org.eclipse.acceleo.query.ast.NullLiteral;
import org.eclipse.acceleo.query.ast.Or;
import org.eclipse.acceleo.query.ast.RealLiteral;
import org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral;
import org.eclipse.acceleo.query.ast.SetInExtensionLiteral;
import org.eclipse.acceleo.query.ast.StringLiteral;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.ast.TypeSetLiteral;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.ast.util.AstSwitch;
import org.eclipse.acceleo.query.runtime.EvaluationResult;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.impl.EvaluationServices;
import org.eclipse.acceleo.query.runtime.impl.LambdaValue;
import org.eclipse.acceleo.query.runtime.impl.Nothing;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;

/**
 * Evaluates the asts.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class AstEvaluator extends AstSwitch<Object> {

	/**
	 * Message used to report bad predicate typing runtime detection.
	 */
	private static final String BAD_PREDICATE_TYPE_MSG = "Conditional's predicate must evaluate to a boolean value but was %s instead.";

	/**
	 * Variable definitions used during evaluation.
	 */
	private final Stack<Map<String, Object>> variablesStack;

	/**
	 * The evaluation services.
	 */
	private final EvaluationServices services;

	/** Aggregated status of an evaluation. */
	private Diagnostic diagnostic;

	/**
	 * Creates a new {@link AstEvaluator} instance given an {@link IReadOnlyQueryEnvironment} instance.
	 * 
	 * @param queryEnv
	 *            the environment used to evaluate
	 * @deprecated use {@link #AstEvaluator(EvaluationServices)}
	 */
	public AstEvaluator(IReadOnlyQueryEnvironment queryEnv) {
		this(new EvaluationServices(queryEnv));
	}

	/**
	 * Creates a new {@link AstEvaluator} instance given an {@link EvaluationServices} instance.
	 * 
	 * @param services
	 *            the {@link EvaluationServices} used to evaluate
	 */
	public AstEvaluator(EvaluationServices services) {
		this.services = services;
		variablesStack = new Stack<Map<String, Object>>();
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
	public EvaluationResult eval(Map<String, Object> varDefinitions, Expression ast) {
		variablesStack.push(varDefinitions);
		diagnostic = new BasicDiagnostic();
		final Object result = doSwitch(ast);
		variablesStack.pop();

		return new EvaluationResult(result, diagnostic);
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
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseCall(org.eclipse.acceleo.query.ast.Call)
	 */
	@Override
	public Object caseCall(Call object) {
		final Object result;

		List<Expression> exprArgs = object.getArguments();
		final int argc = exprArgs.size();
		final Object[] args = new Object[argc];

		int i = 0;
		for (Expression arg : exprArgs) {
			args[i++] = doSwitch(arg);
		}

		// call the service.
		switch (object.getType()) {
			case CALLSERVICE:
				result = services.call(object.getServiceName(), args, diagnostic);
				break;
			case CALLORAPPLY:
				result = services.callOrApply(object.getServiceName(), args, diagnostic);
				break;
			case COLLECTIONCALL:
				result = services.collectionServiceCall(object.getServiceName(), args, diagnostic);
				break;
			default:
				throw new UnsupportedOperationException("should never happen");
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseAnd(org.eclipse.acceleo.query.ast.And)
	 */
	@Override
	public Object caseAnd(And object) {
		final Object result;
		final Object[] args = new Object[2];

		args[0] = doSwitch(object.getArguments().get(0));
		if (args[0] != null && args[0].getClass() == Boolean.class && Boolean.FALSE.equals(args[0])) {
			result = Boolean.FALSE;
		} else {
			args[1] = doSwitch(object.getArguments().get(1));
			result = services.call(object.getServiceName(), args, diagnostic);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseOr(org.eclipse.acceleo.query.ast.Or)
	 */
	@Override
	public Object caseOr(Or object) {
		final Object result;
		final Object[] args = new Object[2];

		args[0] = doSwitch(object.getArguments().get(0));
		if (args[0] != null && args[0].getClass() == Boolean.class && Boolean.TRUE.equals(args[0])) {
			result = Boolean.TRUE;
		} else {
			args[1] = doSwitch(object.getArguments().get(1));
			result = services.call(object.getServiceName(), args, diagnostic);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseImplies(org.eclipse.acceleo.query.ast.Implies)
	 */
	@Override
	public Object caseImplies(Implies object) {
		final Object result;
		final Object[] args = new Object[2];

		args[0] = doSwitch(object.getArguments().get(0));
		if (args[0] != null && args[0].getClass() == Boolean.class && Boolean.FALSE.equals(args[0])) {
			result = Boolean.TRUE;
		} else {
			args[1] = doSwitch(object.getArguments().get(1));
			result = services.call(object.getServiceName(), args, diagnostic);
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
		return services.getVariableValue(variablesStack.peek(), object.getVariableName(), diagnostic);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseLambda(org.eclipse.acceleo.query.ast.Lambda)
	 */
	@Override
	public Object caseLambda(Lambda object) {
		return new LambdaValue(object, new HashMap<String, Object>(variablesStack.peek()), this, diagnostic);
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
		final List<Object> result = Lists.newArrayList();

		for (Expression expression : object.getValues()) {
			result.add(doSwitch(expression));
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseConditional(org.eclipse.acceleo.query.ast.Conditional)
	 */
	@Override
	public Object caseConditional(Conditional object) {
		Object selector = this.doSwitch(object.getPredicate());
		Object result;
		if (selector instanceof Boolean) {
			if ((Boolean)selector) {
				result = this.doSwitch(object.getTrueBranch());
			} else {
				result = this.doSwitch(object.getFalseBranch());
			}
		} else {
			Nothing nothing = new Nothing(String.format(BAD_PREDICATE_TYPE_MSG, selector));
			Diagnostic diag = new BasicDiagnostic(Diagnostic.WARNING, AstBuilderListener.PLUGIN_ID, 0,
					nothing.getMessage(), new Object[] {object.getPredicate() });
			((BasicDiagnostic)diagnostic).add(diag);
			result = nothing;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseLet(org.eclipse.acceleo.query.ast.Let)
	 */
	@Override
	public Object caseLet(Let object) {
		Map<String, Object> letEnv = Maps.newHashMap(variablesStack.peek());
		for (Binding binding : object.getBindings()) {
			letEnv.put(binding.getName(), doSwitch(binding.getValue()));
		}
		variablesStack.push(letEnv);
		Object result = doSwitch(object.getBody());
		variablesStack.pop();
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseTypeSetLiteral(org.eclipse.acceleo.query.ast.TypeSetLiteral)
	 */
	@Override
	public Object caseTypeSetLiteral(TypeSetLiteral object) {
		final Set<Object> result = new LinkedHashSet<Object>(object.getTypes().size());

		for (TypeLiteral type : object.getTypes()) {
			result.add(doSwitch(type));
		}

		return result;
	}

}
