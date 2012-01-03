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
package org.eclipse.acceleo.internal.parser;

/**
 * The syntax problems of the parsing.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This class is not intended to be subclassed by clients.
 */
public interface IAcceleoParserProblemsConstants {

	/**
	 * Syntax problem message : The parenthesis are required.
	 */
	String SYNTAX_PARENTHESIS_ARE_REQUIRED = AcceleoParserMessages
			.getString("IAcceleoParserProblemsConstants.MissingParenthesis"); //$NON-NLS-1$

	/**
	 * Syntax problem message : The parenthesis aren't terminated.
	 */
	String SYNTAX_PARENTHESIS_NOT_TERMINATED = AcceleoParserMessages
			.getString("IAcceleoParserProblemsConstants.MissingCloseParenthesis"); //$NON-NLS-1$

	/**
	 * Syntax problem message : Text not valid.
	 */
	String SYNTAX_TEXT_NOT_VALID = AcceleoParserMessages
			.getString("IAcceleoParserProblemsConstants.InvalidText"); //$NON-NLS-1$

	/**
	 * Syntax problem message : Type not valid.
	 */
	String SYNTAX_TYPE_NOT_VALID = AcceleoParserMessages
			.getString("IAcceleoParserProblemsConstants.InvalidType"); //$NON-NLS-1$

	/**
	 * Syntax problem message : Name not valid.
	 */
	String SYNTAX_NAME_NOT_VALID = AcceleoParserMessages
			.getString("IAcceleoParserProblemsConstants.InvalidIdentifier"); //$NON-NLS-1$

}
