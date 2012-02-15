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
import org.junit.Test;

/**
 * Those tests will check if we correctly parse the module header and all kind of problems, warnings or
 * informations.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class CSTParserModuleTests extends AbstractCSTParserTests {

	@Test
	public void testParseModuleHeaderUML() {
		StringBuffer buffer = new StringBuffer("mymodule(http://www.eclipse.org/uml2/2.1.0/UML)"); //$NON-NLS-1$
		testParseModuleHeader(buffer, 0, 0, 0);
	}

	@Test
	public void testParseModuleHeaderUMLExtendsOneModule() {
		StringBuffer buffer = new StringBuffer(
				"mymodule(http://www.eclipse.org/uml2/2.1.0/UML) extends mymodule1"); //$NON-NLS-1$
		testParseModuleHeader(buffer, 0, 1, 0);
	}

	@Test
	public void testParseModuleHeaderUMLExtendsTwoModules() {
		StringBuffer buffer = new StringBuffer(
				"mymodule(http://www.eclipse.org/uml2/2.1.0/UML) extends mymodule1, mymodule2"); //$NON-NLS-1$
		testParseModuleHeader(buffer, 0, 2, 0);
	}

	@Test
	public void testParseModuleIsMissing() {
		StringBuffer buffer = new StringBuffer("[template name()][/template]"); //$NON-NLS-1$
		checkCSTInvalidParsing(buffer, 0, 0, 1);
	}

	@Test
	public void testParseModuleIsNotTerminated() {
		StringBuffer buffer = new StringBuffer(
				"[module library2text(http:///org/eclipse/emf/examples/library/extlibrary.ecore/1.0.0)/]"); //$NON-NLS-1$
		checkCSTParsing(buffer, 0, 0, 0);

		buffer = new StringBuffer(
				"[module library2text(http:///org/eclipse/emf/examples/library/extlibrary.ecore/1.0.0)"); //$NON-NLS-1$
		checkCSTInvalidParsing(buffer, 0, 0, 1);
	}

	@Test
	public void testParseModuleTextBeforeHeader() {
		StringBuffer buffer = new StringBuffer(
				"[module library2text(http:///org/eclipse/emf/examples/library/extlibrary.ecore/1.0.0)/]"); //$NON-NLS-1$
		checkCSTParsing(buffer, 0, 0, 0);

		buffer = new StringBuffer("blablablablabla" //$NON-NLS-1$
				+ "[module library2text(http:///org/eclipse/emf/examples/library/extlibrary.ecore/1.0.0)/]"); //$NON-NLS-1$
		checkCSTParsing(buffer, 0, 1, 0);

		buffer = new StringBuffer("[comment @TODO do something later/]" + "blablablablabla" //$NON-NLS-1$ //$NON-NLS-2$
				+ "[module library2text(http:///org/eclipse/emf/examples/library/extlibrary.ecore/1.0.0)/]"); //$NON-NLS-1$
		checkCSTParsing(buffer, 1, 1, 0);

		buffer = new StringBuffer("[comment encoding=UTF-8/]" + "blablablablabla" //$NON-NLS-1$ //$NON-NLS-2$
				+ "[comment @TODO do something later/]" + "blablablablabla" //$NON-NLS-1$ //$NON-NLS-2$
				+ "[module library2text(http:///org/eclipse/emf/examples/library/extlibrary.ecore/1.0.0)/]"); //$NON-NLS-1$
		checkCSTParsing(buffer, 1, 2, 0);

		buffer = new StringBuffer("[** @TODO do something later/]" + "blablablablabla" //$NON-NLS-1$ //$NON-NLS-2$
				+ "[module library2text(http:///org/eclipse/emf/examples/library/extlibrary.ecore/1.0.0)/]"); //$NON-NLS-1$
		checkCSTParsing(buffer, 1, 1, 0);

		buffer = new StringBuffer("[comment @FIXME do something later/]" + "blablablablabla" //$NON-NLS-1$ //$NON-NLS-2$
				+ "[module library2text(http:///org/eclipse/emf/examples/library/extlibrary.ecore/1.0.0)/]"); //$NON-NLS-1$
		checkCSTParsing(buffer, 1, 1, 0);

		buffer = new StringBuffer("[** @FIXME do something later/]" + "blablablablabla" //$NON-NLS-1$ //$NON-NLS-2$
				+ "[module library2text(http:///org/eclipse/emf/examples/library/extlibrary.ecore/1.0.0)/]"); //$NON-NLS-1$
		checkCSTParsing(buffer, 1, 1, 0);

		// In case of an invalid parsing, @TODO, @FIXME and the text before the module are not detected
		buffer = new StringBuffer("blablablablabla" //$NON-NLS-1$
				+ "[module library2text(http:///org/eclipse/emf/examples/library/extlibrary.ecore/1.0.0)"); //$NON-NLS-1$
		checkCSTInvalidParsing(buffer, 0, 0, 1);

		buffer = new StringBuffer("[comment @TODO do something later/]" + "blablablablabla" //$NON-NLS-1$ //$NON-NLS-2$
				+ "[module library2text(http:///org/eclipse/emf/examples/library/extlibrary.ecore/1.0.0)"); //$NON-NLS-1$
		checkCSTInvalidParsing(buffer, 0, 0, 1);

		buffer = new StringBuffer("[comment encoding=UTF-8/]" + "blablablablabla" //$NON-NLS-1$ //$NON-NLS-2$
				+ "[comment @TODO do something later/]" + "blablablablabla" //$NON-NLS-1$ //$NON-NLS-2$
				+ "[module library2text(http:///org/eclipse/emf/examples/library/extlibrary.ecore/1.0.0)"); //$NON-NLS-1$
		checkCSTInvalidParsing(buffer, 0, 0, 1);

		buffer = new StringBuffer("[** @TODO do something later/]" + "blablablablabla" //$NON-NLS-1$ //$NON-NLS-2$
				+ "[module library2text(http:///org/eclipse/emf/examples/library/extlibrary.ecore/1.0.0)"); //$NON-NLS-1$
		checkCSTInvalidParsing(buffer, 0, 0, 1);

		buffer = new StringBuffer("[comment @FIXME do something later/]" + "blablablablabla" //$NON-NLS-1$ //$NON-NLS-2$
				+ "[module library2text(http:///org/eclipse/emf/examples/library/extlibrary.ecore/1.0.0)"); //$NON-NLS-1$
		checkCSTInvalidParsing(buffer, 0, 0, 1);

		buffer = new StringBuffer("[** @FIXME do something later/]" + "blablablablabla" //$NON-NLS-1$ //$NON-NLS-2$
				+ "[module library2text(http:///org/eclipse/emf/examples/library/extlibrary.ecore/1.0.0)"); //$NON-NLS-1$
		checkCSTInvalidParsing(buffer, 0, 0, 1);
	}

	@Test
	public void testParseInvalidCommentBeforeModule() {
		StringBuffer buffer = new StringBuffer("[comment encoding=UTF-8]" + "blablablablabla" //$NON-NLS-1$ //$NON-NLS-2$
				+ "[comment @TODO do something later/]" + "blablablablabla" //$NON-NLS-1$ //$NON-NLS-2$
				+ "[module library2text(http:///org/eclipse/emf/examples/library/extlibrary.ecore/1.0.0)/]"); //$NON-NLS-1$
		checkCSTParsing(buffer, 1, 1, 1);
	}

	@Test
	public void testParseInvalidDocumentationBlockBeforeModule() {
		StringBuffer buffer = new StringBuffer("[comment encoding=UTF-8/]" + "blablablablabla" //$NON-NLS-1$ //$NON-NLS-2$
				+ "[** @TODO do something later]" + "blablablablabla" //$NON-NLS-1$ //$NON-NLS-2$
				+ "[module library2text(http:///org/eclipse/emf/examples/library/extlibrary.ecore/1.0.0)/]"); //$NON-NLS-1$
		checkCSTParsing(buffer, 0, 0, 1);
	}

	private void testParseModuleHeader(StringBuffer buffer, int infos, int warnings, int problems) {
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(buffer);
		CSTParser parser = new CSTParser(source);
		Module eModule = CstFactory.eINSTANCE.createModule();
		parser.parseModuleHeader(0, buffer.length(), eModule);
		checkProblems(source, problems);
		checkWarnings(source, warnings);
		checkInfos(source, infos);
	}
}
