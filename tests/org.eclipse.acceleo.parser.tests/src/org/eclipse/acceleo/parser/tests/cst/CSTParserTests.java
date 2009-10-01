/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.parser.tests.cst;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;

import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.internal.parser.ast.CST2ASTConverter;
import org.eclipse.acceleo.internal.parser.cst.CSTParser;
import org.eclipse.acceleo.parser.AcceleoSourceBuffer;
import org.eclipse.acceleo.parser.cst.CstFactory;
import org.eclipse.acceleo.parser.cst.Module;
import org.eclipse.acceleo.parser.cst.Template;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.ocl.helper.Choice;
import org.osgi.framework.Bundle;

public class CSTParserTests extends TestCase {

	private Bundle bundle;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		bundle = Platform.getBundle("org.eclipse.acceleo.parser.tests");
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		bundle = null;
	}

	private File createFile(String pathName) {
		try {
			String fileLocation = FileLocator.resolve(bundle.getEntry(pathName)).getPath();
			return new File(fileLocation);
		} catch (IOException e) {
			throw new AssertionFailedError(e.getMessage());
		} catch (NullPointerException e) {
			/*
			 * on the server the unit test fails with an NPE :S
			 */
			throw new AssertionFailedError(e.getMessage());
		}

	}

	public void testParseFileLibrary2textAcceleo() {
		File file = createFile("/data/template/cstParserLibrary2text.mtl");
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(file);
		CSTParser parser = new CSTParser(source);
		assertNotNull(parser.parse());
		if (source.getProblems().getList().size() > 0) {
			fail(source.getProblems().getMessage());
		}
	}

	public void testParseEmptyBuffer() {
		StringBuffer buffer = new StringBuffer("");
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(buffer);
		CSTParser parser = new CSTParser(source);
		assertNull(parser.parse());
		checkProblems(source, 1);
	}

	public void testParseBufferModuleIsMissing() {
		StringBuffer buffer = new StringBuffer("[template name()][/template]");
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(buffer);
		CSTParser parser = new CSTParser(source);
		assertNull(parser.parse());
		checkProblems(source, 1);
	}

	public void testParseBufferModuleIsNotTerminated() {
		StringBuffer buffer = new StringBuffer(
				"[module library2text(http:///org/eclipse/emf/examples/library/extlibrary.ecore/1.0.0)/]");
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(buffer);
		CSTParser parser = new CSTParser(source);
		assertNotNull(parser.parse());
		checkProblems(source, 0);

		buffer = new StringBuffer(
				"[module library2text(http:///org/eclipse/emf/examples/library/extlibrary.ecore/1.0.0)");
		source = new AcceleoSourceBuffer(buffer);
		parser = new CSTParser(source);
		assertNull(parser.parse());
		checkProblems(source, 1);
	}

	private void checkProblems(AcceleoSourceBuffer source, int problemsCount) {
		if (source.getProblems().getList().size() != problemsCount) {
			fail("You must have " + problemsCount + " syntax errors : " + source.getProblems().getMessage());
		}
	}

	public void testParseModuleHeaderUML() {
		StringBuffer buffer = new StringBuffer("mymodule(http://www.eclipse.org/uml2/2.1.0/UML)");
		testParseModuleHeader(buffer);
	}

	public void testParseModuleHeaderUMLExtendsOneModule() {
		StringBuffer buffer = new StringBuffer(
				"mymodule(http://www.eclipse.org/uml2/2.1.0/UML) extends mymodule1");
		testParseModuleHeader(buffer);
	}

	public void testParseModuleHeaderUMLExtendsTwoModules() {
		StringBuffer buffer = new StringBuffer(
				"mymodule(http://www.eclipse.org/uml2/2.1.0/UML) extends mymodule1, mymodule2");
		testParseModuleHeader(buffer);
	}

	private void testParseModuleHeader(StringBuffer buffer) {
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(buffer);
		CSTParser parser = new CSTParser(source);
		Module eModule = CstFactory.eINSTANCE.createModule();
		parser.parseModuleHeader(0, buffer.length(), eModule);
		if (source.getProblems().getList().size() > 0) {
			fail(source.getProblems().getMessage());
		}
	}

	public void testParseTemplateHeaderWithOneParameter() {
		StringBuffer buffer = new StringBuffer("public class2Java(c : Class)");
		testParseTemplateHeader(buffer, 0);
	}

	public void testParseTemplateHeaderWithTwoParameters() {
		StringBuffer buffer = new StringBuffer("public class2Java(c1 : Class, c2 : Class)");
		testParseTemplateHeader(buffer, 0);
	}

	public void testParseTemplateHeaderWithDuplicatedParameters() {
		StringBuffer buffer = new StringBuffer("public class2Java(dup : Class, dup : Class)");
		testParseTemplateHeader(buffer, 1);
	}

	public void testParseTemplateHeaderWithBadParameter() {
		StringBuffer buffer = new StringBuffer("public class2Java(c1 : Class, c2 - Class)");
		testParseTemplateHeader(buffer, 1);
	}

	public void testParseTemplateHeaderWithOverrides() {
		StringBuffer buffer = new StringBuffer(
				"public class2Java(c1 : Class) overrides class2Java, class2Java");
		testParseTemplateHeader(buffer, 0);
	}

	public void testParseTemplateHeaderWithOverridesGuard() {
		StringBuffer buffer = new StringBuffer(
				"public class2Java(c1 : Class) overrides class2Java ? (c1.name = '')");
		testParseTemplateHeader(buffer, 0);
	}

	public void testParseTemplateHeaderWithOverridesGuardParenthesisAreRequired() {
		StringBuffer buffer = new StringBuffer(
				"public class2Java(c1 : Class) overrides class2Java ? c1.name = ''");
		testParseTemplateHeader(buffer, 1);
	}

	public void testParseTemplateHeaderWithOverridesGuardInitSection() {
		StringBuffer buffer = new StringBuffer(
				"public class2Java(c1 : Class) overrides class2Java ? (c1.name = '') {c2:Class;}");
		testParseTemplateHeader(buffer, 0);
	}

	private void testParseTemplateHeader(StringBuffer buffer, int problemsCount) {
		StringBuffer moduleBuffer = new StringBuffer("mymodule(http://www.eclipse.org/uml2/2.1.0/UML)");
		AcceleoSourceBuffer moduleSource = new AcceleoSourceBuffer(moduleBuffer);
		Module eModule = CstFactory.eINSTANCE.createModule();
		CSTParser moduleParser = new CSTParser(moduleSource);
		moduleParser.parseModuleHeader(0, moduleBuffer.length(), eModule);
		Template eTemplate = CstFactory.eINSTANCE.createTemplate();
		eModule.getOwnedModuleElement().add(eTemplate);
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(buffer);
		CSTParser parser = new CSTParser(source);
		parser.parseTemplateHeader(0, buffer.length(), eTemplate);
		if (source.getProblems().getList().size() != problemsCount) {
			fail("You must have " + problemsCount + " syntax errors : " + source.getProblems().getMessage());
		}
	}

	// FIXME JMU This doesn't work on the build machine with the following stack :
	/*
	 * junit.framework.AssertionFailedError at
	 * org.eclipse.acceleo.parser.tests.cst.CSTParserTests.getSyntaxHelp(CSTParserTests.java:220) at
	 * org.eclipse.acceleo.parser.tests.cst.CSTParserTests.testParseOCLSyntaxHelp(CSTParserTests.java:201) at
	 * org.eclipse.test.EclipseTestRunner.run(EclipseTestRunner.java:332) at
	 * org.eclipse.test.EclipseTestRunner.run(EclipseTestRunner.java:202) at
	 * org.eclipse.test.CoreTestApplication.runTests(CoreTestApplication.java:35) at
	 * org.eclipse.test.CoreTestApplication.run(CoreTestApplication.java:31) at
	 * org.eclipse.equinox.internal.app
	 * .EclipseAppContainer.callMethodWithException(EclipseAppContainer.java:574) at
	 * org.eclipse.equinox.internal.app.EclipseAppHandle.run(EclipseAppHandle.java:196) at
	 * org.eclipse.equinox.internal.app.MainApplicationLauncher.run(MainApplicationLauncher.java:32) at
	 * org.eclipse
	 * .core.runtime.internal.adaptor.EclipseAppLauncher.runApplication(EclipseAppLauncher.java:110) at
	 * org.eclipse.core.runtime.internal.adaptor.EclipseAppLauncher.start(EclipseAppLauncher.java:79) at
	 * org.eclipse.core.runtime.adaptor.EclipseStarter.run(EclipseStarter.java:368) at
	 * org.eclipse.core.runtime.adaptor.EclipseStarter.run(EclipseStarter.java:179) at
	 * org.eclipse.equinox.launcher.Main.invokeFramework(Main.java:559) at
	 * org.eclipse.equinox.launcher.Main.basicRun(Main.java:514) at
	 * org.eclipse.equinox.launcher.Main.run(Main.java:1311) at
	 * org.eclipse.equinox.launcher.Main.main(Main.java:1287) at
	 * org.eclipse.core.launcher.Main.main(Main.java:34)
	 */
	// public void testParseOCLSyntaxHelp() {
	// String bufferBefore = "[module mymodule(http://www.eclipse.org/uml2/2.1.0/UML)/]"
	// + "[template public class2Java(c1 : Class)]" + "[c1.";
	// String bufferAfter = "/] [/template]";
	// List<Choice> choices = getSyntaxHelp(bufferBefore + bufferAfter, bufferBefore.length());
	// assertSame(choices.size(), 52);
	// }
	private List<Choice> getSyntaxHelp(String buffer, int offset) {
		AcceleoSourceBuffer source = new AcceleoSourceBuffer(new StringBuffer(buffer));
		CSTParser parser = new CSTParser(source);
		Module cst = parser.parse();
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = ModelUtils.createResource(URI
				.createURI("http://acceleo.eclipse.org/default.emtl"), resourceSet);
		CST2ASTConverter astConverter = new CST2ASTConverter();
		astConverter.createAST(cst, resource);
		if (resource.getContents().size() > 0
				&& resource.getContents().get(0) instanceof org.eclipse.acceleo.model.mtl.Module) {
			org.eclipse.acceleo.model.mtl.Module ast = (org.eclipse.acceleo.model.mtl.Module)resource
					.getContents().get(0);
			astConverter.getOCL().addRecursivelyMetamodelsToScope(ast);
			astConverter.getOCL().addRecursivelyBehavioralFeaturesToScope(ast);
			assertNotNull(astConverter.getOCL().addRecursivelyVariablesToScopeAndGetContextClassifierAt(ast,
					offset));
		}
		return astConverter.getOCL().getSyntaxHelp("");
	}

}
