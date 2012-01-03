/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.traceability.util;

import java.util.List;

import org.eclipse.acceleo.traceability.*;
import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.GeneratedText;
import org.eclipse.acceleo.traceability.InputElement;
import org.eclipse.acceleo.traceability.ModelFile;
import org.eclipse.acceleo.traceability.ModuleElement;
import org.eclipse.acceleo.traceability.ModuleFile;
import org.eclipse.acceleo.traceability.Resource;
import org.eclipse.acceleo.traceability.TraceabilityModel;
import org.eclipse.acceleo.traceability.TraceabilityPackage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the
 * model, starting with the actual class of the object and proceeding up the inheritance hierarchy until a
 * non-null result is returned, which is the result of the switch. <!-- end-user-doc -->
 * @see org.eclipse.acceleo.traceability.TraceabilityPackage
 * @generated
 */
public class TraceabilitySwitch<T1> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected static TraceabilityPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public TraceabilitySwitch() {
		if (modelPackage == null) {
			modelPackage = TraceabilityPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T1 doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T1 doSwitch(EClass theEClass, EObject theEObject) {
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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T1 doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case TraceabilityPackage.TRACEABILITY_MODEL: {
				TraceabilityModel traceabilityModel = (TraceabilityModel)theEObject;
				T1 result = caseTraceabilityModel(traceabilityModel);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TraceabilityPackage.RESOURCE: {
				Resource resource = (Resource)theEObject;
				T1 result = caseResource(resource);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TraceabilityPackage.MODEL_FILE: {
				ModelFile modelFile = (ModelFile)theEObject;
				T1 result = caseModelFile(modelFile);
				if (result == null) result = caseResource(modelFile);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TraceabilityPackage.MODULE_FILE: {
				ModuleFile moduleFile = (ModuleFile)theEObject;
				T1 result = caseModuleFile(moduleFile);
				if (result == null) result = caseResource(moduleFile);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TraceabilityPackage.GENERATED_FILE: {
				GeneratedFile generatedFile = (GeneratedFile)theEObject;
				T1 result = caseGeneratedFile(generatedFile);
				if (result == null) result = caseResource(generatedFile);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TraceabilityPackage.INPUT_ELEMENT: {
				InputElement inputElement = (InputElement)theEObject;
				T1 result = caseInputElement(inputElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TraceabilityPackage.MODULE_ELEMENT: {
				ModuleElement moduleElement = (ModuleElement)theEObject;
				T1 result = caseModuleElement(moduleElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TraceabilityPackage.GENERATED_TEXT: {
				GeneratedText generatedText = (GeneratedText)theEObject;
				T1 result = caseGeneratedText(generatedText);
				if (result == null) result = caseIComparable(generatedText);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Model</em>'.
	 * <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch. <!--
	 * end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T1 caseTraceabilityModel(TraceabilityModel object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Resource</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Resource</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T1 caseResource(Resource object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Model File</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Model File</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T1 caseModelFile(ModelFile object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Module File</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Module File</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T1 caseModuleFile(ModuleFile object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generated File</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generated File</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T1 caseGeneratedFile(GeneratedFile object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Input Element</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Input Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T1 caseInputElement(InputElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Module Element</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Module Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T1 caseModuleElement(ModuleElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Generated Text</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Generated Text</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T1 caseGeneratedText(GeneratedText object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IComparable</em>'. <!--
	 * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the
	 * switch. <!-- end-user-doc -->
	 * 
	 * @param object
	 *            the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IComparable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public <T> T1 caseIComparable(Comparable<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc
	 * --> This implementation returns null; returning a non-null result will terminate the switch, but this
	 * is the last case anyway. <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@SuppressWarnings("unused")
	public T1 defaultCase(EObject object) {
		return null;
	}

} // TraceabilitySwitch
