/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

package org.eclipse.acceleo.aql.profiler.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.acceleo.aql.profiler.ProfileEntry;
import org.eclipse.acceleo.aql.profiler.ProfileResource;
import org.eclipse.acceleo.aql.profiler.ProfilerFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * The profiler implementation for flat representation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class FlatProfiler extends AbstractProfiler {

	/**
	 * {@link ProfileEntry#getMonitored() Monitored} {@link EObject} to {@link ProfileEntry}.
	 */
	private final Map<EObject, ProfileEntry> map = new HashMap<EObject, ProfileEntry>();

	private final ProfilerFactory profilerFactory;

	/**
	 * The resource that started profiling.
	 */
	private final String startResource;

	/**
	 * Containing {@link ProfileResource}.
	 */
	private ProfileResource resource;

	/**
	 * the root {@link ProfileEntry}.
	 */
	private ProfileEntry root;

	/**
	 * The {@link ProfileEntry} stack.
	 */
	private final IStack<ProfileEntry> stack = new ArrayStack<ProfileEntry>(200);

	/**
	 * constructor.
	 * 
	 * @param profilerFactory
	 *            the {@link ProfilerFactory} to create the profiler model
	 * @param startResource
	 *            the resource that started profiling
	 */
	public FlatProfiler(ProfilerFactory profilerFactory, String startResource) {
		this.profilerFactory = profilerFactory;
		this.startResource = startResource;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.obeo.agility.profiler.IProfiler#reset()
	 */
	public void reset() {
		map.clear();
		resource = null;
		root = null;
		stack.clear();
	}

	public <L extends ProfileEntry> L start(EObject monitored) {
		if (resource == null) {
			resource = profilerFactory.createProfileResource();
			resource.setStartResource(startResource);
			root = profilerFactory.createProfileEntry();
			resource.setEntry(root);
			root.start();
		}
		L entry = (L)map.get(monitored);
		if (entry == null) {
			entry = (L)profilerFactory.createProfileEntry();
			entry.setMonitored(monitored);
			map.put(monitored, entry);
			root.getCallees().add(entry);
		}
		entry.start();
		stack.push(entry);
		return entry;
	}

	public <L extends ProfileEntry> L stop() {
		L currentEntry = (L)stack.pop();
		currentEntry.stop();
		if (stack.size() == 0) {
			root.stop();
		}
		return (L)currentEntry;
	}

	public ProfileResource getResource() {
		return resource;
	}

}
