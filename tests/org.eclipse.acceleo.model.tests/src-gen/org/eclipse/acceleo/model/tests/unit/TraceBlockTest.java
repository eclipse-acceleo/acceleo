package org.eclipse.acceleo.model.tests.unit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.TraceBlock;

/**
 * Tests the behavior of the {@link TraceBlock} class.
 * 
 * @generated
 */
public class TraceBlockTest extends AbstractMtlTest {
	/**
	 * Tests the behavior of reference <code>body</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testBody() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getBlock_Body();
		TraceBlock traceBlock = MtlFactory.eINSTANCE.createTraceBlock();
		traceBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.OCLExpression bodyValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplateExpression();
		List<org.eclipse.ocl.ecore.OCLExpression> listBody = new ArrayList<org.eclipse.ocl.ecore.OCLExpression>(1);
		listBody.add(bodyValue);

		assertFalse(traceBlock.eIsSet(feature));
		assertTrue(traceBlock.getBody().isEmpty());

		traceBlock.getBody().add(bodyValue);
		assertTrue(notified);
		notified = false;
		assertTrue(traceBlock.getBody().contains(bodyValue));
		assertSame(traceBlock.getBody(), traceBlock.eGet(feature));
		assertSame(traceBlock.getBody(), traceBlock.eGet(feature, false));
		assertTrue(traceBlock.eIsSet(feature));

		traceBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(traceBlock.getBody().isEmpty());
		assertSame(traceBlock.getBody(), traceBlock.eGet(feature));
		assertSame(traceBlock.getBody(), traceBlock.eGet(feature, false));
		assertFalse(traceBlock.eIsSet(feature));

		traceBlock.eSet(feature, listBody);
		assertTrue(notified);
		notified = false;
		assertTrue(traceBlock.getBody().contains(bodyValue));
		assertSame(traceBlock.getBody(), traceBlock.eGet(feature));
		assertSame(traceBlock.getBody(), traceBlock.eGet(feature, false));
		assertTrue(traceBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>init</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testInit() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getBlock_Init();
		TraceBlock traceBlock = MtlFactory.eINSTANCE.createTraceBlock();
		traceBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.model.mtl.InitSection initValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createInitSection();

		assertFalse(traceBlock.eIsSet(feature));
		assertNull(traceBlock.getInit());

		traceBlock.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, traceBlock.getInit());
		assertSame(traceBlock.getInit(), traceBlock.eGet(feature));
		assertSame(traceBlock.getInit(), traceBlock.eGet(feature, false));
		assertTrue(traceBlock.eIsSet(feature));

		traceBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(traceBlock.getInit());
		assertSame(traceBlock.getInit(), traceBlock.eGet(feature));
		assertSame(traceBlock.getInit(), traceBlock.eGet(feature, false));
		assertFalse(traceBlock.eIsSet(feature));

		traceBlock.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, traceBlock.getInit());
		assertSame(traceBlock.getInit(), traceBlock.eGet(feature));
		assertSame(traceBlock.getInit(), traceBlock.eGet(feature, false));
		assertTrue(traceBlock.eIsSet(feature));

		traceBlock.eSet(feature, initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, traceBlock.getInit());
		assertSame(traceBlock.getInit(), traceBlock.eGet(feature));
		assertSame(traceBlock.getInit(), traceBlock.eGet(feature, false));
		assertTrue(traceBlock.eIsSet(feature));

		traceBlock.setInit(null);
		assertTrue(notified);
		notified = false;
		assertNull(traceBlock.getInit());
		assertSame(feature.getDefaultValue(), traceBlock.getInit());
		assertSame(traceBlock.getInit(), traceBlock.eGet(feature));
		assertSame(traceBlock.getInit(), traceBlock.eGet(feature, false));
		assertFalse(traceBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>modelElement</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testModelElement() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getTraceBlock_ModelElement();
		TraceBlock traceBlock = MtlFactory.eINSTANCE.createTraceBlock();
		traceBlock.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.OCLExpression modelElementValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplateExpression();

		assertFalse(traceBlock.eIsSet(feature));
		assertNull(traceBlock.getModelElement());

		traceBlock.setModelElement(modelElementValue);
		assertTrue(notified);
		notified = false;
		assertSame(modelElementValue, traceBlock.getModelElement());
		assertSame(traceBlock.getModelElement(), traceBlock.eGet(feature));
		assertSame(traceBlock.getModelElement(), traceBlock.eGet(feature, false));
		assertTrue(traceBlock.eIsSet(feature));

		traceBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(traceBlock.getModelElement());
		assertSame(traceBlock.getModelElement(), traceBlock.eGet(feature));
		assertSame(traceBlock.getModelElement(), traceBlock.eGet(feature, false));
		assertFalse(traceBlock.eIsSet(feature));

		traceBlock.setModelElement(modelElementValue);
		assertTrue(notified);
		notified = false;
		assertSame(modelElementValue, traceBlock.getModelElement());
		assertSame(traceBlock.getModelElement(), traceBlock.eGet(feature));
		assertSame(traceBlock.getModelElement(), traceBlock.eGet(feature, false));
		assertTrue(traceBlock.eIsSet(feature));

		traceBlock.eSet(feature, modelElementValue);
		assertTrue(notified);
		notified = false;
		assertSame(modelElementValue, traceBlock.getModelElement());
		assertSame(traceBlock.getModelElement(), traceBlock.eGet(feature));
		assertSame(traceBlock.getModelElement(), traceBlock.eGet(feature, false));
		assertTrue(traceBlock.eIsSet(feature));

		traceBlock.setModelElement(null);
		assertTrue(notified);
		notified = false;
		assertNull(traceBlock.getModelElement());
		assertSame(feature.getDefaultValue(), traceBlock.getModelElement());
		assertSame(traceBlock.getModelElement(), traceBlock.eGet(feature));
		assertSame(traceBlock.getModelElement(), traceBlock.eGet(feature, false));
		assertFalse(traceBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_StartPosition();
		TraceBlock traceBlock = MtlFactory.eINSTANCE.createTraceBlock();
		traceBlock.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(traceBlock.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)traceBlock.getStartPosition()).intValue());

		traceBlock.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)traceBlock.getStartPosition()).intValue());
		assertEquals(((Integer)traceBlock.getStartPosition()).intValue(), ((Integer)traceBlock.eGet(feature)).intValue());
		assertTrue(traceBlock.eIsSet(feature));

		traceBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)traceBlock.getStartPosition()).intValue());
		assertEquals(((Integer)traceBlock.getStartPosition()).intValue(), ((Integer)traceBlock.eGet(feature)).intValue());
		assertFalse(traceBlock.eIsSet(feature));

		traceBlock.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)traceBlock.getStartPosition()).intValue());
		assertEquals(((Integer)traceBlock.getStartPosition()).intValue(), ((Integer)traceBlock.eGet(feature)).intValue());
		assertTrue(traceBlock.eIsSet(feature));

		traceBlock.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)traceBlock.getStartPosition()).intValue());
		assertEquals(((Integer)traceBlock.getStartPosition()).intValue(), ((Integer)traceBlock.eGet(feature)).intValue());
		assertFalse(traceBlock.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_EndPosition();
		TraceBlock traceBlock = MtlFactory.eINSTANCE.createTraceBlock();
		traceBlock.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(traceBlock.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)traceBlock.getEndPosition()).intValue());

		traceBlock.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)traceBlock.getEndPosition()).intValue());
		assertEquals(((Integer)traceBlock.getEndPosition()).intValue(), ((Integer)traceBlock.eGet(feature)).intValue());
		assertTrue(traceBlock.eIsSet(feature));

		traceBlock.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)traceBlock.getEndPosition()).intValue());
		assertEquals(((Integer)traceBlock.getEndPosition()).intValue(), ((Integer)traceBlock.eGet(feature)).intValue());
		assertFalse(traceBlock.eIsSet(feature));

		traceBlock.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)traceBlock.getEndPosition()).intValue());
		assertEquals(((Integer)traceBlock.getEndPosition()).intValue(), ((Integer)traceBlock.eGet(feature)).intValue());
		assertTrue(traceBlock.eIsSet(feature));

		traceBlock.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)traceBlock.getEndPosition()).intValue());
		assertEquals(((Integer)traceBlock.getEndPosition()).intValue(), ((Integer)traceBlock.eGet(feature)).intValue());
		assertFalse(traceBlock.eIsSet(feature));
	}
	
}

