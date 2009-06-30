package org.eclipse.acceleo.model.tests.unit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.TypedModel;

/**
 * Tests the behavior of the {@link TypedModel} class.
 * 
 * @generated
 */
public class TypedModelTest extends AbstractMtlTest {
	/**
	 * Tests the behavior of reference <code>takesTypesFrom</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testTakesTypesFrom() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getTypedModel_TakesTypesFrom();
		TypedModel typedModel = MtlFactory.eINSTANCE.createTypedModel();
		typedModel.eAdapters().add(new MockEAdapter());
		org.eclipse.emf.ecore.EPackage takesTypesFromValue = org.eclipse.emf.ecore.EcoreFactory.eINSTANCE.createEPackage();
		List<org.eclipse.emf.ecore.EPackage> listTakesTypesFrom = new ArrayList<org.eclipse.emf.ecore.EPackage>(1);
		listTakesTypesFrom.add(takesTypesFromValue);

		assertFalse(typedModel.eIsSet(feature));
		assertTrue(typedModel.getTakesTypesFrom().isEmpty());

		typedModel.getTakesTypesFrom().add(takesTypesFromValue);
		assertTrue(notified);
		notified = false;
		assertTrue(typedModel.getTakesTypesFrom().contains(takesTypesFromValue));
		assertSame(typedModel.getTakesTypesFrom(), typedModel.eGet(feature));
		assertSame(typedModel.getTakesTypesFrom(), typedModel.eGet(feature, false));
		assertTrue(typedModel.eIsSet(feature));

		typedModel.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(typedModel.getTakesTypesFrom().isEmpty());
		assertSame(typedModel.getTakesTypesFrom(), typedModel.eGet(feature));
		assertSame(typedModel.getTakesTypesFrom(), typedModel.eGet(feature, false));
		assertFalse(typedModel.eIsSet(feature));

		typedModel.eSet(feature, listTakesTypesFrom);
		assertTrue(notified);
		notified = false;
		assertTrue(typedModel.getTakesTypesFrom().contains(takesTypesFromValue));
		assertSame(typedModel.getTakesTypesFrom(), typedModel.eGet(feature));
		assertSame(typedModel.getTakesTypesFrom(), typedModel.eGet(feature, false));
		assertTrue(typedModel.eIsSet(feature));
	}


	
}

