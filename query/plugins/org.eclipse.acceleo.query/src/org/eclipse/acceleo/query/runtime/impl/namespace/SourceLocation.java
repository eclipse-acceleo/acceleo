/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl.namespace;

import java.net.URI;

import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation;

/**
 * {@link ISourceLocation} implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class SourceLocation implements ISourceLocation {

	/**
	 * The source {@link URI}.
	 */
	private final URI sourceURI;

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
	 * @param sourceURI
	 *            the source {@link URI}
	 * @param identifierRange
	 *            the identifier {@link IRange}
	 * @param range
	 *            the total {@link IRange}
	 */
	public SourceLocation(URI sourceURI, IRange identifierRange, IRange range) {
		this.sourceURI = sourceURI;
		this.identifierRange = identifierRange;
		this.range = range;
	}

	@Override
	public URI getSourceURI() {
		return sourceURI;
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
