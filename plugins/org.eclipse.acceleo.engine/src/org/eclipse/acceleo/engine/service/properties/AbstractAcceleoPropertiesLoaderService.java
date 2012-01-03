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

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.PropertyResourceBundle;

import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.service.AcceleoService;

/**
 * The Acceleo properties loader service will handle the properties loaders.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public abstract class AbstractAcceleoPropertiesLoaderService extends AbstractAcceleoPropertiesLoader {

	/**
	 * The Acceleo service.
	 */
	protected AcceleoService acceleoService;

	/**
	 * Indicates if the properties loaded should be forced.
	 * 
	 * @since 3.1
	 */
	protected boolean forceProperties;

	/**
	 * Initialize the Acceleo service with the list of properties files.
	 * 
	 * @param propertiesFiles
	 *            The list of properties files.
	 */
	public void initializeService(List<String> propertiesFiles) {
		for (String propertiesFile : propertiesFiles) {
			try {
				acceleoService.addPropertiesFile(propertiesFile);
			} catch (MissingResourceException e) {
				if (forceProperties) {
					Properties loadedProperties = this.loadProperties(propertiesFile);
					if (loadedProperties == null) {
						loadedProperties = alternatePropertiesLoading(propertiesFile);
					}
					if (loadedProperties != null) {
						Map<String, String> propertiesToAdd = new HashMap<String, String>();
						Enumeration<?> propertiesNames = loadedProperties.propertyNames();
						while (propertiesNames.hasMoreElements()) {
							String propertyName = (String)propertiesNames.nextElement();
							String propertyValue = loadedProperties.getProperty(propertyName);
							propertiesToAdd.put(propertyName, propertyValue);
						}
						if (!propertiesToAdd.isEmpty()) {
							try {
								acceleoService.addProperties(propertiesToAdd);
							} catch (MissingResourceException ex) {
								AcceleoEnginePlugin.log(ex, false);
							}
						}
					}
				} else {
					PropertyResourceBundle propertyResourceBundle = this
							.loadPropertiesResourceBundle(propertiesFile);
					if (propertyResourceBundle == null) {
						propertyResourceBundle = alternatePropertiesResourceBundleLoading(propertiesFile);
					}
					if (propertyResourceBundle != null) {
						try {
							acceleoService.addProperties(propertyResourceBundle, new File(propertiesFile)
									.getName());
						} catch (MissingResourceException ex) {
							AcceleoEnginePlugin.log(ex, false);
						}
					} else {
						AcceleoEnginePlugin.log(e, false);
					}
				}
			}
		}
	}
}
