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

import java.io.File;
import java.io.IOException;

import junit.framework.AssertionFailedError;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.osgi.framework.Bundle;

public class CSTParserTests extends AbstractCSTParserTests {

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
			throw new AssertionFailedError(e.getMessage());
		} catch (NullPointerException e) {
			/*
			 * on the server the unit test fails with an NPE :S
			 */
			throw new AssertionFailedError(e.getMessage());
		}

	}

	@Test
	@Ignore
	public void testParseFileLibrary2textAcceleo() {
		File file = createFile("/data/template/cstParserLibrary2text.mtl"); //$NON-NLS-1$
		checkCSTParsing(file, 0, 3, 0);
	}

	@Test
	public void testParseEmptyBuffer() {
		StringBuffer buffer = new StringBuffer(""); //$NON-NLS-1$
		checkCSTInvalidParsing(buffer, 0, 0, 1);
	}

	// TODO JMU : OCL context should work
	// public void testParseOCLSyntaxHelp() {
	// String bufferBefore = "[module mymodule(http://www.eclipse.org/uml2/2.1.0/UML)/]"
	// + "[template public class2Java(c1 : Class)]" + "[c1.";
	// String bufferAfter = "/] [/template]";
	// List<Choice> choices = getSyntaxHelp(bufferBefore + bufferAfter, bufferBefore.length());
	// assertTrue(choices.size() > 40);
	// }
	// private List<Choice> getSyntaxHelp(String buffer, int offset) {
	// AcceleoSourceBuffer source = new AcceleoSourceBuffer(new StringBuffer(buffer));
	// CSTParser parser = new CSTParser(source);
	// Module cst = parser.parse();
	// ResourceSet resourceSet = new ResourceSetImpl();
	// Resource resource = ModelUtils.createResource(URI
	// .createURI("http://acceleo.eclipse.org/default.emtl"), resourceSet);
	// CST2ASTConverter astConverter = new CST2ASTConverter();
	// astConverter.createAST(cst, resource);
	// if (resource.getContents().size() > 0
	// && resource.getContents().get(0) instanceof org.eclipse.acceleo.model.mtl.Module) {
	// org.eclipse.acceleo.model.mtl.Module ast = (org.eclipse.acceleo.model.mtl.Module)resource
	// .getContents().get(0);
	// astConverter.getOCL().addRecursivelyMetamodelsToScope(ast);
	// astConverter.getOCL().addRecursivelyBehavioralFeaturesToScope(ast);
	// assertNotNull(astConverter.getOCL().addRecursivelyVariablesToScopeAndGetContextClassifierAt(ast,
	// offset));
	// }
	// return astConverter.getOCL().getSyntaxHelp("");
	// }

}
