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
	 * The documentation of the method.
	 */
	String value();

	/**
	 * The input parameters of the method.
	 */
	Param[] params() default {};

	/**
	 * The documentation of the result.
	 */
	String result() default "";

	/**
	 * The exceptions that can be thrown.
	 */
	Throw[] exceptions() default {};

	/**
	 * The examples for this service.
	 */
	Example[] examples() default {};

	/**
	 * Additional comments for the example of this service.
	 */
	String comment() default "";
}