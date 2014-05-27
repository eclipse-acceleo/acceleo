/*******************************************************************************
 * Copyright (c) 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.completeocl;

import java.io.IOException;

import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * This interface defines the general contract of the interpreter evaluation results export mechanism.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public interface IEvaluationExporter {
	/**
	 * Exports the interpreter results.
	 * 
	 * @param outputPath
	 *            the output target file path.
	 * @param resultRoot
	 *            The root of the evaluation model.
	 * @param monitor
	 *            the monitor.
	 * @throws IOException
	 *             threw when an IO exception occurs.
	 * @throws CoreException
	 *             threw when a Core exception occurs.
	 */
	public void export(String outputPath, OCLElement resultRoot, IProgressMonitor monitor)
			throws IOException, CoreException;
}
