/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.parser;

import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

import org.eclipse.acceleo.query.ast.Binding;
import org.eclipse.acceleo.query.ast.BooleanLiteral;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.CollectionTypeLiteral;
import org.eclipse.acceleo.query.ast.Conditional;
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorBinding;
import org.eclipse.acceleo.query.ast.ErrorCall;
import org.eclipse.acceleo.query.ast.ErrorEnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorExpression;
import org.eclipse.acceleo.query.ast.ErrorTypeLiteral;
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
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine.AstResult;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IValidationMessage;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.ValidationMessageLevel;
import org.eclipse.acceleo.query.runtime.impl.ServicesValidationResult;
import org.eclipse.acceleo.query.runtime.impl.ValidationMessage;
import org.eclipse.acceleo.query.runtime.impl.ValidationResult;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierSetLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.LambdaType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;

/**
 * Validates an {@link org.eclipse.acceleo.query.ast.Expression Expression}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AstValidator extends AstSwitch<Set<IType>> {

	/**
	 * Message used when a variable overrides an existing value.
	 */
	private static final String VARIABLE_OVERRIDES_AN_EXISTING_VALUE = "Variable %s overrides an existing value.";

	/**
	 * Message used when an {@link EClassifierType} is not registered.
	 */
	private static final String ECLASSIFIER_NOT_REGISTERED = "%s is not registered in the current environment";

	/**
	 * Message used when an empty {@link ICollectionType} is produced.
	 */
	private static final String EMPTY_COLLECTION = "Empty collection: %s";

	/**
	 * Should never happen message.
	 */
	private static final String SHOULD_NEVER_HAPPEN = "should never happen";

	/**
	 * The {@link ValidationResult}.
	 */
	protected ValidationResult validationResult;

	/**
	 * Basic language validation services implementation.
	 */
	private final ValidationServices services;

	/**
	 * Local variable types usable during validation.
	 */
	private final Stack<Map<String, Set<IType>>> variableTypesStack;

	/**
	 * Set of {@link IValidationMessage}.
	 */
	private Set<IValidationMessage> messages = new LinkedHashSet<IValidationMessage>();

	/**
	 * Constructor.
	 * 
	 * @param environment
	 *            the {@link IReadOnlyQueryEnvironment} used to validate
	 * @deprecated use {@link #AstValidator(ValidationServices)}
	 */
	public AstValidator(IReadOnlyQueryEnvironment environment) {
		this(new ValidationServices(environment));
	}

	/**
	 * Constructor.
	 * 
	 * @param services
	 *            the {@link ValidationServices} used to validate
	 */
	public AstValidator(ValidationServices services) {
		this.services = services;
		this.variableTypesStack = new Stack<Map<String, Set<IType>>>();
	}

	/**
	 * Checks warnings and errors from {@link NothingType} removing them from the resulting {@link Set} of
	 * {@link IType}.
	 * 
	 * @param expression
	 *            the {@link Expression} associated with {@link IType}
	 * @param types
	 *            the {@link Set} of {@link IType} to check
	 * @return a {@link Set} of {@link IType} filtered from {@link NothingType}
	 */
	private Set<IType> checkWarningsAndErrors(Expression expression, Set<IType> types) {
		final Set<IType> result = new LinkedHashSet<IType>();
		final Set<IType> validationTypes = new LinkedHashSet<IType>();
		final List<ValidationMessage> msgs = new ArrayList<ValidationMessage>();
		final List<ValidationMessage> infoMsgs = new ArrayList<ValidationMessage>();
		for (IType type : types) {
			final AstResult astResult = validationResult.getAstResult();
			final int startPostion = getStartPosition(astResult, expression);
			final int endPosition = astResult.getEndPosition(expression);
			if (type instanceof NothingType) {
				msgs.add(new ValidationMessage(ValidationMessageLevel.WARNING, ((NothingType)type)
						.getMessage(), startPostion, endPosition));
			} else if (type instanceof EClassifierType) {
				if (services.getQueryEnvironment().getEPackageProvider().isRegistered(
						((EClassifierType)type).getType())) {
					result.add(type);
				} else {
					msgs.add(new ValidationMessage(ValidationMessageLevel.WARNING, String.format(
							ECLASSIFIER_NOT_REGISTERED, type), startPostion, endPosition));
				}
			} else {
				if (type instanceof ICollectionType
						&& ((ICollectionType)type).getCollectionType() instanceof NothingType
						&& !isCollectionInExtension(expression)) {
					final NothingType nothing = (NothingType)((ICollectionType)type).getCollectionType();
					infoMsgs.add(new ValidationMessage(ValidationMessageLevel.INFO, String.format(
							EMPTY_COLLECTION, nothing.getMessage()), startPostion, endPosition));
				}
				result.add(type);
			}
			// Even if it's a Nothing, make the type known for validation purposes
			validationTypes.add(type);
		}

		if (result.size() == 0) {
			for (ValidationMessage message : msgs) {
				message.setLevel(ValidationMessageLevel.ERROR);
			}
		}

		messages.addAll(msgs);
		messages.addAll(infoMsgs);
		validationResult.addTypes(expression, validationTypes);

		return result;

	}

	/**
	 * Tells if the given expression is either a {@link SetInExtensionLiteral} or a
	 * {@link SetInExtensionLiteral}.
	 * 
	 * @param expression
	 *            the {@link Expression} to check
	 * @return <code>true</code> if the given expression is either a {@link SetInExtensionLiteral} or a
	 *         {@link SetInExtensionLiteral}, <code>false</code> otherwise
	 */
	private boolean isCollectionInExtension(Expression expression) {
		return expression instanceof SetInExtensionLiteral
				|| expression instanceof SequenceInExtensionLiteral;
	}

	/**
	 * Gets the start position for the given {@link Expression} and {@link AstResult}.
	 * 
	 * @param astResult
	 *            the {@link AstResult}
	 * @param expression
	 *            the {@link Expression}
	 * @return the start position for the given {@link Expression} and {@link AstResult}
	 */
	private int getStartPosition(final AstResult astResult, Expression expression) {
		final int startPostion;
		if (expression instanceof Call) {
			final String serviceName = ((Call)expression).getServiceName();
			if (AstBuilderListener.OPERATOR_SERVICE_NAMES.contains(serviceName)) {
				if (AstBuilderListener.NOT_OPERATOR.equals(serviceName)
						|| AstBuilderListener.UNARY_MIN_OPERATOR.equals(serviceName)) {
					startPostion = astResult.getStartPosition(expression);
				} else {
					startPostion = astResult.getStartPosition(((Call)expression).getArguments().get(0));
				}
			} else {
				startPostion = astResult.getEndPosition(((Call)expression).getArguments().get(0));
			}
		} else {
			startPostion = astResult.getStartPosition(expression);
		}
		return startPostion;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseBooleanLiteral(org.eclipse.acceleo.query.ast.BooleanLiteral)
	 */
	@Override
	public Set<IType> caseBooleanLiteral(BooleanLiteral object) {
		final Set<IType> possibleTypes = Sets.newLinkedHashSet();

		possibleTypes.add(new ClassType(services.getQueryEnvironment(), java.lang.Boolean.class));

		return checkWarningsAndErrors(object, possibleTypes);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseCall(org.eclipse.acceleo.query.ast.Call)
	 */
	@Override
	public Set<IType> caseCall(Call call) {
		final Set<IType> possibleTypes;

		final List<Set<IType>> argTypes = inferArgTypes(call);

		final String serviceName = call.getServiceName();
		final ServicesValidationResult servicesValidationResult;
		switch (call.getType()) {
			case CALLSERVICE:
				servicesValidationResult = services.callType(call, validationResult, serviceName, argTypes);
				break;
			case CALLORAPPLY:
				servicesValidationResult = services.callOrApplyTypes(call, validationResult, serviceName,
						argTypes);
				break;
			case COLLECTIONCALL:
				servicesValidationResult = services.collectionServiceCallTypes(call, validationResult,
						serviceName, argTypes);
				break;
			default:
				throw new UnsupportedOperationException(SHOULD_NEVER_HAPPEN);
		}

		possibleTypes = servicesValidationResult.getResultingTypes();
		return checkWarningsAndErrors(call, possibleTypes);
	}

	/**
	 * Computes argument types and inferred types.
	 * 
	 * @param call
	 *            the {@link Call}
	 * @return the {@link List} of arguments possible types
	 */
	private List<Set<IType>> inferArgTypes(Call call) {
		final List<Set<IType>> result = new ArrayList<Set<IType>>();

		if (call.getArguments().size() == 1) {
			if (AstBuilderListener.NOT_SERVICE_NAME.equals(call.getServiceName())) {
				final Expression operand = call.getArguments().get(0);
				final Set<IType> operandTypes = doSwitch(operand);
				result.add(operandTypes);
				inferNotTypes(call, operand);
			} else {
				result.add(doSwitch(call.getArguments().get(0)));
			}
		} else if (call.getArguments().size() == 2) {
			if (AstBuilderListener.OCL_IS_KIND_OF_SERVICE_NAME.equals(call.getServiceName())) {
				final Expression receiver = call.getArguments().get(0);
				final Set<IType> receiverTypes = doSwitch(receiver);
				final Set<IType> argTypes = doSwitch(call.getArguments().get(1));
				result.add(receiverTypes);
				result.add(argTypes);
				if (receiver instanceof VarRef) {
					inferOclIsKindOfTypes(call, (VarRef)receiver, argTypes);
				}
			} else if (AstBuilderListener.OCL_IS_TYPE_OF_SERVICE_NAME.equals(call.getServiceName())) {
				final Expression receiver = call.getArguments().get(0);
				final Set<IType> receiverTypes = doSwitch(receiver);
				final Set<IType> argTypes = doSwitch(call.getArguments().get(1));
				result.add(receiverTypes);
				result.add(argTypes);
				if (receiver instanceof VarRef) {
					inferOclIsTypeOfTypes(call, (VarRef)receiver, argTypes);
				}
			} else if (AstBuilderListener.AND_SERVICE_NAME.equals(call.getServiceName())) {
				final Expression leftOperand = call.getArguments().get(0);
				final Expression rightOperand = call.getArguments().get(1);
				final Set<IType> leftOperandTypes = doSwitch(leftOperand);

				// compute and inferred types before propagating the right operand inferred types
				final Map<String, Set<IType>> rightOperandInferredTypes = new HashMap<String, Set<IType>>(
						variableTypesStack.peek());
				rightOperandInferredTypes.putAll(validationResult.getInferredVariableTypes(leftOperand,
						Boolean.TRUE));
				final AstValidator rightValidator = new AstValidator(services.getQueryEnvironment());
				final IValidationResult rightValidatorResult = rightValidator.validate(variableTypesStack
						.peek(), validationResult.getAstResult().subResult(rightOperand));

				// propagate the right operand inferred types
				variableTypesStack.push(rightOperandInferredTypes);
				final Set<IType> rightOperandTypes = new LinkedHashSet<IType>();
				try {
					// compute right operand types with left operand inferred types
					rightOperandTypes.addAll(doSwitch(rightOperand));
					result.add(leftOperandTypes);
					result.add(rightOperandTypes);
				} finally {
					variableTypesStack.pop();
					inferAndTypes(call, validationResult, rightValidatorResult);
				}
			} else if (AstBuilderListener.OR_SERVICE_NAME.equals(call.getServiceName())) {
				final Expression leftOperand = call.getArguments().get(0);
				final Expression rightOperand = call.getArguments().get(1);
				final Set<IType> leftOperandTypes = doSwitch(leftOperand);

				// compute or inferred types before propagating the right operand inferred types
				final Map<String, Set<IType>> rightOperandInferredTypes = new HashMap<String, Set<IType>>(
						variableTypesStack.peek());
				rightOperandInferredTypes.putAll(validationResult.getInferredVariableTypes(leftOperand,
						Boolean.FALSE));
				final AstValidator rightValidator = new AstValidator(services.getQueryEnvironment());
				final IValidationResult rightValidatorResult = rightValidator.validate(variableTypesStack
						.peek(), validationResult.getAstResult().subResult(rightOperand));

				// propagate the right operand inferred types
				variableTypesStack.push(rightOperandInferredTypes);
				final Set<IType> rightOperandTypes = new LinkedHashSet<IType>();
				try {
					// compute right operand types with left operand inferred types
					rightOperandTypes.addAll(doSwitch(rightOperand));
					result.add(leftOperandTypes);
					result.add(rightOperandTypes);
				} finally {
					variableTypesStack.pop();
					inferOrTypes(call, validationResult, rightValidatorResult);
				}
			} else {
				result.add(doSwitch(call.getArguments().get(0)));
				result.add(doSwitch(call.getArguments().get(1)));
			}
		} else {
			for (Expression arg : call.getArguments()) {
				result.add(doSwitch(arg));
			}
		}

		return result;
	}

	/**
	 * Computes inferred {@link IType} for {@link AstBuilderListener#NOT_SERVICE_NAME} {@link Call}. It swaps
	 * <code>true</code> and <code>false</code> inferred {@link IType}.
	 * 
	 * @param call
	 *            the {@link AstBuilderListener#OCL_IS_KIND_OF_SERVICE_NAME} {@link Call}
	 * @param operand
	 *            operand the {@link Expression} operand
	 */
	private void inferNotTypes(Call call, final Expression operand) {
		final Map<String, Set<IType>> inferredOperandTrueTypes = validationResult.getInferredVariableTypes(
				operand, Boolean.TRUE);
		final Map<String, Set<IType>> inferredOperandFalseTypes = validationResult.getInferredVariableTypes(
				operand, Boolean.FALSE);
		validationResult.putInferredVariableTypes(call, Boolean.TRUE, inferredOperandFalseTypes);
		validationResult.putInferredVariableTypes(call, Boolean.FALSE, inferredOperandTrueTypes);
	}

	/**
	 * Computes inferred {@link IType} for {@link AstBuilderListener#OCL_IS_KIND_OF_SERVICE_NAME} {@link Call}
	 * .
	 * 
	 * @param call
	 *            the {@link AstBuilderListener#OCL_IS_KIND_OF_SERVICE_NAME} {@link Call}
	 * @param varRef
	 *            receiver {@link VarRef}
	 * @param argTypes
	 *            argument {@link IType} of the {@link AstBuilderListener#OCL_IS_KIND_OF_SERVICE_NAME}
	 *            {@link Call}
	 */
	private void inferOclIsKindOfTypes(Call call, VarRef varRef, Set<IType> argTypes) {
		final Set<IType> originalTypes = variableTypesStack.peek().get(varRef.getVariableName());
		if (originalTypes != null) {
			final Set<IType> inferredTrueTypes = new LinkedHashSet<IType>();
			final Set<IType> inferredFalseTypes = new LinkedHashSet<IType>();
			final StringBuilder messageWhenTrue = new StringBuilder("Always false:");
			final StringBuilder messageWhenFalse = new StringBuilder("Always true:");

			for (IType originalType : originalTypes) {
				for (IType argType : argTypes) {
					final IType lowerArgType = services.lower(argType, argType);
					if (lowerArgType != null && lowerArgType.isAssignableFrom(originalType)) {
						inferredTrueTypes.add(originalType);
						messageWhenFalse.append(String.format(
								"\nNothing inferred when %s (%s) is not kind of %s",
								varRef.getVariableName(), originalType, argType));
					} else if (originalType != null && originalType.isAssignableFrom(lowerArgType)) {
						inferredTrueTypes.add(lowerArgType);
						inferredFalseTypes.add(originalType);
					} else {
						final Set<IType> intersectionTypes = services
								.intersection(originalType, lowerArgType);
						if (intersectionTypes.isEmpty()) {
							messageWhenTrue.append(String.format(
									"\nNothing inferred when %s (%s) is kind of %s",
									varRef.getVariableName(), originalType, argType));
							inferredFalseTypes.add(originalType);
						} else {
							inferredTrueTypes.addAll(intersectionTypes);
							inferredFalseTypes.add(originalType);
						}
					}
				}
			}

			if (!argTypes.isEmpty()) {
				registerInferredTypes(call, varRef, inferredTrueTypes, inferredFalseTypes, messageWhenTrue,
						messageWhenFalse);
			}
		}
	}

	/**
	 * Computes inferred {@link IType} for {@link AstBuilderListener#OCL_IS_TYPE_OF_SERVICE_NAME} {@link Call}
	 * .
	 * 
	 * @param call
	 *            the {@link AstBuilderListener#OCL_IS_TYPE_OF_SERVICE_NAME} {@link Call}
	 * @param varRef
	 *            receiver {@link VarRef}
	 * @param argTypes
	 *            argument {@link IType} of the {@link AstBuilderListener#OCL_IS_TYPE_OF_SERVICE_NAME}
	 *            {@link Call}
	 */
	private void inferOclIsTypeOfTypes(Call call, VarRef varRef, Set<IType> argTypes) {
		final Set<IType> originalTypes = variableTypesStack.peek().get(varRef.getVariableName());
		if (originalTypes != null) {
			final Set<IType> inferredTrueTypes = new LinkedHashSet<IType>();
			final Set<IType> inferredFalseTypes = new LinkedHashSet<IType>();
			final StringBuilder messageWhenTrue = new StringBuilder("Always false:");
			final StringBuilder messageWhenFalse = new StringBuilder("Always true:");

			for (IType originalType : originalTypes) {
				for (IType argType : argTypes) {
					final IType lowerArgType = services.lower(argType, argType);
					final IType lowerType = services.lower(originalType, lowerArgType);
					if (lowerArgType != null && lowerArgType.equals(originalType)) {
						inferredTrueTypes.add(lowerArgType);
						final Set<EClass> upperSubEClasses = getUpperSubTypes(originalType);
						if (upperSubEClasses.isEmpty()) {
							messageWhenFalse.append(String.format(
									"\nNothing inferred when %s (%s) is not type of %s", varRef
											.getVariableName(), originalType, argType));
						} else {
							for (EClass upperSubEClasse : upperSubEClasses) {
								inferredFalseTypes.add(new EClassifierType(services.getQueryEnvironment(),
										upperSubEClasse));
							}
						}
					} else if (lowerType != null && lowerType.isAssignableFrom(lowerArgType)) {
						inferredTrueTypes.add(lowerArgType);
						inferredFalseTypes.add(originalType);
					} else {
						messageWhenTrue.append(String.format("\nNothing inferred when %s (%s) is type of %s",
								varRef.getVariableName(), originalType, argType));
						inferredFalseTypes.add(originalType);
					}
				}
			}

			if (!argTypes.isEmpty()) {
				registerInferredTypes(call, varRef, inferredTrueTypes, inferredFalseTypes, messageWhenTrue,
						messageWhenFalse);
			}
		}
	}

	/**
	 * Registers inferred {@link IType} and mesages.
	 * 
	 * @param call
	 *            the {@link Call} that inferred types (oclIsKindOf, oclIsTypeOf, ...)
	 * @param varRef
	 *            the {@link VarRef} use in the {@link Call}
	 * @param inferredTrueTypes
	 *            inferred {@link IType} when <code>true</code>
	 * @param inferredFalseTypes
	 *            inferred {@link IType} when <code>false</code>
	 * @param messageWhenTrue
	 *            message when <code>true</code>
	 * @param messageWhenFalse
	 *            message when <code>false</code>
	 */
	private void registerInferredTypes(Call call, VarRef varRef, final Set<IType> inferredTrueTypes,
			final Set<IType> inferredFalseTypes, final StringBuilder messageWhenTrue,
			final StringBuilder messageWhenFalse) {
		if (!inferredTrueTypes.isEmpty()) {
			Map<String, Set<IType>> inferredTrueTypesMap = new HashMap<String, Set<IType>>();
			inferredTrueTypesMap.put(varRef.getVariableName(), inferredTrueTypes);
			validationResult.putInferredVariableTypes(call, Boolean.TRUE, inferredTrueTypesMap);
		} else {
			final AstResult astResult = validationResult.getAstResult();
			final int startPostion = astResult.getStartPosition(call);
			final int endPosition = astResult.getEndPosition(call);
			final ValidationMessage message = new ValidationMessage(ValidationMessageLevel.INFO,
					messageWhenTrue.toString(), startPostion, endPosition);
			messages.add(message);
		}
		if (!inferredFalseTypes.isEmpty()) {
			Map<String, Set<IType>> inferredFalseTypesMap = new HashMap<String, Set<IType>>();
			inferredFalseTypesMap.put(varRef.getVariableName(), inferredFalseTypes);
			validationResult.putInferredVariableTypes(call, Boolean.FALSE, inferredFalseTypesMap);
		} else {
			final AstResult astResult = validationResult.getAstResult();
			final int startPostion = astResult.getStartPosition(call);
			final int endPosition = astResult.getEndPosition(call);
			final ValidationMessage message = new ValidationMessage(ValidationMessageLevel.INFO,
					messageWhenFalse.toString(), startPostion, endPosition);
			validationResult.getMessages().add(message);
		}
	}

	/**
	 * Gets the {@link Set} of {@link EClass} which {@link EClass#getESuperTypes() direct super types}
	 * contains the {@link EClass} corresponding to the given {@link IType}.
	 * 
	 * @param iType
	 *            the {@link IType}
	 * @return the {@link Set} of {@link EClass} which {@link EClass#getESuperTypes() direct super types}
	 *         contains the {@link EClass} corresponding to the given {@link IType}
	 */
	private Set<EClass> getUpperSubTypes(IType iType) {
		final Set<EClass> result = new LinkedHashSet<EClass>();

		if (iType.getType() instanceof EClass) {
			final EClass eCls = (EClass)iType.getType();
			final Set<EClass> subEClasses = services.getQueryEnvironment().getEPackageProvider()
					.getAllSubTypes(eCls);
			for (EClass subEClass : subEClasses) {
				if (subEClass.getESuperTypes().contains(eCls)) {
					result.add(subEClass);
				}
			}
		}

		return result;
	}

	/**
	 * Computes inferred {@link IType} for {@link AstBuilderListener#OR_SERVICE_NAME} {@link Call}.
	 * <ul>
	 * <li>then true {@link AstValidator#unionInferredTypes(Map, Map) union}(true, true)</li>
	 * <li>then false {@link AstValidator#intersectionInferredTypes(Map, Map) intersection}(false, false)</li>
	 * </ul>
	 * 
	 * @param call
	 *            the {@link AstBuilderListener#OR_SERVICE_NAME} {@link Call}
	 * @param leftValidationResult
	 *            the left {@link IValidationResult}
	 * @param rightValidationResult
	 *            the right {@link IValidationResult} without inference from the left operand
	 */
	private void inferOrTypes(Call call, IValidationResult leftValidationResult,
			IValidationResult rightValidationResult) {
		final Expression leftOperand = call.getArguments().get(0);
		final Expression rightOperand = call.getArguments().get(1);
		final Map<String, Set<IType>> inferredLeftVariableTypesWhenTrue = leftValidationResult
				.getInferredVariableTypes(leftOperand, Boolean.TRUE);
		final Map<String, Set<IType>> inferredRightVariableTypesWhenTrue = rightValidationResult
				.getInferredVariableTypes(rightOperand, Boolean.TRUE);
		final Map<String, Set<IType>> inferredLeftVariableTypesWhenFalse = leftValidationResult
				.getInferredVariableTypes(leftOperand, Boolean.FALSE);
		final Map<String, Set<IType>> inferredRightVariableTypesWhenFalse = rightValidationResult
				.getInferredVariableTypes(rightOperand, Boolean.FALSE);
		final Map<String, Set<IType>> orInferredTypesWhenTrue = unionInferredTypes(
				inferredLeftVariableTypesWhenTrue, inferredRightVariableTypesWhenTrue);
		final Map<String, Set<IType>> orInferredTypesWhenFalse = intersectionInferredTypes(
				inferredLeftVariableTypesWhenFalse, inferredRightVariableTypesWhenFalse);
		validationResult.putInferredVariableTypes(call, Boolean.TRUE, orInferredTypesWhenTrue);
		validationResult.putInferredVariableTypes(call, Boolean.FALSE, orInferredTypesWhenFalse);
	}

	/**
	 * Creates the union of inferred types.
	 * 
	 * @param inferredLeftVariableTypes
	 *            first operand
	 * @param inferredRightVariableTypes
	 *            second operand
	 * @return the union of inferred types
	 */
	private Map<String, Set<IType>> unionInferredTypes(Map<String, Set<IType>> inferredLeftVariableTypes,
			Map<String, Set<IType>> inferredRightVariableTypes) {
		final Map<String, Set<IType>> result = new HashMap<String, Set<IType>>();
		final Map<String, Set<IType>> rightLocal = new HashMap<String, Set<IType>>(inferredRightVariableTypes);

		for (Entry<String, Set<IType>> entry : inferredLeftVariableTypes.entrySet()) {
			final Set<IType> inferredTypes = new LinkedHashSet<IType>(entry.getValue());
			final Set<IType> inferredRightTypes = rightLocal.remove(entry.getKey());
			if (inferredRightTypes != null) {
				inferredTypes.addAll(inferredRightTypes);
			}
			result.put(entry.getKey(), inferredTypes);
		}
		result.putAll(rightLocal);

		return result;
	}

	/**
	 * Computes inferred {@link IType} for {@link AstBuilderListener#AND_SERVICE_NAME} {@link Call}.
	 * <ul>
	 * <li>then true {@link AstValidator#intersectionInferredTypes(Map, Map) intersection}(true, true)</li>
	 * <li>then false {@link AstValidator#unionInferredTypes(Map, Map) union}(false, false)</li>
	 * </ul>
	 * 
	 * @param call
	 *            the {@link AstBuilderListener#AND_SERVICE_NAME} {@link Call}
	 * @param leftValidationResult
	 *            the left {@link IValidationResult}
	 * @param rightValidationResult
	 *            the right {@link IValidationResult} without inference from the left operand
	 */
	private void inferAndTypes(Call call, IValidationResult leftValidationResult,
			IValidationResult rightValidationResult) {
		final Expression leftOperand = call.getArguments().get(0);
		final Expression rightOperand = call.getArguments().get(1);
		final Map<String, Set<IType>> inferredLeftVariableTypesWhenTrue = leftValidationResult
				.getInferredVariableTypes(leftOperand, Boolean.TRUE);
		final Map<String, Set<IType>> inferredRightVariableTypesWhenTrue = rightValidationResult
				.getInferredVariableTypes(rightOperand, Boolean.TRUE);
		final Map<String, Set<IType>> inferredLeftVariableTypesWhenFalse = leftValidationResult
				.getInferredVariableTypes(leftOperand, Boolean.FALSE);
		// we use the right
		final Map<String, Set<IType>> inferredRightVariableTypesWhenFalse = rightValidationResult
				.getInferredVariableTypes(rightOperand, Boolean.FALSE);
		final Map<String, Set<IType>> andInferredTypesWhenTrue = intersectionInferredTypes(
				inferredLeftVariableTypesWhenTrue, inferredRightVariableTypesWhenTrue);
		final Map<String, Set<IType>> andInferredTypesWhenFalse = unionInferredTypes(
				inferredLeftVariableTypesWhenFalse, inferredRightVariableTypesWhenFalse);
		validationResult.putInferredVariableTypes(call, Boolean.TRUE, andInferredTypesWhenTrue);
		validationResult.putInferredVariableTypes(call, Boolean.FALSE, andInferredTypesWhenFalse);
	}

	/**
	 * Creates the intersection of inferred types.
	 * 
	 * @param inferredLeftVariableTypes
	 *            first operand
	 * @param inferredRightVariableTypes
	 *            second operand
	 * @return the intersection of inferred types
	 */
	private Map<String, Set<IType>> intersectionInferredTypes(
			final Map<String, Set<IType>> inferredLeftVariableTypes,
			final Map<String, Set<IType>> inferredRightVariableTypes) {
		final Map<String, Set<IType>> result = new HashMap<String, Set<IType>>();

		final Map<String, Set<IType>> rightLocal = new HashMap<String, Set<IType>>(inferredRightVariableTypes);
		for (Entry<String, Set<IType>> entry : inferredLeftVariableTypes.entrySet()) {
			final Set<IType> inferredTypes = new LinkedHashSet<IType>();
			final Set<IType> inferredRightTypes = rightLocal.remove(entry.getKey());
			if (inferredRightTypes != null) {
				for (IType leftType : entry.getValue()) {
					for (IType rightType : inferredRightTypes) {
						inferredTypes.addAll(services.intersection(leftType, rightType));
					}
				}
			} else {
				inferredTypes.addAll(entry.getValue());
			}
			result.put(entry.getKey(), inferredTypes);
		}
		result.putAll(rightLocal);

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseCollectionTypeLiteral(org.eclipse.acceleo.query.ast.CollectionTypeLiteral)
	 */
	@Override
	public Set<IType> caseCollectionTypeLiteral(CollectionTypeLiteral object) {
		final Set<IType> possibleTypes = new LinkedHashSet<IType>();

		for (IType type : doSwitch(object.getElementType())) {
			if (object.getValue() == List.class) {
				possibleTypes.add(new SequenceType(services.getQueryEnvironment(), type));
			} else if (object.getValue() == Set.class) {
				possibleTypes.add(new SetType(services.getQueryEnvironment(), type));
			} else {
				throw new UnsupportedOperationException(SHOULD_NEVER_HAPPEN);
			}
		}

		return checkWarningsAndErrors(object, possibleTypes);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseEnumLiteral(org.eclipse.acceleo.query.ast.EnumLiteral)
	 */
	@Override
	public Set<IType> caseEnumLiteral(EnumLiteral object) {
		final Set<IType> possibleTypes = new LinkedHashSet<IType>();
		possibleTypes
				.add(new EClassifierType(services.getQueryEnvironment(), object.getLiteral().getEEnum()));
		return checkWarningsAndErrors(object, possibleTypes);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseIntegerLiteral(org.eclipse.acceleo.query.ast.IntegerLiteral)
	 */
	@Override
	public Set<IType> caseIntegerLiteral(IntegerLiteral object) {
		final Set<IType> possibleTypes = Sets.newLinkedHashSet();

		possibleTypes.add(new ClassType(services.getQueryEnvironment(), java.lang.Integer.class));

		return checkWarningsAndErrors(object, possibleTypes);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseLambda(org.eclipse.acceleo.query.ast.Lambda)
	 */
	@Override
	public Set<IType> caseLambda(Lambda object) {
		final Set<IType> lambdaExpressionTypes = new LinkedHashSet<IType>();

		final Map<String, Set<IType>> newVariableTypes = new HashMap<String, Set<IType>>(variableTypesStack
				.peek());
		for (VariableDeclaration variableDeclaration : object.getParameters()) {
			final Set<IType> types = doSwitch(variableDeclaration);
			if (newVariableTypes.containsKey(variableDeclaration.getName())) {
				lambdaExpressionTypes.add(services.nothing(VARIABLE_OVERRIDES_AN_EXISTING_VALUE,
						variableDeclaration.getName()));
			}
			newVariableTypes.put(variableDeclaration.getName(), types);
		}

		variableTypesStack.push(newVariableTypes);
		final Set<IType> lambdaExpressionPossibleTypes = doSwitch(object.getExpression());
		final String evaluatorName = object.getParameters().get(0).getName();
		final Set<IType> lambdaEvaluatorPossibleTypes = newVariableTypes.get(evaluatorName);
		for (IType lambdaEvaluatorPossibleType : lambdaEvaluatorPossibleTypes) {
			for (IType lambdaExpressionType : lambdaExpressionPossibleTypes) {
				lambdaExpressionTypes.add(new LambdaType(services.getQueryEnvironment(), evaluatorName,
						lambdaEvaluatorPossibleType, lambdaExpressionType));
			}
		}
		variableTypesStack.pop();

		return lambdaExpressionTypes;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseRealLiteral(org.eclipse.acceleo.query.ast.RealLiteral)
	 */
	@Override
	public Set<IType> caseRealLiteral(RealLiteral object) {
		final Set<IType> possibleTypes = Sets.newLinkedHashSet();

		possibleTypes.add(new ClassType(services.getQueryEnvironment(), java.lang.Double.class));

		return checkWarningsAndErrors(object, possibleTypes);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseStringLiteral(org.eclipse.acceleo.query.ast.StringLiteral)
	 */
	@Override
	public Set<IType> caseStringLiteral(StringLiteral object) {
		final Set<IType> possibleTypes = Sets.newLinkedHashSet();

		possibleTypes.add(new ClassType(services.getQueryEnvironment(), java.lang.String.class));

		return checkWarningsAndErrors(object, possibleTypes);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseVarRef(org.eclipse.acceleo.query.ast.VarRef)
	 */
	@Override
	public Set<IType> caseVarRef(VarRef object) {
		final Set<IType> variableTypes = services.getVariableTypes(variableTypesStack.peek(), object
				.getVariableName());
		return checkWarningsAndErrors(object, variableTypes);
	}

	/**
	 * Validates the given {@link AstResult}.
	 * 
	 * @param variableTypes
	 *            the set of defined variables.
	 * @param astResult
	 *            the {@link AstResult}
	 * @return the {@link IValidationResult}
	 */
	public IValidationResult validate(Map<String, Set<IType>> variableTypes, AstResult astResult) {
		validationResult = new ValidationResult(astResult);

		this.variableTypesStack.push(variableTypes);
		doSwitch(astResult.getAst());
		this.variableTypesStack.pop();
		validationResult.getMessages().addAll(messages);
		messages = new LinkedHashSet<IValidationMessage>();

		return validationResult;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseTypeLiteral(org.eclipse.acceleo.query.ast.TypeLiteral)
	 */
	@Override
	public Set<IType> caseTypeLiteral(TypeLiteral object) {
		final Set<IType> possibleTypes;

		if (object.getValue() instanceof EClassifier) {
			possibleTypes = new LinkedHashSet<IType>();
			possibleTypes.add(new EClassifierLiteralType(services.getQueryEnvironment(), (EClassifier)object
					.getValue()));
		} else if (object.getValue() instanceof Class<?>) {
			possibleTypes = Sets.newLinkedHashSet();
			possibleTypes.add(new ClassType(services.getQueryEnvironment(), (Class<?>)object.getValue()));
		} else {
			throw new UnsupportedOperationException(SHOULD_NEVER_HAPPEN);
		}

		return checkWarningsAndErrors(object, possibleTypes);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseTypeSetLiteral(org.eclipse.acceleo.query.ast.TypeSetLiteral)
	 */
	@Override
	public Set<IType> caseTypeSetLiteral(TypeSetLiteral object) {
		final Set<IType> possibleTypes = new LinkedHashSet<IType>();
		final Set<EClassifier> types = new LinkedHashSet<EClassifier>();
		final EClassifierSetLiteralType possibleType = new EClassifierSetLiteralType(services
				.getQueryEnvironment(), types);
		possibleTypes.add(possibleType);

		for (TypeLiteral type : object.getTypes()) {
			if (!types.add((EClassifier)type.getValue())) {
				possibleTypes.add(services.nothing(
						"EClassifierLiteral=%s is duplicated in the type set literal.", ((EClassifier)type
								.getValue()).getName()));
			}
		}

		return checkWarningsAndErrors(object, possibleTypes);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorCall(org.eclipse.acceleo.query.ast.ErrorCall)
	 */
	@Override
	public Set<IType> caseErrorCall(ErrorCall object) {
		for (Expression arg : object.getArguments()) {
			doSwitch(arg);
		}
		return checkWarningsAndErrors(object, services.getErrorTypes(validationResult, object));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorExpression(org.eclipse.acceleo.query.ast.ErrorExpression)
	 */
	@Override
	public Set<IType> caseErrorExpression(ErrorExpression object) {
		return checkWarningsAndErrors(object, services.getErrorTypes(validationResult, object));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorTypeLiteral(org.eclipse.acceleo.query.ast.ErrorTypeLiteral)
	 */
	@Override
	public Set<IType> caseErrorTypeLiteral(ErrorTypeLiteral object) {
		return checkWarningsAndErrors(object, services.getErrorTypes(validationResult, object));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorEnumLiteral(org.eclipse.acceleo.query.ast.ErrorEnumLiteral)
	 */
	@Override
	public Set<IType> caseErrorEnumLiteral(ErrorEnumLiteral object) {
		return checkWarningsAndErrors(object, services.getErrorTypes(validationResult, object));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseNullLiteral(org.eclipse.acceleo.query.ast.NullLiteral)
	 */
	@Override
	public Set<IType> caseNullLiteral(NullLiteral object) {
		final Set<IType> possibleTypes = new LinkedHashSet<IType>();

		possibleTypes.add(new ClassType(services.getQueryEnvironment(), null));

		return checkWarningsAndErrors(object, possibleTypes);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseSetInExtensionLiteral(org.eclipse.acceleo.query.ast.SetInExtensionLiteral)
	 */
	@Override
	public Set<IType> caseSetInExtensionLiteral(SetInExtensionLiteral object) {
		final Set<IType> possibleTypes = new LinkedHashSet<IType>();

		if (!object.getValues().isEmpty()) {
			for (Expression expression : object.getValues()) {
				for (IType type : doSwitch(expression)) {
					possibleTypes.add(new SetType(services.getQueryEnvironment(), type));
				}
			}
		} else {
			possibleTypes.add(new SetType(services.getQueryEnvironment(), services
					.nothing("Empty OrderedSet defined in extension")));
		}

		return checkWarningsAndErrors(object, possibleTypes);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseSequenceInExtensionLiteral(org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral)
	 */
	@Override
	public Set<IType> caseSequenceInExtensionLiteral(SequenceInExtensionLiteral object) {
		final Set<IType> possibleTypes = new LinkedHashSet<IType>();

		if (!object.getValues().isEmpty()) {
			for (Expression expression : object.getValues()) {
				for (IType type : doSwitch(expression)) {
					possibleTypes.add(new SequenceType(services.getQueryEnvironment(), type));
				}
			}
		} else {
			possibleTypes.add(new SequenceType(services.getQueryEnvironment(), services
					.nothing("Empty Sequence defined in extension")));
		}

		return checkWarningsAndErrors(object, possibleTypes);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseVariableDeclaration(org.eclipse.acceleo.query.ast.VariableDeclaration)
	 */
	@Override
	public Set<IType> caseVariableDeclaration(VariableDeclaration variableDeclaration) {
		final Set<IType> result = new LinkedHashSet<IType>();

		final Set<IType> expressionTypes = validationResult.getPossibleTypes(variableDeclaration
				.getExpression());
		int nbNullType = 0;
		for (IType type : expressionTypes) {
			if (type instanceof ICollectionType) {
				result.add(((ICollectionType)type).getCollectionType());
			} else {
				if (type.getType() == null) {
					nbNullType++;
				}
				result.add(type);
			}
		}

		final boolean allNullType = result.size() == nbNullType;
		if (variableDeclaration.getType() != null) {
			final Set<IType> declaredTypes = getDeclarationTypes(services.getQueryEnvironment(),
					doSwitch(variableDeclaration.getType()));
			if (!(variableDeclaration.getType() instanceof ErrorTypeLiteral)) {
				final List<IType> incompatibleTypes = new ArrayList<IType>();
				for (IType expressionType : result) {
					boolean compatible = false;
					for (IType declaredType : declaredTypes) {
						if (declaredType.isAssignableFrom(expressionType)) {
							compatible = true;
							break;
						}
					}
					if (!compatible) {
						incompatibleTypes.add(expressionType);
					}
				}

				if (!incompatibleTypes.isEmpty()) {
					for (IType incompatibleType : incompatibleTypes) {
						result.add(services.nothing("%s is incompatible with declaration %s.",
								incompatibleType, declaredTypes));
					}
				}
				if (allNullType) {
					result.clear();
					result.addAll(declaredTypes);
				}
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseConditional(org.eclipse.acceleo.query.ast.Conditional)
	 */
	@Override
	public Set<IType> caseConditional(Conditional object) {
		Set<IType> result = Sets.newLinkedHashSet();

		final Set<IType> selectorTypes;
		if (object.getPredicate() != null) {
			selectorTypes = doSwitch(object.getPredicate());
		} else {
			selectorTypes = Collections.emptySet();
		}
		final Map<String, Set<IType>> trueBranchInferredTypes = new HashMap<String, Set<IType>>(
				variableTypesStack.peek());
		trueBranchInferredTypes.putAll(validationResult.getInferredVariableTypes(object.getPredicate(),
				Boolean.TRUE));
		variableTypesStack.push(trueBranchInferredTypes);
		final Set<IType> trueTypes = new LinkedHashSet<IType>();
		try {
			if (object.getTrueBranch() != null) {
				trueTypes.addAll(doSwitch(object.getTrueBranch()));
			}
		} finally {
			variableTypesStack.pop();
		}
		final Map<String, Set<IType>> falseBranchInferredTypes = new HashMap<String, Set<IType>>(
				variableTypesStack.peek());
		falseBranchInferredTypes.putAll(validationResult.getInferredVariableTypes(object.getPredicate(),
				Boolean.FALSE));
		variableTypesStack.push(falseBranchInferredTypes);
		final Set<IType> falseTypes = new LinkedHashSet<IType>();
		try {
			if (object.getFalseBranch() != null) {
				falseTypes.addAll(doSwitch(object.getFalseBranch()));
			}
		} finally {
			variableTypesStack.pop();
		}
		if (!selectorTypes.isEmpty()) {
			boolean onlyBoolean = true;
			boolean onlyNotBoolean = true;
			final IType booleanObjectType = new ClassType(services.getQueryEnvironment(), Boolean.class);
			final IType booleanType = new ClassType(services.getQueryEnvironment(), boolean.class);
			for (IType type : selectorTypes) {
				final boolean assignableFrom = booleanObjectType.isAssignableFrom(type)
						|| booleanType.isAssignableFrom(type);
				onlyBoolean = onlyBoolean && assignableFrom;
				onlyNotBoolean = onlyNotBoolean && !assignableFrom;
				if (!onlyBoolean && !onlyNotBoolean) {
					break;
				}
			}
			if (onlyBoolean) {
				result.addAll(trueTypes);
				result.addAll(falseTypes);
			} else if (onlyNotBoolean) {
				result.add(services.nothing("The predicate never evaluates to a boolean type (%s).",
						selectorTypes));
			} else {
				result.add(services.nothing(
						"The predicate may evaluate to a value that is not a boolean type (%s).",
						selectorTypes));
				result.addAll(trueTypes);
				result.addAll(falseTypes);
			}
		} else {
			result.add(services.nothing("The predicate never evaluates to a boolean type (%s).",
					selectorTypes));
		}

		return checkWarningsAndErrors(object, result);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseLet(org.eclipse.acceleo.query.ast.Let)
	 */
	@Override
	public Set<IType> caseLet(Let object) {
		Set<IType> result = Sets.newLinkedHashSet();

		final Map<String, Set<IType>> newVariableTypes = new HashMap<String, Set<IType>>(variableTypesStack
				.peek());
		for (Binding binding : object.getBindings()) {
			final Set<IType> bindingTypes = doSwitch(binding);
			if (binding.getName() != null) {
				if (newVariableTypes.containsKey(binding.getName())) {
					result.add(services.nothing(VARIABLE_OVERRIDES_AN_EXISTING_VALUE, binding.getName()));
				}
				newVariableTypes.put(binding.getName(), bindingTypes);
			}
		}

		variableTypesStack.push(newVariableTypes);
		try {
			final Set<IType> bodyTypes = doSwitch(object.getBody());
			result.addAll(bodyTypes);
		} finally {
			variableTypesStack.pop();
		}

		return checkWarningsAndErrors(object, result);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseBinding(org.eclipse.acceleo.query.ast.Binding)
	 */
	@Override
	public Set<IType> caseBinding(Binding binding) {
		final Set<IType> expressionTypes = doSwitch(binding.getValue());

		if (binding.getType() != null) {
			final Set<IType> declaredTypes = getDeclarationTypes(services.getQueryEnvironment(),
					doSwitch(binding.getType()));
			if (!(binding.getType() instanceof ErrorTypeLiteral)) {
				final List<IType> incompatibleTypes = new ArrayList<IType>();
				for (IType expressionType : expressionTypes) {
					boolean compatible = false;
					for (IType declaredType : declaredTypes) {
						if (declaredType.isAssignableFrom(expressionType)) {
							compatible = true;
							break;
						}
					}
					if (!compatible) {
						incompatibleTypes.add(expressionType);
					}
				}

				if (!incompatibleTypes.isEmpty()) {
					for (IType incompatibleType : incompatibleTypes) {
						expressionTypes.add(services.nothing("%s is incompatible with declaration %s.",
								incompatibleType, declaredTypes));
					}
				}
			}
		}

		return expressionTypes;
	}

	/**
	 * Gets the {@link Set} declaration types from the given {@link Set} of {@link IType}.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param types
	 *            the {@link Set} of {@link IType}
	 * @return the {@link Set} declaration types from the given {@link Set} of {@link IType}
	 */
	public Set<IType> getDeclarationTypes(IReadOnlyQueryEnvironment queryEnvironment, final Set<IType> types) {
		final Set<IType> res = new LinkedHashSet<IType>();

		for (IType iType : types) {
			if (iType instanceof EClassifierLiteralType) {
				res.add(new EClassifierType(queryEnvironment, ((EClassifierLiteralType)iType).getType()));
			} else if (iType instanceof EClassifierSetLiteralType) {
				for (EClassifier eClassifier : ((EClassifierSetLiteralType)iType).getEClassifiers()) {
					res.add(new EClassifierType(queryEnvironment, eClassifier));
				}
			} else {
				res.add(iType);
			}
		}

		return res;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorBinding(org.eclipse.acceleo.query.ast.ErrorBinding)
	 */
	@Override
	public Set<IType> caseErrorBinding(ErrorBinding object) {
		final Set<IType> result;

		if (object.getValue() != null) {
			result = doSwitch(object.getValue());
		} else {
			result = Collections.emptySet();
		}

		return result;
	}

}
