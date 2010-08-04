/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template.outline.actions;

import org.eclipse.acceleo.parser.cst.Query;
import org.eclipse.acceleo.parser.cst.Template;
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
		// 2-If we are comparing two templates or two queries, we compare them with their name.
		// 3-Queries come before templates.
		// 4-We don't care of the rest.
		int result = 0;
		if (e1 instanceof Template && e2 instanceof Template) {
			Template temp1 = (Template)e1;
			Template temp2 = (Template)e2;
			result = temp1.getName().compareTo(temp2.getName());
		} else if (e1 instanceof Query && e2 instanceof Query) {
			Query q1 = (Query)e1;
			Query q2 = (Query)e2;
			result = q1.getName().compareTo(q2.getName());
		} else if (e1 instanceof Template && e2 instanceof Query) {
			result = 1;
		} else if (e1 instanceof Query && e2 instanceof Template) {
			result = -1;
		} else if (e1 instanceof TypedModel) {
			result = -1;
		} else if (e2 instanceof TypedModel) {
			result = 1;
		}
		return result;
	}
}
