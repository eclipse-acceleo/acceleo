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
  

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class QueryParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__58=1, T__57=2, T__56=3, T__55=4, T__54=5, T__53=6, T__52=7, T__51=8, 
		T__50=9, T__49=10, T__48=11, T__47=12, T__46=13, T__45=14, T__44=15, T__43=16, 
		T__42=17, T__41=18, T__40=19, T__39=20, T__38=21, T__37=22, T__36=23, 
		T__35=24, T__34=25, T__33=26, T__32=27, T__31=28, T__30=29, T__29=30, 
		T__28=31, T__27=32, T__26=33, T__25=34, T__24=35, T__23=36, T__22=37, 
		T__21=38, T__20=39, T__19=40, T__18=41, T__17=42, T__16=43, T__15=44, 
		T__14=45, T__13=46, T__12=47, T__11=48, T__10=49, T__9=50, T__8=51, T__7=52, 
		T__6=53, T__5=54, T__4=55, T__3=56, T__2=57, T__1=58, T__0=59, WS=60, 
		MultOp=61, Integer=62, Real=63, String=64, Ident=65;
	public static final String[] tokenNames = {
		"<INVALID>", "'{'", "'::'", "'one'", "'='", "'oclAsType('", "'filter('", 
		"'OrderedSet('", "'null'", "'('", "'implies'", "','", "'false'", "'Sequence('", 
		"'Real'", "'oclIsTypeOf('", "'reject'", "'>='", "'String'", "'<'", "']'", 
		"'forAll'", "'Sequence{'", "'precedingSiblings('", "'<>'", "'let'", "'+'", 
		"'oclIsKindOf('", "'eInverse('", "'true'", "'eContents('", "'Integer'", 
		"'}'", "'any'", "'siblings('", "'eContainerOrSelf('", "'<='", "'eAllContents('", 
		"'isUnique'", "'collect'", "'exists'", "'.'", "'Boolean'", "'->'", "'OrderedSet{'", 
		"'eContainer('", "':'", "'['", "'|'", "'select'", "'>'", "'xor'", "'or'", 
		"'as('", "'in'", "'is('", "')'", "'and'", "'not'", "'-'", "WS", "MultOp", 
		"Integer", "Real", "String", "Ident"
	};
	public static final int
		RULE_entry = 0, RULE_expression = 1, RULE_binding = 2, RULE_addOp = 3, 
		RULE_compOp = 4, RULE_varRef = 5, RULE_navigationSegment = 6, RULE_callExp = 7, 
		RULE_lambdaExpression = 8, RULE_collectionIterator = 9, RULE_expressionSequence = 10, 
		RULE_variableDefinition = 11, RULE_literal = 12, RULE_typeLiteral = 13, 
		RULE_qualifiedName = 14;
	public static final String[] ruleNames = {
		"entry", "expression", "binding", "addOp", "compOp", "varRef", "navigationSegment", 
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
			setState(30); expression(0);
			setState(31); match(EOF);
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
	public static class AddContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public AddOpContext addOp() {
			return getRuleContext(AddOpContext.class,0);
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
	public static class LetExprContext extends ExpressionContext {
		public List<BindingContext> binding() {
			return getRuleContexts(BindingContext.class);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BindingContext binding(int i) {
			return getRuleContext(BindingContext.class,i);
		}
		public LetExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterLetExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitLetExpr(this);
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
	public static class CompContext extends ExpressionContext {
		public CompOpContext compOp() {
			return getRuleContext(CompOpContext.class,0);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
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
	public static class MultContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode MultOp() { return getToken(QueryParser.MultOp, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
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
	public static class XorContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public XorContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterXor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitXor(this);
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
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				_localctx = new NotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(34); match(T__1);
				setState(35); expression(13);
				}
				break;

			case 2:
				{
				_localctx = new MinContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(36); match(T__0);
				setState(37); expression(12);
				}
				break;

			case 3:
				{
				_localctx = new LetExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(38); match(T__34);
				setState(39); binding();
				setState(44);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__48) {
					{
					{
					setState(40); match(T__48);
					setState(41); binding();
					}
					}
					setState(46);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(47); match(T__5);
				setState(48); expression(1);
				}
				break;

			case 4:
				{
				_localctx = new VarContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(50); varRef();
				}
				break;

			case 5:
				{
				_localctx = new LitContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(51); literal();
				}
				break;

			case 6:
				{
				_localctx = new ParenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(52); match(T__50);
				setState(53); expression(0);
				setState(54); match(T__3);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(85);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(83);
					switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
					case 1:
						{
						_localctx = new MultContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(58);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(59); match(MultOp);
						setState(60); expression(12);
						}
						break;

					case 2:
						{
						_localctx = new AddContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(61);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(62); addOp();
						setState(63); expression(11);
						}
						break;

					case 3:
						{
						_localctx = new CompContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(65);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(66); compOp();
						setState(67); expression(10);
						}
						break;

					case 4:
						{
						_localctx = new AndContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(69);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(70); match(T__2);
						setState(71); expression(9);
						}
						break;

					case 5:
						{
						_localctx = new OrContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(72);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(73); match(T__7);
						setState(74); expression(8);
						}
						break;

					case 6:
						{
						_localctx = new XorContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(75);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(76); match(T__8);
						setState(77); expression(7);
						}
						break;

					case 7:
						{
						_localctx = new ImpliesContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(78);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(79); match(T__49);
						setState(80); expression(6);
						}
						break;

					case 8:
						{
						_localctx = new NavContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(81);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(82); navigationSegment();
						}
						break;
					}
					} 
				}
				setState(87);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
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

	public static class BindingContext extends ParserRuleContext {
		public TerminalNode Ident() { return getToken(QueryParser.Ident, 0); }
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BindingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_binding; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterBinding(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitBinding(this);
		}
	}

	public final BindingContext binding() throws RecognitionException {
		BindingContext _localctx = new BindingContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_binding);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(88); match(Ident);
			setState(91);
			_la = _input.LA(1);
			if (_la==T__13) {
				{
				setState(89); match(T__13);
				setState(90); typeLiteral();
				}
			}

			setState(93); match(T__55);
			setState(94); expression(0);
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
		enterRule(_localctx, 6, RULE_addOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(96);
			_la = _input.LA(1);
			if ( !(_la==T__33 || _la==T__0) ) {
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
		enterRule(_localctx, 8, RULE_compOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__55) | (1L << T__42) | (1L << T__40) | (1L << T__35) | (1L << T__23) | (1L << T__9))) != 0)) ) {
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
		enterRule(_localctx, 10, RULE_varRef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100); match(Ident);
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

	public final NavigationSegmentContext navigationSegment() throws RecognitionException {
		NavigationSegmentContext _localctx = new NavigationSegmentContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_navigationSegment);
		try {
			setState(108);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				_localctx = new FeatureContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(102); match(T__18);
				setState(103); match(Ident);
				}
				break;

			case 2:
				_localctx = new ApplyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(104); match(T__18);
				setState(105); callExp();
				}
				break;

			case 3:
				_localctx = new CallServiceContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(106); match(T__16);
				setState(107); callExp();
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
	public static class EInverseContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
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
	public static class EContainerOrSelfContext extends CallExpContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class,0);
		}
		public EContainerOrSelfContext(CallExpContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterEContainerOrSelf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitEContainerOrSelf(this);
		}
	}
	public static class IterationCallContext extends CallExpContext {
		public VariableDefinitionContext variableDefinition() {
			return getRuleContext(VariableDefinitionContext.class,0);
		}
		public CollectionIteratorContext collectionIterator() {
			return getRuleContext(CollectionIteratorContext.class,0);
		}
		public LambdaExpressionContext lambdaExpression() {
			return getRuleContext(LambdaExpressionContext.class,0);
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

	public final CallExpContext callExp() throws RecognitionException {
		CallExpContext _localctx = new CallExpContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_callExp);
		int _la;
		try {
			setState(180);
			switch (_input.LA(1)) {
			case T__53:
				_localctx = new FilterContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(110); match(T__53);
				setState(111); typeLiteral();
				setState(112); match(T__3);
				}
				break;
			case T__54:
				_localctx = new AsTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(114); match(T__54);
				setState(115); typeLiteral();
				setState(116); match(T__3);
				}
				break;
			case T__6:
				_localctx = new AsContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(118); match(T__6);
				setState(119); typeLiteral();
				setState(120); match(T__3);
				}
				break;
			case T__32:
				_localctx = new IsKindContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(122); match(T__32);
				setState(123); typeLiteral();
				setState(124); match(T__3);
				}
				break;
			case T__44:
				_localctx = new IsTypeContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(126); match(T__44);
				setState(127); typeLiteral();
				setState(128); match(T__3);
				}
				break;
			case T__4:
				_localctx = new IsContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(130); match(T__4);
				setState(131); typeLiteral();
				setState(132); match(T__3);
				}
				break;
			case T__36:
				_localctx = new PrecSiblingsContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(134); match(T__36);
				setState(136);
				_la = _input.LA(1);
				if (((((_la - 7)) & ~0x3f) == 0 && ((1L << (_la - 7)) & ((1L << (T__52 - 7)) | (1L << (T__46 - 7)) | (1L << (T__45 - 7)) | (1L << (T__41 - 7)) | (1L << (T__28 - 7)) | (1L << (T__17 - 7)) | (1L << (Ident - 7)))) != 0)) {
					{
					setState(135); typeLiteral();
					}
				}

				setState(138); match(T__3);
				}
				break;
			case T__25:
				_localctx = new SiblingsContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(139); match(T__25);
				setState(141);
				_la = _input.LA(1);
				if (((((_la - 7)) & ~0x3f) == 0 && ((1L << (_la - 7)) & ((1L << (T__52 - 7)) | (1L << (T__46 - 7)) | (1L << (T__45 - 7)) | (1L << (T__41 - 7)) | (1L << (T__28 - 7)) | (1L << (T__17 - 7)) | (1L << (Ident - 7)))) != 0)) {
					{
					setState(140); typeLiteral();
					}
				}

				setState(143); match(T__3);
				}
				break;
			case T__29:
				_localctx = new EContentContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(144); match(T__29);
				setState(146);
				_la = _input.LA(1);
				if (((((_la - 7)) & ~0x3f) == 0 && ((1L << (_la - 7)) & ((1L << (T__52 - 7)) | (1L << (T__46 - 7)) | (1L << (T__45 - 7)) | (1L << (T__41 - 7)) | (1L << (T__28 - 7)) | (1L << (T__17 - 7)) | (1L << (Ident - 7)))) != 0)) {
					{
					setState(145); typeLiteral();
					}
				}

				setState(148); match(T__3);
				}
				break;
			case T__22:
				_localctx = new EAContentContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(149); match(T__22);
				setState(151);
				_la = _input.LA(1);
				if (((((_la - 7)) & ~0x3f) == 0 && ((1L << (_la - 7)) & ((1L << (T__52 - 7)) | (1L << (T__46 - 7)) | (1L << (T__45 - 7)) | (1L << (T__41 - 7)) | (1L << (T__28 - 7)) | (1L << (T__17 - 7)) | (1L << (Ident - 7)))) != 0)) {
					{
					setState(150); typeLiteral();
					}
				}

				setState(153); match(T__3);
				}
				break;
			case T__24:
				_localctx = new EContainerOrSelfContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(154); match(T__24);
				setState(155); typeLiteral();
				setState(156); match(T__3);
				}
				break;
			case T__14:
				_localctx = new EContainerContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(158); match(T__14);
				setState(160);
				_la = _input.LA(1);
				if (((((_la - 7)) & ~0x3f) == 0 && ((1L << (_la - 7)) & ((1L << (T__52 - 7)) | (1L << (T__46 - 7)) | (1L << (T__45 - 7)) | (1L << (T__41 - 7)) | (1L << (T__28 - 7)) | (1L << (T__17 - 7)) | (1L << (Ident - 7)))) != 0)) {
					{
					setState(159); typeLiteral();
					}
				}

				setState(162); match(T__3);
				}
				break;
			case T__31:
				_localctx = new EInverseContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(163); match(T__31);
				setState(166);
				switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
				case 1:
					{
					setState(164); typeLiteral();
					}
					break;

				case 2:
					{
					setState(165); expression(0);
					}
					break;
				}
				setState(168); match(T__3);
				}
				break;
			case T__56:
			case T__43:
			case T__38:
			case T__26:
			case T__21:
			case T__20:
			case T__19:
			case T__10:
				_localctx = new IterationCallContext(_localctx);
				enterOuterAlt(_localctx, 14);
				{
				setState(169); collectionIterator();
				setState(170); match(T__50);
				setState(171); variableDefinition();
				setState(172); lambdaExpression();
				setState(173); match(T__3);
				}
				break;
			case Ident:
				_localctx = new ServiceCallContext(_localctx);
				enterOuterAlt(_localctx, 15);
				{
				setState(175); qualifiedName();
				setState(176); match(T__50);
				setState(177); expressionSequence();
				setState(178); match(T__3);
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
		enterRule(_localctx, 16, RULE_lambdaExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(182); expression(0);
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
		enterRule(_localctx, 18, RULE_collectionIterator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(184);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__56) | (1L << T__43) | (1L << T__38) | (1L << T__26) | (1L << T__21) | (1L << T__20) | (1L << T__19) | (1L << T__10))) != 0)) ) {
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
		enterRule(_localctx, 20, RULE_expressionSequence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__58) | (1L << T__51) | (1L << T__50) | (1L << T__47) | (1L << T__37) | (1L << T__34) | (1L << T__30) | (1L << T__15) | (1L << T__12) | (1L << T__1) | (1L << T__0) | (1L << Integer) | (1L << Real))) != 0) || _la==String || _la==Ident) {
				{
				setState(186); expression(0);
				setState(191);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__48) {
					{
					{
					setState(187); match(T__48);
					setState(188); expression(0);
					}
					}
					setState(193);
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
		enterRule(_localctx, 22, RULE_variableDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(196); match(Ident);
			setState(199);
			_la = _input.LA(1);
			if (_la==T__13) {
				{
				setState(197); match(T__13);
				setState(198); typeLiteral();
				}
			}

			setState(201); match(T__11);
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

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_literal);
		try {
			setState(229);
			switch (_input.LA(1)) {
			case String:
				_localctx = new StringLitContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(203); match(String);
				}
				break;
			case Integer:
				_localctx = new IntegerLitContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(204); match(Integer);
				}
				break;
			case Real:
				_localctx = new RealLitContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(205); match(Real);
				}
				break;
			case T__30:
				_localctx = new TrueLitContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(206); match(T__30);
				}
				break;
			case T__47:
				_localctx = new FalseLitContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(207); match(T__47);
				}
				break;
			case T__51:
				_localctx = new NullLitContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(208); match(T__51);
				}
				break;
			case T__58:
				_localctx = new SetLitContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(209); match(T__58);
				setState(210); expressionSequence();
				setState(211); match(T__27);
				}
				break;
			case T__12:
				_localctx = new SeqLitContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(213); match(T__12);
				setState(214); expressionSequence();
				setState(215); match(T__39);
				}
				break;
			case T__37:
				_localctx = new ExplicitSeqLitContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(217); match(T__37);
				setState(218); expressionSequence();
				setState(219); match(T__27);
				}
				break;
			case T__15:
				_localctx = new ExplicitSetLitContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(221); match(T__15);
				setState(222); expressionSequence();
				setState(223); match(T__27);
				}
				break;
			case Ident:
				_localctx = new EnumOrClassifierLitContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(225); qualifiedName();
				setState(226); match(T__57);
				setState(227); match(Ident);
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

	public final TypeLiteralContext typeLiteral() throws RecognitionException {
		TypeLiteralContext _localctx = new TypeLiteralContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_typeLiteral);
		try {
			setState(244);
			switch (_input.LA(1)) {
			case T__41:
				_localctx = new StrTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(231); match(T__41);
				}
				break;
			case T__28:
				_localctx = new IntTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(232); match(T__28);
				}
				break;
			case T__45:
				_localctx = new RealTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(233); match(T__45);
				}
				break;
			case T__17:
				_localctx = new BooleanTypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(234); match(T__17);
				}
				break;
			case T__46:
				_localctx = new SeqTypeContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(235); match(T__46);
				setState(236); typeLiteral();
				setState(237); match(T__3);
				}
				break;
			case T__52:
				_localctx = new SetTypeContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(239); match(T__52);
				setState(240); typeLiteral();
				setState(241); match(T__3);
				}
				break;
			case Ident:
				_localctx = new ModelObjectTypeContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(243); qualifiedName();
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
		enterRule(_localctx, 28, RULE_qualifiedName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(246); match(Ident);
			setState(249);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(247); match(T__57);
				setState(248); match(Ident);
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
		case 0: return precpred(_ctx, 11);

		case 1: return precpred(_ctx, 10);

		case 2: return precpred(_ctx, 9);

		case 3: return precpred(_ctx, 8);

		case 4: return precpred(_ctx, 7);

		case 5: return precpred(_ctx, 6);

		case 6: return precpred(_ctx, 5);

		case 7: return precpred(_ctx, 14);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3C\u00fe\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\3\2\3\2\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3-\n\3\f\3\16\3\60\13\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\5\3;\n\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3V\n\3"+
		"\f\3\16\3Y\13\3\3\4\3\4\3\4\5\4^\n\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3"+
		"\7\3\b\3\b\3\b\3\b\3\b\3\b\5\bo\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\5\t\u008b\n\t\3\t\3\t\3\t\5\t\u0090\n\t\3\t\3\t\3\t\5\t\u0095\n\t\3\t"+
		"\3\t\3\t\5\t\u009a\n\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u00a3\n\t\3\t\3"+
		"\t\3\t\3\t\5\t\u00a9\n\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3"+
		"\t\5\t\u00b7\n\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\7\f\u00c0\n\f\f\f\16\f"+
		"\u00c3\13\f\5\f\u00c5\n\f\3\r\3\r\3\r\5\r\u00ca\n\r\3\r\3\r\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u00e8\n\16\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u00f7"+
		"\n\17\3\20\3\20\3\20\5\20\u00fc\n\20\3\20\2\3\4\21\2\4\6\b\n\f\16\20\22"+
		"\24\26\30\32\34\36\2\5\4\2\34\34==\b\2\6\6\23\23\25\25\32\32&&\64\64\b"+
		"\2\5\5\22\22\27\27##(*\63\63\u0128\2 \3\2\2\2\4:\3\2\2\2\6Z\3\2\2\2\b"+
		"b\3\2\2\2\nd\3\2\2\2\ff\3\2\2\2\16n\3\2\2\2\20\u00b6\3\2\2\2\22\u00b8"+
		"\3\2\2\2\24\u00ba\3\2\2\2\26\u00c4\3\2\2\2\30\u00c6\3\2\2\2\32\u00e7\3"+
		"\2\2\2\34\u00f6\3\2\2\2\36\u00f8\3\2\2\2 !\5\4\3\2!\"\7\2\2\3\"\3\3\2"+
		"\2\2#$\b\3\1\2$%\7<\2\2%;\5\4\3\17&\'\7=\2\2\';\5\4\3\16()\7\33\2\2)."+
		"\5\6\4\2*+\7\r\2\2+-\5\6\4\2,*\3\2\2\2-\60\3\2\2\2.,\3\2\2\2./\3\2\2\2"+
		"/\61\3\2\2\2\60.\3\2\2\2\61\62\78\2\2\62\63\5\4\3\3\63;\3\2\2\2\64;\5"+
		"\f\7\2\65;\5\32\16\2\66\67\7\13\2\2\678\5\4\3\289\7:\2\29;\3\2\2\2:#\3"+
		"\2\2\2:&\3\2\2\2:(\3\2\2\2:\64\3\2\2\2:\65\3\2\2\2:\66\3\2\2\2;W\3\2\2"+
		"\2<=\f\r\2\2=>\7?\2\2>V\5\4\3\16?@\f\f\2\2@A\5\b\5\2AB\5\4\3\rBV\3\2\2"+
		"\2CD\f\13\2\2DE\5\n\6\2EF\5\4\3\fFV\3\2\2\2GH\f\n\2\2HI\7;\2\2IV\5\4\3"+
		"\13JK\f\t\2\2KL\7\66\2\2LV\5\4\3\nMN\f\b\2\2NO\7\65\2\2OV\5\4\3\tPQ\f"+
		"\7\2\2QR\7\f\2\2RV\5\4\3\bST\f\20\2\2TV\5\16\b\2U<\3\2\2\2U?\3\2\2\2U"+
		"C\3\2\2\2UG\3\2\2\2UJ\3\2\2\2UM\3\2\2\2UP\3\2\2\2US\3\2\2\2VY\3\2\2\2"+
		"WU\3\2\2\2WX\3\2\2\2X\5\3\2\2\2YW\3\2\2\2Z]\7C\2\2[\\\7\60\2\2\\^\5\34"+
		"\17\2][\3\2\2\2]^\3\2\2\2^_\3\2\2\2_`\7\6\2\2`a\5\4\3\2a\7\3\2\2\2bc\t"+
		"\2\2\2c\t\3\2\2\2de\t\3\2\2e\13\3\2\2\2fg\7C\2\2g\r\3\2\2\2hi\7+\2\2i"+
		"o\7C\2\2jk\7+\2\2ko\5\20\t\2lm\7-\2\2mo\5\20\t\2nh\3\2\2\2nj\3\2\2\2n"+
		"l\3\2\2\2o\17\3\2\2\2pq\7\b\2\2qr\5\34\17\2rs\7:\2\2s\u00b7\3\2\2\2tu"+
		"\7\7\2\2uv\5\34\17\2vw\7:\2\2w\u00b7\3\2\2\2xy\7\67\2\2yz\5\34\17\2z{"+
		"\7:\2\2{\u00b7\3\2\2\2|}\7\35\2\2}~\5\34\17\2~\177\7:\2\2\177\u00b7\3"+
		"\2\2\2\u0080\u0081\7\21\2\2\u0081\u0082\5\34\17\2\u0082\u0083\7:\2\2\u0083"+
		"\u00b7\3\2\2\2\u0084\u0085\79\2\2\u0085\u0086\5\34\17\2\u0086\u0087\7"+
		":\2\2\u0087\u00b7\3\2\2\2\u0088\u008a\7\31\2\2\u0089\u008b\5\34\17\2\u008a"+
		"\u0089\3\2\2\2\u008a\u008b\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u00b7\7:"+
		"\2\2\u008d\u008f\7$\2\2\u008e\u0090\5\34\17\2\u008f\u008e\3\2\2\2\u008f"+
		"\u0090\3\2\2\2\u0090\u0091\3\2\2\2\u0091\u00b7\7:\2\2\u0092\u0094\7 \2"+
		"\2\u0093\u0095\5\34\17\2\u0094\u0093\3\2\2\2\u0094\u0095\3\2\2\2\u0095"+
		"\u0096\3\2\2\2\u0096\u00b7\7:\2\2\u0097\u0099\7\'\2\2\u0098\u009a\5\34"+
		"\17\2\u0099\u0098\3\2\2\2\u0099\u009a\3\2\2\2\u009a\u009b\3\2\2\2\u009b"+
		"\u00b7\7:\2\2\u009c\u009d\7%\2\2\u009d\u009e\5\34\17\2\u009e\u009f\7:"+
		"\2\2\u009f\u00b7\3\2\2\2\u00a0\u00a2\7/\2\2\u00a1\u00a3\5\34\17\2\u00a2"+
		"\u00a1\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a4\3\2\2\2\u00a4\u00b7\7:"+
		"\2\2\u00a5\u00a8\7\36\2\2\u00a6\u00a9\5\34\17\2\u00a7\u00a9\5\4\3\2\u00a8"+
		"\u00a6\3\2\2\2\u00a8\u00a7\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\u00aa\3\2"+
		"\2\2\u00aa\u00b7\7:\2\2\u00ab\u00ac\5\24\13\2\u00ac\u00ad\7\13\2\2\u00ad"+
		"\u00ae\5\30\r\2\u00ae\u00af\5\22\n\2\u00af\u00b0\7:\2\2\u00b0\u00b7\3"+
		"\2\2\2\u00b1\u00b2\5\36\20\2\u00b2\u00b3\7\13\2\2\u00b3\u00b4\5\26\f\2"+
		"\u00b4\u00b5\7:\2\2\u00b5\u00b7\3\2\2\2\u00b6p\3\2\2\2\u00b6t\3\2\2\2"+
		"\u00b6x\3\2\2\2\u00b6|\3\2\2\2\u00b6\u0080\3\2\2\2\u00b6\u0084\3\2\2\2"+
		"\u00b6\u0088\3\2\2\2\u00b6\u008d\3\2\2\2\u00b6\u0092\3\2\2\2\u00b6\u0097"+
		"\3\2\2\2\u00b6\u009c\3\2\2\2\u00b6\u00a0\3\2\2\2\u00b6\u00a5\3\2\2\2\u00b6"+
		"\u00ab\3\2\2\2\u00b6\u00b1\3\2\2\2\u00b7\21\3\2\2\2\u00b8\u00b9\5\4\3"+
		"\2\u00b9\23\3\2\2\2\u00ba\u00bb\t\4\2\2\u00bb\25\3\2\2\2\u00bc\u00c1\5"+
		"\4\3\2\u00bd\u00be\7\r\2\2\u00be\u00c0\5\4\3\2\u00bf\u00bd\3\2\2\2\u00c0"+
		"\u00c3\3\2\2\2\u00c1\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c5\3\2"+
		"\2\2\u00c3\u00c1\3\2\2\2\u00c4\u00bc\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5"+
		"\27\3\2\2\2\u00c6\u00c9\7C\2\2\u00c7\u00c8\7\60\2\2\u00c8\u00ca\5\34\17"+
		"\2\u00c9\u00c7\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00cc"+
		"\7\62\2\2\u00cc\31\3\2\2\2\u00cd\u00e8\7B\2\2\u00ce\u00e8\7@\2\2\u00cf"+
		"\u00e8\7A\2\2\u00d0\u00e8\7\37\2\2\u00d1\u00e8\7\16\2\2\u00d2\u00e8\7"+
		"\n\2\2\u00d3\u00d4\7\3\2\2\u00d4\u00d5\5\26\f\2\u00d5\u00d6\7\"\2\2\u00d6"+
		"\u00e8\3\2\2\2\u00d7\u00d8\7\61\2\2\u00d8\u00d9\5\26\f\2\u00d9\u00da\7"+
		"\26\2\2\u00da\u00e8\3\2\2\2\u00db\u00dc\7\30\2\2\u00dc\u00dd\5\26\f\2"+
		"\u00dd\u00de\7\"\2\2\u00de\u00e8\3\2\2\2\u00df\u00e0\7.\2\2\u00e0\u00e1"+
		"\5\26\f\2\u00e1\u00e2\7\"\2\2\u00e2\u00e8\3\2\2\2\u00e3\u00e4\5\36\20"+
		"\2\u00e4\u00e5\7\4\2\2\u00e5\u00e6\7C\2\2\u00e6\u00e8\3\2\2\2\u00e7\u00cd"+
		"\3\2\2\2\u00e7\u00ce\3\2\2\2\u00e7\u00cf\3\2\2\2\u00e7\u00d0\3\2\2\2\u00e7"+
		"\u00d1\3\2\2\2\u00e7\u00d2\3\2\2\2\u00e7\u00d3\3\2\2\2\u00e7\u00d7\3\2"+
		"\2\2\u00e7\u00db\3\2\2\2\u00e7\u00df\3\2\2\2\u00e7\u00e3\3\2\2\2\u00e8"+
		"\33\3\2\2\2\u00e9\u00f7\7\24\2\2\u00ea\u00f7\7!\2\2\u00eb\u00f7\7\20\2"+
		"\2\u00ec\u00f7\7,\2\2\u00ed\u00ee\7\17\2\2\u00ee\u00ef\5\34\17\2\u00ef"+
		"\u00f0\7:\2\2\u00f0\u00f7\3\2\2\2\u00f1\u00f2\7\t\2\2\u00f2\u00f3\5\34"+
		"\17\2\u00f3\u00f4\7:\2\2\u00f4\u00f7\3\2\2\2\u00f5\u00f7\5\36\20\2\u00f6"+
		"\u00e9\3\2\2\2\u00f6\u00ea\3\2\2\2\u00f6\u00eb\3\2\2\2\u00f6\u00ec\3\2"+
		"\2\2\u00f6\u00ed\3\2\2\2\u00f6\u00f1\3\2\2\2\u00f6\u00f5\3\2\2\2\u00f7"+
		"\35\3\2\2\2\u00f8\u00fb\7C\2\2\u00f9\u00fa\7\4\2\2\u00fa\u00fc\7C\2\2"+
		"\u00fb\u00f9\3\2\2\2\u00fb\u00fc\3\2\2\2\u00fc\37\3\2\2\2\25.:UW]n\u008a"+
		"\u008f\u0094\u0099\u00a2\u00a8\u00b6\u00c1\u00c4\u00c9\u00e7\u00f6\u00fb";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}