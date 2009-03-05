/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.references;

import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.OpenDeclarationAction;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

/**
 * This action trigger the references search.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class ReferencesSearchAction extends OpenDeclarationAction {

	/**
	 * The action ID.
	 */
	public static final String ACTION_ID = "org.eclipse.acceleo.ide.ui.editors.template.actions.search.references"; //$NON-NLS-1$

	/**
	 * The associated command ID.
	 */
	public static final String COMMAND_ID = "org.eclipse.acceleo.ide.ui.search.references"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		super.run();
		IEditorPart part;
		EObject declaration = null;
		if (PlatformUI.getWorkbench() != null && PlatformUI.getWorkbench().getActiveWorkbenchWindow() != null
				&& PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage() != null) {
			part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		} else {
			part = null;
		}
		if (part instanceof AcceleoEditor && ((AcceleoEditor)part).getContent() != null) {
			AcceleoEditor editor = (AcceleoEditor)part;
			declaration = findDeclaration(editor);
			if (declaration == null) {
				int offset;
				ISelection selection = editor.getSelectionProvider().getSelection();
				if (selection instanceof TextSelection) {
					offset = ((TextSelection)selection).getOffset();
					declaration = editor.getContent().getASTNode(offset, offset);
				}
			}
			if (declaration != null) {
				ISearchQuery query = new ReferencesSearchQuery(editor, declaration);
				NewSearchUI.runQueryInBackground(query);
			}
		}
	}
}
