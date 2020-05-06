/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.parser;

import org.eclipse.acceleo.ASTNode;
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
import org.eclipse.acceleo.ProtectedArea;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.TextStatement;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.query.parser.AstSerializer;
import org.eclipse.acceleo.util.AcceleoSwitch;

/**
 * Serializes {@link ASTNode}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoAstSerializer extends AcceleoSwitch<Object> {

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
	 * The resulting {@link StringBuilder}.
	 */
	private StringBuilder builder;

	/**
	 * The current binding separator.
	 */
	private String bindingSeparator;

	/**
	 * Serializes the given {@link ASTNode}.
	 * 
	 * @param node
	 *            the {@link ASTNode}
	 * @return the serialized {@link ASTNode}
	 */
	public String serialize(ASTNode node) {
		builder = new StringBuilder();

		doSwitch(node);

		return builder.toString();
	}

	@Override
	public Object caseBinding(Binding binding) {
		builder.append(binding.getName());
		builder.append(" : ");
		if (binding.getType() != null) {
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
		for (Statement statement : block.getStatements()) {
			doSwitch(statement);
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
		doSwitch(expressionStatement.getExpression());

		return DUMMY;
	}

	@Override
	public Object caseFileStatement(FileStatement fileStatement) {
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
		builder.append(AcceleoParser.FOR_HEADER_START);
		builder.append(AcceleoParser.OPEN_PARENTHESIS);
		bindingSeparator = AcceleoParser.PIPE;
		doSwitch(forStatement.getBinding());
		builder.append(AcceleoParser.CLOSE_PARENTHESIS);
		// TODO sperator()
		builder.append(AcceleoParser.FOR_HEADER_END);
		doSwitch(forStatement.getBody());
		builder.append(AcceleoParser.FOR_END);

		return DUMMY;
	}

	@Override
	public Object caseIfStatement(IfStatement ifStatement) {
		builder.append(AcceleoParser.IF_HEADER_START);
		builder.append(AcceleoParser.OPEN_PARENTHESIS);
		doSwitch(ifStatement.getCondition());
		builder.append(AcceleoParser.CLOSE_PARENTHESIS);
		builder.append(AcceleoParser.IF_HEADER_END);
		if (ifStatement.getThen() != null) {
			doSwitch(ifStatement.getElse());
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
		builder.append(metamodel.getReferencedPackage().getNsURI());
		builder.append(AcceleoParser.QUOTE);

		return DUMMY;
	}

	@Override
	public Object caseModule(Module module) {
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

		for (ModuleElement moduleElement : module.getModuleElements()) {
			builder.append("\n");
			doSwitch(moduleElement);
			builder.append("\n");
		}

		return DUMMY;
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
		builder.append(AcceleoParser.PROTECTED_AREA_HEADER_START);
		builder.append(AcceleoParser.OPEN_PARENTHESIS);
		doSwitch(protectedArea.getId());
		builder.append(AcceleoParser.CLOSE_PARENTHESIS);
		builder.append(AcceleoParser.PROTECTED_AREA_HEADER_END);
		doSwitch(protectedArea.getBody());
		builder.append(AcceleoParser.PROTECTED_AREA_END);

		return DUMMY;
	}

	@Override
	public Object caseQuery(Query query) {
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
			builder.append(SPACE);
		}
		builder.append(AcceleoParser.TEMPLATE_HEADER_END);
		doSwitch(template.getBody());
		builder.append(AcceleoParser.TEMPLATE_END);

		return DUMMY;
	}

	@Override
	public Object caseTextStatement(TextStatement textStatement) {
		builder.append(textStatement.getValue());

		return DUMMY;
	}

	@Override
	public Object caseVariable(Variable variable) {
		builder.append(variable.getName());
		builder.append(SPACE);
		builder.append(AcceleoParser.COLON);
		builder.append(SPACE);
		builder.append(querySerializer.serialize(variable.getType().getAst()));

		return DUMMY;
	}

}
