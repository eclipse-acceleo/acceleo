package org.eclipse.acceleo.model.tests.unit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.Block;

/**
 * Tests the behavior of the {@link Block} class.
 * 
 * @generated
 */
public class BlockTest extends AbstractMtlTest {
	/**
	 * Tests the behavior of reference <code>body</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testBody() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getBlock_Body();
		Block block = MtlFactory.eINSTANCE.createBlock();
		block.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.OCLExpression bodyValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplateExpression();
		List<org.eclipse.ocl.ecore.OCLExpression> listBody = new ArrayList<org.eclipse.ocl.ecore.OCLExpression>(1);
		listBody.add(bodyValue);

		assertFalse(block.eIsSet(feature));
		assertTrue(block.getBody().isEmpty());

		block.getBody().add(bodyValue);
		assertTrue(notified);
		notified = false;
		assertTrue(block.getBody().contains(bodyValue));
		assertSame(block.getBody(), block.eGet(feature));
		assertSame(block.getBody(), block.eGet(feature, false));
		assertTrue(block.eIsSet(feature));

		block.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(block.getBody().isEmpty());
		assertSame(block.getBody(), block.eGet(feature));
		assertSame(block.getBody(), block.eGet(feature, false));
		assertFalse(block.eIsSet(feature));

		block.eSet(feature, listBody);
		assertTrue(notified);
		notified = false;
		assertTrue(block.getBody().contains(bodyValue));
		assertSame(block.getBody(), block.eGet(feature));
		assertSame(block.getBody(), block.eGet(feature, false));
		assertTrue(block.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>init</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testInit() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getBlock_Init();
		Block block = MtlFactory.eINSTANCE.createBlock();
		block.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.model.mtl.InitSection initValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createInitSection();

		assertFalse(block.eIsSet(feature));
		assertNull(block.getInit());

		block.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, block.getInit());
		assertSame(block.getInit(), block.eGet(feature));
		assertSame(block.getInit(), block.eGet(feature, false));
		assertTrue(block.eIsSet(feature));

		block.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(block.getInit());
		assertSame(block.getInit(), block.eGet(feature));
		assertSame(block.getInit(), block.eGet(feature, false));
		assertFalse(block.eIsSet(feature));

		block.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, block.getInit());
		assertSame(block.getInit(), block.eGet(feature));
		assertSame(block.getInit(), block.eGet(feature, false));
		assertTrue(block.eIsSet(feature));

		block.eSet(feature, initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, block.getInit());
		assertSame(block.getInit(), block.eGet(feature));
		assertSame(block.getInit(), block.eGet(feature, false));
		assertTrue(block.eIsSet(feature));

		block.setInit(null);
		assertTrue(notified);
		notified = false;
		assertNull(block.getInit());
		assertSame(feature.getDefaultValue(), block.getInit());
		assertSame(block.getInit(), block.eGet(feature));
		assertSame(block.getInit(), block.eGet(feature, false));
		assertFalse(block.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_StartPosition();
		Block block = MtlFactory.eINSTANCE.createBlock();
		block.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(block.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)block.getStartPosition()).intValue());

		block.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)block.getStartPosition()).intValue());
		assertEquals(((Integer)block.getStartPosition()).intValue(), ((Integer)block.eGet(feature)).intValue());
		assertTrue(block.eIsSet(feature));

		block.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)block.getStartPosition()).intValue());
		assertEquals(((Integer)block.getStartPosition()).intValue(), ((Integer)block.eGet(feature)).intValue());
		assertFalse(block.eIsSet(feature));

		block.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)block.getStartPosition()).intValue());
		assertEquals(((Integer)block.getStartPosition()).intValue(), ((Integer)block.eGet(feature)).intValue());
		assertTrue(block.eIsSet(feature));

		block.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)block.getStartPosition()).intValue());
		assertEquals(((Integer)block.getStartPosition()).intValue(), ((Integer)block.eGet(feature)).intValue());
		assertFalse(block.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_EndPosition();
		Block block = MtlFactory.eINSTANCE.createBlock();
		block.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(block.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)block.getEndPosition()).intValue());

		block.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)block.getEndPosition()).intValue());
		assertEquals(((Integer)block.getEndPosition()).intValue(), ((Integer)block.eGet(feature)).intValue());
		assertTrue(block.eIsSet(feature));

		block.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)block.getEndPosition()).intValue());
		assertEquals(((Integer)block.getEndPosition()).intValue(), ((Integer)block.eGet(feature)).intValue());
		assertFalse(block.eIsSet(feature));

		block.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)block.getEndPosition()).intValue());
		assertEquals(((Integer)block.getEndPosition()).intValue(), ((Integer)block.eGet(feature)).intValue());
		assertTrue(block.eIsSet(feature));

		block.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)block.getEndPosition()).intValue());
		assertEquals(((Integer)block.getEndPosition()).intValue(), ((Integer)block.eGet(feature)).intValue());
		assertFalse(block.eIsSet(feature));
	}
	
}

