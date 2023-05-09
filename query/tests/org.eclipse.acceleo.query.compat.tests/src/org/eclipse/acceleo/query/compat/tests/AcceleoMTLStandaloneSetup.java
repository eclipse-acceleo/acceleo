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
package org.eclipse.acceleo.query.compat.tests;

import java.net.URL;
import java.security.CodeSource;
import java.util.Map;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.resource.EMtlBinaryResourceFactoryImpl;
import org.eclipse.acceleo.model.mtl.resource.EMtlResourceFactoryImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.expressions.ExpressionsPackage;

public class AcceleoMTLStandaloneSetup {

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
		setupURIConverter(uriMAP);

	}

	private void setupEPackageRegistry(EPackage.Registry pakRegistry) {
		pakRegistry.put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);

		pakRegistry.put(org.eclipse.ocl.ecore.EcorePackage.eINSTANCE.getNsURI(),
				org.eclipse.ocl.ecore.EcorePackage.eINSTANCE);
		pakRegistry.put(ExpressionsPackage.eINSTANCE.getNsURI(), ExpressionsPackage.eINSTANCE);

		pakRegistry.put(MtlPackage.eINSTANCE.getNsURI(), MtlPackage.eINSTANCE);

		pakRegistry.put("http://www.eclipse.org/ocl/1.1.0/oclstdlib.ecore", //$NON-NLS-1$
				getOCLStdLibPackage());
	}

	private void setupResourceFactoryRegistry(Resource.Factory.Registry resRegistry) {
		resRegistry.getExtensionToFactoryMap().put("ecore", //$NON-NLS-1$
				new EcoreResourceFactoryImpl());
		resRegistry.getContentTypeToFactoryMap().put(IAcceleoConstants.BINARY_CONTENT_TYPE,
				new EMtlBinaryResourceFactoryImpl());
		resRegistry.getContentTypeToFactoryMap().put(IAcceleoConstants.XMI_CONTENT_TYPE,
				new EMtlResourceFactoryImpl());
		resRegistry.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
	}

	protected EPackage getOCLStdLibPackage() {
		EcoreEnvironmentFactory factory = new EcoreEnvironmentFactory();
		EcoreEnvironment environment = (EcoreEnvironment)factory.createEnvironment();
		EPackage oclStdLibPackage = (EPackage)EcoreUtil.getRootContainer(environment.getOCLStandardLibrary()
				.getBag());
		environment.dispose();
		return oclStdLibPackage;
	}

	/**
	 * Registers the libraries.
	 */
	protected void setupURIConverter(Map<URI, URI> uriMap) {
		CodeSource acceleoModel = MtlPackage.class.getProtectionDomain().getCodeSource();
		if (acceleoModel != null) {

			String libraryLocation = acceleoModel.getLocation().toString();

			if (libraryLocation.endsWith(".jar")) { //$NON-NLS-1$
				libraryLocation = "jar:" + libraryLocation + '!'; //$NON-NLS-1$
			}

			URL stdlib = MtlPackage.class.getResource("/model/mtlstdlib.ecore"); //$NON-NLS-1$
			URL resource = MtlPackage.class.getResource("/model/mtlnonstdlib.ecore"); //$NON-NLS-1$

			uriMap.put(
					URI.createURI("http://www.eclipse.org/acceleo/mtl/3.0/mtlstdlib.ecore"), URI.createURI(stdlib.toString())); //$NON-NLS-1$
			uriMap.put(
					URI.createURI("http://www.eclipse.org/acceleo/mtl/3.0/mtlnonstdlib.ecore"), URI.createURI(resource.toString())); //$NON-NLS-1$
		} else {
			System.err.println("Coudln't retrieve location of plugin 'org.eclipse.acceleo.model'."); //$NON-NLS-1$
		}
	}

}
