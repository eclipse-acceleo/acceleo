/*******************************************************************************
 * Copyright (c) 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui.evaluation.strategy;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoWriter;
import org.eclipse.acceleo.aql.ide.evaluation.strategy.AcceleoWorkspaceWriterFactory;
import org.eclipse.acceleo.aql.ide.ui.evaluation.writer.AcceleoUIWorkspaceURIWriter;
import org.eclipse.acceleo.query.runtime.impl.namespace.JavaLoader;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;

/**
 * A {@link AcceleoUIWorkspaceWriterFactory} that format Java code with the JDT.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoUIWorkspaceWriterFactory extends AcceleoWorkspaceWriterFactory {

	/**
	 * Constructor.
	 */
	public AcceleoUIWorkspaceWriterFactory() {
		this(null);
	}

	/**
	 * Constructor.
	 * 
	 * @param preview
	 *            the preview {@link Map}
	 */
	public AcceleoUIWorkspaceWriterFactory(Map<URI, String> preview) {
		super(preview);
	}

	@Override
	public IAcceleoWriter createWriter(OpenModeKind openModeKind, URI uri, URIConverter uriConverter,
			Charset charset, String lineDelimiter) throws IOException {
		final IAcceleoWriter res;

		if (openModeKind == OpenModeKind.OVERWRITE && EMFPlugin.IS_ECLIPSE_RUNNING && JavaLoader.JAVA.equals(
				uri.fileExtension())) {
			res = new AcceleoUIWorkspaceURIWriter(uri, uriConverter, charset, lineDelimiter, getPreview());
		} else {
			res = super.createWriter(openModeKind, uri, uriConverter, charset, lineDelimiter);
		}

		return res;
	}

}
