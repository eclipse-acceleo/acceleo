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
package org.eclipse.acceleo.query.runtime;

import java.util.Collection;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Provides {@link EPackage} in the {@link IReadOnlyQueryEnvironment}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 * @noimplements This interface is not designed to be implemented outside of AQL.
 */
public interface IEPackageProvider {

	/**
	 * Returns the {@link Collection} of {@link EPackage} registered with the specified name.
	 * 
	 * @param name
	 *            the name of the requested package.
	 * @return the {@link Collection} of {@link EPackage} registered with the specified name
	 * @since 8.0.2
	 */
	Set<EPackage> getEPackage(String name);

	/**
	 * the classifier with the specified name in the package registered with the specified name.
	 * 
	 * @param name
	 *            the name of the searched classifier's package
	 * @param classifierName
	 *            the name of the searched classifier
	 * @return the list of classifiers matching the given {@link EClassifier} and {@link EPackage} names.
	 * @since 8.0.2
	 */
	Set<EClassifier> getTypes(String name, String classifierName);

	/**
	 * the list of classifiers known to the package provider with the specified name.
	 * 
	 * @param name
	 *            the name of the searched classifier
	 * @return the list of classifiers matching the given {@link EClassifier} names.
	 * @since 8.0.2
	 */
	Set<EClassifier> getTypes(String name);

	/**
	 * the classifier with the specified name in the package registered with the specified name.
	 * 
	 * @param name
	 *            the name of the searched classifier's package
	 * @param classifierName
	 *            the name of the searched classifier
	 * @return the classifier with the specified name in the package registered with the specified name.
	 * @deprecated you should use the getTypes(...) method which consider that there might be several EClasses
	 *             sharing the same name.
	 */
	@Deprecated
	EClassifier getType(String name, String classifierName);

	/**
	 * Returns an {@link EClassifier} instance when a unique one is found in all the registered packages. If
	 * several are found, returns <code>null</code> and log a warning.
	 * 
	 * @param classifierName
	 *            the name of the search classifier.
	 * @return the requested classifier unless several classifiers have the same name in different packages.
	 */
	@Deprecated
	EClassifier getType(String classifierName);

	/**
	 * Returns the {@link EEnumLiteral} with the specified name in the specified enum.
	 * 
	 * @param packageName
	 *            the name of the package where to search the {@link EEnumLiteral}
	 * @param enumName
	 *            the name of the {@link org.eclipse.emf.ecore.EEnum EEnum} containing the literal.
	 * @param literalName
	 *            the name of the searched {@link EEnumLiteral}.
	 * @return the list of {@link EEnumLiteral} instances matching the given {@link EPackage},
	 *         {@link org.eclipse.emf.ecore.EEnum EEnum} and {@link EEnumLiteral} names.
	 * @since 4.1
	 */
	Collection<EEnumLiteral> getEnumLiterals(String packageName, String enumName, String literalName);

	/**
	 * Returns the {@link EEnumLiteral} with the specified name in the specified enum.
	 * 
	 * @param packageName
	 *            the name of the package where to search the {@link EEnumLiteral}
	 * @param enumName
	 *            the name of the {@link org.eclipse.emf.ecore.EEnum EEnum} containing the literal.
	 * @param literalName
	 *            the name of the searched {@link EEnumLiteral}.
	 * @return the specified {@link EEnumLiteral}
	 * @deprecated you should use the getEnumLiterals(...) method which consider that there might be several
	 *             EnumLiterals sharing the same name.
	 */
	@Deprecated
	EEnumLiteral getEnumLiteral(String packageName, String enumName, String literalName);

	/**
	 * Returns the {@link EEnumLiteral} with the specified name in the specified enum if it exists in one of
	 * the registered package. Returns <code>null</code> otherwise.
	 * 
	 * @param enumName
	 *            the name of the {@link org.eclipse.emf.ecore.EEnum EEnum} containing the literal.
	 * @param literalName
	 *            the name of the searched {@link EEnumLiteral}.
	 * @return the specified {@link EEnumLiteral}
	 */
	@Deprecated
	EEnumLiteral getEnumLiteral(String enumName, String literalName);

	/**
	 * Gets the {@link EClassifier} represented by the given {@link Class}.
	 * 
	 * @param cls
	 *            the {@link Class}
	 * @return the {@link EClassifier} represented by the given {@link Class} if any, <code>null</code>
	 *         otherwise
	 * @since 5.0
	 */
	Set<EClassifier> getEClassifiers(Class<?> cls);

	/**
	 * Gets the {@link EClassifier} represented by the given {@link Class} {@link Class#getCanonicalName()
	 * canonical name}.
	 * 
	 * @param clsName
	 *            the {@link Class} qualified name
	 * @return the {@link EClassifier} represented by the given {@link Class} {@link Class#getCanonicalName()
	 *         canonical name} if any, <code>null</code> otherwise
	 * @since 8.0.2
	 */
	Set<EClassifier> getEClassifiers(String clsName);

	/**
	 * Gets the {@link Class} of instance of the given {@link EClassifier}.
	 * 
	 * @param eCls
	 *            the {@link EClassifier}
	 * @return the {@link Class} of instance of the given {@link EClassifier} if any, <code>null</code>
	 *         otherwise
	 */
	Class<?> getClass(EClassifier eCls);

	/**
	 * Checks whether the given EClassifier has been registered in this package provider.
	 * 
	 * @param eCls
	 *            The EClassifier to check.
	 * @return <code>true</code> if this classifier has been registered in this package provider,
	 *         <code>false</code> otherwise.
	 * @since 4.1
	 */
	boolean isRegistered(EClassifier eCls);

	/**
	 * Gets the {@link Set} of registered {@link EPackage}.
	 * 
	 * @return the {@link Set} of registered {@link EPackage}
	 * @since 4.0.0
	 */
	Set<EPackage> getRegisteredEPackages();

	/**
	 * Gets the {@link EStructuralFeature} from the given {@link Set} of {@link EClass}.
	 * 
	 * @param receiverEClasses
	 *            the {@link Set} of {@link EClass}.
	 * @return the {@link EStructuralFeature} from the given {@link Set} of {@link EClass}
	 */
	Set<EStructuralFeature> getEStructuralFeatures(Set<EClass> receiverEClasses);

	/**
	 * Gets the {@link Set} of {@link IQueryEnvironment#registerEPackage(EPackage) registered}
	 * {@link EClassifier}.
	 * 
	 * @return the {@link Set} of {@link IQueryEnvironment#registerEPackage(EPackage) registered}
	 *         {@link EClassifier}
	 */
	Set<EClassifier> getEClassifiers();

	/**
	 * Gets the {@link Set} of {@link IQueryEnvironment#registerEPackage(EPackage) registered}
	 * {@link EEnumLiteral}.
	 * 
	 * @return the {@link Set} of {@link IQueryEnvironment#registerEPackage(EPackage) registered}
	 *         {@link EEnumLiteral}
	 */
	Set<EEnumLiteral> getEEnumLiterals();

	/**
	 * Gets the {@link Set} of {@link EClass} that can directly contain in the given {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 * @return the {@link Set} of {@link EClass} that can directly contain in the given {@link EClass}
	 */
	Set<EClass> getContainingEClasses(EClass eCls);

	/**
	 * Gets the {@link Set} of {@link EClass} that can directly and indirectly contain in the given
	 * {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 * @return the {@link Set} of {@link EClass} that can directly and indirectly contain in the given
	 *         {@link EClass}
	 */
	Set<EClass> getAllContainingEClasses(EClass eCls);

	/**
	 * Gets all sub types for the given {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 * @return all sub types for the given {@link EClass}
	 */
	Set<EClass> getAllSubTypes(EClass eCls);

	/**
	 * Gets the {@link Set} of {@link EClass} that can be directly contained in the given {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 * @return the {@link Set} of {@link EClass} that can be directly contained in the given {@link EClass}
	 */
	Set<EClass> getContainedEClasses(EClass eCls);

	/**
	 * Gets the {@link Set} of {@link EClass} that can be directly and indirectly contained in the given
	 * {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 * @return the {@link Set} of {@link EClass} that can be directly and indirectly contained in the given
	 *         {@link EClass}
	 */
	Set<EClass> getAllContainedEClasses(EClass eCls);

	/**
	 * Gets the {@link Set} of {@link EClass} that can directly reference the given {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 * @return the {@link Set} of {@link EClass} that can directly reference in the given {@link EClass}
	 */
	Set<EClass> getInverseEClasses(EClass eCls);

	/**
	 * Gets the {@link Set} of {@link EClass} that can be following siblings of the given {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 * @return the {@link Set} of {@link EClass} that can be following siblings of the given {@link EClass}
	 */
	Set<EClass> getFollowingSiblingsEClasses(EClass eCls);

	/**
	 * Gets the {@link Set} of {@link EClass} that can be preceding siblings of the given {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 * @return the {@link Set} of {@link EClass} that can be preceding siblings of the given {@link EClass}
	 */
	Set<EClass> getPrecedingSiblingsEClasses(EClass eCls);

	/**
	 * Gets the {@link Set} of {@link EClassifier} that can be siblings of the given {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 * @return the {@link Set} of {@link EClassifier} that can be siblings of the given {@link EClass}
	 */
	Set<EClass> getSiblingsEClasses(EClass eCls);

	/**
	 * Gets the {@link Set} of containing {@link EStructuralFeature} for the given {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 * @return the {@link Set} of containing {@link EStructuralFeature} for the given {@link EClass}
	 * @since 4.1
	 */
	Set<EStructuralFeature> getContainingEStructuralFeatures(EClass eCls);

	/**
	 * Gets the {@link Set} of all containing {@link EStructuralFeature} transitively for the given
	 * {@link EClass}.
	 * 
	 * @param eCls
	 *            the {@link EClass}
	 * @return the {@link Set} of all containing {@link EStructuralFeature} transitively for the given
	 *         {@link EClass}
	 * @since 4.1
	 */
	Set<EStructuralFeature> getAllContainingEStructuralFeatures(EClass eCls);

}
