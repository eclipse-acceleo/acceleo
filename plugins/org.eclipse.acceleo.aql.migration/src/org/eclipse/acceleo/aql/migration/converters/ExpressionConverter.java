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
package org.eclipse.acceleo.aql.migration.converters;

import org.eclipse.acceleo.AcceleoFactory;
import org.eclipse.acceleo.ExpressionStatement;
import org.eclipse.acceleo.Statement;
import org.eclipse.acceleo.aql.migration.MigrationException;
import org.eclipse.acceleo.aql.migration.converters.utils.OperationUtils;
import org.eclipse.acceleo.aql.migration.converters.utils.TypeUtils;
import org.eclipse.acceleo.model.mtl.MtlPackage;
import org.eclipse.acceleo.model.mtl.Query;
import org.eclipse.acceleo.model.mtl.QueryInvocation;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.TemplateInvocation;
import org.eclipse.acceleo.query.ast.AstFactory;
import org.eclipse.acceleo.query.ast.BooleanLiteral;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.acceleo.query.ast.IntegerLiteral;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.RealLiteral;
import org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral;
import org.eclipse.acceleo.query.ast.SetInExtensionLiteral;
import org.eclipse.acceleo.query.ast.StringLiteral;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.parser.AstBuilderListener;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.ocl.ecore.BooleanLiteralExp;
import org.eclipse.ocl.ecore.CollectionItem;
import org.eclipse.ocl.ecore.CollectionLiteralExp;
import org.eclipse.ocl.ecore.CollectionType;
import org.eclipse.ocl.ecore.EcorePackage;
import org.eclipse.ocl.ecore.EnumLiteralExp;
import org.eclipse.ocl.ecore.IntegerLiteralExp;
import org.eclipse.ocl.ecore.IteratorExp;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.ecore.OperationCallExp;
import org.eclipse.ocl.ecore.OrderedSetType;
import org.eclipse.ocl.ecore.PropertyCallExp;
import org.eclipse.ocl.ecore.RealLiteralExp;
import org.eclipse.ocl.ecore.SetType;
import org.eclipse.ocl.ecore.StringLiteralExp;
import org.eclipse.ocl.ecore.TypeExp;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.ocl.ecore.VariableExp;

/**
 * A converter dedicated to OCLExpressions.
 * 
 * @author <a href="mailto:william.piers@obeo.fr">William Piers</a>
 */
public final class ExpressionConverter extends AbstractConverter {

	/**
	 * The self variable.
	 */
	private static final String SELF_VARIABLE_NAME = "self";

	/**
	 * The flag stating whether we should resolve the self variable.
	 */
	private boolean resolveSelf = true;

	/**
	 * Converts the given {@link OCLExpression} to a {@link Statement}.
	 * 
	 * @param input
	 *            the expression to convert
	 * @return the statement
	 */
	public Statement convertToStatement(OCLExpression input) {
		Statement output = AcceleoFactory.eINSTANCE.createExpressionStatement();
		((ExpressionStatement)output).setExpression(convertToExpression(input, false));
		return output;
	}

	/**
	 * Converts the given {@link OCLExpression} to an {@link Expression}.
	 * 
	 * @param inputExpression
	 *            the expression to convert
	 * @param allowSelf
	 *            if <true>, won't resolve self for this expression
	 * @return the Acceleo 4 expression
	 */
	public org.eclipse.acceleo.Expression convertToExpression(OCLExpression inputExpression,
			boolean allowSelf) {
		org.eclipse.acceleo.Expression outputExpression = AcceleoFactory.eINSTANCE.createExpression();
		if (allowSelf) {
			this.resolveSelf = false;
		}
		outputExpression.setAst(createAstResult((Expression)convert(inputExpression)));
		if (allowSelf) {
			this.resolveSelf = true;
		}
		return outputExpression;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.aql.migration.converters.AbstractConverter#convert(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected Object convert(EObject input) {
		Object output = null;
		switch (input.eClass().getClassifierID()) {
			case EcorePackage.VARIABLE:
				output = caseVariable((Variable)input);
				break;
			case EcorePackage.PROPERTY_CALL_EXP:
				output = casePropertyCallExp((PropertyCallExp)input);
				break;
			case EcorePackage.VARIABLE_EXP:
				output = caseVariableExp((VariableExp)input);
				break;
			case MtlPackage.TEMPLATE_INVOCATION:
				output = caseTemplateInvocation((TemplateInvocation)input);
				break;
			case MtlPackage.QUERY_INVOCATION:
				output = caseQueryInvocation((QueryInvocation)input);
				break;
			case EcorePackage.ITERATOR_EXP:
				output = caseIteratorExp((IteratorExp)input);
				break;
			case EcorePackage.TYPE_EXP:
				output = TypeUtils.createTypeLiteral(((TypeExp)input).getReferredType());
				break;
			case EcorePackage.OPERATION_CALL_EXP:
				output = caseOperationCallExp((OperationCallExp)input);
				break;
			case EcorePackage.STRING_LITERAL_EXP:
				output = AstFactory.eINSTANCE.createStringLiteral();
				((StringLiteral)output).setValue(((StringLiteralExp)input).getStringSymbol());
				break;
			case EcorePackage.INTEGER_LITERAL_EXP:
				output = AstFactory.eINSTANCE.createIntegerLiteral();
				((IntegerLiteral)output).setValue(((IntegerLiteralExp)input).getIntegerSymbol());
				break;
			case EcorePackage.BOOLEAN_LITERAL_EXP:
				output = AstFactory.eINSTANCE.createBooleanLiteral();
				((BooleanLiteral)output).setValue(((BooleanLiteralExp)input).getBooleanSymbol());
				break;
			case EcorePackage.REAL_LITERAL_EXP:
				output = AstFactory.eINSTANCE.createRealLiteral();
				((RealLiteral)output).setValue(((RealLiteralExp)input).getRealSymbol());
				break;
			case EcorePackage.ENUM_LITERAL_EXP:
				output = AstFactory.eINSTANCE.createEnumLiteral();
				((EnumLiteral)output).setLiteral(((EnumLiteralExp)input).getReferredEnumLiteral());
				break;
			case EcorePackage.NULL_LITERAL_EXP:
				output = AstFactory.eINSTANCE.createNullLiteral();
				break;
			case EcorePackage.COLLECTION_LITERAL_EXP:
				output = convertCollectionLiteralExp((CollectionLiteralExp)input);
				break;
			case EcorePackage.COLLECTION_ITEM:
				output = convert(((CollectionItem)input).getItem());
				break;
			// case EcorePackage.COLLECTION_RANGE:
			// output = convert(((CollectionItem)input).getItem());
			// break;
			default:
				throw new MigrationException(input);
		}
		return output;
	}

	private Object caseVariable(Variable input) {
		VariableDeclaration output = AstFactory.eINSTANCE.createVariableDeclaration();
		output.setName(input.getName());
		if (output.getExpression() != null) {
			output.setExpression((Expression)convert(input.getInitExpression()));
		}
		return output;
	}

	private Expression caseVariableExp(VariableExp input) {
		Expression output = null;
		// if (input.getReferredVariable().eContainer() instanceof IteratorExp) {
		// IteratorExp iterator = (IteratorExp)input.getReferredVariable().eContainer();
		// output = (Expression)convert(iterator.getSource());
		// } else {
		output = AstFactory.eINSTANCE.createVarRef();
		String variableName = input.getReferredVariable().getName();
		if (resolveSelf && SELF_VARIABLE_NAME.equals(variableName)) {
			Variable variable = findVariable(input);
			variableName = variable.getName();
		}
		((VarRef)output).setVariableName(variableName);
		// }
		return output;
	}

	private Expression casePropertyCallExp(PropertyCallExp input) {
		Call output = AstFactory.eINSTANCE.createCall();
		output.setServiceName(AstBuilderListener.FEATURE_ACCESS_SERVICE_NAME);
		if (input.getSource().getType() instanceof CollectionType) {
			output.setType(CallType.COLLECTIONCALL);
		} else {
			output.setType(CallType.CALLORAPPLY);
		}
		output.getArguments().add((Expression)convert(input.getSource()));
		StringLiteral propertyName = AstFactory.eINSTANCE.createStringLiteral();
		propertyName.setValue(input.getReferredProperty().getName());
		output.getArguments().add(propertyName);
		return output;
	}

	private Expression caseOperationCallExp(OperationCallExp input) {
		final Expression res;

		if ("oclIsUndefined".equals(input.getReferredOperation().getName())) {
			Call output = AstFactory.eINSTANCE.createCall();
			output.setServiceName(AstBuilderListener.EQUALS_SERVICE_NAME);
			output.setType(CallType.CALLSERVICE);
			output.getArguments().add((Expression)convert(input.getSource()));
			output.getArguments().add(AstFactory.eINSTANCE.createNullLiteral());
			res = output;
		} else if (isInvokeCall(input)) {
			Call output = OperationUtils.createCall(input);
			output.setType(CallType.CALLSERVICE);
			final String serviceSignature = ((org.eclipse.ocl.expressions.StringLiteralExp<EClassifier>)input
					.getArgument().get(1)).getStringSymbol();
			final String serviceName = serviceSignature.substring(0, serviceSignature.indexOf("("));
			output.setServiceName(serviceName);
			map(((CollectionLiteralExp)input.getArgument().get(2)).getPart(), output.getArguments());
			res = output;
		} else {
			Call output = OperationUtils.createCall(input);
			output.getArguments().add((Expression)convert(input.getSource()));
			map(input.getArgument(), output.getArguments());
			res = output;
		}

		return res;
	}

	/**
	 * Tells if the given {@link OperationCallExp} is an invoke() call.
	 * 
	 * @param input
	 *            the {@link OperationCallExp}
	 * @return <code>true</code> if the given OperationCallExp is an invoke() call, <code>false</code>
	 *         otherwise
	 */
	public boolean isInvokeCall(OperationCallExp input) {
		final EOperation referredOperation = input.getReferredOperation();
		return referredOperation != null && "invoke".equals(referredOperation.getName()) && referredOperation
				.eContainer() instanceof EClass && "oclstdlib_OclAny_Class".equals(((EClass)referredOperation
						.eContainer()).getName());
	}

	private Expression caseIteratorExp(IteratorExp input) {
		if ("collect".equals(input.getName()) && (input.getSource().getType() instanceof OrderedSetType
				|| input.getSource().getType() instanceof SetType)) {
			// we add a asSequence before to match A3 behavior
			Call asSequence = AstFactory.eINSTANCE.createCall();
			asSequence.setServiceName("asSequence");
			asSequence.setType(CallType.COLLECTIONCALL);
			asSequence.getArguments().add((Expression)convert(input.getSource()));
			return convertIterator(input, asSequence);
		} else {
			return convertIterator(input, (Expression)convert(input.getSource()));
		}
	}

	private Expression convertIterator(IteratorExp input, Expression source) {
		Call output = AstFactory.eINSTANCE.createCall();
		if (input.getSource().getType() instanceof CollectionType) {
			output.setType(CallType.COLLECTIONCALL);
		} else {
			output.setType(CallType.CALLORAPPLY);
		}
		output.setServiceName(input.getName());
		output.getArguments().add(source);

		Lambda lambda = AstFactory.eINSTANCE.createLambda();
		map(input.getIterator(), lambda.getParameters());
		lambda.setExpression((Expression)convert(input.getBody()));
		output.getArguments().add(lambda);
		return output;
	}

	private Expression caseTemplateInvocation(TemplateInvocation input) {
		Call output = AstFactory.eINSTANCE.createCall();
		output.setType(CallType.CALLORAPPLY);
		output.setServiceName(input.getDefinition().getName());
		map(input.getArgument(), output.getArguments());
		return output;
	}

	private Expression caseQueryInvocation(QueryInvocation input) {
		Call output = AstFactory.eINSTANCE.createCall();
		output.setType(CallType.CALLORAPPLY);
		output.setServiceName(input.getDefinition().getName());
		map(input.getArgument(), output.getArguments());
		return output;
	}

	private Expression convertCollectionLiteralExp(CollectionLiteralExp input) {
		Expression output = null;
		EClassifier type = input.getEType();
		switch (type.eClass().getClassifierID()) {
			case EcorePackage.SEQUENCE_TYPE:
				output = AstFactory.eINSTANCE.createSequenceInExtensionLiteral();
				map(input.getPart(), ((SequenceInExtensionLiteral)output).getValues());
				break;
			case EcorePackage.BAG_TYPE:
				output = AstFactory.eINSTANCE.createSequenceInExtensionLiteral();
				map(input.getPart(), ((SequenceInExtensionLiteral)output).getValues());
				break;
			case EcorePackage.SET_TYPE:
				output = AstFactory.eINSTANCE.createSetInExtensionLiteral();
				map(input.getPart(), ((SetInExtensionLiteral)output).getValues());
				break;
			case EcorePackage.ORDERED_SET_TYPE:
				output = AstFactory.eINSTANCE.createSetInExtensionLiteral();
				map(input.getPart(), ((SetInExtensionLiteral)output).getValues());
				break;
			default:
				throw new MigrationException(type);
		}
		return output;
	}

	// TODO use accurate rules here
	private static Variable findVariable(EObject context) {
		Variable variable = null;
		EObject validParent = context.eContainer();
		while (validParent != null && !(validParent instanceof Template || validParent instanceof Query)) {
			validParent = validParent.eContainer();
		}
		if (validParent instanceof Template) {
			variable = ((Template)validParent).getParameter().get(0);
		} else if (validParent instanceof Query) {
			variable = ((Query)validParent).getParameter().get(0);
		}
		return variable;
	}

}
