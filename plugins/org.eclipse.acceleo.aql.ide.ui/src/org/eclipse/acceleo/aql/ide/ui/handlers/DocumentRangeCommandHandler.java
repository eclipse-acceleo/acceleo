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
package org.eclipse.acceleo.aql.ide.ui.handlers;

import java.util.Collections;

import org.eclipse.acceleo.aql.ide.ui.AcceleoUIPlugin;
import org.eclipse.acceleo.aql.ls.AcceleoLanguageServer;
import org.eclipse.acceleo.aql.ls.services.workspace.command.DocumentRangeParams;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.lsp4e.LSPEclipseUtils;
import org.eclipse.lsp4e.LanguageServers;
import org.eclipse.lsp4e.LanguageServers.LanguageServerDocumentExecutor;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Command handler for {@link DocumentRangeParams} send to the {@link AcceleoLanguageServer}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class DocumentRangeCommandHandler extends AbstractHandler {

	/**
	 * Executes the command for the given {@link ExecutionEvent} and LSP command.
	 * 
	 * @param event
	 *            the {@link ExecutionEvent}
	 * @param lsCommand
	 *            the LSP command
	 * @return the command for the given {@link ExecutionEvent} and LSP command
	 * @throws ExecutionException
	 */
	protected Object execute(ExecutionEvent event, String lsCommand) throws ExecutionException {
		final ITextSelection selection = (ITextSelection)HandlerUtil.getCurrentSelection(event);
		final ITextEditor editorPart = (ITextEditor)HandlerUtil.getActiveEditor(event);

		try {
			IDocument document = LSPEclipseUtils.getDocument(editorPart);
			final TextDocumentIdentifier textIdentifier = LSPEclipseUtils.toTextDocumentIdentifier(document);
			Position startPosition = LSPEclipseUtils.toPosition(selection.getOffset(), document);
			Position endPosition = LSPEclipseUtils.toPosition(selection.getOffset() + selection.getLength(),
					document);
			final Range range = new Range(startPosition, endPosition);
			final DocumentRangeParams documentRangeParams = new DocumentRangeParams(textIdentifier, range);

			final LanguageServerDocumentExecutor executor = LanguageServers.forDocument(document);
			final ExecuteCommandParams executionCommandParams = new ExecuteCommandParams(lsCommand,
					Collections.singletonList(documentRangeParams));
			executor.computeFirst(ls -> ls.getWorkspaceService().executeCommand(executionCommandParams));
		} catch (BadLocationException e) {
			AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID,
					"unable to run LS command " + lsCommand, e));
		}
		return null;
	}

}
