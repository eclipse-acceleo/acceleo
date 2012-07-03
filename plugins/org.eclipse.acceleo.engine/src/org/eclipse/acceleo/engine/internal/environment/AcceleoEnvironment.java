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
package org.eclipse.acceleo.engine.internal.environment;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.acceleo.common.internal.utils.compatibility.AcceleoOCLReflection;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ocl.AbstractTypeResolver;
import org.eclipse.ocl.Environment;
import org.eclipse.ocl.EnvironmentFactory;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.SendSignalAction;

/**
 * The environment that will be used throughout the evaluation of Acceleo templates.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEnvironment extends EcoreEnvironment {
	/**
	 * This map will be populated with the factories towards which we'll need to restore broken links. See
	 * {@link #restoreBrokenEnvironmentPackages(Resource)}.
	 */
	private final Map<String, EFactory> factories = new HashMap<String, EFactory>();

	/** Instance of the OCL standard library reflection for this environment. */
	private AcceleoOCLReflection oclStdLibReflection;

	/**
	 * Delegates instantiation to the super-constructor.
	 * 
	 * @param reg
	 *            The package registry.
	 */
	protected AcceleoEnvironment(EPackage.Registry reg) {
		super(reg);
	}

	/**
	 * Delegates instantiation to the super-constructor.
	 * 
	 * @param parent
	 *            The parent environment.
	 * @since 3.2
	 */
	protected AcceleoEnvironment(
			Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> parent) {
		super(parent);
	}

	/**
	 * Returns this environment's reflection of the OCL standard library.
	 * 
	 * @return This environment's reflection of the OCL standard library.
	 */
	public AcceleoOCLReflection getOCLStandardLibraryReflection() {
		if (oclStdLibReflection == null) {
			oclStdLibReflection = new AcceleoOCLReflection(this);
		}
		return oclStdLibReflection;
	}

	/**
	 * Restores the broken packages' links toward the environment.
	 * <p>
	 * When the parsed template is saved as a model, {@link EFactory factories} instance aren't persisted with
	 * it. This step is needed in order for the evaluation to be able to find these factories back.
	 * <p>
	 * 
	 * @param resource
	 *            Resource of the emtl file on which packages are to be fixed.
	 */
	public void restoreBrokenEnvironmentPackages(Resource resource) {
		if (factories.isEmpty()) {
			// This needs to be kept in sync with new developments
			@SuppressWarnings("unchecked")
			final AbstractTypeResolver<EPackage, ?, ?, ?, ?> typeResolver = (AbstractTypeResolver<EPackage, ?, ?, ?, ?>)getTypeResolver();
			final EPackage tuplePackage = typeResolver.getTuplePackage();
			final EPackage typePackage = typeResolver.getTypePackage();
			final EPackage collectionPackage = typeResolver.getCollectionPackage();
			final EPackage additionsPackage = typeResolver.getAdditionalFeaturesPackage();

			factories.put(tuplePackage.getName(), tuplePackage.getEFactoryInstance());
			factories.put(typePackage.getName(), typePackage.getEFactoryInstance());
			factories.put(collectionPackage.getName(), collectionPackage.getEFactoryInstance());
			factories.put(additionsPackage.getName(), additionsPackage.getEFactoryInstance());
		}

		for (EObject element : resource.getContents()) {
			if (element instanceof EPackage) {
				final String packageName = ((EPackage)element).getName();
				final EFactory factory = factories.get(packageName);
				if (factory != null && ((EPackage)element).getEFactoryInstance() != factory) {
					((EPackage)element).setEFactoryInstance(factory);
				}
			}
		}
	}

	/*
	 * (non-javadoc) This has been overriden here to avoid encapsulation ...
	 */
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.ecore.EcoreEnvironment#setFactory(org.eclipse.ocl.EnvironmentFactory)
	 */
	@Override
	protected void setFactory(
			EnvironmentFactory<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> factory) {
		super.setFactory(factory);
	}
}
