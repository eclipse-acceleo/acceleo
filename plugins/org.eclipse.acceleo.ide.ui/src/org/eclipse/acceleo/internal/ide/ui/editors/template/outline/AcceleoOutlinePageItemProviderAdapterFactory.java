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

import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITableItemLabelProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;

/**
 * Specific item provider adapter factory for the outline of the template editor.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoOutlinePageItemProviderAdapterFactory extends ReflectiveItemProviderAdapterFactory {

	/**
	 * Constructor.
	 */
	public AcceleoOutlinePageItemProviderAdapterFactory() {
		reflectiveItemProviderAdapter = createReflectiveItemProvider();
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemPropertySource.class);
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(ITableItemLabelProvider.class);
	}

	/**
	 * Creates the specific item provider for the template editor.
	 * 
	 * @return the item provider
	 */
	protected AcceleoOutlinePageItemProvider createReflectiveItemProvider() {
		return new AcceleoOutlinePageItemProvider(this);
	}

}
