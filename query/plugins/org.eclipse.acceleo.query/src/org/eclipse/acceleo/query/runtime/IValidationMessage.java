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
 * Message reported during validation of a query.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public interface IValidationMessage {
	/**
	 * Level of validation message.
	 * 
	 * @return the level of the validation message.
	 */
	ValidationMessageLevel getLevel();

	/**
	 * The actual message.
	 * 
	 * @return the actual message.
	 */
	String getMessage();

	/**
	 * The column number where the problem has been detected.
	 * 
	 * @return The column number where the problem has been detected.
	 */
	int getLocationLine();

	/**
	 * The column number where the problem has been detected.
	 * 
	 * @return The column number where the problem has been detected.
	 */
	int getLocationColumn();

}