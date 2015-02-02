// Generated from Query.g4 by ANTLR 4.3

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
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class QueryParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__54=1, T__53=2, T__52=3, T__51=4, T__50=5, T__49=6, T__48=7, T__47=8, 
		T__46=9, T__45=10, T__44=11, T__43=12, T__42=13, T__41=14, T__40=15, T__39=16, 
		T__38=17, T__37=18, T__36=19, T__35=20, T__34=21, T__33=22, T__32=23, 
		T__31=24, T__30=25, T__29=26, T__28=27, T__27=28, T__26=29, T__25=30, 
		T__24=31, T__23=32, T__22=33, T__21=34, T__20=35, T__19=36, T__18=37, 
		T__17=38, T__16=39, T__15=40, T__14=41, T__13=42, T__12=43, T__11=44, 
		T__10=45, T__9=46, T__8=47, T__7=48, T__6=49, T__5=50, T__4=51, T__3=52, 
		T__2=53, T__1=54, T__0=55, WS=56, MultOp=57, Integer=58, Real=59, String=60, 
		Ident=61;
	public static final String[] tokenNames = {
		"<INVALID>", "'Sequence{'", "'OrderedSet{'", "'implies'", "'Sequence('", 
		"'['", "'or'", "'<'", "'false'", "'siblings('", "'<='", "'oclIsKindOf('", 
		"'reject'", "'}'", "'precedingSiblings('", "'any'", "'->'", "'isUnique'", 
		"')'", "'collect'", "'::'", "'is('", "'='", "'oclAsType('", "'String'", 
		"'one'", "'forAll'", "'null'", "'eAllContents('", "'|'", "']'", "'eContents('", 
		"'oclIsTypeOf('", "'select'", "','", "'-'", "':'", "'('", "'not'", "'eContainer('", 
		"'{'", "'and'", "'eInverse('", "'true'", "'.'", "'+'", "'<>'", "'filter('", 
		"'Boolean'", "'Integer'", "'exists'", "'>'", "'Real'", "'as('", "'OrderedSet('", 
		"'>='", "WS", "MultOp", "Integer", "Real", "String", "Ident"
	};
	public static final int
		RULE_entry = 0, RULE_expression = 1, RULE_addOp = 2, RULE_compOp = 3, 
		RULE_varRef = 4, RULE_navigationSegment = 5, RULE_callExp = 6, RULE_lambdaExpression = 7, 
		RULE_collectionIterator = 8, RULE_expressionSequence = 9, RULE_variableDefinition = 10, 
		RULE_literal = 11, RULE_typeLiteral = 12, RULE_qualifiedName = 13;
	public static final String[] ruleNames = {
		"entry", "expression", "addOp", "compOp", "varRef", "navigationSegment", 
		"callExp", "lambdaExpression", "collectionIterator", "expressionSequence", 
		"variableDefinition", "literal", "typeLiteral", "qualifiedName"
	};

	@Override
	public String getGrammarFileName() { return "Query.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public QueryParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class EntryContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(QueryParser.EOF, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public EntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitEntry(this);
		}
	}

	public final EntryContext entry() throws RecognitionException {
		EntryContext _localctx = new EntryContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_entry);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28); expression(0);
			setState(29); match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class MultContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public TerminalNode MultOp() { return getToken(QueryParser.MultOp, 0); }
		public MultContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterMult(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitMult(this);
		}
	}
	public static class NavContext extends ExpressionContext {
		public NavigationSegmentContext navigationSegment() {
			return getRuleContext(NavigationSegmentContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NavContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterNav(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitNav(this);
		}
	}
	public static class ImpliesContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ImpliesContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterImplies(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitImplies(this);
		}
	}
	public static class OrContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public OrContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitOr(this);
		}
	}
	public static class LitContext extends ExpressionContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public LitContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitLit(this);
		}
	}
	public static class AddContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public AddOpContext addOp() {
			return getRuleContext(AddOpContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public AddContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterAdd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitAdd(this);
		}
	}
	public static class VarContext extends ExpressionContext {
		public VarRefContext varRef() {
			return getRuleContext(VarRefContext.class,0);
		}
		public VarContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitVar(this);
		}
	}
	public static class ParenContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ParenContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterParen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitParen(this);
		}
	}
	public static class AndContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public AndContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitAnd(this);
		}
	}
	public static class MinContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public MinContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterMin(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitMin(this);
		}
	}
	public static class CompContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public CompOpContext compOp() {
			return getRuleContext(CompOpContext.class,0);
		}
		public CompContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterComp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitComp(this);
		}
	}
	public static class NotContext extends ExpressionContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NotContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterNot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitNot(this);
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
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				_localctx = new NotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(32); match(T__17);
				setState(33); expression(11);
				}
				break;

			case 2:
				{
				_localctx = new MinContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(34); match(T__20);
				setState(35); expression(10);
				}
				break;

			case 3:
				{
				_localctx = new VarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(36); varRef();
				}
				break;

			case 4:
				{
				_localctx = new LitContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(37); literal();
				}
				break;

			case 5:
				{
				_localctx = new ParenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(38); match(T__18);
				setState(39); expression(0);
				setState(40); match(T__37);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(68);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(66);
					switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
					case 1:
						{
						_localctx = new MultContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(44);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(45); match(MultOp);
						setState(46); expression(10);
						}
						break;

					case 2:
						{
						_localctx = new AddContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(47);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(48); addOp();
						setState(49); expression(9);
						}
						break;

					case 3:
						{
						_localctx = new CompContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(51);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(52); compOp();
						setState(53); expression(8);
						}
						break;

					case 4:
						{
						_localctx = new AndContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(55);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(56); match(T__14);
						setState(57); expression(7);
						}
						break;

					case 5:
						{
						_localctx = new OrContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(58);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(59); match(T__49);
						setState(60); expression(6);
						}
						break;

					case 6:
						{
						_localctx = new ImpliesContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(61);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(62); match(T__52);
						setState(63); expression(5);
						}
						break;

					case 7:
						{
						_localctx = new NavContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(64);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(65); navigationSegment();
						}
						break;
					}
					} 
				}
				setState(70);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class AddOpContext extends ParserRuleContext {
		public AddOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_addOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterAddOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitAddOp(this);
		}
	}

	public final AddOpContext addOp() throws RecognitionException {
		AddOpContext _localctx = new AddOpContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_addOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			_la = _input.LA(1);
			if ( !(_la==T__20 || _la==T__10) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CompOpContext extends ParserRuleContext {
		public CompOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterCompOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitCompOp(this);
		}
	}

	public final CompOpContext compOp() throws RecognitionException {
		CompOpContext _localctx = new CompOpContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_compOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__48) | (1L << T__45) | (1L << T__33) | (1L << T__9) | (1L << T__4) | (1L << T__0))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarRefContext extends ParserRuleContext {
		public TerminalNode Ident() { return getToken(QueryParser.Ident, 0); }
		public VarRefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varRef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterVarRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitVarRef(this);
		}
	}

	public final VarRefContext varRef() throws RecognitionException {
		VarRefContext _localctx = new VarRefContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_varRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75); match(Ident);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NavigationSegmentContext extends ParserRuleContext {
		public NavigationSegmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_navigationSegment; }
	 
		public NavigationSegmentContext() { }
		public void copyFrom(NavigationSegmentContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class CallServiceContext extends NavigationSegmentContext {
		public CallExpContext callExp() {
			return getRuleContext(CallExpContext.class,0);
		}
		public CallServiceContext(NavigationSegmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterCallService(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitCallService(this);
		}
	}
	public static class ApplyContext extends NavigationSegmentContext {
		public CallExpContext callExp() {
			return getRuleContext(CallExpContext.class,0);
		}
		public ApplyContext(NavigationSegmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterApply(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitApply(this);
		}
	}
	public static class FeatureContext extends NavigationSegmentContext {
		public TerminalNode Ident() { return getToken(QueryParser.Ident, 0); }
		public FeatureContext(NavigationSegmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterFeature(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitFeature(this);
		}
	}

	public final NavigationSegmentContext navigationSegment() throws RecognitionException {
		NavigationSegmentContext _localctx = new NavigationSegmentContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_navigationSegment);
		try {
			setState(83);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				_localctx = new FeatureContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(77); match(T__11);
				setState(78); match(Ident);
				}
				break;

			case 2:
				_localctx = new ApplyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(79); match(T__11);
				setState(80); callExp();
				}
				break;

			case 3:
				_localctx = new CallServiceContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(81); match(T__39);
				setState(82); callExp();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CallExpContext extends ParserRuleContext {
		public CallExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_callExp; }
	 
		public CallExpContext() { }
		public void copyFrom(CallExpContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PrecSiblingsContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class,0);
		}
		public PrecSiblingsContext(CallExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterPrecSiblings(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitPrecSiblings(this);
		}
	}
	public static class AsContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class,0);
		}
		public AsContext(CallExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterAs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitAs(this);
		}
	}
	public static class EContainerContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class,0);
		}
		public EContainerContext(CallExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterEContainer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitEContainer(this);
		}
	}
	public static class IsContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class,0);
		}
		public IsContext(CallExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterIs(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitIs(this);
		}
	}
	public static class EInverseContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class,0);
		}
		public EInverseContext(CallExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterEInverse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitEInverse(this);
		}
	}
	public static class IsKindContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class,0);
		}
		public IsKindContext(CallExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterIsKind(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitIsKind(this);
		}
	}
	public static class IterationCallContext extends CallExpContext {
		public LambdaExpressionContext lambdaExpression() {
			return getRuleContext(LambdaExpressionContext.class,0);
		}
		public VariableDefinitionContext variableDefinition() {
			return getRuleContext(VariableDefinitionContext.class,0);
		}
		public CollectionIteratorContext collectionIterator() {
			return getRuleContext(CollectionIteratorContext.class,0);
		}
		public IterationCallContext(CallExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterIterationCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitIterationCall(this);
		}
	}
	public static class FilterContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class,0);
		}
		public FilterContext(CallExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterFilter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitFilter(this);
		}
	}
	public static class EContentContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class,0);
		}
		public EContentContext(CallExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterEContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitEContent(this);
		}
	}
	public static class ServiceCallContext extends CallExpContext {
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public ServiceCallContext(CallExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterServiceCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitServiceCall(this);
		}
	}
	public static class IsTypeContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class,0);
		}
		public IsTypeContext(CallExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterIsType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitIsType(this);
		}
	}
	public static class SiblingsContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class,0);
		}
		public SiblingsContext(CallExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterSiblings(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitSiblings(this);
		}
	}
	public static class AsTypeContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class,0);
		}
		public AsTypeContext(CallExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterAsType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitAsType(this);
		}
	}
	public static class EAContentContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class,0);
		}
		public EAContentContext(CallExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterEAContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitEAContent(this);
		}
	}

	public final CallExpContext callExp() throws RecognitionException {
		CallExpContext _localctx = new CallExpContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_callExp);
		int _la;
		try {
			setState(150);
			switch (_input.LA(1)) {
			case T__8:
				_localctx = new FilterContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(85); match(T__8);
				setState(86); typeLiteral();
				setState(87); match(T__37);
				}
				break;
			case T__32:
				_localctx = new AsTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(89); match(T__32);
				setState(90); typeLiteral();
				setState(91); match(T__37);
				}
				break;
			case T__2:
				_localctx = new AsContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(93); match(T__2);
				setState(94); typeLiteral();
				setState(95); match(T__37);
				}
				break;
			case T__44:
				_localctx = new IsKindContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(97); match(T__44);
				setState(98); typeLiteral();
				setState(99); match(T__37);
				}
				break;
			case T__23:
				_localctx = new IsTypeContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(101); match(T__23);
				setState(102); typeLiteral();
				setState(103); match(T__37);
				}
				break;
			case T__34:
				_localctx = new IsContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(105); match(T__34);
				setState(106); typeLiteral();
				setState(107); match(T__37);
				}
				break;
			case T__41:
				_localctx = new PrecSiblingsContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(109); match(T__41);
				setState(111);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__51) | (1L << T__31) | (1L << T__7) | (1L << T__6) | (1L << T__3) | (1L << T__1) | (1L << Ident))) != 0)) {
					{
					setState(110); typeLiteral();
					}
				}

				setState(113); match(T__37);
				}
				break;
			case T__46:
				_localctx = new SiblingsContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(114); match(T__46);
				setState(116);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__51) | (1L << T__31) | (1L << T__7) | (1L << T__6) | (1L << T__3) | (1L << T__1) | (1L << Ident))) != 0)) {
					{
					setState(115); typeLiteral();
					}
				}

				setState(118); match(T__37);
				}
				break;
			case T__24:
				_localctx = new EContentContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(119); match(T__24);
				setState(121);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__51) | (1L << T__31) | (1L << T__7) | (1L << T__6) | (1L << T__3) | (1L << T__1) | (1L << Ident))) != 0)) {
					{
					setState(120); typeLiteral();
					}
				}

				setState(123); match(T__37);
				}
				break;
			case T__27:
				_localctx = new EAContentContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(124); match(T__27);
				setState(126);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__51) | (1L << T__31) | (1L << T__7) | (1L << T__6) | (1L << T__3) | (1L << T__1) | (1L << Ident))) != 0)) {
					{
					setState(125); typeLiteral();
					}
				}

				setState(128); match(T__37);
				}
				break;
			case T__16:
				_localctx = new EContainerContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(129); match(T__16);
				setState(131);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__51) | (1L << T__31) | (1L << T__7) | (1L << T__6) | (1L << T__3) | (1L << T__1) | (1L << Ident))) != 0)) {
					{
					setState(130); typeLiteral();
					}
				}

				setState(133); match(T__37);
				}
				break;
			case T__13:
				_localctx = new EInverseContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(134); match(T__13);
				setState(136);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__51) | (1L << T__31) | (1L << T__7) | (1L << T__6) | (1L << T__3) | (1L << T__1) | (1L << Ident))) != 0)) {
					{
					setState(135); typeLiteral();
					}
				}

				setState(138); match(T__37);
				}
				break;
			case T__43:
			case T__40:
			case T__38:
			case T__36:
			case T__30:
			case T__29:
			case T__22:
			case T__5:
				_localctx = new IterationCallContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(139); collectionIterator();
				setState(140); match(T__18);
				setState(141); variableDefinition();
				setState(142); lambdaExpression();
				setState(143); match(T__37);
				}
				break;
			case Ident:
				_localctx = new ServiceCallContext(_localctx);
				enterOuterAlt(_localctx, 14);
				{
				setState(145); qualifiedName();
				setState(146); match(T__18);
				setState(147); expressionSequence();
				setState(148); match(T__37);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LambdaExpressionContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public LambdaExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lambdaExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterLambdaExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitLambdaExpression(this);
		}
	}

	public final LambdaExpressionContext lambdaExpression() throws RecognitionException {
		LambdaExpressionContext _localctx = new LambdaExpressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_lambdaExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152); expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CollectionIteratorContext extends ParserRuleContext {
		public CollectionIteratorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_collectionIterator; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterCollectionIterator(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitCollectionIterator(this);
		}
	}

	public final CollectionIteratorContext collectionIterator() throws RecognitionException {
		CollectionIteratorContext _localctx = new CollectionIteratorContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_collectionIterator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(154);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__43) | (1L << T__40) | (1L << T__38) | (1L << T__36) | (1L << T__30) | (1L << T__29) | (1L << T__22) | (1L << T__5))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionSequenceContext extends ParserRuleContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionSequenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionSequence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterExpressionSequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitExpressionSequence(this);
		}
	}

	public final ExpressionSequenceContext expressionSequence() throws RecognitionException {
		ExpressionSequenceContext _localctx = new ExpressionSequenceContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_expressionSequence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__54) | (1L << T__53) | (1L << T__50) | (1L << T__47) | (1L << T__28) | (1L << T__20) | (1L << T__18) | (1L << T__17) | (1L << T__15) | (1L << T__12) | (1L << Integer) | (1L << Real) | (1L << String) | (1L << Ident))) != 0)) {
				{
				setState(156); expression(0);
				setState(161);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__21) {
					{
					{
					setState(157); match(T__21);
					setState(158); expression(0);
					}
					}
					setState(163);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDefinitionContext extends ParserRuleContext {
		public TerminalNode Ident() { return getToken(QueryParser.Ident, 0); }
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class,0);
		}
		public VariableDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterVariableDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitVariableDefinition(this);
		}
	}

	public final VariableDefinitionContext variableDefinition() throws RecognitionException {
		VariableDefinitionContext _localctx = new VariableDefinitionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_variableDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(166); match(Ident);
			setState(169);
			_la = _input.LA(1);
			if (_la==T__19) {
				{
				setState(167); match(T__19);
				setState(168); typeLiteral();
				}
			}

			setState(171); match(T__26);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LiteralContext extends ParserRuleContext {
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
	 
		public LiteralContext() { }
		public void copyFrom(LiteralContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class RealLitContext extends LiteralContext {
		public TerminalNode Real() { return getToken(QueryParser.Real, 0); }
		public RealLitContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterRealLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitRealLit(this);
		}
	}
	public static class StringLitContext extends LiteralContext {
		public TerminalNode String() { return getToken(QueryParser.String, 0); }
		public StringLitContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterStringLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitStringLit(this);
		}
	}
	public static class NullLitContext extends LiteralContext {
		public NullLitContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterNullLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitNullLit(this);
		}
	}
	public static class ExplicitSeqLitContext extends LiteralContext {
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public ExplicitSeqLitContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterExplicitSeqLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitExplicitSeqLit(this);
		}
	}
	public static class EnumOrClassifierLitContext extends LiteralContext {
		public TerminalNode Ident() { return getToken(QueryParser.Ident, 0); }
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public EnumOrClassifierLitContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterEnumOrClassifierLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitEnumOrClassifierLit(this);
		}
	}
	public static class ExplicitSetLitContext extends LiteralContext {
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public ExplicitSetLitContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterExplicitSetLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitExplicitSetLit(this);
		}
	}
	public static class TrueLitContext extends LiteralContext {
		public TrueLitContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterTrueLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitTrueLit(this);
		}
	}
	public static class SeqLitContext extends LiteralContext {
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public SeqLitContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterSeqLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitSeqLit(this);
		}
	}
	public static class FalseLitContext extends LiteralContext {
		public FalseLitContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterFalseLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitFalseLit(this);
		}
	}
	public static class SetLitContext extends LiteralContext {
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
		}
		public SetLitContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterSetLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitSetLit(this);
		}
	}
	public static class IntegerLitContext extends LiteralContext {
		public TerminalNode Integer() { return getToken(QueryParser.Integer, 0); }
		public IntegerLitContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterIntegerLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitIntegerLit(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_literal);
		try {
			setState(199);
			switch (_input.LA(1)) {
			case String:
				_localctx = new StringLitContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(173); match(String);
				}
				break;
			case Integer:
				_localctx = new IntegerLitContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(174); match(Integer);
				}
				break;
			case Real:
				_localctx = new RealLitContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(175); match(Real);
				}
				break;
			case T__12:
				_localctx = new TrueLitContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(176); match(T__12);
				}
				break;
			case T__47:
				_localctx = new FalseLitContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(177); match(T__47);
				}
				break;
			case T__28:
				_localctx = new NullLitContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(178); match(T__28);
				}
				break;
			case T__15:
				_localctx = new SetLitContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(179); match(T__15);
				setState(180); expressionSequence();
				setState(181); match(T__42);
				}
				break;
			case T__50:
				_localctx = new SeqLitContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(183); match(T__50);
				setState(184); expressionSequence();
				setState(185); match(T__25);
				}
				break;
			case T__54:
				_localctx = new ExplicitSeqLitContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(187); match(T__54);
				setState(188); expressionSequence();
				setState(189); match(T__42);
				}
				break;
			case T__53:
				_localctx = new ExplicitSetLitContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(191); match(T__53);
				setState(192); expressionSequence();
				setState(193); match(T__42);
				}
				break;
			case Ident:
				_localctx = new EnumOrClassifierLitContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(195); qualifiedName();
				setState(196); match(T__35);
				setState(197); match(Ident);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeLiteralContext extends ParserRuleContext {
		public TypeLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeLiteral; }
	 
		public TypeLiteralContext() { }
		public void copyFrom(TypeLiteralContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class StrTypeContext extends TypeLiteralContext {
		public StrTypeContext(TypeLiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterStrType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitStrType(this);
		}
	}
	public static class ModelObjectTypeContext extends TypeLiteralContext {
		public QualifiedNameContext qualifiedName() {
			return getRuleContext(QualifiedNameContext.class,0);
		}
		public ModelObjectTypeContext(TypeLiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterModelObjectType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitModelObjectType(this);
		}
	}
	public static class RealTypeContext extends TypeLiteralContext {
		public RealTypeContext(TypeLiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterRealType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitRealType(this);
		}
	}
	public static class IntTypeContext extends TypeLiteralContext {
		public IntTypeContext(TypeLiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterIntType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitIntType(this);
		}
	}
	public static class SeqTypeContext extends TypeLiteralContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class,0);
		}
		public SeqTypeContext(TypeLiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterSeqType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitSeqType(this);
		}
	}
	public static class BooleanTypeContext extends TypeLiteralContext {
		public BooleanTypeContext(TypeLiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterBooleanType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitBooleanType(this);
		}
	}
	public static class SetTypeContext extends TypeLiteralContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class,0);
		}
		public SetTypeContext(TypeLiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterSetType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitSetType(this);
		}
	}

	public final TypeLiteralContext typeLiteral() throws RecognitionException {
		TypeLiteralContext _localctx = new TypeLiteralContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_typeLiteral);
		try {
			setState(214);
			switch (_input.LA(1)) {
			case T__31:
				_localctx = new StrTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(201); match(T__31);
				}
				break;
			case T__6:
				_localctx = new IntTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(202); match(T__6);
				}
				break;
			case T__3:
				_localctx = new RealTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(203); match(T__3);
				}
				break;
			case T__7:
				_localctx = new BooleanTypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(204); match(T__7);
				}
				break;
			case T__51:
				_localctx = new SeqTypeContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(205); match(T__51);
				setState(206); typeLiteral();
				setState(207); match(T__37);
				}
				break;
			case T__1:
				_localctx = new SetTypeContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(209); match(T__1);
				setState(210); typeLiteral();
				setState(211); match(T__37);
				}
				break;
			case Ident:
				_localctx = new ModelObjectTypeContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(213); qualifiedName();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QualifiedNameContext extends ParserRuleContext {
		public List<TerminalNode> Ident() { return getTokens(QueryParser.Ident); }
		public TerminalNode Ident(int i) {
			return getToken(QueryParser.Ident, i);
		}
		public QualifiedNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_qualifiedName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterQualifiedName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitQualifiedName(this);
		}
	}

	public final QualifiedNameContext qualifiedName() throws RecognitionException {
		QualifiedNameContext _localctx = new QualifiedNameContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_qualifiedName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216); match(Ident);
			setState(219);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(217); match(T__35);
				setState(218); match(Ident);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 1: return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0: return precpred(_ctx, 9);

		case 1: return precpred(_ctx, 8);

		case 2: return precpred(_ctx, 7);

		case 3: return precpred(_ctx, 6);

		case 4: return precpred(_ctx, 5);

		case 5: return precpred(_ctx, 4);

		case 6: return precpred(_ctx, 12);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3?\u00e0\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3-\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3E\n\3\f\3\16"+
		"\3H\13\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\5\7V\n\7\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\br\n\b\3\b\3\b\3\b\5\bw\n\b\3\b\3\b\3"+
		"\b\5\b|\n\b\3\b\3\b\3\b\5\b\u0081\n\b\3\b\3\b\3\b\5\b\u0086\n\b\3\b\3"+
		"\b\3\b\5\b\u008b\n\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5"+
		"\b\u0099\n\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\7\13\u00a2\n\13\f\13\16\13"+
		"\u00a5\13\13\5\13\u00a7\n\13\3\f\3\f\3\f\5\f\u00ac\n\f\3\f\3\f\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3"+
		"\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00ca\n\r\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u00d9\n\16\3\17\3\17\3\17\5\17"+
		"\u00de\n\17\3\17\2\3\4\20\2\4\6\b\n\f\16\20\22\24\26\30\32\34\2\5\4\2"+
		"%%//\b\2\t\t\f\f\30\30\60\60\65\6599\t\2\16\16\21\21\23\23\25\25\33\34"+
		"##\64\64\u0105\2\36\3\2\2\2\4,\3\2\2\2\6I\3\2\2\2\bK\3\2\2\2\nM\3\2\2"+
		"\2\fU\3\2\2\2\16\u0098\3\2\2\2\20\u009a\3\2\2\2\22\u009c\3\2\2\2\24\u00a6"+
		"\3\2\2\2\26\u00a8\3\2\2\2\30\u00c9\3\2\2\2\32\u00d8\3\2\2\2\34\u00da\3"+
		"\2\2\2\36\37\5\4\3\2\37 \7\2\2\3 \3\3\2\2\2!\"\b\3\1\2\"#\7(\2\2#-\5\4"+
		"\3\r$%\7%\2\2%-\5\4\3\f&-\5\n\6\2\'-\5\30\r\2()\7\'\2\2)*\5\4\3\2*+\7"+
		"\24\2\2+-\3\2\2\2,!\3\2\2\2,$\3\2\2\2,&\3\2\2\2,\'\3\2\2\2,(\3\2\2\2-"+
		"F\3\2\2\2./\f\13\2\2/\60\7;\2\2\60E\5\4\3\f\61\62\f\n\2\2\62\63\5\6\4"+
		"\2\63\64\5\4\3\13\64E\3\2\2\2\65\66\f\t\2\2\66\67\5\b\5\2\678\5\4\3\n"+
		"8E\3\2\2\29:\f\b\2\2:;\7+\2\2;E\5\4\3\t<=\f\7\2\2=>\7\b\2\2>E\5\4\3\b"+
		"?@\f\6\2\2@A\7\5\2\2AE\5\4\3\7BC\f\16\2\2CE\5\f\7\2D.\3\2\2\2D\61\3\2"+
		"\2\2D\65\3\2\2\2D9\3\2\2\2D<\3\2\2\2D?\3\2\2\2DB\3\2\2\2EH\3\2\2\2FD\3"+
		"\2\2\2FG\3\2\2\2G\5\3\2\2\2HF\3\2\2\2IJ\t\2\2\2J\7\3\2\2\2KL\t\3\2\2L"+
		"\t\3\2\2\2MN\7?\2\2N\13\3\2\2\2OP\7.\2\2PV\7?\2\2QR\7.\2\2RV\5\16\b\2"+
		"ST\7\22\2\2TV\5\16\b\2UO\3\2\2\2UQ\3\2\2\2US\3\2\2\2V\r\3\2\2\2WX\7\61"+
		"\2\2XY\5\32\16\2YZ\7\24\2\2Z\u0099\3\2\2\2[\\\7\31\2\2\\]\5\32\16\2]^"+
		"\7\24\2\2^\u0099\3\2\2\2_`\7\67\2\2`a\5\32\16\2ab\7\24\2\2b\u0099\3\2"+
		"\2\2cd\7\r\2\2de\5\32\16\2ef\7\24\2\2f\u0099\3\2\2\2gh\7\"\2\2hi\5\32"+
		"\16\2ij\7\24\2\2j\u0099\3\2\2\2kl\7\27\2\2lm\5\32\16\2mn\7\24\2\2n\u0099"+
		"\3\2\2\2oq\7\20\2\2pr\5\32\16\2qp\3\2\2\2qr\3\2\2\2rs\3\2\2\2s\u0099\7"+
		"\24\2\2tv\7\13\2\2uw\5\32\16\2vu\3\2\2\2vw\3\2\2\2wx\3\2\2\2x\u0099\7"+
		"\24\2\2y{\7!\2\2z|\5\32\16\2{z\3\2\2\2{|\3\2\2\2|}\3\2\2\2}\u0099\7\24"+
		"\2\2~\u0080\7\36\2\2\177\u0081\5\32\16\2\u0080\177\3\2\2\2\u0080\u0081"+
		"\3\2\2\2\u0081\u0082\3\2\2\2\u0082\u0099\7\24\2\2\u0083\u0085\7)\2\2\u0084"+
		"\u0086\5\32\16\2\u0085\u0084\3\2\2\2\u0085\u0086\3\2\2\2\u0086\u0087\3"+
		"\2\2\2\u0087\u0099\7\24\2\2\u0088\u008a\7,\2\2\u0089\u008b\5\32\16\2\u008a"+
		"\u0089\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u0099\7\24"+
		"\2\2\u008d\u008e\5\22\n\2\u008e\u008f\7\'\2\2\u008f\u0090\5\26\f\2\u0090"+
		"\u0091\5\20\t\2\u0091\u0092\7\24\2\2\u0092\u0099\3\2\2\2\u0093\u0094\5"+
		"\34\17\2\u0094\u0095\7\'\2\2\u0095\u0096\5\24\13\2\u0096\u0097\7\24\2"+
		"\2\u0097\u0099\3\2\2\2\u0098W\3\2\2\2\u0098[\3\2\2\2\u0098_\3\2\2\2\u0098"+
		"c\3\2\2\2\u0098g\3\2\2\2\u0098k\3\2\2\2\u0098o\3\2\2\2\u0098t\3\2\2\2"+
		"\u0098y\3\2\2\2\u0098~\3\2\2\2\u0098\u0083\3\2\2\2\u0098\u0088\3\2\2\2"+
		"\u0098\u008d\3\2\2\2\u0098\u0093\3\2\2\2\u0099\17\3\2\2\2\u009a\u009b"+
		"\5\4\3\2\u009b\21\3\2\2\2\u009c\u009d\t\4\2\2\u009d\23\3\2\2\2\u009e\u00a3"+
		"\5\4\3\2\u009f\u00a0\7$\2\2\u00a0\u00a2\5\4\3\2\u00a1\u009f\3\2\2\2\u00a2"+
		"\u00a5\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00a7\3\2"+
		"\2\2\u00a5\u00a3\3\2\2\2\u00a6\u009e\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7"+
		"\25\3\2\2\2\u00a8\u00ab\7?\2\2\u00a9\u00aa\7&\2\2\u00aa\u00ac\5\32\16"+
		"\2\u00ab\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ad\u00ae"+
		"\7\37\2\2\u00ae\27\3\2\2\2\u00af\u00ca\7>\2\2\u00b0\u00ca\7<\2\2\u00b1"+
		"\u00ca\7=\2\2\u00b2\u00ca\7-\2\2\u00b3\u00ca\7\n\2\2\u00b4\u00ca\7\35"+
		"\2\2\u00b5\u00b6\7*\2\2\u00b6\u00b7\5\24\13\2\u00b7\u00b8\7\17\2\2\u00b8"+
		"\u00ca\3\2\2\2\u00b9\u00ba\7\7\2\2\u00ba\u00bb\5\24\13\2\u00bb\u00bc\7"+
		" \2\2\u00bc\u00ca\3\2\2\2\u00bd\u00be\7\3\2\2\u00be\u00bf\5\24\13\2\u00bf"+
		"\u00c0\7\17\2\2\u00c0\u00ca\3\2\2\2\u00c1\u00c2\7\4\2\2\u00c2\u00c3\5"+
		"\24\13\2\u00c3\u00c4\7\17\2\2\u00c4\u00ca\3\2\2\2\u00c5\u00c6\5\34\17"+
		"\2\u00c6\u00c7\7\26\2\2\u00c7\u00c8\7?\2\2\u00c8\u00ca\3\2\2\2\u00c9\u00af"+
		"\3\2\2\2\u00c9\u00b0\3\2\2\2\u00c9\u00b1\3\2\2\2\u00c9\u00b2\3\2\2\2\u00c9"+
		"\u00b3\3\2\2\2\u00c9\u00b4\3\2\2\2\u00c9\u00b5\3\2\2\2\u00c9\u00b9\3\2"+
		"\2\2\u00c9\u00bd\3\2\2\2\u00c9\u00c1\3\2\2\2\u00c9\u00c5\3\2\2\2\u00ca"+
		"\31\3\2\2\2\u00cb\u00d9\7\32\2\2\u00cc\u00d9\7\63\2\2\u00cd\u00d9\7\66"+
		"\2\2\u00ce\u00d9\7\62\2\2\u00cf\u00d0\7\6\2\2\u00d0\u00d1\5\32\16\2\u00d1"+
		"\u00d2\7\24\2\2\u00d2\u00d9\3\2\2\2\u00d3\u00d4\78\2\2\u00d4\u00d5\5\32"+
		"\16\2\u00d5\u00d6\7\24\2\2\u00d6\u00d9\3\2\2\2\u00d7\u00d9\5\34\17\2\u00d8"+
		"\u00cb\3\2\2\2\u00d8\u00cc\3\2\2\2\u00d8\u00cd\3\2\2\2\u00d8\u00ce\3\2"+
		"\2\2\u00d8\u00cf\3\2\2\2\u00d8\u00d3\3\2\2\2\u00d8\u00d7\3\2\2\2\u00d9"+
		"\33\3\2\2\2\u00da\u00dd\7?\2\2\u00db\u00dc\7\26\2\2\u00dc\u00de\7?\2\2"+
		"\u00dd\u00db\3\2\2\2\u00dd\u00de\3\2\2\2\u00de\35\3\2\2\2\23,DFUqv{\u0080"+
		"\u0085\u008a\u0098\u00a3\u00a6\u00ab\u00c9\u00d8\u00dd";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}