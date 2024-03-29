/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.services.workspace.command;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.IfStatement;
import org.eclipse.acceleo.aql.completion.proposals.templates.AcceleoCodeTemplates;
import org.eclipse.acceleo.aql.ls.common.AcceleoLanguageServerPositionUtils;
import org.eclipse.acceleo.aql.ls.services.textdocument.AcceleoTextDocument;
import org.eclipse.acceleo.aql.parser.AcceleoAstSerializer;
import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.AQLUtils.AcceleoAQLResult;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.WorkspaceEdit;

/**
 * Wrap the selected statements in a {@link IfStatement}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class WrapInIfCommand extends AbstractDocumentRangeCommand {

	public WorkspaceEdit exec(AcceleoTextDocument document, Range range) {
		final WorkspaceEdit res;

		if (range.getStart().getCharacter() == 0 && range.getEnd().getCharacter() == 0) {
			final int startIndex = AcceleoLanguageServerPositionUtils.getCorrespondingCharacterIndex(range
					.getStart(), document.getContents());
			final int endIndex = AcceleoLanguageServerPositionUtils.getCorrespondingCharacterIndex(range
					.getEnd(), document.getContents());
			final String text = document.getContents().substring(startIndex, endIndex);
			if (!text.isEmpty() && isSameBlock(document, startIndex, endIndex)) {
				final Map<String, List<TextEdit>> changes = new LinkedHashMap<>();

				final IfStatement statement = AcceleoPackage.eINSTANCE.getAcceleoFactory()
						.createIfStatement();
				Expression expression = AcceleoPackage.eINSTANCE.getAcceleoFactory().createExpression();
				final AcceleoAQLResult aqlExpression = AQLUtils.parseWhileAqlExpression(
						AcceleoCodeTemplates.DEFAULT_EXPRESSION);
				expression.setAst(aqlExpression.getAstResult());
				expression.setAql(aqlExpression.getAstResult().getAst());
				statement.setCondition(expression);

				// TODO pass the new line String
				final String lineDelimiter = System.lineSeparator();
				final String blockIndentation = getBlockIndentation(text);
				statement.setThen(createBlock(text, blockIndentation, "  ", lineDelimiter, false));

				final String replacementText = blockIndentation + new AcceleoAstSerializer(lineDelimiter)
						.serialize(statement);

				final Range replacementRange = AcceleoLanguageServerPositionUtils.getCorrespondingRange(
						startIndex, endIndex, document.getContents());
				final TextEdit edit = new TextEdit(replacementRange, replacementText + lineDelimiter);
				final String sourceURI = document.getQueryEnvironment().getLookupEngine().getResolver()
						.getSourceURI(document.getModuleQualifiedName()).toString();
				changes.put(sourceURI, Collections.singletonList(edit));

				res = new WorkspaceEdit(changes);
			} else {
				res = null;
			}
		} else {
			res = null;
		}

		return res;
	}

}
