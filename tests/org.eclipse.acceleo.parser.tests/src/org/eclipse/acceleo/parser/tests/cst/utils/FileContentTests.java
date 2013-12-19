/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.parser.tests.cst.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import junit.framework.AssertionFailedError;

import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.osgi.framework.Bundle;

public class FileContentTests {

	private static Bundle bundle;

	@BeforeClass
	public static void setUp() throws Exception {
		bundle = Platform.getBundle("org.eclipse.acceleo.parser.tests"); //$NON-NLS-1$
	}

	@AfterClass
	public static void tearDown() throws Exception {
		bundle = null;
	}

	@Test
	public void testLineNumber() {
		StringBuffer buffer = new StringBuffer("1\n2\n3\n4\n5"); //$NON-NLS-1$
		assertEquals(FileContent.lineNumber(buffer, 0), 1);
		assertEquals(FileContent.lineNumber(buffer, 1), 1);
		assertEquals(FileContent.lineNumber(buffer, 2), 2);
		assertEquals(FileContent.lineNumber(buffer, 3), 2);
		assertEquals(FileContent.lineNumber(buffer, 4), 3);
		assertEquals(FileContent.lineNumber(buffer, 5), 3);
		assertEquals(FileContent.lineNumber(buffer, 6), 4);
	}

	@Test
	public void testColumnNumber() {
		StringBuffer buffer = new StringBuffer("1\n2\n3\n4\n5"); //$NON-NLS-1$
		assertEquals(FileContent.columnNumber(buffer, 0), 1);
		assertEquals(FileContent.columnNumber(buffer, 1), 2);
		assertEquals(FileContent.columnNumber(buffer, 2), 1);
		assertEquals(FileContent.columnNumber(buffer, 3), 2);
		assertEquals(FileContent.columnNumber(buffer, 4), 1);
		assertEquals(FileContent.columnNumber(buffer, 5), 2);
		assertEquals(FileContent.columnNumber(buffer, 6), 1);
	}

	@Test
	@Ignore
	public void testencodingISO() {
		StringBuffer bufferISO = FileContent
				.getFileContent(createFile("data/template/FileContentEncodingISO_8859_1.mtl")); //$NON-NLS-1$

		try {
			String refISO = new String(
					"[comment encoding=ISO-8859-1 /]\n\n[module FileContentEncoding(http://www.eclipse.org/emf/2002/Ecore) /]\n\n[comment]\n	\u00EAtre, o\u00F9, ha\u00EFr, \u00E9t\u00E9\n[/comment]\n" //$NON-NLS-1$
					.getBytes("UTF-8"), "UTF-8"); //$NON-NLS-1$ //$NON-NLS-2$

			assertEquals(refISO, bufferISO.toString());
		} catch (UnsupportedEncodingException e) {
			fail(e.getMessage());
		}
	}

	public void testencodingUTF() {
		StringBuffer bufferUTF = FileContent
				.getFileContent(createFile("data/template/FileContentEncodingUTF_8.mtl")); //$NON-NLS-1$
		try {
			String refUTF = new String(
					"[comment encoding=UTF-8 /]\n\n[module FileContentEncodingUTF_8(http://www.eclipse.org/emf/2002/Ecore) /]\n\n[comment]\n	\u0434\u043E\u0431\u044A\u0440 \u0434\u0435\u043D\n[/comment]\n" //$NON-NLS-1$
					.getBytes("UTF-8"), "UTF-8"); //$NON-NLS-1$ //$NON-NLS-2$

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
