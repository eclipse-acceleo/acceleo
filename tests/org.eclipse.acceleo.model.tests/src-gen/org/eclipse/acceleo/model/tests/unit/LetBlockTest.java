package org.eclipse.acceleo.model.tests.unit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.LetBlock;

/**
 * Tests the behavior of the {@link LetBlock} class.
 * 
 * @generated
 */
public class LetBlockTest extends AbstractMtlTest {
	/**
	 * Tests the behavior of reference <code>body</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testBody() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getBlock_Body();
		LetBlock letBlock = MtlFactory.eINSTANCE.createLetBlock();
		letBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.OCLExpression bodyValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplateExpression();
		List<org.eclipse.ocl.ecore.OCLExpression> listBody = new ArrayList<org.eclipse.ocl.ecore.OCLExpression>(1);
		listBody.add(bodyValue);

		assertFalse(letBlock.eIsSet(feature));
		assertTrue(letBlock.getBody().isEmpty());

		letBlock.getBody().add(bodyValue);
		assertTrue(notified);
		notified = false;
		assertTrue(letBlock.getBody().contains(bodyValue));
		assertSame(letBlock.getBody(), letBlock.eGet(feature));
		assertSame(letBlock.getBody(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(letBlock.getBody().isEmpty());
		assertSame(letBlock.getBody(), letBlock.eGet(feature));
		assertSame(letBlock.getBody(), letBlock.eGet(feature, false));
		assertFalse(letBlock.eIsSet(feature));

		letBlock.eSet(feature, listBody);
		assertTrue(notified);
		notified = false;
		assertTrue(letBlock.getBody().contains(bodyValue));
		assertSame(letBlock.getBody(), letBlock.eGet(feature));
		assertSame(letBlock.getBody(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>elseLet</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testElseLet() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getLetBlock_ElseLet();
		LetBlock letBlock = MtlFactory.eINSTANCE.createLetBlock();
		letBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.model.mtl.LetBlock elseLetValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createLetBlock();
		List<org.eclipse.acceleo.model.mtl.LetBlock> listElseLet = new ArrayList<org.eclipse.acceleo.model.mtl.LetBlock>(1);
		listElseLet.add(elseLetValue);

		assertFalse(letBlock.eIsSet(feature));
		assertTrue(letBlock.getElseLet().isEmpty());

		letBlock.getElseLet().add(elseLetValue);
		assertTrue(notified);
		notified = false;
		assertTrue(letBlock.getElseLet().contains(elseLetValue));
		assertSame(letBlock.getElseLet(), letBlock.eGet(feature));
		assertSame(letBlock.getElseLet(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(letBlock.getElseLet().isEmpty());
		assertSame(letBlock.getElseLet(), letBlock.eGet(feature));
		assertSame(letBlock.getElseLet(), letBlock.eGet(feature, false));
		assertFalse(letBlock.eIsSet(feature));

		letBlock.eSet(feature, listElseLet);
		assertTrue(notified);
		notified = false;
		assertTrue(letBlock.getElseLet().contains(elseLetValue));
		assertSame(letBlock.getElseLet(), letBlock.eGet(feature));
		assertSame(letBlock.getElseLet(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>init</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testInit() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getBlock_Init();
		LetBlock letBlock = MtlFactory.eINSTANCE.createLetBlock();
		letBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.model.mtl.InitSection initValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createInitSection();

		assertFalse(letBlock.eIsSet(feature));
		assertNull(letBlock.getInit());

		letBlock.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, letBlock.getInit());
		assertSame(letBlock.getInit(), letBlock.eGet(feature));
		assertSame(letBlock.getInit(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(letBlock.getInit());
		assertSame(letBlock.getInit(), letBlock.eGet(feature));
		assertSame(letBlock.getInit(), letBlock.eGet(feature, false));
		assertFalse(letBlock.eIsSet(feature));

		letBlock.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, letBlock.getInit());
		assertSame(letBlock.getInit(), letBlock.eGet(feature));
		assertSame(letBlock.getInit(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.eSet(feature, initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, letBlock.getInit());
		assertSame(letBlock.getInit(), letBlock.eGet(feature));
		assertSame(letBlock.getInit(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.setInit(null);
		assertTrue(notified);
		notified = false;
		assertNull(letBlock.getInit());
		assertSame(feature.getDefaultValue(), letBlock.getInit());
		assertSame(letBlock.getInit(), letBlock.eGet(feature));
		assertSame(letBlock.getInit(), letBlock.eGet(feature, false));
		assertFalse(letBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>else</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testElse() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getLetBlock_Else();
		LetBlock letBlock = MtlFactory.eINSTANCE.createLetBlock();
		letBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.model.mtl.Block elseValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createBlock();

		assertFalse(letBlock.eIsSet(feature));
		assertNull(letBlock.getElse());

		letBlock.setElse(elseValue);
		assertTrue(notified);
		notified = false;
		assertSame(elseValue, letBlock.getElse());
		assertSame(letBlock.getElse(), letBlock.eGet(feature));
		assertSame(letBlock.getElse(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(letBlock.getElse());
		assertSame(letBlock.getElse(), letBlock.eGet(feature));
		assertSame(letBlock.getElse(), letBlock.eGet(feature, false));
		assertFalse(letBlock.eIsSet(feature));

		letBlock.setElse(elseValue);
		assertTrue(notified);
		notified = false;
		assertSame(elseValue, letBlock.getElse());
		assertSame(letBlock.getElse(), letBlock.eGet(feature));
		assertSame(letBlock.getElse(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.eSet(feature, elseValue);
		assertTrue(notified);
		notified = false;
		assertSame(elseValue, letBlock.getElse());
		assertSame(letBlock.getElse(), letBlock.eGet(feature));
		assertSame(letBlock.getElse(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.setElse(null);
		assertTrue(notified);
		notified = false;
		assertNull(letBlock.getElse());
		assertSame(feature.getDefaultValue(), letBlock.getElse());
		assertSame(letBlock.getElse(), letBlock.eGet(feature));
		assertSame(letBlock.getElse(), letBlock.eGet(feature, false));
		assertFalse(letBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>letVariable</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testLetVariable() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getLetBlock_LetVariable();
		LetBlock letBlock = MtlFactory.eINSTANCE.createLetBlock();
		letBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.Variable letVariableValue = org.eclipse.ocl.ecore.EcoreFactory.eINSTANCE.createVariable();

		assertFalse(letBlock.eIsSet(feature));
		assertNull(letBlock.getLetVariable());

		letBlock.setLetVariable(letVariableValue);
		assertTrue(notified);
		notified = false;
		assertSame(letVariableValue, letBlock.getLetVariable());
		assertSame(letBlock.getLetVariable(), letBlock.eGet(feature));
		assertSame(letBlock.getLetVariable(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(letBlock.getLetVariable());
		assertSame(letBlock.getLetVariable(), letBlock.eGet(feature));
		assertSame(letBlock.getLetVariable(), letBlock.eGet(feature, false));
		assertFalse(letBlock.eIsSet(feature));

		letBlock.setLetVariable(letVariableValue);
		assertTrue(notified);
		notified = false;
		assertSame(letVariableValue, letBlock.getLetVariable());
		assertSame(letBlock.getLetVariable(), letBlock.eGet(feature));
		assertSame(letBlock.getLetVariable(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.eSet(feature, letVariableValue);
		assertTrue(notified);
		notified = false;
		assertSame(letVariableValue, letBlock.getLetVariable());
		assertSame(letBlock.getLetVariable(), letBlock.eGet(feature));
		assertSame(letBlock.getLetVariable(), letBlock.eGet(feature, false));
		assertTrue(letBlock.eIsSet(feature));

		letBlock.setLetVariable(null);
		assertTrue(notified);
		notified = false;
		assertNull(letBlock.getLetVariable());
		assertSame(feature.getDefaultValue(), letBlock.getLetVariable());
		assertSame(letBlock.getLetVariable(), letBlock.eGet(feature));
		assertSame(letBlock.getLetVariable(), letBlock.eGet(feature, false));
		assertFalse(letBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_StartPosition();
		LetBlock letBlock = MtlFactory.eINSTANCE.createLetBlock();
		letBlock.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(letBlock.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)letBlock.getStartPosition()).intValue());

		letBlock.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)letBlock.getStartPosition()).intValue());
		assertEquals(((Integer)letBlock.getStartPosition()).intValue(), ((Integer)letBlock.eGet(feature)).intValue());
		assertTrue(letBlock.eIsSet(feature));

		letBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)letBlock.getStartPosition()).intValue());
		assertEquals(((Integer)letBlock.getStartPosition()).intValue(), ((Integer)letBlock.eGet(feature)).intValue());
		assertFalse(letBlock.eIsSet(feature));

		letBlock.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)letBlock.getStartPosition()).intValue());
		assertEquals(((Integer)letBlock.getStartPosition()).intValue(), ((Integer)letBlock.eGet(feature)).intValue());
		assertTrue(letBlock.eIsSet(feature));

		letBlock.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)letBlock.getStartPosition()).intValue());
		assertEquals(((Integer)letBlock.getStartPosition()).intValue(), ((Integer)letBlock.eGet(feature)).intValue());
		assertFalse(letBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_EndPosition();
		LetBlock letBlock = MtlFactory.eINSTANCE.createLetBlock();
		letBlock.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(letBlock.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)letBlock.getEndPosition()).intValue());

		letBlock.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)letBlock.getEndPosition()).intValue());
		assertEquals(((Integer)letBlock.getEndPosition()).intValue(), ((Integer)letBlock.eGet(feature)).intValue());
		assertTrue(letBlock.eIsSet(feature));

		letBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)letBlock.getEndPosition()).intValue());
		assertEquals(((Integer)letBlock.getEndPosition()).intValue(), ((Integer)letBlock.eGet(feature)).intValue());
		assertFalse(letBlock.eIsSet(feature));

		letBlock.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)letBlock.getEndPosition()).intValue());
		assertEquals(((Integer)letBlock.getEndPosition()).intValue(), ((Integer)letBlock.eGet(feature)).intValue());
		assertTrue(letBlock.eIsSet(feature));

		letBlock.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)letBlock.getEndPosition()).intValue());
		assertEquals(((Integer)letBlock.getEndPosition()).intValue(), ((Integer)letBlock.eGet(feature)).intValue());
		assertFalse(letBlock.eIsSet(feature));
	}
	
}

