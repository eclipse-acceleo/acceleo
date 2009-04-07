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
package org.eclipse.acceleo.engine.generation;

import java.io.File;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.engine.AcceleoEngineMessages;
import org.eclipse.acceleo.engine.AcceleoEvaluationException;
import org.eclipse.acceleo.engine.event.AcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.internal.environment.AcceleoEnvironment;
import org.eclipse.acceleo.engine.internal.environment.AcceleoEnvironmentFactory;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.model.mtl.VisibilityKind;
import org.eclipse.ocl.ecore.OCL;
import org.eclipse.ocl.ecore.Variable;

/**
 * This class can be used to launch the generation of an Acceleo template.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoGenericEngine implements IAcceleoEngine {
	/**
	 * This will hold the list of all listeners registered for notification on text generation from this
	 * engine.
	 */
	private final List<AcceleoTextGenerationListener> listeners = new ArrayList<AcceleoTextGenerationListener>(
			3);

	/** Holds a reference to the ocl instance. */
	private OCL ocl;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.IAcceleoEngine#addListener(org.eclipse.acceleo.engine.event.AcceleoTextGenerationListener)
	 */
	public void addListener(AcceleoTextGenerationListener listener) {
		listeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.IAcceleoEngine#evaluate(org.eclipse.acceleo.model.mtl.Template,
	 *      java.util.List, java.io.File, boolean)
	 */
	public Map<String, Writer> evaluate(Template template, List<? extends Object> arguments,
			File generationRoot, boolean preview) {
		if (template == null || arguments == null || (!preview && generationRoot == null)) {
			throw new NullPointerException(AcceleoEngineMessages.getString("AcceleoEngine.NullArguments")); //$NON-NLS-1$
		}
		if (template.getVisibility() != VisibilityKind.PUBLIC) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEngine.IllegalTemplateInvocation")); //$NON-NLS-1$
		}
		if (template.getParameter().size() != arguments.size()) {
			throw new AcceleoEvaluationException(AcceleoEngineMessages
					.getString("AcceleoEngine.IllegalArguments")); //$NON-NLS-1$
		}

		// We need to create an OCL instance for each generation since the environment factory is contextual
		AcceleoEnvironmentFactory factory = new AcceleoEnvironmentFactory(generationRoot, (Module)template
				.eContainer(), new ArrayList<AcceleoTextGenerationListener>(listeners), preview);
		ocl = OCL.newInstance(factory);
		((AcceleoEnvironment)ocl.getEnvironment()).restoreBrokenEnvironmentPackages(template.eResource());

		doEvaluate(template, arguments);

		Map<String, Writer> result = Collections.<String, Writer> emptyMap();
		if (preview) {
			result = factory.getEvaluationPreview();
		}
		factory.dispose();

		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.generation.IAcceleoEngine#removeListener(org.eclipse.acceleo.engine.event.AcceleoTextGenerationListener)
	 */
	public void removeListener(AcceleoTextGenerationListener listener) {
		listeners.remove(listener);
	}

	/**
	 * This does the actual work of template evaluation. It will be called from the public API methods exposed
	 * by the engine.
	 * 
	 * @param template
	 *            The template which is to be evaluated.
	 * @param arguments
	 *            These will be passed as the template arguments.
	 */
	private void doEvaluate(Template template, List<? extends Object> arguments) {
		// Guard Evaluation
		boolean guardValue = true;
		if (template.getGuard() != null) {
			final OCL.Query guard = ocl.createQuery(template.getGuard());
			// Sets all needed variables for the guard evaluation
			for (int i = 0; i < template.getParameter().size(); i++) {
				Variable param = template.getParameter().get(i);
				Object value = arguments.get(i);
				if (param.getType().isInstance(value)) {
					guard.getEvaluationEnvironment().add(param.getName(), value);
				} else {
					throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
							"AcceleoEngine.ArgumentMismatch", template.getName())); //$NON-NLS-1$
				}
				// [255379] also sets "self" variable to match the very first parameter
				if (i == 0) {
					guard.getEvaluationEnvironment().add("self", value); //$NON-NLS-1$
				}
			}
			guardValue = ((Boolean)guard.evaluate()).booleanValue();
		}

		// If there were no guard or its condition is verified, evaluate the template now.
		if (guardValue) {
			final OCL.Query query = ocl.createQuery(template);
			// Sets all needed variables for the template evaluation
			for (int i = 0; i < template.getParameter().size(); i++) {
				Variable param = template.getParameter().get(i);
				Object value = arguments.get(i);
				if (param.getType().isInstance(value)) {
					query.getEvaluationEnvironment().add(param.getName(), value);
				} else {
					throw new AcceleoEvaluationException(AcceleoEngineMessages.getString(
							"AcceleoEngine.ArgumentMismatch", template.getName())); //$NON-NLS-1$
				}
				// [255379] also sets "self" variable to match the very first parameter
				if (i == 0) {
					query.getEvaluationEnvironment().add("self", value); //$NON-NLS-1$
				}
			}
			query.evaluate();
		}
	}
}
