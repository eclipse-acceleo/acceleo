package org.eclipse.acceleo.tests.evaluation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluationEnvironment;
import org.eclipse.acceleo.aql.evaluation.EvaluationSwitch;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.junit.Before;
import org.junit.Test;

public class GenerationTest {
	private IQueryEnvironment aqlEnvironment;

	private AcceleoEvaluationEnvironment acceleoEnvironment;

	private Module module1;

	private Module module2;

	private Module module3;

	private Module module4;

	@Before
	public void loadModules() throws IOException {
		aqlEnvironment = Query.newEnvironmentWithDefaultServices(null);
		module1 = readModule(aqlEnvironment, "M1.acceleo");
		module2 = readModule(aqlEnvironment, "M2.acceleo");
		module3 = readModule(aqlEnvironment, "M3.acceleo");
		module4 = readModule(aqlEnvironment, "M4.acceleo");

		acceleoEnvironment = new AcceleoEvaluationEnvironment();
		acceleoEnvironment.registerModule("org::eclipse::acceleo::aql::tests::evaluation::m1", module1);
		acceleoEnvironment.registerModule("org::eclipse::acceleo::aql::tests::evaluation::m2", module2);
		acceleoEnvironment.registerModule("org::eclipse::acceleo::aql::tests::evaluation::m3", module3);
		acceleoEnvironment.registerModule("org::eclipse::acceleo::aql::tests::evaluation::m4", module4);
	}

	@Test
	public void testCallM1T11EPackage() {
		acceleoEnvironment.addVariable("param1", EcoreFactory.eINSTANCE.createEPackage());

		EvaluationSwitch evaluationEngine = new EvaluationSwitch(acceleoEnvironment);
		ModuleElement start = module1.getModuleElements().get(0);
		assertTrue(start instanceof Template && ((Template)start).getName().equals("t11"));
		acceleoEnvironment.pushStack(start, true);
		Object result = evaluationEngine.doSwitch(start);

		assertTrue(result instanceof String);
		assertEquals("\r\ngenerated from m1.t11(EPackage)\r\n", result);
		assertNull(acceleoEnvironment.getCurrentStack());
	}

	@Test
	public void testCallM1T11EClass() {
		acceleoEnvironment.addVariable("param1", EcoreFactory.eINSTANCE.createEClass());

		EvaluationSwitch evaluationEngine = new EvaluationSwitch(acceleoEnvironment);
		ModuleElement start = module1.getModuleElements().get(1);
		assertTrue(start instanceof Template && ((Template)start).getName().equals("t11"));
		acceleoEnvironment.pushStack(start, true);
		Object result = evaluationEngine.doSwitch(start);

		assertTrue(result instanceof String);
		assertEquals(
				"\r\n\r\ngenerated from m1.overrideMe(EClass)\r\n\r\n\r\ngenerated from m1.privateInBoth(EClass)\r\n\r\n",
				result);
		assertNull(acceleoEnvironment.getCurrentStack());
	}

	/*
	 * M2.t21 calls "t11" which is not overriden in M2, so goes up to M1.t11, which calls "overrideMe",
	 * overriden in M2 so we go down to M2.overrideMe, and "privateInBoth" which should call the one in M1 at
	 * that point in the stack.
	 */
	@Test
	public void testCallM2T21() {
		EPackage pack = EcoreFactory.eINSTANCE.createEPackage();
		EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		pack.getEClassifiers().add(clazz);
		acceleoEnvironment.addVariable("param1", pack);

		EvaluationSwitch evaluationEngine = new EvaluationSwitch(acceleoEnvironment);
		ModuleElement start = module2.getModuleElements().get(0);
		assertTrue(start instanceof Template && ((Template)start).getName().equals("t21"));
		acceleoEnvironment.pushStack(start, true);
		Object result = evaluationEngine.doSwitch(start);

		assertTrue(result instanceof String);
		assertEquals(
				"\r\ngenerated from m2.t21(EPackage)\r\n\r\n\r\ngenerated from m2.overrideMe(EClass)\r\n\r\n\r\ngenerated from m1.privateInBoth(EClass)\r\n\r\n\r\n",
				result);
	}

	@Test
	public void testCallM2OverrideMe() {
		EPackage pack = EcoreFactory.eINSTANCE.createEPackage();
		EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		pack.getEClassifiers().add(clazz);
		acceleoEnvironment.addVariable("param1", pack);

		EvaluationSwitch evaluationEngine = new EvaluationSwitch(acceleoEnvironment);
		ModuleElement start = module2.getModuleElements().get(1);
		assertTrue(start instanceof Template && ((Template)start).getName().equals("overrideMe"));
		acceleoEnvironment.pushStack(start, true);
		Object result = evaluationEngine.doSwitch(start);

		assertTrue(result instanceof String);
		assertEquals("\r\ngenerated from m2.overrideMe(EClass)\r\n", result);
	}

	/*
	 * We're calling M2.toImportsAndBack as a starting point. This will call m4.t41 that's accessible through
	 * the import of m2. This then tries to call "t31" which is available on m3 through the extends of m4,
	 * which in turns tries to call "overrideMe". This is available on both m3 (the current template) and m4
	 * (the one we were called from), we'll thus call m4.overrideMe since it's the one doing the overriding.
	 * m4.overrideMe, m3.t31 and m4.t41 don't have more statements to evaluate, so we go back to
	 * m2.toImportsAndBack, which will call "overrideMe" on his own. We're no longer in the import's
	 * hierarchy, so this is expected to call m2.overrideMe.
	 */
	@Test
	public void testCallM2ToImportsAndBack() {
		EPackage pack = EcoreFactory.eINSTANCE.createEPackage();
		EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		pack.getEClassifiers().add(clazz);
		acceleoEnvironment.addVariable("param1", clazz);

		EvaluationSwitch evaluationEngine = new EvaluationSwitch(acceleoEnvironment);
		ModuleElement start = module2.getModuleElements().get(3);
		assertTrue(start instanceof Template && ((Template)start).getName().equals("toImportsAndBack"));
		acceleoEnvironment.pushStack(start, true);
		Object result = evaluationEngine.doSwitch(start);

		assertTrue(result instanceof String);
		assertEquals(
				"\r\n\r\ngenerated from m4.t41(EClass)\r\n\r\ngenerated from m3.t31(EClass)\r\n\r\ngenerated from m4.overrideMe(EClass)\r\n\r\n\r\n\r\n\r\ngenerated from m2.overrideMe(EClass)\r\n\r\n",
				result);
	}

	/*
	 * We're calling m2.toImportsExtends as a starting point. this tries to call "t31" which is not available
	 * in our hierarchy, not available on the import, but available on the import's extends hierarchy, on m3.
	 * We thus call m3.t31 which calls "overrideMe". Since m3 is not the one which was imported, we expect
	 * that "overrideMe" to be the one from m4.
	 */
	@Test
	public void testCallM2ToImportsExtends() {
		EPackage pack = EcoreFactory.eINSTANCE.createEPackage();
		EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		pack.getEClassifiers().add(clazz);
		acceleoEnvironment.addVariable("param1", clazz);

		EvaluationSwitch evaluationEngine = new EvaluationSwitch(acceleoEnvironment);
		ModuleElement start = module2.getModuleElements().get(4);
		assertTrue(start instanceof Template && ((Template)start).getName().equals("toImportsExtends"));
		acceleoEnvironment.pushStack(start, true);
		Object result = evaluationEngine.doSwitch(start);

		assertTrue(result instanceof String);
		assertEquals(
				"\r\n\r\ngenerated from m3.t31(EClass)\r\n\r\ngenerated from m3.overrideMe(EClass)\r\n\r\n\r\n",
				result);
	}

	private Module readModule(IQueryEnvironment env, String name) throws IOException {
		AcceleoParser parser = new AcceleoParser(env);
		URL url = getClass().getResource(name);
		Object content = url.getContent();
		if (content instanceof InputStream) {
			try (InputStream stream = (InputStream)content) {
				String moduleSource = CharStreams.toString(new InputStreamReader(stream, "UTF-8"));
				return parser.parse(moduleSource);
			}
		} else {
			fail("failed to load content of module " + name);
		}
		return null;
	}
}
