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
import org.hamcrest.Matcher;

import static org.hamcrest.core.Is.is;

/**
 * The text content static class gives shortcut methods in the same way of hamcrest Is.
 * 
 * @author <a href="mailto:hugo.marchadour@obeo.fr">Hugo Marchadour</a>
 */
public final class TemplateExp {

	/**
	 * The constructor.
	 */
	private TemplateExp() {
		// Never used
	}

	/**
	 * Create an EObject matcher with the textContent.
	 * 
	 * @param textContent
	 *            the text to match.
	 * @return the EObject matcher.
	 */
	public static Matcher<EObject> templateText(String textContent) {
		MatcherTextContent matcherTextContent = new MatcherTextContent(is(textContent));

		return matcherTextContent;
	}

	/**
	 * Create an EObject matcher with begin and end index.
	 * 
	 * @param begin
	 *            the begin index.
	 * @param end
	 *            the end index.
	 * @return the EObject matcher.
	 */
	public static Matcher<EObject> templateLocation(int begin, int end) {
		MatcherOCLLocation matcherOCLLocation = new MatcherOCLLocation(begin, end);

		return matcherOCLLocation;
	}

	/**
	 * Create an EObject matcher that matches all matchers.
	 * 
	 * @param matchers
	 *            all matchers.
	 * @return the EObject matcher.
	 */
	public static Matcher<EObject> and(Matcher<EObject>... matchers) {
		MatcherAnd matcher = new MatcherAnd(matchers);
		return matcher;
	}

	/**
	 * Create an EObject matcher on the instance type.
	 * 
	 * @param clazz
	 *            the class type.
	 * @return the EObject matcher.
	 */
	public static Matcher<EObject> instanceOf(Class<? extends EObject> clazz) {
		MatcherInstanceOf matcher = new MatcherInstanceOf(clazz);
		return matcher;
	}
}
