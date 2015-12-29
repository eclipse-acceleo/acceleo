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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IEPackageProvider;
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
import org.eclipse.emf.ecore.EcorePackage;

/**
 * {@link EPackageProvider} instances are used to access a set of ecore packages and getting classifiers, and
 * enums literals.
 * 
 * @author <a href="mailto:romain.guider@obeo.fr">Romain Guider</a>
 */
public class EPackageProvider implements IEPackageProvider {

	/**
	 * Maps of multi-EOperations : maps the arity to maps that maps {@link EOperation#getName() EOperation's
	 * name} to their {@link EOperation} {@link List}.
	 */
	protected final Map<Integer, Map<String, List<EOperation>>> eOperations = new HashMap<Integer, Map<String, List<EOperation>>>();

	/**
	 * Map the name to their corresponding package.
	 */
	private Map<String, EPackage> ePackages = new LinkedHashMap<String, EPackage>();

	/**
	 * {@link Class} to {@link EClassifier} mapping.
	 */
	private final Map<Class<?>, Set<EClassifier>> class2classifiers = new HashMap<Class<?>, Set<EClassifier>>();

	/**
	 * {@link EClassifier} to {@link Class} mapping.
	 */
	private final Map<EClassifier, Class<?>> classifier2class = new HashMap<EClassifier, Class<?>>();

	/**
	 * {@link List} of known {@link EOperation} and their addition order.
	 */
	private final List<EOperation> eOperationsList = new ArrayList<EOperation>();

	/**
	 * Mapping from an {@link EClass} to its containing {@link EStructuralFeature} for one {@link EClass}
	 * hierarchy.
	 */
	private final Map<EClass, Set<EStructuralFeature>> containingFeaturesForOneClassHierarchy = new HashMap<EClass, Set<EStructuralFeature>>();

	/**
	 * Mapping from an {@link EClass} to its containing {@link EStructuralFeature}.
	 */
	private final Map<EClass, Set<EStructuralFeature>> containingFeatures = new HashMap<EClass, Set<EStructuralFeature>>();

	/**
	 * Mapping from an {@link EClass} to all its containing {@link EStructuralFeature} (transitive).
	 */
	private final Map<EClass, Set<EStructuralFeature>> allContainingFeatures = new HashMap<EClass, Set<EStructuralFeature>>();

	/**
	 * Mapping from an {@link EClass} to its inverse {@link EStructuralFeature}.
	 */
	private final Map<EClass, Set<EStructuralFeature>> inverseFeatures = new HashMap<EClass, Set<EStructuralFeature>>();

	/**
	 * Mapping from an {@link EClass} to its sub {@link EClass}.
	 */
	private final Map<EClass, Set<EClass>> subTypes = new HashMap<EClass, Set<EClass>>();

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#getEPackage(java.lang.String)
	 */
	@Override
	public EPackage getEPackage(String name) {
		return ePackages.get(name);
	}

	/**
	 * Removes the {@link EPackageProvider#registerPackage(EPackage) registered} {@link EPackage} with the
	 * given {@link EPackage#getName() name}.
	 * 
	 * @param name
	 *            the {@link EPackage#getName() name}
	 * @return the removed {@link EPackage} if any, <code>null</code> otherwise
	 */
	public EPackage removePackage(String name) {
		final EPackage ePackage = ePackages.remove(name);

		if (ePackage != null) {
			for (EClassifier eCls : ePackage.getEClassifiers()) {
				removeEClassifierClass(eCls);
				if (eCls instanceof EClass) {
					removeEOperations((EClass)eCls);
					removeFeatures((EClass)eCls);
					removeSubType((EClass)eCls);
				}
			}
			for (EPackage childPkg : ePackage.getESubpackages()) {
				removePackage(childPkg.getName());
			}
			containingFeatures.clear();
			allContainingFeatures.clear();
		}

		return ePackage;
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
	 * Removes {@link EStructuralFeature} for the given {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass} to remove
	 */
	private void removeFeatures(EClass eCls) {
		for (EStructuralFeature feature : eCls.getEStructuralFeatures()) {
			if (feature.getEType() instanceof EClass) {
				Set<EStructuralFeature> possibleInverseFeatures = inverseFeatures.get(feature.getEType());
				if (possibleInverseFeatures != null && possibleInverseFeatures.remove(feature)
						&& possibleInverseFeatures.size() == 0) {
					inverseFeatures.remove(feature.getEType());
				}
				if (isContainingEStructuralFeature(feature)) {
					Set<EStructuralFeature> possibleContainementFeatures = containingFeaturesForOneClassHierarchy
							.get(feature.getEType());
					if (possibleContainementFeatures != null && possibleContainementFeatures.remove(feature)
							&& possibleContainementFeatures.size() == 0) {
						containingFeaturesForOneClassHierarchy.remove(feature.getEType());
					}
				}
			}
		}
	}

	/**
	 * Tells if the given {@link EStructuralFeature} is a containing {@link EStructuralFeature}.
	 * 
	 * @param feature
	 *            the {@link EStructuralFeature} to test
	 * @return <code>true</code> if the given {@link EStructuralFeature} is a containing
	 *         {@link EStructuralFeature},<code>false</code> otherwise
	 */
	private boolean isContainingEStructuralFeature(EStructuralFeature feature) {
		return (feature instanceof EReference && ((EReference)feature).isContainment())
				|| feature instanceof EAttribute;
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
		final Class<?> instanceClass = getClass(eCls);
		final Set<EClassifier> classifiers = class2classifiers.get(instanceClass);
		if (classifiers != null) {
			if (classifiers.size() == 1) {
				class2classifiers.remove(instanceClass);
			} else {
				classifiers.remove(eCls);
			}
		}
		classifier2class.remove(eCls);
	}

	/**
	 * Register a new {@link EPackage}.
	 * 
	 * @param ePackage
	 *            the package to be registered.
	 * @return the registered {@link EPackage} if any, <code>null</code> otherwise
	 */
	public EPackage registerPackage(EPackage ePackage) {
		final EPackage result;

		if (!("ecore".equals(ePackage.getName()) && !EcorePackage.eNS_URI.equals(ePackage.getNsURI()))) {
			if (ePackage.getName() != null) {
				EPackage existing = ePackages.put(ePackage.getName(), ePackage);
				if (existing != null) {
					// duplicate package
					return null;
				}

				result = ePackage;
				ePackages.put(ePackage.getName(), ePackage);
				for (EClassifier eCls : ePackage.getEClassifiers()) {
					registerEClassifierClass(eCls);
					if (eCls instanceof EClass) {
						registerEOperations((EClass)eCls);
						registerFeatures((EClass)eCls);
						registerSubTypes((EClass)eCls);
					}
				}
				for (EPackage childPkg : ePackage.getESubpackages()) {
					registerPackage(childPkg);
				}
				containingFeatures.clear();
				allContainingFeatures.clear();
			} else {
				throw new IllegalStateException("Couldn't register package " + ePackage.getName()
						+ " because its name is null.");
			}
		} else {
			result = null;
		}

		return result;
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
	 * Registers {@link EStructuralFeature} for the given {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass} to register
	 */
	private void registerFeatures(EClass eCls) {
		for (EStructuralFeature feature : eCls.getEStructuralFeatures()) {
			if (feature.getEType() instanceof EClass) {
				Set<EStructuralFeature> possibleInverseFeatures = inverseFeatures.get(feature.getEType());
				if (possibleInverseFeatures == null) {
					possibleInverseFeatures = new LinkedHashSet<EStructuralFeature>();
					inverseFeatures.put((EClass)feature.getEType(), possibleInverseFeatures);
				}
				possibleInverseFeatures.add(feature);
				if (isContainingEStructuralFeature(feature)) {
					Set<EStructuralFeature> possibleContainementFeatures = containingFeaturesForOneClassHierarchy
							.get(feature.getEType());
					if (possibleContainementFeatures == null) {
						possibleContainementFeatures = new LinkedHashSet<EStructuralFeature>();
						containingFeaturesForOneClassHierarchy.put((EClass)feature.getEType(),
								possibleContainementFeatures);
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
		final Class<?> customClass = classifier2class.get(eCls);
		final Class<?> instanceClass;
		if (customClass != null) {
			instanceClass = customClass;
		} else {
			instanceClass = eCls.getInstanceClass();
			classifier2class.put(eCls, instanceClass);
		}
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
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#lookupEOperation(org.eclipse.emf.ecore.EClass,
	 *      java.lang.String, java.util.List)
	 */
	@Override
	public EOperation lookupEOperation(EClass receiverEClass, String eOperationName,
			List<EParameter> parameterTypes) {
		final EOperation result;

		final List<EOperation> multiEOperations = getMultiEOperation(eOperationName, parameterTypes.size());
		if (multiEOperations != null) {
			EOperation compatibleEOperation = null;
			Iterator<EOperation> itOp = multiEOperations.iterator();
			while (itOp.hasNext() && compatibleEOperation == null) {
				EOperation eOperation = itOp.next();
				if (eOperation.getEContainingClass() == EcorePackage.eINSTANCE.getEObject()
						|| eOperation.getEContainingClass().isSuperTypeOf(receiverEClass)) {

					boolean parametersAreCompatibles = parameterTypes.size() == eOperation.getEParameters()
							.size();
					final Iterator<EParameter> itParamType = parameterTypes.iterator();
					final Iterator<EParameter> itOperationParam = eOperation.getEParameters().iterator();

					while (parametersAreCompatibles && itOperationParam.hasNext() && itParamType.hasNext()) {
						parametersAreCompatibles = isCompatibleType(itOperationParam.next(), itParamType
								.next());
					}
					if (parametersAreCompatibles) {
						compatibleEOperation = eOperation;
					}
				}
			}
			result = compatibleEOperation;
		} else {
			result = null;
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#getEOperations(java.util.Set)
	 */
	@Override
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
				result = parameter.getEType() == EcorePackage.eINSTANCE.getEObject()
						|| ((EClass)parameter.getEType()).isSuperTypeOf((EClass)passedParameter.getEType());
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
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#getType(java.lang.String, java.lang.String)
	 */
	@Override
	public EClassifier getType(String name, String classifierName) {
		EPackage ePackage = ePackages.get(name);
		if (ePackage != null) {
			return ePackage.getEClassifier(classifierName);
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#getEnumLiteral(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public EEnumLiteral getEnumLiteral(String name, String enumName, String literalName) {
		final EEnumLiteral result;

		final EPackage ePackage = ePackages.get(name);
		if (ePackage != null) {
			final EClassifier eClassifier = ePackage.getEClassifier(enumName);
			result = getEnumLiteral(eClassifier, literalName);
		} else {
			result = null;
		}

		return result;
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
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#getEClass(java.lang.Class)
	 */
	@Override
	public Set<EClassifier> getEClass(Class<?> cls) {
		return class2classifiers.get(cls);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#getClass(org.eclipse.emf.ecore.EClassifier)
	 */
	@Override
	public Class<?> getClass(EClassifier eCls) {
		return classifier2class.get(eCls);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#isRegistered(org.eclipse.emf.ecore.EClassifier)
	 */
	public boolean isRegistered(EClassifier eCls) {
		return classifier2class.containsKey(eCls);
	}

	/**
	 * Registers a custom mapping from an {@link EClassifier} to its {@link Class}.
	 * 
	 * @param eClassifier
	 *            the {@link EClassifier}
	 * @param cls
	 *            the {@link Class}
	 */
	public void registerCustomClassMapping(EClassifier eClassifier, Class<?> cls) {
		// remove old mappings
		final Class<?> oldClass = classifier2class.remove(eClassifier);
		if (oldClass != null) {
			final Set<EClassifier> eClassifiers = class2classifiers.get(oldClass);
			if (eClassifiers.remove(eClassifier) && eClassifiers.isEmpty()) {
				class2classifiers.remove(oldClass);
			}
		}
		// add new mappings
		classifier2class.put(eClassifier, cls);
		Set<EClassifier> eClassifiers = class2classifiers.get(cls);
		if (eClassifiers == null) {
			eClassifiers = new LinkedHashSet<EClassifier>();
			class2classifiers.put(cls, eClassifiers);
		}
		eClassifiers.add(eClassifier);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#getEStructuralFeatures(java.util.Set)
	 */
	@Override
	public Set<EStructuralFeature> getEStructuralFeatures(Set<EClass> receiverEClasses) {
		Set<EStructuralFeature> result = new LinkedHashSet<EStructuralFeature>();

		for (EClass eCls : receiverEClasses) {
			if (isRegistered(eCls)) {
				result.addAll(eCls.getEAllStructuralFeatures());
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#getEClassifiers()
	 */
	@Override
	public Set<EClassifier> getEClassifiers() {
		final Set<EClassifier> result = new LinkedHashSet<EClassifier>();

		for (EPackage ePkg : ePackages.values()) {
			result.addAll(ePkg.getEClassifiers());
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#getEEnumLiterals()
	 */
	@Override
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
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#getContainingEClasses(org.eclipse.emf.ecore.EClass)
	 */
	@Override
	public Set<EClass> getContainingEClasses(EClass eCls) {
		final Set<EClass> result = new LinkedHashSet<EClass>();

		for (EStructuralFeature feature : getContainingEStructuralFeatures(eCls)) {
			result.add(feature.getEContainingClass());
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#getAllContainingEClasses(org.eclipse.emf.ecore.EClass)
	 */
	@Override
	public Set<EClass> getAllContainingEClasses(EClass eCls) {
		final Set<EClass> result = new LinkedHashSet<EClass>();

		for (EStructuralFeature feature : getAllContainingEStructuralFeatures(eCls)) {
			result.add(feature.getEContainingClass());
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider2#getAllContainingEStructuralFeatures(org.eclipse.emf.ecore.EClass)
	 */
	public Set<EStructuralFeature> getAllContainingEStructuralFeatures(EClass type) {
		Set<EStructuralFeature> result = allContainingFeatures.get(type);

		if (result == null) {
			result = new LinkedHashSet<EStructuralFeature>();
			allContainingFeatures.put(type, result);

			final Set<EClass> knownECls = new HashSet<EClass>();
			Set<EStructuralFeature> previousAdded = new LinkedHashSet<EStructuralFeature>(
					getContainingEStructuralFeatures(type));
			result.addAll(previousAdded);
			while (!previousAdded.isEmpty()) {
				Set<EStructuralFeature> currentAdded = new LinkedHashSet<EStructuralFeature>();
				for (EStructuralFeature feature : previousAdded) {
					final EClass eContainingClass = feature.getEContainingClass();
					if (!knownECls.contains(eContainingClass)) {
						for (EStructuralFeature parentFeature : getContainingEStructuralFeatures(eContainingClass)) {
							if (result.add(parentFeature)) {
								knownECls.add(eContainingClass);
								currentAdded.add(parentFeature);
							}
						}
					}
				}
				previousAdded = currentAdded;
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider2#getContainingEStructuralFeatures(org.eclipse.emf.ecore.EClass)
	 */
	public Set<EStructuralFeature> getContainingEStructuralFeatures(EClass eCls) {
		Set<EStructuralFeature> result = containingFeatures.get(eCls);

		if (result == null) {
			result = new LinkedHashSet<EStructuralFeature>();
			containingFeatures.put(eCls, result);

			result.addAll(getContainingEStructuralFeaturesForOneEClassHierarchyLevel(eCls));
			for (EClass superType : eCls.getEAllSuperTypes()) {
				result.addAll(getContainingEStructuralFeaturesForOneEClassHierarchyLevel(superType));
			}
			for (EClass subType : getAllSubTypes(eCls)) {
				result.addAll(getContainingEStructuralFeaturesForOneEClassHierarchyLevel(subType));
			}
			// always add EObject EClass containing EStructuralFeatures
			result.addAll(getContainingEStructuralFeaturesForOneEClassHierarchyLevel(EcorePackage.eINSTANCE
					.getEObject()));
		}

		return result;
	}

	/**
	 * Gets the containing {@link EClass} for the given {@link EClass} and only it, not its sub {@link EClass}
	 * nor super {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 * @return the containing {@link EClass} for the given {@link EClass} and only it, not its sub
	 *         {@link EClass} nor super {@link EClass}
	 */
	private Set<EStructuralFeature> getContainingEStructuralFeaturesForOneEClassHierarchyLevel(EClass eCls) {
		final Set<EStructuralFeature> result = new LinkedHashSet<EStructuralFeature>();

		Set<EStructuralFeature> features = containingFeaturesForOneClassHierarchy.get(eCls);
		if (features != null) {
			result.addAll(features);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#getAllSubTypes(org.eclipse.emf.ecore.EClass)
	 */
	@Override
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

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#getContainedEClasses(org.eclipse.emf.ecore.EClass)
	 */
	@Override
	public Set<EClass> getContainedEClasses(EClass eCls) {
		final Set<EClass> result = new LinkedHashSet<EClass>();

		if (isRegistered(eCls)) {
			final Set<EStructuralFeature> features = new LinkedHashSet<EStructuralFeature>(eCls
					.getEAllStructuralFeatures());
			for (EClass subECls : getAllSubTypes(eCls)) {
				features.addAll(subECls.getEStructuralFeatures());
			}
			for (EStructuralFeature feature : features) {
				if (isContainingEStructuralFeature(feature) && feature.getEType() instanceof EClass) {
					result.add((EClass)feature.getEType());
				}
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#getAllContainedEClasses(org.eclipse.emf.ecore.EClass)
	 */
	@Override
	public Set<EClass> getAllContainedEClasses(EClass eCls) {
		final Set<EClass> direcltyContainedEClasses = getContainedEClasses(eCls);
		final Set<EClass> result = new LinkedHashSet<EClass>(direcltyContainedEClasses);

		Set<EClass> added = new LinkedHashSet<EClass>(direcltyContainedEClasses);
		while (!added.isEmpty()) {
			final Set<EClass> toDig = new LinkedHashSet<EClass>();
			for (EClass a : added) {
				for (EClass contained : getContainedEClasses(a)) {
					if (result.add(contained)) {
						toDig.add(contained);
					}
				}
			}
			added = toDig;
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#getInverseEClasses(org.eclipse.emf.ecore.EClass)
	 */
	@Override
	public Set<EClass> getInverseEClasses(EClass eCls) {
		final Set<EClass> result = new LinkedHashSet<EClass>();

		result.addAll(getInverseClassesForOneEClassHierarchyLevel(eCls));
		for (EClass superType : eCls.getEAllSuperTypes()) {
			result.addAll(getInverseClassesForOneEClassHierarchyLevel(superType));
		}
		for (EClass subType : getAllSubTypes(eCls)) {
			result.addAll(getInverseClassesForOneEClassHierarchyLevel(subType));
		}
		// always add EObject EClass containing EClasses
		for (EClass eObjectType : getInverseClassesForOneEClassHierarchyLevel(EcorePackage.eINSTANCE
				.getEObject())) {
			result.add(eObjectType);
		}

		return result;
	}

	/**
	 * Gets inverse {@link EClass} for the given {@link EClass} and only it, not its sub {@link EClass} nor
	 * super {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 * @return the containing {@link EClass} for the given {@link EClass} and only it, not its sub
	 *         {@link EClass} nor super {@link EClass}
	 */
	private Set<EClass> getInverseClassesForOneEClassHierarchyLevel(EClass eCls) {
		final Set<EClass> result = new LinkedHashSet<EClass>();

		Set<EStructuralFeature> features = inverseFeatures.get(eCls);
		if (features != null) {
			for (EStructuralFeature feature : features) {
				result.add(feature.getEContainingClass());
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#getFollowingSiblingsEClasses(org.eclipse.emf.ecore.EClass)
	 */
	@Override
	public Set<EClass> getFollowingSiblingsEClasses(EClass eCls) {
		final Set<EClass> result = new LinkedHashSet<EClass>();

		final Set<EStructuralFeature> containingEStructuralFeatures = getContainingEStructuralFeatures(eCls);
		final Set<EStructuralFeature> followingEStructuralFeatures = new LinkedHashSet<EStructuralFeature>();
		for (EStructuralFeature feature : containingEStructuralFeatures) {
			followingEStructuralFeatures.addAll(getFollowingSiblingEStructuralFeatures(feature));
		}

		for (EStructuralFeature feature : followingEStructuralFeatures) {
			if (isContainingEStructuralFeature(feature) && feature.getEType() instanceof EClass) {
				result.add((EClass)feature.getEType());
			}
		}

		return result;
	}

	/**
	 * Gets the {@link Set} of following {@link EStructuralFeature} of the given {@link EStructuralFeature},
	 * if the given {@link EStructuralFeature} is {@link EStructuralFeature#isMany() many} it's included in
	 * the result.
	 * 
	 * @param feature
	 *            the {@link EStructuralFeature}
	 * @return the {@link Set} of following {@link EStructuralFeature} of the given {@link EStructuralFeature}
	 *         , if the given {@link EStructuralFeature} is {@link EStructuralFeature#isMany() many} it's
	 *         included in the result
	 */
	private Set<EStructuralFeature> getFollowingSiblingEStructuralFeatures(EStructuralFeature feature) {
		final Set<EStructuralFeature> result = new LinkedHashSet<EStructuralFeature>();

		if (feature.isMany()) {
			result.add(feature);
		}

		boolean add = false;
		for (EStructuralFeature childFeature : feature.getEContainingClass().getEStructuralFeatures()) {
			if (add && isContainingEStructuralFeature(childFeature)) {
				result.add(childFeature);
			}
			add = add || childFeature == feature;
		}

		for (EClass eCls : getAllSubTypes(feature.getEContainingClass())) {
			for (EStructuralFeature childFeature : eCls.getEStructuralFeatures()) {
				if (isContainingEStructuralFeature(childFeature)) {
					result.add(childFeature);
				}
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#getPrecedingSiblingsEClasses(org.eclipse.emf.ecore.EClass)
	 */
	@Override
	public Set<EClass> getPrecedingSiblingsEClasses(EClass eCls) {
		final Set<EClass> result = new LinkedHashSet<EClass>();

		final Set<EStructuralFeature> containingEStructuralFeatures = getContainingEStructuralFeatures(eCls);
		final Set<EStructuralFeature> precedingEStructuralFeatures = new LinkedHashSet<EStructuralFeature>();
		for (EStructuralFeature feature : containingEStructuralFeatures) {
			precedingEStructuralFeatures.addAll(getPrecedingSiblingEStructuralFeatures(feature));
		}

		for (EStructuralFeature feature : precedingEStructuralFeatures) {
			if (feature.getEType() instanceof EClass) {
				result.add((EClass)feature.getEType());
			}
		}

		return result;
	}

	/**
	 * Gets the {@link Set} of preceding {@link EStructuralFeature} of the given {@link EStructuralFeature},
	 * if the given {@link EStructuralFeature} is {@link EStructuralFeature#isMany() many} it's included in
	 * the result.
	 * 
	 * @param feature
	 *            the {@link EStructuralFeature}
	 * @return the {@link Set} of preceding {@link EStructuralFeature} of the given {@link EStructuralFeature}
	 *         , if the given {@link EStructuralFeature} is {@link EStructuralFeature#isMany() many} it's
	 *         included in the result
	 */
	private Set<EStructuralFeature> getPrecedingSiblingEStructuralFeatures(EStructuralFeature feature) {
		final Set<EStructuralFeature> result = new LinkedHashSet<EStructuralFeature>();

		for (EStructuralFeature childFeature : feature.getEContainingClass().getEAllStructuralFeatures()) {
			if (childFeature == feature) {
				break;
			}
			if (isContainingEStructuralFeature(childFeature)) {
				result.add(childFeature);
			}
		}

		if (feature.isMany()) {
			result.add(feature);
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#getSiblingsEClasses(org.eclipse.emf.ecore.EClass)
	 */
	@Override
	public Set<EClass> getSiblingsEClasses(EClass eCls) {
		final Set<EClass> result = new LinkedHashSet<EClass>();

		final Set<EStructuralFeature> containingEStructuralFeatures = getContainingEStructuralFeatures(eCls);
		final Set<EStructuralFeature> siblingEStructuralFeatures = new LinkedHashSet<EStructuralFeature>();
		for (EStructuralFeature feature : containingEStructuralFeatures) {
			siblingEStructuralFeatures.addAll(getSiblingEStructuralFeatures(feature));
		}

		for (EStructuralFeature feature : siblingEStructuralFeatures) {
			if (isContainingEStructuralFeature(feature) && feature.getEType() instanceof EClass) {
				result.add((EClass)feature.getEType());
			}
		}

		return result;
	}

	/**
	 * Gets the {@link Set} of sibling {@link EStructuralFeature} of the given {@link EStructuralFeature}, if
	 * the given {@link EStructuralFeature} is {@link EStructuralFeature#isMany() many} it's included in the
	 * result.
	 * 
	 * @param feature
	 *            the {@link EStructuralFeature}
	 * @return the {@link Set} of sibling {@link EStructuralFeature} of the given {@link EStructuralFeature},
	 *         if the given {@link EStructuralFeature} is {@link EStructuralFeature#isMany() many} it's
	 *         included in the result
	 */
	private Set<EStructuralFeature> getSiblingEStructuralFeatures(EStructuralFeature feature) {
		final Set<EStructuralFeature> preceding = getPrecedingSiblingEStructuralFeatures(feature);
		final Set<EStructuralFeature> following = getFollowingSiblingEStructuralFeatures(feature);
		final Set<EStructuralFeature> result = new LinkedHashSet<EStructuralFeature>(preceding.size()
				+ following.size());

		result.addAll(preceding);
		result.addAll(following);

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.IEPackageProvider#getRegisteredEPackages()
	 */
	@Override
	public Set<EPackage> getRegisteredEPackages() {
		return new LinkedHashSet<EPackage>(ePackages.values());
	}

}
