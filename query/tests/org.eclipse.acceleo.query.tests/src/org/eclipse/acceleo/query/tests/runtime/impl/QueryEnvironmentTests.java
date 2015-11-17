/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests.runtime.impl;

import java.util.List;

import org.eclipse.acceleo.query.runtime.CrossReferenceProvider;
import org.eclipse.acceleo.query.runtime.IQueryEnvironmentListener;
import org.eclipse.acceleo.query.runtime.IRootEObjectProvider;
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.RootEObjectProvider;
import org.eclipse.acceleo.query.runtime.ServiceRegistrationResult;
import org.eclipse.acceleo.query.runtime.impl.QueryEnvironment;
import org.eclipse.acceleo.query.tests.runtime.lookup.basic.LookupEngineTest.TestCrossReferenceProvider;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests {@link QueryEnvironment}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class QueryEnvironmentTests {

	public static class TestQueryEnvironmentListener implements IQueryEnvironmentListener {

		public int servicePackageRegistered;

		public int servicePackageRemoved;

		public int ePackageRegistered;

		public int ePackageRemoved;

		public int customClassMappingRegistered;

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IQueryEnvironmentListener#servicePackageRegistered(org.eclipse.acceleo.query.runtime.ServiceRegistrationResult,
		 *      java.lang.Class)
		 */
		@Override
		public void servicePackageRegistered(ServiceRegistrationResult result, Class<?> services) {
			servicePackageRegistered++;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IQueryEnvironmentListener#servicePackageRemoved(java.lang.Class)
		 */
		@Override
		public void servicePackageRemoved(Class<?> services) {
			servicePackageRemoved++;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IQueryEnvironmentListener#ePackageRegistered(org.eclipse.emf.ecore.EPackage)
		 */
		@Override
		public void ePackageRegistered(EPackage ePackage) {
			ePackageRegistered++;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IQueryEnvironmentListener#ePackageRemoved(org.eclipse.emf.ecore.EPackage)
		 */
		@Override
		public void ePackageRemoved(EPackage ePackage) {
			ePackageRemoved++;
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.IQueryEnvironmentListener#customClassMappingRegistered(org.eclipse.emf.ecore.EClassifier,
		 *      java.lang.Class)
		 */
		@Override
		public void customClassMappingRegistered(EClassifier eClassifier, Class<?> cls) {
			customClassMappingRegistered++;
		}

	}

	public static class TestQueryEnvironment extends QueryEnvironment {

		/**
		 * Constructor.
		 * 
		 * @param crossReferencer
		 *            a new {@link CrossReferencer} that will be used to resolve eReference requests in
		 *            services needed it.
		 * @param rootProvider
		 *            a new {@link IRootEObjectProvider} that will be used to search all instances requests in
		 *            services needed it.
		 */
		public TestQueryEnvironment(CrossReferenceProvider crossReferencer, IRootEObjectProvider rootProvider) {
			super(crossReferencer, rootProvider);
		}

		/**
		 * {@inheritDoc}
		 *
		 * @see org.eclipse.acceleo.query.runtime.impl.QueryEnvironment#getListeners()
		 */
		@Override
		public List<IQueryEnvironmentListener> getListeners() {
			return super.getListeners();
		}

	}

	private TestQueryEnvironment queryEnvironment;

	private TestQueryEnvironmentListener listener;

	@Before
	public void before() {
		final CrossReferenceProvider crossReferencer = new TestCrossReferenceProvider();
		final IRootEObjectProvider rootProvider = new RootEObjectProvider();
		queryEnvironment = new TestQueryEnvironment(crossReferencer, rootProvider);
		listener = new TestQueryEnvironmentListener();
		queryEnvironment.addQueryEnvironmentListener(listener);
	}

	@Test
	public void isRegisteredServicePackageNull() {
		assertFalse(queryEnvironment.isRegisteredServicePackage(null));
	}

	@Test
	public void isRegisteredServicePackage() throws InvalidAcceleoPackageException {
		assertFalse(queryEnvironment.isRegisteredServicePackage(this.getClass()));

		queryEnvironment.registerServicePackage(this.getClass());

		assertTrue(queryEnvironment.isRegisteredServicePackage(this.getClass()));
		assertListener(listener, 1, 0, 0, 0, 0);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void registerServicePackageNull() throws InvalidAcceleoPackageException {
		queryEnvironment.registerServicePackage(null);
	}

	@Test
	public void registerServicePackageNotRegistered() throws InvalidAcceleoPackageException {
		ServiceRegistrationResult result = queryEnvironment.registerServicePackage(this.getClass());

		assertTrue(queryEnvironment.getLookupEngine().isRegisteredService(this.getClass()));
		assertFalse(result.getRegistered().isEmpty());
		assertListener(listener, 1, 0, 0, 0, 0);
	}

	@Test
	public void registerServicePackageRegistered() throws InvalidAcceleoPackageException {
		ServiceRegistrationResult result = queryEnvironment.registerServicePackage(this.getClass());

		assertFalse(result.getRegistered().isEmpty());

		result = queryEnvironment.registerServicePackage(this.getClass());

		assertTrue(queryEnvironment.getLookupEngine().isRegisteredService(this.getClass()));
		assertTrue(result.getRegistered().isEmpty());
		assertListener(listener, 1, 0, 0, 0, 0);
	}

	@Test
	public void removeServicePackageNull() {
		queryEnvironment.removeServicePackage(null);
		assertListener(listener, 0, 0, 0, 0, 0);
	}

	@Test
	public void removeServicePackageNotRegistered() throws InvalidAcceleoPackageException {
		assertFalse(queryEnvironment.getLookupEngine().isRegisteredService(this.getClass()));

		queryEnvironment.removeServicePackage(this.getClass());

		assertFalse(queryEnvironment.getLookupEngine().isRegisteredService(this.getClass()));
		assertListener(listener, 0, 0, 0, 0, 0);
	}

	@Test
	public void removeServicePackageRegistered() throws InvalidAcceleoPackageException {
		assertFalse(queryEnvironment.getLookupEngine().isRegisteredService(this.getClass()));

		queryEnvironment.registerServicePackage(this.getClass());

		assertTrue(queryEnvironment.getLookupEngine().isRegisteredService(this.getClass()));
		assertListener(listener, 1, 0, 0, 0, 0);

		queryEnvironment.removeServicePackage(this.getClass());

		assertFalse(queryEnvironment.getLookupEngine().isRegisteredService(this.getClass()));
		assertListener(listener, 1, 1, 0, 0, 0);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void registerEPackageNull() {
		queryEnvironment.registerEPackage(null);
	}

	@Test
	public void registerEPackageNotRegistered() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("test");
		ePkg.setNsURI("test");
		ePkg.setNsPrefix("test");

		queryEnvironment.registerEPackage(ePkg);

		assertTrue(queryEnvironment.getEPackageProvider().getRegisteredEPackages().contains(ePkg));
		assertListener(listener, 0, 0, 1, 0, 0);
	}

	@Test
	public void registerEPackageRegistered() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("test");
		ePkg.setNsURI("test");
		ePkg.setNsPrefix("test");

		queryEnvironment.registerEPackage(ePkg);

		assertTrue(queryEnvironment.getEPackageProvider().getRegisteredEPackages().contains(ePkg));
		assertListener(listener, 0, 0, 1, 0, 0);

		queryEnvironment.registerEPackage(ePkg);

		assertTrue(queryEnvironment.getEPackageProvider().getRegisteredEPackages().contains(ePkg));
		assertListener(listener, 0, 0, 1, 0, 0);
	}

	@Test
	public void removeEPackageNull() {
		queryEnvironment.removeEPackage(null);

		assertListener(listener, 0, 0, 0, 0, 0);
	}

	@Test
	public void removeEPackageNotRegistered() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("test");
		ePkg.setNsURI("test");
		ePkg.setNsPrefix("test");

		queryEnvironment.removeEPackage(ePkg.getName());

		assertFalse(queryEnvironment.getEPackageProvider().getRegisteredEPackages().contains(ePkg));
		assertListener(listener, 0, 0, 0, 0, 0);
	}

	@Test
	public void removeEPackageRegistered() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("test");
		ePkg.setNsURI("test");
		ePkg.setNsPrefix("test");

		queryEnvironment.registerEPackage(ePkg);

		assertTrue(queryEnvironment.getEPackageProvider().getRegisteredEPackages().contains(ePkg));
		assertListener(listener, 0, 0, 1, 0, 0);

		queryEnvironment.removeEPackage(ePkg.getName());

		assertFalse(queryEnvironment.getEPackageProvider().getRegisteredEPackages().contains(ePkg));
		assertListener(listener, 0, 0, 1, 1, 0);
	}

	@Test
	public void registerCustomClassMapping() {
		queryEnvironment.registerCustomClassMapping(EcorePackage.eINSTANCE.getEAttribute(), this.getClass());

		assertEquals(this.getClass(), queryEnvironment.getEPackageProvider().getClass(
				EcorePackage.eINSTANCE.getEAttribute()));
		assertListener(listener, 0, 0, 0, 0, 1);
	}

	@Test(expected = java.lang.IllegalArgumentException.class)
	public void addQueryEnvironmentListenerNull() {
		queryEnvironment.addQueryEnvironmentListener(null);
	}

	@Test
	public void addQueryEnvironmentListener() {
		final TestQueryEnvironmentListener listener = new TestQueryEnvironmentListener();

		queryEnvironment.addQueryEnvironmentListener(listener);

		assertEquals(2, queryEnvironment.getListeners().size());
		assertEquals(listener, queryEnvironment.getListeners().get(1));
	}

	@Test
	public void removeQueryEnvironmentListenerNull() {
		queryEnvironment.removeQueryEnvironmentListener(null);

		assertFalse(queryEnvironment.getListeners().contains(null));
		assertEquals(1, queryEnvironment.getListeners().size());
	}

	@Test
	public void removeQueryEnvironmentListenerNotAdded() {
		final TestQueryEnvironmentListener listener = new TestQueryEnvironmentListener();

		queryEnvironment.removeQueryEnvironmentListener(listener);

		assertEquals(1, queryEnvironment.getListeners().size());
	}

	@Test
	public void removeQueryEnvironmentListenerAdded() {
		final TestQueryEnvironmentListener listener = new TestQueryEnvironmentListener();

		queryEnvironment.addQueryEnvironmentListener(listener);

		assertEquals(2, queryEnvironment.getListeners().size());
		assertEquals(listener, queryEnvironment.getListeners().get(1));

		queryEnvironment.removeQueryEnvironmentListener(listener);

		assertEquals(1, queryEnvironment.getListeners().size());
	}

	public static void assertListener(TestQueryEnvironmentListener listener, int servicePackageRegistered,
			int servicePackageRemoved, int ePackageRegistered, int ePackageRemoved,
			int customClassMappingRegistered) {
		assertEquals(servicePackageRegistered, listener.servicePackageRegistered);
		assertEquals(servicePackageRemoved, listener.servicePackageRemoved);
		assertEquals(ePackageRegistered, listener.ePackageRegistered);
		assertEquals(ePackageRemoved, listener.ePackageRemoved);
		assertEquals(customClassMappingRegistered, listener.customClassMappingRegistered);
	}

}
