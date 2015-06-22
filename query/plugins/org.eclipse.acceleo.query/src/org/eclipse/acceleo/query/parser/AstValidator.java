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
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.eclipse.acceleo.query.ast.BooleanLiteral;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.CollectionTypeLiteral;
import org.eclipse.acceleo.query.ast.Conditional;
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.Error;
import org.eclipse.acceleo.query.ast.ErrorCollectionCall;
import org.eclipse.acceleo.query.ast.ErrorExpression;
import org.eclipse.acceleo.query.ast.ErrorFeatureAccessOrCall;
import org.eclipse.acceleo.query.ast.ErrorTypeLiteral;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.FeatureAccess;
import org.eclipse.acceleo.query.ast.IntegerLiteral;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.NullLiteral;
import org.eclipse.acceleo.query.ast.RealLiteral;
import org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral;
import org.eclipse.acceleo.query.ast.SetInExtensionLiteral;
import org.eclipse.acceleo.query.ast.StringLiteral;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.ast.util.AstSwitch;
import org.eclipse.acceleo.query.runtime.IQueryBuilderEngine.AstResult;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IValidationResult;
import org.eclipse.acceleo.query.runtime.ValidationMessageLevel;
import org.eclipse.acceleo.query.runtime.impl.ValidationMessage;
import org.eclipse.acceleo.query.runtime.impl.ValidationResult;
import org.eclipse.acceleo.query.runtime.impl.ValidationServices;
import org.eclipse.acceleo.query.validation.type.ClassType;
import org.eclipse.acceleo.query.validation.type.EClassifierLiteralType;
import org.eclipse.acceleo.query.validation.type.EClassifierType;
import org.eclipse.acceleo.query.validation.type.ICollectionType;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.acceleo.query.validation.type.LambdaType;
import org.eclipse.acceleo.query.validation.type.NothingType;
import org.eclipse.acceleo.query.validation.type.SequenceType;
import org.eclipse.acceleo.query.validation.type.SetType;
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
	private final Stack<Map<String, Set<IType>>> variableTypesStack = new Stack<Map<String, Set<IType>>>();

	/**
	 * Constructor.
	 * 
	 * @param environment
	 *            the {@link IQueryEnvironment} used to validate
	 * @param variableTypes
	 *            the set of defined variables.
	 */
	public AstValidator(IQueryEnvironment environment, Map<String, Set<IType>> variableTypes) {
		this.variableTypesStack.push(variableTypes);
		this.services = new ValidationServices(environment);
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
		final List<ValidationMessage> msgs = new ArrayList<ValidationMessage>();
		for (IType type : types) {
			if (type instanceof NothingType) {
				final AstResult astResult = validationResult.getAstResult();
				final int startPostion;
				if (expression instanceof Call) {
					startPostion = astResult.getEndPosition(((Call)expression).getArguments().get(0));
				} else if (expression instanceof FeatureAccess) {
					startPostion = astResult.getEndPosition(((FeatureAccess)expression).getTarget());
				} else {
					startPostion = astResult.getStartPosition(expression);
				}
				final int endPosition = astResult.getEndPosition(expression);

				msgs.add(new ValidationMessage(ValidationMessageLevel.WARNING, ((NothingType)type)
						.getMessage(), startPostion, endPosition));
			} else {
				result.add(type);
			}
		}

		if (result.size() == 0) {
			for (ValidationMessage message : msgs) {
				message.setLevel(ValidationMessageLevel.ERROR);
			}
		}

		validationResult.getMessages().addAll(msgs);
		validationResult.addTypes(expression, result);

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseBooleanLiteral(org.eclipse.acceleo.query.ast.BooleanLiteral)
	 */
	@Override
	public Set<IType> caseBooleanLiteral(BooleanLiteral object) {
		final Set<IType> possibleTypes = services.getIType(java.lang.Boolean.class);
		return checkWarningsAndErrors(object, possibleTypes);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseCall(org.eclipse.acceleo.query.ast.Call)
	 */
	@Override
	public Set<IType> caseCall(Call object) {
		final Set<IType> possibleTypes;

		final List<Set<IType>> argTypes = new ArrayList<Set<IType>>();
		for (Expression arg : object.getArguments()) {
			argTypes.add(doSwitch(arg));
		}

		final String serviceName = object.getServiceName();
		switch (object.getType()) {
			case CALLSERVICE:
				possibleTypes = services.callType(serviceName, argTypes);
				break;
			case CALLORAPPLY:
				possibleTypes = services.callOrApplyTypes(serviceName, argTypes);
				break;
			case COLLECTIONCALL:
				possibleTypes = services.collectionServiceCallTypes(serviceName, argTypes);
				break;
			default:
				throw new UnsupportedOperationException(SHOULD_NEVER_HAPPEN);
		}

		return checkWarningsAndErrors(object, possibleTypes);
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
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseFeatureAccess(org.eclipse.acceleo.query.ast.FeatureAccess)
	 */
	@Override
	public Set<IType> caseFeatureAccess(FeatureAccess object) {
		final Set<IType> reveiverTypes = doSwitch(object.getTarget());
		final String featureName = object.getFeatureName();
		final Set<IType> flattened = services.featureAccessTypes(reveiverTypes, featureName);
		return checkWarningsAndErrors(object, flattened);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseIntegerLiteral(org.eclipse.acceleo.query.ast.IntegerLiteral)
	 */
	@Override
	public Set<IType> caseIntegerLiteral(IntegerLiteral object) {
		final Set<IType> possibleTypes = services.getIType(java.lang.Integer.class);
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
			final Set<IType> types;
			if (variableDeclaration.getType() == null || variableDeclaration.getType() instanceof Error) {
				final Set<IType> variableTypes = doSwitch(variableDeclaration.getExpression());
				types = new LinkedHashSet<IType>();
				for (IType type : variableTypes) {
					if (type instanceof ICollectionType) {
						types.add(((ICollectionType)type).getCollectionType());
					} else {
						types.add(type);
					}
				}
			} else {
				types = doSwitch(variableDeclaration.getType());
			}
			if (newVariableTypes.containsKey(variableDeclaration.getName())) {
				lambdaExpressionTypes.add(services.nothing(VARIABLE_OVERRIDES_AN_EXISTING_VALUE,
						variableDeclaration.getName()));
			}
			newVariableTypes.put(variableDeclaration.getName(), types);
		}

		variableTypesStack.push(newVariableTypes);
		final Set<IType> lambdaExpressionPossibleTypes = doSwitch(object.getExpression());
		final Set<IType> lambdaEvaluatorPossibleTypes = newVariableTypes.get(object.getParameters().get(0)
				.getName());
		for (IType lambdaEvaluatorPossibleType : lambdaEvaluatorPossibleTypes) {
			for (IType lambdaExpressionType : lambdaExpressionPossibleTypes) {
				lambdaExpressionTypes.add(new LambdaType(services.getQueryEnvironment(),
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
		final Set<IType> possibleTypes = services.getIType(java.lang.Double.class);
		return checkWarningsAndErrors(object, possibleTypes);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseStringLiteral(org.eclipse.acceleo.query.ast.StringLiteral)
	 */
	@Override
	public Set<IType> caseStringLiteral(StringLiteral object) {
		final Set<IType> possibleTypes = services.getIType(java.lang.String.class);
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
	 * @param astResult
	 *            the {@link AstResult}
	 * @return the {@link IValidationResult}
	 */
	public IValidationResult validate(AstResult astResult) {
		validationResult = new ValidationResult(astResult);

		doSwitch(astResult.getAst());

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
			possibleTypes = services.getIType((Class<?>)object.getValue());
		} else {
			throw new UnsupportedOperationException(SHOULD_NEVER_HAPPEN);
		}

		return checkWarningsAndErrors(object, possibleTypes);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorCollectionCall(org.eclipse.acceleo.query.ast.ErrorCollectionCall)
	 */
	@Override
	public Set<IType> caseErrorCollectionCall(ErrorCollectionCall object) {
		doSwitch(object.getTarget());
		return checkWarningsAndErrors(object, services.getErrorTypes(object));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorExpression(org.eclipse.acceleo.query.ast.ErrorExpression)
	 */
	@Override
	public Set<IType> caseErrorExpression(ErrorExpression object) {
		return checkWarningsAndErrors(object, services.getErrorTypes(object));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorFeatureAccessOrCall(org.eclipse.acceleo.query.ast.ErrorFeatureAccessOrCall)
	 */
	@Override
	public Set<IType> caseErrorFeatureAccessOrCall(ErrorFeatureAccessOrCall object) {
		doSwitch(object.getTarget());
		return checkWarningsAndErrors(object, services.getErrorTypes(object));
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseErrorTypeLiteral(org.eclipse.acceleo.query.ast.ErrorTypeLiteral)
	 */
	@Override
	public Set<IType> caseErrorTypeLiteral(ErrorTypeLiteral object) {
		return checkWarningsAndErrors(object, services.getErrorTypes(object));
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

		for (Expression expression : object.getValues()) {
			for (IType type : doSwitch(expression)) {
				possibleTypes.add(new SetType(services.getQueryEnvironment(), type));
			}
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

		for (Expression expression : object.getValues()) {
			for (IType type : doSwitch(expression)) {
				possibleTypes.add(new SequenceType(services.getQueryEnvironment(), type));
			}
		}

		return checkWarningsAndErrors(object, possibleTypes);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseVariableDeclaration(org.eclipse.acceleo.query.ast.VariableDeclaration)
	 */
	@Override
	public Set<IType> caseVariableDeclaration(VariableDeclaration object) {
		doSwitch(object.getExpression());
		return null;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.ast.util.AstSwitch#caseConditional(org.eclipse.acceleo.query.ast.Conditional)
	 */
	@Override
	public Set<IType> caseConditional(Conditional object) {
		Set<IType> result = Sets.newLinkedHashSet();

		final Set<IType> trueTypes = doSwitch(object.getTrueBranch());
		final Set<IType> falseTypes = doSwitch(object.getFalseBranch());
		Set<IType> selectorTypes = doSwitch(object.getPredicate());
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

}
