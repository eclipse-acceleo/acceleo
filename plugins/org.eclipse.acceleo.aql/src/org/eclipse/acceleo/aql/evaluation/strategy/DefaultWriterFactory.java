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
package org.eclipse.acceleo.aql.evaluation.strategy;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;

import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.evaluation.writer.AcceleoFileWriter;
import org.eclipse.acceleo.aql.evaluation.writer.AcceleoURIWriter;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoWriter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;

/**
 * The default {@link IWriterFactory}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class DefaultWriterFactory implements IWriterFactory {

	/** Used to call URIConverter methods with no options. */
	private static final Map<String, Object> EMPTY_OPTION_MAP = Collections.emptyMap();

	@Override
	public IAcceleoWriter createWriter(OpenModeKind openModeKind, URI uri, URIConverter uriConverter,
			Charset charset, String lineDelimiter) throws IOException {
		final IAcceleoWriter res;

		switch (openModeKind) {
			case APPEND:
				final boolean exists = uriConverter.exists(uri, EMPTY_OPTION_MAP);
				final String fileString = uri.toFileString();
				if (fileString != null) {
					res = new AcceleoFileWriter(new File(fileString), charset, true);
				} else {
					res = new AcceleoURIWriter(uri, uriConverter, charset);
					if (exists) {
						try (InputStream contentInputStream = uriConverter.createInputStream(uri)) {
							final String content = AcceleoUtil.getContent(contentInputStream, charset.name());
							res.append(content);
						}
					}
				}
				break;

			default:
				res = new AcceleoURIWriter(uri, uriConverter, charset);
				break;
		}

		return res;
	}

}
