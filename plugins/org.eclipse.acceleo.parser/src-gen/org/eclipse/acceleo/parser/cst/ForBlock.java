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
package org.eclipse.acceleo.parser.cst;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>For Block</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.cst.ForBlock#getLoopVariable <em>Loop Variable</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.ForBlock#getIterSet <em>Iter Set</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.ForBlock#getBefore <em>Before</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.ForBlock#getEach <em>Each</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.ForBlock#getAfter <em>After</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.cst.ForBlock#getGuard <em>Guard</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.parser.cst.CstPackage#getForBlock()
 * @model
 * @generated
 * @since 3.0
 */
public interface ForBlock extends Block {
	/**
	 * Returns the value of the '<em><b>Loop Variable</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Loop Variable</em>' containment reference isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Loop Variable</em>' containment reference.
	 * @see #setLoopVariable(Variable)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getForBlock_LoopVariable()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Variable getLoopVariable();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.ForBlock#getLoopVariable
	 * <em>Loop Variable</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Loop Variable</em>' containment reference.
	 * @see #getLoopVariable()
	 * @generated
	 */
	void setLoopVariable(Variable value);

	/**
	 * Returns the value of the '<em><b>Iter Set</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Iter Set</em>' containment reference isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Iter Set</em>' containment reference.
	 * @see #setIterSet(ModelExpression)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getForBlock_IterSet()
	 * @model containment="true" required="true"
	 * @generated
	 */
	ModelExpression getIterSet();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.ForBlock#getIterSet <em>Iter Set</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Iter Set</em>' containment reference.
	 * @see #getIterSet()
	 * @generated
	 */
	void setIterSet(ModelExpression value);

	/**
	 * Returns the value of the '<em><b>Before</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Before</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Before</em>' containment reference.
	 * @see #setBefore(ModelExpression)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getForBlock_Before()
	 * @model containment="true"
	 * @generated
	 */
	ModelExpression getBefore();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.ForBlock#getBefore <em>Before</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Before</em>' containment reference.
	 * @see #getBefore()
	 * @generated
	 */
	void setBefore(ModelExpression value);

	/**
	 * Returns the value of the '<em><b>Each</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Each</em>' containment reference isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Each</em>' containment reference.
	 * @see #setEach(ModelExpression)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getForBlock_Each()
	 * @model containment="true"
	 * @generated
	 */
	ModelExpression getEach();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.ForBlock#getEach <em>Each</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Each</em>' containment reference.
	 * @see #getEach()
	 * @generated
	 */
	void setEach(ModelExpression value);

	/**
	 * Returns the value of the '<em><b>After</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>After</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>After</em>' containment reference.
	 * @see #setAfter(ModelExpression)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getForBlock_After()
	 * @model containment="true"
	 * @generated
	 */
	ModelExpression getAfter();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.ForBlock#getAfter <em>After</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>After</em>' containment reference.
	 * @see #getAfter()
	 * @generated
	 */
	void setAfter(ModelExpression value);

	/**
	 * Returns the value of the '<em><b>Guard</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Guard</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Guard</em>' containment reference.
	 * @see #setGuard(ModelExpression)
	 * @see org.eclipse.acceleo.parser.cst.CstPackage#getForBlock_Guard()
	 * @model containment="true"
	 * @generated
	 */
	ModelExpression getGuard();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.cst.ForBlock#getGuard <em>Guard</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Guard</em>' containment reference.
	 * @see #getGuard()
	 * @generated
	 */
	void setGuard(ModelExpression value);

} // ForBlock
