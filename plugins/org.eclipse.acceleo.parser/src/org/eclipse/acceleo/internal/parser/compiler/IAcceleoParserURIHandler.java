/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.parser.compiler;

import org.eclipse.emf.common.util.URI;

/**
 * The common interface for the URI handler.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @sicne 3.2
 */
public interface IAcceleoParserURIHandler {

	/**
	 * Transform the uri of a loaded Acceleo module.
	 * 
	 * @param uri
	 *            the uri
	 * @return A new URI for the Acceleo module.
	 */
	URI transform(URI uri);
}
