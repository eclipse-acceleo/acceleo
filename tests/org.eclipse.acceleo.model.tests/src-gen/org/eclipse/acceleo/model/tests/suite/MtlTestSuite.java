package org.eclipse.acceleo.model.tests.suite;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.eclipse.acceleo.model.tests.unit.ModuleTest;
import org.eclipse.acceleo.model.tests.unit.BlockTest;
import org.eclipse.acceleo.model.tests.unit.InitSectionTest;
import org.eclipse.acceleo.model.tests.unit.TemplateTest;
import org.eclipse.acceleo.model.tests.unit.TemplateInvocationTest;
import org.eclipse.acceleo.model.tests.unit.QueryTest;
import org.eclipse.acceleo.model.tests.unit.QueryInvocationTest;
import org.eclipse.acceleo.model.tests.unit.ProtectedAreaBlockTest;
import org.eclipse.acceleo.model.tests.unit.ForBlockTest;
import org.eclipse.acceleo.model.tests.unit.IfBlockTest;
import org.eclipse.acceleo.model.tests.unit.LetBlockTest;
import org.eclipse.acceleo.model.tests.unit.FileBlockTest;
import org.eclipse.acceleo.model.tests.unit.TraceBlockTest;
import org.eclipse.acceleo.model.tests.unit.MacroTest;
import org.eclipse.acceleo.model.tests.unit.MacroInvocationTest;
import org.eclipse.acceleo.model.tests.unit.TypedModelTest;
import org.eclipse.acceleo.model.tests.unit.VisibilityKindTest;
import org.eclipse.acceleo.model.tests.unit.OpenModeKindTest;
import org.eclipse.acceleo.model.tests.unit.MtlAdapterFactoryTest;
import org.eclipse.acceleo.model.tests.unit.MtlFactoryTest;
import org.eclipse.acceleo.model.tests.unit.MtlSwitchTest;

/**
 * This test suite allows clients to launch all tests generated for package mtl at once.
 *
 * @generated
 */
public class MtlTestSuite extends TestCase {
	/**
	 * Standalone launcher for package mtl's tests.
	 *
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * This will return a suite populated with all generated tests for package mtl.
	 *
	 * @generated
	 */
	public static Test suite() {
		final TestSuite suite = new TestSuite("Mtl test suite"); //$NON-NLS-1$

		suite.addTestSuite(ModuleTest.class);
		suite.addTestSuite(BlockTest.class);
		suite.addTestSuite(InitSectionTest.class);
		suite.addTestSuite(TemplateTest.class);
		suite.addTestSuite(TemplateInvocationTest.class);
		suite.addTestSuite(QueryTest.class);
		suite.addTestSuite(QueryInvocationTest.class);
		suite.addTestSuite(ProtectedAreaBlockTest.class);
		suite.addTestSuite(ForBlockTest.class);
		suite.addTestSuite(IfBlockTest.class);
		suite.addTestSuite(LetBlockTest.class);
		suite.addTestSuite(FileBlockTest.class);
		suite.addTestSuite(TraceBlockTest.class);
		suite.addTestSuite(MacroTest.class);
		suite.addTestSuite(MacroInvocationTest.class);
		suite.addTestSuite(TypedModelTest.class);
		suite.addTestSuite(VisibilityKindTest.class);
		suite.addTestSuite(OpenModeKindTest.class);
		suite.addTestSuite(MtlAdapterFactoryTest.class);
		suite.addTestSuite(MtlFactoryTest.class);
		suite.addTestSuite(MtlSwitchTest.class);

		return suite;
	}
}

