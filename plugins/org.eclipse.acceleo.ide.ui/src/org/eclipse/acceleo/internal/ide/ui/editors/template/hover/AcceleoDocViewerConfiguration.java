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
package org.eclipse.acceleo.internal.ide.ui.editors.template.hover;

import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AbstractAcceleoScanner;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

/**
 * The source viewer configuration of the AcceleoDoc.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoDocViewerConfiguration extends SourceViewerConfiguration {
	/**
	 * The constructor.
	 */
	public AcceleoDocViewerConfiguration() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getConfiguredContentTypes(org.eclipse.jface.text.source.ISourceViewer)
	 */
	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		AbstractAcceleoScanner[] acceleoScanners = getScanners();
		String[] result = new String[acceleoScanners.length];
		for (int i = 0; i < acceleoScanners.length; i++) {
			AbstractAcceleoScanner scanner = acceleoScanners[i];
			result[i] = scanner.getConfiguredContentType();
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getPresentationReconciler(org.eclipse.jface.text.source.ISourceViewer)
	 */
	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();
		AbstractAcceleoScanner[] acceleoScanners = getScanners();
		for (int i = 0; i < acceleoScanners.length; i++) {
			AbstractAcceleoScanner scanner = acceleoScanners[i];
			DefaultDamagerRepairer dr = new DefaultDamagerRepairer(scanner);
			reconciler.setDamager(dr, scanner.getConfiguredContentType());
			reconciler.setRepairer(dr, scanner.getConfiguredContentType());
		}
		return reconciler;
	}

	/**
	 * Returns all the partition scanner.
	 * 
	 * @return All the partition scanner
	 */
	private AbstractAcceleoScanner[] getScanners() {
		AbstractAcceleoScanner[] array = new AbstractAcceleoScanner[3];
		array[0] = new AcceleoDocDefaultScanner();
		array[1] = new AcceleoDocBoldScanner();
		array[2] = new AcceleoDocTitleScanner();
		return array;
	}
}
