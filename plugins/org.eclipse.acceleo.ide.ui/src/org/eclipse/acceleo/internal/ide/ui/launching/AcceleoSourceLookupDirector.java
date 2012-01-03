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
package org.eclipse.acceleo.internal.ide.ui.launching;

import java.util.Set;

import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.ISourceContainerType;
import org.eclipse.debug.core.sourcelookup.ISourceLookupParticipant;
import org.eclipse.jdt.launching.sourcelookup.containers.JavaProjectSourceContainer;

/**
 * Acceleo source lookup director. Directs source lookup among a collection of source lookup participants, and
 * a common collection of source containers.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoSourceLookupDirector extends AbstractSourceLookupDirector {

	/**
	 * Filtered types.
	 */
	private static Set<String> fFilteredTypes;

	static {
		fFilteredTypes = new CompactHashSet<String>();
		fFilteredTypes.add(JavaProjectSourceContainer.TYPE_ID);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.sourcelookup.ISourceLookupDirector#initializeParticipants()
	 */
	public void initializeParticipants() {
		addParticipants(new ISourceLookupParticipant[] {new AcceleoSourceLookupParticipant() });
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.sourcelookup.AbstractSourceLookupDirector#supportsSourceContainerType(org.eclipse.debug.core.sourcelookup.ISourceContainerType)
	 */
	@Override
	public boolean supportsSourceContainerType(ISourceContainerType type) {
		return !fFilteredTypes.contains(type.getId());
	}
}
