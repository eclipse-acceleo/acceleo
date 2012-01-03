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
import org.eclipse.acceleo.compatibility.model.mt.expressions.Call;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Expression;
import org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsFactory;
import org.eclipse.acceleo.internal.compatibility.AcceleoCompatibilityMessages;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateConstants;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TextSearch;

/**
 * The utility class to parse a new call.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class CallParser {

	/**
	 * No public access to the constructor.
	 */
	private CallParser() {
		// nothing to do here
	}

	/**
	 * Create a new call in the given region.
	 * 
	 * @param offset
	 *            is the current offset of the whole buffer, it is often 0
	 * @param buffer
	 *            is the buffer
	 * @param range
	 *            is the region of the buffer that contains the call to create
	 * @param template
	 *            is the current template where to put the new call
	 * @return the new call
	 * @throws TemplateSyntaxException
	 *             when a syntax issue occurs
	 */
	public static Call createCall(int offset, String buffer, Region range, Template template)
			throws TemplateSyntaxException {
		Region localRange = range;
		Region trim = TextSearch.trim(buffer, localRange.b(), localRange.e());
		if (trim.b() == -1) {
			throw new TemplateSyntaxException(AcceleoCompatibilityMessages
					.getString("TemplateSyntaxError.MissingElement"), template, localRange); //$NON-NLS-1$
		}

		localRange = trim;
		Expression filter = null;
		Region begin = TextSearch.indexIn(buffer, TemplateConstants.getDefault().getBrackets()[0],
				localRange, TemplateConstants.getDefault().getSpec(), new String[][] {
						TemplateConstants.getDefault().getLiteral(),
						TemplateConstants.getDefault().getParenth(), });
		if (begin.b() > -1) {
			Region end = TextSearch.blockIndexEndIn(buffer, TemplateConstants.getDefault().getBrackets()[0],
					TemplateConstants.getDefault().getBrackets()[1], new Region(begin.b(), localRange.e()),
					false, TemplateConstants.getDefault().getSpec(), TemplateConstants.getDefault()
							.getInhibsExpression());
			if (end.b() == -1) {
				throw new TemplateSyntaxException(AcceleoCompatibilityMessages
						.getString("TemplateSyntaxError.MissingCloseBracket"), template, begin.b()); //$NON-NLS-1$
			}
			if (buffer.substring(end.e(), localRange.e()).trim().length() > 0) {
				throw new TemplateSyntaxException(AcceleoCompatibilityMessages
						.getString("TemplateSyntaxError.InvalidSequence"), template, end.e()); //$NON-NLS-1$
			}
			Region pos = new Region(begin.e(), end.b());
			filter = ExpressionParser.createExpression(offset, buffer, pos, template);
			filter.setBegin(offset + pos.b());
			filter.setEnd(offset + pos.e());
			localRange = new Region(localRange.b(), begin.b());
			trim = TextSearch.trim(buffer, localRange.b(), localRange.e());
			if (trim.b() == -1) {
				throw new TemplateSyntaxException(AcceleoCompatibilityMessages
						.getString("TemplateSyntaxError.MissingElement"), template, localRange); //$NON-NLS-1$
			}
			localRange = trim;
		}
		Call call;
		begin = TextSearch.indexIn(buffer, TemplateConstants.getDefault().getParenth()[0], localRange,
				TemplateConstants.getDefault().getSpec(), new String[][] {TemplateConstants.getDefault()
						.getLiteral(), });
		if (begin.b() > -1) {
			Region end = TextSearch.blockIndexEndIn(buffer, TemplateConstants.getDefault().getParenth()[0],
					TemplateConstants.getDefault().getParenth()[1], new Region(begin.b(), localRange.e()),
					false, TemplateConstants.getDefault().getSpec(), TemplateConstants.getDefault()
							.getInhibsExpression());
			if (end.b() == -1) {
				throw new TemplateSyntaxException(AcceleoCompatibilityMessages
						.getString("TemplateSyntaxError.MissingCloseParenthesis"), template, begin.b()); //$NON-NLS-1$
			}
			if (buffer.substring(end.e(), localRange.e()).trim().length() > 0) {
				throw new TemplateSyntaxException(AcceleoCompatibilityMessages
						.getString("TemplateSyntaxError.InvalidSequence"), template, end.e()); //$NON-NLS-1$
			}
			Region[] positions = TextSearch.splitPositionsIn(buffer, new Region(begin.e(), end.b()),
					new String[] {TemplateConstants.getDefault().getArgSep() }, false, TemplateConstants
							.getDefault().getSpec(), TemplateConstants.getDefault().getInhibsExpression());
			call = newCall(buffer.substring(localRange.b(), begin.b()));
			call.setBegin(offset + localRange.b());
			call.setEnd(offset + localRange.e());
			for (int i = 0; i < positions.length; i++) {
				Region pos = positions[i];
				call.getArguments().add(ExpressionParser.createExpression(offset, buffer, pos, template));
			}
		} else {
			call = newCall(buffer.substring(localRange.b(), localRange.e()));
			call.setBegin(offset + localRange.b());
			call.setEnd(offset + localRange.e());
		}
		call.setBegin(offset + localRange.b());
		call.setEnd(offset + localRange.e());
		if (filter != null) {
			call.setFilter(filter);
		}
		return call;
	}

	/**
	 * Create the call for the given named link.
	 * 
	 * @param link
	 *            the link string representing the call
	 * @return the corresponding Call
	 */
	private static Call newCall(String link) {
		Call call = ExpressionsFactory.eINSTANCE.createCall();
		if (link != null) {
			int iDot = link.indexOf(TemplateConstants.getDefault().getLinkPrefixSeparator());
			if (iDot > -1) {
				call.setPrefix(link.substring(0, iDot).trim());
				call.setName(link.substring(
						iDot + TemplateConstants.getDefault().getLinkPrefixSeparator().length()).trim());
			} else {
				call.setPrefix(""); //$NON-NLS-1$
				call.setName(link.trim());
			}
		} else {
			call.setPrefix(""); //$NON-NLS-1$
			call.setName(""); //$NON-NLS-1$
		}
		return call;
	}
}
