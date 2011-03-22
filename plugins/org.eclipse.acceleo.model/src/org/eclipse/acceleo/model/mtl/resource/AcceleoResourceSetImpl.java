/*******************************************************************************
 * Copyright (c) 2006, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.model.mtl.resource;

import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * A custom resource set used by Acceleo to provide a support for binary/xmi resources in stand alone.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class AcceleoResourceSetImpl extends ResourceSetImpl {

	/**
	 * The constructor.
	 */
	public AcceleoResourceSetImpl() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceSetImpl#getResourceFactoryRegistry()
	 */
	@Override
	public Registry getResourceFactoryRegistry() {
		if (resourceFactoryRegistry == null) {
			Registry registry = super.getResourceFactoryRegistry();
			resourceFactoryRegistry = new AcceleoResourceFactoryRegistry(registry);
		}
		return resourceFactoryRegistry;
	}
}
