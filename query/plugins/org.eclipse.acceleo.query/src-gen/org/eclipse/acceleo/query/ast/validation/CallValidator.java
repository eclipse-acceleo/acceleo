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
package org.eclipse.acceleo.query.ast.validation;

import org.eclipse.acceleo.query.ast.CallType;
import org.eclipse.acceleo.query.ast.Expression;
import org.eclipse.emf.common.util.EList;

/**
 * A sample validator interface for {@link org.eclipse.acceleo.query.ast.Call}. This doesn't really do
 * anything, and it's not a real EMF artifact. It was generated by the
 * org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be
 * extended. This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface CallValidator {
	boolean validate();

	boolean validateServiceName(String value);

	boolean validateType(CallType value);

	boolean validateArguments(EList<Expression> value);
}