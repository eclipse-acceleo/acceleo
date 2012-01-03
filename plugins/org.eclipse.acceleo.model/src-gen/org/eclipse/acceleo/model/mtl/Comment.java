/*******************************************************************************
 * Copyright (c) 2006, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.model.mtl;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Comment</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.model.mtl.Comment#getBody <em>Body</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getComment()
 * @model
 * @generated
 * @since 3.1
 */
public interface Comment extends ModuleElement {
	/**
	 * Returns the value of the '<em><b>Body</b></em>' containment reference. It is bidirectional and its
	 * opposite is '{@link org.eclipse.acceleo.model.mtl.CommentBody#getComment <em>Comment</em>}'. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' containment reference isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Body</em>' containment reference.
	 * @see #setBody(CommentBody)
	 * @see org.eclipse.acceleo.model.mtl.MtlPackage#getComment_Body()
	 * @see org.eclipse.acceleo.model.mtl.CommentBody#getComment
	 * @model opposite="comment" containment="true"
	 * @generated
	 */
	CommentBody getBody();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.model.mtl.Comment#getBody <em>Body</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Body</em>' containment reference.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(CommentBody value);

} // Comment
