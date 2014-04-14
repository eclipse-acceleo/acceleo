/*******************************************************************************
 * Copyright (c) 2006, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.unit.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The query test interface defines a java annotation for the Acceleo Unit test. This annotation identifies an
 * Acceleo Unit test function and matches the query with the "qualifiedName" and the "index" attributes. The
 * "qualifiedName" attribute specifies the query name to use in the module under test. The "index" attribute
 * is optional and specifies if we have several queries with the same name but with a different argument
 * signature, the index of the template targeted.
 * 
 * @author <a href="mailto:hugo.marchadour@obeo.fr">Hugo Marchadour</a>
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface QueryTest {

	/**
	 * The qualified name getter.
	 */
	String qualifiedName();

	/**
	 * The index getter.
	 */
	int index() default 0;
}
