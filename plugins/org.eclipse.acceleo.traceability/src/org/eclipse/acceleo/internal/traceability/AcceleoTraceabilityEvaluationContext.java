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
package org.eclipse.acceleo.internal.traceability;

import java.io.File;
import java.util.List;

import org.eclipse.acceleo.engine.event.AcceleoTextGenerationEvent;
import org.eclipse.acceleo.engine.event.IAcceleoTextGenerationListener;
import org.eclipse.acceleo.engine.generation.strategy.IAcceleoGenerationStrategy;
import org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationContext;
import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.acceleo.traceability.GeneratedFile;
import org.eclipse.acceleo.traceability.TraceabilityModel;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * This specific implementation of the evaluation context will hold a reference to the traceability model so
 * as to be able to send traceability information along with the event when a file is generated.
 * 
 * @param <C>
 *            This should be EClassifier for ecore, Class for UML.
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoTraceabilityEvaluationContext<C> extends AcceleoEvaluationContext<C> {
	/** Trace of the evaluation. */
	private final TraceabilityModel evaluationTrace;

	/**
	 * Instantiates an evaluation context given the root of the to-be-generated files. A reference to the
	 * evaluation trace will be passed along to this constructor.
	 * 
	 * @param root
	 *            Root of all files that will be generated.
	 * @param listeners
	 *            The list of all listeners that are to be notified for text generation from this context.
	 * @param generationStrategy
	 *            The generation strategy that's to be used by this context.
	 * @param monitor
	 *            This will be used as the progress monitor for the generation.
	 * @param trace
	 *            Trace of the current evaluation.
	 */
	public AcceleoTraceabilityEvaluationContext(File root, List<IAcceleoTextGenerationListener> listeners,
			IAcceleoGenerationStrategy generationStrategy, Monitor monitor, TraceabilityModel trace) {
		super(root, listeners, generationStrategy, monitor);
		evaluationTrace = trace;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.evaluation.AcceleoEvaluationContext#fireFileGenerated(java.lang.String,
	 *      org.eclipse.acceleo.model.mtl.Block, org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected void fireFileGenerated(String filePath, Block fileBlock, EObject source) {
		GeneratedFile file = evaluationTrace.getGeneratedFile(filePath);
		if (file != null) {
			AcceleoTextGenerationEvent event = new AcceleoTextGenerationEvent(filePath, fileBlock, source,
					file);
			for (IAcceleoTextGenerationListener listener : listeners) {
				if (!notifyOnGenerationEnd) {
					EcoreUtil.remove(file);
				}
				listener.fileGenerated(event);
			}
		}
	}
}
