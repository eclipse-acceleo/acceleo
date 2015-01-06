/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.services;

/**
 * Boolean services.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class BooleanServices {

	/**
	 * Logical or.
	 * 
	 * @param op1
	 *            the first operand
	 * @param op2
	 *            the second operand
	 * @return the logical or of the two operands.
	 */
	public Boolean or(Boolean op1, Boolean op2) {
		return op1 || op2;
	}

	/**
	 * Logical and.
	 * 
	 * @param op1
	 *            the first operand
	 * @param op2
	 *            the second operand
	 * @return the logical and of the two operands.
	 */
	public Boolean and(Boolean op1, Boolean op2) {
		return op1 && op2;
	}

	/**
	 * Logical negation.
	 * 
	 * @param op1
	 *            the operand
	 * @return the logical negation of the operand.
	 */
	public Boolean not(Boolean op1) {
		return !op1;
	}

}
