package org.eclipse.acceleo.tests.evaluation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.common.io.CharStreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.aql.AcceleoEnvironment;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.evaluation.writer.DefaultGenerationStrategy;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * This will test the template lookup through various scenarios involving imports and extends.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class TemplateLookupTest {
	/** Name of the first parameter on all of our templates. */
	private static final String PARAM1 = "param1";

	/** The aql environment used to read and parse the modules from disk. */
	private IQueryEnvironment aqlEnvironment;

	/** The Acceleo environment we'll use to test the lookup. */
	private AcceleoEnvironment acceleoEnvironment;

	/** First of the tested modules. See M1 through M4.acceleo beside this class. */
	private Module module1;

	/** Second of the tested modules. See M1 through M4.acceleo beside this class. */
	private Module module2;

	/** Third of the tested modules. See M1 through M4.acceleo beside this class. */
	private Module module3;

	/** Fourth of the tested modules. See M1 through M4.acceleo beside this class. */
	private Module module4;

	/**
	 * Prepares the environment. This will load the 4 modules and register them against a new environment
	 * before each test.
	 * 
	 * @throws IOException
	 *             If we couldn't read the modules from disk.
	 */
	@Before
	public void loadModules() throws IOException {
		aqlEnvironment = Query.newEnvironmentWithDefaultServices(null);
		module1 = readModule(aqlEnvironment, "/org/eclipse/acceleo/tests/evaluation/M1.acceleo");
		module2 = readModule(aqlEnvironment, "/org/eclipse/acceleo/tests/evaluation/M2.acceleo");
		module3 = readModule(aqlEnvironment, "/org/eclipse/acceleo/tests/evaluation/M3.acceleo");
		module4 = readModule(aqlEnvironment, "/org/eclipse/acceleo/tests/evaluation/M4.acceleo");

		acceleoEnvironment = new AcceleoEnvironment(new DefaultGenerationStrategy(), URI.createURI(""));
		acceleoEnvironment.registerModule("org::eclipse::acceleo::aql::tests::evaluation::m1", module1);
		acceleoEnvironment.registerModule("org::eclipse::acceleo::aql::tests::evaluation::m2", module2);
		acceleoEnvironment.registerModule("org::eclipse::acceleo::aql::tests::evaluation::m3", module3);
		acceleoEnvironment.registerModule("org::eclipse::acceleo::aql::tests::evaluation::m4", module4);
	}

	/**
	 * Calls "M1.t11(EPackage)". This doesn't call any other module's templates.
	 */
	@Test
	public void testCallM1T11EPackage() {
		final Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(PARAM1, EcoreFactory.eINSTANCE.createEPackage());

		final AcceleoEvaluator evaluationEngine = new AcceleoEvaluator();
		final ModuleElement start = module1.getModuleElements().get(0);
		assertTrue(start instanceof Template && "t11".equals(((Template)start).getName()));
		acceleoEnvironment.pushImport(acceleoEnvironment.getModuleQualifiedName(module1), start);

		final String result = (String)evaluationEngine.generate(acceleoEnvironment, start, variables);

		assertEquals("\r\ngenerated from m1.t11(EPackage)\r\n", result);
		assertNull(acceleoEnvironment.getCurrentStack());
	}

	/**
	 * Calls "M1.t11(EClass)". This calls two other templates that should be found in the same module.
	 */
	@Test
	public void testCallM1T11EClass() {
		final Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(PARAM1, EcoreFactory.eINSTANCE.createEClass());

		final AcceleoEvaluator evaluationEngine = new AcceleoEvaluator();
		final ModuleElement start = module1.getModuleElements().get(1);
		assertTrue(start instanceof Template && "t11".equals(((Template)start).getName()));
		acceleoEnvironment.pushImport(acceleoEnvironment.getModuleQualifiedName(module1), start);

		final String result = (String)evaluationEngine.generate(acceleoEnvironment, start, variables);

		assertEquals(
				"\r\n\r\ngenerated from m1.overrideMe(EClass)\r\n\r\n\r\ngenerated from m1.privateInBoth(EClass)\r\n\r\n",
				result);
		assertNull(acceleoEnvironment.getCurrentStack());
	}

	/**
	 * M2.t21 calls "t11" which is not overriden in M2, so goes up to M1.t11, which calls "overrideMe",
	 * overriden in M2 so we go down to M2.overrideMe, and "privateInBoth" which should call the one in M1 at
	 * that point in the stack.
	 */
	@Test
	public void testCallM2T21() {
		EPackage pack = EcoreFactory.eINSTANCE.createEPackage();
		EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		pack.getEClassifiers().add(clazz);
		final Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(PARAM1, pack);

		final AcceleoEvaluator evaluationEngine = new AcceleoEvaluator();
		ModuleElement start = module2.getModuleElements().get(0);
		assertTrue(start instanceof Template && "t21".equals(((Template)start).getName()));
		acceleoEnvironment.pushImport(acceleoEnvironment.getModuleQualifiedName(module2), start);

		final String result = (String)evaluationEngine.generate(acceleoEnvironment, start, variables);

		assertEquals(
				"\r\ngenerated from m2.t21(EPackage)\r\n\r\n\r\ngenerated from m2.overrideMe(EClass)\r\n\r\n\r\ngenerated from m1.privateInBoth(EClass)\r\n\r\n\r\n",
				result);
	}

	/**
	 * Calls M2.overrideMe(), which is overriden in M1 but M1 is not in the scope, so we're not going to call
	 * anything in other modules.
	 */
	@Test
	public void testCallM2OverrideMe() {
		EPackage pack = EcoreFactory.eINSTANCE.createEPackage();
		EClass clazz = EcoreFactory.eINSTANCE.createEClass();
		pack.getEClassifiers().add(clazz);
		final Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(PARAM1, pack);

		final AcceleoEvaluator evaluationEngine = new AcceleoEvaluator();
		ModuleElement start = module2.getModuleElements().get(1);
		assertTrue(start instanceof Template && "overrideMe".equals(((Template)start).getName()));
		acceleoEnvironment.pushImport(acceleoEnvironment.getModuleQualifiedName(module2), start);

		final String result = (String)evaluationEngine.generate(acceleoEnvironment, start, variables);

		assertEquals("\r\ngenerated from m2.overrideMe(EClass)\r\n", result);
	}

	/**
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
		final Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(PARAM1, clazz);

		final AcceleoEvaluator evaluationEngine = new AcceleoEvaluator();
		ModuleElement start = module2.getModuleElements().get(3);
		assertTrue(start instanceof Template && "toImportsAndBack".equals(((Template)start).getName()));
		acceleoEnvironment.pushImport(acceleoEnvironment.getModuleQualifiedName(module2), start);

		final String result = (String)evaluationEngine.generate(acceleoEnvironment, start, variables);

		assertEquals(
				"\r\n\r\ngenerated from m4.t41(EClass)\r\n\r\ngenerated from m3.t31(EClass)\r\n\r\ngenerated from m4.overrideMe(EClass)\r\n\r\n\r\n\r\n\r\ngenerated from m2.overrideMe(EClass)\r\n\r\n",
				result);
	}

	/**
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
		final Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(PARAM1, clazz);

		final AcceleoEvaluator evaluationEngine = new AcceleoEvaluator();
		ModuleElement start = module2.getModuleElements().get(4);
		assertTrue(start instanceof Template && "toImportsExtends".equals(((Template)start).getName()));
		acceleoEnvironment.pushImport(acceleoEnvironment.getModuleQualifiedName(module2), start);

		final String result = (String)evaluationEngine.generate(acceleoEnvironment, start, variables);

		assertEquals(
				"\r\n\r\ngenerated from m3.t31(EClass)\r\n\r\ngenerated from m4.overrideMe(EClass)\r\n\r\n\r\n",
				result);
	}

	/**
	 * Reads a module from disk, parses it and returns the AST.
	 * 
	 * @param env
	 *            The AQL environment with which to parse this module.
	 * @param name
	 *            Name of the module file to read. This can be a path relative to this current class.
	 * @return The parsed AST.
	 * @throws IOException
	 *             if we couldn't read the file from disk.
	 */
	private Module readModule(IQueryEnvironment env, String name) throws IOException {
		AcceleoParser parser = new AcceleoParser(env);
		InputStream content = getClass().getResourceAsStream(name);
		try (InputStream stream = (InputStream)content) {
			String moduleSource = CharStreams.toString(new InputStreamReader(stream, "UTF-8"));
			return parser.parse(moduleSource).getModule();
		}
	}
}
