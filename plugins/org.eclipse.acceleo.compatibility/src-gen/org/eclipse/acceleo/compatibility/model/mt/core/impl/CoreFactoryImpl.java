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
package org.eclipse.acceleo.compatibility.model.mt.core.impl;

import org.eclipse.acceleo.compatibility.model.mt.core.CoreFactory;
import org.eclipse.acceleo.compatibility.model.mt.core.CorePackage;
import org.eclipse.acceleo.compatibility.model.mt.core.FilePath;
import org.eclipse.acceleo.compatibility.model.mt.core.Metamodel;
import org.eclipse.acceleo.compatibility.model.mt.core.Method;
import org.eclipse.acceleo.compatibility.model.mt.core.Parameter;
import org.eclipse.acceleo.compatibility.model.mt.core.Script;
import org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor;
import org.eclipse.acceleo.compatibility.model.mt.core.Service;
import org.eclipse.acceleo.compatibility.model.mt.core.Template;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * 
 * @generated
 */
public class CoreFactoryImpl extends EFactoryImpl implements CoreFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static CoreFactory init() {
		try {
			CoreFactory theCoreFactory = (CoreFactory)EPackage.Registry.INSTANCE
					.getEFactory("http://www.eclipse.org/acceleo/mt/2.6.0/core"); //$NON-NLS-1$
			if (theCoreFactory != null) {
				return theCoreFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CoreFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CoreFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case CorePackage.TEMPLATE:
				return createTemplate();
			case CorePackage.SCRIPT:
				return createScript();
			case CorePackage.SCRIPT_DESCRIPTOR:
				return createScriptDescriptor();
			case CorePackage.FILE_PATH:
				return createFilePath();
			case CorePackage.METAMODEL:
				return createMetamodel();
			case CorePackage.SERVICE:
				return createService();
			case CorePackage.METHOD:
				return createMethod();
			case CorePackage.PARAMETER:
				return createParameter();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() //$NON-NLS-1$
						+ "' is not a valid classifier"); //$NON-NLS-1$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Template createTemplate() {
		TemplateImpl template = new TemplateImpl();
		return template;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Script createScript() {
		ScriptImpl script = new ScriptImpl();
		return script;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ScriptDescriptor createScriptDescriptor() {
		ScriptDescriptorImpl scriptDescriptor = new ScriptDescriptorImpl();
		return scriptDescriptor;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public FilePath createFilePath() {
		FilePathImpl filePath = new FilePathImpl();
		return filePath;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Metamodel createMetamodel() {
		MetamodelImpl metamodel = new MetamodelImpl();
		return metamodel;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Service createService() {
		ServiceImpl service = new ServiceImpl();
		return service;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Method createMethod() {
		MethodImpl method = new MethodImpl();
		return method;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Parameter createParameter() {
		ParameterImpl parameter = new ParameterImpl();
		return parameter;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public CorePackage getCorePackage() {
		return (CorePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CorePackage getPackage() {
		return CorePackage.eINSTANCE;
	}

} // CoreFactoryImpl
