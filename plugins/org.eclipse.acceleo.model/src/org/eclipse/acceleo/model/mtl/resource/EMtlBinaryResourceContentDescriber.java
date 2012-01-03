/*******************************************************************************
 * Copyright (c) 2006, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.model.mtl.resource;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.core.runtime.content.IContentDescriber;
import org.eclipse.core.runtime.content.IContentDescription;

/**
 * The EMTL binary resource content describer.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class EMtlBinaryResourceContentDescriber implements IContentDescriber {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement,
	 *      java.lang.String, java.lang.Object)
	 */
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {
		// do nothing
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.content.IContentDescriber#describe(java.io.InputStream,
	 *      org.eclipse.core.runtime.content.IContentDescription)
	 */
	public int describe(InputStream contents, IContentDescription description) throws IOException {
		// We will use the XML content describer to see if this is a XML file.
		// If it is not, we'll consider that it's a binary file :)
		XMLContentDescriber xmlContentDescriber = new XMLContentDescriber();
		if (xmlContentDescriber.describe(contents, description) == IContentDescriber.VALID) {
			return IContentDescriber.INVALID;
		}
		return VALID;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.content.IContentDescriber#getSupportedOptions()
	 */
	public QualifiedName[] getSupportedOptions() {
		return new QualifiedName[0];
	}

}
