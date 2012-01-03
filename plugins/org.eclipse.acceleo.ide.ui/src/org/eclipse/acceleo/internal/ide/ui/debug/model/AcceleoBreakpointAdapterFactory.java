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
package org.eclipse.acceleo.internal.ide.ui.debug.model;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.ui.actions.IRunToLineTarget;
import org.eclipse.debug.ui.actions.IToggleBreakpointsTarget;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Creates a toggle breakpoint adapter factory. This factory is used to create a new Acceleo breakpoint.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoBreakpointAdapterFactory implements IAdapterFactory {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		Object adapter = null;
		if (adaptableObject instanceof ITextEditor) {
			ITextEditor editorPart = (ITextEditor)adaptableObject;
			IResource resource = (IResource)editorPart.getEditorInput().getAdapter(IResource.class);
			if (resource != null && IAcceleoConstants.MTL_FILE_EXTENSION.equals(resource.getFileExtension())) {
				if (adapterType.equals(IToggleBreakpointsTarget.class)) {
					adapter = new AcceleoLineBreakpointAdapter();
				} else if (adapterType.equals(IRunToLineTarget.class)) {
					adapter = new AcceleoRunToLineAdapter();
				}
			}
		}
		return adapter;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 */
	public Class<?>[] getAdapterList() {
		return new Class[] {IToggleBreakpointsTarget.class };
	}

}
