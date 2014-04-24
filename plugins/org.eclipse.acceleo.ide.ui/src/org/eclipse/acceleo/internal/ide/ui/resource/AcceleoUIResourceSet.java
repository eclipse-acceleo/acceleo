/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.resource;

import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.util.List;

import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.model.mtl.resource.AcceleoResourceSetImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

/**
 * The resource set used by the Acceleo UI.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public final class AcceleoUIResourceSet {

	/**
	 * The sole resource set of the UI.
	 */
	private static final ResourceSet RESOURCE_SET = new AcceleoResourceSetImpl();

	static {
		RESOURCE_SET.setPackageRegistry(AcceleoPackageRegistry.INSTANCE);
	}

	/**
	 * The constructor.
	 */
	private AcceleoUIResourceSet() {
		// prevent instantiation
	}

	/**
	 * Remove the resource with the given uri from the resource set.
	 * 
	 * @param uri
	 *            The uri of the resource.
	 * @throws IOException
	 *             In case of problem during the removal of the resource
	 */
	public static void removeResource(URI uri) throws IOException {
		synchronized(RESOURCE_SET) {
			Resource resource = RESOURCE_SET.getResource(uri, false);
			if (resource != null) {
				resource.unload();
			}
		}
	}

	/**
	 * Returns the resource with the given uri from the resource set. If the resource is not in the resource
	 * set, it is loaded instead.
	 * 
	 * @param uri
	 *            The uri of the resource
	 * @return The resource with the given uri from the resource set.
	 * @throws IOException
	 *             In case of problems during the loading.
	 */
	public static EObject getResource(URI uri) throws IOException {
		synchronized(RESOURCE_SET) {
			return ModelUtils.load(uri, RESOURCE_SET, true);
		}
	}

	/**
	 * Unload all the resources in the resource set.
	 */
	public static void clear() {
		synchronized(RESOURCE_SET) {
			List<Resource> resources = RESOURCE_SET.getResources();
			for (Resource resource : resources) {
				resource.unload();
			}
		}
	}

	/**
	 * Returns the list of the resources of this resource set.
	 * 
	 * @return The list of the resources of this resource set.
	 */
	public static List<Resource> getResources() {
		synchronized(RESOURCE_SET) {
			return ImmutableList.copyOf(RESOURCE_SET.getResources());
		}
	}
}
