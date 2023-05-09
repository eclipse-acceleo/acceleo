/**
 *  Copyright (c) 2015, 2021 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 * 
 */
package org.eclipse.acceleo.query.ast.impl;

import org.eclipse.acceleo.query.ast.And;
import org.eclipse.acceleo.query.ast.AstFactory;
import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.acceleo.query.ast.Binding;
import org.eclipse.acceleo.query.ast.BooleanLiteral;
import org.eclipse.acceleo.query.ast.Call;
import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.ast.ClassTypeLiteral;
import org.eclipse.acceleo.query.ast.CollectionTypeLiteral;
import org.eclipse.acceleo.query.ast.Conditional;
import org.eclipse.acceleo.query.ast.EClassifierTypeLiteral;
import org.eclipse.acceleo.query.ast.EnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorBinding;
import org.eclipse.acceleo.query.ast.ErrorCall;
import org.eclipse.acceleo.query.ast.ErrorConditional;
import org.eclipse.acceleo.query.ast.ErrorEClassifierTypeLiteral;
import org.eclipse.acceleo.query.ast.ErrorEnumLiteral;
import org.eclipse.acceleo.query.ast.ErrorExpression;
import org.eclipse.acceleo.query.ast.ErrorStringLiteral;
import org.eclipse.acceleo.query.ast.ErrorTypeLiteral;
import org.eclipse.acceleo.query.ast.ErrorVariableDeclaration;
import org.eclipse.acceleo.query.ast.Implies;
import org.eclipse.acceleo.query.ast.IntegerLiteral;
import org.eclipse.acceleo.query.ast.Lambda;
import org.eclipse.acceleo.query.ast.Let;
import org.eclipse.acceleo.query.ast.Literal;
import org.eclipse.acceleo.query.ast.NullLiteral;
import org.eclipse.acceleo.query.ast.Or;
import org.eclipse.acceleo.query.ast.RealLiteral;
import org.eclipse.acceleo.query.ast.SequenceInExtensionLiteral;
import org.eclipse.acceleo.query.ast.SetInExtensionLiteral;
import org.eclipse.acceleo.query.ast.StringLiteral;
import org.eclipse.acceleo.query.ast.TypeSetLiteral;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class AstFactoryImpl extends EFactoryImpl implements AstFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static AstFactory init() {
		try {
			AstFactory theAstFactory = (AstFactory)EPackage.Registry.INSTANCE.getEFactory(AstPackage.eNS_URI);
			if (theAstFactory != null) {
				return theAstFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AstFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AstFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case AstPackage.VAR_REF:
				return createVarRef();
			case AstPackage.CALL:
				return createCall();
			case AstPackage.LITERAL:
				return createLiteral();
			case AstPackage.INTEGER_LITERAL:
				return createIntegerLiteral();
			case AstPackage.REAL_LITERAL:
				return createRealLiteral();
			case AstPackage.STRING_LITERAL:
				return createStringLiteral();
			case AstPackage.BOOLEAN_LITERAL:
				return createBooleanLiteral();
			case AstPackage.ENUM_LITERAL:
				return createEnumLiteral();
			case AstPackage.ECLASSIFIER_TYPE_LITERAL:
				return createEClassifierTypeLiteral();
			case AstPackage.CLASS_TYPE_LITERAL:
				return createClassTypeLiteral();
			case AstPackage.TYPE_SET_LITERAL:
				return createTypeSetLiteral();
			case AstPackage.COLLECTION_TYPE_LITERAL:
				return createCollectionTypeLiteral();
			case AstPackage.LAMBDA:
				return createLambda();
			case AstPackage.NULL_LITERAL:
				return createNullLiteral();
			case AstPackage.SET_IN_EXTENSION_LITERAL:
				return createSetInExtensionLiteral();
			case AstPackage.SEQUENCE_IN_EXTENSION_LITERAL:
				return createSequenceInExtensionLiteral();
			case AstPackage.VARIABLE_DECLARATION:
				return createVariableDeclaration();
			case AstPackage.ERROR_EXPRESSION:
				return createErrorExpression();
			case AstPackage.ERROR_TYPE_LITERAL:
				return createErrorTypeLiteral();
			case AstPackage.ERROR_ECLASSIFIER_TYPE_LITERAL:
				return createErrorEClassifierTypeLiteral();
			case AstPackage.ERROR_ENUM_LITERAL:
				return createErrorEnumLiteral();
			case AstPackage.ERROR_CALL:
				return createErrorCall();
			case AstPackage.ERROR_VARIABLE_DECLARATION:
				return createErrorVariableDeclaration();
			case AstPackage.ERROR_STRING_LITERAL:
				return createErrorStringLiteral();
			case AstPackage.ERROR_CONDITIONAL:
				return createErrorConditional();
			case AstPackage.BINDING:
				return createBinding();
			case AstPackage.ERROR_BINDING:
				return createErrorBinding();
			case AstPackage.LET:
				return createLet();
			case AstPackage.CONDITIONAL:
				return createConditional();
			case AstPackage.OR:
				return createOr();
			case AstPackage.AND:
				return createAnd();
			case AstPackage.IMPLIES:
				return createImplies();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName()
						+ "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case AstPackage.CALL_TYPE:
				return createCallTypeFromString(eDataType, initialValue);
			case AstPackage.JAVA_CLASS:
				return createJavaClassFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName()
						+ "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case AstPackage.CALL_TYPE:
				return convertCallTypeToString(eDataType, instanceValue);
			case AstPackage.JAVA_CLASS:
				return convertJavaClassToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName()
						+ "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public VarRef createVarRef() {
		VarRefImpl varRef = new VarRefImpl();
		return varRef;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Call createCall() {
		CallImpl call = new CallImpl();
		return call;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Literal createLiteral() {
		LiteralImpl literal = new LiteralImpl();
		return literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public IntegerLiteral createIntegerLiteral() {
		IntegerLiteralImpl integerLiteral = new IntegerLiteralImpl();
		return integerLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public RealLiteral createRealLiteral() {
		RealLiteralImpl realLiteral = new RealLiteralImpl();
		return realLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public StringLiteral createStringLiteral() {
		StringLiteralImpl stringLiteral = new StringLiteralImpl();
		return stringLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public BooleanLiteral createBooleanLiteral() {
		BooleanLiteralImpl booleanLiteral = new BooleanLiteralImpl();
		return booleanLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EnumLiteral createEnumLiteral() {
		EnumLiteralImpl enumLiteral = new EnumLiteralImpl();
		return enumLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClassifierTypeLiteral createEClassifierTypeLiteral() {
		EClassifierTypeLiteralImpl eClassifierTypeLiteral = new EClassifierTypeLiteralImpl();
		return eClassifierTypeLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ClassTypeLiteral createClassTypeLiteral() {
		ClassTypeLiteralImpl classTypeLiteral = new ClassTypeLiteralImpl();
		return classTypeLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public TypeSetLiteral createTypeSetLiteral() {
		TypeSetLiteralImpl typeSetLiteral = new TypeSetLiteralImpl();
		return typeSetLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CollectionTypeLiteral createCollectionTypeLiteral() {
		CollectionTypeLiteralImpl collectionTypeLiteral = new CollectionTypeLiteralImpl();
		return collectionTypeLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Lambda createLambda() {
		LambdaImpl lambda = new LambdaImpl();
		return lambda;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NullLiteral createNullLiteral() {
		NullLiteralImpl nullLiteral = new NullLiteralImpl();
		return nullLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SetInExtensionLiteral createSetInExtensionLiteral() {
		SetInExtensionLiteralImpl setInExtensionLiteral = new SetInExtensionLiteralImpl();
		return setInExtensionLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public SequenceInExtensionLiteral createSequenceInExtensionLiteral() {
		SequenceInExtensionLiteralImpl sequenceInExtensionLiteral = new SequenceInExtensionLiteralImpl();
		return sequenceInExtensionLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public VariableDeclaration createVariableDeclaration() {
		VariableDeclarationImpl variableDeclaration = new VariableDeclarationImpl();
		return variableDeclaration;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorExpression createErrorExpression() {
		ErrorExpressionImpl errorExpression = new ErrorExpressionImpl();
		return errorExpression;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorTypeLiteral createErrorTypeLiteral() {
		ErrorTypeLiteralImpl errorTypeLiteral = new ErrorTypeLiteralImpl();
		return errorTypeLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorEClassifierTypeLiteral createErrorEClassifierTypeLiteral() {
		ErrorEClassifierTypeLiteralImpl errorEClassifierTypeLiteral = new ErrorEClassifierTypeLiteralImpl();
		return errorEClassifierTypeLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorEnumLiteral createErrorEnumLiteral() {
		ErrorEnumLiteralImpl errorEnumLiteral = new ErrorEnumLiteralImpl();
		return errorEnumLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorCall createErrorCall() {
		ErrorCallImpl errorCall = new ErrorCallImpl();
		return errorCall;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorVariableDeclaration createErrorVariableDeclaration() {
		ErrorVariableDeclarationImpl errorVariableDeclaration = new ErrorVariableDeclarationImpl();
		return errorVariableDeclaration;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorStringLiteral createErrorStringLiteral() {
		ErrorStringLiteralImpl errorStringLiteral = new ErrorStringLiteralImpl();
		return errorStringLiteral;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorConditional createErrorConditional() {
		ErrorConditionalImpl errorConditional = new ErrorConditionalImpl();
		return errorConditional;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Binding createBinding() {
		BindingImpl binding = new BindingImpl();
		return binding;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ErrorBinding createErrorBinding() {
		ErrorBindingImpl errorBinding = new ErrorBindingImpl();
		return errorBinding;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Let createLet() {
		LetImpl let = new LetImpl();
		return let;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Conditional createConditional() {
		ConditionalImpl conditional = new ConditionalImpl();
		return conditional;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Or createOr() {
		OrImpl or = new OrImpl();
		return or;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public And createAnd() {
		AndImpl and = new AndImpl();
		return and;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Implies createImplies() {
		ImpliesImpl implies = new ImpliesImpl();
		return implies;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CallType createCallTypeFromString(EDataType eDataType, String initialValue) {
		CallType result = CallType.get(initialValue);
		if (result == null)
			throw new IllegalArgumentException("The value '" + initialValue
					+ "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertCallTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Class createJavaClassFromString(EDataType eDataType, String initialValue) {
		return (Class)super.createFromString(eDataType, initialValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String convertJavaClassToString(EDataType eDataType, Object instanceValue) {
		return super.convertToString(eDataType, instanceValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public AstPackage getAstPackage() {
		return (AstPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static AstPackage getPackage() {
		return AstPackage.eINSTANCE;
	}

} // AstFactoryImpl
