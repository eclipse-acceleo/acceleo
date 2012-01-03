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
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter
 * <code>createXXX</code> method for each class of the model. <!-- end-user-doc -->
 * @see org.eclipse.acceleo.traceability.TraceabilityPackage
 * @generated
 */
public class TraceabilityAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected static TraceabilityPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public TraceabilityAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = TraceabilityPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc --> This
	 * implementation returns <code>true</code> if the object is either the model's package or is an instance
	 * object of the model. <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	protected TraceabilitySwitch<Adapter> modelSwitch = new TraceabilitySwitch<Adapter>() {
			@Override
			public Adapter caseTraceabilityModel(TraceabilityModel object) {
				return createTraceabilityModelAdapter();
			}
			@Override
			public Adapter caseResource(Resource object) {
				return createResourceAdapter();
			}
			@Override
			public Adapter caseModelFile(ModelFile object) {
				return createModelFileAdapter();
			}
			@Override
			public Adapter caseModuleFile(ModuleFile object) {
				return createModuleFileAdapter();
			}
			@Override
			public Adapter caseGeneratedFile(GeneratedFile object) {
				return createGeneratedFileAdapter();
			}
			@Override
			public Adapter caseInputElement(InputElement object) {
				return createInputElementAdapter();
			}
			@Override
			public Adapter caseModuleElement(ModuleElement object) {
				return createModuleElementAdapter();
			}
			@Override
			public Adapter caseGeneratedText(GeneratedText object) {
				return createGeneratedTextAdapter();
			}
			@Override
			public <T> Adapter caseIComparable(Comparable<T> object) {
				return createIComparableAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.traceability.TraceabilityModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.traceability.TraceabilityModel
	 * @generated
	 */
	public Adapter createTraceabilityModelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.traceability.Resource <em>Resource</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.traceability.Resource
	 * @generated
	 */
	public Adapter createResourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.traceability.ModelFile <em>Model File</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.traceability.ModelFile
	 * @generated
	 */
	public Adapter createModelFileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.traceability.ModuleFile <em>Module File</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can
	 * easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.traceability.ModuleFile
	 * @generated
	 */
	public Adapter createModuleFileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.traceability.GeneratedFile <em>Generated File</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.traceability.GeneratedFile
	 * @generated
	 */
	public Adapter createGeneratedFileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.traceability.InputElement <em>Input Element</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.traceability.InputElement
	 * @generated
	 */
	public Adapter createInputElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.traceability.ModuleElement <em>Module Element</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.traceability.ModuleElement
	 * @generated
	 */
	public Adapter createModuleElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.acceleo.traceability.GeneratedText <em>Generated Text</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.traceability.GeneratedText
	 * @generated
	 */
	public Adapter createGeneratedTextAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link java.lang.Comparable <em>IComparable</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see java.lang.Comparable
	 * @generated
	 */
	public Adapter createIComparableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc --> This default implementation returns
	 * null. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // TraceabilityAdapterFactory
