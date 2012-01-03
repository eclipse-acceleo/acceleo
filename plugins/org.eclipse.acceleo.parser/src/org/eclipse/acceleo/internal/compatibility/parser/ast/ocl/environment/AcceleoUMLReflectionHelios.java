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
package org.eclipse.acceleo.internal.compatibility.parser.ast.ocl.environment;

import org.eclipse.acceleo.internal.parser.ast.ocl.environment.AcceleoUMLReflection;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.SendSignalAction;

/**
 * The UMLReflection interface has evolved between OCL 1.3 and OCL 3.0. In order to be compatible with both,
 * we need to externalize here the delegated calls to the new methods.
 * <p>
 * This code is meant to be called in Eclipse 3.6 and later only, and will <em>not</em> compile in earlier
 * versions. That is expected and will not provoke runtime errors.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoUMLReflectionHelios extends AcceleoUMLReflection {
	/**
	 * Instantiates an UML Reflection given the one to which all calls are to be redirected.
	 * 
	 * @param delegate
	 *            The UML Reflection to which calls are to be redirected.
	 */
	public AcceleoUMLReflectionHelios(
			org.eclipse.ocl.utilities.UMLReflection<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint> delegate) {
		super(delegate);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.parser.ast.ocl.environment.AcceleoUMLReflection#isConstraint(java.lang.Object)
	 */
	@Override
	public boolean isConstraint(Object metaElement) {
		return delegate.isConstraint(metaElement);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.parser.ast.ocl.environment.AcceleoUMLReflection#isPackage(java.lang.Object)
	 */
	@Override
	public boolean isPackage(Object metaElement) {
		return delegate.isPackage(metaElement);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.parser.ast.ocl.environment.AcceleoUMLReflection#setIsStatic(java.lang.Object,
	 *      boolean)
	 */
	@Override
	public boolean setIsStatic(Object feature, boolean isStatic) {
		return delegate.setIsStatic(feature, isStatic);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.utilities.UMLReflection#addConstrainedElement(java.lang.Object,
	 *      org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public void addConstrainedElement(Constraint constraint, EObject constrainedElement) {
		delegate.addConstrainedElement(constraint, constrainedElement);
	}
}
