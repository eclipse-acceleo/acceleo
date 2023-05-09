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
package org.eclipse.acceleo.query.runtime.servicelookup;

public class ServicesClass {

	public String service0(String arg) {
		return "service0";
	}

	public String service1(String arg) {
		return "service1";
	}

	public String service2(String arg) {
		return "service1";
	}

	public String service3(Object obj) {
		return obj.toString();
	}

	public String service4(Integer obj) {
		return obj.toString();
	}

	public String service4(Object obj) {
		return obj.toString();
	}

	public String service4(String str) {
		return str;
	}

	public String service4(Number obj) {
		return obj.toString();
	}

	/**
	 * Higher of a series of three overridden methods
	 * 
	 * @param obj1
	 * @param nbr1
	 * @param nbr2
	 * @return
	 */
	public String service5(Object obj1, Number nbr1, Number nbr2) {
		return obj1.toString() + nbr1.toString() + nbr2.toString();
	}

	/**
	 * Second level of the series of three overriden methods.
	 * 
	 * @param int1
	 * @param int2
	 * @param nbr2
	 * @return
	 */
	public String service5(Integer int1, Integer int2, Number nbr2) {
		return int1.toString() + int2.toString() + nbr2.toString();
	}

	/**
	 * Third level of the series of three overriden methods.
	 * 
	 * @param int1
	 * @param int2
	 * @param int3
	 * @return
	 */
	public String service5(Integer int1, Integer int2, Integer int3) {
		return int1.toString() + int2.toString() + int3.toString();
	}

	/**
	 * A method which the two firt parameter types are comparable to the higher level of the series of three
	 * overriden methods but the last one is incomparable with no third parameter of those methods.
	 * 
	 * @param str1
	 * @param i
	 * @param str2
	 * @return
	 */
	public String service5(String str1, Number i, String str2) {
		return str1 + i + str2;
	}
}
