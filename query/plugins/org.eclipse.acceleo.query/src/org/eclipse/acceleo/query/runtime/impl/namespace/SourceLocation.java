/*******************************************************************************
 * Copyright (c) 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl.namespace;

import java.net.URL;

import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation;

/**
 * {@link ISourceLocation} implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class SourceLocation implements ISourceLocation {

	/**
	 * The source {@link URL}.
	 */
	private static URL sourceURL;

	/**
	 * The identifier {@link IRange}.
	 */
	private final IRange identifierRange;

	/**
	 * The total {@link IRange}.
	 */
	private final IRange range;

	/**
	 * Constructor.
	 * 
	 * @param sourceURL
	 *            the source {@link URL}
	 * @param identifierRange
	 *            the identifier {@link IRange}
	 * @param range
	 *            the total {@link IRange}
	 */
	public SourceLocation(URL sourceURL, IRange identifierRange, IRange range) {
		this.identifierRange = identifierRange;
		this.range = range;
	}

	@Override
	public URL getSourceURL() {
		return sourceURL;
	}

	@Override
	public IRange getIdentifierRange() {
		return identifierRange;
	}

	@Override
	public IRange getRange() {
		return range;
	}

}
