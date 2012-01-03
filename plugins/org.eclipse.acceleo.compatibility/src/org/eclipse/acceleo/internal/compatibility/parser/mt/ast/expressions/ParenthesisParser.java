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
import org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsFactory;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Parenthesis;
import org.eclipse.acceleo.internal.compatibility.AcceleoCompatibilityMessages;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateConstants;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TextSearch;

/**
 * The utility class to parse a new parenthesis expression.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class ParenthesisParser {

	/**
	 * No public access to the constructor.
	 */
	private ParenthesisParser() {
		// nothing to do here
	}

	/**
	 * Create a new parenthesis expression in the given region.
	 * 
	 * @param offset
	 *            is the current offset of the whole buffer, it is often 0
	 * @param buffer
	 *            is the buffer
	 * @param range
	 *            is the region of the buffer that contains the new parenthesis expression to create
	 * @param template
	 *            is the current template where to put the new object
	 * @return the new parenthesis expression
	 * @throws TemplateSyntaxException
	 *             if a syntax issue occurs
	 */
	public static Parenthesis createParenthesis(int offset, String buffer, Region range, Template template)
			throws TemplateSyntaxException {
		Region localRange = range;
		Region trim = TextSearch.trim(buffer, localRange.b(), localRange.e());
		if (trim.b() == -1) {
			throw new TemplateSyntaxException(AcceleoCompatibilityMessages
					.getString("TemplateSyntaxError.MissingElement"), template, localRange); //$NON-NLS-1$
		}
		localRange = trim;

		Region begin = TextSearch.indexIn(buffer, TemplateConstants.getDefault().getParenth()[0], localRange);
		if (begin.b() == localRange.b()) {
			Region end = TextSearch.blockIndexEndIn(buffer, TemplateConstants.getDefault().getParenth()[0],
					TemplateConstants.getDefault().getParenth()[1], new Region(begin.b(), localRange.e()),
					true, TemplateConstants.getDefault().getSpec(), TemplateConstants.getDefault()
							.getInhibsExpression());
			if (end.e() == localRange.e()) {
				Expression expression = ExpressionParser.createExpression(offset, buffer, new Region(
						localRange.b() + TemplateConstants.getDefault().getParenth()[0].length(), localRange
								.e()
								- TemplateConstants.getDefault().getParenth()[1].length()), template);
				Parenthesis parenthesis = ExpressionsFactory.eINSTANCE.createParenthesis();
				parenthesis.setExpression(expression);
				parenthesis.setBegin(offset + localRange.b());
				parenthesis.setEnd(offset + localRange.e());
				return parenthesis;
			}
		}
		return null;
	}
}
