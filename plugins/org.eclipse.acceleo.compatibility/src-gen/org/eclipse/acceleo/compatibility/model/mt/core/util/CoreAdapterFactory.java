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
package org.eclipse.acceleo.compatibility.model.mt.core.util;

import org.eclipse.acceleo.compatibility.model.mt.Resource;
import org.eclipse.acceleo.compatibility.model.mt.core.ASTNode;
import org.eclipse.acceleo.compatibility.model.mt.core.CorePackage;
import org.eclipse.acceleo.compatibility.model.mt.core.FilePath;
import org.eclipse.acceleo.compatibility.model.mt.core.Metamodel;
import org.eclipse.acceleo.compatibility.model.mt.core.Method;
import org.eclipse.acceleo.compatibility.model.mt.core.Parameter;
import org.eclipse.acceleo.compatibility.model.mt.core.Script;
import org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor;
import org.eclipse.acceleo.compatibility.model.mt.core.Service;
import org.eclipse.acceleo.compatibility.model.mt.core.Template;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides an adapter
 * <code>createXXX</code> method for each class of the model. <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.core.CorePackage
 * @generated
 */
public class CoreAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected static CorePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CoreAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = CorePackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object. <!-- begin-user-doc --> This
	 * implementation returns <code>true</code> if the object is either the model's package or is an instance
	 * object of the model. <!-- end-user-doc -->
	 * 
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
	 * The switch that delegates to the <code>createXXX</code> methods. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected CoreSwitch<Adapter> modelSwitch = new CoreSwitch<Adapter>() {
		@Override
		public Adapter caseASTNode(ASTNode object) {
			return createASTNodeAdapter();
		}

		@Override
		public Adapter caseTemplate(Template object) {
			return createTemplateAdapter();
		}

		@Override
		public Adapter caseScript(Script object) {
			return createScriptAdapter();
		}

		@Override
		public Adapter caseScriptDescriptor(ScriptDescriptor object) {
			return createScriptDescriptorAdapter();
		}

		@Override
		public Adapter caseFilePath(FilePath object) {
			return createFilePathAdapter();
		}

		@Override
		public Adapter caseMetamodel(Metamodel object) {
			return createMetamodelAdapter();
		}

		@Override
		public Adapter caseService(Service object) {
			return createServiceAdapter();
		}

		@Override
		public Adapter caseMethod(Method object) {
			return createMethodAdapter();
		}

		@Override
		public Adapter caseParameter(Parameter object) {
			return createParameterAdapter();
		}

		@Override
		public Adapter caseResource(Resource object) {
			return createResourceAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param target
	 *            the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.ASTNode <em>AST Node</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore
	 * a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.ASTNode
	 * @generated
	 */
	public Adapter createASTNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.Template <em>Template</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Template
	 * @generated
	 */
	public Adapter createTemplateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.Script <em>Script</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore
	 * a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Script
	 * @generated
	 */
	public Adapter createScriptAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor <em>Script Descriptor</em>}'.
	 * <!-- begin-user-doc --> This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor
	 * @generated
	 */
	public Adapter createScriptDescriptorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.FilePath <em>File Path</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.FilePath
	 * @generated
	 */
	public Adapter createFilePathAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.Metamodel <em>Metamodel</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Metamodel
	 * @generated
	 */
	public Adapter createMetamodelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.Service <em>Service</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore
	 * a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Service
	 * @generated
	 */
	public Adapter createServiceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.Method <em>Method</em>}'. <!-- begin-user-doc
	 * --> This default implementation returns null so that we can easily ignore cases; it's useful to ignore
	 * a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Method
	 * @generated
	 */
	public Adapter createMethodAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.Parameter <em>Parameter</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Parameter
	 * @generated
	 */
	public Adapter createParameterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.Resource <em>Resource</em>}'. <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases; it's useful to ignore a
	 * case when inheritance will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see org.eclipse.acceleo.compatibility.model.mt.Resource
	 * @generated
	 */
	public Adapter createResourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case. <!-- begin-user-doc --> This default implementation returns
	 * null. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // CoreAdapterFactory
