/*******************************************************************************
 *  Copyright (c) 2018, 2020 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui.dialog;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Shell;

/**
 * File selection dialog.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class FileSelectionDialog extends AbstractResourceSelectionDialog {

	/**
	 * The file extension to filter.
	 */
	private String fileExtension;

	/**
	 * Constructor.
	 * 
	 * @param parentShell
	 *            the parent {@link Shell}
	 * @param title
	 *            the title
	 * @param defaultResourceName
	 *            the default resource name
	 * @param fileExtension
	 *            the file extension to filter
	 * @param onlyFileSelection
	 *            tells if only files can be selected
	 */
	public FileSelectionDialog(Shell parentShell, String title, String defaultResourceName,
			String fileExtension, boolean onlyFileSelection) {
		super(parentShell, title, "Select a file.", defaultResourceName, onlyFileSelection);
		this.fileExtension = fileExtension;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.ide.ui.dialog.AbstractResourceSelectionDialog#findResource(java.lang.String)
	 */
	@Override
	protected IResource findResource(String defaultResourceName) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(defaultResourceName));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.ide.ui.dialog.AbstractResourceSelectionDialog#isValid(org.eclipse.core.resources.IResource)
	 */
	@Override
	protected boolean isValid(IResource resource) {
		if (resource instanceof IFile) {
			return resource instanceof IFile && (fileExtension == null || fileExtension.equals(
					((IFile)resource).getFileExtension()));
		} else {
			return !onlyFileSelection;
		}
	}

}
