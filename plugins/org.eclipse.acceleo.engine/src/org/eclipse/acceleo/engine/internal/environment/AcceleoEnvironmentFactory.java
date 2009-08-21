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
package org.eclipse.acceleo.engine.internal.environment;

import java.io.File;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
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
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.ecore.SendSignalAction;

/**
 * Represents the environment factory used to evaluate Acceleo template.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoEnvironmentFactory extends EcoreEnvironmentFactory {
	/**
	 * Generation context for this factory. This will be shared by both the evaluation environment and
	 * visitor.
	 */
	private final AcceleoEvaluationContext context;

	/** Module for which this environment factory has been created. */
	private final Module module;

	/** This will hold the list of properties accessible from the generation context. */
	private final List<Properties> properties = new ArrayList<Properties>();

	/**
	 * Default constructor. Packages will be looked up into the global EMF registry.
	 * 
	 * @param generationRoot
	 *            Root of all files that will be generated.
	 * @param module
	 *            The module for which this factory is to be created.
	 * @param listeners
	 *            The list of all listeners that are to be notified for text generation from this context.
	 * @param props
	 *            The list of Properties that can be accessed from the context.
	 * @param preview
	 *            Tells the evaluation context that it is currently in preview mode.
	 * @param monitor
	 *            This will be used as the progress monitor for the generation.
	 */
	public AcceleoEnvironmentFactory(File generationRoot, Module module,
			List<IAcceleoTextGenerationListener> listeners, List<Properties> props, boolean preview,
			Monitor monitor) {
		super(EPackage.Registry.INSTANCE);
		context = new AcceleoEvaluationContext(generationRoot, listeners, preview, monitor);
		properties.addAll(props);
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

		AcceleoEnvironment result = new AcceleoEnvironment(getEPackageRegistry());
		result.setFactory(this);
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.ecore.EcoreEnvironmentFactory#createEvaluationEnvironment()
	 */
	@Override
	public EvaluationEnvironment<EClassifier, EOperation, EStructuralFeature, EClass, EObject> createEvaluationEnvironment() {
		return new AcceleoEvaluationEnvironment(module, properties);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ocl.ecore.EcoreEnvironmentFactory#createEvaluationEnvironment(org.eclipse.ocl.EvaluationEnvironment)
	 */
	@Override
	public EvaluationEnvironment<EClassifier, EOperation, EStructuralFeature, EClass, EObject> createEvaluationEnvironment(
			EvaluationEnvironment<EClassifier, EOperation, EStructuralFeature, EClass, EObject> parent) {
		return new AcceleoEvaluationEnvironment(parent, module, properties);
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
	 * This can be used to dispose of all resources loaded from this factory.
	 */
	public void dispose() {
		context.dispose();
		properties.clear();
	}

	/**
	 * Returns the preview of the generation handled by this factory's generation context.
	 * 
	 * @return The preview of the generation handled by this factory's generation context.
	 */
	public Map<String, Writer> getEvaluationPreview() {
		return context.getGenerationPreview();
	}
}
