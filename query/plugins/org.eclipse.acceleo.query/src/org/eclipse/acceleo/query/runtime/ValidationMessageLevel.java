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
 * Message level used on {@link IValidationMessage}.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public enum ValidationMessageLevel {
	/**
	 * used for simple information message.
	 */
	INFO,
	/**
	 * used to report potential errors (error that can occur in certain cases but that do not * always occur).
	 */
	WARNING,
	/**
	 * used to report errors that will most probably occur.
	 */
	ERROR;

}
