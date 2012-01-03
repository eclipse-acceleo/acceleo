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

import org.eclipse.emf.common.util.EList;
import org.eclipse.ocl.ecore.OCLExpression;

/**
 * <!-- begin-user-doc --> A representation of the model object ' <em><b>Template Invocation</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.TemplateInvocation#getDefinition <em>Definition</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.TemplateInvocation#getArgument <em>Argument</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.TemplateInvocation#getBefore <em>Before</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.TemplateInvocation#getAfter <em>After</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.TemplateInvocation#getEach <em>Each</em>}</li>
 * <li>{@link org.eclipse.acceleo.model.mtl.TemplateInvocation#isSuper <em>Super</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getTemplateInvocation()
 * @model
 * @generated
 */
public interface TemplateInvocation extends TemplateExpression {
	/**
	 * Returns the value of the '<em><b>Definition</b></em>' reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Definition</em>' reference isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Definition</em>' reference.
	 * @see #setDefinition(Template)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getTemplateInvocation_Definition()
	 * @model required="true"
	 * @generated
	 */
	Template getDefinition();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.TemplateInvocation#getDefinition
	 * <em>Definition</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Definition</em>' reference.
	 * @see #getDefinition()
	 * @generated
	 */
	void setDefinition(Template value);

	/**
	 * Returns the value of the '<em><b>Argument</b></em>' containment reference list. The list contents are
	 * of type {@link org.eclipse.ocl.ecore.OCLExpression}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Argument</em>' containment reference list isn't clear, there really should
	 * be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Argument</em>' containment reference list.
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getTemplateInvocation_Argument()
	 * @model containment="true"
	 * @generated
	 */
	EList<OCLExpression> getArgument();

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
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getTemplateInvocation_Before()
	 * @model containment="true"
	 * @generated
	 */
	OCLExpression getBefore();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.TemplateInvocation#getBefore
	 * <em>Before</em>}' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Before</em>' containment reference.
	 * @see #getBefore()
	 * @generated
	 */
	void setBefore(OCLExpression value);

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
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getTemplateInvocation_After()
	 * @model containment="true"
	 * @generated
	 */
	OCLExpression getAfter();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.TemplateInvocation#getAfter <em>After</em>}
	 * ' containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>After</em>' containment reference.
	 * @see #getAfter()
	 * @generated
	 */
	void setAfter(OCLExpression value);

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
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getTemplateInvocation_Each()
	 * @model containment="true"
	 * @generated
	 */
	OCLExpression getEach();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.TemplateInvocation#getEach <em>Each</em>}'
	 * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Each</em>' containment reference.
	 * @see #getEach()
	 * @generated
	 */
	void setEach(OCLExpression value);

	/**
	 * Returns the value of the '<em><b>Super</b></em>' attribute. The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Super</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Super</em>' attribute.
	 * @see #setSuper(boolean)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getTemplateInvocation_Super()
	 * @model default="false"
	 * @generated
	 */
	boolean isSuper();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.TemplateInvocation#isSuper <em>Super</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Super</em>' attribute.
	 * @see #isSuper()
	 * @generated
	 */
	void setSuper(boolean value);

} // TemplateInvocation
