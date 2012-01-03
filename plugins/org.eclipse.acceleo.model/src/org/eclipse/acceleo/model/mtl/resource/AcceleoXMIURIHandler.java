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
package org.eclipse.acceleo.model.mtl.resource;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl.PlatformSchemeAware;

/**
 * The Acceleo XMI URI Handler.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class AcceleoXMIURIHandler extends PlatformSchemeAware {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl.PlatformSchemeAware#deresolve(org.eclipse.emf.common.util.URI)
	 */
	@Override
	public URI deresolve(URI uri) {
		Map<String, String> dynamicEcorePackagePaths = AcceleoPackageRegistry.INSTANCE
				.getDynamicEcorePackagePaths();
		Iterator<Entry<String, String>> iterator = dynamicEcorePackagePaths.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> next = iterator.next();
			if (next.getValue().equals(uri.trimFragment().toString())) {
				return URI.createURI(next.getKey()).appendFragment(uri.fragment());
			}
		}
		return super.deresolve(uri);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl#resolve(URI)
	 */
	@Override
	public URI resolve(URI uri) {
		Map<String, String> dynamicEcorePackagePaths = AcceleoPackageRegistry.INSTANCE
				.getDynamicEcorePackagePaths();
		Iterator<Entry<String, String>> iterator = dynamicEcorePackagePaths.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> next = iterator.next();
			if (next.getKey().equals(uri.trimFragment().toString())) {
				return URI.createURI(next.getValue()).appendFragment(uri.fragment());
			}
		}
		return super.resolve(uri);
	}
}
