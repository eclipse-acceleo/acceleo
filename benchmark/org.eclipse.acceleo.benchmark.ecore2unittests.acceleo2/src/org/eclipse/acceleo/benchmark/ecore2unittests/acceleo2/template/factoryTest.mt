<%
metamodel http://www.eclipse.org/emf/2002/GenModel
import org.eclipse.acceleo.benchmark.ecore2unittests.acceleo2.services.StringService
import org.eclipse.acceleo.benchmark.ecore2unittests.acceleo2.template.common
%>

<%script type="GenPackage" name="filename"%>
src-gen/<%testPackage().replaceAll("\.", "/")%>/unit/<%prefix.toU1Case()%>FactoryTest.java

<%script type="GenPackage" name="factoryTest" file="<%filename%>"%>
package <%testPackage()%>.unit;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.impl.EClassifierImpl;

import <%modelPackage()%>.<%modelFactoryClassName()%>;
import <%modelPackage()%>.<%modelPackageClassName()%>;
<%for (genClasses.ecoreClass[!abstract]) {%>
import <%current(1).modelPackage()%>.<%name%>;
<%}%>
<%for (genEnums.ecoreEnum) {%>
import <%current(1).modelPackage()%>.<%name%>;
<%}%>

/**
 * Tests the behavior of the {@link <%modelFactoryClassName()%> generated factory} for package <%ecorePackage.name%>.
 *
 * @generated
 */
@SuppressWarnings("nls")
public class <%modelFactoryClassName()%>Test extends TestCase {
	<%generateTestCreateClasses.sep("\n")%>

	/**
	 * Ensures that trying to create an {@link EClass} from another package yields the expected exception.
	 *
	 * @generated
	 */
	public void testCreateUnknownEClass() {
		try {
			EClass eClass = EcoreFactory.eINSTANCE.createEClass();
			((EClassifierImpl)eClass).setClassifierID(-1);
			<%modelFactoryClassName()%>.eINSTANCE.create(eClass);
			fail("Expected IllegalArgumentException hasn't been thrown");
		} catch (IllegalArgumentException e) {
			// Expected behavior
		}
	}
	
	<%generateTestConvertEnums.sep("\n")%>

	/**
	 * Ensures that trying to convert an {@link EEnum} from another package to String yields the expected
	 * exception.
	 *
	 * @generated
	 */
	public void testConvertUnknownEEnumToString() {
		try {
			EEnum eEnum = EcoreFactory.eINSTANCE.createEEnum();
			((EClassifierImpl)eEnum).setClassifierID(-1);
			<%modelFactoryClassName()%>.eINSTANCE.convertToString(eEnum, eEnum);
			fail("Expected IllegalArgumentException hasn't been thrown");
		} catch (IllegalArgumentException e) {
			// Expected behavior
		}
	}

	<%generateTestCreateEnums.sep("\n")%>

	/**
	 * Ensures that trying to create an {@link EEnum} from another package from String yields the expected
	 * exception.
	 *
	 * @generated
	 */
	public void testCreateUnknownEEnumFromString() {
		try {
			EEnum eEnum = EcoreFactory.eINSTANCE.createEEnum();
			((EClassifierImpl)eEnum).setClassifierID(-1);
			<%modelFactoryClassName()%>.eINSTANCE.createFromString(eEnum, "ThisShouldntBeAKnownEEnumLiteral");
			fail("Expected IllegalArgumentException hasn't been thrown");
		} catch (IllegalArgumentException e) {
			// Expected behavior
		}
	}
}

<%script type="GenPackage" name="generateTestCreateClasses"%>
<%for (genClasses[!ecoreClass.abstract]) {%>
/**
 * Ensures that creating {@link <%ecoreClass.name.toU1Case()%>} can be done through the factory.
 *
 * @generated
 */
public void testCreate<%ecoreClass.name.toU1Case()%>() {
	Object result = <%current(1).modelFactoryClassName()%>.eINSTANCE.create<%ecoreClass.name.toU1Case()%>();
	assertNotNull(result);
	assertTrue(result instanceof <%ecoreClass.name%>);
		result = <%current(1).modelFactoryClassName()%>.eINSTANCE.create(<%current(1).modelPackageClassName()%>.Literals.<%ecoreClass.name.toU1Case().convertToPackageString%>);
	assertNotNull(result);
	assertTrue(result instanceof <%ecoreClass.name%>);
}
<%}%>

<%script type="GenPackage" name="generateTestConvertEnums"%>
<%for (genEnums) {%>
/**
 * Ensures that converting {@link <%ecoreEnum.name.toU1Case()%>} to String can be done through the factory. 
 *
 * @generated
 */
public void testConvert<%ecoreEnum.name.toU1Case()%>ToString() {
	for (<%ecoreEnum.name.toU1Case()%> value : <%ecoreEnum.name.toU1Case()%>.VALUES) {
		Object result = <%current(1).modelFactoryClassName()%>.eINSTANCE.convertToString(<%current(1).modelPackageClassName()%>.Literals.<%ecoreEnum.name.toU1Case().convertToPackageString%>, value);
		assertNotNull(result);
		assertEquals(value.toString(), result);
	}
}
<%}%>

<%script type="GenPackage" name="generateTestCreateEnums"%>
<%for (genEnums) {%>
/**
 * Ensures that creating {@link <%ecoreEnum.name.toU1Case()%>} from String can be done through the factory. 
 *
 * @generated
 */
public void testCreate<%ecoreEnum.name.toU1Case()%>FromString() {
	for (<%ecoreEnum.name.toU1Case()%> value : <%ecoreEnum.name.toU1Case()%>.VALUES) {
		Object result = <%current(1).modelFactoryClassName()%>.eINSTANCE.createFromString(<%current(1).modelPackageClassName()%>.Literals.<%ecoreEnum.name.toU1Case().convertToPackageString%>, value.getLiteral());
		assertNotNull(result);
		assertSame(value, result);

		try {
			<%current(1).modelFactoryClassName()%>.eINSTANCE.createFromString(<%current(1).modelPackageClassName()%>.Literals.<%ecoreEnum.name.toU1Case().convertToPackageString%>, "ThisShouldntBeAKnownEEnumLiteral");
			fail("Expected IllegalArgumentException hasn't been thrown");
		} catch (IllegalArgumentException e) {
			// Expected behavior
		}
	}
}
<%}%>