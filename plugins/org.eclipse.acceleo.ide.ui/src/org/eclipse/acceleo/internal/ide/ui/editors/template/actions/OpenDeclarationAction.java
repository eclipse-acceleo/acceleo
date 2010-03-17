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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions;

import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.OpenDeclarationUtils;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

/**
 * An action to open the declaration of the selected element.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class OpenDeclarationAction extends Action implements IWorkbenchWindowActionDelegate, IObjectActionDelegate {

	/**
	 * The action ID.
	 */
	public static final String ACTION_ID = "org.eclipse.acceleo.ide.ui.editors.template.actions.open.declaration"; //$NON-NLS-1$

	/**
	 * The associated command ID.
	 */
	public static final String COMMAND_ID = "org.eclipse.acceleo.ide.ui.open.declaration"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
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

			OpenDeclarationUtils.showEObject(editor.getSite().getPage(), getFileURI(declaration),
					OpenDeclarationUtils.createRegion(declaration), declaration);
		}
	}

	/**
	 * Find the declaration of the text selected in the given editor.
	 * 
	 * @param editor
	 *            the current opened editor
	 * @return the EObject corresponding to the declaration of the selected element
	 */
	protected EObject findDeclaration(AcceleoEditor editor) {
		EObject res = null;
		int offset;
		ISelection selection = editor.getSelectionProvider().getSelection();
		if (selection instanceof TextSelection) {
			offset = ((TextSelection)selection).getOffset();
		} else {
			offset = -1;
		}
		ASTNode astNode = editor.getContent().getASTNode(offset, offset);
		if (astNode != null) {
			res = OpenDeclarationUtils.findDeclarationFromAST(astNode);
		}
		if (res == null) {
			CSTNode cstNode = editor.getContent().getCSTNode(offset, offset);
			if (cstNode != null) {
				res = OpenDeclarationUtils.findDeclarationFromCST(editor, astNode, cstNode);
			}
		}
		return res;
	}

	/**
	 * Get the file URI for the given EObject.
	 * 
	 * @param eObj
	 *            the EObject
	 * @return the file URI if any of null
	 */
	public URI getFileURI(EObject eObj) {
		URI res = null;
		if (eObj != null && eObj.eResource() != null) {
			res = eObj.eResource().getURI();
		}
		return res;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection aSelection) {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart aPart) {
	}

}
