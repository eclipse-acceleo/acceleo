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
package org.eclipse.acceleo.ui.interpreter.completeocl.internal.action;

import java.io.IOException;
import java.util.HashMap;

import org.eclipse.acceleo.ui.interpreter.completeocl.IEvaluationExporter;
import org.eclipse.acceleo.ui.interpreter.completeocl.evaluationresult.OCLElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.xtext.resource.XtextResource;

/**
 * The Model exporter.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class ModelExportCompleteOCLEvaluationResultAction extends AbstractExportCompleteOCLEvaluationResultAction {

	/**
	 * Constructor.
	 * 
	 * @param resource
	 *            the xtext resource to evaluate.
	 * @param target
	 *            the target resource.
	 * @param metaModelManager
	 *            the meta model manager.
	 */
	public ModelExportCompleteOCLEvaluationResultAction(XtextResource resource, Resource target,
			MetaModelManager metaModelManager) {
		super("Model EXPORT", resource, target, metaModelManager, new IEvaluationExporter() {
			public void export(String outputPath, OCLElement resultRoot, IProgressMonitor monitor) {
				Resource res = new XMIResourceImpl(URI.createFileURI(outputPath));
				res.getContents().add(resultRoot);
				try {
					res.save(new HashMap<String, Object>());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
