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
 * This annotation is used to document an example of the method.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD })
public @interface Example {
	/**
	 * Gets the expression used in this example.
	 * 
	 * @return the expression used in this example
	 */
	String expression();

	/**
	 * Gets the result of the expression.
	 * 
	 * @return the result of the expression
	 */
	String result();

	/**
	 * Gets examples in other languages.
	 * 
	 * @return examples in other languages
	 */
	Other[] others() default {};
}
