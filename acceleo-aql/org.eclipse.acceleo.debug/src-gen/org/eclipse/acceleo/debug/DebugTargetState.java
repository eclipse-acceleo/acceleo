/*******************************************************************************
 * Copyright (c) 2015, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.debug;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Target State</b></em>',
 * and utility methods for working with them. <!-- end-user-doc --> <!-- begin-model-doc --> Possible States
 * of a {@link DebugTarget} {@link DebugTarget#getState() state}. <!-- end-model-doc -->
 * 
 * @see org.eclipse.acceleo.debug.DebugPackage#getDebugTargetState()
 * @model
 * @generated
 */
public enum DebugTargetState implements Enumerator {
	/**
	 * The '<em><b>CONNECTED</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #CONNECTED_VALUE
	 * @generated
	 * @ordered
	 */
	CONNECTED(0, "CONNECTED", "CONNECTED"),
	/**
	 * The '<em><b>DISCONNECTED</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #DISCONNECTED_VALUE
	 * @generated
	 * @ordered
	 */
	DISCONNECTED(1, "DISCONNECTED", "DISCONNECTED"),

	/**
	 * The '<em><b>TERMINATING</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #TERMINATING_VALUE
	 * @generated
	 * @ordered
	 */
	TERMINATING(2, "TERMINATING", "TERMINATING"),
	/**
	 * The '<em><b>TERMINATED</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #TERMINATED_VALUE
	 * @generated
	 * @ordered
	 */
	TERMINATED(3, "TERMINATED", "TERMINATED");

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final String copyright = "Copyright (c) 2015 Obeo.\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n \n Contributors:\n    Obeo - initial API and implementation";

	/**
	 * The '<em><b>CONNECTED</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> The {@link DebugTarget} is connected. <!-- end-model-doc -->
	 * 
	 * @see #CONNECTED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CONNECTED_VALUE = 0;

	/**
	 * The '<em><b>DISCONNECTED</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> The {@link DebugTarget} is disconnected. <!-- end-model-doc -->
	 * 
	 * @see #DISCONNECTED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DISCONNECTED_VALUE = 1;

	/**
	 * The '<em><b>TERMINATING</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> The {@link DebugTarget} is terminating. <!-- end-model-doc -->
	 * 
	 * @see #TERMINATING
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int TERMINATING_VALUE = 2;

	/**
	 * The '<em><b>TERMINATED</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> The {@link DebugTarget} is terminated. <!-- end-model-doc -->
	 * 
	 * @see #TERMINATED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int TERMINATED_VALUE = 3;

	/**
	 * An array of all the '<em><b>Target State</b></em>' enumerators. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	private static final DebugTargetState[] VALUES_ARRAY = new DebugTargetState[] {CONNECTED, DISCONNECTED,
			TERMINATING, TERMINATED, };

	/**
	 * A public read-only list of all the '<em><b>Target State</b></em>' enumerators. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<DebugTargetState> VALUES = Collections.unmodifiableList(Arrays.asList(
			VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Target State</b></em>' literal with the specified literal value. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static DebugTargetState get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DebugTargetState result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Target State</b></em>' literal with the specified name. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param name
	 *            the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static DebugTargetState getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DebugTargetState result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Target State</b></em>' literal with the specified integer value. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static DebugTargetState get(int value) {
		switch (value) {
			case CONNECTED_VALUE:
				return CONNECTED;
			case DISCONNECTED_VALUE:
				return DISCONNECTED;
			case TERMINATING_VALUE:
				return TERMINATING;
			case TERMINATED_VALUE:
				return TERMINATED;
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
	private DebugTargetState(int value, String name, String literal) {
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

} // DebugTargetState
