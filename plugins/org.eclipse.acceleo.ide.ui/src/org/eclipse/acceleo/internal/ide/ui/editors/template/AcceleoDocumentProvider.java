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

import org.eclipse.acceleo.internal.ide.ui.editors.template.outline.AcceleoOutlinePage;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoPartitionScanner;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.IPositionUpdater;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

/**
 * Shared document provider for the Acceleo editor.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoDocumentProvider extends FileDocumentProvider {

	/**
	 * The editor.
	 */
	private AcceleoEditor editor;

	/**
	 * A new position updater. A position updater is responsible for adapting document positions. It is called
	 * when a modification has been detected in the document.
	 */
	private IPositionUpdater positionUpdater;

	/**
	 * Constructor.
	 * 
	 * @param editor
	 *            is the editor
	 */
	public AcceleoDocumentProvider(AcceleoEditor editor) {
		super();
		this.editor = editor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.editors.text.StorageDocumentProvider#createDocument(java.lang.Object)
	 */
	@Override
	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.createDocument(element);
		if (document != null) {
			IDocumentPartitioner partitioner = new FastPartitioner(new AcceleoPartitionScanner(),
					AcceleoPartitionScanner.LEGAL_CONTENT_TYPES);
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
			if (positionUpdater == null) {
				positionUpdater = new IPositionUpdater() {
					public void update(DocumentEvent event) {
						handlePositionUpdate(event.getOffset(), event.getOffset() + event.getLength(), event
								.getText());
					}
				};
				document.addPositionUpdater(positionUpdater);
			}
		}
		return document;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.editors.text.StorageDocumentProvider#createEmptyDocument()
	 */
	@Override
	protected IDocument createEmptyDocument() {
		return new AcceleoDocument();
	}

	/**
	 * A position updater is responsible for adapting document positions. This method is called by a position
	 * updater. A modification has been detected in the template text, at the specified start, until the
	 * character at index end - 1 or to the end of the sequence if no such character exists.
	 * 
	 * @param posBegin
	 *            the beginning index, inclusive
	 * @param posEnd
	 *            the ending index, exclusive
	 * @param newText
	 *            the string that will replace previous contents
	 */
	protected void handlePositionUpdate(int posBegin, int posEnd, String newText) {
		if (editor.getContent() != null) {
			EObject cstNode = editor.getContent().updateCST(posBegin, posEnd, newText);
			if (cstNode != null) {
				AcceleoOutlinePage outline = editor.getContentOutlinePage();
				if (outline.getControl() != null && !outline.getControl().isDisposed()) {
					outline.refresh(cstNode);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.texteditor.AbstractDocumentProvider#disconnected()
	 */
	@Override
	protected void disconnected() {
		super.disconnected();
		if (positionUpdater != null && editor.getEditorInput() != null) {
			IDocument document = getDocument(editor.getEditorInput());
			if (document != null) {
				document.removePositionUpdater(positionUpdater);
			}
		}
	}

}
