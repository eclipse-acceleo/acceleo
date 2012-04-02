/*****************************************************************************************
 * Copyright (c) 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *****************************************************************************************/
package org.eclipse.acceleo.parser.interpreter;

import org.eclipse.emf.common.util.URI;

/**
 * Describes a module that should be built in memory.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.2
 */
public class ModuleDescriptor {
	/** The URI of this module. */
	private URI moduleURI;

	/** Qualified name of this module, OCL-style ("::" namespace separator). */
	private String qualifiedName;

	/** Full String representation of this module's content. */
	private String moduleContent;

	/**
	 * Builds a module descriptor with all required fields.
	 * 
	 * @param moduleURI
	 *            URI of the module that should be built.
	 * @param qualifiedName
	 *            Qualified name of this module in case it should be later imported.
	 * @param moduleContent
	 *            Full String representation of the module that should be built.
	 */
	public ModuleDescriptor(URI moduleURI, String qualifiedName, String moduleContent) {
		this.moduleURI = moduleURI;
		this.qualifiedName = qualifiedName;
		this.moduleContent = moduleContent;
	}

	/**
	 * Returns this module's URI.
	 * 
	 * @return This module's URI.
	 */
	public URI getModuleURI() {
		return moduleURI;
	}

	/**
	 * Returns this module's qualified name.
	 * 
	 * @return This module's qualified name.
	 */
	public String getQualifiedName() {
		return qualifiedName;
	}

	/**
	 * Returns this module's String content.
	 * 
	 * @return This module's String content.
	 */
	public String getModuleContent() {
		return moduleContent;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ModuleDescriptor)) {
			return false;
		}

		boolean equal = false;
		if (obj == this) {
			equal = true;
		} else {
			ModuleDescriptor other = (ModuleDescriptor)obj;
			equal = getModuleURI().equals(other.getModuleURI())
					&& getQualifiedName().equals(other.getQualifiedName())
					&& getModuleContent().equals(other.getModuleContent());
		}

		return equal;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getModuleURI().hashCode() + getQualifiedName().hashCode() + getModuleContent().hashCode();
	}
}
