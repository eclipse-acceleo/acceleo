/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

package org.eclipse.acceleo.aql.profiler.internal;

import org.eclipse.acceleo.aql.profiler.ProfileEntry;
import org.eclipse.acceleo.aql.profiler.ProfileResource;
import org.eclipse.acceleo.aql.profiler.ProfilerFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * The profiler implementation for tree representation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class TreeProfiler extends AbstractProfiler {

	/**
	 * The profiling current {@link Context}.
	 */
	private Context<? extends ProfileEntry> currentContext;

	private final ProfilerFactory profilerFactory;

	/**
	 * Containing {@link ProfileResource}.
	 */
	private ProfileResource resource;

	/**
	 * constructor.
	 * 
	 * @param profilerFactory
	 *            the {@link ProfilerFactory} to create the profiler model
	 */
	public TreeProfiler(ProfilerFactory profilerFactory) {
		this.profilerFactory = profilerFactory;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.obeo.agility.profiler.IProfiler#reset()
	 */
	public void reset() {
		currentContext = null;
		resource = null;
	}

	public <L extends ProfileEntry> L start(EObject monitored) {
		final Context<L> nextContext;
		if (currentContext != null) {
			nextContext = currentContext.<L> getChildContext(monitored, profilerFactory);
			nextContext.getcurrentEntry().start();
			currentContext.getcurrentEntry().getCallees().add(nextContext.getcurrentEntry());
		} else {
			final ProfileEntry entry = profilerFactory.createProfileEntry();
			entry.setMonitored(monitored);
			if (resource == null) {
				resource = profilerFactory.createProfileResource();
			}
			resource.setEntry(entry);
			entry.start();
			nextContext = new Context<L>(null, (L)entry);
		}
		currentContext = nextContext;
		return nextContext.getcurrentEntry();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.obeo.agility.profiler.IProfiler#stop()
	 */
	public <L extends ProfileEntry> L stop() {
		currentContext.getcurrentEntry().stop();
		L res = (L)currentContext.getcurrentEntry();
		currentContext = currentContext.getParent();
		return res;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fr.obeo.agility.profiler.IProfiler#getResource()
	 */
	public ProfileResource getResource() {
		return resource;
	}

}
