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
package org.eclipse.acceleo.internal.parser.ast.ocl;

import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.cst.CSTNode;
import org.eclipse.ocl.cst.OperationCallExpCS;

/**
 * Exception indicating a failure to parse or validate an OCL expression. We wrap this OCL expression because
 * there was missing information in the basic exception.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class WrappedOCLException extends ParserException {

	/**
	 * The serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The basic OCL exception.
	 */
	private final ParserException parserException;

	/**
	 * The beginning index of the issue.
	 */
	private final int startPosition;

	/**
	 * The ending index of the issue.
	 */
	private final int endPosition;

	/**
	 * Constructor.
	 * 
	 * @param parserException
	 *            the basic OCL exception
	 * @param problemObject
	 *            is the OCL problem object, it means the cause object
	 */
	public WrappedOCLException(ParserException parserException, Object problemObject) {
		super(parserException.getMessage());
		this.parserException = parserException;
		if (problemObject instanceof OperationCallExpCS) {
			OperationCallExpCS node = (OperationCallExpCS)problemObject;
			startPosition = node.getSimpleNameCS().getStartOffset() - 5; // OCL issue workaround
			endPosition = node.getSimpleNameCS().getEndOffset() - 4; // OCL issue workaround
		} else if (problemObject instanceof CSTNode) {
			CSTNode node = (CSTNode)problemObject;
			startPosition = node.getStartOffset() - 5; // OCL issue workaround
			endPosition = node.getEndOffset() - 4; // OCL issue workaround
		} else {
			startPosition = -1;
			endPosition = -1;
		}
	}

	/**
	 * Returns the basic OCL exception.
	 * 
	 * @return the basic OCL exception
	 */
	public ParserException getParserException() {
		return parserException;
	}

	/**
	 * Returns the beginning index of the issue.
	 * 
	 * @return the beginning index of the issue
	 */
	public int getStartPosition() {
		return startPosition;
	}

	/**
	 * Returns the ending index of the issue.
	 * 
	 * @return the ending index of the issue
	 */
	public int getEndPosition() {
		return endPosition;
	}

}
