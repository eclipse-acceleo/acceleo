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
package org.eclipse.acceleo.parser.tests.extlibrary;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Writer</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.parser.tests.extlibrary.Writer#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.acceleo.parser.tests.extlibrary.Writer#getBooks <em>Books</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.parser.tests.extlibrary.ExtlibraryPackage#getWriter()
 * @model
 * @generated
 */
public interface Writer extends Person {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.acceleo.parser.tests.extlibrary.ExtlibraryPackage#getWriter_Name()
	 * @model transient="true" volatile="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.parser.tests.extlibrary.Writer#getName <em>Name</em>}
	 * ' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Books</b></em>' reference list. The list contents are of type
	 * {@link org.eclipse.acceleo.parser.tests.extlibrary.Book}. It is bidirectional and its opposite is '
	 * {@link org.eclipse.acceleo.parser.tests.extlibrary.Book#getAuthor <em>Author</em>}'. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Books</em>' reference list isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Books</em>' reference list.
	 * @see org.eclipse.acceleo.parser.tests.extlibrary.ExtlibraryPackage#getWriter_Books()
	 * @see org.eclipse.acceleo.parser.tests.extlibrary.Book#getAuthor
	 * @model opposite="author"
	 * @generated
	 */
	EList<Book> getBooks();

} // Writer
