/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * All the syntax problems of the parsing.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoParserProblems {

	/**
	 * List of problems.
	 */
	private List<AcceleoParserProblem> list;

	/**
	 * Lines of the problems.
	 */
	private Set<Integer> lines;

	/**
	 * Constructor.
	 */
	public AcceleoParserProblems() {
		list = new ArrayList<AcceleoParserProblem>();
		lines = new HashSet<Integer>();
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
		Integer newLine = new Integer(line);
		if (!lines.contains(newLine)) {
			lines.add(newLine);
			list.add(new AcceleoParserProblem(message, line, posBegin, posEnd));
		}
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
	 * Clear the list of problems.
	 */
	public void clear() {
		list.clear();
		lines.clear();
	}

	/**
	 * Computes a buffer with all the messages of the problems.
	 * 
	 * @return the message
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
