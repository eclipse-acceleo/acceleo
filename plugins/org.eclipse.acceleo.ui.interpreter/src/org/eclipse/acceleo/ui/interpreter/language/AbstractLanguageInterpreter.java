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
package org.eclipse.acceleo.ui.interpreter.language;

import java.util.List;
import java.util.concurrent.Future;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

/**
 * This class describes the necessary contract for a language to be considered usable in the interpreter view.
 * <p>
 * A Language Interpreter will need to be able to provide the necessary tooling for the language edition
 * (completion, syntax highlighting...), a parser that can check for syntax errors and return them for
 * display, and an evaluation engine that can be called on given EObjects for a given expression.
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
@SuppressWarnings("unused")
public abstract class AbstractLanguageInterpreter {
	/**
	 * If editing this language needs a custom implementation of a SourceViewer, this method can be used to
	 * instantiate it.
	 * 
	 * @param parent
	 *            The parent composite for this source viewer.
	 * @return The source viewer that is to be used for this language. Can be <code>null</code>, in which case
	 *         a default {@link SourceViewer} will be instantiated.
	 */
	public SourceViewer createSourceViewer(Composite parent) {
		return null;
	}

	/**
	 * If this language interpreter requires a specific form for the "result" viewer, this method can be used
	 * to instantiate it.
	 * 
	 * @param parent
	 *            The parent composite for this result viewer.
	 * @return The viewer that is to be used to display the result of this language's evaluations. Can be
	 *         <code>null</code>, in which case a default {@link TreeViewer} will be instantiated.
	 */
	public Viewer createResultViewer(Composite parent) {
		return null;
	}

	/**
	 * This can be used to configure the source viewer to fit the language needs. Document scanner, document
	 * partitioner, source viewer configuration... can be set accordingly.
	 * 
	 * @param viewer
	 *            The viewer that will be displayed to the user for the edition of expressions in this
	 *            language.
	 */
	public void configureSourceViewer(SourceViewer viewer) {
		// Do nothing
	}

	/**
	 * This will be called each time the selection of EObjects in the workspace is updated so that the
	 * language interpreter can react to the change. This list correspond to the EObjects for which the viewer
	 * should provide completion proposals and against which the evaluation will be run.
	 * 
	 * @param targets
	 *            The list of target EObjects.
	 */
	public void setTargetEObject(List<EObject> targets) {
		// Do nothing
	}

	/**
	 * This will be called when the interpreter view needs the current expression to be compiled. It will be
	 * called whenever the evaluation is needed : explicit call to the evaluate action, real time
	 * evaluation...
	 * <p>
	 * The returned task must be cancellable; real time compilations might not be run to the end before the
	 * user changes the expression. Do note that this task must be thread-safe as it will be called
	 * asynchronously and we do not guarantee that each task will be canceled before the subsequent is
	 * started.
	 * </p>
	 * <p>
	 * The IStatus returned by the compilation task will be treated as a MultiStatus representing all of the
	 * problems that have been encountered while trying to compile the expression.
	 * </p>
	 * 
	 * @return Cancellable task that can be run by the interpreter view to know whether the expression is
	 *         well-formed. Can be <code>null</code>.
	 */
	public Future<IStatus> getCompilationTask() {
		return null;
	}

	/**
	 * This will be called when the user requests that his expression be evaluated against the currently
	 * selected EObjects. It will be called whenever the evaluation is needed : explicit call to the evaluate
	 * action, real time evaluation...
	 * <p>
	 * The returned task must be cancellable; real time evaluations might not be run to the end before the
	 * user changes the expression. Do note that this task must be thread-safe as it will be called
	 * asynchronously and we do not guarantee that each task will be canceled before the subsequent is
	 * started.
	 * </p>
	 * <p>
	 * The list of Objects returned by the evaluation task will be considered as the results to be displayed
	 * to the user. Runtime exceptions can be logged in the error log.
	 * </p>
	 * 
	 * @return Cancellable task that can be run by the interpreter view to compute the results of a given
	 *         evaluation. Cannot be <code>null</code>.
	 */
	public abstract Future<List<Object>> getEvaluationTask();
}
