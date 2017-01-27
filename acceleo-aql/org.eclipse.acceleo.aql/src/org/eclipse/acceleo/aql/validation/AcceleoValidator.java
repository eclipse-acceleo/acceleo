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
package org.eclipse.acceleo.aql.validation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.eclipse.acceleo.Binding;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.ErrorBinding;
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
import org.eclipse.acceleo.Expression;
import org.eclipse.acceleo.ExpressionStatement;
import org.eclipse.acceleo.FileStatement;
import org.eclipse.acceleo.ForStatement;
import org.eclipse.acceleo.IfStatement;
import org.eclipse.acceleo.Import;
import org.eclipse.acceleo.LetStatement;
import org.eclipse.acceleo.Metamodel;
import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.ModuleElementDocumentation;
import org.eclipse.acceleo.ModuleReference;
import org.eclipse.acceleo.ProtectedArea;
import org.eclipse.acceleo.Query;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.TextStatement;
import org.eclipse.acceleo.Variable;
import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.query.parser.AstValidator;
import org.eclipse.acceleo.query.runtime.IValidationMessage;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.ValidationMessageLevel;
import org.eclipse.acceleo.query.runtime.impl.ValidationMessage;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.util.AcceleoSwitch;
import org.eclipse.emf.ecore.EPackage;

/**
 * Validates {@link Module}. A module can be parsed using {@link org.eclipse.acceleo.aql.parser.AcceleoParser
 * AcceleoParser}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
@SuppressWarnings("restriction")
public class AcceleoValidator extends AcceleoSwitch<List<IValidationMessage>> {

	/**
	 * The {@link IAcceleoEnvironment}.
	 */
	private final IAcceleoEnvironment environment;

	/**
	 * The {@link AstValidator}.
	 */
	private final AstValidator validator;

	/**
	 * The {@link Stack} of variables {@link IType}.
	 */
	private final Stack<Map<String, Set<IType>>> stack = new Stack<Map<String, Set<IType>>>();

	/**
	 * Tells if we should force a collection in the {@link Binding} validation.
	 */
	private boolean forceCollectionBinding;

	/**
	 * Constructor.
	 * 
	 * @param environment
	 *            the {@link IAcceleoEnvironment}
	 */
	public AcceleoValidator(IAcceleoEnvironment environment) {
		this.environment = environment;
		validator = new AstValidator(new ValidationServices(environment.getQueryEnvironment()));
	}

	/**
	 * Validates the given Module.
	 * 
	 * @param module
	 *            the {@link Module} to validate
	 * @return the {@link List} of {@link IValidationMessage}
	 */
	public List<IValidationMessage> validate(Module module) {
		stack.clear();
		stack.push(new HashMap<String, Set<IType>>());
		forceCollectionBinding = false;
		return doSwitch(module);
	}

	@Override
	public List<IValidationMessage> caseModule(Module module) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		final Set<EPackage> ePackages = new HashSet<EPackage>();
		for (Metamodel metamodel : module.getMetamodels()) {
			res.addAll(doSwitch(metamodel));
			if (metamodel.getReferencedPackage() != null) {
				if (!ePackages.add(metamodel.getReferencedPackage())) {
					res.add(new ValidationMessage(ValidationMessageLevel.WARNING, metamodel
							.getReferencedPackage().getNsURI()
							+ " already referenced", metamodel.getStartPosition(), metamodel.getEndPosition()));
				}
			}
		}

		if (module.getExtends() != null) {
			res.addAll(doSwitch(module.getExtends()));
		}

		final Set<String> imports = new HashSet<String>();
		for (Import imp : module.getImports()) {
			res.addAll(doSwitch(imp));
			if (imp.getModule().getQualifiedName() != null) {
				if (!imports.add(imp.getModule().getQualifiedName())) {
					res.add(new ValidationMessage(ValidationMessageLevel.WARNING, imp.getModule()
							.getQualifiedName()
							+ " already imported", imp.getStartPosition(), imp.getEndPosition()));
				}
			}
		}

		for (ModuleElement element : module.getModuleElements()) {
			// TODO check IService registration
			res.addAll(doSwitch(element));
		}

		return res;
	}

	@Override
	public List<IValidationMessage> caseModuleElementDocumentation(
			ModuleElementDocumentation moduleElementDocumentation) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	public List<IValidationMessage> caseComment(Comment comment) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	public List<IValidationMessage> caseErrorModule(ErrorModule errorModule) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		if (errorModule.getMissingOpenParenthesis() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.OPEN_PARENTHESIS), errorModule
							.getMissingOpenParenthesis(), errorModule.getMissingOpenParenthesis()));
		} else if (errorModule.getMissingEPackage() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR, "Missing metamodel", errorModule
					.getMissingEPackage(), errorModule.getMissingEPackage()));
		} else if (errorModule.getMissingCloseParenthesis() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.CLOSE_PARENTHESIS), errorModule
							.getMissingCloseParenthesis(), errorModule.getMissingCloseParenthesis()));
		} else if (errorModule.getMissingEndHeader() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.MODULE_HEADER_END), errorModule
							.getMissingEndHeader(), errorModule.getMissingEndHeader()));
		}

		return res;
	}

	@Override
	public List<IValidationMessage> caseMetamodel(Metamodel metamodel) {
		return Collections.emptyList();
	}

	@Override
	public List<IValidationMessage> caseErrorMetamodel(ErrorMetamodel errorMetamodel) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		if (errorMetamodel.getFragment() != null) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR, "Invalid metamodel "
					+ errorMetamodel.getFragment(), errorMetamodel.getStartPosition(), errorMetamodel
					.getEndPosition()));
		} else if (errorMetamodel.getMissingEndQuote() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.QUOTE), errorMetamodel.getMissingEndQuote(),
					errorMetamodel.getMissingEndQuote()));
		}

		return res;
	}

	@Override
	public List<IValidationMessage> caseImport(Import imp) {
		return doSwitch(imp.getModule());
	}

	@Override
	public List<IValidationMessage> caseErrorImport(ErrorImport errorImport) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		res.addAll(doSwitch(errorImport.getModule()));

		if (errorImport.getMissingEnd() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.IMPORT_END), errorImport.getMissingEnd(),
					errorImport.getMissingEnd()));
		}

		return res;
	}

	@Override
	public List<IValidationMessage> caseModuleReference(ModuleReference moduleReference) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		if (!environment.hasModule(moduleReference.getQualifiedName())) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR, "Could not find module "
					+ moduleReference.getQualifiedName(), moduleReference.getStartPosition(), moduleReference
					.getEndPosition()));
		}

		return res;
	}

	@Override
	public List<IValidationMessage> caseBlock(Block block) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		for (Statement statement : block.getStatements()) {
			res.addAll(doSwitch(statement));
		}

		return res;
	}

	@Override
	public List<IValidationMessage> caseTextStatement(TextStatement object) {
		return Collections.emptyList();
	}

	@Override
	public List<IValidationMessage> caseTemplate(Template template) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		stack.push(new HashMap<String, Set<IType>>(stack.peek()));
		try {
			final Set<String> parameterNames = new HashSet<String>();
			for (Variable parameter : template.getParameters()) {
				res.addAll(doSwitch(parameter));
				if (parameter.getName() != null) {
					if (!parameterNames.add(parameter.getName())) {
						res.add(new ValidationMessage(ValidationMessageLevel.ERROR, parameter.getName()
								+ " duplicated parameter", parameter.getStartPosition(), parameter
								.getEndPosition()));

					}
				}
			}
			if (template.getGuard() != null) {
				res.addAll(doSwitch(template.getGuard()));
			}
			if (template.getPost() != null) {
				stack.push(new HashMap<String, Set<IType>>(stack.peek()));
				Set<IType> possibleTypes = new LinkedHashSet<IType>();
				possibleTypes.add(new ClassType(environment.getQueryEnvironment(), String.class));
				stack.peek().put("self", possibleTypes);
				try {
					res.addAll(doSwitch(template.getPost()));
				} finally {
					stack.pop();
				}
			}
			res.addAll(doSwitch(template.getBody()));
		} finally {
			stack.pop();
		}

		return res;
	}

	@Override
	public List<IValidationMessage> caseErrorTemplate(ErrorTemplate errorTemplate) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		if (errorTemplate.getMissingVisibility() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR, "Missing visibility", errorTemplate
					.getMissingVisibility(), errorTemplate.getMissingVisibility()));
		} else if (errorTemplate.getMissingName() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR, "Missing name", errorTemplate
					.getMissingName(), errorTemplate.getMissingName()));
		} else if (errorTemplate.getMissingOpenParenthesis() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.OPEN_PARENTHESIS), errorTemplate
							.getMissingOpenParenthesis(), errorTemplate.getMissingOpenParenthesis()));
		} else if (errorTemplate.getMissingCloseParenthesis() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.CLOSE_PARENTHESIS), errorTemplate
							.getMissingCloseParenthesis(), errorTemplate.getMissingCloseParenthesis()));
		} else if (errorTemplate.getMissingGuardOpenParenthesis() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.OPEN_PARENTHESIS), errorTemplate
							.getMissingGuardOpenParenthesis(), errorTemplate.getMissingGuardOpenParenthesis()));
		} else if (errorTemplate.getMissingGuardCloseParenthesis() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.CLOSE_PARENTHESIS), errorTemplate
							.getMissingGuardCloseParenthesis(), errorTemplate
							.getMissingGuardCloseParenthesis()));
		} else if (errorTemplate.getMissingPostCloseParenthesis() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.CLOSE_PARENTHESIS), errorTemplate
							.getMissingPostCloseParenthesis(), errorTemplate.getMissingPostCloseParenthesis()));
		} else if (errorTemplate.getMissingEndHeader() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.TEMPLATE_HEADER_END), errorTemplate
							.getMissingEndHeader(), errorTemplate.getMissingEndHeader()));
		} else if (errorTemplate.getMissingEnd() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.TEMPLATE_END), errorTemplate.getMissingEnd(),
					errorTemplate.getMissingEnd()));
		}

		return res;
	}

	@Override
	public List<IValidationMessage> caseQuery(Query query) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		stack.push(new HashMap<String, Set<IType>>(stack.peek()));
		try {
			final Set<String> parameterNames = new HashSet<String>();
			for (Variable parameter : query.getParameters()) {
				res.addAll(doSwitch(parameter));
				if (parameter.getName() != null) {
					if (!parameterNames.add(parameter.getName())) {
						res.add(new ValidationMessage(ValidationMessageLevel.ERROR, parameter.getName()
								+ " duplicated parameter", parameter.getStartPosition(), parameter
								.getEndPosition()));

					}
				}
			}
			res.addAll(doSwitch(query.getBody()));
		} finally {
			stack.pop();
		}

		return res;
	}

	@Override
	public List<IValidationMessage> caseErrorQuery(ErrorQuery errorQuery) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		if (errorQuery.getMissingVisibility() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR, "Missing visibility", errorQuery
					.getMissingVisibility(), errorQuery.getMissingVisibility()));
		} else if (errorQuery.getMissingName() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR, "Missing name", errorQuery
					.getMissingName(), errorQuery.getMissingName()));
		} else if (errorQuery.getMissingOpenParenthesis() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.OPEN_PARENTHESIS), errorQuery
							.getMissingOpenParenthesis(), errorQuery.getMissingOpenParenthesis()));
		} else if (errorQuery.getMissingCloseParenthesis() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.CLOSE_PARENTHESIS), errorQuery
							.getMissingCloseParenthesis(), errorQuery.getMissingCloseParenthesis()));
		} else if (errorQuery.getMissingColon() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.COLON), errorQuery.getMissingColon(), errorQuery
							.getMissingColon()));
		} else if (errorQuery.getMissingType() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR, "Missing or invalid type", errorQuery
					.getMissingType(), errorQuery.getMissingType()));
		} else if (errorQuery.getMissingEqual() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.EQUAL), errorQuery.getMissingEqual(), errorQuery
							.getMissingEqual()));
		} else if (errorQuery.getMissingEnd() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.QUERY_END), errorQuery.getMissingEnd(), errorQuery
							.getMissingEnd()));
		}

		return res;
	}

	@Override
	public List<IValidationMessage> caseVariable(Variable variable) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		if (stack.peek().containsKey(variable.getName())) {
			res.add(new ValidationMessage(ValidationMessageLevel.WARNING, "Variable " + variable.getName()
					+ " already exists.", variable.getStartPosition(), variable.getEndPosition()));
		}
		final Set<IType> types = new LinkedHashSet<IType>();
		types.add(new EClassifierType(environment.getQueryEnvironment(), variable.getType()));
		stack.peek().put(variable.getName(), types);

		return res;
	}

	@Override
	public List<IValidationMessage> caseErrorVariable(ErrorVariable errorVariable) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		if (errorVariable.getMissingName() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR, "Missing name", errorVariable
					.getMissingName(), errorVariable.getMissingName()));
		} else if (errorVariable.getMissingColon() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.COLON), errorVariable.getMissingColon(),
					errorVariable.getMissingColon()));
		} else if (errorVariable.getMissingType() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR, "Missing or invalid type",
					errorVariable.getMissingType(), errorVariable.getMissingType()));
		}

		return res;
	}

	@Override
	public List<IValidationMessage> caseBinding(Binding binding) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		if (stack.peek().containsKey(binding.getName())) {
			res.add(new ValidationMessage(ValidationMessageLevel.WARNING, "Variable " + binding.getName()
					+ " already exists.", binding.getStartPosition(), binding.getEndPosition()));
		}
		final IValidationResult validationResult = validateExpression(binding.getInitExpression());
		res.addAll(shiftMessages(validationResult.getMessages(), binding.getInitExpression()
				.getStartPosition()));

		final Set<IType> possibleTypes = validationResult.getPossibleTypes(validationResult.getAstResult()
				.getAst());
		if (binding.getType() != null) {
			final EClassifierType iType = new EClassifierType(environment.getQueryEnvironment(), binding
					.getType());
			for (IType possibleType : possibleTypes) {
				if (!iType.isAssignableFrom(possibleType)) {
					if (forceCollectionBinding) {
						if (possibleType instanceof ICollectionType) {
							if (!iType.isAssignableFrom(((ICollectionType)possibleType).getCollectionType())) {
								res.add(new ValidationMessage(ValidationMessageLevel.WARNING, iType
										+ " is incompatible with " + possibleType,
										binding.getStartPosition(), binding.getEndPosition()));
							}
						} else {
							res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
									"Must be a Collection not " + possibleType, binding.getStartPosition(),
									binding.getEndPosition()));
						}
					} else {
						res.add(new ValidationMessage(ValidationMessageLevel.WARNING, iType
								+ " is incompatible with " + possibleType, binding.getStartPosition(),
								binding.getEndPosition()));
					}
				}
			}
		} else if (forceCollectionBinding) {
			for (IType possibleType : possibleTypes) {
				if (!(possibleType instanceof ICollectionType)) {
					res.add(new ValidationMessage(ValidationMessageLevel.ERROR, "Must be a Collection not "
							+ possibleType, binding.getStartPosition(), binding.getEndPosition()));
				}
			}
		}

		final Set<IType> variableTypes = new LinkedHashSet<IType>();
		if (forceCollectionBinding) {
			for (IType possibleType : possibleTypes) {
				if (possibleType instanceof ICollectionType) {
					variableTypes.add(((ICollectionType)possibleType).getCollectionType());
				}
			}
		} else {
			variableTypes.addAll(possibleTypes);
		}
		stack.peek().put(binding.getName(), variableTypes);

		return res;
	}

	@Override
	public List<IValidationMessage> caseErrorBinding(ErrorBinding errorBinding) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		if (errorBinding.getMissingName() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR, "Missing name", errorBinding
					.getMissingName(), errorBinding.getMissingName()));
		} else if (errorBinding.getMissingColon() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.COLON), errorBinding.getMissingColon(), errorBinding
							.getMissingColon()));
		} else if (errorBinding.getMissingType() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR, "Missing type literal", errorBinding
					.getMissingType(), errorBinding.getMissingType()));
		} else if (errorBinding.getMissingAffectationSymbolePosition() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR, getMissingTokenMessage(errorBinding
					.getMissingAffectationSymbole()), errorBinding.getMissingAffectationSymbolePosition(),
					errorBinding.getMissingAffectationSymbolePosition()));
		}

		return res;
	}

	@Override
	public List<IValidationMessage> caseExpressionStatement(ExpressionStatement expressionStatement) {
		return doSwitch(expressionStatement.getExpression());
	}

	@Override
	public List<IValidationMessage> caseErrorExpressionStatement(
			ErrorExpressionStatement errorExpressionStatement) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		res.addAll(doSwitch(errorExpressionStatement.getExpression()));
		if (errorExpressionStatement.getMissingEndHeader() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.EXPRESSION_STATEMENT_END), errorExpressionStatement
							.getMissingEndHeader(), errorExpressionStatement.getMissingEndHeader()));
		}

		return res;
	}

	@Override
	public List<IValidationMessage> caseProtectedArea(ProtectedArea protectedArea) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		res.addAll(doSwitch(protectedArea.getId()));
		res.addAll(doSwitch(protectedArea.getBody()));

		return res;
	}

	@Override
	public List<IValidationMessage> caseExpression(Expression expression) {
		final IValidationResult validationResult = validateExpression(expression);

		return shiftMessages(validationResult.getMessages(), expression.getStartPosition());
	}

	@Override
	public List<IValidationMessage> caseErrorProtectedArea(ErrorProtectedArea errorProtectedArea) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		if (errorProtectedArea.getMissingOpenParenthesis() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.OPEN_PARENTHESIS), errorProtectedArea
							.getMissingOpenParenthesis(), errorProtectedArea.getMissingOpenParenthesis()));
		} else if (errorProtectedArea.getMissingCloseParenthesis() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.CLOSE_PARENTHESIS), errorProtectedArea
							.getMissingCloseParenthesis(), errorProtectedArea.getMissingCloseParenthesis()));
		} else if (errorProtectedArea.getMissingEndHeader() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.PROTECTED_AREA_HEADER_END), errorProtectedArea
							.getMissingEndHeader(), errorProtectedArea.getMissingEndHeader()));
		} else if (errorProtectedArea.getMissingEnd() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.PROTECTED_AREA_END), errorProtectedArea
							.getMissingEnd(), errorProtectedArea.getMissingEnd()));
		}

		return res;
	}

	@Override
	public List<IValidationMessage> caseForStatement(ForStatement forStatement) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		stack.push(new HashMap<String, Set<IType>>(stack.peek()));
		try {
			forceCollectionBinding = true;
			try {
				res.addAll(doSwitch(forStatement.getBinding()));
			} finally {
				forceCollectionBinding = false;
			}
			res.addAll(doSwitch(forStatement.getBody()));
		} finally {
			stack.pop();
		}

		return res;
	}

	@Override
	public List<IValidationMessage> caseErrorForStatement(ErrorForStatement errorForStatement) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		if (errorForStatement.getMissingOpenParenthesis() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.OPEN_PARENTHESIS), errorForStatement
							.getMissingOpenParenthesis(), errorForStatement.getMissingOpenParenthesis()));
		} else if (errorForStatement.getMissingCloseParenthesis() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.CLOSE_PARENTHESIS), errorForStatement
							.getMissingCloseParenthesis(), errorForStatement.getMissingCloseParenthesis()));
		} else if (errorForStatement.getMissingEndHeader() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.FOR_HEADER_END), errorForStatement
							.getMissingEndHeader(), errorForStatement.getMissingEndHeader()));
		} else if (errorForStatement.getMissingEnd() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.FOR_END), errorForStatement.getMissingEnd(),
					errorForStatement.getMissingEnd()));
		}

		return res;
	}

	@Override
	public List<IValidationMessage> caseIfStatement(IfStatement ifStatement) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		final IValidationResult validationResult = validateExpression(ifStatement.getCondition());
		final Set<IType> conditionTypes = validationResult.getPossibleTypes(ifStatement.getCondition()
				.getAst().getAst());
		res.addAll(shiftMessages(validationResult.getMessages(), ifStatement.getCondition()
				.getStartPosition()));

		if (!conditionTypes.isEmpty()) {
			boolean onlyBoolean = true;
			boolean onlyNotBoolean = true;
			final IType booleanObjectType = new ClassType(environment.getQueryEnvironment(), Boolean.class);
			final IType booleanType = new ClassType(environment.getQueryEnvironment(), boolean.class);
			for (IType type : conditionTypes) {
				final boolean assignableFrom = booleanObjectType.isAssignableFrom(type)
						|| booleanType.isAssignableFrom(type);
				onlyBoolean = onlyBoolean && assignableFrom;
				onlyNotBoolean = onlyNotBoolean && !assignableFrom;
				if (!onlyBoolean && !onlyNotBoolean) {
					break;
				}
			}
			if (onlyBoolean) {
				// nothing to do here
			} else if (onlyNotBoolean) {
				final String message = String.format("The predicate never evaluates to a boolean type (%s).",
						conditionTypes);
				res.add(new ValidationMessage(ValidationMessageLevel.ERROR, message, ifStatement
						.getCondition().getStartPosition(), ifStatement.getCondition().getEndPosition()));
			} else {
				final String message = String.format(
						"The predicate may evaluate to a value that is not a boolean type (%s).",
						conditionTypes);
				res.add(new ValidationMessage(ValidationMessageLevel.WARNING, message, ifStatement
						.getCondition().getStartPosition(), ifStatement.getCondition().getEndPosition()));
			}
		} else {
			final String message = String.format("The predicate never evaluates to a boolean type (%s).",
					conditionTypes);
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR, message, ifStatement.getCondition()
					.getStartPosition(), ifStatement.getCondition().getEndPosition()));
		}

		final Map<String, Set<IType>> thenTypes = new HashMap<String, Set<IType>>(stack.peek());
		thenTypes.putAll(validationResult.getInferredVariableTypes(ifStatement.getCondition().getAst()
				.getAst(), Boolean.TRUE));
		stack.push(thenTypes);
		try {
			doSwitch(ifStatement.getThen());
		} finally {
			stack.pop();
		}
		if (ifStatement.getElse() != null) {
			final Map<String, Set<IType>> elseTypes = new HashMap<String, Set<IType>>(stack.peek());
			elseTypes.putAll(validationResult.getInferredVariableTypes(ifStatement.getCondition().getAst()
					.getAst(), Boolean.FALSE));
			stack.push(elseTypes);
			try {
				doSwitch(ifStatement.getElse());
			} finally {
				stack.pop();
			}
		}

		return res;
	}

	@Override
	public List<IValidationMessage> caseErrorIfStatement(ErrorIfStatement errorIfStatement) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		if (errorIfStatement.getMissingOpenParenthesis() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.OPEN_PARENTHESIS), errorIfStatement
							.getMissingOpenParenthesis(), errorIfStatement.getMissingOpenParenthesis()));
		} else if (errorIfStatement.getMissingCloseParenthesis() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.CLOSE_PARENTHESIS), errorIfStatement
							.getMissingCloseParenthesis(), errorIfStatement.getMissingCloseParenthesis()));
		} else if (errorIfStatement.getMissingEndHeader() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.IF_HEADER_END), errorIfStatement
							.getMissingEndHeader(), errorIfStatement.getMissingEndHeader()));
		} else if (errorIfStatement.getMissingEnd() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.IF_END), errorIfStatement.getMissingEnd(),
					errorIfStatement.getMissingEnd()));
		}

		return res;
	}

	@Override
	public List<IValidationMessage> caseLetStatement(LetStatement letStatement) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		stack.push(new HashMap<String, Set<IType>>(stack.peek()));
		try {
			for (Binding binding : letStatement.getVariables()) {
				res.addAll(doSwitch(binding));
			}
			res.addAll(doSwitch(letStatement.getBody()));
		} finally {
			stack.pop();
		}

		return res;
	}

	@Override
	public List<IValidationMessage> caseErrorLetStatement(ErrorLetStatement errorLetStatement) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		if (errorLetStatement.getMissingEndHeader() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.LET_HEADER_END), errorLetStatement
							.getMissingEndHeader(), errorLetStatement.getMissingEndHeader()));
		} else if (errorLetStatement.getMissingEnd() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.LET_END), errorLetStatement.getMissingEnd(),
					errorLetStatement.getMissingEnd()));
		}

		return res;
	}

	@Override
	public List<IValidationMessage> caseFileStatement(FileStatement fileStatement) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		res.addAll(doSwitch(fileStatement.getUrl()));
		if (fileStatement.getCharset() != null) {
			res.addAll(doSwitch(fileStatement.getCharset()));
		}
		res.addAll(doSwitch(fileStatement.getBody()));

		return res;
	}

	@Override
	public List<IValidationMessage> caseErrorFileStatement(ErrorFileStatement errorFileStatement) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		if (errorFileStatement.getMissingOpenParenthesis() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.OPEN_PARENTHESIS), errorFileStatement
							.getMissingOpenParenthesis(), errorFileStatement.getMissingOpenParenthesis()));
		} else if (errorFileStatement.getMissingComma() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.COMMA), errorFileStatement.getMissingComma(),
					errorFileStatement.getMissingComma()));
		} else if (errorFileStatement.getMissingOpenMode() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					"Missing or invalid file open mode: overwrite, append, create", errorFileStatement
							.getMissingOpenMode(), errorFileStatement.getMissingOpenMode()));
		} else if (errorFileStatement.getMissingCloseParenthesis() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.CLOSE_PARENTHESIS), errorFileStatement
							.getMissingCloseParenthesis(), errorFileStatement.getMissingCloseParenthesis()));
		} else if (errorFileStatement.getMissingEndHeader() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.FILE_HEADER_END), errorFileStatement
							.getMissingEndHeader(), errorFileStatement.getMissingEndHeader()));
		} else if (errorFileStatement.getMissingEnd() != -1) {
			res.add(new ValidationMessage(ValidationMessageLevel.ERROR,
					getMissingTokenMessage(AcceleoParser.FILE_END), errorFileStatement.getMissingEnd(),
					errorFileStatement.getMissingEnd()));
		}

		return res;
	}

	/**
	 * Shifts the given {@link List} of {@link IValidationMessage} to the given offset.
	 * 
	 * @param messages
	 *            the {@link List} of {@link IValidationMessage}
	 * @param offset
	 *            the offset
	 * @return the shifted {@link List} of {@link IValidationMessage}
	 */
	private List<IValidationMessage> shiftMessages(List<IValidationMessage> messages, int offset) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>(messages.size());

		for (IValidationMessage message : messages) {
			final int newStartPosition = message.getStartPosition() + offset;
			final int newEndPosition = message.getEndPosition() + offset;
			res.add(new ValidationMessage(message.getLevel(), message.getMessage(), newStartPosition,
					newEndPosition));
		}

		return res;
	}

	/**
	 * Validates the given {@link Expression}.
	 * 
	 * @param expression
	 *            the {@link Expression}
	 * @return the {@link IValidationResult}
	 */
	private IValidationResult validateExpression(Expression expression) {
		return validator.validate(stack.peek(), expression.getAst());
	}

	/**
	 * Gets the message when the given token is missing.
	 * 
	 * @param token
	 *            the token
	 * @return the message when the given token is missing
	 */
	protected String getMissingTokenMessage(String token) {
		return "Missing \"" + token + "\"";
	}

}
