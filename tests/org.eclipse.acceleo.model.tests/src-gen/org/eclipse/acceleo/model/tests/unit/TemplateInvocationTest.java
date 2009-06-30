package org.eclipse.acceleo.model.tests.unit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.TemplateInvocation;

/**
 * Tests the behavior of the {@link TemplateInvocation} class.
 * 
 * @generated
 */
public class TemplateInvocationTest extends AbstractMtlTest {
	/**
	 * Tests the behavior of reference <code>argument</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testArgument() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getTemplateInvocation_Argument();
		TemplateInvocation templateInvocation = MtlFactory.eINSTANCE.createTemplateInvocation();
		templateInvocation.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.OCLExpression argumentValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplateExpression();
		List<org.eclipse.ocl.ecore.OCLExpression> listArgument = new ArrayList<org.eclipse.ocl.ecore.OCLExpression>(1);
		listArgument.add(argumentValue);

		assertFalse(templateInvocation.eIsSet(feature));
		assertTrue(templateInvocation.getArgument().isEmpty());

		templateInvocation.getArgument().add(argumentValue);
		assertTrue(notified);
		notified = false;
		assertTrue(templateInvocation.getArgument().contains(argumentValue));
		assertSame(templateInvocation.getArgument(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getArgument(), templateInvocation.eGet(feature, false));
		assertTrue(templateInvocation.eIsSet(feature));

		templateInvocation.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(templateInvocation.getArgument().isEmpty());
		assertSame(templateInvocation.getArgument(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getArgument(), templateInvocation.eGet(feature, false));
		assertFalse(templateInvocation.eIsSet(feature));

		templateInvocation.eSet(feature, listArgument);
		assertTrue(notified);
		notified = false;
		assertTrue(templateInvocation.getArgument().contains(argumentValue));
		assertSame(templateInvocation.getArgument(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getArgument(), templateInvocation.eGet(feature, false));
		assertTrue(templateInvocation.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>definition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testDefinition() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getTemplateInvocation_Definition();
		TemplateInvocation templateInvocation = MtlFactory.eINSTANCE.createTemplateInvocation();
		templateInvocation.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.model.mtl.Template definitionValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplate();

		assertFalse(templateInvocation.eIsSet(feature));
		assertNull(templateInvocation.getDefinition());

		templateInvocation.setDefinition(definitionValue);
		assertTrue(notified);
		notified = false;
		assertSame(definitionValue, templateInvocation.getDefinition());
		assertSame(templateInvocation.getDefinition(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getDefinition(), templateInvocation.eGet(feature, false));
		assertTrue(templateInvocation.eIsSet(feature));

		templateInvocation.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(templateInvocation.getDefinition());
		assertSame(templateInvocation.getDefinition(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getDefinition(), templateInvocation.eGet(feature, false));
		assertFalse(templateInvocation.eIsSet(feature));

		templateInvocation.setDefinition(definitionValue);
		assertTrue(notified);
		notified = false;
		assertSame(definitionValue, templateInvocation.getDefinition());
		assertSame(templateInvocation.getDefinition(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getDefinition(), templateInvocation.eGet(feature, false));
		assertTrue(templateInvocation.eIsSet(feature));

		templateInvocation.eSet(feature, definitionValue);
		assertTrue(notified);
		notified = false;
		assertSame(definitionValue, templateInvocation.getDefinition());
		assertSame(templateInvocation.getDefinition(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getDefinition(), templateInvocation.eGet(feature, false));
		assertTrue(templateInvocation.eIsSet(feature));

		templateInvocation.setDefinition(null);
		assertTrue(notified);
		notified = false;
		assertNull(templateInvocation.getDefinition());
		assertSame(feature.getDefaultValue(), templateInvocation.getDefinition());
		assertSame(templateInvocation.getDefinition(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getDefinition(), templateInvocation.eGet(feature, false));
		assertFalse(templateInvocation.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>before</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testBefore() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getTemplateInvocation_Before();
		TemplateInvocation templateInvocation = MtlFactory.eINSTANCE.createTemplateInvocation();
		templateInvocation.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.OCLExpression beforeValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplateExpression();

		assertFalse(templateInvocation.eIsSet(feature));
		assertNull(templateInvocation.getBefore());

		templateInvocation.setBefore(beforeValue);
		assertTrue(notified);
		notified = false;
		assertSame(beforeValue, templateInvocation.getBefore());
		assertSame(templateInvocation.getBefore(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getBefore(), templateInvocation.eGet(feature, false));
		assertTrue(templateInvocation.eIsSet(feature));

		templateInvocation.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(templateInvocation.getBefore());
		assertSame(templateInvocation.getBefore(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getBefore(), templateInvocation.eGet(feature, false));
		assertFalse(templateInvocation.eIsSet(feature));

		templateInvocation.setBefore(beforeValue);
		assertTrue(notified);
		notified = false;
		assertSame(beforeValue, templateInvocation.getBefore());
		assertSame(templateInvocation.getBefore(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getBefore(), templateInvocation.eGet(feature, false));
		assertTrue(templateInvocation.eIsSet(feature));

		templateInvocation.eSet(feature, beforeValue);
		assertTrue(notified);
		notified = false;
		assertSame(beforeValue, templateInvocation.getBefore());
		assertSame(templateInvocation.getBefore(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getBefore(), templateInvocation.eGet(feature, false));
		assertTrue(templateInvocation.eIsSet(feature));

		templateInvocation.setBefore(null);
		assertTrue(notified);
		notified = false;
		assertNull(templateInvocation.getBefore());
		assertSame(feature.getDefaultValue(), templateInvocation.getBefore());
		assertSame(templateInvocation.getBefore(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getBefore(), templateInvocation.eGet(feature, false));
		assertFalse(templateInvocation.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>after</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testAfter() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getTemplateInvocation_After();
		TemplateInvocation templateInvocation = MtlFactory.eINSTANCE.createTemplateInvocation();
		templateInvocation.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.OCLExpression afterValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplateExpression();

		assertFalse(templateInvocation.eIsSet(feature));
		assertNull(templateInvocation.getAfter());

		templateInvocation.setAfter(afterValue);
		assertTrue(notified);
		notified = false;
		assertSame(afterValue, templateInvocation.getAfter());
		assertSame(templateInvocation.getAfter(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getAfter(), templateInvocation.eGet(feature, false));
		assertTrue(templateInvocation.eIsSet(feature));

		templateInvocation.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(templateInvocation.getAfter());
		assertSame(templateInvocation.getAfter(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getAfter(), templateInvocation.eGet(feature, false));
		assertFalse(templateInvocation.eIsSet(feature));

		templateInvocation.setAfter(afterValue);
		assertTrue(notified);
		notified = false;
		assertSame(afterValue, templateInvocation.getAfter());
		assertSame(templateInvocation.getAfter(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getAfter(), templateInvocation.eGet(feature, false));
		assertTrue(templateInvocation.eIsSet(feature));

		templateInvocation.eSet(feature, afterValue);
		assertTrue(notified);
		notified = false;
		assertSame(afterValue, templateInvocation.getAfter());
		assertSame(templateInvocation.getAfter(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getAfter(), templateInvocation.eGet(feature, false));
		assertTrue(templateInvocation.eIsSet(feature));

		templateInvocation.setAfter(null);
		assertTrue(notified);
		notified = false;
		assertNull(templateInvocation.getAfter());
		assertSame(feature.getDefaultValue(), templateInvocation.getAfter());
		assertSame(templateInvocation.getAfter(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getAfter(), templateInvocation.eGet(feature, false));
		assertFalse(templateInvocation.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>each</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testEach() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getTemplateInvocation_Each();
		TemplateInvocation templateInvocation = MtlFactory.eINSTANCE.createTemplateInvocation();
		templateInvocation.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.OCLExpression eachValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplateExpression();

		assertFalse(templateInvocation.eIsSet(feature));
		assertNull(templateInvocation.getEach());

		templateInvocation.setEach(eachValue);
		assertTrue(notified);
		notified = false;
		assertSame(eachValue, templateInvocation.getEach());
		assertSame(templateInvocation.getEach(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getEach(), templateInvocation.eGet(feature, false));
		assertTrue(templateInvocation.eIsSet(feature));

		templateInvocation.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(templateInvocation.getEach());
		assertSame(templateInvocation.getEach(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getEach(), templateInvocation.eGet(feature, false));
		assertFalse(templateInvocation.eIsSet(feature));

		templateInvocation.setEach(eachValue);
		assertTrue(notified);
		notified = false;
		assertSame(eachValue, templateInvocation.getEach());
		assertSame(templateInvocation.getEach(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getEach(), templateInvocation.eGet(feature, false));
		assertTrue(templateInvocation.eIsSet(feature));

		templateInvocation.eSet(feature, eachValue);
		assertTrue(notified);
		notified = false;
		assertSame(eachValue, templateInvocation.getEach());
		assertSame(templateInvocation.getEach(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getEach(), templateInvocation.eGet(feature, false));
		assertTrue(templateInvocation.eIsSet(feature));

		templateInvocation.setEach(null);
		assertTrue(notified);
		notified = false;
		assertNull(templateInvocation.getEach());
		assertSame(feature.getDefaultValue(), templateInvocation.getEach());
		assertSame(templateInvocation.getEach(), templateInvocation.eGet(feature));
		assertSame(templateInvocation.getEach(), templateInvocation.eGet(feature, false));
		assertFalse(templateInvocation.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_StartPosition();
		TemplateInvocation templateInvocation = MtlFactory.eINSTANCE.createTemplateInvocation();
		templateInvocation.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(templateInvocation.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)templateInvocation.getStartPosition()).intValue());

		templateInvocation.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)templateInvocation.getStartPosition()).intValue());
		assertEquals(((Integer)templateInvocation.getStartPosition()).intValue(), ((Integer)templateInvocation.eGet(feature)).intValue());
		assertTrue(templateInvocation.eIsSet(feature));

		templateInvocation.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)templateInvocation.getStartPosition()).intValue());
		assertEquals(((Integer)templateInvocation.getStartPosition()).intValue(), ((Integer)templateInvocation.eGet(feature)).intValue());
		assertFalse(templateInvocation.eIsSet(feature));

		templateInvocation.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)templateInvocation.getStartPosition()).intValue());
		assertEquals(((Integer)templateInvocation.getStartPosition()).intValue(), ((Integer)templateInvocation.eGet(feature)).intValue());
		assertTrue(templateInvocation.eIsSet(feature));

		templateInvocation.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)templateInvocation.getStartPosition()).intValue());
		assertEquals(((Integer)templateInvocation.getStartPosition()).intValue(), ((Integer)templateInvocation.eGet(feature)).intValue());
		assertFalse(templateInvocation.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_EndPosition();
		TemplateInvocation templateInvocation = MtlFactory.eINSTANCE.createTemplateInvocation();
		templateInvocation.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(templateInvocation.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)templateInvocation.getEndPosition()).intValue());

		templateInvocation.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)templateInvocation.getEndPosition()).intValue());
		assertEquals(((Integer)templateInvocation.getEndPosition()).intValue(), ((Integer)templateInvocation.eGet(feature)).intValue());
		assertTrue(templateInvocation.eIsSet(feature));

		templateInvocation.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)templateInvocation.getEndPosition()).intValue());
		assertEquals(((Integer)templateInvocation.getEndPosition()).intValue(), ((Integer)templateInvocation.eGet(feature)).intValue());
		assertFalse(templateInvocation.eIsSet(feature));

		templateInvocation.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)templateInvocation.getEndPosition()).intValue());
		assertEquals(((Integer)templateInvocation.getEndPosition()).intValue(), ((Integer)templateInvocation.eGet(feature)).intValue());
		assertTrue(templateInvocation.eIsSet(feature));

		templateInvocation.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)templateInvocation.getEndPosition()).intValue());
		assertEquals(((Integer)templateInvocation.getEndPosition()).intValue(), ((Integer)templateInvocation.eGet(feature)).intValue());
		assertFalse(templateInvocation.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>super</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testSuper() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getTemplateInvocation_Super();
		TemplateInvocation templateInvocation = MtlFactory.eINSTANCE.createTemplateInvocation();
		templateInvocation.eAdapters().add(new MockEAdapter());
		boolean superValue = getBooleanDistinctFromDefault(feature);

		assertFalse(templateInvocation.eIsSet(feature));
		assertEquals(((Boolean)feature.getDefaultValue()).booleanValue(), ((Boolean)templateInvocation.isSuper()).booleanValue());

		templateInvocation.setSuper(superValue);
		assertTrue(notified);
		notified = false;
		assertEquals(superValue, ((Boolean)templateInvocation.isSuper()).booleanValue());
		assertEquals(((Boolean)templateInvocation.isSuper()).booleanValue(), ((Boolean)templateInvocation.eGet(feature)).booleanValue());
		assertTrue(templateInvocation.eIsSet(feature));

		templateInvocation.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Boolean)feature.getDefaultValue()).booleanValue(), ((Boolean)templateInvocation.isSuper()).booleanValue());
		assertEquals(((Boolean)templateInvocation.isSuper()).booleanValue(), ((Boolean)templateInvocation.eGet(feature)).booleanValue());
		assertFalse(templateInvocation.eIsSet(feature));

		templateInvocation.eSet(feature, superValue);
		assertTrue(notified);
		notified = false;
		assertEquals(superValue, ((Boolean)templateInvocation.isSuper()).booleanValue());
		assertEquals(((Boolean)templateInvocation.isSuper()).booleanValue(), ((Boolean)templateInvocation.eGet(feature)).booleanValue());
		assertTrue(templateInvocation.eIsSet(feature));

		templateInvocation.setSuper(((Boolean)feature.getDefaultValue()).booleanValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Boolean)feature.getDefaultValue()).booleanValue(), ((Boolean)templateInvocation.isSuper()).booleanValue());
		assertEquals(((Boolean)templateInvocation.isSuper()).booleanValue(), ((Boolean)templateInvocation.eGet(feature)).booleanValue());
		assertFalse(templateInvocation.eIsSet(feature));
	}
	
}

