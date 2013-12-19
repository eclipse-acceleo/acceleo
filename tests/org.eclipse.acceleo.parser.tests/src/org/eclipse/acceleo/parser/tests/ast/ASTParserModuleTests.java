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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.acceleo.model.mtl.Documentation;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class ASTParserModuleTests extends AbstractASTParserTests {

	@BeforeClass
	public static void setUp() {
		if (EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI) == null) {
			EPackage.Registry.INSTANCE.put(EcorePackage.eNS_URI, EcorePackage.eINSTANCE);
		}
		if (EPackage.Registry.INSTANCE.getEPackage(GenModelPackage.eNS_URI) == null) {
			EPackage.Registry.INSTANCE.put(GenModelPackage.eNS_URI, GenModelPackage.eINSTANCE);
		}
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
		String content = this.getContentFromPath("/data/ast/module/moduleValid.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleValid.mtl"); //$NON-NLS-1$
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
	public void testParseModuleInvalidHeader() {
		String content = this.getContentFromPath("/data/ast/module/moduleInvalidHeader.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidHeader.mtl"); //$NON-NLS-1$
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
	public void testParseModuleDocumentation1() {
		String content = this.getContentFromPath("/data/ast/module/moduleDocumentation1.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleDocumentation1.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
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
		String content = this.getContentFromPath("/data/ast/module/moduleDocumentation2.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleDocumentation2.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
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
		String content = this.getContentFromPath("/data/ast/module/moduleDocumentation3.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleDocumentation3.mtl"); //$NON-NLS-1$
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
	public void testParseModuleDocumentationDeprecated() {
		String content = this.getContentFromPath("/data/ast/module/moduleDocumentationDeprecated.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleDocumentationDeprecated.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
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

	@Ignore
	@Test
	public void testParseModuleExtendsOtherModule() {
		String content = this.getContentFromPath("/data/ast/module/moduleValid.mtl"); //$NON-NLS-1$
		IFile moduleValidIFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleValid.mtl"); //$NON-NLS-1$

		this.parseAndLoadModule(moduleValidIFile);

		content = this.getContentFromPath("/data/ast/module/moduleExtendsOtherModule.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleExtendsOtherModule.mtl"); //$NON-NLS-1$

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
	public void testParseModuleExtendsSelf() {
		String content = this.getContentFromPath("/data/ast/module/moduleExtendsSelf.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleExtendsSelf.mtl"); //$NON-NLS-1$
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
	public void testParseModuleExtendsInvalidModule() {
		String content = this.getContentFromPath("/data/ast/module/moduleExtendsInvalidModule.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleExtendsInvalidModule.mtl"); //$NON-NLS-1$
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
	public void testParseModuleMultipleExtends() {
		String content = this.getContentFromPath("/data/ast/module/moduleValid.mtl"); //$NON-NLS-1$
		IFile moduleValidIFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleValid.mtl"); //$NON-NLS-1$

		this.parseAndLoadModule(moduleValidIFile);

		content = this.getContentFromPath("/data/ast/module/moduleMultipleExtends.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleMultipleExtends.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
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
		String content = this.getContentFromPath("/data/ast/module/moduleMultipleMetaModel.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleMultipleMetaModel.mtl"); //$NON-NLS-1$
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
	public void testParseModuleMultipleSameMetaModel() {
		String content = this.getContentFromPath("/data/ast/module/moduleMultipleSameMetaModel.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleMultipleSameMetaModel.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
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
		String content = this.getContentFromPath("/data/ast/module/moduleInvalidMetaModel.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidMetaModel.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Ignore
	@Test
	public void testParseModuleExtendsIncompatibleMetaModel() {
		String content = this.getContentFromPath("/data/ast/module/moduleValid.mtl"); //$NON-NLS-1$
		IFile moduleValidIFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleValid.mtl"); //$NON-NLS-1$

		this.parseAndLoadModule(moduleValidIFile);

		content = this.getContentFromPath("/data/ast/module/moduleExtendsIncompatibleMetaModel.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleExtendsIncompatibleMetaModel.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
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
		String content = this.getContentFromPath("/data/ast/module/moduleExtendsMultipleSameModule.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleExtendsMultipleSameModule.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 3, 0);
			checkCST2ASTConvertion(0, 4, 0);
			checkASTResolution(0, 4, 0);
			checkASTDocumentationResolution(0, 4, 0);
		} else {
			fail();
		}
	}

	@Ignore
	@Test
	public void testParseModuleExtendsDeprecatedModule() {
		String content = this.getContentFromPath("/data/ast/module/moduleExtendsDeprecatedModule.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleExtendsDeprecatedModule.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
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
		String content = this.getContentFromPath("/data/ast/module/module.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"module.mtl"); //$NON-NLS-1$
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
	public void testParseModuleNamedKeyword() {
		String content = this.getContentFromPath("/data/ast/module/protected.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"protected.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 1);
			checkCST2ASTConvertion(0, 1, 1);
			checkASTResolution(0, 1, 1);
			checkASTDocumentationResolution(0, 1, 1);
		} else {
			fail();
		}
	}

	@Ignore
	@Test
	public void testParseModuleImport() {
		String content = this.getContentFromPath("/data/ast/module/moduleValid.mtl"); //$NON-NLS-1$
		IFile moduleValidIFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleValid.mtl"); //$NON-NLS-1$

		this.parseAndLoadModule(moduleValidIFile);

		content = this.getContentFromPath("/data/ast/module/moduleImport.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleImport.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(0, 1, 0);
			checkASTResolution(0, 1, 0);
			checkASTDocumentationResolution(0, 1, 0);
		} else {
			fail();
		}
	}

	@Ignore
	@Test
	public void testParseModuleMultipleImport() {
		String content = this.getContentFromPath("/data/ast/module/moduleValid.mtl"); //$NON-NLS-1$
		IFile moduleValidIFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleValid.mtl"); //$NON-NLS-1$

		this.parseAndLoadModule(moduleValidIFile);

		content = this.getContentFromPath("/data/ast/module/moduleMultipleImport.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleMultipleImport.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
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
		String content = this.getContentFromPath("/data/ast/module/moduleDeprecatedImport.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleDeprecatedImport.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
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
		String content = this.getContentFromPath("/data/ast/module/moduleInvalidImport.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidImport.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(0, 1, 0);
			checkASTResolution(0, 1, 1);
			checkASTDocumentationResolution(0, 1, 1);
		} else {
			fail();
		}
	}

	@Ignore
	@Test
	public void testParseModuleDuplicatedImport() {
		String content = this.getContentFromPath("/data/ast/module/moduleDuplicatedImport.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleDuplicatedImport.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
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
		String content = this.getContentFromPath("/data/ast/module/moduleImportSelf.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleImportSelf.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(0, 1, 0);
			checkASTResolution(0, 1, 1);
			checkASTDocumentationResolution(0, 1, 1);
		} else {
			fail();
		}
	}

	@Ignore
	@Test
	public void testParseModuleQualifiedNameExtends() {
		String content = this.getContentFromPath("/data/ast/module/moduleQualifiedNameExtends.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleQualifiedNameExtends.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(0, 0, 0);
			checkASTResolution(0, 0, 0);
			checkASTDocumentationResolution(0, 0, 0);
		} else {
			fail();
		}
	}

	@Ignore
	@Test
	public void testParseModuleQualifiedNameImports() {
		String content = this.getContentFromPath("/data/ast/module/moduleQualifiedNameImports.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleQualifiedNameImports.mtl"); //$NON-NLS-1$
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
	public void testParseModuleMissingMetaModel() {
		String content = this.getContentFromPath("/data/ast/module/moduleMissingMetaModel.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleMissingMetaModel.mtl"); //$NON-NLS-1$
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
	public void testParseModuleEmptyMetaModel() {
		String content = this.getContentFromPath("/data/ast/module/moduleEmptyMetaModel.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleEmptyMetaModel.mtl"); //$NON-NLS-1$
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
	public void testParseModuleMissingParenthesis() {
		String content = this.getContentFromPath("/data/ast/module/moduleMissingParenthesis.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleMissingParenthesis.mtl"); //$NON-NLS-1$
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
	public void testParseModuleInvalidFile() {
		String content = this.getContentFromPath("/data/ast/module/moduleInvalidFile.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidFile.mtl"); //$NON-NLS-1$
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
	public void testParseModuleInvalidFile2() {
		String content = this.getContentFromPath("/data/ast/module/moduleInvalidFile2.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidFile2.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
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
		String content = this.getContentFromPath("/data/ast/module/moduleInvalidDefinition.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidDefinition.mtl"); //$NON-NLS-1$
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
	public void testParseModuleInvalidForm() {
		String content = this.getContentFromPath("/data/ast/module/moduleInvalidForm.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidForm.mtl"); //$NON-NLS-1$
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
	public void testParseModuleInvalidImportForm() {
		String content = this.getContentFromPath("/data/ast/module/moduleInvalidImportForm.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidImportForm.mtl"); //$NON-NLS-1$
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
	public void testParseModuleInvalidDocumentation() {
		String content = this.getContentFromPath("/data/ast/module/moduleInvalidDocumentation.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidDocumentation.mtl"); //$NON-NLS-1$
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
	public void testParseModuleInvalidDocumentation2() {
		String content = this.getContentFromPath("/data/ast/module/moduleInvalidDocumentation2.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidDocumentation2.mtl"); //$NON-NLS-1$
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
	public void testParseModuleAlternateComment() {
		String content = this.getContentFromPath("/data/ast/module/moduleAlternateComment.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleAlternateComment.mtl"); //$NON-NLS-1$
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
	public void testParseModuleWithoutEncoding() {
		String content = this.getContentFromPath("/data/ast/module/moduleWithoutEncoding.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleWithoutEncoding.mtl"); //$NON-NLS-1$
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
	public void testParseModuleWithoutEncodingDocumentation() {
		String content = this.getContentFromPath("/data/ast/module/moduleWithoutEncodingDocumentation.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleWithoutEncodingDocumentation.mtl"); //$NON-NLS-1$
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
	public void testParseModuleInvalidComment() {
		String content = this.getContentFromPath("/data/ast/module/moduleInvalidComment.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidComment.mtl"); //$NON-NLS-1$
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
	public void testParseModuleInvalidComment2() {
		String content = this.getContentFromPath("/data/ast/module/moduleInvalidComment2.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidComment2.mtl"); //$NON-NLS-1$
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
	public void testParseModuleInvalidComment3() {
		String content = this.getContentFromPath("/data/ast/module/moduleInvalidComment3.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidComment3.mtl"); //$NON-NLS-1$
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
	public void testParseModuleInvalidComment4() {
		String content = this.getContentFromPath("/data/ast/module/moduleInvalidComment4.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidComment4.mtl"); //$NON-NLS-1$
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
	public void testParseModuleInvalidAlternateComment() {
		String content = this.getContentFromPath("/data/ast/module/moduleInvalidAlternateComment.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidAlternateComment.mtl"); //$NON-NLS-1$
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
	public void testParseModuleInvalidModel() {
		String content = this.getContentFromPath("/data/ast/module/moduleInvalidModel.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidModel.mtl"); //$NON-NLS-1$
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
	public void testParseModuleInvalidModuleElement() {
		String content = this.getContentFromPath("/data/ast/module/moduleInvalidModuleElement.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidModuleElement.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 1);
			checkCST2ASTConvertion(0, 0, 1);
			checkASTResolution(0, 0, 1);
			checkASTDocumentationResolution(0, 0, 1);
		} else {
			fail();
		}
	}

	@Ignore
	@Test
	public void testParseModuleInvalidExtends() {
		String content = this.getContentFromPath("/data/ast/module/moduleInvalidExtends.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/module/files"), project, //$NON-NLS-1$
				"moduleInvalidextends.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 1);
			checkCST2ASTConvertion(0, 1, 1);
			checkASTResolution(0, 1, 1);
			checkASTDocumentationResolution(0, 1, 1);
		} else {
			fail();
		}
	}

}
