/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.library.connector.ILibrary;
import org.eclipse.acceleo.common.utils.CompactLinkedHashSet;

/**
 * This will allow Acceleo to know which {@link ILibrary} implementation are available to the evaluation
 * context.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 0.8
 */
public final class AcceleoLibraryConnectorsRegistry {

	/** Singleton instance of the registry. */
	public static final AcceleoLibraryConnectorsRegistry INSTANCE = new AcceleoLibraryConnectorsRegistry();

	/** This will contain the services registered for Acceleo evaluations. */
	private final Set<Class<ILibrary>> registeredLibraryConnectors = new CompactLinkedHashSet<Class<ILibrary>>();

	/**
	 * This will contain the {@link ILibrary} class implementation keyed by the file extension they are able
	 * to manage.
	 */
	private final Map<String, Class<?>> connectorByFileExtension = new HashMap<String, Class<?>>();

	/**
	 * This class is a singleton. Access instance through {@link #INSTANCE}.
	 */
	private AcceleoLibraryConnectorsRegistry() {
		// Hide constructor
	}

	/**
	 * Adds a library implementation to this registry.
	 * 
	 * @param connector
	 *            the {@link ILibrary} implementation
	 * @param fileExtension
	 *            the file extension of files managed by <code>connector</code> class.
	 */
	public void addLibraryConnector(Class<ILibrary> connector, String fileExtension) {
		this.registeredLibraryConnectors.add(connector);
		this.connectorByFileExtension.put(fileExtension, connector);
	}

	/**
	 * Removes a library from this library.
	 * 
	 * @param attribute
	 *            the name of the class implementing the {@link ILibrary} (as returned by
	 *            {@link Class#getName()}.
	 */
	public void removeLibraryConnector(String attribute) {
		for (Class<?> connector : this.registeredLibraryConnectors) {
			if (attribute.equals(connector.getName())) {
				this.registeredLibraryConnectors.remove(connector);

				for (Iterator<Map.Entry<String, Class<?>>> it = this.connectorByFileExtension.entrySet()
						.iterator(); it.hasNext();) {
					Map.Entry<String, Class<?>> entry = it.next();
					if (entry.getValue().equals(connector)) {
						it.remove();
					}
				}
			}
		}
	}

	public Set<Class<ILibrary>> getAllRegisteredLibraryConnector() {
		return this.registeredLibraryConnectors;
	}

	/**
	 * Return the library connector class managing this kind of resources. The connector is match against the
	 * file extension of the resource.
	 * 
	 * @param resource
	 *            the path to the resource (only the file extension is taken into account).
	 * @return the {@link ILibrary} class managing this kind of resource
	 */
	@SuppressWarnings("unchecked")
	public Class<ILibrary> getConnectorForResource(String resource) {
		Class<ILibrary> ret = null;
		int idx = resource.lastIndexOf('.');

		if (idx > 0 && idx < resource.length()) {
			String fileExtension = resource.substring(idx + 1, resource.length());
			ret = (Class<ILibrary>)this.connectorByFileExtension.get(fileExtension);
		}

		return ret;
	}

	/**
	 * Clean this registry by releasing all hold resources.
	 */
	public void clearRegistry() {
		this.registeredLibraryConnectors.clear();
		this.connectorByFileExtension.clear();
	}
}
