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
package org.eclipse.acceleo.query.runtime;

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Provides {@link EPackage} in the {@link IReadOnlyQueryEnvironment}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public interface IEPackageProvider {

	/**
	 * Returns the package registered with the specified name. Returns null if no such package is registered.
	 * 
	 * @param name
	 *            the name of the requested package.
	 * @return the package registered with the specified name.
	 */
	EPackage getEPackage(String name);

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
	EOperation lookupEOperation(EClass receiverEClass, String eOperationName, List<EParameter> parameterTypes);

	/**
	 * Gets the {@link Set} of {@link EOperation} for the given {@link Set} of receiver {@link EClass}.
	 * 
	 * @param receiverTypes
	 *            the {@link Set} of receiver {@link EClass}
	 * @return the {@link Set} of {@link EOperation} for the given {@link Set} of receiver {@link EClass}
	 */
	Set<EOperation> getEOperations(Set<EClass> receiverTypes);

	/**
	 * the classifier with the specified name in the package registered with the specified name.
	 * 
	 * @param name
	 *            the name of the searched classifier's package
	 * @param classifierName
	 *            the name of the searched classifier
	 * @return the classifier with the specified name in the package registered with the specified name.
	 */
	EClassifier getType(String name, String classifierName);

	/**
	 * Returns an {@link EClassifier} instance when a unique one is found in all the registered packages. If
	 * several are found, returns <code>null</code> and log a warning.
	 * 
	 * @param classifierName
	 *            the name of the search classifier.
	 * @return the requested classifier unless several classifiers have the same name in different packages.
	 */
	EClassifier getType(String classifierName);

	/**
	 * Returns the {@link EEnumLiteral} with the specified name in the specified enum.
	 * 
	 * @param name
	 *            the name of the package where to search the {@link EEnumLiteral}
	 * @param enumName
	 *            the name of the {@link org.eclipse.emf.ecore.EEnum EEnum} containing the literal.
	 * @param literalName
	 *            the name of the searched {@link EEnumLiteral}.
	 * @return the specified {@link EEnumLiteral}
	 */
	EEnumLiteral getEnumLiteral(String name, String enumName, String literalName);

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
	EEnumLiteral getEnumLiteral(String enumName, String literalName);

	/**
	 * Gets the {@link EClassifier} represented by the given {@link Class}.
	 * 
	 * @param cls
	 *            the {@link Class}
	 * @return the {@link EClassifier} represented by the given {@link Class} if any, <code>null</code>
	 *         otherwise
	 */
	Set<EClassifier> getEClass(Class<?> cls);

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

}
