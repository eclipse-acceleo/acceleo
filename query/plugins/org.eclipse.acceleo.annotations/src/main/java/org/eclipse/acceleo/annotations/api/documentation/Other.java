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
package org.eclipse.acceleo.annotations.api.documentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to document an example of the expression in other languages.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD })
public @interface Other {
	/**
	 * The identifier for the Acceleo 3 (MTL) language.
	 */
	String ACCELEO_3 = "Acceleo 3 (MTL)"; //$NON-NLS-1$

	/**
	 * The identifier for the Acceleo 2 language.
	 */
	String ACCELEO_2 = "Acceleo 2"; //$NON-NLS-1$

	/**
	 * Gets the name of the language.
	 * 
	 * @return the name of the language
	 */
	String language();

	/**
	 * Gets the expression.
	 * 
	 * @return the expression
	 */
	String expression();

	/**
	 * Gets the result.
	 * 
	 * @return the result
	 */
	String result();
}
