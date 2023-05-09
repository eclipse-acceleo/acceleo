/**
 * Copyright (c) 2008, 2021 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo.util;

import java.util.Map;

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

/**
 * This class contains helper methods to serialize and deserialize XML documents <!-- begin-user-doc --> <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class AcceleoXMLProcessor extends XMLProcessor {

	/**
	 * Public constructor to instantiate the helper. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AcceleoXMLProcessor() {
		super((EPackage.Registry.INSTANCE));
		AcceleoPackage.eINSTANCE.eClass();
	}

	/**
	 * Register for "*" and "xml" file extensions the AcceleoResourceFactoryImpl factory. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected Map<String, Resource.Factory> getRegistrations() {
		if (registrations == null) {
			super.getRegistrations();
			registrations.put(XML_EXTENSION, new AcceleoResourceFactoryImpl());
			registrations.put(STAR_EXTENSION, new AcceleoResourceFactoryImpl());
		}
		return registrations;
	}

} // AcceleoXMLProcessor
