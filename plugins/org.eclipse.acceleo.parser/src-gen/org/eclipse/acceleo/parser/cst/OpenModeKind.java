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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Open Mode Kind</b></em>
 * ', and utility methods for working with them. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.parser.cst.CstPackage#getOpenModeKind()
 * @model
 * @generated
 * @since 3.0
 */
public enum OpenModeKind implements Enumerator {
	/**
	 * The '<em><b>Append</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #APPEND_VALUE
	 * @generated
	 * @ordered
	 */
	APPEND(0, "Append", "Append"), //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Over Write</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #OVER_WRITE_VALUE
	 * @generated
	 * @ordered
	 */
	OVER_WRITE(1, "OverWrite", "OverWrite"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The '<em><b>Append</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Append</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #APPEND
	 * @model name="Append"
	 * @generated
	 * @ordered
	 */
	public static final int APPEND_VALUE = 0;

	/**
	 * The '<em><b>Over Write</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Over Write</b></em>' literal object isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #OVER_WRITE
	 * @model name="OverWrite"
	 * @generated
	 * @ordered
	 */
	public static final int OVER_WRITE_VALUE = 1;

	/**
	 * An array of all the '<em><b>Open Mode Kind</b></em>' enumerators. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	private static final OpenModeKind[] VALUES_ARRAY = new OpenModeKind[] {APPEND, OVER_WRITE, };

	/**
	 * A public read-only list of all the '<em><b>Open Mode Kind</b></em>' enumerators. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<OpenModeKind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Open Mode Kind</b></em>' literal with the specified literal value. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static OpenModeKind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OpenModeKind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Open Mode Kind</b></em>' literal with the specified name. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static OpenModeKind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OpenModeKind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Open Mode Kind</b></em>' literal with the specified integer value. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static OpenModeKind get(int value) {
		switch (value) {
			case APPEND_VALUE:
				return APPEND;
			case OVER_WRITE_VALUE:
				return OVER_WRITE;
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
	private OpenModeKind(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
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

} // OpenModeKind
