/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.namespace;

import java.net.URI;

/**
 * Source location.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface ISourceLocation {

	/**
	 * Position.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	public interface IPosition {

		/**
		 * Gets the line number.
		 * 
		 * @return the line number
		 */
		int getLine();

		/**
		 * Gets the column.
		 * 
		 * @return the column
		 */
		int getColumn();

		/**
		 * Get the position (offset).
		 * 
		 * @return the position (offset)
		 */
		int getPosition();

	}

	/**
	 * Range.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	public interface IRange {

		/**
		 * Gets the start {@link IPosition}.
		 * 
		 * @return the start {@link IPosition}
		 */
		IPosition getStart();

		/**
		 * Gets the end {@link IPosition}.
		 * 
		 * @return the end {@link IPosition}
		 */
		IPosition getEnd();
	}

	/**
	 * Gets the source {@link URI}.
	 * 
	 * @return the source {@link URI}
	 */
	URI getSourceURI();

	/**
	 * Gets the identifier {@link IRange}.
	 * 
	 * @return the identifier {@link IRange}
	 */
	IRange getIdentifierRange();

	/**
	 * Gets the total {@link IRange}.
	 * 
	 * @return the total {@link IRange}
	 */
	IRange getRange();
}
