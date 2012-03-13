/*******************************************************************************
 * Copyright (c) 2006, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.standalone.tests.loading;

import java.io.IOException;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.resource.AcceleoResourceFactoryRegistry;
import org.eclipse.acceleo.model.mtl.resource.AcceleoResourceSetImpl;
import org.eclipse.acceleo.model.mtl.resource.EMtlBinaryResourceFactoryImpl;
import org.eclipse.acceleo.model.mtl.resource.EMtlResourceFactoryImpl;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.expressions.ExpressionsPackage;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Stand alone tests for the loading of Acceleo binary and xmi resources.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class LoadingTests {

	/**
	 * The user.dir property key.
	 */
	private static final String USERDIR = "user.dir";

	/**
	 * Load a XMI resource in stand alone.
	 */
	@Test
	public void loadXMIResourceWithAcceleoResourceSet() {
		ResourceSet resourceSet = new AcceleoResourceSetImpl();
		registerResourceFactories(resourceSet);
		registerPackages(resourceSet);

		String path = System.getProperty(USERDIR);
		path = path + "/data/xmi/generate.emtl";

		URI moduleURI = URI.createFileURI(URI.decode(path));
		try {
			EObject eObject = ModelUtils.load(moduleURI, resourceSet);
			assertThat(eObject, is(instanceOf(Module.class)));
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Load a XMI resource in stand alone.
	 */
	@Test
	public void loadXMIResourceWithConfiguredResourceSet() {
		ResourceSet resourceSet = new ResourceSetImpl();

		Registry resourceFactoryRegistry = resourceSet.getResourceFactoryRegistry();
		resourceSet.setResourceFactoryRegistry(new AcceleoResourceFactoryRegistry(resourceFactoryRegistry));

		registerResourceFactories(resourceSet);
		registerPackages(resourceSet);

		String path = System.getProperty(USERDIR);
		path = path + "/data/xmi/generate.emtl";

		URI moduleURI = URI.createFileURI(URI.decode(path));
		try {
			EObject eObject = ModelUtils.load(moduleURI, resourceSet);
			assertThat(eObject, is(instanceOf(Module.class)));
		} catch (IOException e) {
			fail(e.getMessage());
		}

		resourceSet.setResourceFactoryRegistry(resourceFactoryRegistry);
	}

	/**
	 * Load a Binary resource in stand alone.
	 */
	@Test
	public void loadBinaryResourceWithAcceleoResourceSet() {
		ResourceSet resourceSet = new AcceleoResourceSetImpl();
		registerResourceFactories(resourceSet);
		registerPackages(resourceSet);

		String path = System.getProperty(USERDIR);
		path = path + "/data/binary/generate.emtl";

		URI moduleURI = URI.createFileURI(URI.decode(path));
		try {
			EObject eObject = ModelUtils.load(moduleURI, resourceSet);
			assertThat(eObject, is(instanceOf(Module.class)));
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Load a Binary resource in stand alone.
	 */
	@Test
	public void loadBinaryResourceWithConfiguredResourceSet() {
		ResourceSet resourceSet = new ResourceSetImpl();

		Registry resourceFactoryRegistry = resourceSet.getResourceFactoryRegistry();
		resourceSet.setResourceFactoryRegistry(new AcceleoResourceFactoryRegistry(resourceFactoryRegistry));

		registerResourceFactories(resourceSet);
		registerPackages(resourceSet);

		String path = System.getProperty(USERDIR);
		path = path + "/data/binary/generate.emtl";

		URI moduleURI = URI.createFileURI(URI.decode(path));
		try {
			EObject eObject = ModelUtils.load(moduleURI, resourceSet);
			assertThat(eObject, is(instanceOf(Module.class)));
		} catch (IOException e) {
			fail(e.getMessage());
		}

		resourceSet.setResourceFactoryRegistry(resourceFactoryRegistry);
	}

	/**
	 * Tests the loading of an ".xmi" model in stand alone.
	 */
	@Test
	public void loadXMIModel() {
		ResourceSet rs = new ResourceSetImpl();

		String path = System.getProperty(USERDIR);
		path = path + "/data/model.xmi";

		URI moduleURI = URI.createFileURI(URI.decode(path));
		try {
			EObject eObject = ModelUtils.load(moduleURI, rs);
			assertNotNull(eObject);
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * This will update the resource set's resource factory registry with all usual factories.
	 * 
	 * @param resourceSet
	 *            The resource set which registry has to be updated.
	 */
	private void registerResourceFactories(ResourceSet resourceSet) {
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", //$NON-NLS-1$
				new EcoreResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getContentTypeToFactoryMap().put(
				IAcceleoConstants.BINARY_CONTENT_TYPE, new EMtlBinaryResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getContentTypeToFactoryMap().put(
				IAcceleoConstants.XMI_CONTENT_TYPE, new EMtlResourceFactoryImpl());
	}

	/**
	 * This will update the resource set's package registry with all usual EPackages.
	 * 
	 * @param resourceSet
	 *            The resource set which registry has to be updated.
	 */
	private void registerPackages(ResourceSet resourceSet) {
		resourceSet.getPackageRegistry().put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(GenModelPackage.eINSTANCE.getNsURI(), GenModelPackage.eINSTANCE);

		resourceSet.getPackageRegistry().put(org.eclipse.ocl.ecore.EcorePackage.eINSTANCE.getNsURI(),
				org.eclipse.ocl.ecore.EcorePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(ExpressionsPackage.eINSTANCE.getNsURI(),
				ExpressionsPackage.eINSTANCE);

		resourceSet.getPackageRegistry().put(MtlPackage.eINSTANCE.getNsURI(), MtlPackage.eINSTANCE);

		resourceSet.getPackageRegistry().put("http://www.eclipse.org/ocl/1.1.0/oclstdlib.ecore", //$NON-NLS-1$
				getOCLStdLibPackage());
	}

	/**
	 * Returns the package containing the OCL standard library.
	 * 
	 * @return The package containing the OCL standard library.
	 */
	private EPackage getOCLStdLibPackage() {
		EcoreEnvironmentFactory factory = new EcoreEnvironmentFactory();
		EcoreEnvironment environment = (EcoreEnvironment)factory.createEnvironment();
		EPackage oclStdLibPackage = (EPackage)EcoreUtil.getRootContainer(environment.getOCLStandardLibrary()
				.getBag());
		environment.dispose();
		return oclStdLibPackage;
	}
}
