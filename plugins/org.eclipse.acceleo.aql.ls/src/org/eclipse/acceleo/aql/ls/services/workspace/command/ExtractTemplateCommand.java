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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.ExpressionStatement;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.completion.proposals.templates.AcceleoCodeTemplates;
import org.eclipse.acceleo.aql.ls.common.AcceleoLanguageServerPositionUtils;
import org.eclipse.acceleo.aql.ls.services.textdocument.AcceleoTextDocument;
import org.eclipse.acceleo.aql.parser.AcceleoAstSerializer;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.WorkspaceEdit;

/**
 * Extracts selected statements in a {@link Template}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ExtractTemplateCommand extends AbstractDocumentRangeCommand {

	public WorkspaceEdit exec(AcceleoTextDocument document, Range range) {
		final WorkspaceEdit res;

		if (range.getStart().getCharacter() == 0 && range.getEnd().getCharacter() == 0) {
			final int startIndex = AcceleoLanguageServerPositionUtils.getCorrespondingCharacterIndex(range
					.getStart(), document.getContents());
			final int endIndex = AcceleoLanguageServerPositionUtils.getCorrespondingCharacterIndex(range
					.getEnd(), document.getContents());
			if (isSameBlock(document, startIndex, endIndex)) {
				final Map<String, List<TextEdit>> changes = new LinkedHashMap<>();

				final Template template = AcceleoPackage.eINSTANCE.getAcceleoFactory().createTemplate();
				template.setVisibility(VisibilityKind.PUBLIC);
				template.setName(AcceleoCodeTemplates.DEFAULT_NEW_TEMPLATE_NAME);

				// TODO pass the new line String
				// create the template
				final String lineDelimiter = System.lineSeparator();
				final String text = document.getContents().substring(startIndex, endIndex);
				final String blockIndentation = getBlockIndentation(text);
				template.setBody(createBlock(text, blockIndentation, "  ", lineDelimiter, true));
				final List<Variable> parameters = getVariablesDeclaredOutSide(document, startIndex, endIndex);
				template.getParameters().addAll(parameters);
				final String addTemplateText = lineDelimiter + lineDelimiter + new AcceleoAstSerializer(
						lineDelimiter).serialize(template);
				final ModuleElement containingElement = AcceleoUtil.getContainingModuleElement(document
						.getAcceleoAstResult().getAstNode(startIndex));
				final Position addTemplatePosition = AcceleoLanguageServerPositionUtils.getEndPositionOf(
						containingElement, document.getAcceleoAstResult());
				final Range addTemplateRange = new Range(addTemplatePosition, addTemplatePosition);
				final TextEdit addTemplateEdit = new TextEdit(addTemplateRange, addTemplateText
						+ lineDelimiter);
				final String sourceURI = document.getQueryEnvironment().getLookupEngine().getResolver()
						.getSourceURI(document.getModuleQualifiedName()).toString();

				// create the call to the created template
				final Range replacementCallRange = AcceleoLanguageServerPositionUtils.getCorrespondingRange(
						startIndex, endIndex, document.getContents());
				final ExpressionStatement templateCall = createCall(template.getName(), parameters);
				final String replacementCallText = blockIndentation + new AcceleoAstSerializer(lineDelimiter)
						.serialize(templateCall);
				final TextEdit replacementCallEdit = new TextEdit(replacementCallRange, replacementCallText
						+ lineDelimiter);

				final List<TextEdit> edits = new ArrayList<>();
				edits.add(addTemplateEdit);
				edits.add(replacementCallEdit);
				changes.put(sourceURI, edits);

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
