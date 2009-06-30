package org.eclipse.acceleo.model.tests.unit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.QueryInvocation;

/**
 * Tests the behavior of the {@link QueryInvocation} class.
 * 
 * @generated
 */
public class QueryInvocationTest extends AbstractMtlTest {
	/**
	 * Tests the behavior of reference <code>argument</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testArgument() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getQueryInvocation_Argument();
		QueryInvocation queryInvocation = MtlFactory.eINSTANCE.createQueryInvocation();
		queryInvocation.eAdapters().add(new MockEAdapter());
		org.eclipse.ocl.ecore.OCLExpression argumentValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplateExpression();
		List<org.eclipse.ocl.ecore.OCLExpression> listArgument = new ArrayList<org.eclipse.ocl.ecore.OCLExpression>(1);
		listArgument.add(argumentValue);

		assertFalse(queryInvocation.eIsSet(feature));
		assertTrue(queryInvocation.getArgument().isEmpty());

		queryInvocation.getArgument().add(argumentValue);
		assertTrue(notified);
		notified = false;
		assertTrue(queryInvocation.getArgument().contains(argumentValue));
		assertSame(queryInvocation.getArgument(), queryInvocation.eGet(feature));
		assertSame(queryInvocation.getArgument(), queryInvocation.eGet(feature, false));
		assertTrue(queryInvocation.eIsSet(feature));

		queryInvocation.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(queryInvocation.getArgument().isEmpty());
		assertSame(queryInvocation.getArgument(), queryInvocation.eGet(feature));
		assertSame(queryInvocation.getArgument(), queryInvocation.eGet(feature, false));
		assertFalse(queryInvocation.eIsSet(feature));

		queryInvocation.eSet(feature, listArgument);
		assertTrue(notified);
		notified = false;
		assertTrue(queryInvocation.getArgument().contains(argumentValue));
		assertSame(queryInvocation.getArgument(), queryInvocation.eGet(feature));
		assertSame(queryInvocation.getArgument(), queryInvocation.eGet(feature, false));
		assertTrue(queryInvocation.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>definition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testDefinition() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getQueryInvocation_Definition();
		QueryInvocation queryInvocation = MtlFactory.eINSTANCE.createQueryInvocation();
		queryInvocation.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.model.mtl.Query definitionValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createQuery();

		assertFalse(queryInvocation.eIsSet(feature));
		assertNull(queryInvocation.getDefinition());

		queryInvocation.setDefinition(definitionValue);
		assertTrue(notified);
		notified = false;
		assertSame(definitionValue, queryInvocation.getDefinition());
		assertSame(queryInvocation.getDefinition(), queryInvocation.eGet(feature));
		assertSame(queryInvocation.getDefinition(), queryInvocation.eGet(feature, false));
		assertTrue(queryInvocation.eIsSet(feature));

		queryInvocation.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertNull(queryInvocation.getDefinition());
		assertSame(queryInvocation.getDefinition(), queryInvocation.eGet(feature));
		assertSame(queryInvocation.getDefinition(), queryInvocation.eGet(feature, false));
		assertFalse(queryInvocation.eIsSet(feature));

		queryInvocation.setDefinition(definitionValue);
		assertTrue(notified);
		notified = false;
		assertSame(definitionValue, queryInvocation.getDefinition());
		assertSame(queryInvocation.getDefinition(), queryInvocation.eGet(feature));
		assertSame(queryInvocation.getDefinition(), queryInvocation.eGet(feature, false));
		assertTrue(queryInvocation.eIsSet(feature));

		queryInvocation.eSet(feature, definitionValue);
		assertTrue(notified);
		notified = false;
		assertSame(definitionValue, queryInvocation.getDefinition());
		assertSame(queryInvocation.getDefinition(), queryInvocation.eGet(feature));
		assertSame(queryInvocation.getDefinition(), queryInvocation.eGet(feature, false));
		assertTrue(queryInvocation.eIsSet(feature));

		queryInvocation.setDefinition(null);
		assertTrue(notified);
		notified = false;
		assertNull(queryInvocation.getDefinition());
		assertSame(feature.getDefaultValue(), queryInvocation.getDefinition());
		assertSame(queryInvocation.getDefinition(), queryInvocation.eGet(feature));
		assertSame(queryInvocation.getDefinition(), queryInvocation.eGet(feature, false));
		assertFalse(queryInvocation.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>startPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testStartPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_StartPosition();
		QueryInvocation queryInvocation = MtlFactory.eINSTANCE.createQueryInvocation();
		queryInvocation.eAdapters().add(new MockEAdapter());
		int startPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(queryInvocation.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)queryInvocation.getStartPosition()).intValue());

		queryInvocation.setStartPosition(startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)queryInvocation.getStartPosition()).intValue());
		assertEquals(((Integer)queryInvocation.getStartPosition()).intValue(), ((Integer)queryInvocation.eGet(feature)).intValue());
		assertTrue(queryInvocation.eIsSet(feature));

		queryInvocation.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)queryInvocation.getStartPosition()).intValue());
		assertEquals(((Integer)queryInvocation.getStartPosition()).intValue(), ((Integer)queryInvocation.eGet(feature)).intValue());
		assertFalse(queryInvocation.eIsSet(feature));

		queryInvocation.eSet(feature, startPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(startPositionValue, ((Integer)queryInvocation.getStartPosition()).intValue());
		assertEquals(((Integer)queryInvocation.getStartPosition()).intValue(), ((Integer)queryInvocation.eGet(feature)).intValue());
		assertTrue(queryInvocation.eIsSet(feature));

		queryInvocation.setStartPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)queryInvocation.getStartPosition()).intValue());
		assertEquals(((Integer)queryInvocation.getStartPosition()).intValue(), ((Integer)queryInvocation.eGet(feature)).intValue());
		assertFalse(queryInvocation.eIsSet(feature));
	}

	/**
	 * Tests the behavior of attribute <code>endPosition</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testEndPosition() {
		EStructuralFeature feature = org.eclipse.ocl.utilities.UtilitiesPackage.eINSTANCE.getASTNode_EndPosition();
		QueryInvocation queryInvocation = MtlFactory.eINSTANCE.createQueryInvocation();
		queryInvocation.eAdapters().add(new MockEAdapter());
		int endPositionValue = getIntDistinctFromDefault(feature);

		assertFalse(queryInvocation.eIsSet(feature));
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)queryInvocation.getEndPosition()).intValue());

		queryInvocation.setEndPosition(endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)queryInvocation.getEndPosition()).intValue());
		assertEquals(((Integer)queryInvocation.getEndPosition()).intValue(), ((Integer)queryInvocation.eGet(feature)).intValue());
		assertTrue(queryInvocation.eIsSet(feature));

		queryInvocation.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)queryInvocation.getEndPosition()).intValue());
		assertEquals(((Integer)queryInvocation.getEndPosition()).intValue(), ((Integer)queryInvocation.eGet(feature)).intValue());
		assertFalse(queryInvocation.eIsSet(feature));

		queryInvocation.eSet(feature, endPositionValue);
		assertTrue(notified);
		notified = false;
		assertEquals(endPositionValue, ((Integer)queryInvocation.getEndPosition()).intValue());
		assertEquals(((Integer)queryInvocation.getEndPosition()).intValue(), ((Integer)queryInvocation.eGet(feature)).intValue());
		assertTrue(queryInvocation.eIsSet(feature));

		queryInvocation.setEndPosition(((Integer)feature.getDefaultValue()).intValue());
		assertTrue(notified);
		notified = false;
		assertEquals(((Integer)feature.getDefaultValue()).intValue(), ((Integer)queryInvocation.getEndPosition()).intValue());
		assertEquals(((Integer)queryInvocation.getEndPosition()).intValue(), ((Integer)queryInvocation.eGet(feature)).intValue());
		assertFalse(queryInvocation.eIsSet(feature));
	}
	
}

