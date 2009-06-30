package org.eclipse.acceleo.model.tests.unit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.Query;

/**
 * Tests the behavior of the {@link Query} class.
 * 
 * @generated
 */
public class QueryTest extends AbstractMtlTest {
	/**
	 * Tests the behavior of reference <code>parameter</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testParameter() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getQuery_Parameter();
		Query query = MtlFactory.eINSTANCE.createQuery();
		query.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.Variable parameterValue = org.eclipse.ocl.ecore.EcoreFactory.eINSTANCE.createVariable();
		List<org.eclipse.ocl.ecore.Variable> listParameter = new ArrayList<org.eclipse.ocl.ecore.Variable>(1);
		listParameter.add(parameterValue);

		assertFalse(query.eIsSet(feature));
		assertTrue(query.getParameter().isEmpty());

		query.getParameter().add(parameterValue);
		assertTrue(notified);
		notified = false;
		assertTrue(query.getParameter().contains(parameterValue));
		assertSame(query.getParameter(), query.eGet(feature));
		assertSame(query.getParameter(), query.eGet(feature, false));
		assertTrue(query.eIsSet(feature));

		query.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(query.getParameter().isEmpty());
		assertSame(query.getParameter(), query.eGet(feature));
		assertSame(query.getParameter(), query.eGet(feature, false));
		assertFalse(query.eIsSet(feature));

		query.eSet(feature, listParameter);
		assertTrue(notified);
		notified = false;
		assertTrue(query.getParameter().contains(parameterValue));
		assertSame(query.getParameter(), query.eGet(feature));
		assertSame(query.getParameter(), query.eGet(feature, false));
		assertTrue(query.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>expression</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testExpression() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getQuery_Expression();
		Query query = MtlFactory.eINSTANCE.createQuery();
		query.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.OCLExpression expressionValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplateExpression();

		assertFalse(query.eIsSet(feature));
		assertNull(query.getExpression());

		query.setExpression(expressionValue);
		assertTrue(notified);
		notified = false;
		assertSame(expressionValue, query.getExpression());
		assertSame(query.getExpression(), query.eGet(feature));
		assertSame(query.getExpression(), query.eGet(feature, false));
		assertTrue(query.eIsSet(feature));

		query.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(query.getExpression());
		assertSame(query.getExpression(), query.eGet(feature));
		assertSame(query.getExpression(), query.eGet(feature, false));
		assertFalse(query.eIsSet(feature));

		query.setExpression(expressionValue);
		assertTrue(notified);
		notified = false;
		assertSame(expressionValue, query.getExpression());
		assertSame(query.getExpression(), query.eGet(feature));
		assertSame(query.getExpression(), query.eGet(feature, false));
		assertTrue(query.eIsSet(feature));

		query.eSet(feature, expressionValue);
		assertTrue(notified);
		notified = false;
		assertSame(expressionValue, query.getExpression());
		assertSame(query.getExpression(), query.eGet(feature));
		assertSame(query.getExpression(), query.eGet(feature, false));
		assertTrue(query.eIsSet(feature));

		query.setExpression(null);
		assertTrue(notified);
		notified = false;
		assertNull(query.getExpression());
		assertSame(feature.getDefaultValue(), query.getExpression());
		assertSame(query.getExpression(), query.eGet(feature));
		assertSame(query.getExpression(), query.eGet(feature, false));
		assertFalse(query.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>type</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testType() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getQuery_Type();
		Query query = MtlFactory.eINSTANCE.createQuery();
		query.eAdapters().add(new MockEAdapter());
		org.eclipse.emf.ecore.EClassifier typeValue = org.eclipse.emf.ecore.EcoreFactory.eINSTANCE.createEClass();

		assertFalse(query.eIsSet(feature));
		assertNull(query.getType());

		query.setType(typeValue);
		assertTrue(notified);
		notified = false;
		assertSame(typeValue, query.getType());
		assertSame(query.getType(), query.eGet(feature));
		assertSame(query.getType(), query.eGet(feature, false));
		assertTrue(query.eIsSet(feature));

		query.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(query.getType());
		assertSame(query.getType(), query.eGet(feature));
		assertSame(query.getType(), query.eGet(feature, false));
		assertFalse(query.eIsSet(feature));

		query.setType(typeValue);
		assertTrue(notified);
		notified = false;
		assertSame(typeValue, query.getType());
		assertSame(query.getType(), query.eGet(feature));
		assertSame(query.getType(), query.eGet(feature, false));
		assertTrue(query.eIsSet(feature));

		query.eSet(feature, typeValue);
		assertTrue(notified);
		notified = false;
		assertSame(typeValue, query.getType());
		assertSame(query.getType(), query.eGet(feature));
		assertSame(query.getType(), query.eGet(feature, false));
		assertTrue(query.eIsSet(feature));

		query.setType(null);
		assertTrue(notified);
		notified = false;
		assertNull(query.getType());
		assertSame(feature.getDefaultValue(), query.getType());
		assertSame(query.getType(), query.eGet(feature));
		assertSame(query.getType(), query.eGet(feature, false));
		assertFalse(query.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_StartPosition();
		Query query = MtlFactory.eINSTANCE.createQuery();
		query.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(query.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)query.getStartPosition()).intValue());

		query.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)query.getStartPosition()).intValue());
		assertEquals(((Integer)query.getStartPosition()).intValue(), ((Integer)query.eGet(feature)).intValue());
		assertTrue(query.eIsSet(feature));

		query.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)query.getStartPosition()).intValue());
		assertEquals(((Integer)query.getStartPosition()).intValue(), ((Integer)query.eGet(feature)).intValue());
		assertFalse(query.eIsSet(feature));

		query.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)query.getStartPosition()).intValue());
		assertEquals(((Integer)query.getStartPosition()).intValue(), ((Integer)query.eGet(feature)).intValue());
		assertTrue(query.eIsSet(feature));

		query.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)query.getStartPosition()).intValue());
		assertEquals(((Integer)query.getStartPosition()).intValue(), ((Integer)query.eGet(feature)).intValue());
		assertFalse(query.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_EndPosition();
		Query query = MtlFactory.eINSTANCE.createQuery();
		query.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(query.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)query.getEndPosition()).intValue());

		query.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)query.getEndPosition()).intValue());
		assertEquals(((Integer)query.getEndPosition()).intValue(), ((Integer)query.eGet(feature)).intValue());
		assertTrue(query.eIsSet(feature));

		query.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)query.getEndPosition()).intValue());
		assertEquals(((Integer)query.getEndPosition()).intValue(), ((Integer)query.eGet(feature)).intValue());
		assertFalse(query.eIsSet(feature));

		query.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)query.getEndPosition()).intValue());
		assertEquals(((Integer)query.getEndPosition()).intValue(), ((Integer)query.eGet(feature)).intValue());
		assertTrue(query.eIsSet(feature));

		query.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)query.getEndPosition()).intValue());
		assertEquals(((Integer)query.getEndPosition()).intValue(), ((Integer)query.eGet(feature)).intValue());
		assertFalse(query.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>visibility</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testVisibility() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getModuleElement_Visibility();
		Query query = MtlFactory.eINSTANCE.createQuery();
		query.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.model.mtl.VisibilityKind visibilityValue = (org.eclipse.acceleo.model.mtl.VisibilityKind)feature.getDefaultValue();
		for (org.eclipse.acceleo.model.mtl.VisibilityKind aVisibilityKind : org.eclipse.acceleo.model.mtl.VisibilityKind.VALUES) {
			if (visibilityValue.getValue() != aVisibilityKind.getValue()) {
				visibilityValue = aVisibilityKind;
				break;
			}
		}

		assertFalse(query.eIsSet(feature));
		assertEquals(feature.getDefaultValue(), query.getVisibility());

		query.setVisibility(visibilityValue);
		assertTrue(notified);
		notified = false;
		assertEquals(visibilityValue, query.getVisibility());
		assertEquals(query.getVisibility(), query.eGet(feature));
		assertTrue(query.eIsSet(feature));

		query.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), query.getVisibility());
		assertEquals(query.getVisibility(), query.eGet(feature));
		assertFalse(query.eIsSet(feature));

		query.eSet(feature, visibilityValue);
		assertTrue(notified);
		notified = false;
		assertEquals(visibilityValue, query.getVisibility());
		assertEquals(query.getVisibility(), query.eGet(feature));
		assertTrue(query.eIsSet(feature));

		query.setVisibility(null);
		assertTrue(notified);
		notified = false;
		assertEquals(feature.getDefaultValue(), query.getVisibility());
		assertEquals(query.getVisibility(), query.eGet(feature));
		assertFalse(query.eIsSet(feature));
	}
	
}

