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
package org.eclipse.acceleo.unit.core.matchers;

import org.eclipse.emf.ecore.EObject;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * The matcher instance of class match an EObject with it type.
 * 
 * @author <a href="mailto:hugo.marchadour@obeo.fr">Hugo Marchadour</a>
 */
public class MatcherInstanceOf extends BaseMatcher<EObject> {

	/**
	 * The class type to match.
	 */
	private Class<? extends EObject> clazz;

	/**
	 * The constructor.
	 * 
	 * @param clazz
	 *            The class type.
	 */
	public MatcherInstanceOf(Class<? extends EObject> clazz) {
		this.clazz = clazz;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.hamcrest.Matcher#matches(java.lang.Object)
	 */
	public boolean matches(Object item) {
		return clazz.isInstance(item);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
	 */
	public void describeTo(Description description) {
		// TODO Auto-generated method stub

	}
}
