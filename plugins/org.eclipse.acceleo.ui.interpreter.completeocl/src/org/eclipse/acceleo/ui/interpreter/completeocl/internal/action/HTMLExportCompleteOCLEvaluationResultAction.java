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

import org.eclipse.acceleo.ui.interpreter.completeocl.HTMLExporter;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ocl.examples.pivot.manager.MetaModelManager;
import org.eclipse.xtext.resource.XtextResource;

/**
 * The HTML exporter.
 * 
 * @author <a href="mailto:marwa.rostren@obeo.fr">Marwa Rostren</a>
 */
public class HTMLExportCompleteOCLEvaluationResultAction extends AbstractExportCompleteOCLEvaluationResultAction {

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
	public HTMLExportCompleteOCLEvaluationResultAction(XtextResource resource, Resource target,
			MetaModelManager metaModelManager) {
		super("HTML EXPORT", resource, target, metaModelManager, new HTMLExporter());
	}
}
