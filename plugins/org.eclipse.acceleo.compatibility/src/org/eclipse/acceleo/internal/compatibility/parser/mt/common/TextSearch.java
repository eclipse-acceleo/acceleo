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
package org.eclipse.acceleo.internal.compatibility.parser.mt.common;

import java.util.LinkedList;
import java.util.List;

/**
 * Java language supports pattern matching. This class includes methods for examining sequences of characters,
 * for searching strings with or without the regular expression library. The public static attribute
 * 'settings' specifies the pattern matching configuration for all the methods of this class : <li>regular
 * expression</li> <li>ignore case</li> <li>escape characters</li> <li>recursive blocks</li>
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class TextSearch {

	/**
	 * This property is used to disable the recursive block search.
	 */
	public static final String FORCE_NOT_RECURSIVE = "__FORCE_NOT_RECURSIVE__"; //$NON-NLS-1$

	/**
	 * No public access to the constructor.
	 */
	private TextSearch() {
		// nothing to do here
	}

	/**
	 * To find a string in the buffer.
	 * 
	 * @param buffer
	 *            is the buffer
	 * @param tag
	 *            is the string to search
	 * @param region
	 *            is the beginning and the ending indexes
	 * @param spec
	 *            is a specific sequence to ignore
	 * @param inhibs
	 *            are some blocks to ignore
	 * @return the index of the string, or -1 if it doesn't exist
	 */
	public static Region indexIn(final String buffer, final String tag, Region region, String spec,
			String[][] inhibs) {
		Region toReturn;
		if (buffer == null || region.b() < 0 || region.e() <= region.b() || region.e() > buffer.length()) {
			toReturn = Region.NOT_FOUND;
		} else if (spec == null && inhibs == null) {
			toReturn = indexIn(buffer, tag, region);
		} else if (tag == null) {
			toReturn = Region.NOT_FOUND;
		} else {
			int inhibsSize = 0;
			if (inhibs != null) {
				inhibsSize = inhibs.length;
			}
			final Region notInitialized = new Region(-2, -2);
			Region[] positions = new Region[3 + inhibsSize];
			for (int i = 0; i < positions.length; i++) {
				positions[i] = notInitialized;
			}
			toReturn = null;
			int pos = region.b();
			while (toReturn == null && pos > -1 && pos < region.e()) {
				// Positions for tags and inhibs
				if (positions[0].b() != -1 && spec != null && pos > positions[0].b()) {
					positions[0] = indexIn(buffer, spec, new Region(pos, region.e())); // spec
				}
				if (positions[1].b() != -1 && pos > positions[1].b()) {
					positions[1] = indexIn(buffer, tag, new Region(pos, region.e())); // tag
				}
				for (int i = 3; i < positions.length; i++) { // inhibsTag
					if (positions[i].b() != -1 && pos > positions[i].b()) {
						assert inhibs != null;
						positions[i] = indexIn(buffer, inhibs[i - 3][0], new Region(pos, region.e()));
					}
				}
				// Get next position
				int positionMin = region.e();
				int iPositionMin = -1;
				for (int i = 0; i < positions.length; i++) {
					if ((positions[i].b() > -1) && (positions[i].b() < positionMin)) {
						iPositionMin = i;
						positionMin = positions[i].b();
					}
				}
				if (iPositionMin == -1 /* not FOUND */) {
					toReturn = Region.NOT_FOUND;
				} else if (iPositionMin == 0 /* spec */) {
					pos = positions[iPositionMin].e();
				} else if (iPositionMin == 1 /* tag */) {
					toReturn = positions[iPositionMin];
				} else if (iPositionMin >= 3 /* inhibsTag */) {
					boolean forceNotRecursive;
					assert inhibs != null;
					if (inhibs[iPositionMin - 3].length >= 3 && inhibs[iPositionMin - 3][2] != null) {
						forceNotRecursive = inhibs[iPositionMin - 3][2].indexOf(FORCE_NOT_RECURSIVE) > -1;
					} else {
						forceNotRecursive = false;
					}
					if (!forceNotRecursive) {
						pos = blockIndexEndIn(buffer, inhibs[iPositionMin - 3][0],
								inhibs[iPositionMin - 3][1],
								new Region(positions[iPositionMin].b(), region.e()), true, spec, inhibs).e();
					} else {
						pos = blockIndexEndIn(buffer, inhibs[iPositionMin - 3][0],
								inhibs[iPositionMin - 3][1],
								new Region(positions[iPositionMin].b(), region.e()), false, spec, null).e();
					}
				}
			}
			if (toReturn == null) {
				toReturn = Region.NOT_FOUND;
			}
		}
		return toReturn;
	}

	/**
	 * To find a string in the buffer.
	 * 
	 * @param buffer
	 *            is the buffer
	 * @param tag
	 *            is the string to search
	 * @param region
	 *            is the beginning and the ending index
	 * @return the index of the string, or -1 if it doesn't exist
	 */
	public static Region indexIn(final String buffer, final String tag, Region region) {
		Region toReturn;
		if (buffer == null) {
			toReturn = Region.NOT_FOUND;
		} else if (region.b() < 0 || region.e() <= 0 || region.e() <= region.b()
				|| region.e() > buffer.length()) {
			toReturn = Region.NOT_FOUND;
		} else if (tag == null || tag.length() == 0) {
			toReturn = Region.NOT_FOUND;
		} else {
			String substring = buffer.substring(region.b(), region.e());
			int i = substring.indexOf(tag);
			if (i > -1) {
				int b = region.b() + i;
				toReturn = new Region(b, b + tag.length());
			} else {
				toReturn = Region.NOT_FOUND;
			}
		}
		return toReturn;
	}

	// CHECKSTYLE:OFF

	/**
	 * To find the ending of the block. The beginning of the block is given.
	 * 
	 * @param buffer
	 *            is the buffer
	 * @param beginTag
	 *            is the beginning string to search
	 * @param endTag
	 *            is the ending string to search
	 * @param region
	 *            is the beginning and the ending indexes, it means the beginning and the ending indexes of
	 *            the block
	 * @param recursive
	 *            indicates if the inner blocks are recursively ignored
	 * @param spec
	 *            is a specific sequence to ignore
	 * @param inhibs
	 *            are some blocks to ignore
	 * @return the index of the end of the block, Region.NOT_FOUND if the block doesn't exist between posBegin
	 *         and posEnd
	 */
	public static Region blockIndexEndIn(final String buffer, String beginTag, String endTag, Region region,
			boolean recursive, String spec, String[][] inhibs) {
		if (buffer == null || region.b() < 0 || region.e() <= region.b() || region.e() > buffer.length()) {
			return Region.NOT_FOUND;
		} else if (beginTag == null || endTag == null) {
			return Region.NOT_FOUND;
		}
		Region posBeginInt2 = indexIn(buffer, beginTag, region);
		if (posBeginInt2.b() != region.b() && beginTag.length() > 0) {
			return Region.NOT_FOUND;
		} else if (endTag.length() == 0) {
			return new Region(region.e(), region.e());
		}
		// Block search
		int nbBeginTagOuvert = 1;
		int pos = posBeginInt2.e();
		int inhibsSize = 0;
		if (inhibs != null) {
			inhibsSize = inhibs.length;
		}
		final Region notInitialized = new Region(-2, -2);
		Region[] positions = new Region[4 + inhibsSize];
		for (int i = 0; i < positions.length; i++) {
			positions[i] = notInitialized;
		}
		while (pos > -1 && pos < region.e()) {
			// Positions for end, begin, and inhibs
			if (positions[0].b() != -1 && spec != null && pos > positions[0].b()) {
				positions[0] = indexIn(buffer, spec, new Region(pos, region.e())); // spec
			}
			if (positions[1].b() != -1 && pos > positions[1].b()) {
				positions[1] = indexIn(buffer, endTag, new Region(pos, region.e())); // endTag
			}
			if (positions[2].b() != -1 && recursive && beginTag.length() > 0 && pos > positions[2].b()) {
				positions[2] = indexIn(buffer, beginTag, new Region(pos, region.e())); // beginTag
			}
			for (int i = 4; i < positions.length; i++) { // inhibsTag
				if (positions[i].b() != -1 && pos > positions[i].b()) {
					assert inhibs != null;
					positions[i] = indexIn(buffer, inhibs[i - 4][0], new Region(pos, region.e()));
				}
			}
			// Get next position
			int positionMin = region.e();
			int iPositionMin = -1;
			for (int i = 0; i < positions.length; i++) {
				if ((positions[i].b() > -1) && (positions[i].b() < positionMin)) {
					iPositionMin = i;
					positionMin = positions[i].b();
				}
			}
			if (iPositionMin == -1 /* not FOUND */) {
				return Region.NOT_FOUND;
			} else if (iPositionMin == 0 /* spec */) {
				pos = positions[iPositionMin].e();
			} else if (iPositionMin == 1 /* endTag */) {
				nbBeginTagOuvert--;
				pos = positions[iPositionMin].e();
				if (!recursive || beginTag.length() == 0) {
					return positions[iPositionMin];
				}
			} else if (iPositionMin == 2 /* beginTag */) {
				nbBeginTagOuvert++;
				pos = positions[iPositionMin].e();
			} else if (iPositionMin >= 4 /* inhibsTag */) {
				boolean forceNotRecursive;
				assert inhibs != null;
				if (inhibs[iPositionMin - 4].length >= 3 && inhibs[iPositionMin - 4][2] != null) {
					forceNotRecursive = inhibs[iPositionMin - 4][2].indexOf(FORCE_NOT_RECURSIVE) > -1;
				} else {
					forceNotRecursive = false;
				}
				if (!forceNotRecursive) {
					pos = blockIndexEndIn(buffer, inhibs[iPositionMin - 4][0], inhibs[iPositionMin - 4][1],
							new Region(positions[iPositionMin].b(), region.e()), true, spec, inhibs).e();
				} else {
					pos = blockIndexEndIn(buffer, inhibs[iPositionMin - 4][0], inhibs[iPositionMin - 4][1],
							new Region(positions[iPositionMin].b(), region.e()), false, spec, null).e();
				}
			}
			if (nbBeginTagOuvert == 0) {
				return positions[iPositionMin];
			}
		}
		return Region.NOT_FOUND;
	}

	// CHECKSTYLE:ON

	/**
	 * To find the last index of the string in the buffer.
	 * 
	 * @param buffer
	 *            is the buffer
	 * @param tag
	 *            is the string to search
	 * @param posBegin
	 *            is the beginning index
	 * @param posEnd
	 *            is the ending index
	 * @param spec
	 *            is a specific sequence to ignore
	 * @param inhibs
	 *            are some blocks to ignore
	 * @return the last index of the string, or -1 if it doesn't exist
	 */
	public static Region lastIndexIn(final String buffer, final String tag, int posBegin, int posEnd,
			String spec, String[][] inhibs) {
		if (buffer == null || posBegin < 0 || posEnd <= posBegin || posEnd > buffer.length()) {
			return Region.NOT_FOUND;
		}
		Region i = indexIn(buffer, tag, new Region(posBegin, posEnd), spec, inhibs);
		Region lastI = i;
		while (i.b() > -1) {
			i = indexIn(buffer, tag, new Region(i.e(), posEnd), spec, inhibs);
			if (i.b() > -1) {
				lastI = i;
			}
		}
		return lastI;
	}

	/**
	 * Splits this buffer around matches of the given separators. It returns a list of the regions
	 * corresponding to the separated parts of the text.
	 * 
	 * @param buffer
	 *            is the buffer to split
	 * @param region
	 *            is the working region
	 * @param separators
	 *            are the separators to consider
	 * @param keepSeparators
	 *            indicates if we keep the separators as new regions
	 * @param spec
	 *            is a specific sequence to ignore
	 * @param inhibs
	 *            are some blocks to ignore
	 * @return the regions corresponding to the separated parts of the text, it means a list with a single
	 *         element (which is the given region) if there isn't any separator in the buffer
	 */
	public static Region[] splitPositionsIn(final String buffer, Region region, String[] separators,
			boolean keepSeparators, String spec, String[][] inhibs) {
		List<Region> list = splitPositions(buffer, region, separators, keepSeparators, spec, inhibs);
		return list.toArray(new Region[list.size()]);
	}

	/**
	 * Splits this buffer around matches of the given separators. It returns an array of the regions
	 * corresponding to the separated parts of the text.
	 * 
	 * @param buffer
	 *            is the buffer to split
	 * @param region
	 *            is the working region
	 * @param separators
	 *            are the separators to consider
	 * @param keepSeparators
	 *            indicates if we keep the separators as new regions
	 * @param spec
	 *            is a specific sequence to ignore
	 * @param inhibs
	 *            are some blocks to ignore
	 * @return an array of the regions corresponding to the separated parts of the text, it means a list with
	 *         a single element (which is the given region) if there isn't any separator in the buffer
	 */
	private static List<Region> splitPositions(final String buffer, Region region, String[] separators,
			boolean keepSeparators, String spec, String[][] inhibs) {
		if (buffer.length() == 0 || region.b() < 0 || region.e() <= region.b()
				|| region.e() > buffer.length()) {
			return new LinkedList<Region>();
		}
		List<Region> result = null;
		for (int i = 0; result == null && i < separators.length; i++) {
			Region index = indexIn(buffer, separators[i], region, spec, inhibs);
			if (keepSeparators) {
				if (index.b() == region.b()) {
					result = splitPositions(buffer, new Region(index.e(), region.e()), separators,
							keepSeparators, spec, inhibs);
					result.add(new Region(index.b(), index.e()));
				} else if (index.e() == buffer.length()) {
					result = splitPositions(buffer, new Region(region.b(), index.b()), separators,
							keepSeparators, spec, inhibs);
					result.add(new Region(index.b(), index.e()));
				} else if (index.b() > -1) {
					result = splitPositions(buffer, new Region(region.b(), index.b()), separators,
							keepSeparators, spec, inhibs);
					result.add(new Region(index.b(), index.e()));
					result.addAll(splitPositions(buffer, new Region(index.e(), region.e()), separators,
							keepSeparators, spec, inhibs));
				}
			} else {
				if (index.b() == 0) {
					result = splitPositions(buffer, new Region(index.e(), region.e()), separators,
							keepSeparators, spec, inhibs);
				} else if (index.e() == buffer.length()) {
					result = splitPositions(buffer, new Region(region.b(), index.b()), separators,
							keepSeparators, spec, inhibs);
				} else if (index.b() > -1) {
					result = splitPositions(buffer, new Region(region.b(), index.b()), separators,
							keepSeparators, spec, inhibs);
					result.addAll(splitPositions(buffer, new Region(index.e(), region.e()), separators,
							keepSeparators, spec, inhibs));

				}
			}
		}
		if (result == null) {
			result = new LinkedList<Region>();
			result.add(region);
		}
		return result;
	}

	/**
	 * It may be used to trim whitespace from the beginning and end of a string.
	 * 
	 * @param buffer
	 *            is the buffer that contains the text to trim
	 * @param posBegin
	 *            is the beginning index of the text to trim
	 * @param posEnd
	 *            is the ending index of the text to trim
	 * @return the text region with leading and trailing white space ignored, or the given region (posBegin,
	 *         posEnd) if it has no leading or trailing white space
	 */
	public static Region trim(final String buffer, int posBegin, int posEnd) {
		if (posBegin >= 0 && posBegin < buffer.length() && posBegin <= posEnd && posEnd <= buffer.length()) {
			int begin = posBegin;
			int end = posEnd;
			while (begin < end) {
				char c = buffer.charAt(begin);
				if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
					begin++;
				} else {
					break;
				}
			}
			while (end > begin) {
				char c = buffer.charAt(end - 1);
				if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
					end--;
				} else {
					break;
				}
			}
			if (begin < end) {
				return new Region(begin, end);
			}
		}
		return Region.NOT_FOUND;
	}

}
