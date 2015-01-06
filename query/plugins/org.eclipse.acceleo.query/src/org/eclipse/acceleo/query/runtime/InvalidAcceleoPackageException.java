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
package org.eclipse.acceleo.query.runtime;

/**
 * Exception thrown when an invalid package is registered.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class InvalidAcceleoPackageException extends Exception {

	/**
	 * Generated serial UID.
	 */
	private static final long serialVersionUID = -6398441984048705917L;

	/**
	 * Creates a new {@link InvalidAcceleoPackageException} instance.
	 */
	public InvalidAcceleoPackageException() {
	}

	/**
	 * Creates a new {@link InvalidAcceleoPackageException} instance.
	 * 
	 * @param message
	 *            the message to be printed in the stack trace.
	 */
	public InvalidAcceleoPackageException(String message) {
		super(message);
	}

	/**
	 * Creates a new {@link InvalidAcceleoPackageException} instance.
	 * 
	 * @param cause
	 *            the exception that's embedded in this instance.
	 */
	public InvalidAcceleoPackageException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new {@link InvalidAcceleoPackageException} instance.
	 * 
	 * @param cause
	 *            the exception that's embedded in this instance.
	 * @param message
	 *            the message to be printed in the stack trace.
	 */
	public InvalidAcceleoPackageException(String message, Throwable cause) {
		super(message, cause);
	}

}
