/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.parser;

/**
 * One syntax warning of the parsing.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.0
 */
public class AcceleoParserWarning {

	/**
	 * The message.
	 */
	private String message;

	/**
	 * The line of the warning in the text.
	 */
	private int line;

	/**
	 * The beginning index of the warning in the text.
	 */
	private int posBegin;

	/**
	 * The ending index of the warning in the text.
	 */
	private int posEnd;

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            is the message
	 * @param line
	 *            is the line of the warning in the file
	 * @param posBegin
	 *            is the beginning index of the warning in the file
	 * @param posEnd
	 *            is the ending index of the warning in the file
	 */
	public AcceleoParserWarning(String message, int line, int posBegin, int posEnd) {
		this.message = message;
		this.line = line;
		this.posBegin = posBegin;
		this.posEnd = posEnd;
	}

	/**
	 * Gets the message.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Gets the line of the warning in the file.
	 * 
	 * @return the line of the warning in the file
	 */
	public int getLine() {
		return line;
	}

	/**
	 * Gets the beginning index of the warning in the file.
	 * 
	 * @return the beginning index of the warning in the file
	 */
	public int getPosBegin() {
		return posBegin;
	}

	/**
	 * Gets the ending index of the warning in the file.
	 * 
	 * @return the ending index of the warning in the file
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
}
