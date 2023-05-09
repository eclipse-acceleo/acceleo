/*******************************************************************************
 * Copyright (c) 2015, 2021 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Call Type</b></em>',
 * and utility methods for working with them. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.query.ast.AstPackage#getCallType()
 * @model
 * @generated
 */
public enum CallType implements Enumerator {
	/**
	 * The '<em><b>CALLSERVICE</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #CALLSERVICE_VALUE
	 * @generated
	 * @ordered
	 */
	CALLSERVICE(0, "CALLSERVICE", "CALLSERVICE"),
	/**
	 * The '<em><b>CALLORAPPLY</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #CALLORAPPLY_VALUE
	 * @generated
	 * @ordered
	 */
	CALLORAPPLY(1, "CALLORAPPLY", "CALLORAPPLY"),
	/**
	 * The '<em><b>COLLECTIONCALL</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #COLLECTIONCALL_VALUE
	 * @generated
	 * @ordered
	 */
	COLLECTIONCALL(2, "COLLECTIONCALL", "COLLECTIONCALL");

	/**
	 * The '<em><b>CALLSERVICE</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CALLSERVICE</b></em>' literal object isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #CALLSERVICE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CALLSERVICE_VALUE = 0;

	/**
	 * The '<em><b>CALLORAPPLY</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CALLORAPPLY</b></em>' literal object isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #CALLORAPPLY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CALLORAPPLY_VALUE = 1;

	/**
	 * The '<em><b>COLLECTIONCALL</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>COLLECTIONCALL</b></em>' literal object isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #COLLECTIONCALL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int COLLECTIONCALL_VALUE = 2;

	/**
	 * An array of all the '<em><b>Call Type</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	private static final CallType[] VALUES_ARRAY = new CallType[] {CALLSERVICE, CALLORAPPLY,
			COLLECTIONCALL, };

	/**
	 * A public read-only list of all the '<em><b>Call Type</b></em>' enumerators. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<CallType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Call Type</b></em>' literal with the specified literal value. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @param literal
	 *            the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static CallType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CallType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Call Type</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param name
	 *            the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static CallType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			CallType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Call Type</b></em>' literal with the specified integer value. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static CallType get(int value) {
		switch (value) {
			case CALLSERVICE_VALUE:
				return CALLSERVICE;
			case CALLORAPPLY_VALUE:
				return CALLORAPPLY;
			case COLLECTIONCALL_VALUE:
				return COLLECTIONCALL;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private CallType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} // CallType
