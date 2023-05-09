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
package org.eclipse.acceleo.query.tests;

import java.util.Map;

import org.eclipse.acceleo.query.tests.anydsl.AnydslPackage;
import org.eclipse.acceleo.query.tests.qmodel.QmodelPackage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.uml2.types.TypesPackage;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.resource.UMLResource;

public class Setup {

	public static Setup createSetupForCurrentEnvironment() {
		// we might need slightly different setups if Eclipse is running or not.
		return new Setup();
	}

	public ResourceSet newConfiguredResourceSet() {
		ResourceSet set = new ResourceSetImpl();
		setup(set);
		return set;
	}

	public void setup(ResourceSet set) {
		setup(set.getPackageRegistry(), set.getResourceFactoryRegistry(), set.getURIConverter().getURIMap());
	}

	public void setupGlobals() {
		setup(EPackage.Registry.INSTANCE, Resource.Factory.Registry.INSTANCE, URIConverter.URI_MAP);
	}

	public void setup(EPackage.Registry pakRegistry, Resource.Factory.Registry resRegistry,
			Map<URI, URI> uriMAP) {
		setupResourceFactoryRegistry(resRegistry);
		setupEPackageRegistry(pakRegistry);
	}

	private void setupEPackageRegistry(EPackage.Registry pakRegistry) {
		pakRegistry.put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
		pakRegistry.put(QmodelPackage.eINSTANCE.getNsURI(), QmodelPackage.eINSTANCE);
		pakRegistry.put(AnydslPackage.eINSTANCE.getNsURI(), AnydslPackage.eINSTANCE);
		pakRegistry.put(UMLPackage.eINSTANCE.getNsURI(), UMLPackage.eINSTANCE);
		pakRegistry.put(TypesPackage.eINSTANCE.getNsURI(), TypesPackage.eINSTANCE);
	}

	private void setupResourceFactoryRegistry(Resource.Factory.Registry resRegistry) {
		resRegistry.getExtensionToFactoryMap().put("ecore", //$NON-NLS-1$
				new EcoreResourceFactoryImpl());
		resRegistry.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		resRegistry.getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
	}

}
