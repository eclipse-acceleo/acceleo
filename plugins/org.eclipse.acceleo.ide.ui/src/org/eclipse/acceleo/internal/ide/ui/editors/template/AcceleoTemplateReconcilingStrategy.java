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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.projection.ProjectionAnnotation;
import org.eclipse.swt.widgets.Display;

/**
 * This reconciling strategy will allow us to enable folding support in the Acceleo Template editor.
 * <p>
 * Folding rules are as follows :
 * <ol>
 * <li>All items stated below as &quot;foldable&quot; will effectively be if they span more than 2 lines.</li>
 * <li>Comments located before the module statement are considered foldable.</li>
 * <li>The module block itself is foldable.</li>
 * <li>Templates, Macros and Queries are foldable.</li>
 * <li>Within a module, comments located outside of Templates, Macros and Queries are foldable.</li>
 * </ol>
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public final class AcceleoTemplateReconcilingStrategy implements IReconcilingStrategy, IReconcilingStrategyExtension {
	/** This will be matched against to determine if we are on a comment statement. */
	private static final String COMMENT_STATEMENT_NAME = "comment"; //$NON-NLS-1$

	/** This will be matched against to determine if we are on a macro statement. */
	private static final String MACRO_STATEMENT_NAME = "macro"; //$NON-NLS-1$

	/** This will be matched against to determine if we are on a query statement. */
	private static final String QUERY_STATEMENT_NAME = "query"; //$NON-NLS-1$

	/** This will be matched against to determine if we are on a template statement. */
	private static final String TEMPLATE_STATEMENT_NAME = "template"; //$NON-NLS-1$

	/**
	 * This will hold the list of all annotations that have been added since the last reconciling.
	 */
	protected final Map<Annotation, Position> addedAnnotations = new HashMap<Annotation, Position>();

	/** Current known positions of foldable block. */
	protected final Map<Annotation, Position> currentAnnotations = new HashMap<Annotation, Position>();

	/**
	 * This will hold the list of all annotations that have been removed since the last reconciling.
	 */
	protected final List<Annotation> deletedAnnotations = new ArrayList<Annotation>();

	/** Editor this provides folding support to. */
	protected final AcceleoEditor editor;

	/**
	 * This will hold the list of all annotations that have been modified since the last reconciling.
	 */
	protected final Map<Annotation, Position> modifiedAnnotations = new HashMap<Annotation, Position>();

	/** The document we'll seek foldable blocks in. */
	private IDocument document;

	/** Current offset. */
	private int offset;

	/** This will inform us that the search for the tail of a given element should look for "/]". */
	private boolean seekSelfClosing;

	/** The statement start delimiter ('[' at current). */
	private char statementStart = '[';

	/**
	 * Instantiates the reconciling strategy for a given editor.
	 * 
	 * @param editor
	 *            Editor which we seek to provide folding support for.
	 */
	public AcceleoTemplateReconcilingStrategy(AcceleoEditor editor) {
		this.editor = editor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension#initialReconcile()
	 */
	public void initialReconcile() {
		offset = 0;
		computePositions();
		updateFoldingStructure();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.reconciler.IReconcilingStrategy#reconcile(org.eclipse.jface.text.reconciler.DirtyRegion,
	 *      org.eclipse.jface.text.IRegion)
	 */
	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {
		reconcile(subRegion);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.reconciler.IReconcilingStrategy#reconcile(org.eclipse.jface.text.IRegion)
	 */
	public void reconcile(IRegion partition) {
		offset = partition.getOffset();
		computePositions();
		updateFoldingStructure();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.reconciler.IReconcilingStrategy#setDocument(org.eclipse.jface.text.IDocument)
	 */
	public void setDocument(IDocument document) {
		this.document = document;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.reconciler.IReconcilingStrategyExtension#setProgressMonitor(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void setProgressMonitor(IProgressMonitor monitor) {
		// none
	}

	/**
	 * This will compute the current block positions. The offset at which computations start is determined by
	 * {@link #offset}.
	 */
	private void computePositions() {
		deletedAnnotations.clear();
		modifiedAnnotations.clear();
		addedAnnotations.clear();
		deletedAnnotations.addAll(currentAnnotations.keySet());
		for (Map.Entry<Annotation, Position> entry : currentAnnotations.entrySet()) {
			final Position position = entry.getValue();
			if (position.getOffset() + position.getLength() < offset) {
				deletedAnnotations.remove(entry.getKey());
			}
		}
		try {
			boolean eof = seekStatementStart();
			int startOffset = offset;
			while (!eof) {
				// set offset after the block start delimiter.
				offset += 1;

				// See computations rules in this class' javadoc
				if (COMMENT_STATEMENT_NAME.equals(document.get(offset, COMMENT_STATEMENT_NAME.length()))) {
					eof = seekCommentEnd();
					final int endOffset = offset++;
					if (document.getNumberOfLines(startOffset, endOffset - startOffset) > 2) {
						createOrUpdateAnnotation(startOffset, endOffset - startOffset, false);
					}
				} else {
					String moduleElement = null;
					if (MACRO_STATEMENT_NAME.equals(document.get(offset, MACRO_STATEMENT_NAME.length()))) {
						moduleElement = MACRO_STATEMENT_NAME;
					} else if (TEMPLATE_STATEMENT_NAME.equals(document.get(offset, TEMPLATE_STATEMENT_NAME
							.length()))) {
						moduleElement = TEMPLATE_STATEMENT_NAME;
					} else if (QUERY_STATEMENT_NAME.equals(document
							.get(offset, QUERY_STATEMENT_NAME.length()))) {
						moduleElement = QUERY_STATEMENT_NAME;
					}
					if (moduleElement != null) {
						eof = seekModuleElementEnd(moduleElement);
						alignModuleElementEnd(moduleElement);
						if (document.getNumberOfLines(startOffset, offset - startOffset) > 2) {
							createOrUpdateAnnotation(startOffset, offset - startOffset, false);
						}
					}
				}

				eof = seekStatementStart();
				startOffset = offset;
			}
		} catch (BadLocationException e) {
			// Nothing to do
		}
		for (Annotation deleted : deletedAnnotations) {
			currentAnnotations.remove(deleted);
		}
	}

	/**
	 * Align the module element end for the hover pop up of the folded code.
	 * 
	 * @param moduleElement
	 *            The module element.
	 * @throws BadLocationException
	 *             In case of problems.
	 */
	private void alignModuleElementEnd(String moduleElement) throws BadLocationException {
		if (IAcceleoConstants.DEFAULT_END_BODY_CHAR.equals(String.valueOf(document.getChar(offset)))
				&& document.get().length() >= (offset + 1 + moduleElement.length() + 1)) {
			String next = document.get().substring(offset, offset + 1 + moduleElement.length() + 1);
			String nextToFind = IAcceleoConstants.DEFAULT_END_BODY_CHAR + moduleElement
					+ IAcceleoConstants.DEFAULT_END;
			if (nextToFind.equals(next)) {
				offset = offset + 1 + moduleElement.length() + 1;
			}
		}
	}

	/**
	 * This will update lists {@link #deletedAnnotations}, {@link #addedAnnotations} and
	 * {@link #modifiedAnnotations} according to the given values.
	 * 
	 * @param newOffset
	 *            Offset of the text we want the annotation updated of.
	 * @param newLength
	 *            Length of the text we want the annotation updated of.
	 * @param initiallyCollapsed
	 *            Indicates that the created annotation should be folded from start.
	 * @throws BadLocationException
	 *             Thrown if we try and get an out of range character. Should not happen.
	 */
	private void createOrUpdateAnnotation(int newOffset, int newLength, boolean initiallyCollapsed)
			throws BadLocationException {
		boolean createAnnotation = true;
		final Map<Annotation, Position> copy = new HashMap<Annotation, Position>(currentAnnotations);
		final String text = document.get(newOffset, newLength);
		for (Map.Entry<Annotation, Position> entry : copy.entrySet()) {
			if (entry.getKey().getText().equals(text)) {
				createAnnotation = false;
				final Position oldPosition = entry.getValue();
				if (oldPosition.getOffset() != newOffset || oldPosition.getLength() != newLength) {
					final Position newPosition = new Position(newOffset, newLength);
					modifiedAnnotations.put(entry.getKey(), newPosition);
					currentAnnotations.put(entry.getKey(), newPosition);
				}
				deletedAnnotations.remove(entry.getKey());
				break;
			}
		}
		if (createAnnotation) {
			final Annotation annotation = new ProjectionAnnotation(initiallyCollapsed);
			annotation.setText(text);
			final Position position = new Position(newOffset, newLength);
			currentAnnotations.put(annotation, position);
			addedAnnotations.put(annotation, position);
		}
	}

	/**
	 * Eats all chars away till we find either the "<code>[/comment</code>" of the "<code>/]</code>" String.
	 * 
	 * @return <code>true</code> if the end of file has been reached. <code>false</code> otherwise.
	 * @throws BadLocationException
	 *             Thrown if we try and get an out of range character. Should not happen.
	 */
	private boolean seekCommentEnd() throws BadLocationException {
		char next = document.getChar(offset);
		boolean eof = offset + 1 >= document.getLength();
		while (!eof && next != statementStart && (next != ']' || document.getChar(offset - 1) != '/')) {
			offset++;
			eof = offset + 1 >= document.getLength();
			next = document.getChar(offset);
			if (seekSelfClosing && next == ']' && document.getChar(offset - 1) != '/') {
				seekSelfClosing = false;
			}
		}
		/*
		 * We can be in either one of three cases here 1) eof, don't go any further and return it 2) we're at
		 * the offset preceding a '[' character 3) we're at the offset preceding a ']' character which is
		 * itself preceded by '/'
		 */
		boolean isSelfClosing = !eof && seekSelfClosing && next == ']' && document.getChar(offset - 1) == '/';
		boolean isCommentEnd = !isSelfClosing && !eof && '/' != document.getChar(offset + 1)
				&& COMMENT_STATEMENT_NAME != document.get(offset + 2, COMMENT_STATEMENT_NAME.length());
		if (!eof && !isSelfClosing && !isCommentEnd) {
			offset++;
			seekCommentEnd();
		}
		// reset state
		seekSelfClosing = true;
		return eof;
	}

	/**
	 * Eats characters away until we find the end of a module element.
	 * 
	 * @param moduleElement
	 *            Name of the module element we seek the end of.
	 * @return <code>true</code> if the end of file has been reached. <code>false</code> otherwise.
	 * @throws BadLocationException
	 *             Thrown if we try and get an out of range character. Should not happen.
	 */
	private boolean seekModuleElementEnd(String moduleElement) throws BadLocationException {
		boolean eof = offset + moduleElement.length() + 2 >= document.getLength();
		char next = document.getChar(offset);
		while (!eof && next != statementStart) {
			eof = offset + moduleElement.length() + 2 >= document.getLength();
			next = document.getChar(offset++);
		}
		if ('/' != document.getChar(offset)
				|| !moduleElement.equals(document.get(offset + 1, moduleElement.length()))) {
			offset++;
			seekModuleElementEnd(moduleElement);
		}
		eof = offset + moduleElement.length() + 2 >= document.getLength();
		return eof;
	}

	/**
	 * Eats chars away till we find a {@link #statementStart} char.
	 * 
	 * @return <code>true</code> if the end of file has been reached. <code>false</code> otherwise.
	 * @throws BadLocationException
	 *             Thrown if we try and get an out of range character. Should not happen.
	 */
	private boolean seekStatementStart() throws BadLocationException {
		char next = document.getChar(offset);
		boolean eof = offset + 1 >= document.getLength();
		while (!eof && next != statementStart) {
			offset++;
			next = document.getChar(offset);
			eof = offset + 1 == document.getLength();
		}
		return eof;
	}

	/**
	 * Updates the editor's folding structure.
	 */
	private void updateFoldingStructure() {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				editor.updateFoldingStructure(addedAnnotations, deletedAnnotations, modifiedAnnotations);
			}
		});
	}
}
