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

import org.eclipse.emf.common.util.EList;
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
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.AbstractTypeResolver;
import org.eclipse.ocl.ecore.EcoreEnvironment;
import org.eclipse.ocl.ecore.internal.OCLEcorePlugin;
import org.eclipse.ocl.ecore.internal.OCLStandardLibraryImpl;
import org.eclipse.ocl.ecore.internal.TupleFactory;

/**
 * The Acceleo type resolver.
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
	 * @see org.eclipse.ocl.AbstractTypeResolver#createTuplePackage()
	 */
	@Override
	protected EPackage createTuplePackage() {
		EPackage result = super.createTuplePackage();

		result.setEFactoryInstance(new TupleFactory());

		return result;
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
	 * @see org.eclipse.ocl.AbstractTypeResolver#findPackage(java.lang.String)
	 */
	@Override
	protected EPackage findPackage(String name) {
		EPackage result = null;

		for (EObject o : getResource().getContents()) {
			if (o instanceof EPackage) {
				EPackage epkg = (EPackage)o;

				if (name.equals(epkg.getName())) {
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
	 * @see org.eclipse.ocl.AbstractTypeResolver#addClassifier(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected void addClassifier(EPackage pkg, EClassifier classifier) {
		pkg.getEClassifiers().add(classifier);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractTypeResolver#addOperation(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected void addOperation(EClassifier owner, EOperation operation) {
		((EClass)owner).getEOperations().add(operation);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractTypeResolver#addProperty(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected void addProperty(EClassifier owner, EStructuralFeature property) {
		((EClass)owner).getEStructuralFeatures().add(property);
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
				if (getShadowedClassifier(next) == type || classifierEqual(getShadowedClassifier(next), type)) {
					return next;
				}
			}
		}

		return null;
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
	private boolean classifierEqual(EClassifier shadowedClassifier, EClassifier type) {
		boolean result = true;
		// Same name
		result = result && shadowedClassifier.getName().equals(type.getName());

		// Same container
		EObject shadowedEContainer = shadowedClassifier.eContainer();
		EObject typeEContainer = type.eContainer();
		result = result
				&& (shadowedEContainer instanceof ENamedElement && typeEContainer instanceof ENamedElement)
				&& (((ENamedElement)shadowedEContainer).getName().equals(((ENamedElement)typeEContainer)
						.getName()));

		result = result && EcoreUtil.getURI(shadowedEContainer).equals(EcoreUtil.getURI(typeEContainer));

		// Same features
		if (shadowedClassifier instanceof EClass && type instanceof EClass) {
			EClass shadowClass = (EClass)shadowedClassifier;
			EClass typeClass = (EClass)type;

			EList<EAttribute> shadowEAllAttributes = shadowClass.getEAllAttributes();
			EList<EAttribute> typeEAllAttributes = typeClass.getEAllAttributes();

			result = result && shadowEAllAttributes.size() == typeEAllAttributes.size();
			if (result) {
				for (int i = 0; i < shadowEAllAttributes.size(); i++) {
					result = result
							&& shadowEAllAttributes.get(i).getName().equals(
									typeEAllAttributes.get(i).getName());
					result = result
							&& shadowEAllAttributes.get(i).getLowerBound() == typeEAllAttributes.get(i)
									.getLowerBound();
					result = result
							&& shadowEAllAttributes.get(i).getUpperBound() == typeEAllAttributes.get(i)
									.getUpperBound();
				}
			}
		}

		return result;
	}
}
