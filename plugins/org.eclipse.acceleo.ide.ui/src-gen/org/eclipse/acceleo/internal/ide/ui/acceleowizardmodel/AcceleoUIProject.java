/**
 * <copyright>
 * </copyright>
 *
 * $Id: AcceleoUIProject.java,v 1.1 2011/02/22 08:40:08 sbegaudeau Exp $
 */
package org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Acceleo UI Project</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getGeneratorName <em>Generator Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getPluginsDependencies <em>Plugins Dependencies</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getModules <em>Modules</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getModulePlugins <em>Module Plugins</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getModuleJavaClass <em>Module Java Class</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getModelNameFilter <em>Model Name Filter</em>}</li>
 *   <li>{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getTargetFolderRelativePath <em>Target Folder Relative Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoUIProject()
 * @model
 * @generated
 */
public interface AcceleoUIProject extends EObject {
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
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoUIProject_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Generator Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generator Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Generator Name</em>' attribute.
	 * @see #setGeneratorName(String)
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoUIProject_GeneratorName()
	 * @model required="true"
	 * @generated
	 */
	String getGeneratorName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getGeneratorName <em>Generator Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Generator Name</em>' attribute.
	 * @see #getGeneratorName()
	 * @generated
	 */
	void setGeneratorName(String value);

	/**
	 * Returns the value of the '<em><b>Plugins Dependencies</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plugins Dependencies</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plugins Dependencies</em>' attribute list.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoUIProject_PluginsDependencies()
	 * @model
	 * @generated
	 */
	EList<String> getPluginsDependencies();

	/**
	 * Returns the value of the '<em><b>Modules</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Modules</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modules</em>' attribute list.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoUIProject_Modules()
	 * @model
	 * @generated
	 */
	EList<String> getModules();

	/**
	 * Returns the value of the '<em><b>Module Plugins</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Module Plugins</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Plugins</em>' attribute list.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoUIProject_ModulePlugins()
	 * @model
	 * @generated
	 */
	EList<String> getModulePlugins();

	/**
	 * Returns the value of the '<em><b>Module Java Class</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Module Java Class</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Module Java Class</em>' attribute list.
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoUIProject_ModuleJavaClass()
	 * @model
	 * @generated
	 */
	EList<String> getModuleJavaClass();

	/**
	 * Returns the value of the '<em><b>Model Name Filter</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model Name Filter</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model Name Filter</em>' attribute.
	 * @see #setModelNameFilter(String)
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoUIProject_ModelNameFilter()
	 * @model required="true"
	 * @generated
	 */
	String getModelNameFilter();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getModelNameFilter <em>Model Name Filter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model Name Filter</em>' attribute.
	 * @see #getModelNameFilter()
	 * @generated
	 */
	void setModelNameFilter(String value);

	/**
	 * Returns the value of the '<em><b>Target Folder Relative Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target Folder Relative Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Folder Relative Path</em>' attribute.
	 * @see #setTargetFolderRelativePath(String)
	 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage#getAcceleoUIProject_TargetFolderRelativePath()
	 * @model required="true"
	 * @generated
	 */
	String getTargetFolderRelativePath();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleoUIProject#getTargetFolderRelativePath <em>Target Folder Relative Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Folder Relative Path</em>' attribute.
	 * @see #getTargetFolderRelativePath()
	 * @generated
	 */
	void setTargetFolderRelativePath(String value);

} // AcceleoUIProject
