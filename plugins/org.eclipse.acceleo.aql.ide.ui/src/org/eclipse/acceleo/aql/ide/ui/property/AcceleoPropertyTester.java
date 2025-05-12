/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import org.eclipse.acceleo.aql.ide.ui.AcceleoUIPlugin;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.lsp4e.LSPEclipseUtils;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;

public class AcceleoPropertyTester extends org.eclipse.core.expressions.PropertyTester {

	/**
	 * The "isMain" property.
	 */
	public final static String IS_MAIN = "isMain";

	/**
	 * The "isBlockSelection" property.
	 */
	public final static String IS_BLOCK_SELECTION = "isBlockSelection";

	/**
	 * The "isAcceleoTextSelection" property.
	 */
	public final static String IS_ACCELEO_TEXT_SELECTION = "isAcceleoTextSelection";

	/**
	 * The "isInJavaProject" property.
	 */
	public final static String IS_IN_JAVA_PROJECT = "isInJavaProject";

	/**
	 * The "isInPluginProject" property.
	 */
	public final static String IS_IN_PLUGIN_PROJECT = "isInPluginProject";

	/**
	 * The "isInMavenProject" property.
	 */
	public final static String IS_IN_MAVEN_PROJECT = "isInMavenProject";

	/**
	 * PDE plug-in nature.
	 */
	private static final String PLUGIN_NATURE = "org.eclipse.pde.PluginNature";

	/**
	 * M2E Maven nature.
	 */
	private static final String MAVEN_NATURE = "org.eclipse.m2e.core.maven2Nature";

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		final boolean res;

		switch (property) {
			case IS_MAIN:
				if (receiver instanceof IFile) {
					res = AcceleoPlugin.isAcceleoMain((IFile)receiver);
				} else {
					res = false;
				}
				break;

			case IS_BLOCK_SELECTION:
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
							if (selection.getOffset() - 1 < document.getLength()) {
								final boolean startAtNewLine = selection.getOffset() == 0 || document.get()
										.charAt(selection.getOffset() - 1) == '\n' || document.get().charAt(
												selection.getOffset() - 1) == '\r';
								res = startAtNewLine && !(selection.isEmpty() && selection.getText().endsWith(
										"\n") || selection.getText().endsWith("\r"));
							} else {
								res = false;
							}
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

			case IS_ACCELEO_TEXT_SELECTION:
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

			case IS_IN_JAVA_PROJECT:
				res = hasNature(receiver, JavaCore.NATURE_ID);
				break;

			case IS_IN_PLUGIN_PROJECT:
				res = hasNature(receiver, PLUGIN_NATURE);
				break;

			case IS_IN_MAVEN_PROJECT:
				res = hasNature(receiver, MAVEN_NATURE);
				break;

			default:
				throw new IllegalStateException("Unkown property" + property);
		}

		return res;
	}

	/**
	 * Tells if the given receiver {@link Object} is in an {@link IProject} with the given nature.
	 * 
	 * @param receiver
	 *            the receiver of the property test
	 * @param nature
	 *            the nature
	 * @return <code>true</code> if the given receiver {@link Object} is in an {@link IProject} with the given
	 *         nature, <code>false</code> otherwise
	 */
	protected boolean hasNature(Object receiver, String nature) {
		boolean res;

		if (receiver instanceof IResource) {
			final IProject project = ((IResource)receiver).getProject();
			if (project != null) {
				try {
					res = project.hasNature(nature);
				} catch (CoreException e) {
					AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, getClass(),
							"can't get nature for project " + project.getName(), e));
					res = false;
				}
			} else {
				res = false;
			}
		} else {
			res = false;
		}

		return res;
	}

}
