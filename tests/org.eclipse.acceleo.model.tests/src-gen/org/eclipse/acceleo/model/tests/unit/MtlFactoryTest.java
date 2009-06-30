package org.eclipse.acceleo.model.tests.unit;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.impl.EClassifierImpl;

import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.TemplateExpression;
import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.acceleo.model.mtl.InitSection;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.TemplateInvocation;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.QueryInvocation;
import org.eclipse.acceleo.model.mtl.ProtectedAreaBlock;
import org.eclipse.acceleo.model.mtl.ForBlock;
import org.eclipse.acceleo.model.mtl.IfBlock;
import org.eclipse.acceleo.model.mtl.LetBlock;
import org.eclipse.acceleo.model.mtl.FileBlock;
import org.eclipse.acceleo.model.mtl.TraceBlock;
import org.eclipse.acceleo.model.mtl.Macro;
import org.eclipse.acceleo.model.mtl.MacroInvocation;
import org.eclipse.acceleo.model.mtl.TypedModel;
import org.eclipse.acceleo.model.mtl.VisibilityKind;
import org.eclipse.acceleo.model.mtl.OpenModeKind;

/**
 * Tests the behavior of the {@link MtlFactory generated factory} for package mtl.
 *
 * @generated
 */
@SuppressWarnings("nls")
public class MtlFactoryTest extends TestCase {
	/**
	 * Ensures that creating {@link Module} can be done through the factory.
	 *
	 * @generated
	 */
	public void testCreateModule() {
		Object result = MtlFactory.eINSTANCE.createModule();
		assertNotNull(result);
		assertTrue(result instanceof Module);

		result = MtlFactory.eINSTANCE.create(MtlPackage.Literals.MODULE);
		assertNotNull(result);
		assertTrue(result instanceof Module);
	}

	/**
	 * Ensures that creating {@link TemplateExpression} can be done through the factory.
	 *
	 * @generated
	 */
	public void testCreateTemplateExpression() {
		Object result = MtlFactory.eINSTANCE.createTemplateExpression();
		assertNotNull(result);
		assertTrue(result instanceof TemplateExpression);

		result = MtlFactory.eINSTANCE.create(MtlPackage.Literals.TEMPLATE_EXPRESSION);
		assertNotNull(result);
		assertTrue(result instanceof TemplateExpression);
	}

	/**
	 * Ensures that creating {@link Block} can be done through the factory.
	 *
	 * @generated
	 */
	public void testCreateBlock() {
		Object result = MtlFactory.eINSTANCE.createBlock();
		assertNotNull(result);
		assertTrue(result instanceof Block);

		result = MtlFactory.eINSTANCE.create(MtlPackage.Literals.BLOCK);
		assertNotNull(result);
		assertTrue(result instanceof Block);
	}

	/**
	 * Ensures that creating {@link InitSection} can be done through the factory.
	 *
	 * @generated
	 */
	public void testCreateInitSection() {
		Object result = MtlFactory.eINSTANCE.createInitSection();
		assertNotNull(result);
		assertTrue(result instanceof InitSection);

		result = MtlFactory.eINSTANCE.create(MtlPackage.Literals.INIT_SECTION);
		assertNotNull(result);
		assertTrue(result instanceof InitSection);
	}

	/**
	 * Ensures that creating {@link Template} can be done through the factory.
	 *
	 * @generated
	 */
	public void testCreateTemplate() {
		Object result = MtlFactory.eINSTANCE.createTemplate();
		assertNotNull(result);
		assertTrue(result instanceof Template);

		result = MtlFactory.eINSTANCE.create(MtlPackage.Literals.TEMPLATE);
		assertNotNull(result);
		assertTrue(result instanceof Template);
	}

	/**
	 * Ensures that creating {@link TemplateInvocation} can be done through the factory.
	 *
	 * @generated
	 */
	public void testCreateTemplateInvocation() {
		Object result = MtlFactory.eINSTANCE.createTemplateInvocation();
		assertNotNull(result);
		assertTrue(result instanceof TemplateInvocation);

		result = MtlFactory.eINSTANCE.create(MtlPackage.Literals.TEMPLATE_INVOCATION);
		assertNotNull(result);
		assertTrue(result instanceof TemplateInvocation);
	}

	/**
	 * Ensures that creating {@link Query} can be done through the factory.
	 *
	 * @generated
	 */
	public void testCreateQuery() {
		Object result = MtlFactory.eINSTANCE.createQuery();
		assertNotNull(result);
		assertTrue(result instanceof Query);

		result = MtlFactory.eINSTANCE.create(MtlPackage.Literals.QUERY);
		assertNotNull(result);
		assertTrue(result instanceof Query);
	}

	/**
	 * Ensures that creating {@link QueryInvocation} can be done through the factory.
	 *
	 * @generated
	 */
	public void testCreateQueryInvocation() {
		Object result = MtlFactory.eINSTANCE.createQueryInvocation();
		assertNotNull(result);
		assertTrue(result instanceof QueryInvocation);

		result = MtlFactory.eINSTANCE.create(MtlPackage.Literals.QUERY_INVOCATION);
		assertNotNull(result);
		assertTrue(result instanceof QueryInvocation);
	}

	/**
	 * Ensures that creating {@link ProtectedAreaBlock} can be done through the factory.
	 *
	 * @generated
	 */
	public void testCreateProtectedAreaBlock() {
		Object result = MtlFactory.eINSTANCE.createProtectedAreaBlock();
		assertNotNull(result);
		assertTrue(result instanceof ProtectedAreaBlock);

		result = MtlFactory.eINSTANCE.create(MtlPackage.Literals.PROTECTED_AREA_BLOCK);
		assertNotNull(result);
		assertTrue(result instanceof ProtectedAreaBlock);
	}

	/**
	 * Ensures that creating {@link ForBlock} can be done through the factory.
	 *
	 * @generated
	 */
	public void testCreateForBlock() {
		Object result = MtlFactory.eINSTANCE.createForBlock();
		assertNotNull(result);
		assertTrue(result instanceof ForBlock);

		result = MtlFactory.eINSTANCE.create(MtlPackage.Literals.FOR_BLOCK);
		assertNotNull(result);
		assertTrue(result instanceof ForBlock);
	}

	/**
	 * Ensures that creating {@link IfBlock} can be done through the factory.
	 *
	 * @generated
	 */
	public void testCreateIfBlock() {
		Object result = MtlFactory.eINSTANCE.createIfBlock();
		assertNotNull(result);
		assertTrue(result instanceof IfBlock);

		result = MtlFactory.eINSTANCE.create(MtlPackage.Literals.IF_BLOCK);
		assertNotNull(result);
		assertTrue(result instanceof IfBlock);
	}

	/**
	 * Ensures that creating {@link LetBlock} can be done through the factory.
	 *
	 * @generated
	 */
	public void testCreateLetBlock() {
		Object result = MtlFactory.eINSTANCE.createLetBlock();
		assertNotNull(result);
		assertTrue(result instanceof LetBlock);

		result = MtlFactory.eINSTANCE.create(MtlPackage.Literals.LET_BLOCK);
		assertNotNull(result);
		assertTrue(result instanceof LetBlock);
	}

	/**
	 * Ensures that creating {@link FileBlock} can be done through the factory.
	 *
	 * @generated
	 */
	public void testCreateFileBlock() {
		Object result = MtlFactory.eINSTANCE.createFileBlock();
		assertNotNull(result);
		assertTrue(result instanceof FileBlock);

		result = MtlFactory.eINSTANCE.create(MtlPackage.Literals.FILE_BLOCK);
		assertNotNull(result);
		assertTrue(result instanceof FileBlock);
	}

	/**
	 * Ensures that creating {@link TraceBlock} can be done through the factory.
	 *
	 * @generated
	 */
	public void testCreateTraceBlock() {
		Object result = MtlFactory.eINSTANCE.createTraceBlock();
		assertNotNull(result);
		assertTrue(result instanceof TraceBlock);

		result = MtlFactory.eINSTANCE.create(MtlPackage.Literals.TRACE_BLOCK);
		assertNotNull(result);
		assertTrue(result instanceof TraceBlock);
	}

	/**
	 * Ensures that creating {@link Macro} can be done through the factory.
	 *
	 * @generated
	 */
	public void testCreateMacro() {
		Object result = MtlFactory.eINSTANCE.createMacro();
		assertNotNull(result);
		assertTrue(result instanceof Macro);

		result = MtlFactory.eINSTANCE.create(MtlPackage.Literals.MACRO);
		assertNotNull(result);
		assertTrue(result instanceof Macro);
	}

	/**
	 * Ensures that creating {@link MacroInvocation} can be done through the factory.
	 *
	 * @generated
	 */
	public void testCreateMacroInvocation() {
		Object result = MtlFactory.eINSTANCE.createMacroInvocation();
		assertNotNull(result);
		assertTrue(result instanceof MacroInvocation);

		result = MtlFactory.eINSTANCE.create(MtlPackage.Literals.MACRO_INVOCATION);
		assertNotNull(result);
		assertTrue(result instanceof MacroInvocation);
	}

	/**
	 * Ensures that creating {@link TypedModel} can be done through the factory.
	 *
	 * @generated
	 */
	public void testCreateTypedModel() {
		Object result = MtlFactory.eINSTANCE.createTypedModel();
		assertNotNull(result);
		assertTrue(result instanceof TypedModel);

		result = MtlFactory.eINSTANCE.create(MtlPackage.Literals.TYPED_MODEL);
		assertNotNull(result);
		assertTrue(result instanceof TypedModel);
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
			MtlFactory.eINSTANCE.create(eClass);
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
			Object result = MtlFactory.eINSTANCE.convertToString(MtlPackage.Literals.VISIBILITY_KIND, value);
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
			Object result = MtlFactory.eINSTANCE.convertToString(MtlPackage.Literals.OPEN_MODE_KIND, value);
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
			MtlFactory.eINSTANCE.convertToString(eEnum, eEnum);
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
			Object result = MtlFactory.eINSTANCE.createFromString(MtlPackage.Literals.VISIBILITY_KIND, value.getLiteral());
			assertNotNull(result);
			assertSame(value, result);

			try {
				MtlFactory.eINSTANCE.createFromString(MtlPackage.Literals.VISIBILITY_KIND, "ThisShouldntBeAKnownEEnumLiteral");
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
			Object result = MtlFactory.eINSTANCE.createFromString(MtlPackage.Literals.OPEN_MODE_KIND, value.getLiteral());
			assertNotNull(result);
			assertSame(value, result);

			try {
				MtlFactory.eINSTANCE.createFromString(MtlPackage.Literals.OPEN_MODE_KIND, "ThisShouldntBeAKnownEEnumLiteral");
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
			MtlFactory.eINSTANCE.createFromString(eEnum, "ThisShouldntBeAKnownEEnumLiteral");
			fail("Expected IllegalArgumentException hasn't been thrown");
		} catch (IllegalArgumentException e) {
			// Expected behavior
		}
	}
}

