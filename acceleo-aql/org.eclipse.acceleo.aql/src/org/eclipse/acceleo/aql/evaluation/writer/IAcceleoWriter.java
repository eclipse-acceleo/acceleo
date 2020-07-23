/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.evaluation.writer;

import java.io.Closeable;

import org.eclipse.emf.common.util.URI;

/**
 * Base class for the writers that need to be returned by the {@link IAcceleoGenerationStrategy generation
 * strategies}.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public interface IAcceleoWriter extends Closeable, Appendable {
	/**
	 * Returns the URI for which this writer was created.
	 * 
	 * @return The URI for which this writer was created.
	 */
	URI getTargetURI();
}
