/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.services.exceptions;

/**
 * A {@link RuntimeException} for when our language server receives requests that do not respect the expected
 * Language Server Protocol. This may be due to several reasons, such as incorrect implementations of the
 * protocol, mismatched protocol versions, etc.
 * 
 * @author Florent Latombe
 */
public class LanguageServerProtocolException extends RuntimeException {

	/**
	 * Class UID for serialization.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with a message.
	 * 
	 * @param message
	 *            the {@link String} message.
	 */
	public LanguageServerProtocolException(String message) {
		super(message);
	}

	/**
	 * Constructor with a message and a cause.
	 * 
	 * @param message
	 *            the {@link String} message.
	 * @param cause
	 *            the {@link Throwable} cause.
	 */
	public LanguageServerProtocolException(String message, Throwable cause) {
		super(message, cause);
	}
}
