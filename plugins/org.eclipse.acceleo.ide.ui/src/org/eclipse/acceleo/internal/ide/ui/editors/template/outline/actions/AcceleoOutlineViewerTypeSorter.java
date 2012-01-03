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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.parser.cst.Macro;
import org.eclipse.acceleo.parser.cst.ModuleElement;
import org.eclipse.acceleo.parser.cst.Query;
import org.eclipse.acceleo.parser.cst.Template;
import org.eclipse.acceleo.parser.cst.TypedModel;
import org.eclipse.acceleo.parser.cst.Variable;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

/**
 * The sorter (by type) of the treeviewer of the outline view.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoOutlineViewerTypeSorter extends ViewerSorter {

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
		// 2- Module elements are ordered by the name of their first parameter.
		int result = 0;
		if (e1 instanceof ModuleElement && e2 instanceof ModuleElement) {
			ModuleElement mod1 = (ModuleElement)e1;
			ModuleElement mod2 = (ModuleElement)e2;
			List<Variable> parameter1 = getParameter(mod1);
			List<Variable> parameter2 = getParameter(mod2);

			if (parameter1.size() == 0) {
				result = 1;
			} else if (parameter2.size() == 0) {
				result = -1;
			} else if (parameter1.get(0) != null && parameter1.get(0).getType() != null) {
				Variable variable1 = parameter1.get(0);
				Variable variable2 = parameter2.get(0);
				result = variable1.getType().compareTo(variable2.getType());
			}

		} else if (e1 instanceof TypedModel) {
			result = -1;
		} else if (e2 instanceof TypedModel) {
			result = 1;
		}
		return result;
	}

	/**
	 * Returns the list of variables of a module element.
	 * 
	 * @param m
	 *            The module element.
	 * @return The variables of the module element.
	 */
	private List<Variable> getParameter(ModuleElement m) {
		List<Variable> result = new ArrayList<Variable>();
		if (m instanceof Query) {
			Query query = (Query)m;
			result = query.getParameter();
		} else if (m instanceof Template) {
			Template template = (Template)m;
			result = template.getParameter();
		} else if (m instanceof Macro) {
			Macro macro = (Macro)m;
			result = macro.getParameter();
		}
		return result;
	}
}
