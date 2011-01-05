/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ide.ui.tests.editors.template.rules;

import junit.framework.TestCase;

import org.eclipse.acceleo.internal.ide.ui.editors.template.rules.FirstVariableRule;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.rules.RuleBasedScanner;

@SuppressWarnings("nls")
public class FirstVariableRuleTests extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testFirstVariableRuleTemplate() {
		String[] previousWords = {"[", "template", "public", "name", "(" };
		String textBefore = "[template public name(";
		String textRange = "a:Property";
		String textAfter = ")]";
		testFirstVariableRule(previousWords, textBefore, textRange, textAfter, true);
	}

	public void testFirstVariableRuleTemplateWithSpaces() {
		String[] previousWords = {"[", "template", "public", "name", "(" };
		String textBefore = "[   template   public   name   ( \t ";
		String textRange = "a : Property";
		String textAfter = " ) ] ";
		testFirstVariableRule(previousWords, textBefore, textRange, textAfter, true);
	}

	public void testFirstVariableRuleTemplateNotValid() {
		String[] previousWords = {"[", "template", "public", "name", "(" };
		String textBefore = "[template public BAD_STRING name(";
		String textRange = "a:Property";
		String textAfter = ")]";
		testFirstVariableRule(previousWords, textBefore, textRange, textAfter, false);
	}

	public void testFirstVariableRuleTemplateWithUnknownParts() {
		String[] previousWords = {"[", "template", "*", "*", "(" };
		String textBefore = "[template public name(";
		String textRange = "a:Property";
		String textAfter = ")]";
		testFirstVariableRule(previousWords, textBefore, textRange, textAfter, true);
	}

	private void testFirstVariableRule(String[] previousWords, String textBefore, String textRange,
			String textAfter, boolean isValid) {
		RuleBasedScanner scanner = new RuleBasedScanner();
		Document document = new Document(textBefore + textRange + textAfter);
		scanner.setRange(document, textBefore.length(), textRange.length());
		FirstVariableRule rule = new FirstVariableRule(previousWords, null);
		if (isValid) {
			assertEquals(rule.read(scanner), textRange.length());
		} else {
			assertNotSame(rule.read(scanner), textRange.length());
		}
	}

}
