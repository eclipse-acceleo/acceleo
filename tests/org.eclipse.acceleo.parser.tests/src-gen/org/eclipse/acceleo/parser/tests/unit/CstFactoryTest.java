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
package org.eclipse.acceleo.parser.tests.unit;

import junit.framework.TestCase;

import org.eclipse.acceleo.parser.cst.Block;
import org.eclipse.acceleo.parser.cst.Comment;
import org.eclipse.acceleo.parser.cst.CstFactory;
import org.eclipse.acceleo.parser.cst.CstPackage;
import org.eclipse.acceleo.parser.cst.FileBlock;
import org.eclipse.acceleo.parser.cst.ForBlock;
import org.eclipse.acceleo.parser.cst.IfBlock;
import org.eclipse.acceleo.parser.cst.InitSection;
import org.eclipse.acceleo.parser.cst.LetBlock;
import org.eclipse.acceleo.parser.cst.Macro;
import org.eclipse.acceleo.parser.cst.ModelExpression;
import org.eclipse.acceleo.parser.cst.Module;
import org.eclipse.acceleo.parser.cst.ModuleExtendsValue;
import org.eclipse.acceleo.parser.cst.ModuleImportsValue;
import org.eclipse.acceleo.parser.cst.OpenModeKind;
import org.eclipse.acceleo.parser.cst.ProtectedAreaBlock;
import org.eclipse.acceleo.parser.cst.Query;
import org.eclipse.acceleo.parser.cst.Template;
import org.eclipse.acceleo.parser.cst.TemplateExpression;
import org.eclipse.acceleo.parser.cst.TemplateOverridesValue;
import org.eclipse.acceleo.parser.cst.TextExpression;
import org.eclipse.acceleo.parser.cst.TraceBlock;
import org.eclipse.acceleo.parser.cst.TypedModel;
import org.eclipse.acceleo.parser.cst.Variable;
import org.eclipse.acceleo.parser.cst.VisibilityKind;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.impl.EClassifierImpl;

/**
 * Tests the behavior of the {@link CstFactory generated factory} for package cst.
 * 
 * @generated
 */
@SuppressWarnings("nls")
public class CstFactoryTest extends TestCase {
	/**
	 * Ensures that creating {@link Module} can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateModule() {
		Object result = CstFactory.eINSTANCE.createModule();
		assertNotNull(result);
		assertTrue(result instanceof Module);

		result = CstFactory.eINSTANCE.create(CstPackage.Literals.MODULE);
		assertNotNull(result);
		assertTrue(result instanceof Module);
	}

	/**
	 * Ensures that creating {@link ModuleExtendsValue} can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateModuleExtendsValue() {
		Object result = CstFactory.eINSTANCE.createModuleExtendsValue();
		assertNotNull(result);
		assertTrue(result instanceof ModuleExtendsValue);

		result = CstFactory.eINSTANCE.create(CstPackage.Literals.MODULE_EXTENDS_VALUE);
		assertNotNull(result);
		assertTrue(result instanceof ModuleExtendsValue);
	}

	/**
	 * Ensures that creating {@link ModuleImportsValue} can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateModuleImportsValue() {
		Object result = CstFactory.eINSTANCE.createModuleImportsValue();
		assertNotNull(result);
		assertTrue(result instanceof ModuleImportsValue);

		result = CstFactory.eINSTANCE.create(CstPackage.Literals.MODULE_IMPORTS_VALUE);
		assertNotNull(result);
		assertTrue(result instanceof ModuleImportsValue);
	}

	/**
	 * Ensures that creating {@link TypedModel} can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateTypedModel() {
		Object result = CstFactory.eINSTANCE.createTypedModel();
		assertNotNull(result);
		assertTrue(result instanceof TypedModel);

		result = CstFactory.eINSTANCE.create(CstPackage.Literals.TYPED_MODEL);
		assertNotNull(result);
		assertTrue(result instanceof TypedModel);
	}

	/**
	 * Ensures that creating {@link Comment} can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateComment() {
		Object result = CstFactory.eINSTANCE.createComment();
		assertNotNull(result);
		assertTrue(result instanceof Comment);

		result = CstFactory.eINSTANCE.create(CstPackage.Literals.COMMENT);
		assertNotNull(result);
		assertTrue(result instanceof Comment);
	}

	/**
	 * Ensures that creating {@link Template} can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateTemplate() {
		Object result = CstFactory.eINSTANCE.createTemplate();
		assertNotNull(result);
		assertTrue(result instanceof Template);

		result = CstFactory.eINSTANCE.create(CstPackage.Literals.TEMPLATE);
		assertNotNull(result);
		assertTrue(result instanceof Template);
	}

	/**
	 * Ensures that creating {@link TemplateOverridesValue} can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateTemplateOverridesValue() {
		Object result = CstFactory.eINSTANCE.createTemplateOverridesValue();
		assertNotNull(result);
		assertTrue(result instanceof TemplateOverridesValue);

		result = CstFactory.eINSTANCE.create(CstPackage.Literals.TEMPLATE_OVERRIDES_VALUE);
		assertNotNull(result);
		assertTrue(result instanceof TemplateOverridesValue);
	}

	/**
	 * Ensures that creating {@link Variable} can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateVariable() {
		Object result = CstFactory.eINSTANCE.createVariable();
		assertNotNull(result);
		assertTrue(result instanceof Variable);

		result = CstFactory.eINSTANCE.create(CstPackage.Literals.VARIABLE);
		assertNotNull(result);
		assertTrue(result instanceof Variable);
	}

	/**
	 * Ensures that creating {@link TemplateExpression} can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateTemplateExpression() {
		Object result = CstFactory.eINSTANCE.createTemplateExpression();
		assertNotNull(result);
		assertTrue(result instanceof TemplateExpression);

		result = CstFactory.eINSTANCE.create(CstPackage.Literals.TEMPLATE_EXPRESSION);
		assertNotNull(result);
		assertTrue(result instanceof TemplateExpression);
	}

	/**
	 * Ensures that creating {@link ModelExpression} can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateModelExpression() {
		Object result = CstFactory.eINSTANCE.createModelExpression();
		assertNotNull(result);
		assertTrue(result instanceof ModelExpression);

		result = CstFactory.eINSTANCE.create(CstPackage.Literals.MODEL_EXPRESSION);
		assertNotNull(result);
		assertTrue(result instanceof ModelExpression);
	}

	/**
	 * Ensures that creating {@link TextExpression} can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateTextExpression() {
		Object result = CstFactory.eINSTANCE.createTextExpression();
		assertNotNull(result);
		assertTrue(result instanceof TextExpression);

		result = CstFactory.eINSTANCE.create(CstPackage.Literals.TEXT_EXPRESSION);
		assertNotNull(result);
		assertTrue(result instanceof TextExpression);
	}

	/**
	 * Ensures that creating {@link Block} can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateBlock() {
		Object result = CstFactory.eINSTANCE.createBlock();
		assertNotNull(result);
		assertTrue(result instanceof Block);

		result = CstFactory.eINSTANCE.create(CstPackage.Literals.BLOCK);
		assertNotNull(result);
		assertTrue(result instanceof Block);
	}

	/**
	 * Ensures that creating {@link InitSection} can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateInitSection() {
		Object result = CstFactory.eINSTANCE.createInitSection();
		assertNotNull(result);
		assertTrue(result instanceof InitSection);

		result = CstFactory.eINSTANCE.create(CstPackage.Literals.INIT_SECTION);
		assertNotNull(result);
		assertTrue(result instanceof InitSection);
	}

	/**
	 * Ensures that creating {@link ProtectedAreaBlock} can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateProtectedAreaBlock() {
		Object result = CstFactory.eINSTANCE.createProtectedAreaBlock();
		assertNotNull(result);
		assertTrue(result instanceof ProtectedAreaBlock);

		result = CstFactory.eINSTANCE.create(CstPackage.Literals.PROTECTED_AREA_BLOCK);
		assertNotNull(result);
		assertTrue(result instanceof ProtectedAreaBlock);
	}

	/**
	 * Ensures that creating {@link ForBlock} can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateForBlock() {
		Object result = CstFactory.eINSTANCE.createForBlock();
		assertNotNull(result);
		assertTrue(result instanceof ForBlock);

		result = CstFactory.eINSTANCE.create(CstPackage.Literals.FOR_BLOCK);
		assertNotNull(result);
		assertTrue(result instanceof ForBlock);
	}

	/**
	 * Ensures that creating {@link IfBlock} can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateIfBlock() {
		Object result = CstFactory.eINSTANCE.createIfBlock();
		assertNotNull(result);
		assertTrue(result instanceof IfBlock);

		result = CstFactory.eINSTANCE.create(CstPackage.Literals.IF_BLOCK);
		assertNotNull(result);
		assertTrue(result instanceof IfBlock);
	}

	/**
	 * Ensures that creating {@link LetBlock} can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateLetBlock() {
		Object result = CstFactory.eINSTANCE.createLetBlock();
		assertNotNull(result);
		assertTrue(result instanceof LetBlock);

		result = CstFactory.eINSTANCE.create(CstPackage.Literals.LET_BLOCK);
		assertNotNull(result);
		assertTrue(result instanceof LetBlock);
	}

	/**
	 * Ensures that creating {@link FileBlock} can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateFileBlock() {
		Object result = CstFactory.eINSTANCE.createFileBlock();
		assertNotNull(result);
		assertTrue(result instanceof FileBlock);

		result = CstFactory.eINSTANCE.create(CstPackage.Literals.FILE_BLOCK);
		assertNotNull(result);
		assertTrue(result instanceof FileBlock);
	}

	/**
	 * Ensures that creating {@link TraceBlock} can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateTraceBlock() {
		Object result = CstFactory.eINSTANCE.createTraceBlock();
		assertNotNull(result);
		assertTrue(result instanceof TraceBlock);

		result = CstFactory.eINSTANCE.create(CstPackage.Literals.TRACE_BLOCK);
		assertNotNull(result);
		assertTrue(result instanceof TraceBlock);
	}

	/**
	 * Ensures that creating {@link Macro} can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateMacro() {
		Object result = CstFactory.eINSTANCE.createMacro();
		assertNotNull(result);
		assertTrue(result instanceof Macro);

		result = CstFactory.eINSTANCE.create(CstPackage.Literals.MACRO);
		assertNotNull(result);
		assertTrue(result instanceof Macro);
	}

	/**
	 * Ensures that creating {@link Query} can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateQuery() {
		Object result = CstFactory.eINSTANCE.createQuery();
		assertNotNull(result);
		assertTrue(result instanceof Query);

		result = CstFactory.eINSTANCE.create(CstPackage.Literals.QUERY);
		assertNotNull(result);
		assertTrue(result instanceof Query);
	}

	/**
	 * Ensures that trying to create an {@link EClass} from another package yields the expected exception.
	 * 
	 * @generated
	 */
	public void testCreateUnknownEClass() {
		try {
			EClass eClass = EcoreFactory.eINSTANCE.createEClass();
			((EClassifierImpl)eClass).setClassifierID(-1);
			CstFactory.eINSTANCE.create(eClass);
			fail("Expected IllegalArgumentException hasn't been thrown");
		} catch (IllegalArgumentException e) {
			// Expected behavior
		}
	}

	/**
	 * Ensures that converting {@link VisibilityKind} to String can be done through the factory.
	 * 
	 * @generated
	 */
	public void testConvertVisibilityKindToString() {
		for (VisibilityKind value : VisibilityKind.VALUES) {
			Object result = CstFactory.eINSTANCE.convertToString(CstPackage.Literals.VISIBILITY_KIND, value);
			assertNotNull(result);
			assertEquals(value.toString(), result);
		}
	}

	/**
	 * Ensures that converting {@link OpenModeKind} to String can be done through the factory.
	 * 
	 * @generated
	 */
	public void testConvertOpenModeKindToString() {
		for (OpenModeKind value : OpenModeKind.VALUES) {
			Object result = CstFactory.eINSTANCE.convertToString(CstPackage.Literals.OPEN_MODE_KIND, value);
			assertNotNull(result);
			assertEquals(value.toString(), result);
		}
	}

	/**
	 * Ensures that trying to convert an {@link EEnum} from another package to String yields the expected
	 * exception.
	 * 
	 * @generated
	 */
	public void testConvertUnknownEEnumToString() {
		try {
			EEnum eEnum = EcoreFactory.eINSTANCE.createEEnum();
			((EClassifierImpl)eEnum).setClassifierID(-1);
			CstFactory.eINSTANCE.convertToString(eEnum, eEnum);
			fail("Expected IllegalArgumentException hasn't been thrown");
		} catch (IllegalArgumentException e) {
			// Expected behavior
		}
	}

	/**
	 * Ensures that creating {@link VisibilityKind} from String can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateVisibilityKindFromString() {
		for (VisibilityKind value : VisibilityKind.VALUES) {
			Object result = CstFactory.eINSTANCE.createFromString(CstPackage.Literals.VISIBILITY_KIND, value
					.getLiteral());
			assertNotNull(result);
			assertSame(value, result);

			try {
				CstFactory.eINSTANCE.createFromString(CstPackage.Literals.VISIBILITY_KIND,
						"ThisShouldntBeAKnownEEnumLiteral");
				fail("Expected IllegalArgumentException hasn't been thrown");
			} catch (IllegalArgumentException e) {
				// Expected behavior
			}
		}
	}

	/**
	 * Ensures that creating {@link OpenModeKind} from String can be done through the factory.
	 * 
	 * @generated
	 */
	public void testCreateOpenModeKindFromString() {
		for (OpenModeKind value : OpenModeKind.VALUES) {
			Object result = CstFactory.eINSTANCE.createFromString(CstPackage.Literals.OPEN_MODE_KIND, value
					.getLiteral());
			assertNotNull(result);
			assertSame(value, result);

			try {
				CstFactory.eINSTANCE.createFromString(CstPackage.Literals.OPEN_MODE_KIND,
						"ThisShouldntBeAKnownEEnumLiteral");
				fail("Expected IllegalArgumentException hasn't been thrown");
			} catch (IllegalArgumentException e) {
				// Expected behavior
			}
		}
	}

	/**
	 * Ensures that trying to create an {@link EEnum} from another package from String yields the expected
	 * exception.
	 * 
	 * @generated
	 */
	public void testCreateUnknownEEnumFromString() {
		try {
			EEnum eEnum = EcoreFactory.eINSTANCE.createEEnum();
			((EClassifierImpl)eEnum).setClassifierID(-1);
			CstFactory.eINSTANCE.createFromString(eEnum, "ThisShouldntBeAKnownEEnumLiteral");
			fail("Expected IllegalArgumentException hasn't been thrown");
		} catch (IllegalArgumentException e) {
			// Expected behavior
		}
	}
}
