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

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

public class FileContentTests extends TestCase {

	private Bundle bundle;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		bundle = Platform.getBundle("org.eclipse.acceleo.parser.tests");
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

	public void testencodingISO() {
		StringBuffer bufferISO = FileContent
				.getFileContent(createFile("data/template/FileContentEncodingISO_8859_1.mtl"));

		try {
			String refISO = new String(
					"[comment encoding=ISO-8859-1 /]\n\n[module FileContentEncoding(http://www.eclipse.org/emf/2002/Ecore) /]\n\n[comment]\n	être, où, haïr, été\n[/comment]\n"
							.getBytes(), "UTF-8");

			assertEquals(refISO, bufferISO.toString());
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		}
	}

	public void testencodingUTF() {
		StringBuffer bufferUTF = FileContent
				.getFileContent(createFile("data/template/FileContentEncodingUTF_8.mtl"));
		try {
			String refUTF = new String(
					"[comment encoding=UTF-8 /]\n\n[module FileContentEncodingUTF_8(http://www.eclipse.org/emf/2002/Ecore) /]\n\n[comment]\n	добър ден\n[/comment]\n"
							.getBytes(), "UTF-8");

			assertEquals(refUTF, bufferUTF.toString());
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		}
	}

	private File createFile(String pathName) {
		try {
			String fileLocation = FileLocator.resolve(bundle.getEntry(pathName)).getPath();
			return new File(fileLocation);
		} catch (IOException e) {
			throw new AssertionFailedError(e.getMessage());
		} catch (NullPointerException e) {
			/*
			 * on the server the unit test fails with an NPE :S
			 */
			throw new AssertionFailedError(e.getMessage());
		}
	}
}
