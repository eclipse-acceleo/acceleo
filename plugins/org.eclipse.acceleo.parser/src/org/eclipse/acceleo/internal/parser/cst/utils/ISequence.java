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
package org.eclipse.acceleo.internal.parser.cst.utils;

/**
 * A sequence to search in the text.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public interface ISequence {

	/**
	 * To find the first offset of this sequence in the given buffer.
	 * 
	 * @param buffer
	 *            is the buffer
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @return the index of the first offset, Region.NOT_FOUND if the sequence doesn't exist between posBegin
	 *         and posEnd
	 */
	Region search(final StringBuffer buffer, int posBegin, int posEnd);

	/**
	 * To find the first offset of this sequence in the given buffer, ignoring other sequences...
	 * 
	 * @param buffer
	 *            is the buffer
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param spec
	 *            is a specific sequence to ignore
	 * @param inhibs
	 *            are some blocks to ignore
	 * @return the index of the first offset, Region.NOT_FOUND if the sequence doesn't exist between posBegin
	 *         and posEnd
	 */
	Region search(final StringBuffer buffer, int posBegin, int posEnd, Sequence spec, SequenceBlock[] inhibs);

}
