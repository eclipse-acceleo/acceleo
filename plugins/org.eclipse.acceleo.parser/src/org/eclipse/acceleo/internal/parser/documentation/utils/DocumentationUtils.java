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
package org.eclipse.acceleo.internal.parser.documentation.utils;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.parser.AcceleoParserInfo;
import org.eclipse.acceleo.parser.AcceleoSourceBuffer;

/**
 * This class contains a collection of utility method to manipulate the documentation.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public final class DocumentationUtils {

	/**
	 * The constructor.
	 */
	private DocumentationUtils() {
		// prevent instantiation
	}

	/**
	 * Look for '@TODO' or '@FIXME' in the comment.
	 * 
	 * @param source
	 *            The source buffer.
	 * @param startPosition
	 *            The start position of the comment
	 * @param endPosition
	 *            The end position of the comment
	 * @param type
	 *            The type of comment
	 */
	public static void parseToDoFixMe(AcceleoSourceBuffer source, int startPosition, int endPosition,
			CommentType type) {
		int startBody = -1;
		int endBody = -1;

		switch (type) {
			case COMMENT_IN_HEADER:
				startBody = startPosition + IAcceleoConstants.DEFAULT_BEGIN.length()
						+ IAcceleoConstants.COMMENT.length();
				endBody = endPosition
						- (IAcceleoConstants.DEFAULT_END_BODY_CHAR.length() + IAcceleoConstants.DEFAULT_END
								.length());
				break;
			case COMMENT_WITH_END_HEADER:
				startBody = startPosition + IAcceleoConstants.DEFAULT_BEGIN.length()
						+ IAcceleoConstants.COMMENT.length() + IAcceleoConstants.DEFAULT_END.length();
				endBody = endPosition
						- (IAcceleoConstants.DEFAULT_BEGIN.length()
								+ IAcceleoConstants.DEFAULT_END_BODY_CHAR.length()
								+ IAcceleoConstants.COMMENT.length() + IAcceleoConstants.DEFAULT_END.length());
				break;
			case DOCUMENTATION:
				startBody = startPosition + IAcceleoConstants.DEFAULT_BEGIN.length()
						+ IAcceleoConstants.DOCUMENTATION_BEGIN.length();
				endBody = endPosition
						- (IAcceleoConstants.DEFAULT_END_BODY_CHAR.length() + IAcceleoConstants.DEFAULT_END
								.length());
				break;
			default:
				break;
		}
		if (startBody != -1 && endBody != -1 && startBody < endBody) {
			checkKeyword(source.getBuffer().substring(startBody, endBody), IAcceleoConstants.TAG_TODO,
					source, startBody, endBody);
			checkKeyword(source.getBuffer().substring(startBody, endBody), IAcceleoConstants.TAG_FIXME,
					source, startBody, endBody);
		}
	}

	/**
	 * Check for @TODO and @FIXME in the body of the comment and log a new task if a @TODO or @FIXME is found.
	 * 
	 * @param body
	 *            The body of the comment
	 * @param keyword
	 *            The keyword @TODO or @FIXME
	 * @param source
	 *            The source buffer
	 * @param bodyStartIndex
	 *            The starting index of the body of the comment
	 * @param bodyStopIndex
	 *            The ending index of the body of the comment
	 */
	public static void checkKeyword(final String body, final String keyword,
			final AcceleoSourceBuffer source, int bodyStartIndex, int bodyStopIndex) {
		int index = 0;
		while (index != -1 && index != body.length()) {
			index = body.indexOf(keyword, index + 1);
			if (index != -1) {
				int eol = body.indexOf(System.getProperty("line.separator"), index); //$NON-NLS-1$
				String message = ""; //$NON-NLS-1$
				int end = 0;

				int begin = bodyStartIndex + index;

				if (eol == -1) {
					eol = bodyStopIndex;
					message = body.substring(index);
					end = eol;
				} else {
					message = body.substring(index, eol);
					end = begin + (eol - index);
				}

				if (IAcceleoConstants.TAG_TODO.equals(keyword)) {
					source.logInfo(AcceleoParserInfo.TODO_COMMENT + message, begin, end);
				} else if (IAcceleoConstants.TAG_FIXME.equals(keyword)) {
					source.logInfo(AcceleoParserInfo.FIXME_COMMENT + message, begin, end);
				}
			}
		}
	}

	/**
	 * The type of comment.
	 * 
	 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
	 */
	public enum CommentType {
		/**
		 * [comment .... /].
		 */
		COMMENT_IN_HEADER,
		/**
		 * [comment] .... [/comment].
		 */
		COMMENT_WITH_END_HEADER,
		/**
		 * [**.../].
		 */
		DOCUMENTATION,
	}
}
