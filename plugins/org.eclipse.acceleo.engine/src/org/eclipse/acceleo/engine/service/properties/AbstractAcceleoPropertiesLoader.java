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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.PropertyResourceBundle;

import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;

/**
 * The AcceleoPropertiesLoader is a helper class used to load and save properties file.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public abstract class AbstractAcceleoPropertiesLoader {

	/**
	 * Loads the properties matching the given filepath.
	 * 
	 * @param filepath
	 *            The absolute filepath
	 * @return The properties of the file
	 */
	public Properties loadProperties(String filepath) {
		Properties properties = new Properties();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filepath);
			properties.load(fis);
		} catch (FileNotFoundException e) {
			properties = null;
		} catch (IOException e) {
			properties = null;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					properties = null;
				}
			}
		}

		return properties;
	}

	/**
	 * Loads the properties matching the given filepath.
	 * 
	 * @param filepath
	 *            The absolute filepath
	 * @return The properties resource bundle of the file
	 * @since 3.1
	 */
	public PropertyResourceBundle loadPropertiesResourceBundle(String filepath) {
		PropertyResourceBundle propertyResourceBundle = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filepath);
			propertyResourceBundle = new PropertyResourceBundle(fis);
		} catch (FileNotFoundException e) {
			// do not log
		} catch (IOException e) {
			// do not log
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					// do not log
				}
			}
		}
		return propertyResourceBundle;
	}

	/**
	 * Returns the properties from the file. This method is called by
	 * {@link AbstractAcceleoPropertiesLoader#loadProperties(String)} if the properties file is not found.
	 * 
	 * @param filepath
	 *            the filename
	 * @return The properties from the file. This method is called by
	 *         {@link AbstractAcceleoPropertiesLoader#loadProperties(String)} if the properties file is not
	 *         found.
	 */
	protected abstract Properties alternatePropertiesLoading(String filepath);

	/**
	 * Returns the properties from the file. This method is called by
	 * {@link AbstractAcceleoPropertiesLoader#loadProperties(String)} if the properties file is not found.
	 * 
	 * @param filepath
	 *            the filename
	 * @return The properties resource bundle from the file. This method is called by
	 *         {@link AbstractAcceleoPropertiesLoader#loadProperties(String)} if the properties file is not
	 *         found.
	 * @since 3.1
	 */
	protected PropertyResourceBundle alternatePropertiesResourceBundleLoading(String filepath) {
		return null;
	}

	/**
	 * Save the given properties file at the given location. The file path must be an absolute file path.
	 * 
	 * @param properties
	 *            The properties to save
	 * @param filepath
	 *            the file path
	 */
	public void saveProperties(Properties properties, String filepath) {
		try {
			FileOutputStream fis = new FileOutputStream(filepath);
			properties.store(fis, AcceleoEngineMessages.getString("AcceleoPropertiesLoader.Saving")); //$NON-NLS-1$
			fis.close();
		} catch (FileNotFoundException e) {
			AcceleoEnginePlugin.log(e, false);
		} catch (IOException e) {
			AcceleoEnginePlugin.log(e, false);
		}
	}
}
