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
package org.eclipse.acceleo.aql.ide.evaluation.strategy;

import java.io.IOException;
import java.nio.charset.Charset;

import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.aql.evaluation.strategy.DefaultWriterFactory;
import org.eclipse.acceleo.aql.evaluation.strategy.IWriterFactory;
import org.eclipse.acceleo.aql.evaluation.writer.IAcceleoWriter;
import org.eclipse.acceleo.aql.ide.evaluation.writer.AcceleoWorkspaceURIWriter;
import org.eclipse.acceleo.query.runtime.impl.namespace.JavaLoader;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;

/**
 * The default {@link IWriterFactory}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoWorkspaceURIWriterFactory extends DefaultWriterFactory {

	@Override
	public IAcceleoWriter createWriter(OpenModeKind openModeKind, URI uri, URIConverter uriConverter,
			Charset charset, String lineDelimiter) throws IOException {
		final IAcceleoWriter res;

		if (openModeKind == OpenModeKind.OVERWRITE) {
			if (EMFPlugin.IS_ECLIPSE_RUNNING && JavaLoader.JAVA.equals(uri.fileExtension())) {
				res = new AcceleoWorkspaceURIWriter(uri, uriConverter, charset, lineDelimiter);
			} else {
				res = super.createWriter(openModeKind, uri, uriConverter, charset, lineDelimiter);
			}
		} else {
			res = super.createWriter(openModeKind, uri, uriConverter, charset, lineDelimiter);
		}

		return res;
	}

}
