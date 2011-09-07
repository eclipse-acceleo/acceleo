/**
 * <copyright>
 * </copyright>
 *
 * $Id: AcceleoProject.java,v 1.2 2011/02/25 12:47:28 sbegaudeau Exp $
 */
package org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Acceleo Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject#getGeneratorName <em>Generator Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject#getAcceleoModules <em>Acceleo Modules</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject#getPluginDependencies <em>Plugin Dependencies</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject#getExportedPackages <em>Exported Packages</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject#getJre <em>Jre</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoProject()
 * @model
 * @generated
 */
public interface AcceleoProject extends EObject {
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
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoProject_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Generator Name</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generator Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generator Name</em>' attribute.
	 * @see #setGeneratorName(String)
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoProject_GeneratorName()
	 * @model default="" required="true" derived="true"
	 * @generated
	 */
	String getGeneratorName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject#getGeneratorName <em>Generator Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generator Name</em>' attribute.
	 * @see #getGeneratorName()
	 * @generated
	 */
	void setGeneratorName(String value);

	/**
	 * Returns the value of the '<em><b>Acceleo Modules</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoModule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Acceleo Modules</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Acceleo Modules</em>' containment reference list.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoProject_AcceleoModules()
	 * @model containment="true"
	 * @generated
	 */
	EList<AcceleoModule> getAcceleoModules();

	/**
	 * Returns the value of the '<em><b>Plugin Dependencies</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plugin Dependencies</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plugin Dependencies</em>' attribute list.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoProject_PluginDependencies()
	 * @model derived="true"
	 * @generated
	 */
	EList<String> getPluginDependencies();

	/**
	 * Returns the value of the '<em><b>Exported Packages</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exported Packages</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exported Packages</em>' attribute list.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoProject_ExportedPackages()
	 * @model
	 * @generated
	 */
	EList<String> getExportedPackages();

	/**
	 * Returns the value of the '<em><b>Jre</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Jre</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Jre</em>' attribute.
	 * @see #setJre(String)
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoProject_Jre()
	 * @model
	 * @generated
	 */
	String getJre();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoProject#getJre <em>Jre</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Jre</em>' attribute.
	 * @see #getJre()
	 * @generated
	 */
	void setJre(String value);

} // AcceleoProject
