/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.parser;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * All the syntax problems of the parsing.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoParserProblems implements AcceleoParserMessages {

	/**
	 * List of problems.
	 */
	private List<AcceleoParserProblem> list;

	/**
	 * Constructor.
	 */
	public AcceleoParserProblems() {
		list = new ArrayList<AcceleoParserProblem>();
	}

	/**
	 * The way to add a new problem.
	 * 
	 * @param file
	 *            is the file, can be null if the parser has been created with a buffer
	 * @param message
	 *            is the message
	 * @param line
	 *            is the line of the problem
	 * @param posBegin
	 *            is the beginning index of the problem
	 * @param posEnd
	 *            is the ending index of the problem
	 */
	public void addProblem(File file, String message, int line, int posBegin, int posEnd) {
		list.add(new AcceleoParserProblem(message, line, posBegin, posEnd));
	}

	/**
	 * gets the list of problems.
	 * 
	 * @return the list of problems
	 */
	public List<AcceleoParserProblem> getList() {
		// We copy the problems list to prevent concurrent thread access...
		return new ArrayList<AcceleoParserProblem>(list);
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
		for (Iterator<AcceleoParserProblem> problemsIt = list.iterator(); problemsIt.hasNext();) {
			result.append(problemsIt.next().getMessage());
			if (problemsIt.hasNext()) {
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
