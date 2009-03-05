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
package org.eclipse.acceleo.parser.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.acceleo.parser.AcceleoParser;
import org.eclipse.acceleo.parser.AcceleoParserProblem;
import org.eclipse.acceleo.parser.AcceleoSourceBuffer;
import org.osgi.framework.Bundle;

public class AcceleoParserTests extends TestCase {

	private Bundle bundle;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		bundle = Platform.getBundle("org.eclipse.acceleo.parser.tests");
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		bundle = null;
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

	private URI createFileURI(String pathName) {
		try {
			String fileLocation = FileLocator.resolve(bundle.getEntry(pathName)).getPath();
			return URI.createFileURI(fileLocation);
		} catch (IOException e) {
			throw new AssertionFailedError(e.getMessage());
		}
	}

	public void testCompileSourceBufferEcoreAcceleoWithImport() {
		File file = createFile("/data/template/mtlParserEcore.mtl");
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(file);
		AcceleoParser parser = new AcceleoParser();
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = ModelUtils.createResource(URI.createURI("http://acceleo.eclipse.org/default.emtl"),
				resourceSet);
		List<URI> dependencies = new ArrayList<URI>();
		dependencies.add(createFileURI("/data/template/mtlParserEcoreCommon.emtl"));
		parser.parse(source, resource, dependencies);
		assertNotNull(source.getAST());
		if (source.getProblems().getList().size() > 0) {
			fail(source.getProblems().getMessage());
		}
	}

	public void testCompileSourceBufferLibrary2textAcceleo() {
		File file = createFile("/data/template/mtlParserLibrary2text.mtl");
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(file);
		AcceleoParser parser = new AcceleoParser();
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = ModelUtils.createResource(URI.createURI("http://acceleo.eclipse.org/default.emtl"),
				resourceSet);
		parser.parse(source, resource, new ArrayList<URI>());
		assertNotNull(source.getAST());
		if (source.getProblems().getList().size() > 0) {
			fail(source.getProblems().getMessage());
		}
	}

	public void testCompileFileLibrary2textAcceleo() {
		File iFile = createFile("/data/template/mtlParserLibrary2text.mtl");
		URI oURI = createFileURI("/data/template/mtlParserLibrary2text.emtl");
		testCompileFile(iFile, oURI, 0);
	}

	public void testCompileFileLibrary2textAcceleoWithBadOutputURI() {
		File iFile = createFile("/data/template/mtlParserLibrary2text.mtl");
		URI oURI = URI.createURI("http://acceleo.eclipse.org");
		testCompileFile(iFile, oURI, 1);
	}

	private void testCompileFile(File iFile, URI oURI, int problemsCount) {
		List<File> iFiles = new ArrayList<File>();
		iFiles.add(iFile);
		AcceleoParser parser = new AcceleoParser();
		List<URI> oURIs = new ArrayList<URI>();
		oURIs.add(oURI);
		assertNull(parser.getProblems(iFile));
		parser.parse(iFiles, oURIs, new ArrayList<URI>());
		if (parser.getProblems(iFile).getList().size() != problemsCount) {
			fail("You must have " + problemsCount + " syntax errors : "
					+ parser.getProblems(iFile).getMessage());
		}
		if (problemsCount == 0) {
			assertEquals(parser.getProblems(iFile).getMessage(), "");
			assertEquals(parser.getProblems(iFile).toString(), "");
		} else {
			assertTrue(parser.getProblems(iFile).getMessage() != null);
			Iterator<AcceleoParserProblem> it = parser.getProblems(iFile).getList().iterator();
			while (it.hasNext()) {
				AcceleoParserProblem parserProblem = it.next();
				assertTrue(parserProblem.getMessage() != null);
				assertTrue(parserProblem.getLine() > 0);
				assertTrue(parserProblem.getPosBegin() > -1);
				assertTrue(parserProblem.getPosEnd() > -1);
				assertTrue(parserProblem.toString().length() > 0);
			}
		}
		parser.getProblems(iFile).clear();
	}

	public void testIndentStrategy() {
		File file = createFile("/data/template/mtlIndentStrategy.mtl");
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(file);
		AcceleoParser parser = new AcceleoParser();
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = ModelUtils.createResource(URI.createURI("http://acceleo.eclipse.org/default.emtl"),
				resourceSet);
		List<URI> dependencies = new ArrayList<URI>();
		parser.parse(source, resource, dependencies);
		assertNotNull(source.getAST());
		if (source.getProblems().getList().size() > 0) {
			fail(source.getProblems().getMessage());
		}
		String[] results = {"\n", "\n", "\t\t", "\n", "", "\t\t", "\n", "", "\t", "\n", "\t\t", "\n", "", "",
				"\t\t\t", "\n", "", "\t\t\t", "\n", "", "\n", "\n\t\t", "\n", ""};
		int i = -1;
		StringBuffer report = new StringBuffer();
		Iterator<EObject> it = resource.getContents().get(0).eAllContents();
		while (it.hasNext()) {
			EObject eObject = (EObject)it.next();
			if (eObject instanceof org.eclipse.ocl.ecore.StringLiteralExp) {
				i++;
				org.eclipse.ocl.ecore.StringLiteralExp literal = (org.eclipse.ocl.ecore.StringLiteralExp)eObject;
				String symbol = literal.getStringSymbol().replaceAll("\r", "");
				if (i >= results.length || !results[i].equals(symbol)) {
					int line = FileContent.lineNumber(source.getBuffer(), literal.getStartPosition());
					report.append("New value at line " + line + " [" + i + "] = '" + symbol + "'\n");
				}
			}
		}
		if (report.length() > 0) {
			System.out.print(report);
			fail(report.toString());
		}
	}

}
