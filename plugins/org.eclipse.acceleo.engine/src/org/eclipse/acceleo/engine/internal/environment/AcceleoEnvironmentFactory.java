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

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.generation.AbstractAcceleoEnvironmentFactory;
import org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationContext;
import org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitor;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ocl.Environment;
import org.eclipse.ocl.EvaluationEnvironment;
import org.eclipse.ocl.EvaluationVisitor;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.SendSignalAction;

/**
 * Represents the environment factory used to evaluate Acceleo template.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEnvironmentFactory extends AbstractAcceleoEnvironmentFactory {
	/**
	 * Generation context for this factory. This will be shared by both the evaluation environment and
	 * visitor.
	 */
	protected AcceleoEvaluationContext<EClassifier> context;

	/** Module for which this environment factory has been created. */
	private final Module module;

	/** This will hold a reference to the class allowing for properties lookup. */
	private AcceleoPropertiesLookup propertiesLookup;

	/**
	 * Default constructor. Packages will be looked up into the global EMF registry.
	 * 
	 * @param generationRoot
	 *            Root of all files that will be generated.
	 * @param module
	 *            The module for which this factory is to be created.
	 * @param listeners
	 *            The list of all listeners that are to be notified for text generation from this context.
	 * @param properties
	 *            The class allowing for properties lookup for this generation.
	 * @param strategy
	 *            The generation strategy that's to be used by this factory's context.
	 * @param monitor
	 *            This will be used as the progress monitor for the generation.
	 */
	public AcceleoEnvironmentFactory(File generationRoot, Module module,
			List<IAcceleoTextGenerationListener> listeners, AcceleoPropertiesLookup properties,
			IAcceleoGenerationStrategy strategy, Monitor monitor) {
		super(AcceleoPackageRegistry.INSTANCE);
		context = new AcceleoEvaluationContext<EClassifier>(generationRoot, listeners, strategy, monitor);
		propertiesLookup = properties;
		this.module = module;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.ecore.EcoreEnvironmentFactory#createEnvironment()
	 */
	@Override
	public Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> createEnvironment() {
		AcceleoEnvironment result = new AcceleoEnvironment(getEPackageRegistry());
		result.setFactory(this);
		result.restoreBrokenEnvironmentPackages(module.eResource());
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.ecore.EcoreEnvironmentFactory#createEnvironment(org.eclipse.ocl.Environment)
	 */
	@Override
	public Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> createEnvironment(
			Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> parent) {
		if (!(parent instanceof AcceleoEnvironment)) {
			throw new IllegalArgumentException(AcceleoEngineMessages.getString(
					"AcceleoEnvironmentFactory.IllegalParent", parent.getClass().getName())); //$NON-NLS-1$
		}

		AcceleoEnvironment result = new AcceleoEnvironment(parent);
		result.setFactory(this);
		result.restoreBrokenEnvironmentPackages(module.eResource());
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.ecore.EcoreEnvironmentFactory#createEvaluationEnvironment()
	 */
	@Override
	public EvaluationEnvironment<EClassifier, EOperation, EStructuralFeature, EClass, EObject> createEvaluationEnvironment() {
		return new AcceleoEvaluationEnvironment(module, propertiesLookup);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.ecore.EcoreEnvironmentFactory#createEvaluationEnvironment(org.eclipse.ocl.EvaluationEnvironment)
	 */
	@Override
	public EvaluationEnvironment<EClassifier, EOperation, EStructuralFeature, EClass, EObject> createEvaluationEnvironment(
			EvaluationEnvironment<EClassifier, EOperation, EStructuralFeature, EClass, EObject> parent) {
		return new AcceleoEvaluationEnvironment(parent, module, propertiesLookup);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.AbstractEnvironmentFactory#createEvaluationVisitor(org.eclipse.ocl.Environment,
	 *      org.eclipse.ocl.EvaluationEnvironment, java.util.Map)
	 */
	@Override
	public EvaluationVisitor<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> createEvaluationVisitor(
			Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> env,
			EvaluationEnvironment<EClassifier, EOperation, EStructuralFeature, EClass, EObject> evalEnv,
			Map<? extends EClass, ? extends Set<? extends EObject>> extentMap) {
		return new AcceleoEvaluationVisitor<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject>(
				super.createEvaluationVisitor(env, evalEnv, extentMap), context);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.AbstractAcceleoEnvironmentFactory#dispose()
	 */
	@Override
	public void dispose() {
		context.dispose();
		propertiesLookup = null;
		AcceleoLibraryOperationVisitor.dispose();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.AbstractAcceleoEnvironmentFactory#getEvaluationPreview()
	 */
	@Override
	public Map<String, String> getEvaluationPreview() {
		return context.getGenerationPreview();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.AbstractAcceleoEnvironmentFactory#hookGenerationEnd()
	 */
	@Override
	public void hookGenerationEnd() {
		context.hookGenerationEnd();
	}
}
