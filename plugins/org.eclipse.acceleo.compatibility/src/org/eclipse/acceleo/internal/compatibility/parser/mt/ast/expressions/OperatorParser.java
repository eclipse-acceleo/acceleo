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
import org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsFactory;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Operator;
import org.eclipse.acceleo.internal.compatibility.AcceleoCompatibilityMessages;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateConstants;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TextSearch;

/**
 * The utility class to parse a new operator.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class OperatorParser {

	/**
	 * No public access to the constructor.
	 */
	private OperatorParser() {
		// nothing to do here
	}

	/**
	 * Create a new operator in the given region.
	 * 
	 * @param offset
	 *            is the current offset of the whole buffer, it is often 0
	 * @param buffer
	 *            is the buffer
	 * @param range
	 *            is the region of the buffer that contains the new operator to create
	 * @param template
	 *            is the current template where to put the new object
	 * @return the new operator
	 * @throws TemplateSyntaxException
	 *             if a syntax issue occurs
	 */
	public static Operator createOperator(int offset, String buffer, Region range, Template template)
			throws TemplateSyntaxException {
		Region localRange = range;
		Region trim = TextSearch.trim(buffer, localRange.b(), localRange.e());
		if (trim.b() == -1) {
			throw new TemplateSyntaxException(AcceleoCompatibilityMessages
					.getString("TemplateSyntaxError.MissingElement"), template, localRange); //$NON-NLS-1$
		}
		localRange = trim;

		for (int i = 0; i < TemplateConstants.getDefault().getOperators().length; i++) {
			Region[] positions = TextSearch.splitPositionsIn(buffer, localRange,
					new String[] {TemplateConstants.getDefault().getOperators()[i] }, false,
					TemplateConstants.getDefault().getSpec(), TemplateConstants.getDefault()
							.getInhibsExpression());

			if (validateOperands(buffer, TemplateConstants.getDefault().getOperators()[i], positions)) {
				Operator operator = ExpressionsFactory.eINSTANCE.createOperator();
				operator.setOperator(TemplateConstants.getDefault().getOperators()[i]);
				operator.setBegin(offset + localRange.b());
				operator.setEnd(offset + localRange.e());
				for (int j = 0; j < positions.length; j++) {
					Region pos = positions[j];
					operator.getOperands().add(
							ExpressionParser.createExpression(offset, buffer, pos, template));
				}
				return operator;
			}
		}
		return null;
	}

	/**
	 * Validate the operands.
	 * 
	 * @param buffer
	 *            the current buffer
	 * @param operator
	 *            the current operator
	 * @param operandPos
	 *            positions of operands
	 * @return true if operands can be used false otherwise
	 */
	private static boolean validateOperands(String buffer, String operator, Region[] operandPos) {
		boolean res = operandPos.length > 1;
		for (int i = 0; i < operandPos.length && res; ++i) {
			if (TemplateConstants.getDefault().getOperatorInf().equals(operator)
					|| TemplateConstants.getDefault().getOperatorSup().equals(operator)) {
				// Test if the first operand char is "=" when parsing "<" or ">"
				// operators to avoid "<=" and ">=" conflict
				res = res && !buffer.substring(operandPos[i].b(), operandPos[i].e()).startsWith("="); //$NON-NLS-1$
			}
		}
		return res;
	}
}
