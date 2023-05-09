/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.tests.anydsl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Color</b></em>', and
 * utility methods for working with them. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getColor()
 * @model
 * @generated
 */
public enum Color implements Enumerator {
	/**
	 * The '<em><b>Black</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #BLACK_VALUE
	 * @generated
	 * @ordered
	 */
	BLACK(0, "black", "black"),

	/**
	 * The '<em><b>Red</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #RED_VALUE
	 * @generated
	 * @ordered
	 */
	RED(1, "red", "red"),

	/**
	 * The '<em><b>Green</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #GREEN_VALUE
	 * @generated
	 * @ordered
	 */
	GREEN(2, "green", "green"),

	/**
	 * The '<em><b>Yellow</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #YELLOW_VALUE
	 * @generated
	 * @ordered
	 */
	YELLOW(3, "yellow", "yellow"),

	/**
	 * The '<em><b>Orange</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #ORANGE_VALUE
	 * @generated
	 * @ordered
	 */
	ORANGE(4, "orange", "orange"),

	/**
	 * The '<em><b>Brown</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #BROWN_VALUE
	 * @generated
	 * @ordered
	 */
	BROWN(5, "brown", "brown"),

	/**
	 * The '<em><b>Pink</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #PINK_VALUE
	 * @generated
	 * @ordered
	 */
	PINK(6, "pink", "pink"), /**
	 * The '<em><b>Pal Pink</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #PAL_PINK_VALUE
	 * @generated
	 * @ordered
	 */
	PAL_PINK(7, "palPink", "palPink"), /**
	 * The '<em><b>Very Yellow</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #VERY_YELLOW_VALUE
	 * @generated
	 * @ordered
	 */
	VERY_YELLOW(8, "veryYellow", "veryYellow"), /**
	 * The '<em><b>White</b></em>' literal object. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #WHITE_VALUE
	 * @generated
	 * @ordered
	 */
	WHITE(9, "white", "white");

	/**
	 * The '<em><b>Black</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Black</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #BLACK
	 * @model name="black"
	 * @generated
	 * @ordered
	 */
	public static final int BLACK_VALUE = 0;

	/**
	 * The '<em><b>Red</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Red</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #RED
	 * @model name="red"
	 * @generated
	 * @ordered
	 */
	public static final int RED_VALUE = 1;

	/**
	 * The '<em><b>Green</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Green</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #GREEN
	 * @model name="green"
	 * @generated
	 * @ordered
	 */
	public static final int GREEN_VALUE = 2;

	/**
	 * The '<em><b>Yellow</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Yellow</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #YELLOW
	 * @model name="yellow"
	 * @generated
	 * @ordered
	 */
	public static final int YELLOW_VALUE = 3;

	/**
	 * The '<em><b>Orange</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Orange</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #ORANGE
	 * @model name="orange"
	 * @generated
	 * @ordered
	 */
	public static final int ORANGE_VALUE = 4;

	/**
	 * The '<em><b>Brown</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Brown</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #BROWN
	 * @model name="brown"
	 * @generated
	 * @ordered
	 */
	public static final int BROWN_VALUE = 5;

	/**
	 * The '<em><b>Pink</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Pink</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #PINK
	 * @model name="pink"
	 * @generated
	 * @ordered
	 */
	public static final int PINK_VALUE = 6;

	/**
	 * The '<em><b>Pal Pink</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Pal Pink</b></em>' literal object isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #PAL_PINK
	 * @model name="palPink"
	 * @generated
	 * @ordered
	 */
	public static final int PAL_PINK_VALUE = 7;

	/**
	 * The '<em><b>Very Yellow</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Very Yellow</b></em>' literal object isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #VERY_YELLOW
	 * @model name="veryYellow"
	 * @generated
	 * @ordered
	 */
	public static final int VERY_YELLOW_VALUE = 8;

	/**
	 * The '<em><b>White</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>White</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #WHITE
	 * @model name="white"
	 * @generated
	 * @ordered
	 */
	public static final int WHITE_VALUE = 9;

	/**
	 * An array of all the '<em><b>Color</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final Color[] VALUES_ARRAY = new Color[] {BLACK, RED, GREEN, YELLOW, ORANGE, BROWN, PINK,
			PAL_PINK, VERY_YELLOW, WHITE, };

	/**
	 * A public read-only list of all the '<em><b>Color</b></em>' enumerators. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<Color> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Color</b></em>' literal with the specified literal value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static Color get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Color result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Color</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static Color getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Color result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Color</b></em>' literal with the specified integer value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static Color get(int value) {
		switch (value) {
			case BLACK_VALUE:
				return BLACK;
			case RED_VALUE:
				return RED;
			case GREEN_VALUE:
				return GREEN;
			case YELLOW_VALUE:
				return YELLOW;
			case ORANGE_VALUE:
				return ORANGE;
			case BROWN_VALUE:
				return BROWN;
			case PINK_VALUE:
				return PINK;
			case PAL_PINK_VALUE:
				return PAL_PINK;
			case VERY_YELLOW_VALUE:
				return VERY_YELLOW;
			case WHITE_VALUE:
				return WHITE;
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
	private Color(int value, String name, String literal) {
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

} // Color
