/**
 * Copyright (c) 2008, 2025 Obeo.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.acceleo;

import org.eclipse.acceleo.aql.parser.AcceleoAstResult;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Module</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.acceleo.Module#getMetamodels <em>Metamodels</em>}</li>
 * <li>{@link org.eclipse.acceleo.Module#getExtends <em>Extends</em>}</li>
 * <li>{@link org.eclipse.acceleo.Module#getImports <em>Imports</em>}</li>
 * <li>{@link org.eclipse.acceleo.Module#getModuleElements <em>Module Elements</em>}</li>
 * <li>{@link org.eclipse.acceleo.Module#getStartHeaderPosition <em>Start Header Position</em>}</li>
 * <li>{@link org.eclipse.acceleo.Module#getEndHeaderPosition <em>End Header Position</em>}</li>
 * <li>{@link org.eclipse.acceleo.Module#getAst <em>Ast</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.AcceleoPackage#getModule()
 * @model
 * @generated
 */
public interface Module extends NamedElement, DocumentedElement, AcceleoASTNode {
	/**
	 * Returns the value of the '<em><b>Metamodels</b></em>' reference list. The list contents are of type
	 * {@link org.eclipse.acceleo.Metamodel}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metamodels</em>' reference list isn't clear, there really should be more of
	 * a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Metamodels</em>' reference list.
	 * @see org.eclipse.acceleo.AcceleoPackage#getModule_Metamodels()
	 * @model required="true"
	 * @generated
	 */
	EList<Metamodel> getMetamodels();

	/**
	 * Returns the value of the '<em><b>Extends</b></em>' containment reference. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extends</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Extends</em>' containment reference.
	 * @see #setExtends(ModuleReference)
	 * @see org.eclipse.acceleo.AcceleoPackage#getModule_Extends()
	 * @model containment="true"
	 * @generated
	 */
	ModuleReference getExtends();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.Module#getExtends <em>Extends</em>}' containment
	 * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Extends</em>' containment reference.
	 * @see #getExtends()
	 * @generated
	 */
	void setExtends(ModuleReference value);

	/**
	 * Returns the value of the '<em><b>Imports</b></em>' containment reference list. The list contents are of
	 * type {@link org.eclipse.acceleo.Import}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Imports</em>' containment reference list isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Imports</em>' containment reference list.
	 * @see org.eclipse.acceleo.AcceleoPackage#getModule_Imports()
	 * @model containment="true"
	 * @generated
	 */
	EList<Import> getImports();

	/**
	 * Returns the value of the '<em><b>Module Elements</b></em>' containment reference list. The list
	 * contents are of type {@link org.eclipse.acceleo.ModuleElement}. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Module Elements</em>' containment reference list isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Module Elements</em>' containment reference list.
	 * @see org.eclipse.acceleo.AcceleoPackage#getModule_ModuleElements()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<ModuleElement> getModuleElements();

	/**
	 * Returns the value of the '<em><b>Start Header Position</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Header Position</em>' attribute isn't clear, there really should be
	 * more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Start Header Position</em>' attribute.
	 * @see #setStartHeaderPosition(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getModule_StartHeaderPosition()
	 * @model required="true"
	 * @generated
	 */
	int getStartHeaderPosition();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.Module#getStartHeaderPosition <em>Start Header
	 * Position</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Start Header Position</em>' attribute.
	 * @see #getStartHeaderPosition()
	 * @generated
	 */
	void setStartHeaderPosition(int value);

	/**
	 * Returns the value of the '<em><b>End Header Position</b></em>' attribute. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End Header Position</em>' attribute isn't clear, there really should be more
	 * of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>End Header Position</em>' attribute.
	 * @see #setEndHeaderPosition(int)
	 * @see org.eclipse.acceleo.AcceleoPackage#getModule_EndHeaderPosition()
	 * @model required="true"
	 * @generated
	 */
	int getEndHeaderPosition();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.Module#getEndHeaderPosition <em>End Header
	 * Position</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>End Header Position</em>' attribute.
	 * @see #getEndHeaderPosition()
	 * @generated
	 */
	void setEndHeaderPosition(int value);

	/**
	 * Returns the value of the '<em><b>Ast</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Ast</em>' attribute.
	 * @see #setAst(AcceleoAstResult)
	 * @see org.eclipse.acceleo.AcceleoPackage#getModule_Ast()
	 * @model dataType="org.eclipse.acceleo.AcceleoAstResult" required="true"
	 * @generated
	 */
	AcceleoAstResult getAst();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.Module#getAst <em>Ast</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Ast</em>' attribute.
	 * @see #getAst()
	 * @generated
	 */
	void setAst(AcceleoAstResult value);

	/**
	 * Returns the value of the '<em><b>Encoding</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the value of the '<em>Encoding</em>' attribute.
	 * @see #setEncoding(String)
	 * @see org.eclipse.acceleo.AcceleoPackage#getModule_Encoding()
	 * @model
	 * @generated
	 */
	String getEncoding();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.Module#getEncoding <em>Encoding</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Encoding</em>' attribute.
	 * @see #getEncoding()
	 * @generated
	 */
	void setEncoding(String value);

	/**
	 * Returns the value of the '<em><b>Qualified Name</b></em>' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the value of the '<em>Qualified Name</em>' attribute.
	 * @see #setQualifiedName(String)
	 * @see org.eclipse.acceleo.AcceleoPackage#getModule_QualifiedName()
	 * @model required="true"
	 * @generated
	 */
	String getQualifiedName();

	/**
	 * Sets the value of the '{@link org.eclipse.acceleo.Module#getQualifiedName <em>Qualified Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value
	 *            the new value of the '<em>Qualified Name</em>' attribute.
	 * @see #getQualifiedName()
	 * @generated
	 */
	void setQualifiedName(String value);

} // Module
