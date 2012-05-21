/**
 * <copyright> 
 *
 * Copyright (c) 2005, 2011 IBM Corporation, Zeligsoft Inc., and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *   Zeligsoft - Bug 182994
 *   Obeo - Fork for Acceleo
 *
 * </copyright>
 */
package org.eclipse.acceleo.internal.parser.ast.ocl.environment;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ocl.AbstractTypeResolver;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.internal.OCLEcorePlugin;
import org.eclipse.ocl.ecore.internal.OCLStandardLibraryImpl;
import org.eclipse.ocl.util.TypeUtil;
import org.eclipse.ocl.utilities.UMLReflection;

/**
 * The Acceleo type resolver. Used in Eclipse 3.5+ only by
 * {@link org.eclipse.acceleo.internal.compatibility.parser.ast.ocl.environment.AcceleoEnvironmentGalileo}. It
 * will not compile under Eclipse 4.3 and before, it is expected.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class AcceleoTypeResolver extends AbstractTypeResolver<EPackage, EClassifier, EOperation, EStructuralFeature, EParameter> {

	/**
	 * Initializes me with an environment. I create my own resource for persistence of model-based types.
	 * 
	 * @param env
	 *            the environment that I persist
	 */
	public AcceleoTypeResolver(EcoreEnvironment env) {
		super(env);
	}

	/**
	 * Initializes me with a resource in which I will persist the model-based types that I generate in my
	 * associated {@link org.eclipse.ocl.Environment}.
	 * 
	 * @param env
	 *            my environment
	 * @param resource
	 *            my resource
	 */
	public AcceleoTypeResolver(EcoreEnvironment env, Resource resource) {
		super(env, resource);
	}

	/**
	 * Check the equality of the two classifiers.
	 * 
	 * @param shadowedClassifier
	 *            The shadowed classifier.
	 * @param type
	 *            The type
	 * @return <code>true</code> if they are equals, false otherwise.
	 */
	public static boolean classifierEqual(EClassifier shadowedClassifier, EClassifier type) {
		boolean result = true;

		if (shadowedClassifier != null && type != null && shadowedClassifier.getName() != null) {
			// Same name
			result = result && shadowedClassifier.getName().equals(type.getName());

			// Same container
			EObject shadowedEContainer = shadowedClassifier.eContainer();
			EObject typeEContainer = type.eContainer();
			if (shadowedEContainer instanceof ENamedElement && typeEContainer instanceof ENamedElement) {
				result = result
						&& ((ENamedElement)shadowedEContainer).getName() != null
						&& (((ENamedElement)shadowedEContainer).getName()
								.equals(((ENamedElement)typeEContainer).getName()));
			}

			// Same features
			if (shadowedClassifier instanceof EClass && type instanceof EClass) {
				EClass shadowClass = (EClass)shadowedClassifier;
				EClass typeClass = (EClass)type;

				List<EAttribute> shadowEAllAttributes = shadowClass.getEAllAttributes();
				List<EAttribute> typeEAllAttributes = typeClass.getEAllAttributes();

				if (shadowEAllAttributes != null && typeEAllAttributes != null) {
					result = result && shadowEAllAttributes.size() == typeEAllAttributes.size();
				}
				if (!(result && shadowEAllAttributes != null && typeEAllAttributes != null)) {
					return result;
				}
				for (int i = 0; i < shadowEAllAttributes.size(); i++) {
					EAttribute shadowEAttribute = shadowEAllAttributes.get(i);
					EAttribute typeEAttribute = typeEAllAttributes.get(i);

					if (shadowEAttribute != null && typeEAttribute != null
							&& shadowEAttribute.getName() != null) {
						result = result
								&& shadowEAttribute.getName().equals(typeEAllAttributes.get(i).getName());
					}
					if (shadowEAttribute != null && typeEAttribute != null) {
						result = result && shadowEAttribute.getLowerBound() == typeEAttribute.getLowerBound();
						result = result && shadowEAttribute.getUpperBound() == typeEAttribute.getUpperBound();
					}
				}
			}
		}

		return result;
	}

	/**
	 * Returns the additional operations defined on the given classifier in the context of this environment.
	 * This should always be preferred to {@link #getAdditionalOperations(EClassifier)}.
	 * 
	 * @param owner
	 *            The classifier on which to seek additional operations.
	 * @param name
	 *            The name filter for the classifier's operations.
	 * @return The additional operations defined on the given classifier in the context of this environment.
	 */
	public List<EOperation> getAdditionalOperations(EClassifier owner, String name) {
		final List<EOperation> result = new ArrayList<EOperation>();
		if (hasAdditionalFeatures()) {
			EClassifier shadow = findShadowClass(owner);

			if (shadow == null) {
				return result;
			}

			final UMLReflection<EPackage, EClassifier, EOperation, EStructuralFeature, ?, EParameter, ?, ?, ?, ?> umlReflection = getEnvironment()
					.getUMLReflection();

			if (umlReflection instanceof AcceleoUMLReflection) {
				result.addAll(((AcceleoUMLReflection)umlReflection).getOperations(shadow, name));
			} else {
				final List<EOperation> candidates = umlReflection.getOperations(shadow);
				for (EOperation candidate : candidates) {
					if (name.equals(candidate.getName())) {
						result.add(candidate);
					}
				}
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractTypeResolver#addClassifier(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected void addClassifier(EPackage pkg, EClassifier classifier) {
		if (pkg != null) {
			pkg.getEClassifiers().add(classifier);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractTypeResolver#addOperation(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected void addOperation(EClassifier owner, EOperation operation) {
		if (owner instanceof EClass) {
			((EClass)owner).getEOperations().add(operation);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractTypeResolver#addProperty(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected void addProperty(EClassifier owner, EStructuralFeature property) {
		if (owner instanceof EClass) {
			((EClass)owner).getEStructuralFeatures().add(property);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractTypeResolver#createPackage(java.lang.String)
	 */
	@Override
	protected EPackage createPackage(String name) {
		EPackage result = EcoreFactory.eINSTANCE.createEPackage();

		result.setName(name);
		getResource().getContents().add(result);

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractTypeResolver#createResource()
	 */
	@Override
	protected Resource createResource() {
		Resource.Factory factory = OCLEcorePlugin.getEcoreResourceFactory();

		return factory.createResource(URI.createURI("ocl:///oclenv.ecore")); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractTypeResolver#createShadowClass(java.lang.Object)
	 */
	@Override
	protected EClass createShadowClass(EClassifier type) {
		return OCLStandardLibraryImpl.createShadowClass(type);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractTypeResolver#findMatchingOperation(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected EOperation findMatchingOperation(EClassifier shadow, EOperation operation) {
		final UMLReflection<EPackage, EClassifier, EOperation, EStructuralFeature, ?, EParameter, ?, ?, ?, ?> umlReflection = getEnvironment()
				.getUMLReflection();
		final String operationName = umlReflection.getName(operation);

		final List<EOperation> candidates;
		if (umlReflection instanceof AcceleoUMLReflection) {
			candidates = ((AcceleoUMLReflection)umlReflection).getOperations(shadow, operationName);
		} else {
			candidates = umlReflection.getOperations(shadow);
		}

		for (EOperation next : candidates) {
			if (next == operation
					|| (umlReflection.getName(next).equals(operationName) && matchParameters(next, operation))) {
				return next;
			}
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractTypeResolver#findPackage(java.lang.String)
	 */
	@Override
	protected EPackage findPackage(String name) {
		EPackage result = null;

		for (EObject o : getResource().getContents()) {
			if (o instanceof EPackage) {
				EPackage epkg = (EPackage)o;

				if (name != null && name.equals(epkg.getName())) {
					result = epkg;
					break;
				}
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractTypeResolver#findShadowClass(java.lang.Object)
	 */
	@Override
	protected EClassifier findShadowClass(EClassifier type) {
		EPackage pkg = null;
		if (hasAdditionalFeatures()) {
			pkg = getAdditionalFeaturesPackage();
		}

		if (pkg != null) {
			for (EClassifier next : pkg.getEClassifiers()) {
				EClassifier shadow = getShadowedClassifier(next);
				if (shadow == type || shadow != null && classifierEqual(shadow, type)) {
					return next;
				}
			}
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractTypeResolver#getShadowedClassifier(java.lang.Object)
	 */
	@Override
	protected EClassifier getShadowedClassifier(EClassifier shadow) {
		if (shadow instanceof EClass) {
			return OCLStandardLibraryImpl.getRealClassifier((EClass)shadow);
		}

		return null;
	}

	/**
	 * Determines whether two operations have the same parameter signature.
	 * <p>
	 * Copied from org.eclipse.ocl.AbstractTypeResolver.
	 * </p>
	 * 
	 * @param a
	 *            an operation
	 * @param b
	 *            another operation
	 * @return <code>true</code> if they have the same formal parameters in the same order; <code>false</code>
	 *         , otherwise.
	 * @see org.eclipse.ocl.AbstractTypeResolver#matchParameters(Object, Object)
	 */
	private boolean matchParameters(EOperation a, EOperation b) {
		final UMLReflection<EPackage, EClassifier, EOperation, EStructuralFeature, ?, EParameter, ?, ?, ?, ?> umlReflection = getEnvironment()
				.getUMLReflection();
		List<EParameter> aparms = umlReflection.getParameters(a);
		List<EParameter> bparms = umlReflection.getParameters(b);

		boolean matching = false;
		if (aparms.size() == bparms.size()) {
			int count = aparms.size();

			for (int i = 0; i < count; i++) {
				EParameter aparm = aparms.get(i);
				EParameter bparm = bparms.get(i);

				if (!umlReflection.getName(aparm).equals(umlReflection.getName(bparm))
						|| TypeUtil.getRelationship(getEnvironment(),
								resolve(umlReflection.getOCLType(aparm)), resolve(umlReflection
										.getOCLType(bparm))) != UMLReflection.SAME_TYPE) {

					return false;
				}
			}

			matching = true;
		}

		return matching;
	}
}
