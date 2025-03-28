/*******************************************************************************
 * Copyright (c) 2008, 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.evaluation.strategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.ProtectedArea;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoWriter;
import org.eclipse.acceleo.aql.evaluation.writer.NullWriter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;

/**
 * This generation strategy can be used to generate files on-the-fly.
 * <p>
 * It will create {@link java.io.BufferedWriter}s so that files are just written to the disk as needed. This
 * is the least memory-expensive strategy; however, it is not aware of the workspace and the VCSs in use for
 * these files and does not check for write permission before writing the files. As such, it is not to be used
 * with a pessimistic locking VCS such as clearcase for example.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class DefaultGenerationStrategy implements IAcceleoGenerationStrategy {

	/** Used to call URIConverter methods with no options. */
	private static final Map<String, Object> EMPTY_OPTION_MAP = Collections.emptyMap();

	/**
	 * Keeps track of the contents of all protected areas from the files we've created writers for.
	 * <p>
	 * This will only contain contents about the writers created in {@link OpenModeKind#OVERWRITE} mode.
	 * </p>
	 */
	protected final Map<URI, Map<String, List<String>>> protectedAreaContents;

	/**
	 * We'll use this uri converter to create our streams towards files to be opened in
	 * {@link OpenModeKind#OVERWRITE} or {@link OpenModeKind#CREATE} mode.
	 */
	protected final URIConverter uriConverter;

	/**
	 * The {@link IWriterFactory}.
	 */
	private final IWriterFactory writerFactory;

	/**
	 * Constructor.
	 * 
	 * @param uriConverter
	 *            the {@link URIConverter}
	 * @param writerFactory
	 *            the {@link IWriterFactory}
	 */
	public DefaultGenerationStrategy(URIConverter uriConverter, IWriterFactory writerFactory) {
		protectedAreaContents = new LinkedHashMap<URI, Map<String, List<String>>>();
		this.uriConverter = uriConverter;
		this.writerFactory = writerFactory;
	}

	@Override
	public void closeWriter(IAcceleoWriter writer) throws IOException {
		writer.close();
	}

	@Override
	public String consumeProtectedAreaContent(URI uri, String protectedAreaID) {
		final String res;

		final Map<String, List<String>> uriAreaContentsMap = protectedAreaContents.get(uri);
		if (uriAreaContentsMap != null) {
			final List<String> areaContents = uriAreaContentsMap.get(protectedAreaID);
			if (areaContents == null || areaContents.isEmpty()) {
				res = null;
			} else {
				res = areaContents.remove(0);
				if (areaContents.isEmpty()) {
					uriAreaContentsMap.remove(protectedAreaID);
					if (uriAreaContentsMap.isEmpty()) {
						protectedAreaContents.remove(uri);
					}
				}
			}
		} else {
			res = null;
		}

		return res;
	}

	@Override
	public Map<String, List<String>> consumeAllProtectedAreas(URI uri) {
		final Map<String, List<String>> res;

		Map<String, List<String>> remainingAreaContents = protectedAreaContents.remove(uri);
		if (remainingAreaContents != null) {
			res = remainingAreaContents;
		} else {
			res = Collections.emptyMap();
		}

		return res;
	}

	@Override
	public IAcceleoWriter createWriterForLog(URI uri, Charset charset, String lineDelimiter)
			throws IOException {
		return createWriterFor(uri, OpenModeKind.APPEND, charset, lineDelimiter);
	}

	@Override
	public IAcceleoWriter createWriterForLostContent(URI uri, String protectedAreaID, Charset charset,
			String lineDelimiter) throws IOException {
		final URI lostURI = URI.createURI(uri.toString() + "-lost.txt");
		return createWriterFor(lostURI, OpenModeKind.APPEND, charset, lineDelimiter);
	}

	@Override
	public IAcceleoWriter createWriterFor(URI uri, OpenModeKind openMode, Charset charset,
			String lineDelimiter) throws IOException {
		final IAcceleoWriter writer;

		final boolean exists = uriConverter.exists(uri, EMPTY_OPTION_MAP);
		switch (openMode) {
			case CREATE:
				if (exists) {
					writer = new NullWriter(uri, charset, lineDelimiter);
				} else {
					writer = writerFactory.createWriter(OpenModeKind.CREATE, uri, uriConverter, charset,
							lineDelimiter);
				}
				break;
			case OVERWRITE:
				if (exists) {
					try (InputStream input = uriConverter.createInputStream(uri);) {
						Map<String, List<String>> protectedAreas = readProtectedAreaContent(
								new InputStreamReader(input), lineDelimiter);

						if (protectedAreas != null && !protectedAreas.isEmpty()) {
							protectedAreaContents.put(uri, protectedAreas);
						}
					}
				}

				writer = writerFactory.createWriter(OpenModeKind.OVERWRITE, uri, uriConverter, charset,
						lineDelimiter);
				break;
			case APPEND:
				writer = writerFactory.createWriter(OpenModeKind.APPEND, uri, uriConverter, charset,
						lineDelimiter);
				break;
			default:
				// TODO shouldn't happen, fall back to a null writer and log
				writer = new NullWriter(uri, charset, lineDelimiter);
				break;
		}

		return writer;
	}

	@Override
	public void start(URI destination) {
		// nothing to do here
	}

	@Override
	public void terminate() {
		// nothing to do here
	}

	/**
	 * Gets the mapping from {@link ProtectedArea}'s {@link ProtectedArea#getId() ID} to its {@link List} of
	 * contents.
	 * 
	 * @param reader
	 *            the {@link Reader}
	 * @param lineDelimiter
	 *            the line delimiter to use to create content
	 * @return the mapping from {@link ProtectedArea}'s {@link ProtectedArea#getId() ID} to its {@link List}
	 *         of contents
	 * @throws IOException
	 *             if the {@link Reader} can't be read
	 */
	protected Map<String, List<String>> readProtectedAreaContent(Reader reader, String lineDelimiter)
			throws IOException {
		final Map<String, List<String>> protectedAreas = new LinkedHashMap<>();
		final BufferedReader localReader = new BufferedReader(reader);

		String line = localReader.readLine();
		while (line != null) {
			final int start = line.indexOf(USER_CODE_START);
			if (start >= 0) {
				final String id = line.substring(start + USER_CODE_START.length()).trim();
				final StringBuffer areaContent = new StringBuffer(1024);
				areaContent.append(line.substring(start));
				areaContent.append(lineDelimiter);

				line = localReader.readLine();
				while (line != null) {
					final int end = line.indexOf(USER_CODE_END);
					if (end >= 0) {
						final int endOffset = end + USER_CODE_END.length();
						areaContent.append(line.substring(0, endOffset));
						areaContent.append(lineDelimiter);
						break;
					} else {
						areaContent.append(line);
						areaContent.append(lineDelimiter);
					}
					line = localReader.readLine();
				}
				protectedAreas.computeIfAbsent(id, i -> new ArrayList<>()).add(areaContent.toString());
			}
			line = localReader.readLine();
		}
		return protectedAreas;
	}

}
