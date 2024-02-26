/*******************************************************************************
 * Copyright (c) 2015, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.parser;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.EmptyStackException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.acceleo.query.ast.ASTNode;
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
import org.eclipse.acceleo.query.ast.ErrorEClassifierTypeLiteral;
import org.eclipse.acceleo.query.ast.ErrorEnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorExpression;
import org.eclipse.acceleo.query.ast.ErrorStringLiteral;
import org.eclipse.acceleo.query.ast.ErrorTypeLiteral;
import org.eclipse.acceleo.query.ast.ErrorVariableDeclaration;
import org.eclipse.acceleo.query.ast.Expression;
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
import org.eclipse.acceleo.query.parser.QueryParser.ArgumentsContext;
import org.eclipse.acceleo.query.parser.QueryParser.BindingContext;
import org.eclipse.acceleo.query.parser.QueryParser.BooleanTypeContext;
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
import org.eclipse.acceleo.query.parser.QueryParser.LambdaContext;
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
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;

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
	 * Feature access service name.
	 */
	public static final String FEATURE_ACCESS_SERVICE_NAME = "aqlFeatureAccess";

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
	 * <code>==<code> operator.
	 */
	public static final String EQUALS_JAVA_OPERATOR = "==";

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
	 * Invalid type literal.
	 */
	public static final String INVALID_TYPE_LITERAL = "invalid type literal %s";

	/**
	 * This should not happen.
	 */
	private static final String THIS_SHOULDN_T_HAPPEN = "This shouldn't happen.";

	/**
	 * Invalid enum literal message.
	 */
	private static final String INVALID_ENUM_LITERAL = "invalid enum literal: %s";

	/**
	 * Number of children in {@link ConditionalContext}.
	 */
	private static final int CONDITIONAL_CONTEXT_CHILD_COUNT = 7;

	/**
	 * Super call modifier.
	 */
	public static final String SUPER_CALL = "super:";

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
		public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line,
				int charPositionInLine, String msg, RecognitionException e) {
			if (e != null) {
				if (e.getCtx() instanceof ArgumentsContext) {
					argumentsContextError(e);
				} else if (e.getCtx() instanceof TypeLiteralContext) {
					typeLiteralContextError(offendingSymbol, msg, e);
				} else if (e.getCtx() instanceof LiteralContext) {
					literalContextError(offendingSymbol, msg, e);
				} else if (e.getCtx() instanceof ClassifierTypeRuleContext) {
					classifierTypeRuleContextError(offendingSymbol, msg, e);
				} else if (e.getCtx() instanceof VariableDefinitionContext) {
					variableDefinitionContextError(offendingSymbol, e);
				} else if (e.getCtx() instanceof ServiceCallContext) {
					serviceCallContextError(offendingSymbol, e);
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
				diagnosticStack.addLast(new BasicDiagnostic(Diagnostic.WARNING, PLUGIN_ID, 0, msg,
						new Object[] {startPosition, endPosition, }));
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
			if (e.getCtx().getParent().getParent() instanceof VariableDefinitionContext) {
				errorRule = QueryParser.RULE_expression;
				final String variableName = e.getCtx().getParent().getChild(0).getText();
				final ErrorEClassifierTypeLiteral errorEClassifierTypeLiteral;
				if (ctx.getChildCount() > 0) {
					errorEClassifierTypeLiteral = builder.errorEClassifierTypeLiteral(false, ctx.getChild(0)
							.getText());
				} else {
					errorEClassifierTypeLiteral = builder.errorEClassifierTypeLiteral(false, null);
				}
				setPositions(errorEClassifierTypeLiteral, ctx.start, (Token)offendingSymbol);
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, PLUGIN_ID, 0, String.format(
						INVALID_TYPE_LITERAL, ctx.getText()), new Object[] {errorEClassifierTypeLiteral }));
				errors.add(errorEClassifierTypeLiteral);
				final Expression variableExpression = lambdaVariableExpression.getLast();
				final VariableDeclaration variableDeclaration = builder.variableDeclaration(variableName,
						errorEClassifierTypeLiteral, variableExpression);
				setPositions(variableDeclaration, ctx.start, (Token)offendingSymbol);
				push(variableDeclaration);
				final ErrorExpression errorExpression = builder.errorExpression();
				pushError(errorExpression, MISSING_EXPRESSION);
				setPositions(errorExpression, (Token)offendingSymbol, (Token)offendingSymbol);
			} else {
				errorRule = QueryParser.RULE_classifierTypeRule;
				final Error error;
				if (ctx.getParent() instanceof ClassifierSetTypeContext) {
					if (ctx.getChildCount() > 0) {
						error = builder.errorEClassifierTypeLiteral(false, ctx.getChild(0).getText());
					} else {
						error = builder.errorEClassifierTypeLiteral(false, null);
					}
				} else {
					error = builder.errorTypeLiteral();
				}
				setPositions(error, ctx.start, (Token)offendingSymbol);
				pushError(error, "missing classifier literal");
			}
		}

		/**
		 * {@link ArgumentsContext} error case.
		 * 
		 * @param e
		 *            the {@link ArgumentsContext}
		 */
		private void argumentsContextError(RecognitionException e) {
			errorRule = QueryParser.RULE_expression;
			final ErrorExpression errorExpression = builder.errorExpression();
			pushError(errorExpression, MISSING_EXPRESSION);

			final ArgumentsContext ctx = ((ArgumentsContext)e.getCtx());
			final Token lastToken = ((TerminalNode)ctx.getChild(ctx.getChildCount() - 1)).getSymbol();
			final int position = lastToken.getStartIndex() + lastToken.getText().length();
			final int line = lastToken.getLine() - 1;
			final int column = lastToken.getCharPositionInLine() + lastToken.getText().length();

			setIdentifierPositions(errorExpression, position, line, column);
			setPositions(errorExpression, position, line, column);
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
			if (e.getCtx().getParent() instanceof VariableDefinitionContext) {
				errorRule = QueryParser.RULE_expression;
				final String variableName = e.getCtx().getParent().getChild(0).getText();
				final ErrorTypeLiteral type = builder.errorTypeLiteral();
				setPositions(type, ((TypeLiteralContext)e.getCtx()).start, (Token)offendingSymbol);
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, PLUGIN_ID, 0, String.format(
						INVALID_TYPE_LITERAL, msg), new Object[] {type }));
				errors.add(type);
				final Expression variableExpression = lambdaVariableExpression.getLast();
				final VariableDeclaration variableDeclaration = builder.variableDeclaration(variableName,
						type, variableExpression);
				setPositions(variableDeclaration, ((TypeLiteralContext)e.getCtx()).start,
						(Token)offendingSymbol);
				push(variableDeclaration);
				final ErrorExpression errorExpression = builder.errorExpression();
				pushError(errorExpression, MISSING_EXPRESSION);
				setPositions(errorExpression, ((TypeLiteralContext)e.getCtx()).start, (Token)offendingSymbol);
			} else if (stack.isEmpty() || !(stack.getLast() instanceof TypeLiteral)) {
				errorRule = QueryParser.RULE_typeLiteral;
				final ErrorTypeLiteral errorTypeLiteral = builder.errorTypeLiteral();
				setPositions(errorTypeLiteral, ((TypeLiteralContext)e.getCtx()).start,
						(Token)offendingSymbol);
				pushError(errorTypeLiteral, String.format(INVALID_TYPE_LITERAL, msg));
			} else {
				diagnosticStack.addLast(new BasicDiagnostic(Diagnostic.WARNING, PLUGIN_ID, 0, msg,
						new Object[] {((TypeLiteralContext)e.getCtx()).start.getStartIndex(),
								((Token)offendingSymbol).getStopIndex() + 1, }));
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
			final Token start = ctx.start;
			final Token end = (Token)offendingSymbol;
			final String ePackage = ctx.getParent().getStart().getText();
			errorRule = QueryParser.RULE_typeLiteral;
			if (ctx.getChildCount() == 4) {
				final String eEnumName = ctx.getChild(2).getText();
				final ErrorEnumLiteral errorEnumLiteral = builder.errorEnumLiteral(false, ePackage,
						eEnumName);
				setPositions(errorEnumLiteral, start, end);
				pushError(errorEnumLiteral, String.format(INVALID_ENUM_LITERAL, msg));
			} else {
				final ErrorEClassifierTypeLiteral errorEClassifierTypeLiteral = builder
						.errorEClassifierTypeLiteral(false, ePackage);
				setPositions(errorEClassifierTypeLiteral, start, end);
				pushError(errorEClassifierTypeLiteral, String.format(INVALID_TYPE_LITERAL, msg));
			}
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
			if (e.getCtx().getChildCount() > 0) {
				errorRule = QueryParser.RULE_expression;
				final String variableName = e.getCtx().getChild(0).getText();
				final TypeLiteral type;
				if (e.getCtx().getChildCount() > 2) {
					type = popTypeLiteral();
					// overwrite the end position to the current error position (the start position is
					// fine)
					final int position = ((Token)offendingSymbol).getStopIndex() + 1;
					final int line = ((Token)offendingSymbol).getLine() - 1;
					final int column = ((Token)offendingSymbol).getCharPositionInLine()
							+ ((Token)offendingSymbol).getText().length();
					positions.setEndPositions(type, position);
					positions.setEndLines(type, line);
					positions.setEndColumns(type, column);
				} else {
					type = null;
				}
				final Expression variableExpression = lambdaVariableExpression.getLast();
				final ErrorVariableDeclaration errorVariableDeclaration = builder.errorVariableDeclaration(
						variableName, type, variableExpression);
				setIdentifierPositions(errorVariableDeclaration, (Token)e.getCtx().getChild(0).getPayload());
				setPositions(errorVariableDeclaration, ((VariableDefinitionContext)e.getCtx()).start,
						(Token)offendingSymbol);
				pushError(errorVariableDeclaration, "incomplete variable definition");
			} else {
				final Expression variableExpression = lambdaVariableExpression.getLast();
				errorRule = QueryParser.RULE_variableDefinition;
				final ErrorVariableDeclaration errorVariableDeclaration = builder.errorVariableDeclaration(
						null, null, variableExpression);
				setIdentifierPositions(errorVariableDeclaration, ((VariableDefinitionContext)e
						.getCtx()).start);
				setPositions(errorVariableDeclaration, ((VariableDefinitionContext)e.getCtx()).start,
						(Token)offendingSymbol);
				pushError(errorVariableDeclaration, "missing variable declaration");
			}
			if (((Token)offendingSymbol).getText().isEmpty() || ")".equals(((Token)offendingSymbol)
					.getText())) {
				final ErrorExpression errorExpression = builder.errorExpression();
				// not missing ')' only missing expression
				if (((Token)offendingSymbol).getStartIndex() == ((Token)offendingSymbol).getStopIndex()) {
					// position is indeed before the closing parenthesis
					final int position = ((Token)offendingSymbol).getStopIndex() /* + 1 - 1 */;
					final int line = ((Token)offendingSymbol).getLine() - 1;
					final int column = ((Token)offendingSymbol).getCharPositionInLine()
							+ ((Token)offendingSymbol).getText().length() - 1;
					setIdentifierPositions(errorExpression, position, line, column);
					setPositions(errorExpression, position, line, column);
				} else {
					final int position = ((Token)offendingSymbol).getStopIndex() + 1;
					final int line = ((Token)offendingSymbol).getLine() - 1;
					final int column = ((Token)offendingSymbol).getCharPositionInLine()
							+ ((Token)offendingSymbol).getText().length();
					setIdentifierPositions(errorExpression, position, line, column);
					setPositions(errorExpression, position, line, column);
				}
				pushError(errorExpression, MISSING_EXPRESSION);
			}
		}

		/**
		 * {@link SerCalContext} error case.
		 * 
		 * @param offendingSymbol
		 *            the offending symbol
		 * @param e
		 *            the {@link RecognitionException}
		 */
		private void serviceCallContextError(Object offendingSymbol, RecognitionException e) {
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
					args[i] = popExpression();
				}
				receiver = args[0];
				errorCollectionCall = builder.errorCall(name, false, args);
			} else {
				receiver = popExpression();
				errorCollectionCall = builder.errorCall(null, false, receiver);
			}
			if (e.getCtx().getChildCount() > 0) {
				setIdentifierPositions(errorCollectionCall, (Token)e.getCtx().getChild(0).getPayload());
			} else {
				setIdentifierPositions(errorCollectionCall, (Token)offendingSymbol);
			}
			setPositions(errorCollectionCall, receiver, (Token)offendingSymbol);
			pushError(errorCollectionCall, "missing collection service call");
		}

		/**
		 * {@link NavigationSegmentContext} error case.
		 * 
		 * @param offendingSymbol
		 *            the offending symbol
		 */
		private void navigationSegmentContextError(Object offendingSymbol) {
			final Expression receiver = popExpression();
			final ErrorCall errorCall = builder.errorCall(FEATURE_ACCESS_SERVICE_NAME, false, receiver);
			errorCall.setType(CallType.CALLORAPPLY);
			setIdentifierPositions(errorCall, (Token)offendingSymbol);
			setPositions(errorCall, receiver, (Token)offendingSymbol);
			pushError(errorCall, "missing feature access or service call");
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
			final int position = ((Token)offendingSymbol).getStopIndex() + 1;
			final int line = ((Token)offendingSymbol).getLine() - 1;
			final int column = ((Token)offendingSymbol).getCharPositionInLine() + ((Token)offendingSymbol)
					.getText().length();
			setIdentifierPositions(errorBinding, position, line, column);
			setPositions(errorBinding, position, line, column);
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
				diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, PLUGIN_ID, 0, msg, new Object[] {}));
			} else {
				switch (e.getCtx().getRuleIndex()) {
					case QueryParser.RULE_expression:
						errorRule = QueryParser.RULE_expression;
						final ErrorExpression errorExpression = builder.errorExpression();
						final int position = ((ParserRuleContext)e.getCtx()).start.getStartIndex();
						final int line = ((ParserRuleContext)e.getCtx()).start.getLine() - 1;
						final int column = ((ParserRuleContext)e.getCtx()).start.getCharPositionInLine();
						setIdentifierPositions(errorExpression, position, line, column);
						setPositions(errorExpression, position, line, column);
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
			diagnosticStack.addLast(new BasicDiagnostic(Diagnostic.WARNING, PLUGIN_ID, 0, msg, new Object[] {
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
	private Deque<Object> stack = new ArrayDeque<Object>();

	/**
	 * The last rule index if any error. see {@link QueryParser}.
	 */
	private int errorRule = -1;

	/**
	 * The parsed {@link Positions}.
	 */
	private Positions<ASTNode> positions = new Positions<>();

	/**
	 * The {@link List} of {@link Error}.
	 */
	private List<Error> errors = new ArrayList<Error>();

	/**
	 * Temporary lexing warnings waiting for their {@link Expression}.
	 */
	private Deque<Diagnostic> diagnosticStack = new ArrayDeque<Diagnostic>();

	/** Aggregated status of the parsing. */
	private final List<Diagnostic> diagnostics = new ArrayList<Diagnostic>();

	/**
	 * The {@link ANTLRErrorListener} pushing {@link org.eclipse.acceleo.query.ast.Error Error}.
	 */
	private final ANTLRErrorListener errorListener = new QueryErrorListener();

	/**
	 * Ast Builder.
	 */
	private final AstBuilder builder = new AstBuilder();

	/**
	 * The {@link Lambda} {@link VariableDeclaration#getExpression() variable declaration expression}.
	 */
	private Deque<Expression> lambdaVariableExpression = new ArrayDeque<>();

	/**
	 * Tells if the last argument was a {@link Lambda}.
	 */
	private boolean lastArgumentIsLambda;

	/**
	 * Creates a new {@link AstBuilderListener}.
	 * 
	 * @param environment
	 *            the package provider
	 * @deprecated see {@link #AstBuilderListener()}
	 */
	public AstBuilderListener(IReadOnlyQueryEnvironment environment) {
		this();
	}

	/**
	 * Creates a new {@link AstBuilderListener}.
	 */
	public AstBuilderListener() {
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
	 * Sets the identifier positions of the given node.
	 * 
	 * @param node
	 *            the node
	 * @param position
	 *            the position
	 * @param line
	 *            the line
	 * @param column
	 *            the column
	 */
	private void setIdentifierPositions(ASTNode node, int position, int line, int column) {
		positions.setIdentifierStartPositions(node, position);
		positions.setIdentifierStartLines(node, line);
		positions.setIdentifierStartColumns(node, column);
		positions.setIdentifierEndPositions(node, position);
		positions.setIdentifierEndLines(node, line);
		positions.setIdentifierEndColumns(node, column);
	}

	/**
	 * Sets the positions of the given node.
	 * 
	 * @param node
	 *            the node
	 * @param position
	 *            the position
	 * @param line
	 *            the line
	 * @param column
	 *            the column
	 */
	private void setPositions(ASTNode node, int position, int line, int column) {
		positions.setStartPositions(node, position);
		positions.setStartLines(node, line);
		positions.setStartColumns(node, column);
		positions.setEndPositions(node, position);
		positions.setEndLines(node, line);
		positions.setEndColumns(node, column);
	}

	/**
	 * Sets the identifier positions of the given node.
	 * 
	 * @param node
	 *            the node
	 * @param identifierToken
	 *            the identifierToken {@link Token}
	 */
	private void setIdentifierPositions(ASTNode node, Token identifierToken) {
		positions.setIdentifierStartPositions(node, Integer.valueOf(identifierToken.getStartIndex()));
		positions.setIdentifierStartLines(node, Integer.valueOf(identifierToken.getLine() - 1));
		positions.setIdentifierStartColumns(node, Integer.valueOf(identifierToken.getCharPositionInLine()));
		positions.setIdentifierEndPositions(node, Integer.valueOf(identifierToken.getStopIndex() + 1));
		positions.setIdentifierEndLines(node, Integer.valueOf(identifierToken.getLine() - 1));
		positions.setIdentifierEndColumns(node, Integer.valueOf(identifierToken.getCharPositionInLine()
				+ identifierToken.getText().length()));
	}

	/**
	 * Sets the positions of the given node.
	 * 
	 * @param node
	 *            the node
	 * @param start
	 *            the start {@link Token}
	 * @param end
	 *            the end {@link Token}
	 */
	private void setPositions(ASTNode node, Token start, Token end) {
		positions.setStartPositions(node, Integer.valueOf(start.getStartIndex()));
		positions.setStartLines(node, Integer.valueOf(start.getLine() - 1));
		positions.setStartColumns(node, Integer.valueOf(start.getCharPositionInLine()));
		positions.setEndPositions(node, Integer.valueOf(end.getStopIndex() + 1));
		positions.setEndLines(node, Integer.valueOf(end.getLine() - 1));
		positions.setEndColumns(node, Integer.valueOf(end.getCharPositionInLine() + end.getText().length()));
	}

	/**
	 * Sets the identifier positions of the given node.
	 * 
	 * @param node
	 *            the node
	 * @param start
	 *            the start {@link Token}
	 * @param end
	 *            the end {@link Token}
	 */
	private void setIdentifierPositions(ASTNode node, Token start, Token end) {
		positions.setIdentifierStartPositions(node, Integer.valueOf(start.getStartIndex()));
		positions.setIdentifierStartLines(node, Integer.valueOf(start.getLine() - 1));
		positions.setIdentifierStartColumns(node, Integer.valueOf(start.getCharPositionInLine()));
		positions.setIdentifierEndPositions(node, Integer.valueOf(end.getStopIndex() + 1));
		positions.setIdentifierEndLines(node, Integer.valueOf(end.getLine() - 1));
		positions.setIdentifierEndColumns(node, Integer.valueOf(end.getCharPositionInLine() + end.getText()
				.length()));
	}

	/**
	 * Sets the positions of the given node.
	 * 
	 * @param node
	 *            the node
	 * @param astNodeStart
	 *            the start {@link ASTNode}
	 * @param astNodeEnd
	 *            the end {@link ASTNode}
	 */
	private void setPositions(ASTNode node, ASTNode astNodeStart, ASTNode astNodeEnd) {
		positions.setStartPositions(node, positions.getStartPositions(astNodeStart));
		positions.setStartLines(node, positions.getStartLines(astNodeStart));
		positions.setStartColumns(node, positions.getStartColumns(astNodeStart));
		positions.setEndPositions(node, positions.getEndPositions(astNodeEnd));
		positions.setEndLines(node, positions.getEndLines(astNodeEnd));
		positions.setEndColumns(node, positions.getEndColumns(astNodeEnd));
	}

	/**
	 * Sets the identifier positions of the given node.
	 * 
	 * @param node
	 *            the node
	 * @param astNodeStart
	 *            the start {@link ASTNode}
	 * @param astNodeEnd
	 *            the end {@link ASTNode}
	 */
	private void setIdentifierPositions(ASTNode node, ASTNode astNodeStart, ASTNode astNodeEnd) {
		positions.setIdentifierStartPositions(node, positions.getStartPositions(astNodeStart));
		positions.setIdentifierStartLines(node, positions.getStartLines(astNodeStart));
		positions.setIdentifierStartColumns(node, positions.getStartColumns(astNodeStart));
		positions.setIdentifierEndPositions(node, positions.getEndPositions(astNodeEnd));
		positions.setIdentifierEndLines(node, positions.getEndLines(astNodeEnd));
		positions.setIdentifierEndColumns(node, positions.getEndColumns(astNodeEnd));
	}

	/**
	 * Sets the node positions.
	 * 
	 * @param node
	 *            the node
	 * @param astNodeStart
	 *            the start {@link ASTNode}
	 * @param end
	 *            the end {@link Token}
	 */
	private void setPositions(ASTNode node, ASTNode astNodeStart, Token end) {
		positions.setStartPositions(node, positions.getStartPositions(astNodeStart));
		positions.setStartLines(node, positions.getStartLines(astNodeStart));
		positions.setStartColumns(node, positions.getStartColumns(astNodeStart));
		positions.setEndPositions(node, Integer.valueOf(end.getStopIndex() + 1));
		positions.setEndLines(node, Integer.valueOf(end.getLine() - 1));
		positions.setEndColumns(node, Integer.valueOf(end.getCharPositionInLine() + end.getText().length()));
	}

	/**
	 * Returns the {@link AstResult}.
	 * 
	 * @return the {@link AstResult}.
	 */
	public AstResult getAstResult() {
		final Expression ast = popExpression();
		final BasicDiagnostic diagnostic = new BasicDiagnostic();
		for (Diagnostic diag : diagnostics) {
			diagnostic.add(diag);
		}
		final Positions<ASTNode> resultPositions = positions;
		positions = new Positions<>();
		final List<Error> resultErrors = errors;
		errors = new ArrayList<Error>();
		return new AstResult(ast, resultPositions, resultErrors, diagnostic);
	}

	/**
	 * Pop the top of the stack.
	 * 
	 * @return the value on top of the stack
	 */
	private Expression popExpression() {
		try {
			final Expression expression = (Expression)pop();

			if (!diagnosticStack.isEmpty()) {
				final List<?> data = diagnosticStack.getLast().getData();
				if (data.get(0).equals(positions.getStartPositions(expression)) && data.get(1).equals(
						positions.getEndPositions(expression))) {
					final Diagnostic tmpDiagnostic = diagnosticStack.removeLast();
					diagnostics.add(new BasicDiagnostic(tmpDiagnostic.getSeverity(), tmpDiagnostic
							.getSource(), tmpDiagnostic.getCode(), tmpDiagnostic.getMessage(), new Object[] {
									expression }));
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
			return (Binding)pop();
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
			return (VariableDeclaration)pop();
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
			return (TypeLiteral)pop();
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
		return (Call)stack.getLast();
	}

	/**
	 * push an object on the stack.
	 * 
	 * @param obj
	 *            the pushed object.
	 */
	private void push(Object obj) {
		this.stack.addLast(obj);
	}

	/**
	 * Pops the stack.
	 * 
	 * @return the element at the top of the stack if any
	 */
	protected Object pop() {
		final Object element = stack.removeLast();
		return element;
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
		diagnostics.add(new BasicDiagnostic(Diagnostic.ERROR, PLUGIN_ID, 0, msg, new Object[] {error }));
		push(error);
	}

	/**
	 * Pops the last {@link ErrorExpression}.
	 */
	private void popErrorExpression() {
		if (!stack.isEmpty() && stack.getLast() instanceof ErrorExpression) {
			final ErrorExpression error = (ErrorExpression)pop();
			errors.remove(error);
			positions.remove(error);
			diagnostics.remove(diagnostics.size() - 1);
		}
	}

	@Override
	public void exitIntType(IntTypeContext ctx) {
		final Literal typeLiteral = builder.typeLiteral(java.lang.Integer.class);

		setPositions(typeLiteral, ctx.start, ctx.stop);

		push(typeLiteral);
	}

	@Override
	public void exitFalseLit(FalseLitContext ctx) {
		final BooleanLiteral booleanLiteral = builder.booleanLiteral(false);

		setPositions(booleanLiteral, ctx.start, ctx.stop);

		push(booleanLiteral);
	}

	@Override
	public void exitRealType(RealTypeContext ctx) {
		final Literal realLiteral = builder.typeLiteral(java.lang.Double.class);

		setPositions(realLiteral, ctx.start, ctx.stop);

		push(realLiteral);
	}

	@Override
	public void exitTrueLit(TrueLitContext ctx) {
		final BooleanLiteral booleanLiteral = builder.booleanLiteral(true);

		setPositions(booleanLiteral, ctx.start, ctx.stop);

		push(booleanLiteral);
	}

	@Override
	public void exitSeqType(SeqTypeContext ctx) {
		final TypeLiteral elementType = popTypeLiteral();
		final CollectionTypeLiteral collectionTypeLiteral = builder.collectionTypeLiteral(List.class,
				elementType);

		setPositions(collectionTypeLiteral, ctx.start, ctx.stop);

		push(collectionTypeLiteral);
	}

	@Override
	public void exitSetType(SetTypeContext ctx) {
		final TypeLiteral elementType = popTypeLiteral();
		final CollectionTypeLiteral collectionTypeLiteral = builder.collectionTypeLiteral(Set.class,
				elementType);

		setPositions(collectionTypeLiteral, ctx.start, ctx.stop);

		push(collectionTypeLiteral);
	}

	@Override
	public void exitNot(NotContext ctx) {
		final Call callService = builder.callService(NOT_SERVICE_NAME, popExpression());

		setIdentifierPositions(callService, ctx.start, ctx.stop);
		setPositions(callService, ctx.start, ctx.stop);

		push(callService);
	}

	@Override
	public void exitStringLit(StringLitContext ctx) {
		final String text = ctx.getText();
		final StringLiteral stringLiteral = builder.stringLiteral(text.substring(1, text.length() - 1));

		setPositions(stringLiteral, ctx.start, ctx.stop);

		push(stringLiteral);
	}

	@Override
	public void exitErrorStringLit(ErrorStringLitContext ctx) {
		final String text = ctx.getText();
		final ErrorStringLiteral errorStringLiteral = builder.errorStringLiteral(text.substring(1, text
				.length()));

		setPositions(errorStringLiteral, ctx.start, ctx.stop);

		pushError(errorStringLiteral, "String literal is not properly closed by a simple-quote.");
	}

	@Override
	public void exitRealLit(RealLitContext ctx) {
		final RealLiteral realLiteral = builder.realLiteral(Double.valueOf(ctx.getText()));

		setPositions(realLiteral, ctx.start, ctx.stop);

		push(realLiteral);
	}

	@Override
	public void exitStrType(StrTypeContext ctx) {
		final Literal typeLiteral = builder.typeLiteral(java.lang.String.class);

		setPositions(typeLiteral, ctx.start, ctx.stop);

		push(typeLiteral);
	}

	@Override
	public void exitOr(OrContext ctx) {
		Expression op2 = popExpression();
		Expression op1 = popExpression();
		final Or callService = builder.callOrService(op1, op2);

		setIdentifierPositions(callService, (Token)ctx.getChild(1).getPayload());
		setPositions(callService, op1, op2);

		push(callService);
	}

	@Override
	public void exitXor(XorContext ctx) {
		Expression op2 = popExpression();
		Expression op1 = popExpression();
		final Call callService = builder.callService(XOR_SERVICE_NAME, op1, op2);

		setIdentifierPositions(callService, (Token)ctx.getChild(1).getPayload());
		setPositions(callService, op1, op2);

		push(callService);

	}

	@Override
	public void exitImplies(ImpliesContext ctx) {
		Expression op2 = popExpression();
		Expression op1 = popExpression();
		final Implies callService = builder.callImpliesService(op1, op2);

		setIdentifierPositions(callService, op1, op2);
		setPositions(callService, op1, op2);

		push(callService);
	}

	@Override
	public void exitBooleanType(BooleanTypeContext ctx) {
		final Literal typeLiteral = builder.typeLiteral(java.lang.Boolean.class);

		setPositions(typeLiteral, ctx.start, ctx.stop);

		push(typeLiteral);
	}

	@Override
	public void exitIntegerLit(IntegerLitContext ctx) {
		final IntegerLiteral integerLiteral = builder.integerLiteral(Integer.valueOf(ctx.getText()));

		setPositions(integerLiteral, ctx.start, ctx.stop);

		push(integerLiteral);
	}

	@Override
	public void exitAnd(AndContext ctx) {
		Expression op2 = popExpression();
		Expression op1 = popExpression();
		final And callService = builder.callAndService(op1, op2);

		setIdentifierPositions(callService, op1, op2);
		setPositions(callService, op1, op2);

		push(callService);
	}

	@Override
	public void exitVarRef(VarRefContext ctx) {
		final VarRef varRef = builder.varRef(ctx.getText());

		setIdentifierPositions(varRef, ctx.start);
		setPositions(varRef, ctx.start, ctx.stop);

		push(varRef);
	}

	@Override
	public void exitFeature(FeatureContext ctx) {
		final Expression receiver = popExpression();
		final String featureName = AstBuilder.stripUnderscore(ctx.getChild(1).getText());
		final StringLiteral featureNameLiteral = builder.stringLiteral(featureName);
		final Call call = builder.callService(FEATURE_ACCESS_SERVICE_NAME, receiver, featureNameLiteral);
		call.setType(CallType.CALLORAPPLY);

		setPositions(featureNameLiteral, ctx.stop, ctx.stop);
		setIdentifierPositions(call, (Token)ctx.getChild(1).getPayload());
		setPositions(call, receiver, featureNameLiteral);

		push(call);
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
		Expression op2 = popExpression();
		Expression op1 = popExpression();
		final Call callService = builder.callService(service, op1, op2);

		setIdentifierPositions(callService, op1, op2);
		setPositions(callService, op1, op2);

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
			boolean missinEndParenthesis = ctx.getChild(ctx.getChildCount() - 1) instanceof ErrorNode || !")"
					.equals(ctx.getChild(ctx.getChildCount() - 1).getText());
			if (missinEndParenthesis && lastArgumentIsLambda) {
				popErrorExpression();
			}
			final int argc = getNumberOfArgs(ctx.getChild(2).getChildCount());
			final Expression[] args = new Expression[argc];
			for (int i = argc - 1; i >= 0; i--) {
				args[i] = popExpression();
			}
			final String serviceName;
			final boolean isSuperCall;
			if (SUPER_CALL.equals(ctx.getChild(0).getText())) {
				serviceName = ctx.getChild(1).getText().replace("::", ".");
				isSuperCall = true;
			} else {
				serviceName = ctx.getChild(0).getText().replace("::", ".");
				isSuperCall = false;
			}

			final Call call;
			if (missinEndParenthesis) {
				call = builder.errorCall(serviceName, true, args);
				pushError((Error)call, "missing ')'");
			} else {
				call = builder.callService(serviceName, args);
				push(call);
			}

			call.setSuperCall(isSuperCall);
			setIdentifierPositions(call, (Token)ctx.getChild(0).getPayload());
			setPositions(call, args[0], ctx.stop);
		} else {
			errorRule = NO_ERROR;
		}
	}

	@Override
	public void enterArguments(ArgumentsContext ctx) {
		lastArgumentIsLambda = false;
		lambdaVariableExpression.addLast((Expression)stack.getLast());
	}

	@Override
	public void exitArguments(ArgumentsContext ctx) {
		lastArgumentIsLambda = stack.getLast() instanceof Lambda;
		lambdaVariableExpression.removeLast();
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
		final Call callService = builder.callService(UNARY_MIN_SERVICE_NAME, popExpression());

		setIdentifierPositions(callService, ctx.start, ctx.stop);
		setPositions(callService, ctx.start, ctx.stop);

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
		} else if (EQUALS_OPERATOR.equals(op) || EQUALS_JAVA_OPERATOR.equals(op)) {
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

	@Override
	public void exitVariableDefinition(VariableDefinitionContext ctx) {
		// If the error flag is raised an error occurred and the variable is already
		// there.
		if (errorRule == NO_ERROR) {
			final VariableDeclaration variableDeclaration;
			final Token stop;
			if (ctx.getChildCount() == 4) {
				final TypeLiteral typeLiteral = popTypeLiteral();
				final Expression variableExpression = lambdaVariableExpression.getLast();
				variableDeclaration = builder.variableDeclaration(ctx.getChild(0).getText(), typeLiteral,
						variableExpression);
				stop = ((ParserRuleContext)ctx.getChild(2)).stop;
			} else {
				final Expression variableExpression = lambdaVariableExpression.getLast();
				variableDeclaration = builder.variableDeclaration(ctx.getChild(0).getText(),
						variableExpression);
				stop = ((TerminalNode)ctx.getChild(0)).getSymbol();
			}
			setIdentifierPositions(variableDeclaration, (Token)ctx.getChild(0).getPayload());
			setPositions(variableDeclaration, ctx.start, stop);

			push(variableDeclaration);
		} else {
			errorRule = NO_ERROR;
		}
	}

	@Override
	public void exitLambda(LambdaContext ctx) {
		final Expression expression = popExpression();
		popErrorExpression();
		final VariableDeclaration declaration = popVariableDeclaration();

		final Lambda lambda = builder.lambda(expression, declaration);
		setIdentifierPositions(lambda, declaration, declaration);
		setPositions(lambda, declaration, expression);
		push(lambda);
	}

	@Override
	public void exitEnumLit(EnumLitContext ctx) {
		if (ctx.getChildCount() >= 5) {
			final EnumLiteral toPush;
			final String ePackageName = ctx.getChild(0).getText();
			final String eEnumName = ctx.getChild(2).getText();
			final String eEnumLiteralName = ctx.getChild(4).getText();
			if (ctx.getChild(4) instanceof ErrorNode) {
				toPush = builder.errorEnumLiteral(false, ePackageName, eEnumName);
				pushError((Error)toPush, String.format(INVALID_ENUM_LITERAL, "missing literal name"));
			} else {
				toPush = builder.enumLiteral(ePackageName, eEnumName, eEnumLiteralName);
				push(toPush);
			}
			setIdentifierPositions(toPush, ctx.start, ctx.stop);
			setPositions(toPush, ctx.start, ctx.stop);
		}
	}

	@Override
	public void exitErrorEnumLit(ErrorEnumLitContext ctx) {
		if (errorRule == NO_ERROR) {
			final String ePackageName = ctx.getChild(0).getText();
			final String eEnumName = ctx.getChild(2).getText();

			final ErrorEnumLiteral errorEnumLiteral = builder.errorEnumLiteral(true, ePackageName, eEnumName);

			pushError(errorEnumLiteral, String.format(INVALID_ENUM_LITERAL, "':' instead of '::'"));
			setIdentifierPositions(errorEnumLiteral, ctx.start, ctx.stop);
			setPositions(errorEnumLiteral, ctx.start, ctx.stop);
		} else {
			errorRule = NO_ERROR;
		}
	}

	@Override
	public void exitClassifierType(ClassifierTypeContext ctx) {
		if (errorRule == NO_ERROR) {
			final Literal toPush;
			final String ePackageName = ctx.getChild(0).getText();
			if (ctx.getChild(2) == null || ctx.getChild(2) instanceof ErrorNode) {
				toPush = builder.errorEClassifierTypeLiteral(false, ePackageName);
				pushError((Error)toPush, String.format(INVALID_TYPE_LITERAL, ctx.getText()));
			} else {
				final String eClassName = ctx.getChild(2).getText();
				toPush = builder.eClassifierTypeLiteral(ePackageName, eClassName);
				push(toPush);
			}
			setIdentifierPositions(toPush, ctx.start, ctx.stop);
			setPositions(toPush, ctx.start, ctx.stop);
		}
	}

	@Override
	public void exitErrorClassifierType(ErrorClassifierTypeContext ctx) {
		final String ePackageName = ctx.getChild(0).getText();

		final ErrorEClassifierTypeLiteral errorTypeLiteral = builder.errorEClassifierTypeLiteral(true,
				ePackageName);

		pushError((Error)errorTypeLiteral, String.format(INVALID_TYPE_LITERAL, ctx.getText()));
		setIdentifierPositions(errorTypeLiteral, ctx.start, ctx.stop);
		setPositions(errorTypeLiteral, ctx.start, ctx.stop);
	}

	/**
	 * Gets the {@link ANTLRErrorListener}.
	 * 
	 * @return the {@link ANTLRErrorListener}
	 */
	public ANTLRErrorListener getErrorListener() {
		return errorListener;
	}

	@Override
	public void exitNullLit(NullLitContext ctx) {
		final NullLiteral nullLiteral = builder.nullLiteral();

		setPositions(nullLiteral, ctx.start, ctx.stop);

		push(nullLiteral);
	}

	@Override
	public void exitExplicitSetLit(ExplicitSetLitContext ctx) {
		final SetInExtensionLiteral setInExtension = builder.setInExtension(getExpressions(ctx));

		setPositions(setInExtension, ctx.start, ctx.stop);

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
			expressions[i] = popExpression();
		}

		return Arrays.asList(expressions);
	}

	@Override
	public void exitExplicitSeqLit(ExplicitSeqLitContext ctx) {
		final SequenceInExtensionLiteral sequenceInExtension = builder.sequenceInExtension(getExpressions(
				ctx));

		setPositions(sequenceInExtension, ctx.start, ctx.stop);

		push(sequenceInExtension);
	}

	@Override
	public void exitConditional(ConditionalContext ctx) {
		final int count = ctx.getChildCount();
		final Expression predicate;
		final Expression trueBranch;
		final Expression falseBranch;
		if (count <= 3) {
			predicate = popExpression();
			trueBranch = null;
			falseBranch = null;
		} else if (count <= 5) {
			trueBranch = popExpression();
			predicate = popExpression();
			falseBranch = null;
		} else {
			falseBranch = popExpression();
			trueBranch = popExpression();
			predicate = popExpression();
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

		setPositions(conditional, ctx.start, ctx.stop);
	}

	@Override
	public void exitBinding(BindingContext ctx) {
		// If the error flag is raised an error occurred and the binding is already
		// there.
		if (errorRule != QueryParser.RULE_binding) {
			final String variable = ctx.getChild(0).getText();
			final Expression expression = popExpression();
			final TypeLiteral type;
			if (ctx.getChildCount() == 5) {
				type = popTypeLiteral();
			} else {
				type = null;
			}
			final Binding binding = builder.binding(variable, type, expression);

			setIdentifierPositions(binding, (Token)ctx.getChild(0).getPayload());
			setPositions(binding, ctx.start, ctx.stop);

			push(binding);
		} else {
			errorRule = NO_ERROR;
		}
	}

	@Override
	public void exitLetExpr(LetExprContext ctx) {
		final Expression body;
		final Binding[] bindings;

		if (!(ctx.getChild(ctx.getChildCount() - 1) instanceof ExpressionContext)) {
			popErrorExpression();
			body = builder.errorExpression();
			final int position = ctx.stop.getStopIndex() + 1;
			final int line = ctx.stop.getLine() - 1;
			final int column = ctx.stop.getCharPositionInLine() + ctx.stop.getText().length();
			setPositions(body, position, line, column);
			final List<Binding> bindingList = new ArrayList<Binding>();
			while (!stack.isEmpty() && stack.getLast() instanceof Binding) {
				bindingList.add(popBinding());
			}
			bindings = bindingList.toArray(new Binding[bindingList.size()]);
		} else {
			body = popExpression();
			int bindingNumber = 1 + (ctx.getChildCount() - 3) / 2;
			bindings = new Binding[bindingNumber];
			for (int i = bindingNumber - 1; i >= 0; i--) {
				bindings[i] = popBinding();
			}
		}
		final Let let = builder.let(body, bindings);

		setPositions(let, ctx.start, ctx.stop);

		push(let);
	}

	@Override
	public void exitClassifierSetType(ClassifierSetTypeContext ctx) {
		final int nbTypes = (ctx.getChildCount() + 1) / 2 - 1;
		final TypeLiteral[] types = new TypeLiteral[nbTypes];

		for (int i = nbTypes - 1; i >= 0; i--) {
			types[i] = popTypeLiteral();
		}

		final TypeSetLiteral classifierSetType = builder.typeSetLiteral(Arrays.asList(types));

		setPositions(classifierSetType, ctx.start, ctx.stop);

		push(classifierSetType);
	}

}
