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
 * <!-- begin-user-doc --> A representation of the literals of the enumeration '<em><b>Continent</b></em>',
 * and utility methods for working with them. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.query.tests.anydsl.AnydslPackage#getContinent()
 * @model
 * @generated
 */
public enum Continent implements Enumerator {
	/**
	 * The '<em><b>Europe</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #EUROPE_VALUE
	 * @generated
	 * @ordered
	 */
	EUROPE(0, "Europe", "Europe"),

	/**
	 * The '<em><b>Asia</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #ASIA_VALUE
	 * @generated
	 * @ordered
	 */
	ASIA(1, "Asia", "Asia"),

	/**
	 * The '<em><b>Africa</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #AFRICA_VALUE
	 * @generated
	 * @ordered
	 */
	AFRICA(2, "Africa", "Africa"),

	/**
	 * The '<em><b>America</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #AMERICA_VALUE
	 * @generated
	 * @ordered
	 */
	AMERICA(3, "America", "America"),

	/**
	 * The '<em><b>Australia</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #AUSTRALIA_VALUE
	 * @generated
	 * @ordered
	 */
	AUSTRALIA(4, "Australia", "Australia"),

	/**
	 * The '<em><b>Antarctica</b></em>' literal object. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #ANTARCTICA_VALUE
	 * @generated
	 * @ordered
	 */
	ANTARCTICA(5, "Antarctica", "Antarctica");

	/**
	 * The '<em><b>Europe</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Europe</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #EUROPE
	 * @model name="Europe"
	 * @generated
	 * @ordered
	 */
	public static final int EUROPE_VALUE = 0;

	/**
	 * The '<em><b>Asia</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Asia</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #ASIA
	 * @model name="Asia"
	 * @generated
	 * @ordered
	 */
	public static final int ASIA_VALUE = 1;

	/**
	 * The '<em><b>Africa</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Africa</b></em>' literal object isn't clear, there really should be more of a
	 * description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #AFRICA
	 * @model name="Africa"
	 * @generated
	 * @ordered
	 */
	public static final int AFRICA_VALUE = 2;

	/**
	 * The '<em><b>America</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>America</b></em>' literal object isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #AMERICA
	 * @model name="America"
	 * @generated
	 * @ordered
	 */
	public static final int AMERICA_VALUE = 3;

	/**
	 * The '<em><b>Australia</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Australia</b></em>' literal object isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #AUSTRALIA
	 * @model name="Australia"
	 * @generated
	 * @ordered
	 */
	public static final int AUSTRALIA_VALUE = 4;

	/**
	 * The '<em><b>Antarctica</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Antarctica</b></em>' literal object isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #ANTARCTICA
	 * @model name="Antarctica"
	 * @generated
	 * @ordered
	 */
	public static final int ANTARCTICA_VALUE = 5;

	/**
	 * An array of all the '<em><b>Continent</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	private static final Continent[] VALUES_ARRAY = new Continent[] {EUROPE, ASIA, AFRICA, AMERICA,
			AUSTRALIA, ANTARCTICA, };

	/**
	 * A public read-only list of all the '<em><b>Continent</b></em>' enumerators. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<Continent> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Continent</b></em>' literal with the specified literal value. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static Continent get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Continent result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Continent</b></em>' literal with the specified name. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static Continent getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			Continent result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Continent</b></em>' literal with the specified integer value. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static Continent get(int value) {
		switch (value) {
			case EUROPE_VALUE:
				return EUROPE;
			case ASIA_VALUE:
				return ASIA;
			case AFRICA_VALUE:
				return AFRICA;
			case AMERICA_VALUE:
				return AMERICA;
			case AUSTRALIA_VALUE:
				return AUSTRALIA;
			case ANTARCTICA_VALUE:
				return ANTARCTICA;
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
	private Continent(int value, String name, String literal) {
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

} // Continent
