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
import org.junit.Ignore;
import org.junit.Test;

public class ASTParserTemplateTests extends AbstractASTParserTests {

	@BeforeClass
	public static void setUp() {
		bundle = Platform.getBundle("org.eclipse.acceleo.parser.tests"); //$NON-NLS-1$
		project = createAcceleoProject("org.eclipse.acceleo.parser.tests.template"); //$NON-NLS-1$
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
	public void testParseTemplateValid() {
		String content = this.getContentFromPath("/data/ast/template/templateValid.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateValid.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateDocumentation() {
		String content = this.getContentFromPath("/data/ast/template/templateDocumentation.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateDocumentation.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateOverride() {
		String content = this.getContentFromPath("/data/ast/template/templateOverride.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateOverride.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(1, 1, 0);
			checkASTResolution(1, 1, 0);
			checkASTDocumentationResolution(1, 1, 0);
		} else {
			fail();
		}
	}

	@Ignore
	@Test
	public void testParseTemplateOverrideQualifiedName() {
		String content = this.getContentFromPath("/data/ast/template/templateOverrideQualifiedName.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateOverrideQualifiedName.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(1, 0, 0);
			checkASTResolution(1, 0, 0);
			checkASTDocumentationResolution(1, 0, 0);
		} else {
			fail();
		}
	}

	@Ignore
	@Test
	public void testParseTemplateOverrideInSameModule() {
		String content = this.getContentFromPath("/data/ast/template/templateOverrideInSameModule.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateOverrideInSameModule.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(1, 1, 0);
			checkASTResolution(1, 1, 1);
			checkASTDocumentationResolution(1, 1, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseTemplateOverrideInSameModuleQualifiedName() {
		String content = this
				.getContentFromPath("/data/ast/template/templateOverrideInSameModuleQualifiedName.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateOverrideInSameModuleQualifiedName.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(1, 1, 0);
			checkASTResolution(1, 1, 1);
			checkASTDocumentationResolution(1, 1, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseTemplateOverrideMissingTemplate() {
		String content = this.getContentFromPath("/data/ast/template/templateOverrideMissingTemplate.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateOverrideMissingTemplate.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(1, 0, 0);
			checkASTResolution(1, 0, 1);
			checkASTDocumentationResolution(1, 0, 1);
		} else {
			fail();
		}
	}

	@Ignore
	@Test
	public void testParseTemplateMultipleOverride() {
		String content = this.getContentFromPath("/data/ast/module/templateValid.mtl"); //$NON-NLS-1$
		IFile moduleValidIFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateValid.mtl"); //$NON-NLS-1$

		this.parseAndLoadModule(moduleValidIFile);

		content = this.getContentFromPath("/data/ast/template/templateMultipleOverride.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMultipleOverride.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 2, 0);
			checkCST2ASTConvertion(1, 4, 0);
			checkASTResolution(1, 4, 0);
			checkASTDocumentationResolution(1, 4, 0);
		} else {
			fail();
		}
	}

	@Ignore
	@Test
	public void testParseTemplateMultipleOverrideQualifiedName() {
		String content = this.getContentFromPath("/data/ast/module/templateValid.mtl"); //$NON-NLS-1$
		IFile moduleValidIFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateValid.mtl"); //$NON-NLS-1$

		this.parseAndLoadModule(moduleValidIFile);

		content = this.getContentFromPath("/data/ast/template/templateMultipleOverrideQualifiedName.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMultipleOverrideQualifiedName.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 2, 0);
			checkCST2ASTConvertion(1, 4, 0);
			checkASTResolution(1, 4, 0);
			checkASTDocumentationResolution(1, 4, 0);
		} else {
			fail();
		}
	}

	@Ignore
	@Test
	public void testParseTemplateMultipleOverrideSameValue() {
		String content = this.getContentFromPath("/data/ast/module/templateValid.mtl"); //$NON-NLS-1$
		IFile moduleValidIFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateValid.mtl"); //$NON-NLS-1$

		this.parseAndLoadModule(moduleValidIFile);

		content = this.getContentFromPath("/data/ast/template/templateMultipleOverrideSameValue.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMultipleOverrideSameValue.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 2, 0);
			checkCST2ASTConvertion(1, 4, 0);
			checkASTResolution(1, 5, 0);
			checkASTDocumentationResolution(1, 5, 0);
		} else {
			fail();
		}
	}

	@Ignore
	@Test
	public void testParseTemplateMultipleOverrideSameValueQualifiedName() {
		String content = this.getContentFromPath("/data/ast/module/templateValid.mtl"); //$NON-NLS-1$
		IFile moduleValidIFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateValid.mtl"); //$NON-NLS-1$

		this.parseAndLoadModule(moduleValidIFile);

		content = this
				.getContentFromPath("/data/ast/template/templateMultipleOverrideSameValueQualifiedName.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMultipleOverrideSameValueQualifiedName.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 2, 0);
			checkCST2ASTConvertion(1, 4, 0);
			checkASTResolution(1, 5, 0);
			checkASTDocumentationResolution(1, 5, 0);
		} else {
			fail();
		}
	}

	@Ignore
	@Test
	public void testParseTemplateMultipleOverrideInSameModule() {
		String content = this.getContentFromPath("/data/ast/module/templateValid.mtl"); //$NON-NLS-1$
		IFile moduleValidIFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateValid.mtl"); //$NON-NLS-1$

		this.parseAndLoadModule(moduleValidIFile);

		content = this.getContentFromPath("/data/ast/template/templateMultipleOverrideInSameModule.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMultipleOverrideInSameModule.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 2, 0);
			checkCST2ASTConvertion(1, 4, 0);
			checkASTResolution(1, 4, 1);
			checkASTDocumentationResolution(1, 4, 1);
		} else {
			fail();
		}
	}

	@Ignore
	@Test
	public void testParseTemplateMultipleOverrideInSameModuleQualifiedName() {
		String content = this.getContentFromPath("/data/ast/module/templateValid.mtl"); //$NON-NLS-1$
		IFile moduleValidIFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateValid.mtl"); //$NON-NLS-1$

		this.parseAndLoadModule(moduleValidIFile);

		content = this
				.getContentFromPath("/data/ast/template/templateMultipleOverrideInSameModuleQualifiedName.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMultipleOverrideInSameModuleQualifiedName.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 2, 0);
			checkCST2ASTConvertion(1, 4, 0);
			checkASTResolution(1, 4, 1);
			checkASTDocumentationResolution(1, 4, 1);
		} else {
			fail();
		}
	}

	@Test
	public void testParseTemplatePost() {
		String content = this.getContentFromPath("/data/ast/template/templatePost.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templatePost.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateGuard() {
		String content = this.getContentFromPath("/data/ast/template/templateGuard.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateGuard.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateVariableInit() {
		String content = this.getContentFromPath("/data/ast/template/templateVariableInit.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateVariableInit.mtl"); //$NON-NLS-1$
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
	public void testParseTemplatePostAndGuard() {
		String content = this.getContentFromPath("/data/ast/template/templatePostAndGuard.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templatePostAndGuard.mtl"); //$NON-NLS-1$
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
	public void testParseTemplatePostGuardVariableInitOverridesQualifiedName() {
		String content = this.getContentFromPath("/data/ast/module/templateValid.mtl"); //$NON-NLS-1$
		IFile moduleValidIFile = createFile(content, new Path(
				"/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateValid.mtl"); //$NON-NLS-1$

		this.parseAndLoadModule(moduleValidIFile);

		content = this
				.getContentFromPath("/data/ast/template/templatePostGuardVariableInitOverridesQualifiedName.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templatePostGuardVariableInitOverridesQualifiedName.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(1, 0, 0);
			checkASTResolution(1, 0, 0);
			checkASTDocumentationResolution(1, 0, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseTemplateDocumentationDeprecated() {
		String content = this.getContentFromPath("/data/ast/template/templateDocumentationDeprecated.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateDocumentationDeprecated.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateDocumentationTODO() {
		String content = this.getContentFromPath("/data/ast/template/templateDocumentationTODO.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateDocumentationTODO.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateDocumentationFIXME() {
		String content = this.getContentFromPath("/data/ast/template/templateDocumentationFIXME.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateDocumentationFIXME.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateDocumentationTODOFIXME() {
		String content = this.getContentFromPath("/data/ast/template/templateDocumentationTODOFIXME.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateDocumentationTODOFIXME.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateOverrideDeprecated() {
		String content = this.getContentFromPath("/data/ast/template/templateOverrideDeprecated.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateOverrideDeprecated.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 1, 0);
			checkCST2ASTConvertion(1, 1, 0);
			checkASTResolution(1, 2, 0);
			checkASTDocumentationResolution(1, 2, 0);
		} else {
			fail();
		}
	}

	@Test
	public void testParseTemplateParamNameKeyword() {
		String content = this.getContentFromPath("/data/ast/template/templateParamNameKeyword.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateParamNameKeyword.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateNameKeyword() {
		String content = this.getContentFromPath("/data/ast/template/templateNameKeyword.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateNameKeyword.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateOtherForm() {
		String content = this.getContentFromPath("/data/ast/template/templateOtherForm.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateOtherForm.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateInvalidHeader() {
		String content = this.getContentFromPath("/data/ast/template/templateInvalidHeader.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateInvalidHeader.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateInvalidName() {
		String content = this.getContentFromPath("/data/ast/template/templateInvalidName.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateInvalidName.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateInvalidParamName() {
		String content = this.getContentFromPath("/data/ast/template/templateInvalidParamName.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateInvalidParamName.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateInvalidVisibility() {
		String content = this.getContentFromPath("/data/ast/template/templateInvalidVisibility.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateInvalidVisibility.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateInvalidVarInit() {
		String content = this.getContentFromPath("/data/ast/template/templateInvalidVarInit.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateInvalidVarInit.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateInvalidPost() {
		String content = this.getContentFromPath("/data/ast/template/templateInvalidPost.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateInvalidPost.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateInvalidGuard() {
		String content = this.getContentFromPath("/data/ast/template/templateInvalidGuard.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateInvalidGuard.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateInvalidParamType() {
		String content = this.getContentFromPath("/data/ast/template/templateInvalidParamType.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateInvalidParamType.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateMissingVisibility() {
		String content = this.getContentFromPath("/data/ast/template/templateMissingVisibility.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMissingVisibility.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateMissingName() {
		String content = this.getContentFromPath("/data/ast/template/templateMissingName.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMissingName.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateMissingParameterName() {
		String content = this.getContentFromPath("/data/ast/template/templateMissingParameterName.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMissingParameterName.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateMissingParenthesis() {
		String content = this.getContentFromPath("/data/ast/template/templateMissingParenthesis.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMissingParenthesis.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateMissingParenthesis2() {
		String content = this.getContentFromPath("/data/ast/template/templateMissingParenthesis2.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMissingParenthesis2.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateMissingParenthesis3() {
		String content = this.getContentFromPath("/data/ast/template/templateMissingParenthesis3.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMissingParenthesis3.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateMissingParenthesis4() {
		String content = this.getContentFromPath("/data/ast/template/templateMissingParenthesis4.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMissingParenthesis4.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateMissingGuardParenthesis() {
		String content = this.getContentFromPath("/data/ast/template/templateMissingGuardParenthesis.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMissingGuardParenthesis.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateMissingPostParenthesis() {
		String content = this.getContentFromPath("/data/ast/template/templateMissingPostParenthesis.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMissingPostParenthesis.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateMissingPostParenthesis2() {
		String content = this.getContentFromPath("/data/ast/template/templateMissingPostParenthesis2.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMissingPostParenthesis2.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateMissingColon() {
		String content = this.getContentFromPath("/data/ast/template/templateMissingColon.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMissingColon.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateMissingCurlyBrace() {
		String content = this.getContentFromPath("/data/ast/template/templateMissingCurlyBrace.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMissingCurlyBrace.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateMissingSemiColon() {
		String content = this.getContentFromPath("/data/ast/template/templateMissingSemiColon.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMissingSemiColon.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateMissingQuestionMark() {
		String content = this.getContentFromPath("/data/ast/template/templateMissingQuestionMark.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMissingQuestionMark.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateMissingParameterType() {
		String content = this.getContentFromPath("/data/ast/template/templateMissingParameterType.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMissingParameterType.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateMultipleSameNameAndParam() {
		String content = this.getContentFromPath("/data/ast/template/templateMultipleSameNameAndParam.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMultipleSameNameAndParam.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateMultipleSameName() {
		String content = this.getContentFromPath("/data/ast/template/templateMultipleSameName.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMultipleSameName.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateMultipleParamSameName() {
		String content = this.getContentFromPath("/data/ast/template/templateMultipleParamSameName.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMultipleParamSameName.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateMultipleVarInit() {
		String content = this.getContentFromPath("/data/ast/template/templateMultipleVarInit.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMultipleVarInit.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateMultipleVariable() {
		String content = this.getContentFromPath("/data/ast/template/templateMultipleVariable.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateMultipleVariable.mtl"); //$NON-NLS-1$
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
	public void testParseTemplatePublicMain() {
		String content = this.getContentFromPath("/data/ast/template/templatePublicMain.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templatePublicMain.mtl"); //$NON-NLS-1$
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
	public void testParseTemplateProtectedMain() {
		String content = this.getContentFromPath("/data/ast/template/templateProtectedMain.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templateProtectedMain.mtl"); //$NON-NLS-1$
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
	public void testParseTemplatePrivateMain() {
		String content = this.getContentFromPath("/data/ast/template/templatePrivateMain.mtl"); //$NON-NLS-1$

		IFile moduleFile = createFile(content,
				new Path("/org/eclipse/acceleo/parser/tests/template/files"), project, //$NON-NLS-1$
				"templatePrivateMain.mtl"); //$NON-NLS-1$
		if (moduleFile.exists() && content.length() > 0) {
			checkCSTParsing(moduleFile, 0, 0, 0);
			checkCST2ASTConvertion(0, 1, 0);
			checkASTResolution(0, 1, 0);
			checkASTDocumentationResolution(0, 1, 0);
		} else {
			fail();
		}
	}
}
