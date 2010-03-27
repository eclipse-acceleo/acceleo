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
package org.eclipse.acceleo.internal.ide.ui.editors.template;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;

/**
 * The content provider used to show the CST objects in the outline view.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoOutlinePageContentProvider extends AdapterFactoryContentProvider {

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory
	 *            is the adapter factory
	 */
	public AcceleoOutlinePageContentProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object object) {
		if (object instanceof List<?>) {
			final Comparator<CSTNode> cstNodeComparator = new Comparator<CSTNode>() {
				public int compare(CSTNode n0, CSTNode n1) {
					if (n0.getStartPosition() < n1.getStartPosition()) {
						return -1;
					} else {
						return 1;
					}
				}
			};
			List<CSTNode> orderedCollection = new ArrayList<CSTNode>(((List<?>)object).size());
			for (Object element : (List<?>)object) {
				if (element instanceof CSTNode) {
					orderedCollection.add((CSTNode)element);
				}
			}
			Collections.sort(orderedCollection, cstNodeComparator);
			return orderedCollection.toArray();
		} else {
			return super.getElements(object);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	public void notifyChanged(Notification notification) {

	}

}
