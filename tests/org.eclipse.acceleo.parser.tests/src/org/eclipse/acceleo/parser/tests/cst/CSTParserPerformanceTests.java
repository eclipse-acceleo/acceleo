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
package org.eclipse.acceleo.parser.tests.cst;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import org.eclipse.core.runtime.Platform;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.osgi.framework.Bundle;

public class CSTParserPerformanceTests extends AbstractCSTParserTests {

	private static Bundle bundle;

	private StringBuffer result;

	private int numberOfTests = 100;

	private long parsingTime = 120;

	@BeforeClass
	public static void setUp() throws Exception {
		bundle = Platform.getBundle("org.eclipse.emf.eef.codegen"); //$NON-NLS-1$
	}

	@AfterClass
	public static void tearDown() throws Exception {
		bundle = null;
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testEEFParsing() {
		result = new StringBuffer();

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //$NON-NLS-1$
		Date date = new Date();
		result.append("###############################################\n"); //$NON-NLS-1$
		result.append("# CST Parsing test on EEF - " + dateFormat.format(date) + '\n'); //$NON-NLS-1$
		result.append("###############################################\n"); //$NON-NLS-1$

		Enumeration entries = bundle.findEntries("/", "*.mtl", true); //$NON-NLS-1$ //$NON-NLS-2$
		if (entries != null) {
			while (entries.hasMoreElements()) {
				Object element = entries.nextElement();
				if (element instanceof URL) {
					URL url = (URL)element;
					try {
						result.append("#File: " + url.getPath() + '\n'); //$NON-NLS-1$
						InputStream stream = url.openStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8")); //$NON-NLS-1$
						String line = ""; //$NON-NLS-1$
						StringBuffer sb = new StringBuffer();

						while ((line = reader.readLine()) != null) {
							sb.append(line).append("\n"); //$NON-NLS-1$
						}

						testParsingPerformance(sb);
					} catch (IOException e) {
						fail();
					}
				}
			}

			System.err.println(result.toString());
		} else {
			fail("No entries found"); //$NON-NLS-1$
		}
	}

	private void testParsingPerformance(StringBuffer file) {
		long endTime = 0;

		for (int i = 0; i < numberOfTests; i++) {
			long beginTime = System.currentTimeMillis();
			checkCSTParsing(file, 0, 0, 0);
			endTime += System.currentTimeMillis() - beginTime;
		}

		result.append(file.length() + " " + ((float)endTime) / ((float)numberOfTests) + '\n'); //$NON-NLS-1$

		assertTrue("The parsing is not fast enough: " + ((float)endTime) / ((float)numberOfTests) //$NON-NLS-1$
				+ "ms (it should be lower than " + parsingTime + "ms)", ((float)endTime) //$NON-NLS-1$ //$NON-NLS-2$
				/ ((float)numberOfTests) <= parsingTime);
	}
}
