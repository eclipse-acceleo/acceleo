/*******************************************************************************
 * Copyright (c) 2017, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.profiler.internal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.acceleo.aql.profiler.IProfiler;
import org.eclipse.acceleo.aql.profiler.ProfileEntry;
import org.eclipse.acceleo.aql.profiler.ProfilerFactory;
import org.eclipse.acceleo.aql.profiler.ProfilerPackage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public abstract class AbstractProfiler implements IProfiler {

	/**
	 * The EObject representing time that is not profiled.
	 */
	public static final EObject INTERNAL = ProfilerPackage.eINSTANCE.getEClassifier("Internal"); //$NON-NLS-1$

	/**
	 * Save profiling results to the given URI.
	 * 
	 * @param modelURI
	 *            the {@link URI} where to save results.
	 * @throws IOException
	 *             if model serialization fail
	 */
	public void save(URI modelURI) throws IOException {
		addDefaultNodes();
		computePercentage();
		if (getResource() != null) {
			save(getResource(), modelURI);
		}
	}

	/**
	 * Compute percentage for the current profile tree.
	 */
	private void computePercentage() {
		if (getResource() != null && getResource().getEntry() != null) {
			ProfileEntry root = getResource().getEntry();
			final long baseTime = root.getDuration();
			root.setPercentage(100.0f);
			final Iterator<EObject> it = root.eAllContents();
			while (it.hasNext()) {
				final ProfileEntry node = (ProfileEntry)it.next();
				if (node.getDuration() == baseTime) {
					// prevents NaN when baseTime == 0
					node.setPercentage(100.0f);
				} else {
					node.setPercentage(node.getDuration() * 100.0f / baseTime);
				}
			}
		}
	}

	/**
	 * Add default entries to reach 100%.
	 */
	protected void addDefaultNodes() {
		if (getResource() != null && getResource().getEntry() != null) {
			addDefaultNodes(getResource().getEntry());
		}
	}

	/**
	 * Add default entries to reach 100%.
	 * 
	 * @param entry
	 *            the root node
	 */
	private void addDefaultNodes(ProfileEntry entry) {
		long childrenDuration = 0;

		for (ProfileEntry child : entry.getCallees()) {
			childrenDuration += child.getDuration();
			addDefaultNodes(child);
		}
		if (entry.getCallees().size() > 0 && entry.getDuration() - childrenDuration > 0) {
			ProfileEntry def = ProfilerFactory.eINSTANCE.createProfileEntry();
			def.setCreationTime(System.currentTimeMillis());
			def.setDuration(entry.getDuration() - childrenDuration);
			def.setMonitored(INTERNAL);
			entry.getCallees().add(def);
		}
	}

	/**
	 * Serialize the current profiling data.
	 * 
	 * @param result
	 *            root element for profiling data
	 * @param modelURI
	 *            URI where to save data
	 * @throws IOException
	 *             if save fail
	 */
	private void save(EObject result, URI modelURI) throws IOException {
		final ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put(
				Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
		final Resource newModelResource = resourceSet.createResource(modelURI);
		newModelResource.getContents().add(result);
		final Map<?, ?> options = new HashMap<Object, Object>();
		newModelResource.save(options);
		newModelResource.unload();
	}

}
