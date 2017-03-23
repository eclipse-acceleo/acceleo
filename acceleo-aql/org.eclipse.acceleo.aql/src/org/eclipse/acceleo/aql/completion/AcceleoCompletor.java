/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.completion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Error;
import org.eclipse.acceleo.ErrorBinding;
import org.eclipse.acceleo.ErrorExpression;
import org.eclipse.acceleo.ErrorExpressionStatement;
import org.eclipse.acceleo.ErrorFileStatement;
import org.eclipse.acceleo.ErrorForStatement;
import org.eclipse.acceleo.ErrorIfStatement;
import org.eclipse.acceleo.ErrorImport;
import org.eclipse.acceleo.ErrorLetStatement;
import org.eclipse.acceleo.ErrorMetamodel;
import org.eclipse.acceleo.ErrorModule;
import org.eclipse.acceleo.ErrorProtectedArea;
import org.eclipse.acceleo.ErrorQuery;
import org.eclipse.acceleo.ErrorTemplate;
import org.eclipse.acceleo.ErrorVariable;
import org.eclipse.acceleo.ForStatement;
import org.eclipse.acceleo.IfStatement;
import org.eclipse.acceleo.LetStatement;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.OpenModeKind;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.VisibilityKind;
import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.validation.AcceleoValidator;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.query.parser.AstCompletor;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.CompletionServices;
import org.eclipse.acceleo.query.runtime.impl.completion.TextCompletionProposal;
import org.eclipse.acceleo.util.AcceleoSwitch;
import org.eclipse.emf.ecore.EPackage;

/**
 * Complete {@link Module}. A module can be parsed using {@link org.eclipse.acceleo.aql.parser.AcceleoParser
 * AcceleoParser} and validated using {@link org.eclipse.acceleo.aql.validation.AcceleoValidator
 * AcceleoValidator}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@SuppressWarnings("restriction")
public class AcceleoCompletor extends AcceleoSwitch<List<ICompletionProposal>> {

	/**
	 * A space.
	 */
	private static final String SPACE = " ";

	/**
	 * The {@link AstCompletor}.
	 */
	private AstCompletor aqlCompletor;

	/**
	 * The {@link IAcceleoValidationResult}.
	 */
	private IAcceleoValidationResult validationResult;

	/**
	 * Gets the {@link List} of {@link ICompletionProposal} for the given source at the given position.
	 * 
	 * @param acceleoEnvironment
	 *            the {@link IAcceleoEnvironment}
	 * @param source
	 *            the source module
	 * @param position
	 *            the caret position
	 * @return the {@link List} of {@link ICompletionProposal} for the given source at the given position
	 */
	public List<ICompletionProposal> getProposals(IAcceleoEnvironment acceleoEnvironment, String source,
			int position) {

		final AcceleoParser parser = new AcceleoParser(acceleoEnvironment.getQueryEnvironment());
		final AcceleoAstResult astResult = parser.parse(source.substring(0, position));
		acceleoEnvironment.registerModule("to::complet", astResult.getModule());
		final AcceleoValidator validator = new AcceleoValidator(acceleoEnvironment);

		return getProposals(acceleoEnvironment.getQueryEnvironment(), validator.validate(astResult,
				"to::complet"));
	}

	/**
	 * Gets the {@link List} of {@link ICompletionProposal} for the given {@link IAcceleoValidationResult}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param validationRes
	 *            the {@link IAcceleoValidationResult}
	 * @return the {@link List} of {@link ICompletionProposal}
	 */
	protected List<ICompletionProposal> getProposals(IReadOnlyQueryEnvironment queryEnvironment,
			IAcceleoValidationResult validationRes) {
		final List<ICompletionProposal> result;
		aqlCompletor = new AstCompletor(new CompletionServices(queryEnvironment));
		validationResult = validationRes;

		final List<Error> errors = validationRes.getAcceleoAstResult().getErrors();
		if (errors.size() > 0) {
			final Error errorToComplete = getErrorToComplete(validationRes.getAcceleoAstResult(), errors);
			result = doSwitch(errorToComplete);
		} else {
			result = new ArrayList<ICompletionProposal>();
			final Module module = validationRes.getAcceleoAstResult().getModule();
			if (!hasTemplateOrQuery(module)) {
				result.add(new TextCompletionProposal(AcceleoParser.IMPORT_START, 0));
			}
			result.addAll(getModuleElementProposals());
		}

		return result;
	}

	/**
	 * Tells if the given {@link Module} {@link Module#getModuleElements() has} any {@link Query} or
	 * {@link Template}.
	 * 
	 * @param module
	 *            the {@link Module} to check
	 * @return <code>true</code> if the given {@link Module} {@link Module#getModuleElements() has} any
	 *         {@link Query} or {@link Template}, <code>false</code> otherwise
	 */
	private boolean hasTemplateOrQuery(Module module) {
		for (ModuleElement element : module.getModuleElements()) {
			if (element instanceof Template || element instanceof Query) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Gets the {@link List} of {@link ICompletionProposal} for {@link org.eclipse.acceleo.ModuleElement
	 * ModuleElement}.
	 * 
	 * @return the {@link List} of {@link ICompletionProposal} for {@link org.eclipse.acceleo.ModuleElement
	 *         ModuleElement}
	 */
	protected List<ICompletionProposal> getModuleElementProposals() {
		final List<ICompletionProposal> res = new ArrayList<ICompletionProposal>();

		res.add(new TextCompletionProposal(AcceleoParser.DOCUMENTATION_START, 0));
		res.add(new TextCompletionProposal(AcceleoParser.COMMENT_START, 0));
		res.add(new TextCompletionProposal(AcceleoParser.TEMPLATE_HEADER_START, 0));
		res.add(new TextCompletionProposal(AcceleoParser.QUERY_START, 0));

		return res;
	}

	/**
	 * Gets the {@link Error} to use for completion starting point. It's the first error that
	 * {@link org.eclipse.acceleo.ASTNode#getEndPosition() end} at the end of the {@link Module}.
	 * 
	 * @param astResult
	 *            the {@link AcceleoAstResult}
	 * @param errors
	 *            the possible {@link Error}
	 * @return the {@link Error} to use for completion starting point
	 */
	private Error getErrorToComplete(AcceleoAstResult astResult, List<Error> errors) {
		Error result = errors.get(0);

		int currentEnd = result.getEndPosition();
		for (int i = 1; i < errors.size(); i++) {
			final Error error = errors.get(i);
			int end = error.getEndPosition();
			if (end > currentEnd) {
				currentEnd = end;
				result = error;
			}
		}

		return result;
	}

	@Override
	public List<ICompletionProposal> caseErrorModule(ErrorModule errorModule) {
		final List<ICompletionProposal> res = new ArrayList<ICompletionProposal>();

		if (errorModule.getMissingOpenParenthesis() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.OPEN_PARENTHESIS, 0));
		} else if (errorModule.getMissingEPackage() != -1) {
			for (String nsURI : EPackage.Registry.INSTANCE.keySet()) {
				res.add(new TextCompletionProposal(nsURI, 0));
			}
		} else if (errorModule.getMissingCloseParenthesis() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.CLOSE_PARENTHESIS
					+ AcceleoParser.MODULE_HEADER_END, 0));
		} else if (errorModule.getMissingEndHeader() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.MODULE_HEADER_END, 0));
		}

		return res;
	}

	@Override
	public List<ICompletionProposal> caseErrorMetamodel(ErrorMetamodel errorMetamodel) {
		final List<ICompletionProposal> res = new ArrayList<ICompletionProposal>();

		if (errorMetamodel.getFragment() != null) {
			for (String nsURI : EPackage.Registry.INSTANCE.keySet()) {
				res.add(new TextCompletionProposal(nsURI, 0));
			}
		} else if (errorMetamodel.getMissingEndQuote() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.QUOTE, 0));
		}

		return res;
	}

	@Override
	public List<ICompletionProposal> caseErrorImport(ErrorImport errorImport) {
		final List<ICompletionProposal> res = new ArrayList<ICompletionProposal>();

		if (errorImport.getMissingEnd() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.IMPORT_END + SPACE, 0));
		}

		return null;
	}

	@Override
	public List<ICompletionProposal> caseErrorTemplate(ErrorTemplate errorTemplate) {
		final List<ICompletionProposal> res = new ArrayList<ICompletionProposal>();

		if (errorTemplate.getMissingVisibility() != -1) {
			for (VisibilityKind kind : VisibilityKind.VALUES) {
				res.add(new TextCompletionProposal(kind.getName() + SPACE, 0));
			}
		} else if (errorTemplate.getMissingName() != -1) {
			// nothing to do here
		} else if (errorTemplate.getMissingOpenParenthesis() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.OPEN_PARENTHESIS, 0));
		} else if (errorTemplate.getMissingParameters() != -1) {
			// nothing to do here
		} else if (errorTemplate.getMissingCloseParenthesis() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.COMMA + SPACE, 0));
			res.add(new TextCompletionProposal(AcceleoParser.CLOSE_PARENTHESIS, 0));
		} else if (errorTemplate.getMissingGuardOpenParenthesis() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.OPEN_PARENTHESIS, 0));
		} else if (errorTemplate.getGuard() != null
				&& errorTemplate.getGuard().getAst().getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			res.addAll(aqlCompletor.getProposals(getVariableNames(errorTemplate), validationResult
					.getValidationResult(errorTemplate.getGuard().getAst())));
		} else if (errorTemplate.getMissingGuardCloseParenthesis() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.CLOSE_PARENTHESIS, 0));
		} else if (errorTemplate.getPost() != null
				&& errorTemplate.getPost().getAst().getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			final Set<String> variableNames = new LinkedHashSet<String>();
			variableNames.add("self");
			res.addAll(aqlCompletor.getProposals(variableNames, validationResult
					.getValidationResult(errorTemplate.getPost().getAst())));
		} else if (errorTemplate.getMissingPostCloseParenthesis() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.CLOSE_PARENTHESIS
					+ AcceleoParser.TEMPLATE_HEADER_END, 0));
		} else if (errorTemplate.getMissingEndHeader() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.TEMPLATE_HEADER_END, 0));
		} else if (errorTemplate.getMissingEnd() != -1) {
			res.addAll(getStatementProposals());
			res.add(new TextCompletionProposal(AcceleoParser.TEMPLATE_END, 0));
		}

		return res;
	}

	@Override
	public List<ICompletionProposal> caseErrorQuery(ErrorQuery errorQuery) {
		final List<ICompletionProposal> res = new ArrayList<ICompletionProposal>();

		if (errorQuery.getMissingVisibility() != -1) {
			for (VisibilityKind kind : VisibilityKind.VALUES) {
				res.add(new TextCompletionProposal(kind.getName() + SPACE, 0));
			}
		} else if (errorQuery.getMissingName() != -1) {
			// nothing to do here
		} else if (errorQuery.getMissingOpenParenthesis() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.OPEN_PARENTHESIS, 0));
		} else if (errorQuery.getMissingParameters() != -1) {
			// nothing to do here
		} else if (errorQuery.getMissingCloseParenthesis() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.COMMA + SPACE, 0));
			res.add(new TextCompletionProposal(AcceleoParser.CLOSE_PARENTHESIS + SPACE + AcceleoParser.COLON
					+ SPACE, 0));
		} else if (errorQuery.getMissingColon() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.COLON + SPACE, 0));
		} else if (errorQuery.getMissingType() != -1) {
			res.addAll(aqlCompletor.getProposals(Collections.<String> emptySet(), validationResult
					.getValidationResult(errorQuery.getType())));
		} else if (errorQuery.getMissingEqual() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.EQUAL + SPACE, 0));
		} else if (errorQuery.getBody().getAst().getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			res.addAll(aqlCompletor.getProposals(getVariableNames(errorQuery), validationResult
					.getValidationResult(errorQuery.getBody().getAst())));
		} else if (errorQuery.getMissingEnd() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.QUERY_END, 0));
		}

		return res;
	}

	@Override
	public List<ICompletionProposal> caseErrorVariable(ErrorVariable errorVariable) {
		final List<ICompletionProposal> res = new ArrayList<ICompletionProposal>();

		if (errorVariable.getMissingName() != -1) {
			// nothing to do here
		} else if (errorVariable.getMissingColon() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.COLON + SPACE, 0));
		} else if (errorVariable.getMissingType() != -1) {
			res.addAll(aqlCompletor.getProposals(Collections.<String> emptySet(), validationResult
					.getValidationResult(errorVariable.getType())));
		}

		return res;
	}

	@Override
	public List<ICompletionProposal> caseErrorExpression(ErrorExpression errorExpression) {
		final List<ICompletionProposal> res = new ArrayList<ICompletionProposal>();

		if (errorExpression.getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			res.addAll(aqlCompletor.getProposals(getVariableNames(errorExpression), validationResult
					.getValidationResult(errorExpression.getAst())));
		}

		return res;
	}

	/**
	 * Gets the {@link Set} of {@link String variable name} in the scope of the given {@link ASTNode}.
	 * 
	 * @param scope
	 *            the {@link ASTNode} scope
	 * @return the {@link Set} of {@link String variable name} in the scope of the given {@link ASTNode}
	 */
	private Set<String> getVariableNames(ASTNode scope) {
		final List<String> res = new ArrayList<String>();

		ASTNode currentScope = scope;
		while (currentScope != null) {
			if (currentScope instanceof Template) {
				final Template template = (Template)currentScope;
				for (Variable variable : template.getParameters()) {
					res.add(variable.getName());
				}
			} else if (currentScope instanceof Query) {
				final Query query = (Query)currentScope;
				for (Variable variable : query.getParameters()) {
					res.add(variable.getName());
				}
			} else if (currentScope instanceof LetStatement) {
				final LetStatement let = (LetStatement)currentScope;
				for (Variable variable : let.getVariables()) {
					res.add(variable.getName());
				}
			} else if (currentScope instanceof ForStatement) {
				final ForStatement forStatement = (ForStatement)currentScope;
				res.add(forStatement.getBinding().getName());
			}

			if (currentScope.eContainer() instanceof ASTNode) {
				currentScope = (ASTNode)currentScope.eContainer();
			} else {
				currentScope = null;
			}
		}

		Collections.sort(res);

		return new LinkedHashSet<String>(res);
	}

	@Override
	public List<ICompletionProposal> caseErrorBinding(ErrorBinding errorBinding) {
		final List<ICompletionProposal> res = new ArrayList<ICompletionProposal>();

		if (errorBinding.getMissingName() != -1) {
			// nothing to do here
		} else if (errorBinding.getMissingColon() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.COLON + SPACE, 0));
		} else if (errorBinding.getMissingType() != -1) {
			final IValidationResult typeValidationResult = validationResult.getValidationResult(errorBinding
					.getType());
			res.addAll(aqlCompletor.getProposals(Collections.<String> emptySet(), typeValidationResult));
		} else if (errorBinding.getMissingAffectationSymbolePosition() != -1) {
			res.add(new TextCompletionProposal(errorBinding.getMissingAffectationSymbole() + SPACE, 0));
		} else if (errorBinding.getInitExpression().getAst().getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			res.addAll(aqlCompletor.getProposals(getVariableNames(errorBinding), validationResult
					.getValidationResult(errorBinding.getInitExpression().getAst())));
		}

		return res;
	}

	@Override
	public List<ICompletionProposal> caseErrorExpressionStatement(
			ErrorExpressionStatement errorExpressionStatement) {
		final List<ICompletionProposal> res = new ArrayList<ICompletionProposal>();

		if (errorExpressionStatement.getExpression().getAst().getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			res.addAll(aqlCompletor.getProposals(getVariableNames(errorExpressionStatement), validationResult
					.getValidationResult(errorExpressionStatement.getExpression().getAst())));
		} else if (errorExpressionStatement.getMissingEndHeader() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.EXPRESSION_STATEMENT_END, 0));
		}

		return res;
	}

	@Override
	public List<ICompletionProposal> caseErrorProtectedArea(ErrorProtectedArea errorProtectedArea) {
		final List<ICompletionProposal> res = new ArrayList<ICompletionProposal>();

		if (errorProtectedArea.getMissingOpenParenthesis() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.OPEN_PARENTHESIS, 0));
		} else if (errorProtectedArea.getId().getAst().getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			res.addAll(aqlCompletor.getProposals(getVariableNames(errorProtectedArea), validationResult
					.getValidationResult(errorProtectedArea.getId().getAst())));
		} else if (errorProtectedArea.getMissingCloseParenthesis() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.CLOSE_PARENTHESIS
					+ AcceleoParser.PROTECTED_AREA_HEADER_END, 0));
		} else if (errorProtectedArea.getMissingEndHeader() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.PROTECTED_AREA_HEADER_END, 0));
		} else if (errorProtectedArea.getMissingEnd() != -1) {
			res.addAll(getStatementProposals());
			res.add(new TextCompletionProposal(AcceleoParser.PROTECTED_AREA_END, 0));
		}

		return res;
	}

	@Override
	public List<ICompletionProposal> caseErrorForStatement(ErrorForStatement errorForStatement) {
		final List<ICompletionProposal> res = new ArrayList<ICompletionProposal>();

		if (errorForStatement.getMissingOpenParenthesis() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.OPEN_PARENTHESIS, 0));
		} else if (errorForStatement.getMissingBinding() != -1) {
			// nothing to do here
		} else if (errorForStatement.getMissingCloseParenthesis() != -1) {
			res.add(new TextCompletionProposal(
					AcceleoParser.CLOSE_PARENTHESIS + AcceleoParser.FOR_HEADER_END, 0));
		} else if (errorForStatement.getMissingEndHeader() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.FOR_HEADER_END, 0));
		} else if (errorForStatement.getMissingEnd() != -1) {
			res.addAll(getStatementProposals());
			res.add(new TextCompletionProposal(AcceleoParser.FOR_END, 0));
		}

		return res;
	}

	@Override
	public List<ICompletionProposal> caseErrorIfStatement(ErrorIfStatement errorIfStatement) {
		final List<ICompletionProposal> res = new ArrayList<ICompletionProposal>();

		if (errorIfStatement.getMissingOpenParenthesis() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.OPEN_PARENTHESIS, 0));
		} else if (errorIfStatement.getCondition().getAst().getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			res.addAll(aqlCompletor.getProposals(getVariableNames(errorIfStatement), validationResult
					.getValidationResult(errorIfStatement.getCondition().getAst())));
		} else if (errorIfStatement.getMissingCloseParenthesis() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.CLOSE_PARENTHESIS + AcceleoParser.IF_HEADER_END,
					0));
		} else if (errorIfStatement.getMissingEndHeader() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.IF_HEADER_END, 0));
		} else if (errorIfStatement.getMissingEnd() != -1) {
			res.addAll(getStatementProposals());
			if (errorIfStatement.getElse() == null) {
				res.add(new TextCompletionProposal(AcceleoParser.IF_ELSE, 0));
				if (errorIfStatement.eContainer().eContainingFeature() == AcceleoPackage.eINSTANCE
						.getIfStatement_Else()
						|| !(errorIfStatement.eContainer().eContainer() instanceof IfStatement)) {
					res.add(new TextCompletionProposal(AcceleoParser.IF_ELSEIF
							+ AcceleoParser.OPEN_PARENTHESIS + AcceleoParser.CLOSE_PARENTHESIS
							+ AcceleoParser.IF_HEADER_END, 2));
				}
			}
			res.add(new TextCompletionProposal(AcceleoParser.IF_END, 0));
		}

		return res;
	}

	@Override
	public List<ICompletionProposal> caseErrorLetStatement(ErrorLetStatement errorLetStatement) {
		final List<ICompletionProposal> res = new ArrayList<ICompletionProposal>();
		if (errorLetStatement.getMissingBindings() != -1) {
			// nothing to do here
		} else if (errorLetStatement.getMissingEndHeader() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.LET_HEADER_END, 0));
		} else if (errorLetStatement.getMissingEnd() != -1) {
			res.addAll(getStatementProposals());
			res.add(new TextCompletionProposal(AcceleoParser.LET_END, 0));
		}

		return res;
	}

	@Override
	public List<ICompletionProposal> caseErrorFileStatement(ErrorFileStatement errorFileStatement) {
		final List<ICompletionProposal> res = new ArrayList<ICompletionProposal>();

		if (errorFileStatement.getMissingOpenParenthesis() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.OPEN_PARENTHESIS, 0));
		} else if (errorFileStatement.getUrl().getAst().getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			res.addAll(aqlCompletor.getProposals(getVariableNames(errorFileStatement), validationResult
					.getValidationResult(errorFileStatement.getUrl().getAst())));
		} else if (errorFileStatement.getMissingComma() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.COMMA + SPACE, 0));
		} else if (errorFileStatement.getMissingOpenMode() != -1) {
			for (OpenModeKind kind : OpenModeKind.VALUES) {
				res.add(new TextCompletionProposal(kind.getName(), 0));
			}
		} else if (errorFileStatement.getMissingCloseParenthesis() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.CLOSE_PARENTHESIS
					+ AcceleoParser.FILE_HEADER_END, 0));
		} else if (errorFileStatement.getMissingEndHeader() != -1) {
			res.add(new TextCompletionProposal(AcceleoParser.FILE_HEADER_END, 0));
		} else if (errorFileStatement.getMissingEnd() != -1) {
			res.addAll(getStatementProposals());
			res.add(new TextCompletionProposal(AcceleoParser.FILE_END, 0));
		}

		return res;
	}

	/**
	 * Gets the {@link List} of {@link ICompletionProposal} for {@link org.eclipse.acceleo.Statement
	 * Statement}.
	 * 
	 * @return the {@link List} of {@link ICompletionProposal} for {@link org.eclipse.acceleo.Statement
	 *         Statement}
	 */
	List<ICompletionProposal> getStatementProposals() {
		final List<ICompletionProposal> res = new ArrayList<ICompletionProposal>();

		res.add(new TextCompletionProposal(AcceleoParser.FILE_HEADER_START, 0));
		res.add(new TextCompletionProposal(AcceleoParser.IF_HEADER_START, 0));
		res.add(new TextCompletionProposal(AcceleoParser.LET_HEADER_START, 0));
		res.add(new TextCompletionProposal(AcceleoParser.PROTECTED_AREA_HEADER_START, 0));
		res.add(new TextCompletionProposal(AcceleoParser.COMMENT_START, 0));
		res.add(new TextCompletionProposal(AcceleoParser.EXPRESSION_STATEMENT_START, 0));

		return res;
	}

}
