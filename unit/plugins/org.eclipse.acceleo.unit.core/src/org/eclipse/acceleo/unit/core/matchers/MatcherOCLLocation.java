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
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

/**
 * The matcher ocl location class match an OCLExpression location with an begin and end value.
 * 
 * @author <a href="mailto:hugo.marchadour@obeo.fr">Hugo Marchadour</a>
 */
public class MatcherOCLLocation extends BaseMatcher<EObject> {

	/**
	 * the begin index.
	 */
	private int begin;

	/**
	 * the end index.
	 */
	private int end;

	/**
	 * The constructor.
	 * 
	 * @param begin
	 *            the begin index.
	 * @param end
	 *            the end index.
	 */
	public MatcherOCLLocation(int begin, int end) {
		this.begin = begin;
		this.end = end;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.hamcrest.Matcher#matches(java.lang.Object)
	 */
	public boolean matches(Object item) {
		boolean result = false;

		if (item instanceof OCLExpression) {
			OCLExpression oclExpression = (OCLExpression)item;
			result = (oclExpression.getStartPosition() == begin) && (oclExpression.getEndPosition() == end);
		} else {
			throw new IllegalArgumentException("Unsupported type. Use an OCL expression subtype."); //$NON-NLS-1$
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
