// Generated from Query.g4 by ANTLR 4.2.2

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

import java.util.List;

import org.antlr.v4.runtime.FailedPredicateException;
import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast" })
public class QueryParser extends Parser {
	protected static final DFA[] _decisionToDFA;

	protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();

	public static final int T__53 = 1, T__52 = 2, T__51 = 3, T__50 = 4, T__49 = 5, T__48 = 6, T__47 = 7,
			T__46 = 8, T__45 = 9, T__44 = 10, T__43 = 11, T__42 = 12, T__41 = 13, T__40 = 14, T__39 = 15,
			T__38 = 16, T__37 = 17, T__36 = 18, T__35 = 19, T__34 = 20, T__33 = 21, T__32 = 22, T__31 = 23,
			T__30 = 24, T__29 = 25, T__28 = 26, T__27 = 27, T__26 = 28, T__25 = 29, T__24 = 30, T__23 = 31,
			T__22 = 32, T__21 = 33, T__20 = 34, T__19 = 35, T__18 = 36, T__17 = 37, T__16 = 38, T__15 = 39,
			T__14 = 40, T__13 = 41, T__12 = 42, T__11 = 43, T__10 = 44, T__9 = 45, T__8 = 46, T__7 = 47,
			T__6 = 48, T__5 = 49, T__4 = 50, T__3 = 51, T__2 = 52, T__1 = 53, T__0 = 54, WS = 55,
			MultOp = 56, Integer = 57, Real = 58, String = 59, Ident = 60;

	public static final String[] tokenNames = {"<INVALID>", "'Sequence{'", "'OrderedSet{'", "'Sequence('",
			"'['", "'or'", "'<'", "'false'", "'siblings('", "'<='", "'oclIsKindOf('", "'reject'", "'}'",
			"'precedingSiblings('", "'any'", "'->'", "'isUnique'", "')'", "'collect'", "'::'", "'is('",
			"'='", "'oclAsType('", "'String'", "'one'", "'forAll'", "'null'", "'eAllContents('", "'|'",
			"']'", "'oclIsTypeOf('", "'eContents('", "'select'", "','", "'-'", "':'", "'('", "'not'",
			"'eContainer('", "'{'", "'and'", "'eInverse('", "'true'", "'.'", "'+'", "'<>'", "'filter('",
			"'Boolean'", "'Integer'", "'exists'", "'>'", "'Real'", "'as('", "'OrderedSet('", "'>='", "WS",
			"MultOp", "Integer", "Real", "String", "Ident" };

	public static final int RULE_entry = 0, RULE_expression = 1, RULE_addOp = 2, RULE_compOp = 3,
			RULE_varRef = 4, RULE_navigationSegment = 5, RULE_callExp = 6, RULE_lambdaExpression = 7,
			RULE_collectionIterator = 8, RULE_expressionSequence = 9, RULE_variableDefinition = 10,
			RULE_literal = 11, RULE_typeLiteral = 12, RULE_qualifiedName = 13;

	public static final String[] ruleNames = {"entry", "expression", "addOp", "compOp", "varRef",
			"navigationSegment", "callExp", "lambdaExpression", "collectionIterator", "expressionSequence",
			"variableDefinition", "literal", "typeLiteral", "qualifiedName" };

	@Override
	public String getGrammarFileName() {
		return "Query.g4";
	}

	@Override
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override
	public String[] getRuleNames() {
		return ruleNames;
	}

	@Override
	public String getSerializedATN() {
		return _serializedATN;
	}

	@Override
	public ATN getATN() {
		return _ATN;
	}

	public QueryParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
	}

	public static class EntryContext extends ParserRuleContext {
		public TerminalNode EOF() {
			return getToken(QueryParser.EOF, 0);
		}

		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class, 0);
		}

		public EntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_entry;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterEntry(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitEntry(this);
		}
	}

	public final EntryContext entry() throws RecognitionException {
		EntryContext _localctx = new EntryContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_entry);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(28);
				expression(0);
				setState(29);
				match(EOF);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_expression;
		}

		public ExpressionContext() {
		}

		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class MultContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class, i);
		}

		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}

		public TerminalNode MultOp() {
			return getToken(QueryParser.MultOp, 0);
		}

		public MultContext(ExpressionContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterMult(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitMult(this);
		}
	}

	public static class NavContext extends ExpressionContext {
		public NavigationSegmentContext navigationSegment() {
			return getRuleContext(NavigationSegmentContext.class, 0);
		}

		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class, 0);
		}

		public NavContext(ExpressionContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterNav(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitNav(this);
		}
	}

	public static class OrContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class, i);
		}

		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}

		public OrContext(ExpressionContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterOr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitOr(this);
		}
	}

	public static class LitContext extends ExpressionContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class, 0);
		}

		public LitContext(ExpressionContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterLit(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitLit(this);
		}
	}

	public static class AddContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class, i);
		}

		public AddOpContext addOp() {
			return getRuleContext(AddOpContext.class, 0);
		}

		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}

		public AddContext(ExpressionContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterAdd(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitAdd(this);
		}
	}

	public static class VarContext extends ExpressionContext {
		public VarRefContext varRef() {
			return getRuleContext(VarRefContext.class, 0);
		}

		public VarContext(ExpressionContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterVar(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitVar(this);
		}
	}

	public static class ParenContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class, 0);
		}

		public ParenContext(ExpressionContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterParen(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitParen(this);
		}
	}

	public static class AndContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class, i);
		}

		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}

		public AndContext(ExpressionContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterAnd(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitAnd(this);
		}
	}

	public static class MinContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class, 0);
		}

		public MinContext(ExpressionContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterMin(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitMin(this);
		}
	}

	public static class CompContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class, i);
		}

		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}

		public CompOpContext compOp() {
			return getRuleContext(CompOpContext.class, 0);
		}

		public CompContext(ExpressionContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterComp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitComp(this);
		}
	}

	public static class NotContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class, 0);
		}

		public NotContext(ExpressionContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterNot(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitNot(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 2;
		enterRecursionRule(_localctx, 2, RULE_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(42);
				switch (getInterpreter().adaptivePredict(_input, 0, _ctx)) {
					case 1: {
						_localctx = new NotContext(_localctx);
						_ctx = _localctx;
						_prevctx = _localctx;

						setState(32);
						match(37);
						setState(33);
						expression(10);
					}
						break;

					case 2: {
						_localctx = new MinContext(_localctx);
						_ctx = _localctx;
						_prevctx = _localctx;
						setState(34);
						match(34);
						setState(35);
						expression(9);
					}
						break;

					case 3: {
						_localctx = new VarContext(_localctx);
						_ctx = _localctx;
						_prevctx = _localctx;
						setState(36);
						varRef();
					}
						break;

					case 4: {
						_localctx = new LitContext(_localctx);
						_ctx = _localctx;
						_prevctx = _localctx;
						setState(37);
						literal();
					}
						break;

					case 5: {
						_localctx = new ParenContext(_localctx);
						_ctx = _localctx;
						_prevctx = _localctx;
						setState(38);
						match(36);
						setState(39);
						expression(0);
						setState(40);
						match(17);
					}
						break;
				}
				_ctx.stop = _input.LT(-1);
				setState(65);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input, 2, _ctx);
				while (_alt != 2 && _alt != ATN.INVALID_ALT_NUMBER) {
					if (_alt == 1) {
						if (_parseListeners != null)
							triggerExitRuleEvent();
						_prevctx = _localctx;
						{
							setState(63);
							switch (getInterpreter().adaptivePredict(_input, 1, _ctx)) {
								case 1: {
									_localctx = new MultContext(new ExpressionContext(_parentctx,
											_parentState));
									pushNewRecursionContext(_localctx, _startState, RULE_expression);
									setState(44);
									if (!(precpred(_ctx, 8)))
										throw new FailedPredicateException(this, "precpred(_ctx, 8)");
									setState(45);
									match(MultOp);
									setState(46);
									expression(9);
								}
									break;

								case 2: {
									_localctx = new AddContext(
											new ExpressionContext(_parentctx, _parentState));
									pushNewRecursionContext(_localctx, _startState, RULE_expression);
									setState(47);
									if (!(precpred(_ctx, 7)))
										throw new FailedPredicateException(this, "precpred(_ctx, 7)");
									setState(48);
									addOp();
									setState(49);
									expression(8);
								}
									break;

								case 3: {
									_localctx = new CompContext(new ExpressionContext(_parentctx,
											_parentState));
									pushNewRecursionContext(_localctx, _startState, RULE_expression);
									setState(51);
									if (!(precpred(_ctx, 6)))
										throw new FailedPredicateException(this, "precpred(_ctx, 6)");
									setState(52);
									compOp();
									setState(53);
									expression(7);
								}
									break;

								case 4: {
									_localctx = new AndContext(
											new ExpressionContext(_parentctx, _parentState));
									pushNewRecursionContext(_localctx, _startState, RULE_expression);
									setState(55);
									if (!(precpred(_ctx, 5)))
										throw new FailedPredicateException(this, "precpred(_ctx, 5)");
									setState(56);
									match(40);
									setState(57);
									expression(6);
								}
									break;

								case 5: {
									_localctx = new OrContext(new ExpressionContext(_parentctx, _parentState));
									pushNewRecursionContext(_localctx, _startState, RULE_expression);
									setState(58);
									if (!(precpred(_ctx, 4)))
										throw new FailedPredicateException(this, "precpred(_ctx, 4)");
									setState(59);
									match(5);
									setState(60);
									expression(5);
								}
									break;

								case 6: {
									_localctx = new NavContext(
											new ExpressionContext(_parentctx, _parentState));
									pushNewRecursionContext(_localctx, _startState, RULE_expression);
									setState(61);
									if (!(precpred(_ctx, 11)))
										throw new FailedPredicateException(this, "precpred(_ctx, 11)");
									setState(62);
									navigationSegment();
								}
									break;
							}
						}
					}
					setState(67);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input, 2, _ctx);
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class AddOpContext extends ParserRuleContext {
		public AddOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_addOp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterAddOp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitAddOp(this);
		}
	}

	public final AddOpContext addOp() throws RecognitionException {
		AddOpContext _localctx = new AddOpContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_addOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(68);
				_la = _input.LA(1);
				if (!(_la == 34 || _la == 44)) {
					_errHandler.recoverInline(this);
				}
				consume();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CompOpContext extends ParserRuleContext {
		public CompOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_compOp;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterCompOp(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitCompOp(this);
		}
	}

	public final CompOpContext compOp() throws RecognitionException {
		CompOpContext _localctx = new CompOpContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_compOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(70);
				_la = _input.LA(1);
				if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 6) | (1L << 9) | (1L << 21)
						| (1L << 45) | (1L << 50) | (1L << 54))) != 0))) {
					_errHandler.recoverInline(this);
				}
				consume();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarRefContext extends ParserRuleContext {
		public TerminalNode Ident() {
			return getToken(QueryParser.Ident, 0);
		}

		public VarRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_varRef;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterVarRef(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitVarRef(this);
		}
	}

	public final VarRefContext varRef() throws RecognitionException {
		VarRefContext _localctx = new VarRefContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_varRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(72);
				match(Ident);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NavigationSegmentContext extends ParserRuleContext {
		public NavigationSegmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_navigationSegment;
		}

		public NavigationSegmentContext() {
		}

		public void copyFrom(NavigationSegmentContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class CallServiceContext extends NavigationSegmentContext {
		public CallExpContext callExp() {
			return getRuleContext(CallExpContext.class, 0);
		}

		public CallServiceContext(NavigationSegmentContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterCallService(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitCallService(this);
		}
	}

	public static class ApplyContext extends NavigationSegmentContext {
		public CallExpContext callExp() {
			return getRuleContext(CallExpContext.class, 0);
		}

		public ApplyContext(NavigationSegmentContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterApply(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitApply(this);
		}
	}

	public static class FeatureContext extends NavigationSegmentContext {
		public TerminalNode Ident() {
			return getToken(QueryParser.Ident, 0);
		}

		public FeatureContext(NavigationSegmentContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterFeature(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitFeature(this);
		}
	}

	public final NavigationSegmentContext navigationSegment() throws RecognitionException {
		NavigationSegmentContext _localctx = new NavigationSegmentContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_navigationSegment);
		try {
			setState(80);
			switch (getInterpreter().adaptivePredict(_input, 3, _ctx)) {
				case 1:
					_localctx = new FeatureContext(_localctx);
					enterOuterAlt(_localctx, 1);
					{
						setState(74);
						match(43);
						setState(75);
						match(Ident);
					}
					break;

				case 2:
					_localctx = new ApplyContext(_localctx);
					enterOuterAlt(_localctx, 2);
					{
						setState(76);
						match(43);
						setState(77);
						callExp();
					}
					break;

				case 3:
					_localctx = new CallServiceContext(_localctx);
					enterOuterAlt(_localctx, 3);
					{
						setState(78);
						match(15);
						setState(79);
						callExp();
					}
					break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CallExpContext extends ParserRuleContext {
		public CallExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_callExp;
		}

		public CallExpContext() {
		}

		public void copyFrom(CallExpContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class PrecSiblingsContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class, 0);
		}

		public PrecSiblingsContext(CallExpContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterPrecSiblings(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitPrecSiblings(this);
		}
	}

	public static class AsContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class, 0);
		}

		public AsContext(CallExpContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterAs(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitAs(this);
		}
	}

	public static class EContainerContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class, 0);
		}

		public EContainerContext(CallExpContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterEContainer(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitEContainer(this);
		}
	}

	public static class IsContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class, 0);
		}

		public IsContext(CallExpContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterIs(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitIs(this);
		}
	}

	public static class EInverseContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class, 0);
		}

		public EInverseContext(CallExpContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterEInverse(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitEInverse(this);
		}
	}

	public static class IsKindContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class, 0);
		}

		public IsKindContext(CallExpContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterIsKind(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitIsKind(this);
		}
	}

	public static class IterationCallContext extends CallExpContext {
		public LambdaExpressionContext lambdaExpression() {
			return getRuleContext(LambdaExpressionContext.class, 0);
		}

		public VariableDefinitionContext variableDefinition() {
			return getRuleContext(VariableDefinitionContext.class, 0);
		}

		public CollectionIteratorContext collectionIterator() {
			return getRuleContext(CollectionIteratorContext.class, 0);
		}

		public IterationCallContext(CallExpContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterIterationCall(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitIterationCall(this);
		}
	}

	public static class FilterContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class, 0);
		}

		public FilterContext(CallExpContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterFilter(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitFilter(this);
		}
	}

	public static class EContentContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class, 0);
		}

		public EContentContext(CallExpContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterEContent(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitEContent(this);
		}
	}

	public static class ServiceCallContext extends CallExpContext {
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class, 0);
		}

		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class, 0);
		}

		public ServiceCallContext(CallExpContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterServiceCall(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitServiceCall(this);
		}
	}

	public static class IsTypeContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class, 0);
		}

		public IsTypeContext(CallExpContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterIsType(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitIsType(this);
		}
	}

	public static class SiblingsContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class, 0);
		}

		public SiblingsContext(CallExpContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterSiblings(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitSiblings(this);
		}
	}

	public static class AsTypeContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class, 0);
		}

		public AsTypeContext(CallExpContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterAsType(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitAsType(this);
		}
	}

	public static class EAContentContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class, 0);
		}

		public EAContentContext(CallExpContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterEAContent(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitEAContent(this);
		}
	}

	public final CallExpContext callExp() throws RecognitionException {
		CallExpContext _localctx = new CallExpContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_callExp);
		int _la;
		try {
			setState(147);
			switch (_input.LA(1)) {
				case 46:
					_localctx = new FilterContext(_localctx);
					enterOuterAlt(_localctx, 1);
					{
						setState(82);
						match(46);
						setState(83);
						typeLiteral();
						setState(84);
						match(17);
					}
					break;
				case 22:
					_localctx = new AsTypeContext(_localctx);
					enterOuterAlt(_localctx, 2);
					{
						setState(86);
						match(22);
						setState(87);
						typeLiteral();
						setState(88);
						match(17);
					}
					break;
				case 52:
					_localctx = new AsContext(_localctx);
					enterOuterAlt(_localctx, 3);
					{
						setState(90);
						match(52);
						setState(91);
						typeLiteral();
						setState(92);
						match(17);
					}
					break;
				case 10:
					_localctx = new IsKindContext(_localctx);
					enterOuterAlt(_localctx, 4);
					{
						setState(94);
						match(10);
						setState(95);
						typeLiteral();
						setState(96);
						match(17);
					}
					break;
				case 30:
					_localctx = new IsTypeContext(_localctx);
					enterOuterAlt(_localctx, 5);
					{
						setState(98);
						match(30);
						setState(99);
						typeLiteral();
						setState(100);
						match(17);
					}
					break;
				case 20:
					_localctx = new IsContext(_localctx);
					enterOuterAlt(_localctx, 6);
					{
						setState(102);
						match(20);
						setState(103);
						typeLiteral();
						setState(104);
						match(17);
					}
					break;
				case 13:
					_localctx = new PrecSiblingsContext(_localctx);
					enterOuterAlt(_localctx, 7);
					{
						setState(106);
						match(13);
						setState(108);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 23) | (1L << 47)
								| (1L << 48) | (1L << 51) | (1L << 53) | (1L << Ident))) != 0)) {
							{
								setState(107);
								typeLiteral();
							}
						}

						setState(110);
						match(17);
					}
					break;
				case 8:
					_localctx = new SiblingsContext(_localctx);
					enterOuterAlt(_localctx, 8);
					{
						setState(111);
						match(8);
						setState(113);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 23) | (1L << 47)
								| (1L << 48) | (1L << 51) | (1L << 53) | (1L << Ident))) != 0)) {
							{
								setState(112);
								typeLiteral();
							}
						}

						setState(115);
						match(17);
					}
					break;
				case 31:
					_localctx = new EContentContext(_localctx);
					enterOuterAlt(_localctx, 9);
					{
						setState(116);
						match(31);
						setState(118);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 23) | (1L << 47)
								| (1L << 48) | (1L << 51) | (1L << 53) | (1L << Ident))) != 0)) {
							{
								setState(117);
								typeLiteral();
							}
						}

						setState(120);
						match(17);
					}
					break;
				case 27:
					_localctx = new EAContentContext(_localctx);
					enterOuterAlt(_localctx, 10);
					{
						setState(121);
						match(27);
						setState(123);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 23) | (1L << 47)
								| (1L << 48) | (1L << 51) | (1L << 53) | (1L << Ident))) != 0)) {
							{
								setState(122);
								typeLiteral();
							}
						}

						setState(125);
						match(17);
					}
					break;
				case 38:
					_localctx = new EContainerContext(_localctx);
					enterOuterAlt(_localctx, 11);
					{
						setState(126);
						match(38);
						setState(128);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 23) | (1L << 47)
								| (1L << 48) | (1L << 51) | (1L << 53) | (1L << Ident))) != 0)) {
							{
								setState(127);
								typeLiteral();
							}
						}

						setState(130);
						match(17);
					}
					break;
				case 41:
					_localctx = new EInverseContext(_localctx);
					enterOuterAlt(_localctx, 12);
					{
						setState(131);
						match(41);
						setState(133);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 23) | (1L << 47)
								| (1L << 48) | (1L << 51) | (1L << 53) | (1L << Ident))) != 0)) {
							{
								setState(132);
								typeLiteral();
							}
						}

						setState(135);
						match(17);
					}
					break;
				case 11:
				case 14:
				case 16:
				case 18:
				case 24:
				case 25:
				case 32:
				case 49:
					_localctx = new IterationCallContext(_localctx);
					enterOuterAlt(_localctx, 13);
					{
						setState(136);
						collectionIterator();
						setState(137);
						match(36);
						setState(138);
						variableDefinition();
						setState(139);
						lambdaExpression();
						setState(140);
						match(17);
					}
					break;
				case Ident:
					_localctx = new ServiceCallContext(_localctx);
					enterOuterAlt(_localctx, 14);
					{
						setState(142);
						qualifiedName();
						setState(143);
						match(36);
						setState(144);
						expressionSequence();
						setState(145);
						match(17);
					}
					break;
				default:
					throw new NoViableAltException(this);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LambdaExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class, 0);
		}

		public LambdaExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_lambdaExpression;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterLambdaExpression(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitLambdaExpression(this);
		}
	}

	public final LambdaExpressionContext lambdaExpression() throws RecognitionException {
		LambdaExpressionContext _localctx = new LambdaExpressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_lambdaExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(149);
				expression(0);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CollectionIteratorContext extends ParserRuleContext {
		public CollectionIteratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_collectionIterator;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterCollectionIterator(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitCollectionIterator(this);
		}
	}

	public final CollectionIteratorContext collectionIterator() throws RecognitionException {
		CollectionIteratorContext _localctx = new CollectionIteratorContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_collectionIterator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(151);
				_la = _input.LA(1);
				if (!((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 11) | (1L << 14) | (1L << 16)
						| (1L << 18) | (1L << 24) | (1L << 25) | (1L << 32) | (1L << 49))) != 0))) {
					_errHandler.recoverInline(this);
				}
				consume();
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionSequenceContext extends ParserRuleContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class, i);
		}

		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}

		public ExpressionSequenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_expressionSequence;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterExpressionSequence(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitExpressionSequence(this);
		}
	}

	public final ExpressionSequenceContext expressionSequence() throws RecognitionException {
		ExpressionSequenceContext _localctx = new ExpressionSequenceContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_expressionSequence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(161);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 1) | (1L << 2) | (1L << 4) | (1L << 7)
						| (1L << 26) | (1L << 34) | (1L << 36) | (1L << 37) | (1L << 39) | (1L << 42)
						| (1L << Integer) | (1L << Real) | (1L << String) | (1L << Ident))) != 0)) {
					{
						setState(153);
						expression(0);
						setState(158);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la == 33) {
							{
								{
									setState(154);
									match(33);
									setState(155);
									expression(0);
								}
							}
							setState(160);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
					}
				}

			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDefinitionContext extends ParserRuleContext {
		public TerminalNode Ident() {
			return getToken(QueryParser.Ident, 0);
		}

		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class, 0);
		}

		public VariableDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_variableDefinition;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterVariableDefinition(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitVariableDefinition(this);
		}
	}

	public final VariableDefinitionContext variableDefinition() throws RecognitionException {
		VariableDefinitionContext _localctx = new VariableDefinitionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_variableDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(163);
				match(Ident);
				setState(166);
				_la = _input.LA(1);
				if (_la == 35) {
					{
						setState(164);
						match(35);
						setState(165);
						typeLiteral();
					}
				}

				setState(168);
				match(28);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_literal;
		}

		public LiteralContext() {
		}

		public void copyFrom(LiteralContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class RealLitContext extends LiteralContext {
		public TerminalNode Real() {
			return getToken(QueryParser.Real, 0);
		}

		public RealLitContext(LiteralContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterRealLit(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitRealLit(this);
		}
	}

	public static class StringLitContext extends LiteralContext {
		public TerminalNode String() {
			return getToken(QueryParser.String, 0);
		}

		public StringLitContext(LiteralContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterStringLit(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitStringLit(this);
		}
	}

	public static class NullLitContext extends LiteralContext {
		public NullLitContext(LiteralContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterNullLit(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitNullLit(this);
		}
	}

	public static class ExplicitSeqLitContext extends LiteralContext {
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class, 0);
		}

		public ExplicitSeqLitContext(LiteralContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterExplicitSeqLit(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitExplicitSeqLit(this);
		}
	}

	public static class EnumOrClassifierLitContext extends LiteralContext {
		public TerminalNode Ident() {
			return getToken(QueryParser.Ident, 0);
		}

		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class, 0);
		}

		public EnumOrClassifierLitContext(LiteralContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterEnumOrClassifierLit(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitEnumOrClassifierLit(this);
		}
	}

	public static class ExplicitSetLitContext extends LiteralContext {
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class, 0);
		}

		public ExplicitSetLitContext(LiteralContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterExplicitSetLit(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitExplicitSetLit(this);
		}
	}

	public static class TrueLitContext extends LiteralContext {
		public TrueLitContext(LiteralContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterTrueLit(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitTrueLit(this);
		}
	}

	public static class SeqLitContext extends LiteralContext {
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class, 0);
		}

		public SeqLitContext(LiteralContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterSeqLit(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitSeqLit(this);
		}
	}

	public static class FalseLitContext extends LiteralContext {
		public FalseLitContext(LiteralContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterFalseLit(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitFalseLit(this);
		}
	}

	public static class SetLitContext extends LiteralContext {
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class, 0);
		}

		public SetLitContext(LiteralContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterSetLit(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitSetLit(this);
		}
	}

	public static class IntegerLitContext extends LiteralContext {
		public TerminalNode Integer() {
			return getToken(QueryParser.Integer, 0);
		}

		public IntegerLitContext(LiteralContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterIntegerLit(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitIntegerLit(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_literal);
		try {
			setState(196);
			switch (_input.LA(1)) {
				case String:
					_localctx = new StringLitContext(_localctx);
					enterOuterAlt(_localctx, 1);
					{
						setState(170);
						match(String);
					}
					break;
				case Integer:
					_localctx = new IntegerLitContext(_localctx);
					enterOuterAlt(_localctx, 2);
					{
						setState(171);
						match(Integer);
					}
					break;
				case Real:
					_localctx = new RealLitContext(_localctx);
					enterOuterAlt(_localctx, 3);
					{
						setState(172);
						match(Real);
					}
					break;
				case 42:
					_localctx = new TrueLitContext(_localctx);
					enterOuterAlt(_localctx, 4);
					{
						setState(173);
						match(42);
					}
					break;
				case 7:
					_localctx = new FalseLitContext(_localctx);
					enterOuterAlt(_localctx, 5);
					{
						setState(174);
						match(7);
					}
					break;
				case 26:
					_localctx = new NullLitContext(_localctx);
					enterOuterAlt(_localctx, 6);
					{
						setState(175);
						match(26);
					}
					break;
				case 39:
					_localctx = new SetLitContext(_localctx);
					enterOuterAlt(_localctx, 7);
					{
						setState(176);
						match(39);
						setState(177);
						expressionSequence();
						setState(178);
						match(12);
					}
					break;
				case 4:
					_localctx = new SeqLitContext(_localctx);
					enterOuterAlt(_localctx, 8);
					{
						setState(180);
						match(4);
						setState(181);
						expressionSequence();
						setState(182);
						match(29);
					}
					break;
				case 1:
					_localctx = new ExplicitSeqLitContext(_localctx);
					enterOuterAlt(_localctx, 9);
					{
						setState(184);
						match(1);
						setState(185);
						expressionSequence();
						setState(186);
						match(12);
					}
					break;
				case 2:
					_localctx = new ExplicitSetLitContext(_localctx);
					enterOuterAlt(_localctx, 10);
					{
						setState(188);
						match(2);
						setState(189);
						expressionSequence();
						setState(190);
						match(12);
					}
					break;
				case Ident:
					_localctx = new EnumOrClassifierLitContext(_localctx);
					enterOuterAlt(_localctx, 11);
					{
						setState(192);
						qualifiedName();
						setState(193);
						match(19);
						setState(194);
						match(Ident);
					}
					break;
				default:
					throw new NoViableAltException(this);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeLiteralContext extends ParserRuleContext {
		public TypeLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_typeLiteral;
		}

		public TypeLiteralContext() {
		}

		public void copyFrom(TypeLiteralContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class StrTypeContext extends TypeLiteralContext {
		public StrTypeContext(TypeLiteralContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterStrType(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitStrType(this);
		}
	}

	public static class ModelObjectTypeContext extends TypeLiteralContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class, 0);
		}

		public ModelObjectTypeContext(TypeLiteralContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterModelObjectType(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitModelObjectType(this);
		}
	}

	public static class RealTypeContext extends TypeLiteralContext {
		public RealTypeContext(TypeLiteralContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterRealType(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitRealType(this);
		}
	}

	public static class IntTypeContext extends TypeLiteralContext {
		public IntTypeContext(TypeLiteralContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterIntType(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitIntType(this);
		}
	}

	public static class SeqTypeContext extends TypeLiteralContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class, 0);
		}

		public SeqTypeContext(TypeLiteralContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterSeqType(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitSeqType(this);
		}
	}

	public static class BooleanTypeContext extends TypeLiteralContext {
		public BooleanTypeContext(TypeLiteralContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterBooleanType(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitBooleanType(this);
		}
	}

	public static class SetTypeContext extends TypeLiteralContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class, 0);
		}

		public SetTypeContext(TypeLiteralContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterSetType(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitSetType(this);
		}
	}

	public final TypeLiteralContext typeLiteral() throws RecognitionException {
		TypeLiteralContext _localctx = new TypeLiteralContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_typeLiteral);
		try {
			setState(211);
			switch (_input.LA(1)) {
				case 23:
					_localctx = new StrTypeContext(_localctx);
					enterOuterAlt(_localctx, 1);
					{
						setState(198);
						match(23);
					}
					break;
				case 48:
					_localctx = new IntTypeContext(_localctx);
					enterOuterAlt(_localctx, 2);
					{
						setState(199);
						match(48);
					}
					break;
				case 51:
					_localctx = new RealTypeContext(_localctx);
					enterOuterAlt(_localctx, 3);
					{
						setState(200);
						match(51);
					}
					break;
				case 47:
					_localctx = new BooleanTypeContext(_localctx);
					enterOuterAlt(_localctx, 4);
					{
						setState(201);
						match(47);
					}
					break;
				case 3:
					_localctx = new SeqTypeContext(_localctx);
					enterOuterAlt(_localctx, 5);
					{
						setState(202);
						match(3);
						setState(203);
						typeLiteral();
						setState(204);
						match(17);
					}
					break;
				case 53:
					_localctx = new SetTypeContext(_localctx);
					enterOuterAlt(_localctx, 6);
					{
						setState(206);
						match(53);
						setState(207);
						typeLiteral();
						setState(208);
						match(17);
					}
					break;
				case Ident:
					_localctx = new ModelObjectTypeContext(_localctx);
					enterOuterAlt(_localctx, 7);
					{
						setState(210);
						qualifiedName();
					}
					break;
				default:
					throw new NoViableAltException(this);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QualifiedNameContext extends ParserRuleContext {
		public List<TerminalNode> Ident() {
			return getTokens(QueryParser.Ident);
		}

		public TerminalNode Ident(int i) {
			return getToken(QueryParser.Ident, i);
		}

		public QualifiedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_qualifiedName;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).enterQualifiedName(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof QueryListener)
				((QueryListener)listener).exitQualifiedName(this);
		}
	}

	public final QualifiedNameContext qualifiedName() throws RecognitionException {
		QualifiedNameContext _localctx = new QualifiedNameContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_qualifiedName);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(213);
				match(Ident);
				setState(216);
				switch (getInterpreter().adaptivePredict(_input, 16, _ctx)) {
					case 1: {
						setState(214);
						match(19);
						setState(215);
						match(Ident);
					}
						break;
				}
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
			case 1:
				return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}

	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
			case 0:
				return precpred(_ctx, 8);

			case 1:
				return precpred(_ctx, 7);

			case 2:
				return precpred(_ctx, 6);

			case 3:
				return precpred(_ctx, 5);

			case 4:
				return precpred(_ctx, 4);

			case 5:
				return precpred(_ctx, 11);
		}
		return true;
	}

	public static final String _serializedATN = "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3>\u00dd\4\2\t\2\4"
			+ "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"
			+ "\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3"
			+ "\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3-\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"
			+ "\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3B\n\3\f\3\16\3E\13\3\3\4"
			+ "\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\5\7S\n\7\3\b\3\b\3\b\3\b"
			+ "\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"
			+ "\b\3\b\3\b\3\b\3\b\5\bo\n\b\3\b\3\b\3\b\5\bt\n\b\3\b\3\b\3\b\5\by\n\b"
			+ "\3\b\3\b\3\b\5\b~\n\b\3\b\3\b\3\b\5\b\u0083\n\b\3\b\3\b\3\b\5\b\u0088"
			+ "\n\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\u0096\n\b\3\t"
			+ "\3\t\3\n\3\n\3\13\3\13\3\13\7\13\u009f\n\13\f\13\16\13\u00a2\13\13\5\13"
			+ "\u00a4\n\13\3\f\3\f\3\f\5\f\u00a9\n\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r"
			+ "\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"
			+ "\r\3\r\3\r\5\r\u00c7\n\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"
			+ "\3\16\3\16\3\16\3\16\5\16\u00d6\n\16\3\17\3\17\3\17\5\17\u00db\n\17\3"
			+ "\17\2\3\4\20\2\4\6\b\n\f\16\20\22\24\26\30\32\34\2\5\4\2$$..\b\2\b\b\13"
			+ "\13\27\27//\64\6488\t\2\r\r\20\20\22\22\24\24\32\33\"\"\63\63\u0101\2"
			+ "\36\3\2\2\2\4,\3\2\2\2\6F\3\2\2\2\bH\3\2\2\2\nJ\3\2\2\2\fR\3\2\2\2\16"
			+ "\u0095\3\2\2\2\20\u0097\3\2\2\2\22\u0099\3\2\2\2\24\u00a3\3\2\2\2\26\u00a5"
			+ "\3\2\2\2\30\u00c6\3\2\2\2\32\u00d5\3\2\2\2\34\u00d7\3\2\2\2\36\37\5\4"
			+ "\3\2\37 \7\2\2\3 \3\3\2\2\2!\"\b\3\1\2\"#\7\'\2\2#-\5\4\3\f$%\7$\2\2%"
			+ "-\5\4\3\13&-\5\n\6\2\'-\5\30\r\2()\7&\2\2)*\5\4\3\2*+\7\23\2\2+-\3\2\2"
			+ "\2,!\3\2\2\2,$\3\2\2\2,&\3\2\2\2,\'\3\2\2\2,(\3\2\2\2-C\3\2\2\2./\f\n"
			+ "\2\2/\60\7:\2\2\60B\5\4\3\13\61\62\f\t\2\2\62\63\5\6\4\2\63\64\5\4\3\n"
			+ "\64B\3\2\2\2\65\66\f\b\2\2\66\67\5\b\5\2\678\5\4\3\t8B\3\2\2\29:\f\7\2"
			+ "\2:;\7*\2\2;B\5\4\3\b<=\f\6\2\2=>\7\7\2\2>B\5\4\3\7?@\f\r\2\2@B\5\f\7"
			+ "\2A.\3\2\2\2A\61\3\2\2\2A\65\3\2\2\2A9\3\2\2\2A<\3\2\2\2A?\3\2\2\2BE\3"
			+ "\2\2\2CA\3\2\2\2CD\3\2\2\2D\5\3\2\2\2EC\3\2\2\2FG\t\2\2\2G\7\3\2\2\2H"
			+ "I\t\3\2\2I\t\3\2\2\2JK\7>\2\2K\13\3\2\2\2LM\7-\2\2MS\7>\2\2NO\7-\2\2O"
			+ "S\5\16\b\2PQ\7\21\2\2QS\5\16\b\2RL\3\2\2\2RN\3\2\2\2RP\3\2\2\2S\r\3\2"
			+ "\2\2TU\7\60\2\2UV\5\32\16\2VW\7\23\2\2W\u0096\3\2\2\2XY\7\30\2\2YZ\5\32"
			+ "\16\2Z[\7\23\2\2[\u0096\3\2\2\2\\]\7\66\2\2]^\5\32\16\2^_\7\23\2\2_\u0096"
			+ "\3\2\2\2`a\7\f\2\2ab\5\32\16\2bc\7\23\2\2c\u0096\3\2\2\2de\7 \2\2ef\5"
			+ "\32\16\2fg\7\23\2\2g\u0096\3\2\2\2hi\7\26\2\2ij\5\32\16\2jk\7\23\2\2k"
			+ "\u0096\3\2\2\2ln\7\17\2\2mo\5\32\16\2nm\3\2\2\2no\3\2\2\2op\3\2\2\2p\u0096"
			+ "\7\23\2\2qs\7\n\2\2rt\5\32\16\2sr\3\2\2\2st\3\2\2\2tu\3\2\2\2u\u0096\7"
			+ "\23\2\2vx\7!\2\2wy\5\32\16\2xw\3\2\2\2xy\3\2\2\2yz\3\2\2\2z\u0096\7\23"
			+ "\2\2{}\7\35\2\2|~\5\32\16\2}|\3\2\2\2}~\3\2\2\2~\177\3\2\2\2\177\u0096"
			+ "\7\23\2\2\u0080\u0082\7(\2\2\u0081\u0083\5\32\16\2\u0082\u0081\3\2\2\2"
			+ "\u0082\u0083\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0096\7\23\2\2\u0085\u0087"
			+ "\7+\2\2\u0086\u0088\5\32\16\2\u0087\u0086\3\2\2\2\u0087\u0088\3\2\2\2"
			+ "\u0088\u0089\3\2\2\2\u0089\u0096\7\23\2\2\u008a\u008b\5\22\n\2\u008b\u008c"
			+ "\7&\2\2\u008c\u008d\5\26\f\2\u008d\u008e\5\20\t\2\u008e\u008f\7\23\2\2"
			+ "\u008f\u0096\3\2\2\2\u0090\u0091\5\34\17\2\u0091\u0092\7&\2\2\u0092\u0093"
			+ "\5\24\13\2\u0093\u0094\7\23\2\2\u0094\u0096\3\2\2\2\u0095T\3\2\2\2\u0095"
			+ "X\3\2\2\2\u0095\\\3\2\2\2\u0095`\3\2\2\2\u0095d\3\2\2\2\u0095h\3\2\2\2"
			+ "\u0095l\3\2\2\2\u0095q\3\2\2\2\u0095v\3\2\2\2\u0095{\3\2\2\2\u0095\u0080"
			+ "\3\2\2\2\u0095\u0085\3\2\2\2\u0095\u008a\3\2\2\2\u0095\u0090\3\2\2\2\u0096"
			+ "\17\3\2\2\2\u0097\u0098\5\4\3\2\u0098\21\3\2\2\2\u0099\u009a\t\4\2\2\u009a"
			+ "\23\3\2\2\2\u009b\u00a0\5\4\3\2\u009c\u009d\7#\2\2\u009d\u009f\5\4\3\2"
			+ "\u009e\u009c\3\2\2\2\u009f\u00a2\3\2\2\2\u00a0\u009e\3\2\2\2\u00a0\u00a1"
			+ "\3\2\2\2\u00a1\u00a4\3\2\2\2\u00a2\u00a0\3\2\2\2\u00a3\u009b\3\2\2\2\u00a3"
			+ "\u00a4\3\2\2\2\u00a4\25\3\2\2\2\u00a5\u00a8\7>\2\2\u00a6\u00a7\7%\2\2"
			+ "\u00a7\u00a9\5\32\16\2\u00a8\u00a6\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\u00aa"
			+ "\3\2\2\2\u00aa\u00ab\7\36\2\2\u00ab\27\3\2\2\2\u00ac\u00c7\7=\2\2\u00ad"
			+ "\u00c7\7;\2\2\u00ae\u00c7\7<\2\2\u00af\u00c7\7,\2\2\u00b0\u00c7\7\t\2"
			+ "\2\u00b1\u00c7\7\34\2\2\u00b2\u00b3\7)\2\2\u00b3\u00b4\5\24\13\2\u00b4"
			+ "\u00b5\7\16\2\2\u00b5\u00c7\3\2\2\2\u00b6\u00b7\7\6\2\2\u00b7\u00b8\5"
			+ "\24\13\2\u00b8\u00b9\7\37\2\2\u00b9\u00c7\3\2\2\2\u00ba\u00bb\7\3\2\2"
			+ "\u00bb\u00bc\5\24\13\2\u00bc\u00bd\7\16\2\2\u00bd\u00c7\3\2\2\2\u00be"
			+ "\u00bf\7\4\2\2\u00bf\u00c0\5\24\13\2\u00c0\u00c1\7\16\2\2\u00c1\u00c7"
			+ "\3\2\2\2\u00c2\u00c3\5\34\17\2\u00c3\u00c4\7\25\2\2\u00c4\u00c5\7>\2\2"
			+ "\u00c5\u00c7\3\2\2\2\u00c6\u00ac\3\2\2\2\u00c6\u00ad\3\2\2\2\u00c6\u00ae"
			+ "\3\2\2\2\u00c6\u00af\3\2\2\2\u00c6\u00b0\3\2\2\2\u00c6\u00b1\3\2\2\2\u00c6"
			+ "\u00b2\3\2\2\2\u00c6\u00b6\3\2\2\2\u00c6\u00ba\3\2\2\2\u00c6\u00be\3\2"
			+ "\2\2\u00c6\u00c2\3\2\2\2\u00c7\31\3\2\2\2\u00c8\u00d6\7\31\2\2\u00c9\u00d6"
			+ "\7\62\2\2\u00ca\u00d6\7\65\2\2\u00cb\u00d6\7\61\2\2\u00cc\u00cd\7\5\2"
			+ "\2\u00cd\u00ce\5\32\16\2\u00ce\u00cf\7\23\2\2\u00cf\u00d6\3\2\2\2\u00d0"
			+ "\u00d1\7\67\2\2\u00d1\u00d2\5\32\16\2\u00d2\u00d3\7\23\2\2\u00d3\u00d6"
			+ "\3\2\2\2\u00d4\u00d6\5\34\17\2\u00d5\u00c8\3\2\2\2\u00d5\u00c9\3\2\2\2"
			+ "\u00d5\u00ca\3\2\2\2\u00d5\u00cb\3\2\2\2\u00d5\u00cc\3\2\2\2\u00d5\u00d0"
			+ "\3\2\2\2\u00d5\u00d4\3\2\2\2\u00d6\33\3\2\2\2\u00d7\u00da\7>\2\2\u00d8"
			+ "\u00d9\7\25\2\2\u00d9\u00db\7>\2\2\u00da\u00d8\3\2\2\2\u00da\u00db\3\2"
			+ "\2\2\u00db\35\3\2\2\2\23,ACRnsx}\u0082\u0087\u0095\u00a0\u00a3\u00a8\u00c6" + "\u00d5\u00da";

	public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
