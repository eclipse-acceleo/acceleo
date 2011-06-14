/*******************************************************************************
 * Copyright (c) 2010, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.engine.service.properties;

import java.util.Properties;

import org.eclipse.acceleo.engine.service.AcceleoService;

/**
 * This properties loader will ensure the same behavior as in the previous versions of Acceleo.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.0.5
 */
public class BasicAcceleoPropertiesLoaderService extends AbstractAcceleoPropertiesLoaderService {

	/**
	 * The constructor.
	 * 
	 * @param acceleoService
	 *            The Acceleo service.
	 */
	public BasicAcceleoPropertiesLoaderService(AcceleoService acceleoService) {
		this.acceleoService = acceleoService;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.service.properties.AbstractAcceleoPropertiesLoader#alternatePropertiesLoading(java.lang.String)
	 */
	@Override
	protected Properties alternatePropertiesLoading(String filepath) {
		/*
		 * We won't try any other property loading here. You can override this method to try to look for the
		 * properties file in a bundle for example.
		 */
		return null;
	}

}
