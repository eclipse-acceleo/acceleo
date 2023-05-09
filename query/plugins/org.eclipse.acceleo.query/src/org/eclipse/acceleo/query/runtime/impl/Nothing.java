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

/**
 * Instances of {@link Nothing} are the values passed around when a query cannot be evaluated.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class Nothing {
	/**
	 * The message associated with this nothing instance. Usually set to an error message related to the issue
	 * that resulted in this nothing.
	 */
	private final String message;

	/**
	 * If this nothing was created as a result of an exception raised during evaluation, this will reference
	 * it.
	 */
	private final Throwable cause;

	/**
	 * Creates a nothing with the given message and no linked exception.
	 * 
	 * @param message
	 *            The message.
	 */
	public Nothing(String message) {
		this(message, null);
	}

	/**
	 * Creates a nothing with the given error message and cause exception.
	 * 
	 * @param message
	 *            The message.
	 * @param cause
	 *            The root cause of this Nothing's creation.
	 */
	public Nothing(String message, Throwable cause) {
		this.message = message;
		this.cause = cause;
	}

	/**
	 * The message associated with this Nothing.
	 * 
	 * @return the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * The root cause of this nothing.
	 * 
	 * @return the root cause of this nothing.
	 */
	public Throwable getCause() {
		return cause;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return message;
	}

}
