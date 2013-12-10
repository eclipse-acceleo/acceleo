/*
 * Copyright (c) 2005, 2012 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Obeo - initial API and implementation
 */
package org.eclipse.acceleo.compatibility.tests.unit.parser.mt.ast.expressions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.acceleo.compatibility.model.mt.expressions.BooleanLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.CallSet;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Expression;
import org.eclipse.acceleo.compatibility.model.mt.expressions.IntegerLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Not;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Operator;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Parenthesis;
import org.eclipse.acceleo.compatibility.model.mt.expressions.StringLiteral;
import org.eclipse.acceleo.compatibility.tests.unit.parser.AbstractAcceleoTest;
import org.eclipse.acceleo.internal.compatibility.parser.mt.ast.expressions.ExpressionParser;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.junit.Test;

/**
 * Test Class for the Expression parser.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@SuppressWarnings("nls")
public class ExpressionParserTest extends AbstractAcceleoTest {

	@Test
	public void testEmptyString() {
		String buffer = ""; //$NON-NLS-1$
		try {
			ExpressionParser.createExpression(0, buffer, new Region(0, buffer.length()), null);
			fail("Should raise a TemplateSyntaxException");
		} catch (TemplateSyntaxException e) {
			// OK
		}
	}

	@Test
	public void testCallSet() {
		String buffer = "call"; //$NON-NLS-1$
		try {
			Expression exp = ExpressionParser.createExpression(0, buffer, new Region(0, buffer.length()),
					null);
			assertTrue("Should be a CallSet", CallSet.class.isAssignableFrom(exp.getClass())); //$NON-NLS-1$
			assertEquals("Should begin at 0 index.", 0, exp.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), exp.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testStringLiteral() {
		String buffer = "\"MyString\""; //$NON-NLS-1$
		try {
			Expression exp = ExpressionParser.createExpression(0, buffer, new Region(0, buffer.length()),
					null);
			assertTrue("Should be a StringLiteral", StringLiteral.class.isAssignableFrom(exp.getClass())); //$NON-NLS-1$
			assertEquals("Should begin at 0 index.", 0, exp.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), exp.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testIntegerLiteral() {
		String buffer = "13"; //$NON-NLS-1$
		try {
			Expression exp = ExpressionParser.createExpression(0, buffer, new Region(0, buffer.length()),
					null);
			assertTrue("Should be a IntegerLiteral", IntegerLiteral.class.isAssignableFrom(exp.getClass())); //$NON-NLS-1$
			assertEquals("Should begin at 0 index.", 0, exp.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), exp.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testBooleanLiteral() {
		String buffer = "false"; //$NON-NLS-1$
		try {
			Expression exp = ExpressionParser.createExpression(0, buffer, new Region(0, buffer.length()),
					null);
			assertTrue("Should be a BooleanLiteral", BooleanLiteral.class.isAssignableFrom(exp.getClass())); //$NON-NLS-1$
			assertEquals("Should begin at 0 index.", 0, exp.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), exp.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testNot() {
		String buffer = "!true"; //$NON-NLS-1$
		try {
			Expression exp = ExpressionParser.createExpression(0, buffer, new Region(0, buffer.length()),
					null);
			assertTrue("Should be a Not", Not.class.isAssignableFrom(exp.getClass())); //$NON-NLS-1$
			assertEquals("Should begin at 0 index.", 0, exp.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), exp.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testOperator() {
		String buffer = "0 + 0"; //$NON-NLS-1$
		try {
			Expression exp = ExpressionParser.createExpression(0, buffer, new Region(0, buffer.length()),
					null);
			assertTrue("Should be an Operator", Operator.class.isAssignableFrom(exp.getClass())); //$NON-NLS-1$
			assertEquals("Should begin at 0 index.", 0, exp.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), exp.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testParenthesis() {
		String buffer = "(call)"; //$NON-NLS-1$
		try {
			Expression exp = ExpressionParser.createExpression(0, buffer, new Region(0, buffer.length()),
					null);
			assertTrue("Should be an Parenthesis", Parenthesis.class.isAssignableFrom(exp.getClass())); //$NON-NLS-1$
			assertEquals("Should begin at 0 index.", 0, exp.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), exp.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}
}
