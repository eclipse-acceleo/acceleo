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

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.AcceleoUIDocumentationUtils;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.parser.cst.VisibilityKind;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * The label provider of the table viewer of the first page of the wizard of the pull up refactoring.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoPullUpTableLabelProvider extends LabelProvider {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		Image result = null;
		if (element instanceof Template) {
			Template template = (Template)element;
			if (VisibilityKind.PUBLIC.getName().equals(template.getVisibility().getName())) {
				result = AcceleoUIActivator.getDefault().getImage("icons/template-editor/Template.gif"); //$NON-NLS-1$
			}
			if (VisibilityKind.PRIVATE.getName().equals(template.getVisibility().getName())) {
				result = AcceleoUIActivator.getDefault().getImage(
						"icons/template-editor/Template_private.gif"); //$NON-NLS-1$
			}
			if (VisibilityKind.PROTECTED.getName().equals(template.getVisibility().getName())) {
				result = AcceleoUIActivator.getDefault().getImage(
						"icons/template-editor/Template_protected.gif"); //$NON-NLS-1$
			}

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
		if (element instanceof Template) {
			return AcceleoUIDocumentationUtils.getSignatureFrom((Template)element);
		}
		return super.getText(element);
	}
}
