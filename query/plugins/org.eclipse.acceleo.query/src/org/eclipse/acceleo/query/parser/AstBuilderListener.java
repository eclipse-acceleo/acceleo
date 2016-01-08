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
import java.util.Collection;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.misc.Nullable;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.acceleo.query.ast.And;
import org.eclipse.acceleo.query.ast.Binding;
import org.eclipse.acceleo.query.ast.BooleanLiteral;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.ast.CollectionTypeLiteral;
import org.eclipse.acceleo.query.ast.Conditional;
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.Error;
import org.eclipse.acceleo.query.ast.ErrorBinding;
import org.eclipse.acceleo.query.ast.ErrorCall;
import org.eclipse.acceleo.query.ast.ErrorConditional;
import org.eclipse.acceleo.query.ast.ErrorEnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorExpression;
import org.eclipse.acceleo.query.ast.ErrorFeatureAccessOrCall;
import org.eclipse.acceleo.query.ast.ErrorStringLiteral;
import org.eclipse.acceleo.query.ast.ErrorTypeLiteral;
import org.eclipse.acceleo.query.ast.ErrorVariableDeclaration;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.FeatureAccess;
import org.eclipse.acceleo.query.ast.Implies;
import org.eclipse.acceleo.query.ast.IntegerLiteral;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.Let;
import org.eclipse.acceleo.query.ast.Literal;
import org.eclipse.acceleo.query.ast.NullLiteral;
import org.eclipse.acceleo.query.ast.Or;
import org.eclipse.acceleo.query.ast.RealLiteral;
import org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral;
import org.eclipse.acceleo.query.ast.SetInExtensionLiteral;
import org.eclipse.acceleo.query.ast.StringLiteral;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.ast.TypeSetLiteral;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.parser.QueryParser.AddContext;
import org.eclipse.acceleo.query.parser.QueryParser.AndContext;
import org.eclipse.acceleo.query.parser.QueryParser.BindingContext;
import org.eclipse.acceleo.query.parser.QueryParser.BooleanTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.CallExpContext;
import org.eclipse.acceleo.query.parser.QueryParser.CallOrApplyContext;
import org.eclipse.acceleo.query.parser.QueryParser.ClassifierSetTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.ClassifierTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.ClassifierTypeRuleContext;
import org.eclipse.acceleo.query.parser.QueryParser.CollectionCallContext;
import org.eclipse.acceleo.query.parser.QueryParser.CompContext;
import org.eclipse.acceleo.query.parser.QueryParser.ConditionalContext;
import org.eclipse.acceleo.query.parser.QueryParser.EnumLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.ErrorClassifierTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.ErrorEnumLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.ErrorStringLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.ExplicitSeqLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.ExplicitSetLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.ExpressionContext;
import org.eclipse.acceleo.query.parser.QueryParser.FalseLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.FeatureContext;
import org.eclipse.acceleo.query.parser.QueryParser.ImpliesContext;
import org.eclipse.acceleo.query.parser.QueryParser.IntTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.IntegerLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.IterationCallContext;
import org.eclipse.acceleo.query.parser.QueryParser.LetExprContext;
import org.eclipse.acceleo.query.parser.QueryParser.LiteralContext;
import org.eclipse.acceleo.query.parser.QueryParser.MinContext;
import org.eclipse.acceleo.query.parser.QueryParser.MultContext;
import org.eclipse.acceleo.query.parser.QueryParser.NavigationSegmentContext;
import org.eclipse.acceleo.query.parser.QueryParser.NotContext;
import org.eclipse.acceleo.query.parser.QueryParser.NullLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.OrContext;
import org.eclipse.acceleo.query.parser.QueryParser.ParenContext;
import org.eclipse.acceleo.query.parser.QueryParser.RealLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.RealTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.SeqTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.ServiceCallContext;
import org.eclipse.acceleo.query.parser.QueryParser.SetTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.StrTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.StringLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.TrueLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.TypeLiteralContext;
import org.eclipse.acceleo.query.parser.QueryParser.VarRefContext;
import org.eclipse.acceleo.query.parser.QueryParser.VariableDefinitionContext;
import org.eclipse.acceleo.query.parser.QueryParser.XorContext;
import org.eclipse.acceleo.query.runtime.AcceleoQueryEvaluationException;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine.AstResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;

/**
 * The {@link AstBuilderListener} builds an AST when plugged into the parser.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class AstBuilderListener extends QueryBaseListener {

	/**
	 * The plugin ID.
	 */
	public static final String PLUGIN_ID = "org.eclipse.acceleo.query";

	/**
	 * OCL is kind of service name.
	 */
	public static final String OCL_IS_KIND_OF_SERVICE_NAME = "oclIsKindOf";

	/**
	 * OCL is type of service name.
	 */
	public static final String OCL_IS_TYPE_OF_SERVICE_NAME = "oclIsTypeOf";

	/**
	 * <code>if<code> operator.
	 */
	public static final String CONDITIONAL_OPERATOR = "if";

	/**
	 * <code>not<code> service name.
	 */
	public static final String NOT_SERVICE_NAME = "not";

	/**
	 * <code>not<code> operator.
	 */
	public static final String NOT_OPERATOR = "not";

	/**
	 * <code>let<code> operator.
	 */
	public static final String LET_OPERATOR = "let";

	/**
	 * <code>&lt;&gt;<code> service name.
	 */
	public static final String DIFFERS_SERVICE_NAME = "differs";

	/**
	 * <code>&lt;&gt;<code> operator.
	 */
	public static final String DIFFERS_OPERATOR = "<>";

	/**
	 * <code>!=<code> operator.
	 */
	public static final String DIFFERS_JAVA_OPERATOR = "!=";

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
	 * <code>xor<code> service name.
	 */
	public static final String XOR_SERVICE_NAME = "xor";

	/**
	 * <code>xor<code> operator.
	 */
	public static final String XOR_OPERATOR = "xor";

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
	 * Invalid type literal.
	 */
	private static final String INVALID_TYPE_LITERAL = "invalid type literal %s";

	/**
	 * Ambiguous {@link EEnumLiteral} message.
	 */
	private static final String AMBIGUOUS_ENUM_LITERAL = "several enumliterals are matching the literal name: %s, eenum : %s and package name : %s";

	/**
	 * Ambiguous {@link EClassifier} message.
	 */
	private static final String AMBIGUOUS_TYPE_LITERAL = "several types are matching the EClassifier name: %s , package name : %s";

	/**
	 * Number of children in {@link ConditionalContext}.
	 */
	private static final int CONDITIONAL_CONTEXT_CHILD_COUNT = 7;

	/**
	 * Error listener.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private final class QueryErrorListener extends BaseErrorListener {

		/**
		 * Missing expression message.
		 */
		private static final String MISSING_EXPRESSION = "missing expression";

		@Override
		public void syntaxError(@NotNull Recognizer<?, ?> recognizer, @Nullable Object offendingSymbol,
				int line, int charPositionInLine, @NotNull String msg, @Nullable RecognitionException e) {
			if (e != null) {
				if (e.getCtx() instanceof IterationCallContext) {
					iterationCallContextError(e);
				} else if (e.getCtx() instanceof TypeLiteralContext) {
					typeLiteralContextError(offendingSymbol, msg, e);
				} else if (e.getCtx() instanceof LiteralContext) {
					literalContextError(offendingSymbol, msg, e);
				} else if (e.getCtx() instanceof ClassifierTypeRuleContext) {
					classifierTypeRuleContextError(offendingSymbol, msg, e);
				} else if (e.getCtx() instanceof VariableDefinitionContext) {
					variableDefinitionContextError(offendingSymbol, e);
				} else if (e.getCtx() instanceof CallExpContext) {
					callExpContextError(offendingSymbol, e);
				} else if (e.getCtx() instanceof NavigationSegmentContext) {
					navigationSegmentContextError(offendingSymbol);
				} else if (e.getCtx() instanceof BindingContext) {
					bindingContextError(offendingSymbol, e);
				} else if (e.getCtx() instanceof ConditionalContext) {
					errorRule = QueryParser.RULE_expression;
				} else if (e.getCtx() instanceof ParenContext) {
					// nothing to do here
				} else {
					defaultError(offendingSymbol, msg, e);
				}
			} else if (recognizer instanceof QueryParser) {
				noRecognitionException(recognizer, offendingSymbol, msg);
			} else {
				final QueryParser parser = (QueryParser)recognizer;
				final Integer startPosition = Integer.valueOf(((EnumLitContext)parser.getContext()).start
						.getStartIndex());
				final Integer endPosition = Integer.valueOf(((Token)offendingSymbol).getStopIndex() + 1);
				diagnosticStack.push(new BasicDiagnostic(Diagnostic.WARNING, PLUGIN_ID, 0, msg, new Object[] {
						startPosition, endPosition, }));
			}
		}

		/**
		 * {@link ClassifierTypeRuleContext} error case.
		 * 
		 * @param offendingSymbol
		 *            the offending symbol
		 * @param msg
		 *            the error message
		 * @param e
		 *            the {@link RecognitionException}
		 */
		private void classifierTypeRuleContextError(Object offendingSymbol, String msg,
				RecognitionException e) {
			final ClassifierTypeRuleContext ctx = (ClassifierTypeRuleContext)e.getCtx();
			final Integer startPosition = Integer.valueOf(ctx.start.getStartIndex());
			final Integer endPosition = Integer.valueOf(((Token)offendingSymbol).getStopIndex() + 1);
			if (e.getCtx().getParent().getParent() instanceof VariableDefinitionContext) {
				errorRule = QueryParser.RULE_expression;
				final String variableName = e.getCtx().getParent().getChild(0).getText();
				final ErrorTypeLiteral errorTypeLiteral;
				if (ctx.getChildCount() > 0) {
					errorTypeLiteral = builder.errorTypeLiteral(false, new String[] {ctx.getChild(0)
							.getText(), });
				} else {
					errorTypeLiteral = builder.errorTypeLiteral(false, new String[] {});
				}
				startPositions.put(errorTypeLiteral, startPosition);
				endPositions.put(errorTypeLiteral, endPosition);
				diagnostic.add(new BasicDiagnostic(Diagnostic.ERROR, PLUGIN_ID, 0, String.format(
						INVALID_TYPE_LITERAL, ctx.getText()), new Object[] {errorTypeLiteral }));
				errors.add(errorTypeLiteral);
				final Expression variableExpression = pop();
				final VariableDeclaration variableDeclaration = builder.variableDeclaration(variableName,
						errorTypeLiteral, variableExpression);
				startPositions.put(variableDeclaration, startPosition);
				endPositions.put(variableDeclaration, endPosition);
				push(variableDeclaration);
				final ErrorExpression errorExpression = builder.errorExpression();
				pushError(errorExpression, MISSING_EXPRESSION);
				startPositions.put(errorExpression, endPosition);
				endPositions.put(errorExpression, endPosition);
			} else {
				errorRule = QueryParser.RULE_classifierTypeRule;
				final ErrorTypeLiteral errorTypeLiteral;
				if (ctx.getChildCount() > 0) {
					errorTypeLiteral = builder.errorTypeLiteral(false, new String[] {ctx.getChild(0)
							.getText(), });
				} else {
					errorTypeLiteral = builder.errorTypeLiteral(false, new String[] {});
				}
				startPositions.put(errorTypeLiteral, startPosition);
				endPositions.put(errorTypeLiteral, endPosition);
				pushError(errorTypeLiteral, "missing classifier literal");
			}
		}

		/**
		 * {@link IterationCallContext} error case.
		 * 
		 * @param e
		 *            the {@link RecognitionException}
		 */
		private void iterationCallContextError(RecognitionException e) {
			errorRule = QueryParser.RULE_expression;
			final ErrorExpression errorExpression = builder.errorExpression();
			pushError(errorExpression, MISSING_EXPRESSION);
			final Integer position = Integer.valueOf(((IterationCallContext)e.getCtx()).start
					.getStartIndex());
			startPositions.put(errorExpression, position);
			endPositions.put(errorExpression, position);
		}

		/**
		 * {@link TypeLiteralContext} error case.
		 * 
		 * @param offendingSymbol
		 *            the offending symbol
		 * @param msg
		 *            the error message
		 * @param e
		 *            the {@link RecognitionException}
		 */
		private void typeLiteralContextError(Object offendingSymbol, String msg, RecognitionException e) {
			final Integer startPosition = Integer.valueOf(((TypeLiteralContext)e.getCtx()).start
					.getStartIndex());
			final Integer endPosition = Integer.valueOf(((Token)offendingSymbol).getStopIndex() + 1);
			if (e.getCtx().getParent() instanceof VariableDefinitionContext) {
				errorRule = QueryParser.RULE_expression;
				final String variableName = e.getCtx().getParent().getChild(0).getText();
				final ErrorTypeLiteral type = builder.errorTypeLiteral(false, new String[] {});
				startPositions.put(type, startPosition);
				endPositions.put(type, endPosition);
				diagnostic.add(new BasicDiagnostic(Diagnostic.ERROR, PLUGIN_ID, 0, String.format(
						INVALID_TYPE_LITERAL, msg), new Object[] {type }));
				errors.add(type);
				final Expression variableExpression = pop();
				final VariableDeclaration variableDeclaration = builder.variableDeclaration(variableName,
						type, variableExpression);
				startPositions.put(variableDeclaration, startPosition);
				endPositions.put(variableDeclaration, endPosition);
				push(variableDeclaration);
				final ErrorExpression errorExpression = builder.errorExpression();
				pushError(errorExpression, MISSING_EXPRESSION);
				startPositions.put(errorExpression, startPosition);
				endPositions.put(errorExpression, endPosition);
			} else if (stack.isEmpty() || !(stack.peek() instanceof TypeLiteral)) {
				errorRule = QueryParser.RULE_typeLiteral;
				final ErrorTypeLiteral errorTypeLiteral = builder.errorTypeLiteral(false, new String[] {});
				startPositions.put(errorTypeLiteral, startPosition);
				endPositions.put(errorTypeLiteral, endPosition);
				pushError(errorTypeLiteral, String.format(INVALID_TYPE_LITERAL, msg));
			} else {
				diagnosticStack.push(new BasicDiagnostic(Diagnostic.WARNING, PLUGIN_ID, 0, msg, new Object[] {
						startPosition, endPosition, }));
			}
		}

		/**
		 * {@link LiteralContext} error case.
		 * 
		 * @param offendingSymbol
		 *            the offending symbol
		 * @param msg
		 *            the error message
		 * @param e
		 *            the {@link RecognitionException}
		 */
		private void literalContextError(Object offendingSymbol, String msg, RecognitionException e) {
			final LiteralContext ctx = (LiteralContext)e.getCtx();
			final Integer startPosition = Integer.valueOf(ctx.start.getStartIndex());
			final Integer endPosition = Integer.valueOf(((Token)offendingSymbol).getStopIndex() + 1);
			final String ePackage = ctx.getParent().getStart().getText();
			errorRule = QueryParser.RULE_typeLiteral;
			final ErrorTypeLiteral errorTypeLiteral = builder.errorTypeLiteral(false, new String[] {
					ePackage, });
			startPositions.put(errorTypeLiteral, startPosition);
			endPositions.put(errorTypeLiteral, endPosition);
			pushError(errorTypeLiteral, String.format(INVALID_TYPE_LITERAL, msg));
		}

		/**
		 * {@link VariableDefinitionContext} error case.
		 * 
		 * @param offendingSymbol
		 *            the offending symbol
		 * @param e
		 *            the {@link RecognitionException}
		 */
		private void variableDefinitionContextError(Object offendingSymbol, RecognitionException e) {
			final Integer startPosition = Integer.valueOf(((VariableDefinitionContext)e.getCtx()).start
					.getStartIndex());
			final Integer endPosition = Integer.valueOf(((Token)offendingSymbol).getStopIndex() + 1);
			if (e.getCtx().getChildCount() > 0) {
				errorRule = QueryParser.RULE_expression;
				final String variableName = e.getCtx().getChild(0).getText();
				final TypeLiteral type;
				if (e.getCtx().getChildCount() > 2) {
					type = popTypeLiteral();
					// overwrite the end position to the current error position (the start position is
					// fine)
					endPositions.put(type, endPosition);
				} else {
					type = null;
				}
				final Expression variableExpression = pop();
				final ErrorVariableDeclaration errorVariableDeclaration = builder.errorVariableDeclaration(
						variableName, type, variableExpression);
				startPositions.put(errorVariableDeclaration, startPosition);
				endPositions.put(errorVariableDeclaration, endPosition);
				pushError(errorVariableDeclaration, "incomplete variable definition");
			} else {
				final Expression variableExpression = pop();
				errorRule = QueryParser.RULE_variableDefinition;
				final ErrorVariableDeclaration errorVariableDeclaration = builder.errorVariableDeclaration(
						null, null, variableExpression);
				startPositions.put(errorVariableDeclaration, startPosition);
				endPositions.put(errorVariableDeclaration, endPosition);
				pushError(errorVariableDeclaration, "missing variable declaration");
			}
			if (((Token)offendingSymbol).getText().isEmpty() || ")".equals(((Token)offendingSymbol)
					.getText())) {
				final ErrorExpression errorExpression = builder.errorExpression();
				startPositions.put(errorExpression, endPosition);
				endPositions.put(errorExpression, endPosition);
				pushError(errorExpression, MISSING_EXPRESSION);
			}
		}

		/**
		 * {@link CallExpContext} error case.
		 * 
		 * @param offendingSymbol
		 *            the offending symbol
		 * @param e
		 *            the {@link RecognitionException}
		 */
		private void callExpContextError(Object offendingSymbol, RecognitionException e) {
			errorRule = QueryParser.RULE_navigationSegment;
			final String name;
			if (e.getCtx().getChildCount() > 0) {
				name = e.getCtx().getChild(0).getText();
			} else {
				name = null;
			}
			final Expression receiver;
			final ErrorCall errorCollectionCall;
			if (e.getCtx().getChildCount() == 3) {
				final int argc = getNumberOfArgs(e.getCtx().getChild(2).getChildCount());
				final Expression[] args = new Expression[argc];
				for (int i = argc - 1; i >= 0; i--) {
					args[i] = pop();
				}
				receiver = args[0];
				errorCollectionCall = builder.errorCall(name, false, args);
			} else {
				receiver = pop();
				errorCollectionCall = builder.errorCall(null, false, receiver);
			}
			startPositions.put(errorCollectionCall, startPositions.get(receiver));
			endPositions.put(errorCollectionCall, Integer.valueOf(((Token)offendingSymbol).getStopIndex()
					+ 1));
			pushError(errorCollectionCall, "missing collection service call");
		}

		/**
		 * {@link NavigationSegmentContext} error case.
		 * 
		 * @param offendingSymbol
		 *            the offending symbol
		 */
		private void navigationSegmentContextError(Object offendingSymbol) {
			final Expression receiver = pop();
			final ErrorFeatureAccessOrCall errorFeatureAccessOrCall = builder.errorFeatureAccessOrCall(
					receiver);
			startPositions.put(errorFeatureAccessOrCall, startPositions.get(receiver));
			endPositions.put(errorFeatureAccessOrCall, Integer.valueOf(((Token)offendingSymbol).getStopIndex()
					+ 1));
			pushError(errorFeatureAccessOrCall, "missing feature access or service call");
		}

		/**
		 * {@link BindingContext} error case.
		 * 
		 * @param offendingSymbol
		 *            the offending symbol
		 * @param e
		 *            the {@link RecognitionException}
		 */
		private void bindingContextError(Object offendingSymbol, RecognitionException e) {
			errorRule = QueryParser.RULE_binding;
			final String name;
			final TypeLiteral type;
			if (e.getCtx().getChildCount() > 0 && !"in".equals(e.getCtx().getChild(0).getText())) {
				name = e.getCtx().getChild(0).getText();
				if (e.getCtx().getChildCount() == 3) {
					type = popTypeLiteral();
				} else {
					type = null;
				}
			} else {
				name = null;
				type = null;
			}
			final ErrorBinding errorBinding = builder.errorBinding(name, type);
			startPositions.put(errorBinding, Integer.valueOf(((Token)offendingSymbol).getStopIndex() + 1));
			endPositions.put(errorBinding, Integer.valueOf(((Token)offendingSymbol).getStopIndex() + 1));
			pushError(errorBinding, "invalid variable declaration in let");
		}

		/**
		 * Default error case.
		 * 
		 * @param offendingSymbol
		 *            the offending symbol
		 * @param msg
		 *            the error message
		 * @param e
		 *            the {@link RecognitionException}
		 */
		private void defaultError(Object offendingSymbol, String msg, RecognitionException e) {
			if (offendingSymbol == null && e.getCtx() == null) {
				diagnostic.add(new BasicDiagnostic(Diagnostic.ERROR, PLUGIN_ID, 0, msg, new Object[] {}));
			} else {
				switch (e.getCtx().getRuleIndex()) {
					case QueryParser.RULE_expression:
						errorRule = QueryParser.RULE_expression;
						final ErrorExpression errorExpression = builder.errorExpression();
						final Integer position = Integer.valueOf(((ParserRuleContext)e.getCtx()).start
								.getStartIndex());
						startPositions.put(errorExpression, position);
						endPositions.put(errorExpression, position);
						pushError(errorExpression, MISSING_EXPRESSION);
						break;

					default:
						break;
				}
			}
		}

		/**
		 * Handles parser error when the {@link RecognitionException} is <code>null</code>.
		 * 
		 * @param recognizer
		 *            the {@link Recognizer}
		 * @param offendingSymbol
		 *            the offending symbol
		 * @param msg
		 *            the error message
		 */
		private void noRecognitionException(Recognizer<?, ?> recognizer, Object offendingSymbol, String msg) {
			final QueryParser parser = (QueryParser)recognizer;
			final Integer startPosition = Integer.valueOf(((ParserRuleContext)parser.getContext()).start
					.getStartIndex());
			final Integer endPosition = Integer.valueOf(((Token)offendingSymbol).getStopIndex() + 1);
			diagnosticStack.push(new BasicDiagnostic(Diagnostic.WARNING, PLUGIN_ID, 0, msg, new Object[] {
					startPosition, endPosition, }));
		}
	}

	/**
	 * No error rule marker.
	 */
	private static final int NO_ERROR = -1;

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
	 * Mapping from an {@link Expression} or a {@link VariableDeclaration} to its start position in the parsed
	 * text.
	 */
	private final Map<Object, Integer> startPositions = new HashMap<Object, Integer>();

	/**
	 * Mapping from an {@link Expression} or a {@link VariableDeclaration} to its end position in the parsed
	 * text.
	 */
	private final Map<Object, Integer> endPositions = new HashMap<Object, Integer>();

	/**
	 * The {@link List} of {@link Error}.
	 */
	private final List<Error> errors = new ArrayList<Error>();

	/**
	 * Temporary lexing warnings waiting for their {@link Expression}.
	 */
	private Stack<Diagnostic> diagnosticStack = new Stack<Diagnostic>();

	/** Aggregated status of the parsing. */
	private final BasicDiagnostic diagnostic = new BasicDiagnostic();

	/**
	 * The {@link ANTLRErrorListener} pushing {@link org.eclipse.acceleo.query.ast.Error Error}.
	 */
	private final ANTLRErrorListener errorListener = new QueryErrorListener();

	/**
	 * Ast Builder.
	 */
	private final AstBuilder builder = new AstBuilder();

	/**
	 * {@link org.eclipse.emf.ecore.EPackage} instances that are available during evaluation.
	 */
	private final IQueryEnvironment environment;

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
		result.add(XOR_SERVICE_NAME);

		return result;
	}

	/**
	 * Returns the {@link AstResult}.
	 * 
	 * @return the {@link AstResult}.
	 */
	public AstResult getAstResult() {
		return new AstResult(pop(), startPositions, endPositions, errors, diagnostic);
	}

	/**
	 * Pop the top of the stack.
	 * 
	 * @return the value on top of the stack
	 */
	private Expression pop() {
		try {
			final Expression expression = (Expression)stack.pop();

			if (!diagnosticStack.isEmpty()) {
				final List<?> data = diagnosticStack.peek().getData();
				if (data.get(0).equals(startPositions.get(expression)) && data.get(1).equals(endPositions.get(
						expression))) {
					final Diagnostic tmpDiagnostic = diagnosticStack.pop();
					diagnostic.add(new BasicDiagnostic(tmpDiagnostic.getSeverity(), tmpDiagnostic.getSource(),
							tmpDiagnostic.getCode(), tmpDiagnostic.getMessage(), new Object[] {expression }));
				}
			}

			return expression;
		} catch (EmptyStackException e) {
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG, e);
		} catch (ClassCastException cce) {
			throw new AcceleoQueryEvaluationException(INTERNAL_ERROR_MSG, cce);
		}
	}

	/**
	 * Pop the top of the stack and returns it as a type literal.
	 * 
	 * @return the value on top of the stack.
	 */
	private Binding popBinding() {
		try {
			return (Binding)stack.pop();
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
	 * Pop the top of the stack and returns it as a binding.
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
	 * Peeks the current {@link Call} at the top of the stack.
	 * 
	 * @return the current {@link Call} at the top of the stack
	 */
	private Call peekCall() {
		return (Call)stack.peek();
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
	 * @param msg
	 *            the error message
	 */
	private void pushError(Error error, String msg) {
		errors.add(error);
		diagnostic.add(new BasicDiagnostic(Diagnostic.ERROR, PLUGIN_ID, 0, msg, new Object[] {error }));
		this.stack.push(error);
	}

	@Override
	public void exitIntType(IntTypeContext ctx) {
		final Literal typeLiteral = builder.typeLiteral(java.lang.Integer.class);

		startPositions.put(typeLiteral, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(typeLiteral, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(typeLiteral);
	}

	@Override
	public void exitFalseLit(FalseLitContext ctx) {
		final BooleanLiteral booleanLiteral = builder.booleanLiteral(false);

		startPositions.put(booleanLiteral, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(booleanLiteral, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(booleanLiteral);
	}

	@Override
	public void exitRealType(RealTypeContext ctx) {
		final Literal realLiteral = builder.typeLiteral(java.lang.Double.class);

		startPositions.put(realLiteral, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(realLiteral, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(realLiteral);
	}

	@Override
	public void exitTrueLit(TrueLitContext ctx) {
		final BooleanLiteral booleanLiteral = builder.booleanLiteral(true);

		startPositions.put(booleanLiteral, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(booleanLiteral, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(booleanLiteral);
	}

	@Override
	public void exitSeqType(SeqTypeContext ctx) {
		final TypeLiteral elementType = popTypeLiteral();
		final CollectionTypeLiteral collectionTypeLiteral = builder.collectionTypeLiteral(List.class,
				elementType);

		startPositions.put(collectionTypeLiteral, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(collectionTypeLiteral, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(collectionTypeLiteral);
	}

	@Override
	public void exitSetType(SetTypeContext ctx) {
		final TypeLiteral elementType = popTypeLiteral();
		final CollectionTypeLiteral collectionTypeLiteral = builder.collectionTypeLiteral(Set.class,
				elementType);

		startPositions.put(collectionTypeLiteral, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(collectionTypeLiteral, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(collectionTypeLiteral);
	}

	@Override
	public void exitNot(NotContext ctx) {
		final Call callService = builder.callService(NOT_SERVICE_NAME, pop());

		startPositions.put(callService, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(callService, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(callService);
	}

	@Override
	public void exitStringLit(StringLitContext ctx) {
		final String text = ctx.getText();
		final StringLiteral stringLiteral = builder.stringLiteral(text.substring(1, text.length() - 1));

		startPositions.put(stringLiteral, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(stringLiteral, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(stringLiteral);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitErrorStringLit(org.eclipse.acceleo.query.parser.QueryParser.ErrorStringLitContext)
	 */
	@Override
	public void exitErrorStringLit(ErrorStringLitContext ctx) {
		final String text = ctx.getText();
		final ErrorStringLiteral errorStringLiteral = builder.errorStringLiteral(text.substring(1, text
				.length()));

		startPositions.put(errorStringLiteral, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(errorStringLiteral, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		pushError(errorStringLiteral, "String literal is not properly closed by a simple-quote: " + text);
	}

	@Override
	public void exitRealLit(RealLitContext ctx) {
		final RealLiteral realLiteral = builder.realLiteral(Double.valueOf(ctx.getText()));

		startPositions.put(realLiteral, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(realLiteral, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(realLiteral);
	}

	@Override
	public void exitStrType(StrTypeContext ctx) {
		final Literal typeLiteral = builder.typeLiteral(java.lang.String.class);

		startPositions.put(typeLiteral, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(typeLiteral, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(typeLiteral);
	}

	@Override
	public void exitOr(OrContext ctx) {
		Expression op2 = pop();
		Expression op1 = pop();
		final Or callService = builder.callOrService(op1, op2);
		startPositions.put(callService, startPositions.get(op1));
		endPositions.put(callService, endPositions.get(op2));
		push(callService);
	}

	@Override
	public void exitXor(XorContext ctx) {
		pushBinary(XOR_SERVICE_NAME, ctx);
	}

	@Override
	public void exitImplies(ImpliesContext ctx) {
		Expression op2 = pop();
		Expression op1 = pop();
		final Implies callService = builder.callImpliesService(op1, op2);
		startPositions.put(callService, startPositions.get(op1));
		endPositions.put(callService, endPositions.get(op2));
		push(callService);
	}

	@Override
	public void exitBooleanType(BooleanTypeContext ctx) {
		final Literal typeLiteral = builder.typeLiteral(java.lang.Boolean.class);

		startPositions.put(typeLiteral, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(typeLiteral, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(typeLiteral);
	}

	@Override
	public void exitIntegerLit(IntegerLitContext ctx) {
		final IntegerLiteral integerLiteral = builder.integerLiteral(Integer.valueOf(ctx.getText()));

		startPositions.put(integerLiteral, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(integerLiteral, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(integerLiteral);
	}

	@Override
	public void exitAnd(AndContext ctx) {
		Expression op2 = pop();
		Expression op1 = pop();
		final And callService = builder.callAndService(op1, op2);
		startPositions.put(callService, startPositions.get(op1));
		endPositions.put(callService, endPositions.get(op2));
		push(callService);
	}

	@Override
	public void exitVarRef(VarRefContext ctx) {
		final VarRef varRef = builder.varRef(ctx.getText());

		startPositions.put(varRef, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(varRef, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(varRef);
	}

	@Override
	public void exitFeature(FeatureContext ctx) {
		final Expression receiver = pop();
		final FeatureAccess featureAccess = builder.featureAccess(receiver, ctx.getChild(1).getText());

		startPositions.put(featureAccess, startPositions.get(receiver));
		endPositions.put(featureAccess, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(featureAccess);
	}

	/**
	 * Factorization of binary operation evaluation.
	 * 
	 * @param service
	 *            the called service.
	 * @param ctx
	 *            the {@link ParserRuleContext}
	 */
	private void pushBinary(String service, ParserRuleContext ctx) {
		Expression op2 = pop();
		Expression op1 = pop();
		final Call callService = builder.callService(service, op1, op2);
		startPositions.put(callService, startPositions.get(op1));
		endPositions.put(callService, endPositions.get(op2));
		push(callService);
	}

	/**
	 * Evaluation stack is as follows : [/.../,receiver, service name (string), START_EXPR_SEQ, expr*].
	 * 
	 * @param ctx
	 *            the parsing context
	 */
	@Override
	public void exitServiceCall(ServiceCallContext ctx) {
		if (errorRule != QueryParser.RULE_navigationSegment) {
			final int argc = getNumberOfArgs(ctx.getChild(2).getChildCount());
			final Expression[] args = new Expression[argc];
			for (int i = argc - 1; i >= 0; i--) {
				args[i] = pop();
			}
			final String serviceName = ctx.getChild(0).getText().replace("::", ".");
			final Call call;
			if (ctx.getChild(ctx.getChildCount() - 1) instanceof ErrorNode) {
				call = builder.errorCall(serviceName, true, args);
				pushError((Error)call, "missing ')'");
			} else {
				call = builder.callService(serviceName, args);
				push(call);
			}

			startPositions.put(call, startPositions.get(args[0]));
			endPositions.put(call, Integer.valueOf(ctx.stop.getStopIndex() + 1));
		}
	}

	/**
	 * Gets the number of arguments separated by a token given a context child count.
	 * 
	 * @param childCount
	 *            the context child count
	 * @return the number of arguments separated by a token given a context child count
	 */
	private int getNumberOfArgs(int childCount) {
		final int result;

		if (childCount == 0) {
			result = 1;
		} else {
			result = 1 + 1 + childCount / 2;
		}

		return result;
	}

	@Override
	public void exitMin(MinContext ctx) {
		final Call callService = builder.callService(UNARY_MIN_SERVICE_NAME, pop());

		startPositions.put(callService, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(callService, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(callService);
	}

	@Override
	public void exitAdd(AddContext ctx) {
		final String op = ctx.getChild(1).getText();

		if (ADD_OPERATOR.equals(op)) {
			pushBinary(ADD_SERVICE_NAME, ctx);
		} else if (SUB_OPERATOR.equals(op)) {
			pushBinary(SUB_SERVICE_NAME, ctx);
		} else {
			throw new AcceleoQueryEvaluationException(THIS_SHOULDN_T_HAPPEN);
		}
	}

	@Override
	public void exitMult(MultContext ctx) {
		final String op = ctx.getChild(1).getText();

		if (MULT_OPERATOR.equals(op)) {
			pushBinary(MULT_SERVICE_NAME, ctx);
		} else if (DIV_OPERATOR.equals(op)) {
			pushBinary(DIV_SERVICE_NAME, ctx);
		} else {
			throw new AcceleoQueryEvaluationException(THIS_SHOULDN_T_HAPPEN);
		}
	}

	@Override
	public void exitComp(CompContext ctx) {
		final String op = ctx.getChild(1).getText();

		if (LESS_THAN_OPERATOR.equals(op)) {
			pushBinary(LESS_THAN_SERVICE_NAME, ctx);
		} else if (LESS_THAN_EQUAL_OPERATOR.equals(op)) {
			pushBinary(LESS_THAN_EQUAL_SERVICE_NAME, ctx);
		} else if (GREATER_THAN_OPERATOR.equals(op)) {
			pushBinary(GREATER_THAN_SERVICE_NAME, ctx);
		} else if (GREATER_THAN_EQUAL_OPERATOR.equals(op)) {
			pushBinary(GREATER_THAN_EQUAL_SERVICE_NAME, ctx);
		} else if (EQUALS_OPERATOR.equals(op)) {
			pushBinary(EQUALS_SERVICE_NAME, ctx);
		} else if (DIFFERS_OPERATOR.equals(op) || DIFFERS_JAVA_OPERATOR.equals(op)) {
			pushBinary(DIFFERS_SERVICE_NAME, ctx);
		} else {
			throw new AcceleoQueryEvaluationException(THIS_SHOULDN_T_HAPPEN);
		}
	}

	@Override
	public void exitCollectionCall(CollectionCallContext ctx) {
		peekCall().setType(CallType.COLLECTIONCALL);
	}

	@Override
	public void exitCallOrApply(CallOrApplyContext ctx) {
		peekCall().setType(CallType.CALLORAPPLY);
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

			if (ctx.getChildCount() == 4) {
				final TypeLiteral typeLiteral = popTypeLiteral();
				final Expression variableExpression = pop();
				variableDeclaration = builder.variableDeclaration(ctx.getChild(0).getText(), typeLiteral,
						variableExpression);
				endPositions.put(variableDeclaration, Integer.valueOf(((ParserRuleContext)ctx.getChild(
						2)).stop.getStopIndex() + 1));
			} else {
				final Expression variableExpression = pop();
				variableDeclaration = builder.variableDeclaration(ctx.getChild(0).getText(),
						variableExpression);
				endPositions.put(variableDeclaration, Integer.valueOf(((TerminalNode)ctx.getChild(0))
						.getSymbol().getStopIndex() + 1));
			}
			startPositions.put(variableDeclaration, Integer.valueOf(ctx.start.getStartIndex()));

			stack.push(variableDeclaration);
		} else {
			errorRule = NO_ERROR;
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
		iterator = popVariableDeclaration();
		final Lambda lambda = builder.lambda(ast, iterator);
		startPositions.put(lambda, startPositions.get(ast));
		endPositions.put(lambda, Integer.valueOf(endPositions.get(ast)));
		final Call call;
		if (ctx.getChild(ctx.getChildCount() - 1) instanceof ErrorNode) {
			call = builder.errorCall(serviceName, true, iterator.getExpression(), lambda);
			pushError((Error)call, "missing ')'");
		} else {
			call = builder.callService(serviceName, iterator.getExpression(), lambda);
			push(call);
		}

		startPositions.put(call, startPositions.get(iterator.getExpression()));
		endPositions.put(call, Integer.valueOf(ctx.stop.getStopIndex() + 1));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitEnumLit(org.eclipse.acceleo.query.parser.QueryParser.EnumLitContext)
	 */
	@Override
	public void exitEnumLit(EnumLitContext ctx) {
		final EnumLiteral toPush;
		final String ePackageName = ctx.getChild(0).getText();
		final String eEnumName = ctx.getChild(2).getText();
		final String eEnumLiteralName = ctx.getChild(4).getText();
		final Collection<EEnumLiteral> eEnumLiterals = environment.getEPackageProvider().getEnumLiterals(
				ePackageName, eEnumName, eEnumLiteralName);
		Integer startPosition = Integer.valueOf(ctx.start.getStartIndex());
		Integer stopPosition = Integer.valueOf(ctx.stop.getStopIndex() + 1);
		if (eEnumLiterals.size() == 0) {
			List<String> segments = new ArrayList<String>(3);
			segments.add(ePackageName);
			segments.add(eEnumName);
			if (!(ctx.getChild(4) instanceof ErrorNode)) {
				segments.add(eEnumLiteralName);
			}
			toPush = builder.errorEnumLiteral(false, segments.toArray(new String[segments.size()]));
			pushError((Error)toPush, "invalid enum literal");
		} else {
			toPush = builder.enumLiteral(eEnumLiterals.iterator().next());
			push(toPush);
			if (eEnumLiterals.size() > 1) {
				diagnosticStack.push(new BasicDiagnostic(Diagnostic.WARNING, PLUGIN_ID, 0, String.format(
						AMBIGUOUS_ENUM_LITERAL, eEnumLiteralName, eEnumName, ePackageName), new Object[] {
								startPosition, stopPosition, }));
			}
		}
		startPositions.put(toPush, startPosition);
		endPositions.put(toPush, stopPosition);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitErrorEnumLit(org.eclipse.acceleo.query.parser.QueryParser.ErrorEnumLitContext)
	 */
	@Override
	public void exitErrorEnumLit(ErrorEnumLitContext ctx) {
		if (errorRule == NO_ERROR) {
			final String ePackageName = ctx.getChild(0).getText();
			final String eEnumName = ctx.getChild(2).getText();

			final ErrorEnumLiteral errorEnumLiteral = builder.errorEnumLiteral(true, ePackageName, eEnumName);

			pushError(errorEnumLiteral, "invalid enum literal");
			startPositions.put(errorEnumLiteral, Integer.valueOf(ctx.start.getStartIndex()));
			endPositions.put(errorEnumLiteral, Integer.valueOf(ctx.stop.getStopIndex() + 1));
		} else {
			errorRule = NO_ERROR;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitClassifierType(org.eclipse.acceleo.query.parser.QueryParser.ClassifierTypeContext)
	 */
	@Override
	public void exitClassifierType(ClassifierTypeContext ctx) {
		if (errorRule == NO_ERROR) {
			final Literal toPush;
			final String ePackageName = ctx.getChild(0).getText();
			final String eClassName;
			Integer startPosition = Integer.valueOf(ctx.start.getStartIndex());
			Integer stopPosition = Integer.valueOf(ctx.stop.getStopIndex() + 1);
			Collection<EClassifier> type = Collections.emptySet();
			if (ctx.getChild(2) == null || ctx.getChild(2) instanceof ErrorNode) {
				eClassName = null;
				type = Collections.emptySet();
			} else {
				eClassName = ctx.getChild(2).getText();
				type = environment.getEPackageProvider().getTypes(ePackageName, eClassName);
			}
			if (type.size() == 0) {
				List<String> segments = new ArrayList<String>(2);
				segments.add(ePackageName);
				if (eClassName != null) {
					segments.add(eClassName);
				}
				toPush = builder.errorTypeLiteral(false, segments.toArray(new String[segments.size()]));
				pushError((Error)toPush, String.format(INVALID_TYPE_LITERAL, ctx.getText()));
			} else {
				toPush = builder.typeLiteral(type);
				push(toPush);
				if (type.size() > 1) {
					diagnosticStack.push(new BasicDiagnostic(Diagnostic.WARNING, PLUGIN_ID, 0, String.format(
							AMBIGUOUS_TYPE_LITERAL, eClassName, ePackageName), new Object[] {startPosition,
									stopPosition, }));
				}
			}
			startPositions.put(toPush, startPosition);
			endPositions.put(toPush, stopPosition);
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitErrorClassifierType(org.eclipse.acceleo.query.parser.QueryParser.ErrorClassifierTypeContext)
	 */
	@Override
	public void exitErrorClassifierType(ErrorClassifierTypeContext ctx) {
		final String ePackageName = ctx.getChild(0).getText();

		final ErrorTypeLiteral errorTypeLiteral = builder.errorTypeLiteral(true, ePackageName);

		pushError((Error)errorTypeLiteral, String.format(INVALID_TYPE_LITERAL, ctx.getText()));
		startPositions.put(errorTypeLiteral, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(errorTypeLiteral, Integer.valueOf(ctx.stop.getStopIndex() + 1));
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
		final NullLiteral nullLiteral = builder.nullLiteral();

		startPositions.put(nullLiteral, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(nullLiteral, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(nullLiteral);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitExplicitSetLit(org.eclipse.acceleo.query.parser.QueryParser.ExplicitSetLitContext)
	 */
	@Override
	public void exitExplicitSetLit(ExplicitSetLitContext ctx) {
		final SetInExtensionLiteral setInExtension = builder.setInExtension(getExpressions(ctx));

		startPositions.put(setInExtension, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(setInExtension, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(setInExtension);
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

		for (int i = nbExpressions - 1; i >= 0; i--) {
			expressions[i] = pop();
		}

		return Arrays.asList(expressions);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitExplicitSeqLit(org.eclipse.acceleo.query.parser.QueryParser.ExplicitSeqLitContext)
	 */
	@Override
	public void exitExplicitSeqLit(ExplicitSeqLitContext ctx) {
		final SequenceInExtensionLiteral sequenceInExtension = builder.sequenceInExtension(getExpressions(
				ctx));

		startPositions.put(sequenceInExtension, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(sequenceInExtension, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(sequenceInExtension);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitConditional(org.eclipse.acceleo.query.parser.QueryParser.ConditionalContext)
	 */
	@Override
	public void exitConditional(ConditionalContext ctx) {
		final int count = ctx.getChildCount();
		final Expression predicate;
		final Expression trueBranch;
		final Expression falseBranch;
		if (count <= 3) {
			predicate = pop();
			trueBranch = null;
			falseBranch = null;
		} else if (count <= 5) {
			trueBranch = pop();
			predicate = pop();
			falseBranch = null;
		} else {
			falseBranch = pop();
			trueBranch = pop();
			predicate = pop();
		}

		final Conditional conditional;
		if (errorRule == QueryParser.RULE_expression || count == CONDITIONAL_CONTEXT_CHILD_COUNT && ctx
				.getChild(6) instanceof ErrorNode) {
			conditional = builder.errorConditional(predicate, trueBranch, falseBranch);
			errorRule = NO_ERROR;
			pushError((ErrorConditional)conditional, "incomplet conditional");
		} else {
			conditional = builder.conditional(predicate, trueBranch, falseBranch);
			push(conditional);
		}

		startPositions.put(conditional, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(conditional, Integer.valueOf(ctx.stop.getStopIndex() + 1));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitBinding(org.eclipse.acceleo.query.parser.QueryParser.BindingContext)
	 */
	@Override
	public void exitBinding(BindingContext ctx) {
		// If the error flag is raised an error occurred and the binding is already
		// there.
		if (errorRule != QueryParser.RULE_binding) {
			final String variable = ctx.getChild(0).getText();
			final Expression expression = pop();
			final TypeLiteral type;
			if (ctx.getChildCount() == 5) {
				type = popTypeLiteral();
			} else {
				type = null;
			}
			final Binding binding = builder.binding(variable, type, expression);

			startPositions.put(binding, Integer.valueOf(ctx.start.getStartIndex()));
			endPositions.put(binding, Integer.valueOf(ctx.stop.getStopIndex() + 1));

			push(binding);
		} else {
			errorRule = NO_ERROR;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitLetExpr(org.eclipse.acceleo.query.parser.QueryParser.LetExprContext)
	 */
	@Override
	public void exitLetExpr(LetExprContext ctx) {
		Expression body;
		if (!(ctx.getChild(ctx.getChildCount() - 1) instanceof ExpressionContext)) {
			body = builder.errorExpression();
			startPositions.put(body, Integer.valueOf(ctx.stop.getStopIndex() + 1));
			endPositions.put(body, Integer.valueOf(ctx.stop.getStopIndex() + 1));
		} else {
			body = pop();
		}
		int bindingNumber = 1 + (ctx.getChildCount() - 3) / 2;
		Binding[] bindings = new Binding[bindingNumber];
		for (int i = bindingNumber - 1; i >= 0; i--) {
			bindings[i] = popBinding();
		}
		final Let let = builder.let(body, bindings);

		startPositions.put(let, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(let, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(let);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitClassifierSetType(org.eclipse.acceleo.query.parser.QueryParser.ClassifierSetTypeContext)
	 */
	@Override
	public void exitClassifierSetType(ClassifierSetTypeContext ctx) {
		final int nbTypes = (ctx.getChildCount() + 1) / 2 - 1;
		final TypeLiteral[] types = new TypeLiteral[nbTypes];

		for (int i = nbTypes - 1; i >= 0; i--) {
			types[i] = popTypeLiteral();
		}

		final TypeSetLiteral classifierSetType = builder.typeSetLiteral(Arrays.asList(types));

		startPositions.put(classifierSetType, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(classifierSetType, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(classifierSetType);
	}

}
