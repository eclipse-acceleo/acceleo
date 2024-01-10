/*******************************************************************************
 * Copyright (c) 2020, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.parser;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.AcceleoASTNode;
import org.eclipse.acceleo.Binding;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.BlockComment;
import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.CommentBody;
import org.eclipse.acceleo.Documentation;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.ExpressionStatement;
import org.eclipse.acceleo.FileStatement;
import org.eclipse.acceleo.ForStatement;
import org.eclipse.acceleo.IfStatement;
import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.LetStatement;
import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleDocumentation;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.ModuleElementDocumentation;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.acceleo.NewLineStatement;
import org.eclipse.acceleo.ProtectedArea;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.TextStatement;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.query.ast.Conditional;
import org.eclipse.acceleo.query.ast.Let;
import org.eclipse.acceleo.query.parser.AstBuilder;
import org.eclipse.acceleo.query.parser.AstSerializer;
import org.eclipse.acceleo.util.AcceleoSwitch;

/**
 * Serializes {@link AcceleoASTNode}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoAstSerializer extends AcceleoSwitch<Object> {

	/**
	 * The indentation space.
	 */
	private static final String INDENTATION_SPACE = "  ";

	/**
	 * A space.
	 */
	private static final String SPACE = " ";

	/**
	 * A dummy {@link Object} to prevent switching in super types.
	 */
	private static final Object DUMMY = new Object();

	/**
	 * The {@link AstSerializer}.
	 */
	private AstSerializer querySerializer = new AstSerializer();

	/**
	 * The current binding separator.
	 */
	private String bindingSeparator;

	/**
	 * The resulting {@link StringBuilder}.
	 */
	private StringBuilder builder;

	/**
	 * The current indentation.
	 */
	private String currentIndentation = "";

	/**
	 * The current block header start column.
	 */
	private int currentBlockHeaderStartColumn;

	/**
	 * The new line {@link String}.
	 */
	private final String newLine;

	public AcceleoAstSerializer(String newLine) {
		this.newLine = newLine;
	}

	/**
	 * Serializes the given {@link AcceleoASTNode}.
	 * 
	 * @param node
	 *            the {@link AcceleoASTNode}
	 * @return the serialized {@link AcceleoASTNode}
	 */
	public String serialize(AcceleoASTNode node) {
		builder = new StringBuilder();

		currentIndentation = "";
		updateCurrentBlockHeaderStartColumn();
		doSwitch(node);

		return builder.toString();
	}

	@Override
	public Object caseBinding(Binding binding) {
		final String bindingName = AstBuilder.protectWithUnderscore(binding.getName());
		builder.append(bindingName);
		if (binding.getType() != null) {
			builder.append(" : ");
			builder.append(querySerializer.serialize(binding.getType().getAst()));
		}
		builder.append(SPACE);
		builder.append(bindingSeparator);
		builder.append(SPACE);
		doSwitch(binding.getInitExpression());

		return DUMMY;
	}

	@Override
	public Object caseBlock(Block block) {
		final String savedIndentation = currentIndentation;
		try {
			final StringBuilder blockIndentation = new StringBuilder();
			if (block.isInlined()) {
				currentIndentation = "";
			} else {
				for (int i = 0; i < currentBlockHeaderStartColumn; i++) {
					blockIndentation.append(SPACE);
				}
				if (block.getStatements().isEmpty()) {
					currentIndentation = blockIndentation.toString();
				} else {
					currentIndentation = blockIndentation.toString() + INDENTATION_SPACE;
				}
				insertNewLine();
			}
			if (!block.getStatements().isEmpty()) {
				for (int i = 0; i < block.getStatements().size() - 1; i++) {
					doSwitch(block.getStatements().get(i));
				}
				currentIndentation = blockIndentation.toString();
				doSwitch(block.getStatements().get(block.getStatements().size() - 1));
			}
		} finally {
			currentIndentation = savedIndentation;
		}

		return DUMMY;
	}

	@Override
	public Object caseBlockComment(BlockComment blockComment) {

		builder.append(AcceleoParser.BLOCK_COMMENT_START);
		doSwitch(blockComment.getBody());
		builder.append(AcceleoParser.BLOCK_COMMENT_END);

		return DUMMY;
	}

	@Override
	public Object caseComment(Comment comment) {
		builder.append(AcceleoParser.COMMENT_START);
		doSwitch(comment.getBody());
		builder.append(AcceleoParser.COMMENT_END);

		return DUMMY;
	}

	@Override
	public Object caseCommentBody(CommentBody commentBody) {
		builder.append(commentBody.getValue());

		return DUMMY;
	}

	@Override
	public Object caseDocumentation(Documentation documentation) {

		builder.append(AcceleoParser.DOCUMENTATION_START);
		doSwitch(documentation.getBody());
		builder.append(AcceleoParser.DOCUMENTATION_END);

		return DUMMY;
	}

	@Override
	public Object caseExpression(Expression expression) {
		builder.append(querySerializer.serialize(expression.getAst().getAst()));

		return DUMMY;
	}

	@Override
	public Object caseExpressionStatement(ExpressionStatement expressionStatement) {
		builder.append(AcceleoParser.EXPRESSION_STATEMENT_START);
		if (expressionStatement.getExpression() != null && (expressionStatement.getExpression()
				.getAql() instanceof Conditional || expressionStatement.getExpression()
						.getAql() instanceof Let)) {
			builder.append(SPACE);
		}
		doSwitch(expressionStatement.getExpression());
		builder.append(AcceleoParser.EXPRESSION_STATEMENT_END);
		if (expressionStatement.isNewLineNeeded()) {
			insertNewLine();
		}
		return DUMMY;
	}

	@Override
	public Object caseFileStatement(FileStatement fileStatement) {
		updateCurrentBlockHeaderStartColumn();
		builder.append(AcceleoParser.FILE_HEADER_START);
		builder.append(AcceleoParser.OPEN_PARENTHESIS);
		doSwitch(fileStatement.getUrl());
		builder.append(AcceleoParser.COMMA);
		builder.append(SPACE);
		builder.append(fileStatement.getMode().getName());
		if (fileStatement.getCharset() != null) {
			builder.append(AcceleoParser.COMMA);
			builder.append(SPACE);
			doSwitch(fileStatement.getCharset());
		}
		builder.append(AcceleoParser.CLOSE_PARENTHESIS);
		builder.append(AcceleoParser.FILE_HEADER_END);
		doSwitch(fileStatement.getBody());
		builder.append(AcceleoParser.FILE_END);
		return DUMMY;
	};

	@Override
	public Object caseForStatement(ForStatement forStatement) {
		updateCurrentBlockHeaderStartColumn();
		builder.append(AcceleoParser.FOR_HEADER_START);
		builder.append(AcceleoParser.OPEN_PARENTHESIS);
		bindingSeparator = AcceleoParser.PIPE;
		if (forStatement.getBinding() != null) {
			doSwitch(forStatement.getBinding());
		}
		builder.append(AcceleoParser.CLOSE_PARENTHESIS);

		if (forStatement.getSeparator() != null) {
			builder.append(SPACE);
			builder.append(AcceleoParser.FOR_SEPARATOR);
			doSwitch(forStatement.getSeparator());
			builder.append(AcceleoParser.CLOSE_PARENTHESIS);
		}
		builder.append(AcceleoParser.FOR_HEADER_END);
		doSwitch(forStatement.getBody());
		builder.append(AcceleoParser.FOR_END);

		return DUMMY;
	}

	@Override
	public Object caseIfStatement(IfStatement ifStatement) {
		updateCurrentBlockHeaderStartColumn();
		builder.append(AcceleoParser.IF_HEADER_START);
		builder.append(AcceleoParser.OPEN_PARENTHESIS);
		doSwitch(ifStatement.getCondition());
		builder.append(AcceleoParser.CLOSE_PARENTHESIS);
		builder.append(AcceleoParser.IF_HEADER_END);
		if (ifStatement.getThen() != null) {
			doSwitch(ifStatement.getThen());
		}
		if (ifStatement.getElse() != null) {
			final Block elseBlock = ifStatement.getElse();
			generateElse(elseBlock);
		}
		builder.append(AcceleoParser.IF_END);

		return DUMMY;
	}

	/**
	 * Serializes the given {@link Block} as a elseif or a else.
	 * 
	 * @param block
	 *            the {@link Block}
	 */
	private void generateElse(Block block) {
		updateCurrentBlockHeaderStartColumn();
		if (block.getStatements().size() == 1 && block.getStatements().get(0) instanceof IfStatement) {
			final IfStatement ifStatement = (IfStatement)block.getStatements().get(0);
			builder.append(AcceleoParser.IF_ELSEIF);
			builder.append(AcceleoParser.OPEN_PARENTHESIS);
			doSwitch(ifStatement.getCondition());
			builder.append(AcceleoParser.CLOSE_PARENTHESIS);
			builder.append(AcceleoParser.IF_HEADER_END);
			doSwitch(ifStatement.getThen());
			if (ifStatement.getElse() != null) {
				final Block elseBlock = ifStatement.getElse();
				generateElse(elseBlock);
			}
		} else {
			builder.append(AcceleoParser.IF_ELSE);
			doSwitch(block);
		}
	}

	@Override
	public Object caseImport(Import imp) {
		builder.append(AcceleoParser.IMPORT_START);
		doSwitch(imp.getModule());
		builder.append(AcceleoParser.IMPORT_END);
		return DUMMY;
	}

	@Override
	public Object caseLetStatement(LetStatement letStatement) {
		updateCurrentBlockHeaderStartColumn();
		builder.append(AcceleoParser.LET_HEADER_START);
		if (!letStatement.getVariables().isEmpty()) {
			final StringBuilder previousBuilder = builder;
			try {
				builder = new StringBuilder();
				bindingSeparator = AcceleoParser.EQUAL;
				for (Binding binding : letStatement.getVariables()) {
					doSwitch(binding);
					builder.append(AcceleoParser.COMMA);
					builder.append(SPACE);
				}
				previousBuilder.append(builder.substring(0, builder.length() - 2));
			} finally {
				builder = previousBuilder;
			}
		}
		builder.append(AcceleoParser.LET_HEADER_END);
		doSwitch(letStatement.getBody());
		builder.append(AcceleoParser.LET_END);

		return DUMMY;
	};

	@Override
	public Object caseMetamodel(Metamodel metamodel) {
		builder.append(AcceleoParser.QUOTE);
		if (metamodel.getReferencedPackage() != null) {
			builder.append(metamodel.getReferencedPackage().getNsURI());
		}
		builder.append(AcceleoParser.QUOTE);

		return DUMMY;
	}

	@Override
	public Object caseModule(Module module) {
		if (module.getDocumentation() != null) {
			doSwitch(module.getDocumentation());
			insertNewLine();
		}
		builder.append(AcceleoParser.MODULE_HEADER_START);
		builder.append(module.getName());
		builder.append(AcceleoParser.OPEN_PARENTHESIS);
		if (!module.getMetamodels().isEmpty()) {
			final StringBuilder previousBuilder = builder;
			try {
				builder = new StringBuilder();
				for (Metamodel metamodel : module.getMetamodels()) {
					doSwitch(metamodel);
					builder.append(AcceleoParser.COMMA);
					builder.append(SPACE);
				}
				previousBuilder.append(builder.substring(0, builder.length() - 2));
			} finally {
				builder = previousBuilder;
			}
		}
		builder.append(AcceleoParser.CLOSE_PARENTHESIS);
		if (module.getExtends() != null) {
			builder.append(SPACE);
			builder.append(AcceleoParser.EXTENDS);
			doSwitch(module.getExtends());
		}
		builder.append(AcceleoParser.MODULE_HEADER_END);

		if (!module.getImports().isEmpty()) {
			insertNewLine();
			for (Import importedModule : module.getImports()) {
				insertNewLine();
				doSwitch(importedModule);
			}
		}

		final List<ModuleElement> moduleElements = getSignificantModuleElements(module);
		final int numberOfElements = moduleElements.size();
		int currentElementIndex = 0;
		if (!moduleElements.isEmpty()) {
			insertNewLine();
			for (ModuleElement moduleElement : moduleElements) {
				insertNewLine();
				doSwitch(moduleElement);
				currentElementIndex++;
				if ((currentElementIndex < numberOfElements) && (moduleElement instanceof Template
						|| moduleElement instanceof Query)) {
					insertNewLine();
				}
			}
		}

		return DUMMY;
	}

	private List<ModuleElement> getSignificantModuleElements(Module module) {
		final List<ModuleElement> res = new ArrayList<ModuleElement>();

		for (ModuleElement moduleElement : module.getModuleElements()) {
			if (!(moduleElement instanceof Documentation) || ((Documentation)moduleElement)
					.getDocumentedElement() == null) {
				res.add(moduleElement);
			}
		}

		return res;
	}

	@Override
	public Object caseModuleDocumentation(ModuleDocumentation moduleDocumentation) {
		builder.append(AcceleoParser.DOCUMENTATION_START);
		doSwitch(moduleDocumentation.getBody());
		builder.append(AcceleoParser.DOCUMENTATION_END);
		return DUMMY;
	}

	@Override
	public Object caseModuleElementDocumentation(ModuleElementDocumentation moduleElementDocumentation) {
		builder.append(AcceleoParser.DOCUMENTATION_START);
		doSwitch(moduleElementDocumentation.getBody());
		builder.append(AcceleoParser.DOCUMENTATION_END);
		return DUMMY;
	}

	@Override
	public Object caseModuleReference(ModuleReference moduleReference) {
		builder.append(moduleReference.getQualifiedName());
		return DUMMY;
	}

	@Override
	public Object caseProtectedArea(ProtectedArea protectedArea) {
		updateCurrentBlockHeaderStartColumn();
		builder.append(AcceleoParser.PROTECTED_AREA_HEADER_START);
		builder.append(AcceleoParser.OPEN_PARENTHESIS);
		doSwitch(protectedArea.getId());
		builder.append(AcceleoParser.CLOSE_PARENTHESIS);
		if (protectedArea.getStartTagPrefix() != null) {
			builder.append(SPACE);
			builder.append(AcceleoParser.PROTECTED_AREA_START_TAG_PREFIX);
			doSwitch(protectedArea.getStartTagPrefix());
			builder.append(AcceleoParser.CLOSE_PARENTHESIS);
		}
		if (protectedArea.getEndTagPrefix() != null) {
			builder.append(SPACE);
			builder.append(AcceleoParser.PROTECTED_AREA_END_TAG_PREFIX);
			doSwitch(protectedArea.getEndTagPrefix());
			builder.append(AcceleoParser.CLOSE_PARENTHESIS);
		}
		builder.append(AcceleoParser.PROTECTED_AREA_HEADER_END);
		doSwitch(protectedArea.getBody());
		builder.append(AcceleoParser.PROTECTED_AREA_END);

		return DUMMY;
	}

	@Override
	public Object caseQuery(Query query) {
		if (query.getDocumentation() != null) {
			doSwitch(query.getDocumentation());
			insertNewLine();
		}
		builder.append(AcceleoParser.QUERY_START);
		builder.append(query.getVisibility());
		builder.append(SPACE);
		builder.append(query.getName());
		builder.append(AcceleoParser.OPEN_PARENTHESIS);
		if (!query.getParameters().isEmpty()) {
			final StringBuilder previousBuilder = builder;
			try {
				builder = new StringBuilder();
				for (Variable parameter : query.getParameters()) {
					doSwitch(parameter);
					builder.append(AcceleoParser.COMMA);
					builder.append(SPACE);
				}
				previousBuilder.append(builder.substring(0, builder.length() - 2));
			} finally {
				builder = previousBuilder;
			}
		}
		builder.append(AcceleoParser.CLOSE_PARENTHESIS);
		builder.append(SPACE);
		if (query.getType() != null) {
			builder.append(AcceleoParser.COLON);
			builder.append(SPACE);
			builder.append(querySerializer.serialize(query.getType().getAst()));
			builder.append(SPACE);
		}
		builder.append(AcceleoParser.EQUAL);
		builder.append(SPACE);
		doSwitch(query.getBody());
		builder.append(AcceleoParser.QUERY_END);

		return DUMMY;
	}

	@Override
	public Object caseTemplate(Template template) {
		if (template.getDocumentation() != null) {
			doSwitch(template.getDocumentation());
			insertNewLine();
		}
		updateCurrentBlockHeaderStartColumn();
		builder.append(AcceleoParser.TEMPLATE_HEADER_START);
		builder.append(template.getVisibility());
		builder.append(SPACE);
		builder.append(template.getName());
		builder.append(AcceleoParser.OPEN_PARENTHESIS);
		if (!template.getParameters().isEmpty()) {
			final StringBuilder previousBuilder = builder;
			try {
				builder = new StringBuilder();
				for (Variable parameter : template.getParameters()) {
					doSwitch(parameter);
					builder.append(AcceleoParser.COMMA);
					builder.append(SPACE);
				}
				previousBuilder.append(builder.substring(0, builder.length() - 2));
			} finally {
				builder = previousBuilder;
			}
		}
		builder.append(AcceleoParser.CLOSE_PARENTHESIS);
		if (template.getGuard() != null) {
			builder.append(SPACE);
			builder.append(AcceleoParser.TEMPLATE_GUARD);
			builder.append(SPACE);
			builder.append(AcceleoParser.OPEN_PARENTHESIS);
			doSwitch(template.getGuard());
			builder.append(AcceleoParser.CLOSE_PARENTHESIS);
		}
		if (template.getPost() != null) {
			builder.append(SPACE);
			builder.append(AcceleoParser.TEMPLATE_POST);
			doSwitch(template.getPost());
			builder.append(AcceleoParser.CLOSE_PARENTHESIS);
		}
		builder.append(AcceleoParser.TEMPLATE_HEADER_END);
		doSwitch(template.getBody());
		builder.append(AcceleoParser.TEMPLATE_END);

		return DUMMY;
	}

	@Override
	public Object caseTextStatement(TextStatement textStatement) {
		builder.append(textStatement.getValue());
		if (textStatement.isNewLineNeeded()) {
			insertNewLine();
		}
		return DUMMY;
	}

	@Override
	public Object caseVariable(Variable variable) {
		final String variableName = AstBuilder.protectWithUnderscore(variable.getName());
		builder.append(variableName);
		builder.append(SPACE);
		builder.append(AcceleoParser.COLON);
		builder.append(SPACE);
		builder.append(querySerializer.serialize(variable.getType().getAst()));

		return DUMMY;
	}

	@Override
	public Object caseNewLineStatement(NewLineStatement newLineStatement) {
		final int lastIndexOfNewLine = builder.lastIndexOf(newLine);
		if (!newLineStatement.isIndentationNeeded()) {
			if (builder.substring(lastIndexOfNewLine).trim().isEmpty()) {
				builder.setLength(lastIndexOfNewLine + newLine.length());
			} else {
				builder.append(newLine);
			}
		}
		insertNewLine();
		return DUMMY;
	}

	/**
	 * Inserts a new line.
	 */
	private void insertNewLine() {
		builder.append(newLine);
		builder.append(currentIndentation);
	}

	/**
	 * Updates the current block header start column to the current position.
	 */
	private void updateCurrentBlockHeaderStartColumn() {
		final int lastNewLineIndex = builder.lastIndexOf(newLine);
		if (lastNewLineIndex <= 0) {
			currentBlockHeaderStartColumn = builder.length();
		} else {
			currentBlockHeaderStartColumn = builder.length() - (lastNewLineIndex + newLine.length());
		}
	}

}
