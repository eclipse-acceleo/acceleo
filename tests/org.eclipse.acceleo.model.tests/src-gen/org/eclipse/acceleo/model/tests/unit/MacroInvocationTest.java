package org.eclipse.acceleo.model.tests.unit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.MacroInvocation;

/**
 * Tests the behavior of the {@link MacroInvocation} class.
 * 
 * @generated
 */
public class MacroInvocationTest extends AbstractMtlTest {
	/**
	 * Tests the behavior of reference <code>argument</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testArgument() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getMacroInvocation_Argument();
		MacroInvocation macroInvocation = MtlFactory.eINSTANCE.createMacroInvocation();
		macroInvocation.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.OCLExpression argumentValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplateExpression();
		List<org.eclipse.ocl.ecore.OCLExpression> listArgument = new ArrayList<org.eclipse.ocl.ecore.OCLExpression>(1);
		listArgument.add(argumentValue);

		assertFalse(macroInvocation.eIsSet(feature));
		assertTrue(macroInvocation.getArgument().isEmpty());

		macroInvocation.getArgument().add(argumentValue);
		assertTrue(notified);
		notified = false;
		assertTrue(macroInvocation.getArgument().contains(argumentValue));
		assertSame(macroInvocation.getArgument(), macroInvocation.eGet(feature));
		assertSame(macroInvocation.getArgument(), macroInvocation.eGet(feature, false));
		assertTrue(macroInvocation.eIsSet(feature));

		macroInvocation.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(macroInvocation.getArgument().isEmpty());
		assertSame(macroInvocation.getArgument(), macroInvocation.eGet(feature));
		assertSame(macroInvocation.getArgument(), macroInvocation.eGet(feature, false));
		assertFalse(macroInvocation.eIsSet(feature));

		macroInvocation.eSet(feature, listArgument);
		assertTrue(notified);
		notified = false;
		assertTrue(macroInvocation.getArgument().contains(argumentValue));
		assertSame(macroInvocation.getArgument(), macroInvocation.eGet(feature));
		assertSame(macroInvocation.getArgument(), macroInvocation.eGet(feature, false));
		assertTrue(macroInvocation.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>definition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testDefinition() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getMacroInvocation_Definition();
		MacroInvocation macroInvocation = MtlFactory.eINSTANCE.createMacroInvocation();
		macroInvocation.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.model.mtl.Macro definitionValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createMacro();

		assertFalse(macroInvocation.eIsSet(feature));
		assertNull(macroInvocation.getDefinition());

		macroInvocation.setDefinition(definitionValue);
		assertTrue(notified);
		notified = false;
		assertSame(definitionValue, macroInvocation.getDefinition());
		assertSame(macroInvocation.getDefinition(), macroInvocation.eGet(feature));
		assertSame(macroInvocation.getDefinition(), macroInvocation.eGet(feature, false));
		assertTrue(macroInvocation.eIsSet(feature));

		macroInvocation.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(macroInvocation.getDefinition());
		assertSame(macroInvocation.getDefinition(), macroInvocation.eGet(feature));
		assertSame(macroInvocation.getDefinition(), macroInvocation.eGet(feature, false));
		assertFalse(macroInvocation.eIsSet(feature));

		macroInvocation.setDefinition(definitionValue);
		assertTrue(notified);
		notified = false;
		assertSame(definitionValue, macroInvocation.getDefinition());
		assertSame(macroInvocation.getDefinition(), macroInvocation.eGet(feature));
		assertSame(macroInvocation.getDefinition(), macroInvocation.eGet(feature, false));
		assertTrue(macroInvocation.eIsSet(feature));

		macroInvocation.eSet(feature, definitionValue);
		assertTrue(notified);
		notified = false;
		assertSame(definitionValue, macroInvocation.getDefinition());
		assertSame(macroInvocation.getDefinition(), macroInvocation.eGet(feature));
		assertSame(macroInvocation.getDefinition(), macroInvocation.eGet(feature, false));
		assertTrue(macroInvocation.eIsSet(feature));

		macroInvocation.setDefinition(null);
		assertTrue(notified);
		notified = false;
		assertNull(macroInvocation.getDefinition());
		assertSame(feature.getDefaultValue(), macroInvocation.getDefinition());
		assertSame(macroInvocation.getDefinition(), macroInvocation.eGet(feature));
		assertSame(macroInvocation.getDefinition(), macroInvocation.eGet(feature, false));
		assertFalse(macroInvocation.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_StartPosition();
		MacroInvocation macroInvocation = MtlFactory.eINSTANCE.createMacroInvocation();
		macroInvocation.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(macroInvocation.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)macroInvocation.getStartPosition()).intValue());

		macroInvocation.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)macroInvocation.getStartPosition()).intValue());
		assertEquals(((Integer)macroInvocation.getStartPosition()).intValue(), ((Integer)macroInvocation.eGet(feature)).intValue());
		assertTrue(macroInvocation.eIsSet(feature));

		macroInvocation.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)macroInvocation.getStartPosition()).intValue());
		assertEquals(((Integer)macroInvocation.getStartPosition()).intValue(), ((Integer)macroInvocation.eGet(feature)).intValue());
		assertFalse(macroInvocation.eIsSet(feature));

		macroInvocation.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)macroInvocation.getStartPosition()).intValue());
		assertEquals(((Integer)macroInvocation.getStartPosition()).intValue(), ((Integer)macroInvocation.eGet(feature)).intValue());
		assertTrue(macroInvocation.eIsSet(feature));

		macroInvocation.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)macroInvocation.getStartPosition()).intValue());
		assertEquals(((Integer)macroInvocation.getStartPosition()).intValue(), ((Integer)macroInvocation.eGet(feature)).intValue());
		assertFalse(macroInvocation.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_EndPosition();
		MacroInvocation macroInvocation = MtlFactory.eINSTANCE.createMacroInvocation();
		macroInvocation.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(macroInvocation.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)macroInvocation.getEndPosition()).intValue());

		macroInvocation.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)macroInvocation.getEndPosition()).intValue());
		assertEquals(((Integer)macroInvocation.getEndPosition()).intValue(), ((Integer)macroInvocation.eGet(feature)).intValue());
		assertTrue(macroInvocation.eIsSet(feature));

		macroInvocation.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)macroInvocation.getEndPosition()).intValue());
		assertEquals(((Integer)macroInvocation.getEndPosition()).intValue(), ((Integer)macroInvocation.eGet(feature)).intValue());
		assertFalse(macroInvocation.eIsSet(feature));

		macroInvocation.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)macroInvocation.getEndPosition()).intValue());
		assertEquals(((Integer)macroInvocation.getEndPosition()).intValue(), ((Integer)macroInvocation.eGet(feature)).intValue());
		assertTrue(macroInvocation.eIsSet(feature));

		macroInvocation.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)macroInvocation.getEndPosition()).intValue());
		assertEquals(((Integer)macroInvocation.getEndPosition()).intValue(), ((Integer)macroInvocation.eGet(feature)).intValue());
		assertFalse(macroInvocation.eIsSet(feature));
	}
	
}

