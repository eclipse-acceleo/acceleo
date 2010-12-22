<%
metamodel http://www.eclipse.org/emf/2002/GenModel
import org.eclipse.acceleo.benchmark.ecore2unittests.acceleo2.template.common
%>

<%script type="GenPackage" name="fileName"%>
src-gen/<%testPackage.replaceAll("\.", "/")%>/unit/<%prefix.toU1Case()%>AdapterFactoryTest.java

<%script type="GenPackage" name="adapterFactoryTest" file="<%fileName%>"%>
package <%testPackage()%>.unit;

import junit.framework.TestCase;

import <%modelPackage()%>.<%modelFactoryClassName()%>;
import <%modelPackage()%>.<%modelPackageClassName()%>;
import <%modelPackage()%>.util.<%modelAdapterFactoryClassName()%>;
[for (eClass : EClass | pack.genClasses.ecoreClass->select(not abstract))]
import <%modelPackage()%>.[eClass.name%>;
[/for]

/*
 * TODO This is but a skeleton for the tests of <%modelAdapterFactoryClassName()%>.
 * Set as "generated NOT" and override each test if you overrode the default generated
 * behavior.
 */
/**
 * Tests the behavior of the {@link <%modelAdapterFactoryClassName()%> generated adapter factory} for package <%ecorePackage.name%>.
 *
 * @generated
 */
@SuppressWarnings("nls")
public class <%modelAdapterFactoryClassName()%>Test extends TestCase {
	[for (eClass : EClass | pack.genClasses.ecoreClass) separator('\n')]
	/**
	 * Ensures that creating adapters for {@link [eClass.name%>} can be done through the AdapterFactory.
	 *
	 * @generated
	 */
	public void testCreate[eClass.name.toUpperFirst()%>Adapter() {
		<%modelAdapterFactoryClassName()%> adapterFactory = new <%modelAdapterFactoryClassName()%>();
		assertNull(adapterFactory.create[eClass.name.toUpperFirst()%>Adapter());
		[if (not eClass.abstract)]
		assertNull(adapterFactory.createAdapter(<%modelFactoryClassName()%>.eINSTANCE.create[eClass.name.toUpperFirst()%>()));
		[elseif (not eClass.interface)]
		assertNull(adapterFactory.createAdapter(new [eClass.qualifiedGeneratedImplementation()%>(){}));
		[/if]
	}
	[/for]
	
	/**
	 * Ensures that the AdapterFactory knows all classes of package <%ecorePackage.name%>.
	 *
	 * @generated
	 */
	public void testIsFactoryForType() {
		<%modelAdapterFactoryClassName()%> adapterFactory = new <%modelAdapterFactoryClassName()%>();
		[for (eClass : EClass | pack.genClasses.ecoreClass) ? (not eClass.abstract)]
		assertTrue(adapterFactory.isFactoryForType(<%modelFactoryClassName()%>.eINSTANCE.create[eClass.name.toUpperFirst()%>()));
		[/for]
		assertTrue(adapterFactory.isFactoryForType(<%modelPackageClassName()%>.eINSTANCE));
		[if (pack.ecorePackage.nsURI <> 'http://www.eclipse.org/emf/2002/Ecore')]
		org.eclipse.emf.ecore.EClass eClass = org.eclipse.emf.ecore.EcoreFactory.eINSTANCE.createEClass();
		assertFalse(adapterFactory.isFactoryForType(eClass));
		[/if]
		assertFalse(adapterFactory.isFactoryForType(new Object()));
	}
}

