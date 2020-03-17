/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.jsonrpc.CompletableFutures;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;

public class AcceleoTextDocumentService implements TextDocumentService {

	@Override
	public void didOpen(DidOpenTextDocumentParams params) {
		// TODO Auto-generated method stub
		System.out.println(params);
	}

	@Override
	public void didChange(DidChangeTextDocumentParams params) {
		// TODO Auto-generated method stub
		System.out.println(params);
	}

	@Override
	public void didClose(DidCloseTextDocumentParams params) {
		// TODO Auto-generated method stub
		System.out.println(params);
	}

	@Override
	public void didSave(DidSaveTextDocumentParams params) {
		// TODO Auto-generated method stub
		System.out.println(params);
	}

	@Override
	public CompletableFuture<Either<List<CompletionItem>, CompletionList>> completion(
			CompletionParams params) {
		System.out.println(params);
		return CompletableFutures.computeAsync(cc -> {
			List<CompletionItem> items = new ArrayList<CompletionItem>();

			if (!cc.isCanceled()) {
				CompletionItem item = new CompletionItem("toto");
				items.add(item);
			}

			return Either.forRight(new CompletionList(items));
		});
	}

}
