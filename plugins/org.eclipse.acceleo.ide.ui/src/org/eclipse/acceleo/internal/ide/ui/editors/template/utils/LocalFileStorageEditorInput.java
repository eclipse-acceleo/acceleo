/*******************************************************************************
 * Copyright (c) 2009, 2011 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Obeo - Tweaking to cope with Acceleo
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template.utils;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.debug.core.sourcelookup.containers.LocalFileStorage;
import org.eclipse.jdt.ui.ISharedImages;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;

/**
 * The base of this class is entirely copy pasted from
 * org.eclipse.jdt.internal.debug.ui.LocalFileStorageEditorInput.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class LocalFileStorageEditorInput extends PlatformObject implements IStorageEditorInput {
	/**
	 * Storage associated with this editor input.
	 */
	private IStorage storage;

	/**
	 * Constructs an editor input on the given storage.
	 * 
	 * @param targetStorage
	 *            The storage associated with this editor input.
	 */
	public LocalFileStorageEditorInput(IStorage targetStorage) {
		storage = targetStorage;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IStorageEditorInput#getStorage()
	 */
	public IStorage getStorage() {
		return storage;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return JavaUI.getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_CUNIT);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IEditorInput#getName()
	 */
	public String getName() {
		return getStorage().getName();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IEditorInput#getPersistable()
	 */
	public IPersistableElement getPersistable() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 */
	public String getToolTipText() {
		return getStorage().getFullPath().toOSString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		return object instanceof LocalFileStorageEditorInput
				&& getStorage().equals(((LocalFileStorageEditorInput)object).getStorage());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getStorage().hashCode();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IEditorInput#exists()
	 */
	public boolean exists() {
		return ((LocalFileStorage)getStorage()).getFile().exists();
	}
}
