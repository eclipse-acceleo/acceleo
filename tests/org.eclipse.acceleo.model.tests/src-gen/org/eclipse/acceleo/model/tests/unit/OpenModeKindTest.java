package org.eclipse.acceleo.model.tests.unit;

import junit.framework.TestCase;

import org.eclipse.acceleo.model.mtl.OpenModeKind;

/**
 * Tests the behavior of the {@link OpenModeKind} enumeration.
 * 
 * @generated
 */
@SuppressWarnings("nls")
public class OpenModeKindTest extends TestCase {
	/**
	 * Tests the behavior of the {@link OpenModeKind#get(int)} method.
	 * 
	 * @generated
 	 */
	public void testGetInt() {
		int highestValue = -1;
		for (OpenModeKind value : OpenModeKind.VALUES) {
			assertSame(OpenModeKind.get(value.getValue()), value);
			if (value.getValue() > highestValue) {
				highestValue = value.getValue();
			}
		}
		assertNull(OpenModeKind.get(++highestValue));
	}

	/**
	 * Tests the behavior of the {@link OpenModeKind#get(java.lang.String)} method.
	 * 
	 * @generated
 	 */
	public void testGetString() {
		for (OpenModeKind value : OpenModeKind.VALUES) {
			assertSame(OpenModeKind.get(value.getLiteral()), value);
		}
		assertNull(OpenModeKind.get("ThisIsNotAValueOfTheTestedEnum"));
	}

	/**
	 * Tests the behavior of the {@link OpenModeKind#getByName(java.lang.String)} method.
	 * 
	 * @generated
 	 */
	public void testGetByName() {
		for (OpenModeKind value : OpenModeKind.VALUES) {
			assertSame(OpenModeKind.getByName(value.getName()), value);
		}
		assertNull(OpenModeKind.getByName("ThisIsNotTheNameOfAValueFromTheTestedEnum"));
	}
}

