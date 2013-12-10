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

import org.eclipse.acceleo.compatibility.model.mt.expressions.CallSet;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Parenthesis;
import org.eclipse.acceleo.compatibility.tests.unit.parser.AbstractAcceleoTest;
import org.eclipse.acceleo.internal.compatibility.parser.mt.ast.expressions.ParenthesisParser;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateConstants;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.junit.Test;

/**
 * Test Class for the Parenthesis parser.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@SuppressWarnings("nls")
public class ParenthesisParserTest extends AbstractAcceleoTest {

	@Test
	public void testEmptyString() {
		String buffer = ""; //$NON-NLS-1$
		try {
			ParenthesisParser.createParenthesis(0, buffer, new Region(0, buffer.length()), null);
			fail("Should raise a TemplateSyntaxException");
		} catch (TemplateSyntaxException e) {
			// OK
		}
	}

	@Test
	public void testNotParenthesisExpression() {
		String buffer = "call"; //$NON-NLS-1$
		try {
			Parenthesis parenthesis = ParenthesisParser.createParenthesis(0, buffer, new Region(0, buffer
					.length()), null);
			assertEquals("Should be null.", null, parenthesis);
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testParenthesisExpression() {
		String buffer = TemplateConstants.getDefault().getParenth()[0]
				+ "call" + TemplateConstants.getDefault().getParenth()[1]; //$NON-NLS-1$
		try {
			Parenthesis parenthesis = ParenthesisParser.createParenthesis(0, buffer, new Region(0, buffer
					.length()), null);
			assertEquals(
					"Should containt a call named \"call\".", "call", ((CallSet)parenthesis.getExpression()).getCalls().get(0).getName()); //$NON-NLS-1$ 
			assertEquals("Should begin at 0 index.", 0, parenthesis.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), parenthesis.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}
}
