/*******************************************************************************
 * Copyright (c) 2015, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.services.tests;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.eclipse.acceleo.query.services.ResourceServices;
import org.eclipse.acceleo.query.tests.Setup;
import org.eclipse.acceleo.query.tests.UnitTestModels;
import org.eclipse.acceleo.query.tests.qmodel.QmodelPackage;
import org.eclipse.acceleo.query.tests.qmodel.Queries;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the resource-related services.
 */
public class ResourceServicesTest extends AbstractEngineInitializationWithCrossReferencer {
	public ResourceServices resourceServices;

	public Resource reverseModel;

	public Resource umlWithFragment;

	public Resource umlFragment;

	@Before
	public void setup() throws URISyntaxException, IOException {
		resourceServices = new ResourceServices();
		this.reverseModel = new UnitTestModels(Setup.createSetupForCurrentEnvironment()).reverse();
		this.umlWithFragment = new UnitTestModels(Setup.createSetupForCurrentEnvironment()).umlWithFragment();
		EObject umlRoot = umlWithFragment.getContents().get(0);
		List<EObject> umlContent = umlRoot.eContents();
		umlFragment = umlContent.get(0).eResource();
		assertNotEquals(umlWithFragment, umlFragment);
	}

	@Test
	public void testEResource() {
		TreeIterator<EObject> iterator = EcoreUtil.getAllContents(reverseModel, true);
		while (iterator.hasNext()) {
			assertEquals(reverseModel, resourceServices.eResource(iterator.next()));
		}
	}

	@Test
	public void testEResourceFragment() {
		TreeIterator<EObject> iterator = EcoreUtil.getAllContents(umlWithFragment, true);
		while (iterator.hasNext()) {
			EObject next = iterator.next();
			if (next.toString().contains("sameResource")) {
				assertEquals(umlWithFragment, resourceServices.eResource(next));
			} else {
				assertEquals(umlFragment, resourceServices.eResource(next));
			}
		}
	}

	@Test
	public void testGetURI() {
		URI reverseURI = resourceServices.getURI(reverseModel);
		assertEquals("reverse.qmodel", reverseURI.lastSegment());
	}

	@Test
	public void testGetURIFragment() {
		URI containerURI = resourceServices.getURI(umlWithFragment);
		assertEquals("container.uml", containerURI.lastSegment());

		URI fragmentURI = resourceServices.getURI(umlFragment);
		assertEquals("controlled.uml", fragmentURI.lastSegment());
	}

	@Test
	public void testGetContents() {
		List<EObject> reverseContents = resourceServices.getContents(reverseModel);
		assertEquals(1, reverseContents.size());
		assertTrue(reverseContents.get(0) instanceof Queries);

		// Make sure the service is not returning the direct contents but a view of it
		reverseContents.clear();
		assertFalse(reverseModel.getContents().isEmpty());
	}

	@Test
	public void testGetContentsFragment() {
		List<EObject> containerContents = resourceServices.getContents(umlWithFragment);
		assertEquals(1, containerContents.size());
		EObject containerRoot = containerContents.get(0);
		assertTrue(containerRoot instanceof Package);
		assertEquals(2, containerRoot.eContents().size());
		EObject fragmentedPackage = containerRoot.eContents().get(0);
		assertTrue(fragmentedPackage instanceof Package);

		// Make sure the service is not returning the direct contents but a view of it
		containerContents.clear();
		assertFalse(umlWithFragment.getContents().isEmpty());

		List<EObject> fragmentContents = resourceServices.getContents(umlFragment);
		assertEquals(1, fragmentContents.size());
		assertEquals(fragmentedPackage, fragmentContents.get(0));
	}

	@Test
	public void testGetContentsFiltered() {
		List<EObject> reverseContentsNone = resourceServices.getContents(reverseModel, EcorePackage.eINSTANCE
				.getEPackage());
		assertTrue(reverseContentsNone.isEmpty());

		List<EObject> reverseContentsQueries = resourceServices.getContents(reverseModel,
				QmodelPackage.eINSTANCE.getQueries());
		assertEquals(1, reverseContentsQueries.size());
		assertTrue(reverseContentsQueries.get(0) instanceof Queries);

		// Make sure the service is not returning the direct contents but a view of it
		reverseContentsQueries.clear();
		assertFalse(reverseModel.getContents().isEmpty());
	}

	@Test
	public void testGetContentsFilteredAsEObject() {
		List<EObject> reverseContentsAll = resourceServices.getContents(reverseModel, EcorePackage.eINSTANCE
				.getEObject());
		assertEquals(reverseModel.getContents(), reverseContentsAll);
	}

	@Test
	public void testGetContentsFilteredFragment() {
		List<EObject> umlContentsNone = resourceServices.getContents(umlWithFragment, UMLPackage.eINSTANCE
				.getClass_());
		assertTrue(umlContentsNone.isEmpty());

		List<EObject> umlContentsPackage = resourceServices.getContents(umlWithFragment, UMLPackage.eINSTANCE
				.getPackage());
		assertEquals(1, umlContentsPackage.size());
		assertTrue(umlContentsPackage.get(0) instanceof Package);

		// Make sure the service is not returning the direct contents but a view of it
		umlContentsPackage.clear();
		assertFalse(umlWithFragment.getContents().isEmpty());
	}

	@Test
	public void testLastSegment() {
		URI reverseURI = reverseModel.getURI();
		assertEquals("reverse.qmodel", resourceServices.lastSegment(reverseURI));
	}

	@Test
	public void testLastSegmentFragment() {
		URI containerURI = umlWithFragment.getURI();
		assertEquals("container.uml", resourceServices.lastSegment(containerURI));

		URI fragmentURI = umlFragment.getURI();
		assertEquals("controlled.uml", resourceServices.lastSegment(fragmentURI));
	}

	@Test
	public void testfileExtension() {
		URI reverseURI = reverseModel.getURI();
		assertEquals("qmodel", resourceServices.fileExtension(reverseURI));
	}

	@Test
	public void testfileExtensionFragment() {
		URI containerURI = umlWithFragment.getURI();
		assertEquals("uml", resourceServices.fileExtension(containerURI));

		URI fragmentURI = umlFragment.getURI();
		assertEquals("uml", resourceServices.fileExtension(fragmentURI));
	}

	@Test
	public void testIsPlatformResource() {
		URI typoUri = URI.createURI("platform:/ressource/typo");
		assertFalse(resourceServices.isPlatformResource(typoUri));

		URI fileUri = URI.createURI("file://absolute/uri");
		assertFalse(resourceServices.isPlatformResource(fileUri));

		URI goodURI = URI.createURI("platform:/resource/good/one");
		assertTrue(resourceServices.isPlatformResource(goodURI));
	}

	@Test
	public void testIsPlatformPlugin() {
		URI typoUri = URI.createURI("platform:/plug-in/typo");
		assertFalse(resourceServices.isPlatformPlugin(typoUri));

		URI fileUri = URI.createURI("file://absolute/uri");
		assertFalse(resourceServices.isPlatformPlugin(fileUri));

		URI goodURI = URI.createURI("platform:/plugin/good/one");
		assertTrue(resourceServices.isPlatformPlugin(goodURI));
	}

	@Test
	public void getEObject() {
		assertEquals(null, resourceServices.getEObject(null, null));
		assertEquals(null, resourceServices.getEObject(reverseModel, null));
		assertEquals(reverseModel.getContents().get(0), resourceServices.getEObject(reverseModel, "/"));
	}

	@Test
	public void getURIFragment() {
		assertEquals(null, resourceServices.getURIFragment(null));
		final EObject noInResource = EcorePackage.eINSTANCE.getEcoreFactory().createEClass();
		assertEquals(null, resourceServices.getURIFragment(noInResource));
		assertEquals("/", resourceServices.getURIFragment(reverseModel.getContents().get(0)));
	}

}
