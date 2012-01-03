/*******************************************************************************
 * Copyright (c) 2011, 2012 Obeo.
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
 * This exception will be used to wrap runtime exceptions blocking the evaluation of Acceleo modules.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.1
 */
public class AcceleoRuntimeException extends RuntimeException {
	/** Serial version UID. Used for deserialization. */
	private static final long serialVersionUID = -926769089368960423L;

	/**
	 * Enhance visibility.
	 * 
	 * @param cause
	 *            Actual Runtime exception that caused this.
	 */
	public AcceleoRuntimeException(Throwable cause) {
		super(cause);
	}
}
