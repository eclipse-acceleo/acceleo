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
package org.eclipse.acceleo.parser.tests.ast;

import java.io.File;

import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.fail;

public class ASTDocumentationTests extends AbstractASTParserTests {

	@BeforeClass
	public static void setUp() {
		bundle = Platform.getBundle("org.eclipse.acceleo.parser.tests"); //$NON-NLS-1$
		project = createAcceleoProject("org.eclipse.acceleo.parser.tests.documentation"); //$NON-NLS-1$
	}

	@AfterClass
	public static void tearDown() {
		try {
			project.delete(true, new NullProgressMonitor());
			bundle = null;
			EList<Resource> resources = oResourceSet.getResources();
			for (Resource resource : resources) {
				resource.unload();
			}
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testParseDocumentationValid() {
		File file = this.getFileFromPath("/data/ast/documentation/documentationValid.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer, new Path(
				"/org/eclipse/acceleo/parser/tests/documentation/files"), project, //$NON-NLS-1$
				"documentationValid.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(0, 0, 0);
			checkASTResolution(0, 0, 0);
			checkASTDocumentationResolution(0, 0, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseDocumentationValid2() {
		File file = this.getFileFromPath("/data/ast/documentation/documentationValid2.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer, new Path(
				"/org/eclipse/acceleo/parser/tests/documentation/files"), project, //$NON-NLS-1$
				"documentationValid2.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(0, 4, 0);
			checkASTResolution(0, 4, 0);
			checkASTDocumentationResolution(0, 5, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseDocumentationModuleElementInvalid() {
		File file = this.getFileFromPath("/data/ast/documentation/documentationModuleElementInvalid.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer, new Path(
				"/org/eclipse/acceleo/parser/tests/documentation/files"), project, //$NON-NLS-1$
				"documentationModuleElementInvalid.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}
}
