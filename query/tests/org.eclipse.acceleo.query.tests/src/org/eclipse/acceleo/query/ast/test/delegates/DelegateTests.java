/*******************************************************************************
 * Copyright (c) 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ast.test.delegates;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.delegates.AQLInvocationDelegateFactory;
import org.eclipse.acceleo.query.delegates.AQLSettingDelegateFactory;
import org.eclipse.acceleo.query.delegates.AQLValidationDelegate;
import org.eclipse.acceleo.query.delegates.DelegateUtils;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class DelegateTests {

	private static EPackage ePackage;

	private static EClass eClass;

	private static EAttribute eAttribute1;

	private static EAttribute eAttribute2;

	private static EReference eReference;

	private static EOperation eOperation;

	private static EClass eClassParsingError;

	private static EAttribute eAttribute1ParsingError;

	private static EAttribute eAttribute2ParsingError;

	private static EReference eReferenceParsingError;

	private static EOperation eOperationParsingError;

	private static EClass eClassEvaluationError;

	private static EAttribute eAttribute1EvaluationError;

	private static EAttribute eAttribute2EvaluationError;

	private static EReference eReferenceEvaluationError;

	private static EOperation eOperationEvaluationError;

	@BeforeClass
	public static void beforeClass() {
		// delegates registration
		EOperation.Internal.InvocationDelegate.Factory.Registry.INSTANCE.put(AstPackage.eNS_URI,
				new AQLInvocationDelegateFactory());
		EStructuralFeature.Internal.SettingDelegate.Factory.Registry.INSTANCE.put(AstPackage.eNS_URI,
				new AQLSettingDelegateFactory());
		EValidator.ValidationDelegate.Registry.INSTANCE.put(AstPackage.eNS_URI, new AQLValidationDelegate());

		// EPackage construction
		ePackage = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePackage.setName("testEPackage");
		ePackage.setNsPrefix("test");
		ePackage.setNsURI("test");

		eClass = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eClass.setName("TestEClass");
		ePackage.getEClassifiers().add(eClass);

		eAttribute1 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		eAttribute1.setName("testAttribute1");
		eAttribute1.setEType(EcorePackage.eINSTANCE.getEString());
		eClass.getEStructuralFeatures().add(eAttribute1);

		eAttribute2 = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		eAttribute2.setName("testAttribute2");
		eAttribute2.setEType(EcorePackage.eINSTANCE.getEString());
		eClass.getEStructuralFeatures().add(eAttribute2);

		eReference = EcorePackage.eINSTANCE.getEcoreFactory().createEReference();
		eReference.setName("testReference");
		eReference.setEType(eClass);
		eClass.getEStructuralFeatures().add(eReference);

		eOperation = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		eOperation.setName("testOperation");
		eOperation.setEType(EcorePackage.eINSTANCE.getEString());
		eClass.getEOperations().add(eOperation);
		final EParameter eParameter1 = EcorePackage.eINSTANCE.getEcoreFactory().createEParameter();
		eParameter1.setName("testParameter1");
		eParameter1.setEType(EcorePackage.eINSTANCE.getEString());
		eOperation.getEParameters().add(eParameter1);
		final EParameter eParameter2 = EcorePackage.eINSTANCE.getEcoreFactory().createEParameter();
		eParameter2.setName("testParameter2");
		eParameter2.setEType(EcorePackage.eINSTANCE.getEString());
		eOperation.getEParameters().add(eParameter2);

		eClassParsingError = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eClassParsingError.setName("TestEClassParsingError");
		ePackage.getEClassifiers().add(eClassParsingError);

		eAttribute1ParsingError = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		eAttribute1ParsingError.setName("testAttribute1ParsingError");
		eAttribute1ParsingError.setEType(EcorePackage.eINSTANCE.getEString());
		eClassParsingError.getEStructuralFeatures().add(eAttribute1ParsingError);

		eAttribute2ParsingError = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		eAttribute2ParsingError.setName("testAttribute2ParsingError");
		eAttribute2ParsingError.setEType(EcorePackage.eINSTANCE.getEString());
		eClassParsingError.getEStructuralFeatures().add(eAttribute2ParsingError);

		eReferenceParsingError = EcorePackage.eINSTANCE.getEcoreFactory().createEReference();
		eReferenceParsingError.setName("testReferenceParsingError");
		eReferenceParsingError.setEType(eClass);
		eClassParsingError.getEStructuralFeatures().add(eReferenceParsingError);

		eOperationParsingError = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		eOperationParsingError.setName("testOperationParsingError");
		eOperationParsingError.setEType(EcorePackage.eINSTANCE.getEString());
		eClassParsingError.getEOperations().add(eOperationParsingError);
		final EParameter eParameter1ParsingError = EcorePackage.eINSTANCE.getEcoreFactory()
				.createEParameter();
		eParameter1ParsingError.setName("testParameter1ParsingError");
		eParameter1ParsingError.setEType(EcorePackage.eINSTANCE.getEString());
		eOperationParsingError.getEParameters().add(eParameter1ParsingError);
		final EParameter eParameter2ParsingError = EcorePackage.eINSTANCE.getEcoreFactory()
				.createEParameter();
		eParameter2ParsingError.setName("testParameter2ParsingError");
		eParameter2ParsingError.setEType(EcorePackage.eINSTANCE.getEString());
		eOperationParsingError.getEParameters().add(eParameter2ParsingError);

		eClassEvaluationError = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		eClassEvaluationError.setName("TestEClassEvaluationError");
		ePackage.getEClassifiers().add(eClassEvaluationError);

		eAttribute1EvaluationError = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		eAttribute1EvaluationError.setName("testAttribute1EvaluationError");
		eAttribute1EvaluationError.setEType(EcorePackage.eINSTANCE.getEString());
		eClassEvaluationError.getEStructuralFeatures().add(eAttribute1EvaluationError);

		eAttribute2EvaluationError = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();
		eAttribute2EvaluationError.setName("testAttribute2EvaluationError");
		eAttribute2EvaluationError.setEType(EcorePackage.eINSTANCE.getEString());
		eClassEvaluationError.getEStructuralFeatures().add(eAttribute2EvaluationError);

		eReferenceEvaluationError = EcorePackage.eINSTANCE.getEcoreFactory().createEReference();
		eReferenceEvaluationError.setName("testReferenceEvaluationError");
		eReferenceEvaluationError.setEType(eClass);
		eClassEvaluationError.getEStructuralFeatures().add(eReferenceEvaluationError);

		eOperationEvaluationError = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();
		eOperationEvaluationError.setName("testOperationEvaluationError");
		eOperationEvaluationError.setEType(EcorePackage.eINSTANCE.getEString());
		eClassEvaluationError.getEOperations().add(eOperationEvaluationError);
		final EParameter eParameter1EvaluationError = EcorePackage.eINSTANCE.getEcoreFactory()
				.createEParameter();
		eParameter1EvaluationError.setName("testParameter1EvaluationError");
		eParameter1EvaluationError.setEType(EcorePackage.eINSTANCE.getEString());
		eOperationEvaluationError.getEParameters().add(eParameter1EvaluationError);
		final EParameter eParameter2EvaluationError = EcorePackage.eINSTANCE.getEcoreFactory()
				.createEParameter();
		eParameter2EvaluationError.setName("testParameter2EvaluationError");
		eParameter2EvaluationError.setEType(EcorePackage.eINSTANCE.getEString());
		eOperationEvaluationError.getEParameters().add(eParameter2EvaluationError);

		// set delegates annotations
		DelegateUtils.setInvocationDelegates(ePackage);
		DelegateUtils.setSettingDelegates(ePackage);
		DelegateUtils.setValidationDelegates(ePackage);

		DelegateUtils.setDerivation(eReference, "self.eClass.name");
		DelegateUtils.setDerivation(eReferenceParsingError, "self.");
		DelegateUtils.setDerivation(eReferenceEvaluationError, "notAVariable");

		DelegateUtils.setConstraint(eClass, "AttributeIsShort", "self.testAttribute1.size() < 5");
		DelegateUtils.setConstraint(eClassParsingError, "AttributeParsingError", "5 < ");
		DelegateUtils.setConstraint(eClassEvaluationError, "AttributeParsingError", "notAVariable");

		DelegateUtils.setDerivation(eReference, "self");
		DelegateUtils.setDerivation(eReferenceParsingError, "5 < ");
		DelegateUtils.setDerivation(eReferenceEvaluationError, "notAVariable");

		DelegateUtils.setBody(eOperation, "testParameter1 + testParameter2");
		DelegateUtils.setBody(eOperationParsingError, "testParameter1 +");
		DelegateUtils.setBody(eOperationEvaluationError, "notAVariable");
	}

	@Test
	public void derivation() {
		final EObject eObj = EcoreUtil.create(eClass);
		final Object value = eObj.eGet(eReference);

		assertEquals(eObj, value);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void derivationParsingError() {
		final EObject eObj = EcoreUtil.create(eClassParsingError);
		final Object value = eObj.eGet(eReferenceParsingError);

		assertEquals(eObj, value);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void derivationEvaluationError() {
		final EObject eObj = EcoreUtil.create(eClassEvaluationError);
		eObj.eGet(eReferenceEvaluationError);
	}

	@Test
	public void constraintOK() {
		final EObject eObj = EcoreUtil.create(eClass);

		eObj.eSet(eAttribute1, "abc");

		Diagnostic diagnostic = Diagnostician.INSTANCE.validate(eObj);

		assertEquals(Diagnostic.OK, diagnostic.getSeverity());
	}

	@Test
	public void constraintKO() {
		final EObject eObj = EcoreUtil.create(eClass);

		eObj.eSet(eAttribute1, "abcdefgh");

		Diagnostic diagnostic = Diagnostician.INSTANCE.validate(eObj);

		assertEquals(Diagnostic.ERROR, diagnostic.getSeverity());
		assertEquals(1, diagnostic.getChildren().size());
		assertTrue(diagnostic.getChildren().get(0).getMessage().startsWith(
				"The 'AttributeIsShort' constraint is violated on"));
	}

	@Test
	public void constraintParsingError() {
		final EObject eObj = EcoreUtil.create(eClassParsingError);

		eObj.eSet(eAttribute1ParsingError, "abc");

		Diagnostic diagnostic = Diagnostician.INSTANCE.validate(eObj);

		assertEquals(Diagnostic.ERROR, diagnostic.getSeverity());
		assertEquals(1, diagnostic.getChildren().size());
		assertTrue(diagnostic.getChildren().get(0).getMessage().endsWith("missing expression"));
	}

	@Test
	public void constraintEvaluationError() {
		final EObject eObj = EcoreUtil.create(eClassEvaluationError);

		eObj.eSet(eAttribute1EvaluationError, "abc");

		Diagnostic diagnostic = Diagnostician.INSTANCE.validate(eObj);

		assertEquals(Diagnostic.ERROR, diagnostic.getSeverity());
		assertEquals(1, diagnostic.getChildren().size());
		assertTrue(diagnostic.getChildren().get(0).getMessage().endsWith(
				"Couldn't find the 'notAVariable' variable"));
	}

	@Test
	public void body() throws InvocationTargetException {
		final EObject eObj = EcoreUtil.create(eClass);
		final EList<Object> arguments = new BasicEList<Object>();
		arguments.add("abc");
		arguments.add("def");
		final Object value = eObj.eInvoke(eOperation, arguments);

		assertEquals("abcdef", value);
	}

	@Test(expected = java.lang.reflect.InvocationTargetException.class)
	public void bodyParsingError() throws InvocationTargetException {
		final EObject eObj = EcoreUtil.create(eClassParsingError);
		final EList<Object> arguments = new BasicEList<Object>();
		arguments.add("abc");
		arguments.add("def");
		eObj.eInvoke(eOperationParsingError, arguments);
	}

	@Test(expected = java.lang.reflect.InvocationTargetException.class)
	public void bodyEvaluationError() throws InvocationTargetException {
		final EObject eObj = EcoreUtil.create(eClassEvaluationError);
		final EList<Object> arguments = new BasicEList<Object>();
		arguments.add("abc");
		arguments.add("def");
		eObj.eInvoke(eOperationEvaluationError, arguments);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void bodyEvaluationWrongNumberOfArguments() throws InvocationTargetException {
		final EObject eObj = EcoreUtil.create(eClassEvaluationError);
		final EList<Object> arguments = new BasicEList<Object>();
		arguments.add("abc");
		arguments.add("def");
		arguments.add("def");
		eObj.eInvoke(eOperationEvaluationError, arguments);
	}

}
