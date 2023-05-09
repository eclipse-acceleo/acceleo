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
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Group</b></em>', and
 * utility methods for working with them. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getGroup()
 * @model
 * @generated
 */
public enum Group implements Enumerator {
	/**
	 * The '<em><b>Water</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #WATER_VALUE
	 * @generated
	 * @ordered
	 */
	WATER(0, "Water", "Water"),

	/**
	 * The '<em><b>Dairy</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #DAIRY_VALUE
	 * @generated
	 * @ordered
	 */
	DAIRY(1, "Dairy", "Dairy"),

	/**
	 * The '<em><b>Fruit</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #FRUIT_VALUE
	 * @generated
	 * @ordered
	 */
	FRUIT(2, "Fruit", "Fruit"),

	/**
	 * The '<em><b>Grain</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #GRAIN_VALUE
	 * @generated
	 * @ordered
	 */
	GRAIN(3, "Grain", "Grain"),

	/**
	 * The '<em><b>Protein</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #PROTEIN_VALUE
	 * @generated
	 * @ordered
	 */
	PROTEIN(4, "Protein", "Protein"),

	/**
	 * The '<em><b>Sweet</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #SWEET_VALUE
	 * @generated
	 * @ordered
	 */
	SWEET(5, "Sweet", "Sweet"),

	/**
	 * The '<em><b>Vegetable</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #VEGETABLE_VALUE
	 * @generated
	 * @ordered
	 */
	VEGETABLE(6, "Vegetable", "Vegetable"),

	/**
	 * The '<em><b>Alcohol</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #ALCOHOL_VALUE
	 * @generated
	 * @ordered
	 */
	ALCOHOL(7, "Alcohol", "Alcohol");

	/**
	 * The '<em><b>Water</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Water</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #WATER
	 * @model name="Water"
	 * @generated
	 * @ordered
	 */
	public static final int WATER_VALUE = 0;

	/**
	 * The '<em><b>Dairy</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Dairy</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #DAIRY
	 * @model name="Dairy"
	 * @generated
	 * @ordered
	 */
	public static final int DAIRY_VALUE = 1;

	/**
	 * The '<em><b>Fruit</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Fruit</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #FRUIT
	 * @model name="Fruit"
	 * @generated
	 * @ordered
	 */
	public static final int FRUIT_VALUE = 2;

	/**
	 * The '<em><b>Grain</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Grain</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #GRAIN
	 * @model name="Grain"
	 * @generated
	 * @ordered
	 */
	public static final int GRAIN_VALUE = 3;

	/**
	 * The '<em><b>Protein</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Protein</b></em>' literal object isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #PROTEIN
	 * @model name="Protein"
	 * @generated
	 * @ordered
	 */
	public static final int PROTEIN_VALUE = 4;

	/**
	 * The '<em><b>Sweet</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Sweet</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #SWEET
	 * @model name="Sweet"
	 * @generated
	 * @ordered
	 */
	public static final int SWEET_VALUE = 5;

	/**
	 * The '<em><b>Vegetable</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Vegetable</b></em>' literal object isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #VEGETABLE
	 * @model name="Vegetable"
	 * @generated
	 * @ordered
	 */
	public static final int VEGETABLE_VALUE = 6;

	/**
	 * The '<em><b>Alcohol</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Alcohol</b></em>' literal object isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #ALCOHOL
	 * @model name="Alcohol"
	 * @generated
	 * @ordered
	 */
	public static final int ALCOHOL_VALUE = 7;

	/**
	 * An array of all the '<em><b>Group</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final Group[] VALUES_ARRAY = new Group[] {WATER, DAIRY, FRUIT, GRAIN, PROTEIN, SWEET,
			VEGETABLE, ALCOHOL, };

	/**
	 * A public read-only list of all the '<em><b>Group</b></em>' enumerators. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<Group> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Group</b></em>' literal with the specified literal value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static Group get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Group result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Group</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static Group getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Group result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Group</b></em>' literal with the specified integer value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static Group get(int value) {
		switch (value) {
			case WATER_VALUE:
				return WATER;
			case DAIRY_VALUE:
				return DAIRY;
			case FRUIT_VALUE:
				return FRUIT;
			case GRAIN_VALUE:
				return GRAIN;
			case PROTEIN_VALUE:
				return PROTEIN;
			case SWEET_VALUE:
				return SWEET;
			case VEGETABLE_VALUE:
				return VEGETABLE;
			case ALCOHOL_VALUE:
				return ALCOHOL;
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
	private Group(int value, String name, String literal) {
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

} // Group
