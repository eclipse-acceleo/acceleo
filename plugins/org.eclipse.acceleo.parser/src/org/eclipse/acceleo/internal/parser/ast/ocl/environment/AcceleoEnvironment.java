/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.parser.ast.ocl.environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.common.utils.AcceleoNonStandardLibrary;
import org.eclipse.acceleo.common.utils.AcceleoStandardLibrary;
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
import org.eclipse.ocl.EnvironmentFactory;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.SendSignalAction;
import org.eclipse.ocl.utilities.UMLReflection;

/**
 * The environment that will be used throughout the evaluation of Acceleo templates.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoEnvironment extends EcoreEnvironment {
	/** Acceleo non-standard library. */
	private AcceleoNonStandardLibrary acceleoNonStdLib;

	/** Acceleo standard library. */
	private AcceleoStandardLibrary acceleoStdLib;

	/** List of {@link EPackage} the parser knows about. */
	private Collection<EPackage> metamodels = new ArrayList<EPackage>();

	/** We'll only create a single instance of the uml reflection. */
	private UMLReflection<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint> umlReflection;

	/**
	 * Delegates instantiation to the super constructor.
	 * 
	 * @param parent
	 *            Parent for this Acceleo environment.
	 */
	protected AcceleoEnvironment(
			Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> parent) {
		super(parent);
	}

	/**
	 * Delegates instantiation to the super-constructor.
	 * 
	 * @param oclEnvironmentResource
	 *            resource used to keep the OCL environment.
	 */
	protected AcceleoEnvironment(Resource oclEnvironmentResource) {
		super(EPackage.Registry.INSTANCE, oclEnvironmentResource);

		// Add standard Acceleo operations
		addHelperOperations(getOCLStandardLibrary().getString(), getAcceleoStandardLibrary()
				.getExistingOperations(getOCLStandardLibrary().getString()));
		addHelperOperations(getOCLStandardLibrary().getInteger(), getAcceleoStandardLibrary()
				.getExistingOperations(getOCLStandardLibrary().getInteger()));
		addHelperOperations(getOCLStandardLibrary().getReal(), getAcceleoStandardLibrary()
				.getExistingOperations(getOCLStandardLibrary().getReal()));

		// TODO we should provide a way to desactivate non-standard library
		// Add non-standard Acceleo operations
		addHelperOperations(getOCLStandardLibrary().getString(), getAcceleoNonStandardLibrary()
				.getExistingOperations(getOCLStandardLibrary().getString()));
		addHelperOperations(getOCLStandardLibrary().getOclAny(), getAcceleoNonStandardLibrary()
				.getExistingOperations(getOCLStandardLibrary().getOclAny()));
	}

	/**
	 * Add a new {@link EPackage} in the list of the metamodels considered during the parsing.
	 * 
	 * @param metamodel
	 *            {@link EPackage} to add in the current {@link EPackage}'s known by the parser.
	 */
	public void addMetamodel(EPackage metamodel) {
		metamodels.add(metamodel);
	}

	/**
	 * Remove a {@link EPackage} in the list of the metamodels considered during the parsing.
	 * 
	 * @param metamodel
	 *            {@link EPackage} to remove in the current {@link EPackage}'s known by the parser.
	 */
	public void removeMetamodel(EPackage metamodel) {
		metamodels.remove(metamodel);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.ecore.EcoreEnvironment#lookupClassifier(java.util.List)
	 */
	@Override
	public EClassifier lookupClassifier(List<String> names) {
		EClassifier classifier = null;
		if (names.size() > 0) {
			classifier = lookupClassifier(names.get(names.size() - 1));
			if (classifier == null) {
				classifier = super.lookupClassifier(names);
			}
		}
		return classifier;
	}

	/**
	 * Selects the meta-model object for the given name in the current module.
	 * 
	 * @param name
	 *            is the name of the type to search
	 * @return the meta-model object, or null if it doesn't exist
	 */
	public EClassifier lookupClassifier(String name) {
		if (name != null) {
			Iterator<EClassifier> eClassifierIt = getTypes().iterator();
			while (eClassifierIt.hasNext()) {
				EClassifier eClassifier = eClassifierIt.next();
				if (name.equals(eClassifier.getName())) {
					return eClassifier;
				}
			}
		}
		// super never returns invalid types.
		// return getOCLStandardLibrary().getInvalid();
		return null;
	}

	/**
	 * Returns the Acceleo non-standard library.
	 * 
	 * @return The Acceleo non-standard library.
	 */
	public AcceleoNonStandardLibrary getAcceleoNonStandardLibrary() {
		if (acceleoNonStdLib == null) {
			acceleoNonStdLib = new AcceleoNonStandardLibrary();
		}
		return acceleoNonStdLib;
	}

	/**
	 * Returns the Acceleo standard library.
	 * 
	 * @return The Acceleo standard library.
	 */
	public AcceleoStandardLibrary getAcceleoStandardLibrary() {
		if (acceleoStdLib == null) {
			acceleoStdLib = new AcceleoStandardLibrary();
		}
		return acceleoStdLib;
	}

	/**
	 * Gets the meta-model types of the current module.
	 * 
	 * @return the meta-model objects, or an empty list
	 */
	public List<EClassifier> getTypes() {
		List<EClassifier> result = new ArrayList<EClassifier>();
		final Iterator<EPackage> ePackageIt = metamodels.iterator();
		while (ePackageIt.hasNext()) {
			final EPackage ePackage = ePackageIt.next();
			Iterator<EClassifier> eClassifierIt = ePackage.getEClassifiers().iterator();
			while (eClassifierIt.hasNext()) {
				EClassifier eClassifier = eClassifierIt.next();
				computeOCLType(result, eClassifier);
			}
		}
		computeOCLType(result, getOCLStandardLibrary().getBag());
		computeOCLType(result, getOCLStandardLibrary().getBoolean());
		computeOCLType(result, getOCLStandardLibrary().getCollection());
		computeOCLType(result, getOCLStandardLibrary().getInteger());
		computeOCLType(result, getOCLStandardLibrary().getInvalid());
		computeOCLType(result, getOCLStandardLibrary().getOclAny());
		computeOCLType(result, getOCLStandardLibrary().getOclElement());
		computeOCLType(result, getOCLStandardLibrary().getOclExpression());
		computeOCLType(result, getOCLStandardLibrary().getOclMessage());
		computeOCLType(result, getOCLStandardLibrary().getOclType());
		computeOCLType(result, getOCLStandardLibrary().getOclVoid());
		computeOCLType(result, getOCLStandardLibrary().getOrderedSet());
		computeOCLType(result, getOCLStandardLibrary().getReal());
		computeOCLType(result, getOCLStandardLibrary().getSequence());
		computeOCLType(result, getOCLStandardLibrary().getSet());
		computeOCLType(result, getOCLStandardLibrary().getState());
		computeOCLType(result, getOCLStandardLibrary().getString());
		computeOCLType(result, getOCLStandardLibrary().getT());
		computeOCLType(result, getOCLStandardLibrary().getT2());
		computeOCLType(result, getOCLStandardLibrary().getUnlimitedNatural());
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.ecore.EcoreEnvironment#getUMLReflection()
	 */
	@Override
	public UMLReflection<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint> getUMLReflection() {
		if (umlReflection == null) {
			umlReflection = new AcceleoUMLReflection(super.getUMLReflection());
		}
		return umlReflection;
	}

	/**
	 * Gets the type of the OCL environment that matches with the given type. It is put in the 'result' list.
	 * 
	 * @param result
	 *            is the list where to put the OCL type (in out parameter)
	 * @param type
	 *            is the type to find in the OCL environment
	 */
	private void computeOCLType(List<EClassifier> result, EClassifier type) {
		EClassifier oclType = getTypeResolver().resolve(type);
		if (oclType != null) {
			result.add(oclType);
		} else {
			result.add(type);
		}
	}

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

	/**
	 * Convenience method allowing us to add multiple additional operations at once.
	 * 
	 * @param owner
	 *            The owner of the added operations.
	 * @param operations
	 *            List of EOperations that are to be added to the environment.
	 */
	public void addHelperOperations(EClassifier owner, List<EOperation> operations) {
		for (EOperation operation : operations) {
			addHelperOperation(owner, operation);
		}
	}
}
