/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.parser.tests.cst.utils;

import junit.framework.TestCase;

import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;

public class FileContentTests extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testLineNumber() {
		StringBuffer buffer = new StringBuffer("1\n2\n3\n4\n5");
		assertEquals(FileContent.lineNumber(buffer, 0), 1);
		assertEquals(FileContent.lineNumber(buffer, 1), 1);
		assertEquals(FileContent.lineNumber(buffer, 2), 2);
		assertEquals(FileContent.lineNumber(buffer, 3), 2);
		assertEquals(FileContent.lineNumber(buffer, 4), 3);
		assertEquals(FileContent.lineNumber(buffer, 5), 3);
		assertEquals(FileContent.lineNumber(buffer, 6), 4);
	}

	public void testColumnNumber() {
		StringBuffer buffer = new StringBuffer("1\n2\n3\n4\n5");
		assertEquals(FileContent.columnNumber(buffer, 0), 1);
		assertEquals(FileContent.columnNumber(buffer, 1), 2);
		assertEquals(FileContent.columnNumber(buffer, 2), 1);
		assertEquals(FileContent.columnNumber(buffer, 3), 2);
		assertEquals(FileContent.columnNumber(buffer, 4), 1);
		assertEquals(FileContent.columnNumber(buffer, 5), 2);
		assertEquals(FileContent.columnNumber(buffer, 6), 1);
	}

}
