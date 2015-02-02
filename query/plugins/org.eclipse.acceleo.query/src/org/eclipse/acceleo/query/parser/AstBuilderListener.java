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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.misc.Nullable;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.ast.Error;
import org.eclipse.acceleo.query.ast.ErrorTypeLiteral;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.Literal;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.parser.QueryParser.AddContext;
import org.eclipse.acceleo.query.parser.QueryParser.AndContext;
import org.eclipse.acceleo.query.parser.QueryParser.ApplyContext;
import org.eclipse.acceleo.query.parser.QueryParser.AsContext;
import org.eclipse.acceleo.query.parser.QueryParser.AsTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.BooleanTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.CallExpContext;
import org.eclipse.acceleo.query.parser.QueryParser.CallServiceContext;
import org.eclipse.acceleo.query.parser.QueryParser.CompContext;
import org.eclipse.acceleo.query.parser.QueryParser.EAContentContext;
import org.eclipse.acceleo.query.parser.QueryParser.EContainerContext;
import org.eclipse.acceleo.query.parser.QueryParser.EContentContext;
import org.eclipse.acceleo.query.parser.QueryParser.EInverseContext;
import org.eclipse.acceleo.query.parser.QueryParser.EnumOrClassifierLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.ExplicitSeqLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.ExplicitSetLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.FalseLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.FeatureContext;
import org.eclipse.acceleo.query.parser.QueryParser.FilterContext;
import org.eclipse.acceleo.query.parser.QueryParser.ImpliesContext;
import org.eclipse.acceleo.query.parser.QueryParser.IntTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.IntegerLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.IsKindContext;
import org.eclipse.acceleo.query.parser.QueryParser.IsTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.IterationCallContext;
import org.eclipse.acceleo.query.parser.QueryParser.LiteralContext;
import org.eclipse.acceleo.query.parser.QueryParser.MinContext;
import org.eclipse.acceleo.query.parser.QueryParser.ModelObjectTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.MultContext;
import org.eclipse.acceleo.query.parser.QueryParser.NavigationSegmentContext;
import org.eclipse.acceleo.query.parser.QueryParser.NotContext;
import org.eclipse.acceleo.query.parser.QueryParser.NullLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.OrContext;
import org.eclipse.acceleo.query.parser.QueryParser.RealLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.RealTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.SeqLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.SeqTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.ServiceCallContext;
import org.eclipse.acceleo.query.parser.QueryParser.SetLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.SetTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.StrTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.StringLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.TrueLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.TypeLiteralContext;
import org.eclipse.acceleo.query.parser.QueryParser.VarRefContext;
import org.eclipse.acceleo.query.parser.QueryParser.VariableDefinitionContext;
import org.eclipse.acceleo.query.runtime.AcceleoQueryEvaluationException;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine.AstResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.impl.EvaluationServices;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;

/**
 * The {@link AstBuilderListener} builds an AST when pluged into the parser.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class AstBuilderListener extends QueryBaseListener {

	/**
	 * <code>not<code> service name.
	 */
	public static final String NOT_SERVICE_NAME = "not";

	/**
	 * <code>not<code> operator.
	 */
	public static final String NOT_OPERATOR = "not";

	/**
	 * <code>&lt;&gt;<code> service name.
	 */
	public static final String DIFFERS_SERVICE_NAME = "differs";

	/**
	 * <code>&lt;&gt;<code> operator.
	 */
	public static final String DIFFERS_OPERATOR = "<>";

	/**
	 * <code>=<code> service name.
	 */
	public static final String EQUALS_SERVICE_NAME = "equals";

	/**
	 * <code>=<code> operator.
	 */
	public static final String EQUALS_OPERATOR = "=";

	/**
	 * <code>&gt;=<code> service name.
	 */
	public static final String GREATER_THAN_EQUAL_SERVICE_NAME = "greaterThanEqual";

	/**
	 * <code>&gt;=<code> operator.
	 */
	public static final String GREATER_THAN_EQUAL_OPERATOR = ">=";

	/**
	 * <code>&gt;<code> service name.
	 */
	public static final String GREATER_THAN_SERVICE_NAME = "greaterThan";

	/**
	 * <code>&gt;<code> operator.
	 */
	public static final String GREATER_THAN_OPERATOR = ">";

	/**
	 * <code>&lt;=<code> service name.
	 */
	public static final String LESS_THAN_EQUAL_SERVICE_NAME = "lessThanEqual";

	/**
	 * <code>&lt;=<code> operator.
	 */
	public static final String LESS_THAN_EQUAL_OPERATOR = "<=";

	/**
	 * <code>&lt;<code> service name.
	 */
	public static final String LESS_THAN_SERVICE_NAME = "lessThan";

	/**
	 * <code>&lt;<code> operator.
	 */
	public static final String LESS_THAN_OPERATOR = "<";

	/**
	 * <code>/<code> service name.
	 */
	public static final String DIV_SERVICE_NAME = "divOp";

	/**
	 * <code>/<code> operator.
	 */
	public static final String DIV_OPERATOR = "/";

	/**
	 * <code>*<code> service name.
	 */
	public static final String MULT_SERVICE_NAME = "mult";

	/**
	 * <code>*<code> operator.
	 */
	public static final String MULT_OPERATOR = "*";

	/**
	 * <code>-<code> service name.
	 */
	public static final String SUB_SERVICE_NAME = "sub";

	/**
	 * <code>-<code> operator.
	 */
	public static final String SUB_OPERATOR = "-";

	/**
	 * <code>+<code> service name.
	 */
	public static final String ADD_SERVICE_NAME = "add";

	/**
	 * <code>+<code> operator.
	 */
	public static final String ADD_OPERATOR = "+";

	/**
	 * <code>-<code> service name.
	 */
	public static final String UNARY_MIN_SERVICE_NAME = "unaryMin";

	/**
	 * <code>-<code> operator.
	 */
	public static final String UNARY_MIN_OPERATOR = "-";

	/**
	 * <code>and<code> service name.
	 */
	public static final String AND_SERVICE_NAME = "and";

	/**
	 * <code>and<code> operator.
	 */
	public static final String AND_OPERATOR = "and";

	/**
	 * <code>or<code> service name.
	 */
	public static final String OR_SERVICE_NAME = "or";

	/**
	 * <code>or<code> operator.
	 */
	public static final String OR_OPERATOR = "or";

	/**
	 * <code>implies<code> service name.
	 */
	public static final String IMPLIES_SERVICE_NAME = "implies";

	/**
	 * <code>implies<code> operator.
	 */
	public static final String IMPLIES_OPERATOR = "implies";

	/**
	 * {@link Set} of operator service names.
	 */
	public static final Set<String> OPERATOR_SERVICE_NAMES = initOperatorServiceNames();

	/**
	 * This should not happen.
	 */
	private static final String THIS_SHOULDN_T_HAPPEN = "This shouldn't happen.";

	/**
	 * Error listener.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private final class QueryErrorListener extends BaseErrorListener {
		@Override
		public void syntaxError(@NotNull Recognizer<?, ?> recognizer, @Nullable Object offendingSymbol,
				int line, int charPositionInLine, @NotNull String msg, @Nullable RecognitionException e) {
			if (e != null) {
				if (e.getCtx() instanceof IterationCallContext) {
					errorRule = QueryParser.RULE_expression;
					pushError(builder.errorExpression());
				} else if (e.getCtx() instanceof TypeLiteralContext) {
					if (e.getCtx().getParent() instanceof VariableDefinitionContext) {
						errorRule = QueryParser.RULE_expression;
						final String variableName = e.getCtx().getParent().getChild(0).getText();
						final ErrorTypeLiteral type = builder.errorTypeLiteral(new String[] {});
						errors.add(type);
						final Expression variableExpression = pop();
						push(builder.variableDeclaration(variableName, type, variableExpression));
						pushError(builder.errorExpression());
					} else if (!(stack.peek() instanceof TypeLiteral)) {
						errorRule = QueryParser.RULE_typeLiteral;
						pushError(builder.errorTypeLiteral(new String[] {}));
					}
				} else if (e.getCtx() instanceof VariableDefinitionContext) {
					if (e.getCtx().getChildCount() > 0) {
						errorRule = QueryParser.RULE_expression;
						final String variableName = e.getCtx().getChild(0).getText();
						final TypeLiteral type = createModelType((ModelObjectTypeContext)e.getCtx().getChild(
								2));
						final Expression variableExpression = pop();
						push(builder.variableDeclaration(variableName, type, variableExpression));
					} else {
						final Expression variableExpression = pop();
						errorRule = QueryParser.RULE_variableDefinition;
						pushError(builder.errorVariableDeclaration(variableExpression));
					}
					pushError(builder.errorExpression());
				} else if (e.getCtx() instanceof CallExpContext) {
					errorRule = QueryParser.RULE_navigationSegment;
					pushError(builder.errorCollectionCall(pop()));
				} else if (e.getCtx() instanceof NavigationSegmentContext) {
					errorRule = QueryParser.RULE_navigationSegment;
					pushError(builder.errorFeatureAccessOrCall(pop()));
				} else {
					errorRule = e.getCtx().getRuleIndex();
					switch (e.getCtx().getRuleIndex()) {
						case QueryParser.RULE_expression:
							pushError(builder.errorExpression());
							break;

						default:
							break;
					}
				}
			} else if (recognizer instanceof QueryParser) {
				final QueryParser parser = (QueryParser)recognizer;
				if (parser.getContext() instanceof EnumOrClassifierLitContext) {
					errorRule = QueryParser.RULE_typeLiteral;
					final ParseTree firstChild = parser.getContext().getChild(0);
					if (firstChild.getChildCount() == 1) {
						pushError(builder.errorTypeLiteral(new String[] {firstChild.getChild(0).getText(), }));
					} else if (firstChild.getChildCount() == 3) {
						pushError(builder.errorTypeLiteral(new String[] {firstChild.getChild(0).getText(),
								firstChild.getChild(2).getText(), }));
					} else {
						throw new UnsupportedOperationException("there is no error then...");
					}
				}
			}
		}
	}

	/**
	 * No error rule marker.
	 */
	private static final int NO_ERROR = -1;

	/**
	 * Child count used to identify fully qualified names in a production.
	 */
	private static final int FULLY_QUALIFIED_NAME = 3;

	/**
	 * Message logged when an internal error occurs.
	 */
	private static final String INTERNAL_ERROR_MSG = "Internal exception occured while evaluating an expression";

	/**
	 * The evaluation stack used to hold temporary results.
	 */
	private Stack<Object> stack = new Stack<Object>();

	/**
	 * The last rule index if any error. see {@link QueryParser}.
	 */
	private int errorRule = -1;

	/**
	 * The {@link List} of {@link Error}.
	 */
	private final List<Error> errors = new ArrayList<Error>();

	/**
	 * The {@link ANTLRErrorListener} pushing {@link org.eclipse.acceleo.query.ast.Error Error}.
	 */
	private final ANTLRErrorListener errorListener = new QueryErrorListener();

	/**
	 * Ast Builder.
	 */
	private AstBuilder builder = new AstBuilder();

	/**
	 * {@link org.eclipse.emf.ecore.EPackage} instances that are available during evaluation.
	 */
	private IQueryEnvironment environment;

	/**
	 * Creates a new {@link AstBuilderListener}.
	 * 
	 * @param environment
	 *            the package provider
	 */
	public AstBuilderListener(IQueryEnvironment environment) {
		this.environment = environment;
	}

	/**
	 * Gets the {@link Set} of operator service names.
	 * 
	 * @return the {@link Set} of operator service names
	 */
	private static Set<String> initOperatorServiceNames() {
		final Set<String> result = new LinkedHashSet<String>();

		result.add(ADD_SERVICE_NAME);
		result.add(AND_SERVICE_NAME);
		result.add(DIFFERS_SERVICE_NAME);
		result.add(DIV_SERVICE_NAME);
		result.add(EQUALS_SERVICE_NAME);
		result.add(GREATER_THAN_EQUAL_SERVICE_NAME);
		result.add(GREATER_THAN_SERVICE_NAME);
		result.add(IMPLIES_SERVICE_NAME);
		result.add(LESS_THAN_EQUAL_SERVICE_NAME);
		result.add(LESS_THAN_SERVICE_NAME);
		result.add(MULT_SERVICE_NAME);
		result.add(NOT_SERVICE_NAME);
		result.add(OR_SERVICE_NAME);
		result.add(SUB_SERVICE_NAME);
		result.add(UNARY_MIN_SERVICE_NAME);

		return result;
	}

	/**
	 * Returns the {@link AstResult}.
	 * 
	 * @return the {@link AstResult}.
	 */
	public AstResult getAstResult() {
		return new AstResult(pop(), errors);
	}

	/**
	 * Pop the top of the stack.
	 * 
	 * @return the value on top of the stack
	 */
	private Expression pop() {
		try {
			return (Expression)stack.pop();
		} catch (EmptyStackException e) {
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG, e);
		} catch (ClassCastException cce) {
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG, cce);
		}
	}

	/**
	 * Pop the top of the stack and returns it as a string.
	 * 
	 * @return the value on top of the stack.
	 */
	private String popString() {
		try {
			return (String)stack.pop();
		} catch (EmptyStackException e) {
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG, e);
		} catch (ClassCastException e2) {
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG, e2);
		}
	}

	/**
	 * Pop the top of the stack and returns it as a {@link VariableDeclaration}.
	 * 
	 * @return the value on top of the stack.
	 */
	private VariableDeclaration popVariableDeclaration() {
		try {
			return (VariableDeclaration)stack.pop();
		} catch (EmptyStackException e) {
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG, e);
		} catch (ClassCastException e2) {
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG, e2);
		}
	}

	/**
	 * Pop the top of the stack and returns it as a type literal.
	 * 
	 * @return the value on top of the stack.
	 */
	private TypeLiteral popTypeLiteral() {
		try {
			return (TypeLiteral)stack.pop();
		} catch (EmptyStackException e) {
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG, e);
		} catch (ClassCastException e2) {
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG, e2);
		}
	}

	/**
	 * Pop the top of the stack and returns it as a string.
	 * 
	 * @return the value on top of the stack.
	 */
	private Expression[] popArgs() {
		try {
			return (Expression[])stack.pop();
		} catch (EmptyStackException e) {
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG, e);
		} catch (ClassCastException e2) {
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG, e2);
		}
	}

	/**
	 * push an object on the stack.
	 * 
	 * @param obj
	 *            the pushed object.
	 */
	private void push(Object obj) {
		this.stack.push(obj);
	}

	/**
	 * Pushes an {@link Error} on the stack.
	 * 
	 * @param error
	 *            the {@link Error} to push
	 */
	private void pushError(Error error) {
		errors.add(error);
		this.stack.push(error);
	}

	@Override
	public void exitIntType(IntTypeContext ctx) {
		push(builder.typeLiteral(java.lang.Boolean.class));
	}

	@Override
	public void exitFalseLit(FalseLitContext ctx) {
		push(builder.booleanLiteral(false));
	}

	@Override
	public void exitRealType(RealTypeContext ctx) {
		push(builder.realLiteral(Double.parseDouble(ctx.getText())));

	}

	@Override
	public void exitTrueLit(TrueLitContext ctx) {
		push(builder.booleanLiteral(true));

	}

	@Override
	public void exitSeqType(SeqTypeContext ctx) {
		TypeLiteral elementType = popTypeLiteral();
		push(builder.collectionTypeLiteral(List.class, elementType));
	}

	@Override
	public void exitSetType(SetTypeContext ctx) {
		TypeLiteral elementType = popTypeLiteral();
		push(builder.collectionTypeLiteral(Set.class, elementType));
	}

	@Override
	public void exitNot(NotContext ctx) {
		push(builder.callService(CallType.CALLSERVICE, NOT_SERVICE_NAME, pop()));
	}

	@Override
	public void exitStringLit(StringLitContext ctx) {
		String text = ctx.getText();
		push(builder.stringLiteral(text.substring(1, text.length() - 1)));
	}

	@Override
	public void exitRealLit(RealLitContext ctx) {
		push(builder.realLiteral(Double.valueOf(ctx.getText())));
	}

	@Override
	public void exitStrType(StrTypeContext ctx) {
		push(builder.typeLiteral(java.lang.String.class));
	}

	@Override
	public void exitOr(OrContext ctx) {
		pushBinary(OR_SERVICE_NAME);
	}

	@Override
	public void exitImplies(ImpliesContext ctx) {
		pushBinary(IMPLIES_SERVICE_NAME);
	}

	@Override
	public void exitBooleanType(BooleanTypeContext ctx) {
		push(builder.typeLiteral(java.lang.Boolean.class));
	}

	@Override
	public void exitIntegerLit(IntegerLitContext ctx) {
		push(builder.integerLiteral(Integer.valueOf(ctx.getText())));
	}

	@Override
	public void exitAnd(AndContext ctx) {
		pushBinary(AND_SERVICE_NAME);
	}

	@Override
	public void exitVarRef(VarRefContext ctx) {
		push(builder.varRef(ctx.getText()));
	}

	@Override
	public void exitFeature(FeatureContext ctx) {
		Expression receiver = pop();
		push(builder.featureAccess(receiver, ctx.getChild(1).getText()));
	}

	// @Override
	// public void exitQualifiedName(QualifiedNameContext ctx) {
	// StringBuilder builder = new StringBuilder().append(ctx.getChild(0));
	// if (ctx.getChildCount() == FULLY_QUALIFIED_NAME) {
	// builder.append('.').append(ctx.getChild(2));
	// }
	// push(builder.toString());
	// }

	/**
	 * Factorization of binary operation evaluation.
	 * 
	 * @param service
	 *            the called service.
	 */
	private void pushBinary(String service) {
		Expression op2 = pop();
		Expression op1 = pop();
		push(builder.callService(CallType.CALLSERVICE, service, op1, op2));
	}

	/**
	 * Evaluation stack is as follows : [/.../,receiver, service name (string), START_EXPR_SEQ, expr*].
	 * 
	 * @param ctx
	 *            the parsing context
	 */
	@Override
	public void exitServiceCall(ServiceCallContext ctx) {
		int childCount = ctx.getChild(2).getChildCount();
		// CHECKSTYLE:OFF
		int argc = 1 + (childCount == 0 ? 0 : 1 + childCount / 2);
		// CHECKSTYLE:ON
		Expression[] args = new Expression[argc];
		for (int i = argc - 1; i >= 1; i--) {
			args[i] = pop();
		}
		String serviceName = ctx.getChild(0).getText().replace("::", ".");
		args[0] = pop();
		push(serviceName);
		push(args);
	}

	@Override
	public void exitMin(MinContext ctx) {
		push(builder.callService(CallType.CALLSERVICE, UNARY_MIN_SERVICE_NAME, pop()));
	}

	@Override
	public void exitAdd(AddContext ctx) {
		final String op = ctx.getChild(1).getText();

		if (ADD_OPERATOR.equals(op)) {
			pushBinary(ADD_SERVICE_NAME);
		} else if (SUB_OPERATOR.equals(op)) {
			pushBinary(SUB_SERVICE_NAME);
		} else {
			throw new AcceleoQueryEvaluationException(THIS_SHOULDN_T_HAPPEN);
		}
	}

	@Override
	public void exitMult(MultContext ctx) {
		final String op = ctx.getChild(1).getText();

		if (MULT_OPERATOR.equals(op)) {
			pushBinary(MULT_SERVICE_NAME);
		} else if (DIV_OPERATOR.equals(op)) {
			pushBinary(DIV_SERVICE_NAME);
		} else {
			throw new AcceleoQueryEvaluationException(THIS_SHOULDN_T_HAPPEN);
		}
	}

	@Override
	public void exitComp(CompContext ctx) {
		final String op = ctx.getChild(1).getText();

		if (LESS_THAN_OPERATOR.equals(op)) {
			pushBinary(LESS_THAN_SERVICE_NAME);
		} else if (LESS_THAN_EQUAL_OPERATOR.equals(op)) {
			pushBinary(LESS_THAN_EQUAL_SERVICE_NAME);
		} else if (GREATER_THAN_OPERATOR.equals(op)) {
			pushBinary(GREATER_THAN_SERVICE_NAME);
		} else if (GREATER_THAN_EQUAL_OPERATOR.equals(op)) {
			pushBinary(GREATER_THAN_EQUAL_SERVICE_NAME);
		} else if (EQUALS_OPERATOR.equals(op)) {
			pushBinary(EQUALS_SERVICE_NAME);
		} else if (DIFFERS_OPERATOR.equals(op)) {
			pushBinary(DIFFERS_SERVICE_NAME);
		} else {
			throw new AcceleoQueryEvaluationException(THIS_SHOULDN_T_HAPPEN);
		}
	}

	// @Override
	// public void exitModelObjectType(ModelObjectTypeContext ctx) {
	// EClassifier type;
	// if (ctx.getChild(0).getChildCount() == FULLY_QUALIFIED_NAME) {
	// // fully qualified name encountered.
	// String nsPrefix = ctx.getChild(0).getChild(0).getText();
	// String name = ctx.getChild(0).getChild(2).getText();
	// type = environment.getEPackageProvider().getType(nsPrefix, name);
	// } else {
	// String name = ctx.getChild(0).getChild(0).getText();
	// type = environment.getEPackageProvider().getType(name);
	// }
	// if (type == null || !(type instanceof EClass)) {
	// push(EvaluationServices.NOTHING);
	// } else {
	// push(builder.typeLiteral(type));
	// }
	// }

	/**
	 * Creates a {@link TypeLiteral} given a {@link ModelObjectTypeContext}.
	 * 
	 * @param ctx
	 *            the context
	 * @return the type literal.
	 */
	private TypeLiteral createModelType(ModelObjectTypeContext ctx) {
		final TypeLiteral result;

		EClassifier type;
		String nsPrefix = null;
		String name = null;
		if (ctx.getChild(0).getChildCount() == FULLY_QUALIFIED_NAME) {
			// fully qualified name encountered.
			nsPrefix = ctx.getChild(0).getChild(0).getText();
			name = ctx.getChild(0).getChild(2).getText();
			type = environment.getEPackageProvider().getType(nsPrefix, name);
		} else {
			name = ctx.getChild(0).getChild(0).getText();
			type = environment.getEPackageProvider().getType(name);
		}
		if (type == null) {
			List<String> segments = new ArrayList<String>(2);
			if (nsPrefix != null) {
				segments.add(nsPrefix);
			}
			if (name != null) {
				segments.add(name);
			}
			result = builder.errorTypeLiteral(segments.toArray(new String[segments.size()]));
			errors.add((ErrorTypeLiteral)result);
		} else {
			result = builder.typeLiteral(type);
		}

		return result;
	}

	/**
	 * Factorization of the filtered services call (eContainer, eContents, eAllContents, etc.).
	 * 
	 * @param serviceName
	 *            the name of the called service
	 * @param ctx
	 *            the rule context
	 */
	private void callFilteredService(String serviceName, ParserRuleContext ctx) {
		if (errorRule == NO_ERROR) {
			if (ctx.getChildCount() == 3) {
				// filtered version.
				// Expression type = pop();
				final Expression type;
				if (ctx.getChild(1) instanceof ModelObjectTypeContext) {
					type = createModelType((ModelObjectTypeContext)ctx.getChild(1));
				} else {
					type = popTypeLiteral();
				}
				Expression receiver = pop();
				push(serviceName);
				push(new Expression[] {receiver, type });
			} else {
				Expression receiver = pop();
				push(serviceName);
				push(new Expression[] {receiver });
			}
		} else {
			TypeLiteral type = popTypeLiteral();
			Expression receiver = pop();
			push(serviceName);
			push(new Expression[] {receiver, type });
		}
	}

	/**
	 * The stack contains, in that order, [receiver, type literal] {@inheritDoc}.
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitEContent(org.eclipse.acceleo.query.parser.QueryParser.EContentContext)
	 */
	@Override
	public void exitEContent(EContentContext ctx) {
		callFilteredService("eContents", ctx);
	}

	/**
	 * The stack contains, in that order, [receiver, type literal] {@inheritDoc}.
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitEContent(org.eclipse.acceleo.query.parser.QueryParser.EContentContext)
	 */
	@Override
	public void exitEAContent(EAContentContext ctx) {
		callFilteredService("eAllContents", ctx);
	}

	/**
	 * The stack contains, in that order, [receiver, type literal] {@inheritDoc}.
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitEContent(org.eclipse.acceleo.query.parser.QueryParser.EContentContext)
	 */
	@Override
	public void exitEContainer(EContainerContext ctx) {
		callFilteredService("eContainer", ctx);
	}

	/**
	 * The stack contains, in that order, [receiver, type literal] {@inheritDoc}.
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitEContent(org.eclipse.acceleo.query.parser.QueryParser.EContentContext)
	 */
	@Override
	public void exitEInverse(EInverseContext ctx) {
		callFilteredService("eInverse", ctx);
	}

	@Override
	public void exitFilter(FilterContext ctx) {
		callFilteredService("filter", ctx);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitAs(org.eclipse.acceleo.query.parser.QueryParser.AsContext)
	 */
	@Override
	public void exitAs(AsContext ctx) {
		callFilteredService("oclAsType", ctx);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitAsType(org.eclipse.acceleo.query.parser.QueryParser.AsTypeContext)
	 */
	@Override
	public void exitAsType(AsTypeContext ctx) {
		callFilteredService("oclAsType", ctx);
	}

	@Override
	public void exitCallService(CallServiceContext ctx) {
		if (errorRule != QueryParser.RULE_navigationSegment) {
			Expression[] args = popArgs();
			String serviceName = popString();
			push(builder.callService(CallType.COLLECTIONCALL, serviceName, args));
		}
	}

	@Override
	public void exitApply(ApplyContext ctx) {
		Expression[] args = popArgs();
		String serviceName = popString();
		push(builder.callService(CallType.CALLORAPPLY, serviceName, args));
	}

	@Override
	public void exitIsType(IsTypeContext ctx) {
		callFilteredService("oclIsTypeOf", ctx);
	}

	@Override
	public void exitIsKind(IsKindContext ctx) {
		callFilteredService("oclIsKindOf", ctx);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitVariableDefinition(org.eclipse.acceleo.query.parser.QueryParser.VariableDefinitionContext)
	 */
	@Override
	public void exitVariableDefinition(VariableDefinitionContext ctx) {
		// If the error flag is raised an error occurred and the variable is already
		// there.
		if (errorRule == NO_ERROR) {
			final VariableDeclaration variableDeclaration;

			final Expression variableExpression = pop();
			if (ctx.getChildCount() == 4) {
				variableDeclaration = builder.variableDeclaration(ctx.getChild(0).getText(),
						createModelType((ModelObjectTypeContext)ctx.getChild(2)), variableExpression);
			} else {
				variableDeclaration = builder.variableDeclaration(ctx.getChild(0).getText(),
						variableExpression);
			}

			stack.push(variableDeclaration);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitSelect(org.eclipse.acceleo.query.parser.QueryParser.SelectContext)
	 */
	@Override
	public void exitIterationCall(IterationCallContext ctx) {
		// the stack contains [receiver, variableDef, expression]
		final String serviceName = ctx.getChild(0).getText();
		final Expression ast = pop();
		final VariableDeclaration iterator;
		final EvaluationServices service = new EvaluationServices(environment, true);
		final Lambda lambda;
		iterator = popVariableDeclaration();
		lambda = builder.lambda(ast, new AstEvaluator(service), iterator);
		push(serviceName);
		push(new Expression[] {iterator.getExpression(), lambda });
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#enterIterationCall(org.eclipse.acceleo.query.parser.QueryParser.IterationCallContext)
	 */
	@Override
	public void enterIterationCall(IterationCallContext ctx) {
		super.enterIterationCall(ctx);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitEnumOrClassifierLit(org.eclipse.acceleo.query.parser.QueryParser.EnumOrClassifierLitContext)
	 */
	@Override
	public void exitEnumOrClassifierLit(EnumOrClassifierLitContext ctx) {
		if (errorRule == NO_ERROR) {
			final Literal toPush;
			// checks whether the enum name is fully qualified or not
			String literalName = ctx.getChild(2).getText();
			if (ctx.getChild(0).getChildCount() == 1) { // unqualified
				toPush = twoSegmentEnumOrClassifierLiteral(ctx, literalName);
			} else {
				toPush = threeSegmentEnumLiteral(ctx, literalName);
			}

			push(toPush);
		}
	}

	/**
	 * Gets the {@link Literal} for the given {@link EnumOrClassifierLitContext} with two segments and the
	 * given {@link EEnumLiteral#getName() literal name} or {@link EClassifier#getName() eclassifier name}.
	 * 
	 * @param ctx
	 *            the {@link EnumOrClassifierLitContext} with two segments
	 * @param literalName
	 *            the {@link EEnumLiteral#getName()} or {@link EClassifier#getName() eclassifier name}
	 * @return the {@link Literal} for the given {@link EnumOrClassifierLitContext} with two segments and the
	 *         given {@link EEnumLiteral#getName() literal name} or {@link EClassifier#getName() eclassifier
	 *         name}
	 */
	private Literal twoSegmentEnumOrClassifierLiteral(EnumOrClassifierLitContext ctx, String literalName) {
		final Literal toPush;
		EEnumLiteral literal;
		String enumName = ctx.getChild(0).getChild(0).getText();
		literal = environment.getEPackageProvider().getEnumLiteral(enumName, literalName);
		if (literal != null) {
			toPush = builder.enumLiteral(literal);
		} else {
			final EClassifier classifier = environment.getEPackageProvider().getType(enumName, literalName);
			if (classifier != null) {
				toPush = builder.typeLiteral(classifier);
			} else {
				toPush = builder.errorTypeLiteral(new String[] {enumName, literalName, });
			}
		}
		return toPush;
	}

	/**
	 * Gets the {@link Literal} for the given {@link EnumOrClassifierLitContext} with two segments and the
	 * given {@link EEnumLiteral#getName() literal name}.
	 * 
	 * @param ctx
	 *            the {@link EnumOrClassifierLitContext} with two segments
	 * @param literalName
	 *            the {@link EEnumLiteral#getName() literal name}
	 * @return the {@link Literal} for the given {@link EnumOrClassifierLitContext} with two segments and the
	 *         given {@link EEnumLiteral#getName() literal name}
	 */
	private Literal threeSegmentEnumLiteral(EnumOrClassifierLitContext ctx, String literalName) {
		final Literal toPush;
		EEnumLiteral literal;
		String nsPrefix = ctx.getChild(0).getChild(0).getText();
		String enumName = ctx.getChild(0).getChild(2).getText();
		literal = environment.getEPackageProvider().getEnumLiteral(nsPrefix, enumName, literalName);
		if (literal != null) {
			toPush = builder.enumLiteral(literal);
		} else {
			toPush = builder.errorTypeLiteral(new String[] {nsPrefix, enumName, literalName, });
		}
		return toPush;
	}

	/**
	 * Gets the {@link ANTLRErrorListener}.
	 * 
	 * @return the {@link ANTLRErrorListener}
	 */
	public ANTLRErrorListener getErrorListener() {
		return errorListener;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitNullLit(org.eclipse.acceleo.query.parser.QueryParser.NullLitContext)
	 */
	@Override
	public void exitNullLit(NullLitContext ctx) {
		push(builder.nullLiteral());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitSetLit(org.eclipse.acceleo.query.parser.QueryParser.SetLitContext)
	 */
	@Override
	public void exitSetLit(SetLitContext ctx) {
		push(builder.setInExtension(getExpressions(ctx)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitExplicitSetLit(org.eclipse.acceleo.query.parser.QueryParser.ExplicitSetLitContext)
	 */
	@Override
	public void exitExplicitSetLit(ExplicitSetLitContext ctx) {
		push(builder.setInExtension(getExpressions(ctx)));
	}

	/**
	 * Gets the {@link List} of {@link Expression} from the given {@link LiteralContext} (
	 * {@link SetLitContext} or {@link ExplicitSetLitContext} or {@link SeqLitContext} or
	 * {@link ExplicitSeqLitContext}).
	 * 
	 * @param ctx
	 *            the {@link LiteralContext} ( {@link SetLitContext} or {@link ExplicitSetLitContext} or
	 *            {@link SeqLitContext} or {@link ExplicitSeqLitContext})
	 * @return the {@link List} of {@link Expression} from the given {@link LiteralContext} (
	 *         {@link SetLitContext} or {@link ExplicitSetLitContext} or {@link SeqLitContext} or
	 *         {@link ExplicitSeqLitContext})
	 */
	private List<Expression> getExpressions(LiteralContext ctx) {
		final int nbExpressions = (ctx.getChild(1).getChildCount() + 1) / 2;
		final Expression[] expressions = new Expression[nbExpressions];

		for (int i = nbExpressions - 1; i >= 0; --i) {
			expressions[i] = pop();
		}

		return Arrays.asList(expressions);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitSeqLit(org.eclipse.acceleo.query.parser.QueryParser.SeqLitContext)
	 */
	@Override
	public void exitSeqLit(SeqLitContext ctx) {
		push(builder.sequenceInExtension(getExpressions(ctx)));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitExplicitSeqLit(org.eclipse.acceleo.query.parser.QueryParser.ExplicitSeqLitContext)
	 */
	@Override
	public void exitExplicitSeqLit(ExplicitSeqLitContext ctx) {
		push(builder.sequenceInExtension(getExpressions(ctx)));
	}

}
