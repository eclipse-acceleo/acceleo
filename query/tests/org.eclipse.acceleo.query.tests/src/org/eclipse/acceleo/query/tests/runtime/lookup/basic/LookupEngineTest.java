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
package org.eclipse.acceleo.query.tests.runtime.lookup.basic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.CrossReferenceProvider;
import org.eclipse.acceleo.query.runtime.ILookupEngine;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.ServiceRegistrationResult;
import org.eclipse.acceleo.query.runtime.impl.AbstractServiceProvider;
import org.eclipse.acceleo.query.runtime.lookup.basic.BasicLookupEngine;
import org.eclipse.acceleo.query.runtime.lookup.basic.CacheLookupEngine;
import org.eclipse.acceleo.query.runtime.lookup.basic.Service;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class LookupEngineTest {

	private static final class TestCrossReferenceProvider implements CrossReferenceProvider {

		@Override
		public Collection<Setting> getInverseReferences(EObject self) {
			return null;
		}

	}

	private interface ITestLookupEngine extends ILookupEngine {

		ServiceRegistrationResult registerServices(Class<?> newServices)
				throws InvalidAcceleoPackageException;

		void removeServices(Class<?> servicesClass);

		Map<Integer, Map<String, List<IService>>> getServices();

		Map<Class<?>, Set<IService>> getClassToServices();
	}

	private static class TestBasicLookupEngine extends BasicLookupEngine implements ITestLookupEngine {

		public TestBasicLookupEngine(CrossReferenceProvider crossReferencer) {
			super(Query.newEnvironmentWithDefaultServices(crossReferencer), crossReferencer);
		}

		@Override
		public Map<Integer, Map<String, List<IService>>> getServices() {
			return super.getServices();
		}

		@Override
		public Map<Class<?>, Set<IService>> getClassToServices() {
			return super.getClassToServices();
		}

	}

	private static class TestCacheLookupEngine extends CacheLookupEngine implements ITestLookupEngine {

		public TestCacheLookupEngine(CrossReferenceProvider crossReferencer) {
			super(Query.newEnvironmentWithDefaultServices(crossReferencer), crossReferencer);
		}

		@Override
		public Map<Integer, Map<String, List<IService>>> getServices() {
			return super.getServices();
		}

		@Override
		public Map<Class<?>, Set<IService>> getClassToServices() {
			return super.getClassToServices();
		}

	}

	/**
	 * TestService {@link Class}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	public static class TestServices1 {

		public CrossReferenceProvider crossReferencer;

		public Boolean service1(EClassifier eObj) {
			return Boolean.FALSE;
		}

		@SuppressWarnings("unused")
		private Boolean notAService(EClassifier eObj) {
			return Boolean.FALSE;
		}

		public void setCrossReferencer(CrossReferenceProvider crossReferencer) {
			this.crossReferencer = crossReferencer;
		}

	}

	public static final class TestStaticServices {

		private TestStaticServices() {
			// nothing to do here;
		}

		public static Boolean service1(EClassifier eObj) {
			return Boolean.FALSE;
		}

		@SuppressWarnings("unused")
		private Boolean notAService(EClassifier eObj) {
			return Boolean.FALSE;
		}

	}

	/**
	 * TestService {@link Class}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	public static class TestDuplicateServices1 {

		public Boolean service1(EClassifier eObj) {
			return Boolean.FALSE;
		}

	}

	/**
	 * TestService {@link Class}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	public static class TestMaskServices1 {

		public Boolean service1(EClass eCls) {
			return Boolean.FALSE;
		}

	}

	/**
	 * TestService {@link Class}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	public static class ExtendedTestServices1 extends TestServices1 {

		public Boolean service2(EClassifier eObj) {
			return Boolean.FALSE;
		}

	}

	/**
	 * TestService {@link Class}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	public static class TestServicesProvider1 extends AbstractServiceProvider {

		private IService service1;

		public CrossReferenceProvider crossReferencer;

		public Boolean service1(EClassifier eObj) {
			return Boolean.FALSE;
		}

		@SuppressWarnings("unused")
		private Boolean notAService(EClassifier eObj) {
			return Boolean.FALSE;
		}

		public void setCrossReferencer(CrossReferenceProvider crossReferencer) {
			this.crossReferencer = crossReferencer;
		}

		@Override
		protected IService getService(Method method) {
			IService result = new Service(method, this);

			service1 = result;

			return result;
		}

	}

	/**
	 * TestService {@link Class}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	public static class TestDuplicateServicesProvider1 extends AbstractServiceProvider {

		private IService service1;

		public Boolean service1(EClassifier eObj) {
			return Boolean.FALSE;
		}

		@Override
		protected IService getService(Method method) {
			IService result = new Service(method, this);

			service1 = result;

			return result;
		}

	}

	/**
	 * TestService {@link Class}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	public static class TestMaskServicesProvider1 extends AbstractServiceProvider {

		private IService service1;

		public Boolean service1(EClass eCls) {
			return Boolean.FALSE;
		}

		@Override
		protected IService getService(Method method) {
			IService result = new Service(method, this);

			service1 = result;

			return result;
		}

	}

	/**
	 * TestService {@link Class}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	public static class ExtendedTestServicesProvider1 extends TestServicesProvider1 {

		private IService service2;

		public Boolean service2(EClassifier eObj) {
			return Boolean.FALSE;
		}

		@Override
		protected IService getService(Method method) {
			final IService result;

			if ("service2".equals(method.getName())) {
				result = new Service(method, this);
				service2 = result;
			} else {
				result = super.getService(method);
			}

			return result;
		}

	}

	private final Class<ITestLookupEngine> cls;

	public LookupEngineTest(Class<ITestLookupEngine> cls) {
		this.cls = cls;
	}

	@Parameters(name = "{0}")
	public static Collection<Object[]> classes() {
		return Arrays.asList(new Object[][] { {TestBasicLookupEngine.class, },
				{TestCacheLookupEngine.class, }, });
	}

	ITestLookupEngine instanciate(CrossReferenceProvider provider) {
		try {
			final Constructor<ITestLookupEngine> constructor = cls
					.getConstructor(CrossReferenceProvider.class);
			return constructor.newInstance(provider);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Test
	public void simpleRegistration() throws InvalidAcceleoPackageException, NoSuchMethodException,
			SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final ServiceRegistrationResult result = engine.registerServices(TestServices1.class);
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getClassToServices().size());
		assertEquals(1, engine.getClassToServices().get(TestServices1.class).size());

		final IService service = engine.lookup("service1", new Class<?>[] {EClassifier.class });
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), service.getServiceMethod());

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), result.getRegistered()
				.get(0));
	}

	@Test
	public void staticRegistration() throws InvalidAcceleoPackageException, NoSuchMethodException,
			SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final ServiceRegistrationResult result = engine.registerServices(TestStaticServices.class);
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getClassToServices().size());
		assertEquals(1, engine.getClassToServices().get(TestStaticServices.class).size());

		final IService service = engine.lookup("service1", new Class<?>[] {EClassifier.class });
		assertEquals(TestStaticServices.class.getMethod("service1", EClassifier.class), service
				.getServiceMethod());

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestStaticServices.class.getMethod("service1", EClassifier.class), result
				.getRegistered().get(0));
	}

	@Test
	public void extendedRegistration() throws InvalidAcceleoPackageException, NoSuchMethodException,
			SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final ServiceRegistrationResult result = engine.registerServices(ExtendedTestServices1.class);
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getClassToServices().size());
		assertEquals(2, engine.getClassToServices().get(ExtendedTestServices1.class).size());

		IService service = engine.lookup("service1", new Class<?>[] {EClassifier.class });
		assertEquals(ExtendedTestServices1.class.getMethod("service1", EClassifier.class), service
				.getServiceMethod());
		service = engine.lookup("service2", new Class<?>[] {EClassifier.class });
		assertEquals(ExtendedTestServices1.class.getMethod("service2", EClassifier.class), service
				.getServiceMethod());

		assertEquals(2, result.getRegistered().size());
		assertEquals(ExtendedTestServices1.class.getMethod("service2", EClassifier.class), result
				.getRegistered().get(0));
		assertEquals(ExtendedTestServices1.class.getMethod("service1", EClassifier.class), result
				.getRegistered().get(1));
	}

	@Test
	public void doubleRegistration() throws InvalidAcceleoPackageException, NoSuchMethodException,
			SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = engine.registerServices(TestServices1.class);
		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), result.getRegistered()
				.get(0));

		result = engine.registerServices(TestServices1.class);
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getClassToServices().size());
		assertEquals(1, engine.getClassToServices().get(TestServices1.class).size());

		final IService service = engine.lookup("service1", new Class<?>[] {EClassifier.class });
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), service.getServiceMethod());

		assertEquals(0, result.getRegistered().size());
	}

	@Test
	public void duplicateRegistration() throws InvalidAcceleoPackageException, NoSuchMethodException,
			SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = engine.registerServices(TestServices1.class);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), result.getRegistered()
				.get(0));

		result = engine.registerServices(TestDuplicateServices1.class);
		assertEquals(1, result.getDuplicated().size());
		final Method expectedDuplicatedMethod = TestServices1.class.getMethod("service1", EClassifier.class);
		final Method actualDuplicatedMethod = result.getDuplicated().get(
				TestDuplicateServices1.class.getMethod("service1", EClassifier.class)).get(0);
		assertEquals(expectedDuplicatedMethod, actualDuplicatedMethod);
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(2, engine.getClassToServices().size());
		assertEquals(1, engine.getClassToServices().get(TestServices1.class).size());
		assertEquals(1, engine.getClassToServices().get(TestDuplicateServices1.class).size());

		final IService service = engine.lookup("service1", new Class<?>[] {EClassifier.class });
		assertEquals(TestDuplicateServices1.class.getMethod("service1", EClassifier.class), service
				.getServiceMethod());

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestDuplicateServices1.class.getMethod("service1", EClassifier.class), result
				.getRegistered().get(0));
	}

	@Test
	public void maskRegistration() throws InvalidAcceleoPackageException, NoSuchMethodException,
			SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = engine.registerServices(TestServices1.class);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), result.getRegistered()
				.get(0));

		result = engine.registerServices(TestMaskServices1.class);
		assertEquals(0, result.getDuplicated().size());
		assertEquals(1, result.getMasked().size());
		final Method expectedMaskMethod = TestServices1.class.getMethod("service1", EClassifier.class);
		final Method actualMaskMethod = result.getMasked().get(
				TestMaskServices1.class.getMethod("service1", EClass.class)).get(0);
		assertEquals(expectedMaskMethod, actualMaskMethod);
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(2, engine.getClassToServices().size());
		assertEquals(1, engine.getClassToServices().get(TestServices1.class).size());
		assertEquals(1, engine.getClassToServices().get(TestMaskServices1.class).size());

		IService service = engine.lookup("service1", new Class<?>[] {EClassifier.class });
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), service.getServiceMethod());
		service = engine.lookup("service1", new Class<?>[] {EClass.class });
		assertEquals(TestMaskServices1.class.getMethod("service1", EClass.class), service.getServiceMethod());

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestMaskServices1.class.getMethod("service1", EClass.class), result.getRegistered().get(
				0));
	}

	@Test
	public void maskedByRegistration() throws InvalidAcceleoPackageException, NoSuchMethodException,
			SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = engine.registerServices(TestMaskServices1.class);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestMaskServices1.class.getMethod("service1", EClass.class), result.getRegistered().get(
				0));

		result = engine.registerServices(TestServices1.class);
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(1, result.getIsMaskedBy().size());
		final Method expectedIsMaskedByMethod = TestMaskServices1.class.getMethod("service1", EClass.class);
		final Method actualIsMaskedByMethod = result.getIsMaskedBy().get(
				TestServices1.class.getMethod("service1", EClassifier.class)).get(0);
		assertEquals(expectedIsMaskedByMethod, actualIsMaskedByMethod);

		assertEquals(2, engine.getClassToServices().size());
		assertEquals(1, engine.getClassToServices().get(TestServices1.class).size());
		assertEquals(1, engine.getClassToServices().get(TestMaskServices1.class).size());

		IService service = engine.lookup("service1", new Class<?>[] {EClassifier.class });
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), service.getServiceMethod());
		service = engine.lookup("service1", new Class<?>[] {EClass.class });
		assertEquals(TestMaskServices1.class.getMethod("service1", EClass.class), service.getServiceMethod());

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), result.getRegistered()
				.get(0));
	}

	@Test
	public void withCrossReferencerRegistration() throws InvalidAcceleoPackageException,
			NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final ServiceRegistrationResult result = engine.registerServices(TestServices1.class);
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getClassToServices().size());
		assertEquals(1, engine.getClassToServices().get(TestServices1.class).size());
		assertTrue(engine.getClassToServices().get(TestServices1.class).iterator().next()
				.getServiceInstance() instanceof TestServices1);
		TestServices1 instance = (TestServices1)engine.getClassToServices().get(TestServices1.class)
				.iterator().next().getServiceInstance();
		assertEquals(provider, instance.crossReferencer);

		final IService service = engine.lookup("service1", new Class<?>[] {EClassifier.class });
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), service.getServiceMethod());

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), result.getRegistered()
				.get(0));
	}

	@Test
	public void simpleRegistrationServiceProvider() throws InvalidAcceleoPackageException,
			NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final ServiceRegistrationResult result = engine.registerServices(TestServicesProvider1.class);
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getClassToServices().size());
		assertEquals(1, engine.getClassToServices().get(TestServicesProvider1.class).size());

		final IService service = engine.lookup("service1", new Class<?>[] {EClassifier.class });
		assertEquals(((TestServicesProvider1)service.getServiceInstance()).service1, service);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class), result
				.getRegistered().get(0));
	}

	@Test
	public void extendedRegistrationServiceProvider() throws InvalidAcceleoPackageException,
			NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final ServiceRegistrationResult result = engine.registerServices(ExtendedTestServicesProvider1.class);
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getClassToServices().size());
		assertEquals(2, engine.getClassToServices().get(ExtendedTestServicesProvider1.class).size());

		IService service = engine.lookup("service1", new Class<?>[] {EClassifier.class });
		assertEquals(ExtendedTestServicesProvider1.class.getMethod("service1", EClassifier.class), service
				.getServiceMethod());
		assertEquals(((TestServicesProvider1)service.getServiceInstance()).service1, service);
		service = engine.lookup("service2", new Class<?>[] {EClassifier.class });
		assertEquals(ExtendedTestServicesProvider1.class.getMethod("service2", EClassifier.class), service
				.getServiceMethod());
		assertEquals(((ExtendedTestServicesProvider1)service.getServiceInstance()).service2, service);

		assertEquals(2, result.getRegistered().size());
		assertEquals(ExtendedTestServicesProvider1.class.getMethod("service2", EClassifier.class), result
				.getRegistered().get(0));
		assertEquals(ExtendedTestServicesProvider1.class.getMethod("service1", EClassifier.class), result
				.getRegistered().get(1));
	}

	@Test
	public void doubleRegistrationServiceProvider() throws InvalidAcceleoPackageException,
			NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = engine.registerServices(TestServicesProvider1.class);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class), result
				.getRegistered().get(0));

		result = engine.registerServices(TestServicesProvider1.class);
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getClassToServices().size());
		assertEquals(1, engine.getClassToServices().get(TestServicesProvider1.class).size());

		final IService service = engine.lookup("service1", new Class<?>[] {EClassifier.class });
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class), service
				.getServiceMethod());
		assertEquals(((TestServicesProvider1)service.getServiceInstance()).service1, service);

		assertEquals(0, result.getRegistered().size());
	}

	@Test
	public void duplicateRegistrationServiceProvider() throws InvalidAcceleoPackageException,
			NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = engine.registerServices(TestServicesProvider1.class);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class), result
				.getRegistered().get(0));

		result = engine.registerServices(TestDuplicateServicesProvider1.class);
		assertEquals(1, result.getDuplicated().size());
		final Method expectedDuplicatedMethod = TestServicesProvider1.class.getMethod("service1",
				EClassifier.class);
		final Method actualDuplicatedMethod = result.getDuplicated().get(
				TestDuplicateServicesProvider1.class.getMethod("service1", EClassifier.class)).get(0);
		assertEquals(expectedDuplicatedMethod, actualDuplicatedMethod);
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(2, engine.getClassToServices().size());
		assertEquals(1, engine.getClassToServices().get(TestServicesProvider1.class).size());
		assertEquals(1, engine.getClassToServices().get(TestDuplicateServicesProvider1.class).size());

		final IService service = engine.lookup("service1", new Class<?>[] {EClassifier.class });
		assertEquals(TestDuplicateServicesProvider1.class.getMethod("service1", EClassifier.class), service
				.getServiceMethod());
		assertEquals(((TestDuplicateServicesProvider1)service.getServiceInstance()).service1, service);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestDuplicateServicesProvider1.class.getMethod("service1", EClassifier.class), result
				.getRegistered().get(0));
	}

	@Test
	public void maskRegistrationServiceProvider() throws InvalidAcceleoPackageException,
			NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = engine.registerServices(TestServicesProvider1.class);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class), result
				.getRegistered().get(0));

		result = engine.registerServices(TestMaskServicesProvider1.class);
		assertEquals(0, result.getDuplicated().size());
		assertEquals(1, result.getMasked().size());
		final Method expectedMaskMethod = TestServicesProvider1.class
				.getMethod("service1", EClassifier.class);
		final Method actualMaskMethod = result.getMasked().get(
				TestMaskServicesProvider1.class.getMethod("service1", EClass.class)).get(0);
		assertEquals(expectedMaskMethod, actualMaskMethod);
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(2, engine.getClassToServices().size());
		assertEquals(1, engine.getClassToServices().get(TestServicesProvider1.class).size());
		assertEquals(1, engine.getClassToServices().get(TestMaskServicesProvider1.class).size());

		IService service = engine.lookup("service1", new Class<?>[] {EClassifier.class });
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class), service
				.getServiceMethod());
		assertEquals(((TestServicesProvider1)service.getServiceInstance()).service1, service);
		service = engine.lookup("service1", new Class<?>[] {EClass.class });
		assertEquals(TestMaskServicesProvider1.class.getMethod("service1", EClass.class), service
				.getServiceMethod());
		assertEquals(((TestMaskServicesProvider1)service.getServiceInstance()).service1, service);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestMaskServicesProvider1.class.getMethod("service1", EClass.class), result
				.getRegistered().get(0));
	}

	@Test
	public void maskedByRegistrationServiceProvider() throws InvalidAcceleoPackageException,
			NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = engine.registerServices(TestMaskServicesProvider1.class);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestMaskServicesProvider1.class.getMethod("service1", EClass.class), result
				.getRegistered().get(0));

		result = engine.registerServices(TestServicesProvider1.class);
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(1, result.getIsMaskedBy().size());
		final Method expectedIsMaskedByMethod = TestMaskServicesProvider1.class.getMethod("service1",
				EClass.class);
		final Method actualIsMaskedByMethod = result.getIsMaskedBy().get(
				TestServicesProvider1.class.getMethod("service1", EClassifier.class)).get(0);
		assertEquals(expectedIsMaskedByMethod, actualIsMaskedByMethod);

		assertEquals(2, engine.getClassToServices().size());
		assertEquals(1, engine.getClassToServices().get(TestServicesProvider1.class).size());
		assertEquals(1, engine.getClassToServices().get(TestMaskServicesProvider1.class).size());

		IService service = engine.lookup("service1", new Class<?>[] {EClassifier.class });
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class), service
				.getServiceMethod());
		assertEquals(((TestServicesProvider1)service.getServiceInstance()).service1, service);
		service = engine.lookup("service1", new Class<?>[] {EClass.class });
		assertEquals(TestMaskServicesProvider1.class.getMethod("service1", EClass.class), service
				.getServiceMethod());
		assertEquals(((TestMaskServicesProvider1)service.getServiceInstance()).service1, service);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class), result
				.getRegistered().get(0));
	}

	@Test
	public void withCrossReferencerRegistrationServiceProvider() throws InvalidAcceleoPackageException,
			NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final ServiceRegistrationResult result = engine.registerServices(TestServicesProvider1.class);
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getClassToServices().size());
		assertEquals(1, engine.getClassToServices().get(TestServicesProvider1.class).size());
		assertTrue(engine.getClassToServices().get(TestServicesProvider1.class).iterator().next()
				.getServiceInstance() instanceof TestServicesProvider1);
		TestServicesProvider1 instance = (TestServicesProvider1)engine.getClassToServices().get(
				TestServicesProvider1.class).iterator().next().getServiceInstance();
		assertEquals(provider, instance.crossReferencer);

		final IService service = engine.lookup("service1", new Class<?>[] {EClassifier.class });
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class), service
				.getServiceMethod());
		assertEquals(((TestServicesProvider1)service.getServiceInstance()).service1, service);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class), result
				.getRegistered().get(0));
	}

	@Test
	public void isServiceMethod() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final Method toStringMethod = Object.class.getMethod("toString");
		assertFalse(engine.isServiceMethod(this, toStringMethod));

		final Method shouldRegisterMethod = LookupEngineTest.class.getMethod("isServiceMethod");
		assertTrue(engine.isServiceMethod(this, shouldRegisterMethod));
		assertFalse(engine.isServiceMethod(null, shouldRegisterMethod));
	}

	@Test
	public void isCrossReferencerMethod() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final Method isCrossReferencerMethod = LookupEngineTest.class.getMethod("isCrossReferencerMethod");
		assertFalse(engine.isCrossReferencerMethod(isCrossReferencerMethod));

		final Method setCrossReferencer = TestServices1.class.getMethod("setCrossReferencer",
				CrossReferenceProvider.class);
		assertTrue(engine.isCrossReferencerMethod(setCrossReferencer));
	}

	@Test
	public void isRegisteredService() throws InvalidAcceleoPackageException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		assertFalse(engine.isRegisteredService(TestServices1.class));
		engine.registerServices(TestServices1.class);
		assertTrue(engine.isRegisteredService(TestServices1.class));
	}

	@Test
	public void removeServicesEmpty() {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		engine.removeServices(ExtendedTestServices1.class);

		assertEquals(0, engine.getClassToServices().size());
		assertEquals(0, engine.getServices().size());
	}

	@Test
	public void removeServices() throws InvalidAcceleoPackageException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		engine.registerServices(ExtendedTestServices1.class);

		assertEquals(1, engine.getClassToServices().size());
		assertEquals(1, engine.getServices().size());

		engine.removeServices(ExtendedTestServices1.class);

		assertEquals(0, engine.getClassToServices().size());
		assertEquals(0, engine.getServices().size());
	}

	@Test
	public void getServicesEmpty() {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);
		final Set<Class<?>> types = new LinkedHashSet<Class<?>>();
		types.add(EClassifier.class);

		assertEquals(0, engine.getServices(types).size());
	}

	@Test
	public void getServices() throws InvalidAcceleoPackageException, NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);
		final Set<Class<?>> types = new LinkedHashSet<Class<?>>();
		types.add(EClassifier.class);

		engine.registerServices(ExtendedTestServices1.class);
		final Set<IService> services = engine.getServices(types);
		assertEquals(2, services.size());
		final Iterator<IService> it = services.iterator();
		assertEquals(ExtendedTestServices1.class.getMethod("service2", EClassifier.class), it.next()
				.getServiceMethod());
		assertEquals(ExtendedTestServices1.class.getMethod("service1", EClassifier.class), it.next()
				.getServiceMethod());
	}

	@Test
	public void lookupEmpty() {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		assertEquals(null, engine.lookup("service", new Class<?>[] {}));
	}

}