/*******************************************************************************
 * Copyright (c) 2008, 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.evaluation.writer;

import com.google.common.io.LineReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.acceleo.OpenModeKind;
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
	private static final Map<String, Object> EMPTY_OPTION_MAP = new HashMap<>();

	/**
	 * Keeps track of the contents of all protected areas from the files we've created writers for.
	 * <p>
	 * This will only contain contents about the writers created in {@link OpenModeKind#OVERWRITE} mode.
	 * </p>
	 */
	protected final Map<URI, Map<String, String>> protectedAreaContents;

	/**
	 * We'll use this uri converter to create our streams towards files to be opened in
	 * {@link OpenModeKind#OVERWRITE} or {@link OpenModeKind#CREATE} mode.
	 */
	protected final URIConverter uriConverter;

	/**
	 * Constructor.
	 */
	public DefaultGenerationStrategy() {
		protectedAreaContents = new LinkedHashMap<URI, Map<String, String>>();
		uriConverter = URIConverter.INSTANCE; // FIXME pass the instance ?
	}

	@Override
	public void closeWriter(IAcceleoWriter writer) throws IOException {
		writer.close();
	}

	@Override
	public String getProtectedAreaContent(URI uri, String protectedAreaID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAcceleoWriter createWriterFor(URI uri, OpenModeKind openMode, String charset, String lineDelimiter) {
		try {
			final IAcceleoWriter writer;
			final boolean exists = uriConverter.exists(uri, EMPTY_OPTION_MAP);
			switch (openMode) {
				case CREATE:
					if (exists) {
						writer = new NullWriter(uri);
						break;
					}
					// fall through: same behavior as "OVERWRITE" if the file doesn't exist
				case OVERWRITE:
					if (exists) {
						try (final InputStream input = uriConverter.createInputStream(uri);) {
							Map<String, String> protectedAreas = readProtectedAreaContent(
									new InputStreamReader(input), lineDelimiter);
							if (protectedAreas != null && !protectedAreas.isEmpty()) {
								protectedAreaContents.put(uri, protectedAreas);
							}
						}
					}

					// TODO Throw exception if charset doesn't exist? Should be caught by validation.
					Charset cs = Charset.forName(charset);
					writer = new AcceleoFileWriter(uri, uriConverter, cs);
					break;
				case APPEND:
					// FIXME we can't create a stream to "append" to a file with the URIConverter. We probably
					// need to fall back to a regular FileWriter here. For now, we'll fall through to the
					// "default" case and use a null writer.
				default:
					// TODO shouldn't happen, fall back to a null writer and log
					writer = new NullWriter(uri);
					break;
			}
			return writer;
		} catch (IOException e) {
			// FIXME log
                        e.printStackTrace();
			return new NullWriter(uri);
		}
	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub

	}

	protected Map<String, String> readProtectedAreaContent(Reader fileReader, String lineDelimiter)
			throws IOException {
		Map<String, String> protectedAreas = new LinkedHashMap<>();
		LineReader reader = new LineReader(fileReader);

		String line = reader.readLine();
		while (line != null) {
			final int start = line.indexOf(USER_CODE_START);
			if (start >= 0) {
				final String marker = line.substring(start + USER_CODE_START.length()).trim();
				final StringBuffer areaContent = new StringBuffer(1024);
				areaContent.append(line.substring(start));

				line = reader.readLine();
				while (line != null) {
					final int end = line.indexOf(USER_CODE_END);
					if (end >= 0) {
						final int endOffset = end + USER_CODE_END.length();
						areaContent.append(line.substring(0, endOffset));
						break;
					} else {
						areaContent.append(line);
						areaContent.append(lineDelimiter);
					}
				}
				protectedAreas.put(marker, areaContent.toString());
			}
			line = reader.readLine();
		}
		return protectedAreas;
	}
}
