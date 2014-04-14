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
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.ecore.PropertyCallExp;
import org.eclipse.ocl.ecore.StringLiteralExp;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * The matcher text content class match a text with an OCLExpression. For example a literalExp, the text will
 * be able match with literalExp.getStringSymbol().
 * 
 * @author <a href="mailto:hugo.marchadour@obeo.fr">Hugo Marchadour</a>
 */
public class MatcherTextContent extends BaseMatcher<EObject> {

	/**
	 * The string matcher.
	 */
	private Matcher<String> stringMatcher;

	/**
	 * The constructor.
	 * 
	 * @param stringMatcher
	 *            The string matcher.
	 */
	public MatcherTextContent(Matcher<String> stringMatcher) {

		this.stringMatcher = stringMatcher;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.hamcrest.Matcher#matches(java.lang.Object)
	 */
	public boolean matches(Object item) {
		boolean result = false;

		if (item instanceof EObject) {
			OCLExpression oclExpression = (OCLExpression)item;
			if (oclExpression instanceof StringLiteralExp) {
				StringLiteralExp literalExp = (StringLiteralExp)oclExpression;
				result = stringMatcher.matches(literalExp.getStringSymbol());

			} else if (oclExpression instanceof PropertyCallExp) {
				PropertyCallExp propertyExp = (PropertyCallExp)oclExpression;
				result = stringMatcher.matches(propertyExp.toString());
			} else {
				throw new UnsupportedOperationException(
						"The template element " + oclExpression + "is not supported by the MatcherTextContent class"); //$NON-NLS-1$ //$NON-NLS-2$
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
