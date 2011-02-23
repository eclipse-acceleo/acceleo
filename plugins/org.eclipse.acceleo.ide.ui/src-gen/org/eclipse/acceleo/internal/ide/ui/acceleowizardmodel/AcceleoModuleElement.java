/**
 * <copyright>
 * </copyright>
 *
 * $Id: AcceleoModuleElement.java,v 1.2 2011/02/23 15:35:39 sbegaudeau Exp $
 */
package org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Acceleo Module Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement#getParameterType <em>Parameter Type</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement#getKind <em>Kind</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement#isIsMain <em>Is Main</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement#isGenerateFile <em>Generate File</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoModuleElement()
 * @model
 * @generated
 */
public interface AcceleoModuleElement extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoModuleElement_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Parameter Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter Type</em>' attribute.
	 * @see #setParameterType(String)
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoModuleElement_ParameterType()
	 * @model required="true"
	 * @generated
	 */
	String getParameterType();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement#getParameterType <em>Parameter Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parameter Type</em>' attribute.
	 * @see #getParameterType()
	 * @generated
	 */
	void setParameterType(String value);

	/**
	 * Returns the value of the '<em><b>Kind</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.ModuleElementKind}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Kind</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.ModuleElementKind
	 * @see #setKind(ModuleElementKind)
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoModuleElement_Kind()
	 * @model required="true"
	 * @generated
	 */
	ModuleElementKind getKind();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement#getKind <em>Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Kind</em>' attribute.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.ModuleElementKind
	 * @see #getKind()
	 * @generated
	 */
	void setKind(ModuleElementKind value);

	/**
	 * Returns the value of the '<em><b>Is Main</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Main</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Main</em>' attribute.
	 * @see #setIsMain(boolean)
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoModuleElement_IsMain()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isIsMain();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement#isIsMain <em>Is Main</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Main</em>' attribute.
	 * @see #isIsMain()
	 * @generated
	 */
	void setIsMain(boolean value);

	/**
	 * Returns the value of the '<em><b>Generate File</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generate File</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generate File</em>' attribute.
	 * @see #setGenerateFile(boolean)
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoModuleElement_GenerateFile()
	 * @model default="false" required="true"
	 * @generated
	 */
	boolean isGenerateFile();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModuleElement#isGenerateFile <em>Generate File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generate File</em>' attribute.
	 * @see #isGenerateFile()
	 * @generated
	 */
	void setGenerateFile(boolean value);

} // AcceleoModuleElement
