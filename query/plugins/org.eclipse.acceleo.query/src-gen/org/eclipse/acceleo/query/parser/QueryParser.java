// Generated from Query.g4 by ANTLR 4.10.1

/*******************************************************************************
 * Copyright (c) 2015, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
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
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, T__32=33, T__33=34, T__34=35, T__35=36, T__36=37, T__37=38, 
		T__38=39, T__39=40, T__40=41, T__41=42, T__42=43, T__43=44, T__44=45, 
		T__45=46, T__46=47, T__47=48, T__48=49, T__49=50, T__50=51, T__51=52, 
		T__52=53, WS=54, MultOp=55, Integer=56, Real=57, String=58, ErrorString=59, 
		Ident=60;
	public static final int
		RULE_entry = 0, RULE_expression = 1, RULE_binding = 2, RULE_addOp = 3, 
		RULE_compOp = 4, RULE_navigationSegment = 5, RULE_callExp = 6, RULE_lambdaExpression = 7, 
		RULE_collectionIterator = 8, RULE_expressionSequence = 9, RULE_variableDefinition = 10, 
		RULE_literal = 11, RULE_typeLiteral = 12, RULE_classifierTypeRule = 13;
	private static String[] makeRuleNames() {
		return new String[] {
			"entry", "expression", "binding", "addOp", "compOp", "navigationSegment", 
			"callExp", "lambdaExpression", "collectionIterator", "expressionSequence", 
			"variableDefinition", "literal", "typeLiteral", "classifierTypeRule"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'not'", "'-'", "'and'", "'or'", "'xor'", "'implies'", "'('", "')'", 
			"'if'", "'then'", "'else'", "'endif'", "'let'", "','", "'in'", "':'", 
			"'='", "'+'", "'<='", "'>='", "'!='", "'<>'", "'=='", "'<'", "'>'", "'.'", 
			"'->'", "'super:'", "'select'", "'reject'", "'collect'", "'any'", "'exists'", 
			"'forAll'", "'isUnique'", "'one'", "'sortedBy'", "'closure'", "'|'", 
			"'true'", "'false'", "'null'", "'Sequence{'", "'}'", "'OrderedSet{'", 
			"'::'", "'String'", "'Integer'", "'Real'", "'Boolean'", "'Sequence('", 
			"'OrderedSet('", "'{'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, "WS", "MultOp", "Integer", "Real", 
			"String", "ErrorString", "Ident"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Query.g4"; }

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
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode EOF() { return getToken(QueryParser.EOF, 0); }
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
			setState(28);
			expression(0);
			setState(29);
			match(EOF);
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
	public static class VarRefContext extends ExpressionContext {
		public TerminalNode Ident() { return getToken(QueryParser.Ident, 0); }
		public VarRefContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterVarRef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitVarRef(this);
		}
	}
	public static class AddContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
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
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NavigationSegmentContext navigationSegment() {
			return getRuleContext(NavigationSegmentContext.class,0);
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
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
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
		public BindingContext binding(int i) {
			return getRuleContext(BindingContext.class,i);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
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
	public static class ConditionalContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
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
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
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
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
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
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
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
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
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
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
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
			setState(62);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				_localctx = new NotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(32);
				match(T__0);
				setState(33);
				expression(14);
				}
				break;
			case 2:
				{
				_localctx = new MinContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(34);
				match(T__1);
				setState(35);
				expression(13);
				}
				break;
			case 3:
				{
				_localctx = new VarRefContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(36);
				match(Ident);
				}
				break;
			case 4:
				{
				_localctx = new LitContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(37);
				literal();
				}
				break;
			case 5:
				{
				_localctx = new ParenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(38);
				match(T__6);
				setState(39);
				expression(0);
				setState(40);
				match(T__7);
				}
				break;
			case 6:
				{
				_localctx = new ConditionalContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(42);
				match(T__8);
				setState(43);
				expression(0);
				setState(44);
				match(T__9);
				setState(45);
				expression(0);
				setState(46);
				match(T__10);
				setState(47);
				expression(0);
				setState(48);
				match(T__11);
				}
				break;
			case 7:
				{
				_localctx = new LetExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(50);
				match(T__12);
				setState(51);
				binding();
				setState(56);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__13) {
					{
					{
					setState(52);
					match(T__13);
					setState(53);
					binding();
					}
					}
					setState(58);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(59);
				match(T__14);
				setState(60);
				expression(1);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(91);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(89);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
					case 1:
						{
						_localctx = new MultContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(64);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(65);
						match(MultOp);
						setState(66);
						expression(13);
						}
						break;
					case 2:
						{
						_localctx = new AddContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(67);
						if (!(precpred(_ctx, 11))) throw new FailedPredicateException(this, "precpred(_ctx, 11)");
						setState(68);
						addOp();
						setState(69);
						expression(12);
						}
						break;
					case 3:
						{
						_localctx = new CompContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(71);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(72);
						compOp();
						setState(73);
						expression(11);
						}
						break;
					case 4:
						{
						_localctx = new AndContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(75);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(76);
						match(T__2);
						setState(77);
						expression(10);
						}
						break;
					case 5:
						{
						_localctx = new OrContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(78);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(79);
						match(T__3);
						setState(80);
						expression(9);
						}
						break;
					case 6:
						{
						_localctx = new XorContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(81);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(82);
						match(T__4);
						setState(83);
						expression(8);
						}
						break;
					case 7:
						{
						_localctx = new ImpliesContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(84);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(85);
						match(T__5);
						setState(86);
						expression(7);
						}
						break;
					case 8:
						{
						_localctx = new NavContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(87);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(88);
						navigationSegment();
						}
						break;
					}
					} 
				}
				setState(93);
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
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class,0);
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
			setState(94);
			match(Ident);
			setState(97);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__15) {
				{
				setState(95);
				match(T__15);
				setState(96);
				typeLiteral();
				}
			}

			setState(99);
			match(T__16);
			setState(100);
			expression(0);
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
			setState(102);
			_la = _input.LA(1);
			if ( !(_la==T__1 || _la==T__17) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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
			setState(104);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__16) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__24))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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
	public static class CallOrApplyContext extends NavigationSegmentContext {
		public CallExpContext callExp() {
			return getRuleContext(CallExpContext.class,0);
		}
		public CallOrApplyContext(NavigationSegmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterCallOrApply(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitCallOrApply(this);
		}
	}
	public static class CollectionCallContext extends NavigationSegmentContext {
		public CallExpContext callExp() {
			return getRuleContext(CallExpContext.class,0);
		}
		public CollectionCallContext(NavigationSegmentContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterCollectionCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitCollectionCall(this);
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
			setState(112);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				_localctx = new FeatureContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(106);
				match(T__25);
				setState(107);
				match(Ident);
				}
				break;
			case 2:
				_localctx = new CallOrApplyContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(108);
				match(T__25);
				setState(109);
				callExp();
				}
				break;
			case 3:
				_localctx = new CollectionCallContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(110);
				match(T__26);
				setState(111);
				callExp();
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
	public static class ServiceCallContext extends CallExpContext {
		public TerminalNode Ident() { return getToken(QueryParser.Ident, 0); }
		public ExpressionSequenceContext expressionSequence() {
			return getRuleContext(ExpressionSequenceContext.class,0);
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
	public static class IterationCallContext extends CallExpContext {
		public CollectionIteratorContext collectionIterator() {
			return getRuleContext(CollectionIteratorContext.class,0);
		}
		public VariableDefinitionContext variableDefinition() {
			return getRuleContext(VariableDefinitionContext.class,0);
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

	public final CallExpContext callExp() throws RecognitionException {
		CallExpContext _localctx = new CallExpContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_callExp);
		int _la;
		try {
			setState(128);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__28:
			case T__29:
			case T__30:
			case T__31:
			case T__32:
			case T__33:
			case T__34:
			case T__35:
			case T__36:
			case T__37:
				_localctx = new IterationCallContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(114);
				collectionIterator();
				setState(115);
				match(T__6);
				setState(116);
				variableDefinition();
				setState(117);
				lambdaExpression();
				setState(118);
				match(T__7);
				}
				break;
			case T__27:
			case Ident:
				_localctx = new ServiceCallContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(121);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__27) {
					{
					setState(120);
					match(T__27);
					}
				}

				setState(123);
				match(Ident);
				setState(124);
				match(T__6);
				setState(125);
				expressionSequence();
				setState(126);
				match(T__7);
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
			setState(130);
			expression(0);
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
			setState(132);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__28) | (1L << T__29) | (1L << T__30) | (1L << T__31) | (1L << T__32) | (1L << T__33) | (1L << T__34) | (1L << T__35) | (1L << T__36) | (1L << T__37))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class ExpressionSequenceContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
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
			setState(142);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__6) | (1L << T__8) | (1L << T__12) | (1L << T__39) | (1L << T__40) | (1L << T__41) | (1L << T__42) | (1L << T__44) | (1L << T__46) | (1L << T__47) | (1L << T__48) | (1L << T__49) | (1L << T__50) | (1L << T__51) | (1L << T__52) | (1L << Integer) | (1L << Real) | (1L << String) | (1L << ErrorString) | (1L << Ident))) != 0)) {
				{
				setState(134);
				expression(0);
				setState(139);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__13) {
					{
					{
					setState(135);
					match(T__13);
					setState(136);
					expression(0);
					}
					}
					setState(141);
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
			setState(144);
			match(Ident);
			setState(147);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__15) {
				{
				setState(145);
				match(T__15);
				setState(146);
				typeLiteral();
				}
			}

			setState(149);
			match(T__38);
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
	public static class EnumLitContext extends LiteralContext {
		public List<TerminalNode> Ident() { return getTokens(QueryParser.Ident); }
		public TerminalNode Ident(int i) {
			return getToken(QueryParser.Ident, i);
		}
		public EnumLitContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterEnumLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitEnumLit(this);
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
	public static class TypeLitContext extends LiteralContext {
		public TypeLiteralContext typeLiteral() {
			return getRuleContext(TypeLiteralContext.class,0);
		}
		public TypeLitContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterTypeLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitTypeLit(this);
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
	public static class ErrorEnumLitContext extends LiteralContext {
		public List<TerminalNode> Ident() { return getTokens(QueryParser.Ident); }
		public TerminalNode Ident(int i) {
			return getToken(QueryParser.Ident, i);
		}
		public ErrorEnumLitContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterErrorEnumLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitErrorEnumLit(this);
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
		enterRule(_localctx, 22, RULE_literal);
		try {
			setState(176);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				_localctx = new StringLitContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(151);
				match(String);
				}
				break;
			case 2:
				_localctx = new ErrorStringLitContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(152);
				match(ErrorString);
				}
				break;
			case 3:
				_localctx = new IntegerLitContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(153);
				match(Integer);
				}
				break;
			case 4:
				_localctx = new RealLitContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(154);
				match(Real);
				}
				break;
			case 5:
				_localctx = new TrueLitContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(155);
				match(T__39);
				}
				break;
			case 6:
				_localctx = new FalseLitContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(156);
				match(T__40);
				}
				break;
			case 7:
				_localctx = new NullLitContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(157);
				match(T__41);
				}
				break;
			case 8:
				_localctx = new ExplicitSeqLitContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(158);
				match(T__42);
				setState(159);
				expressionSequence();
				setState(160);
				match(T__43);
				}
				break;
			case 9:
				_localctx = new ExplicitSetLitContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(162);
				match(T__44);
				setState(163);
				expressionSequence();
				setState(164);
				match(T__43);
				}
				break;
			case 10:
				_localctx = new EnumLitContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(166);
				match(Ident);
				setState(167);
				match(T__45);
				setState(168);
				match(Ident);
				setState(169);
				match(T__45);
				setState(170);
				match(Ident);
				}
				break;
			case 11:
				_localctx = new ErrorEnumLitContext(_localctx);
				enterOuterAlt(_localctx, 11);
				{
				setState(171);
				match(Ident);
				setState(172);
				match(T__45);
				setState(173);
				match(Ident);
				setState(174);
				match(T__15);
				}
				break;
			case 12:
				_localctx = new TypeLitContext(_localctx);
				enterOuterAlt(_localctx, 12);
				{
				setState(175);
				typeLiteral();
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
	public static class ClassifierSetTypeContext extends TypeLiteralContext {
		public List<ClassifierTypeRuleContext> classifierTypeRule() {
			return getRuleContexts(ClassifierTypeRuleContext.class);
		}
		public ClassifierTypeRuleContext classifierTypeRule(int i) {
			return getRuleContext(ClassifierTypeRuleContext.class,i);
		}
		public ClassifierSetTypeContext(TypeLiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterClassifierSetType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitClassifierSetType(this);
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
	public static class ClsTypeContext extends TypeLiteralContext {
		public ClassifierTypeRuleContext classifierTypeRule() {
			return getRuleContext(ClassifierTypeRuleContext.class,0);
		}
		public ClsTypeContext(TypeLiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterClsType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitClsType(this);
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
		enterRule(_localctx, 24, RULE_typeLiteral);
		int _la;
		try {
			setState(202);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__46:
				_localctx = new StrTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(178);
				match(T__46);
				}
				break;
			case T__47:
				_localctx = new IntTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(179);
				match(T__47);
				}
				break;
			case T__48:
				_localctx = new RealTypeContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(180);
				match(T__48);
				}
				break;
			case T__49:
				_localctx = new BooleanTypeContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(181);
				match(T__49);
				}
				break;
			case T__50:
				_localctx = new SeqTypeContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(182);
				match(T__50);
				setState(183);
				typeLiteral();
				setState(184);
				match(T__7);
				}
				break;
			case T__51:
				_localctx = new SetTypeContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(186);
				match(T__51);
				setState(187);
				typeLiteral();
				setState(188);
				match(T__7);
				}
				break;
			case Ident:
				_localctx = new ClsTypeContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(190);
				classifierTypeRule();
				}
				break;
			case T__52:
				_localctx = new ClassifierSetTypeContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(191);
				match(T__52);
				setState(192);
				classifierTypeRule();
				setState(197);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__38) {
					{
					{
					setState(193);
					match(T__38);
					setState(194);
					classifierTypeRule();
					}
					}
					setState(199);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(200);
				match(T__43);
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

	public static class ClassifierTypeRuleContext extends ParserRuleContext {
		public ClassifierTypeRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classifierTypeRule; }
	 
		public ClassifierTypeRuleContext() { }
		public void copyFrom(ClassifierTypeRuleContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ClassifierTypeContext extends ClassifierTypeRuleContext {
		public List<TerminalNode> Ident() { return getTokens(QueryParser.Ident); }
		public TerminalNode Ident(int i) {
			return getToken(QueryParser.Ident, i);
		}
		public ClassifierTypeContext(ClassifierTypeRuleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterClassifierType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitClassifierType(this);
		}
	}
	public static class ErrorClassifierTypeContext extends ClassifierTypeRuleContext {
		public TerminalNode Ident() { return getToken(QueryParser.Ident, 0); }
		public ErrorClassifierTypeContext(ClassifierTypeRuleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).enterErrorClassifierType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof QueryListener ) ((QueryListener)listener).exitErrorClassifierType(this);
		}
	}

	public final ClassifierTypeRuleContext classifierTypeRule() throws RecognitionException {
		ClassifierTypeRuleContext _localctx = new ClassifierTypeRuleContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_classifierTypeRule);
		try {
			setState(209);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				_localctx = new ClassifierTypeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(204);
				match(Ident);
				setState(205);
				match(T__45);
				setState(206);
				match(Ident);
				}
				break;
			case 2:
				_localctx = new ErrorClassifierTypeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(207);
				match(Ident);
				setState(208);
				match(T__15);
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
			return precpred(_ctx, 12);
		case 1:
			return precpred(_ctx, 11);
		case 2:
			return precpred(_ctx, 10);
		case 3:
			return precpred(_ctx, 9);
		case 4:
			return precpred(_ctx, 8);
		case 5:
			return precpred(_ctx, 7);
		case 6:
			return precpred(_ctx, 6);
		case 7:
			return precpred(_ctx, 15);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001<\u00d4\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u00017\b\u0001"+
		"\n\u0001\f\u0001:\t\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0003\u0001"+
		"?\b\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0005\u0001Z\b\u0001\n\u0001\f\u0001]\t\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002b\b\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0003\u0005q\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006z\b\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006\u0081\b\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0005"+
		"\t\u008a\b\t\n\t\f\t\u008d\t\t\u0003\t\u008f\b\t\u0001\n\u0001\n\u0001"+
		"\n\u0003\n\u0094\b\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u00b1\b\u000b"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0005"+
		"\f\u00c4\b\f\n\f\f\f\u00c7\t\f\u0001\f\u0001\f\u0003\f\u00cb\b\f\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003\r\u00d2\b\r\u0001\r\u0000\u0001"+
		"\u0002\u000e\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016"+
		"\u0018\u001a\u0000\u0003\u0002\u0000\u0002\u0002\u0012\u0012\u0002\u0000"+
		"\u0011\u0011\u0013\u0019\u0001\u0000\u001d&\u00f0\u0000\u001c\u0001\u0000"+
		"\u0000\u0000\u0002>\u0001\u0000\u0000\u0000\u0004^\u0001\u0000\u0000\u0000"+
		"\u0006f\u0001\u0000\u0000\u0000\bh\u0001\u0000\u0000\u0000\np\u0001\u0000"+
		"\u0000\u0000\f\u0080\u0001\u0000\u0000\u0000\u000e\u0082\u0001\u0000\u0000"+
		"\u0000\u0010\u0084\u0001\u0000\u0000\u0000\u0012\u008e\u0001\u0000\u0000"+
		"\u0000\u0014\u0090\u0001\u0000\u0000\u0000\u0016\u00b0\u0001\u0000\u0000"+
		"\u0000\u0018\u00ca\u0001\u0000\u0000\u0000\u001a\u00d1\u0001\u0000\u0000"+
		"\u0000\u001c\u001d\u0003\u0002\u0001\u0000\u001d\u001e\u0005\u0000\u0000"+
		"\u0001\u001e\u0001\u0001\u0000\u0000\u0000\u001f \u0006\u0001\uffff\uffff"+
		"\u0000 !\u0005\u0001\u0000\u0000!?\u0003\u0002\u0001\u000e\"#\u0005\u0002"+
		"\u0000\u0000#?\u0003\u0002\u0001\r$?\u0005<\u0000\u0000%?\u0003\u0016"+
		"\u000b\u0000&\'\u0005\u0007\u0000\u0000\'(\u0003\u0002\u0001\u0000()\u0005"+
		"\b\u0000\u0000)?\u0001\u0000\u0000\u0000*+\u0005\t\u0000\u0000+,\u0003"+
		"\u0002\u0001\u0000,-\u0005\n\u0000\u0000-.\u0003\u0002\u0001\u0000./\u0005"+
		"\u000b\u0000\u0000/0\u0003\u0002\u0001\u000001\u0005\f\u0000\u00001?\u0001"+
		"\u0000\u0000\u000023\u0005\r\u0000\u000038\u0003\u0004\u0002\u000045\u0005"+
		"\u000e\u0000\u000057\u0003\u0004\u0002\u000064\u0001\u0000\u0000\u0000"+
		"7:\u0001\u0000\u0000\u000086\u0001\u0000\u0000\u000089\u0001\u0000\u0000"+
		"\u00009;\u0001\u0000\u0000\u0000:8\u0001\u0000\u0000\u0000;<\u0005\u000f"+
		"\u0000\u0000<=\u0003\u0002\u0001\u0001=?\u0001\u0000\u0000\u0000>\u001f"+
		"\u0001\u0000\u0000\u0000>\"\u0001\u0000\u0000\u0000>$\u0001\u0000\u0000"+
		"\u0000>%\u0001\u0000\u0000\u0000>&\u0001\u0000\u0000\u0000>*\u0001\u0000"+
		"\u0000\u0000>2\u0001\u0000\u0000\u0000?[\u0001\u0000\u0000\u0000@A\n\f"+
		"\u0000\u0000AB\u00057\u0000\u0000BZ\u0003\u0002\u0001\rCD\n\u000b\u0000"+
		"\u0000DE\u0003\u0006\u0003\u0000EF\u0003\u0002\u0001\fFZ\u0001\u0000\u0000"+
		"\u0000GH\n\n\u0000\u0000HI\u0003\b\u0004\u0000IJ\u0003\u0002\u0001\u000b"+
		"JZ\u0001\u0000\u0000\u0000KL\n\t\u0000\u0000LM\u0005\u0003\u0000\u0000"+
		"MZ\u0003\u0002\u0001\nNO\n\b\u0000\u0000OP\u0005\u0004\u0000\u0000PZ\u0003"+
		"\u0002\u0001\tQR\n\u0007\u0000\u0000RS\u0005\u0005\u0000\u0000SZ\u0003"+
		"\u0002\u0001\bTU\n\u0006\u0000\u0000UV\u0005\u0006\u0000\u0000VZ\u0003"+
		"\u0002\u0001\u0007WX\n\u000f\u0000\u0000XZ\u0003\n\u0005\u0000Y@\u0001"+
		"\u0000\u0000\u0000YC\u0001\u0000\u0000\u0000YG\u0001\u0000\u0000\u0000"+
		"YK\u0001\u0000\u0000\u0000YN\u0001\u0000\u0000\u0000YQ\u0001\u0000\u0000"+
		"\u0000YT\u0001\u0000\u0000\u0000YW\u0001\u0000\u0000\u0000Z]\u0001\u0000"+
		"\u0000\u0000[Y\u0001\u0000\u0000\u0000[\\\u0001\u0000\u0000\u0000\\\u0003"+
		"\u0001\u0000\u0000\u0000][\u0001\u0000\u0000\u0000^a\u0005<\u0000\u0000"+
		"_`\u0005\u0010\u0000\u0000`b\u0003\u0018\f\u0000a_\u0001\u0000\u0000\u0000"+
		"ab\u0001\u0000\u0000\u0000bc\u0001\u0000\u0000\u0000cd\u0005\u0011\u0000"+
		"\u0000de\u0003\u0002\u0001\u0000e\u0005\u0001\u0000\u0000\u0000fg\u0007"+
		"\u0000\u0000\u0000g\u0007\u0001\u0000\u0000\u0000hi\u0007\u0001\u0000"+
		"\u0000i\t\u0001\u0000\u0000\u0000jk\u0005\u001a\u0000\u0000kq\u0005<\u0000"+
		"\u0000lm\u0005\u001a\u0000\u0000mq\u0003\f\u0006\u0000no\u0005\u001b\u0000"+
		"\u0000oq\u0003\f\u0006\u0000pj\u0001\u0000\u0000\u0000pl\u0001\u0000\u0000"+
		"\u0000pn\u0001\u0000\u0000\u0000q\u000b\u0001\u0000\u0000\u0000rs\u0003"+
		"\u0010\b\u0000st\u0005\u0007\u0000\u0000tu\u0003\u0014\n\u0000uv\u0003"+
		"\u000e\u0007\u0000vw\u0005\b\u0000\u0000w\u0081\u0001\u0000\u0000\u0000"+
		"xz\u0005\u001c\u0000\u0000yx\u0001\u0000\u0000\u0000yz\u0001\u0000\u0000"+
		"\u0000z{\u0001\u0000\u0000\u0000{|\u0005<\u0000\u0000|}\u0005\u0007\u0000"+
		"\u0000}~\u0003\u0012\t\u0000~\u007f\u0005\b\u0000\u0000\u007f\u0081\u0001"+
		"\u0000\u0000\u0000\u0080r\u0001\u0000\u0000\u0000\u0080y\u0001\u0000\u0000"+
		"\u0000\u0081\r\u0001\u0000\u0000\u0000\u0082\u0083\u0003\u0002\u0001\u0000"+
		"\u0083\u000f\u0001\u0000\u0000\u0000\u0084\u0085\u0007\u0002\u0000\u0000"+
		"\u0085\u0011\u0001\u0000\u0000\u0000\u0086\u008b\u0003\u0002\u0001\u0000"+
		"\u0087\u0088\u0005\u000e\u0000\u0000\u0088\u008a\u0003\u0002\u0001\u0000"+
		"\u0089\u0087\u0001\u0000\u0000\u0000\u008a\u008d\u0001\u0000\u0000\u0000"+
		"\u008b\u0089\u0001\u0000\u0000\u0000\u008b\u008c\u0001\u0000\u0000\u0000"+
		"\u008c\u008f\u0001\u0000\u0000\u0000\u008d\u008b\u0001\u0000\u0000\u0000"+
		"\u008e\u0086\u0001\u0000\u0000\u0000\u008e\u008f\u0001\u0000\u0000\u0000"+
		"\u008f\u0013\u0001\u0000\u0000\u0000\u0090\u0093\u0005<\u0000\u0000\u0091"+
		"\u0092\u0005\u0010\u0000\u0000\u0092\u0094\u0003\u0018\f\u0000\u0093\u0091"+
		"\u0001\u0000\u0000\u0000\u0093\u0094\u0001\u0000\u0000\u0000\u0094\u0095"+
		"\u0001\u0000\u0000\u0000\u0095\u0096\u0005\'\u0000\u0000\u0096\u0015\u0001"+
		"\u0000\u0000\u0000\u0097\u00b1\u0005:\u0000\u0000\u0098\u00b1\u0005;\u0000"+
		"\u0000\u0099\u00b1\u00058\u0000\u0000\u009a\u00b1\u00059\u0000\u0000\u009b"+
		"\u00b1\u0005(\u0000\u0000\u009c\u00b1\u0005)\u0000\u0000\u009d\u00b1\u0005"+
		"*\u0000\u0000\u009e\u009f\u0005+\u0000\u0000\u009f\u00a0\u0003\u0012\t"+
		"\u0000\u00a0\u00a1\u0005,\u0000\u0000\u00a1\u00b1\u0001\u0000\u0000\u0000"+
		"\u00a2\u00a3\u0005-\u0000\u0000\u00a3\u00a4\u0003\u0012\t\u0000\u00a4"+
		"\u00a5\u0005,\u0000\u0000\u00a5\u00b1\u0001\u0000\u0000\u0000\u00a6\u00a7"+
		"\u0005<\u0000\u0000\u00a7\u00a8\u0005.\u0000\u0000\u00a8\u00a9\u0005<"+
		"\u0000\u0000\u00a9\u00aa\u0005.\u0000\u0000\u00aa\u00b1\u0005<\u0000\u0000"+
		"\u00ab\u00ac\u0005<\u0000\u0000\u00ac\u00ad\u0005.\u0000\u0000\u00ad\u00ae"+
		"\u0005<\u0000\u0000\u00ae\u00b1\u0005\u0010\u0000\u0000\u00af\u00b1\u0003"+
		"\u0018\f\u0000\u00b0\u0097\u0001\u0000\u0000\u0000\u00b0\u0098\u0001\u0000"+
		"\u0000\u0000\u00b0\u0099\u0001\u0000\u0000\u0000\u00b0\u009a\u0001\u0000"+
		"\u0000\u0000\u00b0\u009b\u0001\u0000\u0000\u0000\u00b0\u009c\u0001\u0000"+
		"\u0000\u0000\u00b0\u009d\u0001\u0000\u0000\u0000\u00b0\u009e\u0001\u0000"+
		"\u0000\u0000\u00b0\u00a2\u0001\u0000\u0000\u0000\u00b0\u00a6\u0001\u0000"+
		"\u0000\u0000\u00b0\u00ab\u0001\u0000\u0000\u0000\u00b0\u00af\u0001\u0000"+
		"\u0000\u0000\u00b1\u0017\u0001\u0000\u0000\u0000\u00b2\u00cb\u0005/\u0000"+
		"\u0000\u00b3\u00cb\u00050\u0000\u0000\u00b4\u00cb\u00051\u0000\u0000\u00b5"+
		"\u00cb\u00052\u0000\u0000\u00b6\u00b7\u00053\u0000\u0000\u00b7\u00b8\u0003"+
		"\u0018\f\u0000\u00b8\u00b9\u0005\b\u0000\u0000\u00b9\u00cb\u0001\u0000"+
		"\u0000\u0000\u00ba\u00bb\u00054\u0000\u0000\u00bb\u00bc\u0003\u0018\f"+
		"\u0000\u00bc\u00bd\u0005\b\u0000\u0000\u00bd\u00cb\u0001\u0000\u0000\u0000"+
		"\u00be\u00cb\u0003\u001a\r\u0000\u00bf\u00c0\u00055\u0000\u0000\u00c0"+
		"\u00c5\u0003\u001a\r\u0000\u00c1\u00c2\u0005\'\u0000\u0000\u00c2\u00c4"+
		"\u0003\u001a\r\u0000\u00c3\u00c1\u0001\u0000\u0000\u0000\u00c4\u00c7\u0001"+
		"\u0000\u0000\u0000\u00c5\u00c3\u0001\u0000\u0000\u0000\u00c5\u00c6\u0001"+
		"\u0000\u0000\u0000\u00c6\u00c8\u0001\u0000\u0000\u0000\u00c7\u00c5\u0001"+
		"\u0000\u0000\u0000\u00c8\u00c9\u0005,\u0000\u0000\u00c9\u00cb\u0001\u0000"+
		"\u0000\u0000\u00ca\u00b2\u0001\u0000\u0000\u0000\u00ca\u00b3\u0001\u0000"+
		"\u0000\u0000\u00ca\u00b4\u0001\u0000\u0000\u0000\u00ca\u00b5\u0001\u0000"+
		"\u0000\u0000\u00ca\u00b6\u0001\u0000\u0000\u0000\u00ca\u00ba\u0001\u0000"+
		"\u0000\u0000\u00ca\u00be\u0001\u0000\u0000\u0000\u00ca\u00bf\u0001\u0000"+
		"\u0000\u0000\u00cb\u0019\u0001\u0000\u0000\u0000\u00cc\u00cd\u0005<\u0000"+
		"\u0000\u00cd\u00ce\u0005.\u0000\u0000\u00ce\u00d2\u0005<\u0000\u0000\u00cf"+
		"\u00d0\u0005<\u0000\u0000\u00d0\u00d2\u0005\u0010\u0000\u0000\u00d1\u00cc"+
		"\u0001\u0000\u0000\u0000\u00d1\u00cf\u0001\u0000\u0000\u0000\u00d2\u001b"+
		"\u0001\u0000\u0000\u0000\u000f8>Y[apy\u0080\u008b\u008e\u0093\u00b0\u00c5"+
		"\u00ca\u00d1";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}