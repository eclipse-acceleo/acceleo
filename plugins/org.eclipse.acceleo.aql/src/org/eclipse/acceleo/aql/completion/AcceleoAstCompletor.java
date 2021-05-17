/*******************************************************************************
 * Copyright (c) 2020, 2021 Obeo.
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
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.acceleo.ASTNode;
import org.eclipse.acceleo.AcceleoPackage;
import org.eclipse.acceleo.Binding;
import org.eclipse.acceleo.ErrorBinding;
import org.eclipse.acceleo.ErrorBlockComment;
import org.eclipse.acceleo.ErrorComment;
import org.eclipse.acceleo.ErrorExpression;
import org.eclipse.acceleo.ErrorExpressionStatement;
import org.eclipse.acceleo.ErrorFileStatement;
import org.eclipse.acceleo.ErrorForStatement;
import org.eclipse.acceleo.ErrorIfStatement;
import org.eclipse.acceleo.ErrorImport;
import org.eclipse.acceleo.ErrorLetStatement;
import org.eclipse.acceleo.ErrorMetamodel;
import org.eclipse.acceleo.ErrorModule;
import org.eclipse.acceleo.ErrorModuleDocumentation;
import org.eclipse.acceleo.ErrorModuleElementDocumentation;
import org.eclipse.acceleo.ErrorModuleReference;
import org.eclipse.acceleo.ErrorProtectedArea;
import org.eclipse.acceleo.ErrorQuery;
import org.eclipse.acceleo.ErrorTemplate;
import org.eclipse.acceleo.ErrorVariable;
import org.eclipse.acceleo.ForStatement;
import org.eclipse.acceleo.IfStatement;
import org.eclipse.acceleo.LetStatement;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.completion.proposals.AcceleoCompletionProposal;
import org.eclipse.acceleo.aql.completion.proposals.AcceleoCompletionProposalsProvider;
import org.eclipse.acceleo.aql.completion.proposals.syntax.AcceleoSyntacticCompletionProposals;
import org.eclipse.acceleo.aql.completion.proposals.templates.AcceleoCodeTemplateCompletionProposal;
import org.eclipse.acceleo.aql.completion.proposals.templates.AcceleoCodeTemplateCompletionProposalsProvider;
import org.eclipse.acceleo.aql.completion.proposals.templates.AcceleoCodeTemplates;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.query.parser.AstCompletor;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.ICompletionResult;
import org.eclipse.acceleo.query.runtime.IServiceCompletionProposal;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.BasicFilter;
import org.eclipse.acceleo.query.runtime.impl.CompletionServices;
import org.eclipse.acceleo.query.runtime.impl.QueryCompletionEngine;
import org.eclipse.acceleo.query.runtime.impl.completion.EClassifierCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.EEnumLiteralCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.EFeatureCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.EOperationServiceCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.VariableCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.VariableDeclarationCompletionProposal;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.util.AcceleoSwitch;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * An {@link AcceleoSwitch} that provides the {@link List} of {@link AcceleoCompletionProposal} for the
 * Acceleo AST location passed as argument.
 * 
 * @author Florent Latombe
 */
public class AcceleoAstCompletor extends AcceleoSwitch<List<AcceleoCompletionProposal>> {

	/**
	 * {@link Comparator} for {@link ICompletionProposal}.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static class ProposalComparator implements Comparator<ICompletionProposal> {

		@Override
		public int compare(ICompletionProposal o1, ICompletionProposal o2) {
			final int res;

			final int value1 = getValue(o1);
			final int value2 = getValue(o2);

			if (o1 instanceof IServiceCompletionProposal && o2 instanceof IServiceCompletionProposal) {
				res = ((IServiceCompletionProposal)o1).getObject().getShortSignature().compareTo(
						((IServiceCompletionProposal)o2).getObject().getShortSignature());
			} else if (value1 > value2) {
				res = 1;
			} else if (value1 < value2) {
				res = -1;
			} else if (o1 != null && o2 != null) {
				res = o1.getProposal().compareTo(o2.getProposal());
			} else {
				res = 0;
			}

			return res;
		}

		/**
		 * Gets a value order for the given {@link ICompletionProposal}.
		 * 
		 * @param proposal
		 *            the {@link ICompletionProposal}
		 * @return a value order for the given {@link ICompletionProposal}
		 */
		private int getValue(ICompletionProposal proposal) {
			final int res;

			if (proposal instanceof VariableCompletionProposal
					|| proposal instanceof VariableDeclarationCompletionProposal) {
				res = 0;
			} else if (proposal instanceof EFeatureCompletionProposal) {
				res = 1;
			} else if (proposal instanceof IServiceCompletionProposal
					|| proposal instanceof EOperationServiceCompletionProposal) {
				res = 2;
			} else if (proposal instanceof EClassifierCompletionProposal
					|| proposal instanceof EEnumLiteralCompletionProposal
					|| proposal instanceof EFeatureCompletionProposal) {
				res = 3;
			} else {
				res = 4;
			}

			return res;
		}

	}

	/**
	 * The comparator of {@link ICompletionProposal}.
	 */
	private static final Comparator<ICompletionProposal> COMPLETION_PROPOSAL_COMPARATOR = new ProposalComparator();

	/**
	 * A space.
	 */
	private static final String SPACE = " ";

	/**
	 * The {@link IQualifiedNameQueryEnvironment}.
	 */
	private final IQualifiedNameQueryEnvironment queryEnvironment;

	/**
	 * The {@link IAcceleoValidationResult}.
	 */
	private final IAcceleoValidationResult acceleoValidationResult;

	/**
	 * The {@link QueryCompletionEngine}.
	 */
	private final QueryCompletionEngine aqlCompletionEngine;

	/**
	 * The {@link AstCompletor}.
	 */
	private final AstCompletor astCompletor;

	/**
	 * The {@link AcceleoCompletionProposalsProvider}.
	 */
	private final AcceleoCompletionProposalsProvider acceleoCompletionProposalProvider;

	/**
	 * The module source fragment.
	 */
	private String moduleSourceFragment;

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the (non-{@code null}) contextual {@link IQualifiedNameQueryEnvironment}.
	 * @param acceleoValidationResult
	 *            the (non-{@code null}) contextual {@link IAcceleoValidationResult}.
	 */
	public AcceleoAstCompletor(IQualifiedNameQueryEnvironment queryEnvironment,
			IAcceleoValidationResult acceleoValidationResult) {
		this.queryEnvironment = Objects.requireNonNull(queryEnvironment);
		this.acceleoValidationResult = Objects.requireNonNull(acceleoValidationResult);

		this.astCompletor = new AstCompletor(new CompletionServices(this.queryEnvironment));
		this.aqlCompletionEngine = new QueryCompletionEngine(queryEnvironment);
		this.acceleoCompletionProposalProvider = new AcceleoCompletionProposalsProvider();
	}

	/**
	 * Get the {@link List} of {@link AcceleoCompletionProposal} for the given acceleo element.
	 * 
	 * @param sourceFragment
	 *            the module source code
	 * @param acceleoElementToComplete
	 *            the acceleo element to complete
	 * @return the {@link List} of {@link AcceleoCompletionProposal} for the given acceleo element
	 */
	public List<AcceleoCompletionProposal> getCompletion(String sourceFragment,
			EObject acceleoElementToComplete) {
		this.moduleSourceFragment = sourceFragment;
		return doSwitch(acceleoElementToComplete);
	}

	@Override
	public List<AcceleoCompletionProposal> caseModule(Module moduleToComplete) {
		List<AcceleoCompletionProposal> completionProposals = new ArrayList<>();

		if (!moduleToComplete.getModuleElements().stream().anyMatch(
				moduleElement -> moduleElement instanceof Query || moduleElement instanceof Template)) {
			// The module has no queries or templates, or the cursor is above them, so we can add imports.
			completionProposals.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(
					AcceleoPackage.Literals.IMPORT));
		}

		completionProposals.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(
				AcceleoPackage.Literals.TEMPLATE));
		completionProposals.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(
				AcceleoPackage.Literals.QUERY));
		completionProposals.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(
				AcceleoPackage.Literals.MODULE_ELEMENT_DOCUMENTATION));
		completionProposals.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(
				AcceleoPackage.Literals.COMMENT));
		completionProposals.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(
				AcceleoPackage.Literals.BLOCK_COMMENT));
		completionProposals.add(AcceleoCodeTemplateCompletionProposalsProvider.NEW_COMMENT_MAIN);

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorComment(ErrorComment errorComment) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorComment.getMissingEndHeader() != -1) {
			if (errorComment instanceof ErrorBlockComment) {
				res.add(AcceleoSyntacticCompletionProposals.BLOCK_COMMENT_END);
			} else {
				res.add(AcceleoSyntacticCompletionProposals.COMMENT_END);
			}

		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorModuleDocumentation(
			ErrorModuleDocumentation errorModuleDocumentation) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorModuleDocumentation.getMissingEndHeader() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.DOCUMENTATION_END);
		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorModuleElementDocumentation(
			ErrorModuleElementDocumentation errorModuleElementDocumentation) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorModuleElementDocumentation.getMissingEndHeader() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.DOCUMENTATION_END);
		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorModule(ErrorModule errorModule) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorModule.getMissingOpenParenthesis() != -1) {
			if (errorModule.getName() == null) {
				String sampleModuleName = AcceleoCodeTemplates.DEFAULT_NEW_MODULE_NAME;
				res.add(new AcceleoCodeTemplateCompletionProposal(sampleModuleName, sampleModuleName,
						AcceleoPackage.Literals.MODULE));
			} else {
				res.add(AcceleoSyntacticCompletionProposals.OPEN_PARENTHESIS);
			}
		} else if (errorModule.getMissingEPackage() != -1) {
			List<String> candidateMetamodelURIs = new ArrayList<>(EPackage.Registry.INSTANCE.keySet());
			Collections.sort(candidateMetamodelURIs);
			for (String nsURI : candidateMetamodelURIs) {
				String metamodelString = AcceleoParser.QUOTE + nsURI + AcceleoParser.QUOTE;
				res.add(new AcceleoCompletionProposal(nsURI, metamodelString,
						AcceleoPackage.Literals.METAMODEL));
			}
		} else if (errorModule.getMissingCloseParenthesis() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.CLOSE_PARENTHESIS);
			res.add(AcceleoSyntacticCompletionProposals.COMMA_SPACE);
		} else if (errorModule.getMissingEndHeader() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.MODULE_HEADER_END);
			if (errorModule.getExtends() == null) {
				res.add(AcceleoSyntacticCompletionProposals.MODULE_EXTENSION);
			}
		} else {
			// We do not even have a module header.
			res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(
					AcceleoPackage.Literals.MODULE));
			res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(
					AcceleoPackage.Literals.MODULE_DOCUMENTATION));
			res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(
					AcceleoPackage.Literals.COMMENT));
			res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(
					AcceleoPackage.Literals.BLOCK_COMMENT));
		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorMetamodel(ErrorMetamodel errorMetamodel) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorMetamodel.getFragment() != null) {
			for (String nsURI : EPackage.Registry.INSTANCE.keySet()) {
				res.add(new AcceleoCompletionProposal(nsURI, nsURI, AcceleoPackage.Literals.METAMODEL));
			}
		} else if (errorMetamodel.getMissingEndQuote() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.QUOTE_DOUBLE);
		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorImport(ErrorImport errorImport) {
		final List<AcceleoCompletionProposal> completionProposals = new ArrayList<AcceleoCompletionProposal>();

		if (errorImport.getMissingEnd() != -1) {
			completionProposals.add(AcceleoSyntacticCompletionProposals.IMPORT_END);
		}

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorModuleReference(
			ErrorModuleReference errorModuleReference) {
		final List<AcceleoCompletionProposal> completionProposals = new ArrayList<AcceleoCompletionProposal>();

		final List<String> availableQualifiedNames = new ArrayList<String>(queryEnvironment.getLookupEngine()
				.getResolver().getAvailableQualifiedNames());
		Collections.sort(availableQualifiedNames);
		for (String qualifiedName : availableQualifiedNames) {
			completionProposals.add(new AcceleoCodeTemplateCompletionProposal(qualifiedName, qualifiedName,
					AcceleoPackage.Literals.MODULE_REFERENCE));
		}

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorTemplate(ErrorTemplate errorTemplate) {
		final List<AcceleoCompletionProposal> completionProposals = new ArrayList<AcceleoCompletionProposal>();

		if (errorTemplate.getMissingVisibility() != -1) {
			completionProposals.addAll(AcceleoSyntacticCompletionProposals.MODULE_ELEMENT_VISIBILITY_KINDS);
		} else if (errorTemplate.getMissingName() != -1) {
			String sampleTemplateName = AcceleoCodeTemplates.DEFAULT_NEW_TEMPLATE_NAME;
			completionProposals.add(new AcceleoCodeTemplateCompletionProposal(sampleTemplateName,
					sampleTemplateName, AcceleoPackage.Literals.TEMPLATE));
		} else if (errorTemplate.getMissingOpenParenthesis() != -1) {
			completionProposals.add(AcceleoSyntacticCompletionProposals.OPEN_PARENTHESIS);
		} else if (errorTemplate.getMissingParameters() != -1) {
			String sampleParameterName = AcceleoCodeTemplates.DEFAULT_NEW_TEMPLATE_PARAMETER_NAME;
			completionProposals.add(new AcceleoCodeTemplateCompletionProposal(sampleParameterName,
					sampleParameterName, AcceleoPackage.Literals.TEMPLATE));
		} else if (errorTemplate.getMissingCloseParenthesis() != -1) {
			completionProposals.add(AcceleoSyntacticCompletionProposals.CLOSE_PARENTHESIS);
			completionProposals.add(AcceleoSyntacticCompletionProposals.COMMA_SPACE);
		} else if (errorTemplate.getMissingGuardOpenParenthesis() != -1) {
			completionProposals.add(AcceleoSyntacticCompletionProposals.OPEN_PARENTHESIS);
		} else if (errorTemplate.getGuard() != null && errorTemplate.getGuard().getAst()
				.getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			completionProposals.addAll(this.getAqlCompletionProposals(getVariables(errorTemplate),
					acceleoValidationResult.getValidationResult(errorTemplate.getGuard().getAst())));
		} else if (errorTemplate.getMissingGuardCloseParenthesis() != -1) {
			completionProposals.add(AcceleoSyntacticCompletionProposals.CLOSE_PARENTHESIS);
			completionProposals.addAll(this.getAqlCompletionProposals(getVariables(errorTemplate),
					acceleoValidationResult.getValidationResult(errorTemplate.getGuard().getAst())));
		} else if (errorTemplate.getPost() != null && errorTemplate.getPost().getAst()
				.getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			final Map<String, Set<IType>> variables = new HashMap<String, Set<IType>>();
			final Set<IType> possibleTypes = Collections.singleton(new ClassType(queryEnvironment,
					String.class));
			variables.put("self", possibleTypes);
			completionProposals.addAll(getAqlCompletionProposals(variables, acceleoValidationResult
					.getValidationResult(errorTemplate.getPost().getAst())));
		} else if (errorTemplate.getMissingPostCloseParenthesis() != -1) {
			completionProposals.add(AcceleoSyntacticCompletionProposals.CLOSE_PARENTHESIS);
			completionProposals.addAll(this.getAqlCompletionProposals(getVariables(errorTemplate),
					acceleoValidationResult.getValidationResult(errorTemplate.getPost().getAst())));
		} else if (errorTemplate.getMissingEndHeader() != -1) {
			completionProposals.add(AcceleoSyntacticCompletionProposals.TEMPLATE_HEADER_END);
			if (errorTemplate.getGuard() == null && errorTemplate.getPost() == null) {
				completionProposals.add(AcceleoSyntacticCompletionProposals.TEMPLATE_GUARD_START);
			}
			if (errorTemplate.getPost() == null) {
				completionProposals.add(AcceleoSyntacticCompletionProposals.TEMPLATE_POST_START);
			}
		} else if (errorTemplate.getMissingEnd() != -1) {
			completionProposals.add(AcceleoSyntacticCompletionProposals.TEMPLATE_END);
			completionProposals.addAll(getStatementProposals());
		}

		return completionProposals;
	}

	/**
	 * Gets the {@link List} of {@link AcceleoCompletionProposal} for {@link org.eclipse.acceleo.Statement
	 * Statement}.
	 * 
	 * @return the {@link List} of {@link AcceleoCompletionProposal} for {@link org.eclipse.acceleo.Statement
	 *         Statement}
	 */
	private List<AcceleoCompletionProposal> getStatementProposals() {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(AcceleoPackage.Literals.STATEMENT));
		res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(AcceleoPackage.Literals.COMMENT));
		res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(
				AcceleoPackage.Literals.BLOCK_COMMENT));

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorQuery(ErrorQuery errorQuery) {
		final List<AcceleoCompletionProposal> completionProposals = new ArrayList<AcceleoCompletionProposal>();

		if (errorQuery.getMissingVisibility() != -1) {
			completionProposals.addAll(AcceleoSyntacticCompletionProposals.MODULE_ELEMENT_VISIBILITY_KINDS);
		} else if (errorQuery.getMissingName() != -1) {
			String sampleQueryName = AcceleoCodeTemplates.DEFAULT_NEW_QUERY_NAME;
			completionProposals.add(new AcceleoCodeTemplateCompletionProposal(sampleQueryName,
					sampleQueryName, AcceleoPackage.Literals.QUERY));
		} else if (errorQuery.getMissingOpenParenthesis() != -1) {
			completionProposals.add(AcceleoSyntacticCompletionProposals.OPEN_PARENTHESIS);
		} else if (errorQuery.getMissingParameters() != -1) {
			String sampleParameterName = AcceleoCodeTemplates.DEFAULT_NEW_QUERY_PARAMETER_NAME;
			completionProposals.add(new AcceleoCodeTemplateCompletionProposal(sampleParameterName,
					sampleParameterName, AcceleoPackage.Literals.TEMPLATE));
		} else if (errorQuery.getMissingCloseParenthesis() != -1) {
			completionProposals.add(AcceleoSyntacticCompletionProposals.CLOSE_PARENTHESIS);
			completionProposals.add(AcceleoSyntacticCompletionProposals.COMMA_SPACE);
		} else if (errorQuery.getMissingColon() != -1) {
			completionProposals.add(AcceleoSyntacticCompletionProposals.COLON_SPACE);
		} else if (errorQuery.getMissingType() != -1) {
			final IValidationResult typeValidation = acceleoValidationResult.getValidationResult(errorQuery
					.getType());
			completionProposals.addAll(astCompletor.getProposals(Collections.emptySet(), typeValidation)
					.stream().map(AcceleoAstCompletor::transform).collect(Collectors.toList()));
		} else if (errorQuery.getMissingEqual() != -1) {
			completionProposals.add(AcceleoSyntacticCompletionProposals.EQUAL_SPACE);
		} else if (errorQuery.getBody().getAst().getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			completionProposals.addAll(this.getAqlCompletionProposals(getVariables(errorQuery),
					acceleoValidationResult.getValidationResult(errorQuery.getBody().getAst())));
		} else if (errorQuery.getMissingEnd() != -1) {
			completionProposals.add(AcceleoSyntacticCompletionProposals.QUERY_END);
			// Before the end of a query, there is the value expression, which is valid in this case.
			completionProposals.addAll(this.getAqlCompletionProposals(getVariables(errorQuery),
					acceleoValidationResult.getValidationResult(errorQuery.getBody().getAst())));
		}

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorVariable(ErrorVariable errorVariable) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorVariable.getMissingName() != -1) {
			// nothing to do here
		} else if (errorVariable.getMissingColon() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.COLON_SPACE);
		} else if (errorVariable.getMissingType() != -1) {
			final IValidationResult typeValidation = acceleoValidationResult.getValidationResult(errorVariable
					.getType());
			res.addAll(astCompletor.getProposals(Collections.emptySet(), typeValidation).stream().map(
					AcceleoAstCompletor::transform).collect(Collectors.toList()));
		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorExpression(ErrorExpression errorExpression) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorExpression.getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			res.addAll(this.getAqlCompletionProposals(getVariables(errorExpression), acceleoValidationResult
					.getValidationResult(errorExpression.getAst())));
		}

		return res;
	}

	/**
	 * Gets the mapping of {@link String variable name} to its possible {@link IType} in the scope of the
	 * given {@link ASTNode}.
	 * 
	 * @param scope
	 *            the {@link ASTNode} scope
	 * @return the mapping of {@link String variable name} to its possible {@link IType} in the scope of the
	 *         given {@link ASTNode}
	 */
	private Map<String, Set<IType>> getVariables(ASTNode scope) {
		final Map<String, Set<IType>> res = new HashMap<String, Set<IType>>();

		ASTNode currentScope = scope;
		while (currentScope != null) {
			if (currentScope instanceof Template) {
				final Template template = (Template)currentScope;
				for (Variable variable : template.getParameters()) {
					res.put(variable.getName(), getPossibleTypes(variable));
				}
			} else if (currentScope instanceof Query) {
				final Query query = (Query)currentScope;
				for (Variable variable : query.getParameters()) {
					res.put(variable.getName(), getPossibleTypes(variable));
				}
			} else if (currentScope instanceof LetStatement) {
				final LetStatement let = (LetStatement)currentScope;
				for (Variable variable : let.getVariables()) {
					res.put(variable.getName(), getPossibleTypes(variable));
				}
			} else if (currentScope instanceof ForStatement) {
				final ForStatement forStatement = (ForStatement)currentScope;
				res.put(forStatement.getBinding().getName(), getPossibleTypes(forStatement.getBinding()));
			}

			if (currentScope.eContainer() instanceof ASTNode) {
				currentScope = (ASTNode)currentScope.eContainer();
			} else {
				currentScope = null;
			}
		}

		return res;
	}

	/**
	 * Gets the {@link Set} of possible {@link IType} for the given {@link Variable}.
	 * 
	 * @param variable
	 *            the {@link Variable}
	 * @return the {@link Set} of possible {@link IType} for the given {@link Variable}
	 */
	private LinkedHashSet<IType> getPossibleTypes(Variable variable) {
		final LinkedHashSet<IType> res = new LinkedHashSet<IType>();

		final IValidationResult validationResult = acceleoValidationResult.getValidationResult(variable
				.getType());
		if (variable.getType() != null) {
			res.addAll(validationResult.getPossibleTypes(variable.getType().getAst()));
		}

		return res;
	}

	/**
	 * Gets the {@link Set} of possible {@link IType} for the given {@link Variable}.
	 * 
	 * @param binding
	 *            the {@link Variable}
	 * @return the {@link Set} of possible {@link IType} for the given {@link Variable}
	 */
	private LinkedHashSet<IType> getPossibleTypes(Binding binding) {
		final LinkedHashSet<IType> res;

		if (binding.getInitExpression() != null) {
			final IValidationResult validationResult = acceleoValidationResult.getValidationResult(binding
					.getInitExpression().getAst());
			res = new LinkedHashSet<IType>();
			if (binding.getType() != null) {
				res.addAll(validationResult.getPossibleTypes(binding.getInitExpression().getAst().getAst()));
			}
		} else {
			res = getPossibleTypes((Variable)binding);
		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorBinding(ErrorBinding errorBinding) {
		final List<AcceleoCompletionProposal> completionProposals = new ArrayList<AcceleoCompletionProposal>();

		if (errorBinding.getMissingName() != -1) {
			String sampleVariableName = AcceleoCodeTemplates.DEFAULT_NEW_BINDING_VARIABLE_NAME;
			completionProposals.add(new AcceleoCodeTemplateCompletionProposal(sampleVariableName,
					sampleVariableName, AcceleoPackage.Literals.BINDING));
		} else if (errorBinding.getMissingColon() != -1) {
			completionProposals.add(AcceleoSyntacticCompletionProposals.COLON_SPACE);
		} else if (errorBinding.getMissingType() != -1) {
			final IValidationResult typeValidationResult = acceleoValidationResult.getValidationResult(
					errorBinding.getType());
			completionProposals.addAll(astCompletor.getProposals(Collections.emptySet(), typeValidationResult)
					.stream().map(AcceleoAstCompletor::transform).collect(Collectors.toList()));
		} else if (errorBinding.getMissingAffectationSymbolePosition() != -1) {
			if (errorBinding.getType() == null && errorBinding.getTypeAql() == null) {
				completionProposals.add(AcceleoSyntacticCompletionProposals.COLON_SPACE);
			}
			completionProposals.add(new AcceleoCompletionProposal(errorBinding.getMissingAffectationSymbole(),
					errorBinding.getMissingAffectationSymbole() + SPACE, AcceleoPackage.Literals.BINDING));
		} else if (errorBinding.getInitExpression().getAst()
				.getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			completionProposals.addAll(this.getAqlCompletionProposals(getVariables(errorBinding),
					acceleoValidationResult.getValidationResult(errorBinding.getInitExpression().getAst())));
		}

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorExpressionStatement(
			ErrorExpressionStatement errorExpressionStatement) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorExpressionStatement.getExpression().getAst()
				.getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			res.addAll(this.getAqlCompletionProposals(getVariables(errorExpressionStatement),
					acceleoValidationResult.getValidationResult(errorExpressionStatement.getExpression()
							.getAst())));
		} else if (errorExpressionStatement.getMissingEndHeader() != -1) {
			res.addAll(this.getAqlCompletionProposals(getVariables(errorExpressionStatement),
					acceleoValidationResult.getValidationResult(errorExpressionStatement.getExpression()
							.getAst())));
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_EXPRESSION_END);
		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorProtectedArea(ErrorProtectedArea errorProtectedArea) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorProtectedArea.getMissingOpenParenthesis() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.OPEN_PARENTHESIS);
		} else if (errorProtectedArea.getId().getAst()
				.getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			res.addAll(this.getAqlCompletionProposals(getVariables(errorProtectedArea),
					acceleoValidationResult.getValidationResult(errorProtectedArea.getId().getAst())));
		} else if (errorProtectedArea.getMissingCloseParenthesis() != -1) {
			res.addAll(this.getAqlCompletionProposals(getVariables(errorProtectedArea),
					acceleoValidationResult.getValidationResult(errorProtectedArea.getId().getAst())));
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_PROTECTED_AREA_HEADER_CLOSE_PARENTHESIS_AND_END);
		} else if (errorProtectedArea.getMissingEndHeader() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_PROTECTED_AREA_HEADER_END);
		} else if (errorProtectedArea.getMissingEnd() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_PROTECTED_AREA_END);
			res.addAll(getStatementProposals());
		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorForStatement(ErrorForStatement errorForStatement) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorForStatement.getMissingOpenParenthesis() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.OPEN_PARENTHESIS);
		} else if (errorForStatement.getMissingBinding() != -1) {
			res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(
					AcceleoPackage.Literals.BINDING));
			res.add(AcceleoSyntacticCompletionProposals.FOR_STATEMENT_PIPE);
		} else if (errorForStatement.getMissingCloseParenthesis() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_FOR_HEADER_CLOSE_PARENTHESIS_AND_END);
			if (errorForStatement.getSeparator() == null) {
				res.add(AcceleoSyntacticCompletionProposals.STATEMENT_FOR_HEADER_SEPARATOR);
			}
			res.addAll(this.getAqlCompletionProposals(getVariables(errorForStatement), acceleoValidationResult
					.getValidationResult(errorForStatement.getBinding().getType())));
		} else if (errorForStatement.getSeparator() != null && errorForStatement.getSeparator().getAst()
				.getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			res.addAll(this.getAqlCompletionProposals(getVariables(errorForStatement), acceleoValidationResult
					.getValidationResult(errorForStatement.getSeparator().getAst())));
		} else if (errorForStatement.getMissingSeparatorCloseParenthesis() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_FOR_HEADER_CLOSE_PARENTHESIS_AND_END);
		} else if (errorForStatement.getMissingEndHeader() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_FOR_HEADER_END);
			if (errorForStatement.getSeparator() == null) {
				res.add(AcceleoSyntacticCompletionProposals.STATEMENT_FOR_HEADER_SEPARATOR);
			}
		} else if (errorForStatement.getMissingEnd() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_FOR_END);
			res.addAll(getStatementProposals());
		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorIfStatement(ErrorIfStatement errorIfStatement) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorIfStatement.getMissingOpenParenthesis() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.OPEN_PARENTHESIS);
		} else if (errorIfStatement.getCondition().getAst()
				.getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			res.addAll(this.getAqlCompletionProposals(getVariables(errorIfStatement), acceleoValidationResult
					.getValidationResult(errorIfStatement.getCondition().getAst())));
		} else if (errorIfStatement.getMissingCloseParenthesis() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_IF_HEADER_CLOSE_PARENTHESIS_AND_END);
			res.addAll(this.getAqlCompletionProposals(getVariables(errorIfStatement), acceleoValidationResult
					.getValidationResult(errorIfStatement.getCondition().getAst())));
		} else if (errorIfStatement.getMissingEndHeader() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_IF_HEADER_END);
		} else if (errorIfStatement.getMissingEnd() != -1) {
			if (errorIfStatement.getElse() == null) {
				res.add(AcceleoSyntacticCompletionProposals.STATEMENT_IF_ELSE);
				if (errorIfStatement.eContainer().eContainingFeature() == AcceleoPackage.eINSTANCE
						.getIfStatement_Else() || !(errorIfStatement.eContainer()
								.eContainer() instanceof IfStatement)) {
					res.add(AcceleoSyntacticCompletionProposals.STATEMENT_IF_ELSEIF);
				}
			}
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_IF_END);
			res.addAll(getStatementProposals());
		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorLetStatement(ErrorLetStatement errorLetStatement) {
		final List<AcceleoCompletionProposal> completionProposals = new ArrayList<AcceleoCompletionProposal>();
		if (errorLetStatement.getMissingBindings() != -1) {
			completionProposals.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(
					AcceleoPackage.Literals.BINDING));
		} else if (errorLetStatement.getMissingEndHeader() != -1) {
			completionProposals.add(AcceleoSyntacticCompletionProposals.STATEMENT_LET_HEADER_END);

			List<Binding> bindings = errorLetStatement.getVariables();
			completionProposals.addAll(this.getAqlCompletionProposals(getVariables(errorLetStatement),
					acceleoValidationResult.getValidationResult(bindings.get(bindings.size() - 1)
							.getType())));
		} else if (errorLetStatement.getMissingEnd() != -1) {
			completionProposals.add(AcceleoSyntacticCompletionProposals.STATEMENT_LET_END);
			completionProposals.addAll(getStatementProposals());
		}

		return completionProposals;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorFileStatement(ErrorFileStatement errorFileStatement) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorFileStatement.getMissingOpenParenthesis() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.OPEN_PARENTHESIS);
		} else if (errorFileStatement.getUrl().getAst()
				.getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			res.addAll(this.getAqlCompletionProposals(getVariables(errorFileStatement),
					acceleoValidationResult.getValidationResult(errorFileStatement.getUrl().getAst())));
		} else if (errorFileStatement.getMissingComma() != -1) {
			res.addAll(this.getAqlCompletionProposals(getVariables(errorFileStatement),
					acceleoValidationResult.getValidationResult(errorFileStatement.getUrl().getAst())));
			res.add(AcceleoSyntacticCompletionProposals.COMMA_SPACE);
		} else if (errorFileStatement.getMissingOpenMode() != -1) {
			res.addAll(AcceleoSyntacticCompletionProposals.STATEMENT_FILE_OPEN_MODES);
		} else if (errorFileStatement.getMissingCloseParenthesis() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_FILE_HEADER_CLOSE_PARENTHESIS_AND_END);
		} else if (errorFileStatement.getMissingEndHeader() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_FILE_HEADER_END);
		} else if (errorFileStatement.getMissingEnd() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_FILE_END);
			res.addAll(getStatementProposals());
		}

		return res;
	}

	/**
	 * Retrieves the AQL completion proposals and transforms them into the corresponding Acceleo completion
	 * proposals.
	 * 
	 * @param variables
	 *            see {@link AstCompletor#getProposals(Set, IValidationResult)}.
	 * @param aqlValidationResult
	 *            see {@link AstCompletor#getProposals(Set, IValidationResult)}.
	 * @return the {@link List} of {@link AcceleoCompletionProposal} corresponding to the AQL completion
	 *         proposals.
	 */
	private List<AcceleoCompletionProposal> getAqlCompletionProposals(Map<String, Set<IType>> variables,
			IValidationResult aqlValidationResult) {
		final int startPosition = acceleoValidationResult.getAcceleoAstResult().getStartPosition(
				aqlValidationResult.getAstResult().getAst());
		final String expression = moduleSourceFragment.substring(startPosition, moduleSourceFragment
				.length());
		final ICompletionResult completionResult = aqlCompletionEngine.getCompletion(expression, expression
				.length(), variables);
		final List<ICompletionProposal> aqlProposals = completionResult.getProposals(new BasicFilter(
				completionResult));
		Collections.sort(aqlProposals, COMPLETION_PROPOSAL_COMPARATOR);
		final List<AcceleoCompletionProposal> aqlProposalsAsAcceleoProposals = aqlProposals.stream().map(
				AcceleoAstCompletor::transform).collect(Collectors.toList());
		return aqlProposalsAsAcceleoProposals;
	}

	/**
	 * Transforms an AQL {@link ICompletionProposal} into a corresponding {@link AcceleoCompletionProposal}.
	 * 
	 * @param aqlCompletionProposal
	 *            the (non-{@code null}) {@link ICompletionProposal} to transform.
	 * @return the corresponding {@link AcceleoCompletionProposal}.
	 */
	private static AcceleoCompletionProposal transform(ICompletionProposal aqlCompletionProposal) {
		return new AcceleoCompletionProposal(aqlCompletionProposal.getProposal(), aqlCompletionProposal
				.getDescription().replace("\n", "<br>"), aqlCompletionProposal.getProposal(),
				AcceleoPackage.Literals.EXPRESSION);
	}

}
