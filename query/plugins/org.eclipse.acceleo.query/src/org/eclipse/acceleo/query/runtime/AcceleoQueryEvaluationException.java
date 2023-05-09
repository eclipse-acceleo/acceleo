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
package org.eclipse.acceleo.query.runtime;

/**
 * Exception thrown whenever an internal error is detected during evaluation of a Query.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class AcceleoQueryEvaluationException extends RuntimeException {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -1793169263392796022L;

	/**
	 * Creates a new {@link AcceleoQueryEvaluationException} instance.
	 */
	public AcceleoQueryEvaluationException() {
	}

	/**
	 * Creates a new {@link AcceleoQueryEvaluationException} instance with the specified message.
	 * 
	 * @param message
	 *            the message to be printed in the stack trace.
	 */
	public AcceleoQueryEvaluationException(String message) {
		super(message);
	}

	/**
	 * Creates a new {@link AcceleoQueryEvaluationException} instance with the specified message.
	 * 
	 * @param cause
	 *            the exception that's embedded in this instance.
	 */

	public AcceleoQueryEvaluationException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new {@link AcceleoQueryEvaluationException} instance with the specified message.
	 * 
	 * @param cause
	 *            the exception that's embedded in this instance.
	 * @param message
	 *            the message to be printed in the stack trace.
	 */

	public AcceleoQueryEvaluationException(String message, Throwable cause) {
		super(message, cause);
	}

}
