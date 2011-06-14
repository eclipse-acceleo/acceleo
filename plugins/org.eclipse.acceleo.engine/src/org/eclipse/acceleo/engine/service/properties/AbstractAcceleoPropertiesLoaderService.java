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

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;

import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.service.AcceleoService;

/**
 * The Acceleo properties loader service will handle the properties loaders.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.0.5
 */
public abstract class AbstractAcceleoPropertiesLoaderService extends AbstractAcceleoPropertiesLoader {

	/**
	 * The Acceleo service.
	 */
	protected AcceleoService acceleoService;

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
				Properties loadedProperties = this.loadProperties(propertiesFile);
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
				} else {
					AcceleoEnginePlugin.log(e, false);
				}
			}
		}
	}
}
