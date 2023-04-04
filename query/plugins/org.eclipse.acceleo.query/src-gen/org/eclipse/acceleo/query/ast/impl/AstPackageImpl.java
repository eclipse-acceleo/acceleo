/**
 *  Copyright (c) 2015, 2023 Obeo.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors:
 *      Obeo - initial API and implementation
 * 
 */
package org.eclipse.acceleo.query.ast.impl;

import org.eclipse.acceleo.query.ast.ASTNode;
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
import org.eclipse.acceleo.query.ast.Declaration;
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
import org.eclipse.acceleo.query.ast.Expression;
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
import org.eclipse.acceleo.query.ast.TypeLiteral;
import org.eclipse.acceleo.query.ast.TypeSetLiteral;
import org.eclipse.acceleo.query.ast.VarRef;
import org.eclipse.acceleo.query.ast.VariableDeclaration;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class AstPackageImpl extends EPackageImpl implements AstPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass astNodeEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass expressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass varRefEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass callEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass literalEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass integerLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass realLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass stringLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass booleanLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass enumLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass typeLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass eClassifierTypeLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass classTypeLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass typeSetLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass collectionTypeLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass lambdaEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass nullLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass setInExtensionLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass sequenceInExtensionLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass variableDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorExpressionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorTypeLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorEClassifierTypeLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorEnumLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorCallEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorVariableDeclarationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorStringLiteralEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorConditionalEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass bindingEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass errorBindingEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass letEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass conditionalEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass orEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass andEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass impliesEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass declarationEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EEnum callTypeEEnum = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EDataType javaClassEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()},
	 * which also performs initialization of the package, or returns the registered package, if one already
	 * exists. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.acceleo.query.ast.AstPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AstPackageImpl() {
		super(eNS_URI, AstFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it
	 * depends.
	 * <p>
	 * This method is used to initialize {@link AstPackage#eINSTANCE} when that field is accessed. Clients
	 * should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static AstPackage init() {
		if (isInited)
			return (AstPackage)EPackage.Registry.INSTANCE.getEPackage(AstPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredAstPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		AstPackageImpl theAstPackage = registeredAstPackage instanceof AstPackageImpl
				? (AstPackageImpl)registeredAstPackage
				: new AstPackageImpl();

		isInited = true;

		// Create package meta-data objects
		theAstPackage.createPackageContents();

		// Initialize created meta-data
		theAstPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAstPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(AstPackage.eNS_URI, theAstPackage);
		return theAstPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getASTNode() {
		return astNodeEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getExpression() {
		return expressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getVarRef() {
		return varRefEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getVarRef_VariableName() {
		return (EAttribute)varRefEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getCall() {
		return callEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getCall_ServiceName() {
		return (EAttribute)callEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getCall_Type() {
		return (EAttribute)callEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getCall_Arguments() {
		return (EReference)callEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getLiteral() {
		return literalEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getIntegerLiteral() {
		return integerLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getIntegerLiteral_Value() {
		return (EAttribute)integerLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getRealLiteral() {
		return realLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getRealLiteral_Value() {
		return (EAttribute)realLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getStringLiteral() {
		return stringLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getStringLiteral_Value() {
		return (EAttribute)stringLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getBooleanLiteral() {
		return booleanLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getBooleanLiteral_Value() {
		return (EAttribute)booleanLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getEnumLiteral() {
		return enumLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getEnumLiteral_EPackageName() {
		return (EAttribute)enumLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getEnumLiteral_EEnumName() {
		return (EAttribute)enumLiteralEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getEnumLiteral_EEnumLiteralName() {
		return (EAttribute)enumLiteralEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTypeLiteral() {
		return typeLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getEClassifierTypeLiteral() {
		return eClassifierTypeLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getEClassifierTypeLiteral_EPackageName() {
		return (EAttribute)eClassifierTypeLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getEClassifierTypeLiteral_EClassifierName() {
		return (EAttribute)eClassifierTypeLiteralEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getClassTypeLiteral() {
		return classTypeLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getClassTypeLiteral_Value() {
		return (EAttribute)classTypeLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getTypeSetLiteral() {
		return typeSetLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getTypeSetLiteral_Types() {
		return (EReference)typeSetLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getCollectionTypeLiteral() {
		return collectionTypeLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getCollectionTypeLiteral_ElementType() {
		return (EReference)collectionTypeLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getLambda() {
		return lambdaEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getLambda_Parameters() {
		return (EReference)lambdaEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getLambda_Expression() {
		return (EReference)lambdaEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getNullLiteral() {
		return nullLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSetInExtensionLiteral() {
		return setInExtensionLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSetInExtensionLiteral_Values() {
		return (EReference)setInExtensionLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getSequenceInExtensionLiteral() {
		return sequenceInExtensionLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getSequenceInExtensionLiteral_Values() {
		return (EReference)sequenceInExtensionLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getVariableDeclaration() {
		return variableDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getVariableDeclaration_Expression() {
		return (EReference)variableDeclarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getError() {
		return errorEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorExpression() {
		return errorExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorTypeLiteral() {
		return errorTypeLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorEClassifierTypeLiteral() {
		return errorEClassifierTypeLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorEClassifierTypeLiteral_MissingColon() {
		return (EAttribute)errorEClassifierTypeLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorEnumLiteral() {
		return errorEnumLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorEnumLiteral_MissingColon() {
		return (EAttribute)errorEnumLiteralEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorCall() {
		return errorCallEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getErrorCall_MissingEndParenthesis() {
		return (EAttribute)errorCallEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorVariableDeclaration() {
		return errorVariableDeclarationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorStringLiteral() {
		return errorStringLiteralEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorConditional() {
		return errorConditionalEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getBinding() {
		return bindingEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getBinding_Value() {
		return (EReference)bindingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getErrorBinding() {
		return errorBindingEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getLet() {
		return letEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getLet_Bindings() {
		return (EReference)letEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getLet_Body() {
		return (EReference)letEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getConditional() {
		return conditionalEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getConditional_Predicate() {
		return (EReference)conditionalEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getConditional_TrueBranch() {
		return (EReference)conditionalEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getConditional_FalseBranch() {
		return (EReference)conditionalEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getOr() {
		return orEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getAnd() {
		return andEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getImplies() {
		return impliesEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getDeclaration() {
		return declarationEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EAttribute getDeclaration_Name() {
		return (EAttribute)declarationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getDeclaration_Type() {
		return (EReference)declarationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EEnum getCallType() {
		return callTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EDataType getJavaClass() {
		return javaClassEDataType;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public AstFactory getAstFactory() {
		return (AstFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to have no affect on any
	 * invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		astNodeEClass = createEClass(AST_NODE);

		expressionEClass = createEClass(EXPRESSION);

		varRefEClass = createEClass(VAR_REF);
		createEAttribute(varRefEClass, VAR_REF__VARIABLE_NAME);

		callEClass = createEClass(CALL);
		createEAttribute(callEClass, CALL__SERVICE_NAME);
		createEAttribute(callEClass, CALL__TYPE);
		createEReference(callEClass, CALL__ARGUMENTS);

		literalEClass = createEClass(LITERAL);

		integerLiteralEClass = createEClass(INTEGER_LITERAL);
		createEAttribute(integerLiteralEClass, INTEGER_LITERAL__VALUE);

		realLiteralEClass = createEClass(REAL_LITERAL);
		createEAttribute(realLiteralEClass, REAL_LITERAL__VALUE);

		stringLiteralEClass = createEClass(STRING_LITERAL);
		createEAttribute(stringLiteralEClass, STRING_LITERAL__VALUE);

		booleanLiteralEClass = createEClass(BOOLEAN_LITERAL);
		createEAttribute(booleanLiteralEClass, BOOLEAN_LITERAL__VALUE);

		enumLiteralEClass = createEClass(ENUM_LITERAL);
		createEAttribute(enumLiteralEClass, ENUM_LITERAL__EPACKAGE_NAME);
		createEAttribute(enumLiteralEClass, ENUM_LITERAL__EENUM_NAME);
		createEAttribute(enumLiteralEClass, ENUM_LITERAL__EENUM_LITERAL_NAME);

		typeLiteralEClass = createEClass(TYPE_LITERAL);

		eClassifierTypeLiteralEClass = createEClass(ECLASSIFIER_TYPE_LITERAL);
		createEAttribute(eClassifierTypeLiteralEClass, ECLASSIFIER_TYPE_LITERAL__EPACKAGE_NAME);
		createEAttribute(eClassifierTypeLiteralEClass, ECLASSIFIER_TYPE_LITERAL__ECLASSIFIER_NAME);

		classTypeLiteralEClass = createEClass(CLASS_TYPE_LITERAL);
		createEAttribute(classTypeLiteralEClass, CLASS_TYPE_LITERAL__VALUE);

		typeSetLiteralEClass = createEClass(TYPE_SET_LITERAL);
		createEReference(typeSetLiteralEClass, TYPE_SET_LITERAL__TYPES);

		collectionTypeLiteralEClass = createEClass(COLLECTION_TYPE_LITERAL);
		createEReference(collectionTypeLiteralEClass, COLLECTION_TYPE_LITERAL__ELEMENT_TYPE);

		lambdaEClass = createEClass(LAMBDA);
		createEReference(lambdaEClass, LAMBDA__PARAMETERS);
		createEReference(lambdaEClass, LAMBDA__EXPRESSION);

		nullLiteralEClass = createEClass(NULL_LITERAL);

		setInExtensionLiteralEClass = createEClass(SET_IN_EXTENSION_LITERAL);
		createEReference(setInExtensionLiteralEClass, SET_IN_EXTENSION_LITERAL__VALUES);

		sequenceInExtensionLiteralEClass = createEClass(SEQUENCE_IN_EXTENSION_LITERAL);
		createEReference(sequenceInExtensionLiteralEClass, SEQUENCE_IN_EXTENSION_LITERAL__VALUES);

		variableDeclarationEClass = createEClass(VARIABLE_DECLARATION);
		createEReference(variableDeclarationEClass, VARIABLE_DECLARATION__EXPRESSION);

		errorEClass = createEClass(ERROR);

		errorExpressionEClass = createEClass(ERROR_EXPRESSION);

		errorTypeLiteralEClass = createEClass(ERROR_TYPE_LITERAL);

		errorEClassifierTypeLiteralEClass = createEClass(ERROR_ECLASSIFIER_TYPE_LITERAL);
		createEAttribute(errorEClassifierTypeLiteralEClass, ERROR_ECLASSIFIER_TYPE_LITERAL__MISSING_COLON);

		errorEnumLiteralEClass = createEClass(ERROR_ENUM_LITERAL);
		createEAttribute(errorEnumLiteralEClass, ERROR_ENUM_LITERAL__MISSING_COLON);

		errorCallEClass = createEClass(ERROR_CALL);
		createEAttribute(errorCallEClass, ERROR_CALL__MISSING_END_PARENTHESIS);

		errorVariableDeclarationEClass = createEClass(ERROR_VARIABLE_DECLARATION);

		errorStringLiteralEClass = createEClass(ERROR_STRING_LITERAL);

		errorConditionalEClass = createEClass(ERROR_CONDITIONAL);

		bindingEClass = createEClass(BINDING);
		createEReference(bindingEClass, BINDING__VALUE);

		errorBindingEClass = createEClass(ERROR_BINDING);

		letEClass = createEClass(LET);
		createEReference(letEClass, LET__BINDINGS);
		createEReference(letEClass, LET__BODY);

		conditionalEClass = createEClass(CONDITIONAL);
		createEReference(conditionalEClass, CONDITIONAL__PREDICATE);
		createEReference(conditionalEClass, CONDITIONAL__TRUE_BRANCH);
		createEReference(conditionalEClass, CONDITIONAL__FALSE_BRANCH);

		orEClass = createEClass(OR);

		andEClass = createEClass(AND);

		impliesEClass = createEClass(IMPLIES);

		declarationEClass = createEClass(DECLARATION);
		createEAttribute(declarationEClass, DECLARATION__NAME);
		createEReference(declarationEClass, DECLARATION__TYPE);

		// Create enums
		callTypeEEnum = createEEnum(CALL_TYPE);

		// Create data types
		javaClassEDataType = createEDataType(JAVA_CLASS);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This method is guarded to have no affect
	 * on any invocation but its first. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		expressionEClass.getESuperTypes().add(this.getASTNode());
		varRefEClass.getESuperTypes().add(this.getExpression());
		callEClass.getESuperTypes().add(this.getExpression());
		literalEClass.getESuperTypes().add(this.getExpression());
		integerLiteralEClass.getESuperTypes().add(this.getLiteral());
		realLiteralEClass.getESuperTypes().add(this.getLiteral());
		stringLiteralEClass.getESuperTypes().add(this.getLiteral());
		booleanLiteralEClass.getESuperTypes().add(this.getLiteral());
		enumLiteralEClass.getESuperTypes().add(this.getLiteral());
		typeLiteralEClass.getESuperTypes().add(this.getLiteral());
		eClassifierTypeLiteralEClass.getESuperTypes().add(this.getTypeLiteral());
		classTypeLiteralEClass.getESuperTypes().add(this.getTypeLiteral());
		typeSetLiteralEClass.getESuperTypes().add(this.getClassTypeLiteral());
		collectionTypeLiteralEClass.getESuperTypes().add(this.getClassTypeLiteral());
		lambdaEClass.getESuperTypes().add(this.getLiteral());
		nullLiteralEClass.getESuperTypes().add(this.getLiteral());
		setInExtensionLiteralEClass.getESuperTypes().add(this.getLiteral());
		sequenceInExtensionLiteralEClass.getESuperTypes().add(this.getLiteral());
		variableDeclarationEClass.getESuperTypes().add(this.getDeclaration());
		errorEClass.getESuperTypes().add(this.getExpression());
		errorExpressionEClass.getESuperTypes().add(this.getError());
		errorTypeLiteralEClass.getESuperTypes().add(this.getError());
		errorTypeLiteralEClass.getESuperTypes().add(this.getTypeLiteral());
		errorEClassifierTypeLiteralEClass.getESuperTypes().add(this.getError());
		errorEClassifierTypeLiteralEClass.getESuperTypes().add(this.getEClassifierTypeLiteral());
		errorEnumLiteralEClass.getESuperTypes().add(this.getError());
		errorEnumLiteralEClass.getESuperTypes().add(this.getEnumLiteral());
		errorCallEClass.getESuperTypes().add(this.getError());
		errorCallEClass.getESuperTypes().add(this.getCall());
		errorVariableDeclarationEClass.getESuperTypes().add(this.getError());
		errorVariableDeclarationEClass.getESuperTypes().add(this.getVariableDeclaration());
		errorStringLiteralEClass.getESuperTypes().add(this.getError());
		errorStringLiteralEClass.getESuperTypes().add(this.getStringLiteral());
		errorConditionalEClass.getESuperTypes().add(this.getError());
		errorConditionalEClass.getESuperTypes().add(this.getConditional());
		bindingEClass.getESuperTypes().add(this.getDeclaration());
		errorBindingEClass.getESuperTypes().add(this.getError());
		errorBindingEClass.getESuperTypes().add(this.getBinding());
		letEClass.getESuperTypes().add(this.getExpression());
		conditionalEClass.getESuperTypes().add(this.getExpression());
		orEClass.getESuperTypes().add(this.getCall());
		andEClass.getESuperTypes().add(this.getCall());
		impliesEClass.getESuperTypes().add(this.getCall());
		declarationEClass.getESuperTypes().add(this.getASTNode());

		// Initialize classes, features, and operations; add parameters
		initEClass(astNodeEClass, ASTNode.class, "ASTNode", IS_ABSTRACT, IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(expressionEClass, Expression.class, "Expression", IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(varRefEClass, VarRef.class, "VarRef", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVarRef_VariableName(), ecorePackage.getEString(), "variableName", null, 0, 1,
				VarRef.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(callEClass, Call.class, "Call", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCall_ServiceName(), ecorePackage.getEString(), "serviceName", null, 0, 1,
				Call.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);
		initEAttribute(getCall_Type(), this.getCallType(), "type", null, 0, 1, Call.class, !IS_TRANSIENT,
				!IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCall_Arguments(), this.getExpression(), null, "arguments", null, 0, -1, Call.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(literalEClass, Literal.class, "Literal", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(integerLiteralEClass, IntegerLiteral.class, "IntegerLiteral", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIntegerLiteral_Value(), ecorePackage.getEInt(), "value", null, 0, 1,
				IntegerLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(realLiteralEClass, RealLiteral.class, "RealLiteral", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRealLiteral_Value(), ecorePackage.getEDouble(), "value", null, 0, 1,
				RealLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringLiteralEClass, StringLiteral.class, "StringLiteral", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringLiteral_Value(), ecorePackage.getEString(), "value", null, 0, 1,
				StringLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(booleanLiteralEClass, BooleanLiteral.class, "BooleanLiteral", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBooleanLiteral_Value(), ecorePackage.getEBoolean(), "value", null, 0, 1,
				BooleanLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(enumLiteralEClass, EnumLiteral.class, "EnumLiteral", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEnumLiteral_EPackageName(), ecorePackage.getEString(), "ePackageName", null, 1, 1,
				EnumLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnumLiteral_EEnumName(), ecorePackage.getEString(), "eEnumName", null, 1, 1,
				EnumLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnumLiteral_EEnumLiteralName(), ecorePackage.getEString(), "eEnumLiteralName", null,
				1, 1, EnumLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeLiteralEClass, TypeLiteral.class, "TypeLiteral", IS_ABSTRACT, IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(eClassifierTypeLiteralEClass, EClassifierTypeLiteral.class, "EClassifierTypeLiteral",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEClassifierTypeLiteral_EPackageName(), ecorePackage.getEString(), "ePackageName",
				null, 1, 1, EClassifierTypeLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEClassifierTypeLiteral_EClassifierName(), ecorePackage.getEString(),
				"eClassifierName", null, 1, 1, EClassifierTypeLiteral.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(classTypeLiteralEClass, ClassTypeLiteral.class, "ClassTypeLiteral", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getClassTypeLiteral_Value(), this.getJavaClass(), "value", null, 1, 1,
				ClassTypeLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeSetLiteralEClass, TypeSetLiteral.class, "TypeSetLiteral", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTypeSetLiteral_Types(), this.getTypeLiteral(), null, "types", null, 1, -1,
				TypeSetLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(collectionTypeLiteralEClass, CollectionTypeLiteral.class, "CollectionTypeLiteral",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCollectionTypeLiteral_ElementType(), this.getTypeLiteral(), null, "elementType",
				null, 1, 1, CollectionTypeLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(lambdaEClass, Lambda.class, "Lambda", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLambda_Parameters(), this.getVariableDeclaration(), null, "parameters", null, 0, -1,
				Lambda.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLambda_Expression(), this.getExpression(), null, "expression", null, 1, 1,
				Lambda.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(nullLiteralEClass, NullLiteral.class, "NullLiteral", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(setInExtensionLiteralEClass, SetInExtensionLiteral.class, "SetInExtensionLiteral",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSetInExtensionLiteral_Values(), this.getExpression(), null, "values", null, 0, -1,
				SetInExtensionLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sequenceInExtensionLiteralEClass, SequenceInExtensionLiteral.class,
				"SequenceInExtensionLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSequenceInExtensionLiteral_Values(), this.getExpression(), null, "values", null, 0,
				-1, SequenceInExtensionLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(variableDeclarationEClass, VariableDeclaration.class, "VariableDeclaration", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVariableDeclaration_Expression(), this.getExpression(), null, "expression", null, 1,
				1, VariableDeclaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorEClass, org.eclipse.acceleo.query.ast.Error.class, "Error", IS_ABSTRACT, IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(errorExpressionEClass, ErrorExpression.class, "ErrorExpression", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(errorTypeLiteralEClass, ErrorTypeLiteral.class, "ErrorTypeLiteral", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(errorEClassifierTypeLiteralEClass, ErrorEClassifierTypeLiteral.class,
				"ErrorEClassifierTypeLiteral", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErrorEClassifierTypeLiteral_MissingColon(), ecorePackage.getEBoolean(),
				"missingColon", null, 1, 1, ErrorEClassifierTypeLiteral.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorEnumLiteralEClass, ErrorEnumLiteral.class, "ErrorEnumLiteral", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErrorEnumLiteral_MissingColon(), ecorePackage.getEBoolean(), "missingColon", null,
				1, 1, ErrorEnumLiteral.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorCallEClass, ErrorCall.class, "ErrorCall", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErrorCall_MissingEndParenthesis(), ecorePackage.getEBoolean(),
				"missingEndParenthesis", "false", 1, 1, ErrorCall.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorVariableDeclarationEClass, ErrorVariableDeclaration.class, "ErrorVariableDeclaration",
				!IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(errorStringLiteralEClass, ErrorStringLiteral.class, "ErrorStringLiteral", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(errorConditionalEClass, ErrorConditional.class, "ErrorConditional", !IS_ABSTRACT,
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(bindingEClass, Binding.class, "Binding", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBinding_Value(), this.getExpression(), null, "value", null, 1, 1, Binding.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(errorBindingEClass, ErrorBinding.class, "ErrorBinding", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(letEClass, Let.class, "Let", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getLet_Bindings(), this.getBinding(), null, "bindings", null, 1, -1, Let.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLet_Body(), this.getExpression(), null, "body", null, 1, 1, Let.class,
				!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conditionalEClass, Conditional.class, "Conditional", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConditional_Predicate(), this.getExpression(), null, "predicate", null, 1, 1,
				Conditional.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConditional_TrueBranch(), this.getExpression(), null, "trueBranch", null, 1, 1,
				Conditional.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConditional_FalseBranch(), this.getExpression(), null, "falseBranch", null, 1, 1,
				Conditional.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(orEClass, Or.class, "Or", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(andEClass, And.class, "And", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(impliesEClass, Implies.class, "Implies", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);

		initEClass(declarationEClass, Declaration.class, "Declaration", IS_ABSTRACT, IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDeclaration_Name(), ecorePackage.getEString(), "name", null, 1, 1,
				Declaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDeclaration_Type(), this.getTypeLiteral(), null, "type", null, 0, 1,
				Declaration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(callTypeEEnum, CallType.class, "CallType");
		addEEnumLiteral(callTypeEEnum, CallType.CALLSERVICE);
		addEEnumLiteral(callTypeEEnum, CallType.CALLORAPPLY);
		addEEnumLiteral(callTypeEEnum, CallType.COLLECTIONCALL);

		// Initialize data types
		initEDataType(javaClassEDataType, Class.class, "JavaClass", IS_SERIALIZABLE,
				!IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} // AstPackageImpl
