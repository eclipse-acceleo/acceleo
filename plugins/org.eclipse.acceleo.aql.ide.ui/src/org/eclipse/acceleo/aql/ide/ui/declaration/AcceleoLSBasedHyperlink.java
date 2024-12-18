/*******************************************************************************
 * Copyright (c) 2016, 2023 Red Hat Inc. and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael Istria (Red Hat Inc.) - initial implementation
 *  Michał Niewrzał (Rogue Wave Software Inc.) - hyperlink range detection
 *  Lucas Bullen (Red Hat Inc.) - [Bug 517428] Requests sent before initialization
 *  Martin Lippert (Pivotal Inc.) - [Bug 561270] labels include more details now
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui.declaration;

import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.lsp4e.LSPEclipseUtils;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.validation.NonNull;

// copied from org.eclipse.lsp4e.operations.declaration.LSBasedHyperlink
public class AcceleoLSBasedHyperlink implements IHyperlink {

	private static final String DASH_SEPARATOR = " - "; //$NON-NLS-1$

	private static final String FILE = "file"; //$NON-NLS-1$

	private final Either<Location, LocationLink> location;

	private final IRegion highlightRegion;

	public AcceleoLSBasedHyperlink(Either<Location, LocationLink> location, IRegion highlightRegion) {
		this.location = location;
		this.highlightRegion = highlightRegion;
	}

	public AcceleoLSBasedHyperlink(Location location, IRegion linkRegion) {
		this(Either.forLeft(location), linkRegion);
	}

	public AcceleoLSBasedHyperlink(LocationLink locationLink, IRegion linkRegion) {
		this(Either.forRight(locationLink), linkRegion);
	}

	@Override
	public IRegion getHyperlinkRegion() {
		return this.highlightRegion;
	}

	@Override
	public String getTypeLabel() {
		return getLabel();
	}

	@Override
	public String getHyperlinkText() {
		return getLabel();
	}

	/**
	 * @return
	 * @noreference test only
	 */
	public Either<Location, LocationLink> getLocation() {
		return location;
	}

	@Override
	public void open() {
		if (location.isLeft()) {
			LSPEclipseUtils.openInEditor(location.getLeft());
		} else {
			LSPEclipseUtils.openInEditor(location.getRight());
		}
	}

	private String getLabel() {
		if (this.location != null) {
			String uri = this.location.isLeft() ? this.location.getLeft().getUri()
					: this.location.getRight().getTargetUri();
			if (uri != null) {
				if (uri.startsWith(FILE) && uri.length() > FILE.length()) {
					Range range = this.location.isLeft() ? this.location.getLeft().getRange()
							: this.location.getRight().getTargetSelectionRange();
					int line = -1;
					if (range != null && range.getStart() != null) {
						line = range.getStart().getLine();
					}
					return getFileBasedLabel(uri, line);
				}
				return getGenericUriBasedLabel(uri);
			}
		}

		return "";
	}

	private String getGenericUriBasedLabel(String uri) {
		return uri;
	}

	private String getFileBasedLabel(String uriStr, int line) {
		URI uri = URI.create(uriStr);
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IFile[] files = workspaceRoot.findFilesForLocationURI(uri);
		if (files != null && files.length == 1 && files[0].getProject() != null) {
			IFile file = files[0];

			@SuppressWarnings("null")
			String lineContent = getLineContent(line, file);
			if (lineContent != null) {
				return lineContent;
			} else {
				IPath containerPath = file.getParent().getProjectRelativePath();
				return file.getName() + DASH_SEPARATOR + file.getProject().getName() + (containerPath
						.isEmpty() ? "" : IPath.SEPARATOR + containerPath.toString()); //$NON-NLS-1$
			}
		}
		Path path = Paths.get(uri);
		return path.getFileName() + (path.getParent() == null || path.getParent().getParent() == null ? "" //$NON-NLS-1$
				: DASH_SEPARATOR + path.toString());
	}

	/**
	 * Gets the text content of the given line in the given {@link IFile}.
	 *
	 * @param line
	 *            the line index
	 * @param file
	 *            the {@link IFile}
	 * @return the text content of the given line in the given {@link IFile}
	 */
	public static String getLineContent(int line, final @NonNull IFile file) {
		try (InputStream is = file.getContents()) {
			String content = new String(is.readNBytes((int)file.getLocation().toFile().length()), Charset
					.forName(file.getCharset()));
			IDocument document = new Document(content);

			int offset = document.getLineOffset(line);
			int length = document.getLineLength(line);

			return document.get(offset, length).trim();
		} catch (Exception e) {
			return null;
		}
	}
}
