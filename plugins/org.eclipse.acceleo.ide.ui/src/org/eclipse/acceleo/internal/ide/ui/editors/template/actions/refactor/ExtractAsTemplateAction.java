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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoSourceContent;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.ModuleElement;
import org.eclipse.acceleo.parser.cst.Variable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
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
 * An action to extract the selected text as a new template.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class ExtractAsTemplateAction extends Action implements IWorkbenchWindowActionDelegate, IObjectActionDelegate {

	/**
	 * The action ID.
	 */
	public static final String ACTION_ID = "org.eclipse.acceleo.ide.ui.editors.template.actions.refactor.extractAsTemplate"; //$NON-NLS-1$

	/**
	 * The associated command ID.
	 */
	public static final String COMMAND_ID = "org.eclipse.acceleo.ide.ui.extractAsTemplate"; //$NON-NLS-1$

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
			if (editor.getContent() != null && document != null && selection != null
					&& selection.getLength() > 0) {
				try {
					int newOffset = modify(document, editor.getContent(), selection.getOffset(), selection
							.getLength());
					if (newOffset > -1) {
						editor.selectAndReveal(newOffset, 0);
					}
				} catch (BadLocationException ex) {
					AcceleoUIActivator.getDefault().getLog().log(
							new Status(IStatus.WARNING, AcceleoUIActivator.PLUGIN_ID, ex.getMessage(), ex));
				}
			}
		}
	}

	/**
	 * Modify the content of the editor.
	 * 
	 * @param document
	 *            is the document to modify
	 * @param content
	 *            is the content of the editor
	 * @param offset
	 *            is the offset of the text to extract
	 * @param length
	 *            is the length of the text to extract
	 * @return the new offset to reveal in the editor
	 * @throws BadLocationException
	 *             when an index issue occurs
	 */
	private int modify(IDocument document, AcceleoSourceContent content, int offset, int length)
			throws BadLocationException {
		int newOffset = document.getLength();
		String paramType = "E"; //$NON-NLS-1$
		CSTNode currentNode = content.getCSTNode(offset, offset + length);
		if (currentNode instanceof ModuleElement) {
			newOffset = ((ModuleElement)currentNode).getEndPosition();
			paramType = getCurrentTypeName(currentNode, paramType);
		} else if (currentNode != null) {
			currentNode = content.getCSTParent(currentNode, ModuleElement.class);
			if (currentNode instanceof ModuleElement) {
				newOffset = ((ModuleElement)currentNode).getEndPosition();
				paramType = getCurrentTypeName(currentNode, paramType);
			}
		}
		String paramName = Character.toLowerCase(paramType.charAt(0)) + paramType.substring(1);
		String templateName = "new" + paramType + "Template"; //$NON-NLS-1$ //$NON-NLS-2$
		StringBuilder newText = new StringBuilder();
		if (newOffset > 0) {
			newText.append('\n');
			if (!"\n".equals(document.get(newOffset - 1, 1))) { //$NON-NLS-1$
				newText.append("\n"); //$NON-NLS-1$
			}
		}
		int selectAndReveal = newOffset + newText.length();
		String templateContent = document.get(offset, length);
		newText.append(getNewTemplateText(templateName, paramType, paramName, templateContent));
		document.replace(newOffset, 0, newText.toString());
		String templateCall = "[new" + paramType + "Template()/]"; //$NON-NLS-1$ //$NON-NLS-2$
		document.replace(offset, length, templateCall);
		selectAndReveal = selectAndReveal + (templateCall.length() - length);
		return selectAndReveal;
	}

	/**
	 * Gets the name of the current context type.
	 * 
	 * @param currentNode
	 *            is the current CST node, it means the node at the current position
	 * @param defaultType
	 *            the default value if there isn't any context type at the given position
	 * @return the type name, or the given default value
	 */
	private String getCurrentTypeName(CSTNode currentNode, String defaultType) {
		Variable eContext = null;
		if (currentNode instanceof org.eclipse.acceleo.parser.cst.Template) {
			org.eclipse.acceleo.parser.cst.Template iTemplate = (org.eclipse.acceleo.parser.cst.Template)currentNode;
			if (iTemplate.getParameter().size() > 0) {
				eContext = iTemplate.getParameter().get(0);
			}
		} else if (currentNode instanceof org.eclipse.acceleo.parser.cst.Query) {
			org.eclipse.acceleo.parser.cst.Query iQuery = (org.eclipse.acceleo.parser.cst.Query)currentNode;
			if (iQuery.getParameter().size() > 0) {
				eContext = iQuery.getParameter().get(0);
			}
		} else if (currentNode instanceof org.eclipse.acceleo.parser.cst.Macro) {
			org.eclipse.acceleo.parser.cst.Macro iMacro = (org.eclipse.acceleo.parser.cst.Macro)currentNode;
			if (iMacro.getParameter().size() > 0) {
				eContext = iMacro.getParameter().get(0);
			}
		} else if (currentNode instanceof org.eclipse.acceleo.parser.cst.ForBlock) {
			eContext = ((org.eclipse.acceleo.parser.cst.ForBlock)currentNode).getLoopVariable();
		} else if (currentNode instanceof org.eclipse.acceleo.parser.cst.LetBlock) {
			eContext = ((org.eclipse.acceleo.parser.cst.LetBlock)currentNode).getLetVariable();
		}
		String res;
		if (eContext != null && eContext.getType() != null) {
			res = eContext.getType();
		} else if (eContext != null && eContext.eContainer() instanceof CSTNode) {
			res = getCurrentTypeName((CSTNode)currentNode.eContainer(), defaultType);
		} else {
			res = defaultType;
		}
		return res;
	}

	/**
	 * Creates the new template text.
	 * 
	 * @param templateName
	 *            is the new template name
	 * @param paramType
	 *            is the parameter type of the new template
	 * @param paramName
	 *            is the parameter name of the new template
	 * @param templateContent
	 *            is the new template content
	 * @return the new template text
	 */
	private String getNewTemplateText(String templateName, String paramType, String paramName,
			String templateContent) {
		StringBuilder newText = new StringBuilder();
		newText.append("[template public " + templateName + " ("); //$NON-NLS-1$ //$NON-NLS-2$
		newText.append(paramName);
		newText.append(" : "); //$NON-NLS-1$
		newText.append(paramType);
		newText.append(") ]\n"); //$NON-NLS-1$
		newText.append(templateContent);
		newText.append('\n');
		newText.append("[/template]"); //$NON-NLS-1$
		return newText.toString();
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
