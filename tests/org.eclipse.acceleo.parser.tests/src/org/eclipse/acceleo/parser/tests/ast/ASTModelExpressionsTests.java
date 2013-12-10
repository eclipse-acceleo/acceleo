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

public class ASTModelExpressionsTests extends AbstractASTParserTests {

	@BeforeClass
	public static void setUp() {
		bundle = Platform.getBundle("org.eclipse.acceleo.parser.tests"); //$NON-NLS-1$
		project = createAcceleoProject("org.eclipse.acceleo.parser.tests.modelexpression"); //$NON-NLS-1$
	}

	@AfterClass
	public static void tearDown() {
		try {
			project.delete(true, new NullProgressMonitor());
			EList<Resource> resources = oResourceSet.getResources();
			for (Resource resource : resources) {
				resource.unload();
			}
			bundle = null;
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testParseForValid() {
		String content = this.getContentFromPath("/data/ast/modelexpression/forValid.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"forValid.mtl"); //$NON-NLS-1$
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
	public void testParseForValid2() {
		String content = this.getContentFromPath("/data/ast/modelexpression/forValid2.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"forValid2.mtl"); //$NON-NLS-1$
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
	public void testParseForValid3() {
		String content = this.getContentFromPath("/data/ast/modelexpression/forValid3.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"forValid3.mtl"); //$NON-NLS-1$
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
	public void testParseForValid4() {
		String content = this.getContentFromPath("/data/ast/modelexpression/forValid4.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"forValid4.mtl"); //$NON-NLS-1$
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
	public void testParseForValid5() {
		String content = this.getContentFromPath("/data/ast/modelexpression/forValid5.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"forValid5.mtl"); //$NON-NLS-1$
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
	public void testParseForValid6() {
		String content = this.getContentFromPath("/data/ast/modelexpression/forValid6.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"forValid6.mtl"); //$NON-NLS-1$
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
	public void testParseForInvalid() {
		String content = this.getContentFromPath("/data/ast/modelexpression/forInvalid.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"forInvalid.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseForInvalid2() {
		String content = this.getContentFromPath("/data/ast/modelexpression/forInvalid2.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"forInvalid2.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseForInvalid3() {
		String content = this.getContentFromPath("/data/ast/modelexpression/forInvalid3.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"forInvalid3.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseForInvalid4() {
		String content = this.getContentFromPath("/data/ast/modelexpression/forInvalid4.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"forInvalid4.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseIfValid() {
		String content = this.getContentFromPath("/data/ast/modelexpression/ifValid.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"ifValid.mtl"); //$NON-NLS-1$
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
	public void testParseIfValid2() {
		String content = this.getContentFromPath("/data/ast/modelexpression/ifValid2.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"ifValid2.mtl"); //$NON-NLS-1$
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
	public void testParseIfValid3() {
		String content = this.getContentFromPath("/data/ast/modelexpression/ifValid3.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"ifValid3.mtl"); //$NON-NLS-1$
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
	public void testParseIfValid4() {
		String content = this.getContentFromPath("/data/ast/modelexpression/ifValid4.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"ifValid4.mtl"); //$NON-NLS-1$
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
	public void testParseIfInvalid() {
		String content = this.getContentFromPath("/data/ast/modelexpression/ifInvalid.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"ifInvalid.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseIfInvalid2() {
		String content = this.getContentFromPath("/data/ast/modelexpression/ifInvalid2.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"ifInvalid2.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseIfInvalid3() {
		String content = this.getContentFromPath("/data/ast/modelexpression/ifInvalid3.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"ifInvalid3.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseIfInvalid4() {
		String content = this.getContentFromPath("/data/ast/modelexpression/ifInvalid4.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"ifInvalid4.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseIfInvalid5() {
		String content = this.getContentFromPath("/data/ast/modelexpression/ifInvalid5.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"ifInvalid5.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(0, 0, 0);
			checkASTResolution(0, 0, 2);
			checkASTDocumentationResolution(0, 0, 2);
		} else {
			fail();
		}
	}

	@Test
	public void testParseIfInvalid6() {
		String content = this.getContentFromPath("/data/ast/modelexpression/ifInvalid6.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"ifInvalid6.mtl"); //$NON-NLS-1$
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
	public void testParseIfInvalid7() {
		String content = this.getContentFromPath("/data/ast/modelexpression/ifInvalid7.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"ifInvalid7.mtl"); //$NON-NLS-1$
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
	public void testParseIfInvalid8() {
		String content = this.getContentFromPath("/data/ast/modelexpression/ifInvalid8.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"ifInvalid8.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseIfInvalid9() {
		String content = this.getContentFromPath("/data/ast/modelexpression/ifInvalid9.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"ifInvalid9.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseIfInvalid10() {
		String content = this.getContentFromPath("/data/ast/modelexpression/ifInvalid10.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"ifInvalid10.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseLetValid() {
		String content = this.getContentFromPath("/data/ast/modelexpression/letValid.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"letValid.mtl"); //$NON-NLS-1$
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
	public void testParseLetValid2() {
		String content = this.getContentFromPath("/data/ast/modelexpression/letValid2.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"letValid2.mtl"); //$NON-NLS-1$
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
	public void testParseLetValid3() {
		String content = this.getContentFromPath("/data/ast/modelexpression/letValid3.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"letValid3.mtl"); //$NON-NLS-1$
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
	public void testParseLetInvalid() {
		String content = this.getContentFromPath("/data/ast/modelexpression/letInvalid.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"letInvalid.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseLetInvalid2() {
		String content = this.getContentFromPath("/data/ast/modelexpression/letInvalid2.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"letInvalid2.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseLetInvalid3() {
		String content = this.getContentFromPath("/data/ast/modelexpression/letInvalid3.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"letInvalid3.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseLetInvalid4() {
		String content = this.getContentFromPath("/data/ast/modelexpression/letInvalid4.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"letInvalid4.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseLetInvalid5() {
		String content = this.getContentFromPath("/data/ast/modelexpression/letInvalid5.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"letInvalid5.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseLetInvalid6() {
		String content = this.getContentFromPath("/data/ast/modelexpression/letInvalid6.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"letInvalid6.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseFileValid() {
		String content = this.getContentFromPath("/data/ast/modelexpression/fileValid.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"fileValid.mtl"); //$NON-NLS-1$
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
	public void testParseFileValid2() {
		String content = this.getContentFromPath("/data/ast/modelexpression/fileValid2.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"fileValid2.mtl"); //$NON-NLS-1$
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
	public void testParseFileInvalid() {
		String content = this.getContentFromPath("/data/ast/modelexpression/fileInvalid.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"fileInvalid.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseFileInvalid2() {
		String content = this.getContentFromPath("/data/ast/modelexpression/fileInvalid2.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"fileInvalid2.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseFileInvalid3() {
		String content = this.getContentFromPath("/data/ast/modelexpression/fileInvalid3.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"fileInvalid3.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 2);
			checkASTDocumentationResolution(0, 0, 2);
		} else {
			fail();
		}
	}

	@Test
	public void testParseFileInvalid4() {
		String content = this.getContentFromPath("/data/ast/modelexpression/fileInvalid4.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"fileInvalid4.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseFileInvalid5() {
		String content = this.getContentFromPath("/data/ast/modelexpression/fileInvalid5.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"fileInvalid5.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseFileInvalid6() {
		String content = this.getContentFromPath("/data/ast/modelexpression/fileInvalid6.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"fileInvalid6.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseFileInvalid7() {
		String content = this.getContentFromPath("/data/ast/modelexpression/fileInvalid7.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"fileInvalid7.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseFileInvalid8() {
		String content = this.getContentFromPath("/data/ast/modelexpression/fileInvalid8.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"fileInvalid8.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 2);
			checkASTDocumentationResolution(0, 0, 2);
		} else {
			fail();
		}
	}

	@Test
	public void testParseTraceValid() {
		String content = this.getContentFromPath("/data/ast/modelexpression/traceValid.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"traceValid.mtl"); //$NON-NLS-1$
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
	public void testParseTraceValid2() {
		String content = this.getContentFromPath("/data/ast/modelexpression/traceValid2.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"traceValid2.mtl"); //$NON-NLS-1$
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
	public void testParseTraceInvalid() {
		String content = this.getContentFromPath("/data/ast/modelexpression/traceInvalid.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"traceInvalid.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseTraceInvalid2() {
		String content = this.getContentFromPath("/data/ast/modelexpression/traceInvalid2.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"traceInvalid2.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseProtectedValid() {
		String content = this.getContentFromPath("/data/ast/modelexpression/protectedValid.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"protectedValid.mtl"); //$NON-NLS-1$
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
	public void testParseProtectedValid2() {
		String content = this.getContentFromPath("/data/ast/modelexpression/protectedValid2.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"protectedValid2.mtl"); //$NON-NLS-1$
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
	public void testParseProtectedInvalid() {
		String content = this.getContentFromPath("/data/ast/modelexpression/protectedInvalid.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"protectedInvalid.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseProtectedInvalid2() {
		String content = this.getContentFromPath("/data/ast/modelexpression/protectedInvalid2.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"protectedInvalid2.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseProtectedInvalid3() {
		String content = this.getContentFromPath("/data/ast/modelexpression/protectedInvalid3.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"protectedInvalid3.mtl"); //$NON-NLS-1$
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
	public void testParseProtectedInvalid4() {
		String content = this.getContentFromPath("/data/ast/modelexpression/protectedInvalid4.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"protectedInvalid4.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(0, 0, 0);
			checkASTResolution(0, 2, 0);
			checkASTDocumentationResolution(0, 2, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseProtectedInvalid5() {
		String content = this.getContentFromPath("/data/ast/modelexpression/protectedInvalid5.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"protectedInvalid5.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseProtectedInvalid6() {
		String content = this.getContentFromPath("/data/ast/modelexpression/protectedInvalid6.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"protectedInvalid6.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseProtectedInvalid7() {
		String content = this.getContentFromPath("/data/ast/modelexpression/protectedInvalid7.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"protectedInvalid7.mtl"); //$NON-NLS-1$
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
	public void testParseCommentValid() {
		String content = this.getContentFromPath("/data/ast/modelexpression/commentValid.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"commentValid.mtl"); //$NON-NLS-1$
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
	public void testParseCommentValid2() {
		String content = this.getContentFromPath("/data/ast/modelexpression/commentValid2.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"commentValid2.mtl"); //$NON-NLS-1$
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
	public void testParseCommentInvalid() {
		String content = this.getContentFromPath("/data/ast/modelexpression/commentInvalid.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"commentInvalid.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseCommentInvalid2() {
		String content = this.getContentFromPath("/data/ast/modelexpression/commentInvalid2.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"commentInvalid2.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseCommentInvalid3() {
		String content = this.getContentFromPath("/data/ast/modelexpression/commentInvalid3.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"commentInvalid3.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseCommentInvalid4() {
		String content = this.getContentFromPath("/data/ast/modelexpression/commentInvalid4.mtl"); //$NON-NLS-1$
		IFile moduleFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/modelexpression/files"), project, //$NON-NLS-1$
				"commentInvalid4.mtl"); //$NON-NLS-1$
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
