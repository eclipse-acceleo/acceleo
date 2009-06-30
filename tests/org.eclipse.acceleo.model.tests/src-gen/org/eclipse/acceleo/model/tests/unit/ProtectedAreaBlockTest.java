package org.eclipse.acceleo.model.tests.unit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.ProtectedAreaBlock;

/**
 * Tests the behavior of the {@link ProtectedAreaBlock} class.
 * 
 * @generated
 */
public class ProtectedAreaBlockTest extends AbstractMtlTest {
	/**
	 * Tests the behavior of reference <code>body</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testBody() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getBlock_Body();
		ProtectedAreaBlock protectedAreaBlock = MtlFactory.eINSTANCE.createProtectedAreaBlock();
		protectedAreaBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.OCLExpression bodyValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplateExpression();
		List<org.eclipse.ocl.ecore.OCLExpression> listBody = new ArrayList<org.eclipse.ocl.ecore.OCLExpression>(1);
		listBody.add(bodyValue);

		assertFalse(protectedAreaBlock.eIsSet(feature));
		assertTrue(protectedAreaBlock.getBody().isEmpty());

		protectedAreaBlock.getBody().add(bodyValue);
		assertTrue(notified);
		notified = false;
		assertTrue(protectedAreaBlock.getBody().contains(bodyValue));
		assertSame(protectedAreaBlock.getBody(), protectedAreaBlock.eGet(feature));
		assertSame(protectedAreaBlock.getBody(), protectedAreaBlock.eGet(feature, false));
		assertTrue(protectedAreaBlock.eIsSet(feature));

		protectedAreaBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(protectedAreaBlock.getBody().isEmpty());
		assertSame(protectedAreaBlock.getBody(), protectedAreaBlock.eGet(feature));
		assertSame(protectedAreaBlock.getBody(), protectedAreaBlock.eGet(feature, false));
		assertFalse(protectedAreaBlock.eIsSet(feature));

		protectedAreaBlock.eSet(feature, listBody);
		assertTrue(notified);
		notified = false;
		assertTrue(protectedAreaBlock.getBody().contains(bodyValue));
		assertSame(protectedAreaBlock.getBody(), protectedAreaBlock.eGet(feature));
		assertSame(protectedAreaBlock.getBody(), protectedAreaBlock.eGet(feature, false));
		assertTrue(protectedAreaBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>init</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testInit() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getBlock_Init();
		ProtectedAreaBlock protectedAreaBlock = MtlFactory.eINSTANCE.createProtectedAreaBlock();
		protectedAreaBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.model.mtl.InitSection initValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createInitSection();

		assertFalse(protectedAreaBlock.eIsSet(feature));
		assertNull(protectedAreaBlock.getInit());

		protectedAreaBlock.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, protectedAreaBlock.getInit());
		assertSame(protectedAreaBlock.getInit(), protectedAreaBlock.eGet(feature));
		assertSame(protectedAreaBlock.getInit(), protectedAreaBlock.eGet(feature, false));
		assertTrue(protectedAreaBlock.eIsSet(feature));

		protectedAreaBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(protectedAreaBlock.getInit());
		assertSame(protectedAreaBlock.getInit(), protectedAreaBlock.eGet(feature));
		assertSame(protectedAreaBlock.getInit(), protectedAreaBlock.eGet(feature, false));
		assertFalse(protectedAreaBlock.eIsSet(feature));

		protectedAreaBlock.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, protectedAreaBlock.getInit());
		assertSame(protectedAreaBlock.getInit(), protectedAreaBlock.eGet(feature));
		assertSame(protectedAreaBlock.getInit(), protectedAreaBlock.eGet(feature, false));
		assertTrue(protectedAreaBlock.eIsSet(feature));

		protectedAreaBlock.eSet(feature, initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, protectedAreaBlock.getInit());
		assertSame(protectedAreaBlock.getInit(), protectedAreaBlock.eGet(feature));
		assertSame(protectedAreaBlock.getInit(), protectedAreaBlock.eGet(feature, false));
		assertTrue(protectedAreaBlock.eIsSet(feature));

		protectedAreaBlock.setInit(null);
		assertTrue(notified);
		notified = false;
		assertNull(protectedAreaBlock.getInit());
		assertSame(feature.getDefaultValue(), protectedAreaBlock.getInit());
		assertSame(protectedAreaBlock.getInit(), protectedAreaBlock.eGet(feature));
		assertSame(protectedAreaBlock.getInit(), protectedAreaBlock.eGet(feature, false));
		assertFalse(protectedAreaBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>marker</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testMarker() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getProtectedAreaBlock_Marker();
		ProtectedAreaBlock protectedAreaBlock = MtlFactory.eINSTANCE.createProtectedAreaBlock();
		protectedAreaBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.OCLExpression markerValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplateExpression();

		assertFalse(protectedAreaBlock.eIsSet(feature));
		assertNull(protectedAreaBlock.getMarker());

		protectedAreaBlock.setMarker(markerValue);
		assertTrue(notified);
		notified = false;
		assertSame(markerValue, protectedAreaBlock.getMarker());
		assertSame(protectedAreaBlock.getMarker(), protectedAreaBlock.eGet(feature));
		assertSame(protectedAreaBlock.getMarker(), protectedAreaBlock.eGet(feature, false));
		assertTrue(protectedAreaBlock.eIsSet(feature));

		protectedAreaBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(protectedAreaBlock.getMarker());
		assertSame(protectedAreaBlock.getMarker(), protectedAreaBlock.eGet(feature));
		assertSame(protectedAreaBlock.getMarker(), protectedAreaBlock.eGet(feature, false));
		assertFalse(protectedAreaBlock.eIsSet(feature));

		protectedAreaBlock.setMarker(markerValue);
		assertTrue(notified);
		notified = false;
		assertSame(markerValue, protectedAreaBlock.getMarker());
		assertSame(protectedAreaBlock.getMarker(), protectedAreaBlock.eGet(feature));
		assertSame(protectedAreaBlock.getMarker(), protectedAreaBlock.eGet(feature, false));
		assertTrue(protectedAreaBlock.eIsSet(feature));

		protectedAreaBlock.eSet(feature, markerValue);
		assertTrue(notified);
		notified = false;
		assertSame(markerValue, protectedAreaBlock.getMarker());
		assertSame(protectedAreaBlock.getMarker(), protectedAreaBlock.eGet(feature));
		assertSame(protectedAreaBlock.getMarker(), protectedAreaBlock.eGet(feature, false));
		assertTrue(protectedAreaBlock.eIsSet(feature));

		protectedAreaBlock.setMarker(null);
		assertTrue(notified);
		notified = false;
		assertNull(protectedAreaBlock.getMarker());
		assertSame(feature.getDefaultValue(), protectedAreaBlock.getMarker());
		assertSame(protectedAreaBlock.getMarker(), protectedAreaBlock.eGet(feature));
		assertSame(protectedAreaBlock.getMarker(), protectedAreaBlock.eGet(feature, false));
		assertFalse(protectedAreaBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_StartPosition();
		ProtectedAreaBlock protectedAreaBlock = MtlFactory.eINSTANCE.createProtectedAreaBlock();
		protectedAreaBlock.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(protectedAreaBlock.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)protectedAreaBlock.getStartPosition()).intValue());

		protectedAreaBlock.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)protectedAreaBlock.getStartPosition()).intValue());
		assertEquals(((Integer)protectedAreaBlock.getStartPosition()).intValue(), ((Integer)protectedAreaBlock.eGet(feature)).intValue());
		assertTrue(protectedAreaBlock.eIsSet(feature));

		protectedAreaBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)protectedAreaBlock.getStartPosition()).intValue());
		assertEquals(((Integer)protectedAreaBlock.getStartPosition()).intValue(), ((Integer)protectedAreaBlock.eGet(feature)).intValue());
		assertFalse(protectedAreaBlock.eIsSet(feature));

		protectedAreaBlock.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)protectedAreaBlock.getStartPosition()).intValue());
		assertEquals(((Integer)protectedAreaBlock.getStartPosition()).intValue(), ((Integer)protectedAreaBlock.eGet(feature)).intValue());
		assertTrue(protectedAreaBlock.eIsSet(feature));

		protectedAreaBlock.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)protectedAreaBlock.getStartPosition()).intValue());
		assertEquals(((Integer)protectedAreaBlock.getStartPosition()).intValue(), ((Integer)protectedAreaBlock.eGet(feature)).intValue());
		assertFalse(protectedAreaBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_EndPosition();
		ProtectedAreaBlock protectedAreaBlock = MtlFactory.eINSTANCE.createProtectedAreaBlock();
		protectedAreaBlock.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(protectedAreaBlock.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)protectedAreaBlock.getEndPosition()).intValue());

		protectedAreaBlock.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)protectedAreaBlock.getEndPosition()).intValue());
		assertEquals(((Integer)protectedAreaBlock.getEndPosition()).intValue(), ((Integer)protectedAreaBlock.eGet(feature)).intValue());
		assertTrue(protectedAreaBlock.eIsSet(feature));

		protectedAreaBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)protectedAreaBlock.getEndPosition()).intValue());
		assertEquals(((Integer)protectedAreaBlock.getEndPosition()).intValue(), ((Integer)protectedAreaBlock.eGet(feature)).intValue());
		assertFalse(protectedAreaBlock.eIsSet(feature));

		protectedAreaBlock.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)protectedAreaBlock.getEndPosition()).intValue());
		assertEquals(((Integer)protectedAreaBlock.getEndPosition()).intValue(), ((Integer)protectedAreaBlock.eGet(feature)).intValue());
		assertTrue(protectedAreaBlock.eIsSet(feature));

		protectedAreaBlock.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)protectedAreaBlock.getEndPosition()).intValue());
		assertEquals(((Integer)protectedAreaBlock.getEndPosition()).intValue(), ((Integer)protectedAreaBlock.eGet(feature)).intValue());
		assertFalse(protectedAreaBlock.eIsSet(feature));
	}
	
}

