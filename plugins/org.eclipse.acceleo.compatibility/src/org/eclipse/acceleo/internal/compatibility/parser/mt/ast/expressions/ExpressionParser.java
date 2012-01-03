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
package org.eclipse.acceleo.internal.compatibility.parser.mt.ast.expressions;

import org.eclipse.acceleo.compatibility.model.mt.core.Template;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Expression;
import org.eclipse.acceleo.internal.compatibility.AcceleoCompatibilityMessages;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TextSearch;

/**
 * The utility class to parse an Acceleo expression.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class ExpressionParser {

	/**
	 * No public access to the constructor.
	 */
	private ExpressionParser() {
		// nothing to do here
	}

	/**
	 * Create a new expression in the given region.
	 * 
	 * @param offset
	 *            is the current offset of the whole buffer, it is often 0
	 * @param buffer
	 *            is the buffer
	 * @param range
	 *            is the region of the buffer that contains the new expression to create
	 * @param template
	 *            is the current template where to put the new object
	 * @return the new expression
	 * @throws TemplateSyntaxException
	 *             if a syntax issue occurs
	 */
	public static Expression createExpression(int offset, String buffer, Region range, Template template)
			throws TemplateSyntaxException {
		Expression res = null;
		Region localRange = range;
		Region trim = TextSearch.trim(buffer, localRange.b(), localRange.e());
		if (trim.b() == -1) {
			throw new TemplateSyntaxException(AcceleoCompatibilityMessages
					.getString("TemplateSyntaxError.MissingElement"), template, localRange); //$NON-NLS-1$
		}
		localRange = trim;

		Expression expression = OperatorParser.createOperator(offset, buffer, localRange, template);
		if (expression != null) {
			res = expression;
		}
		if (expression == null) {
			expression = NotParser.createNot(offset, buffer, localRange, template);
			if (expression != null) {
				res = expression;
			}
		}
		if (expression == null) {
			expression = ParenthesisParser.createParenthesis(offset, buffer, localRange, template);
			if (expression != null) {
				res = expression;
			}
		}
		if (expression == null) {
			expression = LiteralParser.createLiteral(offset, buffer, localRange, template);
			if (expression != null) {
				res = expression;
			}
		}
		if (expression == null) {
			expression = CallSetParser.createCallSet(offset, buffer, localRange, template);
			res = expression;
		}
		return res;
	}
}
