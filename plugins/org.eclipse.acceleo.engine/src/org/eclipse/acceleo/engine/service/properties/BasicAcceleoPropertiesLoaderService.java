/*******************************************************************************
 * Copyright (c) 2010, 2012 Obeo.
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
import java.util.PropertyResourceBundle;

import org.eclipse.acceleo.engine.service.AcceleoService;

/**
 * This properties loader will ensure the same behavior as in the previous versions of Acceleo.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
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
	 * The constructor.
	 * 
	 * @param acceleoService
	 *            The Acceleo service.
	 * @param forceProperties
	 *            Indicates if the loaded properties should be forced
	 * @since 3.1
	 */
	public BasicAcceleoPropertiesLoaderService(AcceleoService acceleoService, boolean forceProperties) {
		this.acceleoService = acceleoService;
		this.forceProperties = forceProperties;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.service.properties.AbstractAcceleoPropertiesLoader#alternatePropertiesLoading(java.lang.String)
	 */
	@Override
	protected Properties alternatePropertiesLoading(String filepath) {
		/*
		 * We will consider the properties file path as an absolute path and try to load it.
		 */
		return this.loadProperties(filepath);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.service.properties.AbstractAcceleoPropertiesLoader#alternatePropertiesResourceBundleLoading(java.lang.String)
	 */
	@Override
	protected PropertyResourceBundle alternatePropertiesResourceBundleLoading(String filepath) {
		/*
		 * We will consider the properties file path as an absolute path and try to load it.
		 */
		return this.loadPropertiesResourceBundle(filepath);
	}

}
