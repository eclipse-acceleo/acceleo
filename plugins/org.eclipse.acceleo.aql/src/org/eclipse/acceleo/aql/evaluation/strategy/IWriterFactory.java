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

import java.io.IOException;
import java.nio.charset.Charset;

import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoWriter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;

/**
 * The factory for {@link IAcceleoWriter}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IWriterFactory {

	/**
	 * Creates the {@link IAcceleoWriter} for the given target {@link URI} and {@link Charset}.
	 * 
	 * @param openModeKind
	 *            the {@link OpenModeKind}
	 * @param uri
	 *            the target {@link URI}
	 * @param uriConverter
	 *            the {@link URIConverter}
	 * @param charset
	 *            the {@link Charset}
	 * @param lineDelimiter
	 *            the line delimiter that was demanded by the user for this writer.
	 * @return the created {@link IAcceleoWriter} for the given target {@link URI} and {@link Charset}
	 * @throws IOException
	 *             if the {@link IAcceleoWriter} can't be created
	 */
	IAcceleoWriter createWriter(OpenModeKind openModeKind, URI uri, URIConverter uriConverter,
			Charset charset, String lineDelimiter) throws IOException;

}
