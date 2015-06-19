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
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.acceleo.query.ast.Binding;
import org.eclipse.acceleo.query.ast.BooleanLiteral;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.ast.CollectionTypeLiteral;
import org.eclipse.acceleo.query.ast.Error;
import org.eclipse.acceleo.query.ast.ErrorCollectionCall;
import org.eclipse.acceleo.query.ast.ErrorExpression;
import org.eclipse.acceleo.query.ast.ErrorFeatureAccessOrCall;
import org.eclipse.acceleo.query.ast.ErrorTypeLiteral;
import org.eclipse.acceleo.query.ast.ErrorVariableDeclaration;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.FeatureAccess;
import org.eclipse.acceleo.query.ast.IntegerLiteral;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.Literal;
import org.eclipse.acceleo.query.ast.NullLiteral;
import org.eclipse.acceleo.query.ast.RealLiteral;
import org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral;
import org.eclipse.acceleo.query.ast.SetInExtensionLiteral;
import org.eclipse.acceleo.query.ast.StringLiteral;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.parser.QueryParser.AddContext;
import org.eclipse.acceleo.query.parser.QueryParser.AndContext;
import org.eclipse.acceleo.query.parser.QueryParser.ApplyContext;
import org.eclipse.acceleo.query.parser.QueryParser.AsContext;
import org.eclipse.acceleo.query.parser.QueryParser.AsTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.BindingContext;
import org.eclipse.acceleo.query.parser.QueryParser.BooleanTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.CallExpContext;
import org.eclipse.acceleo.query.parser.QueryParser.CallServiceContext;
import org.eclipse.acceleo.query.parser.QueryParser.CompContext;
import org.eclipse.acceleo.query.parser.QueryParser.ConditionalContext;
import org.eclipse.acceleo.query.parser.QueryParser.EAContentContext;
import org.eclipse.acceleo.query.parser.QueryParser.EContainerContext;
import org.eclipse.acceleo.query.parser.QueryParser.EContainerOrSelfContext;
import org.eclipse.acceleo.query.parser.QueryParser.EContentContext;
import org.eclipse.acceleo.query.parser.QueryParser.EInverseContext;
import org.eclipse.acceleo.query.parser.QueryParser.EnumOrClassifierLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.ExplicitSeqLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.ExplicitSetLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.ExpressionContext;
import org.eclipse.acceleo.query.parser.QueryParser.FalseLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.FeatureContext;
import org.eclipse.acceleo.query.parser.QueryParser.FilterContext;
import org.eclipse.acceleo.query.parser.QueryParser.ImpliesContext;
import org.eclipse.acceleo.query.parser.QueryParser.IntTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.IntegerLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.IsKindContext;
import org.eclipse.acceleo.query.parser.QueryParser.IsTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.IterationCallContext;
import org.eclipse.acceleo.query.parser.QueryParser.LetExprContext;
import org.eclipse.acceleo.query.parser.QueryParser.LiteralContext;
import org.eclipse.acceleo.query.parser.QueryParser.MinContext;
import org.eclipse.acceleo.query.parser.QueryParser.ModelObjectTypeContext;
import org.eclipse.acceleo.query.parser.QueryParser.MultContext;
import org.eclipse.acceleo.query.parser.QueryParser.NavigationSegmentContext;
import org.eclipse.acceleo.query.parser.QueryParser.NotContext;
import org.eclipse.acceleo.query.parser.QueryParser.NullLitContext;
import org.eclipse.acceleo.query.parser.QueryParser.OrContext;
import org.eclipse.acceleo.query.parser.QueryParser.PrecSiblingsContext;
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
import org.eclipse.acceleo.query.parser.QueryParser.XorContext;
import org.eclipse.acceleo.query.runtime.AcceleoQueryEvaluationException;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine.AstResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;

/**
 * The {@link AstBuilderListener} builds an AST when plugged into the parser.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class AstBuilderListener extends QueryBaseListener {
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
					iterationCallContextError(e);
				} else if (e.getCtx() instanceof TypeLiteralContext) {
					typeLiteralContextError(offendingSymbol, e);
				} else if (e.getCtx() instanceof VariableDefinitionContext) {
					variableDefinitionContextError(offendingSymbol, e);
				} else if (e.getCtx() instanceof CallExpContext) {
					callExpContextError(offendingSymbol);
				} else if (e.getCtx() instanceof NavigationSegmentContext) {
					navigationSegmentContextError(offendingSymbol);
				} else {
					defaultError(offendingSymbol, e);
				}
			} else if (recognizer instanceof QueryParser) {
				noRecognitionException(recognizer, offendingSymbol);
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
			pushError(errorExpression);
			final Integer position = Integer
					.valueOf(((IterationCallContext)e.getCtx()).start.getStartIndex());
			startPositions.put(errorExpression, position);
			endPositions.put(errorExpression, position);
		}

		/**
		 * {@link TypeLiteralContext} error case.
		 * 
		 * @param offendingSymbol
		 *            the offending symbol
		 * @param e
		 *            the {@link RecognitionException}
		 */
		private void typeLiteralContextError(Object offendingSymbol, RecognitionException e) {
			final Integer startPosition = Integer.valueOf(((TypeLiteralContext)e.getCtx()).start
					.getStartIndex());
			final Integer endPosition = Integer.valueOf(((Token)offendingSymbol).getStopIndex() + 1);
			if (e.getCtx().getParent() instanceof VariableDefinitionContext) {
				errorRule = QueryParser.RULE_expression;
				final String variableName = e.getCtx().getParent().getChild(0).getText();
				final ErrorTypeLiteral type = builder.errorTypeLiteral(new String[] {});
				startPositions.put(type, startPosition);
				endPositions.put(type, endPosition);
				errors.add(type);
				final Expression variableExpression = pop();
				final VariableDeclaration variableDeclaration = builder.variableDeclaration(variableName,
						type, variableExpression);
				startPositions.put(variableDeclaration, startPosition);
				endPositions.put(variableDeclaration, endPosition);
				push(variableDeclaration);
				final ErrorExpression errorExpression = builder.errorExpression();
				pushError(errorExpression);
				startPositions.put(errorExpression, startPosition);
				endPositions.put(errorExpression, endPosition);
			} else if (!(stack.peek() instanceof TypeLiteral)) {
				errorRule = QueryParser.RULE_typeLiteral;
				final ErrorTypeLiteral errorTypeLiteral = builder.errorTypeLiteral(new String[] {});
				startPositions.put(errorTypeLiteral, startPosition);
				endPositions.put(errorTypeLiteral, endPosition);
				pushError(errorTypeLiteral);
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
			final Integer startPosition = Integer.valueOf(((VariableDefinitionContext)e.getCtx()).start
					.getStartIndex());
			final Integer endPosition = Integer.valueOf(((Token)offendingSymbol).getStopIndex() + 1);
			if (e.getCtx().getChildCount() > 0) {
				errorRule = QueryParser.RULE_expression;
				final String variableName = e.getCtx().getChild(0).getText();
				final TypeLiteral type = createModelType((ModelObjectTypeContext)e.getCtx().getChild(2));
				// overwrite the end position to the current error position (the start position is
				// fine)
				endPositions.put(type, endPosition);
				final Expression variableExpression = pop();
				final VariableDeclaration variableDeclaration = builder.variableDeclaration(variableName,
						type, variableExpression);
				startPositions.put(variableDeclaration, startPosition);
				endPositions.put(variableDeclaration, endPosition);
				push(variableDeclaration);
			} else {
				final Expression variableExpression = pop();
				errorRule = QueryParser.RULE_variableDefinition;
				final ErrorVariableDeclaration errorVariableDeclaration = builder
						.errorVariableDeclaration(variableExpression);
				startPositions.put(errorVariableDeclaration, startPosition);
				endPositions.put(errorVariableDeclaration, endPosition);
				pushError(errorVariableDeclaration);
			}
			final ErrorExpression errorExpression = builder.errorExpression();
			startPositions.put(errorExpression, endPosition);
			endPositions.put(errorExpression, endPosition);
			pushError(errorExpression);
		}

		/**
		 * {@link CallExpContext} error case.
		 * 
		 * @param offendingSymbol
		 *            the offending symbol
		 */
		private void callExpContextError(Object offendingSymbol) {
			errorRule = QueryParser.RULE_navigationSegment;
			final Expression receiver = pop();
			final ErrorCollectionCall errorCollectionCall = builder.errorCollectionCall(receiver);
			startPositions.put(errorCollectionCall, startPositions.get(receiver));
			endPositions.put(errorCollectionCall, Integer
					.valueOf(((Token)offendingSymbol).getStopIndex() + 1));
			pushError(errorCollectionCall);
		}

		/**
		 * {@link NavigationSegmentContext} error case.
		 * 
		 * @param offendingSymbol
		 *            the offending symbol
		 */
		private void navigationSegmentContextError(Object offendingSymbol) {
			final Expression receiver = pop();
			final ErrorFeatureAccessOrCall errorFeatureAccessOrCall = builder
					.errorFeatureAccessOrCall(receiver);
			startPositions.put(errorFeatureAccessOrCall, startPositions.get(receiver));
			endPositions.put(errorFeatureAccessOrCall, Integer.valueOf(((Token)offendingSymbol)
					.getStopIndex() + 1));
			pushError(errorFeatureAccessOrCall);
		}

		/**
		 * Default error case.
		 * 
		 * @param offendingSymbol
		 *            the offending symbol
		 * @param e
		 *            the {@link RecognitionException}
		 */
		private void defaultError(Object offendingSymbol, RecognitionException e) {
			errorRule = e.getCtx().getRuleIndex();
			switch (e.getCtx().getRuleIndex()) {
				case QueryParser.RULE_expression:
					final ErrorExpression errorExpression = builder.errorExpression();
					final Integer position = Integer.valueOf(((ParserRuleContext)e.getCtx()).start
							.getStartIndex());
					startPositions.put(errorExpression, position);
					endPositions.put(errorExpression, Integer
							.valueOf(((Token)offendingSymbol).getStopIndex() + 1));
					pushError(errorExpression);
					break;

				default:
					break;
			}
		}

		/**
		 * Handles parser error when the {@link RecognitionException} is <code>null</code>.
		 * 
		 * @param recognizer
		 *            the {@link Recognizer}
		 * @param offendingSymbol
		 *            the offending symbol
		 */
		private void noRecognitionException(Recognizer<?, ?> recognizer, Object offendingSymbol) {
			final QueryParser parser = (QueryParser)recognizer;
			if (parser.getContext() instanceof EnumOrClassifierLitContext) {
				final Integer startPosition = Integer.valueOf(((EnumOrClassifierLitContext)parser
						.getContext()).start.getStartIndex());
				final Integer endPosition = Integer.valueOf(((Token)offendingSymbol).getStopIndex() + 1);
				errorRule = QueryParser.RULE_typeLiteral;
				final ParseTree firstChild = parser.getContext().getChild(0);
				if (firstChild.getChildCount() == 1) {
					final ErrorTypeLiteral errorTypeLiteral = builder
							.errorTypeLiteral(new String[] {firstChild.getChild(0).getText(), });
					startPositions.put(errorTypeLiteral, startPosition);
					endPositions.put(errorTypeLiteral, endPosition);
					pushError(errorTypeLiteral);
				} else if (firstChild.getChildCount() == 3) {
					final ErrorTypeLiteral errorTypeLiteral = builder.errorTypeLiteral(new String[] {
							firstChild.getChild(0).getText(), firstChild.getChild(2).getText(), });
					startPositions.put(errorTypeLiteral, startPosition);
					endPositions.put(errorTypeLiteral, endPosition);
					pushError(errorTypeLiteral);
				} else {
					throw new UnsupportedOperationException("there is no error then...");
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
		result.add(XOR_SERVICE_NAME);

		return result;
	}

	/**
	 * Returns the {@link AstResult}.
	 * 
	 * @return the {@link AstResult}.
	 */
	public AstResult getAstResult() {
		return new AstResult(pop(), startPositions, endPositions, errors);
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
		final TypeLiteral typeLiteral = builder.typeLiteral(java.lang.Boolean.class);

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
		final RealLiteral realLiteral = builder.realLiteral(Double.parseDouble(ctx.getText()));

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
		final Call callService = builder.callService(CallType.CALLSERVICE, NOT_SERVICE_NAME, pop());

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

	@Override
	public void exitRealLit(RealLitContext ctx) {
		final RealLiteral realLiteral = builder.realLiteral(Double.valueOf(ctx.getText()));

		startPositions.put(realLiteral, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(realLiteral, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(realLiteral);
	}

	@Override
	public void exitStrType(StrTypeContext ctx) {
		final TypeLiteral typeLiteral = builder.typeLiteral(java.lang.String.class);

		startPositions.put(typeLiteral, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(typeLiteral, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(typeLiteral);
	}

	@Override
	public void exitOr(OrContext ctx) {
		pushBinary(OR_SERVICE_NAME, ctx);
	}

	@Override
	public void exitXor(XorContext ctx) {
		pushBinary(XOR_SERVICE_NAME, ctx);
	}

	@Override
	public void exitImplies(ImpliesContext ctx) {
		pushBinary(IMPLIES_SERVICE_NAME, ctx);
	}

	@Override
	public void exitBooleanType(BooleanTypeContext ctx) {
		final TypeLiteral typeLiteral = builder.typeLiteral(java.lang.Boolean.class);

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
		pushBinary(AND_SERVICE_NAME, ctx);
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
		final Call callService = builder.callService(CallType.CALLSERVICE, service, op1, op2);
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
		final Call callService = builder.callService(CallType.CALLSERVICE, UNARY_MIN_SERVICE_NAME, pop());

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
		} else if (DIFFERS_OPERATOR.equals(op)) {
			pushBinary(DIFFERS_SERVICE_NAME, ctx);
		} else {
			throw new AcceleoQueryEvaluationException(THIS_SHOULDN_T_HAPPEN);
		}
	}

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
		startPositions.put(result, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(result, Integer.valueOf(ctx.stop.getStopIndex() + 1));

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
					type = pop();
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
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitPrecSiblings(org.eclipse.acceleo.query.parser.QueryParser.PrecSiblingsContext)
	 */
	@Override
	public void exitPrecSiblings(PrecSiblingsContext ctx) {
		callFilteredService("precedingSiblings", ctx);
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
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitEContainerOrSelf(org.eclipse.acceleo.query.parser.QueryParser.EContainerOrSelfContext)
	 */
	@Override
	public void exitEContainerOrSelf(EContainerOrSelfContext ctx) {
		callFilteredService("eContainerOrSelf", ctx);
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
			final Expression[] args = popArgs();
			final String serviceName = popString();
			final Call callService = builder.callService(CallType.COLLECTIONCALL, serviceName, args);
			startPositions.put(callService, startPositions.get(args[0]));
			endPositions.put(callService, Integer.valueOf(ctx.stop.getStopIndex() + 1));
			push(callService);
		} else {
			errorRule = NO_ERROR;
		}
	}

	@Override
	public void exitApply(ApplyContext ctx) {
		final Expression[] args = popArgs();
		final String serviceName = popString();
		final Call callService = builder.callService(CallType.CALLORAPPLY, serviceName, args);
		startPositions.put(callService, startPositions.get(args[0]));
		endPositions.put(callService, Integer.valueOf(ctx.stop.getStopIndex() + 1));
		push(callService);
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
				endPositions.put(variableDeclaration, Integer
						.valueOf(((ParserRuleContext)ctx.getChild(2)).stop.getStopIndex() + 1));
			} else {
				variableDeclaration = builder.variableDeclaration(ctx.getChild(0).getText(),
						variableExpression);
				endPositions.put(variableDeclaration, Integer.valueOf(((TerminalNode)ctx.getChild(0))
						.getSymbol().getStopIndex() + 1));
			}
			startPositions.put(variableDeclaration, Integer.valueOf(ctx.start.getStartIndex()));

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
		iterator = popVariableDeclaration();
		final Lambda lambda = builder.lambda(ast, iterator);
		startPositions.put(lambda, startPositions.get(ast));
		endPositions.put(lambda, Integer.valueOf(endPositions.get(ast)));
		push(serviceName);
		push(new Expression[] {iterator.getExpression(), lambda });
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

		final String enumName = ctx.getChild(0).getChild(0).getText();
		final EEnumLiteral literal = environment.getEPackageProvider().getEnumLiteral(enumName, literalName);
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
		startPositions.put(toPush, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(toPush, Integer.valueOf(ctx.stop.getStopIndex() + 1));

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

		final String nsPrefix = ctx.getChild(0).getChild(0).getText();
		final String enumName = ctx.getChild(0).getChild(2).getText();
		final EEnumLiteral literal = environment.getEPackageProvider().getEnumLiteral(nsPrefix, enumName,
				literalName);
		if (literal != null) {
			toPush = builder.enumLiteral(literal);
		} else {
			toPush = builder.errorTypeLiteral(new String[] {nsPrefix, enumName, literalName, });
		}
		startPositions.put(toPush, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(toPush, Integer.valueOf(ctx.stop.getStopIndex() + 1));

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
		final NullLiteral nullLiteral = builder.nullLiteral();

		startPositions.put(nullLiteral, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(nullLiteral, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(nullLiteral);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitSetLit(org.eclipse.acceleo.query.parser.QueryParser.SetLitContext)
	 */
	@Override
	public void exitSetLit(SetLitContext ctx) {
		final SetInExtensionLiteral setInExtension = builder.setInExtension(getExpressions(ctx));

		startPositions.put(setInExtension, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(setInExtension, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(setInExtension);
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
		final SequenceInExtensionLiteral sequenceInExtension = builder
				.sequenceInExtension(getExpressions(ctx));

		startPositions.put(sequenceInExtension, Integer.valueOf(ctx.start.getStartIndex()));
		endPositions.put(sequenceInExtension, Integer.valueOf(ctx.stop.getStopIndex() + 1));

		push(sequenceInExtension);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitExplicitSeqLit(org.eclipse.acceleo.query.parser.QueryParser.ExplicitSeqLitContext)
	 */
	@Override
	public void exitExplicitSeqLit(ExplicitSeqLitContext ctx) {
		final SequenceInExtensionLiteral sequenceInExtension = builder
				.sequenceInExtension(getExpressions(ctx));

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
		int count = ctx.getChildCount();
		Expression predicate;
		Expression trueBranch;
		Expression falseBranch;
		if (count <= 3) {
			predicate = pop();
			trueBranch = builder.errorExpression();
			falseBranch = builder.errorExpression();
		} else if (count <= 5) {
			trueBranch = pop();
			predicate = pop();
			falseBranch = builder.errorExpression();
		} else {
			falseBranch = pop();
			trueBranch = pop();
			predicate = pop();
		}
		push(builder.conditional(predicate, trueBranch, falseBranch));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.parser.QueryBaseListener#exitBinding(org.eclipse.acceleo.query.parser.QueryParser.BindingContext)
	 */
	@Override
	public void exitBinding(BindingContext ctx) {
		String variable = ctx.getChild(0).getText();
		Expression expression = pop();
		push(builder.binding(variable, expression));
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
		} else {
			body = pop();
		}
		int bindingNumber = 1 + (ctx.getChildCount() - 3) / 2;
		Binding[] bindings = new Binding[bindingNumber];
		for (int i = 0; i < bindingNumber; i++) {
			bindings[i] = popBinding();
		}
		push(builder.let(body, bindings));
	}
}
