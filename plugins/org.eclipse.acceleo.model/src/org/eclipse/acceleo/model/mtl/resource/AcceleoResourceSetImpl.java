/*******************************************************************************
 * Copyright (c) 2006, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.model.mtl.resource;

import java.io.File;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * A custom resource set used by Acceleo to provide a support for binary/xmi resources in stand alone.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class AcceleoResourceSetImpl extends ResourceSetImpl {

	/**
	 * The constructor.
	 */
	public AcceleoResourceSetImpl() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceSetImpl#getResourceFactoryRegistry()
	 */
	@Override
	public Registry getResourceFactoryRegistry() {
		if (resourceFactoryRegistry == null) {
			Registry registry = super.getResourceFactoryRegistry();
			resourceFactoryRegistry = new AcceleoResourceFactoryRegistry(registry);
		}
		return resourceFactoryRegistry;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceSetImpl#delegatedGetResource(org.eclipse.emf.common.util.URI,
	 *      boolean)
	 */
	@Override
	protected Resource delegatedGetResource(URI uri, boolean loadOnDemand) {
		// The resource at the given URI has not been found, let's try something else.

		// Use case: Stand Alone Java launch for an uri like the following one:
		// platform:/resource/org.eclipse.acceleo.module.example.uml2java.helios/bin/org/eclipse/acceleo/module/example/uml2java/helios/common/common.emtl
		String[] segments = uri.segments();
		boolean shouldConsider = segments.length > 0 && "resource".equals(segments[0]); //$NON-NLS-1$

		if (!EMFPlugin.IS_ECLIPSE_RUNNING
				&& uri.toString().startsWith("platform:/resource/") && resources.size() > 0 && shouldConsider) { //$NON-NLS-1$
			Resource foundResource = null;

			// Let's look for the first segment with the same name.
			// resource, org.eclipse.acceleo.module.example.uml2java.helios, bin, org, eclipse, acceleo,
			// module, example, uml2java, helios, common, common.emt
			String rootContainerName = segments[1];
			for (Resource resource : resources) {
				URI loadedResourceURI = resource.getURI();
				if (loadedResourceURI.isFile()
						&& URI.decode(loadedResourceURI.toString()).contains(rootContainerName)) {
					// We have loaded a "file:/" resource with the same rootContainer
					String prefix = loadedResourceURI.toString().substring(0,
							loadedResourceURI.toString().indexOf(rootContainerName));
					String newURI = URI.decode(prefix)
							+ uri.toString().substring("platform:/resource/".length()); //$NON-NLS-1$
					File file = new File(newURI);
					if (!file.exists()) {
						file = new File(newURI.substring("file:".length())); //$NON-NLS-1$
					}
					if (file.exists()) {
						foundResource = this.getResource(URI.createURI(newURI), loadOnDemand);
						break;
					}
				} else if (loadedResourceURI.isPlatformResource()
						&& loadedResourceURI.toString().contains(rootContainerName)) {
					// We have loaded a "platform:/resource" resource with the same rootContainer

				}
			}

			if (foundResource != null) {
				return foundResource;
			}
			// We don"t care about the "platform:/resource/" segments.

			// Let's see if the next segments do not match something in the path of another already loaded
			// resource.

			// If it does, let's try to find the other resource.

		}

		return super.delegatedGetResource(uri, loadOnDemand);
	}
}
