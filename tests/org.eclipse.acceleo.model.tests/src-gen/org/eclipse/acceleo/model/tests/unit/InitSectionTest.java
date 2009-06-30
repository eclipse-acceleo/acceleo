package org.eclipse.acceleo.model.tests.unit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.InitSection;

/**
 * Tests the behavior of the {@link InitSection} class.
 * 
 * @generated
 */
public class InitSectionTest extends AbstractMtlTest {
	/**
	 * Tests the behavior of reference <code>variable</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testVariable() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getInitSection_Variable();
		InitSection initSection = MtlFactory.eINSTANCE.createInitSection();
		initSection.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.Variable variableValue = org.eclipse.ocl.ecore.EcoreFactory.eINSTANCE.createVariable();
		List<org.eclipse.ocl.ecore.Variable> listVariable = new ArrayList<org.eclipse.ocl.ecore.Variable>(1);
		listVariable.add(variableValue);

		assertFalse(initSection.eIsSet(feature));
		assertTrue(initSection.getVariable().isEmpty());

		initSection.getVariable().add(variableValue);
		assertTrue(notified);
		notified = false;
		assertTrue(initSection.getVariable().contains(variableValue));
		assertSame(initSection.getVariable(), initSection.eGet(feature));
		assertSame(initSection.getVariable(), initSection.eGet(feature, false));
		assertTrue(initSection.eIsSet(feature));

		initSection.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(initSection.getVariable().isEmpty());
		assertSame(initSection.getVariable(), initSection.eGet(feature));
		assertSame(initSection.getVariable(), initSection.eGet(feature, false));
		assertFalse(initSection.eIsSet(feature));

		initSection.eSet(feature, listVariable);
		assertTrue(notified);
		notified = false;
		assertTrue(initSection.getVariable().contains(variableValue));
		assertSame(initSection.getVariable(), initSection.eGet(feature));
		assertSame(initSection.getVariable(), initSection.eGet(feature, false));
		assertTrue(initSection.eIsSet(feature));
	}


	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_StartPosition();
		InitSection initSection = MtlFactory.eINSTANCE.createInitSection();
		initSection.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(initSection.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)initSection.getStartPosition()).intValue());

		initSection.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)initSection.getStartPosition()).intValue());
		assertEquals(((Integer)initSection.getStartPosition()).intValue(), ((Integer)initSection.eGet(feature)).intValue());
		assertTrue(initSection.eIsSet(feature));

		initSection.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)initSection.getStartPosition()).intValue());
		assertEquals(((Integer)initSection.getStartPosition()).intValue(), ((Integer)initSection.eGet(feature)).intValue());
		assertFalse(initSection.eIsSet(feature));

		initSection.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)initSection.getStartPosition()).intValue());
		assertEquals(((Integer)initSection.getStartPosition()).intValue(), ((Integer)initSection.eGet(feature)).intValue());
		assertTrue(initSection.eIsSet(feature));

		initSection.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)initSection.getStartPosition()).intValue());
		assertEquals(((Integer)initSection.getStartPosition()).intValue(), ((Integer)initSection.eGet(feature)).intValue());
		assertFalse(initSection.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_EndPosition();
		InitSection initSection = MtlFactory.eINSTANCE.createInitSection();
		initSection.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(initSection.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)initSection.getEndPosition()).intValue());

		initSection.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)initSection.getEndPosition()).intValue());
		assertEquals(((Integer)initSection.getEndPosition()).intValue(), ((Integer)initSection.eGet(feature)).intValue());
		assertTrue(initSection.eIsSet(feature));

		initSection.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)initSection.getEndPosition()).intValue());
		assertEquals(((Integer)initSection.getEndPosition()).intValue(), ((Integer)initSection.eGet(feature)).intValue());
		assertFalse(initSection.eIsSet(feature));

		initSection.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)initSection.getEndPosition()).intValue());
		assertEquals(((Integer)initSection.getEndPosition()).intValue(), ((Integer)initSection.eGet(feature)).intValue());
		assertTrue(initSection.eIsSet(feature));

		initSection.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)initSection.getEndPosition()).intValue());
		assertEquals(((Integer)initSection.getEndPosition()).intValue(), ((Integer)initSection.eGet(feature)).intValue());
		assertFalse(initSection.eIsSet(feature));
	}
	
}

