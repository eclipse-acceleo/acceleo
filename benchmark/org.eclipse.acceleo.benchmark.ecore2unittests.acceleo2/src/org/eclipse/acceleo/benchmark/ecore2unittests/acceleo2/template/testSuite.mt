<%
metamodel http://www.eclipse.org/emf/2002/GenModel
import org.eclipse.acceleo.benchmark.ecore2unittests.acceleo2.template.common
%>

<%script type="GenPackage" name="filename"%>
src-gen/<%testPackage().replaceAll("\.", "/")%>/suite/<%prefix.toU1Case()%>TestSuite.java

<%script type="GenPackage" name="testSuite" file="<%filename%>"%>
package <%testPackage()%>.suite;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
<%for (genClasses[!ecoreClass.abstract && ecoreClass.eContents()[!filter("EGenericType")]]) {%>
import <%testPackage()%>.unit.<%ecoreClass.name.toU1Case()%>Test;
<%}%>
<%for (genEnums) {%>
import <%testPackage()%>.unit.<%ecoreEnum.name.toU1Case()%>Test;
<%}%>
import <%testPackage()%>.unit.<%prefix.toU1Case()%>AdapterFactoryTest;
import <%testPackage()%>.unit.<%prefix.toU1Case()%>FactoryTest;
import <%testPackage()%>.unit.<%prefix.toU1Case()%>SwitchTest;

/**
 * This test suite allows clients to launch all tests generated for package <%ecorePackage.name%> at once.
 *
 * @generated
 */
public class <%prefix.toU1Case()%>TestSuite extends TestCase {
	/**
	 * Standalone launcher for package <%ecorePackage.name%>'s tests.
	 *
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * This will return a suite populated with all generated tests for package <%ecorePackage.name%>.
	 *
	 * @generated
	 */
	public static Test suite() {
		final TestSuite suite = new TestSuite("<%prefix.toU1Case()%> test suite"); //$NON-NLS-1$

		<%for (genClasses[!ecoreClass.abstract && ecoreClass.eContents()[!filter("EGenericType")]]) {%>
		suite.addTestSuite(<%ecoreClass.name.toU1Case()%>Test.class);
		<%}%>
		<%for (genEnums) {%>
		suite.addTestSuite(<%ecoreEnum.name.toU1Case()%>Test.class);
		<%}%>
		suite.addTestSuite(<%prefix.toU1Case()%>AdapterFactoryTest.class);
		suite.addTestSuite(<%prefix.toU1Case()%>FactoryTest.class);
		suite.addTestSuite(<%prefix.toU1Case()%>SwitchTest.class);

		return suite;
	}
}