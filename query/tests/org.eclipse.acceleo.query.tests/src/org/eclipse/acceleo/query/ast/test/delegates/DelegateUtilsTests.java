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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.delegates.DelegateUtils;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests {@link DelegateUtils}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class DelegateUtilsTests {

	@Test(expected = java.lang.NullPointerException.class)
	public void setSettingDelegatesNull() {
		DelegateUtils.setSettingDelegates(null);
	}

	@Test
	public void setSettingDelegatesNotExisting() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();

		EcoreUtil.setSettingDelegates(ePkg, new ArrayList<String>(Arrays.asList("uri")));

		DelegateUtils.setSettingDelegates(ePkg);

		final List<String> delegates = EcoreUtil.getSettingDelegates(ePkg);

		assertEquals(2, delegates.size());
		assertEquals("uri", delegates.get(0));
		assertEquals(AstPackage.eNS_URI, delegates.get(1));
	}

	@Test
	public void setSettingDelegatesExisting() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();

		EcoreUtil.setSettingDelegates(ePkg, new ArrayList<String>(Arrays.asList("uri", AstPackage.eNS_URI)));

		DelegateUtils.setSettingDelegates(ePkg);

		final List<String> delegates = EcoreUtil.getSettingDelegates(ePkg);

		assertEquals(2, delegates.size());
		assertEquals("uri", delegates.get(0));
		assertEquals(AstPackage.eNS_URI, delegates.get(1));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void setInvocationDelegatesNull() {
		DelegateUtils.setInvocationDelegates(null);
	}

	@Test
	public void setInvocationDelegatesNotExisting() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();

		EcoreUtil.setInvocationDelegates(ePkg, new ArrayList<String>(Arrays.asList("uri")));

		DelegateUtils.setInvocationDelegates(ePkg);

		final List<String> delegates = EcoreUtil.getInvocationDelegates(ePkg);

		assertEquals(2, delegates.size());
		assertEquals("uri", delegates.get(0));
		assertEquals(AstPackage.eNS_URI, delegates.get(1));
	}

	@Test
	public void setInvocationDelegatesExisting() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();

		EcoreUtil.setInvocationDelegates(ePkg, new ArrayList<String>(Arrays.asList("uri",
				AstPackage.eNS_URI)));

		DelegateUtils.setInvocationDelegates(ePkg);

		final List<String> delegates = EcoreUtil.getInvocationDelegates(ePkg);

		assertEquals(2, delegates.size());
		assertEquals("uri", delegates.get(0));
		assertEquals(AstPackage.eNS_URI, delegates.get(1));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void setValidationDelegatesNull() {
		DelegateUtils.setValidationDelegates(null);
	}

	@Test
	public void setValidationDelegatesNotExisting() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();

		EcoreUtil.setValidationDelegates(ePkg, new ArrayList<String>(Arrays.asList("uri")));

		DelegateUtils.setValidationDelegates(ePkg);

		final List<String> delegates = EcoreUtil.getValidationDelegates(ePkg);

		assertEquals(2, delegates.size());
		assertEquals("uri", delegates.get(0));
		assertEquals(AstPackage.eNS_URI, delegates.get(1));
	}

	@Test
	public void setValidationDelegatesExisting() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();

		EcoreUtil.setValidationDelegates(ePkg, new ArrayList<String>(Arrays.asList("uri",
				AstPackage.eNS_URI)));

		DelegateUtils.setValidationDelegates(ePkg);

		final List<String> delegates = EcoreUtil.getValidationDelegates(ePkg);

		assertEquals(2, delegates.size());
		assertEquals("uri", delegates.get(0));
		assertEquals(AstPackage.eNS_URI, delegates.get(1));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void getConstraintNull() {
		DelegateUtils.getConstraint(null, "someConstraint");
	}

	@Test
	public void getConstraintNotSetted() {
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();

		assertEquals(null, DelegateUtils.getConstraint(eCls, "someConstraint"));
	}

	@Test
	public void getConstraintSetted() {
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();

		DelegateUtils.setConstraint(eCls, "someConstraint", "self");

		assertEquals("self", DelegateUtils.getConstraint(eCls, "someConstraint"));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void setConstraintNull() {
		DelegateUtils.setConstraint(null, "someConstraint", "");
	}

	@Test
	public void setConstraintNotExisting() {
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();

		EcoreUtil.setConstraints(eCls, new ArrayList<String>(Arrays.asList("someOtherConstraint")));

		DelegateUtils.setConstraint(eCls, "someConstraint", "true");

		final List<String> constraints = EcoreUtil.getConstraints(eCls);

		assertEquals(2, constraints.size());
		assertEquals("someOtherConstraint", constraints.get(0));
		assertEquals("someConstraint", constraints.get(1));

		final String expression = EcoreUtil.getAnnotation(eCls, AstPackage.eNS_URI, "someConstraint");

		assertEquals("true", expression);
	}

	@Test
	public void setConstraintExisting() {
		final EClass eCls = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();

		EcoreUtil.setConstraints(eCls, new ArrayList<String>(Arrays.asList("someConstraint",
				"someOtherConstraint")));

		DelegateUtils.setConstraint(eCls, "someConstraint", "true");

		final List<String> constraints = EcoreUtil.getConstraints(eCls);

		assertEquals(2, constraints.size());
		assertEquals("someConstraint", constraints.get(0));
		assertEquals("someOtherConstraint", constraints.get(1));

		final String expression = EcoreUtil.getAnnotation(eCls, AstPackage.eNS_URI, "someConstraint");

		assertEquals("true", expression);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void getBodyNull() {
		DelegateUtils.getBody(null);
	}

	@Test
	public void getBodyNotSetted() {
		final EOperation eOperation = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();

		assertEquals(null, DelegateUtils.getBody(eOperation));
	}

	@Test
	public void getBodySetted() {
		final EOperation eOperation = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();

		DelegateUtils.setBody(eOperation, "self");

		assertEquals("self", DelegateUtils.getBody(eOperation));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void setBodyNull() {
		DelegateUtils.setBody(null, "true");
	}

	@Test
	public void setBody() {
		final EOperation eOperation = EcorePackage.eINSTANCE.getEcoreFactory().createEOperation();

		DelegateUtils.setBody(eOperation, "true");

		final String expression = EcoreUtil.getAnnotation(eOperation, AstPackage.eNS_URI, "body");

		assertEquals("true", expression);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void getDerivationNull() {
		DelegateUtils.getDerivation(null);
	}

	@Test
	public void getDerivationNotSetted() {
		final EStructuralFeature eFeature = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();

		assertEquals(null, DelegateUtils.getDerivation(eFeature));
	}

	@Test
	public void getDerivationSetted() {
		final EStructuralFeature eFeature = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();

		DelegateUtils.setDerivation(eFeature, "self");

		assertEquals("self", DelegateUtils.getDerivation(eFeature));
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void setDerivationNull() {
		DelegateUtils.setDerivation(null, "true");
	}

	@Test
	public void setDerivation() {
		final EStructuralFeature eFeature = EcorePackage.eINSTANCE.getEcoreFactory().createEAttribute();

		DelegateUtils.setDerivation(eFeature, "true");

		final String expression = EcoreUtil.getAnnotation(eFeature, AstPackage.eNS_URI, "derivation");

		assertEquals("true", expression);
	}

}
