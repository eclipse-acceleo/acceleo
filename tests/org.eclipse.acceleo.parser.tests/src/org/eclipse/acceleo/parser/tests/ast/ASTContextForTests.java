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

public class ASTContextForTests extends AbstractASTParserTests {

	@BeforeClass
	public static void setUp() {
		bundle = Platform.getBundle("org.eclipse.acceleo.parser.tests"); //$NON-NLS-1$
		project = createAcceleoProject("org.eclipse.acceleo.parser.tests.context.for"); //$NON-NLS-1$
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
	public void testParserContextForValid() {
		String content = this.getContentFromPath("/data/ast/context/forContextValid.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/context/for/files"), project, //$NON-NLS-1$
				"forContextValid.mtl"); //$NON-NLS-1$
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
	public void testParserContextForValid2() {
		String content = this.getContentFromPath("/data/ast/context/forContextValid2.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/context/for/files"), project, //$NON-NLS-1$
				"forContextValid2.mtl"); //$NON-NLS-1$
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
	public void testParserContextForValid3() {
		String content = this.getContentFromPath("/data/ast/context/forContextValid3.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/context/for/files"), project, //$NON-NLS-1$
				"forContextValid3.mtl"); //$NON-NLS-1$
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
	public void testParserContextForValid4() {
		String content = this.getContentFromPath("/data/ast/context/forContextValid4.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/context/for/files"), project, //$NON-NLS-1$
				"forContextValid4.mtl"); //$NON-NLS-1$
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
	public void testParserContextForInvalid() {
		String content = this.getContentFromPath("/data/ast/context/forContextInvalid.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/context/for/files"), project, //$NON-NLS-1$
				"forContextInvalid.mtl"); //$NON-NLS-1$
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
	public void testParserContextForInvalid2() {
		String content = this.getContentFromPath("/data/ast/context/forContextInvalid2.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/context/for/files"), project, //$NON-NLS-1$
				"forContextInvalid2.mtl"); //$NON-NLS-1$
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
	public void testParserContextForInvalid3() {
		String content = this.getContentFromPath("/data/ast/context/forContextInvalid3.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/context/for/files"), project, //$NON-NLS-1$
				"forContextInvalid3.mtl"); //$NON-NLS-1$
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
	public void testParserContextForInvalid4() {
		String content = this.getContentFromPath("/data/ast/context/forContextInvalid4.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/context/for/files"), project, //$NON-NLS-1$
				"forContextInvalid4.mtl"); //$NON-NLS-1$
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
	public void testParserContextForInvalid5() {
		String content = this.getContentFromPath("/data/ast/context/forContextInvalid5.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/context/for/files"), project, //$NON-NLS-1$
				"forContextInvalid5.mtl"); //$NON-NLS-1$
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
	public void testParserContextForInvalid6() {
		String content = this.getContentFromPath("/data/ast/context/forContextInvalid6.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/context/for/files"), project, //$NON-NLS-1$
				"forContextInvalid6.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(0, 0, 0);
			checkASTResolution(0, 0, 2);
			checkASTDocumentationResolution(0, 0, 2);
		} else {
			fail();
		}
	}
}
