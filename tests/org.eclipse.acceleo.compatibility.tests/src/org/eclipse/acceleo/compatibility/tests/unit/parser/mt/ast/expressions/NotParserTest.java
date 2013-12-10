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
import org.eclipse.acceleo.compatibility.model.mt.expressions.Not;
import org.eclipse.acceleo.compatibility.tests.unit.parser.AbstractAcceleoTest;
import org.eclipse.acceleo.internal.compatibility.parser.mt.ast.expressions.NotParser;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateConstants;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.junit.Test;

/**
 * Test Class for the Not parser.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class NotParserTest extends AbstractAcceleoTest {

	@Test
	public void testEmptyString() {
		String buffer = ""; //$NON-NLS-1$
		try {
			NotParser.createNot(0, buffer, new Region(0, buffer.length()), null);
			fail("Should raise a TemplateSyntaxException"); //$NON-NLS-1$
		} catch (TemplateSyntaxException e) {
			// OK
		}
	}

	@Test
	public void testNotNotExpression() {
		String buffer = "call"; //$NON-NLS-1$
		try {
			Not not = NotParser.createNot(0, buffer, new Region(0, buffer.length()), null);
			assertEquals("Should be null.", null, not); //$NON-NLS-1$
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testNotExpression() {
		String buffer = TemplateConstants.getDefault().getNot() + "call"; //$NON-NLS-1$
		try {
			Not not = NotParser.createNot(0, buffer, new Region(0, buffer.length()), null);
			assertEquals(
					"Should containt a call named \"call\".", "call", ((CallSet)not.getExpression()).getCalls().get(0).getName()); //$NON-NLS-1$ //$NON-NLS-2$
			assertEquals("Should begin at 0 index.", 0, not.getBegin()); //$NON-NLS-1$
			assertEquals("Should end at buffer.length() index.", buffer.length(), not.getEnd()); //$NON-NLS-1$
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}
}
