/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.parser.quickfixes;

import java.net.URI;

/**
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AstTextReplacement implements IAstTextReplacement {

	/**
	 * The {@link URI} of the resource to change.
	 */
	private final URI uri;

	/**
	 * the replacement {@link String}.
	 */
	private final String replacement;

	/**
	 * The start offset.
	 */
	private final int startOffest;

	/**
	 * The start line.
	 */
	private final int startLine;

	/**
	 * the start column.
	 */
	private final int startColumn;

	/**
	 * the end offset.
	 */
	private final int endOffest;

	/**
	 * The end line.
	 */
	private final int endLine;

	/**
	 * The end column.
	 */
	private final int endColumn;

	public AstTextReplacement(URI uri, String replacement, int startOffset, int startLine, int startColumn,
			int endOffset, int endLine, int endColumn) {
		this.uri = uri;
		this.replacement = replacement;
		this.startOffest = startOffset;
		this.startLine = startLine;
		this.startColumn = startColumn;
		this.endOffest = endOffset;
		this.endLine = endLine;
		this.endColumn = endColumn;

	}

	@Override
	public URI getURI() {
		return uri;
	}

	@Override
	public String getReplacement() {
		return replacement;
	}

	@Override
	public int getStartOffset() {
		return startOffest;
	}

	@Override
	public int getStartLine() {
		return startLine;
	}

	@Override
	public int getStartColumn() {
		return startColumn;
	}

	@Override
	public int getEndOffset() {
		return endOffest;
	}

	@Override
	public int getEndLine() {
		return endLine;
	}

	@Override
	public int getEndColumn() {
		return endColumn;
	}

	@Override
	public String toString() {
		return "Replace " + getURI() + " [" + getStartOffset() + ", " + getStartLine() + ", "
				+ getStartColumn() + ", " + getEndOffset() + ", " + getEndLine() + ", " + getEndColumn()
				+ "] " + getReplacement();
	}

}
