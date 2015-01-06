/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
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
	 * The line.
	 */
	private final int line;

	/**
	 * The comlumn.
	 */
	private final int column;

	/**
	 * Constructor.
	 * 
	 * @param level
	 *            the {@link ValidationMessageLevel}
	 * @param message
	 *            the message
	 * @param line
	 *            the line number
	 * @param column
	 *            the column number
	 */
	public ValidationMessage(ValidationMessageLevel level, String message, int line, int column) {
		this.level = level;
		this.message = message;
		this.line = line;
		this.column = column;
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
	 * @see org.eclipse.acceleo.query.runtime.IValidationMessage#getLocationLine()
	 */
	@Override
	public int getLocationLine() {
		return line;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IValidationMessage#getLocationColumn()
	 */
	@Override
	public int getLocationColumn() {
		return column;
	}

}
