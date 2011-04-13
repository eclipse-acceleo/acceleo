/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.internal.utils;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * The resource set used to load the dynamic metamodels.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class AcceleoDynamicMetamodelResourceSetImpl extends ResourceSetImpl {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceSetImpl#delegatedGetResource(org.eclipse.emf.common.util.URI,
	 *      boolean)
	 */
	@Override
	protected Resource delegatedGetResource(URI uri, boolean loadOnDemand) {
		URIConverter converter = this.getURIConverter();
		if (converter != null) {
			for (Resource resource : this.resources) {
				URI resourceURI = resource.getURI();
				for (Resource resource2 : this.resources) {
					URI resourceURI2 = resource2.getURI();

					// SBE FIXME IPath !!
					IPath path = new Path(resourceURI.path());
					IPath path2 = new Path(resourceURI2.path());
					if (!path.equals(path2)
							&& path2.makeRelativeTo(path.removeLastSegments(1)).toString().equals(
									uri.toString())) {
						return resource2;
					}
				}
			}
		}
		return super.delegatedGetResource(uri, loadOnDemand);
	}
}
