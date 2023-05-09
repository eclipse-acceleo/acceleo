/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.parser.tests;

import java.io.StringReader;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.CommonTokenFactory;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.UnbufferedCharStream;
import org.antlr.v4.runtime.UnbufferedTokenStream;
import org.eclipse.acceleo.query.parser.QueryLexer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LexerTest {

	private TokenStream getLexer(String expression) {
		CharStream input = new UnbufferedCharStream(new StringReader(expression), expression.length());
		QueryLexer lexer = new QueryLexer(input);
		lexer.setTokenFactory(new CommonTokenFactory(true));
		TokenStream tokens = new UnbufferedTokenStream<CommonToken>(lexer);
		return tokens;
	}

	private void checkToken(String input, int type) {
		assertEquals(input, getLexer(input).get(0).getText());
		assertEquals(type, getLexer(input).get(0).getType());

	}

	private void checkIncorectToken(String input) {
		getLexer(input).get(0);
	}

	/**
	 * Checks that the lexer recognizes a simple identifier with no number no capital letter and no
	 * underscore.
	 */
	@Test
	public void testCorrectIdent() {
		checkToken("name", QueryLexer.Ident);
		checkToken("ruleName", QueryLexer.Ident);
		checkToken("rule0", QueryLexer.Ident);
		checkToken("_rule", QueryLexer.Ident);
		checkToken("any_rule", QueryLexer.Ident);
	}

	@Test
	public void testIncorrectIdent() {
		checkIncorectToken("rule);name");
	}

	/**
	 * Integer : [0..9]+
	 */
	@Test
	public void testInteger() {
		checkToken("1", QueryLexer.Integer);
		checkToken("10", QueryLexer.Integer);
		checkToken("123", QueryLexer.Integer);
		checkToken("9999", QueryLexer.Integer);
		checkToken("0", QueryLexer.Integer);
	}

	/**
	 * Real : [0..9]+'.'[0..9]+
	 */
	@Test
	public void testReal() {
		checkToken("0.0", QueryLexer.Real);
		checkToken("0.1", QueryLexer.Real);
		checkToken("10.0", QueryLexer.Real);
		checkToken("12.123", QueryLexer.Real);
		checkToken("0.9999", QueryLexer.Real);
	}

	@Test
	public void testIncorrectReal() {
		checkIncorectToken(".1");
		checkIncorectToken("1.");
	}
}
