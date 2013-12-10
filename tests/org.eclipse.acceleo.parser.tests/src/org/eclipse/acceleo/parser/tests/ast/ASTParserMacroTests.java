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

/**
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class ASTParserMacroTests extends AbstractASTParserTests {

	@BeforeClass
	public static void setUp() {
		bundle = Platform.getBundle("org.eclipse.acceleo.parser.tests"); //$NON-NLS-1$
		project = createAcceleoProject("org.eclipse.acceleo.parser.tests.macro"); //$NON-NLS-1$
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
	public void testParseMacroValid() {
		String content = this.getContentFromPath("/data/ast/macro/macroValid.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroValid.mtl"); //$NON-NLS-1$
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
	public void testParseMacroValid2() {
		String content = this.getContentFromPath("/data/ast/macro/macroValid2.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroValid2.mtl"); //$NON-NLS-1$
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
	public void testParseMacroDocumentation() {
		String content = this.getContentFromPath("/data/ast/macro/macroDocumentation.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroDocumentation.mtl"); //$NON-NLS-1$
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
	public void testParseMacroDocumentationDeprecated() {
		String content = this.getContentFromPath("/data/ast/macro/macroDocumentationDeprecated.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroDocumentationDeprecated.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(0, 1, 0);
			checkASTResolution(0, 1, 0);
			checkASTDocumentationResolution(0, 1, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseMacroInvalidName() {
		String content = this.getContentFromPath("/data/ast/macro/macroInvalidName.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroInvalidName.mtl"); //$NON-NLS-1$
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
	public void testParseMacroNamedMacro() {
		String content = this.getContentFromPath("/data/ast/macro/macroNamedMacro.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroNamedMacro.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(0, 1, 0);
			checkASTResolution(0, 1, 0);
			checkASTDocumentationResolution(0, 1, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseMacroNameKeyword() {
		String content = this.getContentFromPath("/data/ast/macro/macroNameKeyword.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroNameKeyword.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(0, 1, 0);
			checkASTResolution(0, 1, 0);
			checkASTDocumentationResolution(0, 1, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseMacroParamNameKeyword() {
		String content = this.getContentFromPath("/data/ast/macro/macroParamNameKeyword.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroParamNameKeyword.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(0, 1, 0);
			checkASTResolution(0, 1, 0);
			checkASTDocumentationResolution(0, 1, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseMacroInvalidHeader() {
		String content = this.getContentFromPath("/data/ast/macro/macroInvalidHeader.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroInvalidHeader.mtl"); //$NON-NLS-1$
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
	public void testParseMacroInvalidHeader2() {
		String content = this.getContentFromPath("/data/ast/macro/macroInvalidHeader2.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroInvalidHeader2.mtl"); //$NON-NLS-1$
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
	public void testParseMacroInvalidReturnType() {
		String content = this.getContentFromPath("/data/ast/macro/macroInvalidReturnType.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroInvalidReturnType.mtl"); //$NON-NLS-1$
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
	public void testParseMacroInvalidParamType() {
		String content = this.getContentFromPath("/data/ast/macro/macroInvalidParamType.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroInvalidParamType.mtl"); //$NON-NLS-1$
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
	public void testParseMacroInvalidParamName() {
		String content = this.getContentFromPath("/data/ast/macro/macroInvalidParamName.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroInvalidParamName.mtl"); //$NON-NLS-1$
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
	public void testParseMacroMissingVisibility() {
		String content = this.getContentFromPath("/data/ast/macro/macroMissingVisibility.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroMissingVisibility.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(0, 1, 0);
			checkASTResolution(0, 1, 0);
			checkASTDocumentationResolution(0, 1, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseMacroInvalidVisibility() {
		String content = this.getContentFromPath("/data/ast/macro/macroInvalidVisibility.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroInvalidVisibility.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 1);
			checkCST2ASTConvertion(0, 1, 1);
			checkASTResolution(0, 1, 1);
			checkASTDocumentationResolution(0, 1, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseMacroMissingName() {
		String content = this.getContentFromPath("/data/ast/macro/macroMissingName.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroMissingName.mtl"); //$NON-NLS-1$
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
	public void testParseMacroMissingReturnType() {
		String content = this.getContentFromPath("/data/ast/macro/macroMissingReturnType.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroMissingReturnType.mtl"); //$NON-NLS-1$
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
	public void testParseMacroMissingParamName() {
		String content = this.getContentFromPath("/data/ast/macro/macroMissingParamName.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroMissingParamName.mtl"); //$NON-NLS-1$
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
	public void testParseMacroMissingParamType() {
		String content = this.getContentFromPath("/data/ast/macro/macroMissingParamType.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroMissingParamType.mtl"); //$NON-NLS-1$
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
	public void testParseMacroMissingParenthesis() {
		String content = this.getContentFromPath("/data/ast/macro/macroMissingParenthesis.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroMissingParenthesis.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 1);
			checkCST2ASTConvertion(0, 1, 1);
			checkASTResolution(0, 1, 2);
			checkASTDocumentationResolution(0, 1, 2);
		} else {
			fail();
		}
	}

	@Test
	public void testParseMacroMissingParenthesis2() {
		String content = this.getContentFromPath("/data/ast/macro/macroMissingParenthesis2.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroMissingParenthesis2.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 2);
			checkCST2ASTConvertion(0, 0, 2);
			checkASTResolution(0, 0, 3);
			checkASTDocumentationResolution(0, 0, 3);
		} else {
			fail();
		}
	}

	@Test
	public void testParseMacroMissingParenthesis3() {
		String content = this.getContentFromPath("/data/ast/macro/macroMissingParenthesis3.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/macro/files"), project, //$NON-NLS-1$
				"macroMissingParenthesis3.mtl"); //$NON-NLS-1$
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
