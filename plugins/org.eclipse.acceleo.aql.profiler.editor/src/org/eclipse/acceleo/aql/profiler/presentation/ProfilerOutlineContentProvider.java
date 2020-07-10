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
package org.eclipse.acceleo.aql.profiler.presentation;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.acceleo.aql.profiler.LoopProfileEntry;
import org.eclipse.acceleo.aql.profiler.ProfileEntry;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;

/**
 * Content provider for the profiler outline.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ProfilerOutlineContentProvider extends AdapterFactoryContentProvider {

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory
	 *            the adapter factory
	 */
	public ProfilerOutlineContentProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public Object[] getChildren(Object object) {
		if (object instanceof LoopProfileEntry) {
			final Set<ProfileEntry> sorted = new TreeSet<ProfileEntry>(new Comparator<ProfileEntry>() {
				public int compare(ProfileEntry o1, ProfileEntry o2) {
					long diff = o2.getDuration() - o1.getDuration();
					if (diff != 0) {
						return (int)diff;
					}
					return o1.getMonitored().toString().compareTo(o2.getMonitored().toString());
				}
			});

			sorted.addAll(((LoopProfileEntry)object).getLoopElements());
			return sorted.toArray();
		}
		return new Object[] {};
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#hasChildren(java.lang.Object)
	 */
	@Override
	public boolean hasChildren(Object object) {
		return object instanceof LoopProfileEntry && ((LoopProfileEntry)object).getLoopElements().size() > 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object object) {
		return getChildren(object);
	}
}
