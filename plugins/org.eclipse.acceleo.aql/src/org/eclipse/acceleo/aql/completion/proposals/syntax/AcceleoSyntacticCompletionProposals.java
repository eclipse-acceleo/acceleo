/*******************************************************************************
 * Copyright (c) 2020, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.completion.proposals.syntax;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.BlockComment;
import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.Documentation;
import org.eclipse.acceleo.ExpressionStatement;
import org.eclipse.acceleo.FileStatement;
import org.eclipse.acceleo.ForStatement;
import org.eclipse.acceleo.IfStatement;
import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.LetStatement;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.ProtectedArea;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.aql.completion.proposals.AcceleoCompletionProposal;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.emf.ecore.EClass;

/**
 * Static {@link ICompletionProposal completion proposals} based purely on the Acceleo syntax.
 * 
 * @author Florent Latombe
 */
public final class AcceleoSyntacticCompletionProposals {

	// Punctuation and special characters.
	/**
	 * A new line in the description of an {@link AcceleoCompletionProposal}.
	 */
	public static final String NEWLINE = AcceleoCompletionProposal.DESCRIPTION_NEWLINE;

	/**
	 * Opening tag for a code sample in the description of an {@link AcceleoCompletionProposal}.
	 */
	public static final String CODE_OPEN = AcceleoCompletionProposal.DESCRIPTION_CODE_OPEN;

	/**
	 * Closing tag for a code sample in the description of an {@link AcceleoCompletionProposal}.
	 */
	public static final String CODE_CLOSE = AcceleoCompletionProposal.DESCRIPTION_CODE_CLOSE;

	/**
	 * Opening tag for a bold text in the description of an {@link AcceleoCompletionProposal}.
	 */
	public static final String BOLD_OPEN = AcceleoCompletionProposal.DESCRIPTION_BOLD_OPEN;

	/**
	 * Closing tag for a bold text in the description of an {@link AcceleoCompletionProposal}.
	 */
	public static final String BOLD_CLOSE = AcceleoCompletionProposal.DESCRIPTION_BOLD_CLOSE;

	/**
	 * Opening tag for a list in the description of an {@link AcceleoCompletionProposal}.
	 */
	public static final String LIST_OPEN = AcceleoCompletionProposal.DESCRIPTION_LIST_OPEN;

	/**
	 * Closing tag for a list in the description of an {@link AcceleoCompletionProposal}.
	 */
	public static final String LIST_CLOSE = AcceleoCompletionProposal.DESCRIPTION_LIST_CLOSE;

	/**
	 * Opening tag for a list item in the description of an {@link AcceleoCompletionProposal}.
	 */
	public static final String LIST_ITEM_OPEN = AcceleoCompletionProposal.DESCRIPTION_LIST_ITEM_OPEN;

	/**
	 * Closing tag for a list item in the description of an {@link AcceleoCompletionProposal}.
	 */
	public static final String LIST_ITEM_CLOSE = AcceleoCompletionProposal.DESCRIPTION_LIST_ITEM_CLOSE;

	/**
	 * A space.
	 */
	public static final String SPACE = " ";

	/**
	 * A quote.
	 */
	public static final AcceleoCompletionProposal QUOTE_DOUBLE = new AcceleoCompletionProposal(
			AcceleoParser.QUOTE, AcceleoParser.QUOTE, null);

	/**
	 * An open parenthesis.
	 */
	public static final AcceleoCompletionProposal OPEN_PARENTHESIS = new AcceleoCompletionProposal(
			AcceleoParser.OPEN_PARENTHESIS, AcceleoParser.OPEN_PARENTHESIS, null);

	/**
	 * A close parenthesis.
	 */
	public static final AcceleoCompletionProposal CLOSE_PARENTHESIS = new AcceleoCompletionProposal(
			AcceleoParser.CLOSE_PARENTHESIS, AcceleoParser.CLOSE_PARENTHESIS, null);

	/**
	 * A comma, followed by a space.
	 */
	public static final AcceleoCompletionProposal COMMA_SPACE = new AcceleoCompletionProposal(
			AcceleoParser.COMMA, AcceleoParser.COMMA + SPACE, null);

	/**
	 * A colon, followed by a space.
	 */
	public static final AcceleoCompletionProposal COLON_SPACE = new AcceleoCompletionProposal(
			AcceleoParser.COLON, AcceleoParser.COLON + SPACE, null);

	/**
	 * An equal sign, followed by a space.
	 */
	public static final AcceleoCompletionProposal EQUAL_SPACE = new AcceleoCompletionProposal(
			AcceleoParser.EQUAL, AcceleoParser.EQUAL + SPACE, null);
	////

	// Comment and Documentation
	/**
	 * The start of a {@link Comment}.
	 */
	public static final AcceleoCompletionProposal COMMENT_START = createSyntacticCompletionProposal(
			AcceleoParser.COMMENT_START, "Inserts the start of a Comment:", AcceleoPackage.Literals.COMMENT);

	/**
	 * The end of a {@link Comment}.
	 */
	public static final AcceleoCompletionProposal COMMENT_END = createSyntacticCompletionProposal(
			AcceleoParser.COMMENT_END, "Inserts the end of a Comment:", AcceleoPackage.Literals.COMMENT);

	/**
	 * The start of a {@link BlockComment}.
	 */
	public static final AcceleoCompletionProposal BLOCK_COMMENT_START = createSyntacticCompletionProposal(
			AcceleoParser.BLOCK_COMMENT_START, "Inserts the start of a Block Comment:",
			AcceleoPackage.Literals.BLOCK_COMMENT);

	/**
	 * The end of a {@link BlockComment}.
	 */
	public static final AcceleoCompletionProposal BLOCK_COMMENT_END = createSyntacticCompletionProposal(
			AcceleoParser.BLOCK_COMMENT_END, "Inserts the end of a Block Comment:",
			AcceleoPackage.Literals.BLOCK_COMMENT);

	/**
	 * The start of a {@link Documentation}.
	 */
	public static final AcceleoCompletionProposal DOCUMENTATION_START = createSyntacticCompletionProposal(
			AcceleoParser.DOCUMENTATION_START,
			"Inserts the start of a Module Documentation or Module Element Documentation:",
			AcceleoPackage.Literals.DOCUMENTATION);

	/**
	 * The end of a {@link Documentation}.
	 */
	public static final AcceleoCompletionProposal DOCUMENTATION_END = createSyntacticCompletionProposal(
			AcceleoParser.DOCUMENTATION_END,
			"Inserts the end of a Module Documentation or Module Element Documentation:",
			AcceleoPackage.Literals.DOCUMENTATION);
	////

	// Import
	/**
	 * The start of an {@link Import}.
	 */
	public static final AcceleoCompletionProposal IMPORT_START = createSyntacticCompletionProposal(
			AcceleoParser.IMPORT_START, "Inserts the start of an Import:", AcceleoPackage.Literals.IMPORT);

	/**
	 * The end of an {@link Import}.
	 */
	public static final AcceleoCompletionProposal IMPORT_END = createSyntacticCompletionProposal(
			AcceleoParser.IMPORT_END, "Inserts the end of an Import:", AcceleoPackage.Literals.IMPORT);
	////

	// Module
	/**
	 * The start of a {@link Module} header.
	 */
	public static final AcceleoCompletionProposal MODULE_HEADER_START = createSyntacticCompletionProposal(
			AcceleoParser.MODULE_HEADER_START, "Inserts the start of an Acceleo Module header:",
			AcceleoPackage.Literals.MODULE);

	/**
	 * The extension clause of a {@link Module} header.
	 */
	public static final AcceleoCompletionProposal MODULE_EXTENSION = createSyntacticCompletionProposal(
			AcceleoParser.EXTENDS, "Inserts the extension clause of an Acceleo Module header:",
			AcceleoPackage.Literals.MODULE);

	/**
	 * The end of a {@link Module} header.
	 */
	public static final AcceleoCompletionProposal MODULE_HEADER_END = createSyntacticCompletionProposal(
			AcceleoParser.MODULE_HEADER_END, "Inserts the end of an Acceleo Module header:",
			AcceleoPackage.Literals.MODULE);
	////

	// Template
	/**
	 * The start of a {@link Template} header.
	 */
	public static final AcceleoCompletionProposal TEMPLATE_HEADER_START = createSyntacticCompletionProposal(
			AcceleoParser.TEMPLATE_HEADER_START, "Inserts the start of an Acceleo Template header:",
			AcceleoPackage.Literals.TEMPLATE);

	/**
	 * The start of the guard of a {@link Template}.
	 */
	public static final AcceleoCompletionProposal TEMPLATE_GUARD_START = createSyntacticCompletionProposal(
			AcceleoParser.TEMPLATE_GUARD + SPACE + AcceleoParser.OPEN_PARENTHESIS,
			"Inserts the start of a template guard:", AcceleoPackage.Literals.TEMPLATE);

	/**
	 * The start of the post of a {@link Template}.
	 */
	public static final AcceleoCompletionProposal TEMPLATE_POST_START = createSyntacticCompletionProposal(
			AcceleoParser.TEMPLATE_POST, "Inserts the start of a template post-treatment:",
			AcceleoPackage.Literals.TEMPLATE);

	/**
	 * The end of a {@link Template} header.
	 */
	public static final AcceleoCompletionProposal TEMPLATE_HEADER_END = createSyntacticCompletionProposal(
			AcceleoParser.TEMPLATE_HEADER_END, "Inserts the end of an Acceleo Template header:",
			AcceleoPackage.Literals.TEMPLATE);

	/**
	 * The footer of a {@link Template}.
	 */
	public static final AcceleoCompletionProposal TEMPLATE_END = createSyntacticCompletionProposal(
			AcceleoParser.TEMPLATE_END, "Inserts the end of an Acceleo Template:",
			AcceleoPackage.Literals.TEMPLATE);
	////

	// Query
	/**
	 * The start of a {@link Query}.
	 */
	public static final AcceleoCompletionProposal QUERY_START = createSyntacticCompletionProposal(
			AcceleoParser.QUERY_START, "Inserts the start of a query:", AcceleoPackage.Literals.QUERY);

	/**
	 * The end of a {@link Query}.
	 */
	public static final AcceleoCompletionProposal QUERY_END = createSyntacticCompletionProposal(
			AcceleoParser.QUERY_END, "Inserts the end of a query:", AcceleoPackage.Literals.QUERY);
	////

	// FileStatement
	/**
	 * The start of a {@link FileStatement} header.
	 */
	public static final AcceleoCompletionProposal STATEMENT_FILE_HEADER_START = createSyntacticCompletionProposal(
			AcceleoParser.FILE_HEADER_START + AcceleoParser.OPEN_PARENTHESIS,
			"Inserts the start of a File Statement header:", AcceleoPackage.Literals.FILE_STATEMENT);

	/**
	 * The end of a {@link FileStatement} header.
	 */
	public static final AcceleoCompletionProposal STATEMENT_FILE_HEADER_END = createSyntacticCompletionProposal(
			AcceleoParser.FILE_HEADER_END, "Inserts the end of a File Statement header:",
			AcceleoPackage.Literals.FILE_STATEMENT);

	/**
	 * A close parenthesis and the end of a {@link FileStatement} header.
	 */
	public static final AcceleoCompletionProposal STATEMENT_FILE_HEADER_CLOSE_PARENTHESIS_AND_END = createSyntacticCompletionProposal(
			AcceleoParser.CLOSE_PARENTHESIS + AcceleoParser.FILE_HEADER_END,
			"Inserts the closing parenthesis and the end of a File Statement header:",
			AcceleoPackage.Literals.FILE_STATEMENT);

	/**
	 * The end of a {@link FileStatement}.
	 */
	public static final AcceleoCompletionProposal STATEMENT_FILE_END = createSyntacticCompletionProposal(
			AcceleoParser.FILE_END, "Inserts the end of a File Statement:",
			AcceleoPackage.Literals.FILE_STATEMENT);
	////

	// IfStatement
	/**
	 * The start of an {@link IfStatement} header.
	 */
	public static final AcceleoCompletionProposal STATEMENT_IF_HEADER_START = createSyntacticCompletionProposal(
			AcceleoParser.IF_HEADER_START + AcceleoParser.OPEN_PARENTHESIS,
			"Inserts the start of an If Statement header:", AcceleoPackage.Literals.IF_STATEMENT);

	/**
	 * The 'else' clause of an {@link IfStatement}.
	 */
	public static final AcceleoCompletionProposal STATEMENT_IF_ELSE = createSyntacticCompletionProposal(
			AcceleoParser.IF_ELSE, "Inserts the 'else' clause of an If Statement:",
			AcceleoPackage.Literals.IF_STATEMENT);

	/**
	 * An 'elseif' clause of an {@link IfStatement}.
	 */
	public static final AcceleoCompletionProposal STATEMENT_IF_ELSEIF = createSyntacticCompletionProposal(
			AcceleoParser.IF_ELSEIF + AcceleoParser.OPEN_PARENTHESIS,
			"Inserts an 'elseif' clause to an If Statement:", AcceleoPackage.Literals.IF_STATEMENT);

	/**
	 * The end of an {@link IfStatement} header.
	 */
	public static final AcceleoCompletionProposal STATEMENT_IF_HEADER_END = createSyntacticCompletionProposal(
			AcceleoParser.IF_HEADER_END, "Inserts the end of an If Statement header:",
			AcceleoPackage.Literals.IF_STATEMENT);

	/**
	 * The end of an {@link IfStatement}.
	 */
	public static final AcceleoCompletionProposal STATEMENT_IF_END = createSyntacticCompletionProposal(
			AcceleoParser.IF_END, "Inserts the end of an If Statement:",
			AcceleoPackage.Literals.IF_STATEMENT);

	/**
	 * A close parenthesis and the end of an {@link IfStatement} header.
	 */
	public static final AcceleoCompletionProposal STATEMENT_IF_HEADER_CLOSE_PARENTHESIS_AND_END = createSyntacticCompletionProposal(
			AcceleoParser.CLOSE_PARENTHESIS + AcceleoParser.IF_HEADER_END,
			"Inserts the closing parenthesis and end of an If Statement header:",
			AcceleoPackage.Literals.IF_STATEMENT);
	////

	// ForStatement
	/**
	 * The start of a {@link ForStatement} header.
	 */
	public static final AcceleoCompletionProposal STATEMENT_FOR_HEADER_START = createSyntacticCompletionProposal(
			AcceleoParser.FOR_HEADER_START + AcceleoParser.OPEN_PARENTHESIS,
			"Inserts the start of a For Statement header:", AcceleoPackage.Literals.FOR_STATEMENT);

	/**
	 * The end of a {@link ForStatement} header.
	 */
	public static final AcceleoCompletionProposal STATEMENT_FOR_HEADER_END = createSyntacticCompletionProposal(
			AcceleoParser.FOR_HEADER_END, "Inserts the end of a For Statement header:",
			AcceleoPackage.Literals.FOR_STATEMENT);

	/**
	 * The end of a {@link ForStatement}.
	 */
	public static final AcceleoCompletionProposal STATEMENT_FOR_END = createSyntacticCompletionProposal(
			AcceleoParser.FOR_END, "Inserts the end of a For Statement:",
			AcceleoPackage.Literals.FOR_STATEMENT);

	/**
	 * A close parenthesis and the end of a {@link ForStatement} header.
	 */
	public static final AcceleoCompletionProposal STATEMENT_FOR_HEADER_CLOSE_PARENTHESIS_AND_END = createSyntacticCompletionProposal(
			AcceleoParser.CLOSE_PARENTHESIS + AcceleoParser.FOR_HEADER_END,
			"Inserts a closing parenthesis and the end of a For Statement header:",
			AcceleoPackage.Literals.FOR_STATEMENT);

	/**
	 * The start of the separator in a {@link ForStatement}.
	 */
	public static final AcceleoCompletionProposal STATEMENT_FOR_HEADER_SEPARATOR = createSyntacticCompletionProposal(
			AcceleoParser.FOR_SEPARATOR, "Inserts the For Statement separator expression:",
			AcceleoPackage.Literals.FOR_STATEMENT);

	/**
	 * The pipe in a {@link ForStatement}.
	 */
	public static final AcceleoCompletionProposal FOR_STATEMENT_PIPE = createSyntacticCompletionProposal(
			AcceleoParser.PIPE,
			"Inserts the For Statement pipe that separates the variable declaration and its value binding:",
			AcceleoPackage.Literals.FOR_STATEMENT);
	////

	// LetStatement
	/**
	 * The start of a {@link LetStatement} header.
	 */
	public static final AcceleoCompletionProposal STATEMENT_LET_HEADER_START = createSyntacticCompletionProposal(
			AcceleoParser.LET_HEADER_START, "Inserts the start of a Let Statement header:",
			AcceleoPackage.Literals.LET_STATEMENT);

	/**
	 * The end of a {@link LetStatement} header.
	 */
	public static final AcceleoCompletionProposal STATEMENT_LET_HEADER_END = createSyntacticCompletionProposal(
			AcceleoParser.LET_HEADER_END, "Inserts the end of a Let Statement header:",
			AcceleoPackage.Literals.LET_STATEMENT);

	/**
	 * The end of a {@link LetStatement}.
	 */
	public static final AcceleoCompletionProposal STATEMENT_LET_END = createSyntacticCompletionProposal(
			AcceleoParser.LET_END, "Inserts the end of a Let Statement:",
			AcceleoPackage.Literals.LET_STATEMENT);
	////

	// ProtectedArea
	/**
	 * The start of a {@link ProtectedArea} header.
	 */
	public static final AcceleoCompletionProposal STATEMENT_PROTECTED_AREA_HEADER_START = createSyntacticCompletionProposal(
			AcceleoParser.PROTECTED_AREA_HEADER_START + AcceleoParser.OPEN_PARENTHESIS,
			"Inserts the start of a Protected Area Statement header:",
			AcceleoPackage.Literals.PROTECTED_AREA);

	/**
	 * The start of the start tag prefix in a {@link ProtectedArea}.
	 */
	public static final AcceleoCompletionProposal STATEMENT_PROTECTED_AREA_START_TAG_PREFIX = createSyntacticCompletionProposal(
			AcceleoParser.PROTECTED_AREA_START_TAG_PREFIX,
			"Inserts the Protected Area Statement start tag prefix expression:",
			AcceleoPackage.Literals.PROTECTED_AREA);

	/**
	 * The start of the start tag prefix in a {@link ProtectedArea}.
	 */
	public static final AcceleoCompletionProposal STATEMENT_PROTECTED_AREA_END_TAG_PREFIX = createSyntacticCompletionProposal(
			AcceleoParser.PROTECTED_AREA_END_TAG_PREFIX,
			"Inserts the Protected Area Statement end tag prefix expression:",
			AcceleoPackage.Literals.PROTECTED_AREA);

	/**
	 * The end of a {@link ProtectedArea} header.
	 */
	public static final AcceleoCompletionProposal STATEMENT_PROTECTED_AREA_HEADER_END = createSyntacticCompletionProposal(
			AcceleoParser.PROTECTED_AREA_HEADER_END, "Inserts the end of a Protected Area Statement header:",
			AcceleoPackage.Literals.PROTECTED_AREA);

	/**
	 * A close parenthesis and the end of a {@link ProtectedArea} header.
	 */
	public static final AcceleoCompletionProposal STATEMENT_PROTECTED_AREA_HEADER_CLOSE_PARENTHESIS_AND_END = createSyntacticCompletionProposal(
			AcceleoParser.CLOSE_PARENTHESIS + AcceleoParser.PROTECTED_AREA_HEADER_END,
			"Inserts a closing parenthesis and the end of a Protected Area Statement header:",
			AcceleoPackage.Literals.PROTECTED_AREA);

	/**
	 * The end of a {@link ProtectedArea}.
	 */
	public static final AcceleoCompletionProposal STATEMENT_PROTECTED_AREA_END = createSyntacticCompletionProposal(
			AcceleoParser.PROTECTED_AREA_END, "Inserts the end of a Protected Area Statement:",
			AcceleoPackage.Literals.PROTECTED_AREA);
	////

	// ExpressionStatement
	/**
	 * The start of an {@link ExpressionStatement}.
	 */
	public static final AcceleoCompletionProposal STATEMENT_EXPRESSION_START = createSyntacticCompletionProposal(
			AcceleoParser.EXPRESSION_STATEMENT_START, "Inserts the start of an Expression Statement:",
			AcceleoPackage.Literals.EXPRESSION_STATEMENT);

	/**
	 * The end of an {@link ExpressionStatement}.
	 */
	public static final AcceleoCompletionProposal STATEMENT_EXPRESSION_END = createSyntacticCompletionProposal(
			AcceleoParser.EXPRESSION_STATEMENT_END, "Inserts the end of an Expression Statement:",
			AcceleoPackage.Literals.EXPRESSION_STATEMENT);
	////

	// EEnumerations
	/**
	 * The {@link List} of {@link AcceleoCompletionProposal} for all the {@link OpenModeKind} values.
	 */
	public static final List<AcceleoCompletionProposal> STATEMENT_FILE_OPEN_MODES = OpenModeKind.VALUES
			.stream().map(openModeKind -> {
				String overwriteLabel = OpenModeKind.OVERWRITE.getName();
				String appendLabel = OpenModeKind.APPEND.getName();
				String createLabel = OpenModeKind.CREATE.getName();
				switch (openModeKind) {
					case OVERWRITE:
						overwriteLabel = BOLD_OPEN + overwriteLabel + BOLD_CLOSE;
						break;

					case APPEND:
						appendLabel = BOLD_OPEN + appendLabel + BOLD_CLOSE;
						break;

					case CREATE:
						createLabel = BOLD_OPEN + createLabel + BOLD_CLOSE;
						break;
					default:
						break;
				}
				return new AcceleoCompletionProposal(openModeKind.getName(),
						"The open mode for a File Statement determines the strategy to use <i>if there already exists a file with the desired name</i> at the generation destination."
								+ NEWLINE + LIST_OPEN + LIST_ITEM_OPEN + overwriteLabel
								+ ": overwrites the contents of the existing file." + LIST_ITEM_CLOSE
								+ LIST_ITEM_OPEN + appendLabel
								+ ": appends the generated contents to the end of the existing file."
								+ LIST_ITEM_CLOSE + LIST_ITEM_OPEN + createLabel
								+ ": no content is generated." + LIST_ITEM_CLOSE + LIST_CLOSE, openModeKind
										.getName(), AcceleoPackage.Literals.FILE_STATEMENT);
			}).collect(Collectors.toList());

	/**
	 * The {@link List} of {@link AcceleoCompletionProposal} for all the {@link VisibilityKind} values.
	 */
	public static final List<AcceleoCompletionProposal> MODULE_ELEMENT_VISIBILITY_KINDS = VisibilityKind.VALUES
			.stream().map(visibilityKind -> {
				String publicLabel = VisibilityKind.PUBLIC.getName();
				String protectedLabel = VisibilityKind.PROTECTED.getName();
				String privateLabel = VisibilityKind.PRIVATE.getName();
				switch (visibilityKind) {
					case PUBLIC:
						publicLabel = BOLD_OPEN + publicLabel + BOLD_CLOSE;
						break;

					case PROTECTED:
						protectedLabel = BOLD_OPEN + protectedLabel + BOLD_CLOSE;
						break;

					case PRIVATE:
						privateLabel = BOLD_OPEN + privateLabel + BOLD_CLOSE;
						break;
					default:
						break;
				}
				return new AcceleoCompletionProposal(visibilityKind.getName(),
						"The visibility of a Query or Template determines whether it is accessible to other Acceleo Modules."
								+ NEWLINE + LIST_OPEN + LIST_ITEM_OPEN + publicLabel
								+ ": always accessible to all Acceleo Modules." + LIST_ITEM_CLOSE
								+ LIST_ITEM_OPEN + protectedLabel
								+ ": accessible only to this Module, and other Modules extending this Module."
								+ LIST_ITEM_CLOSE + LIST_ITEM_OPEN + privateLabel
								+ ": accessible only to this Module." + LIST_ITEM_CLOSE + LIST_CLOSE,
						visibilityKind.getName() + SPACE, AcceleoPackage.Literals.MODULE_ELEMENT);
			}).collect(Collectors.toList());
	////

	private AcceleoSyntacticCompletionProposals() {
		// Utility class.
	}

	/**
	 * Helper to factorize the common parts of the completion proposals that look alike.
	 * 
	 * @param insertedText
	 *            the (non-{@code null}) text inserted by the completion proposal. It will also be used as its
	 *            label and as part of its description.
	 * @param descriptionSentenceBeforeCode
	 *            the (non-{@code null}) text placed in the description above the code-formatted
	 *            {@code insertedText}.
	 * @param acceleoType
	 *            the (maybe-{@code null}) Acceleo {@link EClass} on which the completion is based.
	 * @return the {@link AcceleoCompletionProposal} corresponding to a syntax element.
	 */
	private static AcceleoCompletionProposal createSyntacticCompletionProposal(String insertedText,
			String descriptionSentenceBeforeCode, EClass acceleoType) {
		return new AcceleoCompletionProposal(insertedText, descriptionSentenceBeforeCode + NEWLINE + CODE_OPEN
				+ insertedText + CODE_CLOSE, insertedText, acceleoType);
	}

}
