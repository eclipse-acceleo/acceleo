package org.eclipse.acceleo.model.tests.unit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.Template;

/**
 * Tests the behavior of the {@link Template} class.
 * 
 * @generated
 */
public class TemplateTest extends AbstractMtlTest {
	/**
	 * Tests the behavior of reference <code>body</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testBody() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getBlock_Body();
		Template template = MtlFactory.eINSTANCE.createTemplate();
		template.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.OCLExpression bodyValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplateExpression();
		List<org.eclipse.ocl.ecore.OCLExpression> listBody = new ArrayList<org.eclipse.ocl.ecore.OCLExpression>(1);
		listBody.add(bodyValue);

		assertFalse(template.eIsSet(feature));
		assertTrue(template.getBody().isEmpty());

		template.getBody().add(bodyValue);
		assertTrue(notified);
		notified = false;
		assertTrue(template.getBody().contains(bodyValue));
		assertSame(template.getBody(), template.eGet(feature));
		assertSame(template.getBody(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));

		template.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(template.getBody().isEmpty());
		assertSame(template.getBody(), template.eGet(feature));
		assertSame(template.getBody(), template.eGet(feature, false));
		assertFalse(template.eIsSet(feature));

		template.eSet(feature, listBody);
		assertTrue(notified);
		notified = false;
		assertTrue(template.getBody().contains(bodyValue));
		assertSame(template.getBody(), template.eGet(feature));
		assertSame(template.getBody(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>overrides</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testOverrides() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getTemplate_Overrides();
		Template template = MtlFactory.eINSTANCE.createTemplate();
		template.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.model.mtl.Template overridesValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplate();
		List<org.eclipse.acceleo.model.mtl.Template> listOverrides = new ArrayList<org.eclipse.acceleo.model.mtl.Template>(1);
		listOverrides.add(overridesValue);

		assertFalse(template.eIsSet(feature));
		assertTrue(template.getOverrides().isEmpty());

		template.getOverrides().add(overridesValue);
		assertTrue(notified);
		notified = false;
		assertTrue(template.getOverrides().contains(overridesValue));
		assertSame(template.getOverrides(), template.eGet(feature));
		assertSame(template.getOverrides(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));

		template.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(template.getOverrides().isEmpty());
		assertSame(template.getOverrides(), template.eGet(feature));
		assertSame(template.getOverrides(), template.eGet(feature, false));
		assertFalse(template.eIsSet(feature));

		template.eSet(feature, listOverrides);
		assertTrue(notified);
		notified = false;
		assertTrue(template.getOverrides().contains(overridesValue));
		assertSame(template.getOverrides(), template.eGet(feature));
		assertSame(template.getOverrides(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>parameter</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testParameter() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getTemplate_Parameter();
		Template template = MtlFactory.eINSTANCE.createTemplate();
		template.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.Variable parameterValue = org.eclipse.ocl.ecore.EcoreFactory.eINSTANCE.createVariable();
		List<org.eclipse.ocl.ecore.Variable> listParameter = new ArrayList<org.eclipse.ocl.ecore.Variable>(1);
		listParameter.add(parameterValue);

		assertFalse(template.eIsSet(feature));
		assertTrue(template.getParameter().isEmpty());

		template.getParameter().add(parameterValue);
		assertTrue(notified);
		notified = false;
		assertTrue(template.getParameter().contains(parameterValue));
		assertSame(template.getParameter(), template.eGet(feature));
		assertSame(template.getParameter(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));

		template.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(template.getParameter().isEmpty());
		assertSame(template.getParameter(), template.eGet(feature));
		assertSame(template.getParameter(), template.eGet(feature, false));
		assertFalse(template.eIsSet(feature));

		template.eSet(feature, listParameter);
		assertTrue(notified);
		notified = false;
		assertTrue(template.getParameter().contains(parameterValue));
		assertSame(template.getParameter(), template.eGet(feature));
		assertSame(template.getParameter(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>init</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testInit() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getBlock_Init();
		Template template = MtlFactory.eINSTANCE.createTemplate();
		template.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.model.mtl.InitSection initValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createInitSection();

		assertFalse(template.eIsSet(feature));
		assertNull(template.getInit());

		template.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, template.getInit());
		assertSame(template.getInit(), template.eGet(feature));
		assertSame(template.getInit(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));

		template.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(template.getInit());
		assertSame(template.getInit(), template.eGet(feature));
		assertSame(template.getInit(), template.eGet(feature, false));
		assertFalse(template.eIsSet(feature));

		template.setInit(initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, template.getInit());
		assertSame(template.getInit(), template.eGet(feature));
		assertSame(template.getInit(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));

		template.eSet(feature, initValue);
		assertTrue(notified);
		notified = false;
		assertSame(initValue, template.getInit());
		assertSame(template.getInit(), template.eGet(feature));
		assertSame(template.getInit(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));

		template.setInit(null);
		assertTrue(notified);
		notified = false;
		assertNull(template.getInit());
		assertSame(feature.getDefaultValue(), template.getInit());
		assertSame(template.getInit(), template.eGet(feature));
		assertSame(template.getInit(), template.eGet(feature, false));
		assertFalse(template.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>guard</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testGuard() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getTemplate_Guard();
		Template template = MtlFactory.eINSTANCE.createTemplate();
		template.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.OCLExpression guardValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplateExpression();

		assertFalse(template.eIsSet(feature));
		assertNull(template.getGuard());

		template.setGuard(guardValue);
		assertTrue(notified);
		notified = false;
		assertSame(guardValue, template.getGuard());
		assertSame(template.getGuard(), template.eGet(feature));
		assertSame(template.getGuard(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));

		template.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(template.getGuard());
		assertSame(template.getGuard(), template.eGet(feature));
		assertSame(template.getGuard(), template.eGet(feature, false));
		assertFalse(template.eIsSet(feature));

		template.setGuard(guardValue);
		assertTrue(notified);
		notified = false;
		assertSame(guardValue, template.getGuard());
		assertSame(template.getGuard(), template.eGet(feature));
		assertSame(template.getGuard(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));

		template.eSet(feature, guardValue);
		assertTrue(notified);
		notified = false;
		assertSame(guardValue, template.getGuard());
		assertSame(template.getGuard(), template.eGet(feature));
		assertSame(template.getGuard(), template.eGet(feature, false));
		assertTrue(template.eIsSet(feature));

		template.setGuard(null);
		assertTrue(notified);
		notified = false;
		assertNull(template.getGuard());
		assertSame(feature.getDefaultValue(), template.getGuard());
		assertSame(template.getGuard(), template.eGet(feature));
		assertSame(template.getGuard(), template.eGet(feature, false));
		assertFalse(template.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_StartPosition();
		Template template = MtlFactory.eINSTANCE.createTemplate();
		template.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(template.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)template.getStartPosition()).intValue());

		template.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)template.getStartPosition()).intValue());
		assertEquals(((Integer)template.getStartPosition()).intValue(), ((Integer)template.eGet(feature)).intValue());
		assertTrue(template.eIsSet(feature));

		template.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)template.getStartPosition()).intValue());
		assertEquals(((Integer)template.getStartPosition()).intValue(), ((Integer)template.eGet(feature)).intValue());
		assertFalse(template.eIsSet(feature));

		template.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)template.getStartPosition()).intValue());
		assertEquals(((Integer)template.getStartPosition()).intValue(), ((Integer)template.eGet(feature)).intValue());
		assertTrue(template.eIsSet(feature));

		template.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)template.getStartPosition()).intValue());
		assertEquals(((Integer)template.getStartPosition()).intValue(), ((Integer)template.eGet(feature)).intValue());
		assertFalse(template.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_EndPosition();
		Template template = MtlFactory.eINSTANCE.createTemplate();
		template.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(template.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)template.getEndPosition()).intValue());

		template.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)template.getEndPosition()).intValue());
		assertEquals(((Integer)template.getEndPosition()).intValue(), ((Integer)template.eGet(feature)).intValue());
		assertTrue(template.eIsSet(feature));

		template.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)template.getEndPosition()).intValue());
		assertEquals(((Integer)template.getEndPosition()).intValue(), ((Integer)template.eGet(feature)).intValue());
		assertFalse(template.eIsSet(feature));

		template.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)template.getEndPosition()).intValue());
		assertEquals(((Integer)template.getEndPosition()).intValue(), ((Integer)template.eGet(feature)).intValue());
		assertTrue(template.eIsSet(feature));

		template.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)template.getEndPosition()).intValue());
		assertEquals(((Integer)template.getEndPosition()).intValue(), ((Integer)template.eGet(feature)).intValue());
		assertFalse(template.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>visibility</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testVisibility() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getModuleElement_Visibility();
		Template template = MtlFactory.eINSTANCE.createTemplate();
		template.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.model.mtl.VisibilityKind visibilityValue = (org.eclipse.acceleo.model.mtl.VisibilityKind)feature.getDefaultValue();
		for (org.eclipse.acceleo.model.mtl.VisibilityKind aVisibilityKind : org.eclipse.acceleo.model.mtl.VisibilityKind.VALUES) {
			if (visibilityValue.getValue() != aVisibilityKind.getValue()) {
				visibilityValue = aVisibilityKind;
				break;
			}
		}

		assertFalse(template.eIsSet(feature));
		assertEquals(feature.getDefaultValue(), template.getVisibility());

		template.setVisibility(visibilityValue);
		assertTrue(notified);
		notified = false;
		assertEquals(visibilityValue, template.getVisibility());
		assertEquals(template.getVisibility(), template.eGet(feature));
		assertTrue(template.eIsSet(feature));

		template.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), template.getVisibility());
		assertEquals(template.getVisibility(), template.eGet(feature));
		assertFalse(template.eIsSet(feature));

		template.eSet(feature, visibilityValue);
		assertTrue(notified);
		notified = false;
		assertEquals(visibilityValue, template.getVisibility());
		assertEquals(template.getVisibility(), template.eGet(feature));
		assertTrue(template.eIsSet(feature));

		template.setVisibility(null);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), template.getVisibility());
		assertEquals(template.getVisibility(), template.eGet(feature));
		assertFalse(template.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>main</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testMain() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getTemplate_Main();
		Template template = MtlFactory.eINSTANCE.createTemplate();
		template.eAdapters().add(new MockEAdapter());
		boolean mainValue = getBooleanDistinctFromDefault(feature);

		assertFalse(template.eIsSet(feature));
		assertEquals(((Boolean)feature.getDefaultValue()).booleanValue(), ((Boolean)template.isMain()).booleanValue());

		template.setMain(mainValue);
		assertTrue(notified);
		notified = false;
		assertEquals(mainValue, ((Boolean)template.isMain()).booleanValue());
		assertEquals(((Boolean)template.isMain()).booleanValue(), ((Boolean)template.eGet(feature)).booleanValue());
		assertTrue(template.eIsSet(feature));

		template.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Boolean)feature.getDefaultValue()).booleanValue(), ((Boolean)template.isMain()).booleanValue());
		assertEquals(((Boolean)template.isMain()).booleanValue(), ((Boolean)template.eGet(feature)).booleanValue());
		assertFalse(template.eIsSet(feature));

		template.eSet(feature, mainValue);
		assertTrue(notified);
		notified = false;
		assertEquals(mainValue, ((Boolean)template.isMain()).booleanValue());
		assertEquals(((Boolean)template.isMain()).booleanValue(), ((Boolean)template.eGet(feature)).booleanValue());
		assertTrue(template.eIsSet(feature));

		template.setMain(((Boolean)feature.getDefaultValue()).booleanValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Boolean)feature.getDefaultValue()).booleanValue(), ((Boolean)template.isMain()).booleanValue());
		assertEquals(((Boolean)template.isMain()).booleanValue(), ((Boolean)template.eGet(feature)).booleanValue());
		assertFalse(template.eIsSet(feature));
	}
	
}

