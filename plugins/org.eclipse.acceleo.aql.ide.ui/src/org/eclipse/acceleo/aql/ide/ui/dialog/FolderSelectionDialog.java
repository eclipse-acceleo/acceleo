/*******************************************************************************
 *  Copyright (c) 2018, 2026 Obeo. 
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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Shell;

/**
 * Folder selection dialog.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class FolderSelectionDialog extends AbstractResourceSelectionDialog {

	/**
	 * Constructor.
	 * 
	 * @param parentShell
	 *            the prent {@link Shell}
	 * @param title
	 *            the title
	 * @param defaultResourceName
	 *            tells if only files can be selected.
	 */
	public FolderSelectionDialog(Shell parentShell, String title, String defaultResourceName) {
		super(parentShell, title, "Select a folder.", defaultResourceName, false);
	}

	@Override
	protected String getURILabel() {
		return "Folder: ";
	}

	@Override
	protected IResource findResource(String defaultResourceName) {
		return ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(defaultResourceName));
	}

	@Override
	protected boolean isValid(IResource resource) {
		return resource instanceof IContainer;
	}
}
