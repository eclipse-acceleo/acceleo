<%
metamodel http://www.eclipse.org/emf/2002/GenModel
import org.eclipse.acceleo.benchmark.ecore2unittests.acceleo2.template.common
%>

<%script type="GenEnum" name="filename"%>
src-gen/<%testPackage().replaceAll("\.", "/")%>/unit/<%ecoreEnum.name.toU1Case()%>Test.java

<%script type="GenEnum" name="enumerationTest" file="<%filename%>"%>
package <%testPackage()%>.unit;

import junit.framework.TestCase;

import <%modelPackage()%>.<%ecoreEnum.name.toU1Case()%>;

/**
 * Tests the behavior of the {@link <%ecoreEnum.name.toU1Case()%>} enumeration.
 * 
 * @generated
 */
@SuppressWarnings("nls")
public class <%ecoreEnum.name.toU1Case()%>Test extends TestCase {
	/**
	 * Tests the behavior of the {@link <%ecoreEnum.name.toU1Case()%>#get(int)} method.
	 * 
	 * @generated
 	 */
	public void testGetInt() {
		int highestValue = -1;
		for (<%ecoreEnum.name.toU1Case()%> value : <%ecoreEnum.name.toU1Case()%>.VALUES) {
			assertSame(<%ecoreEnum.name.toU1Case()%>.get(value.getValue()), value);
			if (value.getValue() > highestValue) {
				highestValue = value.getValue();
			}
		}
		assertNull(<%ecoreEnum.name.toU1Case()%>.get(++highestValue));
	}

	/**
	 * Tests the behavior of the {@link <%ecoreEnum.name.toU1Case()%>#get(java.lang.String)} method.
	 * 
	 * @generated
 	 */
	public void testGetString() {
		for (<%ecoreEnum.name.toU1Case()%> value : <%ecoreEnum.name.toU1Case()%>.VALUES) {
			assertSame(<%ecoreEnum.name.toU1Case()%>.get(value.getLiteral()), value);
		}
		assertNull(<%ecoreEnum.name.toU1Case()%>.get("ThisIsNotAValueOfTheTestedEnum"));
	}

	/**
	 * Tests the behavior of the {@link <%ecoreEnum.name.toU1Case()%>#getByName(java.lang.String)} method.
	 * 
	 * @generated
 	 */
	public void testGetByName() {
		for (<%ecoreEnum.name.toU1Case()%> value : <%ecoreEnum.name.toU1Case()%>.VALUES) {
			assertSame(<%ecoreEnum.name.toU1Case()%>.getByName(value.getName()), value);
		}
		assertNull(<%ecoreEnum.name.toU1Case()%>.getByName("ThisIsNotTheNameOfAValueFromTheTestedEnum"));
	}
}