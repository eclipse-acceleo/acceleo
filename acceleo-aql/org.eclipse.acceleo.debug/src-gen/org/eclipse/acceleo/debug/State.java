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
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>State</b></em>', and
 * utility methods for working with them. <!-- end-user-doc --> <!-- begin-model-doc --> Possible States of a
 * {@link StackFrame} {@link StackFrame#getState() state}. <!-- end-model-doc -->
 * 
 * @see org.eclipse.acceleo.debug.DebugPackage#getState()
 * @model
 * @generated
 */
public enum State implements Enumerator {
	/**
	 * The '<em><b>RUNNING</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #RUNNING_VALUE
	 * @generated
	 * @ordered
	 */
	RUNNING(0, "RUNNING", "RUNNING"),

	/**
	 * The '<em><b>STEPPING INTO</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #STEPPING_INTO_VALUE
	 * @generated
	 * @ordered
	 */
	STEPPING_INTO(1, "STEPPING_INTO", "STEPPING_INTO"),
	/**
	 * The '<em><b>STEPPING OVER</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #STEPPING_OVER_VALUE
	 * @generated
	 * @ordered
	 */
	STEPPING_OVER(2, "STEPPING_OVER", "STEPPING_OVER"),
	/**
	 * The '<em><b>STEPPING RETURN</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #STEPPING_RETURN_VALUE
	 * @generated
	 * @ordered
	 */
	STEPPING_RETURN(3, "STEPPING_RETURN", "STEPPING_RETURN"),
	/**
	 * The '<em><b>SUSPENDING</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #SUSPENDING_VALUE
	 * @generated
	 * @ordered
	 */
	SUSPENDING(4, "SUSPENDING", "SUSPENDING"),
	/**
	 * The '<em><b>SUSPENDED</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #SUSPENDED_VALUE
	 * @generated
	 * @ordered
	 */
	SUSPENDED(5, "SUSPENDED", "SUSPENDED"),

	/**
	 * The '<em><b>TERMINATING</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #TERMINATING_VALUE
	 * @generated
	 * @ordered
	 */
	TERMINATING(6, "TERMINATING", "TERMINATING"),
	/**
	 * The '<em><b>TERMINATED</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #TERMINATED_VALUE
	 * @generated
	 * @ordered
	 */
	TERMINATED(7, "TERMINATED", "TERMINATED");

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final String copyright = "Copyright (c) 2015 Obeo.\n All rights reserved. This program and the accompanying materials\n are made available under the terms of the Eclipse Public License v1.0\n which accompanies this distribution, and is available at\n http://www.eclipse.org/legal/epl-v10.html\n \n Contributors:\n    Obeo - initial API and implementation";

	/**
	 * The '<em><b>RUNNING</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> The {@link StackFrame} is running. <!-- end-model-doc -->
	 * 
	 * @see #RUNNING
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int RUNNING_VALUE = 0;

	/**
	 * The '<em><b>STEPPING INTO</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> The {@link StackFrame} is stepping into. <!-- end-model-doc -->
	 * 
	 * @see #STEPPING_INTO
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int STEPPING_INTO_VALUE = 1;

	/**
	 * The '<em><b>STEPPING OVER</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> The {@link StackFrame} is stepping over. <!-- end-model-doc -->
	 * 
	 * @see #STEPPING_OVER
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int STEPPING_OVER_VALUE = 2;

	/**
	 * The '<em><b>STEPPING RETURN</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> The {@link StackFrame} is stepping return. <!-- end-model-doc -->
	 * 
	 * @see #STEPPING_RETURN
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int STEPPING_RETURN_VALUE = 3;

	/**
	 * The '<em><b>SUSPENDING</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> The {@link StackFrame} is stepping over. <!-- end-model-doc -->
	 * 
	 * @see #SUSPENDING
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SUSPENDING_VALUE = 4;

	/**
	 * The '<em><b>SUSPENDED</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> The {@link StackFrame} is suspended. <!-- end-model-doc -->
	 * 
	 * @see #SUSPENDED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SUSPENDED_VALUE = 5;

	/**
	 * The '<em><b>TERMINATING</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> The {@link StackFrame} is terminated. <!-- end-model-doc -->
	 * 
	 * @see #TERMINATING
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int TERMINATING_VALUE = 6;

	/**
	 * The '<em><b>TERMINATED</b></em>' literal value. <!-- begin-user-doc --> <!-- end-user-doc --> <!--
	 * begin-model-doc --> The {@link DebugTarget} is terminated. <!-- end-model-doc -->
	 * 
	 * @see #TERMINATED
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int TERMINATED_VALUE = 7;

	/**
	 * An array of all the '<em><b>State</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final State[] VALUES_ARRAY = new State[] {RUNNING, STEPPING_INTO, STEPPING_OVER,
			STEPPING_RETURN, SUSPENDING, SUSPENDED, TERMINATING, TERMINATED, };

	/**
	 * A public read-only list of all the '<em><b>State</b></em>' enumerators. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<State> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>State</b></em>' literal with the specified literal value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param literal
	 *            the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static State get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			State result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>State</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param name
	 *            the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static State getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			State result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>State</b></em>' literal with the specified integer value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static State get(int value) {
		switch (value) {
			case RUNNING_VALUE:
				return RUNNING;
			case STEPPING_INTO_VALUE:
				return STEPPING_INTO;
			case STEPPING_OVER_VALUE:
				return STEPPING_OVER;
			case STEPPING_RETURN_VALUE:
				return STEPPING_RETURN;
			case SUSPENDING_VALUE:
				return SUSPENDING;
			case SUSPENDED_VALUE:
				return SUSPENDED;
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
	private State(int value, String name, String literal) {
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

} // State
