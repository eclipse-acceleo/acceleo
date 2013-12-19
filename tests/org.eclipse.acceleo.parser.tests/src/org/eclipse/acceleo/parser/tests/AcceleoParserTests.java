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
package org.eclipse.acceleo.parser.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.common.internal.utils.compatibility.AcceleoCompatibilityEclipseHelper;
import org.eclipse.acceleo.common.internal.utils.compatibility.OCLVersion;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.parser.AcceleoFile;
import org.eclipse.acceleo.parser.AcceleoParser;
import org.eclipse.acceleo.parser.AcceleoParserProblem;
import org.eclipse.acceleo.parser.AcceleoSourceBuffer;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.osgi.framework.Bundle;

public class AcceleoParserTests {

	private static Bundle bundle;

	@BeforeClass
	public static void setUp() throws Exception {
		bundle = Platform.getBundle("org.eclipse.acceleo.parser.tests"); //$NON-NLS-1$
	}

	@AfterClass
	public static void tearDown() throws Exception {
		bundle = null;
	}

	private File createFile(String pathName) {
		try {
			String fileLocation = FileLocator.resolve(bundle.getEntry(pathName)).getPath();
			return new File(fileLocation);
		} catch (IOException e) {
			fail(e.getMessage());
		} catch (NullPointerException e) {
			/*
			 * on the server the unit test fails with an NPE :S
			 */
			fail(e.getMessage());
		}
		return null;
	}

	private URI createFileURI(String pathName) {
		try {
			String fileLocation = FileLocator.resolve(bundle.getEntry(pathName)).getPath();
			if (fileLocation.startsWith("file:")) { //$NON-NLS-1$
				fileLocation = fileLocation.substring("file:".length()); //$NON-NLS-1$
			}
			return URI.createFileURI(fileLocation);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		return null;
	}

	@Test
	@Ignore
	public void testCompileSourceBufferEcoreAcceleoWithImport() {
		File file = createFile("/data/template/mtlParserEcore.mtl"); //$NON-NLS-1$
		if (file == null) {
			return;
		}
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(file);
		AcceleoParser parser = new AcceleoParser();
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = ModelUtils.createResource(URI
				.createURI("http://acceleo.eclipse.org/default.emtl"), resourceSet); //$NON-NLS-1$
		List<URI> dependencies = new ArrayList<URI>();
		dependencies.add(createFileURI("/data/template/mtlParserEcoreCommon.emtl")); //$NON-NLS-1$
		parser.parse(source, resource, dependencies);
		assertNotNull(source.getAST());
		if (source.getProblems().getList().size() > 0
				&& AcceleoCompatibilityEclipseHelper.getCurrentOCLVersion() != OCLVersion.GANYMEDE) {
			// Remark : It fails for eclipse 3.4 because OCL didn't support "Set(String)"
			fail(source.getProblems().getMessage());
		}
	}

	public void testCompileRecursiveModuleExtend() {
		// Difficult to test, deactivating this test for now.
		List<File> files = new ArrayList<File>();
		files.add(createFile("/data/template/RecursiveModule1.mtl")); //$NON-NLS-1$
		files.add(createFile("/data/template/RecursiveModule2.mtl")); //$NON-NLS-1$
		File problemFile = createFile("/data/template/RecursiveModule3.mtl"); //$NON-NLS-1$
		files.add(problemFile);
		List<URI> resources = new ArrayList<URI>();
		resources.add(createFileURI("/data/template/RecursiveModule1.emtl")); //$NON-NLS-1$
		resources.add(createFileURI("/data/template/RecursiveModule2.emtl")); //$NON-NLS-1$
		resources.add(createFileURI("/data/template/RecursiveModule3.emtl")); //$NON-NLS-1$
		AcceleoParser parser = new AcceleoParser();
		List<URI> dependencies = new ArrayList<URI>();
		List<AcceleoFile> acceleoFiles = new ArrayList<AcceleoFile>();
		for (File inputFile : files) {
			acceleoFiles.add(new AcceleoFile(inputFile, AcceleoFile.simpleModuleName(inputFile)));
		}
		parser.parse(acceleoFiles, resources, dependencies, null, new BasicMonitor());

		for (File file : files) {
			List<AcceleoParserProblem> problems = parser.getProblems(file).getList();
			if (file != problemFile) {
				assertEquals(0, problems.size());
			} else {
				assertEquals(1, problems.size());
			}
		}
	}

	@Test
	@Ignore
	public void testCompileSourceBufferLibrary2textAcceleo() {
		File file = createFile("/data/template/mtlParserLibrary2text.mtl"); //$NON-NLS-1$
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(file);
		AcceleoParser parser = new AcceleoParser();
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = ModelUtils.createResource(URI
				.createURI("http://acceleo.eclipse.org/default.emtl"), resourceSet); //$NON-NLS-1$
		parser.parse(source, resource, new ArrayList<URI>());
		assertNotNull(source.getAST());
		if (source.getProblems().getList().size() > 0) {
			fail(source.getProblems().getMessage());
		}
	}

	@Test
	@Ignore
	public void testCompileFileLibrary2textAcceleo() {
		File iFile = createFile("/data/template/mtlParserLibrary2text.mtl"); //$NON-NLS-1$
		URI oURI = createFileURI("/data/template/mtlParserLibrary2text.emtl"); //$NON-NLS-1$
		testCompileFile(iFile, oURI, 0);
	}

	@Test
	@Ignore
	public void testCompileFileLibrary2textAcceleoWithBadOutputURI() {
		File iFile = createFile("/data/template/mtlParserLibrary2text.mtl"); //$NON-NLS-1$
		URI oURI = URI.createURI("http://acceleo.eclipse.org"); //$NON-NLS-1$
		testCompileFile(iFile, oURI, 1);
	}

	private void testCompileFile(File iFile, URI oURI, int problemsCount) {
		List<File> iFiles = new ArrayList<File>();
		iFiles.add(iFile);
		AcceleoParser parser = new AcceleoParser();
		List<URI> oURIs = new ArrayList<URI>();
		oURIs.add(oURI);
		assertNull(parser.getProblems(iFile));
		List<AcceleoFile> acceleoFiles = new ArrayList<AcceleoFile>();
		for (File inputFile : iFiles) {
			acceleoFiles.add(new AcceleoFile(inputFile, AcceleoFile.simpleModuleName(inputFile)));
		}
		parser.parse(acceleoFiles, oURIs, new ArrayList<URI>(), null, new BasicMonitor());
		if (parser.getProblems(iFile).getList().size() != problemsCount) {
			fail("You must have " + problemsCount + " syntax errors : " //$NON-NLS-1$ //$NON-NLS-2$
					+ parser.getProblems(iFile).getMessage());
		}
		if (problemsCount == 0) {
			assertEquals(parser.getProblems(iFile).getMessage(), ""); //$NON-NLS-1$
			assertEquals(parser.getProblems(iFile).toString(), ""); //$NON-NLS-1$
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

	@Test
	@Ignore
	public void testIndentStrategy() {
		File file = createFile("/data/template/mtlIndentStrategy.mtl"); //$NON-NLS-1$
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(file);
		AcceleoParser parser = new AcceleoParser();
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = ModelUtils.createResource(URI
				.createURI("http://acceleo.eclipse.org/default.emtl"), resourceSet); //$NON-NLS-1$
		List<URI> dependencies = new ArrayList<URI>();
		parser.parse(source, resource, dependencies);
		assertNotNull(source.getAST());
		if (source.getProblems().getList().size() > 0) {
			fail(source.getProblems().getMessage());
		}
		String[] results = {"\n", "\n", "\t\t", "\n", "\t\t", "\n", "\n", "\t", "\n", "\t\t", "\n", "\t\t\t", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$ //$NON-NLS-11$ //$NON-NLS-12$
				"\n", "\t\t\t", "\n", "\n", "\n\t\t", "\n", "\n" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$
		int i = -1;
		StringBuffer report = new StringBuffer();
		Iterator<EObject> it = resource.getContents().get(0).eAllContents();
		while (it.hasNext()) {
			EObject eObject = it.next();
			if (eObject instanceof org.eclipse.ocl.ecore.StringLiteralExp
					&& !(eObject.eContainer() != null && eObject.eContainer().eContainingFeature() == MtlPackage.eINSTANCE
							.getIfBlock_IfExpr())) {
				i++;
				org.eclipse.ocl.ecore.StringLiteralExp literal = (org.eclipse.ocl.ecore.StringLiteralExp)eObject;
				String symbol = literal.getStringSymbol().replaceAll("\r", ""); //$NON-NLS-1$ //$NON-NLS-2$
				if (i >= results.length || !results[i].equals(symbol)) {
					int line = FileContent.lineNumber(source.getBuffer(), literal.getStartPosition());
					report.append("New value at line " + line + " [" + i + "] = '" + symbol + "'\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				}
			}
		}
		if (report.length() > 0) {
			System.out.print(report);
			fail(report.toString());
		}
	}

}
