/*******************************************************************************
 * Copyright (c) 2017, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.validation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.AcceleoASTNode;
import org.eclipse.acceleo.Binding;
import org.eclipse.acceleo.Block;
import org.eclipse.acceleo.Comment;
import org.eclipse.acceleo.ErrorBinding;
import org.eclipse.acceleo.ErrorBlockComment;
import org.eclipse.acceleo.ErrorComment;
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
import org.eclipse.acceleo.aql.AcceleoUtil;
import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.parser.AstValidator;
import org.eclipse.acceleo.query.runtime.IValidationMessage;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.ValidationMessageLevel;
import org.eclipse.acceleo.query.runtime.impl.ValidationMessage;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameQueryEnvironment;
import org.eclipse.acceleo.query.validation.type.ClassType;
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
public class AcceleoValidator extends AcceleoSwitch<Object> {

	/**
	 * The is incompatible with message.
	 */
	private static final String IS_INCOMPATIBLE_WITH = " is incompatible with ";

	/**
	 * Missing name message.
	 */
	private static final String MISSING_NAME = "Missing name";

	/**
	 * A return value to prevent switch to get to extended EClasses.
	 */
	private static final Object RETURN_VALUE = new Object();

	/**
	 * The {@link IQualifiedNameQueryEnvironment}.
	 */
	private final IQualifiedNameQueryEnvironment queryEnvironment;

	/**
	 * The {@link AstValidator}.
	 */
	private final AstValidator validator;

	/**
	 * Local variable types usable during validation.
	 */
	private Deque<Map<String, Set<IType>>> variableTypesStack = new ArrayDeque<Map<String, Set<IType>>>();

	/**
	 * Tells if we should force a collection in the {@link Binding} validation.
	 */
	private boolean forceCollectionBinding;

	/**
	 * The {@link IAcceleoValidationResult}.
	 */
	private AcceleoValidationResult result;

	/**
	 * {@link String} {@link IType}.
	 */
	private final IType stringType;

	/**
	 * {@link Boolean} {@link IType}.
	 */
	private final IType booleanType;

	/**
	 * {@link Boolean} {@link IType}.
	 */
	private final IType booleanObjectType;

	/**
	 * The mapping from a {@link VarRef#getVariableName() variable name} to its {@link List} of unresolved
	 * {@link VarRef}.
	 */
	private final Map<String, List<VarRef>> unresolvedVarRefsMapping = new HashMap<>();

	/**
	 * Constructor.
	 * 
	 * @param queryEnvironment
	 *            the {@link IQualifiedNameQueryEnvironment}
	 */
	public AcceleoValidator(IQualifiedNameQueryEnvironment queryEnvironment) {
		this.queryEnvironment = queryEnvironment;
		this.stringType = new ClassType(queryEnvironment, String.class);
		this.booleanType = new ClassType(queryEnvironment, boolean.class);
		this.booleanObjectType = new ClassType(queryEnvironment, Boolean.class);
		validator = new AstValidator(new ValidationServices(queryEnvironment));
	}

	/**
	 * Pushes the given variable types into the stack.
	 * 
	 * @param variableTypes
	 *            the variable types to push
	 */
	protected void pushVariableTypes(Map<String, Set<IType>> variableTypes) {
		variableTypesStack.addLast(variableTypes);
	}

	/**
	 * Peeks the last {@link #pushVariableTypes(Map) pushed} variable types from the stack.
	 * 
	 * @return the last {@link #pushVariableTypes(Map) pushed} variable types from the stack
	 */
	protected Map<String, Set<IType>> peekVariableTypes() {
		return variableTypesStack.peekLast();
	}

	/**
	 * Pops the last {@link #pushVariableTypes(Map) pushed} variable types from the stack.
	 * 
	 * @return the last {@link #pushVariableTypes(Map) pushed} variable types from the stack
	 */
	protected Map<String, Set<IType>> popVariableTypes() {
		return variableTypesStack.removeLast();
	}

	/**
	 * Adds an unresolved {@link VarRef}.
	 * 
	 * @param varRef
	 *            the {@link VarRef}
	 */
	private void addUnresolvedVarRef(IValidationResult validationResult) {
		for (VarRef unresolved : validationResult.getUnresolvedVarRef()) {
			unresolvedVarRefsMapping.computeIfAbsent(unresolved.getVariableName(), n -> new ArrayList<>())
					.add(unresolved);
		}
	}

	/**
	 * Resolves unresolved {@link VarRef} for the given {@link Variable}.
	 * 
	 * @param variable
	 *            the {@link Variable}
	 */
	private void resolveVarRefVariable(Variable variable) {
		final List<VarRef> unresolved = unresolvedVarRefsMapping.remove(variable.getName());
		if (unresolved != null) {
			for (VarRef varRef : unresolved) {
				result.putBindingResolvedVarRef(variable, varRef);
			}
		}
	}

	/**
	 * Validates the given {@link Module}.
	 * 
	 * @param astResult
	 *            the {@link AcceleoAstResult} to validate
	 * @param moduleQualifiedName
	 *            the module qualified name
	 * @return the {@link IAcceleoValidationResult}
	 */
	public IAcceleoValidationResult validate(AcceleoAstResult astResult, String moduleQualifiedName) {
		variableTypesStack = new ArrayDeque<Map<String, Set<IType>>>();
		pushVariableTypes(new HashMap<String, Set<IType>>());
		forceCollectionBinding = false;
		result = new AcceleoValidationResult(astResult);

		queryEnvironment.getLookupEngine().pushImportsContext(moduleQualifiedName, moduleQualifiedName);
		try {
			doSwitch(astResult.getModule());
		} finally {
			queryEnvironment.getLookupEngine().popContext(moduleQualifiedName);
		}

		return result;
	}

	/**
	 * Adds a {@link IValidationMessage} to the given {@link AcceleoASTNode}.
	 * 
	 * @param node
	 *            the {@link AcceleoASTNode}
	 * @param level
	 *            the {@link ValidationMessageLevel}
	 * @param messageString
	 *            the message
	 * @param startPosition
	 *            the start position
	 * @param endPosition
	 *            the end position
	 */
	protected void addMessage(AcceleoASTNode node, ValidationMessageLevel level, String messageString,
			int startPosition, int endPosition) {
		final IValidationMessage message = new ValidationMessage(level, messageString, startPosition,
				endPosition);
		result.addMessage(node, message);
	}

	@Override
	public Object caseModule(Module module) {
		final Set<EPackage> ePackages = new HashSet<EPackage>();
		for (Metamodel metamodel : module.getMetamodels()) {
			doSwitch(metamodel);
			if (metamodel.getReferencedPackage() != null) {
				if (!ePackages.add(metamodel.getReferencedPackage())) {
					final AcceleoAstResult acceleoAstResult = result.getAcceleoAstResult();
					addMessage(module, ValidationMessageLevel.WARNING, metamodel.getReferencedPackage()
							.getNsURI() + " already referenced", acceleoAstResult.getStartPosition(metamodel),
							acceleoAstResult.getEndPosition(metamodel));
				}
			}
		}

		if (module.getExtends() != null) {
			doSwitch(module.getExtends());
		}

		final Set<String> imports = new HashSet<String>();
		for (Import imp : module.getImports()) {
			doSwitch(imp);
			if (imp.getModule().getQualifiedName() != null) {
				final AcceleoAstResult acceleoAstResult = result.getAcceleoAstResult();
				if (!imports.add(imp.getModule().getQualifiedName())) {
					addMessage(module, ValidationMessageLevel.WARNING, imp.getModule().getQualifiedName()
							+ " already imported", acceleoAstResult.getStartPosition(imp), acceleoAstResult
									.getEndPosition(imp));
				}
			}
		}

		for (ModuleElement element : module.getModuleElements()) {
			// TODO check IService registration
			doSwitch(element);
		}

		return RETURN_VALUE;
	}

	@Override
	public Object caseModuleElementDocumentation(ModuleElementDocumentation moduleElementDocumentation) {
		// TODO Auto-generated method stub
		return RETURN_VALUE;
	}

	@Override
	public Object caseComment(Comment comment) {
		// TODO Auto-generated method stub
		return RETURN_VALUE;
	}

	@Override
	public Object caseErrorComment(ErrorComment errorComment) {
		if (errorComment.getMissingEndHeader() != -1) {
			final String message;
			if (errorComment instanceof ErrorBlockComment) {
				message = getMissingTokenMessage(AcceleoParser.BLOCK_COMMENT_END);
			} else {
				message = getMissingTokenMessage(AcceleoParser.COMMENT_END);
			}
			addMessage(errorComment, ValidationMessageLevel.ERROR, message, errorComment
					.getMissingEndHeader(), errorComment.getMissingEndHeader());
		}

		return null;
	}

	@Override
	public Object caseErrorModule(ErrorModule errorModule) {
		if (errorModule.getMissingOpenParenthesis() != -1) {
			addMessage(errorModule, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.OPEN_PARENTHESIS), errorModule.getMissingOpenParenthesis(), errorModule
							.getMissingOpenParenthesis());
		} else if (errorModule.getMissingEPackage() != -1) {
			addMessage(errorModule, ValidationMessageLevel.ERROR, "Missing metamodel", errorModule
					.getMissingEPackage(), errorModule.getMissingEPackage());
		} else if (errorModule.getMissingCloseParenthesis() != -1) {
			addMessage(errorModule, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.CLOSE_PARENTHESIS), errorModule.getMissingCloseParenthesis(), errorModule
							.getMissingCloseParenthesis());
		} else if (errorModule.getMissingEndHeader() != -1) {
			addMessage(errorModule, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.MODULE_HEADER_END), errorModule.getMissingEndHeader(), errorModule
							.getMissingEndHeader());
		}

		return null;
	}

	@Override
	public Object caseMetamodel(Metamodel metamodel) {
		return RETURN_VALUE;
	}

	@Override
	public Object caseErrorMetamodel(ErrorMetamodel errorMetamodel) {
		if (errorMetamodel.getFragment() != null) {
			final AcceleoAstResult acceleoAstResult = result.getAcceleoAstResult();
			addMessage(errorMetamodel, ValidationMessageLevel.ERROR, "Invalid metamodel " + errorMetamodel
					.getFragment(), acceleoAstResult.getStartPosition(errorMetamodel), acceleoAstResult
							.getEndPosition(errorMetamodel));
		} else if (errorMetamodel.getMissingEndQuote() != -1) {
			addMessage(errorMetamodel, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.QUOTE), errorMetamodel.getMissingEndQuote(), errorMetamodel
							.getMissingEndQuote());
		}

		return null;
	}

	@Override
	public Object caseImport(Import imp) {
		doSwitch(imp.getModule());
		return RETURN_VALUE;
	}

	@Override
	public Object caseErrorImport(ErrorImport errorImport) {
		doSwitch(errorImport.getModule());

		if (errorImport.getMissingEnd() != -1) {
			addMessage(errorImport, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.IMPORT_END), errorImport.getMissingEnd(), errorImport.getMissingEnd());
		}

		return null;
	}

	@Override
	public Object caseModuleReference(ModuleReference moduleReference) {
		if (moduleReference.getQualifiedName() != null && queryEnvironment.getLookupEngine().getResolver()
				.getURI(moduleReference.getQualifiedName()) == null) {
			final AcceleoAstResult acceleoAstResult = result.getAcceleoAstResult();
			addMessage(moduleReference, ValidationMessageLevel.ERROR, "Could not resolve " + moduleReference
					.getQualifiedName(), acceleoAstResult.getStartPosition(moduleReference), acceleoAstResult
							.getEndPosition(moduleReference));
		}

		return RETURN_VALUE;
	}

	@Override
	public Object caseBlock(Block block) {
		for (Statement statement : block.getStatements()) {
			doSwitch(statement);
		}

		return RETURN_VALUE;
	}

	@Override
	public Object caseTextStatement(TextStatement object) {
		return RETURN_VALUE;
	}

	@Override
	public Object caseTemplate(Template template) {
		pushVariableTypes(new HashMap<String, Set<IType>>(peekVariableTypes()));
		try {
			final Set<String> parameterNames = new HashSet<String>();
			for (Variable parameter : template.getParameters()) {
				doSwitch(parameter);
				if (parameter.getName() != null) {
					if (!parameterNames.add(parameter.getName())) {
						final AcceleoAstResult acceleoAstResult = result.getAcceleoAstResult();
						addMessage(template, ValidationMessageLevel.ERROR, parameter.getName()
								+ " duplicated parameter", acceleoAstResult.getStartPosition(parameter),
								acceleoAstResult.getEndPosition(parameter));
					}
				}
			}
			if (template.getGuard() != null) {
				doSwitch(template.getGuard());
			}
			if (template.getPost() != null) {
				pushVariableTypes(new HashMap<String, Set<IType>>(peekVariableTypes()));
				Set<IType> possibleTypes = new LinkedHashSet<IType>();
				possibleTypes.add(new ClassType(queryEnvironment, String.class));
				peekVariableTypes().put(AcceleoUtil.getTemplateImplicitVariableName(), possibleTypes);
				try {
					doSwitch(template.getPost());
				} finally {
					popVariableTypes();
				}
			}
			doSwitch(template.getBody());
		} finally {
			for (Variable parameter : template.getParameters()) {
				resolveVarRefVariable(parameter);
			}
			popVariableTypes();
		}

		return RETURN_VALUE;
	}

	@Override
	public Object caseErrorTemplate(ErrorTemplate errorTemplate) {
		if (errorTemplate.getMissingVisibility() != -1) {
			addMessage(errorTemplate, ValidationMessageLevel.ERROR, "Missing visibility", errorTemplate
					.getMissingVisibility(), errorTemplate.getMissingVisibility());
		} else if (errorTemplate.getMissingName() != -1) {
			addMessage(errorTemplate, ValidationMessageLevel.ERROR, MISSING_NAME, errorTemplate
					.getMissingName(), errorTemplate.getMissingName());
		} else if (errorTemplate.getMissingOpenParenthesis() != -1) {
			addMessage(errorTemplate, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.OPEN_PARENTHESIS), errorTemplate.getMissingOpenParenthesis(), errorTemplate
							.getMissingOpenParenthesis());
		} else if (errorTemplate.getMissingParameters() != -1) {
			addMessage(errorTemplate, ValidationMessageLevel.ERROR, "Missing parameter", errorTemplate
					.getMissingParameters(), errorTemplate.getMissingParameters());
		} else if (errorTemplate.getMissingCloseParenthesis() != -1) {
			addMessage(errorTemplate, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.CLOSE_PARENTHESIS), errorTemplate.getMissingCloseParenthesis(),
					errorTemplate.getMissingCloseParenthesis());
		} else if (errorTemplate.getMissingGuardOpenParenthesis() != -1) {
			addMessage(errorTemplate, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.OPEN_PARENTHESIS), errorTemplate.getMissingGuardOpenParenthesis(),
					errorTemplate.getMissingGuardOpenParenthesis());
		} else if (errorTemplate.getMissingGuardCloseParenthesis() != -1) {
			addMessage(errorTemplate, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.CLOSE_PARENTHESIS), errorTemplate.getMissingGuardCloseParenthesis(),
					errorTemplate.getMissingGuardCloseParenthesis());
		} else if (errorTemplate.getMissingPostCloseParenthesis() != -1) {
			addMessage(errorTemplate, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.CLOSE_PARENTHESIS), errorTemplate.getMissingPostCloseParenthesis(),
					errorTemplate.getMissingPostCloseParenthesis());
		} else if (errorTemplate.getMissingEndHeader() != -1) {
			addMessage(errorTemplate, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.TEMPLATE_HEADER_END), errorTemplate.getMissingEndHeader(), errorTemplate
							.getMissingEndHeader());
		} else if (errorTemplate.getMissingEnd() != -1) {
			addMessage(errorTemplate, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.TEMPLATE_END), errorTemplate.getMissingEnd(), errorTemplate
							.getMissingEnd());
		}

		return null;
	}

	@Override
	public Object caseQuery(Query query) {
		pushVariableTypes(new HashMap<String, Set<IType>>(peekVariableTypes()));
		try {
			final Set<String> parameterNames = new HashSet<String>();
			for (Variable parameter : query.getParameters()) {
				doSwitch(parameter);
				if (parameter.getName() != null) {
					if (!parameterNames.add(parameter.getName())) {
						final AcceleoAstResult acceleoAstResult = result.getAcceleoAstResult();
						addMessage(query, ValidationMessageLevel.ERROR, parameter.getName()
								+ " duplicated parameter", acceleoAstResult.getStartPosition(parameter),
								acceleoAstResult.getEndPosition(parameter));

					}
				}
			}

			final IValidationResult validationResult = (IValidationResult)doSwitch(query.getBody());
			final Set<IType> possibleTypes = validationResult.getPossibleTypes(validationResult.getAstResult()
					.getAst());
			if (query.getType() != null) {
				final IValidationResult typeValidationResult = validator.validate(Collections.emptyMap(),
						query.getType());
				addUnresolvedVarRef(typeValidationResult);
				result.getAqlValidationResults().put(query.getType(), typeValidationResult);
				final Set<IType> iTypes = validator.getDeclarationTypes(queryEnvironment, typeValidationResult
						.getPossibleTypes(query.getType().getAst()));
				checkTypesCompatibility(query, possibleTypes, iTypes);
			}
		} finally {
			for (Variable parameter : query.getParameters()) {
				resolveVarRefVariable(parameter);
			}
			popVariableTypes();
		}

		return RETURN_VALUE;
	}

	@Override
	public Object caseErrorQuery(ErrorQuery errorQuery) {
		if (errorQuery.getMissingVisibility() != -1) {
			addMessage(errorQuery, ValidationMessageLevel.ERROR, "Missing visibility", errorQuery
					.getMissingVisibility(), errorQuery.getMissingVisibility());
		} else if (errorQuery.getMissingName() != -1) {
			addMessage(errorQuery, ValidationMessageLevel.ERROR, MISSING_NAME, errorQuery.getMissingName(),
					errorQuery.getMissingName());
		} else if (errorQuery.getMissingOpenParenthesis() != -1) {
			addMessage(errorQuery, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.OPEN_PARENTHESIS), errorQuery.getMissingOpenParenthesis(), errorQuery
							.getMissingOpenParenthesis());
		} else if (errorQuery.getMissingParameters() != -1) {
			addMessage(errorQuery, ValidationMessageLevel.ERROR, "Missing parameter", errorQuery
					.getMissingParameters(), errorQuery.getMissingParameters());
		} else if (errorQuery.getMissingCloseParenthesis() != -1) {
			addMessage(errorQuery, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.CLOSE_PARENTHESIS), errorQuery.getMissingCloseParenthesis(), errorQuery
							.getMissingCloseParenthesis());
		} else if (errorQuery.getMissingColon() != -1) {
			addMessage(errorQuery, ValidationMessageLevel.ERROR, getMissingTokenMessage(AcceleoParser.COLON),
					errorQuery.getMissingColon(), errorQuery.getMissingColon());
		} else if (errorQuery.getMissingType() != -1) {
			addMessage(errorQuery, ValidationMessageLevel.ERROR, "Missing or invalid type", errorQuery
					.getMissingType(), errorQuery.getMissingType());
		} else if (errorQuery.getMissingEqual() != -1) {
			addMessage(errorQuery, ValidationMessageLevel.ERROR, getMissingTokenMessage(AcceleoParser.EQUAL),
					errorQuery.getMissingEqual(), errorQuery.getMissingEqual());
		} else if (errorQuery.getMissingEnd() != -1) {
			addMessage(errorQuery, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.QUERY_END), errorQuery.getMissingEnd(), errorQuery.getMissingEnd());
		}

		return null;
	}

	@Override
	public Object caseVariable(Variable variable) {
		if (peekVariableTypes().containsKey(variable.getName())) {
			final AcceleoAstResult acceleoAstResult = result.getAcceleoAstResult();
			addMessage(variable, ValidationMessageLevel.WARNING, "Variable " + variable.getName()
					+ " already exists.", acceleoAstResult.getStartPosition(variable), acceleoAstResult
							.getEndPosition(variable));
		}
		final IValidationResult typeValidationResult = validator.validate(Collections.emptyMap(), variable
				.getType());
		addUnresolvedVarRef(typeValidationResult);
		result.getAqlValidationResults().put(variable.getType(), typeValidationResult);
		final Set<IType> types = validator.getDeclarationTypes(queryEnvironment, typeValidationResult
				.getPossibleTypes(variable.getType().getAst()));
		peekVariableTypes().put(variable.getName(), types);

		return RETURN_VALUE;
	}

	@Override
	public Object caseErrorVariable(ErrorVariable errorVariable) {
		if (errorVariable.getMissingName() != -1) {
			addMessage(errorVariable, ValidationMessageLevel.ERROR, MISSING_NAME, errorVariable
					.getMissingName(), errorVariable.getMissingName());
		} else if (errorVariable.getMissingColon() != -1) {
			addMessage(errorVariable, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.COLON), errorVariable.getMissingColon(), errorVariable.getMissingColon());
		} else if (errorVariable.getMissingType() != -1) {
			addMessage(errorVariable, ValidationMessageLevel.ERROR, "Missing or invalid type", errorVariable
					.getMissingType(), errorVariable.getMissingType());
		}

		return null;
	}

	@Override
	public Object caseBinding(Binding binding) {
		if (peekVariableTypes().containsKey(binding.getName())) {
			final AcceleoAstResult acceleoAstResult = result.getAcceleoAstResult();
			addMessage(binding, ValidationMessageLevel.WARNING, "Variable " + binding.getName()
					+ " already exists.", acceleoAstResult.getStartPosition(binding), acceleoAstResult
							.getEndPosition(binding));
		}

		final IValidationResult validationResult = (IValidationResult)doSwitch(binding.getInitExpression());
		final Set<IType> possibleTypes = validationResult.getPossibleTypes(validationResult.getAstResult()
				.getAst());
		if (binding.getType() != null) {
			final IValidationResult typeValidationResult = validator.validate(Collections.emptyMap(), binding
					.getType());
			result.getAqlValidationResults().put(binding.getType(), typeValidationResult);
			final Set<IType> iTypes = validator.getDeclarationTypes(queryEnvironment, typeValidationResult
					.getPossibleTypes(binding.getType().getAst()));
			checkTypesCompatibility(binding, possibleTypes, iTypes);
		}

		final Set<IType> variableTypes = new LinkedHashSet<IType>();
		if (forceCollectionBinding) {
			for (IType possibleType : possibleTypes) {
				if (possibleType instanceof ICollectionType) {
					variableTypes.add(((ICollectionType)possibleType).getCollectionType());
				} else {
					variableTypes.add(possibleType);
				}
			}
		} else {
			variableTypes.addAll(possibleTypes);
		}
		peekVariableTypes().put(binding.getName(), variableTypes);

		return RETURN_VALUE;
	}

	/**
	 * Check compatibility between {@link Binding#getInitExpression() expression} {@link IType} and
	 * {@link Binding#getType() declared} {@link IType}.
	 * 
	 * @param binding
	 *            the {@link Binding}
	 * @param possibleTypes
	 *            the {@link Set} of {@link Binding#getInitExpression() expression} {@link IType}
	 * @param declaredTypes
	 *            the {@link Set} of {@link Binding#getType() declared} {@link IType}
	 */
	protected void checkTypesCompatibility(AcceleoASTNode binding, final Set<IType> possibleTypes,
			final Set<IType> declaredTypes) {
		for (IType possibleType : possibleTypes) {
			List<IValidationMessage> messages = new ArrayList<IValidationMessage>();
			boolean hasCompatibleType = false;
			for (IType iType : declaredTypes) {
				if (!iType.isAssignableFrom(possibleType)) {
					if (forceCollectionBinding) {
						messages.addAll(validateBindingTypeForceCollection(binding, iType, possibleType));
					} else {
						final AcceleoAstResult acceleoAstResult = result.getAcceleoAstResult();
						messages.add(new ValidationMessage(ValidationMessageLevel.WARNING, iType
								+ IS_INCOMPATIBLE_WITH + possibleType, acceleoAstResult.getStartPosition(
										binding), acceleoAstResult.getEndPosition(binding)));
					}
				} else {
					hasCompatibleType = true;
				}
			}
			if (!hasCompatibleType) {
				result.addMessages(binding, messages);
			}
		}
	}

	/**
	 * Validates the given {@link Binding} type.
	 * 
	 * @param node
	 *            the {@link AcceleoASTNode}
	 * @param iType
	 *            the {@link IType} corresponding to the given {@link Binding#getType() binding type}
	 * @param possibleType
	 *            the possible {@link IType} of the given {@link Binding#getInitExpression() binding
	 *            expression}
	 * @return the {@link List} of {@link IValidationMessage} is something doesn't validate
	 */
	protected List<IValidationMessage> validateBindingTypeForceCollection(AcceleoASTNode node,
			final IType iType, IType possibleType) {
		final List<IValidationMessage> res = new ArrayList<IValidationMessage>();

		if (possibleType instanceof ICollectionType) {
			if (!iType.isAssignableFrom(((ICollectionType)possibleType).getCollectionType())) {
				final AcceleoAstResult acceleoAstResult = result.getAcceleoAstResult();
				res.add(new ValidationMessage(ValidationMessageLevel.WARNING, iType + IS_INCOMPATIBLE_WITH
						+ possibleType, acceleoAstResult.getStartPosition(node), acceleoAstResult
								.getEndPosition(node)));
			}
		} else if (!iType.isAssignableFrom(possibleType)) {
			final AcceleoAstResult acceleoAstResult = result.getAcceleoAstResult();
			res.add(new ValidationMessage(ValidationMessageLevel.WARNING, iType + IS_INCOMPATIBLE_WITH
					+ possibleType, acceleoAstResult.getStartPosition(node), acceleoAstResult.getEndPosition(
							node)));
		}

		return res;
	}

	@Override
	public Object caseErrorBinding(ErrorBinding errorBinding) {
		if (errorBinding.getMissingName() != -1) {
			addMessage(errorBinding, ValidationMessageLevel.ERROR, MISSING_NAME, errorBinding
					.getMissingName(), errorBinding.getMissingName());
		} else if (errorBinding.getMissingColon() != -1) {
			addMessage(errorBinding, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.COLON), errorBinding.getMissingColon(), errorBinding.getMissingColon());
		} else if (errorBinding.getMissingType() != -1) {
			addMessage(errorBinding, ValidationMessageLevel.ERROR, "Missing type literal", errorBinding
					.getMissingType(), errorBinding.getMissingType());
		} else if (errorBinding.getMissingAffectationSymbolePosition() != -1) {
			addMessage(errorBinding, ValidationMessageLevel.ERROR, getMissingTokenMessage(errorBinding
					.getMissingAffectationSymbole()), errorBinding.getMissingAffectationSymbolePosition(),
					errorBinding.getMissingAffectationSymbolePosition());
		}

		return null;
	}

	@Override
	public Object caseExpressionStatement(ExpressionStatement expressionStatement) {
		doSwitch(expressionStatement.getExpression());
		return RETURN_VALUE;
	}

	@Override
	public Object caseErrorExpressionStatement(ErrorExpressionStatement errorExpressionStatement) {
		doSwitch(errorExpressionStatement.getExpression());
		if (errorExpressionStatement.getMissingEndHeader() != -1) {
			addMessage(errorExpressionStatement, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.EXPRESSION_STATEMENT_END), errorExpressionStatement.getMissingEndHeader(),
					errorExpressionStatement.getMissingEndHeader());
		}

		return null;
	}

	@Override
	public Object caseProtectedArea(ProtectedArea protectedArea) {
		final IValidationResult idValidationResult = (IValidationResult)doSwitch(protectedArea.getId());
		Set<IType> idPossibleTypes = idValidationResult.getPossibleTypes(idValidationResult.getAstResult()
				.getAst());
		checkStringType(protectedArea.getId(), idPossibleTypes);
		if (protectedArea.getStartTagPrefix() != null) {
			final IValidationResult startTagPrefixValidationResult = (IValidationResult)doSwitch(protectedArea
					.getStartTagPrefix());
			Set<IType> startTagPrefixPossibleTypes = startTagPrefixValidationResult.getPossibleTypes(
					startTagPrefixValidationResult.getAstResult().getAst());
			checkStringType(protectedArea.getStartTagPrefix(), startTagPrefixPossibleTypes);
		}
		if (protectedArea.getEndTagPrefix() != null) {
			final IValidationResult endTagPrefixValidationResult = (IValidationResult)doSwitch(protectedArea
					.getEndTagPrefix());
			Set<IType> endTagPrefixPossibleTypes = endTagPrefixValidationResult.getPossibleTypes(
					endTagPrefixValidationResult.getAstResult().getAst());
			checkStringType(protectedArea.getEndTagPrefix(), endTagPrefixPossibleTypes);
		}
		doSwitch(protectedArea.getBody());

		return RETURN_VALUE;
	}

	@Override
	public Object caseErrorProtectedArea(ErrorProtectedArea errorProtectedArea) {
		if (errorProtectedArea.getMissingOpenParenthesis() != -1) {
			addMessage(errorProtectedArea, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.OPEN_PARENTHESIS), errorProtectedArea.getMissingOpenParenthesis(),
					errorProtectedArea.getMissingOpenParenthesis());
		} else if (errorProtectedArea.getMissingStartTagPrefixCloseParenthesis() != -1) {
			addMessage(errorProtectedArea, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.CLOSE_PARENTHESIS), errorProtectedArea
							.getMissingStartTagPrefixCloseParenthesis(), errorProtectedArea
									.getMissingStartTagPrefixCloseParenthesis());
		} else if (errorProtectedArea.getMissingEndTagPrefixCloseParenthesis() != -1) {
			addMessage(errorProtectedArea, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.CLOSE_PARENTHESIS), errorProtectedArea
							.getMissingEndTagPrefixCloseParenthesis(), errorProtectedArea
									.getMissingEndTagPrefixCloseParenthesis());
		} else if (errorProtectedArea.getMissingCloseParenthesis() != -1) {
			addMessage(errorProtectedArea, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.CLOSE_PARENTHESIS), errorProtectedArea.getMissingCloseParenthesis(),
					errorProtectedArea.getMissingCloseParenthesis());
		} else if (errorProtectedArea.getMissingEndHeader() != -1) {
			addMessage(errorProtectedArea, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.PROTECTED_AREA_HEADER_END), errorProtectedArea.getMissingEndHeader(),
					errorProtectedArea.getMissingEndHeader());
		} else if (errorProtectedArea.getMissingEnd() != -1) {
			addMessage(errorProtectedArea, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.PROTECTED_AREA_END), errorProtectedArea.getMissingEnd(), errorProtectedArea
							.getMissingEnd());
		}

		return null;
	}

	@Override
	public Object caseExpression(Expression expression) {
		final IValidationResult res = validator.validate(peekVariableTypes(), expression.getAst());
		addUnresolvedVarRef(res);

		result.getAqlValidationResults().put(expression.getAst(), res);
		final AcceleoAstResult acceleoAstResult = result.getAcceleoAstResult();
		result.addMessages(expression, shiftMessages(res.getMessages(), acceleoAstResult.getStartPosition(
				expression)));

		return res;
	}

	@Override
	public Object caseForStatement(ForStatement forStatement) {
		pushVariableTypes(new HashMap<String, Set<IType>>(peekVariableTypes()));
		try {
			if (forStatement.getBinding() != null) {
				forceCollectionBinding = true;
				try {
					doSwitch(forStatement.getBinding());
				} finally {
					forceCollectionBinding = false;
				}
			}
			if (forStatement.getSeparator() != null) {
				doSwitch(forStatement.getSeparator());
			}
			doSwitch(forStatement.getBody());
		} finally {
			if (forStatement.getBinding() != null) {
				resolveVarRefVariable(forStatement.getBinding());
			}
			popVariableTypes();
		}

		return RETURN_VALUE;
	}

	@Override
	public Object caseErrorForStatement(ErrorForStatement errorForStatement) {
		if (errorForStatement.getMissingOpenParenthesis() != -1) {
			addMessage(errorForStatement, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.OPEN_PARENTHESIS), errorForStatement.getMissingOpenParenthesis(),
					errorForStatement.getMissingOpenParenthesis());
		} else if (errorForStatement.getMissingBinding() != -1) {
			addMessage(errorForStatement, ValidationMessageLevel.ERROR, "Missing binding", errorForStatement
					.getMissingBinding(), errorForStatement.getMissingBinding());
		} else if (errorForStatement.getMissingCloseParenthesis() != -1) {
			addMessage(errorForStatement, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.CLOSE_PARENTHESIS), errorForStatement.getMissingCloseParenthesis(),
					errorForStatement.getMissingCloseParenthesis());
		} else if (errorForStatement.getMissingSeparatorCloseParenthesis() != -1) {
			addMessage(errorForStatement, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.CLOSE_PARENTHESIS), errorForStatement.getMissingSeparatorCloseParenthesis(),
					errorForStatement.getMissingSeparatorCloseParenthesis());
		} else if (errorForStatement.getMissingEndHeader() != -1) {
			addMessage(errorForStatement, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.FOR_HEADER_END), errorForStatement.getMissingEndHeader(), errorForStatement
							.getMissingEndHeader());
		} else if (errorForStatement.getMissingEnd() != -1) {
			addMessage(errorForStatement, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.FOR_END), errorForStatement.getMissingEnd(), errorForStatement
							.getMissingEnd());
		}

		return null;
	}

	@Override
	public Object caseIfStatement(IfStatement ifStatement) {
		final IValidationResult conditionValidationResult = (IValidationResult)doSwitch(ifStatement
				.getCondition());
		final Set<IType> conditionPossibleTypes = conditionValidationResult.getPossibleTypes(ifStatement
				.getCondition().getAst().getAst());

		checkBooleanType(ifStatement.getCondition(), conditionPossibleTypes);

		final Map<String, Set<IType>> thenTypes = new HashMap<String, Set<IType>>(peekVariableTypes());
		thenTypes.putAll(conditionValidationResult.getInferredVariableTypes(ifStatement.getCondition()
				.getAst().getAst(), Boolean.TRUE));
		pushVariableTypes(thenTypes);
		try {
			doSwitch(ifStatement.getThen());
		} finally {
			popVariableTypes();
		}
		if (ifStatement.getElse() != null) {
			final Map<String, Set<IType>> elseTypes = new HashMap<String, Set<IType>>(peekVariableTypes());
			elseTypes.putAll(conditionValidationResult.getInferredVariableTypes(ifStatement.getCondition()
					.getAst().getAst(), Boolean.FALSE));
			pushVariableTypes(elseTypes);
			try {
				doSwitch(ifStatement.getElse());
			} finally {
				popVariableTypes();
			}
		}

		return RETURN_VALUE;
	}

	private void checkBooleanType(AcceleoASTNode node, final Set<IType> possibleTypes) {
		if (!possibleTypes.isEmpty()) {
			boolean onlyBoolean = true;
			boolean onlyNotBoolean = true;
			for (IType type : possibleTypes) {
				final boolean assignableFrom = booleanObjectType.isAssignableFrom(type) || booleanType
						.isAssignableFrom(type);
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
						possibleTypes);
				final AcceleoAstResult acceleoAstResult = result.getAcceleoAstResult();
				addMessage(node, ValidationMessageLevel.ERROR, message, acceleoAstResult.getStartPosition(
						node), acceleoAstResult.getEndPosition(node));
			} else {
				final String message = String.format(
						"The predicate may evaluate to a value that is not a boolean type (%s).",
						possibleTypes);
				final AcceleoAstResult acceleoAstResult = result.getAcceleoAstResult();
				addMessage(node, ValidationMessageLevel.WARNING, message, acceleoAstResult.getStartPosition(
						node), acceleoAstResult.getEndPosition(node));
			}
		} else {
			final String message = String.format("The predicate never evaluates to a boolean type (%s).",
					possibleTypes);
			final AcceleoAstResult acceleoAstResult = result.getAcceleoAstResult();
			addMessage(node, ValidationMessageLevel.ERROR, message, acceleoAstResult.getStartPosition(node),
					acceleoAstResult.getEndPosition(node));
		}
	}

	@Override
	public Object caseErrorIfStatement(ErrorIfStatement errorIfStatement) {
		if (errorIfStatement.getMissingOpenParenthesis() != -1) {
			addMessage(errorIfStatement, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.OPEN_PARENTHESIS), errorIfStatement.getMissingOpenParenthesis(),
					errorIfStatement.getMissingOpenParenthesis());
		} else if (errorIfStatement.getMissingCloseParenthesis() != -1) {
			addMessage(errorIfStatement, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.CLOSE_PARENTHESIS), errorIfStatement.getMissingCloseParenthesis(),
					errorIfStatement.getMissingCloseParenthesis());
		} else if (errorIfStatement.getMissingEndHeader() != -1) {
			addMessage(errorIfStatement, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.IF_HEADER_END), errorIfStatement.getMissingEndHeader(), errorIfStatement
							.getMissingEndHeader());
		} else if (errorIfStatement.getMissingEnd() != -1) {
			addMessage(errorIfStatement, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.IF_END), errorIfStatement.getMissingEnd(), errorIfStatement
							.getMissingEnd());
		}

		return null;
	}

	@Override
	public Object caseLetStatement(LetStatement letStatement) {
		pushVariableTypes(new HashMap<String, Set<IType>>(peekVariableTypes()));
		try {
			for (Binding binding : letStatement.getVariables()) {
				doSwitch(binding);
			}
			doSwitch(letStatement.getBody());
		} finally {
			for (Binding binding : letStatement.getVariables()) {
				resolveVarRefVariable(binding);
			}
			popVariableTypes();
		}

		return RETURN_VALUE;
	}

	@Override
	public Object caseErrorLetStatement(ErrorLetStatement errorLetStatement) {
		if (errorLetStatement.getMissingBindings() != -1) {
			addMessage(errorLetStatement, ValidationMessageLevel.WARNING, "Missing binding", errorLetStatement
					.getMissingBindings(), errorLetStatement.getMissingBindings());
		} else if (errorLetStatement.getMissingEndHeader() != -1) {
			addMessage(errorLetStatement, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.LET_HEADER_END), errorLetStatement.getMissingEndHeader(), errorLetStatement
							.getMissingEndHeader());
		} else if (errorLetStatement.getMissingEnd() != -1) {
			addMessage(errorLetStatement, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.LET_END), errorLetStatement.getMissingEnd(), errorLetStatement
							.getMissingEnd());
		}

		return null;
	}

	@Override
	public Object caseFileStatement(FileStatement fileStatement) {
		final IValidationResult urlValidationResult = (IValidationResult)doSwitch(fileStatement.getUrl());
		Set<IType> urlPossibleTypes = urlValidationResult.getPossibleTypes(urlValidationResult.getAstResult()
				.getAst());
		checkStringType(fileStatement.getUrl(), urlPossibleTypes);
		if (fileStatement.getCharset() != null) {
			final IValidationResult charsetValidationResult = (IValidationResult)doSwitch(fileStatement
					.getCharset());
			Set<IType> charsetPossibleTypes = charsetValidationResult.getPossibleTypes(charsetValidationResult
					.getAstResult().getAst());
			checkStringType(fileStatement.getCharset(), charsetPossibleTypes);
		}
		doSwitch(fileStatement.getBody());

		return RETURN_VALUE;
	}

	/**
	 * Checks given possible {@link IType} against the {@link String} {@link IType}.
	 * 
	 * @param node
	 *            the {@link AcceleoASTNode}
	 * @param possibleTypes
	 *            the {@link Set} of possible {@link IType}
	 */
	private void checkStringType(AcceleoASTNode node, Set<IType> possibleTypes) {
		if (!possibleTypes.isEmpty()) {
			boolean onlyString = true;
			boolean onlyNotString = true;
			for (IType type : possibleTypes) {
				final boolean assignableFrom = stringType.isAssignableFrom(type);
				onlyString = onlyString && assignableFrom;
				onlyNotString = onlyNotString && !assignableFrom;
				if (!onlyString && !onlyNotString) {
					break;
				}
			}
			if (!onlyString) {
				if (onlyNotString) {
					final String message = String.format(
							"The expression never evaluates to a String type (%s).", possibleTypes);
					final AcceleoAstResult acceleoAstResult = result.getAcceleoAstResult();
					addMessage(node, ValidationMessageLevel.WARNING, message, acceleoAstResult
							.getStartPosition(node), acceleoAstResult.getEndPosition(node));
				} else {
					final String message = String.format(
							"The expression may evaluate to a value that is not a String type (%s).\"",
							possibleTypes);
					final AcceleoAstResult acceleoAstResult = result.getAcceleoAstResult();
					addMessage(node, ValidationMessageLevel.WARNING, message, acceleoAstResult
							.getStartPosition(node), acceleoAstResult.getEndPosition(node));
				}
			} else {
				// everything is fine
			}
		} else {
			final String message = String.format("The expression never evaluates to a String type (%s).",
					possibleTypes);
			final AcceleoAstResult acceleoAstResult = result.getAcceleoAstResult();
			addMessage(node, ValidationMessageLevel.ERROR, message, acceleoAstResult.getStartPosition(node),
					acceleoAstResult.getEndPosition(node));
		}
	}

	@Override
	public Object caseErrorFileStatement(ErrorFileStatement errorFileStatement) {
		if (errorFileStatement.getMissingOpenParenthesis() != -1) {
			addMessage(errorFileStatement, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.OPEN_PARENTHESIS), errorFileStatement.getMissingOpenParenthesis(),
					errorFileStatement.getMissingOpenParenthesis());
		} else if (errorFileStatement.getMissingComma() != -1) {
			addMessage(errorFileStatement, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.COMMA), errorFileStatement.getMissingComma(), errorFileStatement
							.getMissingComma());
		} else if (errorFileStatement.getMissingOpenMode() != -1) {
			addMessage(errorFileStatement, ValidationMessageLevel.ERROR,
					"Missing or invalid file open mode: overwrite, append, create", errorFileStatement
							.getMissingOpenMode(), errorFileStatement.getMissingOpenMode());
		} else if (errorFileStatement.getMissingCloseParenthesis() != -1) {
			addMessage(errorFileStatement, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.CLOSE_PARENTHESIS), errorFileStatement.getMissingCloseParenthesis(),
					errorFileStatement.getMissingCloseParenthesis());
		} else if (errorFileStatement.getMissingEndHeader() != -1) {
			addMessage(errorFileStatement, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.FILE_HEADER_END), errorFileStatement.getMissingEndHeader(),
					errorFileStatement.getMissingEndHeader());
		} else if (errorFileStatement.getMissingEnd() != -1) {
			addMessage(errorFileStatement, ValidationMessageLevel.ERROR, getMissingTokenMessage(
					AcceleoParser.FILE_END), errorFileStatement.getMissingEnd(), errorFileStatement
							.getMissingEnd());
		}

		return null;
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
