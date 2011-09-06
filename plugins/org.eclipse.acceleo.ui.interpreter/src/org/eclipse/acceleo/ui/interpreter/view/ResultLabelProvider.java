/*******************************************************************************
 * Copyright (c) 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.view;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

/**
 * This will act as the label provider for the "variables" Tree Viewer.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class ResultLabelProvider extends CellLabelProvider {
	/** The delegate label provider. */
	private final AdapterFactoryLabelProvider delegate;

	/**
	 * Instantiates this label provider given its adapter factory.
	 * 
	 * @param adapterFactory
	 *            The adapter factory for this label provider.
	 */
	public ResultLabelProvider(AdapterFactory adapterFactory) {
		super();
		delegate = new AdapterFactoryLabelProvider(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.CellLabelProvider#getToolTipText(java.lang.Object)
	 */
	@Override
	public String getToolTipText(Object element) {
		final String text = delegate.getText(element);
		if (text.indexOf('\n') != -1 || text.indexOf('\r') != -1) {
			return text;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.CellLabelProvider#update(org.eclipse.jface.viewers.ViewerCell)
	 */
	@Override
	public void update(ViewerCell cell) {
		final Object element = cell.getElement();
		cell.setText(delegate.getText(element));
		cell.setImage(delegate.getImage(element));
	}
}
