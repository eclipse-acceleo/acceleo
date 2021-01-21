/**
 */
package inference;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>C</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link inference.C#getCAttr <em>CAttr</em>}</li>
 * </ul>
 *
 * @see inference.InferencePackage#getC()
 * @model
 * @generated
 */
public interface C extends B {

	/**
	 * Returns the value of the '<em><b>CAttr</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the value of the '<em>CAttr</em>' attribute.
	 * @see #setCAttr(Boolean)
	 * @see inference.InferencePackage#getC_CAttr()
	 * @model dataType="inference.bool"
	 * @generated
	 */
	Boolean getCAttr();

	/**
	 * Sets the value of the '{@link inference.C#getCAttr <em>CAttr</em>}' attribute. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>CAttr</em>' attribute.
	 * @see #getCAttr()
	 * @generated
	 */
	void setCAttr(Boolean value);
} // C
