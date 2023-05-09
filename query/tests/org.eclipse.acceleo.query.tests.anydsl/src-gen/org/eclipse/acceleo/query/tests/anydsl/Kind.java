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
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Kind</b></em>', and
 * utility methods for working with them. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getKind()
 * @model
 * @generated
 */
public enum Kind implements Enumerator {
	/**
	 * The '<em><b>Other</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #OTHER_VALUE
	 * @generated
	 * @ordered
	 */
	OTHER(0, "Other", "Other"),

	/**
	 * The '<em><b>Seed</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #SEED_VALUE
	 * @generated
	 * @ordered
	 */
	SEED(1, "Seed", "Seed"),

	/**
	 * The '<em><b>Oilseed</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #OILSEED_VALUE
	 * @generated
	 * @ordered
	 */
	OILSEED(2, "Oilseed", "Oilseed"),

	/**
	 * The '<em><b>Tree</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #TREE_VALUE
	 * @generated
	 * @ordered
	 */
	TREE(3, "Tree", "Tree"),

	/**
	 * The '<em><b>Root</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #ROOT_VALUE
	 * @generated
	 * @ordered
	 */
	ROOT(4, "Root", "Root"),

	/**
	 * The '<em><b>Bulb</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #BULB_VALUE
	 * @generated
	 * @ordered
	 */
	BULB(5, "Bulb", "Bulb"),

	/**
	 * The '<em><b>Leaf</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #LEAF_VALUE
	 * @generated
	 * @ordered
	 */
	LEAF(6, "Leaf", "Leaf"),

	/**
	 * The '<em><b>Stem</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #STEM_VALUE
	 * @generated
	 * @ordered
	 */
	STEM(7, "Stem", "Stem"),

	/**
	 * The '<em><b>Flower</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #FLOWER_VALUE
	 * @generated
	 * @ordered
	 */
	FLOWER(8, "Flower", "Flower"),

	/**
	 * The '<em><b>Inflorescence</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #INFLORESCENCE_VALUE
	 * @generated
	 * @ordered
	 */
	INFLORESCENCE(9, "Inflorescence", "Inflorescence"),

	/**
	 * The '<em><b>Spice</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #SPICE_VALUE
	 * @generated
	 * @ordered
	 */
	SPICE(10, "Spice", "Spice");

	/**
	 * The '<em><b>Other</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Other</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #OTHER
	 * @model name="Other"
	 * @generated
	 * @ordered
	 */
	public static final int OTHER_VALUE = 0;

	/**
	 * The '<em><b>Seed</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Seed</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #SEED
	 * @model name="Seed"
	 * @generated
	 * @ordered
	 */
	public static final int SEED_VALUE = 1;

	/**
	 * The '<em><b>Oilseed</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Oilseed</b></em>' literal object isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #OILSEED
	 * @model name="Oilseed"
	 * @generated
	 * @ordered
	 */
	public static final int OILSEED_VALUE = 2;

	/**
	 * The '<em><b>Tree</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Tree</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #TREE
	 * @model name="Tree"
	 * @generated
	 * @ordered
	 */
	public static final int TREE_VALUE = 3;

	/**
	 * The '<em><b>Root</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Root</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #ROOT
	 * @model name="Root"
	 * @generated
	 * @ordered
	 */
	public static final int ROOT_VALUE = 4;

	/**
	 * The '<em><b>Bulb</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Bulb</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #BULB
	 * @model name="Bulb"
	 * @generated
	 * @ordered
	 */
	public static final int BULB_VALUE = 5;

	/**
	 * The '<em><b>Leaf</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Leaf</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #LEAF
	 * @model name="Leaf"
	 * @generated
	 * @ordered
	 */
	public static final int LEAF_VALUE = 6;

	/**
	 * The '<em><b>Stem</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Stem</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #STEM
	 * @model name="Stem"
	 * @generated
	 * @ordered
	 */
	public static final int STEM_VALUE = 7;

	/**
	 * The '<em><b>Flower</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Flower</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #FLOWER
	 * @model name="Flower"
	 * @generated
	 * @ordered
	 */
	public static final int FLOWER_VALUE = 8;

	/**
	 * The '<em><b>Inflorescence</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Inflorescence</b></em>' literal object isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #INFLORESCENCE
	 * @model name="Inflorescence"
	 * @generated
	 * @ordered
	 */
	public static final int INFLORESCENCE_VALUE = 9;

	/**
	 * The '<em><b>Spice</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Spice</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #SPICE
	 * @model name="Spice"
	 * @generated
	 * @ordered
	 */
	public static final int SPICE_VALUE = 10;

	/**
	 * An array of all the '<em><b>Kind</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final Kind[] VALUES_ARRAY = new Kind[] {OTHER, SEED, OILSEED, TREE, ROOT, BULB, LEAF,
			STEM, FLOWER, INFLORESCENCE, SPICE, };

	/**
	 * A public read-only list of all the '<em><b>Kind</b></em>' enumerators. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<Kind> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Kind</b></em>' literal with the specified literal value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static Kind get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Kind result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Kind</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static Kind getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Kind result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Kind</b></em>' literal with the specified integer value. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static Kind get(int value) {
		switch (value) {
			case OTHER_VALUE:
				return OTHER;
			case SEED_VALUE:
				return SEED;
			case OILSEED_VALUE:
				return OILSEED;
			case TREE_VALUE:
				return TREE;
			case ROOT_VALUE:
				return ROOT;
			case BULB_VALUE:
				return BULB;
			case LEAF_VALUE:
				return LEAF;
			case STEM_VALUE:
				return STEM;
			case FLOWER_VALUE:
				return FLOWER;
			case INFLORESCENCE_VALUE:
				return INFLORESCENCE;
			case SPICE_VALUE:
				return SPICE;
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
	private Kind(int value, String name, String literal) {
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

} // Kind
