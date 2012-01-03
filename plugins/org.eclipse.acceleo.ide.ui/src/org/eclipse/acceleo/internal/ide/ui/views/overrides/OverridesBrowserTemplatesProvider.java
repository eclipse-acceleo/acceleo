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
package org.eclipse.acceleo.internal.ide.ui.views.overrides;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.VisibilityKind;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;

/**
 * The 'OverridesBrowser' view is used to update the settings of the current 'overrides' completion proposal.
 * This content provider displays the Templates of the current Eclipse instance. This content provider wraps
 * an AdapterFactory and it delegates its JFace provider interfaces to corresponding adapter-implemented item
 * provider interfaces.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class OverridesBrowserTemplatesProvider extends AdapterFactoryContentProvider {

	/**
	 * Constructor.
	 * 
	 * @param adapterFactory
	 *            is the adapter factory
	 */
	public OverridesBrowserTemplatesProvider(AdapterFactory adapterFactory) {
		super(adapterFactory);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getElements(java.lang.Object)
	 */
	@Override
	public Object[] getElements(Object object) {
		Object[] result;
		if (object instanceof String) {
			result = new Object[] {(String)object };
		} else if (object instanceof List<?>) {
			result = ((List<?>)object).toArray();
		} else if (object instanceof Object[]) {
			result = (Object[])object;
		} else {
			result = super.getElements(object);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#hasChildren(java.lang.Object)
	 */
	@Override
	public boolean hasChildren(Object object) {
		boolean result;
		if (object instanceof ModuleProjectHandler) {
			result = true;
		} else if (object instanceof Module) {
			result = ((Module)object).getOwnedModuleElement().size() > 0;
		} else if (object instanceof ModuleElement) {
			result = false;
		} else {
			result = super.hasChildren(object);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public Object[] getChildren(Object object) {
		Object[] result;
		if (object instanceof ModuleProjectHandler) {
			result = ((ModuleProjectHandler)object).getModules();
		} else if (object instanceof Module) {
			List<ModuleElement> filteredElements = new ArrayList<ModuleElement>();
			for (ModuleElement moduleElement : ((Module)object).getOwnedModuleElement()) {
				if (moduleElement.getVisibility() != VisibilityKind.PRIVATE) {
					filteredElements.add(moduleElement);
				}
			}
			result = filteredElements.toArray();
		} else if (object instanceof ModuleElement) {
			result = new Object[] {};
		} else {
			result = super.getChildren(object);
		}
		return result;
	}

}
