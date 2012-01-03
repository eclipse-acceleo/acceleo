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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.references;

import org.eclipse.jface.viewers.ViewerSorter;

/**
 * This class sorts ReferenceEntries.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class ReferenceSorter extends ViewerSorter {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ViewerComparator#category(java.lang.Object)
	 */
	@Override
	public int category(Object element) {
		if (element instanceof ReferenceEntry) {
			return 0;
		}
		return 1;
	}
}
