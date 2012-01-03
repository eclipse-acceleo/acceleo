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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.pullup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * The content provider of the table viewer of the first page of the wizard of the pull up refactoring.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoPullUpTableContentProvider implements IStructuredContentProvider {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Module) {
			List<Template> templates = new ArrayList<Template>();
			Module module = (Module)inputElement;
			EList<EObject> eContents = module.eContents();

			if (eContents.size() > 0) {
				for (EObject eObject : eContents) {
					if (eObject instanceof Template && ((Template)eObject).getOverrides().size() == 0) {
						templates.add((Template)eObject);
					}
				}
			}

			if (templates.size() > 0) {
				return templates.toArray(new Object[templates.size()]);
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		// do nothing

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
	 *      java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// do nothing

	}

}
