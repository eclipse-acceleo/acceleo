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
package org.eclipse.acceleo.internal.compatibility.parser.mt.ast.statements;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.compatibility.model.mt.core.Template;
import org.eclipse.acceleo.compatibility.model.mt.statements.Statement;
import org.eclipse.acceleo.internal.compatibility.AcceleoCompatibilityMessages;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateConstants;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TextSearch;

/**
 * The utility class to parse a new statement.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class StatementParser {

	/** Constant for local unknown positions. */
	private static final int UNKNOWN_POSITION = -2;

	/** Constant for the new line string. */
	private static final String NEW_LINE = "\n"; //$NON-NLS-1$

	/**
	 * No public access to the constructor.
	 */
	private StatementParser() {
		// nothing to do here
	}

	/**
	 * Formats the text of a template in a script.
	 * 
	 * @param buffer
	 *            is the full text of a script
	 * @param range
	 *            delimits the part of the text to parse for this template
	 * @param nbReturn
	 *            is the number of return character to ignore at the end of the template
	 * @return is the new limits that delimits the part of the text to parse for this template
	 */
	public static Region formatTemplate(String buffer, Region range, int nbReturn) {
		Region res = range;

		String text = ""; //$NON-NLS-1$
		int posBegin = -1;
		int posEnd = posBegin - 1;

		if (range.b() == -1 || range.b() > range.e()) {
			res = Region.NOT_FOUND;
		} else {
			text = buffer.substring(range.b(), range.e());
			// Search first valid char
			posBegin = searchFirstValidChar(text);
			// Search last valid char
			if (text.length() > 0) {
				posEnd = searchLastValidChar(text, posBegin, nbReturn);
			}
		}

		if (posEnd >= posBegin) {
			boolean formatWithComment = false;
			String sub = text.substring(posBegin, posEnd);
			if (sub.startsWith(TemplateConstants.getDefault().getCommentBegin())
					&& sub.indexOf(TemplateConstants.getDefault().getCommentEnd()) > -1) {
				posBegin = text.indexOf(TemplateConstants.getDefault().getCommentEnd(), posBegin)
						+ TemplateConstants.getDefault().getCommentEnd().length();
				formatWithComment = true;
				if (posEnd > posBegin) {
					sub = sub.substring(posBegin);
				} else {
					sub = ""; //$NON-NLS-1$
				}
			}
			if (sub.endsWith(TemplateConstants.getDefault().getCommentEnd())
					&& sub.lastIndexOf(TemplateConstants.getDefault().getCommentBegin(), posEnd) > -1) {
				posEnd = text.lastIndexOf(TemplateConstants.getDefault().getCommentBegin(), posEnd);
				formatWithComment = true;
			}
			if (formatWithComment) {
				res = formatTemplate(buffer, new Region(range.b() + posBegin, range.b() + posEnd), nbReturn);
			} else {
				res = new Region(range.b() + posBegin, range.b() + posEnd);
			}
		}

		return res;
	}

	/**
	 * Search the position of the first valid char.
	 * 
	 * @param text
	 *            the text to parse
	 * @return the position of the first valid char
	 */
	private static int searchFirstValidChar(String text) {
		int posBegin = text.indexOf(NEW_LINE);
		if (posBegin == -1 || text.substring(0, posBegin).trim().length() > 0) {
			posBegin = 0;
		} else {
			posBegin++;
		}
		return posBegin;
	}

	/**
	 * Search the position of the last valid char.
	 * 
	 * @param text
	 *            the text to parse
	 * @param posBegin
	 *            the position to start
	 * @param nbReturn
	 *            is the number of return character to ignore at the end of the template
	 * @return the position of the last valid char
	 */
	private static int searchLastValidChar(String text, int posBegin, int nbReturn) {
		int n = 0;
		int endLine = text.length();
		int posEnd = text.length() - 1;
		boolean stop = false;
		while (!stop) {
			char c = text.charAt(posEnd);
			if (nbReturn == 0 && c == '\n') {
				posEnd++;
				stop = true;
			} else if (c == '\n' && n < nbReturn) {
				n++;
				endLine = posEnd;
			} else if (c == '\r') {
				endLine = posEnd;
			} else if (c == ' ' || c == '\t') {
				// nothing to do
			} else {
				// Keep space at end
				posEnd = endLine;
				stop = true;
			}
			if (!stop) {
				if (posEnd > posBegin) {
					posEnd--;
				} else {
					if (nbReturn != 0) {
						posEnd = endLine;
					}
					stop = true;
				}
			}
		}
		return posEnd;
	}

	/**
	 * It checks the syntax and creates a template for the given part of the text. The part of the text to be
	 * parsed is delimited by the given limits.
	 * 
	 * @param offset
	 *            offset to add to the found element position
	 * @param buffer
	 *            is the textual representation of the script that contains templates
	 * @param range
	 *            delimits the part of the text to be parsed for this template
	 * @param template
	 *            is the generator's configuration used to apply the new template to model's objects
	 * @return the new template
	 * @throws TemplateSyntaxException
	 *             if the statements can't be parsed
	 */
	public static List<Statement> createStatement(int offset, String buffer, Region range, Template template)
			throws TemplateSyntaxException {
		List<Statement> block = new ArrayList<Statement>();
		if (buffer != null) {
			int[] pos = new int[4];
			for (int i = 0; i < pos.length; i++) {
				pos[i] = UNKNOWN_POSITION;
			}
			int i = range.b();
			while (i < range.e()) {
				if (pos[0] != -1 && i > pos[0]) {
					pos[0] = TextSearch.indexIn(buffer, TemplateConstants.getDefault().getCommentBegin(),
							new Region(i, range.e())).b();
				}
				if (pos[1] != -1 && i > pos[1]) {
					pos[1] = TextSearch.indexIn(buffer, TemplateConstants.getDefault().getIfBegin(),
							new Region(i, range.e())).b();
				}
				if (pos[2] != -1 && i > pos[2]) {
					pos[2] = TextSearch.indexIn(buffer, TemplateConstants.getDefault().getForBegin(),
							new Region(i, range.e())).b();
				}
				if (pos[3] != -1 && i > pos[3]) {
					pos[3] = TextSearch.indexIn(buffer, TemplateConstants.getDefault().getFeatureBegin(),
							new Region(i, range.e())).b();
				}
				int iTab = indexOfMin(pos);
				if (iTab == 0) {
					i = readStatement(offset, buffer, TemplateConstants.getDefault().getCommentBegin(),
							TemplateConstants.getDefault().getCommentEnd(), new Region(i, range.e()), block,
							template);
				} else if (iTab == 1) {
					i = readStatement(offset, buffer, TemplateConstants.getDefault().getIfBegin(),
							TemplateConstants.getDefault().getIfEnd(), new Region(i, range.e()), block,
							template);
				} else if (iTab == 2) {
					i = readStatement(offset, buffer, TemplateConstants.getDefault().getForBegin(),
							TemplateConstants.getDefault().getForEnd(), new Region(i, range.e()), block,
							template);
				} else if (iTab == 3) {
					i = readStatement(offset, buffer, TemplateConstants.getDefault().getFeatureBegin(),
							TemplateConstants.getDefault().getFeatureEnd(), new Region(i, range.e()), block,
							template);
				} else { // -1
					Region posText = new Region(i, range.e());
					block.add(TextParser.createText(offset, buffer, posText));
					i = range.e();
				}
			}
		}
		return block;
	}

	/**
	 * Gets the index of the minimum integer in the given table.
	 * 
	 * @param pos
	 *            is the table
	 * @return the index of the minimum integer
	 */
	protected static int indexOfMin(int[] pos) {
		int index = -1;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < pos.length; i++) {
			if (pos[i] > -1 && pos[i] < min) {
				index = i;
				min = pos[i];
			}
		}
		return index;
	}

	/**
	 * Reads the statements in the buffer, starting at a specified position, and returns the ending index.
	 * 
	 * @param offset
	 *            offset to add to the found element position
	 * @param buffer
	 *            is the textual representation of the script that contains templates
	 * @param tagBegin
	 *            is the begin tag of the statement
	 * @param tagEnd
	 *            is the end tag of the statement
	 * @param range
	 *            delimits the part of the text to be parsed
	 * @param block
	 *            is the list of statements where to add the parsed statements is the current template
	 * @param template
	 *            is the owning template
	 * @return the ending index, or -1
	 * @throws TemplateSyntaxException
	 *             if the statements can't be parsed
	 */
	protected static int readStatement(int offset, String buffer, String tagBegin, String tagEnd,
			Region range, List<Statement> block, Template template) throws TemplateSyntaxException {
		Region end = Region.NOT_FOUND;
		Region begin = TextSearch.indexIn(buffer, tagBegin, range);
		// ASSERT (begin.b > -1)
		if (tagBegin == TemplateConstants.getDefault().getCommentBegin()) {
			end = TextSearch.blockIndexEndIn(buffer, tagBegin, tagEnd, new Region(begin.b(), range.e()),
					false, null, null);
		} else {
			end = TextSearch.blockIndexEndIn(buffer, tagBegin, tagEnd, new Region(begin.b(), range.e()),
					true, null, TemplateConstants.getDefault().getInhibsStatement());
		}
		if (end.b() > -1) {
			boolean untab = tagBegin != TemplateConstants.getDefault().getFeatureBegin()
					&& isFirstSignificantOfLine(buffer, begin.b())
					&& isLastSignificantOfLine(buffer, end.e());
			if (begin.b() > range.b()) {
				Region posText = new Region(range.b(), getIEndPos(buffer, untab, begin.b(), range.b()));
				block.add(TextParser.createText(offset, buffer, posText));
			}
			if (tagBegin == TemplateConstants.getDefault().getIfBegin()) {
				block.add(IfParser.createIf(offset, buffer, new Region(begin.e(), end.b()), template));
			}
			if (tagBegin == TemplateConstants.getDefault().getForBegin()) {
				block.add(ForParser.createFor(offset, buffer, new Region(begin.e(), end.b()), template));
			}
			if (tagBegin == TemplateConstants.getDefault().getFeatureBegin()) {
				block.add(FeatureParser.createFeature(offset, buffer, new Region(begin.e(), end.b()),
						template));
			}
			if (tagBegin == TemplateConstants.getDefault().getCommentBegin()) {
				block.add(CommentParser.createComment(offset, buffer, new Region(begin.e(), end.b())));
			}
			if (untab) {
				// Delete (\s|\t)*\n after <%}%>
				int iNextLine = TextSearch.indexIn(buffer, NEW_LINE, new Region(end.e(), range.e())).e();
				if (iNextLine == -1) {
					iNextLine = range.e();
				}
				if (buffer.substring(end.e(), iNextLine).trim().length() == 0) {
					return iNextLine;
				}
			}
			return end.e();
		}

		block.add(TextParser.createText(offset, buffer, range));
		throw new TemplateSyntaxException(AcceleoCompatibilityMessages.getString(
				"TemplateSyntaxError.UnclosedTag", new Object[] {tagEnd, tagBegin, }), template, //$NON-NLS-1$
				new Region(begin.b(), range.e()));
	}

	/**
	 * Give the position of the iEndText.
	 * 
	 * @param buffer
	 *            the buffer to parse
	 * @param untab
	 *            should remove tabs or not
	 * @param beginBegin
	 *            begins of the begin
	 * @param rangeBegin
	 *            begins of the range
	 * @return the position of the iEndText
	 */
	private static int getIEndPos(String buffer, boolean untab, int beginBegin, int rangeBegin) {
		int iEndText = beginBegin;
		if (untab) {
			// Delete (\s|\t)* before <%if..., <%for...
			int iPrevLine = TextSearch.lastIndexIn(buffer, NEW_LINE, rangeBegin, beginBegin, null, null).e();
			if (iPrevLine == -1) {
				iPrevLine = rangeBegin;
			}
			if (buffer.substring(iPrevLine, beginBegin).trim().length() == 0) {
				iEndText = iPrevLine;
			}
		}
		return iEndText;
	}

	/**
	 * Is the parsed statement the first significant of the line?
	 * 
	 * @param buffer
	 *            the buffer containing the text representation
	 * @param index
	 *            the current position of the statement
	 * @return if it's the first significant of the line or not
	 */
	private static boolean isFirstSignificantOfLine(String buffer, int index) {
		boolean res = true;
		boolean stop = false;
		int localIndex = index;
		if (localIndex > 0 && localIndex < buffer.length()) {
			localIndex--;
			while (localIndex >= 0 && !stop) {
				char c = buffer.charAt(localIndex);
				if (c == '\n') {
					res = true;
					stop = true;
				} else if (c == ' ' || c == '\t' || c == '\r') {
					localIndex--;
				} else {
					res = false;
					stop = true;
				}
			}
		}
		if (stop) {
			return res;
		}
		return true;
	}

	/**
	 * Is the parsed statement the last significant of the line?
	 * 
	 * @param buffer
	 *            the buffer containing the text representation
	 * @param index
	 *            the current position of the statement
	 * @return if it's the last significant of the line or not
	 */
	private static boolean isLastSignificantOfLine(String buffer, int index) {
		boolean res = true;
		boolean stop = false;
		int localIndex = index;
		if (localIndex >= 0) {
			while (localIndex < buffer.length() && !stop) {
				char c = buffer.charAt(localIndex);
				if (c == '\n') {
					res = true;
					stop = true;
				} else if (c == ' ' || c == '\t' || c == '\r') {
					localIndex++;
				} else {
					res = false;
					stop = true;
				}
			}
		}
		if (stop) {
			return res;
		}
		return true;
	}
}
