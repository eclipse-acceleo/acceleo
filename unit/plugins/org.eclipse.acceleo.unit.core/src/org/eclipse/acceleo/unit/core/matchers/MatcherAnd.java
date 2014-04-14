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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.ecore.OCLExpression;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * The matcher text content class match a text with an OCLExpression. For example a literalExp, the text will
 * be able match with literalExp.getStringSymbol().
 * 
 * @author <a href="mailto:hugo.marchadour@obeo.fr">Hugo Marchadour</a>
 */
public class MatcherAnd extends BaseMatcher<EObject> {

	/**
	 * The string matcher.
	 */
	private List<Matcher<EObject>> matchers;

	/**
	 * The constructor.
	 * 
	 * @param matchers
	 *            all matchers.
	 */
	public MatcherAnd(Matcher<EObject>... matchers) {
		this.matchers = new ArrayList<Matcher<EObject>>();
		for (Matcher<EObject> matcher : matchers) {
			this.matchers.add(matcher);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.hamcrest.Matcher#matches(java.lang.Object)
	 */
	public boolean matches(Object item) {
		boolean result = true;

		if (item instanceof EObject) {
			OCLExpression oclExpression = (OCLExpression)item;
			for (Matcher<EObject> matcher : matchers) {
				result &= matcher.matches(oclExpression);
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.hamcrest.SelfDescribing#describeTo(org.hamcrest.Description)
	 */
	public void describeTo(Description description) {
		//
	}

}
