/*******************************************************************************
 * Copyright (c) 2016 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.delegates;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.query.ast.AstPackage;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Utility class for delegates.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public final class DelegateUtils {

	/**
	 * Constructor.
	 */
	private DelegateUtils() {
		// nothing to do here
	}

	/**
	 * Sets the AQL setting delegate on the given {@link EPackage}.
	 * 
	 * @param ePackage
	 *            the {@link EPackage}
	 */
	public static void setSettingDelegates(EPackage ePackage) {
		final List<String> delegates = EcoreUtil.getSettingDelegates(ePackage);
		if (!delegates.contains(AstPackage.eNS_URI)) {
			final List<String> newDelegates = new ArrayList<String>(delegates);
			newDelegates.add(AstPackage.eNS_URI);
			EcoreUtil.setSettingDelegates(ePackage, newDelegates);
		}
	}

	/**
	 * Sets the AQL invocation delegate on the given {@link EPackage}.
	 * 
	 * @param ePackage
	 *            the {@link EPackage}
	 */
	public static void setInvocationDelegates(EPackage ePackage) {
		final List<String> delegates = EcoreUtil.getInvocationDelegates(ePackage);
		if (!delegates.contains(AstPackage.eNS_URI)) {
			final List<String> newDelegates = new ArrayList<String>(delegates);
			newDelegates.add(AstPackage.eNS_URI);
			EcoreUtil.setInvocationDelegates(ePackage, newDelegates);
		}
	}

	/**
	 * Sets the AQL validation delegate on the given {@link EPackage}.
	 * 
	 * @param ePackage
	 *            the {@link EPackage}
	 */
	public static void setValidationDelegates(EPackage ePackage) {
		final List<String> delegates = EcoreUtil.getValidationDelegates(ePackage);
		if (!delegates.contains(AstPackage.eNS_URI)) {
			final List<String> newDelegates = new ArrayList<String>(delegates);
			newDelegates.add(AstPackage.eNS_URI);
			EcoreUtil.setValidationDelegates(ePackage, newDelegates);
		}
	}

	/**
	 * Gets the AQL expression of the given constraint name for the given {@link EModelElement}.
	 * 
	 * @param eModelElement
	 *            the {@link EModelElement}
	 * @param constraintName
	 *            the constraint name
	 * @return the AQL expression of the given constraint name for the given {@link EModelElement} if any,
	 *         <code>null</code> otherwise
	 */
	public static String getConstraint(EModelElement eModelElement, String constraintName) {
		return EcoreUtil.getAnnotation(eModelElement, AstPackage.eNS_URI, constraintName);
	}

	/**
	 * Gets the given expression as the constraint of the given {@link EModelElement}.
	 * 
	 * @param eModelElement
	 *            the {@link EModelElement}
	 * @param constraintName
	 *            the constraint name
	 * @param expression
	 *            the AQL expression
	 */
	public static void setConstraint(EModelElement eModelElement, String constraintName, String expression) {
		final List<String> constraints = EcoreUtil.getConstraints(eModelElement);
		if (!constraints.contains(constraintName)) {
			final List<String> newConstraints = new ArrayList<String>(constraints);
			newConstraints.add(constraintName);
			EcoreUtil.setConstraints(eModelElement, newConstraints);
		}

		EcoreUtil.setAnnotation(eModelElement, AstPackage.eNS_URI, constraintName, expression);
	}

	/**
	 * Gets the AQL body expression of the given {@link EOperation}.
	 * 
	 * @param eOperation
	 *            the {@link EOperation}
	 * @return the AQL expression body of the given {@link EOperation} if any, <code>null</code> otherwise
	 */
	public static String getBody(EOperation eOperation) {
		return EcoreUtil.getAnnotation(eOperation, AstPackage.eNS_URI, "body");
	}

	/**
	 * Sets the given expression as the body of the given {@link EOperation}.
	 * 
	 * @param eOperation
	 *            the {@link EOperation}
	 * @param expression
	 *            the AQL expression
	 */
	public static void setBody(EOperation eOperation, String expression) {
		EcoreUtil.setAnnotation(eOperation, AstPackage.eNS_URI, "body", expression);
	}

	/**
	 * Gets the AQL derivation expression for the given {@link EStructuralFeature}.
	 * 
	 * @param feature
	 *            the {@link EStructuralFeature}
	 * @return the AQL derivation expression for the given {@link EStructuralFeature} if any,
	 *         <code>null</code> otherwise
	 */
	public static String getDerivation(EStructuralFeature feature) {
		return EcoreUtil.getAnnotation(feature, AstPackage.eNS_URI, "derivation");
	}

	/**
	 * Sets the given expression as the derivation of the given {@link EStructuralFeature}.
	 * 
	 * @param feature
	 *            the {@link EStructuralFeature}
	 * @param expression
	 *            the AQL expression
	 */
	public static void setDerivation(EStructuralFeature feature, String expression) {
		EcoreUtil.setAnnotation(feature, AstPackage.eNS_URI, "derivation", expression);
	}

}
