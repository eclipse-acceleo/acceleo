/*******************************************************************************
 * Copyright (c) 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl.namespace;

import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation.IPosition;
import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation.IRange;

public class Range implements IRange {

	/**
	 * The start {@link IPosition}.
	 */
	private final IPosition start;

	/**
	 * The end {@link IPosition}.
	 */
	private final IPosition end;

	/**
	 * Constructor.
	 * 
	 * @param start
	 *            the start {@link IPosition}
	 * @param end
	 *            the end {@link IPosition}
	 */
	public Range(IPosition start, IPosition end) {
		super();
		this.start = start;
		this.end = end;
	}

	@Override
	public IPosition getStart() {
		return start;
	}

	@Override
	public IPosition getEnd() {
		return end;
	}

}
