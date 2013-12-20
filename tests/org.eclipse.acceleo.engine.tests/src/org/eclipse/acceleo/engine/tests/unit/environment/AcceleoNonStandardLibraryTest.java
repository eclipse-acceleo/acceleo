/*******************************************************************************
 * Copyright (c) 2009, 2013 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *     Christian W. Damus - Bug 424214 siblings() of resource roots
 *******************************************************************************/
package org.eclipse.acceleo.engine.tests.unit.environment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.eclipse.acceleo.common.utils.AcceleoNonStandardLibrary;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.internal.environment.AcceleoEnvironmentFactory;
import org.eclipse.acceleo.engine.internal.environment.AcceleoEvaluationEnvironment;
import org.eclipse.acceleo.engine.internal.environment.AcceleoLibraryOperationVisitor;
import org.eclipse.acceleo.engine.internal.environment.AcceleoPropertiesLookup;
import org.eclipse.acceleo.engine.tests.AcceleoEngineTestPlugin;
import org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.ecore.OCL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This will test the behavior of the Acceleo non standard library's operations.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("nls")
public class AcceleoNonStandardLibraryTest extends AbstractAcceleoTest {
	/** The evaluation environment to call for non standard operations on. */
	private AcceleoEvaluationEnvironment evaluationEnvironment;

	/** This will be created at setUp and disposed of at tearDown time. */
	private AcceleoEnvironmentFactory factory;

	/** EOperations defined in the non standard lib. */
	private final Map<String, List<EOperation>> nonStdLib = new HashMap<String, List<EOperation>>();

	/** Values that will be used to test non standard string operations. */
	private final String[] stringValues = new String[] {"a", "\u00e9\u00e8\u0020\u00f1", "", "Foehn12",
			"Standard sentence.", };

	{
		AcceleoNonStandardLibrary lib = new AcceleoNonStandardLibrary();

		List<EOperation> stringOperations = lib
				.getExistingOperations(AcceleoNonStandardLibrary.PRIMITIVE_STRING_NAME);
		List<EOperation> copyOperations = new ArrayList<EOperation>(stringOperations.size());
		for (EOperation operation : stringOperations) {
			copyOperations.add((EOperation)EcoreUtil.copy(operation));
		}
		nonStdLib.put(AcceleoNonStandardLibrary.PRIMITIVE_STRING_NAME, copyOperations);

		List<EOperation> oclAnyOperations = lib
				.getExistingOperations(AcceleoNonStandardLibrary.TYPE_OCLANY_NAME);
		copyOperations = new ArrayList<EOperation>(oclAnyOperations.size());
		for (EOperation operation : oclAnyOperations) {
			copyOperations.add((EOperation)EcoreUtil.copy(operation));
		}
		nonStdLib.put(AcceleoNonStandardLibrary.TYPE_OCLANY_NAME, copyOperations);

		List<EOperation> eObjectOperations = lib
				.getExistingOperations(AcceleoNonStandardLibrary.TYPE_EOBJECT_NAME);
		copyOperations = new ArrayList<EOperation>(eObjectOperations.size());
		for (EOperation operation : eObjectOperations) {
			copyOperations.add((EOperation)EcoreUtil.copy(operation));
		}
		nonStdLib.put(AcceleoNonStandardLibrary.TYPE_EOBJECT_NAME, copyOperations);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#setUp()
	 */
	@Before
	@Override
	public void setUp() {
		super.setUp();
		// only used for initialization
		this.init("NonStdLib");
		factory = new AcceleoEnvironmentFactory(generationRoot, module,
				new ArrayList<IAcceleoTextGenerationListener>(), new AcceleoPropertiesLookup(),
				previewStrategy, new BasicMonitor());
		final OCL ocl = OCL.newInstance(factory);
		evaluationEnvironment = (AcceleoEvaluationEnvironment)ocl.getEvaluationEnvironment();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getModuleLocation()
	 */
	@Override
	public String getModuleLocation() {
		// Reusing the generic engine test template. This is only used for setup.
		return "data/GenericEngine/generic_engine.mtl";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#getReferencePath()
	 */
	@Override
	public String getReferencePath() {
		return "NonStandardLibrary";
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.tests.unit.AbstractAcceleoTest#tearDown()
	 */
	@After
	@Override
	public void tearDown() {
		factory.dispose();
	}

	/**
	 * Tests the behavior of the non standard "ancestors()" operation on OclAny.
	 * <p>
	 * Expects the result to contain all of the containers of the given object.
	 * </p>
	 */
	@Test
	public void testOclAnyAncestorsUnParameterizable() {
		EOperation operation = getOperation(AcceleoNonStandardLibrary.TYPE_EOBJECT_NAME,
				AcceleoNonStandardLibrary.OPERATION_EOBJECT_ANCESTORS);

		final EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub = EcoreFactory.eINSTANCE.createEPackage();
		final EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		clazz.getEStructuralFeatures().add(attribute);
		subSub.getEClassifiers().add(clazz);
		sub.getESubpackages().add(subSub);
		root.getESubpackages().add(sub);

		Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment,
				operation, attribute);
		assertSame("Unexpected count of ancestors returned", 4, ((Collection<?>)result).size());
		final Iterator<?> children = ((Collection<?>)result).iterator();
		assertSame("The first container of the attribute should have been the class", clazz, children.next());
		assertSame("The second container of the attribute should have been the second sub-package", subSub,
				children.next());
		assertSame("The third container of the attribute should have been the first sub-package", sub,
				children.next());
		assertSame("The fourth container of the attribute should have been the root package", root, children
				.next());
	}

	/**
	 * Tests the behavior of the non standard "ancestors(OclAny)" operation on OclAny.
	 * <p>
	 * Expects the result to contain all of the containers of the given type for the given object.
	 * </p>
	 */
	@Test
	public void testOclAnyAncestorsParameterizable() {
		EOperation operation = getOperation(AcceleoNonStandardLibrary.TYPE_EOBJECT_NAME,
				AcceleoNonStandardLibrary.OPERATION_EOBJECT_ANCESTORS);

		final EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub = EcoreFactory.eINSTANCE.createEPackage();
		final EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		clazz.getEStructuralFeatures().add(attribute);
		subSub.getEClassifiers().add(clazz);
		sub.getESubpackages().add(subSub);
		root.getESubpackages().add(sub);

		Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment,
				operation, attribute, EcorePackage.eINSTANCE.getEPackage());
		assertSame("Unexpected count of ancestors returned", 3, ((Collection<?>)result).size());
		final Iterator<?> children = ((Collection<?>)result).iterator();
		assertSame("The first container of the attribute should have been the second sub-package", subSub,
				children.next());
		assertSame("The second container of the attribute should have been the first sub-package", sub,
				children.next());
		assertSame("The third container of the attribute should have been the root package", root, children
				.next());
	}

	/**
	 * Tests the behavior of the non standard "eAllContents()" operation on OclAny.
	 * <p>
	 * Expects the result to contain all of the Objects that can be iterated over through
	 * {@link EObject#eAllContents()}.
	 * </p>
	 */
	@Test
	public void testOclAnyEAllContentsUnparameterizable() {
		EOperation operation = getOperation(AcceleoNonStandardLibrary.TYPE_EOBJECT_NAME,
				AcceleoNonStandardLibrary.OPERATION_EOBJECT_EALLCONTENTS);

		final EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub2 = EcoreFactory.eINSTANCE.createEPackage();
		final EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		clazz.getEStructuralFeatures().add(attribute);
		subSub.getEClassifiers().add(clazz);
		sub.getESubpackages().add(subSub);
		sub.getESubpackages().add(subSub2);
		root.getESubpackages().add(sub);

		Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment,
				operation, root);
		assertSame("Unexpected count of descendants returned", 5, ((Collection<?>)result).size());
		final Iterator<?> children = ((Collection<?>)result).iterator();
		assertSame("The first descendant of the root should have been the first sub-package", sub, children
				.next());
		assertSame("The second descendant of the root should have been the second sub-package", subSub,
				children.next());
		assertSame("The third descendant of the root should have been the EClass", clazz, children.next());
		assertSame("The fourth descendant of the root should have been the attribute", attribute, children
				.next());
		assertSame("The fifth descendant of the root should have been the third sub-package", subSub2,
				children.next());
	}

	/**
	 * Tests the behavior of the non standard "eAllContents(OclAny)" operation on OclAny.
	 * <p>
	 * Expects the result to contain all of the Objects that can be iterated over through
	 * {@link EObject#eAllContents()} of ther given type.
	 * </p>
	 */
	@Test
	public void testOclAnyEAllContentsParameterizable() {
		EOperation operation = getOperation(AcceleoNonStandardLibrary.TYPE_EOBJECT_NAME,
				AcceleoNonStandardLibrary.OPERATION_EOBJECT_EALLCONTENTS);

		final EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub2 = EcoreFactory.eINSTANCE.createEPackage();
		final EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		clazz.getEStructuralFeatures().add(attribute);
		subSub.getEClassifiers().add(clazz);
		sub.getESubpackages().add(subSub);
		sub.getESubpackages().add(subSub2);
		root.getESubpackages().add(sub);

		Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment,
				operation, root, EcorePackage.eINSTANCE.getEPackage());
		assertSame("Unexpected count of descendants returned", 3, ((Collection<?>)result).size());
		final Iterator<?> children = ((Collection<?>)result).iterator();
		assertSame("The first descendant of the root should have been the first sub-package", sub, children
				.next());
		assertSame("The second descendant of the root should have been the second sub-package", subSub,
				children.next());
		assertSame("The third descendant of the root should have been the third sub-package", subSub2,
				children.next());

		// Ensure the behavior when passing null as argument doesn't evolve
		try {
			AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment, operation, root,
					(EObject)null);
			fail("The non standard eAllContents(OclAny) operation previously threw NPEs when called with null argument");
		} catch (NullPointerException e) {
			// Expected behavior
		}
	}

	/**
	 * Tests the behavior of the non standard "eInverse()" operation on OclAny.
	 * <p>
	 * Expects the result to contain all of the Objects that have a reference towards self.
	 * </p>
	 */
	@Test
	public void testOclAnyEInverseUnparameterizable() {
		EOperation operation = getOperation(AcceleoNonStandardLibrary.TYPE_EOBJECT_NAME,
				AcceleoNonStandardLibrary.OPERATION_EOBJECT_EINVERSE);

		final EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub2 = EcoreFactory.eINSTANCE.createEPackage();
		final EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		final EClass clazz2 = EcoreFactory.eINSTANCE.createEClass();
		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		clazz.getEStructuralFeatures().add(attribute);
		subSub.getEClassifiers().add(clazz);
		sub.getESubpackages().add(subSub);
		subSub2.getEClassifiers().add(clazz2);
		sub.getESubpackages().add(subSub2);
		root.getESubpackages().add(sub);
		clazz.getESuperTypes().add(clazz2);

		Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment,
				operation, clazz2);
		assertSame("Unexpected count of inverse references returned", 2, ((Collection<?>)result).size());
		final Iterator<?> children = ((Collection<?>)result).iterator();
		assertSame("The first inverse reference on the second EClass should have been the first EClass",
				clazz, children.next());
		assertTrue("The second inverse reference on the second EClass should have been a GenericType",
				children.next() instanceof EGenericType);
	}

	/**
	 * Tests the behavior of the non standard "eInverse(OclAny)" operation on OclAny.
	 * <p>
	 * Expects the result to contain all of the Objects that have a reference towards self and are instances
	 * of the given type.
	 * </p>
	 */
	@Test
	public void testOclAnyEInverseParameterizable() {
		EOperation operation = getOperation(AcceleoNonStandardLibrary.TYPE_EOBJECT_NAME,
				AcceleoNonStandardLibrary.OPERATION_EOBJECT_EINVERSE);

		final EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub2 = EcoreFactory.eINSTANCE.createEPackage();
		final EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		final EClass clazz2 = EcoreFactory.eINSTANCE.createEClass();
		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		clazz.getEStructuralFeatures().add(attribute);
		subSub.getEClassifiers().add(clazz);
		sub.getESubpackages().add(subSub);
		subSub2.getEClassifiers().add(clazz2);
		sub.getESubpackages().add(subSub2);
		root.getESubpackages().add(sub);
		clazz.getESuperTypes().add(clazz2);

		Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment,
				operation, clazz2, EcorePackage.eINSTANCE.getEGenericType());
		assertSame("Unexpected count of inverse references returned", 1, ((Collection<?>)result).size());
		final Iterator<?> children = ((Collection<?>)result).iterator();
		assertTrue("The inverse reference on the second EClass should have been a GenericType", children
				.next() instanceof EGenericType);
	}

	/**
	 * Tests the behavior of the non standard "siblings()" operation on OclAny.
	 * <p>
	 * Expects the result to contain all of the siblings of the given object, excluding self.
	 * </p>
	 */
	@Test
	public void testOclAnySiblingsUnparameterizable() {
		EOperation operation = getOperation(AcceleoNonStandardLibrary.TYPE_EOBJECT_NAME,
				AcceleoNonStandardLibrary.OPERATION_EOBJECT_SIBLINGS);

		final EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub = EcoreFactory.eINSTANCE.createEPackage();
		final EClass clazz1 = EcoreFactory.eINSTANCE.createEClass();
		final EClass clazz2 = EcoreFactory.eINSTANCE.createEClass();
		final EClass clazz3 = EcoreFactory.eINSTANCE.createEClass();
		subSub.getEClassifiers().add(clazz1);
		subSub.getEClassifiers().add(clazz2);
		subSub.getEClassifiers().add(clazz3);
		sub.getESubpackages().add(subSub);
		root.getESubpackages().add(sub);

		Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment,
				operation, clazz2);
		assertSame("Unexpected count of siblings returned", 2, ((Collection<?>)result).size());
		Iterator<?> children = ((Collection<?>)result).iterator();
		assertSame("The sibling should have been the first added EClass.", clazz1, children.next());
		assertSame("The sibling should have been the third added EClass.", clazz3, children.next());

		result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment, operation,
				clazz1);
		assertSame("Unexpected count of siblings returned", 2, ((Collection<?>)result).size());
		children = ((Collection<?>)result).iterator();
		assertSame("The sibling should have been the second added EClass.", clazz2, children.next());
		assertSame("The sibling should have been the third added EClass.", clazz3, children.next());
	}

	/**
	 * Tests the behavior of the non standard "siblings()" operation on OclAny with an object that is a root
	 * (in the resource contents, no container).
	 * <p>
	 * Expects the result to contain all of the other roots of the given object's resource, excluding self.
	 * </p>
	 */
	@Test
	public void testOclAnySiblingsUnparameterizableResourceRoot() {
		EOperation operation = getOperation(AcceleoNonStandardLibrary.TYPE_EOBJECT_NAME,
				AcceleoNonStandardLibrary.OPERATION_EOBJECT_SIBLINGS);

		final Resource resource = new ResourceImpl(URI.createURI("junk://test"));
		final EPackage p1 = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage p2 = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage p3 = EcoreFactory.eINSTANCE.createEPackage();
		resource.getContents().addAll(Arrays.asList(p1, p2, p3));

		Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment,
				operation, p2);
		assertSame("Unexpected count of siblings returned", 2, ((Collection<?>)result).size());
		Iterator<?> children = ((Collection<?>)result).iterator();
		assertSame("The sibling should have been the first added EPackage.", p1, children.next());
		assertSame("The sibling should have been the third added EPackage.", p3, children.next());

		result = AcceleoLibraryOperationVisitor
				.callNonStandardOperation(evaluationEnvironment, operation, p1);
		assertSame("Unexpected count of siblings returned", 2, ((Collection<?>)result).size());
		children = ((Collection<?>)result).iterator();
		assertSame("The sibling should have been the second added EPackage.", p2, children.next());
		assertSame("The sibling should have been the third added EPackage.", p3, children.next());
	}

	/**
	 * Tests the behavior of the non standard "siblings()" operation on OclAny with an object that is a root
	 * (in the resource contents, no container) but whose containing resource has cross-resource-contained
	 * objects.
	 * <p>
	 * Expects the result to contain all of the other roots of the given object's resource, excluding self.
	 * </p>
	 */
	@Test
	public void testOclAnySiblingsUnparameterizableResourceRootWithCrossContainment() {
		EOperation operation = getOperation(AcceleoNonStandardLibrary.TYPE_EOBJECT_NAME,
				AcceleoNonStandardLibrary.OPERATION_EOBJECT_SIBLINGS);

		final Resource resource = new ResourceImpl(URI.createURI("junk://test"));
		final EPackage p1 = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage p2 = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage p3 = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub1 = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub2 = EcoreFactory.eINSTANCE.createEPackage();
		p1.getESubpackages().addAll(Arrays.asList(sub1, sub2));
		resource.getContents().addAll(Arrays.asList(p1, sub1, p2, sub2, p3));

		Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment,
				operation, p2);
		assertSame("Unexpected count of siblings returned", 2, ((Collection<?>)result).size());
		Iterator<?> children = ((Collection<?>)result).iterator();
		assertSame("The sibling should have been the first added EPackage.", p1, children.next());
		assertSame("The sibling should have been the third added EPackage.", p3, children.next());

		result = AcceleoLibraryOperationVisitor
				.callNonStandardOperation(evaluationEnvironment, operation, p1);
		assertSame("Unexpected count of siblings returned", 2, ((Collection<?>)result).size());
		children = ((Collection<?>)result).iterator();
		assertSame("The sibling should have been the second added EPackage.", p2, children.next());
		assertSame("The sibling should have been the third added EPackage.", p3, children.next());
	}

	/**
	 * Tests the behavior of the non standard "invoke()" operation on OclAny.
	 * <p>
	 * As this can be used more or less for anything, we will use it to invoke generic methods with easily
	 * inferred results.
	 * </p>
	 */
	@Test
	public void testOclAnyInvoke() {
		final Resource resource = new ResourceImpl();
		resource.setURI(URI.createPlatformPluginURI('/' + AcceleoEngineTestPlugin.PLUGIN_ID + '/'
				+ "data/Library/nonstdlib" + EMTL_EXTENSION, true));
		EOperation operation = getOperation(AcceleoNonStandardLibrary.TYPE_OCLANY_NAME,
				AcceleoNonStandardLibrary.OPERATION_OCLANY_INVOKE);
		resource.getContents().add(operation);

		final EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub = EcoreFactory.eINSTANCE.createEPackage();
		final EClass clazz1 = EcoreFactory.eINSTANCE.createEClass();
		final EClass clazz2 = EcoreFactory.eINSTANCE.createEClass();
		final EClass clazz3 = EcoreFactory.eINSTANCE.createEClass();
		final EEnum enumeration = EcoreFactory.eINSTANCE.createEEnum();
		subSub.getEClassifiers().add(clazz1);
		subSub.getEClassifiers().add(clazz2);
		subSub.getEClassifiers().add(clazz3);
		subSub.getEClassifiers().add(enumeration);
		sub.getESubpackages().add(subSub);
		root.getESubpackages().add(sub);

		final List<Object> args = new ArrayList<Object>();

		Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment,
				operation, root, "java.lang.Object", "toString()", args);
		assertNotNull("A result should have been returned", result);
		assertEquals("Unexpected result of invocation with Object.toString as target", root.toString(),
				result);

		args.add(root);
		result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment, operation,
				root, "org.eclipse.emf.ecore.util.EcoreUtil", "getURI(org.eclipse.emf.ecore.EObject)", args);
		assertNotNull("A result should have been returned", result);
		assertEquals("Unexpected result of invocation with Object.toString as target",
				EcoreUtil.getURI(root), result);

		// ClassNotFound
		try {
			result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment,
					operation, root, "inexisting.Object", "method()", args);
			fail("The non-standard 'invoke' operation is expected to fail in AcceleoEvaluationException "
					+ "when trying to invoke a method of an inexisting class.");
		} catch (AcceleoEvaluationException e) {
			// expected behavior
		}

		// NoSuchMethod
		try {
			result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment,
					operation, root, "java.lang.Object", "unknownMethod()", args);
			fail("The non-standard 'invoke' operation is expected to fail in AcceleoEvaluationException "
					+ "when trying to invoke an inexisting method.");
		} catch (AcceleoEvaluationException e) {
			// expected behavior
		}

		// IllegalArgument
		try {
			args.clear();
			args.add("test");
			result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment,
					operation, root, "org.eclipse.emf.ecore.util.EcoreUtil",
					"getURI(org.eclipse.emf.ecore.EObject)", args);
			fail("The non-standard 'invoke' operation is expected to fail in AcceleoEvaluationException "
					+ "when trying to invoke a method with illegal arguments.");
		} catch (AcceleoEvaluationException e) {
			// expected behavior
		}
	}

	/**
	 * Tests the behavior of the non standard "siblings(OclAny)" operation on OclAny.
	 * <p>
	 * Expects the result to contain all of the siblings of the given type for the given object, excluding
	 * self.
	 * </p>
	 */
	@Test
	public void testOclAnySiblingsParameterizable() {
		EOperation operation = getOperation(AcceleoNonStandardLibrary.TYPE_EOBJECT_NAME,
				AcceleoNonStandardLibrary.OPERATION_EOBJECT_SIBLINGS);

		final EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub = EcoreFactory.eINSTANCE.createEPackage();
		final EClass clazz1 = EcoreFactory.eINSTANCE.createEClass();
		final EClass clazz2 = EcoreFactory.eINSTANCE.createEClass();
		final EClass clazz3 = EcoreFactory.eINSTANCE.createEClass();
		final EEnum enumeration = EcoreFactory.eINSTANCE.createEEnum();
		subSub.getEClassifiers().add(clazz1);
		subSub.getEClassifiers().add(clazz2);
		subSub.getEClassifiers().add(clazz3);
		subSub.getEClassifiers().add(enumeration);
		sub.getESubpackages().add(subSub);
		root.getESubpackages().add(sub);

		Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment,
				operation, clazz2, EcorePackage.eINSTANCE.getEEnum());
		assertSame("Unexpected count of siblings returned", 1, ((Collection<?>)result).size());
		Iterator<?> children = ((Collection<?>)result).iterator();
		assertSame("The sibling should have been the only eenum.", enumeration, children.next());

		result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment, operation,
				enumeration, EcorePackage.eINSTANCE.getEEnum());
		assertTrue("There shouldn't have been any sibling of type EEnum for the only EEnum of a package",
				((Collection<?>)result).isEmpty());
	}

	/**
	 * Tests the behavior of the non standard "toString()" operation on OclAny.
	 * <p>
	 * Expects the result to be the same as a call to Object#toString() on the receiver.
	 * </p>
	 */
	@Test
	public void testOclAnyToString() {
		EOperation operation = getOperation(AcceleoNonStandardLibrary.TYPE_OCLANY_NAME,
				AcceleoNonStandardLibrary.OPERATION_OCLANY_TOSTRING);

		final EPackage root = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage sub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub = EcoreFactory.eINSTANCE.createEPackage();
		final EPackage subSub2 = EcoreFactory.eINSTANCE.createEPackage();
		final EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		final EClass clazz2 = EcoreFactory.eINSTANCE.createEClass();
		final EAttribute attribute = EcoreFactory.eINSTANCE.createEAttribute();
		clazz.getEStructuralFeatures().add(attribute);
		subSub.getEClassifiers().add(clazz);
		sub.getESubpackages().add(subSub);
		subSub2.getEClassifiers().add(clazz2);
		sub.getESubpackages().add(subSub2);
		root.getESubpackages().add(sub);
		clazz.getESuperTypes().add(clazz2);

		final TreeIterator<EObject> childrenIterator = root.eAllContents();
		while (childrenIterator.hasNext()) {
			final EObject child = childrenIterator.next();
			assertEquals("Unexpected result of the non-standard toString operation on " + child, child
					.toString(), AcceleoLibraryOperationVisitor.callNonStandardOperation(
					evaluationEnvironment, operation, child));
		}

		for (String value : stringValues) {
			assertEquals("Unexpected result of the non-standard toString operation on a String", value,
					AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment, operation,
							value));
		}

		assertEquals("Unexpected result of the non-standard toString operation on an Integer", "0",
				AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment, operation,
						Integer.valueOf(0)));
	}

	/**
	 * Tests the behavior of the non standard "contains(String)" operation on String.
	 * <p>
	 * Expects the result to be the same as {@link String#contains(CharSequence)}.
	 * </p>
	 */
	@Test
	public void testStringContains() {
		EOperation operation = getOperation(AcceleoNonStandardLibrary.PRIMITIVE_STRING_NAME,
				AcceleoNonStandardLibrary.OPERATION_STRING_CONTAINS);

		final String uncontainedString = "tgdjfsleo";

		// Taking random characters as the value : expecting contains to return false
		for (String value : stringValues) {
			final Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(
					evaluationEnvironment, operation, value, uncontainedString);
			assertTrue("Result of contains should have been a boolean", result instanceof Boolean);
			assertEquals("Result should have been false.", Boolean.FALSE, result);
			assertEquals("The non standard operation should have returned the same result as "
					+ "String#contains(CharSequence)", value.contains(uncontainedString), result);
		}

		// Taking random substring of the value : expecting contains to return true
		for (String value : stringValues) {
			final String subString;
			if (value.length() == 0) {
				subString = value;
			} else {
				final int offset1 = Double.valueOf(Math.random() * value.length()).intValue();
				final int offset2 = Double.valueOf(Math.random() * value.length()).intValue();
				subString = value.substring(Math.min(offset1, offset2), Math.max(offset1, offset2));
			}
			final Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(
					evaluationEnvironment, operation, value, subString);
			assertTrue("Result of contains should have been a boolean", result instanceof Boolean);
			assertEquals("Result should have been true.", Boolean.TRUE, result);
			assertEquals("The non standard operation should have returned the same result as "
					+ "String#contains(CharSequence)", value.contains(subString), result);
		}

		// Checking if value "contains" itself : expecting contains to return true
		for (String value : stringValues) {
			final Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(
					evaluationEnvironment, operation, value, value);
			assertTrue("Result of contains should have been a boolean", result instanceof Boolean);
			assertEquals("Result should have been true.", Boolean.TRUE, result);
			assertEquals("The non standard operation should have returned the same result as "
					+ "String#contains(CharSequence)", value.contains(value), result);
		}

		// Ensure the behavior when passing null as argument doesn't evolve
		try {
			AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment, operation,
					stringValues[0], (Object)null);
			fail("The non standard String.contains operation previously threw NPEs when called with null argument");
		} catch (NullPointerException e) {
			// Expected behavior
		}
	}

	/**
	 * Tests the behavior of the non standard "endsWith(String)" operation on String.
	 * <p>
	 * Expects the result to be the same as {@link String#endsWith(String)}.
	 * </p>
	 */
	@Test
	public void testStringEndsWith() {
		EOperation operation = getOperation(AcceleoNonStandardLibrary.PRIMITIVE_STRING_NAME,
				AcceleoNonStandardLibrary.OPERATION_STRING_ENDSWITH);

		final String uncontainedString = "tgdjfsleo";

		// Taking random characters as the end value : expecting endsWith to return false
		for (String value : stringValues) {
			final Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(
					evaluationEnvironment, operation, value, uncontainedString);
			assertTrue("Result of endsWith should have been a boolean", result instanceof Boolean);
			assertEquals("Result should have been false.", Boolean.FALSE, result);
			assertEquals("The non standard operation should have returned the same result as "
					+ "String#endsWith(String)", value.endsWith(uncontainedString), result);
		}

		// Taking last part of the value : expecting endsWith to return true
		for (String value : stringValues) {
			final String lastPart;
			if (value.length() == 0) {
				lastPart = value;
			} else {
				lastPart = value.substring(Math.max(value.length() / 2, 1));
			}
			final Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(
					evaluationEnvironment, operation, value, lastPart);
			assertTrue("Result of endsWith should have been a boolean", result instanceof Boolean);
			assertEquals("Result should have been true.", Boolean.TRUE, result);
			assertEquals("The non standard operation should have returned the same result as "
					+ "String#endsWith(String)", value.endsWith(lastPart), result);
		}

		// Checking if value "endsWith" itself : expecting endsWith to return true
		for (String value : stringValues) {
			final Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(
					evaluationEnvironment, operation, value, value);
			assertTrue("Result of endsWith should have been a boolean", result instanceof Boolean);
			assertEquals("Result should have been true.", Boolean.TRUE, result);
			assertEquals("The non standard operation should have returned the same result as "
					+ "String#endsWith(String)", value.endsWith(value), result);
		}

		// Ensure the behavior when passing null as argument doesn't evolve
		try {
			AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment, operation,
					stringValues[0], (Object)null);
			fail("The non standard String.endsWith operation previously threw NPEs when called with null argument");
		} catch (NullPointerException e) {
			// Expected behavior
		}
	}

	/**
	 * Tests the behavior of the non standard "replace(String, String)" operation on String.
	 * <p>
	 * Expects the result to be the same as {@link String#replaceFirst(String, String)}.
	 * </p>
	 */
	@Test
	public void testStringReplace() {
		EOperation operation = getOperation(AcceleoNonStandardLibrary.PRIMITIVE_STRING_NAME,
				AcceleoNonStandardLibrary.OPERATION_STRING_REPLACE);

		final String value = "start .*abc.* - .*abc.* end";
		String search = "(.*?)abc.*( end)";
		String replace = "$1 -$2";
		Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment,
				operation, value, search, replace);
		assertEquals("Non standard operation String.replace(String, String) didn't return the "
				+ "expected result.", "start .* - end", result);
		assertEquals("Non standard replace didn't yield the same result as String.replace().", value
				.replaceFirst(search, replace), result);

		search = "not contained substring";
		replace = "random replacement";
		result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment, operation,
				value, search, replace);
		assertEquals("standard operation String.replace(String, String) didn't return the "
				+ "expected result.", "start .*abc.* - .*abc.* end", result);
		assertEquals("Non standard replace didn't yield the same result as String.replace().", value
				.replaceFirst(search, replace), result);

		// Ensure the behavior when passing null as argument doesn't evolve
		try {
			AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment, operation, value,
					(Object)null, "abc");
			fail("The non standard String.replace operation previously threw NPEs when called with null argument");
		} catch (NullPointerException e) {
			// Expected behavior
		}
		try {
			AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment, operation, value,
					"abc", (Object)null);
			fail("The non standard String.replace operation previously threw NPEs when called with null argument");
		} catch (NullPointerException e) {
			// Expected behavior
		}
	}

	/**
	 * Tests the behavior of the non standard "replaceAll(String, String)" operation on String.
	 * <p>
	 * Expects the result to be the same as {@link String#replaceAll(String, String)}.
	 * </p>
	 */
	@Test
	public void testStringReplaceAll() {
		EOperation operation = getOperation(AcceleoNonStandardLibrary.PRIMITIVE_STRING_NAME,
				AcceleoNonStandardLibrary.OPERATION_STRING_REPLACEALL);

		final String value = "start .*abc.* - .*abc.* end";
		String search = "(.*?)abc";
		String replace = "$1def";
		Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment,
				operation, value, search, replace);
		assertEquals("Non standard operation String.replaceAll(String, String) didn't return the "
				+ "expected result.", "start .*def.* - .*def.* end", result);
		assertEquals("Non standard replace didn't yield the same result as String.replaceAll().", value
				.replaceAll(search, replace), result);

		search = "not contained substring";
		replace = "random replacement";
		result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment, operation,
				value, search, replace);
		assertEquals("standard operation String.replaceAll(String, String) didn't return the "
				+ "expected result.", "start .*abc.* - .*abc.* end", result);
		assertEquals("Non standard replaceAll didn't yield the same result as String.replaceAll().", value
				.replaceAll(search, replace), result);

		// Ensure the behavior when passing null as argument doesn't evolve
		try {
			AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment, operation, value,
					(Object)null, "abc");
			fail("The non standard String.replaceAll operation previously threw NPEs when called with null argument");
		} catch (NullPointerException e) {
			// Expected behavior
		}
		try {
			AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment, operation, value,
					"abc", (Object)null);
			fail("The non standard String.replaceAll operation previously threw NPEs when called with null argument");
		} catch (NullPointerException e) {
			// Expected behavior
		}
	}

	/**
	 * Tests the behavior of the non standard "startsWith(String)" operation on String.
	 * <p>
	 * Expects the result to be the same as {@link String#startsWith(String)}.
	 * </p>
	 */
	@Test
	public void testStringStartsWith() {
		EOperation operation = getOperation(AcceleoNonStandardLibrary.PRIMITIVE_STRING_NAME,
				AcceleoNonStandardLibrary.OPERATION_STRING_STARTSWITH);

		final String uncontainedString = "tgdjfsleo";

		// Taking random characters as the end value : expecting startsWith to return false
		for (String value : stringValues) {
			final Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(
					evaluationEnvironment, operation, value, uncontainedString);
			assertTrue("Result of startsWith should have been a boolean", result instanceof Boolean);
			assertEquals("Result should have been false.", Boolean.FALSE, result);
			assertEquals("The non standard operation should have returned the same result as "
					+ "String#startsWith(String)", value.startsWith(uncontainedString), result);
		}

		// Taking first part of the value : expecting startsWith to return true
		for (String value : stringValues) {
			final String firstPart;
			if (value.length() == 0) {
				firstPart = value;
			} else {
				firstPart = value.substring(0, Math.max(value.length() / 2, 1));
			}
			final Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(
					evaluationEnvironment, operation, value, firstPart);
			assertTrue("Result of startsWith should have been a boolean", result instanceof Boolean);
			assertEquals("Result should have been true.", Boolean.TRUE, result);
			assertEquals("The non standard operation should have returned the same result as "
					+ "String#startsWith(String)", value.startsWith(firstPart), result);
		}

		// Checking if value "startsWith" itself : expecting startsWith to return true
		for (String value : stringValues) {
			final Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(
					evaluationEnvironment, operation, value, value);
			assertTrue("Result of startsWith should have been a boolean", result instanceof Boolean);
			assertEquals("Result should have been true.", Boolean.TRUE, result);
			assertEquals("The non standard operation should have returned the same result as "
					+ "String#startsWith(String)", value.startsWith(value), result);
		}

		// Ensure the behavior when passing null as argument doesn't evolve
		try {
			AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment, operation,
					stringValues[0], (Object)null);
			fail("The non standard String.startsWith operation previously threw NPEs when called with null argument");
		} catch (NullPointerException e) {
			// Expected behavior
		}
	}

	/**
	 * Tests the behavior of the non standard String.substituteAll(String, String) operation.
	 * <p>
	 * We expect the call to replace all occurences of the substring by the replacement, not considering both
	 * parameters as regular expressions. Result should be the same as
	 * {@link String#replace(CharSequence, CharSequence)}.
	 * </p>
	 */
	@Test
	public void testStringSubstituteAll() {
		EOperation operation = getOperation(AcceleoNonStandardLibrary.PRIMITIVE_STRING_NAME,
				AcceleoNonStandardLibrary.OPERATION_STRING_SUBSTITUTEALL);

		final String value = "start .*abc.* - .*abc.* end";
		String search = ".*abc.*";
		String replace = "\\$1\\1def\\2$2\\";
		Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment,
				operation, value, search, replace);
		assertEquals("Non standard operation String.substituteAll(String, String) didn't return the "
				+ "expected result.", "start \\$1\\1def\\2$2\\ - \\$1\\1def\\2$2\\ end", result);
		assertEquals("Result of the non standard substituteAll should have been the same as calling"
				+ "String#replace(CharSequence, CharSequence).", value.replace(search, replace), result);

		search = "not contained substring";
		replace = "random replacement";
		result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment, operation,
				value, search, replace);
		assertEquals("Non standard operation String.substituteAll(String, String) didn't return the "
				+ "expected result.", "start .*abc.* - .*abc.* end", result);

		// Ensure the behavior when passing null as argument doesn't evolve
		try {
			AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment, operation, value,
					(EObject)null, "abc");
			fail("The non standard String.substituteAll operation previously threw NPEs when called with null argument");
		} catch (NullPointerException e) {
			// Expected behavior
		}
		try {
			AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment, operation, value,
					"abc", (EObject)null);
			fail("The non standard String.substitute operation previously threw NPEs when called with null argument");
		} catch (NullPointerException e) {
			// Expected behavior
		}
	}

	/**
	 * Tests the behavior of the non standard "tokenize(String)" operation on String.
	 * <p>
	 * Expects the behavior to mimic that of the {@link StringTokenizer}.
	 * </p>
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testStringTokenize() {
		EOperation operation = getOperation(AcceleoNonStandardLibrary.PRIMITIVE_STRING_NAME,
				AcceleoNonStandardLibrary.OPERATION_STRING_TOKENIZE);

		final String[] expected = {"this", "is", "a", "randomly", "delimited", "sentence", };
		final String value = "this/is.a\\randomly_delimited^sentence";
		final Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment,
				operation, value, "^/_.\\");
		assertTrue("Result should have been a list.", result instanceof List<?>);
		assertSame("Result should have contained 6 words.", expected.length, ((List<String>)result).size());
		for (int i = 0; i < expected.length; i++) {
			assertEquals("unexpected String in the tokenized list.", expected[i], ((List<String>)result)
					.get(i));
		}

		// Ensure the behavior when passing null as argument doesn't evolve
		try {
			AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment, operation, value,
					(EObject)null);
			fail("The non standard String.tokenize operation previously threw NPEs when called with null argument");
		} catch (NullPointerException e) {
			// Expected behavior
		}
	}

	/**
	 * Tests the behavior of the non standard "trim()" operation on String.
	 */
	@Test
	public void testStringTrim() {
		EOperation operation = getOperation(AcceleoNonStandardLibrary.PRIMITIVE_STRING_NAME,
				AcceleoNonStandardLibrary.OPERATION_STRING_TRIM);

		final String value = "   abc   abc\nabc\n   ";
		final Object result = AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment,
				operation, value.trim());
		assertEquals("Unexpected result of the non standard String.trim operation.", "abc   abc\nabc", result);
		assertEquals("Non standard String.trim did not yield the same result as java's String#trim().", value
				.trim(), result);
	}

	/**
	 * Tests the behavior of the environment when calling for an undefined operation.
	 * <p>
	 * Expects an {@link UnsupportedOperationException} to be thrown with an accurate error message.
	 * </p>
	 */
	@Test
	public void testUndefinedOperation() {
		final EOperation operation = EcoreFactory.eINSTANCE.createEOperation();
		operation.setName("undefinedOperation");
		operation.setEType(EcorePackage.eINSTANCE.getEString());
		final EAnnotation acceleoAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
		acceleoAnnotation.setSource("Acceleo non-standard");
		operation.getEAnnotations().add(acceleoAnnotation);

		try {
			AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment, operation,
					"source");
			fail("Expected Unsupported Operation hasn't been thrown by the evaluation environment.");
		} catch (UnsupportedOperationException e) {
			// Expected behavior
			final String expectedErrMsg = "undefinedOperation()";
			assertTrue("Exception hasn't been affected an accurate error message", e.getMessage().contains(
					expectedErrMsg));
		}

		try {
			AcceleoLibraryOperationVisitor.callNonStandardOperation(evaluationEnvironment, operation,
					"source", "arg1", "arg2");
			fail("Expected Unsupported Operation hasn't been thrown by the evaluation environment.");
		} catch (UnsupportedOperationException e) {
			// Expected behavior
			final String expectedErrMsg = "undefinedOperation(String, String)";
			assertTrue("Exception hasn't been affected an accurate error message", e.getMessage().contains(
					expectedErrMsg));
		}
	}

	/**
	 * Returns the operation named <code>operationName</code> registered against the type
	 * <code>typeName</code> in the standard library.
	 * 
	 * @param typeName
	 *            Name of the classifier we seek an operation of.
	 * @return The sought operation.
	 */
	private EOperation getOperation(String typeName, String operationName) {
		final List<EOperation> candidates = nonStdLib.get(typeName);
		for (EOperation candidate : candidates) {
			if (candidate.getName().equals(operationName)) {
				return candidate;
			}
		}
		// not guarded
		return null;
	}
}
