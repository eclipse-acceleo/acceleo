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

import org.eclipse.acceleo.compatibility.model.mt.statements.Text;
import org.eclipse.acceleo.compatibility.tests.unit.parser.AbstractAcceleoTest;
import org.eclipse.acceleo.internal.compatibility.parser.mt.ast.statements.TextParser;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.junit.Test;

/**
 * Test Class for the Text parser.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@SuppressWarnings("nls")
public class TextParserTest extends AbstractAcceleoTest {

	@Test
	public void testEmptyString() {
		String buffer = ""; //$NON-NLS-1$
		try {
			Text text = TextParser.createText(0, buffer, new Region(0, buffer.length()));
			assertEquals("Should be an empty string.", "", text.getValue()); //$NON-NLS-1$ 
			assertEquals("Should begin at 0 index.", 0, text.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), text.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testString() {
		String buffer = "call"; //$NON-NLS-1$
		try {
			Text text = TextParser.createText(0, buffer, new Region(0, buffer.length()));
			assertEquals("Should be \"call\".", "call", text.getValue()); //$NON-NLS-1$ 
			assertEquals("Should begin at 0 index.", 0, text.getBegin());
			assertEquals("Should end at buffer.length() index.", buffer.length(), text.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}
}
