// Generated from Query.g4 by ANTLR 4.7.2

/*******************************************************************************
 * Copyright (c) 2015, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast" })
public class QueryLexer extends Lexer {
	static {
		RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION);
	}

	protected static final DFA[] _decisionToDFA;

	protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();

	public static final int T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, T__4 = 5, T__5 = 6, T__6 = 7, T__7 = 8,
			T__8 = 9, T__9 = 10, T__10 = 11, T__11 = 12, T__12 = 13, T__13 = 14, T__14 = 15, T__15 = 16,
			T__16 = 17, T__17 = 18, T__18 = 19, T__19 = 20, T__20 = 21, T__21 = 22, T__22 = 23, T__23 = 24,
			T__24 = 25, T__25 = 26, T__26 = 27, T__27 = 28, T__28 = 29, T__29 = 30, T__30 = 31, T__31 = 32,
			T__32 = 33, T__33 = 34, T__34 = 35, T__35 = 36, T__36 = 37, T__37 = 38, T__38 = 39, T__39 = 40,
			T__40 = 41, T__41 = 42, T__42 = 43, T__43 = 44, T__44 = 45, T__45 = 46, T__46 = 47, T__47 = 48,
			T__48 = 49, T__49 = 50, T__50 = 51, T__51 = 52, WS = 53, MultOp = 54, Integer = 55, Real = 56,
			String = 57, ErrorString = 58, Ident = 59;

	public static String[] channelNames = {"DEFAULT_TOKEN_CHANNEL", "HIDDEN" };

	public static String[] modeNames = {"DEFAULT_MODE" };

	private static String[] makeRuleNames() {
		return new String[] {"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", "T__9",
				"T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", "T__17", "T__18", "T__19",
				"T__20", "T__21", "T__22", "T__23", "T__24", "T__25", "T__26", "T__27", "T__28", "T__29",
				"T__30", "T__31", "T__32", "T__33", "T__34", "T__35", "T__36", "T__37", "T__38", "T__39",
				"T__40", "T__41", "T__42", "T__43", "T__44", "T__45", "T__46", "T__47", "T__48", "T__49",
				"T__50", "T__51", "WS", "MultOp", "Integer", "Real", "String", "ErrorString", "Escape",
				"Ident", "Letter" };
	}

	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {null, "'not'", "'-'", "'and'", "'or'", "'xor'", "'implies'", "'('", "')'",
				"'if'", "'then'", "'else'", "'endif'", "'let'", "','", "'in'", "':'", "'='", "'+'", "'<='",
				"'>='", "'!='", "'<>'", "'=='", "'<'", "'>'", "'.'", "'->'", "'select'", "'reject'",
				"'collect'", "'any'", "'exists'", "'forAll'", "'isUnique'", "'one'", "'sortedBy'",
				"'closure'", "'|'", "'true'", "'false'", "'null'", "'Sequence{'", "'}'", "'OrderedSet{'",
				"'::'", "'String'", "'Integer'", "'Real'", "'Boolean'", "'Sequence('", "'OrderedSet('",
				"'{'" };
	}

	private static final String[] _LITERAL_NAMES = makeLiteralNames();

	private static String[] makeSymbolicNames() {
		return new String[] {null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
				null, null, null, null, null, null, null, null, null, null, "WS", "MultOp", "Integer", "Real",
				"String", "ErrorString", "Ident" };
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

	public QueryLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
	}

	@Override
	public String getGrammarFileName() {
		return "Query.g4";
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
	public String[] getChannelNames() {
		return channelNames;
	}

	@Override
	public String[] getModeNames() {
		return modeNames;
	}

	@Override
	public ATN getATN() {
		return _ATN;
	}

	public static final String _serializedATN = "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2=\u01b9\b\1\4\2\t"
			+ "\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"
			+ "\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"
			+ "\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"
			+ "\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"
			+ "\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"
			+ ",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"
			+ "\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="
			+ "\4>\t>\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6"
			+ "\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\13"
			+ "\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16"
			+ "\3\16\3\16\3\16\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23"
			+ "\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\27\3\30\3\30"
			+ "\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\35"
			+ "\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37"
			+ "\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3!\3!\3!\3!\3!\3!\3!\3\"\3\"\3\"\3\""
			+ "\3\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3#\3#\3#\3$\3$\3$\3$\3%\3%\3%\3%\3%\3%"
			+ "\3%\3%\3%\3&\3&\3&\3&\3&\3&\3&\3&\3\'\3\'\3(\3(\3(\3(\3(\3)\3)\3)\3)\3"
			+ ")\3)\3*\3*\3*\3*\3*\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3,\3,\3-\3-\3-\3-\3"
			+ "-\3-\3-\3-\3-\3-\3-\3-\3.\3.\3.\3/\3/\3/\3/\3/\3/\3/\3\60\3\60\3\60\3"
			+ "\60\3\60\3\60\3\60\3\60\3\61\3\61\3\61\3\61\3\61\3\62\3\62\3\62\3\62\3"
			+ "\62\3\62\3\62\3\62\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3"
			+ "\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\65\3\65\3"
			+ "\66\6\66\u017d\n\66\r\66\16\66\u017e\3\66\3\66\3\67\3\67\38\68\u0186\n"
			+ "8\r8\168\u0187\39\69\u018b\n9\r9\169\u018c\39\39\69\u0191\n9\r9\169\u0192"
			+ "\3:\3:\3:\7:\u0198\n:\f:\16:\u019b\13:\3:\3:\3;\3;\3;\7;\u01a2\n;\f;\16"
			+ ";\u01a5\13;\3<\3<\3<\3<\5<\u01ab\n<\3=\3=\5=\u01af\n=\3=\3=\7=\u01b3\n"
			+ "=\f=\16=\u01b6\13=\3>\3>\4\u0199\u01a3\2?\3\3\5\4\7\5\t\6\13\7\r\b\17"
			+ "\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+"
			+ "\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+"
			+ "U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67m8o9q:s;u<w\2y={\2\3\2\b\5\2\13"
			+ "\f\17\17\"\"\4\2,,\61\61\3\2\62;\3\2))\4\2\62;aa\4\2C\\c|\2\u01c2\2\3"
			+ "\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2"
			+ "\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31"
			+ "\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2"
			+ "\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2"
			+ "\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2"
			+ "\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2"
			+ "I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3"
			+ "\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2"
			+ "\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2"
			+ "o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2y\3\2\2\2\3}\3\2\2\2\5\u0081"
			+ "\3\2\2\2\7\u0083\3\2\2\2\t\u0087\3\2\2\2\13\u008a\3\2\2\2\r\u008e\3\2"
			+ "\2\2\17\u0096\3\2\2\2\21\u0098\3\2\2\2\23\u009a\3\2\2\2\25\u009d\3\2\2"
			+ "\2\27\u00a2\3\2\2\2\31\u00a7\3\2\2\2\33\u00ad\3\2\2\2\35\u00b1\3\2\2\2"
			+ "\37\u00b3\3\2\2\2!\u00b6\3\2\2\2#\u00b8\3\2\2\2%\u00ba\3\2\2\2\'\u00bc"
			+ "\3\2\2\2)\u00bf\3\2\2\2+\u00c2\3\2\2\2-\u00c5\3\2\2\2/\u00c8\3\2\2\2\61"
			+ "\u00cb\3\2\2\2\63\u00cd\3\2\2\2\65\u00cf\3\2\2\2\67\u00d1\3\2\2\29\u00d4"
			+ "\3\2\2\2;\u00db\3\2\2\2=\u00e2\3\2\2\2?\u00ea\3\2\2\2A\u00ee\3\2\2\2C"
			+ "\u00f5\3\2\2\2E\u00fc\3\2\2\2G\u0105\3\2\2\2I\u0109\3\2\2\2K\u0112\3\2"
			+ "\2\2M\u011a\3\2\2\2O\u011c\3\2\2\2Q\u0121\3\2\2\2S\u0127\3\2\2\2U\u012c"
			+ "\3\2\2\2W\u0136\3\2\2\2Y\u0138\3\2\2\2[\u0144\3\2\2\2]\u0147\3\2\2\2_"
			+ "\u014e\3\2\2\2a\u0156\3\2\2\2c\u015b\3\2\2\2e\u0163\3\2\2\2g\u016d\3\2"
			+ "\2\2i\u0179\3\2\2\2k\u017c\3\2\2\2m\u0182\3\2\2\2o\u0185\3\2\2\2q\u018a"
			+ "\3\2\2\2s\u0194\3\2\2\2u\u019e\3\2\2\2w\u01aa\3\2\2\2y\u01ae\3\2\2\2{"
			+ "\u01b7\3\2\2\2}~\7p\2\2~\177\7q\2\2\177\u0080\7v\2\2\u0080\4\3\2\2\2\u0081"
			+ "\u0082\7/\2\2\u0082\6\3\2\2\2\u0083\u0084\7c\2\2\u0084\u0085\7p\2\2\u0085"
			+ "\u0086\7f\2\2\u0086\b\3\2\2\2\u0087\u0088\7q\2\2\u0088\u0089\7t\2\2\u0089"
			+ "\n\3\2\2\2\u008a\u008b\7z\2\2\u008b\u008c\7q\2\2\u008c\u008d\7t\2\2\u008d"
			+ "\f\3\2\2\2\u008e\u008f\7k\2\2\u008f\u0090\7o\2\2\u0090\u0091\7r\2\2\u0091"
			+ "\u0092\7n\2\2\u0092\u0093\7k\2\2\u0093\u0094\7g\2\2\u0094\u0095\7u\2\2"
			+ "\u0095\16\3\2\2\2\u0096\u0097\7*\2\2\u0097\20\3\2\2\2\u0098\u0099\7+\2"
			+ "\2\u0099\22\3\2\2\2\u009a\u009b\7k\2\2\u009b\u009c\7h\2\2\u009c\24\3\2"
			+ "\2\2\u009d\u009e\7v\2\2\u009e\u009f\7j\2\2\u009f\u00a0\7g\2\2\u00a0\u00a1"
			+ "\7p\2\2\u00a1\26\3\2\2\2\u00a2\u00a3\7g\2\2\u00a3\u00a4\7n\2\2\u00a4\u00a5"
			+ "\7u\2\2\u00a5\u00a6\7g\2\2\u00a6\30\3\2\2\2\u00a7\u00a8\7g\2\2\u00a8\u00a9"
			+ "\7p\2\2\u00a9\u00aa\7f\2\2\u00aa\u00ab\7k\2\2\u00ab\u00ac\7h\2\2\u00ac"
			+ "\32\3\2\2\2\u00ad\u00ae\7n\2\2\u00ae\u00af\7g\2\2\u00af\u00b0\7v\2\2\u00b0"
			+ "\34\3\2\2\2\u00b1\u00b2\7.\2\2\u00b2\36\3\2\2\2\u00b3\u00b4\7k\2\2\u00b4"
			+ "\u00b5\7p\2\2\u00b5 \3\2\2\2\u00b6\u00b7\7<\2\2\u00b7\"\3\2\2\2\u00b8"
			+ "\u00b9\7?\2\2\u00b9$\3\2\2\2\u00ba\u00bb\7-\2\2\u00bb&\3\2\2\2\u00bc\u00bd"
			+ "\7>\2\2\u00bd\u00be\7?\2\2\u00be(\3\2\2\2\u00bf\u00c0\7@\2\2\u00c0\u00c1"
			+ "\7?\2\2\u00c1*\3\2\2\2\u00c2\u00c3\7#\2\2\u00c3\u00c4\7?\2\2\u00c4,\3"
			+ "\2\2\2\u00c5\u00c6\7>\2\2\u00c6\u00c7\7@\2\2\u00c7.\3\2\2\2\u00c8\u00c9"
			+ "\7?\2\2\u00c9\u00ca\7?\2\2\u00ca\60\3\2\2\2\u00cb\u00cc\7>\2\2\u00cc\62"
			+ "\3\2\2\2\u00cd\u00ce\7@\2\2\u00ce\64\3\2\2\2\u00cf\u00d0\7\60\2\2\u00d0"
			+ "\66\3\2\2\2\u00d1\u00d2\7/\2\2\u00d2\u00d3\7@\2\2\u00d38\3\2\2\2\u00d4"
			+ "\u00d5\7u\2\2\u00d5\u00d6\7g\2\2\u00d6\u00d7\7n\2\2\u00d7\u00d8\7g\2\2"
			+ "\u00d8\u00d9\7e\2\2\u00d9\u00da\7v\2\2\u00da:\3\2\2\2\u00db\u00dc\7t\2"
			+ "\2\u00dc\u00dd\7g\2\2\u00dd\u00de\7l\2\2\u00de\u00df\7g\2\2\u00df\u00e0"
			+ "\7e\2\2\u00e0\u00e1\7v\2\2\u00e1<\3\2\2\2\u00e2\u00e3\7e\2\2\u00e3\u00e4"
			+ "\7q\2\2\u00e4\u00e5\7n\2\2\u00e5\u00e6\7n\2\2\u00e6\u00e7\7g\2\2\u00e7"
			+ "\u00e8\7e\2\2\u00e8\u00e9\7v\2\2\u00e9>\3\2\2\2\u00ea\u00eb\7c\2\2\u00eb"
			+ "\u00ec\7p\2\2\u00ec\u00ed\7{\2\2\u00ed@\3\2\2\2\u00ee\u00ef\7g\2\2\u00ef"
			+ "\u00f0\7z\2\2\u00f0\u00f1\7k\2\2\u00f1\u00f2\7u\2\2\u00f2\u00f3\7v\2\2"
			+ "\u00f3\u00f4\7u\2\2\u00f4B\3\2\2\2\u00f5\u00f6\7h\2\2\u00f6\u00f7\7q\2"
			+ "\2\u00f7\u00f8\7t\2\2\u00f8\u00f9\7C\2\2\u00f9\u00fa\7n\2\2\u00fa\u00fb"
			+ "\7n\2\2\u00fbD\3\2\2\2\u00fc\u00fd\7k\2\2\u00fd\u00fe\7u\2\2\u00fe\u00ff"
			+ "\7W\2\2\u00ff\u0100\7p\2\2\u0100\u0101\7k\2\2\u0101\u0102\7s\2\2\u0102"
			+ "\u0103\7w\2\2\u0103\u0104\7g\2\2\u0104F\3\2\2\2\u0105\u0106\7q\2\2\u0106"
			+ "\u0107\7p\2\2\u0107\u0108\7g\2\2\u0108H\3\2\2\2\u0109\u010a\7u\2\2\u010a"
			+ "\u010b\7q\2\2\u010b\u010c\7t\2\2\u010c\u010d\7v\2\2\u010d\u010e\7g\2\2"
			+ "\u010e\u010f\7f\2\2\u010f\u0110\7D\2\2\u0110\u0111\7{\2\2\u0111J\3\2\2"
			+ "\2\u0112\u0113\7e\2\2\u0113\u0114\7n\2\2\u0114\u0115\7q\2\2\u0115\u0116"
			+ "\7u\2\2\u0116\u0117\7w\2\2\u0117\u0118\7t\2\2\u0118\u0119\7g\2\2\u0119"
			+ "L\3\2\2\2\u011a\u011b\7~\2\2\u011bN\3\2\2\2\u011c\u011d\7v\2\2\u011d\u011e"
			+ "\7t\2\2\u011e\u011f\7w\2\2\u011f\u0120\7g\2\2\u0120P\3\2\2\2\u0121\u0122"
			+ "\7h\2\2\u0122\u0123\7c\2\2\u0123\u0124\7n\2\2\u0124\u0125\7u\2\2\u0125"
			+ "\u0126\7g\2\2\u0126R\3\2\2\2\u0127\u0128\7p\2\2\u0128\u0129\7w\2\2\u0129"
			+ "\u012a\7n\2\2\u012a\u012b\7n\2\2\u012bT\3\2\2\2\u012c\u012d\7U\2\2\u012d"
			+ "\u012e\7g\2\2\u012e\u012f\7s\2\2\u012f\u0130\7w\2\2\u0130\u0131\7g\2\2"
			+ "\u0131\u0132\7p\2\2\u0132\u0133\7e\2\2\u0133\u0134\7g\2\2\u0134\u0135"
			+ "\7}\2\2\u0135V\3\2\2\2\u0136\u0137\7\177\2\2\u0137X\3\2\2\2\u0138\u0139"
			+ "\7Q\2\2\u0139\u013a\7t\2\2\u013a\u013b\7f\2\2\u013b\u013c\7g\2\2\u013c"
			+ "\u013d\7t\2\2\u013d\u013e\7g\2\2\u013e\u013f\7f\2\2\u013f\u0140\7U\2\2"
			+ "\u0140\u0141\7g\2\2\u0141\u0142\7v\2\2\u0142\u0143\7}\2\2\u0143Z\3\2\2"
			+ "\2\u0144\u0145\7<\2\2\u0145\u0146\7<\2\2\u0146\\\3\2\2\2\u0147\u0148\7"
			+ "U\2\2\u0148\u0149\7v\2\2\u0149\u014a\7t\2\2\u014a\u014b\7k\2\2\u014b\u014c"
			+ "\7p\2\2\u014c\u014d\7i\2\2\u014d^\3\2\2\2\u014e\u014f\7K\2\2\u014f\u0150"
			+ "\7p\2\2\u0150\u0151\7v\2\2\u0151\u0152\7g\2\2\u0152\u0153\7i\2\2\u0153"
			+ "\u0154\7g\2\2\u0154\u0155\7t\2\2\u0155`\3\2\2\2\u0156\u0157\7T\2\2\u0157"
			+ "\u0158\7g\2\2\u0158\u0159\7c\2\2\u0159\u015a\7n\2\2\u015ab\3\2\2\2\u015b"
			+ "\u015c\7D\2\2\u015c\u015d\7q\2\2\u015d\u015e\7q\2\2\u015e\u015f\7n\2\2"
			+ "\u015f\u0160\7g\2\2\u0160\u0161\7c\2\2\u0161\u0162\7p\2\2\u0162d\3\2\2"
			+ "\2\u0163\u0164\7U\2\2\u0164\u0165\7g\2\2\u0165\u0166\7s\2\2\u0166\u0167"
			+ "\7w\2\2\u0167\u0168\7g\2\2\u0168\u0169\7p\2\2\u0169\u016a\7e\2\2\u016a"
			+ "\u016b\7g\2\2\u016b\u016c\7*\2\2\u016cf\3\2\2\2\u016d\u016e\7Q\2\2\u016e"
			+ "\u016f\7t\2\2\u016f\u0170\7f\2\2\u0170\u0171\7g\2\2\u0171\u0172\7t\2\2"
			+ "\u0172\u0173\7g\2\2\u0173\u0174\7f\2\2\u0174\u0175\7U\2\2\u0175\u0176"
			+ "\7g\2\2\u0176\u0177\7v\2\2\u0177\u0178\7*\2\2\u0178h\3\2\2\2\u0179\u017a"
			+ "\7}\2\2\u017aj\3\2\2\2\u017b\u017d\t\2\2\2\u017c\u017b\3\2\2\2\u017d\u017e"
			+ "\3\2\2\2\u017e\u017c\3\2\2\2\u017e\u017f\3\2\2\2\u017f\u0180\3\2\2\2\u0180"
			+ "\u0181\b\66\2\2\u0181l\3\2\2\2\u0182\u0183\t\3\2\2\u0183n\3\2\2\2\u0184"
			+ "\u0186\t\4\2\2\u0185\u0184\3\2\2\2\u0186\u0187\3\2\2\2\u0187\u0185\3\2"
			+ "\2\2\u0187\u0188\3\2\2\2\u0188p\3\2\2\2\u0189\u018b\t\4\2\2\u018a\u0189"
			+ "\3\2\2\2\u018b\u018c\3\2\2\2\u018c\u018a\3\2\2\2\u018c\u018d\3\2\2\2\u018d"
			+ "\u018e\3\2\2\2\u018e\u0190\7\60\2\2\u018f\u0191\t\4\2\2\u0190\u018f\3"
			+ "\2\2\2\u0191\u0192\3\2\2\2\u0192\u0190\3\2\2\2\u0192\u0193\3\2\2\2\u0193"
			+ "r\3\2\2\2\u0194\u0199\7)\2\2\u0195\u0198\5w<\2\u0196\u0198\13\2\2\2\u0197"
			+ "\u0195\3\2\2\2\u0197\u0196\3\2\2\2\u0198\u019b\3\2\2\2\u0199\u019a\3\2"
			+ "\2\2\u0199\u0197\3\2\2\2\u019a\u019c\3\2\2\2\u019b\u0199\3\2\2\2\u019c"
			+ "\u019d\7)\2\2\u019dt\3\2\2\2\u019e\u01a3\7)\2\2\u019f\u01a2\5w<\2\u01a0"
			+ "\u01a2\n\5\2\2\u01a1\u019f\3\2\2\2\u01a1\u01a0\3\2\2\2\u01a2\u01a5\3\2"
			+ "\2\2\u01a3\u01a4\3\2\2\2\u01a3\u01a1\3\2\2\2\u01a4v\3\2\2\2\u01a5\u01a3"
			+ "\3\2\2\2\u01a6\u01a7\7^\2\2\u01a7\u01ab\7^\2\2\u01a8\u01a9\7^\2\2\u01a9"
			+ "\u01ab\7)\2\2\u01aa\u01a6\3\2\2\2\u01aa\u01a8\3\2\2\2\u01abx\3\2\2\2\u01ac"
			+ "\u01af\5{>\2\u01ad\u01af\7a\2\2\u01ae\u01ac\3\2\2\2\u01ae\u01ad\3\2\2"
			+ "\2\u01af\u01b4\3\2\2\2\u01b0\u01b3\5{>\2\u01b1\u01b3\t\6\2\2\u01b2\u01b0"
			+ "\3\2\2\2\u01b2\u01b1\3\2\2\2\u01b3\u01b6\3\2\2\2\u01b4\u01b2\3\2\2\2\u01b4"
			+ "\u01b5\3\2\2\2\u01b5z\3\2\2\2\u01b6\u01b4\3\2\2\2\u01b7\u01b8\t\7\2\2"
			+ "\u01b8|\3\2\2\2\17\2\u017e\u0187\u018c\u0192\u0197\u0199\u01a1\u01a3\u01aa"
			+ "\u01ae\u01b2\u01b4\3\b\2\2";

	public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}
