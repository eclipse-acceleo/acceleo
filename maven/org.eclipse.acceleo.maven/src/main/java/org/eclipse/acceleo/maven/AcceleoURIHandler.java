/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.maven;

import org.eclipse.acceleo.internal.parser.compiler.IAcceleoParserURIHandler;
import org.eclipse.emf.common.util.URI;

/**
 * The URI resolver that will change the path of the jar resource to a platform:/plugin path.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.2
 */
public class AcceleoURIHandler implements IAcceleoParserURIHandler {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.parser.compiler.IAcceleoParserURIHandler#transform(org.eclipse.emf.common.util.URI)
	 */
	public URI transform(URI uri) {
		URI newURI = uri;
		if (newURI.toString().startsWith("jar:file:")) {
			int indexOf = newURI.toString().indexOf(".jar!/");
			if (indexOf != -1) {
				String name = newURI.toString();
				name = name.substring(0, indexOf);
				name = name.substring("jar:file:".length() + 1);
				if (name.endsWith("-SNAPSHOT")) {
					name = name.substring(0, name.length() - "-SNAPSHOT".length());
				}

				name = name.substring(0, name.lastIndexOf("-"));
				if (name.contains("/")) {
					name = name.substring(name.lastIndexOf("/"));
					name = name + "/";
				}
				name = "platform:/plugin" + name + newURI.toString().substring(indexOf + ".jar!/".length());
				newURI = URI.createURI(name);
			}
		}
		return newURI;
	}

}
