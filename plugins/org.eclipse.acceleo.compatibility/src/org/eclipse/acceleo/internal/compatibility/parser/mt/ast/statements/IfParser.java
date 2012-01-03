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
import org.eclipse.acceleo.compatibility.model.mt.statements.If;
import org.eclipse.acceleo.compatibility.model.mt.statements.Statement;
import org.eclipse.acceleo.compatibility.model.mt.statements.StatementsFactory;
import org.eclipse.acceleo.internal.compatibility.AcceleoCompatibilityMessages;
import org.eclipse.acceleo.internal.compatibility.parser.mt.ast.expressions.ExpressionParser;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.Region;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateConstants;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TemplateSyntaxException;
import org.eclipse.acceleo.internal.compatibility.parser.mt.common.TextSearch;

/**
 * The utility class to parse an 'if' statement.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class IfParser {

	/**
	 * No public access to the constructor.
	 */
	private IfParser() {
		// nothing to do here
	}

	/**
	 * Create a new 'if' statement in the given region.
	 * 
	 * @param offset
	 *            is the current offset of the whole buffer, it is often 0
	 * @param buffer
	 *            is the buffer
	 * @param range
	 *            is the region of the buffer that contains the new 'if' statement to create
	 * @param template
	 *            is the current template where to put the new object
	 * @return the new 'if' statement
	 * @throws TemplateSyntaxException
	 *             if a syntax issue occurs
	 */
	public static If createIf(int offset, String buffer, Region range, Template template)
			throws TemplateSyntaxException {
		Expression condition;
		List<Statement> ifBlock;
		List<Expression> elseIfConditions = new ArrayList<Expression>();
		List<List<Statement>> elseIfBlocks = new ArrayList<List<Statement>>();
		List<Statement> elseBlock;
		// If -> Then
		Region iThen = TextSearch.indexIn(buffer, TemplateConstants.getDefault().getIfThen(), range,
				TemplateConstants.getDefault().getSpec(), TemplateConstants.getDefault()
						.getInhibsExpression());
		if (iThen.b() > -1) {
			condition = ExpressionParser.createExpression(offset, buffer, new Region(range.b(), iThen.b()),
					template);
			int iThenEnd;
			// Else -> End
			Region iElse = TextSearch.indexIn(buffer, TemplateConstants.getDefault().getIfElse(), new Region(
					iThen.e(), range.e()), null, TemplateConstants.getDefault().getInhibsStatement());
			if (iElse.b() > -1) {
				iThenEnd = iElse.b();
				Region elsePos = new Region(iElse.e(), range.e());
				elsePos = StatementParser.formatTemplate(buffer, elsePos, 0);
				elseBlock = StatementParser.createStatement(offset, buffer, elsePos, template);
			} else {
				iThenEnd = range.e();
				elseBlock = new ArrayList<Statement>();
			}
			// ElseIf -> ElseIf|Else|End
			int iElseIfEndMax = iThenEnd;
			Region iElseIf = TextSearch.indexIn(buffer, TemplateConstants.getDefault().getIfElseIf(),
					new Region(iThen.e(), iElseIfEndMax), null, TemplateConstants.getDefault()
							.getInhibsStatement());
			if (iElseIf.b() > -1) {
				iThenEnd = iElseIf.b();
				while (iElseIf.b() > -1) {
					// ElseIf test
					Expression elseIfTest;
					Region iTestEnd = TextSearch.indexIn(buffer, TemplateConstants.getDefault().getIfThen(),
							new Region(iElseIf.e(), iElseIfEndMax), TemplateConstants.getDefault().getSpec(),
							TemplateConstants.getDefault().getInhibsExpression());
					if (iTestEnd.b() > -1) {
						elseIfTest = ExpressionParser.createExpression(offset, buffer, new Region(
								iElseIf.e(), iTestEnd.b()), template);
					} else {
						throw new TemplateSyntaxException(
								AcceleoCompatibilityMessages.getString("TemplateSyntaxError.MissingCloseIf"), template, new Region(iElseIf.e(), iElseIfEndMax)); //$NON-NLS-1$
					}
					iElseIf = TextSearch.indexIn(buffer, TemplateConstants.getDefault().getIfElseIf(),
							new Region(iTestEnd.e(), iElseIfEndMax), null, TemplateConstants.getDefault()
									.getInhibsStatement());
					Region elseIfPos;
					if (iElseIf.b() > -1) {
						elseIfPos = new Region(iTestEnd.e(), iElseIf.b());
					} else {
						elseIfPos = new Region(iTestEnd.e(), iElseIfEndMax);
					}
					elseIfPos = StatementParser.formatTemplate(buffer, elseIfPos, 0);
					List<Statement> elseIfBlock = StatementParser.createStatement(offset, buffer, elseIfPos,
							template);
					elseIfConditions.add(elseIfTest);
					elseIfBlocks.add(elseIfBlock);
				}
			}
			// Then -> ElseIf|Else|End
			Region ifPos = new Region(iThen.e(), iThenEnd);
			ifPos = StatementParser.formatTemplate(buffer, ifPos, 0);
			ifBlock = StatementParser.createStatement(offset, buffer, ifPos, template);
		} else {
			condition = ExpressionParser.createExpression(offset, buffer, new Region(range.b(), range.e()),
					template);
			ifBlock = new ArrayList<Statement>();
			elseBlock = new ArrayList<Statement>();
		}
		If eIf = StatementsFactory.eINSTANCE.createIf();
		eIf.setCondition(condition);
		eIf.getThenStatements().addAll(ifBlock);
		eIf.getElseStatements().addAll(elseBlock);
		for (int i = 0; i < elseIfConditions.size(); ++i) {
			if (elseIfConditions.get(i) != null) {
				If eElseIf = StatementsFactory.eINSTANCE.createIf();
				eElseIf.setCondition(elseIfConditions.get(i));
				if (elseIfBlocks.get(i) != null) {
					eElseIf.getThenStatements().addAll(elseIfBlocks.get(i));
				}
				eIf.getElseIf().add(eElseIf);
			}
		}

		eIf.setBegin(offset + range.b());
		eIf.setEnd(offset + range.e());
		return eIf;
	}
}
