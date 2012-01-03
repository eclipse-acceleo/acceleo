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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor;

import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoSourceContent;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.ModuleElement;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

/**
 * An action to extract the selected text as a new template.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class ExtractAsTemplateAction extends AbstractRefactoringWithVariableContextAction {

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
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.AbstractRefactoringWithVariableContextAction#modify(org.eclipse.jface.text.IDocument,
	 *      org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoSourceContent, int, int)
	 */
	@Override
	protected int modify(IDocument document, AcceleoSourceContent content, int offset, int length)
			throws BadLocationException {
		int newOffset = document.getLength();
		CSTNode currentNode = content.getCSTNode(offset, offset + length);
		String paramType = getCurrentVariableTypeName(currentNode, "Type"); //$NON-NLS-1$
		String paramName = getCurrentVariableName(currentNode, "arg"); //$NON-NLS-1$
		if (currentNode instanceof ModuleElement) {
			newOffset = ((ModuleElement)currentNode).getEndPosition();
		} else if (currentNode != null) {
			CSTNode parentNode = content.getCSTParent(currentNode, ModuleElement.class);
			if (parentNode instanceof ModuleElement) {
				newOffset = ((ModuleElement)parentNode).getEndPosition();
			}
		}
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

}
