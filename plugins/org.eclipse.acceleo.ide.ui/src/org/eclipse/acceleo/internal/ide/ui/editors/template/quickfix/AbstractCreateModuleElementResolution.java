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
package org.eclipse.acceleo.internal.ide.ui.editors.template.quickfix;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoSourceContent;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.Variable;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * Quick fix resolution on the Acceleo problem marker. Abstract implementation to create a new module element
 * with the marker information. This class needs to be sub classed to create templates, queries and macros.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public abstract class AbstractCreateModuleElementResolution implements IMarkerResolution2 {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IMarkerResolution#run(org.eclipse.core.resources.IMarker)
	 */
	public void run(IMarker marker) {
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null && window.getActivePage() != null
				&& window.getActivePage().getActiveEditor() instanceof AcceleoEditor) {
			AcceleoEditor editor = (AcceleoEditor)window.getActivePage().getActiveEditor();
			IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
			if (document != null && editor.getContent() != null) {
				int newOffset = createModuleElement(document, editor.getContent(), marker);
				if (newOffset > -1) {
					editor.selectAndReveal(newOffset, 0);
				}
			}
		}
	}

	/**
	 * Creates a new module element with the marker information. The text editor cursor will move to the
	 * returned offset. -1 means "don't move!"
	 * 
	 * @param document
	 *            is the document
	 * @param content
	 *            is the Acceleo editor content
	 * @param marker
	 *            is the marker
	 * @return the offset to reveal
	 */
	protected int createModuleElement(IDocument document, AcceleoSourceContent content, IMarker marker) {
		try {
			String message = marker.getAttribute(IMarker.MESSAGE, ""); //$NON-NLS-1$
			int posBegin = marker.getAttribute(IMarker.CHAR_START, -1);
			int posEnd = marker.getAttribute(IMarker.CHAR_END, posBegin);
			if (message != null && posBegin > -1 && posEnd > -1) {
				int newOffset = newOffset(document, content, posBegin);
				String paramType = ""; //$NON-NLS-1$
				if (message.contains("for the type") && message.endsWith(")") //$NON-NLS-1$ //$NON-NLS-2$
						&& message.lastIndexOf('(') > -1) {
					paramType = message.substring(message.lastIndexOf('(') + 1, message.length() - 1).trim();
				}
				if (paramType.length() == 0) {
					CSTNode currentNode = content.getCSTNode(posBegin, posBegin);
					paramType = getCurrentVariableTypeName(currentNode, "Type"); //$NON-NLS-1$
				}
				String paramName = 'a' + paramType;
				StringBuilder newText = new StringBuilder();
				if (newOffset > 0) {
					newText.append('\n');
					if (!"\n".equals(document.get(newOffset - 1, 1))) { //$NON-NLS-1$
						newText.append("\n"); //$NON-NLS-1$
					}
				}
				int selectAndReveal = newOffset + newText.length();
				String templateName = document.get(posBegin, posEnd - posBegin);
				int newTextLength = newText.length();
				append(newText, templateName, paramType, paramName);
				if (newText.length() > newTextLength) {
					document.replace(newOffset, 0, newText.toString());
					marker.delete();
					return selectAndReveal;
				}
			}
		} catch (BadLocationException e) {
			AcceleoUIActivator.getDefault().getLog().log(
					new Status(IStatus.WARNING, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
		} catch (CoreException e) {
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
		return -1;
	}

	/**
	 * Gets the name of the current variable type.
	 * 
	 * @param currentNode
	 *            is the current CST node, it means the node at the current position
	 * @param defaultType
	 *            the default value if there isn't any variable at the given position
	 * @return the type name, or the given default value
	 */
	private String getCurrentVariableTypeName(CSTNode currentNode, String defaultType) {
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
		} else if (currentNode != null && currentNode.eContainer() instanceof CSTNode) {
			res = getCurrentVariableTypeName((CSTNode)currentNode.eContainer(), defaultType);
		} else {
			res = defaultType;
		}
		return res;
	}

	/**
	 * Go to the offset where to put the new module element.
	 * 
	 * @param document
	 *            is the document
	 * @param content
	 *            is the content
	 * @param offset
	 *            is the offset
	 * @return the offset where to put the new module element
	 */
	protected int newOffset(IDocument document, AcceleoSourceContent content, int offset) {
		return document.getLength();
	}

	/**
	 * Computes the text of the module element to create.
	 * 
	 * @param newText
	 *            the buffer that will contain the new content, it's an input/output parameter
	 * @param name
	 *            is the name of the new module element
	 * @param paramType
	 *            is the type of the first parameter
	 * @param paramName
	 *            is the name of the first parameter
	 */
	protected abstract void append(StringBuilder newText, String name, String paramType, String paramName);

}
