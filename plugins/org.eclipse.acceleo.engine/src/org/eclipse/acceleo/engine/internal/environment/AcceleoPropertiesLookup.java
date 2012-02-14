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
package org.eclipse.acceleo.engine.internal.environment;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.emf.common.EMFPlugin;

/**
 * This class will be used to store the {@link ResourceBundle}s that have been added to a give generation
 * context and allow lookup through all of these in one go.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoPropertiesLookup {
	/** The whole list of resource bundles accessible by the generation engine. */
	private final List<ResourceBundle> bundles = new ArrayList<ResourceBundle>();

	/** This will map the loaded bundles to their file name. */
	private final Map<String, List<ResourceBundle>> bundlesByFileName = new HashMap<String, List<ResourceBundle>>();

	/**
	 * This will contain the custom properties for this engine, properties that will always take precedence
	 * over those contained within {@link #loadedProperties} no matter what.
	 */
	private final Properties customProperties = new Properties();

	/**
	 * This can be used to add custom properties to the engine. These will be available through the
	 * getProperty() services.
	 * 
	 * @param resourceBundle
	 *            The resource bundle.
	 * @param fileName
	 *            The name of the properties file.
	 * @since 3.1
	 */
	public void addProperties(PropertyResourceBundle resourceBundle, String fileName) {
		List<ResourceBundle> nameSakes = bundlesByFileName.get(fileName);
		if (nameSakes == null) {
			nameSakes = new ArrayList<ResourceBundle>();
			bundlesByFileName.put(fileName, nameSakes);
		}
		nameSakes.add(resourceBundle);
		bundles.add(resourceBundle);
	}

	/**
	 * This can be used to add custom properties to the engine. These will be available through the
	 * getProperty() services, <em>note</em> however that there can only be a single pair with a given key,
	 * and these properties will <em>always</em> take precedence over properties file-defined pairs.
	 * 
	 * @param customProps
	 *            Pairs that are to be added to the generation context.
	 */
	public void addProperties(Map<String, String> customProps) {
		customProperties.putAll(customProps);
	}

	/**
	 * This will try and load a bundle for the given properties file path.
	 * 
	 * @param propertiesHolder
	 *            Qualified path to the properties file.
	 * @throws MissingResourceException
	 *             This will be thrown if we cannot locate the properties file in the current classpath.
	 */
	public void addProperties(String propertiesHolder) throws MissingResourceException {
		ResourceBundle bundle;
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			bundle = AcceleoWorkspaceUtil.INSTANCE.getResourceBundle(propertiesHolder);
		} else {
			bundle = ResourceBundle.getBundle(propertiesHolder);
		}
		String fileName = propertiesHolder;
		if (fileName.lastIndexOf('.') != -1) {
			fileName = fileName.substring(fileName.lastIndexOf('.') + 1);
		}
		fileName += ".properties"; //$NON-NLS-1$

		bundles.add(bundle);
		List<ResourceBundle> nameSakes = bundlesByFileName.get(fileName);
		if (nameSakes == null) {
			nameSakes = new ArrayList<ResourceBundle>();
			bundlesByFileName.put(fileName, nameSakes);
		}
		nameSakes.add(bundle);
	}

	/**
	 * Returns the value of the first key/value pair found in the registered properties.
	 * 
	 * @param key
	 *            Key of the value we seek.
	 * @return Value of the first key/value pair found in the registered properties.
	 */
	public String getProperty(String key) {
		if (customProperties.containsKey(key)) {
			return customProperties.getProperty(key);
		}

		ResourceBundle bundle = null;
		for (ResourceBundle candidate : bundles) {
			if (containsKey(candidate, key)) {
				bundle = candidate;
				break;
			}
		}

		String value = null;
		if (bundle != null) {
			value = bundle.getString(key);
		}

		if (value == null) {
			if (EMFPlugin.IS_ECLIPSE_RUNNING && AcceleoPreferences.isDebugMessagesEnabled()) {
				AcceleoEnginePlugin.log(AcceleoEngineMessages.getString(
						"AcceleoPropertiesLookup.PropertiesNotFound", key), false); //$NON-NLS-1$
			}

			value = ""; //$NON-NLS-1$
		}

		return value;
	}

	/**
	 * Returns the value of the first key/value pair found in the registered properties of the given name.
	 * 
	 * @param propertiesFileName
	 *            Name of the properties file in which to seek the property <code>key</code>.
	 * @param key
	 *            Key of the value we seek.
	 * @return Value of the first key/value pair found in the registered properties.
	 */
	public String getProperty(String propertiesFileName, String key) {
		if (customProperties.containsKey(key)) {
			return customProperties.getProperty(key);
		}

		String soughtPropertiesFile = propertiesFileName;
		String propertiesExtension = ".properties"; //$NON-NLS-1$
		if (!propertiesFileName.endsWith(propertiesExtension)) {
			soughtPropertiesFile += propertiesExtension;
		}

		List<ResourceBundle> candidates = bundlesByFileName.get(soughtPropertiesFile);
		ResourceBundle bundle = null;
		if (candidates != null) {
			for (ResourceBundle candidate : candidates) {
				if (containsKey(candidate, key)) {
					bundle = candidate;
					break;
				}
			}
		}

		String value = null;
		if (bundle != null) {
			value = bundle.getString(key);
		}

		if (value == null) {
			if (EMFPlugin.IS_ECLIPSE_RUNNING && AcceleoPreferences.isDebugMessagesEnabled()) {
				AcceleoEnginePlugin.log(AcceleoEngineMessages.getString(
						"AcceleoPropertiesLookup.PropertiesNotFound", key), false); //$NON-NLS-1$
			}
			value = ""; //$NON-NLS-1$
		}

		return value;
	}

	/**
	 * This will iterate over the keys of a given bundle (and its parents) and return <code>true</code> iff it
	 * contains the given key.
	 * 
	 * @param bundle
	 *            The bundle in which to find a given key.
	 * @param lookup
	 *            The key we seek in <code>bundle</code>
	 * @return <code>true</code> if <code>lookup</code> is the key of a pair contained in <code>bundle</code>.
	 */
	private boolean containsKey(ResourceBundle bundle, String lookup) {
		Enumeration<String> enumeration = bundle.getKeys();
		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			if (key.equals(lookup)) {
				return true;
			}
		}
		return false;
	}
}
