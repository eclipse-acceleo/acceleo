<%
metamodel http://www.eclipse.org/emf/2002/GenModel
import org.eclipse.acceleo.benchmark.ecore2unittests.acceleo2.template.common
import org.eclipse.acceleo.benchmark.ecore2unittests.acceleo2.template.commonEcore
%>

<%script type="GenPackage" name="filename"%>
src-gen/<%testPackage().replaceAll("\.", "/")%>/unit/<%prefix.toU1Case()%>SwitchTest.java

<%script type="GenPackage" name="switchTest" file="<%filename%>"%>
package <%testPackage()%>.unit;

import junit.framework.TestCase;

import <%modelPackage()%>.<%modelFactoryClassName()%>;
import <%modelPackage()%>.util.<%modelSwitchClassName()%>;
<%for (genClasses.ecoreClass[!abstract]) {%>
import <%current(1).modelPackage()%>.<%name%>;
<%}%>

/*
 * TODO This is but a skeleton for the tests of <%modelSwitchClassName()%>.
 * Set as "generated NOT" and override each test if you overrode the default generated
 * behavior.
 */
/**
 * Tests the behavior of the {@link <%modelSwitchClassName()%> generated switch} for package <%ecorePackage.name%>.
 *
 * @generated
 */
@SuppressWarnings("nls")
public class <%modelSwitchClassName()%>Test extends TestCase {
	<%generateTestCases.sep("\n")%>
}

<%script type="GenPackage" name="generateTestCases"%>
<%for (genClasses.ecoreClass[!interface]) {%>
/**
 * Ensures that the generated switch knows {@link <%name%>}.
 *
 * @generated
 */
public void testCase<%name.toU1Case()%>() {
	<%current(1).modelSwitchClassName()%><?> <%current(1).ecorePackage.name.toL1Case()%>switch = new <%current(1).modelSwitchClassName()%><Object>();
	assertNull(<%current(1).ecorePackage.name.toL1Case()%>switch.case<%name.toU1Case()%>(<%current(1).instanceCreator(current)%>));
	assertNull(<%current(1).ecorePackage.name.toL1Case()%>switch.doSwitch(<%current(1).instanceCreator(current)%>));
}
<%}%>
	
<%script type="GenPackage" name="instanceCreator"%>
<%if (!args(0).abstract) {%><%modelFactoryClassName()%>.eINSTANCE.create<%args(0).name.toU1Case()%>()<%}else{%><%if (!args(0).interface) {%>new <%args(0).qualifiedGeneratedImplementation()%>(){}<%}%><%}%>