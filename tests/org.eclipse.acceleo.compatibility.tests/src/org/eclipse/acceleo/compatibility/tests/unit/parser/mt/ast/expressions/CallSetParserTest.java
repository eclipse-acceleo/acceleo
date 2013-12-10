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
import org.eclipse.acceleo.compatibility.tests.unit.parser.AbstractAcceleoTest;
import org.eclipse.acceleo.internal.compatibility.parser.mt.ast.expressions.CallSetParser;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.junit.Test;

/**
 * Test Class for the CallSet parser.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@SuppressWarnings("nls")
public class CallSetParserTest extends AbstractAcceleoTest {

	@Test
	public void testEmptyCallSet() {
		String buffer = ""; //$NON-NLS-1$
		try {
			CallSetParser.createCallSet(0, buffer, new Region(0, buffer.length()), null);
			fail("Should raise a TemplateSyntaxException");
		} catch (TemplateSyntaxException e) {
			// OK
		}
	}

	@Test
	public void testCallSetWithEmptyCall() {
		String buffer = "call1..call3"; //$NON-NLS-1$
		try {
			CallSet calls = CallSetParser.createCallSet(0, buffer, new Region(0, buffer.length()), null);
			assertEquals("The callSet should contain 2 calls.", 2, calls.getCalls().size()); //$NON-NLS-1$
			assertEquals("Call #1 should be \"call1\"", "call1", calls.getCalls().get(0).getName()); //$NON-NLS-2$
			assertEquals("Call #2 should be \"call3\"", "call3", calls.getCalls().get(1).getName());
			assertEquals("Should begin at 0 index.", 0, calls.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), calls.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCallSetWithOutEmptyCall() {
		String buffer = "call1.call2.call3"; //$NON-NLS-1$
		try {
			CallSet calls = CallSetParser.createCallSet(0, buffer, new Region(0, buffer.length()), null);
			assertEquals("The callSet should contain 3 calls.", 3, calls.getCalls().size()); //$NON-NLS-1$
			assertEquals("Call #1 should be \"call1\"", "call1", calls.getCalls().get(0).getName()); //$NON-NLS-2$
			assertEquals("Call #2 should be \"call2\"", "call2", calls.getCalls().get(1).getName());
			assertEquals("Call #3 should be \"call3\"", "call3", calls.getCalls().get(2).getName());
			assertEquals("Should begin at 0 index.", 0, calls.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), calls.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}
}
