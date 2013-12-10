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

public class ASTScopeLetTests extends AbstractASTParserTests {

	@BeforeClass
	public static void setUp() {
		bundle = Platform.getBundle("org.eclipse.acceleo.parser.tests"); //$NON-NLS-1$
		project = createAcceleoProject("org.eclipse.acceleo.parser.tests.scope.let"); //$NON-NLS-1$
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
	public void testParserScopeLetValid() {
		String content = this.getContentFromPath("/data/ast/scope/letScopeValid.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/scope/let/files"), project, //$NON-NLS-1$
				"letScopeValid.mtl"); //$NON-NLS-1$
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
	public void testParserScopeLetValid2() {
		String content = this.getContentFromPath("/data/ast/scope/letScopeValid2.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/scope/let/files"), project, //$NON-NLS-1$
				"letScopeValid2.mtl"); //$NON-NLS-1$
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
	public void testParserScopeLetValid3() {
		String content = this.getContentFromPath("/data/ast/scope/letScopeValid3.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/scope/let/files"), project, //$NON-NLS-1$
				"letScopeValid3.mtl"); //$NON-NLS-1$
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
	public void testParserScopeLetValid4() {
		String content = this.getContentFromPath("/data/ast/scope/letScopeValid4.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/scope/let/files"), project, //$NON-NLS-1$
				"letScopeValid4.mtl"); //$NON-NLS-1$
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
	public void testParserScopeLetInvalid() {
		String content = this.getContentFromPath("/data/ast/scope/letScopeInvalid.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/scope/let/files"), project, //$NON-NLS-1$
				"letScopeInvalid.mtl"); //$NON-NLS-1$
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
	public void testParserScopeLetInvalid2() {
		String content = this.getContentFromPath("/data/ast/scope/letScopeInvalid2.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/scope/let/files"), project, //$NON-NLS-1$
				"letScopeInvalid2.mtl"); //$NON-NLS-1$
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
	public void testParserScopeLetInvalid3() {
		String content = this.getContentFromPath("/data/ast/scope/letScopeInvalid3.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/scope/let/files"), project, //$NON-NLS-1$
				"letScopeInvalid3.mtl"); //$NON-NLS-1$
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
	public void testParserScopeLetInvalid4() {
		String content = this.getContentFromPath("/data/ast/scope/letScopeInvalid4.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/scope/let/files"), project, //$NON-NLS-1$
				"letScopeInvalid4.mtl"); //$NON-NLS-1$
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
	public void testParserScopeLetInvalid5() {
		String content = this.getContentFromPath("/data/ast/scope/letScopeInvalid5.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/scope/let/files"), project, //$NON-NLS-1$
				"letScopeInvalid5.mtl"); //$NON-NLS-1$
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
	public void testParserScopeLetInvalid6() {
		String content = this.getContentFromPath("/data/ast/scope/letScopeInvalid6.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/scope/let/files"), project, //$NON-NLS-1$
				"letScopeInvalid6.mtl"); //$NON-NLS-1$
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
	public void testParserScopeLetInvalid7() {
		String content = this.getContentFromPath("/data/ast/scope/letScopeInvalid7.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/scope/let/files"), project, //$NON-NLS-1$
				"letScopeInvalid7.mtl"); //$NON-NLS-1$
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
	public void testParserScopeLetInvalid8() {
		String content = this.getContentFromPath("/data/ast/scope/letScopeInvalid8.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/scope/let/files"), project, //$NON-NLS-1$
				"letScopeInvalid8.mtl"); //$NON-NLS-1$
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
	public void testParserScopeLetInvalid9() {
		String content = this.getContentFromPath("/data/ast/scope/letScopeInvalid9.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/scope/let/files"), project, //$NON-NLS-1$
				"letScopeInvalid9.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}
}
