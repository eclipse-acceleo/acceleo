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
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui.declaration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.lsp4e.LSPEclipseUtils;
import org.eclipse.lsp4e.LanguageServerPlugin;
import org.eclipse.lsp4e.LanguageServers;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.LocationLink;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.jsonrpc.messages.Either;

// copied from org.eclipse.lsp4e.operations.declaration.OpenDeclarationHyperlinkDetector
public class AcceleoOpenDeclarationHyperlinkDetector extends AbstractHyperlinkDetector {

	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region,
			boolean canShowMultipleHyperlinks) {
		final IDocument document = textViewer.getDocument();
		if (document == null || !isAcceleoDocument(document)) {
			return null;
		}
		TextDocumentPositionParams params;
		try {
			params = LSPEclipseUtils.toTextDocumentPosistionParams(region.getOffset(), document);
		} catch (BadLocationException e) {
			LanguageServerPlugin.logError(e);
			return null;
		}
		final var allLinks = new LinkedHashMap<Either<Location, LocationLink>, AcceleoLSBasedHyperlink>();
		try {
			var definitions = LanguageServers.forDocument(document).withCapability(
					ServerCapabilities::getDefinitionProvider).collectAll(ls -> ls.getTextDocumentService()
							.definition(LSPEclipseUtils.toDefinitionParams(params)).thenApply(l -> l));
			var typeDefinitions = LanguageServers.forDocument(document).withCapability(
					ServerCapabilities::getTypeDefinitionProvider).collectAll(ls -> ls
							.getTextDocumentService().typeDefinition(LSPEclipseUtils.toTypeDefinitionParams(
									params)).thenApply(l -> l));
			var implementations = LanguageServers.forDocument(document).withCapability(
					ServerCapabilities::getImplementationProvider).collectAll(ls -> ls
							.getTextDocumentService().implementation(LSPEclipseUtils.toImplementationParams(
									params)).thenApply(l -> l));
			LanguageServers.addAll(LanguageServers.addAll(definitions, typeDefinitions), implementations).get(
					800, TimeUnit.MILLISECONDS).stream().flatMap(locations -> toHyperlinks(document, region,
							locations).stream()).forEach(link -> allLinks.putIfAbsent(link.getLocation(),
									link));
		} catch (ExecutionException e) {
			LanguageServerPlugin.logError(e);
		} catch (InterruptedException e) {
			LanguageServerPlugin.logError(e);
			Thread.currentThread().interrupt();
		} catch (TimeoutException e) {
			LanguageServerPlugin.logWarning(
					"Could not detect hyperlinks due to timeout after 800 milliseconds", e); //$NON-NLS-1$
		}
		if (allLinks.isEmpty()) {
			return null;
		}
		return allLinks.values().toArray(new IHyperlink[allLinks.size()]);
	}

	private static boolean isAcceleoDocument(IDocument document) {
		final boolean res;

		final List<IContentType> contentTypes = LSPEclipseUtils.getDocumentContentTypes(document);
		boolean foundAcceleoContentType = false;
		for (IContentType contentType : contentTypes) {
			if ("Acceleo".equals(contentType.getName())) {
				foundAcceleoContentType = true;
				break;
			}
		}
		res = foundAcceleoContentType;

		return res;
	}

	/**
	 * Returns a list of {@link AcceleoLSBasedHyperlink} using the given LSP locations
	 *
	 * @param document
	 *            the document
	 * @param linkRegion
	 *            the region
	 * @param locations
	 *            the LSP locations
	 */
	private static Collection<AcceleoLSBasedHyperlink> toHyperlinks(IDocument document, IRegion region,
			Either<List<? extends Location>, List<? extends LocationLink>> locations) {
		if (locations == null) {
			return Collections.emptyList();
		} else {
			final Collection<AcceleoLSBasedHyperlink> res = new ArrayList<>();

			if (locations.isLeft()) {
				for (Location location : locations.getLeft()) {
					res.add(new AcceleoLSBasedHyperlink(location, findWord(document, region)));
				}
			} else if (locations.isRight()) {
				for (LocationLink locationLink : locations.getRight()) {
					res.add(new AcceleoLSBasedHyperlink(locationLink, getSelectedRegion(document, region,
							locationLink)));
				}
			}

			return res;
		}
	}

	/**
	 * Returns the selection region, or if that fails , fallback to {@link #findWord(IDocument, IRegion)}
	 */
	private static IRegion getSelectedRegion(IDocument document, IRegion region, LocationLink locationLink) {
		Range originSelectionRange = locationLink.getOriginSelectionRange();
		if (originSelectionRange != null) {
			try {
				int offset = LSPEclipseUtils.toOffset(originSelectionRange.getStart(), document);
				int endOffset = LSPEclipseUtils.toOffset(originSelectionRange.getEnd(), document);
				return new Region(offset, endOffset - offset);
			} catch (BadLocationException e) {
				LanguageServerPlugin.logError(e.getMessage(), e);
			}
		}
		return findWord(document, region);
	}

	/**
	 * Fallback for missing range value (which can be used to highlight hyperlink) in LSP 'definition'
	 * response.
	 */
	private static IRegion findWord(IDocument document, IRegion region) {
		int start = -2;
		int end = -1;
		int offset = region.getOffset();

		try {

			int pos = offset;
			char c;

			while (pos >= 0 && pos < document.getLength()) {
				c = document.getChar(pos);
				if (!Character.isUnicodeIdentifierPart(c)) {
					break;
				}
				--pos;
			}

			start = pos;

			pos = offset;
			int length = document.getLength();

			while (pos < length) {
				c = document.getChar(pos);
				if (!Character.isUnicodeIdentifierPart(c))
					break;
				++pos;
			}

			end = pos;

		} catch (BadLocationException x) {
			LanguageServerPlugin.logWarning(x.getMessage(), x);
		}

		if (start >= -1 && end > -1) {
			if (start == offset && end == offset)
				return new Region(offset, 0);
			else if (start == offset)
				return new Region(start, end - start);
			else
				return new Region(start + 1, end - start - 1);
		}

		return region;
	}

}
