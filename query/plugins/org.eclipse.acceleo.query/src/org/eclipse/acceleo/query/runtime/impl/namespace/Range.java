/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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

	/**
	 * Creates the {@link IRange} identified by the begin and end character indices in the given {@link String
	 * text}.
	 * 
	 * @param beginCharacterIndex
	 *            the (positive) begin character index.
	 * @param endCharacterIndex
	 *            the (positive) end character index.
	 * @param text
	 *            the (non-{@code null}) {@link String text}.
	 * @return the corresponding {@link IRange}.
	 */
	public static IRange getCorrespondingRange(int beginCharacterIndex, int endCharacterIndex, String text) {
		IPosition beginPosition = Position.getCorrespondingPosition(beginCharacterIndex, text);
		IPosition endPosition = Position.getCorrespondingPosition(endCharacterIndex, text);
		return new Range(beginPosition, endPosition);
	}

}
