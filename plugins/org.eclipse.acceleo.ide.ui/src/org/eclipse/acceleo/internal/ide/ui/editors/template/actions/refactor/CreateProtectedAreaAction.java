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

import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoSourceContent;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.ModuleElement;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

/**
 * An action to put the selected text as a protected user code area.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class CreateProtectedAreaAction extends AbstractRefactoringWithVariableContextAction {

	/**
	 * The action ID.
	 */
	public static final String ACTION_ID = "org.eclipse.acceleo.ide.ui.editors.template.actions.refactor.createProtectedArea"; //$NON-NLS-1$

	/**
	 * The associated command ID.
	 */
	public static final String COMMAND_ID = "org.eclipse.acceleo.ide.ui.createProtectedArea"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.AbstractRefactoringWithVariableContextAction#modify(org.eclipse.jface.text.IDocument,
	 *      org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoSourceContent, int, int)
	 */
	@Override
	protected int modify(IDocument document, AcceleoSourceContent content, int offset, int length)
			throws BadLocationException {
		String text = document.get();
		int b = offset;
		int e = offset + length;
		while (b < e && Character.isWhitespace(text.charAt(b))) {
			b++;
		}
		while (e > b && Character.isWhitespace(text.charAt(e - 1))) {
			e--;
		}
		String paramName = "e"; //$NON-NLS-1$
		CSTNode currentNode = content.getCSTNode(b, e);
		if (currentNode instanceof ModuleElement) {
			paramName = getCurrentVariableName(currentNode, paramName);
		} else if (currentNode != null) {
			currentNode = content.getCSTParent(currentNode, ModuleElement.class);
			if (currentNode instanceof ModuleElement) {
				paramName = getCurrentVariableName(currentNode, paramName);
			}
		}
		if (b == e) {
			while (b > 0 && text.charAt(b - 1) != '\n') {
				b--;
			}
			while (e < text.length() && text.charAt(e) != '\r' && text.charAt(e) != '\n') {
				e++;
			}
		}
		try {
			String prefix = "[protected]\n"; //$NON-NLS-1$
			String suffix = "\n[/protected]\n"; //$NON-NLS-1$
			document.replace(e, 0, suffix);
			document.replace(b, 0, prefix);
			return b + prefix.length();
		} catch (BadLocationException ex) {
			return offset;
		}
	}

}
