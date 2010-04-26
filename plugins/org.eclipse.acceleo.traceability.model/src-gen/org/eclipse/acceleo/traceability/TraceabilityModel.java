/**
 * <copyright>
 * </copyright>
 *
 * $Id: TraceabilityModel.java,v 1.2 2010/04/26 15:24:13 lgoubet Exp $
 */
package org.eclipse.acceleo.traceability;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Model</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.acceleo.traceability.TraceabilityModel#getModules <em>Modules</em>}</li>
 * <li>{@link org.eclipse.acceleo.traceability.TraceabilityModel#getGeneratedFiles <em>Generated Files</em>}</li>
 * <li>{@link org.eclipse.acceleo.traceability.TraceabilityModel#getModelFiles <em>Model Files</em>}</li>
 * </ul>
 * </p>
 * 
 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getTraceabilityModel()
 * @model
 * @generated
 */
public interface TraceabilityModel extends EObject {
	/**
	 * Returns the value of the '<em><b>Modules</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.traceability.ModuleFile}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Modules</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Modules</em>' containment reference list.
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getTraceabilityModel_Modules()
	 * @model containment="true"
	 * @generated
	 */
	EList<ModuleFile> getModules();

	/**
	 * Returns the value of the '<em><b>Generated Files</b></em>' containment reference list. The list
	 * contents are of type {@link org.eclipse.acceleo.traceability.GeneratedFile}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Generated Files</em>' containment reference list isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Generated Files</em>' containment reference list.
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getTraceabilityModel_GeneratedFiles()
	 * @model containment="true"
	 * @generated
	 */
	EList<GeneratedFile> getGeneratedFiles();

	/**
	 * Returns the value of the '<em><b>Model Files</b></em>' containment reference list. The list contents
	 * are of type {@link org.eclipse.acceleo.traceability.ModelFile}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model Files</em>' containment reference list isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Model Files</em>' containment reference list.
	 * @see org.eclipse.acceleo.traceability.TraceabilityPackage#getTraceabilityModel_ModelFiles()
	 * @model containment="true"
	 * @generated
	 */
	EList<ModelFile> getModelFiles();

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model
	 * @generated
	 */
	GeneratedFile getGeneratedFile(String filePath);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model
	 * @generated
	 */
	GeneratedFile getGeneratedFile(ModuleElement moduleElement);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model
	 * @generated
	 */
	ModuleFile getGenerationModule(String moduleURI);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model
	 * @generated
	 */
	ModuleFile getGenerationModule(Resource resource);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model
	 * @generated
	 */
	ModelFile getInputModel(String modelURI);

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @model
	 * @generated
	 */
	ModelFile getInputModel(Resource resource);

} // TraceabilityModel
