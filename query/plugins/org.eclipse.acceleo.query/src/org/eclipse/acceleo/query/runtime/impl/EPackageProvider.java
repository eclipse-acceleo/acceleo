/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * {@link EPackageProvider} instances are used to access a set of ecore packages and getting classifiers, and
 * enums literals.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class EPackageProvider {

	/**
	 * Map nsPrefix to their corresponding package.
	 */
	private Map<String, EPackage> ePackages = new HashMap<String, EPackage>();

	/**
	 * {@link Class} to {@link EClassifier} mapping.
	 */
	private final Map<Class<?>, Set<EClassifier>> class2classifiers = new HashMap<Class<?>, Set<EClassifier>>();

	/**
	 * Maps of multi-EOperations : maps the arity to maps that maps {@link EOperation#getName() EOperation's
	 * name} to their {@link EOperation} {@link List}.
	 */
	private final Map<Integer, Map<String, List<EOperation>>> eOperations = new HashMap<Integer, Map<String, List<EOperation>>>();

	/**
	 * {@link List} of known {@link EOperation} and their addition order.
	 */
	private final List<EOperation> eOperationsList = new ArrayList<EOperation>();

	/**
	 * Mapping from an {@link EClass} to its containing {@link EStructuralFeature}.
	 */
	private final Map<EClass, Set<EStructuralFeature>> containingFeatures = new HashMap<EClass, Set<EStructuralFeature>>();

	/**
	 * Mapping from an {@link EClass} to its sub {@link EClass}.
	 */
	private final Map<EClass, Set<EClass>> subTypes = new HashMap<EClass, Set<EClass>>();

	/**
	 * Logger used to report package problems.
	 */
	private Logger logger;

	/**
	 * Creates a new {@link EPackageProvider} with a specified logger.
	 * 
	 * @param logger
	 *            the logger to be used to report package problems.
	 */
	public EPackageProvider(Logger logger) {
		this.logger = logger;
	}

	/**
	 * Creates a new {@link EPackageProvider}.
	 */
	public EPackageProvider() {
		this.logger = Logger.getLogger("EPackageProvider");
	}

	/**
	 * Returns the package registered with the specified nsPrefix. Returns null if no such package is
	 * registered.
	 * 
	 * @param nsPrefix
	 *            the nsPrefix of the requested package.
	 * @return the package registered with the specified nsPrefix.
	 */
	public EPackage getEPackage(String nsPrefix) {
		return ePackages.get(nsPrefix);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#removePackage(java.lang.String)
	 */
	public void removePackage(String nsPrefix) {
		final EPackage ePackage = ePackages.remove(nsPrefix);
		if (ePackage != null) {
			for (EClassifier eCls : ePackage.getEClassifiers()) {
				removeEClassifierClass(eCls);
				if (eCls instanceof EClass) {
					removeEOperations((EClass)eCls);
					removeContainingFeature((EClass)eCls);
					removeSubType((EClass)eCls);
				}
			}
			for (EPackage childPkg : ePackage.getESubpackages()) {
				removePackage(childPkg.getNsPrefix());
			}
		}
	}

	/**
	 * Removes the given {@link EClass} as been a sub tpye of its {@link EClass#getESuperTypes() super types}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 */
	private void removeSubType(EClass eCls) {
		for (EClass superECls : eCls.getESuperTypes()) {
			Set<EClass> types = subTypes.get(superECls);
			if (types != null && types.remove(eCls) && types.size() == 0) {
				types = new LinkedHashSet<EClass>();
				subTypes.remove(superECls);
			}
		}
	}

	/**
	 * Removes the containing {@link EStructuralFeature} for the given {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass} to remove
	 */
	private void removeContainingFeature(EClass eCls) {
		for (EStructuralFeature feature : eCls.getEStructuralFeatures()) {
			if (feature.getEType() instanceof EClass) {
				if ((feature instanceof EReference && ((EReference)feature).isContainment())
						|| feature instanceof EAttribute) {
					Set<EStructuralFeature> possibleContainementFeatures = containingFeatures.get(feature
							.getEType());
					if (possibleContainementFeatures != null && possibleContainementFeatures.remove(feature)
							&& possibleContainementFeatures.size() == 0) {
						containingFeatures.remove(feature.getEType());
					}
				}
			}
		}
	}

	/**
	 * Removes {@link EOperation} of the given {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 */
	private void removeEOperations(EClass eCls) {
		for (EOperation eOperation : eCls.getEOperations()) {
			final List<EOperation> multiEOperation = getMultiEOperation(eOperation.getName(), eOperation
					.getEParameters().size());
			if (multiEOperation != null) {
				if (multiEOperation.remove(eOperation) && multiEOperation.size() == 0) {
					eOperations.get(eOperation.getEParameters().size()).remove(eOperation.getName());
				}
			}
			eOperationsList.remove(eOperation);
		}
	}

	/**
	 * Removes the given {@link EClassifier} from {@link EPackageProvider#class2classifiers the class mapping}
	 * .
	 * 
	 * @param eCls
	 *            the {@link EClassifier} to remove
	 */
	private void removeEClassifierClass(EClassifier eCls) {
		final Class<?> instanceClass = eCls.getInstanceClass();
		final Set<EClassifier> classifiers = class2classifiers.get(instanceClass);
		if (classifiers.size() == 1) {
			class2classifiers.remove(instanceClass);
		} else {
			classifiers.remove(eCls);
		}
	}

	/**
	 * Register a new {@link EPackage}.
	 * 
	 * @param ePackage
	 *            the package to be registered.
	 */
	public void registerPackage(EPackage ePackage) {
		if (ePackage.getNsPrefix() != null) {
			ePackages.put(ePackage.getNsPrefix(), ePackage);
			for (EClassifier eCls : ePackage.getEClassifiers()) {
				registerEClassifierClass(eCls);
				if (eCls instanceof EClass) {
					registerEOperations((EClass)eCls);
					registerContainingFeature((EClass)eCls);
					registerSubTypes((EClass)eCls);
				}
			}
			for (EPackage childPkg : ePackage.getESubpackages()) {
				registerPackage(childPkg);
			}
		} else {
			logger.log(Level.WARNING, "Couldn't register package " + ePackage.getName()
					+ " because it's nsPrefix is null.");
		}
	}

	/**
	 * Registers the given {@link EClass} as a sub type of its {@link EClass#getESuperTypes() super types}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 */
	private void registerSubTypes(EClass eCls) {
		for (EClass superECls : eCls.getESuperTypes()) {
			Set<EClass> types = subTypes.get(superECls);
			if (types == null) {
				types = new LinkedHashSet<EClass>();
				subTypes.put(superECls, types);
			}
			types.add(eCls);
		}
	}

	/**
	 * Registers containing {@link EStructuralFeature} for the given {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass} to register
	 */
	private void registerContainingFeature(EClass eCls) {
		for (EStructuralFeature feature : eCls.getEStructuralFeatures()) {
			if (feature.getEType() instanceof EClass) {
				if ((feature instanceof EReference && ((EReference)feature).isContainment())
						|| feature instanceof EAttribute) {
					Set<EStructuralFeature> possibleContainementFeatures = containingFeatures.get(feature
							.getEType());
					if (possibleContainementFeatures == null) {
						possibleContainementFeatures = new LinkedHashSet<EStructuralFeature>();
						containingFeatures.put((EClass)feature.getEType(), possibleContainementFeatures);
					}
					possibleContainementFeatures.add(feature);
				}
			}
		}
	}

	/**
	 * Registers the given {@link EClassifier} to {@link EPackageProvider#class2classifiers the class mapping}
	 * .
	 * 
	 * @param eCls
	 *            the {@link EClassifier} to register
	 */
	private void registerEClassifierClass(EClassifier eCls) {
		final Class<?> instanceClass = eCls.getInstanceClass();
		Set<EClassifier> classifiers = class2classifiers.get(instanceClass);
		if (classifiers == null) {
			classifiers = new LinkedHashSet<EClassifier>();
			class2classifiers.put(instanceClass, classifiers);
		}
		classifiers.add(eCls);
	}

	/**
	 * Registers {@link EOperation} of the given {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 */
	private void registerEOperations(EClass eCls) {
		for (EOperation eOperation : eCls.getEOperations()) {
			final List<EOperation> multiEOperation = getOrCreateMultimethod(eOperation.getName(), eOperation
					.getEParameters().size());
			multiEOperation.add(eOperation);
			eOperationsList.add(eOperation);
		}
	}

	/**
	 * Lookups the {@link EOperation} with the given receiving {@link EClass}, given
	 * {@link EOperation#getName() name} and {@link EOperation#getEParameters() parameters} type.
	 * 
	 * @param receiverEClass
	 *            the receiver {@link EClass}
	 * @param eOperationName
	 *            the {@link EOperation#getName() EOperation's name}
	 * @param parameterTypes
	 *            the {@link EOperation#getEParameters() parameters} type
	 * @return the {@link EOperation} with the given receiving {@link EClass}, given
	 *         {@link EOperation#getName() name} and {@link EOperation#getEParameters() parameters} type
	 */
	public EOperation lookupEOperation(EClass receiverEClass, String eOperationName,
			List<EParameter> parameterTypes) {
		final EOperation result;

		final List<EOperation> multiEOperations = getMultiEOperation(eOperationName, parameterTypes.size());
		if (multiEOperations != null) {
			EOperation compatibleEOperation = null;
			for (EOperation eOperation : multiEOperations) {
				compatibleEOperation = eOperation;
				if (eOperation.getEContainingClass().isSuperTypeOf(receiverEClass)) {
					final Iterator<EParameter> it = parameterTypes.iterator();
					for (EParameter parameter : eOperation.getEParameters()) {
						if (!isCompatibleType(parameter, it.next())) {
							compatibleEOperation = null;
							break;
						}
					}
				}
				if (compatibleEOperation != null) {
					break;
				}
			}
			result = compatibleEOperation;
		} else {
			result = null;
		}

		return result;
	}

	/**
	 * Gets the {@link Set} of {@link EOperation} for the given {@link Set} of receiver {@link EClass}.
	 * 
	 * @param receiverTypes
	 *            the {@link Set} of receiver {@link EClass}
	 * @return the {@link Set} of {@link EOperation} for the given {@link Set} of receiver {@link EClass}
	 */
	public Set<EOperation> getEOperations(Set<EClass> receiverTypes) {
		final Set<EOperation> result = new LinkedHashSet<EOperation>();

		for (EClass eCls : receiverTypes) {
			for (EOperation eOperation : eOperationsList) {
				if (eOperation.getEContainingClass().isSuperTypeOf(eCls)) {
					result.add(eOperation);
				}
			}
		}

		return result;
	}

	/**
	 * Tells if the given declared {@link EParameter} is compatible with the given passed {@link Class}.
	 * 
	 * @param parameter
	 *            the declared {@link EParameter}
	 * @param passedParameter
	 *            the passed {@link EParameter}
	 * @return <code>true</code> if the given declared {@link EParameter} is compatible with the given passed
	 *         {@link Class}, <code>false</code> otherwise
	 */
	private boolean isCompatibleType(EParameter parameter, EParameter passedParameter) {
		final boolean result;

		if (parameter.isMany() == passedParameter.isMany()) {
			if (parameter.getEType() instanceof EClass && passedParameter.getEType() instanceof EClass) {
				result = ((EClass)parameter.getEType()).isSuperTypeOf((EClass)passedParameter.getEType());
			} else if (passedParameter.getEType() != null) {
				result = parameter.getEType() == passedParameter.getEType();
			} else {
				result = true;
			}
		} else {
			result = false;
		}

		return result;
	}

	/**
	 * the classifier with the specified name in the package registered with the specified nsPrefix.
	 * 
	 * @param nsPrefix
	 *            the nsPrefix of the searched classifier's package
	 * @param classifierName
	 *            the name of the searched classifier
	 * @return the classifier with the specified name in the package registered with the specified nsPrefix.
	 */
	public EClassifier getType(String nsPrefix, String classifierName) {
		EPackage ePackage = ePackages.get(nsPrefix);
		if (ePackage != null) {
			return ePackage.getEClassifier(classifierName);
		} else {
			return null;
		}
	}

	/**
	 * Returns an {@link EClassifier} instance when a unique one is found in all the registered packages. If
	 * several are found, returns <code>null</code> and log a warning.
	 * 
	 * @param classifierName
	 *            the name of the search classifier.
	 * @return the requested classifier unless several classifiers have the same name in different packages.
	 */
	public EClassifier getType(String classifierName) {
		EClassifier result = null;
		for (EPackage ePackage : ePackages.values()) {
			EClassifier foundClassifier = ePackage.getEClassifier(classifierName);
			if (foundClassifier != null) {
				if (result == null) {
					result = foundClassifier;
				} else {
					String firstFullyQualifiedName = result.getEPackage().getNsPrefix() + "."
							+ result.getName();
					String secondFullyQualifiedName = foundClassifier.getEPackage().getNsPrefix() + "."
							+ foundClassifier.getName();
					String message = "Ambiguous classifier request. At least two classifiers matches %s : %s and %s";
					logger.warning(String.format(message, classifierName, firstFullyQualifiedName,
							secondFullyQualifiedName));
				}
			}
		}
		return result;
	}

	/**
	 * Returns the {@link EEnumLiteral} with the specified name in the specified enum.
	 * 
	 * @param nsPrefix
	 *            the nsPrefix of the package where to search the {@link EEnumLiteral}
	 * @param enumName
	 *            the name of the {@link EEnum} containing the literal.
	 * @param literalName
	 *            the name of the searched {@link EEnumLiteral}.
	 * @return the specified {@link EEnumLiteral}
	 */
	public EEnumLiteral getEnumLiteral(String nsPrefix, String enumName, String literalName) {
		EPackage ePackage = ePackages.get(nsPrefix);
		EEnumLiteral result;
		if (ePackage != null) {
			EClassifier eClassifier = ePackage.getEClassifier(enumName);
			result = getEnumLiteral(eClassifier, literalName);
		} else {
			result = null;
		}
		return result;
	}

	/**
	 * Returns the {@link EEnumLiteral} with the specified name in the specified enum if it exists in one of
	 * the registered package. Returns <code>null</code> otherwise.
	 * 
	 * @param enumName
	 *            the name of the {@link EEnum} containing the literal.
	 * @param literalName
	 *            the name of the searched {@link EEnumLiteral}.
	 * @return the specified {@link EEnumLiteral}
	 */
	public EEnumLiteral getEnumLiteral(String enumName, String literalName) {
		EClassifier eClassifier = getType(enumName);
		if (eClassifier == null) {
			return null;
		} else {
			return getEnumLiteral(eClassifier, literalName);
		}

	}

	/**
	 * Returns the enum literal that corresponds to the specified literal name in the specified classifier.
	 * 
	 * @param eClassifier
	 *            the {@link EEnum} in which to look for the literal.
	 * @param literalName
	 *            the name of the literal to look for.
	 * @return the enum literal with the specified name in the specified {@link EEnum}.
	 */
	private EEnumLiteral getEnumLiteral(EClassifier eClassifier, String literalName) {
		EEnumLiteral result;
		if (eClassifier instanceof EEnum) {
			result = ((EEnum)eClassifier).getEEnumLiteral(literalName);
		} else {
			result = null;
		}
		return result;
	}

	/**
	 * Gets the {@link EClassifier} represented by the given {@link Class}.
	 * 
	 * @param cls
	 *            the {@link Class}
	 * @return the {@link EClassifier} represented by the given {@link Class} if any, <code>null</code>
	 *         otherwise
	 */
	public Set<EClassifier> getEClass(Class<?> cls) {
		return class2classifiers.get(cls);
	}

	/**
	 * Gets the {@link Class} of instance of the given {@link EClassifier}.
	 * 
	 * @param eCls
	 *            the {@link EClassifier}
	 * @return the {@link Class} of instance of the given {@link EClassifier}
	 */
	public Class<?> getClass(EClassifier eCls) {
		return eCls.getInstanceClass();
	}

	/**
	 * Gets the {@link EStructuralFeature} from the given {@link Set} of {@link EClass}.
	 * 
	 * @param receiverEClasses
	 *            the {@link Set} of {@link EClass}.
	 * @return the {@link EStructuralFeature} from the given {@link Set} of {@link EClass}
	 */
	public Set<EStructuralFeature> getEStructuralFeatures(Set<EClass> receiverEClasses) {
		Set<EStructuralFeature> result = new LinkedHashSet<EStructuralFeature>();

		for (EClass eCls : receiverEClasses) {
			result.addAll(eCls.getEAllStructuralFeatures());
		}

		return result;
	}

	/**
	 * Gets the {@link Set} of {@link EPackageProvider#registerPackage(EPackage) registered}
	 * {@link EClassifier}.
	 * 
	 * @return the {@link Set} of {@link EPackageProvider#registerPackage(EPackage) registered}
	 *         {@link EClassifier}
	 */
	public Set<EClassifier> getEClassifiers() {
		final Set<EClassifier> result = new LinkedHashSet<EClassifier>();

		for (EPackage ePkg : ePackages.values()) {
			result.addAll(ePkg.getEClassifiers());
		}

		return result;
	}

	/**
	 * Gets the {@link Set} of {@link EPackageProvider#registerPackage(EPackage) registered}
	 * {@link EEnumLiteral}.
	 * 
	 * @return the {@link Set} of {@link EPackageProvider#registerPackage(EPackage) registered}
	 *         {@link EEnumLiteral}
	 */
	public Set<EEnumLiteral> getEEnumLiterals() {
		final Set<EEnumLiteral> result = new LinkedHashSet<EEnumLiteral>();

		for (EPackage ePkg : ePackages.values()) {
			for (EClassifier eClassifier : ePkg.getEClassifiers()) {
				if (eClassifier instanceof EEnum) {
					result.addAll(((EEnum)eClassifier).getELiterals());
				}
			}
		}

		return result;
	}

	/**
	 * Retrieves a multiEOperation from the given number of {@link EOperation#getEParameters() eParameters}
	 * and the {@link EOperation#getName() EOperation's name}.
	 * 
	 * @param eOperationName
	 *            the {@link EOperation#getName() EOperation's name} of the multiEOperation to retrieve.
	 * @param argc
	 *            the {@link EOperation#getEParameters() eParameters} count of the multiEOperation to
	 *            retrieve.
	 * @return the list of {@link EOperation} instances that make up the retrieve multiEOperation.
	 */

	private List<EOperation> getMultiEOperation(String eOperationName, int argc) {
		Map<String, List<EOperation>> argcServices = eOperations.get(argc);
		if (argcServices == null) {
			return null;
		} else {
			return argcServices.get(eOperationName);
		}
	}

	/**
	 * Retrieves or creates a multiEOperation from the given number of {@link EOperation#getEParameters()
	 * eParameters} and the {@link EOperation#getName() EOperation's name}.
	 * 
	 * @param eOperationName
	 *            the {@link EOperation#getName() EOperation's name} of the multiEOperation to retrieve.
	 * @param argc
	 *            the {@link EOperation#getEParameters() eParameters} count of the multiEOperation to
	 *            retrieve.
	 * @return the {@link List} of {@link EOperation} instances that make up the retrieve multiEOperation.
	 */
	private List<EOperation> getOrCreateMultimethod(String eOperationName, int argc) {
		Map<String, List<EOperation>> argcEOperations = eOperations.get(argc);
		if (argcEOperations == null) {
			argcEOperations = new HashMap<String, List<EOperation>>();
			eOperations.put(argc, argcEOperations);
		}
		List<EOperation> result = argcEOperations.get(eOperationName);
		if (result == null) {
			result = new ArrayList<EOperation>();
			argcEOperations.put(eOperationName, result);
		}
		return result;
	}

	/**
	 * Gets the {@link Set} of all {@link EClass} that can contain the given {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 * @return the {@link Set} of all {@link EClass} that can contain the given {@link EClass}
	 */
	public Set<EClass> getAllContainingEClasses(EClass eCls) {
		final Set<EClass> result = new LinkedHashSet<EClass>();

		result.addAll(getContainingClasses(eCls));
		for (EClass superType : eCls.getEAllSuperTypes()) {
			result.addAll(getContainingClasses(superType));
		}
		for (EClass subType : getAllSubTypes(eCls)) {
			result.addAll(getContainingClasses(subType));
		}

		return result;
	}

	/**
	 * Gets the containing {@link EClass} for the given {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 * @return the containing {@link EClass} for the given {@link EClass}
	 */
	private Set<EClass> getContainingClasses(EClass eCls) {
		final Set<EClass> result = new LinkedHashSet<EClass>();

		Set<EStructuralFeature> features = containingFeatures.get(eCls);
		if (features != null) {
			for (EStructuralFeature feature : features) {
				result.add(feature.getEContainingClass());
			}
		}

		return result;
	}

	/**
	 * Gets all sub types for the given {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 * @return all sub types for the given {@link EClass}
	 */
	public Set<EClass> getAllSubTypes(EClass eCls) {
		final Set<EClass> result = new LinkedHashSet<EClass>();

		final Set<EClass> types = subTypes.get(eCls);
		if (types != null) {
			for (EClass type : types) {
				result.add(type);
				result.addAll(getAllSubTypes(type));
			}
		}

		return result;
	}
}
