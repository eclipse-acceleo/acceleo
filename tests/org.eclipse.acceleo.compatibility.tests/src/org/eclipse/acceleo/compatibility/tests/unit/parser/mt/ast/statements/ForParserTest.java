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
import org.eclipse.acceleo.compatibility.model.mt.statements.For;
import org.eclipse.acceleo.compatibility.model.mt.statements.Text;
import org.eclipse.acceleo.compatibility.tests.unit.parser.AbstractAcceleoTest;
import org.eclipse.acceleo.internal.compatibility.parser.mt.ast.statements.ForParser;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.junit.Test;

/**
 * Test Class for the For parser.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@SuppressWarnings("nls")
public class ForParserTest extends AbstractAcceleoTest {

	@Test
	public void testEmptyString() {
		String buffer = ""; //$NON-NLS-1$
		try {
			ForParser.createFor(0, buffer, new Region(0, buffer.length()), null);
			fail("Should raise a TemplateSyntaxException.");
		} catch (TemplateSyntaxException e) {
			// OK
		}
	}

	@Test
	public void testForString() {
		String buffer = " (call) {%>text<%}%>"; //$NON-NLS-1$
		try {
			For eFor = ForParser.createFor(0, buffer, new Region(0, buffer.length()), null);
			assertEquals(
					"Should have a Parenthesis containing a CallSet containing a Call named \"call\" as iterator.",
					"call", ((CallSet)((Parenthesis)eFor.getIterator()).getExpression()).getCalls().get(0)
							.getName());
			assertEquals(
					"Should have a Text with value \"text\" as statement.", "text", ((Text)eFor.getStatements().get(0)).getValue()); //$NON-NLS-1$ 
			assertEquals("Should begin at 0 index.", 0, eFor.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), eFor.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}
}
