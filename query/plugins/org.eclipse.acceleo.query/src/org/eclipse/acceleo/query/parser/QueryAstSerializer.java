/*******************************************************************************
 * Copyright (c) 2016, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.parser;

import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.ast.Binding;
import org.eclipse.acceleo.query.ast.BooleanLiteral;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.ast.CollectionTypeLiteral;
import org.eclipse.acceleo.query.ast.Conditional;
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.Error;
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
 * Serialize a {@link Expression}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
class QueryAstSerializer extends AstSwitch<Void> {

	/**
	 * A closing bracket.
	 */
	private static final String CLOSING_BRACKET = "}";

	/**
	 * A comma and a white space.
	 */
	private static final String COMMA = ", ";

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

	@Override
	public Void caseBinding(Binding binding) {
		builder.append(binding.getName());
		if (binding.getType() != null) {
			builder.append(" : ");
			builder.append(doSwitch(binding.getType()));
		}
		builder.append(" = ");
		builder.append(doSwitch(binding.getValue()));
		return null;
	}

	@Override
	public Void caseBooleanLiteral(BooleanLiteral booleanLiteral) {
		builder.append(booleanLiteral.isValue());
		return null;
	}

	@Override
	public Void caseCall(Call call) {
		if (call.getType() == CallType.COLLECTIONCALL) {
			builder.append("->");
		} else {
			builder.append(".");
		}
		builder.append(call.getServiceName());
		builder.append("(");
		final StringBuilder previousBuilder = builder;
		builder = new StringBuilder();
		for (Expression argument : call.getArguments()) {
			doSwitch(argument);
			builder.append(COMMA);
		}
		if (builder.length() > 0) {
			previousBuilder.append(builder.substring(0, builder.length() - 2));
		}
		builder = previousBuilder;
		builder.append(")");
		return null;
	}

	@Override
	public Void caseCollectionTypeLiteral(CollectionTypeLiteral collectionTypeLiteral) {
		if (collectionTypeLiteral.getValue() == List.class) {
			builder.append("Sequence(");
		} else if (collectionTypeLiteral.getValue() == Set.class) {
			builder.append("OrderedSet(");
		} else {
			builder.append("***invalid type of collection ***(");
		}
		doSwitch(collectionTypeLiteral.getElementType());
		builder.append("");
		return null;
	}

	@Override
	public Void caseConditional(Conditional conditional) {
		builder.append("if (");
		doSwitch(conditional.getPredicate());
		builder.append(") then ");
		doSwitch(conditional.getTrueBranch());
		builder.append(" else ");
		doSwitch(conditional.getFalseBranch());
		builder.append(" endif ");
		return null;
	}

	@Override
	public Void caseEnumLiteral(EnumLiteral enumLiteral) {
		builder.append(enumLiteral.getEPackageName());
		builder.append("::");
		builder.append(enumLiteral.getEEnumName());
		builder.append("::");
		builder.append(enumLiteral.getEEnumLiteralName());
		return null;
	}

	@Override
	public Void caseError(Error error) {
		builder.append("***ERROR***");
		return null;
	}

	@Override
	public Void caseIntegerLiteral(IntegerLiteral object) {
		builder.append(object.getValue());
		return null;
	}

	@Override
	public Void caseLambda(Lambda lambda) {
		doSwitch(lambda.getParameters().get(0));
		builder.append(" | ");
		doSwitch(lambda.getExpression());
		return null;
	}

	@Override
	public Void caseLet(Let let) {
		builder.append("let ");
		final StringBuilder previousBuilder = builder;
		builder = new StringBuilder();
		for (Binding binding : let.getBindings()) {
			doSwitch(binding);
			builder.append(COMMA);
		}
		previousBuilder.append(builder.substring(0, builder.length() - 2));
		builder.append(" in ");
		doSwitch(let.getBody());
		return super.caseLet(let);
	}

	@Override
	public Void caseNullLiteral(NullLiteral nullLiteral) {
		builder.append("null");
		return null;
	}

	@Override
	public Void caseSequenceInExtensionLiteral(SequenceInExtensionLiteral sequenceInExtensionLiteral) {
		builder.append("Sequence{");
		final StringBuilder previousBuilder = builder;
		builder = new StringBuilder();
		for (Expression value : sequenceInExtensionLiteral.getValues()) {
			doSwitch(value);
			builder.append(COMMA);
		}
		previousBuilder.append(builder.substring(0, builder.length() - 2));
		builder.append(CLOSING_BRACKET);
		return null;
	}

	@Override
	public Void caseRealLiteral(RealLiteral realLiteral) {
		builder.append(realLiteral.getValue());
		return null;
	}

	@Override
	public Void caseSetInExtensionLiteral(SetInExtensionLiteral setInExtensionLiteral) {
		builder.append("OrderedSet{");
		final StringBuilder previousBuilder = builder;
		builder = new StringBuilder();
		for (Expression value : setInExtensionLiteral.getValues()) {
			doSwitch(value);
			builder.append(COMMA);
		}
		previousBuilder.append(builder.substring(0, builder.length() - 2));
		builder.append(CLOSING_BRACKET);
		return null;
	}

	@Override
	public Void caseStringLiteral(StringLiteral stringLiteral) {
		builder.append("'");
		builder.append(stringLiteral.getValue());
		builder.append("'");
		return null;
	}

	@Override
	public Void caseTypeSetLiteral(TypeSetLiteral typeSetLiteral) {
		builder.append("{");
		final StringBuilder previousBuilder = builder;
		builder = new StringBuilder();
		for (TypeLiteral type : typeSetLiteral.getTypes()) {
			doSwitch(type);
			builder.append(" | ");
		}
		previousBuilder.append(builder.substring(0, builder.length() - 3));
		builder.append(CLOSING_BRACKET);
		return null;
	}

	@Override
	public Void caseVariableDeclaration(VariableDeclaration variableDeclaration) {
		builder.append(variableDeclaration.getName());
		if (variableDeclaration.getType() != null) {
			builder.append(" : ");
			doSwitch(variableDeclaration.getType());
		}
		builder.append(" = ");
		doSwitch(variableDeclaration.getExpression());
		return null;
	}

	@Override
	public Void caseVarRef(VarRef varRef) {
		builder.append(varRef.getVariableName());
		return null;
	}

}
