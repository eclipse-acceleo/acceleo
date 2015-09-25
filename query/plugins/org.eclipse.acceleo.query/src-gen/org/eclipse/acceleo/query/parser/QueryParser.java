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
		T__63=1, T__62=2, T__61=3, T__60=4, T__59=5, T__58=6, T__57=7, T__56=8, 
		T__55=9, T__54=10, T__53=11, T__52=12, T__51=13, T__50=14, T__49=15, T__48=16, 
		T__47=17, T__46=18, T__45=19, T__44=20, T__43=21, T__42=22, T__41=23, 
		T__40=24, T__39=25, T__38=26, T__37=27, T__36=28, T__35=29, T__34=30, 
		T__33=31, T__32=32, T__31=33, T__30=34, T__29=35, T__28=36, T__27=37, 
		T__26=38, T__25=39, T__24=40, T__23=41, T__22=42, T__21=43, T__20=44, 
		T__19=45, T__18=46, T__17=47, T__16=48, T__15=49, T__14=50, T__13=51, 
		T__12=52, T__11=53, T__10=54, T__9=55, T__8=56, T__7=57, T__6=58, T__5=59, 
		T__4=60, T__3=61, T__2=62, T__1=63, T__0=64, WS=65, MultOp=66, Integer=67, 
		Real=68, String=69, ErrorString=70, Ident=71;
	public static final String[] tokenNames = {
		"<INVALID>", "'endif'", "'{'", "'::'", "'one'", "'='", "'oclAsType('", 
		"'filter('", "'OrderedSet('", "'null'", "'('", "'implies'", "','", "'false'", 
		"'Sequence('", "'Real'", "'oclIsTypeOf('", "'reject'", "'>='", "'String'", 
		"'<'", "']'", "'forAll'", "'Sequence{'", "'precedingSiblings('", "'<>'", 
		"'let'", "'+'", "'then'", "'oclIsKindOf('", "'eInverse('", "'true'", "'eContents('", 
		"'Integer'", "'}'", "'any'", "'siblings('", "'if'", "'eContainerOrSelf('", 
		"'<='", "'sortedBy'", "'eAllContents('", "'isUnique'", "'collect'", "'exists'", 
		"'.'", "'Boolean'", "'->'", "'OrderedSet{'", "'eContainer('", "':'", "'['", 
		"'|'", "'select'", "'>'", "'xor'", "'or'", "'as('", "'in'", "'else'", 
		"'is('", "')'", "'and'", "'not'", "'-'", "WS", "MultOp", "Integer", "Real", 
		"String", "ErrorString", "Ident"
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
	public static class ConditionalContext extends ExpressionContext {
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ConditionalContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterConditional(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitConditional(this);
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
			setState(64);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				_localctx = new NotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(34); match(T__1);
				setState(35); expression(14);
				}
				break;

			case 2:
				{
				_localctx = new MinContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(36); match(T__0);
				setState(37); expression(13);
				}
				break;

			case 3:
				{
				_localctx = new LetExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(38); match(T__38);
				setState(39); binding();
				setState(44);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__52) {
					{
					{
					setState(40); match(T__52);
					setState(41); binding();
					}
					}
					setState(46);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(47); match(T__6);
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
				setState(52); match(T__54);
				setState(53); expression(0);
				setState(54); match(T__3);
				}
				break;

			case 7:
				{
				_localctx = new ConditionalContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(56); match(T__27);
				setState(57); expression(0);
				setState(58); match(T__36);
				setState(59); expression(0);
				setState(60); match(T__5);
				setState(61); expression(0);
				setState(62); match(T__63);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(93);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(91);
					switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
					case 1:
						{
						_localctx = new MultContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(66);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(67); match(MultOp);
						setState(68); expression(13);
						}
						break;

					case 2:
						{
						_localctx = new AddContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(69);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(70); addOp();
						setState(71); expression(12);
						}
						break;

					case 3:
						{
						_localctx = new CompContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(73);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(74); compOp();
						setState(75); expression(11);
						}
						break;

					case 4:
						{
						_localctx = new AndContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(77);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(78); match(T__2);
						setState(79); expression(10);
						}
						break;

					case 5:
						{
						_localctx = new OrContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(80);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(81); match(T__8);
						setState(82); expression(9);
						}
						break;

					case 6:
						{
						_localctx = new XorContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(83);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(84); match(T__9);
						setState(85); expression(8);
						}
						break;

					case 7:
						{
						_localctx = new ImpliesContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(86);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(87); match(T__53);
						setState(88); expression(7);
						}
						break;

					case 8:
						{
						_localctx = new NavContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(89);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(90); navigationSegment();
						}
						break;
					}
					} 
				}
				setState(95);
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
			setState(96); match(Ident);
			setState(99);
			_la = _input.LA(1);
			if (_la==T__14) {
				{
				setState(97); match(T__14);
				setState(98); typeLiteral();
				}
			}

			setState(101); match(T__59);
			setState(102); expression(0);
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
			setState(104);
			_la = _input.LA(1);
			if ( !(_la==T__37 || _la==T__0) ) {
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
			setState(106);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__59) | (1L << T__46) | (1L << T__44) | (1L << T__39) | (1L << T__25) | (1L << T__10))) != 0)) ) {
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
			setState(108); match(Ident);
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
			setState(116);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				_localctx = new FeatureContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(110); match(T__19);
				setState(111); match(Ident);
				}
				break;

			case 2:
				_localctx = new ApplyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(112); match(T__19);
				setState(113); callExp();
				}
				break;

			case 3:
				_localctx = new CallServiceContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(114); match(T__17);
				setState(115); callExp();
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
			setState(188);
			switch (_input.LA(1)) {
			case T__57:
				_localctx = new FilterContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(118); match(T__57);
				setState(119); typeLiteral();
				setState(120); match(T__3);
				}
				break;
			case T__58:
				_localctx = new AsTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(122); match(T__58);
				setState(123); typeLiteral();
				setState(124); match(T__3);
				}
				break;
			case T__7:
				_localctx = new AsContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(126); match(T__7);
				setState(127); typeLiteral();
				setState(128); match(T__3);
				}
				break;
			case T__35:
				_localctx = new IsKindContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(130); match(T__35);
				setState(131); typeLiteral();
				setState(132); match(T__3);
				}
				break;
			case T__48:
				_localctx = new IsTypeContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(134); match(T__48);
				setState(135); typeLiteral();
				setState(136); match(T__3);
				}
				break;
			case T__4:
				_localctx = new IsContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(138); match(T__4);
				setState(139); typeLiteral();
				setState(140); match(T__3);
				}
				break;
			case T__40:
				_localctx = new PrecSiblingsContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(142); match(T__40);
				setState(144);
				_la = _input.LA(1);
				if (((((_la - 8)) & ~0x3f) == 0 && ((1L << (_la - 8)) & ((1L << (T__56 - 8)) | (1L << (T__50 - 8)) | (1L << (T__49 - 8)) | (1L << (T__45 - 8)) | (1L << (T__31 - 8)) | (1L << (T__18 - 8)) | (1L << (Ident - 8)))) != 0)) {
					{
					setState(143); typeLiteral();
					}
				}

				setState(146); match(T__3);
				}
				break;
			case T__28:
				_localctx = new SiblingsContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(147); match(T__28);
				setState(149);
				_la = _input.LA(1);
				if (((((_la - 8)) & ~0x3f) == 0 && ((1L << (_la - 8)) & ((1L << (T__56 - 8)) | (1L << (T__50 - 8)) | (1L << (T__49 - 8)) | (1L << (T__45 - 8)) | (1L << (T__31 - 8)) | (1L << (T__18 - 8)) | (1L << (Ident - 8)))) != 0)) {
					{
					setState(148); typeLiteral();
					}
				}

				setState(151); match(T__3);
				}
				break;
			case T__32:
				_localctx = new EContentContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(152); match(T__32);
				setState(154);
				_la = _input.LA(1);
				if (((((_la - 8)) & ~0x3f) == 0 && ((1L << (_la - 8)) & ((1L << (T__56 - 8)) | (1L << (T__50 - 8)) | (1L << (T__49 - 8)) | (1L << (T__45 - 8)) | (1L << (T__31 - 8)) | (1L << (T__18 - 8)) | (1L << (Ident - 8)))) != 0)) {
					{
					setState(153); typeLiteral();
					}
				}

				setState(156); match(T__3);
				}
				break;
			case T__23:
				_localctx = new EAContentContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(157); match(T__23);
				setState(159);
				_la = _input.LA(1);
				if (((((_la - 8)) & ~0x3f) == 0 && ((1L << (_la - 8)) & ((1L << (T__56 - 8)) | (1L << (T__50 - 8)) | (1L << (T__49 - 8)) | (1L << (T__45 - 8)) | (1L << (T__31 - 8)) | (1L << (T__18 - 8)) | (1L << (Ident - 8)))) != 0)) {
					{
					setState(158); typeLiteral();
					}
				}

				setState(161); match(T__3);
				}
				break;
			case T__26:
				_localctx = new EContainerOrSelfContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(162); match(T__26);
				setState(163); typeLiteral();
				setState(164); match(T__3);
				}
				break;
			case T__15:
				_localctx = new EContainerContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(166); match(T__15);
				setState(168);
				_la = _input.LA(1);
				if (((((_la - 8)) & ~0x3f) == 0 && ((1L << (_la - 8)) & ((1L << (T__56 - 8)) | (1L << (T__50 - 8)) | (1L << (T__49 - 8)) | (1L << (T__45 - 8)) | (1L << (T__31 - 8)) | (1L << (T__18 - 8)) | (1L << (Ident - 8)))) != 0)) {
					{
					setState(167); typeLiteral();
					}
				}

				setState(170); match(T__3);
				}
				break;
			case T__34:
				_localctx = new EInverseContext(_localctx);
				enterOuterAlt(_localctx, 13);
				{
				setState(171); match(T__34);
				setState(174);
				switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
				case 1:
					{
					setState(172); typeLiteral();
					}
					break;

				case 2:
					{
					setState(173); expression(0);
					}
					break;
				}
				setState(176); match(T__3);
				}
				break;
			case T__60:
			case T__47:
			case T__42:
			case T__29:
			case T__24:
			case T__22:
			case T__21:
			case T__20:
			case T__11:
				_localctx = new IterationCallContext(_localctx);
				enterOuterAlt(_localctx, 14);
				{
				setState(177); collectionIterator();
				setState(178); match(T__54);
				setState(179); variableDefinition();
				setState(180); lambdaExpression();
				setState(181); match(T__3);
				}
				break;
			case Ident:
				_localctx = new ServiceCallContext(_localctx);
				enterOuterAlt(_localctx, 15);
				{
				setState(183); qualifiedName();
				setState(184); match(T__54);
				setState(185); expressionSequence();
				setState(186); match(T__3);
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
			setState(190); expression(0);
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
			setState(192);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__60) | (1L << T__47) | (1L << T__42) | (1L << T__29) | (1L << T__24) | (1L << T__22) | (1L << T__21) | (1L << T__20) | (1L << T__11))) != 0)) ) {
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
			setState(202);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__62) | (1L << T__55) | (1L << T__54) | (1L << T__51) | (1L << T__41) | (1L << T__38) | (1L << T__33) | (1L << T__27) | (1L << T__16) | (1L << T__13) | (1L << T__1))) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & ((1L << (T__0 - 64)) | (1L << (Integer - 64)) | (1L << (Real - 64)) | (1L << (String - 64)) | (1L << (ErrorString - 64)) | (1L << (Ident - 64)))) != 0)) {
				{
				setState(194); expression(0);
				setState(199);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__52) {
					{
					{
					setState(195); match(T__52);
					setState(196); expression(0);
					}
					}
					setState(201);
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
			setState(204); match(Ident);
			setState(207);
			_la = _input.LA(1);
			if (_la==T__14) {
				{
				setState(205); match(T__14);
				setState(206); typeLiteral();
				}
			}

			setState(209); match(T__12);
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
	public static class ErrorStringLitContext extends LiteralContext {
		public TerminalNode ErrorString() { return getToken(QueryParser.ErrorString, 0); }
		public ErrorStringLitContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterErrorStringLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitErrorStringLit(this);
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
			setState(238);
			switch (_input.LA(1)) {
			case String:
				_localctx = new StringLitContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(211); match(String);
				}
				break;
			case ErrorString:
				_localctx = new ErrorStringLitContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(212); match(ErrorString);
				}
				break;
			case Integer:
				_localctx = new IntegerLitContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(213); match(Integer);
				}
				break;
			case Real:
				_localctx = new RealLitContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(214); match(Real);
				}
				break;
			case T__33:
				_localctx = new TrueLitContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(215); match(T__33);
				}
				break;
			case T__51:
				_localctx = new FalseLitContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(216); match(T__51);
				}
				break;
			case T__55:
				_localctx = new NullLitContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(217); match(T__55);
				}
				break;
			case T__62:
				_localctx = new SetLitContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(218); match(T__62);
				setState(219); expressionSequence();
				setState(220); match(T__30);
				}
				break;
			case T__13:
				_localctx = new SeqLitContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(222); match(T__13);
				setState(223); expressionSequence();
				setState(224); match(T__43);
				}
				break;
			case T__41:
				_localctx = new ExplicitSeqLitContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(226); match(T__41);
				setState(227); expressionSequence();
				setState(228); match(T__30);
				}
				break;
			case T__16:
				_localctx = new ExplicitSetLitContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(230); match(T__16);
				setState(231); expressionSequence();
				setState(232); match(T__30);
				}
				break;
			case Ident:
				_localctx = new EnumOrClassifierLitContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(234); qualifiedName();
				setState(235); match(T__61);
				setState(236); match(Ident);
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
			setState(253);
			switch (_input.LA(1)) {
			case T__45:
				_localctx = new StrTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(240); match(T__45);
				}
				break;
			case T__31:
				_localctx = new IntTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(241); match(T__31);
				}
				break;
			case T__49:
				_localctx = new RealTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(242); match(T__49);
				}
				break;
			case T__18:
				_localctx = new BooleanTypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(243); match(T__18);
				}
				break;
			case T__50:
				_localctx = new SeqTypeContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(244); match(T__50);
				setState(245); typeLiteral();
				setState(246); match(T__3);
				}
				break;
			case T__56:
				_localctx = new SetTypeContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(248); match(T__56);
				setState(249); typeLiteral();
				setState(250); match(T__3);
				}
				break;
			case Ident:
				_localctx = new ModelObjectTypeContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(252); qualifiedName();
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
			setState(255); match(Ident);
			setState(258);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(256); match(T__61);
				setState(257); match(Ident);
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
		case 0: return precpred(_ctx, 12);

		case 1: return precpred(_ctx, 11);

		case 2: return precpred(_ctx, 10);

		case 3: return precpred(_ctx, 9);

		case 4: return precpred(_ctx, 8);

		case 5: return precpred(_ctx, 7);

		case 6: return precpred(_ctx, 6);

		case 7: return precpred(_ctx, 15);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3I\u0107\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\3\2\3\2\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3-\n\3\f\3\16\3\60\13\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3C\n\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\7\3^\n\3\f\3\16\3a\13\3\3\4\3\4\3\4\5\4f\n\4\3"+
		"\4\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\5\bw\n\b\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u0093\n\t\3\t\3\t\3\t\5\t\u0098\n"+
		"\t\3\t\3\t\3\t\5\t\u009d\n\t\3\t\3\t\3\t\5\t\u00a2\n\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\5\t\u00ab\n\t\3\t\3\t\3\t\3\t\5\t\u00b1\n\t\3\t\3\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u00bf\n\t\3\n\3\n\3\13\3\13\3\f"+
		"\3\f\3\f\7\f\u00c8\n\f\f\f\16\f\u00cb\13\f\5\f\u00cd\n\f\3\r\3\r\3\r\5"+
		"\r\u00d2\n\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\16\3\16\5\16\u00f1\n\16\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\5\17\u0100\n\17\3\20\3\20\3\20\5\20\u0105\n"+
		"\20\3\20\2\3\4\21\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36\2\5\4\2\35\35"+
		"BB\b\2\7\7\24\24\26\26\33\33))88\t\2\6\6\23\23\30\30%%**,.\67\67\u0133"+
		"\2 \3\2\2\2\4B\3\2\2\2\6b\3\2\2\2\bj\3\2\2\2\nl\3\2\2\2\fn\3\2\2\2\16"+
		"v\3\2\2\2\20\u00be\3\2\2\2\22\u00c0\3\2\2\2\24\u00c2\3\2\2\2\26\u00cc"+
		"\3\2\2\2\30\u00ce\3\2\2\2\32\u00f0\3\2\2\2\34\u00ff\3\2\2\2\36\u0101\3"+
		"\2\2\2 !\5\4\3\2!\"\7\2\2\3\"\3\3\2\2\2#$\b\3\1\2$%\7A\2\2%C\5\4\3\20"+
		"&\'\7B\2\2\'C\5\4\3\17()\7\34\2\2).\5\6\4\2*+\7\16\2\2+-\5\6\4\2,*\3\2"+
		"\2\2-\60\3\2\2\2.,\3\2\2\2./\3\2\2\2/\61\3\2\2\2\60.\3\2\2\2\61\62\7<"+
		"\2\2\62\63\5\4\3\3\63C\3\2\2\2\64C\5\f\7\2\65C\5\32\16\2\66\67\7\f\2\2"+
		"\678\5\4\3\289\7?\2\29C\3\2\2\2:;\7\'\2\2;<\5\4\3\2<=\7\36\2\2=>\5\4\3"+
		"\2>?\7=\2\2?@\5\4\3\2@A\7\3\2\2AC\3\2\2\2B#\3\2\2\2B&\3\2\2\2B(\3\2\2"+
		"\2B\64\3\2\2\2B\65\3\2\2\2B\66\3\2\2\2B:\3\2\2\2C_\3\2\2\2DE\f\16\2\2"+
		"EF\7D\2\2F^\5\4\3\17GH\f\r\2\2HI\5\b\5\2IJ\5\4\3\16J^\3\2\2\2KL\f\f\2"+
		"\2LM\5\n\6\2MN\5\4\3\rN^\3\2\2\2OP\f\13\2\2PQ\7@\2\2Q^\5\4\3\fRS\f\n\2"+
		"\2ST\7:\2\2T^\5\4\3\13UV\f\t\2\2VW\79\2\2W^\5\4\3\nXY\f\b\2\2YZ\7\r\2"+
		"\2Z^\5\4\3\t[\\\f\21\2\2\\^\5\16\b\2]D\3\2\2\2]G\3\2\2\2]K\3\2\2\2]O\3"+
		"\2\2\2]R\3\2\2\2]U\3\2\2\2]X\3\2\2\2][\3\2\2\2^a\3\2\2\2_]\3\2\2\2_`\3"+
		"\2\2\2`\5\3\2\2\2a_\3\2\2\2be\7I\2\2cd\7\64\2\2df\5\34\17\2ec\3\2\2\2"+
		"ef\3\2\2\2fg\3\2\2\2gh\7\7\2\2hi\5\4\3\2i\7\3\2\2\2jk\t\2\2\2k\t\3\2\2"+
		"\2lm\t\3\2\2m\13\3\2\2\2no\7I\2\2o\r\3\2\2\2pq\7/\2\2qw\7I\2\2rs\7/\2"+
		"\2sw\5\20\t\2tu\7\61\2\2uw\5\20\t\2vp\3\2\2\2vr\3\2\2\2vt\3\2\2\2w\17"+
		"\3\2\2\2xy\7\t\2\2yz\5\34\17\2z{\7?\2\2{\u00bf\3\2\2\2|}\7\b\2\2}~\5\34"+
		"\17\2~\177\7?\2\2\177\u00bf\3\2\2\2\u0080\u0081\7;\2\2\u0081\u0082\5\34"+
		"\17\2\u0082\u0083\7?\2\2\u0083\u00bf\3\2\2\2\u0084\u0085\7\37\2\2\u0085"+
		"\u0086\5\34\17\2\u0086\u0087\7?\2\2\u0087\u00bf\3\2\2\2\u0088\u0089\7"+
		"\22\2\2\u0089\u008a\5\34\17\2\u008a\u008b\7?\2\2\u008b\u00bf\3\2\2\2\u008c"+
		"\u008d\7>\2\2\u008d\u008e\5\34\17\2\u008e\u008f\7?\2\2\u008f\u00bf\3\2"+
		"\2\2\u0090\u0092\7\32\2\2\u0091\u0093\5\34\17\2\u0092\u0091\3\2\2\2\u0092"+
		"\u0093\3\2\2\2\u0093\u0094\3\2\2\2\u0094\u00bf\7?\2\2\u0095\u0097\7&\2"+
		"\2\u0096\u0098\5\34\17\2\u0097\u0096\3\2\2\2\u0097\u0098\3\2\2\2\u0098"+
		"\u0099\3\2\2\2\u0099\u00bf\7?\2\2\u009a\u009c\7\"\2\2\u009b\u009d\5\34"+
		"\17\2\u009c\u009b\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u009e\3\2\2\2\u009e"+
		"\u00bf\7?\2\2\u009f\u00a1\7+\2\2\u00a0\u00a2\5\34\17\2\u00a1\u00a0\3\2"+
		"\2\2\u00a1\u00a2\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00bf\7?\2\2\u00a4"+
		"\u00a5\7(\2\2\u00a5\u00a6\5\34\17\2\u00a6\u00a7\7?\2\2\u00a7\u00bf\3\2"+
		"\2\2\u00a8\u00aa\7\63\2\2\u00a9\u00ab\5\34\17\2\u00aa\u00a9\3\2\2\2\u00aa"+
		"\u00ab\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00bf\7?\2\2\u00ad\u00b0\7 \2"+
		"\2\u00ae\u00b1\5\34\17\2\u00af\u00b1\5\4\3\2\u00b0\u00ae\3\2\2\2\u00b0"+
		"\u00af\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00bf\7?"+
		"\2\2\u00b3\u00b4\5\24\13\2\u00b4\u00b5\7\f\2\2\u00b5\u00b6\5\30\r\2\u00b6"+
		"\u00b7\5\22\n\2\u00b7\u00b8\7?\2\2\u00b8\u00bf\3\2\2\2\u00b9\u00ba\5\36"+
		"\20\2\u00ba\u00bb\7\f\2\2\u00bb\u00bc\5\26\f\2\u00bc\u00bd\7?\2\2\u00bd"+
		"\u00bf\3\2\2\2\u00bex\3\2\2\2\u00be|\3\2\2\2\u00be\u0080\3\2\2\2\u00be"+
		"\u0084\3\2\2\2\u00be\u0088\3\2\2\2\u00be\u008c\3\2\2\2\u00be\u0090\3\2"+
		"\2\2\u00be\u0095\3\2\2\2\u00be\u009a\3\2\2\2\u00be\u009f\3\2\2\2\u00be"+
		"\u00a4\3\2\2\2\u00be\u00a8\3\2\2\2\u00be\u00ad\3\2\2\2\u00be\u00b3\3\2"+
		"\2\2\u00be\u00b9\3\2\2\2\u00bf\21\3\2\2\2\u00c0\u00c1\5\4\3\2\u00c1\23"+
		"\3\2\2\2\u00c2\u00c3\t\4\2\2\u00c3\25\3\2\2\2\u00c4\u00c9\5\4\3\2\u00c5"+
		"\u00c6\7\16\2\2\u00c6\u00c8\5\4\3\2\u00c7\u00c5\3\2\2\2\u00c8\u00cb\3"+
		"\2\2\2\u00c9\u00c7\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00cd\3\2\2\2\u00cb"+
		"\u00c9\3\2\2\2\u00cc\u00c4\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd\27\3\2\2"+
		"\2\u00ce\u00d1\7I\2\2\u00cf\u00d0\7\64\2\2\u00d0\u00d2\5\34\17\2\u00d1"+
		"\u00cf\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d3\u00d4\7\66"+
		"\2\2\u00d4\31\3\2\2\2\u00d5\u00f1\7G\2\2\u00d6\u00f1\7H\2\2\u00d7\u00f1"+
		"\7E\2\2\u00d8\u00f1\7F\2\2\u00d9\u00f1\7!\2\2\u00da\u00f1\7\17\2\2\u00db"+
		"\u00f1\7\13\2\2\u00dc\u00dd\7\4\2\2\u00dd\u00de\5\26\f\2\u00de\u00df\7"+
		"$\2\2\u00df\u00f1\3\2\2\2\u00e0\u00e1\7\65\2\2\u00e1\u00e2\5\26\f\2\u00e2"+
		"\u00e3\7\27\2\2\u00e3\u00f1\3\2\2\2\u00e4\u00e5\7\31\2\2\u00e5\u00e6\5"+
		"\26\f\2\u00e6\u00e7\7$\2\2\u00e7\u00f1\3\2\2\2\u00e8\u00e9\7\62\2\2\u00e9"+
		"\u00ea\5\26\f\2\u00ea\u00eb\7$\2\2\u00eb\u00f1\3\2\2\2\u00ec\u00ed\5\36"+
		"\20\2\u00ed\u00ee\7\5\2\2\u00ee\u00ef\7I\2\2\u00ef\u00f1\3\2\2\2\u00f0"+
		"\u00d5\3\2\2\2\u00f0\u00d6\3\2\2\2\u00f0\u00d7\3\2\2\2\u00f0\u00d8\3\2"+
		"\2\2\u00f0\u00d9\3\2\2\2\u00f0\u00da\3\2\2\2\u00f0\u00db\3\2\2\2\u00f0"+
		"\u00dc\3\2\2\2\u00f0\u00e0\3\2\2\2\u00f0\u00e4\3\2\2\2\u00f0\u00e8\3\2"+
		"\2\2\u00f0\u00ec\3\2\2\2\u00f1\33\3\2\2\2\u00f2\u0100\7\25\2\2\u00f3\u0100"+
		"\7#\2\2\u00f4\u0100\7\21\2\2\u00f5\u0100\7\60\2\2\u00f6\u00f7\7\20\2\2"+
		"\u00f7\u00f8\5\34\17\2\u00f8\u00f9\7?\2\2\u00f9\u0100\3\2\2\2\u00fa\u00fb"+
		"\7\n\2\2\u00fb\u00fc\5\34\17\2\u00fc\u00fd\7?\2\2\u00fd\u0100\3\2\2\2"+
		"\u00fe\u0100\5\36\20\2\u00ff\u00f2\3\2\2\2\u00ff\u00f3\3\2\2\2\u00ff\u00f4"+
		"\3\2\2\2\u00ff\u00f5\3\2\2\2\u00ff\u00f6\3\2\2\2\u00ff\u00fa\3\2\2\2\u00ff"+
		"\u00fe\3\2\2\2\u0100\35\3\2\2\2\u0101\u0104\7I\2\2\u0102\u0103\7\5\2\2"+
		"\u0103\u0105\7I\2\2\u0104\u0102\3\2\2\2\u0104\u0105\3\2\2\2\u0105\37\3"+
		"\2\2\2\25.B]_ev\u0092\u0097\u009c\u00a1\u00aa\u00b0\u00be\u00c9\u00cc"+
		"\u00d1\u00f0\u00ff\u0104";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}