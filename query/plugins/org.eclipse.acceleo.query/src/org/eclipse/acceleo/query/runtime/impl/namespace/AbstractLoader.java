/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl.namespace;

import org.eclipse.acceleo.query.runtime.namespace.ILoader;

/**
 * Abstract loader.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractLoader implements ILoader {

	/**
	 * A slash.
	 */
	protected static final String SLASH = "/";

	/**
	 * A dot.
	 */
	protected static final String DOT = ".";

	/**
	 * The qualifier separator.
	 */
	private final String qualifierSeparator;

	/**
	 * The file extension.
	 */
	private final String fileExtension;

	/**
	 * The source file extension.
	 */
	private final String sourceFileExtension;

	/**
	 * Constructor.
	 * 
	 * @param qualifierSeparator
	 *            the qualifier separator
	 * @param fileExtension
	 *            the file extension
	 * @param sourceFileExtension
	 *            the source file extension
	 */
	public AbstractLoader(String qualifierSeparator, String fileExtension, String sourceFileExtension) {
		this.qualifierSeparator = qualifierSeparator;
		this.fileExtension = fileExtension;
		this.sourceFileExtension = sourceFileExtension;
	}

	@Override
	public String qualifiedName(String resourceName) {
		final String res;

		if (resourceName.endsWith(DOT + fileExtension)) {
			res = resourceName.replace(SLASH, qualifierSeparator).substring(0, resourceName.length()
					- (fileExtension.length() + 1));
		} else {
			res = null;
		}

		return res;
	}

	@Override
	public String resourceName(String qualifiedName) {
		return qualifiedName.replace(qualifierSeparator, SLASH) + DOT + fileExtension;
	}

	@Override
	public String sourceResourceName(String qualifiedName) {
		return qualifiedName.replace(qualifierSeparator, SLASH) + DOT + sourceFileExtension;
	}
}
