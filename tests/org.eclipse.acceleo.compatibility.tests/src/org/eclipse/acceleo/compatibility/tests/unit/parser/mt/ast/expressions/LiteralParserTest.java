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
import org.eclipse.acceleo.compatibility.model.mt.expressions.DoubleLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.IntegerLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Literal;
import org.eclipse.acceleo.compatibility.model.mt.expressions.NullLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.StringLiteral;
import org.eclipse.acceleo.compatibility.tests.unit.parser.AbstractAcceleoTest;
import org.eclipse.acceleo.internal.compatibility.parser.mt.ast.expressions.LiteralParser;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateConstants;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.junit.Test;

/**
 * Test Class for the Literal parser.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@SuppressWarnings("nls")
public class LiteralParserTest extends AbstractAcceleoTest {

	@Test
	public void testEmptyString() {
		String buffer = ""; //$NON-NLS-1$
		try {
			LiteralParser.createLiteral(0, buffer, new Region(0, buffer.length()), null);
			fail("Should raise a TemplateSyntaxException");
		} catch (TemplateSyntaxException e) {
			// OK
		}
	}

	@Test
	public void testEmptyLiteral() {
		String buffer = TemplateConstants.getDefault().getLiteral()[0]
				+ TemplateConstants.getDefault().getLiteral()[1];
		try {
			Literal literal = LiteralParser.createLiteral(0, buffer, new Region(0, buffer.length()), null);
			assertTrue("Should be a StringLiteral", StringLiteral.class.isAssignableFrom(literal.getClass())); //$NON-NLS-1$
			assertEquals("Value should be an empty string.", "", ((StringLiteral)literal).getValue());
			assertEquals("Should begin at 0 index.", 0, literal.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), literal.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testStringLiteral() {
		String buffer = TemplateConstants.getDefault().getLiteral()[0]
				+ "MyString" + TemplateConstants.getDefault().getLiteral()[1]; //$NON-NLS-1$
		try {
			Literal literal = LiteralParser.createLiteral(0, buffer, new Region(0, buffer.length()), null);
			assertTrue("Should be a StringLiteral", StringLiteral.class.isAssignableFrom(literal.getClass())); //$NON-NLS-1$
			assertEquals("Value should be \"MyString\".", "MyString", ((StringLiteral)literal).getValue());
			assertEquals("Should begin at 0 index.", 0, literal.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), literal.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testNullLiteral() {
		String buffer = "null"; //$NON-NLS-1$
		try {
			Literal literal = LiteralParser.createLiteral(0, buffer, new Region(0, buffer.length()), null);
			assertTrue("Should be a NullLiteral", NullLiteral.class.isAssignableFrom(literal.getClass())); //$NON-NLS-1$
			assertEquals("Should begin at 0 index.", 0, literal.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), literal.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testIntegerLiteral() {
		String buffer = "1234"; //$NON-NLS-1$
		try {
			Literal literal = LiteralParser.createLiteral(0, buffer, new Region(0, buffer.length()), null);
			assertTrue(
					"Should be an IntegerLiteral", IntegerLiteral.class.isAssignableFrom(literal.getClass())); //$NON-NLS-1$
			assertEquals("Value should be 1234.", 1234, ((IntegerLiteral)literal).getValue());
			assertEquals("Should begin at 0 index.", 0, literal.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), literal.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testDoubleDotLiteral() {
		String buffer = "12.34"; //$NON-NLS-1$
		try {
			Literal literal = LiteralParser.createLiteral(0, buffer, new Region(0, buffer.length()), null);
			assertTrue("Should be an DoubleLiteral", DoubleLiteral.class.isAssignableFrom(literal.getClass())); //$NON-NLS-1$
			assertEquals("Value should be 12.34.", 12.34, ((DoubleLiteral)literal).getValue(), 0.0001);
			assertEquals("Should begin at 0 index.", 0, literal.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), literal.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testDoubleNoDigitBeforTheDotLiteral() {
		String buffer = ".34"; //$NON-NLS-1$
		try {
			Literal literal = LiteralParser.createLiteral(0, buffer, new Region(0, buffer.length()), null);
			assertTrue("Should be an DoubleLiteral", DoubleLiteral.class.isAssignableFrom(literal.getClass())); //$NON-NLS-1$
			assertEquals("Value should be 0.34.", 0.34, ((DoubleLiteral)literal).getValue(), 0.0001);
			assertEquals("Should begin at 0 index.", 0, literal.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), literal.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testDoubleNoDigitAfterTheDotLiteral() {
		String buffer = "12."; //$NON-NLS-1$
		try {
			Literal literal = LiteralParser.createLiteral(0, buffer, new Region(0, buffer.length()), null);
			assertTrue("Should be an DoubleLiteral", DoubleLiteral.class.isAssignableFrom(literal.getClass())); //$NON-NLS-1$
			assertEquals("Value should be 12.0.", 12.0, ((DoubleLiteral)literal).getValue(), 0.0001);
			assertEquals("Should begin at 0 index.", 0, literal.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), literal.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testDoubleCommaLiteral() {
		String buffer = "12,34"; //$NON-NLS-1$
		try {
			Literal literal = LiteralParser.createLiteral(0, buffer, new Region(0, buffer.length()), null);
			assertEquals("Literal should be null.", null, literal);
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testBooleanTrueLiteral() {
		String buffer = "true"; //$NON-NLS-1$
		try {
			Literal literal = LiteralParser.createLiteral(0, buffer, new Region(0, buffer.length()), null);
			assertTrue(
					"Should be an DoubleLiteral", BooleanLiteral.class.isAssignableFrom(literal.getClass())); //$NON-NLS-1$
			assertEquals("Value should be true.", true, ((BooleanLiteral)literal).isValue());
			assertEquals("Should begin at 0 index.", 0, literal.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), literal.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testBooleanFalseLiteral() {
		String buffer = "false"; //$NON-NLS-1$
		try {
			Literal literal = LiteralParser.createLiteral(0, buffer, new Region(0, buffer.length()), null);
			assertTrue(
					"Should be an DoubleLiteral", BooleanLiteral.class.isAssignableFrom(literal.getClass())); //$NON-NLS-1$
			assertEquals("Value should be false.", false, ((BooleanLiteral)literal).isValue());
			assertEquals("Should begin at 0 index.", 0, literal.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), literal.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}
}
