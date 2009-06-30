package org.eclipse.acceleo.model.tests.unit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.acceleo.model.mtl.MtlFactory;
import org.eclipse.acceleo.model.mtl.Module;

/**
 * Tests the behavior of the {@link Module} class.
 * 
 * @generated
 */
public class ModuleTest extends AbstractMtlTest {
	/**
	 * Tests the behavior of reference <code>input</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testInput() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getModule_Input();
		Module module = MtlFactory.eINSTANCE.createModule();
		module.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.model.mtl.TypedModel inputValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTypedModel();
		List<org.eclipse.acceleo.model.mtl.TypedModel> listInput = new ArrayList<org.eclipse.acceleo.model.mtl.TypedModel>(1);
		listInput.add(inputValue);

		assertFalse(module.eIsSet(feature));
		assertTrue(module.getInput().isEmpty());

		module.getInput().add(inputValue);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getInput().contains(inputValue));
		assertSame(module.getInput(), module.eGet(feature));
		assertSame(module.getInput(), module.eGet(feature, false));
		assertTrue(module.eIsSet(feature));

		module.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getInput().isEmpty());
		assertSame(module.getInput(), module.eGet(feature));
		assertSame(module.getInput(), module.eGet(feature, false));
		assertFalse(module.eIsSet(feature));

		module.eSet(feature, listInput);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getInput().contains(inputValue));
		assertSame(module.getInput(), module.eGet(feature));
		assertSame(module.getInput(), module.eGet(feature, false));
		assertTrue(module.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>extends</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testExtends() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getModule_Extends();
		Module module = MtlFactory.eINSTANCE.createModule();
		module.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.model.mtl.Module extendsValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createModule();
		List<org.eclipse.acceleo.model.mtl.Module> listExtends = new ArrayList<org.eclipse.acceleo.model.mtl.Module>(1);
		listExtends.add(extendsValue);

		assertFalse(module.eIsSet(feature));
		assertTrue(module.getExtends().isEmpty());

		module.getExtends().add(extendsValue);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getExtends().contains(extendsValue));
		assertSame(module.getExtends(), module.eGet(feature));
		assertSame(module.getExtends(), module.eGet(feature, false));
		assertTrue(module.eIsSet(feature));

		module.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getExtends().isEmpty());
		assertSame(module.getExtends(), module.eGet(feature));
		assertSame(module.getExtends(), module.eGet(feature, false));
		assertFalse(module.eIsSet(feature));

		module.eSet(feature, listExtends);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getExtends().contains(extendsValue));
		assertSame(module.getExtends(), module.eGet(feature));
		assertSame(module.getExtends(), module.eGet(feature, false));
		assertTrue(module.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>imports</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testImports() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getModule_Imports();
		Module module = MtlFactory.eINSTANCE.createModule();
		module.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.model.mtl.Module importsValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createModule();
		List<org.eclipse.acceleo.model.mtl.Module> listImports = new ArrayList<org.eclipse.acceleo.model.mtl.Module>(1);
		listImports.add(importsValue);

		assertFalse(module.eIsSet(feature));
		assertTrue(module.getImports().isEmpty());

		module.getImports().add(importsValue);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getImports().contains(importsValue));
		assertSame(module.getImports(), module.eGet(feature));
		assertSame(module.getImports(), module.eGet(feature, false));
		assertTrue(module.eIsSet(feature));

		module.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getImports().isEmpty());
		assertSame(module.getImports(), module.eGet(feature));
		assertSame(module.getImports(), module.eGet(feature, false));
		assertFalse(module.eIsSet(feature));

		module.eSet(feature, listImports);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getImports().contains(importsValue));
		assertSame(module.getImports(), module.eGet(feature));
		assertSame(module.getImports(), module.eGet(feature, false));
		assertTrue(module.eIsSet(feature));
	}

	/**
	 * Tests the behavior of reference <code>ownedModuleElement</code>'s accessors.
	 * 
	 * @generated
 	 */
	public void testOwnedModuleElement() {
		EStructuralFeature feature = org.eclipse.acceleo.model.mtl.MtlPackage.eINSTANCE.getModule_OwnedModuleElement();
		Module module = MtlFactory.eINSTANCE.createModule();
		module.eAdapters().add(new MockEAdapter());
		org.eclipse.acceleo.model.mtl.ModuleElement ownedModuleElementValue = org.eclipse.acceleo.model.mtl.MtlFactory.eINSTANCE.createTemplate();
		List<org.eclipse.acceleo.model.mtl.ModuleElement> listOwnedModuleElement = new ArrayList<org.eclipse.acceleo.model.mtl.ModuleElement>(1);
		listOwnedModuleElement.add(ownedModuleElementValue);

		assertFalse(module.eIsSet(feature));
		assertTrue(module.getOwnedModuleElement().isEmpty());

		module.getOwnedModuleElement().add(ownedModuleElementValue);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getOwnedModuleElement().contains(ownedModuleElementValue));
		assertSame(module.getOwnedModuleElement(), module.eGet(feature));
		assertSame(module.getOwnedModuleElement(), module.eGet(feature, false));
		assertTrue(module.eIsSet(feature));

		module.eUnset(feature);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getOwnedModuleElement().isEmpty());
		assertSame(module.getOwnedModuleElement(), module.eGet(feature));
		assertSame(module.getOwnedModuleElement(), module.eGet(feature, false));
		assertFalse(module.eIsSet(feature));

		module.eSet(feature, listOwnedModuleElement);
		assertTrue(notified);
		notified = false;
		assertTrue(module.getOwnedModuleElement().contains(ownedModuleElementValue));
		assertSame(module.getOwnedModuleElement(), module.eGet(feature));
		assertSame(module.getOwnedModuleElement(), module.eGet(feature, false));
		assertTrue(module.eIsSet(feature));
	}


	
}

