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
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.CrossReferenceProvider;
import org.eclipse.acceleo.query.runtime.ILookupEngine;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.InvalidAcceleoPackageException;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.ServiceRegistrationResult;
import org.eclipse.acceleo.query.runtime.impl.AbstractServiceProvider;
import org.eclipse.acceleo.query.runtime.impl.JavaMethodService;
import org.eclipse.acceleo.query.runtime.lookup.basic.BasicLookupEngine;
import org.eclipse.acceleo.query.runtime.lookup.basic.CacheLookupEngine;
import org.eclipse.acceleo.query.runtime.lookup.basic.ServiceStore;
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class LookupEngineTest {

	public static final class TestCrossReferenceProvider implements CrossReferenceProvider {

		@Override
		public Collection<Setting> getInverseReferences(EObject self) {
			return null;
		}

	}

	private interface ITestLookupEngine extends ILookupEngine {

		ServiceRegistrationResult registerServices(Class<?> newServices)
				throws InvalidAcceleoPackageException;

		public ServiceRegistrationResult registerServiceInstance(Object instance)
				throws InvalidAcceleoPackageException;

		Class<?> removeServices(Class<?> servicesClass);

		ServiceStore getServices();

	}

	private static class TestBasicLookupEngine extends BasicLookupEngine implements ITestLookupEngine {

		public TestBasicLookupEngine(CrossReferenceProvider crossReferencer) {
			super(Query.newEnvironmentWithDefaultServices(crossReferencer));
		}

		@Override
		public ServiceStore getServices() {
			return super.getServices();
		}

	}

	private static class TestCacheLookupEngine extends CacheLookupEngine implements ITestLookupEngine {

		public TestCacheLookupEngine(CrossReferenceProvider crossReferencer) {
			super(Query.newEnvironmentWithDefaultServices(crossReferencer));
		}

		@Override
		public ServiceStore getServices() {
			return super.getServices();
		}

	}

	/**
	 * TestService {@link Class}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	public static class TestServices1 {

		public final CrossReferenceProvider crossReferencer;

		public TestServices1(CrossReferenceProvider crossReferencer) {
			this.crossReferencer = crossReferencer;
		}

		public Boolean service1(EClassifier eObj) {
			return Boolean.FALSE;
		}

		@SuppressWarnings("unused")
		private Boolean notAService(EClassifier eObj) {
			return Boolean.FALSE;
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

		public ExtendedTestServices1(CrossReferenceProvider crossReferencer) {
			super(crossReferencer);
		}

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

		public final CrossReferenceProvider crossReferencer;

		public TestServicesProvider1(CrossReferenceProvider crossReferencer) {
			this.crossReferencer = crossReferencer;
		}

		public Boolean service1(EClassifier eObj) {
			return Boolean.FALSE;
		}

		@SuppressWarnings("unused")
		private Boolean notAService(EClassifier eObj) {
			return Boolean.FALSE;
		}

		@Override
		protected IService getService(Method method) {
			IService result = new JavaMethodService(method, this);

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
			IService result = new JavaMethodService(method, this);

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
			IService result = new JavaMethodService(method, this);

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

		public ExtendedTestServicesProvider1(CrossReferenceProvider crossReferencer) {
			super(crossReferencer);
		}

		private IService service2;

		public Boolean service2(EClassifier eObj) {
			return Boolean.FALSE;
		}

		@Override
		protected IService getService(Method method) {
			final IService result;

			if ("service2".equals(method.getName())) {
				result = new JavaMethodService(method, this);
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
		return Arrays.asList(new Object[][] {{TestBasicLookupEngine.class, }, {
				TestCacheLookupEngine.class, }, });
	}

	ITestLookupEngine instanciate(CrossReferenceProvider provider) {
		try {
			final Constructor<ITestLookupEngine> constructor = cls.getConstructor(
					CrossReferenceProvider.class);
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

		final ServiceRegistrationResult result = engine.registerServiceInstance(new TestServices1(provider));
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getRegisteredServices().size());
		assertEquals(1, engine.getRegisteredServices().get(TestServices1.class).size());

		final JavaMethodService service = (JavaMethodService)engine.lookup("service1", new Class<?>[] {
				EClassifier.class });
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), service.getMethod());

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), ((JavaMethodService)result
				.getRegistered().get(0)).getMethod());
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

		assertEquals(1, engine.getRegisteredServices().size());
		assertEquals(1, engine.getRegisteredServices().get(TestStaticServices.class).size());

		final JavaMethodService service = (JavaMethodService)engine.lookup("service1", new Class<?>[] {
				EClassifier.class });
		assertEquals(TestStaticServices.class.getMethod("service1", EClassifier.class), service.getMethod());

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestStaticServices.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getMethod());
	}

	@Test
	public void extendedRegistration() throws InvalidAcceleoPackageException, NoSuchMethodException,
			SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final ServiceRegistrationResult result = engine.registerServiceInstance(new ExtendedTestServices1(
				provider));
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getRegisteredServices().size());
		assertEquals(2, engine.getRegisteredServices().get(ExtendedTestServices1.class).size());

		JavaMethodService service = (JavaMethodService)engine.lookup("service1", new Class<?>[] {
				EClassifier.class });
		assertEquals(ExtendedTestServices1.class.getMethod("service1", EClassifier.class), service
				.getMethod());
		service = (JavaMethodService)engine.lookup("service2", new Class<?>[] {EClassifier.class });
		assertEquals(ExtendedTestServices1.class.getMethod("service2", EClassifier.class), service
				.getMethod());

		assertEquals(2, result.getRegistered().size());
		assertEquals(ExtendedTestServices1.class.getMethod("service2", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getMethod());
		assertEquals(ExtendedTestServices1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(1)).getMethod());
	}

	@Test
	public void doubleRegistration() throws InvalidAcceleoPackageException, NoSuchMethodException,
			SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = engine.registerServiceInstance(new TestServices1(provider));
		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), ((JavaMethodService)result
				.getRegistered().get(0)).getMethod());

		result = engine.registerServiceInstance(new TestServices1(provider));
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getRegisteredServices().size());
		assertEquals(1, engine.getRegisteredServices().get(TestServices1.class).size());

		final JavaMethodService service = (JavaMethodService)engine.lookup("service1", new Class<?>[] {
				EClassifier.class });
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), service.getMethod());

		assertEquals(0, result.getRegistered().size());
	}

	@Test
	public void duplicateRegistration() throws InvalidAcceleoPackageException, NoSuchMethodException,
			SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = engine.registerServiceInstance(new TestServices1(provider));

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), ((JavaMethodService)result
				.getRegistered().get(0)).getMethod());

		result = engine.registerServices(TestDuplicateServices1.class);
		assertEquals(1, result.getDuplicated().size());
		final Method expectedDuplicatedMethod = TestServices1.class.getMethod("service1", EClassifier.class);

		final Entry<IService, List<IService>> entry = result.getDuplicated().entrySet().iterator().next();
		assertEquals(TestDuplicateServices1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)entry.getKey()).getMethod());
		assertEquals(1, entry.getValue().size());
		final Method actualDuplicatedMethod = ((JavaMethodService)entry.getValue().get(0)).getMethod();

		assertEquals(expectedDuplicatedMethod, actualDuplicatedMethod);
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(2, engine.getRegisteredServices().size());
		assertEquals(1, engine.getRegisteredServices().get(TestServices1.class).size());
		assertEquals(1, engine.getRegisteredServices().get(TestDuplicateServices1.class).size());

		final JavaMethodService service = (JavaMethodService)engine.lookup("service1", new Class<?>[] {
				EClassifier.class });
		assertEquals(TestDuplicateServices1.class.getMethod("service1", EClassifier.class), service
				.getMethod());

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestDuplicateServices1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getMethod());
	}

	@Test
	public void maskRegistration() throws InvalidAcceleoPackageException, NoSuchMethodException,
			SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = engine.registerServiceInstance(new TestServices1(provider));

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), ((JavaMethodService)result
				.getRegistered().get(0)).getMethod());

		result = engine.registerServices(TestMaskServices1.class);
		assertEquals(0, result.getDuplicated().size());
		assertEquals(1, result.getMasked().size());
		final Method expectedMaskMethod = TestServices1.class.getMethod("service1", EClassifier.class);
		assertEquals(1, result.getMasked().entrySet().size());
		final Entry<IService, List<IService>> entry = result.getMasked().entrySet().iterator().next();
		assertEquals(TestMaskServices1.class.getMethod("service1", EClass.class), ((JavaMethodService)entry
				.getKey()).getMethod());
		assertEquals(1, entry.getValue().size());
		final Method actualMaskMethod = ((JavaMethodService)entry.getValue().get(0)).getMethod();

		assertEquals(expectedMaskMethod, actualMaskMethod);
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(2, engine.getRegisteredServices().size());
		assertEquals(1, engine.getRegisteredServices().get(TestServices1.class).size());
		assertEquals(1, engine.getRegisteredServices().get(TestMaskServices1.class).size());

		JavaMethodService service = (JavaMethodService)engine.lookup("service1", new Class<?>[] {
				EClassifier.class });
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), service.getMethod());
		service = (JavaMethodService)engine.lookup("service1", new Class<?>[] {EClass.class });
		assertEquals(TestMaskServices1.class.getMethod("service1", EClass.class), service.getMethod());

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestMaskServices1.class.getMethod("service1", EClass.class), ((JavaMethodService)result
				.getRegistered().get(0)).getMethod());
	}

	@Test
	public void maskedByRegistration() throws InvalidAcceleoPackageException, NoSuchMethodException,
			SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = engine.registerServices(TestMaskServices1.class);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestMaskServices1.class.getMethod("service1", EClass.class), ((JavaMethodService)result
				.getRegistered().get(0)).getMethod());

		result = engine.registerServiceInstance(new TestServices1(provider));
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(1, result.getIsMaskedBy().size());
		final Method expectedIsMaskedByMethod = TestMaskServices1.class.getMethod("service1", EClass.class);

		final Entry<IService, List<IService>> entry = result.getIsMaskedBy().entrySet().iterator().next();
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), ((JavaMethodService)entry
				.getKey()).getMethod());
		assertEquals(1, entry.getValue().size());
		final Method actualIsMaskedByMethod = ((JavaMethodService)entry.getValue().get(0)).getMethod();

		assertEquals(expectedIsMaskedByMethod, actualIsMaskedByMethod);

		assertEquals(2, engine.getRegisteredServices().size());
		assertEquals(1, engine.getRegisteredServices().get(TestServices1.class).size());
		assertEquals(1, engine.getRegisteredServices().get(TestMaskServices1.class).size());

		JavaMethodService service = (JavaMethodService)engine.lookup("service1", new Class<?>[] {
				EClassifier.class });
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), service.getMethod());
		service = (JavaMethodService)engine.lookup("service1", new Class<?>[] {EClass.class });
		assertEquals(TestMaskServices1.class.getMethod("service1", EClass.class), service.getMethod());

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), ((JavaMethodService)result
				.getRegistered().get(0)).getMethod());
	}

	@Test
	public void withCrossReferencerRegistration() throws InvalidAcceleoPackageException,
			NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final ServiceRegistrationResult result = engine.registerServiceInstance(new TestServices1(provider));
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getRegisteredServices().size());
		assertEquals(1, engine.getRegisteredServices().get(TestServices1.class).size());
		assertTrue(((JavaMethodService)engine.getRegisteredServices().get(TestServices1.class).iterator()
				.next()).getInstance() instanceof TestServices1);
		TestServices1 instance = (TestServices1)((JavaMethodService)engine.getRegisteredServices().get(
				TestServices1.class).iterator().next()).getInstance();
		assertEquals(provider, instance.crossReferencer);

		final JavaMethodService service = (JavaMethodService)engine.lookup("service1", new Class<?>[] {
				EClassifier.class });
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), service.getMethod());

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), ((JavaMethodService)result
				.getRegistered().get(0)).getMethod());
	}

	@Test
	public void simpleRegistrationServiceProvider() throws InvalidAcceleoPackageException,
			NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final ServiceRegistrationResult result = engine.registerServiceInstance(new TestServicesProvider1(
				provider));
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getRegisteredServices().size());
		assertEquals(1, engine.getRegisteredServices().get(TestServicesProvider1.class).size());

		final JavaMethodService service = (JavaMethodService)engine.lookup("service1", new Class<?>[] {
				EClassifier.class });
		assertEquals(((TestServicesProvider1)service.getInstance()).service1, service);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getMethod());
	}

	@Test
	public void extendedRegistrationServiceProvider() throws InvalidAcceleoPackageException,
			NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final ServiceRegistrationResult result = engine
				.registerServiceInstance(new ExtendedTestServicesProvider1(provider));
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getRegisteredServices().size());
		assertEquals(2, engine.getRegisteredServices().get(ExtendedTestServicesProvider1.class).size());

		JavaMethodService service = (JavaMethodService)engine.lookup("service1", new Class<?>[] {
				EClassifier.class });
		assertEquals(ExtendedTestServicesProvider1.class.getMethod("service1", EClassifier.class), service
				.getMethod());
		assertEquals(((TestServicesProvider1)service.getInstance()).service1, service);
		service = (JavaMethodService)engine.lookup("service2", new Class<?>[] {EClassifier.class });
		assertEquals(ExtendedTestServicesProvider1.class.getMethod("service2", EClassifier.class), service
				.getMethod());
		assertEquals(((ExtendedTestServicesProvider1)service.getInstance()).service2, service);

		assertEquals(2, result.getRegistered().size());
		assertEquals(ExtendedTestServicesProvider1.class.getMethod("service2", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getMethod());
		assertEquals(ExtendedTestServicesProvider1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(1)).getMethod());
	}

	@Test
	public void doubleRegistrationServiceProvider() throws InvalidAcceleoPackageException,
			NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = engine
				.registerServiceInstance(new TestServicesProvider1(provider));

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getMethod());

		result = engine.registerServiceInstance(new TestServicesProvider1(provider));
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getRegisteredServices().size());
		assertEquals(1, engine.getRegisteredServices().get(TestServicesProvider1.class).size());

		final JavaMethodService service = (JavaMethodService)engine.lookup("service1", new Class<?>[] {
				EClassifier.class });
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class), service
				.getMethod());
		assertEquals(((TestServicesProvider1)service.getInstance()).service1, service);

		assertEquals(0, result.getRegistered().size());
	}

	@Test
	public void duplicateRegistrationServiceProvider() throws InvalidAcceleoPackageException,
			NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = engine
				.registerServiceInstance(new TestServicesProvider1(provider));

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getMethod());

		result = engine.registerServices(TestDuplicateServicesProvider1.class);
		assertEquals(1, result.getDuplicated().size());
		final Method expectedDuplicatedMethod = TestServicesProvider1.class.getMethod("service1",
				EClassifier.class);

		final Entry<IService, List<IService>> entry = result.getDuplicated().entrySet().iterator().next();
		assertEquals(TestDuplicateServicesProvider1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)entry.getKey()).getMethod());
		assertEquals(1, entry.getValue().size());
		final Method actualDuplicatedMethod = ((JavaMethodService)entry.getValue().get(0)).getMethod();

		assertEquals(expectedDuplicatedMethod, actualDuplicatedMethod);
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(2, engine.getRegisteredServices().size());
		assertEquals(1, engine.getRegisteredServices().get(TestServicesProvider1.class).size());
		assertEquals(1, engine.getRegisteredServices().get(TestDuplicateServicesProvider1.class).size());

		final JavaMethodService service = (JavaMethodService)engine.lookup("service1", new Class<?>[] {
				EClassifier.class });
		assertEquals(TestDuplicateServicesProvider1.class.getMethod("service1", EClassifier.class), service
				.getMethod());
		assertEquals(((TestDuplicateServicesProvider1)service.getInstance()).service1, service);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestDuplicateServicesProvider1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getMethod());
	}

	@Test
	public void maskRegistrationServiceProvider() throws InvalidAcceleoPackageException,
			NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = engine
				.registerServiceInstance(new TestServicesProvider1(provider));

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getMethod());

		result = engine.registerServices(TestMaskServicesProvider1.class);
		assertEquals(0, result.getDuplicated().size());
		assertEquals(1, result.getMasked().size());
		final Method expectedMaskMethod = TestServicesProvider1.class.getMethod("service1",
				EClassifier.class);

		final Entry<IService, List<IService>> entry = result.getMasked().entrySet().iterator().next();
		assertEquals(TestMaskServicesProvider1.class.getMethod("service1", EClass.class),
				((JavaMethodService)entry.getKey()).getMethod());
		assertEquals(1, entry.getValue().size());
		final Method actualMaskMethod = ((JavaMethodService)entry.getValue().get(0)).getMethod();

		assertEquals(expectedMaskMethod, actualMaskMethod);
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(2, engine.getRegisteredServices().size());
		assertEquals(1, engine.getRegisteredServices().get(TestServicesProvider1.class).size());
		assertEquals(1, engine.getRegisteredServices().get(TestMaskServicesProvider1.class).size());

		JavaMethodService service = (JavaMethodService)engine.lookup("service1", new Class<?>[] {
				EClassifier.class });
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class), service
				.getMethod());
		assertEquals(((TestServicesProvider1)service.getInstance()).service1, service);
		service = (JavaMethodService)engine.lookup("service1", new Class<?>[] {EClass.class });
		assertEquals(TestMaskServicesProvider1.class.getMethod("service1", EClass.class), service
				.getMethod());
		assertEquals(((TestMaskServicesProvider1)service.getInstance()).service1, service);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestMaskServicesProvider1.class.getMethod("service1", EClass.class),
				((JavaMethodService)result.getRegistered().get(0)).getMethod());
	}

	@Test
	public void maskedByRegistrationServiceProvider() throws InvalidAcceleoPackageException,
			NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = engine.registerServices(TestMaskServicesProvider1.class);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestMaskServicesProvider1.class.getMethod("service1", EClass.class),
				((JavaMethodService)result.getRegistered().get(0)).getMethod());

		result = engine.registerServiceInstance(new TestServicesProvider1(provider));
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(1, result.getIsMaskedBy().size());
		final Method expectedIsMaskedByMethod = TestMaskServicesProvider1.class.getMethod("service1",
				EClass.class);

		final Entry<IService, List<IService>> entry = result.getIsMaskedBy().entrySet().iterator().next();
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)entry.getKey()).getMethod());
		assertEquals(1, entry.getValue().size());
		final Method actualIsMaskedByMethod = ((JavaMethodService)entry.getValue().get(0)).getMethod();

		assertEquals(expectedIsMaskedByMethod, actualIsMaskedByMethod);

		assertEquals(2, engine.getRegisteredServices().size());
		assertEquals(1, engine.getRegisteredServices().get(TestServicesProvider1.class).size());
		assertEquals(1, engine.getRegisteredServices().get(TestMaskServicesProvider1.class).size());

		JavaMethodService service = (JavaMethodService)engine.lookup("service1", new Class<?>[] {
				EClassifier.class });
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class), service
				.getMethod());
		assertEquals(((TestServicesProvider1)service.getInstance()).service1, service);
		service = (JavaMethodService)engine.lookup("service1", new Class<?>[] {EClass.class });
		assertEquals(TestMaskServicesProvider1.class.getMethod("service1", EClass.class), service
				.getMethod());
		assertEquals(((TestMaskServicesProvider1)service.getInstance()).service1, service);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getMethod());
	}

	@Test
	public void withCrossReferencerRegistrationServiceProvider() throws InvalidAcceleoPackageException,
			NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final ServiceRegistrationResult result = engine.registerServiceInstance(new TestServicesProvider1(
				provider));
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getRegisteredServices().size());
		assertEquals(1, engine.getRegisteredServices().get(TestServicesProvider1.class).size());
		assertTrue(((JavaMethodService)engine.getRegisteredServices().get(TestServicesProvider1.class)
				.iterator().next()).getInstance() instanceof TestServicesProvider1);
		TestServicesProvider1 instance = (TestServicesProvider1)((JavaMethodService)engine
				.getRegisteredServices().get(TestServicesProvider1.class).iterator().next()).getInstance();
		assertEquals(provider, instance.crossReferencer);

		final JavaMethodService service = (JavaMethodService)engine.lookup("service1", new Class<?>[] {
				EClassifier.class });
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class), service
				.getMethod());
		assertEquals(((TestServicesProvider1)service.getInstance()).service1, service);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getMethod());
	}

	@Test
	public void isServiceMethodFromObject() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final Method method = Object.class.getMethod("equals", Object.class);
		assertNotNull(method);
		assertFalse(engine.isServiceMethod(this, method));
	}

	@Test
	public void isServiceMethodNoParameter() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final Method method = MethodHolder.class.getMethod("testNotServiceMethodNoParameter");
		assertNotNull(method);
		assertFalse(engine.isServiceMethod(this, method));
	}

	@Test
	public void isServiceMethodNotStaticNoInstance() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final Method method = MethodHolder.class.getMethod("testServiceMethod", Object.class);
		assertNotNull(method);
		assertFalse(engine.isServiceMethod(null, method));
	}

	@Test
	public void isServiceMethodNotStaticInstance() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final Method method = MethodHolder.class.getMethod("testServiceMethod", Object.class);
		assertNotNull(method);
		assertTrue(engine.isServiceMethod(this, method));
	}

	@Test
	public void isServiceMethodStaticNoInstance() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final Method method = MethodHolder.class.getMethod("testStaticServiceMethod", Object.class);
		assertNotNull(method);
		assertTrue(engine.isServiceMethod(null, method));
	}

	@Test
	public void isServiceMethodStaticInstance() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final Method method = MethodHolder.class.getMethod("testStaticServiceMethod", Object.class);
		assertNotNull(method);
		assertTrue(engine.isServiceMethod(this, method));
	}

	@Test
	public void isRegisteredService() throws InvalidAcceleoPackageException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		assertFalse(engine.isRegisteredService(TestServices1.class));
		engine.registerServiceInstance(new TestServices1(provider));
		assertTrue(engine.isRegisteredService(TestServices1.class));
	}

	@Test
	public void removeServicesEmpty() {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		engine.removeServices(ExtendedTestServices1.class);

		assertEquals(0, engine.getRegisteredServices().size());
		assertEquals(0, engine.getServices().size());
	}

	@Test
	public void removeServices() throws InvalidAcceleoPackageException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		engine.registerServiceInstance(new ExtendedTestServices1(provider));

		assertEquals(1, engine.getRegisteredServices().size());
		assertEquals(2, engine.getServices().size());

		engine.removeServices(ExtendedTestServices1.class);

		assertEquals(0, engine.getRegisteredServices().size());
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
	public void getServices() throws InvalidAcceleoPackageException, NoSuchMethodException,
			SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);
		final Set<Class<?>> types = new LinkedHashSet<Class<?>>();
		types.add(EClassifier.class);

		engine.registerServiceInstance(new ExtendedTestServices1(provider));
		final Set<IService> services = engine.getServices(types);
		assertEquals(2, services.size());
		final Iterator<IService> it = services.iterator();
		assertEquals(ExtendedTestServices1.class.getMethod("service2", EClassifier.class),
				((JavaMethodService)it.next()).getMethod());
		assertEquals(ExtendedTestServices1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)it.next()).getMethod());
	}

	@Test
	public void lookupEmpty() {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		assertEquals(null, engine.lookup("service", new Class<?>[] {}));
	}

	@Test
	public void lookupLowerPriorityLowerType() throws InvalidAcceleoPackageException {
		final ITestLookupEngine engine = instanciate(null);
		final ServiceRegistrationResult registrationResult = engine
				.registerServiceInstance(new TestServiceProvider(0, EClass.class, 1, EClassifier.class));

		assertEquals(0, registrationResult.getDuplicated().size());
		assertEquals(0, registrationResult.getIsMaskedBy().size());
		assertEquals(1, registrationResult.getMasked().size());
		Entry<IService, List<IService>> entry = registrationResult.getMasked().entrySet().iterator().next();
		assertEquals(TestServiceProvider.Service2.class, entry.getKey().getClass());
		assertEquals(1, entry.getValue().size());
		assertEquals(TestServiceProvider.Service1.class, entry.getValue().get(0).getClass());
		assertEquals(2, registrationResult.getRegistered().size());
		assertEquals(TestServiceProvider.Service1.class, registrationResult.getRegistered().get(0).getClass());
		assertEquals(TestServiceProvider.Service2.class, registrationResult.getRegistered().get(1).getClass());

		IService service = engine.lookup(TestServiceProvider.SERVICE_NAME, new Class<?>[] {EClass.class });

		assertEquals(TestServiceProvider.Service2.class, service.getClass());

		service = engine.lookup(TestServiceProvider.SERVICE_NAME, new Class<?>[] {EClassifier.class });

		assertEquals(TestServiceProvider.Service2.class, service.getClass());
	}

	@Test
	public void lookupLowerPriorityEqualType() throws InvalidAcceleoPackageException {
		final ITestLookupEngine engine = instanciate(null);
		final ServiceRegistrationResult registrationResult = engine
				.registerServiceInstance(new TestServiceProvider(0, EClassifier.class, 1, EClassifier.class));

		assertEquals(0, registrationResult.getDuplicated().size());
		assertEquals(0, registrationResult.getIsMaskedBy().size());
		assertEquals(1, registrationResult.getMasked().size());
		Entry<IService, List<IService>> entry = registrationResult.getMasked().entrySet().iterator().next();
		assertEquals(TestServiceProvider.Service2.class, entry.getKey().getClass());
		assertEquals(1, entry.getValue().size());
		assertEquals(TestServiceProvider.Service1.class, entry.getValue().get(0).getClass());
		assertEquals(2, registrationResult.getRegistered().size());
		assertEquals(TestServiceProvider.Service1.class, registrationResult.getRegistered().get(0).getClass());
		assertEquals(TestServiceProvider.Service2.class, registrationResult.getRegistered().get(1).getClass());

		IService service = engine.lookup(TestServiceProvider.SERVICE_NAME, new Class<?>[] {EClass.class });

		assertEquals(TestServiceProvider.Service2.class, service.getClass());

		service = engine.lookup(TestServiceProvider.SERVICE_NAME, new Class<?>[] {EClassifier.class });

		assertEquals(TestServiceProvider.Service2.class, service.getClass());
	}

	@Test
	public void lookupLowerPriorityGreaterType() throws InvalidAcceleoPackageException {
		final ITestLookupEngine engine = instanciate(null);
		final ServiceRegistrationResult registrationResult = engine
				.registerServiceInstance(new TestServiceProvider(0, EClassifier.class, 1, EClass.class));

		assertEquals(0, registrationResult.getDuplicated().size());
		assertEquals(0, registrationResult.getIsMaskedBy().size());
		assertEquals(1, registrationResult.getMasked().size());
		Entry<IService, List<IService>> entry = registrationResult.getMasked().entrySet().iterator().next();
		assertEquals(TestServiceProvider.Service2.class, entry.getKey().getClass());
		assertEquals(1, entry.getValue().size());
		assertEquals(TestServiceProvider.Service1.class, entry.getValue().get(0).getClass());
		assertEquals(2, registrationResult.getRegistered().size());
		assertEquals(TestServiceProvider.Service1.class, registrationResult.getRegistered().get(0).getClass());
		assertEquals(TestServiceProvider.Service2.class, registrationResult.getRegistered().get(1).getClass());

		IService service = engine.lookup(TestServiceProvider.SERVICE_NAME, new Class<?>[] {EClass.class });

		assertEquals(TestServiceProvider.Service2.class, service.getClass());

		service = engine.lookup(TestServiceProvider.SERVICE_NAME, new Class<?>[] {EClassifier.class });

		assertEquals(TestServiceProvider.Service1.class, service.getClass());
	}

	@Test
	public void lookupEqualPriorityLowerType() throws InvalidAcceleoPackageException {
		final ITestLookupEngine engine = instanciate(null);
		final ServiceRegistrationResult registrationResult = engine
				.registerServiceInstance(new TestServiceProvider(0, EClass.class, 0, EClassifier.class));

		assertEquals(0, registrationResult.getDuplicated().size());
		assertEquals(1, registrationResult.getIsMaskedBy().size());
		Entry<IService, List<IService>> entry = registrationResult.getIsMaskedBy().entrySet().iterator()
				.next();
		assertEquals(TestServiceProvider.Service2.class, entry.getKey().getClass());
		assertEquals(1, entry.getValue().size());
		assertEquals(TestServiceProvider.Service1.class, entry.getValue().get(0).getClass());
		assertEquals(0, registrationResult.getMasked().size());
		assertEquals(2, registrationResult.getRegistered().size());
		assertEquals(TestServiceProvider.Service1.class, registrationResult.getRegistered().get(0).getClass());
		assertEquals(TestServiceProvider.Service2.class, registrationResult.getRegistered().get(1).getClass());

		IService service = engine.lookup(TestServiceProvider.SERVICE_NAME, new Class<?>[] {EClass.class });

		assertEquals(TestServiceProvider.Service1.class, service.getClass());

		service = engine.lookup(TestServiceProvider.SERVICE_NAME, new Class<?>[] {EClassifier.class });

		assertEquals(TestServiceProvider.Service2.class, service.getClass());
	}

	@Test
	public void lookupEqualPriorityEqualType() throws InvalidAcceleoPackageException {
		final ITestLookupEngine engine = instanciate(null);
		final ServiceRegistrationResult registrationResult = engine
				.registerServiceInstance(new TestServiceProvider(0, EClassifier.class, 0, EClassifier.class));

		assertEquals(1, registrationResult.getDuplicated().size());
		Entry<IService, List<IService>> entry = registrationResult.getDuplicated().entrySet().iterator()
				.next();
		assertEquals(TestServiceProvider.Service2.class, entry.getKey().getClass());
		assertEquals(1, entry.getValue().size());
		assertEquals(TestServiceProvider.Service1.class, entry.getValue().get(0).getClass());
		assertEquals(0, registrationResult.getIsMaskedBy().size());
		assertEquals(0, registrationResult.getMasked().size());
		assertEquals(2, registrationResult.getRegistered().size());
		assertEquals(TestServiceProvider.Service1.class, registrationResult.getRegistered().get(0).getClass());
		assertEquals(TestServiceProvider.Service2.class, registrationResult.getRegistered().get(1).getClass());

		IService service = engine.lookup(TestServiceProvider.SERVICE_NAME, new Class<?>[] {EClass.class });

		assertEquals(TestServiceProvider.Service2.class, service.getClass());

		service = engine.lookup(TestServiceProvider.SERVICE_NAME, new Class<?>[] {EClassifier.class });

		assertEquals(TestServiceProvider.Service2.class, service.getClass());
	}

	@Test
	public void lookupEqualPriorityGreaterType() throws InvalidAcceleoPackageException {
		final ITestLookupEngine engine = instanciate(null);
		final ServiceRegistrationResult registrationResult = engine
				.registerServiceInstance(new TestServiceProvider(0, EClassifier.class, 0, EClass.class));

		assertEquals(0, registrationResult.getDuplicated().size());
		assertEquals(0, registrationResult.getIsMaskedBy().size());
		assertEquals(1, registrationResult.getMasked().size());
		Entry<IService, List<IService>> entry = registrationResult.getMasked().entrySet().iterator().next();
		assertEquals(TestServiceProvider.Service2.class, entry.getKey().getClass());
		assertEquals(1, entry.getValue().size());
		assertEquals(TestServiceProvider.Service1.class, entry.getValue().get(0).getClass());

		assertEquals(2, registrationResult.getRegistered().size());
		assertEquals(TestServiceProvider.Service1.class, registrationResult.getRegistered().get(0).getClass());
		assertEquals(TestServiceProvider.Service2.class, registrationResult.getRegistered().get(1).getClass());

		IService service = engine.lookup(TestServiceProvider.SERVICE_NAME, new Class<?>[] {EClass.class });

		assertEquals(TestServiceProvider.Service2.class, service.getClass());

		service = engine.lookup(TestServiceProvider.SERVICE_NAME, new Class<?>[] {EClassifier.class });

		assertEquals(TestServiceProvider.Service1.class, service.getClass());
	}

	@Test
	public void lookupGreaterPriorityLowerType() throws InvalidAcceleoPackageException {
		final ITestLookupEngine engine = instanciate(null);
		final ServiceRegistrationResult registrationResult = engine
				.registerServiceInstance(new TestServiceProvider(1, EClass.class, 0, EClassifier.class));

		assertEquals(0, registrationResult.getDuplicated().size());
		assertEquals(1, registrationResult.getIsMaskedBy().size());
		Entry<IService, List<IService>> entry = registrationResult.getIsMaskedBy().entrySet().iterator()
				.next();
		assertEquals(TestServiceProvider.Service2.class, entry.getKey().getClass());
		assertEquals(1, entry.getValue().size());
		assertEquals(TestServiceProvider.Service1.class, entry.getValue().get(0).getClass());
		assertEquals(0, registrationResult.getMasked().size());
		assertEquals(2, registrationResult.getRegistered().size());
		assertEquals(TestServiceProvider.Service1.class, registrationResult.getRegistered().get(0).getClass());
		assertEquals(TestServiceProvider.Service2.class, registrationResult.getRegistered().get(1).getClass());

		IService service = engine.lookup(TestServiceProvider.SERVICE_NAME, new Class<?>[] {EClass.class });

		assertEquals(TestServiceProvider.Service1.class, service.getClass());

		service = engine.lookup(TestServiceProvider.SERVICE_NAME, new Class<?>[] {EClassifier.class });

		assertEquals(TestServiceProvider.Service2.class, service.getClass());
	}

	@Test
	public void lookupGreaterPriorityEqualType() throws InvalidAcceleoPackageException {
		final ITestLookupEngine engine = instanciate(null);
		final ServiceRegistrationResult registrationResult = engine
				.registerServiceInstance(new TestServiceProvider(1, EClassifier.class, 0, EClassifier.class));

		assertEquals(0, registrationResult.getDuplicated().size());
		assertEquals(1, registrationResult.getIsMaskedBy().size());
		Entry<IService, List<IService>> entry = registrationResult.getIsMaskedBy().entrySet().iterator()
				.next();
		assertEquals(TestServiceProvider.Service2.class, entry.getKey().getClass());
		assertEquals(1, entry.getValue().size());
		assertEquals(TestServiceProvider.Service1.class, entry.getValue().get(0).getClass());
		assertEquals(0, registrationResult.getMasked().size());
		assertEquals(2, registrationResult.getRegistered().size());
		assertEquals(TestServiceProvider.Service1.class, registrationResult.getRegistered().get(0).getClass());
		assertEquals(TestServiceProvider.Service2.class, registrationResult.getRegistered().get(1).getClass());

		IService service = engine.lookup(TestServiceProvider.SERVICE_NAME, new Class<?>[] {EClass.class });

		assertEquals(TestServiceProvider.Service1.class, service.getClass());

		service = engine.lookup(TestServiceProvider.SERVICE_NAME, new Class<?>[] {EClassifier.class });

		assertEquals(TestServiceProvider.Service1.class, service.getClass());
	}

	@Test
	public void lookupGreaterPriorityGreaterType() throws InvalidAcceleoPackageException {
		final ITestLookupEngine engine = instanciate(null);
		final ServiceRegistrationResult registrationResult = engine
				.registerServiceInstance(new TestServiceProvider(1, EClassifier.class, 0, EClass.class));

		assertEquals(0, registrationResult.getDuplicated().size());
		assertEquals(1, registrationResult.getIsMaskedBy().size());
		Entry<IService, List<IService>> entry = registrationResult.getIsMaskedBy().entrySet().iterator()
				.next();
		assertEquals(TestServiceProvider.Service2.class, entry.getKey().getClass());
		assertEquals(1, entry.getValue().size());
		assertEquals(TestServiceProvider.Service1.class, entry.getValue().get(0).getClass());
		assertEquals(0, registrationResult.getMasked().size());
		assertEquals(2, registrationResult.getRegistered().size());
		assertEquals(TestServiceProvider.Service1.class, registrationResult.getRegistered().get(0).getClass());
		assertEquals(TestServiceProvider.Service2.class, registrationResult.getRegistered().get(1).getClass());

		IService service = engine.lookup(TestServiceProvider.SERVICE_NAME, new Class<?>[] {EClass.class });

		assertEquals(TestServiceProvider.Service1.class, service.getClass());

		service = engine.lookup(TestServiceProvider.SERVICE_NAME, new Class<?>[] {EClassifier.class });

		assertEquals(TestServiceProvider.Service1.class, service.getClass());
	}

}
