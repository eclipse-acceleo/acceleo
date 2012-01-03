/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.traceability.environment;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.engine.internal.environment.AcceleoEnvironmentFactory;
import org.eclipse.acceleo.engine.internal.environment.AcceleoPropertiesLookup;
import org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitor;
import org.eclipse.acceleo.internal.traceability.AcceleoTraceabilityEvaluationContext;
import org.eclipse.acceleo.internal.traceability.engine.AcceleoTraceabilityVisitor;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.traceability.TraceabilityModel;
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
 * This implementation of an environment factory will create traceability-enabled visitors.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoTraceabilityEnvironmentFactory extends AcceleoEnvironmentFactory {
	/** All traceability information for this session will be saved in this instance. */
	private final TraceabilityModel evaluationTrace;

	/**
	 * Default constructor. Packages will be looked up into the global EMF registry.
	 * 
	 * @param generationRoot
	 *            Root of all files that will be generated.
	 * @param module
	 *            The module for which this factory is to be created.
	 * @param listeners
	 *            The list of all listeners that are to be notified for text generation from this context.
	 * @param propertiesLookup
	 *            The class allowing for properties lookup for this generation.
	 * @param strategy
	 *            The generation strategy that's to be used by this factory's context.
	 * @param monitor
	 *            This will be used as the progress monitor for the generation.
	 * @param trace
	 *            Model in which evaluation traces are to be recorded.
	 */
	public AcceleoTraceabilityEnvironmentFactory(File generationRoot, Module module,
			List<IAcceleoTextGenerationListener> listeners, AcceleoPropertiesLookup propertiesLookup,
			IAcceleoGenerationStrategy strategy, Monitor monitor, TraceabilityModel trace) {
		super(generationRoot, module, listeners, propertiesLookup, strategy, monitor);
		evaluationTrace = trace;
		context = new AcceleoTraceabilityEvaluationContext<EClassifier>(generationRoot, listeners, strategy,
				monitor, evaluationTrace);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.environment.AcceleoEnvironmentFactory#createEvaluationVisitor(org.eclipse.ocl.Environment,
	 *      org.eclipse.ocl.EvaluationEnvironment, java.util.Map)
	 */
	@Override
	public EvaluationVisitor<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> createEvaluationVisitor(
			Environment<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> env,
			EvaluationEnvironment<EClassifier, EOperation, EStructuralFeature, EClass, EObject> evalEnv,
			Map<? extends EClass, ? extends Set<? extends EObject>> extentMap) {
		return new AcceleoTraceabilityVisitor<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject>(
				(AcceleoEvaluationVisitor<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject>)super
						.createEvaluationVisitor(env, evalEnv, extentMap), evaluationTrace);
	}
}
