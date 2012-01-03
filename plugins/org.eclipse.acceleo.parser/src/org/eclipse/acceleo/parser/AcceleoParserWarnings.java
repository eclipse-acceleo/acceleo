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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * All the syntax warnings of the parsing.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class AcceleoParserWarnings implements AcceleoParserMessages {
	/**
	 * List of warnings.
	 */
	private List<AcceleoParserWarning> list;

	/**
	 * Constructor.
	 */
	public AcceleoParserWarnings() {
		list = new ArrayList<AcceleoParserWarning>();
	}

	/**
	 * The way to add a new warning.
	 * 
	 * @param message
	 *            is the message
	 * @param line
	 *            is the line of the warning
	 * @param posBegin
	 *            is the beginning index of the warning
	 * @param posEnd
	 *            is the ending index of the warning
	 */
	public void addWarning(String message, int line, int posBegin, int posEnd) {
		list.add(new AcceleoParserWarning(message, line, posBegin, posEnd));
	}

	/**
	 * gets the list of warnings.
	 * 
	 * @return the list of warnings
	 */
	public List<AcceleoParserWarning> getList() {
		// We copy the warnings list to prevent concurrent thread access...
		return new ArrayList<AcceleoParserWarning>(list);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.parser.AcceleoParserMessages#clear()
	 */
	public void clear() {
		list.clear();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.parser.AcceleoParserMessages#getMessage()
	 */
	public String getMessage() {
		StringBuffer result = new StringBuffer();
		for (Iterator<AcceleoParserWarning> warningsIt = list.iterator(); warningsIt.hasNext();) {
			result.append(warningsIt.next().getMessage());
			if (warningsIt.hasNext()) {
				result.append("\n"); //$NON-NLS-1$
			}
		}
		return result.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getMessage();
	}
}
