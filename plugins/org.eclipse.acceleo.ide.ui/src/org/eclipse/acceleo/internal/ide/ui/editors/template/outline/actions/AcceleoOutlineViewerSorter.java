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
package org.eclipse.acceleo.internal.ide.ui.editors.template.outline.actions;

import org.eclipse.acceleo.parser.cst.ModuleElement;
import org.eclipse.acceleo.parser.cst.TypedModel;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * The sorter of the treeviewer of the outline view.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoOutlineViewerSorter extends ViewerSorter {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer,
	 *      java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Viewer treeViewer, Object e1, Object e2) {
		// Rules:
		// 1-TypedModel always come first.
		// 2- Module elements are ordered by their names.
		int result = 0;
		if (e1 instanceof ModuleElement && e2 instanceof ModuleElement) {
			ModuleElement mod1 = (ModuleElement)e1;
			ModuleElement mod2 = (ModuleElement)e2;
			result = mod1.getName().compareTo(mod2.getName());
		} else if (e1 instanceof TypedModel) {
			result = -1;
		} else if (e2 instanceof TypedModel) {
			result = 1;
		}
		return result;
	}
}
