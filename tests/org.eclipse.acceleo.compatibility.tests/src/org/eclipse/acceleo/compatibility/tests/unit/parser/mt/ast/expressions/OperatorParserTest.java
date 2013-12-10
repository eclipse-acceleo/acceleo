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
import static org.junit.Assert.fail;

import org.eclipse.acceleo.compatibility.model.mt.expressions.Operator;
import org.eclipse.acceleo.compatibility.tests.unit.parser.AbstractAcceleoTest;
import org.eclipse.acceleo.internal.compatibility.parser.mt.ast.expressions.OperatorParser;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateConstants;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.junit.Test;

/**
 * Test Class for the Operator parser.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@SuppressWarnings("nls")
public class OperatorParserTest extends AbstractAcceleoTest {

	@Test
	public void testEmptyString() {
		String buffer = ""; //$NON-NLS-1$
		try {
			OperatorParser.createOperator(0, buffer, new Region(0, buffer.length()), null);
			fail("Should raise a TemplateSyntaxException");
		} catch (TemplateSyntaxException e) {
			// OK
		}
	}

	@Test
	public void testNoOperands() {
		for (int i = 0; i < TemplateConstants.getDefault().getOperators().length; ++i) {
			String buffer = TemplateConstants.getDefault().getOperators()[i];
			try {
				Operator op = OperatorParser.createOperator(0, buffer, new Region(0, buffer.length()), null);
				assertEquals("Should be null.", null, op);
			} catch (TemplateSyntaxException e) {
				fail(e.getMessage());
			}
		}
	}

	@Test
	public void testOneBeforOperand() {
		for (int i = 0; i < TemplateConstants.getDefault().getOperators().length; ++i) {
			String buffer = "1" + TemplateConstants.getDefault().getOperators()[i]; //$NON-NLS-1$
			try {
				Operator op = OperatorParser.createOperator(0, buffer, new Region(0, buffer.length()), null);
				assertEquals("Should be null.", null, op);
			} catch (TemplateSyntaxException e) {
				fail(e.getMessage());
			}
		}
	}

	@Test
	public void testOneAfterOperand() {
		for (int i = 0; i < TemplateConstants.getDefault().getOperators().length; ++i) {
			String buffer = TemplateConstants.getDefault().getOperators()[i] + "1"; //$NON-NLS-1$
			try {
				Operator op = OperatorParser.createOperator(0, buffer, new Region(0, buffer.length()), null);
				assertEquals("Should be null.", null, op);
			} catch (TemplateSyntaxException e) {
				fail(e.getMessage());
			}
		}
	}

	@Test
	public void testWithOperands() {
		for (int i = 0; i < TemplateConstants.getDefault().getOperators().length; ++i) {
			String buffer = "1" + TemplateConstants.getDefault().getOperators()[i] + "1" + TemplateConstants.getDefault().getOperators()[i] + "1"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			try {
				Operator op = OperatorParser.createOperator(0, buffer, new Region(0, buffer.length()), null);
				assertEquals(
						"Should be an " + TemplateConstants.getDefault().getOperators()[i] + ".", TemplateConstants.getDefault().getOperators()[i], op.getOperator()); //$NON-NLS-1$ 
				assertEquals("Should have 3 operands.", 3, op.getOperands().size());
				assertEquals("Should begin at 0 index.", 0, op.getBegin());
				assertEquals("Should end at buffer.length() index.", buffer.length(), op.getEnd());
			} catch (TemplateSyntaxException e) {
				fail(e.getMessage());
			}
		}
	}

	@Test
	public void testMissingLastOperand() {
		for (int i = 0; i < TemplateConstants.getDefault().getOperators().length; ++i) {
			String buffer = "1" + TemplateConstants.getDefault().getOperators()[i] + "1" + TemplateConstants.getDefault().getOperators()[i]; //$NON-NLS-1$ //$NON-NLS-2$
			try {
				Operator op = OperatorParser.createOperator(0, buffer, new Region(0, buffer.length()), null);
				assertEquals(
						"Should be an " + TemplateConstants.getDefault().getOperators()[i] + ".", TemplateConstants.getDefault().getOperators()[i], op.getOperator()); //$NON-NLS-1$ 
				assertEquals("Should have 2 operands.", 2, op.getOperands().size());
				assertEquals("Should begin at 0 index.", 0, op.getBegin());
				assertEquals("Should end at buffer.length() index.", buffer.length(), op.getEnd());
			} catch (TemplateSyntaxException e) {
				fail(e.getMessage());
			}
		}
	}

	@Test
	public void testMissingFirstOperand() {
		for (int i = 0; i < TemplateConstants.getDefault().getOperators().length; ++i) {
			String buffer = TemplateConstants.getDefault().getOperators()[i]
					+ "1" + TemplateConstants.getDefault().getOperators()[i] + "1"; //$NON-NLS-1$ //$NON-NLS-2$
			try {
				Operator op = OperatorParser.createOperator(0, buffer, new Region(0, buffer.length()), null);
				assertEquals(
						"Should be an " + TemplateConstants.getDefault().getOperators()[i] + ".", TemplateConstants.getDefault().getOperators()[i], op.getOperator()); //$NON-NLS-1$ 
				assertEquals("Should have 2 operands.", 2, op.getOperands().size());
				assertEquals("Should begin at 0 index.", 0, op.getBegin());
				assertEquals("Should end at buffer.length() index.", buffer.length(), op.getEnd());
			} catch (TemplateSyntaxException e) {
				fail(e.getMessage());
			}
		}
	}

	@Test
	public void testMissingMiddleOperand() {
		for (int i = 0; i < TemplateConstants.getDefault().getOperators().length; ++i) {
			String buffer = "1" + TemplateConstants.getDefault().getOperators()[i] + TemplateConstants.getDefault().getOperators()[i] + "1"; //$NON-NLS-1$ //$NON-NLS-2$
			try {
				Operator op = OperatorParser.createOperator(0, buffer, new Region(0, buffer.length()), null);
				assertEquals(
						"Should be an " + TemplateConstants.getDefault().getOperators()[i] + ".", TemplateConstants.getDefault().getOperators()[i], op.getOperator()); //$NON-NLS-1$ 
				assertEquals("Should have 2 operands.", 2, op.getOperands().size());
				assertEquals("Should begin at 0 index.", 0, op.getBegin());
				assertEquals("Should end at buffer.length() index.", buffer.length(), op.getEnd());
			} catch (TemplateSyntaxException e) {
				fail(e.getMessage());
			}
		}
	}
}
