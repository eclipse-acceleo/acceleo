/*******************************************************************************
 * Copyright (c) 2010, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.internal.action;

import java.util.Collections;
import java.util.List;

import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.generation.strategy.PreviewStrategy;
import org.eclipse.acceleo.engine.internal.environment.AcceleoEnvironmentFactory;
import org.eclipse.acceleo.engine.internal.environment.AcceleoPropertiesLookup;
import org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationVisitor;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.ui.interpreter.internal.view.AcceleoInterpreterView;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EParameter;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.action.Action;
import org.eclipse.ocl.ecore.CallOperationAction;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.ecore.SendSignalAction;

/**
 * This action will be called by the "ctrl+shift+d" shortcut on a text selection in the "Acceleo Interpreter"
 * view. It will be used to parse, compile and evaluate the selected Acceleo expression.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class EvaluateAction extends Action {
	/** References the interpreter from which the action was triggered. */
	private AcceleoInterpreterView interpreterView;

	/**
	 * Initializes the evaluation action given the interpreter from which it was triggered.
	 * 
	 * @param view
	 *            The interpreter view from which this evaluation was triggered.
	 */
	public void initialize(AcceleoInterpreterView view) {
		interpreterView = view;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		// FIXME check for parsing problems
		final OCLExpression expression = interpreterView.getExpression();
		List<EObject> target = interpreterView.getTargetObjects();
		if (target == null) {
			target = Collections.emptyList();
		}

		if (expression == null) {
			return;
		}

		final Module module = (Module)EcoreUtil.getRootContainer(expression);
		// We won't have listeners or property files here
		List<IAcceleoTextGenerationListener> listeners = Collections.emptyList();
		AcceleoEnvironmentFactory factory = new AcceleoEnvironmentFactory(null, module, listeners,
				new AcceleoPropertiesLookup(), new PreviewStrategy(), new BasicMonitor());
		OCL ocl = OCL.newInstance(factory);
		AcceleoEvaluationVisitor<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject> evaluationVisitor = (AcceleoEvaluationVisitor<EPackage, EClassifier, EOperation, EStructuralFeature, EEnumLiteral, EParameter, EObject, CallOperationAction, SendSignalAction, Constraint, EClass, EObject>)factory
				.createEvaluationVisitor(ocl.getEnvironment(), ocl.getEvaluationEnvironment(),
						ocl.getExtentMap());

		Object actualArgument = target;
		if (target.size() == 1) {
			actualArgument = target.get(0);
		}
		ocl.getEvaluationEnvironment().add("self", actualArgument); //$NON-NLS-1$
		ocl.getEvaluationEnvironment().add("model", interpreterView.getTargetRoot()); //$NON-NLS-1$

		Object result = evaluationVisitor.visitExpression(expression);
		interpreterView.displayResult(result.toString());
	}
}
