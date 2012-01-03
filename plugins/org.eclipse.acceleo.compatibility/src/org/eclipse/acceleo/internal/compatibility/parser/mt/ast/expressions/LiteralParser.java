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
import org.eclipse.acceleo.compatibility.model.mt.expressions.BooleanLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.DoubleLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsFactory;
import org.eclipse.acceleo.compatibility.model.mt.expressions.IntegerLiteral;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Literal;
import org.eclipse.acceleo.compatibility.model.mt.expressions.StringLiteral;
import org.eclipse.acceleo.internal.compatibility.AcceleoCompatibilityMessages;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Conventions;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateConstants;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TextSearch;

/**
 * The utility class to parse a new literal value.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class LiteralParser {

	/** Boolean literal : true. */
	protected static final String LITERAL_TRUE = TemplateConstants.getDefault().getLiteraltrue();

	/** Boolean literal : false. */
	protected static final String LITERAL_FALSE = TemplateConstants.getDefault().getLiteralfalse();

	/** Language literal : null. */
	protected static final String LITERAL_NULL = TemplateConstants.getDefault().getLiteralnull();

	/**
	 * No public access to the constructor.
	 */
	private LiteralParser() {
		// nothing to do here
	}

	/**
	 * Create a new literal in the given region.
	 * 
	 * @param offset
	 *            is the current offset of the whole buffer, it is often 0
	 * @param buffer
	 *            is the buffer
	 * @param range
	 *            is the region of the buffer that contains the new literal to create
	 * @param template
	 *            is the current template where to put the new object
	 * @return the new literal
	 * @throws TemplateSyntaxException
	 *             if a syntax issue occurs
	 */
	public static Literal createLiteral(int offset, String buffer, Region range, Template template)
			throws TemplateSyntaxException {
		Region localRange = range;
		Region trim = TextSearch.trim(buffer, localRange.b(), localRange.e());
		if (trim.b() == -1) {
			throw new TemplateSyntaxException(AcceleoCompatibilityMessages
					.getString("TemplateSyntaxError.MissingElement"), template, localRange); //$NON-NLS-1$
		}
		localRange = trim;

		Literal expression;
		String text = buffer.substring(localRange.b(), localRange.e());
		if ((text.length() >= (TemplateConstants.getDefault().getLiteral()[0].length() + TemplateConstants
				.getDefault().getLiteral()[1].length()))
				&& (text.startsWith(TemplateConstants.getDefault().getLiteral()[0]))) {
			if (!text.endsWith(TemplateConstants.getDefault().getLiteral()[1])) {
				throw new TemplateSyntaxException(AcceleoCompatibilityMessages
						.getString("TemplateSyntaxError.InvalidLitteral"), template, localRange); //$NON-NLS-1$
			}
			String value = text.substring(TemplateConstants.getDefault().getLiteral()[0].length(), text
					.length()
					- TemplateConstants.getDefault().getLiteral()[1].length());
			expression = ExpressionsFactory.eINSTANCE.createStringLiteral();
			((StringLiteral)expression).setValue(Conventions.unformatString(value));
		} else {
			if (text.equals(LITERAL_TRUE)) {
				expression = ExpressionsFactory.eINSTANCE.createBooleanLiteral();
				((BooleanLiteral)expression).setValue(true);
			} else if (text.equals(LITERAL_FALSE)) {
				expression = ExpressionsFactory.eINSTANCE.createBooleanLiteral();
				((BooleanLiteral)expression).setValue(false);
			} else if (text.equals(LITERAL_NULL)) {
				expression = ExpressionsFactory.eINSTANCE.createNullLiteral();
			} else if (text.indexOf(".") > -1) { //$NON-NLS-1$
				try {
					expression = ExpressionsFactory.eINSTANCE.createDoubleLiteral();
					((DoubleLiteral)expression).setValue(Double.parseDouble(text));
				} catch (NumberFormatException e) {
					expression = null;
				}
			} else {
				try {
					expression = ExpressionsFactory.eINSTANCE.createIntegerLiteral();
					((IntegerLiteral)expression).setValue(Integer.parseInt(text));
				} catch (NumberFormatException e) {
					expression = null;
				}
			}
		}
		if (expression != null) {
			expression.setBegin(offset + localRange.b());
			expression.setEnd(offset + localRange.e());
		}
		return expression;
	}
}
