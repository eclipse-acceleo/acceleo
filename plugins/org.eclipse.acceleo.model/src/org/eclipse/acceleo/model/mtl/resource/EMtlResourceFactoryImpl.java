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
package org.eclipse.acceleo.model.mtl.resource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

/**
 * EMTL/XMI Resource factory. It is used to create specific EMF resources for the EMTL models (AST).
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class EMtlResourceFactoryImpl extends XMIResourceFactoryImpl {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl#createResource(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public Resource createResource(URI uri) {
		return new EMtlResourceImpl(uri);
	}

}
