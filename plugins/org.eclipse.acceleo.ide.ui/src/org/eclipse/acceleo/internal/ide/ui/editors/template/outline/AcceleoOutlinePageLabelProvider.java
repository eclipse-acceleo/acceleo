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
package org.eclipse.acceleo.internal.ide.ui.editors.template.outline;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * The label provider for the objects shown in the outline view.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoOutlinePageLabelProvider extends AdapterFactoryLabelProvider {

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory
	 *            is the adapter factory
	 */
	public AcceleoOutlinePageLabelProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object object) {
		if (object instanceof AcceleoOutlineImportContainer) {
			return AcceleoUIActivator.getDefault().getImage("icons/template-editor/Import_declaration.gif"); //$NON-NLS-1$
		}
		return super.getImage(object);
	}
}
