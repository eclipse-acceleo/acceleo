/*******************************************************************************
 * Copyright (c) 2006, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.unit.core.generation;

/**
 * The Region class is a simple Bean class that store a text, a begin index and a end index.
 * 
 * @author <a href="mailto:hugo.marchadour@obeo.fr">Hugo Marchadour</a>
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class Region {

	/**
	 * The text.
	 */
	private String text;

	/**
	 * The begin index.
	 */
	private int beginIndex;

	/**
	 * The end index.
	 */
	private int endIndex;

	/**
	 * The constructor.
	 * 
	 * @param text
	 *            The text.
	 * @param beginIndex
	 *            The begin index.
	 * @param endIndex
	 *            The end index.
	 */
	public Region(String text, int beginIndex, int endIndex) {
		super();
		this.text = text;
		this.beginIndex = beginIndex;
		this.endIndex = endIndex;
	}

	/**
	 * get the begin index.
	 * 
	 * @return the beginIndex
	 */
	public int getBeginIndex() {
		return beginIndex;
	}

	/**
	 * get the end index.
	 * 
	 * @return the endIndex
	 */
	public int getEndIndex() {
		return endIndex;
	}

	/**
	 * get the text.
	 * 
	 * @return the text
	 */
	public String getText() {
		return text;
	}
}
