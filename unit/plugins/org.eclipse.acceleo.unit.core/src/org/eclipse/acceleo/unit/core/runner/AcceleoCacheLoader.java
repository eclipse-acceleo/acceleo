/*******************************************************************************
 * Copyright (c) 2006, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.unit.core.runner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.resource.EMtlBinaryResourceFactoryImpl;
import org.eclipse.acceleo.model.mtl.resource.EMtlResourceFactoryImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.expressions.ExpressionsPackage;

/**
 * The acceleo cache loader class.
 * 
 * @author <a href="mailto:hugo.marchadour@obeo.fr">Hugo Marchadour</a>
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
@SuppressWarnings("restriction")
public final class AcceleoCacheLoader {

	/**
	 * The model map cache.
	 */
	private static final Map<URI, EObject> CACHE = new HashMap<URI, EObject>();

	/**
	 * The ressource set.
	 */
	private static final ResourceSet RESOURCE_SET;

	static {
		RESOURCE_SET = new ResourceSetImpl();
		RESOURCE_SET.setPackageRegistry(AcceleoPackageRegistry.INSTANCE);
		registerPackages(RESOURCE_SET);
		registerResourceFactories(RESOURCE_SET);
	}

	/**
	 * The constructor.
	 */
	private AcceleoCacheLoader() {

	}

	/**
	 * Load the uri fragment.
	 * 
	 * @param uriFragment
	 *            uri fragment
	 * @return the root EObject instance
	 */
	public static EObject load(URI uriFragment) {
		EObject loadedEObject = CACHE.get(uriFragment);
		if (loadedEObject == null) {
			try {
				loadedEObject = ModelUtils.load(uriFragment, RESOURCE_SET);
				if (loadedEObject != null) {
					CACHE.put(uriFragment, loadedEObject);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return loadedEObject;
	}

	/**
	 * Clear the cache.
	 */
	public static void clear() {
		CACHE.clear();
		List<Resource> resources = RESOURCE_SET.getResources();
		for (Resource resource : resources) {
			resource.unload();
			try {
				resource.delete(new HashMap<Object, Object>());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This will update the resource set's package registry with all usual EPackages.
	 * 
	 * @param resourceSet
	 *            The resource set which registry has to be updated.
	 */
	private static void registerPackages(ResourceSet resourceSet) {
		resourceSet.getPackageRegistry().put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);

		resourceSet.getPackageRegistry().put(org.eclipse.ocl.ecore.EcorePackage.eINSTANCE.getNsURI(),
				org.eclipse.ocl.ecore.EcorePackage.eINSTANCE);
		resourceSet.getPackageRegistry().put(ExpressionsPackage.eINSTANCE.getNsURI(),
				ExpressionsPackage.eINSTANCE);

		resourceSet.getPackageRegistry().put(MtlPackage.eINSTANCE.getNsURI(), MtlPackage.eINSTANCE);

		resourceSet.getPackageRegistry().put("http://www.eclipse.org/ocl/1.1.0/oclstdlib.ecore", //$NON-NLS-1$
				getOCLStdLibPackage());
	}

	/**
	 * This will update the resource set's resource factory registry with all usual factories.
	 * 
	 * @param resourceSet
	 *            The resource set which registry has to be updated.
	 */
	private static void registerResourceFactories(ResourceSet resourceSet) {
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", //$NON-NLS-1$
				new EcoreResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("emtl", //$NON-NLS-1$
				new EMtlResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getContentTypeToFactoryMap().put(
				IAcceleoConstants.BINARY_CONTENT_TYPE, new EMtlBinaryResourceFactoryImpl());
		resourceSet.getResourceFactoryRegistry().getContentTypeToFactoryMap().put(
				IAcceleoConstants.XMI_CONTENT_TYPE, new EMtlResourceFactoryImpl());
	}

	/**
	 * Returns the OCL package.
	 * 
	 * @return The OCL package.
	 */
	private static EPackage getOCLStdLibPackage() {
		EcoreEnvironmentFactory factory = new EcoreEnvironmentFactory();
		EcoreEnvironment environment = (EcoreEnvironment)factory.createEnvironment();
		EPackage oclStdLibPackage = (EPackage)EcoreUtil.getRootContainer(environment.getOCLStandardLibrary()
				.getBag());
		environment.dispose();
		return oclStdLibPackage;
	}
}
