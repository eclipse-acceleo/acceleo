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
package org.eclipse.acceleo.engine;

/**
 * This exception will be used to wrap exceptions occuring in the process of evaluating Acceleo modules.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEvaluationException extends RuntimeException {
	/** Serial version UID. Used for deserialization. */
	private static final long serialVersionUID = 7032034280090167507L;

	/**
	 * Instantiates an evaluation exception given its error message.
	 * 
	 * @param message
	 *            The exception details' message.
	 */
	public AcceleoEvaluationException(String message) {
		super(message);
	}

	/**
	 * Instantiates an evaluation exception wrapped around its cause.
	 * 
	 * @param message
	 *            The exception details' message.
	 * @param cause
	 *            Cause of this exception.
	 */
	public AcceleoEvaluationException(String message, Throwable cause) {
		super(message, cause);
	}
}
