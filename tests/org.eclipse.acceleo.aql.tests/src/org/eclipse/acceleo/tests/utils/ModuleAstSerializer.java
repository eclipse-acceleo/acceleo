/*******************************************************************************
 *  Copyright (c) 2016, 2024 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.acceleo.tests.utils;

import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.AcceleoASTNode;
import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.BlockComment;
import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.CommentBody;
import org.eclipse.acceleo.ErrorBinding;
import org.eclipse.acceleo.ErrorComment;
import org.eclipse.acceleo.ErrorExpressionStatement;
import org.eclipse.acceleo.ErrorFileStatement;
import org.eclipse.acceleo.ErrorForStatement;
import org.eclipse.acceleo.ErrorIfStatement;
import org.eclipse.acceleo.ErrorImport;
import org.eclipse.acceleo.ErrorLetStatement;
import org.eclipse.acceleo.ErrorMargin;
import org.eclipse.acceleo.ErrorMetamodel;
import org.eclipse.acceleo.ErrorModule;
import org.eclipse.acceleo.ErrorProtectedArea;
import org.eclipse.acceleo.ErrorQuery;
import org.eclipse.acceleo.ErrorTemplate;
import org.eclipse.acceleo.ErrorVariable;
import org.eclipse.acceleo.ExpressionStatement;
import org.eclipse.acceleo.FileStatement;
import org.eclipse.acceleo.ForStatement;
import org.eclipse.acceleo.IfStatement;
import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleDocumentation;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.ModuleElementDocumentation;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.acceleo.NewLineStatement;
import org.eclipse.acceleo.ParameterDocumentation;
import org.eclipse.acceleo.ProtectedArea;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.TextStatement;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.query.ast.Binding;
import org.eclipse.acceleo.query.ast.BooleanLiteral;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.ast.ClassTypeLiteral;
import org.eclipse.acceleo.query.ast.CollectionTypeLiteral;
import org.eclipse.acceleo.query.ast.Conditional;
import org.eclipse.acceleo.query.ast.EClassifierTypeLiteral;
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.Error;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.IntegerLiteral;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.Let;
import org.eclipse.acceleo.query.ast.NullLiteral;
import org.eclipse.acceleo.query.ast.RealLiteral;
import org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral;
import org.eclipse.acceleo.query.ast.SetInExtensionLiteral;
import org.eclipse.acceleo.query.ast.StringLiteral;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.ast.TypeSetLiteral;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.ast.util.AstSwitch;
import org.eclipse.acceleo.util.AcceleoSwitch;

/**
 * Serialize a {@link Template}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ModuleAstSerializer extends AcceleoSwitch<Void> {

	/**
	 * The space character.
	 */
	private static final String SPACE = " ";

	/** Prefix we'll use for the error messages on missing names. */
	private static final String MISSING_NAME_MESSAGE_PREFIX = "missing name: ";

	/** Prefix we'll use for the error messages on a missing open parenthesis. */
	private static final String MISSING_OPEN_PARENTHESIS_MESSAGE_PREFIX = "missing open parenthesis: ";

	/** Prefix we'll use for the error messages on a missing closing parenthesis. */
	private static final String MISSING_CLOSE_PARENTHESIS_MESSAGE_PREFIX = "missing close parenthesis: ";

	/** Prefix we'll use for the error messages on a missing start tag prefix closing parenthesis. */
	private static final String MISSING_START_TAG_PREFIX_CLOSE_PARENTHESIS_MESSAGE_PREFIX = "missing start tag prefix close parenthesis: ";

	/** Prefix we'll use for the error messages on a missing end tag prefix closing parenthesis. */
	private static final String MISSING_END_TAG_PREFIX_CLOSE_PARENTHESIS_MESSAGE_PREFIX = "missing end tag prefix close parenthesis: ";

	/** Prefix we'll use for the error messages on a missing separator closing parenthesis. */
	private static final String MISSING_SEPARATOR_CLOSE_PARENTHESIS_MESSAGE_PREFIX = "missing separator close parenthesis: ";

	/** Prefix we'll use for the error messages on a missing header ending. */
	private static final String MISSING_END_HEADER_MESSAGE_PREFIX = "missing end header: ";

	/** Prefix we'll use for the error messages on a missing block ending. */
	private static final String MISSING_END_MESSAGE_PREFIX = "missing end: ";

	/** Prefix we'll use for the error messages on a missing colon. */
	private static final String MISSING_COLON_MESSAGE_PREFIX = "missing colon: ";

	/** Prefix we'll use for the error messages on a missing type. */
	private static final String MISSING_TYPE_MESSAGE_PREFIX = "missing type: ";

	/** The string we'll use as a placeholder for null objects. */
	private static final String NULL_STRING = "null";

	/**
	 * Serialize a {@link Expression}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private final class QueryAstSerializer extends AstSwitch<Void> {

		/**
		 * The {@link StringBuilder} used to serialize.
		 */
		private StringBuilder builder;

		/**
		 * Serializes the given {@link Expression}.
		 * 
		 * @param expression
		 *            the {@link Expression}
		 * @return the serialized {@link Expression}
		 */
		public String serialize(Expression expression) {
			builder = new StringBuilder();

			doSwitch(expression);

			return builder.toString();
		}

		@Override
		public Void caseBinding(Binding binding) {
			builder.append(binding.getName());
			if (binding.getType() != null) {
				builder.append(' ').append(':').append(' ');
				builder.append(doSwitch(binding.getType()));
			}
			builder.append(' ').append('=').append(' ');
			builder.append(doSwitch(binding.getValue()));
			return null;
		}

		@Override
		public Void caseBooleanLiteral(BooleanLiteral booleanLiteral) {
			builder.append(booleanLiteral.isValue());
			return null;
		}

		@Override
		public Void caseCall(Call call) {
			if (call.getType() == CallType.COLLECTIONCALL) {
				builder.append("->");
			} else {
				builder.append('.');
			}
			if (call.isSuperCall()) {
				builder.append("super:");
			}
			builder.append(call.getServiceName());
			builder.append('(');
			final StringBuilder previousBuilder = builder;
			builder = new StringBuilder();
			for (Expression argument : call.getArguments()) {
				doSwitch(argument);
				builder.append(',').append(' ');
			}
			if (builder.length() > 0) {
				previousBuilder.append(builder.substring(0, builder.length() - 2));
			}
			builder = previousBuilder;
			builder.append(')');
			return null;
		}

		@Override
		public Void caseCollectionTypeLiteral(CollectionTypeLiteral collectionTypeLiteral) {
			if (collectionTypeLiteral.getValue() == List.class) {
				builder.append("Sequence(");
			} else if (collectionTypeLiteral.getValue() == Set.class) {
				builder.append("OrderedSet(");
			} else {
				builder.append("***invalid type of collection ***(");
			}
			doSwitch(collectionTypeLiteral.getElementType());
			builder.append(")");
			return null;
		}

		@Override
		public Void caseConditional(Conditional conditional) {
			builder.append("if (");
			doSwitch(conditional.getPredicate());
			builder.append(") then ");
			doSwitch(conditional.getTrueBranch());
			builder.append(" else ");
			doSwitch(conditional.getFalseBranch());
			builder.append(" endif ");
			return null;
		}

		@Override
		public Void caseEnumLiteral(EnumLiteral enumLiteral) {
			builder.append(enumLiteral.getEPackageName());
			builder.append("::");
			builder.append(enumLiteral.getEEnumName());
			builder.append("::");
			builder.append(enumLiteral.getEEnumLiteralName());
			return null;
		}

		@Override
		public Void caseError(Error error) {
			builder.append("***ERROR***");
			return null;
		}

		@Override
		public Void caseIntegerLiteral(IntegerLiteral object) {
			builder.append(object.getValue());
			return null;
		}

		@Override
		public Void caseLambda(Lambda lambda) {
			doSwitch(lambda.getParameters().get(0));
			builder.append(" | ");
			doSwitch(lambda.getExpression());
			return null;
		}

		@Override
		public Void caseLet(Let let) {
			builder.append("let ");
			final StringBuilder previousBuilder = builder;
			builder = new StringBuilder();
			for (Binding binding : let.getBindings()) {
				doSwitch(binding);
				builder.append(',').append(' ');
			}
			previousBuilder.append(builder.substring(0, builder.length() - 2));
			builder.append(" in ");
			doSwitch(let.getBody());
			return super.caseLet(let);
		}

		@Override
		public Void caseNullLiteral(NullLiteral nullLiteral) {
			builder.append(NULL_STRING);
			return null;
		}

		@Override
		public Void caseSequenceInExtensionLiteral(SequenceInExtensionLiteral sequenceInExtensionLiteral) {
			builder.append("Sequence{");
			if (!sequenceInExtensionLiteral.getValues().isEmpty()) {
				for (Expression value : sequenceInExtensionLiteral.getValues()) {
					doSwitch(value);
					builder.append(',').append(' ');
				}
			}
			builder.append('}');
			return null;
		}

		@Override
		public Void caseRealLiteral(RealLiteral realLiteral) {
			builder.append(realLiteral.getValue());
			return null;
		}

		@Override
		public Void caseSetInExtensionLiteral(SetInExtensionLiteral setInExtensionLiteral) {
			builder.append("OrderedSet{");
			if (!setInExtensionLiteral.getValues().isEmpty()) {
				final StringBuilder previousBuilder = builder;
				builder = new StringBuilder();
				for (Expression value : setInExtensionLiteral.getValues()) {
					doSwitch(value);
					builder.append(',').append(' ');
				}
				previousBuilder.append(builder.substring(0, builder.length() - 2));
			}
			builder.append('}');
			return null;
		}

		@Override
		public Void caseStringLiteral(StringLiteral stringLiteral) {
			builder.append('\'');
			builder.append(stringLiteral.getValue());
			builder.append('\'');
			return null;
		}

		@Override
		public Void caseTypeSetLiteral(TypeSetLiteral typeSetLiteral) {
			builder.append('{');
			if (!typeSetLiteral.getTypes().isEmpty()) {
				final StringBuilder previousBuilder = builder;
				builder = new StringBuilder();
				try {
					for (TypeLiteral type : typeSetLiteral.getTypes()) {
						doSwitch(type);
						builder.append(" | ");
					}
					previousBuilder.append(builder.substring(0, builder.length() - 3));
				} finally {
					builder = previousBuilder;
				}
			}
			builder.append('}');
			return null;
		}

		@Override
		public Void caseClassTypeLiteral(ClassTypeLiteral object) {
			if (object.getValue() != null) {
				builder.append(object.getValue().getName());
			}
			return null;
		}

		@Override
		public Void caseEClassifierTypeLiteral(EClassifierTypeLiteral object) {
			builder.append(object.getEClassifierName());
			return null;
		}

		@Override
		public Void caseVariableDeclaration(VariableDeclaration variableDeclaration) {
			builder.append(variableDeclaration.getName());
			if (variableDeclaration.getType() != null) {
				builder.append(' ').append(':').append(' ');
				doSwitch(variableDeclaration.getType());
			}
			builder.append(' ').append('=').append(' ');
			doSwitch(variableDeclaration.getExpression());
			return null;
		}

		@Override
		public Void caseVarRef(VarRef varRef) {
			builder.append(varRef.getVariableName());
			return null;
		}

	}

	/**
	 * The {@link StringBuilder} used to serialize.
	 */
	private StringBuilder builder;

	/**
	 * The {@link QueryAstSerializer}.
	 */
	private QueryAstSerializer querySerializer = new QueryAstSerializer();

	/**
	 * Current indentation.
	 */
	private String indentation;

	/**
	 * The {@link AcceleoAstResult}.
	 */
	private AcceleoAstResult ast;

	/**
	 * Increases indentation.
	 */
	protected void indent() {
		indentation = indentation + "  ";
	}

	/**
	 * Decreases indentation.
	 */
	protected void deindent() {
		indentation = indentation.substring(0, indentation.length() - 2);
	}

	/**
	 * Serializes the given {@link AcceleoAstResult}.
	 * 
	 * @param astResult
	 *            the {@link AcceleoAstResult}
	 * @return the serialized {@link AcceleoAstResult}
	 */
	public String serialize(AcceleoAstResult astResult) {
		this.ast = astResult;
		builder = new StringBuilder();
		indentation = "";

		doSwitch(astResult.getModule());

		return builder.toString();
	}

	/**
	 * Creates a new line with the right indentation.
	 */
	protected void newLine() {
		builder.append(AbstractLanguageTestSuite.DEFAULT_END_OF_LINE_CHARACTER + indentation);
	}

	@Override
	public Void caseModule(Module module) {
		if (module.getDocumentation() != null) {
			doSwitch(module.getDocumentation());
		}
		newLine();
		builder.append("header position " + module.getStartHeaderPosition());
		builder.append("..");
		builder.append(module.getEndHeaderPosition());
		newLine();
		builder.append("module " + module.getName());
		if (module.getEncoding() != null) {
			newLine();
			builder.append("encoding ");
			builder.append(module.getEncoding());
		}
		indent();
		for (Metamodel metamodel : module.getMetamodels()) {
			newLine();
			doSwitch(metamodel);
		}
		if (module.getExtends() != null) {
			newLine();
			builder.append("extends ");
			doSwitch(module.getExtends());
		}
		for (Import imported : module.getImports()) {
			newLine();
			doSwitch(imported);
		}
		for (ModuleElement element : module.getModuleElements()) {
			newLine();
			doSwitch(element);
		}
		deindent();

		return null;
	}

	@Override
	public Void caseErrorModule(ErrorModule errorModule) {
		builder.append("*** error module ***");
		newLine();
		builder.append(MISSING_OPEN_PARENTHESIS_MESSAGE_PREFIX + errorModule.getMissingOpenParenthesis());
		newLine();
		builder.append("missing EPackage: " + errorModule.getMissingEPackage());
		newLine();
		builder.append(MISSING_CLOSE_PARENTHESIS_MESSAGE_PREFIX + errorModule.getMissingCloseParenthesis());
		newLine();
		builder.append(MISSING_END_HEADER_MESSAGE_PREFIX + errorModule.getMissingEndHeader());
		newLine();

		return null;
	}

	@Override
	public Void caseImport(Import imp) {
		builder.append("imports ");
		doSwitch(imp.getModule());

		return null;
	}

	@Override
	public Void caseErrorImport(ErrorImport errorImport) {
		builder.append("*** error import ***");
		newLine();
		builder.append(MISSING_END_MESSAGE_PREFIX + errorImport.getMissingEnd());
		newLine();

		return null;
	}

	@Override
	public Void caseModuleReference(ModuleReference moduleReference) {
		builder.append(moduleReference.getQualifiedName());

		return null;
	}

	@Override
	public Void caseMetamodel(Metamodel metamodel) {
		if (metamodel.getReferencedPackage() != null) {
			builder.append("metamodel " + metamodel.getReferencedPackage().getNsURI());
		} else {
			builder.append("metamodel null");
		}
		return null;
	}

	@Override
	public Void caseErrorMetamodel(ErrorMetamodel errorMetamodel) {
		builder.append("*** error metamodel ***");
		newLine();
		builder.append("missing end quote: " + errorMetamodel.getMissingEndQuote());
		newLine();

		return null;
	}

	@Override
	public Void caseTemplate(Template template) {
		if (template.getDocumentation() != null) {
			doSwitch(template.getDocumentation());
		}
		newLine();
		builder.append(template.getVisibility().getName());
		builder.append(" template " + template.getName() + '(');
		final StringBuilder previousBuilder = builder;
		builder = new StringBuilder();
		for (Variable variable : template.getParameters()) {
			doSwitch(variable);
			builder.append(", ");
		}
		if (builder.length() >= 2) {
			previousBuilder.append(builder.substring(0, builder.length() - 2));
		}
		builder = previousBuilder;
		builder.append(')');
		if (template.isMain()) {
			newLine();
			builder.append("@main");
		}
		if (template.getGuard() != null) {
			newLine();
			builder.append("guard ");
			doSwitch(template.getGuard());
		}
		if (template.getPost() != null) {
			newLine();
			builder.append("post ");
			doSwitch(template.getPost());
		}
		doSwitch(template.getBody());
		newLine();
		builder.append("[/template]");

		return null;
	}

	@Override
	public Void caseErrorTemplate(ErrorTemplate errorTemplate) {
		builder.append("*** error template ***");
		newLine();
		builder.append("missing visibility: " + errorTemplate.getMissingVisibility());
		newLine();
		builder.append(MISSING_NAME_MESSAGE_PREFIX + errorTemplate.getMissingName());
		newLine();
		builder.append(MISSING_OPEN_PARENTHESIS_MESSAGE_PREFIX + errorTemplate.getMissingOpenParenthesis());
		newLine();
		builder.append("missing parameters: " + errorTemplate.getMissingParameters());
		newLine();
		builder.append(MISSING_CLOSE_PARENTHESIS_MESSAGE_PREFIX + errorTemplate.getMissingCloseParenthesis());
		newLine();
		builder.append("missing guard open parenthesis: " + errorTemplate.getMissingGuardOpenParenthesis());
		newLine();
		builder.append("missing guard close parenthesis: " + errorTemplate.getMissingGuardCloseParenthesis());
		newLine();
		builder.append("missing post close parenthesis: " + errorTemplate.getMissingPostCloseParenthesis());
		newLine();
		builder.append("missing post end header: " + errorTemplate.getMissingEndHeader());
		newLine();
		builder.append("missing post end: " + errorTemplate.getMissingEnd());
		newLine();

		return null;
	}

	@Override
	public Void caseBlock(Block block) {
		indent();
		for (Statement statement : block.getStatements()) {
			newLine();
			doSwitch(statement);
		}
		deindent();

		return null;
	}

	@Override
	public Void caseBinding(org.eclipse.acceleo.Binding binding) {
		builder.append(binding.getName());
		builder.append(" : ");
		if (binding.getType() != null) {
			builder.append(querySerializer.serialize(binding.getType().getAst()));
		} else {
			builder.append(NULL_STRING);
		}
		builder.append(" = ");
		doSwitch(binding.getInitExpression());

		return null;
	}

	@Override
	public Void caseErrorBinding(ErrorBinding errorBinding) {
		builder.append("*** error binding ***");
		newLine();
		builder.append(MISSING_NAME_MESSAGE_PREFIX + errorBinding.getMissingName());
		newLine();
		builder.append(MISSING_COLON_MESSAGE_PREFIX + errorBinding.getMissingColon());
		newLine();
		builder.append(MISSING_TYPE_MESSAGE_PREFIX + errorBinding.getMissingType());
		newLine();
		builder.append("missing affectation symbol: " + errorBinding.getMissingAffectationSymbole());
		newLine();
		builder.append("missing affectation symbol position: " + errorBinding
				.getMissingAffectationSymbolePosition());
		newLine();

		return null;
	}

	@Override
	public Void caseBlockComment(BlockComment blockComment) {
		builder.append("[blockComment]");

		return null;
	}

	@Override
	public Void caseComment(Comment comment) {
		builder.append("[comment ");
		doSwitch(comment.getBody());
		builder.append(" /]");

		return null;
	}

	@Override
	public Void caseErrorComment(ErrorComment errorComment) {
		builder.append("*** error comment ***");
		newLine();
		builder.append("missing end header: " + errorComment.getMissingEndHeader());
		newLine();
		return null;
	}

	@Override
	public Void caseCommentBody(CommentBody commentBody) {
		builder.append(commentBody.getValue().replaceAll("\r\n", "\n"));

		return null;
	}

	@Override
	public Void caseExpression(org.eclipse.acceleo.Expression expression) {
		builder.append(querySerializer.serialize(expression.getAst().getAst()));

		return null;
	}

	@Override
	public Void caseFileStatement(FileStatement file) {
		builder.append("[file url ");
		doSwitch(file.getUrl());
		builder.append(" mode ");
		builder.append(file.getMode().getName());
		if (file.getCharset() != null) {
			builder.append(" charset ");
			doSwitch(file.getCharset());
		}
		doSwitch(file.getBody());
		newLine();
		builder.append("[/file]");

		return null;
	}

	@Override
	public Void caseErrorFileStatement(ErrorFileStatement errorFileStatement) {
		builder.append("*** error file statement ***");
		newLine();
		builder.append("missing open mode: " + errorFileStatement.getMissingOpenMode());
		newLine();
		builder.append(MISSING_OPEN_PARENTHESIS_MESSAGE_PREFIX + errorFileStatement
				.getMissingOpenParenthesis());
		newLine();
		builder.append("missing comma: " + errorFileStatement.getMissingComma());
		newLine();
		builder.append(MISSING_CLOSE_PARENTHESIS_MESSAGE_PREFIX + errorFileStatement
				.getMissingCloseParenthesis());
		newLine();
		builder.append(MISSING_END_HEADER_MESSAGE_PREFIX + errorFileStatement.getMissingEndHeader());
		newLine();
		builder.append(MISSING_END_MESSAGE_PREFIX + errorFileStatement.getMissingEnd());
		newLine();

		return null;
	}

	@Override
	public Void caseForStatement(ForStatement forStatement) {
		builder.append("[for ");
		if (forStatement.getBinding() != null) {
			doSwitch(forStatement.getBinding());
		} else {
			builder.append("null binding");
		}
		if (forStatement.getSeparator() != null) {
			builder.append(" separator ");
			doSwitch(forStatement.getSeparator());
		}
		doSwitch(forStatement.getBody());
		newLine();
		builder.append("[/for]");

		return null;
	}

	@Override
	public Void caseErrorForStatement(ErrorForStatement errorForStatement) {
		builder.append("*** error for statement ***");
		newLine();
		builder.append(MISSING_OPEN_PARENTHESIS_MESSAGE_PREFIX + errorForStatement
				.getMissingOpenParenthesis());
		newLine();
		builder.append("missing binding: " + errorForStatement.getMissingBinding());
		newLine();
		builder.append(MISSING_CLOSE_PARENTHESIS_MESSAGE_PREFIX + errorForStatement
				.getMissingCloseParenthesis());
		newLine();
		builder.append(MISSING_SEPARATOR_CLOSE_PARENTHESIS_MESSAGE_PREFIX + errorForStatement
				.getMissingSeparatorCloseParenthesis());
		newLine();
		builder.append(MISSING_END_HEADER_MESSAGE_PREFIX + errorForStatement.getMissingEndHeader());
		newLine();
		builder.append(MISSING_END_MESSAGE_PREFIX + errorForStatement.getMissingEnd());
		newLine();

		return null;
	}

	@Override
	public Void caseIfStatement(IfStatement ifStatement) {
		builder.append("[if ");
		doSwitch(ifStatement.getCondition());
		doSwitch(ifStatement.getThen());
		if (ifStatement.getElse() != null) {
			newLine();
			builder.append("[else]");
			doSwitch(ifStatement.getElse());
		}
		newLine();
		builder.append("[/if]");

		return null;
	}

	@Override
	public Void caseErrorIfStatement(ErrorIfStatement errorIfStatement) {
		builder.append("*** error if statement ***");
		newLine();
		builder.append(MISSING_OPEN_PARENTHESIS_MESSAGE_PREFIX + errorIfStatement
				.getMissingOpenParenthesis());
		newLine();
		builder.append(MISSING_CLOSE_PARENTHESIS_MESSAGE_PREFIX + errorIfStatement
				.getMissingCloseParenthesis());
		newLine();
		builder.append(MISSING_END_HEADER_MESSAGE_PREFIX + errorIfStatement.getMissingEndHeader());
		newLine();
		builder.append(MISSING_END_MESSAGE_PREFIX + errorIfStatement.getMissingEnd());
		newLine();

		return null;
	}

	@Override
	public Void caseLetStatement(org.eclipse.acceleo.LetStatement let) {
		builder.append("[let ");
		for (Variable variable : let.getVariables()) {
			newLine();
			doSwitch(variable);
		}

		doSwitch(let.getBody());
		newLine();
		builder.append("[/let]");

		return null;
	}

	@Override
	public Void caseErrorLetStatement(ErrorLetStatement errorLetStatement) {
		builder.append("*** error let statement ***");
		newLine();
		builder.append("missing bindings: " + errorLetStatement.getMissingBindings());
		newLine();
		builder.append(MISSING_END_HEADER_MESSAGE_PREFIX + errorLetStatement.getMissingEndHeader());
		newLine();
		builder.append(MISSING_END_MESSAGE_PREFIX + errorLetStatement.getMissingEnd());
		newLine();

		return null;
	}

	@Override
	public Void caseQuery(Query query) {
		if (query.getDocumentation() != null) {
			doSwitch(query.getDocumentation());
		}
		builder.append(query.getVisibility().getName());
		builder.append(" query " + query.getName() + '(');
		final StringBuilder previousBuilder = builder;
		if (!query.getParameters().isEmpty()) {
			builder = new StringBuilder();
			for (Variable variable : query.getParameters()) {
				doSwitch(variable);
				builder.append(", ");
			}
			previousBuilder.append(builder.substring(0, builder.length() - 2));
			builder = previousBuilder;
		}
		builder.append(") ");
		builder.append(") : ");
		if (query.getType() != null) {
			builder.append(querySerializer.serialize(query.getType().getAst()));
		} else {
			builder.append(NULL_STRING);
		}
		builder.append(' ');
		doSwitch(query.getBody());
		newLine();
		builder.append("/]");

		return null;
	}

	@Override
	public Void caseErrorQuery(ErrorQuery errorQuery) {
		builder.append("*** error query ***");
		newLine();
		builder.append("missing visibility: " + errorQuery.getMissingVisibility());
		newLine();
		builder.append(MISSING_NAME_MESSAGE_PREFIX + errorQuery.getMissingName());
		newLine();
		builder.append(MISSING_OPEN_PARENTHESIS_MESSAGE_PREFIX + errorQuery.getMissingOpenParenthesis());
		newLine();
		builder.append("missing parameters: " + errorQuery.getMissingParameters());
		newLine();
		builder.append(MISSING_CLOSE_PARENTHESIS_MESSAGE_PREFIX + errorQuery.getMissingCloseParenthesis());
		newLine();
		builder.append(MISSING_COLON_MESSAGE_PREFIX + errorQuery.getMissingColon());
		newLine();
		builder.append(MISSING_TYPE_MESSAGE_PREFIX + errorQuery.getMissingType());
		newLine();
		builder.append("missing equal: " + errorQuery.getMissingEqual());
		newLine();
		builder.append(MISSING_END_MESSAGE_PREFIX + errorQuery.getMissingEnd());
		newLine();

		return null;
	}

	@Override
	public Void caseTextStatement(TextStatement text) {
		builder.append(text.getValue());
		if (text.isNewLineNeeded()) {
			builder.append(" (newLineNeeded)");
		}
		return null;
	}

	@Override
	public Void caseNewLineStatement(NewLineStatement newLineStatement) {
		builder.append("NEW_LINE ");
		if (newLineStatement.isIndentationNeeded()) {
			builder.append("(indentationNeeded)" + SPACE);
		}
		return null;
	}

	@Override
	public Void caseExpressionStatement(ExpressionStatement expressionStatement) {
		builder.append('[');
		doSwitch(expressionStatement.getExpression());
		builder.append("/]");
		if (expressionStatement.isNewLineNeeded()) {
			builder.append(" (newLineNeeded");
		}

		return null;
	}

	@Override
	public Void caseErrorExpressionStatement(ErrorExpressionStatement errorExpressionStatement) {
		builder.append("*** error expression statement ***");
		newLine();
		builder.append(MISSING_END_HEADER_MESSAGE_PREFIX + errorExpressionStatement.getMissingEndHeader());
		newLine();

		return null;
	}

	@Override
	public Void caseVariable(Variable variable) {
		if (variable.eClass() == AcceleoPackage.eINSTANCE.getVariable()) {
			builder.append(variable.getName());
			builder.append(" : ");
			if (variable.getType() != null) {
				builder.append(querySerializer.serialize(variable.getType().getAst()));
			} else {
				builder.append(NULL_STRING);
			}
		}

		return null;
	}

	@Override
	public Void caseErrorVariable(ErrorVariable errorVariable) {
		builder.append("*** error variable ***");
		newLine();
		builder.append(MISSING_NAME_MESSAGE_PREFIX + errorVariable.getMissingName());
		newLine();
		builder.append(MISSING_COLON_MESSAGE_PREFIX + errorVariable.getMissingColon());
		newLine();
		builder.append(MISSING_TYPE_MESSAGE_PREFIX + errorVariable.getMissingType());
		newLine();

		return null;
	}

	@Override
	public Void caseProtectedArea(ProtectedArea protectedArea) {
		builder.append("[protected ");
		doSwitch(protectedArea.getId());
		doSwitch(protectedArea.getBody());
		if (protectedArea.getStartTagPrefix() != null) {
			builder.append(" startTagPrefix ");
			doSwitch(protectedArea.getStartTagPrefix());
		}
		if (protectedArea.getEndTagPrefix() != null) {
			builder.append(" endTagPrefix ");
			doSwitch(protectedArea.getEndTagPrefix());
		}
		newLine();
		builder.append("[/protected]");

		return null;
	}

	@Override
	public Void caseErrorProtectedArea(ErrorProtectedArea errorProtectedArea) {
		builder.append("*** error protected area ***");
		newLine();
		builder.append(MISSING_OPEN_PARENTHESIS_MESSAGE_PREFIX + errorProtectedArea
				.getMissingOpenParenthesis());
		newLine();
		builder.append(MISSING_CLOSE_PARENTHESIS_MESSAGE_PREFIX + errorProtectedArea
				.getMissingCloseParenthesis());
		newLine();
		builder.append(MISSING_START_TAG_PREFIX_CLOSE_PARENTHESIS_MESSAGE_PREFIX + errorProtectedArea
				.getMissingStartTagPrefixCloseParenthesis());
		newLine();
		builder.append(MISSING_END_TAG_PREFIX_CLOSE_PARENTHESIS_MESSAGE_PREFIX + errorProtectedArea
				.getMissingEndTagPrefixCloseParenthesis());
		newLine();
		builder.append(MISSING_END_HEADER_MESSAGE_PREFIX + errorProtectedArea.getMissingEndHeader());
		newLine();
		builder.append(MISSING_END_MESSAGE_PREFIX + errorProtectedArea.getMissingEnd());
		newLine();

		return null;
	}

	@Override
	public Void caseModuleDocumentation(ModuleDocumentation modueDocumentation) {
		doSwitch(modueDocumentation.getBody());
		newLine();
		builder.append("author: " + modueDocumentation.getAuthor());
		newLine();
		builder.append("version: " + modueDocumentation.getVersion());
		newLine();
		builder.append("since: " + modueDocumentation.getSince());
		newLine();

		return null;
	}

	@Override
	public Void caseModuleElementDocumentation(ModuleElementDocumentation moduleElementDocumentation) {
		doSwitch(moduleElementDocumentation.getBody());
		for (ParameterDocumentation documentation : moduleElementDocumentation.getParameterDocumentation()) {
			newLine();
			doSwitch(documentation);
		}

		return null;
	}

	@Override
	public Void caseParameterDocumentation(ParameterDocumentation parameterDocumentation) {
		doSwitch(parameterDocumentation.getBody());

		return null;
	}

	@Override
	public Void caseAcceleoASTNode(AcceleoASTNode node) {
		builder.append(" (" + ast.getStartPosition(node));
		builder.append("..");
		builder.append(ast.getEndPosition(node) + ")");

		return null;
	}

	@Override
	public Void caseErrorMargin(ErrorMargin errorMargin) {
		builder.append("*** error margin ***");

		return null;
	}

}
