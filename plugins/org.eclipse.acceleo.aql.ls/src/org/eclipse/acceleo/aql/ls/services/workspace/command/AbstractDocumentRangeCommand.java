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
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Binding;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.ExpressionStatement;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.TextStatement;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.ls.services.textdocument.AcceleoTextDocument;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.query.ast.ASTNode;
import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.ast.ClassTypeLiteral;
import org.eclipse.acceleo.query.ast.CollectionTypeLiteral;
import org.eclipse.acceleo.query.ast.EClassifierTypeLiteral;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.parser.AstResult;
import org.eclipse.acceleo.query.parser.Positions;
import org.eclipse.acceleo.query.parser.QueryParser.ExplicitSetLitContext;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IJavaType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Abstract {@link DocumentRangeParams} command.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AbstractDocumentRangeCommand {

	/**
	 * Tells if the given start and end indexes are in the same {@link Block}.
	 * 
	 * @param acceleoTextDocument
	 *            the {@link AcceleoTextDocument}
	 * @param startIndex
	 *            the start index
	 * @param endIndex
	 *            the end index
	 * @return <code>true</code> if the given start and end indexes are in the same {@link Block},
	 *         <code>false</code> otherwise
	 */
	protected boolean isSameBlock(AcceleoTextDocument acceleoTextDocument, int startIndex, int endIndex) {
		final ASTNode start = acceleoTextDocument.getAcceleoAstResult().getAstNode(startIndex);
		final ASTNode end = acceleoTextDocument.getAcceleoAstResult().getAstNode(endIndex);

		return start.eContainer() instanceof Block && start.eContainer() == end.eContainer()
				|| start instanceof Block && start == end.eContainer();
	}

	/**
	 * Gets the {@link List} of {@link Variable} used in the given range that are not declared in that range.
	 * 
	 * @param acceleoTextDocument
	 *            the {@link AcceleoTextDocument}
	 * @param startIndex
	 *            the start index
	 * @param endIndex
	 *            the end index
	 * @return the {@link List} of {@link Variable} used in the given range that are not declared in that
	 *         range
	 */
	protected List<Variable> getVariablesDeclaredOutSide(AcceleoTextDocument acceleoTextDocument,
			int startIndex, int endIndex) {
		final List<Variable> res = new ArrayList<>();

		final ASTNode start = acceleoTextDocument.getAcceleoAstResult().getAstNode(startIndex);
		final ASTNode end = acceleoTextDocument.getAcceleoAstResult().getAstNode(endIndex);
		final AcceleoAstResult astResult = acceleoTextDocument.getAcceleoAstResult();

		final Block parentBlock;
		if (start instanceof Block) {
			parentBlock = (Block)start;
		} else {
			parentBlock = (Block)start.eContainer();
		}

		final List<Statement> statementsInRange = new ArrayList<>();
		for (Statement statement : parentBlock.getStatements()) {
			final int statementStart = astResult.getStartPosition(statement);
			final int statementEnd = astResult.getEndPosition(statement);
			if (statementStart >= startIndex && statementEnd <= endIndex) {
				statementsInRange.add(statement);
			} else if (statementEnd > endIndex) {
				break;
			}
		}

		final List<VarRef> varRefs = new ArrayList<>();
		for (Statement statement : statementsInRange) {
			final Iterator<EObject> contentsIt = statement.eAllContents();
			while (contentsIt.hasNext()) {
				final EObject current = contentsIt.next();
				if (current instanceof VarRef) {
					varRefs.add((VarRef)current);
				}
			}
		}

		final Set<Variable> variables = new LinkedHashSet<>();
		final IAcceleoValidationResult validationResult = acceleoTextDocument.getAcceleoValidationResults();
		for (VarRef varRef : varRefs) {
			final Variable variable = validationResult.getDeclarationVariable(varRef);
			if (variable != null && astResult.getStartPosition(variable) < startIndex) {
				variables.add(variable);
			}
		}
		for (Variable variable : variables) {
			if (variable instanceof Binding) {
				final Variable var = getVariable(acceleoTextDocument, (Binding)variable);
				res.add(var);
			} else {
				res.add(EcoreUtil.copy(variable));

			}
		}

		return res;
	}

	/**
	 * Gets the {@link Variable} for the given {@link Binding}.
	 * 
	 * @param acceleoTextDocument
	 *            the {@link AcceleoTextDocument}
	 * @param binding
	 *            the {@link Binding}
	 * @return the {@link Variable} for the given {@link Binding}
	 */
	private Variable getVariable(AcceleoTextDocument acceleoTextDocument, Binding binding) {
		final Variable var = AcceleoPackage.eINSTANCE.getAcceleoFactory().createVariable();
		var.setName(binding.getName());
		if (binding.getType() != null) {
			var.setTypeAql(EcoreUtil.copy(binding.getTypeAql()));
			var.setType(binding.getType());
		} else {
			final Set<IType> possibleTypes = acceleoTextDocument.getAcceleoValidationResults()
					.getPossibleTypes(binding.getInitExpression().getAql());
			final TypeLiteral type;
			if (!possibleTypes.isEmpty()) {
				// TODO more than one type ?
				final IType possibleType = possibleTypes.iterator().next();
				type = getType(possibleType);
			} else {
				type = null;
			}
			if (type != null) {
				var.setTypeAql(type);
				var.setType(new AstResult(type, new Positions<>(), Collections.emptyList(),
						new BasicDiagnostic()));
			}
		}
		return var;
	}

	private TypeLiteral getType(final IType possibleType) {
		final TypeLiteral type;
		if (possibleType instanceof EClassifierType) {
			type = AstPackage.eINSTANCE.getAstFactory().createEClassifierTypeLiteral();
			((EClassifierTypeLiteral)type).setEPackageName(((EClassifierType)possibleType).getType()
					.getEPackage().getName());
			((EClassifierTypeLiteral)type).setEClassifierName(((EClassifierType)possibleType).getType()
					.getName());
		} else if (possibleType instanceof SequenceType) {
			type = AstPackage.eINSTANCE.getAstFactory().createCollectionTypeLiteral();
			((CollectionTypeLiteral)type).setValue(List.class);
			((CollectionTypeLiteral)type).setElementType(getType(((SequenceType)possibleType)
					.getCollectionType()));
		} else if (possibleType instanceof SetType) {
			type = AstPackage.eINSTANCE.getAstFactory().createCollectionTypeLiteral();
			((CollectionTypeLiteral)type).setValue(Set.class);
			((CollectionTypeLiteral)type).setElementType(getType(((SetType)possibleType)
					.getCollectionType()));
		} else if (possibleType instanceof IJavaType) {
			type = AstPackage.eINSTANCE.getAstFactory().createClassTypeLiteral();
			((ClassTypeLiteral)type).setValue(((IJavaType)possibleType).getType());
		} else {
			type = null;
		}
		return type;
	}

	/**
	 * Gets the {@link Block} start index for the given {@link AcceleoTextDocument}, start {@link Statement},
	 * and original start index.
	 * 
	 * @param acceleoTextDocument
	 *            the {@link AcceleoTextDocument}
	 * @param startStatement
	 *            the start {@link Statement}
	 * @param start
	 *            the original start index
	 * @return the {@link Block} start index for the given {@link AcceleoTextDocument}, start
	 *         {@link Statement}, and original start index
	 */
	protected int getBlockStartIndex(AcceleoTextDocument acceleoTextDocument, Statement startStatement,
			int start) {
		final int res;

		if (startStatement instanceof TextStatement) {
			res = start;
		} else {
			res = acceleoTextDocument.getAcceleoAstResult().getStartPosition(startStatement);
		}

		return res;
	}

	/**
	 * Gets the {@link Block} end index for the given {@link AcceleoTextDocument}, end {@link Statement}, and
	 * original end index.
	 * 
	 * @param acceleoTextDocument
	 *            the {@link AcceleoTextDocument}
	 * @param endStatement
	 *            the end {@link Statement}
	 * @param end
	 *            the original end index
	 * @return the {@link Block} end index for the given {@link AcceleoTextDocument}, end {@link Statement},
	 *         and original end index
	 */
	protected int getBlockEndIndex(AcceleoTextDocument acceleoTextDocument, Statement endStatement, int end) {
		final int res;

		if (endStatement instanceof TextStatement) {
			res = end;
		} else {
			res = acceleoTextDocument.getAcceleoAstResult().getEndPosition(endStatement);
		}

		return res;
	}

	/**
	 * Creates a {@link Block} with the given text content.
	 * 
	 * @param text
	 *            the text
	 * @param blockIndentation
	 *            the outside block indentation
	 * @param indentation
	 *            the inside block indentation
	 * @param lineDelimiter
	 *            the line delimiter
	 * @param removeBlockIndent
	 *            removes the block indentation form the block
	 * @return the created {@link Block} with the given text content
	 */
	protected Block createBlock(String text, String blockIndentation, String indentation,
			String lineDelimiter, boolean removeBlockIndent) {
		final Block res;

		res = AcceleoPackage.eINSTANCE.getAcceleoFactory().createBlock();
		res.setInlined(false);
		final TextStatement textStatement = AcceleoPackage.eINSTANCE.getAcceleoFactory()
				.createTextStatement();
		// TODO new line
		textStatement.setValue(indentText(text, blockIndentation, indentation, lineDelimiter,
				removeBlockIndent));
		textStatement.setNewLineNeeded(false);
		res.getStatements().add(textStatement);

		return res;
	}

	/**
	 * Indents the given text for a {@link Block}.
	 * 
	 * @param text
	 *            the text
	 * @param blockIndentation
	 *            the outside block indentation
	 * @param indentation
	 *            the inside block indentation
	 * @param lineDelimiter
	 *            the new line delimiter
	 * @param removeBlockIndent
	 *            removes the block indentation form the block
	 * @return the indented text
	 */
	private String indentText(String text, String blockIndentation, String indentation, String lineDelimiter,
			boolean removeBlockIndent) {
		String res;

		final String outputBlockIndentation;
		if (removeBlockIndent) {
			outputBlockIndentation = "";
			res = text.substring(blockIndentation.length());
		} else {
			outputBlockIndentation = blockIndentation;
			res = text;
		}
		final String emptyLineReplacement = UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID
				.randomUUID().toString();
		res = res.replaceAll("(\\r\\n|\\n)(\\r\\n|\\n)", emptyLineReplacement);
		res = res.replaceAll("(\\r\\n|\\n)" + blockIndentation, lineDelimiter + outputBlockIndentation
				+ indentation);
		res = res.replace(emptyLineReplacement, lineDelimiter + lineDelimiter + outputBlockIndentation
				+ indentation);

		return res + outputBlockIndentation;
	}

	/**
	 * Gets the {@link Block} indentation for the given text.
	 * 
	 * @param text
	 *            the text
	 * @return the {@link Block} indentation for the given text
	 */
	protected String getBlockIndentation(String text) {
		int offset = 0;
		while (offset < text.length()) {
			final char charAt = text.charAt(offset);
			if (charAt == ' ' || charAt == '\t') {
				offset++;
			} else {
				break;
			}
		}

		return text.substring(0, offset);
	}

	/**
	 * Creates the {@link ExplicitSetLitContext} containing the {@link Call} with the the given parameters.
	 * 
	 * @param name
	 *            the name of the service to call
	 * @param parameters
	 *            the {@link List} of {@link Variable} to use as parameters
	 * @return the created {@link ExplicitSetLitContext} containing the {@link Call} with the the given
	 *         parameters
	 */
	protected ExpressionStatement createCall(String name, List<Variable> parameters) {
		final ExpressionStatement res = AcceleoPackage.eINSTANCE.getAcceleoFactory()
				.createExpressionStatement();

		final Expression expression = AcceleoPackage.eINSTANCE.getAcceleoFactory().createExpression();
		res.setExpression(expression);
		final Call call = AstPackage.eINSTANCE.getAstFactory().createCall();
		expression.setAql(call);
		expression.setAst(new AstResult(call, new Positions<>(), Collections.emptyList(),
				new BasicDiagnostic()));
		call.setServiceName(name);
		if (parameters.get(0).getTypeAql() instanceof CollectionTypeLiteral) {
			call.setType(CallType.COLLECTIONCALL);
		} else {
			call.setType(CallType.CALLORAPPLY);
		}
		for (Variable var : parameters) {
			final VarRef varRef = AstPackage.eINSTANCE.getAstFactory().createVarRef();
			varRef.setVariableName(var.getName());
			call.getArguments().add(varRef);
		}

		return res;
	}

}
