/*******************************************************************************
 * Copyright (c) 2016, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.migration.parser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.CommentBody;
import org.eclipse.acceleo.Documentation;
import org.eclipse.acceleo.Error;
import org.eclipse.acceleo.ErrorComment;
import org.eclipse.acceleo.ErrorModuleDocumentation;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleDocumentation;

/**
 * Acceleo parser.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class DocumentationParser {

	/**
	 * New line.
	 */
	public static final String NEW_LINE = "\n";

	/**
	 * In line end delimiter.
	 */
	public static final String SLASH_END = "/]";

	/**
	 * Start of {@link Comment}.
	 */
	public static final String COMMENT_START = "[comment ";

	/**
	 * End of {@link Comment}.
	 */
	public static final String COMMENT_END = SLASH_END;

	/**
	 * Start of {@link Module} header.
	 */
	public static final String MODULE_HEADER_START = "[module ";

	/**
	 * Start of {@link Documentation}.
	 */
	public static final String DOCUMENTATION_START = "[**";

	/**
	 * End of {@link Documentation}.
	 */
	public static final String DOCUMENTATION_END = SLASH_END;

	/**
	 * Author tag.
	 */
	public static final String AUTHOR_TAG = "@author ";

	/**
	 * Version tag.
	 */
	public static final String VERSION_TAG = "@version ";

	/**
	 * Since tag.
	 */
	public static final String SINCE_TAG = "@since ";

	/**
	 * Param tag.
	 */
	public static final String PARAM_TAG = "@param ";

	/**
	 * The parser currentPosition.
	 */
	private int currentPosition;

	/**
	 * The source text.
	 */
	private String text;

	/**
	 * The {@link List} of {@link Error}.
	 */
	private List<Error> errors;

	/**
	 * Retrieves the list of comments from the given mtl content.
	 * 
	 * @param mtlContent
	 *            the mtl content
	 * @return the list of comments
	 */
	public List<Comment> parse(String mtlContent) {
		this.currentPosition = 0;
		this.text = mtlContent;
		errors = new ArrayList<Error>();
		return parseCommentsOrModuleDocumentations();
	}

	/**
	 * Parses a {@link List} of {@link Comment} and {@link ModuleDocumentation}.
	 * 
	 * @return the created {@link List} of {@link Comment} and {@link ModuleDocumentation}
	 */
	protected List<Comment> parseCommentsOrModuleDocumentations() {
		final List<Comment> comments = new ArrayList<Comment>();
		Comment comment = parseComment();
		ModuleDocumentation documentation = parseModuleDocumentation();
		while (comment != null || documentation != null) {
			if (comment != null) {
				comments.add(comment);
			}
			if (documentation != null) {
				comments.add(documentation);
			}
			skipSpaces();
			comment = parseComment();
			documentation = parseModuleDocumentation();
		}
		return comments;
	}

	/**
	 * Skips {@link Character#isWhitespace(char) white spaces}.
	 */
	protected void skipSpaces() {
		while (currentPosition < text.length() && Character.isWhitespace(text.charAt(currentPosition))) {
			currentPosition++;
		}
	}

	/**
	 * Parses a {@link Comment}.
	 * 
	 * @return the created {@link Comment} if any recognized, <code>null</code> otherwise
	 */
	protected Comment parseComment() {
		final Comment res;
		if (text.startsWith(COMMENT_START, currentPosition)) {
			final int startOfCommentBody = currentPosition + COMMENT_START.length();
			int endOfCommentBody = text.indexOf(COMMENT_END, startOfCommentBody);
			if (endOfCommentBody < 0) {
				endOfCommentBody = text.length();
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createErrorComment();
				((ErrorComment)res).setMissingEndHeader(endOfCommentBody);
				errors.add((Error)res);
				currentPosition = endOfCommentBody;
			} else {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createComment();
				currentPosition = endOfCommentBody + COMMENT_END.length();
			}

			final CommentBody commentBody = AcceleoPackage.eINSTANCE.getAcceleoFactory().createCommentBody();
			commentBody.setValue(text.substring(startOfCommentBody, endOfCommentBody));

			res.setBody(commentBody);
		} else {
			res = null;
		}

		return res;
	}

	/**
	 * Parses a {@link ModuleDocumentation}.
	 * 
	 * @return the created {@link ModuleDocumentation} if any recognized, <code>null</code> otherwise
	 */
	protected ModuleDocumentation parseModuleDocumentation() {
		final ModuleDocumentation res;

		if (text.startsWith(DOCUMENTATION_START, currentPosition)) {
			currentPosition += DOCUMENTATION_START.length();
			final int startPosition = currentPosition;
			int endPosition = text.indexOf(DOCUMENTATION_END, currentPosition);
			if (endPosition < 0) {
				endPosition = text.length();
				currentPosition = endPosition;
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createErrorModuleDocumentation();
				((ErrorModuleDocumentation)res).setMissingEndHeader(endPosition);
				errors.add((Error)res);
			} else {
				res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createModuleDocumentation();
				currentPosition = endPosition + DOCUMENTATION_END.length();
			}
			final String docString = text.substring(startPosition, endPosition);
			final CommentBody commentBody = AcceleoPackage.eINSTANCE.getAcceleoFactory().createCommentBody();
			commentBody.setValue(docString);
			res.setBody(commentBody);
		} else {
			res = null;
		}

		return res;
	}

	// CHECKSTYLE:ON
}
