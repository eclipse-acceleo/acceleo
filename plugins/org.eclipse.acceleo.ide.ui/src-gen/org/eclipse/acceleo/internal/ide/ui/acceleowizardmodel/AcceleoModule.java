/**
 * <copyright>
 * </copyright>
 *
 * $Id: AcceleoModule.java,v 1.1 2011/02/22 08:40:08 sbegaudeau Exp $
 */
package org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Acceleo Module</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getProjectName <em>Project Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getParentFolder <em>Parent Folder</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getMetamodelURIs <em>Metamodel UR Is</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getModuleElement <em>Module Element</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#isGenerateDocumentation <em>Generate Documentation</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoModule()
 * @model
 * @generated
 */
public interface AcceleoModule extends EObject {
	/**
	 * Returns the value of the '<em><b>Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Project Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Project Name</em>' attribute.
	 * @see #setProjectName(String)
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoModule_ProjectName()
	 * @model required="true" derived="true"
	 * @generated
	 */
	String getProjectName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getProjectName <em>Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Project Name</em>' attribute.
	 * @see #getProjectName()
	 * @generated
	 */
	void setProjectName(String value);

	/**
	 * Returns the value of the '<em><b>Parent Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent Folder</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent Folder</em>' attribute.
	 * @see #setParentFolder(String)
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoModule_ParentFolder()
	 * @model required="true"
	 * @generated
	 */
	String getParentFolder();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getParentFolder <em>Parent Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent Folder</em>' attribute.
	 * @see #getParentFolder()
	 * @generated
	 */
	void setParentFolder(String value);

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
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoModule_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Metamodel UR Is</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metamodel UR Is</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metamodel UR Is</em>' attribute list.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoModule_MetamodelURIs()
	 * @model required="true"
	 * @generated
	 */
	EList<String> getMetamodelURIs();

	/**
	 * Returns the value of the '<em><b>Module Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Module Element</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Element</em>' containment reference.
	 * @see #setModuleElement(AcceleoModuleElement)
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoModule_ModuleElement()
	 * @model containment="true"
	 * @generated
	 */
	AcceleoModuleElement getModuleElement();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#getModuleElement <em>Module Element</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Module Element</em>' containment reference.
	 * @see #getModuleElement()
	 * @generated
	 */
	void setModuleElement(AcceleoModuleElement value);

	/**
	 * Returns the value of the '<em><b>Generate Documentation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generate Documentation</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generate Documentation</em>' attribute.
	 * @see #setGenerateDocumentation(boolean)
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoModule_GenerateDocumentation()
	 * @model required="true"
	 * @generated
	 */
	boolean isGenerateDocumentation();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule#isGenerateDocumentation <em>Generate Documentation</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generate Documentation</em>' attribute.
	 * @see #isGenerateDocumentation()
	 * @generated
	 */
	void setGenerateDocumentation(boolean value);

} // AcceleoModule
