package org.eclipse.acceleo.model.tests.unit;

import junit.framework.TestCase;

import org.eclipse.acceleo.model.mtl.VisibilityKind;

/**
 * Tests the behavior of the {@link VisibilityKind} enumeration.
 * 
 * @generated
 */
@SuppressWarnings("nls")
public class VisibilityKindTest extends TestCase {
	/**
	 * Tests the behavior of the {@link VisibilityKind#get(int)} method.
	 * 
	 * @generated
 	 */
	public void testGetInt() {
		int highestValue = -1;
		for (VisibilityKind value : VisibilityKind.VALUES) {
			assertSame(VisibilityKind.get(value.getValue()), value);
			if (value.getValue() > highestValue) {
				highestValue = value.getValue();
			}
		}
		assertNull(VisibilityKind.get(++highestValue));
	}

	/**
	 * Tests the behavior of the {@link VisibilityKind#get(java.lang.String)} method.
	 * 
	 * @generated
 	 */
	public void testGetString() {
		for (VisibilityKind value : VisibilityKind.VALUES) {
			assertSame(VisibilityKind.get(value.getLiteral()), value);
		}
		assertNull(VisibilityKind.get("ThisIsNotAValueOfTheTestedEnum"));
	}

	/**
	 * Tests the behavior of the {@link VisibilityKind#getByName(java.lang.String)} method.
	 * 
	 * @generated
 	 */
	public void testGetByName() {
		for (VisibilityKind value : VisibilityKind.VALUES) {
			assertSame(VisibilityKind.getByName(value.getName()), value);
		}
		assertNull(VisibilityKind.getByName("ThisIsNotTheNameOfAValueFromTheTestedEnum"));
	}
}

