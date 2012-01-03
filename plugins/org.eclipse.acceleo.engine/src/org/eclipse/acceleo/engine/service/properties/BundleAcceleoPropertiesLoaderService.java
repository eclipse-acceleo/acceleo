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

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.PropertyResourceBundle;

import org.eclipse.acceleo.engine.AcceleoEnginePlugin;
import org.eclipse.acceleo.engine.service.AcceleoService;
import org.eclipse.emf.common.EMFPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.packageadmin.PackageAdmin;

/**
 * This property loader will only be used if eclipse is running, it will look for properties files in bundles.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class BundleAcceleoPropertiesLoaderService extends AbstractAcceleoPropertiesLoaderService {

	/**
	 * Indicates that the path is in a plugin.
	 * 
	 * @since 3.1
	 */
	public static final String PLATFORM_PLUGIN = "platform:/plugin/"; //$NON-NLS-1$

	/**
	 * The extension used by properties files.
	 * 
	 * @since 3.1
	 */
	protected static final String PROPERTIES_FILES_EXTENSION = ".properties"; //$NON-NLS-1$

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
	 * The constructor.
	 * 
	 * @param acceleoService
	 *            The Acceleo service.
	 * @param bundle
	 *            The bundle in which we will look for the properties files.
	 * @param forceProperties
	 *            Indicates if the loaded properties should have the priority.
	 * @since 3.1
	 */
	public BundleAcceleoPropertiesLoaderService(AcceleoService acceleoService, Bundle bundle,
			boolean forceProperties) {
		this.acceleoService = acceleoService;
		this.bundle = bundle;
		this.forceProperties = forceProperties;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.service.properties.AbstractAcceleoPropertiesLoader#alternatePropertiesLoading(java.lang.String)
	 */
	@Override
	protected Properties alternatePropertiesLoading(String filepath) {
		Properties properties = new Properties();
		if (EMFPlugin.IS_ECLIPSE_RUNNING && bundle != null) {
			if (!filepath.startsWith(PLATFORM_PLUGIN)) {
				properties = this.loadPropertiesFromCurrentBundle(filepath);

				// If we don't find the properties file
				if (properties.isEmpty()) {
					// Try the filepath as an absolute path
					properties = this.loadProperties(filepath);
				}
			} else if (filepath.indexOf('/', PLATFORM_PLUGIN.length() + 1) != -1) {
				properties = this.loadPropertiesFromAnotherBundle(filepath);
			}

		}
		return properties;
	}

	/**
	 * Returns the properties in the properties file in the bundle of the generator at the given path.
	 * 
	 * @param pathInBundle
	 *            Path in bundle.
	 * @return The properties in the properties file in the bundle of the generator at the given path.
	 * @since 3.1
	 */
	protected Properties loadPropertiesFromCurrentBundle(String pathInBundle) {
		Properties properties = new Properties();
		try {
			URL resource = bundle.getResource(pathInBundle);
			if (resource != null) {
				properties.load(resource.openStream());
			} else if (pathInBundle != null) {
				String filename = pathInBundle;

				final String dot = "."; //$NON-NLS-1$
				if (!filename.endsWith(PROPERTIES_FILES_EXTENSION) && filename.contains(dot)) {
					filename = filename.substring(filename.lastIndexOf(dot) + 1);
					filename = filename + PROPERTIES_FILES_EXTENSION;
				}

				Enumeration<?> entries = bundle.findEntries("/", filename, true); //$NON-NLS-1$
				Object firstEntry = null;
				if (entries != null && entries.hasMoreElements()) {
					firstEntry = entries.nextElement();
				}
				if (firstEntry instanceof URL) {
					properties.load(((URL)firstEntry).openStream());
				}
			}
		} catch (IOException e) {
			return null;
		}
		return properties;
	}

	/**
	 * Returns the properties from the properties file in another bundle.
	 * 
	 * @param pathInBundle
	 *            The path of the properties file.
	 * @return The properties from the properties file in another bundle.
	 * @since 3.1
	 */
	protected Properties loadPropertiesFromAnotherBundle(String pathInBundle) {
		// We will try to load a property file from another bundle.
		Properties properties = new Properties();

		// Remove "platform:/plugin/"
		String bundleName = pathInBundle.substring(PLATFORM_PLUGIN.length());
		bundleName = bundleName.substring(0, bundleName.indexOf('/'));

		String resourcePath = pathInBundle.substring(PLATFORM_PLUGIN.length());
		resourcePath = resourcePath.substring(resourcePath.indexOf('/'));

		Bundle[] bundles = null;

		BundleContext context = AcceleoEnginePlugin.getDefault().getBundle().getBundleContext();
		ServiceReference packageAdminReference = context.getServiceReference(PackageAdmin.class.getName());
		PackageAdmin packageAdmin = null;
		if (packageAdminReference != null) {
			packageAdmin = (PackageAdmin)context.getService(packageAdminReference);
		}

		if (packageAdmin != null) {
			bundles = packageAdmin.getBundles(bundleName, null);
		}
		if (packageAdminReference != null) {
			context.ungetService(packageAdminReference);
		}

		if (bundles != null) {
			// We now have all the bundle with the matching bundle name.
			for (Bundle resourceBundle : bundles) {
				URL resource = resourceBundle.getResource(resourcePath);
				if (resource != null) {
					try {
						properties.load(resource.openStream());
					} catch (IOException e) {
						AcceleoEnginePlugin.log(e, true);
					}
				}
				if (!properties.isEmpty()) {
					break;
				}
			}
		}
		return properties;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.service.properties.AbstractAcceleoPropertiesLoader#alternatePropertiesResourceBundleLoading(java.lang.String)
	 */
	@Override
	protected PropertyResourceBundle alternatePropertiesResourceBundleLoading(String filepath) {
		PropertyResourceBundle properties = null;
		if (EMFPlugin.IS_ECLIPSE_RUNNING && bundle != null) {
			if (!filepath.startsWith(PLATFORM_PLUGIN)) {
				properties = this.loadPropertiesResourceBundleFromCurrentBundle(filepath);

				// If we don't find the properties file
				if (properties == null) {
					// Try the filepath as an absolute path
					properties = this.loadPropertiesResourceBundle(filepath);
				}
			} else if (filepath.indexOf('/', PLATFORM_PLUGIN.length() + 1) != -1) {
				properties = this.loadPropertiesResourceBundleFromAnotherBundle(filepath);
			}

		}
		return properties;
	}

	/**
	 * Returns the properties resource bundle from the properties file in another bundle.
	 * 
	 * @param pathInBundle
	 *            The path of the properties file.
	 * @return The properties resource bundle from the properties file in another bundle.
	 * @since 3.1
	 */
	protected PropertyResourceBundle loadPropertiesResourceBundleFromAnotherBundle(String pathInBundle) {
		// We will try to load a property file from another bundle.
		PropertyResourceBundle properties = null;

		// Remove "platform:/plugin/"
		String bundleName = pathInBundle.substring(PLATFORM_PLUGIN.length());
		bundleName = bundleName.substring(0, bundleName.indexOf('/'));

		String resourcePath = pathInBundle.substring(PLATFORM_PLUGIN.length());
		resourcePath = resourcePath.substring(resourcePath.indexOf('/'));

		Bundle[] bundles = null;

		BundleContext context = AcceleoEnginePlugin.getDefault().getBundle().getBundleContext();
		ServiceReference packageAdminReference = context.getServiceReference(PackageAdmin.class.getName());
		PackageAdmin packageAdmin = null;
		if (packageAdminReference != null) {
			packageAdmin = (PackageAdmin)context.getService(packageAdminReference);
		}

		if (packageAdmin != null) {
			bundles = packageAdmin.getBundles(bundleName, null);
		}
		if (packageAdminReference != null) {
			context.ungetService(packageAdminReference);
		}

		if (bundles != null) {
			// We now have all the bundle with the matching bundle name.
			for (Bundle resourceBundle : bundles) {
				URL resource = resourceBundle.getResource(resourcePath);
				if (resource != null) {
					try {
						properties = new PropertyResourceBundle(resource.openStream());
					} catch (IOException e) {
						AcceleoEnginePlugin.log(e, true);
					}
				}
				if (properties != null) {
					break;
				}
			}
		}
		return properties;
	}

	/**
	 * Returns the properties resource bundle from the properties file in the bundle of the generator at the
	 * given path.
	 * 
	 * @param pathInBundle
	 *            Path in bundle.
	 * @return The properties resource bundle from the properties file in the bundle of the generator at the
	 *         given path.
	 * @since 3.1
	 */
	protected PropertyResourceBundle loadPropertiesResourceBundleFromCurrentBundle(String pathInBundle) {
		PropertyResourceBundle properties = null;
		try {
			URL resource = bundle.getResource(pathInBundle);
			if (resource != null) {
				properties = new PropertyResourceBundle(resource.openStream());
			} else if (pathInBundle != null) {
				String filename = pathInBundle;

				final String dot = "."; //$NON-NLS-1$
				if (!filename.endsWith(PROPERTIES_FILES_EXTENSION) && filename.contains(dot)) {
					filename = filename.substring(filename.lastIndexOf(dot) + 1);
					filename = filename + PROPERTIES_FILES_EXTENSION;
				}

				Enumeration<?> entries = bundle.findEntries("/", filename, true); //$NON-NLS-1$
				Object firstEntry = null;
				if (entries != null && entries.hasMoreElements()) {
					firstEntry = entries.nextElement();
				}
				if (firstEntry instanceof URL) {
					properties = new PropertyResourceBundle(((URL)firstEntry).openStream());
				}
			}
		} catch (IOException e) {
			return null;
		}
		return properties;
	}
}
