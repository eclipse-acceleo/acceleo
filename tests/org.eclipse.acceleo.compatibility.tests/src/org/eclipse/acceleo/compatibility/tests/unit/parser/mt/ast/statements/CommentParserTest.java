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

import org.eclipse.acceleo.compatibility.model.mt.statements.Comment;
import org.eclipse.acceleo.compatibility.tests.unit.parser.AbstractAcceleoTest;
import org.eclipse.acceleo.internal.compatibility.parser.mt.ast.statements.CommentParser;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.junit.Test;

/**
 * Test Class for the Comment parser.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@SuppressWarnings("nls")
public class CommentParserTest extends AbstractAcceleoTest {

	@Test
	public void testEmptyString() {
		String buffer = ""; //$NON-NLS-1$
		try {
			Comment comment = CommentParser.createComment(0, buffer, new Region(0, buffer.length()));
			assertEquals("Should be an empty string.", "", comment.getValue()); //$NON-NLS-1$ 
			assertEquals("Call should begin at 0 index.", 0, comment.getBegin());
			assertEquals("Call should end at buffer.length() index.", buffer.length(), comment.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testString() {
		String buffer = "call"; //$NON-NLS-1$
		try {
			Comment comment = CommentParser.createComment(0, buffer, new Region(0, buffer.length()));
			assertEquals("Should be \"call\".", "call", comment.getValue()); //$NON-NLS-1$ 
			assertEquals("Call should begin at 0 index.", 0, comment.getBegin());
			assertEquals("Call should end at buffer.length() index.", buffer.length(), comment.getEnd());
		} catch (TemplateSyntaxException e) {
			fail(e.getMessage());
		}
	}
}
