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

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.internal.utils.compatibility.AcceleoCompatibilityHelper;
import org.eclipse.acceleo.common.internal.utils.compatibility.AcceleoOCLStdLibReflection;
import org.eclipse.acceleo.common.internal.utils.compatibility.OCLVersion;
import org.eclipse.acceleo.common.utils.AcceleoNonStandardLibrary;
import org.eclipse.acceleo.common.utils.AcceleoStandardLibrary;
import org.eclipse.acceleo.internal.compatibility.parser.ast.ocl.environment.AcceleoUMLReflectionHelios;
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
import org.eclipse.ocl.expressions.CollectionKind;
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

	/** List of {@link EClassifier} the parser knows about. */
	private List<EClassifier> types = new ArrayList<EClassifier>();

	/** We'll only create a single instance of the uml reflection. */
	private UMLReflection<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint> umlReflection;

	/** Instance of the OCL standard library reflection for this environment. */
	private AcceleoOCLStdLibReflection oclStdLibReflection;

	/**
	 * The first problem object of the last compilation made by this environment.
	 */
	private Object firstProblemObject;

	/**
	 * Delegates instantiation to the super constructor.
	 * 
	 * @param parent
	 *            Parent for this Acceleo environment.
	 */
	public AcceleoEnvironment(
			Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> parent) {
		super(parent);
		if (!(parent instanceof AcceleoEnvironment)) {
			addAdditionalOperations();
		}
	}

	/**
	 * Delegates instantiation to the super-constructor.
	 * 
	 * @param oclEnvironmentResource
	 *            resource used to keep the OCL environment.
	 */
	public AcceleoEnvironment(Resource oclEnvironmentResource) {
		super(EPackage.Registry.INSTANCE, oclEnvironmentResource);
		addAdditionalOperations();
	}

	/**
	 * Add a new {@link EPackage} in the list of the metamodels considered during the parsing.
	 * 
	 * @param metamodel
	 *            {@link EPackage} to add in the current {@link EPackage}'s known by the parser.
	 */
	public void addMetamodel(EPackage metamodel) {
		metamodels.add(metamodel);
		types.clear();
	}

	/**
	 * Remove a {@link EPackage} in the list of the metamodels considered during the parsing.
	 * 
	 * @param metamodel
	 *            {@link EPackage} to remove in the current {@link EPackage}'s known by the parser.
	 */
	public void removeMetamodel(EPackage metamodel) {
		metamodels.remove(metamodel);
		types.clear();
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
			Iterator<EClassifier> eClassifierIt = getTypes().iterator();
			while (classifier == null && eClassifierIt.hasNext()) {
				EClassifier eClassifier = eClassifierIt.next();
				if (names.get(names.size() - 1).equals(eClassifier.getName())
						&& (names.size() < 2 || names.get(names.size() - 2).equals(
								eClassifier.getEPackage().getName()))) {
					classifier = eClassifier;
				}
			}
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
		EClassifier classifier = null;
		if (name != null) {
			classifier = lookupSequenceClassifier(name);
			if (classifier == null) {
				List<String> names = new ArrayList<String>();
				int eNamespace = name.indexOf(IAcceleoConstants.NAMESPACE_SEPARATOR);
				if (eNamespace > -1) {
					String packageName = name.substring(0, eNamespace).trim();
					String className = name.substring(
							eNamespace + IAcceleoConstants.NAMESPACE_SEPARATOR.length()).trim();
					names.add(packageName);
					names.add(className);
				} else {
					names.add(name);
				}
				classifier = lookupClassifier(names);
			}
		}
		if (classifier != null) {
			/*
			 * We need to check if the UML Reflection doesn't give a replacement for this EClassifier. We
			 * could be on a user-defined datatype that overlaps with one of the standard library's. An
			 * example of this would be the UML "String" that need be converted to the standard library's
			 * String before being returned.
			 */
			classifier = getUMLReflection().getOCLType(classifier);
		}
		return classifier;
	}

	/**
	 * Selects the sequence type for the given name in the current module.
	 * 
	 * @param name
	 *            is the name of the sequence type to search
	 * @return the sequence object, or null if it doesn't exist
	 */
	private EClassifier lookupSequenceClassifier(String name) {
		if (name.endsWith(IAcceleoConstants.PARENTHESIS_END)) {
			int iPar = name.indexOf(IAcceleoConstants.PARENTHESIS_BEGIN);
			if (iPar > -1) {
				String sequenceType = name.substring(0, iPar).trim();
				String elementType = name.substring(iPar + IAcceleoConstants.PARENTHESIS_BEGIN.length(),
						name.length() - IAcceleoConstants.PARENTHESIS_END.length()).trim();
				EClassifier elementClassifier = lookupClassifier(elementType);
				Object sequenceClassifier = null;
				if (elementClassifier != null && CollectionKind.getByName(sequenceType) != null) {
					sequenceClassifier = getTypeResolver().resolveCollectionType(
							CollectionKind.getByName(sequenceType), elementClassifier);
				}
				if (sequenceClassifier instanceof EClassifier) {
					return (EClassifier)sequenceClassifier;
				}

			}
		}
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
		if (types.size() == 0) {
			final Iterator<EPackage> ePackageIt = metamodels.iterator();
			while (ePackageIt.hasNext()) {
				final EPackage ePackage = ePackageIt.next();
				Iterator<EClassifier> eClassifierIt = ePackage.getEClassifiers().iterator();
				while (eClassifierIt.hasNext()) {
					EClassifier eClassifier = eClassifierIt.next();
					computeOCLType(types, eClassifier);
				}
			}
			computeOCLType(types, getOCLStandardLibrary().getBag());
			computeOCLType(types, getOCLStandardLibrary().getBoolean());
			computeOCLType(types, getOCLStandardLibrary().getCollection());
			computeOCLType(types, getOCLStandardLibrary().getInteger());
			computeOCLType(types, getOCLStandardLibraryReflection().getOCLInvalid());
			computeOCLType(types, getOCLStandardLibrary().getOclAny());
			computeOCLType(types, getOCLStandardLibrary().getOclElement());
			computeOCLType(types, getOCLStandardLibrary().getOclExpression());
			computeOCLType(types, getOCLStandardLibrary().getOclMessage());
			computeOCLType(types, getOCLStandardLibrary().getOclType());
			computeOCLType(types, getOCLStandardLibrary().getOclVoid());
			computeOCLType(types, getOCLStandardLibrary().getOrderedSet());
			computeOCLType(types, getOCLStandardLibrary().getReal());
			computeOCLType(types, getOCLStandardLibrary().getSequence());
			computeOCLType(types, getOCLStandardLibrary().getSet());
			computeOCLType(types, getOCLStandardLibrary().getState());
			computeOCLType(types, getOCLStandardLibrary().getString());
			computeOCLType(types, getOCLStandardLibrary().getT());
			computeOCLType(types, getOCLStandardLibrary().getT2());
			computeOCLType(types, getOCLStandardLibrary().getUnlimitedNatural());
		}
		return types;
	}

	/**
	 * Returns this environment's reflection of the OCL standard library.
	 * 
	 * @return This environment's reflection of the OCL standard library.
	 */
	public AcceleoOCLStdLibReflection getOCLStandardLibraryReflection() {
		if (oclStdLibReflection == null) {
			oclStdLibReflection = new AcceleoOCLStdLibReflection(this);
		}
		return oclStdLibReflection;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.ecore.EcoreEnvironment#getUMLReflection()
	 */
	@Override
	public UMLReflection<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint> getUMLReflection() {
		if (umlReflection == null) {
			if (AcceleoCompatibilityHelper.getCurrentVersion() == OCLVersion.HELIOS) {
				umlReflection = new AcceleoUMLReflectionHelios(super.getUMLReflection());
			} else {
				umlReflection = new AcceleoUMLReflection(super.getUMLReflection());
			}
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
			if (!result.contains(oclType)) {
				result.add(oclType);
			}
		} else {
			if (!result.contains(type)) {
				result.add(type);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.ecore.EcoreEnvironment#setFactory(org.eclipse.ocl.EnvironmentFactory)
	 */
	@Override
	public void setFactory(
			EnvironmentFactory<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> factory) {
		super.setFactory(factory);
	}

	/**
	 * Adds custom operations from the standard and non-standard libraries to the environment.
	 */
	private void addAdditionalOperations() {
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
		addHelperOperations(getOCLStandardLibrary().getCollection(), getAcceleoNonStandardLibrary()
				.getExistingOperations(getOCLStandardLibrary().getCollection()));
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

	/**
	 * Gets the first problem object of the last compilation made by this environment. Make sure to call
	 * deleteFirstProblemObject() before to get the last compilation issues.
	 * 
	 * @return the first registered problem object, or null
	 */
	public Object getFirstProblemObject() {
		Object result;
		if (firstProblemObject != null) {
			result = firstProblemObject;
		} else if (getInternalParent() instanceof AcceleoEnvironment) {
			result = ((AcceleoEnvironment)getInternalParent()).getFirstProblemObject();
		} else {
			result = null;
		}
		return result;
	}

	/**
	 * Deletes the first problem object to clear the context. You'll have the newest information when you'll
	 * call getFirstProblemObject().
	 */
	public void deleteFirstProblemObject() {
		firstProblemObject = null;
		if (getInternalParent() instanceof AcceleoEnvironment) {
			((AcceleoEnvironment)getInternalParent()).deleteFirstProblemObject();
		}
	}

	/**
	 * Try to set the current problem object of the last compilation made by this environment. We only keep it
	 * if it is the first one.
	 * 
	 * @param problemObject
	 *            the current problem object
	 */
	private void setFirstProblemObjectIfNull(Object problemObject) {
		if (firstProblemObject == null) {
			firstProblemObject = problemObject;
		}
		if (getInternalParent() instanceof AcceleoEnvironment) {
			((AcceleoEnvironment)getInternalParent()).setFirstProblemObjectIfNull(problemObject);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.lpg.AbstractBasicEnvironment#analyzerError(java.lang.String, java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public void analyzerError(String problemMessage, String problemContext, Object problemObject) {
		setFirstProblemObjectIfNull(problemObject);
		super.analyzerError(problemMessage, problemContext, problemObject);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.lpg.AbstractBasicEnvironment#analyzerError(java.lang.String, java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public void analyzerError(String problemMessage, String problemContext, List<?> problemObjects) {
		if (problemObjects != null && problemObjects.size() > 0) {
			setFirstProblemObjectIfNull(problemObjects.get(0));
		}
		super.analyzerError(problemMessage, problemContext, problemObjects);
	}

}
