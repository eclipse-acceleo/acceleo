/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl;

import org.eclipse.acceleo.query.runtime.IValidationMessage;
import org.eclipse.acceleo.query.runtime.ValidationMessageLevel;

/**
 * {@link IValidationMessage} implementation.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ValidationMessage implements IValidationMessage {

	/**
	 * The {@link ValidationMessageLevel}.
	 */
	private ValidationMessageLevel level;

	/**
	 * The message.
	 */
	private final String message;

	/**
	 * The start position.
	 */
	private final int startPosition;

	/**
	 * The end position.
	 */
	private final int endPosition;

	/**
	 * Constructor.
	 * 
	 * @param level
	 *            the {@link ValidationMessageLevel}
	 * @param message
	 *            the message
	 * @param startPosition
	 *            the start position
	 * @param endPosition
	 *            the end position
	 */
	public ValidationMessage(ValidationMessageLevel level, String message, int startPosition, int endPosition) {
		this.level = level;
		this.message = message;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IValidationMessage#getLevel()
	 */
	@Override
	public ValidationMessageLevel getLevel() {
		return level;
	}

	/**
	 * Sets the {@link ValidationMessageLevel}.
	 * 
	 * @param level
	 *            the level to set
	 */
	public void setLevel(ValidationMessageLevel level) {
		this.level = level;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IValidationMessage#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IValidationMessage#getStartPosition()
	 */
	@Override
	public int getStartPosition() {
		return startPosition;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IValidationMessage#getEndPosition()
	 */
	@Override
	public int getEndPosition() {
		return endPosition;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%s from %d to %d: %s", getLevel().toString(), getStartPosition(),
				getEndPosition(), getMessage());
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return level.hashCode() ^ (startPosition << Byte.SIZE + endPosition) ^ message.hashCode();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		final boolean result;

		if (obj instanceof ValidationMessage) {
			final ValidationMessage other = (ValidationMessage)obj;
			result = other.level == level && other.startPosition == startPosition
					&& other.endPosition == endPosition
					&& (other.message != null && other.message.equals(message) || other.message == message);
		} else {
			result = false;
		}

		return result;
	}
}
