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
package org.eclipse.acceleo.query.tests.runtime.lookup.basic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.CrossReferenceProvider;
import org.eclipse.acceleo.query.runtime.ILookupEngine;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.ServiceRegistrationResult;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.acceleo.query.runtime.impl.AbstractServiceProvider;
import org.eclipse.acceleo.query.runtime.impl.JavaMethodService;
import org.eclipse.acceleo.query.runtime.lookup.basic.BasicLookupEngine;
import org.eclipse.acceleo.query.runtime.lookup.basic.CacheLookupEngine;
import org.eclipse.acceleo.query.runtime.lookup.basic.ServiceStore;
import org.eclipse.acceleo.query.services.AnyServices;
import org.eclipse.acceleo.query.services.BooleanServices;
import org.eclipse.acceleo.query.services.CollectionServices;
import org.eclipse.acceleo.query.services.ComparableServices;
import org.eclipse.acceleo.query.services.EObjectServices;
import org.eclipse.acceleo.query.services.NumberServices;
import org.eclipse.acceleo.query.services.PropertiesServices;
import org.eclipse.acceleo.query.services.ResourceServices;
import org.eclipse.acceleo.query.services.StringServices;
import org.eclipse.acceleo.query.services.XPathServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.EcorePackage;
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

		ServiceRegistrationResult registerService(IService<?> service);

		IService<?> removeService(IService<?> service);

		ServiceStore getServices();

		IReadOnlyQueryEnvironment getQueryEnvironment();

	}

	private static class TestBasicLookupEngine extends BasicLookupEngine implements ITestLookupEngine {

		public TestBasicLookupEngine(CrossReferenceProvider crossReferencer) {
			super(Query.newEnvironmentWithDefaultServices(crossReferencer));
		}

		@Override
		public ServiceStore getServices() {
			return super.getServices();
		}

		@Override
		public IReadOnlyQueryEnvironment getQueryEnvironment() {
			return queryEnvironment;
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

		@Override
		public IReadOnlyQueryEnvironment getQueryEnvironment() {
			return queryEnvironment;
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

		private IService<?> service1;

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
		protected IService<Method> getService(Method method) {
			IService<Method> result = new JavaMethodService(method, this);

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

		private IService<?> service1;

		public Boolean service1(EClassifier eObj) {
			return Boolean.FALSE;
		}

		@Override
		protected IService<Method> getService(Method method) {
			IService<Method> result = new JavaMethodService(method, this);

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

		private IService<?> service1;

		public Boolean service1(EClass eCls) {
			return Boolean.FALSE;
		}

		@Override
		protected IService<Method> getService(Method method) {
			IService<Method> result = new JavaMethodService(method, this);

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

		private IService<?> service2;

		public Boolean service2(EClassifier eObj) {
			return Boolean.FALSE;
		}

		@Override
		protected IService<Method> getService(Method method) {
			final IService<Method> result;

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
	public void simpleRegistration() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final ServiceRegistrationResult result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(), new TestServices1(
				provider))) {
			result.merge(engine.registerService(service));
		}
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getRegisteredServices().size());

		final JavaMethodService service = (JavaMethodService)engine.lookup("service1", new IType[] {
				new ClassType(engine.getQueryEnvironment(), EClassifier.class) });
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), service.getOrigin());

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), ((JavaMethodService)result
				.getRegistered().get(0)).getOrigin());
	}

	@Test
	public void staticRegistration() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final ServiceRegistrationResult result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				TestStaticServices.class)) {
			result.merge(engine.registerService(service));
		}
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getRegisteredServices().size());

		final JavaMethodService service = (JavaMethodService)engine.lookup("service1", new IType[] {
				new ClassType(engine.getQueryEnvironment(), EClassifier.class) });
		assertEquals(TestStaticServices.class.getMethod("service1", EClassifier.class), service.getOrigin());

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestStaticServices.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getOrigin());
	}

	@Test
	public void extendedRegistration() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final ServiceRegistrationResult result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				new ExtendedTestServices1(provider))) {
			result.merge(engine.registerService(service));
		}
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(2, engine.getRegisteredServices().size());

		JavaMethodService service = (JavaMethodService)engine.lookup("service1", new IType[] {new ClassType(
				engine.getQueryEnvironment(), EClassifier.class) });
		assertEquals(ExtendedTestServices1.class.getMethod("service1", EClassifier.class), service
				.getOrigin());
		service = (JavaMethodService)engine.lookup("service2", new IType[] {new ClassType(engine
				.getQueryEnvironment(), EClassifier.class) });
		assertEquals(ExtendedTestServices1.class.getMethod("service2", EClassifier.class), service
				.getOrigin());

		assertEquals(2, result.getRegistered().size());
		assertEquals(ExtendedTestServices1.class.getMethod("service2", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getOrigin());
		assertEquals(ExtendedTestServices1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(1)).getOrigin());
	}

	@Test
	public void doubleRegistration() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(), new TestServices1(
				provider))) {
			result.merge(engine.registerService(service));
		}
		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), ((JavaMethodService)result
				.getRegistered().get(0)).getOrigin());

		result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(), new TestServices1(
				provider))) {
			result.merge(engine.registerService(service));
		}
		assertEquals(1, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getRegisteredServices().size());

		final JavaMethodService service = (JavaMethodService)engine.lookup("service1", new IType[] {
				new ClassType(engine.getQueryEnvironment(), EClassifier.class) });
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), service.getOrigin());

		assertEquals(0, result.getRegistered().size());
	}

	@Test
	public void duplicateRegistration() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(), new TestServices1(
				provider))) {
			result.merge(engine.registerService(service));
		}

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), ((JavaMethodService)result
				.getRegistered().get(0)).getOrigin());

		result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				TestDuplicateServices1.class)) {
			result.merge(engine.registerService(service));
		}
		assertEquals(1, result.getDuplicated().size());
		final Method expectedDuplicatedMethod = TestServices1.class.getMethod("service1", EClassifier.class);

		final Entry<IService<?>, List<IService<?>>> entry = result.getDuplicated().entrySet().iterator()
				.next();
		assertEquals(TestDuplicateServices1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)entry.getKey()).getOrigin());
		assertEquals(1, entry.getValue().size());
		final Method actualDuplicatedMethod = ((JavaMethodService)entry.getValue().get(0)).getOrigin();

		assertEquals(expectedDuplicatedMethod, actualDuplicatedMethod);
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(2, engine.getRegisteredServices().size());

		final JavaMethodService service = (JavaMethodService)engine.lookup("service1", new IType[] {
				new ClassType(engine.getQueryEnvironment(), EClassifier.class) });
		assertEquals(TestDuplicateServices1.class.getMethod("service1", EClassifier.class), service
				.getOrigin());

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestDuplicateServices1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getOrigin());
	}

	@Test
	public void maskRegistration() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(), new TestServices1(
				provider))) {
			result.merge(engine.registerService(service));
		}

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), ((JavaMethodService)result
				.getRegistered().get(0)).getOrigin());

		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				TestMaskServices1.class)) {
			result.merge(engine.registerService(service));
		}
		assertEquals(0, result.getDuplicated().size());
		assertEquals(1, result.getMasked().size());
		final Method expectedMaskMethod = TestServices1.class.getMethod("service1", EClassifier.class);
		assertEquals(1, result.getMasked().entrySet().size());
		final Entry<IService<?>, List<IService<?>>> entry = result.getMasked().entrySet().iterator().next();
		assertEquals(TestMaskServices1.class.getMethod("service1", EClass.class), ((JavaMethodService)entry
				.getKey()).getOrigin());
		assertEquals(1, entry.getValue().size());
		final Method actualMaskMethod = ((JavaMethodService)entry.getValue().get(0)).getOrigin();

		assertEquals(expectedMaskMethod, actualMaskMethod);
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(2, engine.getRegisteredServices().size());

		JavaMethodService service = (JavaMethodService)engine.lookup("service1", new IType[] {new ClassType(
				engine.getQueryEnvironment(), EClassifier.class) });
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), service.getOrigin());
		service = (JavaMethodService)engine.lookup("service1", new IType[] {new ClassType(engine
				.getQueryEnvironment(), EClass.class) });
		assertEquals(TestMaskServices1.class.getMethod("service1", EClass.class), service.getOrigin());

		assertEquals(2, result.getRegistered().size());
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), ((JavaMethodService)result
				.getRegistered().get(0)).getOrigin());
		assertEquals(TestMaskServices1.class.getMethod("service1", EClass.class), ((JavaMethodService)result
				.getRegistered().get(1)).getOrigin());
	}

	@Test
	public void maskedByRegistration() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				TestMaskServices1.class)) {
			result.merge(engine.registerService(service));
		}

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestMaskServices1.class.getMethod("service1", EClass.class), ((JavaMethodService)result
				.getRegistered().get(0)).getOrigin());

		result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(), new TestServices1(
				provider))) {
			result.merge(engine.registerService(service));
		}
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(1, result.getIsMaskedBy().size());
		final Method expectedIsMaskedByMethod = TestMaskServices1.class.getMethod("service1", EClass.class);

		final Entry<IService<?>, List<IService<?>>> entry = result.getIsMaskedBy().entrySet().iterator()
				.next();
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), ((JavaMethodService)entry
				.getKey()).getOrigin());
		assertEquals(1, entry.getValue().size());
		final Method actualIsMaskedByMethod = ((JavaMethodService)entry.getValue().get(0)).getOrigin();

		assertEquals(expectedIsMaskedByMethod, actualIsMaskedByMethod);

		assertEquals(2, engine.getRegisteredServices().size());

		JavaMethodService service = (JavaMethodService)engine.lookup("service1", new IType[] {new ClassType(
				engine.getQueryEnvironment(), EClassifier.class) });
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), service.getOrigin());
		service = (JavaMethodService)engine.lookup("service1", new IType[] {new ClassType(engine
				.getQueryEnvironment(), EClass.class) });
		assertEquals(TestMaskServices1.class.getMethod("service1", EClass.class), service.getOrigin());

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), ((JavaMethodService)result
				.getRegistered().get(0)).getOrigin());
	}

	@Test
	public void withCrossReferencerRegistration() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final ServiceRegistrationResult result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(), new TestServices1(
				provider))) {
			result.merge(engine.registerService(service));
		}
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getRegisteredServices().size());
		final Iterator<IService<?>> iterator = engine.getRegisteredServices().iterator();
		final JavaMethodService javaMethodService = (JavaMethodService)iterator.next();
		assertTrue(javaMethodService.getInstance() instanceof TestServices1);
		TestServices1 instance = (TestServices1)javaMethodService.getInstance();
		assertEquals(provider, instance.crossReferencer);

		final JavaMethodService service = (JavaMethodService)engine.lookup("service1", new IType[] {
				new ClassType(engine.getQueryEnvironment(), EClassifier.class) });
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), service.getOrigin());

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServices1.class.getMethod("service1", EClassifier.class), ((JavaMethodService)result
				.getRegistered().get(0)).getOrigin());
	}

	@Test
	public void simpleRegistrationServiceProvider() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final ServiceRegistrationResult result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				new TestServicesProvider1(provider))) {
			result.merge(engine.registerService(service));
		}
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getRegisteredServices().size());

		final JavaMethodService service = (JavaMethodService)engine.lookup("service1", new IType[] {
				new ClassType(engine.getQueryEnvironment(), EClassifier.class) });
		assertEquals(((TestServicesProvider1)service.getInstance()).service1, service);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getOrigin());
	}

	@Test
	public void extendedRegistrationServiceProvider() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final ServiceRegistrationResult result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				new ExtendedTestServicesProvider1(provider))) {
			result.merge(engine.registerService(service));
		}
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(2, engine.getRegisteredServices().size());

		JavaMethodService service = (JavaMethodService)engine.lookup("service1", new IType[] {new ClassType(
				engine.getQueryEnvironment(), EClassifier.class) });
		assertEquals(ExtendedTestServicesProvider1.class.getMethod("service1", EClassifier.class), service
				.getOrigin());
		assertEquals(((TestServicesProvider1)service.getInstance()).service1, service);
		service = (JavaMethodService)engine.lookup("service2", new IType[] {new ClassType(engine
				.getQueryEnvironment(), EClassifier.class) });
		assertEquals(ExtendedTestServicesProvider1.class.getMethod("service2", EClassifier.class), service
				.getOrigin());
		assertEquals(((ExtendedTestServicesProvider1)service.getInstance()).service2, service);

		assertEquals(2, result.getRegistered().size());
		assertEquals(ExtendedTestServicesProvider1.class.getMethod("service2", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getOrigin());
		assertEquals(ExtendedTestServicesProvider1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(1)).getOrigin());
	}

	@Test
	public void doubleRegistrationServiceProvider() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				new TestServicesProvider1(provider))) {
			result.merge(engine.registerService(service));
		}

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getOrigin());

		result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				new TestServicesProvider1(provider))) {
			result.merge(engine.registerService(service));
		}
		assertEquals(1, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getRegisteredServices().size());

		final JavaMethodService service = (JavaMethodService)engine.lookup("service1", new IType[] {
				new ClassType(engine.getQueryEnvironment(), EClassifier.class) });
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class), service
				.getOrigin());
		assertEquals(((TestServicesProvider1)service.getInstance()).service1, service);

		assertEquals(0, result.getRegistered().size());
	}

	@Test
	public void duplicateRegistrationServiceProvider() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				new TestServicesProvider1(provider))) {
			result.merge(engine.registerService(service));
		}

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getOrigin());

		result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				TestDuplicateServicesProvider1.class)) {
			result.merge(engine.registerService(service));
		}
		assertEquals(1, result.getDuplicated().size());
		final Method expectedDuplicatedMethod = TestServicesProvider1.class.getMethod("service1",
				EClassifier.class);

		final Entry<IService<?>, List<IService<?>>> entry = result.getDuplicated().entrySet().iterator()
				.next();
		assertEquals(TestDuplicateServicesProvider1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)entry.getKey()).getOrigin());
		assertEquals(1, entry.getValue().size());
		final Method actualDuplicatedMethod = ((JavaMethodService)entry.getValue().get(0)).getOrigin();

		assertEquals(expectedDuplicatedMethod, actualDuplicatedMethod);
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(2, engine.getRegisteredServices().size());

		final JavaMethodService service = (JavaMethodService)engine.lookup("service1", new IType[] {
				new ClassType(engine.getQueryEnvironment(), EClassifier.class) });
		assertEquals(TestDuplicateServicesProvider1.class.getMethod("service1", EClassifier.class), service
				.getOrigin());
		assertEquals(((TestDuplicateServicesProvider1)service.getInstance()).service1, service);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestDuplicateServicesProvider1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getOrigin());
	}

	@Test
	public void maskRegistrationServiceProvider() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				new TestServicesProvider1(provider))) {
			result.merge(engine.registerService(service));
		}

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getOrigin());

		result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				TestMaskServicesProvider1.class)) {
			result.merge(engine.registerService(service));
		}
		assertEquals(0, result.getDuplicated().size());
		assertEquals(1, result.getMasked().size());
		final Method expectedMaskMethod = TestServicesProvider1.class.getMethod("service1",
				EClassifier.class);

		final Entry<IService<?>, List<IService<?>>> entry = result.getMasked().entrySet().iterator().next();
		assertEquals(TestMaskServicesProvider1.class.getMethod("service1", EClass.class),
				((JavaMethodService)entry.getKey()).getOrigin());
		assertEquals(1, entry.getValue().size());
		final Method actualMaskMethod = ((JavaMethodService)entry.getValue().get(0)).getOrigin();

		assertEquals(expectedMaskMethod, actualMaskMethod);
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(2, engine.getRegisteredServices().size());

		JavaMethodService service = (JavaMethodService)engine.lookup("service1", new IType[] {new ClassType(
				engine.getQueryEnvironment(), EClassifier.class) });
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class), service
				.getOrigin());
		assertEquals(((TestServicesProvider1)service.getInstance()).service1, service);
		service = (JavaMethodService)engine.lookup("service1", new IType[] {new ClassType(engine
				.getQueryEnvironment(), EClass.class) });
		assertEquals(TestMaskServicesProvider1.class.getMethod("service1", EClass.class), service
				.getOrigin());
		assertEquals(((TestMaskServicesProvider1)service.getInstance()).service1, service);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestMaskServicesProvider1.class.getMethod("service1", EClass.class),
				((JavaMethodService)result.getRegistered().get(0)).getOrigin());
	}

	@Test
	public void maskedByRegistrationServiceProvider() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		ServiceRegistrationResult result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				TestMaskServicesProvider1.class)) {
			result.merge(engine.registerService(service));
		}

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestMaskServicesProvider1.class.getMethod("service1", EClass.class),
				((JavaMethodService)result.getRegistered().get(0)).getOrigin());

		result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				new TestServicesProvider1(provider))) {
			result.merge(engine.registerService(service));
		}
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(1, result.getIsMaskedBy().size());
		final Method expectedIsMaskedByMethod = TestMaskServicesProvider1.class.getMethod("service1",
				EClass.class);

		final Entry<IService<?>, List<IService<?>>> entry = result.getIsMaskedBy().entrySet().iterator()
				.next();
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)entry.getKey()).getOrigin());
		assertEquals(1, entry.getValue().size());
		final Method actualIsMaskedByMethod = ((JavaMethodService)entry.getValue().get(0)).getOrigin();

		assertEquals(expectedIsMaskedByMethod, actualIsMaskedByMethod);

		assertEquals(2, engine.getRegisteredServices().size());

		JavaMethodService service = (JavaMethodService)engine.lookup("service1", new IType[] {new ClassType(
				engine.getQueryEnvironment(), EClassifier.class) });
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class), service
				.getOrigin());
		assertEquals(((TestServicesProvider1)service.getInstance()).service1, service);
		service = (JavaMethodService)engine.lookup("service1", new IType[] {new ClassType(engine
				.getQueryEnvironment(), EClass.class) });
		assertEquals(TestMaskServicesProvider1.class.getMethod("service1", EClass.class), service
				.getOrigin());
		assertEquals(((TestMaskServicesProvider1)service.getInstance()).service1, service);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getOrigin());
	}

	@Test
	public void withCrossReferencerRegistrationServiceProvider() throws NoSuchMethodException,
			SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final ServiceRegistrationResult result = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				new TestServicesProvider1(provider))) {
			result.merge(engine.registerService(service));
		}
		assertEquals(0, result.getDuplicated().size());
		assertEquals(0, result.getMasked().size());
		assertEquals(0, result.getIsMaskedBy().size());

		assertEquals(1, engine.getRegisteredServices().size());
		final Iterator<IService<?>> iterator = engine.getRegisteredServices().iterator();
		final JavaMethodService javaMethodService = (JavaMethodService)iterator.next();
		assertTrue(javaMethodService.getInstance() instanceof TestServicesProvider1);
		TestServicesProvider1 instance = (TestServicesProvider1)javaMethodService.getInstance();
		assertEquals(provider, instance.crossReferencer);

		final JavaMethodService service = (JavaMethodService)engine.lookup("service1", new IType[] {
				new ClassType(engine.getQueryEnvironment(), EClassifier.class) });
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class), service
				.getOrigin());
		assertEquals(((TestServicesProvider1)service.getInstance()).service1, service);

		assertEquals(1, result.getRegistered().size());
		assertEquals(TestServicesProvider1.class.getMethod("service1", EClassifier.class),
				((JavaMethodService)result.getRegistered().get(0)).getOrigin());
	}

	@Test
	public void isServiceMethodFromObject() throws NoSuchMethodException, SecurityException {
		final Method method = Object.class.getMethod("equals", Object.class);
		assertNotNull(method);
		assertFalse(ServiceUtils.isServiceMethod(this, method));
	}

	@Test
	public void isServiceMethodNoParameter() throws NoSuchMethodException, SecurityException {
		final Method method = MethodHolder.class.getMethod("testNotServiceMethodNoParameter");
		assertNotNull(method);
		assertFalse(ServiceUtils.isServiceMethod(this, method));
	}

	@Test
	public void isServiceMethodNotStaticNoInstance() throws NoSuchMethodException, SecurityException {
		final Method method = MethodHolder.class.getMethod("testServiceMethod", Object.class);
		assertNotNull(method);
		assertFalse(ServiceUtils.isServiceMethod(null, method));
	}

	@Test
	public void isServiceMethodNotStaticInstance() throws NoSuchMethodException, SecurityException {
		final Method method = MethodHolder.class.getMethod("testServiceMethod", Object.class);
		assertNotNull(method);
		assertTrue(ServiceUtils.isServiceMethod(this, method));
	}

	@Test
	public void isServiceMethodStaticNoInstance() throws NoSuchMethodException, SecurityException {
		final Method method = MethodHolder.class.getMethod("testStaticServiceMethod", Object.class);
		assertNotNull(method);
		assertTrue(ServiceUtils.isServiceMethod(null, method));
	}

	@Test
	public void isServiceMethodStaticInstance() throws NoSuchMethodException, SecurityException {
		final Method method = MethodHolder.class.getMethod("testStaticServiceMethod", Object.class);
		assertNotNull(method);
		assertTrue(ServiceUtils.isServiceMethod(this, method));
	}

	@Test
	public void isRegisteredService() {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final Set<IService<?>> services = ServiceUtils.getServices(engine.getQueryEnvironment(),
				TestServices1.class);
		for (IService<?> service : services) {
			engine.removeService(service);
		}

		for (IService<?> service : services) {
			assertFalse(engine.isRegisteredService(service));
		}
		ServiceRegistrationResult result = new ServiceRegistrationResult();
		for (IService<?> service : services) {
			result.merge(engine.registerService(service));
		}
		for (IService<?> service : services) {
			assertTrue(engine.isRegisteredService(service));
		}
	}

	@Test
	public void removeServiceEmpty() {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(), new TestServices1(
				provider))) {
			engine.removeService(service);
		}

		assertEquals(0, engine.getRegisteredServices().size());
		assertEquals(0, engine.getServices().size());
	}

	@Test
	public void removeService() {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		final Set<IService<?>> services = ServiceUtils.getServices(engine.getQueryEnvironment(),
				new ExtendedTestServices1(provider));
		for (IService<?> service : services) {
			engine.registerService(service);
		}

		assertEquals(2, engine.getRegisteredServices().size());
		assertEquals(2, engine.getServices().size());

		for (IService<?> service : services) {
			engine.removeService(service);
		}

		assertEquals(0, engine.getRegisteredServices().size());
		assertEquals(0, engine.getServices().size());
	}

	@Test
	public void getServicesEmpty() {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);
		final Set<IType> types = new LinkedHashSet<IType>();
		types.add(new ClassType(engine.getQueryEnvironment(), EClassifier.class));

		assertEquals(0, engine.getServices(types).size());
	}

	@Test
	public void getServices() throws NoSuchMethodException, SecurityException {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);
		final Set<IType> types = new LinkedHashSet<IType>();
		types.add(new ClassType(engine.getQueryEnvironment(), EClassifier.class));

		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				new ExtendedTestServices1(provider))) {
			engine.registerService(service);
		}
		final Set<IService<?>> services = engine.getServices(types);
		assertEquals(2, services.size());

		final Set<Method> methods = new LinkedHashSet<Method>();
		for (IService<?> service : services) {
			methods.add(((JavaMethodService)service).getOrigin());
		}
		assertTrue(methods.contains(ExtendedTestServices1.class.getMethod("service1", EClassifier.class)));
		assertTrue(methods.contains(ExtendedTestServices1.class.getMethod("service2", EClassifier.class)));
	}

	@Test
	public void lookupEmpty() {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);

		assertEquals(null, engine.lookup("service", new IType[] {}));
	}

	@Test
	public void lookupLowerPriorityLowerType() {
		final ITestLookupEngine engine = instanciate(null);
		final ServiceRegistrationResult registrationResult = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				new TestServiceProvider(0, EClass.class, 1, EClassifier.class))) {
			registrationResult.merge(engine.registerService(service));
		}

		assertEquals(0, registrationResult.getDuplicated().size());
		assertEquals(0, registrationResult.getIsMaskedBy().size());
		assertEquals(1, registrationResult.getMasked().size());
		Entry<IService<?>, List<IService<?>>> entry = registrationResult.getMasked().entrySet().iterator()
				.next();
		assertEquals(TestServiceProvider.Service2.class, entry.getKey().getClass());
		assertEquals(1, entry.getValue().size());
		assertEquals(TestServiceProvider.Service1.class, entry.getValue().get(0).getClass());
		assertEquals(2, registrationResult.getRegistered().size());
		assertEquals(TestServiceProvider.Service1.class, registrationResult.getRegistered().get(0)
				.getClass());
		assertEquals(TestServiceProvider.Service2.class, registrationResult.getRegistered().get(1)
				.getClass());

		IService<?> service = engine.lookup(TestServiceProvider.SERVICE_NAME, new IType[] {new ClassType(
				engine.getQueryEnvironment(), EClass.class) });

		assertEquals(TestServiceProvider.Service2.class, service.getClass());

		service = engine.lookup(TestServiceProvider.SERVICE_NAME, new IType[] {new ClassType(engine
				.getQueryEnvironment(), EClassifier.class) });

		assertEquals(TestServiceProvider.Service2.class, service.getClass());
	}

	@Test
	public void lookupLowerPriorityEqualType() {
		final ITestLookupEngine engine = instanciate(null);
		final ServiceRegistrationResult registrationResult = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				new TestServiceProvider(0, EClassifier.class, 1, EClassifier.class))) {
			registrationResult.merge(engine.registerService(service));
		}

		assertEquals(0, registrationResult.getDuplicated().size());
		assertEquals(0, registrationResult.getIsMaskedBy().size());
		assertEquals(1, registrationResult.getMasked().size());
		Entry<IService<?>, List<IService<?>>> entry = registrationResult.getMasked().entrySet().iterator()
				.next();
		assertEquals(TestServiceProvider.Service2.class, entry.getKey().getClass());
		assertEquals(1, entry.getValue().size());
		assertEquals(TestServiceProvider.Service1.class, entry.getValue().get(0).getClass());
		assertEquals(2, registrationResult.getRegistered().size());
		assertEquals(TestServiceProvider.Service1.class, registrationResult.getRegistered().get(0)
				.getClass());
		assertEquals(TestServiceProvider.Service2.class, registrationResult.getRegistered().get(1)
				.getClass());

		IService<?> service = engine.lookup(TestServiceProvider.SERVICE_NAME, new IType[] {new ClassType(
				engine.getQueryEnvironment(), EClass.class) });

		assertEquals(TestServiceProvider.Service2.class, service.getClass());

		service = engine.lookup(TestServiceProvider.SERVICE_NAME, new IType[] {new ClassType(engine
				.getQueryEnvironment(), EClassifier.class) });

		assertEquals(TestServiceProvider.Service2.class, service.getClass());
	}

	@Test
	public void lookupLowerPriorityGreaterType() {
		final ITestLookupEngine engine = instanciate(null);
		final ServiceRegistrationResult registrationResult = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				new TestServiceProvider(0, EClassifier.class, 1, EClass.class))) {
			registrationResult.merge(engine.registerService(service));
		}

		assertEquals(0, registrationResult.getDuplicated().size());
		assertEquals(0, registrationResult.getIsMaskedBy().size());
		assertEquals(1, registrationResult.getMasked().size());
		Entry<IService<?>, List<IService<?>>> entry = registrationResult.getMasked().entrySet().iterator()
				.next();
		assertEquals(TestServiceProvider.Service2.class, entry.getKey().getClass());
		assertEquals(1, entry.getValue().size());
		assertEquals(TestServiceProvider.Service1.class, entry.getValue().get(0).getClass());
		assertEquals(2, registrationResult.getRegistered().size());
		assertEquals(TestServiceProvider.Service1.class, registrationResult.getRegistered().get(0)
				.getClass());
		assertEquals(TestServiceProvider.Service2.class, registrationResult.getRegistered().get(1)
				.getClass());

		IService<?> service = engine.lookup(TestServiceProvider.SERVICE_NAME, new IType[] {new ClassType(
				engine.getQueryEnvironment(), EClass.class) });

		assertEquals(TestServiceProvider.Service2.class, service.getClass());

		service = engine.lookup(TestServiceProvider.SERVICE_NAME, new IType[] {new ClassType(engine
				.getQueryEnvironment(), EClassifier.class) });

		assertEquals(TestServiceProvider.Service1.class, service.getClass());
	}

	@Test
	public void lookupEqualPriorityLowerType() {
		final ITestLookupEngine engine = instanciate(null);
		final ServiceRegistrationResult registrationResult = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				new TestServiceProvider(0, EClass.class, 0, EClassifier.class))) {
			registrationResult.merge(engine.registerService(service));
		}

		assertEquals(0, registrationResult.getDuplicated().size());
		assertEquals(1, registrationResult.getIsMaskedBy().size());
		Entry<IService<?>, List<IService<?>>> entry = registrationResult.getIsMaskedBy().entrySet().iterator()
				.next();
		assertEquals(TestServiceProvider.Service2.class, entry.getKey().getClass());
		assertEquals(1, entry.getValue().size());
		assertEquals(TestServiceProvider.Service1.class, entry.getValue().get(0).getClass());
		assertEquals(0, registrationResult.getMasked().size());
		assertEquals(2, registrationResult.getRegistered().size());
		assertEquals(TestServiceProvider.Service1.class, registrationResult.getRegistered().get(0)
				.getClass());
		assertEquals(TestServiceProvider.Service2.class, registrationResult.getRegistered().get(1)
				.getClass());

		IService<?> service = engine.lookup(TestServiceProvider.SERVICE_NAME, new IType[] {new ClassType(
				engine.getQueryEnvironment(), EClass.class) });

		assertEquals(TestServiceProvider.Service1.class, service.getClass());

		service = engine.lookup(TestServiceProvider.SERVICE_NAME, new IType[] {new ClassType(engine
				.getQueryEnvironment(), EClassifier.class) });

		assertEquals(TestServiceProvider.Service2.class, service.getClass());
	}

	@Test
	public void lookupEqualPriorityEqualType() {
		final ITestLookupEngine engine = instanciate(null);
		final ServiceRegistrationResult registrationResult = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				new TestServiceProvider(0, EClassifier.class, 0, EClassifier.class))) {
			registrationResult.merge(engine.registerService(service));
		}

		assertEquals(1, registrationResult.getDuplicated().size());
		Entry<IService<?>, List<IService<?>>> entry = registrationResult.getDuplicated().entrySet().iterator()
				.next();
		assertEquals(TestServiceProvider.Service2.class, entry.getKey().getClass());
		assertEquals(1, entry.getValue().size());
		assertEquals(TestServiceProvider.Service1.class, entry.getValue().get(0).getClass());
		assertEquals(0, registrationResult.getIsMaskedBy().size());
		assertEquals(0, registrationResult.getMasked().size());
		assertEquals(2, registrationResult.getRegistered().size());
		assertEquals(TestServiceProvider.Service1.class, registrationResult.getRegistered().get(0)
				.getClass());
		assertEquals(TestServiceProvider.Service2.class, registrationResult.getRegistered().get(1)
				.getClass());

		IService<?> service = engine.lookup(TestServiceProvider.SERVICE_NAME, new IType[] {new ClassType(
				engine.getQueryEnvironment(), EClass.class) });

		assertEquals(TestServiceProvider.Service2.class, service.getClass());

		service = engine.lookup(TestServiceProvider.SERVICE_NAME, new IType[] {new ClassType(engine
				.getQueryEnvironment(), EClassifier.class) });

		assertEquals(TestServiceProvider.Service2.class, service.getClass());
	}

	@Test
	public void lookupEqualPriorityGreaterType() {
		final ITestLookupEngine engine = instanciate(null);
		final ServiceRegistrationResult registrationResult = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				new TestServiceProvider(0, EClassifier.class, 0, EClass.class))) {
			registrationResult.merge(engine.registerService(service));
		}

		assertEquals(0, registrationResult.getDuplicated().size());
		assertEquals(0, registrationResult.getIsMaskedBy().size());
		assertEquals(1, registrationResult.getMasked().size());
		Entry<IService<?>, List<IService<?>>> entry = registrationResult.getMasked().entrySet().iterator()
				.next();
		assertEquals(TestServiceProvider.Service2.class, entry.getKey().getClass());
		assertEquals(1, entry.getValue().size());
		assertEquals(TestServiceProvider.Service1.class, entry.getValue().get(0).getClass());

		assertEquals(2, registrationResult.getRegistered().size());
		assertEquals(TestServiceProvider.Service1.class, registrationResult.getRegistered().get(0)
				.getClass());
		assertEquals(TestServiceProvider.Service2.class, registrationResult.getRegistered().get(1)
				.getClass());

		IService<?> service = engine.lookup(TestServiceProvider.SERVICE_NAME, new IType[] {new ClassType(
				engine.getQueryEnvironment(), EClass.class) });

		assertEquals(TestServiceProvider.Service2.class, service.getClass());

		service = engine.lookup(TestServiceProvider.SERVICE_NAME, new IType[] {new ClassType(engine
				.getQueryEnvironment(), EClassifier.class) });

		assertEquals(TestServiceProvider.Service1.class, service.getClass());
	}

	@Test
	public void lookupGreaterPriorityLowerType() {
		final ITestLookupEngine engine = instanciate(null);
		final ServiceRegistrationResult registrationResult = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				new TestServiceProvider(1, EClass.class, 0, EClassifier.class))) {
			registrationResult.merge(engine.registerService(service));
		}

		assertEquals(0, registrationResult.getDuplicated().size());
		assertEquals(1, registrationResult.getIsMaskedBy().size());
		Entry<IService<?>, List<IService<?>>> entry = registrationResult.getIsMaskedBy().entrySet().iterator()
				.next();
		assertEquals(TestServiceProvider.Service2.class, entry.getKey().getClass());
		assertEquals(1, entry.getValue().size());
		assertEquals(TestServiceProvider.Service1.class, entry.getValue().get(0).getClass());
		assertEquals(0, registrationResult.getMasked().size());
		assertEquals(2, registrationResult.getRegistered().size());
		assertEquals(TestServiceProvider.Service1.class, registrationResult.getRegistered().get(0)
				.getClass());
		assertEquals(TestServiceProvider.Service2.class, registrationResult.getRegistered().get(1)
				.getClass());

		IService<?> service = engine.lookup(TestServiceProvider.SERVICE_NAME, new IType[] {new ClassType(
				engine.getQueryEnvironment(), EClass.class) });

		assertEquals(TestServiceProvider.Service1.class, service.getClass());

		service = engine.lookup(TestServiceProvider.SERVICE_NAME, new IType[] {new ClassType(engine
				.getQueryEnvironment(), EClassifier.class) });

		assertEquals(TestServiceProvider.Service2.class, service.getClass());
	}

	@Test
	public void lookupGreaterPriorityEqualType() {
		final ITestLookupEngine engine = instanciate(null);
		final ServiceRegistrationResult registrationResult = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				new TestServiceProvider(1, EClassifier.class, 0, EClassifier.class))) {
			registrationResult.merge(engine.registerService(service));
		}

		assertEquals(0, registrationResult.getDuplicated().size());
		assertEquals(1, registrationResult.getIsMaskedBy().size());
		Entry<IService<?>, List<IService<?>>> entry = registrationResult.getIsMaskedBy().entrySet().iterator()
				.next();
		assertEquals(TestServiceProvider.Service2.class, entry.getKey().getClass());
		assertEquals(1, entry.getValue().size());
		assertEquals(TestServiceProvider.Service1.class, entry.getValue().get(0).getClass());
		assertEquals(0, registrationResult.getMasked().size());
		assertEquals(2, registrationResult.getRegistered().size());
		assertEquals(TestServiceProvider.Service1.class, registrationResult.getRegistered().get(0)
				.getClass());
		assertEquals(TestServiceProvider.Service2.class, registrationResult.getRegistered().get(1)
				.getClass());

		IService<?> service = engine.lookup(TestServiceProvider.SERVICE_NAME, new IType[] {new ClassType(
				engine.getQueryEnvironment(), EClass.class) });

		assertEquals(TestServiceProvider.Service1.class, service.getClass());

		service = engine.lookup(TestServiceProvider.SERVICE_NAME, new IType[] {new ClassType(engine
				.getQueryEnvironment(), EClassifier.class) });

		assertEquals(TestServiceProvider.Service1.class, service.getClass());
	}

	@Test
	public void lookupGreaterPriorityGreaterType() {
		final ITestLookupEngine engine = instanciate(null);
		final ServiceRegistrationResult registrationResult = new ServiceRegistrationResult();
		for (IService<?> service : ServiceUtils.getServices(engine.getQueryEnvironment(),
				new TestServiceProvider(1, EClassifier.class, 0, EClass.class))) {
			registrationResult.merge(engine.registerService(service));
		}

		assertEquals(0, registrationResult.getDuplicated().size());
		assertEquals(1, registrationResult.getIsMaskedBy().size());
		Entry<IService<?>, List<IService<?>>> entry = registrationResult.getIsMaskedBy().entrySet().iterator()
				.next();
		assertEquals(TestServiceProvider.Service2.class, entry.getKey().getClass());
		assertEquals(1, entry.getValue().size());
		assertEquals(TestServiceProvider.Service1.class, entry.getValue().get(0).getClass());
		assertEquals(0, registrationResult.getMasked().size());
		assertEquals(2, registrationResult.getRegistered().size());
		assertEquals(TestServiceProvider.Service1.class, registrationResult.getRegistered().get(0)
				.getClass());
		assertEquals(TestServiceProvider.Service2.class, registrationResult.getRegistered().get(1)
				.getClass());

		IService<?> service = engine.lookup(TestServiceProvider.SERVICE_NAME, new IType[] {new ClassType(
				engine.getQueryEnvironment(), EClass.class) });

		assertEquals(TestServiceProvider.Service1.class, service.getClass());

		service = engine.lookup(TestServiceProvider.SERVICE_NAME, new IType[] {new ClassType(engine
				.getQueryEnvironment(), EClassifier.class) });

		assertEquals(TestServiceProvider.Service1.class, service.getClass());
	}

	@Test
	public void lookupPerf() {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);
		final Map<String, IType[]> services = new LinkedHashMap<String, IType[]>();

		registerServices(engine);
		for (IService<?> service : engine.getRegisteredServices()) {
			services.put(service.getName(), service.getParameterTypes(engine.getQueryEnvironment()).toArray(
					new IType[service.getNumberOfParameters()]));
		}

		for (int i = 0; i < 10000; i++) {
			for (Entry<String, IType[]> entry : services.entrySet()) {
				assertNotNull(engine.lookup(entry.getKey(), entry.getValue()));
			}
		}
	}

	@Test
	public void lookup_492250_twoParameters() {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);
		registerServices(engine);

		IService<?> service = engine.lookup("eAllContents", new IType[] {new ClassType(engine
				.getQueryEnvironment(), EClass.class), new ClassType(engine.getQueryEnvironment(),
						EClass.class) });

		assertNotNull(service);
	}

	@Test
	public void lookup_492250_oneParameter() {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);
		registerServices(engine);

		IService<?> service = engine.lookup("eAllContents", new IType[] {new ClassType(engine
				.getQueryEnvironment(), EClass.class) });

		assertNotNull(service);
	}

	@Test
	public void lookup_492250_twoThenOneParameters() {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);
		registerServices(engine);

		IService<?> service = engine.lookup("eAllContents", new IType[] {new ClassType(engine
				.getQueryEnvironment(), EClass.class), new ClassType(engine.getQueryEnvironment(),
						EClass.class) });

		assertNotNull(service);

		service = engine.lookup("eAllContents", new IType[] {new ClassType(engine.getQueryEnvironment(),
				EClass.class) });

		assertNotNull(service);
	}

	@Test
	public void lookup_492250_oneThenTwoParameters() {
		final CrossReferenceProvider provider = new TestCrossReferenceProvider();
		final ITestLookupEngine engine = instanciate(provider);
		registerServices(engine);

		IService<?> service = engine.lookup("eAllContents", new IType[] {new EClassifierType(engine
				.getQueryEnvironment(), EcorePackage.eINSTANCE.getEClass()) });

		assertNotNull(service);

		service = engine.lookup("eAllContents", new IType[] {new ClassType(engine.getQueryEnvironment(),
				EClass.class), new ClassType(engine.getQueryEnvironment(), EClass.class) });

		assertNotNull(service);
	}

	private void registerServices(ITestLookupEngine engine) {
		final Set<IService<?>> services = new LinkedHashSet<IService<?>>();

		services.addAll(ServiceUtils.getServices(engine.getQueryEnvironment(), new AnyServices(engine
				.getQueryEnvironment())));
		services.addAll(ServiceUtils.getServices(engine.getQueryEnvironment(), new EObjectServices(engine
				.getQueryEnvironment(), null, null)));
		services.addAll(ServiceUtils.getServices(engine.getQueryEnvironment(), new XPathServices(engine
				.getQueryEnvironment())));
		services.addAll(ServiceUtils.getServices(engine.getQueryEnvironment(), ComparableServices.class));
		services.addAll(ServiceUtils.getServices(engine.getQueryEnvironment(), NumberServices.class));
		services.addAll(ServiceUtils.getServices(engine.getQueryEnvironment(), StringServices.class));
		services.addAll(ServiceUtils.getServices(engine.getQueryEnvironment(), BooleanServices.class));
		services.addAll(ServiceUtils.getServices(engine.getQueryEnvironment(), CollectionServices.class));
		services.addAll(ServiceUtils.getServices(engine.getQueryEnvironment(), ResourceServices.class));
		services.addAll(ServiceUtils.getServices(engine.getQueryEnvironment(), new PropertiesServices(
				new Properties())));

		for (IService<?> service : services) {
			engine.registerService(service);
		}
	}

}
