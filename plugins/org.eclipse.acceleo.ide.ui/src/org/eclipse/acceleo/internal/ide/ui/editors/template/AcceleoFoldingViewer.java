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
package org.eclipse.acceleo.internal.ide.ui.editors.template;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoPartitionScanner;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;

/**
 * The folding viewer, used to show the folded code.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class AcceleoFoldingViewer extends SourceViewer {
	/**
	 * The input (a String or a Documentation).
	 */
	private Object input;

	/**
	 * The document.
	 */
	private IDocument document;

	/**
	 * The constructor.
	 * 
	 * @param parent
	 *            The composite parent
	 * @param styles
	 *            The style
	 */
	public AcceleoFoldingViewer(final Composite parent, final int styles) {
		super(parent, null, styles);
		this.setEditable(false);

		// We want a yellow pop up like the Javadoc one
		Color background = parent.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND);
		Color foreground = parent.getDisplay().getSystemColor(SWT.COLOR_INFO_FOREGROUND);
		this.getTextWidget().setBackground(background);
		this.getTextWidget().setForeground(foreground);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.source.SourceViewer#createControl(org.eclipse.swt.widgets.Composite, int)
	 */
	@Override
	protected void createControl(Composite parent, int styles) {
		super.createControl(parent, styles);

		this.configure(new AcceleoConfiguration(AcceleoUIActivator.getDefault().getPreferenceStore()));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.TextViewer#setInput(java.lang.Object)
	 */
	@Override
	public void setInput(final Object editorInput) {
		this.input = editorInput;
		this.createDocumentFromInput();
	}

	/**
	 * Uses the input to create the document.
	 */
	private void createDocumentFromInput() {
		String text = null;
		if (input instanceof String) {
			text = (String)input;
		}

		this.document = new Document(text);
		IDocumentPartitioner partitioner = new FastPartitioner(new AcceleoPartitionScanner(),
				AcceleoPartitionScanner.LEGAL_CONTENT_TYPES);
		partitioner.connect(document);
		document.setDocumentPartitioner(partitioner);

		this.setDocument(this.document);
	}

	/**
	 * Returns true if the editor has a content, false otherwise.
	 * 
	 * @return true if the editor has a content, false otherwise.
	 */
	public boolean hasContent() {
		if (this.document != null) {
			return this.document.get().length() > 0;
		}
		return false;
	}
}
