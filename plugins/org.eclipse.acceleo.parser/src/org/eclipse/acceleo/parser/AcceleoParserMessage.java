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
package org.eclipse.acceleo.parser;

/**
 * Message returned by the parser, this acts as a common interface for all three of {@link AcceleoParserInfo},
 * {@link AcceleoParserWarning} and {@link AcceleoParserProblem}.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 * @since 3.1
 */
public interface AcceleoParserMessage {
	/**
	 * Gets the message.
	 * 
	 * @return the message
	 */
	String getMessage();

	/**
	 * Gets the line of the warning in the file.
	 * 
	 * @return the line of the warning in the file
	 */
	int getLine();

	/**
	 * Gets the beginning index of the warning in the file.
	 * 
	 * @return the beginning index of the warning in the file
	 */
	int getPosBegin();

	/**
	 * Gets the ending index of the warning in the file.
	 * 
	 * @return the ending index of the warning in the file
	 */
	int getPosEnd();
}
