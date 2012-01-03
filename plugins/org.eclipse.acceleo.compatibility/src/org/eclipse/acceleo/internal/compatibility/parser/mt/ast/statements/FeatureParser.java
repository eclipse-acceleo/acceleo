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

import org.eclipse.acceleo.compatibility.model.mt.core.Template;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Expression;
import org.eclipse.acceleo.compatibility.model.mt.statements.Feature;
import org.eclipse.acceleo.compatibility.model.mt.statements.StatementsFactory;
import org.eclipse.acceleo.internal.compatibility.parser.mt.ast.expressions.ExpressionParser;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;

/**
 * The utility class to parse a new feature, it means the minimal statement to request the model.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class FeatureParser {

	/**
	 * No public access to the constructor.
	 */
	private FeatureParser() {
		// nothing to do here
	}

	/**
	 * Create a new feature in the given region.
	 * 
	 * @param offset
	 *            is the current offset of the whole buffer, it is often 0
	 * @param buffer
	 *            is the buffer
	 * @param range
	 *            is the region of the buffer that contains the new feature to create
	 * @param template
	 *            is the current template where to put the new object
	 * @return the new feature
	 * @throws TemplateSyntaxException
	 *             if a syntax issue occurs
	 */
	public static Feature createFeature(int offset, String buffer, Region range, Template template)
			throws TemplateSyntaxException {
		Expression expression = ExpressionParser.createExpression(offset, buffer, new Region(range.b(), range
				.e()), template);
		Feature feature = StatementsFactory.eINSTANCE.createFeature();
		feature.setExpression(expression);
		feature.setBegin(offset + range.b());
		feature.setEnd(offset + range.e());
		return feature;
	}
}
