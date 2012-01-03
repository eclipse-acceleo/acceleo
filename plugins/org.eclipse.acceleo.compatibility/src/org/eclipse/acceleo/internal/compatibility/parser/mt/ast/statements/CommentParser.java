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

import org.eclipse.acceleo.compatibility.model.mt.statements.Comment;
import org.eclipse.acceleo.compatibility.model.mt.statements.StatementsFactory;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;

/**
 * The utility class to parse a new comment block.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class CommentParser {

	/**
	 * No public access to the constructor.
	 */
	private CommentParser() {
		// nothing to do here
	}

	/**
	 * Create a new comment in the given region.
	 * 
	 * @param offset
	 *            is the current offset of the whole buffer, it is often 0
	 * @param buffer
	 *            is the buffer
	 * @param range
	 *            is the region of the buffer that contains the new comment to create
	 * @return the new comment
	 * @throws TemplateSyntaxException
	 *             if a syntax issue occurs
	 */
	public static Comment createComment(int offset, String buffer, Region range)
			throws TemplateSyntaxException {
		Comment comment = StatementsFactory.eINSTANCE.createComment();
		comment.setValue(buffer.substring(range.b(), range.e()));
		comment.setBegin(offset + range.b());
		comment.setEnd(offset + range.e());
		return comment;
	}
}
