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
package org.eclipse.acceleo.aql.ide.ui.evaluation.writer;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import org.eclipse.acceleo.aql.ide.evaluation.writer.AcceleoWorkspaceURIWriter;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;

/**
 * This {@link AcceleoWorkspaceURIWriter} will try to format the Java code with the JDT.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoUIWorkspaceURIWriter extends AcceleoWorkspaceURIWriter {

	/**
	 * Creates a writer for the given target {@link URI}.
	 * 
	 * @param targetURI
	 *            URI of the target {@link URI}.
	 * @param uriConverter
	 *            URI Converter to use for this writer's target.
	 * @param charset
	 *            The charset for our written content.
	 * @param lineDelimiter
	 *            the line delimiter
	 * @param preview
	 *            the preview {@link Map} or <code>null</code> for no preview
	 */
	public AcceleoUIWorkspaceURIWriter(URI targetURI, URIConverter uriConverter, Charset charset,
			String lineDelimiter, Map<URI, String> preview) {
		super(targetURI, uriConverter, charset, lineDelimiter, preview);
	}

	@Override
	protected String getContent() throws IOException {
		final String res;

		final String content = super.getContent();
		if (content != null) {
			res = JDTFormaterUtils.getFormatedCode(getTargetURI(), content, getLineDelimiter());
		} else {
			res = content;
		}

		return res;
	}

}
