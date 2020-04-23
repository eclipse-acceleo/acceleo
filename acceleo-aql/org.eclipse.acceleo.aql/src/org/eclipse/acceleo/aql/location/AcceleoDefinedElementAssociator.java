/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.location;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.CommentBody;
import org.eclipse.acceleo.util.AcceleoSwitch;

/**
 * An {@link AcceleoSwitch} that provides, for an element from the AST of Acceleo, the element whose
 * definition we want to link to. This element should most of the time be another Acceleo AST element
 * ({@link ASTNode}), but it can also be an AQL element (variable, service which can be a Java class, etc.).
 * 
 * @author Florent Latombe
 */
public class AcceleoDefinedElementAssociator extends AcceleoSwitch<ASTNode> {

	/**
	 * Constructor.
	 */
	public AcceleoDefinedElementAssociator() {
	}

	/**
	 * Default case: when an element of the AST has been selected, we want its definition.
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseASTNode(org.eclipse.acceleo.ASTNode)
	 */
	@Override
	public ASTNode caseASTNode(ASTNode astNode) {
		return astNode;
	}

	/**
	 * When on a comment body, we want to consider the whole container {@link Comment}.
	 *
	 * @see org.eclipse.acceleo.util.AcceleoSwitch#caseCommentBody(org.eclipse.acceleo.CommentBody)
	 */
	@Override
	public ASTNode caseCommentBody(CommentBody commentBody) {
		// Safe cast because the only containment reference towards a {@link CommentBody} is from {@link
		// Comment}.
		return (Comment)commentBody.eContainer();
	}

	// TODO: we may need to implement more cases.
}
