/**
 * <copyright>
 * </copyright>
 *
 * $Id: AcceleowizardmodelSwitch.java,v 1.3 2011/04/19 13:28:35 sbegaudeau Exp $
 */
package org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.util;

import java.util.List;

import org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.acceleo.internal.ide.ui.acceleowizardmodel.AcceleowizardmodelPackage
 * @generated
 */
public class AcceleowizardmodelSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AcceleowizardmodelPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AcceleowizardmodelSwitch() {
		if (modelPackage == null) {
			modelPackage = AcceleowizardmodelPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case AcceleowizardmodelPackage.ACCELEO_PROJECT: {
				AcceleoProject acceleoProject = (AcceleoProject)theEObject;
				T result = caseAcceleoProject(acceleoProject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AcceleowizardmodelPackage.ACCELEO_UI_PROJECT: {
				AcceleoUIProject acceleoUIProject = (AcceleoUIProject)theEObject;
				T result = caseAcceleoUIProject(acceleoUIProject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AcceleowizardmodelPackage.ACCELEO_MODULE: {
				AcceleoModule acceleoModule = (AcceleoModule)theEObject;
				T result = caseAcceleoModule(acceleoModule);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AcceleowizardmodelPackage.ACCELEO_MODULE_ELEMENT: {
				AcceleoModuleElement acceleoModuleElement = (AcceleoModuleElement)theEObject;
				T result = caseAcceleoModuleElement(acceleoModuleElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AcceleowizardmodelPackage.ACCELEO_MAIN_CLASS: {
				AcceleoMainClass acceleoMainClass = (AcceleoMainClass)theEObject;
				T result = caseAcceleoMainClass(acceleoMainClass);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AcceleowizardmodelPackage.ACCELEO_PACKAGE: {
				AcceleoPackage acceleoPackage = (AcceleoPackage)theEObject;
				T result = caseAcceleoPackage(acceleoPackage);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AcceleowizardmodelPackage.ACCELEO_POM: {
				AcceleoPom acceleoPom = (AcceleoPom)theEObject;
				T result = caseAcceleoPom(acceleoPom);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AcceleowizardmodelPackage.ACCELEO_POM_DEPENDENCY: {
				AcceleoPomDependency acceleoPomDependency = (AcceleoPomDependency)theEObject;
				T result = caseAcceleoPomDependency(acceleoPomDependency);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Acceleo Project</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Acceleo Project</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAcceleoProject(AcceleoProject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Acceleo UI Project</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Acceleo UI Project</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAcceleoUIProject(AcceleoUIProject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Acceleo Module</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Acceleo Module</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAcceleoModule(AcceleoModule object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Acceleo Module Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Acceleo Module Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAcceleoModuleElement(AcceleoModuleElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Acceleo Main Class</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Acceleo Main Class</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAcceleoMainClass(AcceleoMainClass object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Acceleo Package</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Acceleo Package</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAcceleoPackage(AcceleoPackage object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Acceleo Pom</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Acceleo Pom</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAcceleoPom(AcceleoPom object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Acceleo Pom Dependency</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Acceleo Pom Dependency</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAcceleoPomDependency(AcceleoPomDependency object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}

} //AcceleowizardmodelSwitch
