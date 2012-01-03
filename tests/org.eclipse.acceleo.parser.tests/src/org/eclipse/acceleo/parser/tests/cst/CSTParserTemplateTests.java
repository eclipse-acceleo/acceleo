/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.parser.tests.cst;

import org.eclipse.acceleo.internal.parser.cst.CSTParser;
import org.eclipse.acceleo.parser.AcceleoSourceBuffer;
import org.eclipse.acceleo.parser.cst.CstFactory;
import org.eclipse.acceleo.parser.cst.Module;
import org.eclipse.acceleo.parser.cst.Template;
import org.junit.Test;

public class CSTParserTemplateTests extends AbstractCSTParserTests {

	@Test
	public void testParseTemplateHeaderWithOneParameter() {
		StringBuffer buffer = new StringBuffer("public class2Java(c : Class)"); //$NON-NLS-1$
		testParseTemplateHeader(buffer, 0, 0, 0);
	}

	@Test
	public void testParseTemplateHeaderWithTwoParameters() {
		StringBuffer buffer = new StringBuffer("public class2Java(c1 : Class, c2 : Class)"); //$NON-NLS-1$
		testParseTemplateHeader(buffer, 0, 0, 0);
	}

	@Test
	public void testParseTemplateHeaderWithDuplicatedParameters() {
		StringBuffer buffer = new StringBuffer("public class2Java(dup : Class, dup : Class)"); //$NON-NLS-1$
		testParseTemplateHeader(buffer, 0, 0, 1);
	}

	@Test
	public void testParseTemplateHeaderWithBadParameter() {
		StringBuffer buffer = new StringBuffer("public class2Java(c1 : Class, c2 - Class)"); //$NON-NLS-1$
		testParseTemplateHeader(buffer, 0, 0, 1);
	}

	@Test
	public void testParseTemplateHeaderWithOverrides() {
		StringBuffer buffer = new StringBuffer(
				"public class2Java(c1 : Class) overrides class2Java, class2Java"); //$NON-NLS-1$
		testParseTemplateHeader(buffer, 0, 0, 0);
	}

	@Test
	public void testParseTemplateHeaderWithOverridesGuard() {
		StringBuffer buffer = new StringBuffer(
				"public class2Java(c1 : Class) overrides class2Java ? (c1.name = '')"); //$NON-NLS-1$
		testParseTemplateHeader(buffer, 0, 0, 0);
	}

	@Test
	public void testParseTemplateHeaderWithOverridesGuardParenthesisAreRequired() {
		StringBuffer buffer = new StringBuffer(
				"public class2Java(c1 : Class) overrides class2Java ? c1.name = ''"); //$NON-NLS-1$
		testParseTemplateHeader(buffer, 0, 0, 1);
	}

	@Test
	public void testParseTemplateHeaderWithOverridesGuardInitSection() {
		StringBuffer buffer = new StringBuffer(
				"public class2Java(c1 : Class) overrides class2Java ? (c1.name = '') {c2:Class;}"); //$NON-NLS-1$
		testParseTemplateHeader(buffer, 0, 0, 0);
	}

	@Test
	public void testParseTemplateHeaderWithOverridesGuardPostInitSection() {
		StringBuffer buffer = new StringBuffer(
				"public class2Java(c1 : Class) overrides class2Java ? (c1.name = '') post (trim()) {c2:Class;}"); //$NON-NLS-1$
		testParseTemplateHeader(buffer, 0, 0, 0);
	}

	@Test
	public void testParseTemplateHeaderWithPostGuardBadSequence() {
		StringBuffer buffer = new StringBuffer(
				"public class2Java(c1 : Class) post (trim()) ? (c1.name = '') "); //$NON-NLS-1$
		testParseTemplateHeader(buffer, 0, 0, 1);
	}

	private void testParseTemplateHeader(StringBuffer buffer, int infosCount, int warningsCount,
			int problemsCount) {
		StringBuffer moduleBuffer = new StringBuffer("mymodule(http://www.eclipse.org/uml2/2.1.0/UML)"); //$NON-NLS-1$
		AcceleoSourceBuffer moduleSource = new AcceleoSourceBuffer(moduleBuffer);
		Module eModule = CstFactory.eINSTANCE.createModule();
		CSTParser moduleParser = new CSTParser(moduleSource);
		moduleParser.parseModuleHeader(0, moduleBuffer.length(), eModule);
		Template eTemplate = CstFactory.eINSTANCE.createTemplate();
		eModule.getOwnedModuleElement().add(eTemplate);
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(buffer);
		CSTParser parser = new CSTParser(source);
		parser.parseTemplateHeader(0, buffer.length(), eTemplate);
		checkProblems(source, problemsCount);
		checkWarnings(source, warningsCount);
		checkInfos(source, infosCount);
	}
}
