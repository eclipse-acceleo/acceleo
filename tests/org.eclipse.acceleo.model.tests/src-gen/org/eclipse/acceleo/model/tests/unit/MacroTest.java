package org.eclipse.acceleo.model.tests.unit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.Macro;

/**
 * Tests the behavior of the {@link Macro} class.
 * 
 * @generated
 */
public class MacroTest extends AbstractMtlTest {
	/**
	 * Tests the behavior of reference <code>body</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testBody() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getBlock_Body();
		Macro macro = MtlFactory.eINSTANCE.createMacro();
		macro.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.OCLExpression bodyValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplateExpression();
		List<org.eclipse.ocl.ecore.OCLExpression> listBody = new ArrayList<org.eclipse.ocl.ecore.OCLExpression>(1);
		listBody.add(bodyValue);

		assertFalse(macro.eIsSet(feature));
		assertTrue(macro.getBody().isEmpty());

		macro.getBody().add(bodyValue);
		assertTrue(notified);
		notified = false;
		assertTrue(macro.getBody().contains(bodyValue));
		assertSame(macro.getBody(), macro.eGet(feature));
		assertSame(macro.getBody(), macro.eGet(feature, false));
		assertTrue(macro.eIsSet(feature));

		macro.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(macro.getBody().isEmpty());
		assertSame(macro.getBody(), macro.eGet(feature));
		assertSame(macro.getBody(), macro.eGet(feature, false));
		assertFalse(macro.eIsSet(feature));

		macro.eSet(feature, listBody);
		assertTrue(notified);
		notified = false;
		assertTrue(macro.getBody().contains(bodyValue));
		assertSame(macro.getBody(), macro.eGet(feature));
		assertSame(macro.getBody(), macro.eGet(feature, false));
		assertTrue(macro.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>parameter</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testParameter() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getMacro_Parameter();
		Macro macro = MtlFactory.eINSTANCE.createMacro();
		macro.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.Variable parameterValue = org.eclipse.ocl.ecore.EcoreFactory.eINSTANCE.createVariable();
		List<org.eclipse.ocl.ecore.Variable> listParameter = new ArrayList<org.eclipse.ocl.ecore.Variable>(1);
		listParameter.add(parameterValue);

		assertFalse(macro.eIsSet(feature));
		assertTrue(macro.getParameter().isEmpty());

		macro.getParameter().add(parameterValue);
		assertTrue(notified);
		notified = false;
		assertTrue(macro.getParameter().contains(parameterValue));
		assertSame(macro.getParameter(), macro.eGet(feature));
		assertSame(macro.getParameter(), macro.eGet(feature, false));
		assertTrue(macro.eIsSet(feature));

		macro.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(macro.getParameter().isEmpty());
		assertSame(macro.getParameter(), macro.eGet(feature));
		assertSame(macro.getParameter(), macro.eGet(feature, false));
		assertFalse(macro.eIsSet(feature));

		macro.eSet(feature, listParameter);
		assertTrue(notified);
		notified = false;
		assertTrue(macro.getParameter().contains(parameterValue));
		assertSame(macro.getParameter(), macro.eGet(feature));
		assertSame(macro.getParameter(), macro.eGet(feature, false));
		assertTrue(macro.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>init</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testInit() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getBlock_Init();
		Macro macro = MtlFactory.eINSTANCE.createMacro();
		macro.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.model.mtl.InitSection initValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createInitSection();

		assertFalse(macro.eIsSet(feature));
		assertNull(macro.getInit());

		macro.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, macro.getInit());
		assertSame(macro.getInit(), macro.eGet(feature));
		assertSame(macro.getInit(), macro.eGet(feature, false));
		assertTrue(macro.eIsSet(feature));

		macro.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(macro.getInit());
		assertSame(macro.getInit(), macro.eGet(feature));
		assertSame(macro.getInit(), macro.eGet(feature, false));
		assertFalse(macro.eIsSet(feature));

		macro.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, macro.getInit());
		assertSame(macro.getInit(), macro.eGet(feature));
		assertSame(macro.getInit(), macro.eGet(feature, false));
		assertTrue(macro.eIsSet(feature));

		macro.eSet(feature, initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, macro.getInit());
		assertSame(macro.getInit(), macro.eGet(feature));
		assertSame(macro.getInit(), macro.eGet(feature, false));
		assertTrue(macro.eIsSet(feature));

		macro.setInit(null);
		assertTrue(notified);
		notified = false;
		assertNull(macro.getInit());
		assertSame(feature.getDefaultValue(), macro.getInit());
		assertSame(macro.getInit(), macro.eGet(feature));
		assertSame(macro.getInit(), macro.eGet(feature, false));
		assertFalse(macro.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>type</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testType() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getMacro_Type();
		Macro macro = MtlFactory.eINSTANCE.createMacro();
		macro.eAdapters().add(new MockEAdapter());
		org.eclipse.emf.ecore.EClassifier typeValue = org.eclipse.emf.ecore.EcoreFactory.eINSTANCE.createEClass();

		assertFalse(macro.eIsSet(feature));
		assertNull(macro.getType());

		macro.setType(typeValue);
		assertTrue(notified);
		notified = false;
		assertSame(typeValue, macro.getType());
		assertSame(macro.getType(), macro.eGet(feature));
		assertSame(macro.getType(), macro.eGet(feature, false));
		assertTrue(macro.eIsSet(feature));

		macro.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(macro.getType());
		assertSame(macro.getType(), macro.eGet(feature));
		assertSame(macro.getType(), macro.eGet(feature, false));
		assertFalse(macro.eIsSet(feature));

		macro.setType(typeValue);
		assertTrue(notified);
		notified = false;
		assertSame(typeValue, macro.getType());
		assertSame(macro.getType(), macro.eGet(feature));
		assertSame(macro.getType(), macro.eGet(feature, false));
		assertTrue(macro.eIsSet(feature));

		macro.eSet(feature, typeValue);
		assertTrue(notified);
		notified = false;
		assertSame(typeValue, macro.getType());
		assertSame(macro.getType(), macro.eGet(feature));
		assertSame(macro.getType(), macro.eGet(feature, false));
		assertTrue(macro.eIsSet(feature));

		macro.setType(null);
		assertTrue(notified);
		notified = false;
		assertNull(macro.getType());
		assertSame(feature.getDefaultValue(), macro.getType());
		assertSame(macro.getType(), macro.eGet(feature));
		assertSame(macro.getType(), macro.eGet(feature, false));
		assertFalse(macro.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_StartPosition();
		Macro macro = MtlFactory.eINSTANCE.createMacro();
		macro.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(macro.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)macro.getStartPosition()).intValue());

		macro.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)macro.getStartPosition()).intValue());
		assertEquals(((Integer)macro.getStartPosition()).intValue(), ((Integer)macro.eGet(feature)).intValue());
		assertTrue(macro.eIsSet(feature));

		macro.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)macro.getStartPosition()).intValue());
		assertEquals(((Integer)macro.getStartPosition()).intValue(), ((Integer)macro.eGet(feature)).intValue());
		assertFalse(macro.eIsSet(feature));

		macro.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)macro.getStartPosition()).intValue());
		assertEquals(((Integer)macro.getStartPosition()).intValue(), ((Integer)macro.eGet(feature)).intValue());
		assertTrue(macro.eIsSet(feature));

		macro.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)macro.getStartPosition()).intValue());
		assertEquals(((Integer)macro.getStartPosition()).intValue(), ((Integer)macro.eGet(feature)).intValue());
		assertFalse(macro.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_EndPosition();
		Macro macro = MtlFactory.eINSTANCE.createMacro();
		macro.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(macro.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)macro.getEndPosition()).intValue());

		macro.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)macro.getEndPosition()).intValue());
		assertEquals(((Integer)macro.getEndPosition()).intValue(), ((Integer)macro.eGet(feature)).intValue());
		assertTrue(macro.eIsSet(feature));

		macro.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)macro.getEndPosition()).intValue());
		assertEquals(((Integer)macro.getEndPosition()).intValue(), ((Integer)macro.eGet(feature)).intValue());
		assertFalse(macro.eIsSet(feature));

		macro.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)macro.getEndPosition()).intValue());
		assertEquals(((Integer)macro.getEndPosition()).intValue(), ((Integer)macro.eGet(feature)).intValue());
		assertTrue(macro.eIsSet(feature));

		macro.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)macro.getEndPosition()).intValue());
		assertEquals(((Integer)macro.getEndPosition()).intValue(), ((Integer)macro.eGet(feature)).intValue());
		assertFalse(macro.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>visibility</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testVisibility() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getModuleElement_Visibility();
		Macro macro = MtlFactory.eINSTANCE.createMacro();
		macro.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.model.mtl.VisibilityKind visibilityValue = (org.eclipse.acceleo.model.mtl.VisibilityKind)feature.getDefaultValue();
		for (org.eclipse.acceleo.model.mtl.VisibilityKind aVisibilityKind : org.eclipse.acceleo.model.mtl.VisibilityKind.VALUES) {
			if (visibilityValue.getValue() != aVisibilityKind.getValue()) {
				visibilityValue = aVisibilityKind;
				break;
			}
		}

		assertFalse(macro.eIsSet(feature));
		assertEquals(feature.getDefaultValue(), macro.getVisibility());

		macro.setVisibility(visibilityValue);
		assertTrue(notified);
		notified = false;
		assertEquals(visibilityValue, macro.getVisibility());
		assertEquals(macro.getVisibility(), macro.eGet(feature));
		assertTrue(macro.eIsSet(feature));

		macro.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), macro.getVisibility());
		assertEquals(macro.getVisibility(), macro.eGet(feature));
		assertFalse(macro.eIsSet(feature));

		macro.eSet(feature, visibilityValue);
		assertTrue(notified);
		notified = false;
		assertEquals(visibilityValue, macro.getVisibility());
		assertEquals(macro.getVisibility(), macro.eGet(feature));
		assertTrue(macro.eIsSet(feature));

		macro.setVisibility(null);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), macro.getVisibility());
		assertEquals(macro.getVisibility(), macro.eGet(feature));
		assertFalse(macro.eIsSet(feature));
	}
	
}

