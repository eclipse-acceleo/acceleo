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
 * A common interface for all the message reporting system of the parser.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public interface AcceleoParserMessages {

	/**
	 * Clear the list of datas.
	 */
	void clear();

	/**
	 * Computes a buffer with all the messages of the datas.
	 * 
	 * @return the message
	 */
	String getMessage();
}
