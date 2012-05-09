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

public class ASTOperationTests extends AbstractASTParserTests {

	@BeforeClass
	public static void setUp() {
		bundle = Platform.getBundle("org.eclipse.acceleo.parser.tests"); //$NON-NLS-1$
		project = createAcceleoProject("org.eclipse.acceleo.parser.tests.operations"); //$NON-NLS-1$
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
	public void testParseAcceleoStandardOperation() {
		File file = this.getFileFromPath("/data/ast/operations/acceleoStandardOperation.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"acceleoStandardOperation.mtl"); //$NON-NLS-1$
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
	public void testParseAcceleoNonStandardOperation() {
		File file = this.getFileFromPath("/data/ast/operations/acceleoNonStandardOperation.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"acceleoNonStandardOperation.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(0, 0, 0);
			checkASTResolution(1, 1, 0);
			checkASTDocumentationResolution(1, 1, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseAcceleoOperationReturnType() {
		File file = this.getFileFromPath("/data/ast/operations/acceleoOperationReturnType.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"acceleoOperationReturnType.mtl"); //$NON-NLS-1$
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
	public void testParseOCLStandardOperation() {
		File file = this.getFileFromPath("/data/ast/operations/oclStandardOperation.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"oclStandardOperation.mtl"); //$NON-NLS-1$
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
	public void testParseOCLComparison() {
		File file = this.getFileFromPath("/data/ast/operations/oclComparison.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"oclComparison.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(0, 0, 0);
			checkASTResolution(0, 4, 0);
			checkASTDocumentationResolution(0, 4, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseQueryCallResolution() {
		File file = this.getFileFromPath("/data/ast/operations/queryCallResolution.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"queryCallResolution.mtl"); //$NON-NLS-1$
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
	public void testParseQueryCallQualifiedNameResolution() {
		File file = this.getFileFromPath("/data/ast/operations/queryCallQualifiedNameResolution.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"queryCallQualifiedNameResolution.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateCallResolution() {
		File file = this.getFileFromPath("/data/ast/operations/templateCallResolution.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"templateCallResolution.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateCallQualifiedNameResolution() {
		File file = this.getFileFromPath("/data/ast/operations/templateCallQualifiedNameResolution.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"templateCallQualifiedNameResolution.mtl"); //$NON-NLS-1$
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
	public void testParseRecursiveQuery() {
		File file = this.getFileFromPath("/data/ast/operations/recursiveQuery.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"recursiveQuery.mtl"); //$NON-NLS-1$
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
	public void testParseAcceleoEscapeCharacter() {
		File file = this.getFileFromPath("/data/ast/operations/acceleoEscapeCharacter.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"acceleoEscapeCharacter.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(0, 0, 0);
			checkASTResolution(0, 0, 0);
			checkASTDocumentationResolution(0, 0, 0);
		} else {
			fail();
		}
	}
}
