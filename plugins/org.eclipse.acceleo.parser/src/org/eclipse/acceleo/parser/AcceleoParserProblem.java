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
package org.eclipse.acceleo.parser;

import com.google.common.base.Objects;

/**
 * One syntax problem of the parsing.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoParserProblem implements AcceleoParserMessage {

	/**
	 * The message.
	 */
	private String message;

	/**
	 * The line of the problem in the text.
	 */
	private int line;

	/**
	 * The beginning index of the problem in the text.
	 */
	private int posBegin;

	/**
	 * The ending index of the problem in the text.
	 */
	private int posEnd;

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            is the message
	 * @param line
	 *            is the line of the problem in the file
	 * @param posBegin
	 *            is the beginning index of the problem in the file
	 * @param posEnd
	 *            is the ending index of the problem in the file
	 */
	public AcceleoParserProblem(String message, int line, int posBegin, int posEnd) {
		this.message = message;
		this.line = line;
		this.posBegin = posBegin;
		this.posEnd = posEnd;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.parser.AcceleoParserMessage#getMessage()
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.parser.AcceleoParserMessage#getLine()
	 */
	public int getLine() {
		return line;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.parser.AcceleoParserMessage#getPosBegin()
	 */
	public int getPosBegin() {
		return posBegin;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.parser.AcceleoParserMessage#getPosEnd()
	 */
	public int getPosEnd() {
		return posEnd;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getMessage();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AcceleoParserProblem)) {
			return false;
		}

		final AcceleoParserProblem other = (AcceleoParserProblem)obj;
		return this == other
				|| (Objects.equal(this.line, other.line) && Objects.equal(this.message, other.message)
						&& Objects.equal(this.posBegin, other.posBegin) && Objects.equal(this.posEnd,
						other.posEnd));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int hashCode = prime;

		int tmp = line << prime;
		hashCode = hashCode * prime + line * tmp;

		if (message == null) {
			hashCode = hashCode * prime;
		} else {
			hashCode = hashCode * prime + message.hashCode();
		}

		tmp = posBegin << prime;
		hashCode = hashCode * prime + posBegin * tmp;

		tmp = posEnd << prime;
		hashCode = hashCode * prime + posEnd * tmp;

		return hashCode;
	}
}
