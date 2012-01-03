/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.model.mtl.util;

import java.util.Map;

import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents <!-- begin-user-doc --> <!--
 * end-user-doc -->
 * 
 * @generated
 * @since 3.0
 */
public class MtlXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MtlXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		MtlPackage.eINSTANCE.eClass();
	}

	/**
	 * Register for "*" and "xml" file extensions the MtlResourceFactoryImpl factory. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new MtlResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new MtlResourceFactoryImpl());
		}
		return registrations;
	}

} // MtlXMLProcessor
