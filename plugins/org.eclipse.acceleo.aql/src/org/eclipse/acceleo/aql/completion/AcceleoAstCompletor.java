/*******************************************************************************
 * Copyright (c) 2020, 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.completion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.acceleo.AcceleoASTNode;
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
import org.eclipse.acceleo.TypedElement;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.completion.proposals.AcceleoCompletionProposal;
import org.eclipse.acceleo.aql.completion.proposals.AcceleoCompletionProposalsProvider;
import org.eclipse.acceleo.aql.completion.proposals.syntax.AcceleoSyntacticCompletionProposals;
import org.eclipse.acceleo.aql.completion.proposals.templates.AcceleoCodeTemplateCompletionProposal;
import org.eclipse.acceleo.aql.completion.proposals.templates.AcceleoCodeTemplateCompletionProposalsProvider;
import org.eclipse.acceleo.aql.completion.proposals.templates.AcceleoCodeTemplates;
import org.eclipse.acceleo.aql.evaluation.QueryService;
import org.eclipse.acceleo.aql.evaluation.TemplateService;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.validation.AcceleoValidator;
import org.eclipse.acceleo.aql.validation.IAcceleoValidationResult;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.parser.AstCompletor;
import org.eclipse.acceleo.query.runtime.ICompletionProposal;
import org.eclipse.acceleo.query.runtime.ICompletionResult;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.IServiceCompletionProposal;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.impl.BasicFilter;
import org.eclipse.acceleo.query.runtime.impl.QueryCompletionEngine;
import org.eclipse.acceleo.query.runtime.impl.completion.EClassifierCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.EEnumLiteralCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.EFeatureCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.EOperationServiceCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.VariableCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.completion.VariableDeclarationCompletionProposal;
import org.eclipse.acceleo.query.runtime.impl.namespace.Range;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.ISourceLocation.IRange;
import org.eclipse.acceleo.query.services.StringServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.util.AcceleoSwitch;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
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

		/**
		 * The current qualified name.
		 */
		private String currentQualifiedName;

		/**
		 * The closure of extended modules.
		 */
		private final Set<String> extended;

		/**
		 * The {@link IQualifiedNameResolver}.
		 */
		private IQualifiedNameResolver resolver;

		/**
		 * The {@link Set} of receiver {@link EClass}.
		 */
		private final Set<EClass> receiverEClasses;

		/**
		 * Constructor.
		 * 
		 * @param queryEnvironment
		 *            the {@link IQualifiedNameQueryEnvironment}
		 * @param possibleReceiverTypes
		 *            the {@link Set} of possible receiver {@link IType}
		 */
		public ProposalComparator(IQualifiedNameQueryEnvironment queryEnvironment,
				Set<IType> possibleReceiverTypes) {
			this.currentQualifiedName = queryEnvironment.getLookupEngine().getCurrentContext().peek();
			this.resolver = queryEnvironment.getLookupEngine().getResolver();
			extended = getAllExtended(resolver, currentQualifiedName);
			receiverEClasses = new HashSet<>();
			for (IType possibleReceiverType : possibleReceiverTypes) {
				if (possibleReceiverType instanceof EClassifierType) {
					if (((EClassifierType)possibleReceiverType).getType() instanceof EClass) {
						receiverEClasses.add((EClass)((EClassifierType)possibleReceiverType).getType());
					}
				} else if (possibleReceiverType instanceof ClassType) {
					for (EClassifier eClassifier : queryEnvironment.getEPackageProvider().getEClassifiers(
							((ClassType)possibleReceiverType).getType())) {
						if (eClassifier instanceof EClass) {
							receiverEClasses.add((EClass)eClassifier);
						}
					}
				}
			}
		}

		/**
		 * Gets the closure of extended qualified names.
		 * 
		 * @param resolver
		 *            the {@link IQualifiedNameResolver}
		 * @param currentQualifiedName
		 *            the current qualified name
		 * @return
		 */
		private Set<String> getAllExtended(IQualifiedNameResolver resolver, String currentQualifiedName) {
			final Set<String> res = new HashSet<>();

			String superQualifiedName = resolver.getExtend(currentQualifiedName);
			while (superQualifiedName != null) {
				res.add(superQualifiedName);
				superQualifiedName = resolver.getExtend(superQualifiedName);
			}

			return res;
		}

		@Override
		public int compare(ICompletionProposal o1, ICompletionProposal o2) {
			final int res;

			final int value1 = getValue(o1);
			final int value2 = getValue(o2);

			if (value1 > value2) {
				res = 1;
			} else if (value1 < value2) {
				res = -1;
			} else if (o1 != null && o2 != null) {
				if (o1 instanceof IServiceCompletionProposal && o2 instanceof IServiceCompletionProposal) {
					res = ((IServiceCompletionProposal)o1).getObject().getShortSignature().compareTo(
							((IServiceCompletionProposal)o2).getObject().getShortSignature());
				} else {
					res = o1.getProposal().compareTo(o2.getProposal());
				}
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
				if (receiverEClasses.contains(((EFeatureCompletionProposal)proposal).getObject()
						.getEContainingClass())) {
					res = 1;
				} else {
					res = 2;
				}
			} else if (proposal instanceof IServiceCompletionProposal
					|| proposal instanceof EOperationServiceCompletionProposal) {
				if (proposal.getObject() instanceof TemplateService || proposal
						.getObject() instanceof QueryService) {
					final String serviceQualifiedName = resolver.getContextQualifiedName((IService<?>)proposal
							.getObject());
					if (currentQualifiedName.equals(serviceQualifiedName)) {
						res = 3;
					} else if (extended.contains(serviceQualifiedName)) {
						res = 4;
					} else {
						res = 5;
					}
				} else {
					res = 6;
				}
			} else if (proposal instanceof EClassifierCompletionProposal
					|| proposal instanceof EEnumLiteralCompletionProposal) {
				res = 7;
			} else {
				res = 8;
			}

			return res;
		}

	}

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
	 * The {@link AcceleoCompletionProposalsProvider}.
	 */
	private final AcceleoCompletionProposalsProvider acceleoCompletionProposalProvider;

	/**
	 * The new line {@link String}.
	 */
	private final String newLine;

	/**
	 * The {@link AcceleoCodeTemplates} with {@link #newLine}.
	 */
	private final AcceleoCodeTemplates acceleoCodeTemplates;

	/**
	 * The computed module name.
	 */
	private String computedModuleName;

	/**
	 * The module source fragment.
	 */
	private String moduleSourceFragment;

	/**
	 * The caret position in {@link #moduleSourceFragment}.
	 */
	private int position;

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the (non-{@code null}) contextual {@link IQualifiedNameQueryEnvironment}.
	 * @param acceleoValidationResult
	 *            the (non-{@code null}) contextual {@link IAcceleoValidationResult}.
	 * @param newLine
	 *            the new line {@link String}
	 */
	public AcceleoAstCompletor(IQualifiedNameQueryEnvironment queryEnvironment,
			IAcceleoValidationResult acceleoValidationResult, String newLine) {
		this.queryEnvironment = Objects.requireNonNull(queryEnvironment);
		this.acceleoValidationResult = Objects.requireNonNull(acceleoValidationResult);

		this.aqlCompletionEngine = new QueryCompletionEngine(queryEnvironment);
		this.acceleoCompletionProposalProvider = new AcceleoCompletionProposalsProvider(newLine);
		this.newLine = newLine;
		this.acceleoCodeTemplates = new AcceleoCodeTemplates(newLine);
	}

	/**
	 * Get the {@link List} of {@link AcceleoCompletionProposal} for the given acceleo element.
	 * 
	 * @param computedModuleName
	 *            the computed module name
	 * @param sourceFragment
	 *            the module source code
	 * @param position
	 *            the caret position in {@code source}.
	 * @param acceleoElementToComplete
	 *            the acceleo element to complete
	 * @return the {@link List} of {@link AcceleoCompletionProposal} for the given acceleo element
	 */
	public List<AcceleoCompletionProposal> getCompletion(String computedModuleName, String sourceFragment,
			int position, EObject acceleoElementToComplete) {
		this.computedModuleName = computedModuleName;
		this.moduleSourceFragment = sourceFragment;
		this.position = position;

		return doSwitch(acceleoElementToComplete);
	}

	@Override
	public List<AcceleoCompletionProposal> caseModule(Module moduleToComplete) {
		List<AcceleoCompletionProposal> res = new ArrayList<>();

		if (!moduleToComplete.getModuleElements().stream().anyMatch(
				moduleElement -> moduleElement instanceof Query || moduleElement instanceof Template)) {
			// The module has no queries or templates, or the cursor is above them, so we can add imports.
			res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(computedModuleName,
					AcceleoPackage.Literals.IMPORT));
		}

		res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.TEMPLATE));
		res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.QUERY));
		res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.MODULE_ELEMENT_DOCUMENTATION));
		res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.COMMENT));
		res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.BLOCK_COMMENT));
		res.add(AcceleoCodeTemplateCompletionProposalsProvider.NEW_COMMENT_MAIN);

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorComment(ErrorComment errorComment) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorComment.getMissingSpace() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.COMMA_SPACE);
		} else if (errorComment.getMissingEndHeader() != -1) {
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
				res.add(new AcceleoCodeTemplateCompletionProposal(computedModuleName, computedModuleName,
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
			if (errorModule.getExtends() == null) {
				res.add(AcceleoSyntacticCompletionProposals.MODULE_HEADER_END);
				res.add(AcceleoSyntacticCompletionProposals.MODULE_EXTENSION);
			} else {
				final String referenceQualifiedName = errorModule.getExtends().getQualifiedName();
				final List<AcceleoCompletionProposal> refenceCompletions = getReferenceCompletion(
						referenceQualifiedName);
				if (!refenceCompletions.isEmpty()) {
					res.addAll(refenceCompletions);
				} else {
					res.add(AcceleoSyntacticCompletionProposals.MODULE_HEADER_END);
				}
			}
		} else {
			// We do not even have a module header.
			res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(computedModuleName,
					AcceleoPackage.Literals.MODULE));
			res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(computedModuleName,
					AcceleoPackage.Literals.MODULE_DOCUMENTATION));
			res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(computedModuleName,
					AcceleoPackage.Literals.COMMENT));
			res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(computedModuleName,
					AcceleoPackage.Literals.BLOCK_COMMENT));
		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorMetamodel(ErrorMetamodel errorMetamodel) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorMetamodel.getFragment() != null) {
			for (String nsURI : EPackage.Registry.INSTANCE.keySet()) {
				if (nsURI.contains(errorMetamodel.getFragment())) {
					res.add(new AcceleoCompletionProposal(nsURI, nsURI, AcceleoPackage.Literals.METAMODEL));
				}
			}
		} else if (errorMetamodel.getMissingEndQuote() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.QUOTE_DOUBLE);
		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorImport(ErrorImport errorImport) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorImport.getMissingEnd() != -1) {
			final String referenceQualifiedName = errorImport.getModule().getQualifiedName();
			final List<AcceleoCompletionProposal> refenceCompletions = getReferenceCompletion(
					referenceQualifiedName);
			if (!refenceCompletions.isEmpty()) {
				res.addAll(refenceCompletions);
			} else {
				res.add(AcceleoSyntacticCompletionProposals.IMPORT_END);
			}
		}

		return res;
	}

	/**
	 * Gets the {@link List} of {@link AcceleoCompletionProposal} for the given reference qualified name.
	 * 
	 * @param referenceQualifiedName
	 *            the reference qualified name
	 * @return the {@link List} of {@link AcceleoCompletionProposal} for the given reference qualified name
	 */
	private List<AcceleoCompletionProposal> getReferenceCompletion(String referenceQualifiedName) {
		final List<AcceleoCompletionProposal> res = new ArrayList<>();

		if (queryEnvironment.getLookupEngine().getResolver().resolve(referenceQualifiedName) == null) {
			final List<String> availableQualifiedNames = new ArrayList<String>(queryEnvironment
					.getLookupEngine().getResolver().getAvailableQualifiedNames());
			Collections.sort(availableQualifiedNames);
			for (String qualifiedName : availableQualifiedNames) {
				if (referenceQualifiedName == null || qualifiedName.contains(referenceQualifiedName)) {
					res.add(new AcceleoCodeTemplateCompletionProposal(qualifiedName, qualifiedName,
							AcceleoPackage.Literals.MODULE_REFERENCE));
				}
			}
		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorModuleReference(
			ErrorModuleReference errorModuleReference) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		final List<String> availableQualifiedNames = new ArrayList<String>(queryEnvironment.getLookupEngine()
				.getResolver().getAvailableQualifiedNames());
		Collections.sort(availableQualifiedNames);
		for (String qualifiedName : availableQualifiedNames) {
			res.add(new AcceleoCodeTemplateCompletionProposal(qualifiedName, qualifiedName,
					AcceleoPackage.Literals.MODULE_REFERENCE));
		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorTemplate(ErrorTemplate errorTemplate) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorTemplate.getMissingVisibility() != -1) {
			res.addAll(AcceleoSyntacticCompletionProposals.MODULE_ELEMENT_VISIBILITY_KINDS);
		} else if (errorTemplate.getMissingName() != -1) {
			String sampleTemplateName = AcceleoCodeTemplates.DEFAULT_NEW_TEMPLATE_NAME;
			res.add(new AcceleoCodeTemplateCompletionProposal(sampleTemplateName, sampleTemplateName,
					AcceleoPackage.Literals.TEMPLATE));
		} else if (errorTemplate.getMissingOpenParenthesis() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.OPEN_PARENTHESIS);
		} else if (errorTemplate.getMissingParameters() != -1) {
			String sampleParameterName = AcceleoCodeTemplates.DEFAULT_NEW_TEMPLATE_PARAMETER_NAME;
			res.add(new AcceleoCodeTemplateCompletionProposal(sampleParameterName, sampleParameterName,
					AcceleoPackage.Literals.TEMPLATE));
		} else if (errorTemplate.getMissingCloseParenthesis() != -1) {
			if (!errorTemplate.getParameters().isEmpty()) {
				final Variable parameter = errorTemplate.getParameters().get(errorTemplate.getParameters()
						.size() - 1);
				final Set<IType> types = getPossibleTypes(parameter);
				if (types.stream().filter(t -> !(t instanceof NothingType)).collect(Collectors.toList())
						.isEmpty()) {
					res.addAll(getAqlCompletionProposals(Collections.emptyMap(), acceleoValidationResult
							.getValidationResult(parameter.getType())));
				} else {
					res.add(AcceleoSyntacticCompletionProposals.CLOSE_PARENTHESIS);
					res.add(AcceleoSyntacticCompletionProposals.COMMA_SPACE);
				}
			} else {
				res.add(AcceleoSyntacticCompletionProposals.CLOSE_PARENTHESIS);
				res.add(AcceleoSyntacticCompletionProposals.COMMA_SPACE);
			}
		} else if (errorTemplate.getMissingGuardOpenParenthesis() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.OPEN_PARENTHESIS);
		} else if (errorTemplate.getGuard() != null && errorTemplate.getGuard().getAst()
				.getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			res.addAll(getAqlCompletionProposals(getVariables(errorTemplate), acceleoValidationResult
					.getValidationResult(errorTemplate.getGuard().getAst())));
		} else if (errorTemplate.getMissingGuardCloseParenthesis() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.CLOSE_PARENTHESIS);
			res.addAll(getAqlCompletionProposals(getVariables(errorTemplate), acceleoValidationResult
					.getValidationResult(errorTemplate.getGuard().getAst())));
		} else if (errorTemplate.getPost() != null && errorTemplate.getPost().getAst()
				.getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			final Map<String, Set<IType>> variables = new HashMap<String, Set<IType>>();
			final Set<IType> possibleTypes = Collections.singleton(new ClassType(queryEnvironment,
					String.class));
			variables.put(AcceleoUtil.getTemplateImplicitVariableName(), possibleTypes);
			res.addAll(getAqlCompletionProposals(variables, acceleoValidationResult.getValidationResult(
					errorTemplate.getPost().getAst())));
		} else if (errorTemplate.getMissingPostCloseParenthesis() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.CLOSE_PARENTHESIS);
			final Map<String, Set<IType>> variables = new HashMap<String, Set<IType>>();
			final Set<IType> possibleTypes = Collections.singleton(new ClassType(queryEnvironment,
					String.class));
			variables.put(AcceleoUtil.getTemplateImplicitVariableName(), possibleTypes);
			res.addAll(getAqlCompletionProposals(variables, acceleoValidationResult.getValidationResult(
					errorTemplate.getPost().getAst())));
		} else if (errorTemplate.getMissingEndHeader() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.TEMPLATE_HEADER_END);
			if (errorTemplate.getGuard() == null && errorTemplate.getPost() == null) {
				res.add(AcceleoSyntacticCompletionProposals.TEMPLATE_GUARD_START);
			}
			if (errorTemplate.getPost() == null) {
				res.add(AcceleoSyntacticCompletionProposals.TEMPLATE_POST_START);
			}
		} else if (errorTemplate.getMissingEnd() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.TEMPLATE_END);
			final int column = acceleoValidationResult.getAcceleoAstResult().getEndColumn(errorTemplate);
			res.addAll(getStatementProposals(column));
		}

		return res;
	}

	/**
	 * Gets the {@link List} of {@link AcceleoCompletionProposal} for {@link org.eclipse.acceleo.Statement
	 * Statement}.
	 * 
	 * @param column
	 *            the current column
	 * @return the {@link List} of {@link AcceleoCompletionProposal} for {@link org.eclipse.acceleo.Statement
	 *         Statement}
	 */
	private List<AcceleoCompletionProposal> getStatementProposals(int column) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		res.addAll(getBodyCompletionProposals(column));
		res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.STATEMENT));
		res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.COMMENT));
		res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(computedModuleName,
				AcceleoPackage.Literals.BLOCK_COMMENT));

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorQuery(ErrorQuery errorQuery) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorQuery.getMissingVisibility() != -1) {
			res.addAll(AcceleoSyntacticCompletionProposals.MODULE_ELEMENT_VISIBILITY_KINDS);
		} else if (errorQuery.getMissingName() != -1) {
			String sampleQueryName = AcceleoCodeTemplates.DEFAULT_NEW_QUERY_NAME;
			res.add(new AcceleoCodeTemplateCompletionProposal(sampleQueryName, sampleQueryName,
					AcceleoPackage.Literals.QUERY));
		} else if (errorQuery.getMissingOpenParenthesis() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.OPEN_PARENTHESIS);
		} else if (errorQuery.getMissingParameters() != -1) {
			String sampleParameterName = AcceleoCodeTemplates.DEFAULT_NEW_QUERY_PARAMETER_NAME;
			res.add(new AcceleoCodeTemplateCompletionProposal(sampleParameterName, sampleParameterName,
					AcceleoPackage.Literals.TEMPLATE));
		} else if (errorQuery.getMissingCloseParenthesis() != -1) {
			if (!errorQuery.getParameters().isEmpty()) {
				final Variable parameter = errorQuery.getParameters().get(errorQuery.getParameters().size()
						- 1);
				final Set<IType> types = getPossibleTypes(parameter);
				if (types.stream().filter(t -> !(t instanceof NothingType)).collect(Collectors.toList())
						.isEmpty()) {
					res.addAll(getAqlCompletionProposals(Collections.emptyMap(), acceleoValidationResult
							.getValidationResult(parameter.getType())));
				} else {
					res.add(AcceleoSyntacticCompletionProposals.CLOSE_PARENTHESIS);
					res.add(AcceleoSyntacticCompletionProposals.COMMA_SPACE);
				}
			} else {
				res.add(AcceleoSyntacticCompletionProposals.CLOSE_PARENTHESIS);
				res.add(AcceleoSyntacticCompletionProposals.COMMA_SPACE);
			}
		} else if (errorQuery.getMissingColon() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.COLON_SPACE);
		} else if (errorQuery.getMissingType() != -1) {
			final IValidationResult typeValidation = acceleoValidationResult.getValidationResult(errorQuery
					.getType());
			res.addAll(getAqlCompletionProposals(Collections.emptyMap(), typeValidation));
		} else if (errorQuery.getMissingEqual() != -1) {
			final Set<IType> types = getPossibleTypes(errorQuery);
			if (types.stream().filter(t -> !(t instanceof NothingType)).collect(Collectors.toList())
					.isEmpty()) {
				res.addAll(getAqlCompletionProposals(Collections.emptyMap(), acceleoValidationResult
						.getValidationResult(errorQuery.getType())));
			} else {
				res.add(AcceleoSyntacticCompletionProposals.EQUAL_SPACE);
			}
		} else if (errorQuery.getBody().getAst().getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			res.addAll(getAqlCompletionProposals(getVariables(errorQuery), acceleoValidationResult
					.getValidationResult(errorQuery.getBody().getAst())));
		} else if (errorQuery.getMissingEnd() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.QUERY_END);
			// Before the end of a query, there is the value expression, which is valid in this case.
			res.addAll(getAqlCompletionProposals(getVariables(errorQuery), acceleoValidationResult
					.getValidationResult(errorQuery.getBody().getAst())));
		}

		return res;
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
			res.addAll(getAqlCompletionProposals(Collections.emptyMap(), typeValidation));
		} else {
			res.addAll(getAqlCompletionProposals(Collections.emptyMap(), acceleoValidationResult
					.getValidationResult(errorVariable.getType())));
		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorExpression(ErrorExpression errorExpression) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorExpression.getAst().getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			res.addAll(getAqlCompletionProposals(getVariables(errorExpression), acceleoValidationResult
					.getValidationResult(errorExpression.getAst())));
		}

		return res;
	}

	/**
	 * Gets the mapping of {@link String variable name} to its possible {@link IType} in the scope of the
	 * given {@link AcceleoASTNode}.
	 * 
	 * @param scope
	 *            the {@link AcceleoASTNode} scope
	 * @return the mapping of {@link String variable name} to its possible {@link IType} in the scope of the
	 *         given {@link AcceleoASTNode}
	 */
	private Map<String, Set<IType>> getVariables(AcceleoASTNode scope) {
		final Map<String, Set<IType>> res = new HashMap<String, Set<IType>>();

		AcceleoASTNode currentScope = scope;
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
				for (Binding binding : let.getVariables()) {
					res.put(binding.getName(), getPossibleTypes(binding));
				}
			} else if (currentScope instanceof ForStatement) {
				final ForStatement forStatement = (ForStatement)currentScope;
				res.put(forStatement.getBinding().getName(), getPossibleTypes(forStatement.getBinding()));
				final Set<IType> possibleIndexTypes = new LinkedHashSet<>();
				possibleIndexTypes.add(new ClassType(queryEnvironment, Integer.class));
				res.put(forStatement.getBinding().getName() + AcceleoValidator.INDEX_SUFFIX,
						possibleIndexTypes);
			}

			if (currentScope.eContainer() instanceof AcceleoASTNode) {
				currentScope = (AcceleoASTNode)currentScope.eContainer();
			} else {
				currentScope = null;
			}
		}

		return res;
	}

	/**
	 * Gets the {@link Set} of possible {@link IType} for the given {@link TypedElement}.
	 * 
	 * @param variable
	 *            the {@link Variable}
	 * @return the {@link Set} of possible {@link IType} for the given {@link TypedElement}
	 */
	private LinkedHashSet<IType> getPossibleTypes(TypedElement variable) {
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
	private Set<IType> getPossibleTypes(Binding binding) {
		final LinkedHashSet<IType> res;

		if (binding.getInitExpression() != null) {
			final IValidationResult validationResult = acceleoValidationResult.getValidationResult(binding
					.getInitExpression().getAst());
			res = new LinkedHashSet<IType>();
			res.addAll(validationResult.getPossibleTypes(binding.getInitExpression().getAst().getAst()));
		} else {
			res = getPossibleTypes((TypedElement)binding);
		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorBinding(ErrorBinding errorBinding) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorBinding.getMissingName() != -1) {
			String sampleVariableName = AcceleoCodeTemplates.DEFAULT_NEW_BINDING_VARIABLE_NAME;
			res.add(new AcceleoCodeTemplateCompletionProposal(sampleVariableName, sampleVariableName,
					AcceleoPackage.Literals.BINDING));
		} else if (errorBinding.getMissingColon() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.COLON_SPACE);
		} else if (errorBinding.getMissingType() != -1) {
			final IValidationResult typeValidation = acceleoValidationResult.getValidationResult(errorBinding
					.getType());
			res.addAll(getAqlCompletionProposals(Collections.emptyMap(), typeValidation));
		} else if (errorBinding.getMissingAffectationSymbolePosition() != -1) {
			if (errorBinding.getType() == null && errorBinding.getTypeAql() == null) {
				res.add(AcceleoSyntacticCompletionProposals.COLON_SPACE);
			}
			res.add(new AcceleoCompletionProposal(errorBinding.getMissingAffectationSymbole(), errorBinding
					.getMissingAffectationSymbole() + AcceleoParser.SPACE, AcceleoPackage.Literals.BINDING));
		} else if (errorBinding.getInitExpression().getAst()
				.getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			final AcceleoASTNode context = (AcceleoASTNode)errorBinding.eContainer().eContainer();
			res.addAll(getAqlCompletionProposals(getVariables(context), acceleoValidationResult
					.getValidationResult(errorBinding.getInitExpression().getAst())));
		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorExpressionStatement(
			ErrorExpressionStatement errorExpressionStatement) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorExpressionStatement.getExpression().getAst()
				.getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			res.addAll(getAqlCompletionProposals(getVariables(errorExpressionStatement),
					acceleoValidationResult.getValidationResult(errorExpressionStatement.getExpression()
							.getAst())));
			if (errorExpressionStatement.getExpression().getAst()
					.getAst() instanceof org.eclipse.acceleo.query.ast.ErrorExpression) {
				res.addAll(getHeaderStarts(""));
			}
		} else if (errorExpressionStatement.getMissingEndHeader() != -1) {
			res.addAll(getAqlCompletionProposals(getVariables(errorExpressionStatement),
					acceleoValidationResult.getValidationResult(errorExpressionStatement.getExpression()
							.getAst())));
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_EXPRESSION_END);
			final Expression ast = errorExpressionStatement.getExpression().getAst().getAst();
			if (ast instanceof VarRef) {
				res.addAll(getHeaderStarts(((VarRef)ast).getVariableName()));
			}
		}

		return res;
	}

	/**
	 * Gets the {@link List} of header starts {@link AcceleoCompletionProposal}.
	 * 
	 * @param variableName
	 *            the variable name
	 * @return the {@link List} of header starts {@link AcceleoCompletionProposal}
	 */
	private List<AcceleoCompletionProposal> getHeaderStarts(String variableName) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (AcceleoParser.COMMENT.startsWith(variableName)) {
			final String completion = AcceleoParser.COMMENT + AcceleoParser.SPACE;
			res.add(new AcceleoCompletionProposal(completion, completion, AcceleoPackage.Literals.COMMENT));
		}
		if (AcceleoParser.FILE.startsWith(variableName)) {
			final String completion = AcceleoParser.FILE + AcceleoParser.SPACE
					+ AcceleoParser.OPEN_PARENTHESIS;
			res.add(new AcceleoCompletionProposal(completion, completion,
					AcceleoPackage.Literals.FILE_STATEMENT));
		}
		if (AcceleoParser.PROTECTED.startsWith(variableName)) {
			final String completion = AcceleoParser.PROTECTED + AcceleoParser.SPACE
					+ AcceleoParser.OPEN_PARENTHESIS;
			res.add(new AcceleoCompletionProposal(completion, completion,
					AcceleoPackage.Literals.PROTECTED_AREA));
		}
		if (AcceleoParser.FOR.startsWith(variableName)) {
			final String completion = AcceleoParser.FOR + AcceleoParser.SPACE
					+ AcceleoParser.OPEN_PARENTHESIS;
			res.add(new AcceleoCompletionProposal(completion, completion,
					AcceleoPackage.Literals.FOR_STATEMENT));
		}
		if (AcceleoParser.IF.startsWith(variableName)) {
			final String completion = AcceleoParser.IF + AcceleoParser.SPACE + AcceleoParser.OPEN_PARENTHESIS;
			res.add(new AcceleoCompletionProposal(completion, completion,
					AcceleoPackage.Literals.FILE_STATEMENT));
		}
		if (AcceleoParser.LET.startsWith(variableName)) {
			final String completion = AcceleoParser.LET + AcceleoParser.SPACE;
			res.add(new AcceleoCompletionProposal(completion, completion,
					AcceleoPackage.Literals.LET_STATEMENT));
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
			res.addAll(getAqlCompletionProposals(getVariables(errorProtectedArea), acceleoValidationResult
					.getValidationResult(errorProtectedArea.getId().getAst())));
		} else if (errorProtectedArea.getMissingCloseParenthesis() != -1) {
			res.addAll(getAqlCompletionProposals(getVariables(errorProtectedArea), acceleoValidationResult
					.getValidationResult(errorProtectedArea.getId().getAst())));
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_PROTECTED_AREA_HEADER_CLOSE_PARENTHESIS_AND_END);
		} else if (errorProtectedArea.getMissingStartTagPrefixCloseParenthesis() != -1) {
			res.addAll(getAqlCompletionProposals(getVariables(errorProtectedArea), acceleoValidationResult
					.getValidationResult(errorProtectedArea.getId().getAst())));
			res.add(AcceleoSyntacticCompletionProposals.CLOSE_PARENTHESIS);
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_PROTECTED_AREA_HEADER_CLOSE_PARENTHESIS_AND_END);
		} else if (errorProtectedArea.getMissingEndTagPrefixCloseParenthesis() != -1) {
			res.addAll(getAqlCompletionProposals(getVariables(errorProtectedArea), acceleoValidationResult
					.getValidationResult(errorProtectedArea.getId().getAst())));
			res.add(AcceleoSyntacticCompletionProposals.CLOSE_PARENTHESIS);
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_PROTECTED_AREA_HEADER_CLOSE_PARENTHESIS_AND_END);
		} else if (errorProtectedArea.getMissingEndHeader() != -1) {
			if (errorProtectedArea.getEndTagPrefix() == null) {
				if (errorProtectedArea.getStartTagPrefix() == null) {
					res.add(AcceleoSyntacticCompletionProposals.STATEMENT_PROTECTED_AREA_START_TAG_PREFIX);
				}
				res.add(AcceleoSyntacticCompletionProposals.STATEMENT_PROTECTED_AREA_END_TAG_PREFIX);
			}
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_PROTECTED_AREA_HEADER_END);
		} else if (errorProtectedArea.getMissingEnd() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_PROTECTED_AREA_END);
			final int column = acceleoValidationResult.getAcceleoAstResult().getEndColumn(errorProtectedArea);
			res.addAll(getStatementProposals(column));
		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorForStatement(ErrorForStatement errorForStatement) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorForStatement.getMissingOpenParenthesis() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.OPEN_PARENTHESIS);
		} else if (errorForStatement.getMissingBinding() != -1) {
			res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(computedModuleName,
					AcceleoPackage.Literals.BINDING));
			res.add(AcceleoSyntacticCompletionProposals.FOR_STATEMENT_PIPE);
		} else if (errorForStatement.getMissingCloseParenthesis() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_FOR_HEADER_CLOSE_PARENTHESIS_AND_END);
			if (errorForStatement.getSeparator() == null) {
				res.add(AcceleoSyntacticCompletionProposals.STATEMENT_FOR_HEADER_SEPARATOR);
			}
			res.addAll(getAqlCompletionProposals(getVariables(errorForStatement), acceleoValidationResult
					.getValidationResult(errorForStatement.getBinding().getType())));
		} else if (errorForStatement.getSeparator() != null && errorForStatement.getSeparator().getAst()
				.getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			res.addAll(getAqlCompletionProposals(getVariables(errorForStatement), acceleoValidationResult
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
			final int column = acceleoValidationResult.getAcceleoAstResult().getEndColumn(errorForStatement);
			res.addAll(getStatementProposals(column));
		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorIfStatement(ErrorIfStatement errorIfStatement) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorIfStatement.getMissingSpace() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.SPACE);
		} else if (errorIfStatement.getMissingOpenParenthesis() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.OPEN_PARENTHESIS);
		} else if (errorIfStatement.getCondition().getAst()
				.getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			res.addAll(getAqlCompletionProposals(getVariables(errorIfStatement), acceleoValidationResult
					.getValidationResult(errorIfStatement.getCondition().getAst())));
		} else if (errorIfStatement.getMissingCloseParenthesis() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_IF_HEADER_CLOSE_PARENTHESIS_AND_END);
			res.addAll(getAqlCompletionProposals(getVariables(errorIfStatement), acceleoValidationResult
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
			final int column = acceleoValidationResult.getAcceleoAstResult().getEndColumn(errorIfStatement);
			res.addAll(getStatementProposals(column));
		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorLetStatement(ErrorLetStatement errorLetStatement) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();
		if (errorLetStatement.getMissingBindings() != -1) {
			res.addAll(this.acceleoCompletionProposalProvider.getProposalsFor(computedModuleName,
					AcceleoPackage.Literals.BINDING));
		} else if (errorLetStatement.getMissingEndHeader() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_LET_HEADER_END);

			List<Binding> bindings = errorLetStatement.getVariables();
			res.addAll(getAqlCompletionProposals(getVariables(errorLetStatement), acceleoValidationResult
					.getValidationResult(bindings.get(bindings.size() - 1).getType())));
		} else if (errorLetStatement.getMissingEnd() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_LET_END);
			final int column = acceleoValidationResult.getAcceleoAstResult().getEndColumn(errorLetStatement);
			res.addAll(getStatementProposals(column));
		}

		return res;
	}

	@Override
	public List<AcceleoCompletionProposal> caseErrorFileStatement(ErrorFileStatement errorFileStatement) {
		final List<AcceleoCompletionProposal> res = new ArrayList<AcceleoCompletionProposal>();

		if (errorFileStatement.getMissingOpenParenthesis() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.OPEN_PARENTHESIS);
		} else if (errorFileStatement.getUrl().getAst()
				.getAst() instanceof org.eclipse.acceleo.query.ast.Error) {
			res.addAll(getAqlCompletionProposals(getVariables(errorFileStatement), acceleoValidationResult
					.getValidationResult(errorFileStatement.getUrl().getAst())));
		} else if (errorFileStatement.getMissingComma() != -1) {
			res.addAll(getAqlCompletionProposals(getVariables(errorFileStatement), acceleoValidationResult
					.getValidationResult(errorFileStatement.getUrl().getAst())));
			res.add(AcceleoSyntacticCompletionProposals.COMMA_SPACE);
		} else if (errorFileStatement.getMissingOpenMode() != -1) {
			res.addAll(AcceleoSyntacticCompletionProposals.STATEMENT_FILE_OPEN_MODES);
		} else if (errorFileStatement.getMissingCloseParenthesis() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_FILE_HEADER_CLOSE_PARENTHESIS_AND_END);
		} else if (errorFileStatement.getMissingEndHeader() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_FILE_HEADER_END);
		} else if (errorFileStatement.getMissingEnd() != -1) {
			res.add(AcceleoSyntacticCompletionProposals.STATEMENT_FILE_END);
			final int column = acceleoValidationResult.getAcceleoAstResult().getEndColumn(errorFileStatement);
			res.addAll(getStatementProposals(column));
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
		final ICompletionResult completionResult = aqlCompletionEngine.getCompletion(expression, position
				- startPosition, variables);
		final ProposalComparator comparator = new ProposalComparator(queryEnvironment, completionResult
				.getPossibleReceiverTypes());

		completionResult.sort(comparator);
		final List<ICompletionProposal> aqlProposals = completionResult.getProposals(new BasicFilter(
				completionResult));
		final List<AcceleoCompletionProposal> aqlProposalsAsAcceleoProposals = aqlProposals.stream().map(
				cp -> transform(completionResult, startPosition, cp)).collect(Collectors.toList());
		return aqlProposalsAsAcceleoProposals;
	}

	private List<AcceleoCompletionProposal> getBodyCompletionProposals(int column) {
		final List<AcceleoCompletionProposal> res = new ArrayList<>();

		String newLinePrefix = newLine;
		for (int i = 0; i < column; i++) {
			newLinePrefix += AcceleoCodeTemplates.SPACE;
		}

		res.add(new AcceleoCodeTemplateCompletionProposal("New For", "Inserts the following For Statement:"
				+ AcceleoCompletionProposal.DESCRIPTION_NEWLINE
				+ AcceleoCompletionProposal.DESCRIPTION_CODE_OPEN + acceleoCodeTemplates.newForStatement()
				+ AcceleoCompletionProposal.DESCRIPTION_CODE_CLOSE, acceleoCodeTemplates.newForStatement()
						.replaceAll(newLine, newLinePrefix), AcceleoPackage.Literals.FOR_STATEMENT));
		res.add(new AcceleoCodeTemplateCompletionProposal("New If", "Inserts the following If Statement:"
				+ AcceleoCompletionProposal.DESCRIPTION_NEWLINE
				+ AcceleoCompletionProposal.DESCRIPTION_CODE_OPEN + acceleoCodeTemplates.newIfStatement()
				+ AcceleoCompletionProposal.DESCRIPTION_CODE_CLOSE, acceleoCodeTemplates.newIfStatement()
						.replaceAll(newLine, newLinePrefix), AcceleoPackage.Literals.IF_STATEMENT));
		res.add(new AcceleoCodeTemplateCompletionProposal("New Let", "Inserts the following Let Statement:"
				+ AcceleoCompletionProposal.DESCRIPTION_NEWLINE
				+ AcceleoCompletionProposal.DESCRIPTION_CODE_OPEN + acceleoCodeTemplates.newLetStatement()
				+ AcceleoCompletionProposal.DESCRIPTION_CODE_CLOSE, acceleoCodeTemplates.newLetStatement()
						.replaceAll(newLine, newLinePrefix), AcceleoPackage.Literals.LET_STATEMENT));
		res.add(new AcceleoCodeTemplateCompletionProposal("New File", "Inserts the following File Statement:"
				+ AcceleoCompletionProposal.DESCRIPTION_NEWLINE
				+ AcceleoCompletionProposal.DESCRIPTION_CODE_OPEN + acceleoCodeTemplates.newFileStatement()
				+ AcceleoCompletionProposal.DESCRIPTION_CODE_CLOSE, acceleoCodeTemplates.newFileStatement()
						.replaceAll(newLine, newLinePrefix), AcceleoPackage.Literals.FILE_STATEMENT));
		res.add(new AcceleoCodeTemplateCompletionProposal("New Protected Area",
				"Inserts the following Protected Area Statement:"
						+ AcceleoCompletionProposal.DESCRIPTION_NEWLINE
						+ AcceleoCompletionProposal.DESCRIPTION_CODE_OPEN + acceleoCodeTemplates
								.newProtectedAreaStatement()
						+ AcceleoCompletionProposal.DESCRIPTION_CODE_CLOSE, acceleoCodeTemplates
								.newProtectedAreaStatement().replaceAll(newLine, newLinePrefix),
				AcceleoPackage.Literals.PROTECTED_AREA));

		return res;
	}

	/**
	 * Transforms an AQL {@link ICompletionProposal} into a corresponding {@link AcceleoCompletionProposal}.
	 * 
	 * @param completionResult
	 *            the {@link ICompletionResult} containing the {@link ICompletionProposal}
	 * @param startPosition
	 *            the expression start position
	 * @param aqlCompletionProposal
	 *            the (non-{@code null}) {@link ICompletionProposal} to transform.
	 * @return the corresponding {@link AcceleoCompletionProposal}.
	 */
	private AcceleoCompletionProposal transform(ICompletionResult completionResult, int startPosition,
			ICompletionProposal aqlCompletionProposal) {
		final String description = StringServices.NEW_LINE_PATTERN.matcher(aqlCompletionProposal
				.getDescription()).replaceAll("<br>");
		final int replacementStartOffset = startPosition + completionResult.getReplacementOffset();
		final int replacementEndOffset = replacementStartOffset + completionResult.getReplacementLength();
		final IRange replacement = Range.getCorrespondingRange(replacementStartOffset, replacementEndOffset,
				moduleSourceFragment);
		return new AcceleoCompletionProposal(aqlCompletionProposal.getProposal(), description,
				aqlCompletionProposal.getProposal(), AcceleoPackage.Literals.EXPRESSION, replacement);
	}

}
