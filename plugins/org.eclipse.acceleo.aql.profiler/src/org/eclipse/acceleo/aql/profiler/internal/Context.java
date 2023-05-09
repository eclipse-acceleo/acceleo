/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import org.eclipse.acceleo.aql.profiler.ProfilerFactory;
import org.eclipse.emf.ecore.EObject;

/**
 * Profiling context.
 * 
 * @param <T>
 *            the type of the current {@link ProfileEntry}
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class Context<T extends ProfileEntry> {
	/**
	 * Cache {@link EObject} -> {@link Context}.
	 */
	private final Map<EObject, Context<?>> childrenCache = new HashMap<EObject, Context<?>>();

	/**
	 * The parent {@link Context} if any, <code>null</code> otherwise.
	 */
	private final Context<?> parent;

	/**
	 * Current {@link ProfileEntry}.
	 */
	private final T currentEntry;

	/**
	 * Constructor.
	 * 
	 * @param parent
	 *            parent {@link Context} if any
	 * @param entry
	 *            the current {@link ProfileEntry}
	 */
	public Context(Context<?> parent, T entry) {
		this.parent = parent;
		currentEntry = entry;
	}

	/**
	 * Gets for the {@link Context} that match the given {@link EObject}.
	 * 
	 * @param <L>
	 *            the type of child {@link ProfileEntry}
	 * @param monitored
	 *            the {@link EObject} to monitor
	 * @param factory
	 *            the factory to use to create {@link ProfileEntry} if the child {@link Context} can't be
	 *            found
	 * @return the {@link Context}
	 */
	public <L extends ProfileEntry> Context<L> getChildContext(EObject monitored, ProfilerFactory factory) {
		Context<L> childContext = (Context<L>)childrenCache.get(monitored);
		if (childContext == null) {
			final L entry = (L)factory.createProfileEntry();
			entry.setMonitored(monitored);
			childContext = new Context<L>(this, entry);
			childrenCache.put(monitored, childContext);
		}
		return childContext;
	}

	/**
	 * Gets for the current {@link ProfileEntry}.
	 * 
	 * @return the current {@link ProfileEntry}
	 */
	public T getcurrentEntry() {
		return currentEntry;
	}

	/**
	 * Gets for the {@link Context parent} of this {@link Context}.
	 * 
	 * @return the {@link Context parent}
	 */
	public Context<?> getParent() {
		return parent;
	}

}
