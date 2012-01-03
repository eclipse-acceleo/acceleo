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
 * The configuration of a block to parse.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class SequenceBlock implements ISequence {

	/**
	 * The sequence for the beginning of the header.
	 */
	private Sequence beginHeader;

	/**
	 * The sequence for the ending of the header. It doesn't finish the body of the block.
	 */
	private Sequence endHeaderOnly;

	/**
	 * The sequence for the ending of the header. It also finishes the body of the block.
	 */
	private Sequence endHeaderBody;

	/**
	 * Escape sequence for the header.
	 */
	private Sequence specHeader;

	/**
	 * Indicates if the header block can contain itself. For example, parenthesis can contain other
	 * parenthesis.
	 */
	private boolean recursiveHeader;

	/**
	 * The blocks to be ignored when We search the sequence for the ending of the header.
	 */
	private SequenceBlock[] inhibsHeader;

	/**
	 * The sequence for the ending of the body.
	 */
	private Sequence endBody;

	/**
	 * Escape sequence for the body.
	 */
	private Sequence specBody;

	/**
	 * Indicates if the body block can contain itself.
	 * <p>
	 * For example, a 'for' statement can contain another 'for' statement.
	 * <p>
	 * [for ... ] [for ... ] [/for] [/for].
	 */
	private boolean recursiveBody;

	/**
	 * The blocks to be ignored when We search the sequence for the ending of the block.
	 */
	private SequenceBlock[] inhibsBody;

	/**
	 * Constructor.
	 * <p>
	 * It uses only the header settings.
	 * 
	 * @param beginHeader
	 *            the sequence for the beginning of the header
	 * @param endHeaderBody
	 *            the sequence for the ending of the header and the body
	 * @param specHeader
	 *            is the escape sequence for the header
	 * @param recursiveHeader
	 *            indicates if the header block can contain itself
	 * @param inhibsHeader
	 *            the blocks to be ignored when We search the sequence for the ending of the header
	 */
	public SequenceBlock(Sequence beginHeader, Sequence endHeaderBody, Sequence specHeader,
			boolean recursiveHeader, SequenceBlock[] inhibsHeader) {
		this.beginHeader = beginHeader;
		this.endHeaderOnly = null;
		this.endHeaderBody = endHeaderBody;
		this.specHeader = specHeader;
		this.recursiveHeader = recursiveHeader;
		this.inhibsHeader = inhibsHeader;
		this.endBody = null;
		this.specBody = null;
		this.recursiveBody = false;
		this.inhibsBody = null;
	}

	/**
	 * Constructor. It uses the header and the body settings.
	 * 
	 * @param beginHeader
	 *            the sequence for the beginning of the header
	 * @param endHeaderOnly
	 *            the sequence for the ending of the header
	 * @param endHeaderBody
	 *            the sequence for the ending of the header and the body
	 * @param inhibsHeader
	 *            the blocks to be ignored when We search the sequence for the ending of the header
	 * @param endBody
	 *            the sequence for the ending of the body
	 * @param specBody
	 *            is the escape sequence for the body
	 * @param inhibsBody
	 *            the blocks to be ignored when We search the sequence for the ending of the block
	 */
	public SequenceBlock(Sequence beginHeader, Sequence endHeaderOnly, Sequence endHeaderBody,
			SequenceBlock[] inhibsHeader, Sequence endBody, Sequence specBody, SequenceBlock[] inhibsBody) {
		this.beginHeader = beginHeader;
		this.endHeaderOnly = endHeaderOnly;
		this.endHeaderBody = endHeaderBody;
		this.specHeader = null;
		this.recursiveHeader = false;
		this.inhibsHeader = inhibsHeader;
		this.endBody = endBody;
		this.specBody = specBody;
		this.recursiveBody = true;
		this.inhibsBody = inhibsBody;
	}

	/**
	 * Gets the sequence for the ending of the header.
	 * 
	 * @return the sequence for the ending of the header
	 */
	public Sequence getEndHeaderOnly() {
		return endHeaderOnly;
	}

	/**
	 * Gets the sequence for the ending of the header and the body.
	 * 
	 * @return the sequence for the ending of the header and the body
	 */
	public Sequence getEndHeaderBody() {
		return endHeaderBody;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.parser.cst.utils.ISequence#search(java.lang.StringBuffer, int, int)
	 */
	public Region search(final StringBuffer buffer, int posBegin, int posEnd) {
		return searchBeginHeader(buffer, posBegin, posEnd);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.parser.cst.utils.ISequence#search(java.lang.StringBuffer, int, int,
	 *      org.eclipse.acceleo.parser.cst.utils.Sequence,
	 *      org.eclipse.acceleo.parser.cst.utils.SequenceBlock[])
	 */
	public Region search(StringBuffer buffer, int posBegin, int posEnd, Sequence spec, SequenceBlock[] inhibs) {
		if (beginHeader == null) {
			return Region.NOT_FOUND;
		}
		return beginHeader.search(buffer, posBegin, posEnd, spec, inhibs);
	}

	/**
	 * To find the beginning of the header.
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
	public Region searchBeginHeader(final StringBuffer buffer, int posBegin, int posEnd) {
		if (beginHeader == null) {
			return Region.NOT_FOUND;
		}
		return beginHeader.search(buffer, posBegin, posEnd);
	}

	/**
	 * To find the ending of the body. The beginning of the header is given.
	 * 
	 * @param buffer
	 *            is the buffer
	 * @param indexOfBeginHeaderFound
	 *            is the beginning index of the header
	 * @param posEnd
	 *            is the ending index
	 * @return the index of the end of the body, Region.NOT_FOUND if the sequence doesn't exist between
	 *         posBegin and posEnd
	 */
	public Region searchEndBodyAtBeginHeader(final StringBuffer buffer, Region indexOfBeginHeaderFound,
			int posEnd) {
		Region result = Region.NOT_FOUND;
		if (indexOfBeginHeaderFound.b() != -1) {
			Region endHeader = searchEndHeaderAtBeginHeader(buffer, indexOfBeginHeaderFound, posEnd);
			if (endHeader.b() != -1) {
				if (endHeader.getSequence() == endHeaderBody) {
					result = endHeader;
				} else if (endHeader.getSequence() == endHeaderOnly) {
					result = searchEndBodyAtEndHeader(buffer, endHeader, posEnd);
				}
			}
		}
		return result;
	}

	/**
	 * To find the ending of the header. The beginning of the header is given.
	 * 
	 * @param buffer
	 *            is the buffer
	 * @param indexOfBeginHeaderFound
	 *            is the beginning index of the header
	 * @param posEnd
	 *            is the ending index
	 * @return the index of the end of the header, Region.NOT_FOUND if the sequence doesn't exist between
	 *         posBegin and posEnd
	 */
	public Region searchEndHeaderAtBeginHeader(final StringBuffer buffer, Region indexOfBeginHeaderFound,
			int posEnd) {
		Region result;
		if (indexOfBeginHeaderFound.b() == -1) {
			result = Region.NOT_FOUND;
		} else if (endHeaderOnly == null && endHeaderBody == null) {
			result = indexOfBeginHeaderFound;
		} else {
			result = null;
			// Block search
			int nbBeginTagOuvert = 1;
			int pos = indexOfBeginHeaderFound.e();
			int inhibsLength = 0;
			if (inhibsHeader != null) {
				inhibsLength = inhibsHeader.length;
			}
			Region[] positions = Region.createPositions(4 + inhibsLength);
			while (result == null && pos > -1 && pos < posEnd) {
				// Positions for end, begin, and inhibs
				int iPositionMin = getOrCreateIndexOfHeaderNextPosition(positions, buffer, pos, posEnd);
				if (iPositionMin == -1 /* NOT FOUND */) {
					result = Region.NOT_FOUND;
				} else {
					// Get the next element
					if (iPositionMin == 0 /* spec */) {
						pos = positions[iPositionMin].e();
					} else if (!recursiveHeader && iPositionMin == 1 /* endHeaderOnly */) {
						result = positions[iPositionMin];
					} else if (nbBeginTagOuvert == 1 && iPositionMin == 1 /* endHeaderOnly */) {
						result = positions[iPositionMin];
					} else if (iPositionMin == 1 /* endHeaderOnly */) {
						nbBeginTagOuvert--;
						pos = positions[iPositionMin].e();
					} else if (!recursiveHeader && iPositionMin == 2 /* endHeaderBody */) {
						result = positions[iPositionMin];
					} else if (nbBeginTagOuvert == 1 && iPositionMin == 2 /* endHeaderBody */) {
						result = positions[iPositionMin];
					} else if (iPositionMin == 2 /* endHeaderBody */) {
						nbBeginTagOuvert--;
						pos = positions[iPositionMin].e();
					} else if (iPositionMin == 3 /* begin */) {
						nbBeginTagOuvert++;
						pos = positions[iPositionMin].e();
					} else if (iPositionMin >= 4 /* inhibs */) {
						pos = inhibsHeader[iPositionMin - 4].searchEndBodyAtBeginHeader(buffer,
								positions[iPositionMin], posEnd).e();
					}
				}
			}
			if (result == null) {
				result = Region.NOT_FOUND;
			}
		}
		return result;
	}

	/**
	 * Computes the positions of the header elements, and returns the nearest one. The positions are the old
	 * positions of the elements of the header. The returning value is an index in the table 'positions'.
	 * 
	 * @param positions
	 *            are the old positions of each header elements
	 * @param buffer
	 *            is the buffer
	 * @param pos
	 *            is the current offset in the buffer
	 * @param posEnd
	 *            is the ending index
	 * @return an index in the table 'positions' or -1 if there isn't any candidate
	 */
	private int getOrCreateIndexOfHeaderNextPosition(Region[] positions, final StringBuffer buffer, int pos,
			int posEnd) {
		if (positions[0].b() != -1 && specHeader != null && pos > positions[0].b()) {
			positions[0] = specHeader.search(buffer, pos, posEnd); // spec
		}
		if (positions[1].b() != -1 && endHeaderOnly != null && pos > positions[1].b()) {
			positions[1] = endHeaderOnly.search(buffer, pos, posEnd); // end
		}
		if (positions[2].b() != -1 && endHeaderBody != null && pos > positions[2].b()) {
			positions[2] = endHeaderBody.search(buffer, pos, posEnd); // end
		}
		if (positions[3].b() != -1 && beginHeader != null && recursiveHeader && pos > positions[3].b()) {
			positions[3] = beginHeader.search(buffer, pos, posEnd); // begin
		}
		for (int i = 4; i < positions.length; i++) { // inhibs
			if (positions[i].b() != -1 && pos > positions[i].b()) {
				positions[i] = inhibsHeader[i - 4].searchBeginHeader(buffer, pos, posEnd);
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
	 * To find the ending of the body. The ending of the header is given.
	 * 
	 * @param buffer
	 *            is the buffer
	 * @param indexOfEndHeaderFound
	 *            is the ending index of the header
	 * @param posEnd
	 *            is the ending index
	 * @return the index of the end of the body, Region.NOT_FOUND if the sequence doesn't exist between
	 *         posBegin and posEnd
	 */
	public Region searchEndBodyAtEndHeader(final StringBuffer buffer, Region indexOfEndHeaderFound, int posEnd) {
		Region result;
		if (indexOfEndHeaderFound.b() == -1) {
			result = Region.NOT_FOUND;
		} else if (endBody == null) {
			result = indexOfEndHeaderFound;
		} else {
			result = null;
			// Block search
			int nbBeginTagOuvert = 1;
			int pos = indexOfEndHeaderFound.e();
			int inhibsLength = 0;
			if (inhibsBody != null) {
				inhibsLength = inhibsBody.length;
			}
			Region[] positions = Region.createPositions(3 + inhibsLength);
			while (result == null && pos > -1 && pos < posEnd) {
				// Positions for end, begin, and inhibs
				int iPositionMin = getOrCreateIndexOfBodyNextPosition(positions, buffer, pos, posEnd);
				if (iPositionMin == -1 /* NOT FOUND */) {
					result = Region.NOT_FOUND;
				} else {
					// Get the next element
					if (iPositionMin == 0 /* spec */) {
						pos = positions[iPositionMin].e();
					} else if (!recursiveBody && iPositionMin == 1 /* end */) {
						result = positions[iPositionMin];
					} else if (nbBeginTagOuvert == 1 && iPositionMin == 1 /* end */) {
						result = positions[iPositionMin];
					} else if (iPositionMin == 1 /* end */) {
						nbBeginTagOuvert--;
						pos = positions[iPositionMin].e();
					} else if (iPositionMin == 2 /* begin */) {
						nbBeginTagOuvert++;
						pos = positions[iPositionMin].e();
					} else if (iPositionMin >= 3 /* inhibs */) {
						pos = inhibsBody[iPositionMin - 3].searchEndBodyAtBeginHeader(buffer,
								positions[iPositionMin], posEnd).e();
					}
				}
			}
			if (result == null) {
				result = Region.NOT_FOUND;
			}
		}
		return result;
	}

	/**
	 * Computes the positions of the body elements, and returns the nearest one. The positions are the old
	 * positions of the elements of the body. The returning value is an index in the table 'positions'.
	 * 
	 * @param positions
	 *            are the old positions of each body elements
	 * @param buffer
	 *            is the buffer
	 * @param pos
	 *            is the current offset in the buffer
	 * @param posEnd
	 *            is the ending index
	 * @return an index in the table 'positions' or -1 if there isn't any candidate
	 */
	private int getOrCreateIndexOfBodyNextPosition(Region[] positions, final StringBuffer buffer, int pos,
			int posEnd) {
		if (positions[0].b() != -1 && specBody != null && pos > positions[0].b()) {
			positions[0] = specBody.search(buffer, pos, posEnd); // spec
		}
		if (positions[1].b() != -1 && endBody != null && pos > positions[1].b()) {
			positions[1] = endBody.search(buffer, pos, posEnd); // end
		}
		if (positions[2].b() != -1 && beginHeader != null && recursiveBody && pos > positions[2].b()) {
			positions[2] = beginHeader.search(buffer, pos, posEnd); // begin
		}
		for (int i = 3; i < positions.length; i++) { // inhibs
			if (positions[i].b() != -1 && pos > positions[i].b()) {
				positions[i] = inhibsBody[i - 3].searchBeginHeader(buffer, pos, posEnd);
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

}
