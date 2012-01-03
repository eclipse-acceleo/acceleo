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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.Comment;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

/**
 * An action to comment a line.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class CommentAction extends Action implements IWorkbenchWindowActionDelegate, IObjectActionDelegate {

	/**
	 * The action ID.
	 */
	public static final String ACTION_ID = "org.eclipse.acceleo.ide.ui.editors.template.actions.comment"; //$NON-NLS-1$

	/**
	 * The associated command ID.
	 */
	public static final String COMMAND_ID = "org.eclipse.acceleo.ide.ui.comment"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		super.run();
		AcceleoEditor editor = null;
		IDocument document = null;
		ITextSelection selection = null;
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null && window.getActivePage() != null
				&& window.getActivePage().getActiveEditor() instanceof AcceleoEditor) {
			editor = (AcceleoEditor)window.getActivePage().getActiveEditor();
			document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
			if (editor.getSelectionProvider() != null
					&& editor.getSelectionProvider().getSelection() instanceof ITextSelection) {
				selection = (ITextSelection)editor.getSelectionProvider().getSelection();
			}
		}
		if (editor != null && document != null && selection != null) {
			int b = selection.getOffset();
			int e = selection.getOffset() + selection.getLength();
			String text = document.get();
			while (b < e && Character.isWhitespace(text.charAt(b))) {
				b++;
			}
			while (e > b && Character.isWhitespace(text.charAt(e - 1))) {
				e--;
			}
			CSTNode cstNode = editor.getContent().getCSTNode(b, e);
			String newText;
			if (cstNode instanceof Comment) {
				b = cstNode.getStartPosition();
				e = cstNode.getEndPosition();
				newText = ((Comment)cstNode).getBody();
			} else {
				if (b == e) {
					while (b > 0 && text.charAt(b - 1) != '\n') {
						b--;
					}
					while (e < text.length() && text.charAt(e) != '\r' && text.charAt(e) != '\n') {
						e++;
					}
				}
				// FIXME comment sections are in IAcceleoConstants ... isn't the following error-prone?
				newText = "[comment]" + text.substring(b, e) + "[/comment]"; //$NON-NLS-1$ //$NON-NLS-2$
			}
			try {
				document.replace(b, e - b, newText);
			} catch (BadLocationException ex) {
				AcceleoUIActivator.log(ex, true);
			}
		}
	}

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
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
	 *      org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
	 *      org.eclipse.ui.IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

}
