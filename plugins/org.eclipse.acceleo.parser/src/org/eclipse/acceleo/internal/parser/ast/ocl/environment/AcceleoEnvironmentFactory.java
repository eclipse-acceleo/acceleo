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
package org.eclipse.acceleo.internal.parser.ast.ocl.environment;

import org.eclipse.acceleo.common.internal.utils.compatibility.AcceleoCompatibilityHelper;
import org.eclipse.acceleo.common.internal.utils.compatibility.OCLVersion;
import org.eclipse.acceleo.internal.compatibility.parser.ast.ocl.environment.AcceleoEnvironmentGalileo;
import org.eclipse.acceleo.internal.parser.AcceleoParserMessages;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ocl.Environment;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.SendSignalAction;

/**
 * Represents the environment factory used to parse Acceleo template.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoEnvironmentFactory extends EcoreEnvironmentFactory {

	/** Shared instance. */
	public static final AcceleoEnvironmentFactory ACCELEO_INSTANCE = new AcceleoEnvironmentFactory();

	/**
	 * Default constructor. Packages will be looked up into the global EMF registry.
	 */
	public AcceleoEnvironmentFactory() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.ecore.EcoreEnvironmentFactory#loadEnvironment(org.eclipse.emf.ecore.resource.Resource)
	 */
	@Override
	public Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> loadEnvironment(
			Resource resource) {
		AcceleoEnvironment result;
		if (AcceleoCompatibilityHelper.getCurrentVersion() == OCLVersion.GANYMEDE) {
			result = new AcceleoEnvironment(resource);
		} else {
			result = new AcceleoEnvironmentGalileo(resource);
		}
		result.setFactory(this);
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.ecore.EcoreEnvironmentFactory#createEnvironment(org.eclipse.ocl.Environment)
	 */
	@Override
	public Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> createEnvironment(
			Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> parent) {
		if (!(parent instanceof AcceleoEnvironment)) {
			throw new IllegalArgumentException(AcceleoParserMessages.getString(
					"AcceleoEnvironmentFactory.IllegalParent", parent.getClass().getName())); //$NON-NLS-1$
		}
		AcceleoEnvironment result;
		if (AcceleoCompatibilityHelper.getCurrentVersion() == OCLVersion.GANYMEDE) {
			result = new AcceleoEnvironment(parent);
		} else {
			result = new AcceleoEnvironmentGalileo(parent);
		}
		result.setFactory(this);
		return result;
	}
}
