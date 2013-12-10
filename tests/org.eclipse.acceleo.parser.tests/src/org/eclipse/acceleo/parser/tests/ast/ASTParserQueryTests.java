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

public class ASTParserQueryTests extends AbstractASTParserTests {

	@BeforeClass
	public static void setUp() {
		bundle = Platform.getBundle("org.eclipse.acceleo.parser.tests"); //$NON-NLS-1$
		project = createAcceleoProject("org.eclipse.acceleo.parser.tests.query"); //$NON-NLS-1$
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
	public void testParseQueryValid() {
		String content = this.getContentFromPath("/data/ast/query/queryValid.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryValid.mtl"); //$NON-NLS-1$
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
	public void testParseQueryDocumentation() {
		String content = this.getContentFromPath("/data/ast/query/queryDocumentation.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryDocumentation.mtl"); //$NON-NLS-1$
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
	public void testParseQueryDocumentationDeprecated() {
		String content = this.getContentFromPath("/data/ast/query/queryDocumentationDeprecated.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryDocumentationDeprecated.mtl"); //$NON-NLS-1$
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
	public void testParseQueryTODO() {
		String content = this.getContentFromPath("/data/ast/query/queryDocumentationTODO.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryDocumentationTODO.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 1, 0, 0);
			checkCST2ASTConvertion(1, 0, 0);
			checkASTResolution(1, 0, 0);
			checkASTDocumentationResolution(1, 0, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseQueryFIXME() {
		String content = this.getContentFromPath("/data/ast/query/queryDocumentationFIXME.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryDocumentationFIXME.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 1, 0, 0);
			checkCST2ASTConvertion(1, 0, 0);
			checkASTResolution(1, 0, 0);
			checkASTDocumentationResolution(1, 0, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseQueryTODOFIXME() {
		String content = this.getContentFromPath("/data/ast/query/queryDocumentationTODOFIXME.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryDocumentationTODOFIXME.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 2, 0, 0);
			checkCST2ASTConvertion(2, 0, 0);
			checkASTResolution(2, 0, 0);
			checkASTDocumentationResolution(2, 0, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseQueryMultipleDefinedTwice() {
		String content = this.getContentFromPath("/data/ast/query/queryMultipleDefinedTwice.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryMultipleDefinedTwice.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseQueryMultipleDefinedTwiceWithDifferentReturnType() {
		String content = this
				.getContentFromPath("/data/ast/query/queryMultipleDefinedTwiceWithDifferentReturnType.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryMultipleDefinedTwiceWithDifferentReturnType.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseQueryMultipleDefinedTwiceWithDifferentBody() {
		String content = this
				.getContentFromPath("/data/ast/query/queryMultipleDefinedTwiceWithDifferentBody.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryMultipleDefinedTwiceWithDifferentBody.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseQueryMultipleWithSameName() {
		String content = this.getContentFromPath("/data/ast/query/queryMultipleWithSameName.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryMultipleWithSameName.mtl"); //$NON-NLS-1$
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
	public void testParseQueryInvalidHeader() {
		String content = this.getContentFromPath("/data/ast/query/queryInvalidHeader.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryInvalidHeader.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}

		content = this.getContentFromPath("/data/ast/query/queryInvalidHeader2.mtl"); //$NON-NLS-1$
		moduleFile = createFile(content, new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryInvalidHeader2.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}

		content = this.getContentFromPath("/data/ast/query/queryInvalidHeader3.mtl"); //$NON-NLS-1$
		moduleFile = createFile(content, new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryInvalidHeader3.mtl"); //$NON-NLS-1$
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
	public void testParseQueryMissingVisibility() {
		String content = this.getContentFromPath("/data/ast/query/queryMissingVisibility.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryMissingVisibility.mtl"); //$NON-NLS-1$
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
	public void testParseQueryInvalidVisibility() {
		String content = this.getContentFromPath("/data/ast/query/queryInvalidVisibility.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryInvalidVisibility.mtl"); //$NON-NLS-1$
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
	public void testParseQueryMissingName() {
		String content = this.getContentFromPath("/data/ast/query/queryMissingName.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryMissingName.mtl"); //$NON-NLS-1$
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
	public void testParseQueryNamedQuery() {
		String content = this.getContentFromPath("/data/ast/query/queryNamedQuery.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryNamedQuery.mtl"); //$NON-NLS-1$
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
	public void testParseQueryMultipleParametersWithSameName() {
		String content = this.getContentFromPath("/data/ast/query/queryMultipleParametersWithSameName.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryMultipleParametersWithSameName.mtl"); //$NON-NLS-1$
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
	public void testParseQueryInvalidParameterType() {
		String content = this.getContentFromPath("/data/ast/query/queryInvalidParameterType.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryInvalidParameterType.mtl"); //$NON-NLS-1$
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
	public void testParseQueryMissingParameterType() {
		String content = this.getContentFromPath("/data/ast/query/queryMissingParameterType.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryMissingParameterType.mtl"); //$NON-NLS-1$
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
	public void testParseQueryInvalidParameterArea() {
		String content = this.getContentFromPath("/data/ast/query/queryInvalidParameterArea.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryInvalidParameterArea.mtl"); //$NON-NLS-1$
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
	public void testParseQueryMissingColon() {
		String content = this.getContentFromPath("/data/ast/query/queryMissingColon.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryMissingColon.mtl"); //$NON-NLS-1$
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
	public void testParseQueryInvalidReturnType() {
		String content = this.getContentFromPath("/data/ast/query/queryInvalidReturnType.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryInvalidReturnType.mtl"); //$NON-NLS-1$
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
	public void testParseQueryMissingReturnType() {
		String content = this.getContentFromPath("/data/ast/query/queryMissingReturnType.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryMissingReturnType.mtl"); //$NON-NLS-1$
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
	public void testParseQueryMissingEqual() {
		String content = this.getContentFromPath("/data/ast/query/queryMissingEqual.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryMissingEqual.mtl"); //$NON-NLS-1$
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
	public void testParseQueryNameKeyword() {
		String content = this.getContentFromPath("/data/ast/query/queryNameKeyword.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryNameKeyword.mtl"); //$NON-NLS-1$
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
	public void testParseQueryParameterNameKeyword() {
		String content = this.getContentFromPath("/data/ast/query/queryParameterNameKeyword.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryParameterNameKeyword.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(0, 1, 0);
			checkASTResolution(0, 1, 1);
			checkASTDocumentationResolution(0, 1, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseQueryInvalidForm() {
		String content = this.getContentFromPath("/data/ast/query/queryInvalidForm.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryInvalidForm.mtl"); //$NON-NLS-1$
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
	public void testParseQueryInvalidName() {
		String content = this.getContentFromPath("/data/ast/query/queryInvalidName.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryInvalidName.mtl"); //$NON-NLS-1$
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
	public void testParseQueryInvalidName2() {
		String content = this.getContentFromPath("/data/ast/query/queryInvalidName2.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryInvalidName2.mtl"); //$NON-NLS-1$
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
	public void testParseQueryMissingParenthesis() {
		String content = this.getContentFromPath("/data/ast/query/queryMissingParenthesis.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryMissingParenthesis.mtl"); //$NON-NLS-1$
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
	public void testParseQueryMissingParenthesis2() {
		String content = this.getContentFromPath("/data/ast/query/queryMissingParenthesis2.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryMissingParenthesis2.mtl"); //$NON-NLS-1$
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
	public void testParseQueryMissingParenthesis3() {
		String content = this.getContentFromPath("/data/ast/query/queryMissingParenthesis3.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/query/files"), project, //$NON-NLS-1$
				"queryMissingParenthesis3.mtl"); //$NON-NLS-1$
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
