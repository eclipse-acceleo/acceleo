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

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		int result = -1;
		try {
			String message = marker.getAttribute(IMarker.MESSAGE, ""); //$NON-NLS-1$
			int posBegin = marker.getAttribute(IMarker.CHAR_START, -1);
			int posEnd = marker.getAttribute(IMarker.CHAR_END, posBegin);
			String lineDelimiter = "\n"; //$NON-NLS-1$
			if (document.getNumberOfLines() > 0) {
				lineDelimiter = document.getLineDelimiter(0);
			}
			if (message != null && posBegin > -1 && posEnd > -1) {
				int newOffset = newOffset(document, content, posBegin);
				Pattern messagePattern = Pattern
						.compile("Cannot find operation \\(([^()]+)\\(([^()]+)\\)\\) for the type \\(([^()]+)\\)"); //$NON-NLS-1$
				Matcher matcher = messagePattern.matcher(message);
				String templateName;
				String[] paramTypes;
				String[] paramNames;
				if (matcher.find()) {
					templateName = matcher.group(1).trim();
					paramTypes = splitParamTypes(matcher.group(3) + ',' + matcher.group(2));
					paramNames = computeParamNames(paramTypes);
				} else {
					return -1;
				}
				if (paramTypes.length == 0) {
					CSTNode currentNode = content.getCSTNode(posBegin, posBegin);
					paramTypes = new String[] {getCurrentVariableTypeName(currentNode, "Type"), }; //$NON-NLS-1$
				}
				StringBuilder newText = new StringBuilder();
				if (newOffset > 0) {
					newText.append(lineDelimiter);
					if (!lineDelimiter.equals(document.get(newOffset - 1, 1))) {
						newText.append(lineDelimiter);
					}
				}
				int selectAndReveal = newOffset + newText.length();
				int newTextLength = newText.length();
				append(newText, lineDelimiter, templateName, paramTypes, paramNames);
				if (newText.length() > newTextLength) {
					document.replace(newOffset, 0, newText.toString());
					marker.delete();
					result = selectAndReveal;
				}
			}
		} catch (BadLocationException e) {
			AcceleoUIActivator.getDefault().getLog().log(
					new Status(IStatus.WARNING, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
		} catch (CoreException e) {
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
		return result;
	}

	/**
	 * Splits the given String (in the form "Type1, Type2, ..., Typen") in its individual types.
	 * 
	 * @param parameters
	 *            The String to split.
	 * @return The split types.
	 */
	private String[] splitParamTypes(String parameters) {
		String[] types = parameters.split(","); //$NON-NLS-1$
		for (int i = 0; i < types.length; i++) {
			types[i] = types[i].trim();
		}
		return types;
	}

	/**
	 * Computes names that can be used as parameter names for the given types.
	 * 
	 * @param paramTypes
	 *            The types for which we need names.
	 * @return The names for theses types.
	 */
	private String[] computeParamNames(String[] paramTypes) {
		Set<String> names = new LinkedHashSet<String>(paramTypes.length);
		for (String type : paramTypes) {
			String candidate = 'a' + type;
			if (names.contains(candidate)) {
				int count = 1;
				while (names.contains(candidate + count)) {
					count++;
				}
				candidate += count;
			}
			names.add(candidate);
		}
		return names.toArray(new String[names.size()]);
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
	 * @param lineDelimiter
	 *            the String to use as a line delimiter.
	 * @param name
	 *            is the name of the new module element
	 * @param paramTypes
	 *            Types of the parameters for this element
	 * @param paramNames
	 *            Names of the parameters for this element
	 */
	protected abstract void append(StringBuilder newText, String lineDelimiter, String name,
			String[] paramTypes, String[] paramNames);

}
