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

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.TypedModel;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.WorkbenchLabelProvider;

/**
 * This class provide labels for the references search.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class ReferenceLabelProvider extends LabelProvider {

	/**
	 * The label provider of the workbench.
	 */
	private WorkbenchLabelProvider labelProvider;

	/**
	 * Constructor.
	 */
	public ReferenceLabelProvider() {
		labelProvider = new WorkbenchLabelProvider();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		Image result;
		if (element instanceof IResource) {
			result = labelProvider.getImage(element);
		} else if (element instanceof ReferenceEntry) {
			ReferenceEntry entry = (ReferenceEntry)element;
			if (entry.getMatch() instanceof ModuleElement || entry.getMatch() instanceof Variable
					|| entry.getMatch() instanceof Module || entry.getMatch() instanceof TypedModel) {
				result = AcceleoUIActivator.getDefault().getImage(
						"/icons/template-editor/" + (entry.getMatch()).eClass().getName() + ".gif"); //$NON-NLS-1$ //$NON-NLS-2$
			} else {
				result = AcceleoUIActivator.getDefault().getImage(
						"/icons/template-editor/ModelExpression.gif"); //$NON-NLS-1$
			}
		} else {
			result = AcceleoUIActivator.getDefault().getImage("/icons/template-editor/ModelExpression.gif"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		String res = super.getText(element);
		if (element instanceof IFile) {
			res = ((IFile)element).getName();
		} else if (element instanceof IProject) {
			res = ((IProject)element).getName();
		} else if (element instanceof IResource) {
			res = ((IResource)element).getProjectRelativePath().toString();
		} else if (element instanceof ReferenceEntry) {
			res = ((ReferenceEntry)element).getMessage();
		}
		return res;
	}
}
