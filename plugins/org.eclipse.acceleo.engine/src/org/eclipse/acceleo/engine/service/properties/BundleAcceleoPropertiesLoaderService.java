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

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.emf.common.EMFPlugin;
import org.osgi.framework.Bundle;

/**
 * This property loader will only be used if eclipse is running, it will look for properties files in bundles.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.0.5
 */
public class BundleAcceleoPropertiesLoaderService extends AbstractAcceleoPropertiesLoaderService {

	/**
	 * The bundle of the generator.
	 */
	private Bundle bundle;

	/**
	 * The constructor.
	 * 
	 * @param acceleoService
	 *            The Acceleo service.
	 * @param bundle
	 *            The bundle in which we will look for the properties files.
	 */
	public BundleAcceleoPropertiesLoaderService(AcceleoService acceleoService, Bundle bundle) {
		this.acceleoService = acceleoService;
		this.bundle = bundle;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.service.properties.AbstractAcceleoPropertiesLoader#alternatePropertiesLoading(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Properties alternatePropertiesLoading(String filepath) {
		Properties properties = new Properties();
		if (EMFPlugin.IS_ECLIPSE_RUNNING && bundle != null) {
			try {
				URL resource = bundle.getResource(filepath);
				if (resource != null) {
					properties.load(resource.openStream());
				} else if (filepath != null && !filepath.endsWith(".properties")) { //$NON-NLS-1$
					String filename = filepath;
					if (filename.contains(".")) { //$NON-NLS-1$
						filename = filename.substring(filename.lastIndexOf(".") + 1); //$NON-NLS-1$
					}
					filename = filename + ".properties"; //$NON-NLS-1$
					Enumeration<?> entries = bundle.findEntries("/", filename, true); //$NON-NLS-1$
					Object firstEntry = null;
					if (entries.hasMoreElements()) {
						firstEntry = entries.nextElement();
					}
					if (firstEntry instanceof URL) {
						properties.load(((URL)firstEntry).openStream());
					}
				}
			} catch (IOException e) {
				return null;
			}
		}
		return properties;
	}

}
