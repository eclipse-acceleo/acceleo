/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.tests.delegates;

import org.eclipse.acceleo.query.delegates.AQLInvocationDelegateFactory;
import org.eclipse.acceleo.query.delegates.AQLQueryDelegateFactory;
import org.eclipse.acceleo.query.delegates.AQLSettingDelegateFactory;
import org.eclipse.acceleo.query.delegates.AQLValidationDelegate;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.QueryDelegate;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DelegatesRegistrationTests {

	@Test
	public void validationDelegateRegistration() {
		assertTrue(EValidator.ValidationDelegate.Registry.INSTANCE
				.getValidationDelegate("http://www.eclipse.org/acceleo/query/1.0") instanceof AQLValidationDelegate);
	}

	@Test
	public void settingDelegateFactoryRegistration() {
		assertTrue(EStructuralFeature.Internal.SettingDelegate.Factory.Registry.INSTANCE
				.getFactory("http://www.eclipse.org/acceleo/query/1.0") instanceof AQLSettingDelegateFactory);
	}

	@Test
	public void invocationDelegateFactoryRegistration() {
		assertTrue(EOperation.Internal.InvocationDelegate.Factory.Registry.INSTANCE
				.getFactory("http://www.eclipse.org/acceleo/query/1.0") instanceof AQLInvocationDelegateFactory);
	}

	@Test
	public void queryDelegateFactoryRegistration() {
		assertTrue(QueryDelegate.Factory.Registry.INSTANCE
				.getFactory("http://www.eclipse.org/acceleo/query/1.0") instanceof AQLQueryDelegateFactory);
	}

}
