/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests.runtime.impl;

import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IQueryEnvironmentListener;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.ServiceRegistrationResult;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.runtime.impl.QueryEnvironment;
import org.eclipse.acceleo.query.tests.runtime.lookup.basic.MethodHolder;
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

		@Override
		public void serviceRegistered(ServiceRegistrationResult result, IService<?> service) {
			servicePackageRegistered++;
		}

		@Override
		public void serviceRemoved(IService<?> services) {
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
		 */
		public TestQueryEnvironment() {
			super();
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
		queryEnvironment = new TestQueryEnvironment();
		listener = new TestQueryEnvironmentListener();
		queryEnvironment.addQueryEnvironmentListener(listener);
	}

	@Test
	public void isRegisteredServiceNull() {
		assertFalse(queryEnvironment.isRegisteredService(null));
	}

	@Test
	public void isRegisteredService() {
		final Set<IService<?>> services = ServiceUtils.getServices(queryEnvironment, MethodHolder.class);
		for (IService<?> service : services) {
			assertFalse(queryEnvironment.isRegisteredService(service));
		}

		ServiceUtils.registerServices(queryEnvironment, services);

		for (IService<?> service : services) {
			assertTrue(queryEnvironment.isRegisteredService(service));
		}
		assertListener(listener, 2, 0, 0, 0, 0);
	}

	@Test(expected = java.lang.NullPointerException.class)
	public void registerServiceNull() {
		queryEnvironment.registerService(null);
	}

	@Test
	public void registerServiceNotRegistered() {
		final Set<IService<?>> services = ServiceUtils.getServices(queryEnvironment, MethodHolder.class);
		final ServiceRegistrationResult result = ServiceUtils.registerServices(queryEnvironment, services);

		for (IService<?> service : services) {
			assertTrue(queryEnvironment.getLookupEngine().isRegisteredService(service));
		}
		assertFalse(result.getRegistered().isEmpty());
		assertListener(listener, 2, 0, 0, 0, 0);
	}

	@Test
	public void registerServiceRegistered() {
		final Set<IService<?>> services = ServiceUtils.getServices(queryEnvironment, MethodHolder.class);
		ServiceRegistrationResult result = ServiceUtils.registerServices(queryEnvironment, services);

		assertFalse(result.getRegistered().isEmpty());

		result = ServiceUtils.registerServices(queryEnvironment, services);

		for (IService<?> service : services) {
			assertTrue(queryEnvironment.getLookupEngine().isRegisteredService(service));
		}
		assertTrue(result.getRegistered().isEmpty());
		assertListener(listener, 2, 0, 0, 0, 0);
	}

	@Test
	public void removeServiceNull() {
		queryEnvironment.removeService(null);
		assertListener(listener, 0, 0, 0, 0, 0);
	}

	@Test
	public void removeServiceNotRegistered() {
		final Set<IService<?>> services = ServiceUtils.getServices(queryEnvironment, MethodHolder.class);
		for (IService<?> service : services) {
			assertFalse(queryEnvironment.getLookupEngine().isRegisteredService(service));
		}

		for (IService<?> service : services) {
			queryEnvironment.getLookupEngine().removeService(service);
		}

		for (IService<?> service : services) {
			assertFalse(queryEnvironment.getLookupEngine().isRegisteredService(service));
		}
		assertListener(listener, 0, 0, 0, 0, 0);
	}

	@Test
	public void removeServiceRegistered() {
		final Set<IService<?>> services = ServiceUtils.getServices(queryEnvironment, MethodHolder.class);
		for (IService<?> service : services) {
			assertFalse(queryEnvironment.getLookupEngine().isRegisteredService(service));
		}

		ServiceRegistrationResult result = ServiceUtils.registerServices(queryEnvironment, services);

		assertEquals(2, result.getRegistered().size());
		for (IService<?> service : services) {
			assertTrue(queryEnvironment.isRegisteredService(service));
		}
		assertListener(listener, 2, 0, 0, 0, 0);

		ServiceUtils.removeServices(queryEnvironment, services);

		for (IService<?> service : services) {
			assertFalse(queryEnvironment.isRegisteredService(service));
		}
		assertListener(listener, 2, 2, 0, 0, 0);
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

	@Test(expected = java.lang.NullPointerException.class)
	public void removeEPackageNull() {
		queryEnvironment.removeEPackage((EPackage)null);
	}

	@Test
	public void removeEPackageNotRegistered() {
		final EPackage ePkg = EcorePackage.eINSTANCE.getEcoreFactory().createEPackage();
		ePkg.setName("test");
		ePkg.setNsURI("test");
		ePkg.setNsPrefix("test");

		queryEnvironment.removeEPackage(ePkg);

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

		queryEnvironment.removeEPackage(ePkg);

		assertFalse(queryEnvironment.getEPackageProvider().getRegisteredEPackages().contains(ePkg));
		assertListener(listener, 0, 0, 1, 1, 0);
	}

	@Test
	public void registerCustomClassMapping() {
		queryEnvironment.registerCustomClassMapping(EcorePackage.eINSTANCE.getEAttribute(), this.getClass());

		assertEquals(this.getClass(), queryEnvironment.getEPackageProvider().getClass(EcorePackage.eINSTANCE
				.getEAttribute()));
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
