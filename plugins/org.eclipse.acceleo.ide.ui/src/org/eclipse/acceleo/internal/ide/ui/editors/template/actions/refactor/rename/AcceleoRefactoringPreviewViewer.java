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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.rename;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoConfiguration;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoPartitionScanner;
import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * This class define the viewer that compose the preview system during the refactoring.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoRefactoringPreviewViewer extends TextMergeViewer {

	/**
	 * The constructor.
	 * 
	 * @param parent
	 *            The parent.
	 * @param config
	 *            The configuration.
	 */
	public AcceleoRefactoringPreviewViewer(final Composite parent, final CompareConfiguration config) {
		super(parent, SWT.LEFT_TO_RIGHT, config);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.compare.contentmergeviewer.TextMergeViewer#configureTextViewer(org.eclipse.jface.text.TextViewer)
	 */
	@Override
	protected void configureTextViewer(TextViewer textViewer) {
		if (textViewer instanceof ISourceViewer) {
			((SourceViewer)textViewer).configure(new AcceleoConfiguration(AcceleoUIActivator.getDefault()
					.getPreferenceStore()));
		}
		textViewer.refresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.compare.contentmergeviewer.TextMergeViewer#getDocumentPartitioner()
	 */
	@Override
	protected IDocumentPartitioner getDocumentPartitioner() {
		IDocumentPartitioner partitioner = new FastPartitioner(new AcceleoPartitionScanner(),
				AcceleoPartitionScanner.LEGAL_CONTENT_TYPES);
		return partitioner;
	}

}
