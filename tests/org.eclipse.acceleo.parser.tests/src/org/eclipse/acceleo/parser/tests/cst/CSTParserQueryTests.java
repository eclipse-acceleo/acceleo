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
import org.eclipse.acceleo.parser.cst.Query;
import org.junit.Test;

public class CSTParserQueryTests extends AbstractCSTParserTests {

	@Test
	public void testParseQueryHeaderWithOneParameter() {
		StringBuffer buffer = new StringBuffer("public class2Java(c : Class) : OclAny = self"); //$NON-NLS-1$
		testParseQueryHeader(buffer, 0, 0, 0);
	}

	@Test
	public void testParseQueryHeaderWithTwoParameters() {
		StringBuffer buffer = new StringBuffer("public class2Java(c1 : Class, c2 : Class) : OclAny = self"); //$NON-NLS-1$
		testParseQueryHeader(buffer, 0, 0, 0);
	}

	@Test
	public void testParseQueryHeaderWithDuplicatedParameters() {
		StringBuffer buffer = new StringBuffer("public class2Java(dup : Class, dup : Class) : OclAny = self"); //$NON-NLS-1$
		testParseQueryHeader(buffer, 0, 0, 1);
	}

	@Test
	public void testParseQueryHeaderWithBadParameter() {
		StringBuffer buffer = new StringBuffer("public class2Java(c1 : Class, c2 - Class) : OclAny = self"); //$NON-NLS-1$
		testParseQueryHeader(buffer, 0, 0, 1);
	}

	private void testParseQueryHeader(StringBuffer buffer, int infosCount, int warningsCount,
			int problemsCount) {
		StringBuffer moduleBuffer = new StringBuffer("mymodule(http://www.eclipse.org/uml2/2.1.0/UML)"); //$NON-NLS-1$
		AcceleoSourceBuffer moduleSource = new AcceleoSourceBuffer(moduleBuffer);
		Module eModule = CstFactory.eINSTANCE.createModule();
		CSTParser moduleParser = new CSTParser(moduleSource);
		moduleParser.parseModuleHeader(0, moduleBuffer.length(), eModule);
		Query eQuery = CstFactory.eINSTANCE.createQuery();
		eModule.getOwnedModuleElement().add(eQuery);
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(buffer);
		CSTParser parser = new CSTParser(source);
		parser.parseQueryHeader(0, buffer.length(), eQuery);
		checkProblems(source, problemsCount);
		checkWarnings(source, warningsCount);
		checkInfos(source, infosCount);
	}
}
