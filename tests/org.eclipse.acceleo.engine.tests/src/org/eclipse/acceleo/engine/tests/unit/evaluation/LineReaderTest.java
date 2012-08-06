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
package org.eclipse.acceleo.engine.tests.unit.evaluation;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationContext;
import org.eclipse.emf.ecore.EClassifier;

/**
 * Unit tests classe to test the line reader of the evaluation context.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class LineReaderTest extends TestCase {
	private int readLine(String str, List<String> lines) {
		AcceleoEvaluationContext<EClassifier> evaluationContext = new AcceleoEvaluationContext<EClassifier>(
				null, Lists.<IAcceleoTextGenerationListener> newArrayList(), null, null);
		AcceleoEvaluationContext<EClassifier>.LineReader reader = evaluationContext.new LineReader(
				new StringReader(str));
		String line;
		int i = 0;
		try {
			line = reader.readLine();
			if (line != null) {
				assertEquals(lines.get(0), line);
				i = i + 1;
			}
			int count = 1;
			while (line != null) {
				line = reader.readLine();
				if (line != null) {
					assertEquals(lines.get(count), line);
					count++;
					i = i + 1;
				}
			}

		} catch (IOException e) {
			fail(e.getMessage());
		}
		return i;
	}

	public void testEmptyStringReadLine() {
		assertEquals(0, readLine("", Lists.<String> newArrayList())); //$NON-NLS-1$
	}

	public void testOneLineReadLine() {
		assertEquals(1, readLine("test", Lists.newArrayList("test"))); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void testOneBigLineReadLine() {
		String str = "0123456789"; //$NON-NLS-1$
		String buffer = str;
		for (int i = 0; i < 818; i++) {
			buffer = buffer + str;
		}
		buffer = buffer + "012"; //$NON-NLS-1$
		assertEquals(1, readLine(buffer, Lists.newArrayList(buffer)));
	}

	public void testTwoLinesUnixReadLine() {
		assertEquals(2, readLine("test\ntest", Lists.newArrayList("test", "test"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public void testTwoBigLinesMacOsClassicReadLine() {
		String str = "0123456789"; //$NON-NLS-1$
		String buffer = str;
		for (int i = 0; i < 818; i++) {
			buffer = buffer + str;
		}

		// Index of \r = 8192
		String finalBuffer = buffer + "01\r2"; //$NON-NLS-1$
		System.out.println("Index of \\r: " + finalBuffer.indexOf("\r")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(2, readLine(finalBuffer, Lists.newArrayList(buffer + "01", "2"))); //$NON-NLS-1$ //$NON-NLS-2$

		// Index of \r = 8190
		finalBuffer = buffer + "\r01"; //$NON-NLS-1$
		System.out.println("Index of \\r: " + finalBuffer.indexOf("\r")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(2, readLine(finalBuffer, Lists.newArrayList(buffer, "01"))); //$NON-NLS-1$

		// Index of \r = 8191
		finalBuffer = buffer + "0\r1"; //$NON-NLS-1$
		System.out.println("Index of \\r: " + finalBuffer.indexOf("\r")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(1, readLine(finalBuffer, Lists.newArrayList(buffer + "0", "1"))); //$NON-NLS-1$ //$NON-NLS-2$

		// Index of \r = 8191
		finalBuffer = buffer + "0\r1"; //$NON-NLS-1$
		finalBuffer = "01234\r5678" + finalBuffer; //$NON-NLS-1$

		System.out.println("Index of \\r: " + finalBuffer.indexOf("\r")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(3, readLine(finalBuffer, Lists.newArrayList("01234", "5678" + buffer + "0", "1"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	public void testTwoBigLinesDosReadLine() {
		String str = "0123456789"; //$NON-NLS-1$
		String buffer = str;
		for (int i = 0; i < 818; i++) {
			buffer = buffer + str;
		}

		// Index of \r = 8192
		String finalBuffer = buffer + "01\r\n2"; //$NON-NLS-1$
		System.out.println("Index of \\r\\n: " + finalBuffer.indexOf("\r\n")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(2, readLine(finalBuffer, Lists.newArrayList(buffer + "01", "2"))); //$NON-NLS-1$ //$NON-NLS-2$

		// Index of \r = 8190
		finalBuffer = buffer + "\r\n1"; //$NON-NLS-1$
		System.out.println("Index of \\r\\n: " + finalBuffer.indexOf("\r\n")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(2, readLine(finalBuffer, Lists.newArrayList(buffer, "1"))); //$NON-NLS-1$

		// Index of \r = 8191
		finalBuffer = buffer + "0\r\n1"; //$NON-NLS-1$
		System.out.println("Index of \\r\\n: " + finalBuffer.indexOf("\r\n")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(2, readLine(finalBuffer, Lists.newArrayList(buffer + "0", "1"))); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void testTwoBigLinesUnixReadLine() {
		String str = "0123456789"; //$NON-NLS-1$
		String buffer = str;
		for (int i = 0; i < 818; i++) {
			buffer = buffer + str;
		}

		// Index of \r = 8192
		String finalBuffer = buffer + "01\n2"; //$NON-NLS-1$
		System.out.println("Index of \\n: " + finalBuffer.indexOf("\n")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(2, readLine(finalBuffer, Lists.newArrayList(buffer + "01", "2"))); //$NON-NLS-1$ //$NON-NLS-2$

		// Index of \r = 8190
		finalBuffer = buffer + "\n01"; //$NON-NLS-1$
		System.out.println("Index of \\n: " + finalBuffer.indexOf("\n")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(2, readLine(finalBuffer, Lists.newArrayList(buffer, "01"))); //$NON-NLS-1$

		// Index of \r = 8191
		finalBuffer = buffer + "0\n1"; //$NON-NLS-1$
		System.out.println("Index of \\n: " + finalBuffer.indexOf("\n")); //$NON-NLS-1$ //$NON-NLS-2$
		assertEquals(2, readLine(finalBuffer, Lists.newArrayList(buffer + "0", "1"))); //$NON-NLS-1$ //$NON-NLS-2$
	}

	public void testTwoLinesDosReadLine() {
		assertEquals(2, readLine("test\r\ntest", Lists.newArrayList("test", "test"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public void testTwoLinesMacOsClassicReadLine() {
		assertEquals(2, readLine("test\rtest", Lists.newArrayList("test", "test"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public void testTwoLinesUnixEmptyReadLine() {
		assertEquals(2, readLine("test\ntest\n", Lists.newArrayList("test", "test"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public void testTwoLinesDosEmptyReadLine() {
		assertEquals(2, readLine("test\r\ntest\r\n", Lists.newArrayList("test", "test"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public void testTwoLinesMacOsClassicEmptyReadLine() {
		assertEquals(2, readLine("test\rtest\r", Lists.newArrayList("test", "test"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public void testThreeLinesUnixReadLine() {
		assertEquals(3, readLine("test\ntest\na", Lists.newArrayList("test", "test", "a"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	public void testThreeLinesDosReadLine() {
		assertEquals(3, readLine("test\r\ntest\r\na", Lists.newArrayList("test", "test", "a"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	public void testThreeLinesMacOsClassicReadLine() {
		assertEquals(3, readLine("test\rtest\ra", Lists.newArrayList("test", "test", "a"))); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}
}
