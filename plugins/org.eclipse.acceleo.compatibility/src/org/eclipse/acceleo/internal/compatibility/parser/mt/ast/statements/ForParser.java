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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.compatibility.model.mt.core.Template;
import org.eclipse.acceleo.compatibility.model.mt.expressions.Expression;
import org.eclipse.acceleo.compatibility.model.mt.statements.For;
import org.eclipse.acceleo.compatibility.model.mt.statements.Statement;
import org.eclipse.acceleo.compatibility.model.mt.statements.StatementsFactory;
import org.eclipse.acceleo.internal.compatibility.parser.mt.ast.expressions.ExpressionParser;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateConstants;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TextSearch;

/**
 * The utility class to parse a 'for' statement.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class ForParser {

	/**
	 * No public access to the constructor.
	 */
	private ForParser() {
		// nothing to do here
	}

	/**
	 * Create a new 'for' statement in the given region.
	 * 
	 * @param offset
	 *            is the current offset of the whole buffer, it is often 0
	 * @param buffer
	 *            is the buffer
	 * @param range
	 *            is the region of the buffer that contains the new 'for' statement to create
	 * @param template
	 *            is the current template where to put the new object
	 * @return the new 'for' statement
	 * @throws TemplateSyntaxException
	 *             if a syntax issue occurs
	 */
	public static For createFor(int offset, String buffer, Region range, Template template)
			throws TemplateSyntaxException {
		Expression iteratorExpression;
		List<Statement> block;
		Region iThen = TextSearch.indexIn(buffer, TemplateConstants.getDefault().getForThen(), range,
				TemplateConstants.getDefault().getSpec(), TemplateConstants.getDefault()
						.getInhibsExpression());
		if (iThen.b() > -1) {
			iteratorExpression = ExpressionParser.createExpression(offset, buffer, new Region(range.b(),
					iThen.b()), template);
			Region blockPos = new Region(iThen.e(), range.e());
			blockPos = StatementParser.formatTemplate(buffer, blockPos, 0);
			block = StatementParser.createStatement(offset, buffer, blockPos, template);
		} else {
			iteratorExpression = ExpressionParser.createExpression(offset, buffer, new Region(range.b(),
					range.e()), template);
			block = new ArrayList<Statement>();
		}
		For eFor = StatementsFactory.eINSTANCE.createFor();
		eFor.setIterator(iteratorExpression);
		eFor.getStatements().addAll(block);
		eFor.setBegin(offset + range.b());
		eFor.setEnd(offset + range.e());
		return eFor;
	}
}
