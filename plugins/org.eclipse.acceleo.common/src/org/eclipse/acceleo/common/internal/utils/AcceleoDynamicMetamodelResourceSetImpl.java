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
package org.eclipse.acceleo.common.internal.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl.PlatformSchemeAware;

/**
 * The resource set used to load the dynamic metamodels.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class AcceleoDynamicMetamodelResourceSetImpl extends ResourceSetImpl {

	/** The resource set used by all the dynamic metamodels. */
	public static final ResourceSet DYNAMIC_METAMODEL_RESOURCE_SET = new AcceleoDynamicMetamodelResourceSetImpl();

	/**
	 * The constructor.
	 */
	public AcceleoDynamicMetamodelResourceSetImpl() {
		super();
		if (this.loadOptions == null) {
			this.loadOptions = new HashMap<Object, Object>();
		}
		this.loadOptions.put(XMLResource.OPTION_URI_HANDLER, new AcceleoDynamicURIHandler());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.resource.impl.ResourceSetImpl#delegatedGetResource(org.eclipse.emf.common.util.URI,
	 *      boolean)
	 */
	@Override
	protected Resource delegatedGetResource(URI uri, boolean loadOnDemand) {
		URIConverter converter = this.getURIConverter();
		if (converter != null && EMFPlugin.IS_ECLIPSE_RUNNING) {
			for (Resource resource : this.resources) {
				URI resourceURI = resource.getURI();
				for (Resource resource2 : this.resources) {
					URI resourceURI2 = resource2.getURI();

					IPath path = new Path(resourceURI.path());
					IPath path2 = new Path(resourceURI2.path());
					if (!resourceURI.path().equals(resourceURI2.path())
							&& makeRelativeTo(path2, path.removeLastSegments(1)).toString().equals(
									uri.toString())) {
						return resource2;
					}
				}
			}
		}
		return super.delegatedGetResource(uri, loadOnDemand);
	}

	/**
	 * Make relative to.
	 * 
	 * @param path1
	 *            The first path
	 * @param path2
	 *            the second path
	 * @return The first path relative to the second path.
	 * @since 3.1
	 */
	public static IPath makeRelativeTo(IPath path1, IPath path2) {
		IPath path = path1;

		// can't make relative if devices are not equal
		if (path1.getDevice() == path2.getDevice()
				|| (path1.getDevice() != null && path1.getDevice().equalsIgnoreCase(path2.getDevice()))) {
			int commonLength = path1.matchingFirstSegments(path2);
			final int differenceLength = path2.segmentCount() - commonLength;
			final int newSegmentLength = differenceLength + path1.segmentCount() - commonLength;
			if (newSegmentLength == 0) {
				return Path.EMPTY;
			}
			path = new Path(""); //$NON-NLS-1$
			String[] newSegments = new String[newSegmentLength];
			// add parent references for each segment different from the base
			Arrays.fill(newSegments, 0, differenceLength, ".."); //$NON-NLS-1$
			// append the segments of this path not in common with the base
			System.arraycopy(path1.segments(), commonLength, newSegments, differenceLength, newSegmentLength
					- differenceLength);
			for (String segment : newSegments) {
				path = path.append(new Path(segment));
			}
		}

		return path;
	}

	/**
	 * Utility class used to convert platform:/plugin uris to http://www... ones.
	 * 
	 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
	 * @since 3.2
	 */
	public class AcceleoDynamicURIHandler extends PlatformSchemeAware {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl.PlatformSchemeAware#deresolve(org.eclipse.emf.common.util.URI)
		 */
		@Override
		public URI deresolve(URI uri) {
			return super.deresolve(uri);

		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.ecore.xmi.impl.URIHandlerImpl#resolve(URI)
		 */
		@Override
		public URI resolve(URI uri) {
			// platform:/plugin/org.eclipse.emf.ecore/model/Ecore.ecore#//EClass
			Map<String, URI> map = EcorePlugin.getEPackageNsURIToGenModelLocationMap();
			URI trimmedURI = uri.trimFragment();
			Set<Entry<String, URI>> entries = map.entrySet();
			for (Entry<String, URI> entry : entries) {
				URI dummyValue = entry.getValue();
				String dummy = dummyValue.toString();
				if (dummy.endsWith("genmodel")) { //$NON-NLS-1$
					dummy = dummy.substring(0, dummy.length() - "genmodel".length()); //$NON-NLS-1$
					dummy = dummy + "ecore"; //$NON-NLS-1$
				}
				if (dummy.equals(trimmedURI.toString())) {
					URI newURI = URI.createURI(entry.getKey());
					newURI = newURI.appendFragment(uri.fragment());
					// http://www.eclipse.org/emf/2002/Ecore#//EClass
					return newURI;
				}
			}
			return super.resolve(uri);

		}
	}
}
