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
package org.eclipse.acceleo.query.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Binding;
import org.eclipse.acceleo.query.ast.BooleanLiteral;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.ast.ClassTypeLiteral;
import org.eclipse.acceleo.query.ast.CollectionTypeLiteral;
import org.eclipse.acceleo.query.ast.Conditional;
import org.eclipse.acceleo.query.ast.EClassifierTypeLiteral;
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.IntegerLiteral;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.Let;
import org.eclipse.acceleo.query.ast.NullLiteral;
import org.eclipse.acceleo.query.ast.RealLiteral;
import org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral;
import org.eclipse.acceleo.query.ast.SetInExtensionLiteral;
import org.eclipse.acceleo.query.ast.StringLiteral;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.ast.TypeSetLiteral;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.ast.util.AstSwitch;

/**
 * Serialize an {@link Expression}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AstSerializer extends AstSwitch<Object> {

	/**
	 * id of the String type to use in variable declaration properties.
	 */
	public static final String STRING_TYPE = "String";

	/**
	 * id of the integer type to use in variable declaration properties.
	 */
	public static final String INTEGER_TYPE = "Integer";

	/**
	 * id of the real type to use in variable declaration properties.
	 */
	public static final String REAL_TYPE = "Real";

	/**
	 * id of the boolean type to use in variable declaration properties.
	 */
	public static final String BOOLEAN_TYPE = "Boolean";

	/**
	 * A dummy {@link Object} to prevent switching in super types.
	 */
	private static final Object DUMMY = new Object();

	/**
	 * A space caracter.
	 */
	private static final String SPACE = " ";

	/**
	 * The separator between ecore segments.
	 */
	public static final String ECORE_SEPARATOR = "::";

	/**
	 * The mapping of an operator service call name to is precedence.
	 */
	private static final Map<String, Integer> OPERATOR_PRECEDENCE = initOperatorPrecedence();

	/**
	 * The {@link StringBuilder} used to serialize.
	 */
	private StringBuilder builder;

	/**
	 * Serializes the given {@link Expression}.
	 * 
	 * @param expression
	 *            the {@link Expression}
	 * @return the serialized {@link Expression}
	 */
	public String serialize(Expression expression) {
		builder = new StringBuilder();

		doSwitch(expression);

		return builder.toString();
	}

	/**
	 * Initializes the operator precedence.
	 * 
	 * @return the mapping of an operator service call name to is precedence
	 */
	private static Map<String, Integer> initOperatorPrecedence() {
		final Map<String, Integer> res = new HashMap<String, Integer>();

		int precedence = 0;

		res.put(AstBuilderListener.NOT_SERVICE_NAME, precedence);
		precedence++;
		res.put(AstBuilderListener.UNARY_MIN_SERVICE_NAME, precedence);
		precedence++;
		res.put(AstBuilderListener.MULT_SERVICE_NAME, precedence);
		res.put(AstBuilderListener.DIV_SERVICE_NAME, precedence);
		precedence++;
		res.put(AstBuilderListener.ADD_SERVICE_NAME, precedence);
		res.put(AstBuilderListener.SUB_SERVICE_NAME, precedence);
		precedence++;
		res.put(AstBuilderListener.LESS_THAN_EQUAL_SERVICE_NAME, precedence);
		res.put(AstBuilderListener.GREATER_THAN_EQUAL_SERVICE_NAME, precedence);
		res.put(AstBuilderListener.DIFFERS_SERVICE_NAME, precedence);
		res.put(AstBuilderListener.EQUALS_SERVICE_NAME, precedence);
		res.put(AstBuilderListener.LESS_THAN_SERVICE_NAME, precedence);
		res.put(AstBuilderListener.GREATER_THAN_SERVICE_NAME, precedence);
		precedence++;
		res.put(AstBuilderListener.AND_SERVICE_NAME, precedence);
		precedence++;
		res.put(AstBuilderListener.OR_SERVICE_NAME, precedence);
		precedence++;
		res.put(AstBuilderListener.XOR_SERVICE_NAME, precedence);
		precedence++;
		res.put(AstBuilderListener.IMPLIES_SERVICE_NAME, precedence);

		return res;
	}

	@Override
	public Object caseBinding(Binding binding) {
		final String bindingName = AstBuilder.protectWithUnderscore(binding.getName());
		builder.append(bindingName);
		if (binding.getType() != null) {
			builder.append(SPACE).append(':').append(SPACE);
			doSwitch(binding.getType());
		}
		builder.append(SPACE).append('=').append(SPACE);
		doSwitch(binding.getValue());
		return DUMMY;
	}

	@Override
	public Object caseBooleanLiteral(BooleanLiteral booleanLiteral) {
		builder.append(booleanLiteral.isValue());
		return DUMMY;
	}

	@Override
	public Object caseCall(Call call) {
		if (AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME.equals(call.getServiceName())) {
			aqlFeatureAccessCall(call);
		} else if (AstBuilderListener.NOT_SERVICE_NAME.equals(call.getServiceName())) {
			notCall(call);
		} else if (AstBuilderListener.DIFFERS_SERVICE_NAME.equals(call.getServiceName())) {
			differsCall(call);
		} else if (AstBuilderListener.EQUALS_SERVICE_NAME.equals(call.getServiceName())) {
			equalsCall(call);
		} else if (AstBuilderListener.GREATER_THAN_EQUAL_SERVICE_NAME.equals(call.getServiceName())) {
			greaterThanEqualCall(call);
		} else if (AstBuilderListener.GREATER_THAN_SERVICE_NAME.equals(call.getServiceName())) {
			greaterThanCall(call);
		} else if (AstBuilderListener.LESS_THAN_EQUAL_SERVICE_NAME.equals(call.getServiceName())) {
			lessThanEqualCall(call);
		} else if (AstBuilderListener.LESS_THAN_SERVICE_NAME.equals(call.getServiceName())) {
			lessThanCall(call);
		} else if (AstBuilderListener.DIV_SERVICE_NAME.equals(call.getServiceName())) {
			divOpCall(call);
		} else if (AstBuilderListener.MULT_SERVICE_NAME.equals(call.getServiceName())) {
			multCall(call);
		} else if (AstBuilderListener.SUB_SERVICE_NAME.equals(call.getServiceName())) {
			subCall(call);
		} else if (AstBuilderListener.ADD_SERVICE_NAME.equals(call.getServiceName())) {
			addCall(call);
		} else if (AstBuilderListener.UNARY_MIN_SERVICE_NAME.equals(call.getServiceName())) {
			unaryMinCall(call);
		} else if (AstBuilderListener.AND_SERVICE_NAME.equals(call.getServiceName())) {
			andCall(call);
		} else if (AstBuilderListener.OR_SERVICE_NAME.equals(call.getServiceName())) {
			orCall(call);
		} else if (AstBuilderListener.XOR_SERVICE_NAME.equals(call.getServiceName())) {
			xorCall(call);
		} else if (AstBuilderListener.IMPLIES_SERVICE_NAME.equals(call.getServiceName())) {
			impliesCall(call);
		} else {
			call(call);
		}

		return DUMMY;
	}

	/**
	 * A not {@link Call}.
	 * 
	 * @param call
	 *            the not {@link Call}.
	 */
	private void notCall(Call call) {
		builder.append(AstBuilderListener.NOT_OPERATOR);
		builder.append(SPACE);
		if (!call.getArguments().isEmpty()) {
			doSwitch(call.getArguments().get(0));
		}
	}

	/**
	 * A differs {@link Call}.
	 * 
	 * @param call
	 *            the differs {@link Call}.
	 */
	private void differsCall(Call call) {
		final boolean needParenthesis = needParenthesis(call);
		if (needParenthesis) {
			builder.append('(');
		}
		final List<Expression> arguments = new ArrayList<Expression>(call.getArguments());
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		builder.append(SPACE);
		builder.append(AstBuilderListener.DIFFERS_OPERATOR);
		builder.append(SPACE);
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		if (needParenthesis) {
			builder.append(')');
		}
	}

	/**
	 * A equals {@link Call}.
	 * 
	 * @param call
	 *            the equals {@link Call}.
	 */
	private void equalsCall(Call call) {
		final boolean needParenthesis = needParenthesis(call);
		if (needParenthesis) {
			builder.append('(');
		}
		final List<Expression> arguments = new ArrayList<Expression>(call.getArguments());
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		builder.append(SPACE);
		builder.append(AstBuilderListener.EQUALS_OPERATOR);
		builder.append(SPACE);
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		if (needParenthesis) {
			builder.append(')');
		}
	}

	/**
	 * A greater than equal {@link Call}.
	 * 
	 * @param call
	 *            the greater than equal {@link Call}.
	 */
	private void greaterThanEqualCall(Call call) {
		final boolean needParenthesis = needParenthesis(call);
		if (needParenthesis) {
			builder.append('(');
		}
		final List<Expression> arguments = new ArrayList<Expression>(call.getArguments());
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		builder.append(SPACE);
		builder.append(AstBuilderListener.GREATER_THAN_EQUAL_OPERATOR);
		builder.append(SPACE);
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		if (needParenthesis) {
			builder.append(')');
		}
	}

	/**
	 * A greater than {@link Call}.
	 * 
	 * @param call
	 *            the greater than {@link Call}.
	 */
	private void greaterThanCall(Call call) {
		final boolean needParenthesis = needParenthesis(call);
		if (needParenthesis) {
			builder.append('(');
		}
		final List<Expression> arguments = new ArrayList<Expression>(call.getArguments());
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		builder.append(SPACE);
		builder.append(AstBuilderListener.GREATER_THAN_OPERATOR);
		builder.append(SPACE);
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		if (needParenthesis) {
			builder.append(')');
		}
	}

	/**
	 * A less than equal {@link Call}.
	 * 
	 * @param call
	 *            the less than equal {@link Call}.
	 */
	private void lessThanEqualCall(Call call) {
		final boolean needParenthesis = needParenthesis(call);
		if (needParenthesis) {
			builder.append('(');
		}
		final List<Expression> arguments = new ArrayList<Expression>(call.getArguments());
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		builder.append(SPACE);
		builder.append(AstBuilderListener.LESS_THAN_EQUAL_OPERATOR);
		builder.append(SPACE);
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		if (needParenthesis) {
			builder.append(')');
		}
	}

	/**
	 * A less than {@link Call}.
	 * 
	 * @param call
	 *            the less than {@link Call}.
	 */
	private void lessThanCall(Call call) {
		final boolean needParenthesis = needParenthesis(call);
		if (needParenthesis) {
			builder.append('(');
		}
		final List<Expression> arguments = new ArrayList<Expression>(call.getArguments());
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		builder.append(SPACE);
		builder.append(AstBuilderListener.LESS_THAN_OPERATOR);
		builder.append(SPACE);
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		if (needParenthesis) {
			builder.append(')');
		}
	}

	/**
	 * A div op {@link Call}.
	 * 
	 * @param call
	 *            the div op {@link Call}.
	 */
	private void divOpCall(Call call) {
		final boolean needParenthesis = needParenthesis(call);
		if (needParenthesis) {
			builder.append('(');
		}
		final List<Expression> arguments = new ArrayList<Expression>(call.getArguments());
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		builder.append(SPACE);
		builder.append(AstBuilderListener.DIV_OPERATOR);
		builder.append(SPACE);
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		if (needParenthesis) {
			builder.append(')');
		}
	}

	/**
	 * A mult {@link Call}.
	 * 
	 * @param call
	 *            the mult {@link Call}.
	 */
	private void multCall(Call call) {
		final boolean needParenthesis = needParenthesis(call);
		if (needParenthesis) {
			builder.append('(');
		}
		final List<Expression> arguments = new ArrayList<Expression>(call.getArguments());
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		builder.append(SPACE);
		builder.append(AstBuilderListener.MULT_OPERATOR);
		builder.append(SPACE);
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		if (needParenthesis) {
			builder.append(')');
		}
	}

	/**
	 * A sub {@link Call}.
	 * 
	 * @param call
	 *            the sub {@link Call}.
	 */
	private void subCall(Call call) {
		final boolean needParenthesis = needParenthesis(call);
		if (needParenthesis) {
			builder.append('(');
		}
		final List<Expression> arguments = new ArrayList<Expression>(call.getArguments());
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		builder.append(SPACE);
		builder.append(AstBuilderListener.SUB_OPERATOR);
		builder.append(SPACE);
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		if (needParenthesis) {
			builder.append(')');
		}
	}

	/**
	 * A add {@link Call}.
	 * 
	 * @param call
	 *            the add {@link Call}.
	 */
	private void addCall(Call call) {
		final boolean needParenthesis = needParenthesis(call);
		if (needParenthesis) {
			builder.append('(');
		}
		final List<Expression> arguments = new ArrayList<Expression>(call.getArguments());
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		builder.append(SPACE);
		builder.append(AstBuilderListener.ADD_OPERATOR);
		builder.append(SPACE);
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		if (needParenthesis) {
			builder.append(')');
		}
	}

	/**
	 * A unary min {@link Call}.
	 * 
	 * @param call
	 *            the unary min {@link Call}.
	 */
	private void unaryMinCall(Call call) {
		final List<Expression> arguments = new ArrayList<Expression>(call.getArguments());
		builder.append(AstBuilderListener.UNARY_MIN_OPERATOR);
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
	}

	/**
	 * A and {@link Call}.
	 * 
	 * @param call
	 *            the and {@link Call}.
	 */
	private void andCall(Call call) {
		final boolean needParenthesis = needParenthesis(call);
		if (needParenthesis) {
			builder.append('(');
		}
		final List<Expression> arguments = new ArrayList<Expression>(call.getArguments());
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		builder.append(SPACE);
		builder.append(AstBuilderListener.AND_OPERATOR);
		builder.append(SPACE);
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		if (needParenthesis) {
			builder.append(')');
		}
	}

	/**
	 * A or {@link Call}.
	 * 
	 * @param call
	 *            the or {@link Call}.
	 */
	private void orCall(Call call) {
		final boolean needParenthesis = needParenthesis(call);
		if (needParenthesis) {
			builder.append('(');
		}
		final List<Expression> arguments = new ArrayList<Expression>(call.getArguments());
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		builder.append(SPACE);
		builder.append(AstBuilderListener.OR_OPERATOR);
		builder.append(SPACE);
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		if (needParenthesis) {
			builder.append(')');
		}
	}

	/**
	 * A xor {@link Call}.
	 * 
	 * @param call
	 *            the xor {@link Call}.
	 */
	private void xorCall(Call call) {
		final boolean needParenthesis = needParenthesis(call);
		if (needParenthesis) {
			builder.append(')');
		}
		final List<Expression> arguments = new ArrayList<Expression>(call.getArguments());
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		builder.append(SPACE);
		builder.append(AstBuilderListener.XOR_OPERATOR);
		builder.append(SPACE);
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		if (needParenthesis) {
			builder.append(')');
		}
	}

	/**
	 * A implies {@link Call}.
	 * 
	 * @param call
	 *            the implies {@link Call}.
	 */
	private void impliesCall(Call call) {
		final boolean needParenthesis = needParenthesis(call);
		if (needParenthesis) {
			builder.append('(');
		}
		final List<Expression> arguments = new ArrayList<Expression>(call.getArguments());
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		builder.append(SPACE);
		builder.append(AstBuilderListener.IMPLIES_OPERATOR);
		builder.append(SPACE);
		if (!arguments.isEmpty()) {
			final Expression operand = arguments.remove(0);
			doSwitch(operand);
		}
		if (needParenthesis) {
			builder.append(')');
		}
	}

	/**
	 * Tells if the given {@link Call} need to be surrounded with parenthesis.
	 * 
	 * @param call
	 *            the {@link Call}
	 * @return <code>true</code> if the given {@link Call} need to be surrounded with parenthesis,
	 *         <code>false</code> otherwise
	 */
	private boolean needParenthesis(Call call) {
		final boolean res;

		final Integer precedence = OPERATOR_PRECEDENCE.get(call.getServiceName());
		Integer parentPrecedence;
		if (call.eContainer() instanceof Call) {
			parentPrecedence = OPERATOR_PRECEDENCE.get(((Call)call.eContainer()).getServiceName());
			if (parentPrecedence == null && ((Call)call.eContainer()).getArguments().get(0) == call) {
				parentPrecedence = -1;
			}
		} else {
			parentPrecedence = null;
		}

		if (precedence == null || parentPrecedence == null) {
			res = false;
		} else {
			int delta = precedence - parentPrecedence;
			if (delta > 0) {
				res = true;
			} else if (delta == 0) {
				final Call parent = (Call)call.eContainer();
				res = ((AstBuilderListener.SUB_SERVICE_NAME.equals(parent.getServiceName()))
						|| (AstBuilderListener.DIV_SERVICE_NAME.equals(parent.getServiceName()))) && (parent
								.getArguments().size() == 2 && parent.getArguments().get(1) == call);
			} else {
				res = false;
			}
		}

		return res;
	}

	private void aqlFeatureAccessCall(Call call) {
		final List<Expression> arguments = new ArrayList<Expression>(call.getArguments());
		final Expression receiver = arguments.remove(0);
		doSwitch(receiver);
		builder.append('.');
		if (!arguments.isEmpty()) {
			final StringLiteral featureNameLiteral = (StringLiteral)arguments.get(0);
			final String featureName = AstBuilder.protectWithUnderscore(featureNameLiteral.getValue());
			builder.append(featureName);
		}
	}

	private void call(Call call) {
		final List<Expression> arguments = new ArrayList<Expression>(call.getArguments());
		final Expression receiver = arguments.remove(0);
		doSwitch(receiver);
		final String serviceName;
		if (call.getType() == CallType.COLLECTIONCALL) {
			builder.append("->");
			if (!arguments.isEmpty() && arguments.get(0) instanceof Lambda) {
				serviceName = call.getServiceName();
			} else {
				serviceName = AstBuilder.protectWithUnderscore(call.getServiceName());
			}
		} else {
			builder.append('.');
			serviceName = AstBuilder.protectWithUnderscore(call.getServiceName());
		}
		builder.append(serviceName);
		builder.append('(');
		if (!arguments.isEmpty()) {
			final StringBuilder previousBuilder = builder;
			try {
				builder = new StringBuilder();
				for (Expression argument : arguments) {
					doSwitch(argument);
					builder.append(',').append(' ');
				}
				previousBuilder.append(builder.substring(0, builder.length() - 2));
			} finally {
				builder = previousBuilder;
			}
		}
		builder.append(')');
	}

	@Override
	public Object caseCollectionTypeLiteral(CollectionTypeLiteral collectionTypeLiteral) {
		if (collectionTypeLiteral.getValue() == List.class) {
			builder.append("Sequence(");
		} else if (collectionTypeLiteral.getValue() == Set.class) {
			builder.append("OrderedSet(");
		} else {
			builder.append("***invalid type of collection ***(");
		}
		doSwitch(collectionTypeLiteral.getElementType());
		builder.append(")");
		return DUMMY;
	}

	@Override
	public Object caseConditional(Conditional conditional) {
		builder.append("if ");
		doSwitch(conditional.getPredicate());
		builder.append(" then ");
		doSwitch(conditional.getTrueBranch());
		builder.append(" else ");
		doSwitch(conditional.getFalseBranch());
		builder.append(" endif");
		return DUMMY;
	}

	@Override
	public Object caseEnumLiteral(EnumLiteral enumLiteral) {
		builder.append(AstBuilder.protectWithUnderscore(enumLiteral.getEPackageName()));
		builder.append(ECORE_SEPARATOR);
		builder.append(AstBuilder.protectWithUnderscore(enumLiteral.getEEnumName()));
		builder.append(ECORE_SEPARATOR);
		builder.append(AstBuilder.protectWithUnderscore(enumLiteral.getEEnumLiteralName()));

		return DUMMY;
	}

	@Override
	public Object caseIntegerLiteral(IntegerLiteral object) {
		builder.append(object.getValue());
		return DUMMY;
	}

	@Override
	public Object caseLambda(Lambda lambda) {
		doSwitch(lambda.getParameters().get(0));
		builder.append(" | ");
		doSwitch(lambda.getExpression());
		return DUMMY;
	}

	@Override
	public Object caseLet(Let let) {
		builder.append("let ");
		if (!let.getBindings().isEmpty()) {
			final StringBuilder previousBuilder = builder;
			try {
				builder = new StringBuilder();
				for (Binding binding : let.getBindings()) {
					doSwitch(binding);
					builder.append(',').append(' ');
				}
				previousBuilder.append(builder.substring(0, builder.length() - 2));
			} finally {
				builder = previousBuilder;
			}
		}
		builder.append(" in ");
		doSwitch(let.getBody());
		return super.caseLet(let);
	}

	@Override
	public Object caseNullLiteral(NullLiteral nullLiteral) {
		builder.append("null");
		return DUMMY;
	}

	@Override
	public Object caseSequenceInExtensionLiteral(SequenceInExtensionLiteral sequenceInExtensionLiteral) {
		builder.append("Sequence{");
		if (!sequenceInExtensionLiteral.getValues().isEmpty()) {
			final StringBuilder previousBuilder = builder;
			try {
				builder = new StringBuilder();
				for (Expression value : sequenceInExtensionLiteral.getValues()) {
					doSwitch(value);
					builder.append(',').append(' ');
				}
				previousBuilder.append(builder.substring(0, builder.length() - 2));
			} finally {
				builder = previousBuilder;
			}
		}
		builder.append('}');
		return DUMMY;
	}

	@Override
	public Object caseRealLiteral(RealLiteral realLiteral) {
		builder.append(realLiteral.getValue());
		return DUMMY;
	}

	@Override
	public Object caseSetInExtensionLiteral(SetInExtensionLiteral setInExtensionLiteral) {
		builder.append("OrderedSet{");
		if (!setInExtensionLiteral.getValues().isEmpty()) {
			final StringBuilder previousBuilder = builder;
			try {
				builder = new StringBuilder();
				for (Expression value : setInExtensionLiteral.getValues()) {
					doSwitch(value);
					builder.append(',').append(' ');
				}
				previousBuilder.append(builder.substring(0, builder.length() - 2));
			} finally {
				builder = previousBuilder;
			}
		}
		builder.append('}');
		return DUMMY;
	}

	@Override
	public Object caseStringLiteral(StringLiteral stringLiteral) {
		builder.append('\'');
		builder.append(escape(stringLiteral.getValue()));
		builder.append('\'');
		return DUMMY;
	}

	/**
	 * Escapes the given {@link String}.
	 * 
	 * @param value
	 *            the {@link String}
	 * @return the escaped {@link String}
	 */
	private String escape(String value) {
		final StringBuilder res = new StringBuilder();

		for (int i = 0; i < value.length(); i++) {
			final char currentChar = value.charAt(i);
			// TODO special UTF chars depending on the module encoding ?
			switch (currentChar) {
				case '\b':
					res.append("\\b");
					break;
				case '\t':
					res.append("\\t");
					break;
				case '\n':
					res.append("\\n");
					break;
				case '\f':
					res.append("\\f");
					break;
				case '\r':
					res.append("\\r");
					break;
				case '\\':
					res.append("\\\\");
					break;
				case '\'':
					res.append("\\'");
					break;
				default:
					res.append(currentChar);
					break;
			}
		}

		return res.toString();
	}

	@Override
	public Object caseTypeSetLiteral(TypeSetLiteral typeSetLiteral) {
		builder.append('{');
		if (!typeSetLiteral.getTypes().isEmpty()) {
			final StringBuilder previousBuilder = builder;
			try {
				builder = new StringBuilder();
				for (TypeLiteral type : typeSetLiteral.getTypes()) {
					doSwitch(type);
					builder.append(" | ");
				}
				previousBuilder.append(builder.substring(0, builder.length() - 3));
			} finally {
				builder = previousBuilder;
			}
		}
		builder.append('}');
		return DUMMY;
	}

	@Override
	public Object caseClassTypeLiteral(ClassTypeLiteral classTypeLiteral) {
		if (classTypeLiteral.getValue() == Double.class) {
			builder.append(REAL_TYPE);
		} else {
			builder.append(((Class<?>)classTypeLiteral.getValue()).getSimpleName());
		}

		return DUMMY;
	}

	@Override
	public Object caseEClassifierTypeLiteral(EClassifierTypeLiteral object) {
		builder.append(AstBuilder.protectWithUnderscore(object.getEPackageName()));
		builder.append(ECORE_SEPARATOR);
		builder.append(AstBuilder.protectWithUnderscore(object.getEClassifierName()));

		return DUMMY;
	}

	@Override
	public Object caseVariableDeclaration(VariableDeclaration variableDeclaration) {
		final String variableName = AstBuilder.protectWithUnderscore(variableDeclaration.getName());
		builder.append(variableName);
		if (variableDeclaration.getType() != null) {
			builder.append(SPACE).append(':').append(SPACE);
			doSwitch(variableDeclaration.getType());
		}
		return DUMMY;
	}

	@Override
	public Object caseVarRef(VarRef varRef) {
		final String variableName = AstBuilder.protectWithUnderscore(varRef.getVariableName());
		builder.append(variableName);
		return DUMMY;
	}

}
