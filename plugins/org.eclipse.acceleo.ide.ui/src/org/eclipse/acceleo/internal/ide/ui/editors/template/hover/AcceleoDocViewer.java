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

import java.io.IOException;
import java.io.StringReader;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.AcceleoUIDocumentationUtils;
import org.eclipse.acceleo.model.mtl.Documentation;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;

/**
 * The AcceleoDocViewer is used to view the documentation in the AcceleoDoc view and in the AcceleoDoc hover
 * pop up.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoDocViewer extends SourceViewer {

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
	public AcceleoDocViewer(final Composite parent, final int styles) {
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
	 * @see org.eclipse.jface.text.TextViewer#setInput(java.lang.Object)
	 */
	@Override
	public void setInput(final Object editorInput) {
		this.input = editorInput;
		this.createDocumentFromInput();
	}

	/**
	 * Returns true if the input is a documentation.
	 * 
	 * @return true if the input is a documentation
	 */
	public boolean inputIsDocumentation() {
		return this.input instanceof Documentation;
	}

	/**
	 * Uses the input to create the document.
	 */
	private void createDocumentFromInput() {
		String text = null;
		if (input instanceof Documentation) {
			text = AcceleoUIDocumentationUtils.getTextFrom((Documentation)input);
		} else if (input instanceof EOperation) {
			text = AcceleoUIDocumentationUtils.getTextFrom((EOperation)input);
		} else if (input instanceof EObject) {
			text = AcceleoUIDocumentationUtils.getSignatureFrom((EObject)input);
		} else if (input instanceof String) {
			text = (String)input;
		}

		TextPresentation textPresentation = new TextPresentation();
		HTML2TextReader reader = new HTML2TextReader(new StringReader(text), textPresentation);
		try {
			text = reader.getString();
		} catch (IOException e) {
			AcceleoUIActivator.getDefault().getLog().log(
					new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
			text = ""; //$NON-NLS-1$
		}

		this.document = new Document(text);
		IDocumentPartitioner partitioner = new FastPartitioner(new AcceleoDocPartitionScanner(),
				AcceleoDocPartitionScanner.TYPES);
		partitioner.connect(this.document);
		this.document.setDocumentPartitioner(partitioner);
		this.setDocument(this.document);
		TextPresentation.applyTextPresentation(textPresentation, this.getTextWidget());
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

	/**
	 * Returns the text of the document, or null if there is no document.
	 * 
	 * @return the text of the document, or null if there is no document.
	 */
	public String getText() {
		if (this.document != null) {
			return this.document.get();
		}
		return null;
	}

	/**
	 * Returns the input if it is a Documentation, null otherwise.
	 * 
	 * @return the input if it is a Documentation, null otherwise.
	 */
	public Documentation getDocumentation() {
		if (this.inputIsDocumentation()) {
			return (Documentation)this.input;
		}
		return null;
	}

}
