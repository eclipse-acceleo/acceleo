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

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * All the syntax infos of the parsing.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class AcceleoParserInfos implements AcceleoParserMessages {

	/**
	 * List of infos.
	 */
	private Set<AcceleoParserInfo> infos;

	/**
	 * Constructor.
	 */
	public AcceleoParserInfos() {
		infos = Sets.newLinkedHashSet();
	}

	/**
	 * The way to add a new info.
	 * 
	 * @param message
	 *            is the message
	 * @param line
	 *            is the line of the info
	 * @param posBegin
	 *            is the beginning index of the info
	 * @param posEnd
	 *            is the ending index of the info
	 */
	public void addInfo(String message, int line, int posBegin, int posEnd) {
		infos.add(new AcceleoParserInfo(message, line, posBegin, posEnd));
	}

	/**
	 * gets the list of infos.
	 * 
	 * @return the list of infos
	 */
	public List<AcceleoParserInfo> getList() {
		// We copy the infos list to prevent concurrent thread access...
		return Lists.newArrayList(infos);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.parser.AcceleoParserMessages#clear()
	 */
	public void clear() {
		infos.clear();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.parser.AcceleoParserMessages#getMessage()
	 */
	public String getMessage() {
		StringBuffer result = new StringBuffer();
		for (Iterator<AcceleoParserInfo> infosIt = infos.iterator(); infosIt.hasNext();) {
			result.append(infosIt.next().getMessage());
			if (infosIt.hasNext()) {
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
