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

public class ASTScopeTemplateTests extends AbstractASTParserTests {

	@BeforeClass
	public static void setUp() {
		bundle = Platform.getBundle("org.eclipse.acceleo.parser.tests"); //$NON-NLS-1$
		project = createAcceleoProject("org.eclipse.acceleo.parser.tests.scope.template"); //$NON-NLS-1$
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
	public void testParserScopeTemplateValid() {
		String content = this.getContentFromPath("/data/ast/scope/templateScopeValid.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/scope/template/files"), project, //$NON-NLS-1$
				"templateScopeValid.mtl"); //$NON-NLS-1$
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
	public void testParserScopeTemplateValid2() {
		String content = this.getContentFromPath("/data/ast/scope/templateScopeValid2.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/scope/template/files"), project, //$NON-NLS-1$
				"templateScopeValid2.mtl"); //$NON-NLS-1$
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
	public void testParserScopeTemplateValid3() {
		String content = this.getContentFromPath("/data/ast/scope/templateScopeValid3.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/scope/template/files"), project, //$NON-NLS-1$
				"templateScopeValid3.mtl"); //$NON-NLS-1$
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
	public void testParserScopeTemplateValid4() {
		String content = this.getContentFromPath("/data/ast/scope/templateScopeValid4.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/scope/template/files"), project, //$NON-NLS-1$
				"templateScopeValid4.mtl"); //$NON-NLS-1$
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
	public void testParserScopeTemplateValid5() {
		String content = this.getContentFromPath("/data/ast/scope/templateScopeValid5.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/scope/template/files"), project, //$NON-NLS-1$
				"templateScopeValid5.mtl"); //$NON-NLS-1$
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
	public void testParserScopeTemplateInvalid() {
		String content = this.getContentFromPath("/data/ast/scope/templateScopeInvalid.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/scope/template/files"), project, //$NON-NLS-1$
				"templateScopeInvalid.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(0, 0, 0);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParserScopeTemplateInvalid2() {
		String content = this.getContentFromPath("/data/ast/scope/templateScopeInvalid2.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/scope/template/files"), project, //$NON-NLS-1$
				"templateScopeInvalid2.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(0, 0, 0);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}
}
