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
package org.eclipse.acceleo.aql.ls.services.workspace.command;

import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;

/**
 * Parameter for selected range in a text document.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class DocumentRangeParams {

	/**
	 * The text document.
	 */
	@NonNull
	private final TextDocumentIdentifier textDocument;

	/**
	 * The selected {@link Range}.
	 */
	private final Range range;

	public DocumentRangeParams(@NonNull final TextDocumentIdentifier textDocument,
			@NonNull final Range range) {
		this.textDocument = textDocument;
		this.range = range;
	}

	/**
	 * The text document.
	 */
	@NonNull
	public TextDocumentIdentifier getTextDocument() {
		return this.textDocument;
	}

	/**
	 * The range.
	 */
	@NonNull
	public Range getRange() {
		return this.range;
	}

}
