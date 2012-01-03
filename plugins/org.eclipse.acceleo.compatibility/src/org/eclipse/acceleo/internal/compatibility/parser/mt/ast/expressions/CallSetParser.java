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
import org.eclipse.acceleo.compatibility.model.mt.expressions.CallSet;
import org.eclipse.acceleo.compatibility.model.mt.expressions.ExpressionsFactory;
import org.eclipse.acceleo.internal.compatibility.AcceleoCompatibilityMessages;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateConstants;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TextSearch;

/**
 * The utility class to parse a new set of calls.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class CallSetParser {

	/**
	 * No public access to the constructor.
	 */
	private CallSetParser() {
		// nothing to do here
	}

	/**
	 * Create a new set of calls in the given region.
	 * 
	 * @param offset
	 *            is the current offset of the whole buffer, it is often 0
	 * @param buffer
	 *            is the buffer
	 * @param range
	 *            is the region of the buffer that contains the new set of calls to create
	 * @param template
	 *            is the current template where to put the new object
	 * @return the new set of calls
	 * @throws TemplateSyntaxException
	 *             if a syntax issue occurs
	 */
	public static CallSet createCallSet(int offset, String buffer, Region range, Template template)
			throws TemplateSyntaxException {
		Region localRange = range;
		Region trim = TextSearch.trim(buffer, localRange.b(), localRange.e());
		if (trim.b() == -1) {
			throw new TemplateSyntaxException(AcceleoCompatibilityMessages
					.getString("TemplateSyntaxError.MissingElement"), template, localRange); //$NON-NLS-1$
		}

		localRange = trim;
		Region[] positions = TextSearch.splitPositionsIn(buffer, localRange, new String[] {TemplateConstants
				.getDefault().getCallSep(), }, false, TemplateConstants.getDefault().getSpec(),
				TemplateConstants.getDefault().getInhibsExpression());
		CallSet callSet = ExpressionsFactory.eINSTANCE.createCallSet();
		callSet.setBegin(offset + localRange.b());
		callSet.setEnd(offset + localRange.e());
		for (int i = 0; i < positions.length; i++) {
			Region pos = positions[i];
			callSet.getCalls().add(CallParser.createCall(offset, buffer, pos, template));
		}
		return callSet;
	}
}
