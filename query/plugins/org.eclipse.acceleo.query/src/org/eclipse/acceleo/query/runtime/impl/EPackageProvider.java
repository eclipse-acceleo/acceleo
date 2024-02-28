/*******************************************************************************
 * Copyright (c) 2015, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.runtime.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IEPackageProvider;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EPackage;
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
	 * Map the name to their corresponding package.
	 */
	private Map<String, Set<EPackage>> ePackages = new LinkedHashMap<String, Set<EPackage>>();

	/**
	 * {@link Class} to {@link EClassifier} mapping.
	 */
	private final Map<Class<?>, Set<EClassifier>> class2classifiers = new HashMap<Class<?>, Set<EClassifier>>();

	/**
	 * {@link String} class {@link Class#getCanonicalName() canonical name} to {@link EClassifier} mapping.
	 */
	private final Map<String, Set<EClassifier>> className2classifiers = new HashMap<String, Set<EClassifier>>();

	/**
	 * {@link EClassifier} to {@link Class} mapping.
	 */
	private final Map<EClassifier, Class<?>> classifier2class = new HashMap<EClassifier, Class<?>>();

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

	@Override
	public Set<EPackage> getEPackage(String name) {
		final Set<EPackage> res = new LinkedHashSet<EPackage>();

		final Set<EPackage> set = ePackages.get(name);
		if (set != null) {
			res.addAll(set);
		}

		return res;
	}

	/**
	 * Removes the {@link EPackageProvider#registerPackage(EPackage) registered} {@link EPackage}s having the
	 * given {@link EPackage}.
	 * 
	 * @param ePackage
	 *            the {@link EPackage} to remove
	 * @return the list of removed {@link EPackage}
	 */
	public Collection<EPackage> removePackage(EPackage ePackage) {
		final Collection<EPackage> result = new ArrayList<EPackage>();

		final Set<EPackage> set = ePackages.get(ePackage.getName());
		if (set != null) {
			if (set.remove(ePackage)) {
				if (set.isEmpty()) {
					ePackages.remove(ePackage.getName());
				}
				result.add(ePackage);
				for (EClassifier eCls : ePackage.getEClassifiers()) {
					removeEClassifierClass(eCls);
					if (eCls instanceof EClass) {
						removeFeatures((EClass)eCls);
						removeSubType((EClass)eCls);
					}
				}
				for (EPackage childPkg : ePackage.getESubpackages()) {
					removePackage(childPkg);
				}
				containingFeatures.clear();
				allContainingFeatures.clear();
			}
		}

		return result;
	}

	/**
	 * Removes the given {@link EClass} as been a sub type of its {@link EClass#getESuperTypes() super types}.
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
	 * Removes the given {@link EClassifier} from {@link EPackageProvider#class2classifiers the class mapping}
	 * .
	 * 
	 * @param eCls
	 *            the {@link EClassifier} to remove
	 */
	private void removeEClassifierClass(EClassifier eCls) {
		final Class<?> instanceClass = getClass(eCls);
		final Set<EClassifier> classifiersFromClass = class2classifiers.get(instanceClass);
		if (classifiersFromClass != null) {
			if (classifiersFromClass.size() == 1) {
				class2classifiers.remove(instanceClass);
			} else {
				classifiersFromClass.remove(eCls);
			}
		}
		if (instanceClass != null) {
			final String qualifiedName = instanceClass.getCanonicalName();
			final Set<EClassifier> classifiersFromClassName = className2classifiers.get(qualifiedName);
			if (classifiersFromClassName != null) {
				if (classifiersFromClassName.size() == 1) {
					className2classifiers.remove(qualifiedName);
				} else {
					classifiersFromClassName.remove(eCls);
				}
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
				Set<EPackage> set = ePackages.get(ePackage.getName());
				if (set == null) {
					set = new LinkedHashSet<EPackage>();
					ePackages.put(ePackage.getName(), set);
				}
				boolean increasedSize = set.add(ePackage);
				if (!increasedSize) {
					// duplicate package
					return null;
				}
				result = ePackage;
				for (EClassifier eCls : ePackage.getEClassifiers()) {
					registerEClassifierClass(eCls);
					if (eCls instanceof EClass) {
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
		Set<EClassifier> classifiersFromClass = class2classifiers.get(instanceClass);
		if (classifiersFromClass == null) {
			classifiersFromClass = new LinkedHashSet<EClassifier>();
			class2classifiers.put(instanceClass, classifiersFromClass);
		}
		classifiersFromClass.add(eCls);
		if (instanceClass != null) {
			final String qualifiedName = instanceClass.getCanonicalName();
			Set<EClassifier> classifiersFromClassName = className2classifiers.get(qualifiedName);
			if (classifiersFromClassName == null) {
				classifiersFromClassName = new LinkedHashSet<EClassifier>();
				className2classifiers.put(qualifiedName, classifiersFromClassName);
			}
			classifiersFromClassName.add(eCls);
		}
	}

	@Override
	public Set<EClassifier> getTypes(String name, String classifierName) {
		final Set<EClassifier> classifiers = new LinkedHashSet<EClassifier>();

		final Set<EPackage> set = ePackages.get(name);
		if (set != null) {
			for (EPackage ePackage : set) {
				EClassifier clazz = ePackage.getEClassifier(classifierName);
				if (clazz != null) {
					classifiers.add(clazz);
				}
			}
		}

		return classifiers;
	}

	@Override
	public EClassifier getType(String name, String classifierName) {
		Collection<EClassifier> result = getTypes(name, classifierName);
		if (result.size() == 0) {
			return null;
		} else {
			return result.iterator().next();
		}
	}

	@Override
	public Set<EEnumLiteral> getEnumLiterals(String name, String enumName, String literalName) {
		Set<EEnumLiteral> result = new LinkedHashSet<EEnumLiteral>();

		final Set<EPackage> set = ePackages.get(name);
		if (set != null) {
			for (EPackage ePackage : set) {
				EClassifier eClassifier = ePackage.getEClassifier(enumName);
				if (eClassifier != null) {
					EEnumLiteral literal = getEnumLiteral(eClassifier, literalName);
					if (literal != null) {
						result.add(literal);
					}
				}
			}
		}

		return result;
	}

	@Override
	public EClassifier getType(String classifierName) {
		EClassifier result = null;
		for (Set<EPackage> ePkgs : ePackages.values()) {
			for (EPackage ePackage : ePkgs) {
				EClassifier foundClassifier = ePackage.getEClassifier(classifierName);
				if (foundClassifier != null) {
					if (result == null) {
						result = foundClassifier;
					} else {
						String firstFullyQualifiedName = result.getEPackage().getName() + "." + result
								.getName();
						String secondFullyQualifiedName = foundClassifier.getEPackage().getName() + "."
								+ foundClassifier.getName();
						String message = "Ambiguous classifier request. At least two classifiers matches %s : %s and %s";
						throw new IllegalStateException(String.format(message, classifierName,
								firstFullyQualifiedName, secondFullyQualifiedName));
					}
				}
			}
		}
		return result;
	}

	@Override
	public Set<EClassifier> getTypes(String classifierName) {
		Set<EClassifier> result = new LinkedHashSet<EClassifier>();
		for (Set<EPackage> ePkgs : ePackages.values()) {
			for (EPackage ePackage : ePkgs) {
				EClassifier foundClassifier = ePackage.getEClassifier(classifierName);
				if (foundClassifier != null) {
					result.add(foundClassifier);
				}
			}
		}
		return result;
	}

	@Override
	public EEnumLiteral getEnumLiteral(String packageName, String enumName, String literalName) {
		Collection<EEnumLiteral> result = getEnumLiterals(packageName, enumName, literalName);
		if (result.size() == 0) {
			return null;
		} else {
			return result.iterator().next();
		}
	}

	@Override
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

	@Override
	public Set<EClassifier> getEClassifiers(Class<?> cls) {
		return class2classifiers.get(cls);
	}

	@Override
	public Set<EClassifier> getEClassifiers(String clsName) {
		return className2classifiers.get(clsName);
	}

	@Override
	public Class<?> getClass(EClassifier eCls) {
		return classifier2class.get(eCls);
	}

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
			final Set<EClassifier> eClassifiersFromClass = class2classifiers.get(oldClass);
			if (eClassifiersFromClass.remove(eClassifier) && eClassifiersFromClass.isEmpty()) {
				class2classifiers.remove(oldClass);
			}
			final String qualifiedName = oldClass.getCanonicalName();
			final Set<EClassifier> eClassifiersFromClassName = className2classifiers.get(qualifiedName);
			if (eClassifiersFromClassName.remove(eClassifier) && eClassifiersFromClassName.isEmpty()) {
				className2classifiers.remove(qualifiedName);
			}
		}
		// add new mappings
		classifier2class.put(eClassifier, cls);
		Set<EClassifier> eClassifiersFromClass = class2classifiers.get(cls);
		if (eClassifiersFromClass == null) {
			eClassifiersFromClass = new LinkedHashSet<EClassifier>();
			class2classifiers.put(cls, eClassifiersFromClass);
		}
		eClassifiersFromClass.add(eClassifier);
		if (cls != null) {
			final String qualifiedName = cls.getCanonicalName();
			Set<EClassifier> eClassifiersFromClassName = className2classifiers.get(qualifiedName);
			if (eClassifiersFromClassName == null) {
				eClassifiersFromClassName = new LinkedHashSet<EClassifier>();
				className2classifiers.put(qualifiedName, eClassifiersFromClassName);
			}
			eClassifiersFromClassName.add(eClassifier);
		}
	}

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

	@Override
	public Set<EClassifier> getEClassifiers() {
		final Set<EClassifier> result = new LinkedHashSet<EClassifier>();

		for (Set<EPackage> ePkgs : ePackages.values()) {
			for (EPackage ePkg : ePkgs) {
				result.addAll(ePkg.getEClassifiers());
			}
		}

		return result;
	}

	@Override
	public Set<EEnumLiteral> getEEnumLiterals() {
		final Set<EEnumLiteral> result = new LinkedHashSet<EEnumLiteral>();

		for (Set<EPackage> ePkgs : ePackages.values()) {
			for (EPackage ePkg : ePkgs) {
				for (EClassifier eClassifier : ePkg.getEClassifiers()) {
					if (eClassifier instanceof EEnum) {
						result.addAll(((EEnum)eClassifier).getELiterals());
					}
				}
			}
		}

		return result;
	}

	@Override
	public Set<EClass> getContainingEClasses(EClass eCls) {
		final Set<EClass> result = new LinkedHashSet<EClass>();

		for (EStructuralFeature feature : getContainingEStructuralFeatures(eCls)) {
			result.add(feature.getEContainingClass());
		}

		return result;
	}

	@Override
	public Set<EClass> getAllContainingEClasses(EClass eCls) {
		final Set<EClass> result = new LinkedHashSet<EClass>();

		for (EStructuralFeature feature : getAllContainingEStructuralFeatures(eCls)) {
			result.add(feature.getEContainingClass());
		}

		return result;
	}

	@Override
	public Set<EStructuralFeature> getAllContainingEStructuralFeatures(EClass type) {
		Set<EStructuralFeature> result = allContainingFeatures.get(type);

		if (result == null) {
			result = new LinkedHashSet<EStructuralFeature>();
			allContainingFeatures.put(type, result);

			final Set<EClass> knownECls = new HashSet<EClass>();
			Set<EStructuralFeature> previousAdded = new LinkedHashSet<EStructuralFeature>(
					getAllContainingEStructuralFeaturesInternal(type));
			result.addAll(previousAdded);
			while (!previousAdded.isEmpty()) {
				Set<EStructuralFeature> currentAdded = new LinkedHashSet<EStructuralFeature>();
				for (EStructuralFeature feature : previousAdded) {
					final EClass eContainingClass = feature.getEContainingClass();
					if (!knownECls.contains(eContainingClass)) {
						for (EStructuralFeature parentFeature : getAllContainingEStructuralFeaturesInternal(
								eContainingClass)) {
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

	private Set<EStructuralFeature> getAllContainingEStructuralFeaturesInternal(EClass eCls) {
		Set<EStructuralFeature> result = containingFeatures.get(eCls);

		if (result == null) {
			result = new LinkedHashSet<EStructuralFeature>();
			containingFeatures.put(eCls, result);

			result.addAll(getContainingEStructuralFeaturesForOneEClassHierarchyLevel(eCls));
			for (EClass superType : eCls.getEAllSuperTypes()) {
				result.addAll(getAllContainingEStructuralFeaturesInternal(superType));
			}
			for (EClass subType : getAllSubTypes(eCls)) {
				result.addAll(getAllContainingEStructuralFeaturesInternal(subType));
			}
			// always add EObject EClass containing EStructuralFeatures
			result.addAll(getContainingEStructuralFeaturesForOneEClassHierarchyLevel(EcorePackage.eINSTANCE
					.getEObject()));
		}

		return result;
	}

	@Override
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

	@Override
	public Set<EClass> getAllContainedEClasses(EClass eCls) {
		final Set<EClass> directlyContainedEClasses = getContainedEClasses(eCls);
		final Set<EClass> result = new LinkedHashSet<EClass>(directlyContainedEClasses);

		Set<EClass> added = new LinkedHashSet<EClass>(directlyContainedEClasses);
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

	@Override
	public Set<EPackage> getRegisteredEPackages() {
		final LinkedHashSet<EPackage> res = new LinkedHashSet<EPackage>();

		for (Set<EPackage> ePkgs : ePackages.values()) {
			res.addAll(ePkgs);
		}

		return res;
	}
}
