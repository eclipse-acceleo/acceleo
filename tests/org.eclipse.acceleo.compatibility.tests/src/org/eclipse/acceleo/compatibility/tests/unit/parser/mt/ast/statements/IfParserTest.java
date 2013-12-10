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
package org.eclipse.acceleo.compatibility.tests.unit.parser.mt.ast.statements;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.acceleo.compatibility.model.mt.expressions.CallSet;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Parenthesis;
import org.eclipse.acceleo.compatibility.model.mt.statements.If;
import org.eclipse.acceleo.compatibility.model.mt.statements.Text;
import org.eclipse.acceleo.compatibility.tests.unit.parser.AbstractAcceleoTest;
import org.eclipse.acceleo.internal.compatibility.parser.mt.ast.statements.IfParser;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.junit.Test;

/**
 * Test Class for the For parser.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@SuppressWarnings("nls")
public class IfParserTest extends AbstractAcceleoTest {

	@Test
	public void testEmptyString() {
		String buffer = ""; //$NON-NLS-1$
		try {
			IfParser.createIf(0, buffer, new Region(0, buffer.length()), null);
			fail("Should raise a TemplateSyntaxException.");
		} catch (TemplateSyntaxException e) {
			// OK
		}
	}

	@Test
	public void testIfThenString() {
		String buffer = " (call) {%>text<%}%>"; //$NON-NLS-1$
		try {
			If eIf = IfParser.createIf(0, buffer, new Region(0, buffer.length()), null);
			assertEquals(
					"Should have a Parenthesis containing a CallSet containing a Call named \"call\" as condition.",
					"call", ((CallSet)((Parenthesis)eIf.getCondition()).getExpression()).getCalls().get(0)
							.getName());
			assertEquals(
					"Should have a Text with value \"text\" as then statement.", "text", ((Text)eIf.getThenStatements().get(0)).getValue()); //$NON-NLS-1$ 
			assertEquals("Should begin at 0 index.", 0, eIf.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), eIf.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testIfThenElseString() {
		String buffer = " (call) {%>text<%}else{%>textElse<%}%>"; //$NON-NLS-1$
		try {
			If eIf = IfParser.createIf(0, buffer, new Region(0, buffer.length()), null);
			assertEquals(
					"Should have a Parenthesis containing a CallSet containing a Call named \"call\" as condition.",
					"call", ((CallSet)((Parenthesis)eIf.getCondition()).getExpression()).getCalls().get(0)
							.getName());
			assertEquals("Should have a Text with value \"text\" as then statement.", "text", ((Text)eIf
					.getThenStatements().get(0)).getValue());
			assertEquals(
					"Should have a Text with value \"textElse\" as else statement.", "textElse", ((Text)eIf.getElseStatements().get(0)).getValue()); //$NON-NLS-1$ 
			assertEquals("Should begin at 0 index.", 0, eIf.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), eIf.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testIfThenElseElseIfString() {
		String buffer = " (call) {%>text<%}else if (call2){%>textElseIf<%}else{%>textElse<%}%>"; //$NON-NLS-1$
		try {
			If eIf = IfParser.createIf(0, buffer, new Region(0, buffer.length()), null);
			assertEquals(
					"Should have a Parenthesis containing a CallSet containing a Call named \"call\" as condition.",
					"call", ((CallSet)((Parenthesis)eIf.getCondition()).getExpression()).getCalls().get(0)
							.getName());
			assertEquals("Should have a Text with value \"text\" as then statement.", "text", ((Text)eIf
					.getThenStatements().get(0)).getValue());
			assertEquals("Should have a Text with value \"textElse\" as else statement.", "textElse",
					((Text)eIf.getElseStatements().get(0)).getValue());

			If eElseIf = eIf.getElseIf().get(0);
			assertEquals(
					"ElseIf should have a Parenthesis containing a CallSet containing a Call named \"call2\" as condition.",
					"call2", ((CallSet)((Parenthesis)eElseIf.getCondition()).getExpression()).getCalls().get(
							0).getName());
			assertEquals(
					"ElseIf should have a Text with value \"textElseIf\" as then statement.", "textElseIf", ((Text)eElseIf.getThenStatements().get(0)).getValue()); //$NON-NLS-1$ 

			assertEquals("Should begin at 0 index.", 0, eIf.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), eIf.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}
}
