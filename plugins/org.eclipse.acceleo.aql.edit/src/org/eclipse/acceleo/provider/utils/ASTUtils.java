/*******************************************************************************
 * Copyright (c) 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.provider.utils;

import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.query.parser.AstSerializer;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine.AstResult;

/**
 * A helper to render AST expressions.
 * 
 * @author <a href="mailto:william.piers@obeo.fr">William Piers</a>
 */
public class ASTUtils {

	private static final AstSerializer AST_SERIALIZER = new AstSerializer();

	public static String serialize(AstResult astResult) {
		return AST_SERIALIZER.serialize(astResult.getAst());
	}

	public static String serialize(Expression expression) {
		return AST_SERIALIZER.serialize(expression.getAql());
	}
}
