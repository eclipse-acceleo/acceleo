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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.rename;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * Content provider of the combo box in the wizard.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoPositionedTemplateLabelProvider extends LabelProvider {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		return AcceleoUIActivator.getDefault().getImage("icons/template-editor/Template.gif"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		String str = "Unknow object"; //$NON-NLS-1$

		if (element instanceof AcceleoPositionedTemplate) {
			final AcceleoPositionedTemplate apt = (AcceleoPositionedTemplate)element;
			str = apt.getTemplateName() + apt.getTemplate().getParameter();
		}

		return str;
	}

}
