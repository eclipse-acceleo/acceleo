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
package org.eclipse.acceleo.internal.ide.ui.interpreter;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.ui.interpreter.internal.view.actions.LinkWithEditorContextAction;
import org.eclipse.acceleo.ui.interpreter.view.InterpreterView;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * The LoadModuleAction is used to use an Acceleo module in the current context instead of the content of a
 * linked Acceleo editor.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class LoadModuleAction extends Action {

	/**
	 * The Acceleo source viewer.
	 */
	private AcceleoSourceViewer acceleoSource;

	/**
	 * The interpreter view.
	 */
	private InterpreterView interpreterView;

	/**
	 * The toolbar manager.
	 */
	private IToolBarManager toolBarManager;

	/**
	 * The constructor.
	 * 
	 * @param source
	 *            The source viewer
	 * @param interpreterView
	 *            the interpreter view
	 * @param toolBarManager
	 *            the toolbar manager
	 */
	public LoadModuleAction(AcceleoSourceViewer source, InterpreterView interpreterView,
			IToolBarManager toolBarManager) {
		super(null, IAction.AS_CHECK_BOX);
		this.acceleoSource = source;
		this.interpreterView = interpreterView;
		this.toolBarManager = toolBarManager;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return AcceleoUIActivator.getImageDescriptor("icons/interpreter/load_module.gif"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#getToolTipText()
	 */
	@Override
	public String getToolTipText() {
		return AcceleoUIMessages.getString("acceleo.interpreter.load.module"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		if (this.isChecked()) {
			// First we uncheck the link with editor button.
			IContributionItem[] items = this.toolBarManager.getItems();
			if (items.length > 0) {
				IContributionItem contributionItem = items[0];
				if (contributionItem instanceof ActionContributionItem
						&& ((ActionContributionItem)contributionItem).getAction() instanceof LinkWithEditorContextAction) {
					(((ActionContributionItem)contributionItem).getAction()).setChecked(false);
				}
			}

			// Select the Acceleo module to import
			ViewerFilter viewerFilter = new ViewerFilter() {
				@Override
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					if (element instanceof IFile
							&& !IAcceleoConstants.MTL_FILE_EXTENSION.equals(((IFile)element)
									.getFileExtension())) {
						return false;
					}
					return true;
				}
			};
			List<ViewerFilter> viewerFilters = new ArrayList<ViewerFilter>();
			viewerFilters.add(viewerFilter);

			IFile[] files = WorkspaceResourceDialog.openFileSelection(this.acceleoSource.getControl()
					.getShell(), AcceleoUIMessages.getString("acceleo.interpreter.load.module.path.title"), //$NON-NLS-1$
					AcceleoUIMessages.getString("acceleo.interpreter.load.module.path"), false, null, //$NON-NLS-1$
					viewerFilters);
			if (files.length > 0) {
				acceleoSource.setModuleImport(files[0]);
				this.acceleoSource.updateCST(this.interpreterView.getInterpreterContext());
			}
		} else {
			acceleoSource.setModuleImport(null);
			this.acceleoSource.updateCST(this.interpreterView.getInterpreterContext());
		}
	}

	/**
	 * This will be called in order to clear all the references of this.
	 */
	public void dispose() {
		this.acceleoSource = null;
		this.interpreterView = null;
		this.toolBarManager = null;
	}
}
