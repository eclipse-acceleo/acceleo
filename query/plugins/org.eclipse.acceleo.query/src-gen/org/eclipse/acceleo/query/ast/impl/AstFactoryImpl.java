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
package org.eclipse.acceleo.query.ast.impl;

import org.eclipse.acceleo.query.ast.AstFactory;
import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.Binding;
import org.eclipse.acceleo.query.ast.BooleanLiteral;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.ast.CollectionTypeLiteral;
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorCollectionCall;
import org.eclipse.acceleo.query.ast.ErrorExpression;
import org.eclipse.acceleo.query.ast.ErrorFeatureAccessOrCall;
import org.eclipse.acceleo.query.ast.ErrorTypeLiteral;
import org.eclipse.acceleo.query.ast.ErrorVariableDeclaration;
import org.eclipse.acceleo.query.ast.FeatureAccess;
import org.eclipse.acceleo.query.ast.IntegerLiteral;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.Let;
import org.eclipse.acceleo.query.ast.Literal;
import org.eclipse.acceleo.query.ast.NullLiteral;
import org.eclipse.acceleo.query.ast.RealLiteral;
import org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral;
import org.eclipse.acceleo.query.ast.SetInExtensionLiteral;
import org.eclipse.acceleo.query.ast.StringLiteral;
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.acceleo.query.parser.AstEvaluator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * @generated
 */
public class AstFactoryImpl extends EFactoryImpl implements AstFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static AstFactory init() {
		try {
			AstFactory theAstFactory = (AstFactory)EPackage.Registry.INSTANCE.getEFactory(AstPackage.eNS_URI);
			if (theAstFactory != null) {
				return theAstFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AstFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public AstFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case AstPackage.VAR_REF: return createVarRef();
			case AstPackage.FEATURE_ACCESS: return createFeatureAccess();
			case AstPackage.CALL: return createCall();
			case AstPackage.LITERAL: return createLiteral();
			case AstPackage.INTEGER_LITERAL: return createIntegerLiteral();
			case AstPackage.REAL_LITERAL: return createRealLiteral();
			case AstPackage.STRING_LITERAL: return createStringLiteral();
			case AstPackage.BOOLEAN_LITERAL: return createBooleanLiteral();
			case AstPackage.ENUM_LITERAL: return createEnumLiteral();
			case AstPackage.TYPE_LITERAL: return createTypeLiteral();
			case AstPackage.COLLECTION_TYPE_LITERAL: return createCollectionTypeLiteral();
			case AstPackage.LAMBDA: return createLambda();
			case AstPackage.NULL_LITERAL: return createNullLiteral();
			case AstPackage.SET_IN_EXTENSION_LITERAL: return createSetInExtensionLiteral();
			case AstPackage.SEQUENCE_IN_EXTENSION_LITERAL: return createSequenceInExtensionLiteral();
			case AstPackage.VARIABLE_DECLARATION: return createVariableDeclaration();
			case AstPackage.ERROR_EXPRESSION: return createErrorExpression();
			case AstPackage.ERROR_TYPE_LITERAL: return createErrorTypeLiteral();
			case AstPackage.ERROR_FEATURE_ACCESS_OR_CALL: return createErrorFeatureAccessOrCall();
			case AstPackage.ERROR_COLLECTION_CALL: return createErrorCollectionCall();
			case AstPackage.ERROR_VARIABLE_DECLARATION: return createErrorVariableDeclaration();
			case AstPackage.BINDING: return createBinding();
			case AstPackage.LET: return createLet();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case AstPackage.CALL_TYPE:
				return createCallTypeFromString(eDataType, initialValue);
			case AstPackage.OBJECT_TYPE:
				return createObjectTypeFromString(eDataType, initialValue);
			case AstPackage.AST_EVALUATOR:
				return createAstEvaluatorFromString(eDataType, initialValue);
			case AstPackage.OBJECT_ARRAY:
				return createObjectArrayFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case AstPackage.CALL_TYPE:
				return convertCallTypeToString(eDataType, instanceValue);
			case AstPackage.OBJECT_TYPE:
				return convertObjectTypeToString(eDataType, instanceValue);
			case AstPackage.AST_EVALUATOR:
				return convertAstEvaluatorToString(eDataType, instanceValue);
			case AstPackage.OBJECT_ARRAY:
				return convertObjectArrayToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public VarRef createVarRef() {
		VarRefImpl varRef = new VarRefImpl();
		return varRef;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureAccess createFeatureAccess() {
		FeatureAccessImpl featureAccess = new FeatureAccessImpl();
		return featureAccess;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Call createCall() {
		CallImpl call = new CallImpl();
		return call;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Literal createLiteral() {
		LiteralImpl literal = new LiteralImpl();
		return literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public IntegerLiteral createIntegerLiteral() {
		IntegerLiteralImpl integerLiteral = new IntegerLiteralImpl();
		return integerLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public RealLiteral createRealLiteral() {
		RealLiteralImpl realLiteral = new RealLiteralImpl();
		return realLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public StringLiteral createStringLiteral() {
		StringLiteralImpl stringLiteral = new StringLiteralImpl();
		return stringLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanLiteral createBooleanLiteral() {
		BooleanLiteralImpl booleanLiteral = new BooleanLiteralImpl();
		return booleanLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EnumLiteral createEnumLiteral() {
		EnumLiteralImpl enumLiteral = new EnumLiteralImpl();
		return enumLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public TypeLiteral createTypeLiteral() {
		TypeLiteralImpl typeLiteral = new TypeLiteralImpl();
		return typeLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CollectionTypeLiteral createCollectionTypeLiteral() {
		CollectionTypeLiteralImpl collectionTypeLiteral = new CollectionTypeLiteralImpl();
		return collectionTypeLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Lambda createLambda() {
		LambdaImpl lambda = new LambdaImpl();
		return lambda;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NullLiteral createNullLiteral() {
		NullLiteralImpl nullLiteral = new NullLiteralImpl();
		return nullLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public SetInExtensionLiteral createSetInExtensionLiteral() {
		SetInExtensionLiteralImpl setInExtensionLiteral = new SetInExtensionLiteralImpl();
		return setInExtensionLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public SequenceInExtensionLiteral createSequenceInExtensionLiteral() {
		SequenceInExtensionLiteralImpl sequenceInExtensionLiteral = new SequenceInExtensionLiteralImpl();
		return sequenceInExtensionLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public VariableDeclaration createVariableDeclaration() {
		VariableDeclarationImpl variableDeclaration = new VariableDeclarationImpl();
		return variableDeclaration;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ErrorExpression createErrorExpression() {
		ErrorExpressionImpl errorExpression = new ErrorExpressionImpl();
		return errorExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ErrorTypeLiteral createErrorTypeLiteral() {
		ErrorTypeLiteralImpl errorTypeLiteral = new ErrorTypeLiteralImpl();
		return errorTypeLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ErrorFeatureAccessOrCall createErrorFeatureAccessOrCall() {
		ErrorFeatureAccessOrCallImpl errorFeatureAccessOrCall = new ErrorFeatureAccessOrCallImpl();
		return errorFeatureAccessOrCall;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ErrorCollectionCall createErrorCollectionCall() {
		ErrorCollectionCallImpl errorCollectionCall = new ErrorCollectionCallImpl();
		return errorCollectionCall;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public ErrorVariableDeclaration createErrorVariableDeclaration() {
		ErrorVariableDeclarationImpl errorVariableDeclaration = new ErrorVariableDeclarationImpl();
		return errorVariableDeclaration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Binding createBinding() {
		BindingImpl binding = new BindingImpl();
		return binding;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Let createLet() {
		LetImpl let = new LetImpl();
		return let;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public CallType createCallTypeFromString(EDataType eDataType, String initialValue) {
		CallType result = CallType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertCallTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Object createObjectTypeFromString(EDataType eDataType, String initialValue) {
		return super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertObjectTypeToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public AstEvaluator createAstEvaluatorFromString(EDataType eDataType, String initialValue) {
		return (AstEvaluator)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAstEvaluatorToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Object[] createObjectArrayFromString(EDataType eDataType, String initialValue) {
		return (Object[])super.createFromString(initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String convertObjectArrayToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public AstPackage getAstPackage() {
		return (AstPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static AstPackage getPackage() {
		return AstPackage.eINSTANCE;
	}

} // AstFactoryImpl
