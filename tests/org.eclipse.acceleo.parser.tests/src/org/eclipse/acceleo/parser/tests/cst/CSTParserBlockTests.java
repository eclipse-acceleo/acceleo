/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.parser.tests.cst;

import junit.framework.TestCase;

import org.eclipse.acceleo.internal.parser.cst.CSTParser;
import org.eclipse.acceleo.internal.parser.cst.CSTParserBlock;
import org.eclipse.acceleo.parser.AcceleoSourceBuffer;
import org.eclipse.acceleo.parser.cst.CstFactory;
import org.eclipse.acceleo.parser.cst.FileBlock;
import org.eclipse.acceleo.parser.cst.ForBlock;
import org.eclipse.acceleo.parser.cst.IfBlock;
import org.eclipse.acceleo.parser.cst.LetBlock;
import org.eclipse.acceleo.parser.cst.Module;
import org.eclipse.acceleo.parser.cst.Template;
import org.eclipse.acceleo.parser.cst.TraceBlock;

@SuppressWarnings("nls")
public class CSTParserBlockTests extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testParseIf() {
		StringBuffer buffer = new StringBuffer("[if(true)] [/if]");
		testParseIf(buffer, 0);
	}

	public void testParseIfElse() {
		StringBuffer buffer = new StringBuffer("[if(true)] [else] [/if]");
		testParseIf(buffer, 0);
	}

	public void testParseIfElseIf() {
		StringBuffer buffer = new StringBuffer("[if(true)] [elseif(true)] [else] [/if]");
		testParseIf(buffer, 0);
	}

	public void testParseIfWithOwnedIf() {
		StringBuffer buffer = new StringBuffer("[if(true)] [if(true)] [elseif(true)] [else] [/if] [/if]");
		testParseIf(buffer, 0);
	}

	public void testParseIfBadSyntaxValidateQuotes() {
		StringBuffer buffer = new StringBuffer("[if(true)] \" [if(true)] \" [elseif(true)] [/if]");
		testParseIf(buffer, 1);
	}

	public void testParseIfValidateQuotes() {
		StringBuffer buffer = new StringBuffer("[if(true)] \" [elseif(true)] [else] [/if]");
		testParseIf(buffer, 0);
	}

	private void testParseIf(StringBuffer buffer, int problemsCount) {
		StringBuffer moduleBuffer = new StringBuffer("mymodule(http://www.eclipse.org/uml2/2.1.0/UML)");
		AcceleoSourceBuffer moduleSource = new AcceleoSourceBuffer(moduleBuffer);
		CSTParser pAcceleomodule = new CSTParser(moduleSource);
		Module eModule = CstFactory.eINSTANCE.createModule();
		pAcceleomodule.parseModuleHeader(0, moduleBuffer.length(), eModule);
		Template eTemplate = CstFactory.eINSTANCE.createTemplate();
		eModule.getOwnedModuleElement().add(eTemplate);
		IfBlock eIf = CstFactory.eINSTANCE.createIfBlock();
		eTemplate.getBody().add(eIf);
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(buffer);
		CSTParser pAcceleo = new CSTParser(source);
		CSTParserBlock parser = new CSTParserBlock(pAcceleo);
		parser.parse(0, buffer.length(), eIf);
		if (source.getProblems().getList().size() != problemsCount) {
			fail("You must have " + problemsCount + " syntax errors : " + source.getProblems().getMessage());
		}
	}

	public void testParseFor() {
		StringBuffer buffer = new StringBuffer("[for (c : Class | null)] [/for]");
		testParseFor(buffer, 0);
	}

	public void testParseForWithOwnedFor() {
		StringBuffer buffer = new StringBuffer(
				"[for (c : Class | null)] [for(a : Property | null)] [/for] [/for]");
		testParseFor(buffer, 0);
	}

	public void testParseForWithBefore() {
		StringBuffer buffer = new StringBuffer("[for (c : Class | null) before (null)] [/for]");
		testParseFor(buffer, 0);
	}

	public void testParseForWithBeforeSeparator() {
		StringBuffer buffer = new StringBuffer(
				"[for (c : Class | null) before (null) separator (',')] [/for]");
		testParseFor(buffer, 0);
	}

	public void testParseForWithBeforeAfter() {
		StringBuffer buffer = new StringBuffer("[for (c : Class | null) before (null)] after (null)] [/for]");
		testParseFor(buffer, 0);
	}

	public void testParseForWithBeforeGuard() {
		StringBuffer buffer = new StringBuffer("[for (c : Class | null) before (null) ? (null)] [/for]");
		testParseFor(buffer, 0);
	}

	public void testParseForWithBeforeGuardInitSection() {
		StringBuffer buffer = new StringBuffer(
				"[for (c1 : Class | null) before (null) ? (null) {c2:Class;} ] [/for]");
		testParseFor(buffer, 0);
	}

	private void testParseFor(StringBuffer buffer, int problemsCount) {
		StringBuffer moduleBuffer = new StringBuffer("mymodule(http://www.eclipse.org/uml2/2.1.0/UML)");
		AcceleoSourceBuffer moduleSource = new AcceleoSourceBuffer(moduleBuffer);
		Module eModule = CstFactory.eINSTANCE.createModule();
		CSTParser pAcceleomodule = new CSTParser(moduleSource);
		pAcceleomodule.parseModuleHeader(0, moduleBuffer.length(), eModule);
		Template eTemplate = CstFactory.eINSTANCE.createTemplate();
		eModule.getOwnedModuleElement().add(eTemplate);
		ForBlock eFor = CstFactory.eINSTANCE.createForBlock();
		eTemplate.getBody().add(eFor);
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(buffer);
		CSTParser pAcceleo = new CSTParser(source);
		CSTParserBlock parser = new CSTParserBlock(pAcceleo);
		parser.parse(0, buffer.length(), eFor);
		if (source.getProblems().getList().size() != problemsCount) {
			fail("You must have " + problemsCount + " syntax errors : " + source.getProblems().getMessage());
		}
	}

	public void testParseLetWithVariable() {
		StringBuffer buffer = new StringBuffer("[let c1 : Class] [/let]");
		testParseLet(buffer, 0);
	}

	public void testParseLetWithoutVariable() {
		StringBuffer buffer = new StringBuffer("[let ] [/let]");
		testParseLet(buffer, 1);
	}

	public void testParseLetElse() {
		StringBuffer buffer = new StringBuffer("[let c:Class] [else] [/let]");
		testParseLet(buffer, 0);
	}

	public void testParseLetElseLet() {
		StringBuffer buffer = new StringBuffer("[let c1:Class] [elselet c2:Class] [else] [/let]");
		testParseLet(buffer, 0);
	}

	public void testParseLetWithOwnedLet() {
		StringBuffer buffer = new StringBuffer(
				"[let c1:Class] [let c2:Class] [elselet c3:Class] [else] [/let] [/let]");
		testParseLet(buffer, 0);
	}

	private void testParseLet(StringBuffer buffer, int problemsCount) {
		StringBuffer moduleBuffer = new StringBuffer("mymodule(http://www.eclipse.org/uml2/2.1.0/UML)");
		AcceleoSourceBuffer moduleSource = new AcceleoSourceBuffer(moduleBuffer);
		Module eModule = CstFactory.eINSTANCE.createModule();
		CSTParser pAcceleomodule = new CSTParser(moduleSource);
		pAcceleomodule.parseModuleHeader(0, moduleBuffer.length(), eModule);
		Template eTemplate = CstFactory.eINSTANCE.createTemplate();
		eModule.getOwnedModuleElement().add(eTemplate);
		LetBlock eLet = CstFactory.eINSTANCE.createLetBlock();
		eTemplate.getBody().add(eLet);
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(buffer);
		CSTParser pAcceleo = new CSTParser(source);
		CSTParserBlock parser = new CSTParserBlock(pAcceleo);
		parser.parse(0, buffer.length(), eLet);
		if (source.getProblems().getList().size() != problemsCount) {
			fail("You must have " + problemsCount + " syntax errors : " + source.getProblems().getMessage());
		}
	}

	public void testParseTrace() {
		StringBuffer buffer = new StringBuffer("[trace ('')] [/trace]");
		testParseTrace(buffer, 0);
	}

	public void testParseTraceWithOwnedTrace() {
		StringBuffer buffer = new StringBuffer("[trace ('1')] [trace ('2')] [/trace] [/trace]");
		testParseTrace(buffer, 0);
	}

	private void testParseTrace(StringBuffer buffer, int problemsCount) {
		StringBuffer moduleBuffer = new StringBuffer("mymodule(http://www.eclipse.org/uml2/2.1.0/UML)");
		AcceleoSourceBuffer moduleSource = new AcceleoSourceBuffer(moduleBuffer);
		Module eModule = CstFactory.eINSTANCE.createModule();
		CSTParser pAcceleomodule = new CSTParser(moduleSource);
		pAcceleomodule.parseModuleHeader(0, moduleBuffer.length(), eModule);
		Template eTemplate = CstFactory.eINSTANCE.createTemplate();
		eModule.getOwnedModuleElement().add(eTemplate);
		TraceBlock eTrace = CstFactory.eINSTANCE.createTraceBlock();
		eTemplate.getBody().add(eTrace);
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(buffer);
		CSTParser pAcceleo = new CSTParser(source);
		CSTParserBlock parser = new CSTParserBlock(pAcceleo);
		parser.parse(0, buffer.length(), eTrace);
		if (source.getProblems().getList().size() != problemsCount) {
			fail("You must have " + problemsCount + " syntax errors : " + source.getProblems().getMessage());
		}
	}

	public void testParseFileWithOverwriteMode() {
		StringBuffer buffer = new StringBuffer("[file ('file.txt', false)] [/file]");
		testParseFile(buffer, 0);
	}

	public void testParseFileWithAppendMode() {
		StringBuffer buffer = new StringBuffer("[file ('file.txt', true)] [/file]");
		testParseFile(buffer, 0);
	}

	public void testParseFileWithBadOpenMode() {
		StringBuffer buffer = new StringBuffer("[file ('file.txt', badmode)] [/file]");
		testParseFile(buffer, 1);
	}

	public void testParseFileWithOpenModeAndUniqueId() {
		StringBuffer buffer = new StringBuffer("[file ('file.txt', true, 'ID')] [/file]");
		testParseFile(buffer, 0);
	}

	public void testParseFileWithOwnedFile() {
		StringBuffer buffer = new StringBuffer(
				"[file ('file.txt', false)] [file ('file.txt', false)] [/file] [/file]");
		testParseFile(buffer, 0);
	}

	private void testParseFile(StringBuffer buffer, int problemsCount) {
		StringBuffer moduleBuffer = new StringBuffer("mymodule(http://www.eclipse.org/uml2/2.1.0/UML)");
		AcceleoSourceBuffer moduleSource = new AcceleoSourceBuffer(moduleBuffer);
		Module eModule = CstFactory.eINSTANCE.createModule();
		CSTParser pAcceleomodule = new CSTParser(moduleSource);
		pAcceleomodule.parseModuleHeader(0, moduleBuffer.length(), eModule);
		Template eTemplate = CstFactory.eINSTANCE.createTemplate();
		eModule.getOwnedModuleElement().add(eTemplate);
		FileBlock eFile = CstFactory.eINSTANCE.createFileBlock();
		eTemplate.getBody().add(eFile);
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(buffer);
		CSTParser pAcceleo = new CSTParser(source);
		CSTParserBlock parser = new CSTParserBlock(pAcceleo);
		parser.parse(0, buffer.length(), eFile);
		if (source.getProblems().getList().size() != problemsCount) {
			fail("You must have " + problemsCount + " syntax errors : " + source.getProblems().getMessage());
		}
	}

}
