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

import java.util.Iterator;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Binding;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.BlockComment;
import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.CommentBody;
import org.eclipse.acceleo.Documentation;
import org.eclipse.acceleo.ErrorMetamodel;
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
import org.eclipse.emf.ecore.EObject;

/**
 * Serializes {@link ASTNode}.
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
	 * A new line.
	 */
	private static final char NEW_LINE = '\n';

	/**
	 * A dummy {@link Object} to prevent switching in super types.
	 */
	private static final Object DUMMY = new Object();

	/**
	 * The {@link AstSerializer}.
	 */
	private AstSerializer querySerializer = new AstSerializer();

	/**
	 * The resulting {@link IndentedStringBuilder}.
	 */
	private IndentedStringBuilder builder;

	/**
	 * The current binding separator.
	 */
	private String bindingSeparator;

	private class IndentedStringBuilder {
		/**
		 * An on/off switch.
		 */
		private boolean enabled = true;

		/**
		 * The current indentation.
		 */
		private int indentation;

		/**
		 * The internal builder.
		 */
		private StringBuilder stringBuilder = new StringBuilder();

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public void append(Object o) {
			if (o instanceof TextStatement) {
				String value = ((TextStatement)o).getValue();
				EObject eContainer = ((TextStatement)o).eContainer();
				boolean isLastOfABlock = eContainer instanceof Block && !(eContainer
						.eContainer() instanceof ProtectedArea) && ((Block)eContainer).getStatements()
								.indexOf(o) == (((Block)eContainer).getStatements().size() - 1);
				if (!value.isEmpty() && value.charAt(value.length() - 1) == NEW_LINE && isLastOfABlock) {
					append(value.substring(0, value.length() - 1));
					stringBuilder.append(NEW_LINE);
					stringBuilder.append(getIndentationString(indentation - 1));
				} else {
					append(value);
				}
			} else if (o instanceof Character) {
				append((char)o);
			} else {
				String s = String.valueOf(o);
				for (int i = 0; i < s.length(); i++) {
					append(s.charAt(i));
				}
			}
		}

		public void append(char c) {
			stringBuilder.append(c);
			if (NEW_LINE == c) {
				stringBuilder.append(getIndentationString(indentation));
			}
		}

		private String getIndentationString(int count) {
			if (!enabled) {
				return "";
			}
			StringBuilder res = new StringBuilder();
			for (int i = 0; i < count; i++) {
				res.append(INDENTATION_SPACE);
			}
			return res.toString();
		}

		public int length() {
			return stringBuilder.length();
		}

		public String substring(int i, int j) {
			return stringBuilder.substring(i, j);
		}

		@Override
		public String toString() {
			return stringBuilder.toString();
		}

		public void indent() {
			indentation++;
		}

		public void deindent() {
			indentation--;
		}
	}

	/**
	 * Serializes the given {@link ASTNode}.
	 * 
	 * @param node
	 *            the {@link ASTNode}
	 * @return the serialized {@link ASTNode}
	 */
	public String serialize(ASTNode node) {
		builder = new IndentedStringBuilder();

		doSwitch(node);

		return builder.toString();
	}

	@Override
	public Object caseBinding(Binding binding) {
		builder.append(binding.getName());
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
		builder.append(NEW_LINE);
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
		doSwitch(expressionStatement.getExpression());
		builder.append(AcceleoParser.EXPRESSION_STATEMENT_END);
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
		generateBlock(fileStatement.getBody());
		builder.append(AcceleoParser.FILE_END);
		return DUMMY;
	};

	@Override
	public Object caseForStatement(ForStatement forStatement) {
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
		generateBlock(forStatement.getBody());
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
			generateBlock(ifStatement.getThen());
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
			generateBlock(ifStatement.getThen());
			if (ifStatement.getElse() != null) {
				final Block elseBlock = ifStatement.getElse();
				generateElse(elseBlock);
			}
		} else {
			builder.append(AcceleoParser.IF_ELSE);
			generateBlock(block);
		}
	}

	@Override
	public Object caseImport(Import imp) {
		builder.append(AcceleoParser.IMPORT_START);
		doSwitch(imp.getModule());
		builder.append(AcceleoParser.IMPORT_END);
		builder.append(NEW_LINE);
		return DUMMY;
	}

	@Override
	public Object caseLetStatement(LetStatement letStatement) {
		builder.append(AcceleoParser.LET_HEADER_START);
		if (!letStatement.getVariables().isEmpty()) {
			final IndentedStringBuilder previousBuilder = builder;
			try {
				builder = new IndentedStringBuilder();
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
		generateBlock(letStatement.getBody());
		builder.append(AcceleoParser.LET_END);

		return DUMMY;
	};

	@Override
	public Object caseErrorMetamodel(ErrorMetamodel object) {
		return DUMMY;
	}

	@Override
	public Object caseMetamodel(Metamodel metamodel) {
		builder.append(AcceleoParser.QUOTE);
		builder.append(metamodel.getReferencedPackage().getNsURI());
		builder.append(AcceleoParser.QUOTE);

		return DUMMY;
	}

	@Override
	public Object caseModule(Module module) {
		if (module.getDocumentation() != null) {
			doSwitch(module.getDocumentation());
		}
		builder.append(AcceleoParser.MODULE_HEADER_START);
		builder.append(module.getName());
		builder.append(AcceleoParser.OPEN_PARENTHESIS);
		if (!module.getMetamodels().isEmpty()) {
			final IndentedStringBuilder previousBuilder = builder;
			try {
				builder = new IndentedStringBuilder();
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

		if (!module.getModuleElements().isEmpty() || !module.getImports().isEmpty()) {
			builder.append(NEW_LINE);
		}

		for (Import importedModule : module.getImports()) {
			doSwitch(importedModule);
		}

		// @formatter:off
		for (Iterator<ModuleElement> iterator = module.getModuleElements().iterator(); iterator.hasNext(); ) {
			// @formatter:on
			ModuleElement moduleElement = iterator.next();
			if (moduleElement instanceof Query || moduleElement instanceof Template) {
				builder.append(NEW_LINE);
				doSwitch(moduleElement);
				if (iterator.hasNext()) {
					builder.append(NEW_LINE);
				}
			} else {
				doSwitch(moduleElement);
			}
		}

		return DUMMY;
	}

	@Override
	public Object caseModuleDocumentation(ModuleDocumentation moduleDocumentation) {
		builder.append(AcceleoParser.DOCUMENTATION_START);
		doSwitch(moduleDocumentation.getBody());
		builder.append(AcceleoParser.DOCUMENTATION_END);
		builder.append(NEW_LINE);
		return DUMMY;
	}

	@Override
	public Object caseModuleElementDocumentation(ModuleElementDocumentation moduleElementDocumentation) {
		builder.append(AcceleoParser.DOCUMENTATION_START);
		doSwitch(moduleElementDocumentation.getBody());
		builder.append(AcceleoParser.DOCUMENTATION_END);
		builder.append(NEW_LINE);
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
		if (query.getDocumentation() != null) {
			doSwitch(query.getDocumentation());
		}
		builder.append(AcceleoParser.QUERY_START);
		builder.append(query.getVisibility());
		builder.append(SPACE);
		builder.append(query.getName());
		builder.append(AcceleoParser.OPEN_PARENTHESIS);
		if (!query.getParameters().isEmpty()) {
			final IndentedStringBuilder previousBuilder = builder;
			try {
				builder = new IndentedStringBuilder();
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
		}
		builder.append(AcceleoParser.TEMPLATE_HEADER_START);
		builder.append(template.getVisibility());
		builder.append(SPACE);
		builder.append(template.getName());
		builder.append(AcceleoParser.OPEN_PARENTHESIS);
		if (!template.getParameters().isEmpty()) {
			final IndentedStringBuilder previousBuilder = builder;
			try {
				builder = new IndentedStringBuilder();
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
		generateBlock(template.getBody());
		builder.append(AcceleoParser.TEMPLATE_END);

		return DUMMY;
	}

	// NO FORMAT
	// private void generateBlock(Block block) {
	// for (Statement statement : block.getStatements()) {
	// doSwitch(statement);
	// }
	// }

	private void generateBlock(Block block) {
		if (!block.getStatements().isEmpty()) {
			builder.indent();
			EObject container = block.eContainer();
			if (container instanceof Template || isNewLineStatement(container)) {
				builder.append(NEW_LINE);
			}
			Statement lastStatement = block.getStatements().get(block.getStatements().size() - 1);
			for (Statement statement : block.getStatements()) {
				doSwitch(statement);
				if (statement.equals(lastStatement)) {
					builder.deindent();
				}
				if (isNewLineStatement(statement) || statement instanceof IfStatement) {
					builder.append(NEW_LINE);
				}
			}
		}
	}

	private boolean isNewLineStatement(EObject eObject) {
		boolean res = false;
		switch (eObject.eClass().getClassifierID()) {
			case AcceleoPackage.FILE_STATEMENT:
				res = true;
				break;
			case AcceleoPackage.LET_STATEMENT:
				res = true;
				break;
			case AcceleoPackage.FOR_STATEMENT:
				res = true;
				break;
			case AcceleoPackage.PROTECTED_AREA:
				res = true;
				break;
			default:
				break;
		}
		return res;
	}

	@Override
	public Object caseTextStatement(TextStatement textStatement) {
		builder.append(textStatement);
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
