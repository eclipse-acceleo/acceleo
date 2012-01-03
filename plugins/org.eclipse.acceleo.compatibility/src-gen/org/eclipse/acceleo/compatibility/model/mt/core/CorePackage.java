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
package org.eclipse.acceleo.compatibility.model.mt.core;

import org.eclipse.acceleo.compatibility.model.mt.MtPackage;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to
 * represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.acceleo.compatibility.model.mt.core.CoreFactory
 * @model kind="package"
 * @generated
 */
public interface CorePackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "core"; //$NON-NLS-1$

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/acceleo/mt/2.6.0/core"; //$NON-NLS-1$

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "core"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	CorePackage eINSTANCE = org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.compatibility.model.mt.core.impl.ASTNodeImpl
	 * <em>AST Node</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.ASTNodeImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl#getASTNode()
	 * @generated
	 */
	int AST_NODE = 0;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AST_NODE__BEGIN = 0;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AST_NODE__END = 1;

	/**
	 * The number of structural features of the '<em>AST Node</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AST_NODE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.compatibility.model.mt.core.impl.TemplateImpl
	 * <em>Template</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.TemplateImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl#getTemplate()
	 * @generated
	 */
	int TEMPLATE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__NAME = MtPackage.RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Imports</b></em>' reference list. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__IMPORTS = MtPackage.RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Scripts</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__SCRIPTS = MtPackage.RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Begin Tag</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__BEGIN_TAG = MtPackage.RESOURCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>End Tag</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE__END_TAG = MtPackage.RESOURCE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Template</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TEMPLATE_FEATURE_COUNT = MtPackage.RESOURCE_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.compatibility.model.mt.core.impl.ScriptImpl
	 * <em>Script</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.ScriptImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl#getScript()
	 * @generated
	 */
	int SCRIPT = 2;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCRIPT__BEGIN = AST_NODE__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCRIPT__END = AST_NODE__END;

	/**
	 * The feature id for the '<em><b>Descriptor</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCRIPT__DESCRIPTOR = AST_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCRIPT__STATEMENTS = AST_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Script</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCRIPT_FEATURE_COUNT = AST_NODE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.impl.ScriptDescriptorImpl
	 * <em>Script Descriptor</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.ScriptDescriptorImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl#getScriptDescriptor()
	 * @generated
	 */
	int SCRIPT_DESCRIPTOR = 3;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCRIPT_DESCRIPTOR__BEGIN = AST_NODE__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCRIPT_DESCRIPTOR__END = AST_NODE__END;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCRIPT_DESCRIPTOR__NAME = AST_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCRIPT_DESCRIPTOR__TYPE = AST_NODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCRIPT_DESCRIPTOR__DESCRIPTION = AST_NODE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>File</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCRIPT_DESCRIPTOR__FILE = AST_NODE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Post</b></em>' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCRIPT_DESCRIPTOR__POST = AST_NODE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Script Descriptor</em>' class. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SCRIPT_DESCRIPTOR_FEATURE_COUNT = AST_NODE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.compatibility.model.mt.core.impl.FilePathImpl
	 * <em>File Path</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.FilePathImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl#getFilePath()
	 * @generated
	 */
	int FILE_PATH = 4;

	/**
	 * The feature id for the '<em><b>Begin</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_PATH__BEGIN = AST_NODE__BEGIN;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_PATH__END = AST_NODE__END;

	/**
	 * The feature id for the '<em><b>Statements</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_PATH__STATEMENTS = AST_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>File Path</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FILE_PATH_FEATURE_COUNT = AST_NODE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.compatibility.model.mt.core.impl.MetamodelImpl
	 * <em>Metamodel</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.MetamodelImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl#getMetamodel()
	 * @generated
	 */
	int METAMODEL = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METAMODEL__NAME = MtPackage.RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Package Class</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METAMODEL__PACKAGE_CLASS = MtPackage.RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Metamodel</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METAMODEL_FEATURE_COUNT = MtPackage.RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.compatibility.model.mt.core.impl.ServiceImpl
	 * <em>Service</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.ServiceImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl#getService()
	 * @generated
	 */
	int SERVICE = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVICE__NAME = MtPackage.RESOURCE__NAME;

	/**
	 * The feature id for the '<em><b>Methods</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVICE__METHODS = MtPackage.RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Service</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SERVICE_FEATURE_COUNT = MtPackage.RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.compatibility.model.mt.core.impl.MethodImpl
	 * <em>Method</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.MethodImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl#getMethod()
	 * @generated
	 */
	int METHOD = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METHOD__NAME = 0;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' containment reference list. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METHOD__PARAMETERS = 1;

	/**
	 * The feature id for the '<em><b>Return</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METHOD__RETURN = 2;

	/**
	 * The number of structural features of the '<em>Method</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int METHOD_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.acceleo.compatibility.model.mt.core.impl.ParameterImpl
	 * <em>Parameter</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.ParameterImpl
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 8;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARAMETER__TYPE = 0;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = 1;

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.compatibility.model.mt.core.ASTNode
	 * <em>AST Node</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>AST Node</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.ASTNode
	 * @generated
	 */
	EClass getASTNode();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.ASTNode#getBegin <em>Begin</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Begin</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.ASTNode#getBegin()
	 * @see #getASTNode()
	 * @generated
	 */
	EAttribute getASTNode_Begin();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.ASTNode#getEnd <em>End</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>End</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.ASTNode#getEnd()
	 * @see #getASTNode()
	 * @generated
	 */
	EAttribute getASTNode_End();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.compatibility.model.mt.core.Template
	 * <em>Template</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Template</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Template
	 * @generated
	 */
	EClass getTemplate();

	/**
	 * Returns the meta object for the reference list '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.Template#getImports <em>Imports</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Imports</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Template#getImports()
	 * @see #getTemplate()
	 * @generated
	 */
	EReference getTemplate_Imports();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.Template#getScripts <em>Scripts</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Scripts</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Template#getScripts()
	 * @see #getTemplate()
	 * @generated
	 */
	EReference getTemplate_Scripts();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.Template#getBeginTag <em>Begin Tag</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Begin Tag</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Template#getBeginTag()
	 * @see #getTemplate()
	 * @generated
	 */
	EAttribute getTemplate_BeginTag();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.Template#getEndTag <em>End Tag</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>End Tag</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Template#getEndTag()
	 * @see #getTemplate()
	 * @generated
	 */
	EAttribute getTemplate_EndTag();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.compatibility.model.mt.core.Script
	 * <em>Script</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Script</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Script
	 * @generated
	 */
	EClass getScript();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.Script#getDescriptor <em>Descriptor</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Descriptor</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Script#getDescriptor()
	 * @see #getScript()
	 * @generated
	 */
	EReference getScript_Descriptor();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.Script#getStatements <em>Statements</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Script#getStatements()
	 * @see #getScript()
	 * @generated
	 */
	EReference getScript_Statements();

	/**
	 * Returns the meta object for class '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor <em>Script Descriptor</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Script Descriptor</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor
	 * @generated
	 */
	EClass getScriptDescriptor();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor#getName <em>Name</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor#getName()
	 * @see #getScriptDescriptor()
	 * @generated
	 */
	EAttribute getScriptDescriptor_Name();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor#getType <em>Type</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor#getType()
	 * @see #getScriptDescriptor()
	 * @generated
	 */
	EAttribute getScriptDescriptor_Type();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor#getDescription
	 * <em>Description</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor#getDescription()
	 * @see #getScriptDescriptor()
	 * @generated
	 */
	EAttribute getScriptDescriptor_Description();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor#getFile <em>File</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>File</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor#getFile()
	 * @see #getScriptDescriptor()
	 * @generated
	 */
	EReference getScriptDescriptor_File();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor#getPost <em>Post</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Post</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.ScriptDescriptor#getPost()
	 * @see #getScriptDescriptor()
	 * @generated
	 */
	EReference getScriptDescriptor_Post();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.compatibility.model.mt.core.FilePath
	 * <em>File Path</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>File Path</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.FilePath
	 * @generated
	 */
	EClass getFilePath();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.FilePath#getStatements <em>Statements</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Statements</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.FilePath#getStatements()
	 * @see #getFilePath()
	 * @generated
	 */
	EReference getFilePath_Statements();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.compatibility.model.mt.core.Metamodel
	 * <em>Metamodel</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Metamodel</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Metamodel
	 * @generated
	 */
	EClass getMetamodel();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.Metamodel#getPackageClass
	 * <em>Package Class</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Package Class</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Metamodel#getPackageClass()
	 * @see #getMetamodel()
	 * @generated
	 */
	EAttribute getMetamodel_PackageClass();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.compatibility.model.mt.core.Service
	 * <em>Service</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Service</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Service
	 * @generated
	 */
	EClass getService();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.Service#getMethods <em>Methods</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Methods</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Service#getMethods()
	 * @see #getService()
	 * @generated
	 */
	EReference getService_Methods();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.compatibility.model.mt.core.Method
	 * <em>Method</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Method</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Method
	 * @generated
	 */
	EClass getMethod();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.Method#getName <em>Name</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Method#getName()
	 * @see #getMethod()
	 * @generated
	 */
	EAttribute getMethod_Name();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.Method#getParameters <em>Parameters</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Parameters</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Method#getParameters()
	 * @see #getMethod()
	 * @generated
	 */
	EReference getMethod_Parameters();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.Method#getReturn <em>Return</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Return</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Method#getReturn()
	 * @see #getMethod()
	 * @generated
	 */
	EAttribute getMethod_Return();

	/**
	 * Returns the meta object for class '{@link org.eclipse.acceleo.compatibility.model.mt.core.Parameter
	 * <em>Parameter</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.acceleo.compatibility.model.mt.core.Parameter#getType <em>Type</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.acceleo.compatibility.model.mt.core.Parameter#getType()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Type();

	/**
	 * Returns the factory that creates the instances of the model. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CoreFactory getCoreFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.core.impl.ASTNodeImpl <em>AST Node</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.ASTNodeImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl#getASTNode()
		 * @generated
		 */
		EClass AST_NODE = eINSTANCE.getASTNode();

		/**
		 * The meta object literal for the '<em><b>Begin</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute AST_NODE__BEGIN = eINSTANCE.getASTNode_Begin();

		/**
		 * The meta object literal for the '<em><b>End</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute AST_NODE__END = eINSTANCE.getASTNode_End();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.core.impl.TemplateImpl <em>Template</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.TemplateImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl#getTemplate()
		 * @generated
		 */
		EClass TEMPLATE = eINSTANCE.getTemplate();

		/**
		 * The meta object literal for the '<em><b>Imports</b></em>' reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEMPLATE__IMPORTS = eINSTANCE.getTemplate_Imports();

		/**
		 * The meta object literal for the '<em><b>Scripts</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TEMPLATE__SCRIPTS = eINSTANCE.getTemplate_Scripts();

		/**
		 * The meta object literal for the '<em><b>Begin Tag</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEMPLATE__BEGIN_TAG = eINSTANCE.getTemplate_BeginTag();

		/**
		 * The meta object literal for the '<em><b>End Tag</b></em>' attribute feature. <!-- begin-user-doc
		 * --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TEMPLATE__END_TAG = eINSTANCE.getTemplate_EndTag();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.core.impl.ScriptImpl <em>Script</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.ScriptImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl#getScript()
		 * @generated
		 */
		EClass SCRIPT = eINSTANCE.getScript();

		/**
		 * The meta object literal for the '<em><b>Descriptor</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SCRIPT__DESCRIPTOR = eINSTANCE.getScript_Descriptor();

		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SCRIPT__STATEMENTS = eINSTANCE.getScript_Statements();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.core.impl.ScriptDescriptorImpl
		 * <em>Script Descriptor</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.ScriptDescriptorImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl#getScriptDescriptor()
		 * @generated
		 */
		EClass SCRIPT_DESCRIPTOR = eINSTANCE.getScriptDescriptor();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SCRIPT_DESCRIPTOR__NAME = eINSTANCE.getScriptDescriptor_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SCRIPT_DESCRIPTOR__TYPE = eINSTANCE.getScriptDescriptor_Type();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SCRIPT_DESCRIPTOR__DESCRIPTION = eINSTANCE.getScriptDescriptor_Description();

		/**
		 * The meta object literal for the '<em><b>File</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SCRIPT_DESCRIPTOR__FILE = eINSTANCE.getScriptDescriptor_File();

		/**
		 * The meta object literal for the '<em><b>Post</b></em>' containment reference feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SCRIPT_DESCRIPTOR__POST = eINSTANCE.getScriptDescriptor_Post();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.core.impl.FilePathImpl <em>File Path</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.FilePathImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl#getFilePath()
		 * @generated
		 */
		EClass FILE_PATH = eINSTANCE.getFilePath();

		/**
		 * The meta object literal for the '<em><b>Statements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FILE_PATH__STATEMENTS = eINSTANCE.getFilePath_Statements();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.core.impl.MetamodelImpl <em>Metamodel</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.MetamodelImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl#getMetamodel()
		 * @generated
		 */
		EClass METAMODEL = eINSTANCE.getMetamodel();

		/**
		 * The meta object literal for the '<em><b>Package Class</b></em>' attribute feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute METAMODEL__PACKAGE_CLASS = eINSTANCE.getMetamodel_PackageClass();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.core.impl.ServiceImpl <em>Service</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.ServiceImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl#getService()
		 * @generated
		 */
		EClass SERVICE = eINSTANCE.getService();

		/**
		 * The meta object literal for the '<em><b>Methods</b></em>' containment reference list feature. <!--
		 * begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference SERVICE__METHODS = eINSTANCE.getService_Methods();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.core.impl.MethodImpl <em>Method</em>}' class.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.MethodImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl#getMethod()
		 * @generated
		 */
		EClass METHOD = eINSTANCE.getMethod();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute METHOD__NAME = eINSTANCE.getMethod_Name();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference METHOD__PARAMETERS = eINSTANCE.getMethod_Parameters();

		/**
		 * The meta object literal for the '<em><b>Return</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute METHOD__RETURN = eINSTANCE.getMethod_Return();

		/**
		 * The meta object literal for the '
		 * {@link org.eclipse.acceleo.compatibility.model.mt.core.impl.ParameterImpl <em>Parameter</em>}'
		 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.ParameterImpl
		 * @see org.eclipse.acceleo.compatibility.model.mt.core.impl.CorePackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature. <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PARAMETER__TYPE = eINSTANCE.getParameter_Type();

	}

} // CorePackage
