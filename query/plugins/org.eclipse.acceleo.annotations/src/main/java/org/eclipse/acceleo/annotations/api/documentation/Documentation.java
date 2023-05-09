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
 * This annotation is used to document a Java method that will be used as a service by AQL.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD })
public @interface Documentation {
	/**
	 * Gets the documentation of the method.
	 * 
	 * @return the documentation of the method
	 */
	String value();

	/**
	 * Gets the input parameters of the method.
	 * 
	 * @return the input parameters of the method
	 */
	Param[] params() default {};

	/**
	 * Gets the documentation of the result.
	 * 
	 * @return the documentation of the result
	 */
	String result() default "";

	/**
	 * Gets the exceptions that can be thrown.
	 * 
	 * @return the exceptions that can be thrown
	 */
	Throw[] exceptions() default {};

	/**
	 * Gets the examples for this service.
	 * 
	 * @return the examples for this service
	 */
	Example[] examples() default {};

	/**
	 * Gets additional comments for the example of this service.
	 * 
	 * @return additional comments for the example of this service
	 */
	String comment() default "";
}
