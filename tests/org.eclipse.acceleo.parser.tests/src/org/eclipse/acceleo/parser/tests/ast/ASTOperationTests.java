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

import static org.junit.Assert.fail;

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
		String content = this.getContentFromPath("/data/ast/operations/acceleoStandardOperation.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"acceleoStandardOperation.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
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
		String content = this.getContentFromPath("/data/ast/operations/acceleoNonStandardOperation.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"acceleoNonStandardOperation.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
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
		String content = this.getContentFromPath("/data/ast/operations/acceleoOperationReturnType.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"acceleoOperationReturnType.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
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
		String content = this.getContentFromPath("/data/ast/operations/oclStandardOperation.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"oclStandardOperation.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
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
		String content = this.getContentFromPath("/data/ast/operations/oclComparison.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"oclComparison.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
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
		String content = this.getContentFromPath("/data/ast/operations/queryCallResolution.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"queryCallResolution.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
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
		String content = this.getContentFromPath("/data/ast/operations/queryCallQualifiedNameResolution.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"queryCallQualifiedNameResolution.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
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
		String content = this.getContentFromPath("/data/ast/operations/templateCallResolution.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"templateCallResolution.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
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
		String content = this
				.getContentFromPath("/data/ast/operations/templateCallQualifiedNameResolution.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"templateCallQualifiedNameResolution.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
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
		String content = this.getContentFromPath("/data/ast/operations/recursiveQuery.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"recursiveQuery.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
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
		String content = this.getContentFromPath("/data/ast/operations/acceleoEscapeCharacter.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/operations/files"), project, //$NON-NLS-1$
				"acceleoEscapeCharacter.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(0, 0, 0);
			checkASTResolution(0, 0, 0);
			checkASTDocumentationResolution(0, 0, 0);
		} else {
			fail();
		}
	}
}
