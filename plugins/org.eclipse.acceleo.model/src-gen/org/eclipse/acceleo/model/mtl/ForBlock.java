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
package org.eclipse.acceleo.model.mtl;

import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.ecore.Variable;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>For Block</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.ForBlock#getLoopVariable <em>Loop Variable</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.ForBlock#getIterSet <em>Iter Set</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.ForBlock#getBefore <em>Before</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.ForBlock#getEach <em>Each</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.ForBlock#getAfter <em>After</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.ForBlock#getGuard <em>Guard</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getForBlock()
 * @model
 * @generated
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
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getForBlock_LoopVariable()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Variable getLoopVariable();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.ForBlock#getLoopVariable
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
	 * @see #setIterSet(OCLExpression)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getForBlock_IterSet()
	 * @model containment="true" required="true"
	 * @generated
	 */
	OCLExpression getIterSet();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.ForBlock#getIterSet <em>Iter Set</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Iter Set</em>' containment reference.
	 * @see #getIterSet()
	 * @generated
	 */
	void setIterSet(OCLExpression value);

	/**
	 * Returns the value of the '<em><b>Before</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Before</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Before</em>' containment reference.
	 * @see #setBefore(OCLExpression)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getForBlock_Before()
	 * @model containment="true"
	 * @generated
	 */
	OCLExpression getBefore();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.ForBlock#getBefore <em>Before</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Before</em>' containment reference.
	 * @see #getBefore()
	 * @generated
	 */
	void setBefore(OCLExpression value);

	/**
	 * Returns the value of the '<em><b>Each</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Each</em>' containment reference isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Each</em>' containment reference.
	 * @see #setEach(OCLExpression)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getForBlock_Each()
	 * @model containment="true"
	 * @generated
	 */
	OCLExpression getEach();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.ForBlock#getEach <em>Each</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Each</em>' containment reference.
	 * @see #getEach()
	 * @generated
	 */
	void setEach(OCLExpression value);

	/**
	 * Returns the value of the '<em><b>After</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>After</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>After</em>' containment reference.
	 * @see #setAfter(OCLExpression)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getForBlock_After()
	 * @model containment="true"
	 * @generated
	 */
	OCLExpression getAfter();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.ForBlock#getAfter <em>After</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>After</em>' containment reference.
	 * @see #getAfter()
	 * @generated
	 */
	void setAfter(OCLExpression value);

	/**
	 * Returns the value of the '<em><b>Guard</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Guard</em>' containment reference isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Guard</em>' containment reference.
	 * @see #setGuard(OCLExpression)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getForBlock_Guard()
	 * @model containment="true"
	 * @generated
	 */
	OCLExpression getGuard();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.ForBlock#getGuard <em>Guard</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Guard</em>' containment reference.
	 * @see #getGuard()
	 * @generated
	 */
	void setGuard(OCLExpression value);

} // ForBlock
