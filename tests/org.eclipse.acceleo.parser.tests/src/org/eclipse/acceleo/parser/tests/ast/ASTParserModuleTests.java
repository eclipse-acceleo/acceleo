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
import org.eclipse.acceleo.model.mtl.Documentation;
import org.eclipse.acceleo.model.mtl.Module;
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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ASTParserModuleTests extends AbstractASTParserTests {

	@BeforeClass
	public static void setUp() {
		bundle = Platform.getBundle("org.eclipse.acceleo.parser.tests"); //$NON-NLS-1$
		project = createAcceleoProject("org.eclipse.acceleo.parser.tests.module"); //$NON-NLS-1$
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
	public void testParseModuleValid() {
		File file = this.getFileFromPath("/data/ast/module/moduleValid.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleValid.mtl"); //$NON-NLS-1$
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
	public void testParseModuleInvalidHeader() {
		File file = this.getFileFromPath("/data/ast/module/moduleInvalidHeader.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidHeader.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleDocumentation1() {
		File file = this.getFileFromPath("/data/ast/module/moduleDocumentation1.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleDocumentation1.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(0, 0, 0);
			checkASTResolution(0, 0, 0);
			checkASTDocumentationResolution(0, 0, 0);

			Module ast = this.sourceBuffer.getAST();
			assertNotNull(ast);
			Documentation documentation = ast.getDocumentation();
			assertNotNull(documentation);
			assertNotNull(documentation.getBody());
			assertNotNull(documentation.getBody().getValue());
			assertTrue(documentation.getBody().getValue().contains("this is my documentation")); //$NON-NLS-1$
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleDocumentation2() {
		File file = this.getFileFromPath("/data/ast/module/moduleDocumentation2.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleDocumentation2.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(0, 0, 0);
			checkASTResolution(0, 0, 0);
			checkASTDocumentationResolution(0, 0, 0);

			Module ast = this.sourceBuffer.getAST();
			assertNotNull(ast);
			Documentation documentation = ast.getDocumentation();
			assertNotNull(documentation);
			assertNotNull(documentation.getBody());
			assertNotNull(documentation.getBody().getValue());
			assertTrue(documentation.getBody().getValue().contains("this is my documentation")); //$NON-NLS-1$
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleDocumentation3() {
		File file = this.getFileFromPath("/data/ast/module/moduleDocumentation3.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleDocumentation3.mtl"); //$NON-NLS-1$
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
	public void testParseModuleDocumentationDeprecated() {
		File file = this.getFileFromPath("/data/ast/module/moduleDocumentationDeprecated.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleDocumentationDeprecated.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(0, 0, 0);
			checkASTResolution(0, 0, 0);
			checkASTDocumentationResolution(0, 1, 0);

			Module ast = this.sourceBuffer.getAST();
			assertNotNull(ast);
			assertTrue(ast.isDeprecated());
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleExtendsOtherModule() {
		File file = this.getFileFromPath("/data/ast/module/moduleExtendsOtherModule.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleExtendsOtherModule.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(0, 1, 0);
			checkASTResolution(0, 1, 0);
			checkASTDocumentationResolution(0, 1, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleExtendsSelf() {
		File file = this.getFileFromPath("/data/ast/module/moduleExtendsSelf.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleExtendsSelf.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(0, 1, 0);
			checkASTResolution(0, 1, 1);
			checkASTDocumentationResolution(0, 1, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleExtendsInvalidModule() {
		File file = this.getFileFromPath("/data/ast/module/moduleExtendsInvalidModule.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleExtendsInvalidModule.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(0, 1, 0);
			checkASTResolution(0, 1, 1);
			checkASTDocumentationResolution(0, 1, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleMultipleExtends() {
		File file = this.getFileFromPath("/data/ast/module/moduleMultipleExtends.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleMultipleExtends.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 2, 0);
			checkCST2ASTConvertion(0, 3, 0);
			checkASTResolution(0, 3, 0);
			checkASTDocumentationResolution(0, 3, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleMultipleMetaModel() {
		File file = this.getFileFromPath("/data/ast/module/moduleMultipleMetaModel.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleMultipleMetaModel.mtl"); //$NON-NLS-1$
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
	public void testParseModuleMultipleSameMetaModel() {
		File file = this.getFileFromPath("/data/ast/module/moduleMultipleSameMetaModel.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleMultipleSameMetaModel.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(0, 0, 0);
			checkASTResolution(0, 1, 0);
			checkASTDocumentationResolution(0, 1, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleInvalidMetaModel() {
		File file = this.getFileFromPath("/data/ast/module/moduleInvalidMetaModel.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidMetaModel.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleExtendsIncompatibleMetaModel() {
		File file = this.getFileFromPath("/data/ast/module/moduleExtendsIncompatibleMetaModel.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleExtendsIncompatibleMetaModel.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(0, 1, 0);
			checkASTResolution(0, 2, 0);
			checkASTDocumentationResolution(0, 2, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleExtendsMultipleSameModule() {
		File file = this.getFileFromPath("/data/ast/module/moduleExtendsMultipleSameModule.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleExtendsMultipleSameModule.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 3, 0);
			checkCST2ASTConvertion(0, 4, 0);
			checkASTResolution(0, 4, 0);
			checkASTDocumentationResolution(0, 4, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleExtendsDeprecatedModule() {
		File file = this.getFileFromPath("/data/ast/module/moduleExtendsDeprecatedModule.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleExtendsDeprecatedModule.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(0, 1, 0);
			checkASTResolution(0, 2, 0);
			checkASTDocumentationResolution(0, 2, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleNamedModule() {
		File file = this.getFileFromPath("/data/ast/module/module.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"module.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(0, 1, 0);
			checkASTResolution(0, 1, 0);
			checkASTDocumentationResolution(0, 1, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleNamedKeyword() {
		File file = this.getFileFromPath("/data/ast/module/protected.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"protected.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 1);
			checkCST2ASTConvertion(0, 1, 1);
			checkASTResolution(0, 1, 1);
			checkASTDocumentationResolution(0, 1, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleImport() {
		File file = this.getFileFromPath("/data/ast/module/moduleImport.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleImport.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(0, 1, 0);
			checkASTResolution(0, 1, 0);
			checkASTDocumentationResolution(0, 1, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleMultipleImport() {
		File file = this.getFileFromPath("/data/ast/module/moduleMultipleImport.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleMultipleImport.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 3, 0);
			checkCST2ASTConvertion(0, 3, 0);
			checkASTResolution(0, 3, 0);
			checkASTDocumentationResolution(0, 3, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleDeprecatedImport() {
		File file = this.getFileFromPath("/data/ast/module/moduleDeprecatedImport.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleDeprecatedImport.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(0, 1, 0);
			checkASTResolution(0, 2, 0);
			checkASTDocumentationResolution(0, 2, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleInvalidImport() {
		File file = this.getFileFromPath("/data/ast/module/moduleInvalidImport.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidImport.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(0, 1, 0);
			checkASTResolution(0, 1, 1);
			checkASTDocumentationResolution(0, 1, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleDuplicatedImport() {
		File file = this.getFileFromPath("/data/ast/module/moduleDuplicatedImport.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleDuplicatedImport.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 3, 0);
			checkCST2ASTConvertion(0, 3, 0);
			checkASTResolution(0, 3, 0);
			checkASTDocumentationResolution(0, 3, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleImportSelf() {
		File file = this.getFileFromPath("/data/ast/module/moduleImportSelf.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleImportSelf.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(0, 1, 0);
			checkASTResolution(0, 1, 1);
			checkASTDocumentationResolution(0, 1, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleQualifiedNameExtends() {
		File file = this.getFileFromPath("/data/ast/module/moduleQualifiedNameExtends.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleQualifiedNameExtends.mtl"); //$NON-NLS-1$
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
	public void testParseModuleQualifiedNameImports() {
		File file = this.getFileFromPath("/data/ast/module/moduleQualifiedNameImports.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleQualifiedNameImports.mtl"); //$NON-NLS-1$
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
	public void testParseModuleMissingMetaModel() {
		File file = this.getFileFromPath("/data/ast/module/moduleMissingMetaModel.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleMissingMetaModel.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleEmptyMetaModel() {
		File file = this.getFileFromPath("/data/ast/module/moduleEmptyMetaModel.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleEmptyMetaModel.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleMissingParenthesis() {
		File file = this.getFileFromPath("/data/ast/module/moduleMissingParenthesis.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleMissingParenthesis.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleInvalidFile() {
		File file = this.getFileFromPath("/data/ast/module/moduleInvalidFile.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidFile.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleInvalidFile2() {
		File file = this.getFileFromPath("/data/ast/module/moduleInvalidFile2.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidFile2.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 2);
			checkCST2ASTConvertion(0, 0, 2);
			checkASTResolution(0, 0, 2);
			checkASTDocumentationResolution(0, 0, 2);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleInvalidDefinition() {
		File file = this.getFileFromPath("/data/ast/module/moduleInvalidDefinition.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidDefinition.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleInvalidForm() {
		File file = this.getFileFromPath("/data/ast/module/moduleInvalidForm.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidForm.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleInvalidImportForm() {
		File file = this.getFileFromPath("/data/ast/module/moduleInvalidImportForm.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidImportForm.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleInvalidDocumentation() {
		File file = this.getFileFromPath("/data/ast/module/moduleInvalidDocumentation.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidDocumentation.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleInvalidDocumentation2() {
		File file = this.getFileFromPath("/data/ast/module/moduleInvalidDocumentation2.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidDocumentation2.mtl"); //$NON-NLS-1$
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
	public void testParseModuleAlternateComment() {
		File file = this.getFileFromPath("/data/ast/module/moduleAlternateComment.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleAlternateComment.mtl"); //$NON-NLS-1$
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
	public void testParseModuleWithoutEncoding() {
		File file = this.getFileFromPath("/data/ast/module/moduleWithoutEncoding.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleWithoutEncoding.mtl"); //$NON-NLS-1$
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
	public void testParseModuleWithoutEncodingDocumentation() {
		File file = this.getFileFromPath("/data/ast/module/moduleWithoutEncodingDocumentation.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleWithoutEncodingDocumentation.mtl"); //$NON-NLS-1$
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
	public void testParseModuleInvalidComment() {
		File file = this.getFileFromPath("/data/ast/module/moduleInvalidComment.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidComment.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleInvalidComment2() {
		File file = this.getFileFromPath("/data/ast/module/moduleInvalidComment2.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidComment2.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleInvalidComment3() {
		File file = this.getFileFromPath("/data/ast/module/moduleInvalidComment3.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidComment3.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleInvalidComment4() {
		File file = this.getFileFromPath("/data/ast/module/moduleInvalidComment4.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidComment4.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleInvalidAlternateComment() {
		File file = this.getFileFromPath("/data/ast/module/moduleInvalidAlternateComment.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidAlternateComment.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 1);
			checkCST2ASTConvertion(0, 1, 1);
			checkASTResolution(0, 1, 1);
			checkASTDocumentationResolution(0, 1, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleInvalidModel() {
		File file = this.getFileFromPath("/data/ast/module/moduleInvalidModel.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidModel.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleInvalidModuleElement() {
		File file = this.getFileFromPath("/data/ast/module/moduleInvalidModuleElement.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidModuleElement.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseModuleInvalidExtends() {
		File file = this.getFileFromPath("/data/ast/module/moduleInvalidExtends.mtl"); //$NON-NLS-1$
		StringBuffer buffer = FileContent.getFileContent(file);
		IFile moduleFile = createFile(buffer,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidextends.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && buffer.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 1);
			checkCST2ASTConvertion(0, 1, 1);
			checkASTResolution(0, 1, 1);
			checkASTDocumentationResolution(0, 1, 1);
		} else {
			fail();
		}
	}

}
