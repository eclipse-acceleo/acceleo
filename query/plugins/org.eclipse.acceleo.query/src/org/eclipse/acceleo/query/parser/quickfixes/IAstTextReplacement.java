/*******************************************************************************
 * Copyright (c) 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.parser.quickfixes;

import java.net.URI;

/**
 * Replaces some text in a resource.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IAstTextReplacement {

	/**
	 * Gets the {@link URI} of the resource to change.
	 * 
	 * @return the {@link URI} of the resource to change
	 */
	URI getURI();

	/**
	 * Gets the replacement text.
	 * 
	 * @return the replacement text
	 */
	String getReplacement();

	/**
	 * Gets the replacement start offset.
	 * 
	 * @return the replacement start offset
	 */
	int getStartOffset();

	/**
	 * Gets the replacement start line.
	 * 
	 * @return the replacement start line
	 */
	int getStartLine();

	/**
	 * Gets the replacement start column.
	 * 
	 * @return the replacement start column
	 */
	int getStartColumn();

	/**
	 * Gets the replacement end offset.
	 * 
	 * @return the replacement end offset
	 */
	int getEndOffset();

	/**
	 * Gets the replacement end line.
	 * 
	 * @return the replacement end line
	 */
	int getEndLine();

	/**
	 * Gets the replacement end column.
	 * 
	 * @return the replacement end column
	 */
	int getEndColumn();

}
