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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;

/**
 * The configuration of a sequence to parse.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class Sequence implements ISequence {

	/**
	 * The sequence of tokens.
	 */
	private String[] tokens;

	/**
	 * Minimum length of the textual sequence.
	 */
	private int lengthMin;

	/**
	 * Constructor.
	 * 
	 * @param token
	 *            is the single token of the sequence
	 */
	public Sequence(String token) {
		this(new String[] {token });
	}

	/**
	 * Constructor.
	 * 
	 * @param token1
	 *            is the first token of the sequence
	 * @param token2
	 *            is the second token of the sequence
	 */
	public Sequence(String token1, String token2) {
		this(new String[] {token1, token2 });
	}

	/**
	 * Constructor.
	 * 
	 * @param token1
	 *            is the first token of the sequence
	 * @param token2
	 *            is the second token of the sequence
	 * @param token3
	 *            is the third token of the sequence
	 */
	public Sequence(String token1, String token2, String token3) {
		this(new String[] {token1, token2, token3 });
	}

	/**
	 * Constructor.
	 * 
	 * @param token1
	 *            is the first token of the sequence
	 * @param token2
	 *            is the second token of the sequence
	 * @param token3
	 *            is the third token of the sequence
	 * @param token4
	 *            is the forth token of the sequence
	 */
	public Sequence(String token1, String token2, String token3, String token4) {
		this(new String[] {token1, token2, token3, token4 });
	}

	/**
	 * Constructor.
	 * 
	 * @param tokens
	 *            are the tokens of the sequence
	 */
	public Sequence(String[] tokens) {
		this.tokens = tokens;
		this.lengthMin = 0;
		for (int i = 0; i < tokens.length; i++) {
			lengthMin += tokens[i].length();
		}
	}

	/**
	 * To find a string in the buffer.
	 * 
	 * @param buffer
	 *            is the buffer
	 * @param tag
	 *            is the string to search
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @return the index of the string, or -1 if it doesn't exist
	 */
	private int indexOf(StringBuffer buffer, String tag, int posBegin, int posEnd) {
		int index = buffer.substring(posBegin, posEnd).indexOf(tag);
		if ((index + posBegin) > 1 && IAcceleoConstants.LITERAL_ESCAPE.equals(tag)
				&& "\\".equals(buffer.substring(posBegin + index - 1, posBegin + index))) { //$NON-NLS-1$
			// Ensure that "\'" does not count
			index = -1;
		}

		if (index >= 0) {
			return index + posBegin;
		}
		return index;
	}

	/**
	 * Indicates if the tag matches at the given index.
	 * 
	 * @param buffer
	 *            is the buffer
	 * @param tag
	 *            is the tag to match
	 * @param pos
	 *            is the index
	 * @return true if the tag matches at the given index
	 */
	private boolean matches(StringBuffer buffer, String tag, int pos) {
		int len = tag.length();
		int currentPos = pos;
		int index = 0;
		while (--len >= 0) {
			if (buffer.charAt(currentPos++) != tag.charAt(index++)) {
				return false;
			}
		}
		boolean wholeWord = tag.length() > 0 && Character.isJavaIdentifierPart(tag.charAt(0));
		boolean result;
		if (wholeWord) {
			if ((pos == 0 || !Character.isJavaIdentifierPart(buffer.charAt(pos - 1)))
					&& (currentPos >= buffer.length() || !Character.isJavaIdentifierPart(buffer
							.charAt(currentPos)))) {
				result = true;
			} else {
				result = false;
			}
		} else {
			result = true;
		}

		if (result && IAcceleoConstants.LITERAL_ESCAPE.equals(tag) && pos > 1
				&& "\\".equals(buffer.substring(pos - 1, pos))) { //$NON-NLS-1$
			// We have found the string escape token but we have the escape token before.
			result = false;
		}

		return result;
	}

	/**
	 * To find the first offset of this sequence in the given buffer.
	 * 
	 * @param buffer
	 *            is the buffer
	 * @return the index of the first offset, Region.NOT_FOUND if the sequence doesn't exist
	 */
	public Region search(final StringBuffer buffer) {
		return search(buffer, 0, buffer.length());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.parser.cst.utils.ISequence#search(java.lang.StringBuffer, int, int)
	 */
	public Region search(final StringBuffer buffer, int posBegin, int posEnd) {
		if (buffer != null && posBegin >= 0 && tokens != null && tokens.length > 0) {
			int b = indexOf(buffer, tokens[0], posBegin, posEnd);
			while (b > -1) {
				Integer e = matchesEnd(buffer, b, posEnd);
				if (e != null && e.intValue() > -1) {
					return new Region(b, e.intValue(), this);
				}
				b = indexOf(buffer, tokens[0], b + 1, posEnd);
			}
		}
		return Region.NOT_FOUND;
	}

	/**
	 * Indicates if this sequence exists at the given index and returns the ending index.
	 * 
	 * @param buffer
	 *            is the buffer
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @return true if the tag matches at the given index
	 */
	private Integer matchesEnd(final StringBuffer buffer, int posBegin, int posEnd) {
		Integer e;
		if (tokens.length == 1) {
			e = Integer.valueOf(posBegin + tokens[0].length());
		} else {
			e = null;
		}
		int i = posBegin + tokens[0].length();
		for (int j = 1; e == null && j < tokens.length; j++) {
			while (i < posEnd && Character.isSpaceChar(buffer.charAt(i))) {
				i++;
			}
			if (tokens[j].length() > 0 && (i + tokens[j].length() <= posEnd) && matches(buffer, tokens[j], i)) {
				i += tokens[j].length();
				if (j + 1 == tokens.length) {
					e = Integer.valueOf(i);
					break;
				}
			} else {
				break;
			}
		}
		return e;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.parser.cst.utils.ISequence#search(java.lang.StringBuffer, int, int,
	 *      org.eclipse.acceleo.parser.cst.utils.Sequence,
	 *      org.eclipse.acceleo.parser.cst.utils.SequenceBlock[])
	 */
	public Region search(final StringBuffer buffer, int posBegin, int posEnd, Sequence spec,
			SequenceBlock[] inhibs) {
		Region result;
		if (spec == null && inhibs == null) {
			result = search(buffer, posBegin, posEnd);
		} else if (posBegin < 0) {
			result = Region.NOT_FOUND;
		} else {
			result = null;
			int currentPos = posBegin;
			int inhibsLength = 0;
			if (inhibs != null) {
				inhibsLength = inhibs.length;
			}
			Region[] positions = Region.createPositions(2 + inhibsLength);
			while (result == null && currentPos > -1 && currentPos < posEnd) {
				// Positions for tags and inhibs
				int iPositionMin = getOrCreateIndexOfNextPosition(positions, buffer, currentPos, posEnd,
						spec, inhibs);
				if (iPositionMin == -1 /* NOT FOUND */) {
					break;
				}
				// Get the next element
				if (iPositionMin == 0 /* spec */) {
					currentPos = positions[iPositionMin].e();
				} else if (iPositionMin == 1 /* tag */) {
					result = positions[iPositionMin];
				} else if (iPositionMin >= 2 /* inhibs */) {
					// FIXME JMU it could be null...
					assert inhibs != null;
					currentPos = inhibs[iPositionMin - 2].searchEndBodyAtBeginHeader(buffer,
							positions[iPositionMin], posEnd).e();
				}
			}
			if (result == null) {
				result = Region.NOT_FOUND;
			}
		}
		return result;
	}

	/**
	 * Computes the positions of this sequence and of the ignored elements. It returns the nearest one. The
	 * positions are the old positions of the elements. The returning value is an index in the table
	 * 'positions'.
	 * 
	 * @param positions
	 *            are the old positions of each element
	 * @param buffer
	 *            is the buffer
	 * @param pos
	 *            is the current offset in the buffer
	 * @param posEnd
	 *            is the ending index
	 * @param spec
	 *            is a specific sequence to ignore
	 * @param inhibs
	 *            are some blocks to ignore
	 * @return an index in the table 'positions' or -1 if there isn't any candidate
	 */
	private int getOrCreateIndexOfNextPosition(Region[] positions, final StringBuffer buffer, int pos,
			int posEnd, Sequence spec, SequenceBlock[] inhibs) {
		if (positions[0].b() != -1 && spec != null && pos > positions[0].b()) {
			positions[0] = spec.search(buffer, pos, posEnd); // spec
		}
		if (positions[1].b() != -1 && pos > positions[1].b()) {
			positions[1] = search(buffer, pos, posEnd); // tag
		}
		for (int i = 2; i < positions.length; i++) { // inhibs
			if (positions[i].b() != -1 && pos > positions[i].b()) {
				positions[i] = inhibs[i - 2].searchBeginHeader(buffer, pos, posEnd);
			}
		}
		// Get next position
		int positionMin = posEnd;
		int iPositionMin = -1;
		for (int i = 0; i < positions.length; i++) {
			if ((positions[i].b() > -1) && (positions[i].b() < positionMin)) {
				iPositionMin = i;
				positionMin = positions[i].b();
			}
		}
		return iPositionMin;
	}

	/**
	 * This sequence is used to split the buffer between the given positions. It returns the positions of the
	 * different parts. The positions of the separators can be kept.
	 * 
	 * @param buffer
	 *            is the buffer
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param keepSeparator
	 *            indicates if the positions of the separators are kept
	 * @param spec
	 *            is the escape character
	 * @param inhibs
	 *            are the ignored blocks
	 * @return the positions of the different parts
	 */
	public List<Region> split(final StringBuffer buffer, int posBegin, int posEnd, boolean keepSeparator,
			Sequence spec, SequenceBlock[] inhibs) {
		List<Region> result = new ArrayList<Region>();
		if (buffer != null && buffer.length() > 0 && posEnd > 0 && posEnd > posBegin) {
			int currentPos;
			if (posBegin < 0) {
				currentPos = 0;
			} else {
				currentPos = posBegin;
			}
			while (currentPos > -1 && currentPos < posEnd) {
				Region index = search(buffer, currentPos, posEnd, spec, inhibs);
				if (index.b() == -1) {
					if (posEnd > currentPos) {
						result.add(new Region(currentPos, posEnd, this));
					}
					break;
				}

				if (index.b() > currentPos) {
					result.add(new Region(currentPos, index.b(), this));
				}
				if (keepSeparator) {
					result.add(index);
				}
				currentPos = index.e();
			}
		}
		return result;
	}

}
