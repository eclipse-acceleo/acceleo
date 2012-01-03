/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.compatibility.model.mt.statements;

import org.eclipse.acceleo.compatibility.model.mt.core.CorePackage;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to
 * represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.statements.StatementsFactory
 * @model kind="package"
 * @generated
 */
public interface StatementsPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "statements"; //$NON-NLS-1$

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/acceleo/mt/2.6.0/statements"; //$NON-NLS-1$

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "statements"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	StatementsPackage eINSTANCE = org.eclipse.acceleo.compatibility.model.mt.statements.impl.StatementsPackageImpl
			.init();

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.compatibility.model.mt.statements.Statement
	 * <em>Statement</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.Statement
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.StatementsPackageImpl#getStatement()
	 * @generated
	 */
	int STATEMENT = 0;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STATEMENT__BEGIN = CorePackage.AST_NODE__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STATEMENT__END = CorePackage.AST_NODE__END;

	/**
	 * The number of structural features of the '<em>Statement</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int STATEMENT_FEATURE_COUNT = CorePackage.AST_NODE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.statements.impl.CommentImpl <em>Comment</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.CommentImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.StatementsPackageImpl#getComment()
	 * @generated
	 */
	int COMMENT = 1;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMMENT__BEGIN = STATEMENT__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMMENT__END = STATEMENT__END;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMMENT__VALUE = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Comment</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int COMMENT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.compatibility.model.mt.statements.impl.IfImpl
	 * <em>If</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.IfImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.StatementsPackageImpl#getIf()
	 * @generated
	 */
	int IF = 2;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF__BEGIN = STATEMENT__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF__END = STATEMENT__END;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF__CONDITION = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Then Statements</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF__THEN_STATEMENTS = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Else Statements</b></em>' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF__ELSE_STATEMENTS = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Else If</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF__ELSE_IF = STATEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>If</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int IF_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.compatibility.model.mt.statements.impl.ForImpl
	 * <em>For</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.ForImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.StatementsPackageImpl#getFor()
	 * @generated
	 */
	int FOR = 3;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR__BEGIN = STATEMENT__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR__END = STATEMENT__END;

	/**
	 * The feature id for the '<em><b>Iterator</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR__ITERATOR = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR__STATEMENTS = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>For</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FOR_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.statements.impl.FeatureImpl <em>Feature</em>}' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.FeatureImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.StatementsPackageImpl#getFeature()
	 * @generated
	 */
	int FEATURE = 4;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FEATURE__BEGIN = STATEMENT__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FEATURE__END = STATEMENT__END;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FEATURE__EXPRESSION = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Feature</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FEATURE_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.compatibility.model.mt.statements.impl.TextImpl
	 * <em>Text</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.TextImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.StatementsPackageImpl#getText()
	 * @generated
	 */
	int TEXT = 5;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT__BEGIN = STATEMENT__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT__END = STATEMENT__END;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT__VALUE = STATEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Text</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEXT_FEATURE_COUNT = STATEMENT_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.statements.Statement <em>Statement</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Statement</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.Statement
	 * @generated
	 */
	EClass getStatement();

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.statements.Comment <em>Comment</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Comment</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.Comment
	 * @generated
	 */
	EClass getComment();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.statements.Comment#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.Comment#getValue()
	 * @see #getComment()
	 * @generated
	 */
	EAttribute getComment_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.compatibility.model.mt.statements.If
	 * <em>If</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>If</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.If
	 * @generated
	 */
	EClass getIf();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.statements.If#getCondition <em>Condition</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.If#getCondition()
	 * @see #getIf()
	 * @generated
	 */
	EReference getIf_Condition();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.statements.If#getThenStatements
	 * <em>Then Statements</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Then Statements</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.If#getThenStatements()
	 * @see #getIf()
	 * @generated
	 */
	EReference getIf_ThenStatements();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.statements.If#getElseStatements
	 * <em>Else Statements</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Else Statements</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.If#getElseStatements()
	 * @see #getIf()
	 * @generated
	 */
	EReference getIf_ElseStatements();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.statements.If#getElseIf <em>Else If</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Else If</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.If#getElseIf()
	 * @see #getIf()
	 * @generated
	 */
	EReference getIf_ElseIf();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.compatibility.model.mt.statements.For
	 * <em>For</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>For</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.For
	 * @generated
	 */
	EClass getFor();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.statements.For#getIterator <em>Iterator</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Iterator</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.For#getIterator()
	 * @see #getFor()
	 * @generated
	 */
	EReference getFor_Iterator();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.statements.For#getStatements <em>Statements</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.For#getStatements()
	 * @see #getFor()
	 * @generated
	 */
	EReference getFor_Statements();

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.statements.Feature <em>Feature</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Feature</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.Feature
	 * @generated
	 */
	EClass getFeature();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.statements.Feature#getExpression <em>Expression</em>}
	 * '. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.Feature#getExpression()
	 * @see #getFeature()
	 * @generated
	 */
	EReference getFeature_Expression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.compatibility.model.mt.statements.Text
	 * <em>Text</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Text</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.Text
	 * @generated
	 */
	EClass getText();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.statements.Text#getValue <em>Value</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.statements.Text#getValue()
	 * @see #getText()
	 * @generated
	 */
	EAttribute getText_Value();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	StatementsFactory getStatementsFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.statements.Statement <em>Statement</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.statements.Statement
		 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.StatementsPackageImpl#getStatement()
		 * @generated
		 */
		EClass STATEMENT = eINSTANCE.getStatement();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.statements.impl.CommentImpl <em>Comment</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.CommentImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.StatementsPackageImpl#getComment()
		 * @generated
		 */
		EClass COMMENT = eINSTANCE.getComment();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute COMMENT__VALUE = eINSTANCE.getComment_Value();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.statements.impl.IfImpl <em>If</em>}' class. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.IfImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.StatementsPackageImpl#getIf()
		 * @generated
		 */
		EClass IF = eINSTANCE.getIf();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference IF__CONDITION = eINSTANCE.getIf_Condition();

		/**
		 * The meta object literal for the '<em><b>Then Statements</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference IF__THEN_STATEMENTS = eINSTANCE.getIf_ThenStatements();

		/**
		 * The meta object literal for the '<em><b>Else Statements</b></em>' containment reference list
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference IF__ELSE_STATEMENTS = eINSTANCE.getIf_ElseStatements();

		/**
		 * The meta object literal for the '<em><b>Else If</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference IF__ELSE_IF = eINSTANCE.getIf_ElseIf();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.statements.impl.ForImpl <em>For</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.ForImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.StatementsPackageImpl#getFor()
		 * @generated
		 */
		EClass FOR = eINSTANCE.getFor();

		/**
		 * The meta object literal for the '<em><b>Iterator</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FOR__ITERATOR = eINSTANCE.getFor_Iterator();

		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FOR__STATEMENTS = eINSTANCE.getFor_Statements();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.statements.impl.FeatureImpl <em>Feature</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.FeatureImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.StatementsPackageImpl#getFeature()
		 * @generated
		 */
		EClass FEATURE = eINSTANCE.getFeature();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FEATURE__EXPRESSION = eINSTANCE.getFeature_Expression();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.statements.impl.TextImpl <em>Text</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.TextImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.statements.impl.StatementsPackageImpl#getText()
		 * @generated
		 */
		EClass TEXT = eINSTANCE.getText();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEXT__VALUE = eINSTANCE.getText_Value();

	}

} // StatementsPackage
