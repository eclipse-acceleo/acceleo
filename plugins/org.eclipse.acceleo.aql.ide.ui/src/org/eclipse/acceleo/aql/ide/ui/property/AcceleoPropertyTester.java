/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui.property;

import java.util.List;

import org.eclipse.acceleo.aql.ide.AcceleoPlugin;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.lsp4e.LSPEclipseUtils;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;

public class AcceleoPropertyTester extends org.eclipse.core.expressions.PropertyTester {

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		final boolean res;

		switch (property) {
			case "isMain":
				if (receiver instanceof IFile) {
					res = AcceleoPlugin.isAcceleoMain((IFile)receiver);
				} else {
					res = false;
				}
				break;

			case "isBlockSelection":
				if (receiver instanceof ITextSelection) {
					final ITextSelection selection = (ITextSelection)receiver;
					final IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow();
					if (activeWorkbenchWindow != null) {
						final IEditorPart activeEditor = activeWorkbenchWindow.getActivePage()
								.getActiveEditor();
						if (activeEditor instanceof TextEditor) {
							final IDocument document = ((TextEditor)activeEditor).getDocumentProvider()
									.getDocument(activeEditor.getEditorInput());
							final boolean startAtNewLine = selection.getOffset() == 0 || document.get()
									.charAt(selection.getOffset() - 1) == '\n';
							res = startAtNewLine && !selection.isEmpty() && selection.getText().endsWith(
									"\n");
						} else {
							res = false;
						}
					} else {
						res = false;
					}
				} else {
					res = false;
				}
				break;

			case "isAcceleoTextSelection":
				if (receiver instanceof ITextSelection) {
					final IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow();
					if (activeWorkbenchWindow != null) {
						final IEditorPart activeEditor = activeWorkbenchWindow.getActivePage()
								.getActiveEditor();
						if (activeEditor instanceof TextEditor) {
							final IDocument document = ((TextEditor)activeEditor).getDocumentProvider()
									.getDocument(activeEditor.getEditorInput());
							final List<IContentType> contentTypes = LSPEclipseUtils.getDocumentContentTypes(
									document);
							boolean foundAcceleoContentType = false;
							for (IContentType contentType : contentTypes) {
								if ("Acceleo".equals(contentType.getName())) {
									foundAcceleoContentType = true;
									break;
								}
							}
							res = foundAcceleoContentType;
						} else {
							res = false;
						}
					} else {
						res = false;
					}
				} else {
					res = false;
				}
				break;

			default:
				throw new IllegalStateException("Unkown property" + property);
		}

		return res;
	}

}
