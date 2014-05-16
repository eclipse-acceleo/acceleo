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
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.references.ReferenceEntry;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.references.ReferencesSearchQuery;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.references.ReferencesSearchResult;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.OpenDeclarationUtils;
import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.acceleo.model.mtl.IfBlock;
import org.eclipse.acceleo.model.mtl.LetBlock;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.ISynchronizable;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelExtension;
import org.eclipse.ocl.ecore.OCLExpression;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.search.ui.text.AbstractTextSearchResult;
import org.eclipse.search.ui.text.Match;

/**
 * Provides the ability to highlight all the occurrences of the current selection in the editor.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoOccurrencesFinderJob extends Job {

	/**
	 * The annotation type to highlight the occurrences in the acceleo editor. We use actually the
	 * 'org.eclipse.jdt.ui.occurrences' JDT annotation type because we would like the same colors. It would
	 * change in the future.
	 */
	public static final String FIND_OCCURENCES_ANNOTATION_TYPE = "org.eclipse.jdt.ui.occurrences"; //$NON-NLS-1$

	/**
	 * The acceleo editor.
	 */
	private AcceleoEditor editor;

	/**
	 * The search query.
	 */
	private ReferencesSearchQuery query;

	/**
	 * The constructor.
	 * 
	 * @param editor
	 *            The Acceleo editor.
	 * @param name
	 *            The name of the job
	 * @param query
	 *            The search query.
	 */
	public AcceleoOccurrencesFinderJob(final AcceleoEditor editor, final String name,
			final ReferencesSearchQuery query) {
		super(name);
		this.editor = editor;
		this.query = query;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	protected IStatus run(final IProgressMonitor monitor) {
		/*
		 * We capture the reference to the attributes as calls to Job cancellation or clear() from another
		 * thread could reset the instance scoped references.
		 */
		ReferencesSearchQuery capturedQuery = this.query;
		AcceleoEditor capturedEditor = this.editor;

		final ReferencesSearchResult result = (ReferencesSearchResult)capturedQuery.getSearchResult();
		/*
		 * Whatever happens we should never return a null status, see the javadoc.
		 */
		IStatus status = Status.OK_STATUS;
		final int origPriority = Thread.currentThread().getPriority();
		try {
			Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		} catch (SecurityException e) {
			// do nothing
		}

		try {
			status = capturedQuery.run(monitor);
		} finally {
			try {
				Thread.currentThread().setPriority(origPriority);
			} catch (SecurityException e) {
				// do nothing
			}
		}

		boolean shouldContinue = capturedEditor != null && capturedEditor.getDocumentProvider() != null
				&& capturedEditor.getEditorInput() != null;
		if (!monitor.isCanceled() && shouldContinue && capturedEditor != null) {
			final IAnnotationModel annotationModel = capturedEditor.getDocumentProvider().getAnnotationModel(
					capturedEditor.getEditorInput());
			if (annotationModel != null) {
				List<Match> matches = this.listOfTheOccurencesInTheCurrentFile(result);
				for (Match match : matches) {
					ReferenceEntry ref = null;
					if (match.getElement() instanceof ReferenceEntry) {
						ref = (ReferenceEntry)match.getElement();
					} else {
						continue;
					}

					if (monitor.isCanceled()) {
						status = Status.CANCEL_STATUS;
					}

					if (capturedEditor.getDocumentProvider() == null
							|| capturedEditor.getDocumentProvider().getAnnotationModel(
									capturedEditor.getEditorInput()) == null) {
						status = Status.CANCEL_STATUS;
					}

					if (status == Status.CANCEL_STATUS) {
						return status;
					}

					String description = ref.getMatch().eClass().getName();
					try {
						description = ref.getMatch().toString();
					} catch (UnsupportedOperationException e) {
						/*
						 * indeed there are cases where MDT-OCL will throw an UnsupportedOperationException at
						 * us when calling toString(). See
						 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=390083#c9
						 */
					}

					Map<Annotation, Position> annotations2positions = new HashMap<Annotation, Position>();
					for (Position position : this.computePositions(ref)) {
						// Existing marker of the JDT do not modify !
						if (position != null) {
							annotations2positions.put(new Annotation(FIND_OCCURENCES_ANNOTATION_TYPE, false,
									description), position);
						}
					}
					if (annotationModel instanceof IAnnotationModelExtension) {
						IAnnotationModelExtension annotationModelExtension = (IAnnotationModelExtension)annotationModel;
						synchronized(getLockObject(annotationModel)) {
							annotationModelExtension.replaceAnnotations(new Annotation[0],
									annotations2positions);
						}
					} else {
						Set<Entry<Annotation, Position>> entrySet = annotations2positions.entrySet();
						for (Entry<Annotation, Position> entry : entrySet) {
							annotationModel.addAnnotation(entry.getKey(), entry.getValue());
						}
					}
				}
			}
		}

		editor = null;
		query = null;
		return status;
	}

	/**
	 * Compute the regions to highlight from this reference entry.
	 * 
	 * @param ref
	 *            The reference entry
	 * @return The regions to highlight
	 */
	private List<Position> computePositions(ReferenceEntry ref) {
		List<Position> positions = new ArrayList<Position>();

		if (!(ref.getMatch() instanceof ASTNode)) {
			return positions;
		}
		ASTNode astNode = (ASTNode)ref.getMatch();

		if (!(astNode instanceof Block)) {
			positions.add(new Position(astNode.getStartPosition(), astNode.getEndPosition()
					- astNode.getStartPosition()));
		} else {
			Block block = (Block)astNode;
			if (block.getBody() != null && block.getBody().size() > 0) {
				OCLExpression startBody = block.getBody().get(0);
				if (!startBody.eIsProxy()
						|| (startBody.getStartPosition() >= block.getStartPosition() && startBody
								.getStartPosition() <= block.getEndPosition())) {
					Position startPosition = new Position(block.getStartPosition(), startBody
							.getStartPosition()
							- 1 - block.getStartPosition());
					positions.add(startPosition);
				}

				Position endPosition = null;
				if (block instanceof IfBlock) {
					endPosition = computeIfEndRegion((IfBlock)block);
				} else if (block instanceof LetBlock) {
					endPosition = computeLetEndRegion((LetBlock)block);
				} else {
					endPosition = new Position(block.getBody().get(block.getBody().size() - 1)
							.getEndPosition(), block.getEndPosition()
							- block.getBody().get(block.getBody().size() - 1).getEndPosition());
				}

				positions.add(endPosition);
			} else {
				positions.add(new Position(block.getStartPosition(), block.getEndPosition()
						- block.getStartPosition()));
			}
		}

		return positions;
	}

	/**
	 * Returns the end region of the if block.
	 * 
	 * @param iBlock
	 *            The if block
	 * @return The end region of the if block
	 */
	private Position computeIfEndRegion(IfBlock iBlock) {
		Position endRegion = null;
		Block else1 = iBlock.getElse();
		List<IfBlock> elseIf = iBlock.getElseIf();
		// Else and Elseif
		if (else1 != null && elseIf != null && elseIf.size() > 0) {
			int endPosition = else1.getEndPosition();
			int endPosition2 = elseIf.get(elseIf.size() - 1).getEndPosition();
			if (endPosition > endPosition2) {
				endRegion = new Position(endPosition, iBlock.getEndPosition() - endPosition);
			} else {
				endRegion = new Position(endPosition2, iBlock.getEndPosition() - endPosition2);
			}
			// no else but elseif
		} else if (else1 == null && elseIf != null && elseIf.size() > 0) {
			int endPosition = elseIf.get(elseIf.size() - 1).getEndPosition();
			endRegion = new Position(endPosition, iBlock.getEndPosition() - endPosition);
			// no else if
		} else if (elseIf == null || elseIf.size() == 0) {
			if (else1 != null) {
				int endPosition = else1.getEndPosition();
				endRegion = new Position(endPosition, iBlock.getEndPosition() - endPosition);
			} else {
				endRegion = new Position(iBlock.getBody().get(iBlock.getBody().size() - 1).getEndPosition(),
						iBlock.getEndPosition()
								- iBlock.getBody().get(iBlock.getBody().size() - 1).getEndPosition());
			}
		}
		return endRegion;
	}

	/**
	 * Returns the end region of the let block.
	 * 
	 * @param eBlock
	 *            The let block
	 * @return The end region of the let block
	 */
	private Position computeLetEndRegion(LetBlock eBlock) {
		Position endRegion = null;
		Block else1 = eBlock.getElse();
		List<LetBlock> elseLet = eBlock.getElseLet();
		if (else1 != null && elseLet != null && elseLet.size() > 0) {
			int endPosition = else1.getEndPosition();
			int endPosition2 = elseLet.get(elseLet.size() - 1).getEndPosition();
			if (endPosition > endPosition2) {
				endRegion = new Position(endPosition, eBlock.getEndPosition() - endPosition);
			} else {
				endRegion = new Position(endPosition2, eBlock.getEndPosition() - endPosition2);
			}
		} else if (else1 == null && elseLet != null && elseLet.size() > 0) {
			int endPosition = elseLet.get(elseLet.size() - 1).getEndPosition();
			endRegion = new Position(endPosition, eBlock.getEndPosition() - endPosition);
		} else if (else1 != null && (elseLet == null || elseLet.size() == 0)) {
			int endPosition = else1.getEndPosition();
			endRegion = new Position(endPosition, eBlock.getEndPosition() - endPosition);
		} else if (else1 == null || elseLet != null && elseLet.size() == 0) {
			endRegion = new Position(eBlock.getBody().get(eBlock.getBody().size() - 1).getEndPosition(),
					eBlock.getEndPosition()
							- eBlock.getBody().get(eBlock.getBody().size() - 1).getEndPosition());
		}
		return endRegion;
	}

	/**
	 * Returns the lock object for the given annotation model.
	 * 
	 * @param annotationModel
	 *            the annotation model
	 * @return the annotation model's lock object
	 */
	private Object getLockObject(final IAnnotationModel annotationModel) {
		if (annotationModel instanceof ISynchronizable) {
			final Object lock = ((ISynchronizable)annotationModel).getLockObject();
			if (lock != null) {
				return lock;
			}
		}
		return annotationModel;
	}

	/**
	 * Returns the list of all the occurrences of the current selection in the editor that are in the current
	 * file.
	 * 
	 * @param result
	 *            The result of the search query.
	 * @return The list of the occurrences of the current selection in the editor that are in the current
	 *         file.
	 */
	private List<Match> listOfTheOccurencesInTheCurrentFile(final AbstractTextSearchResult result) {
		List<Match> list = new ArrayList<Match>();
		for (Object object : result.getElements()) {
			if (object instanceof ReferenceEntry && ((ReferenceEntry)object).getRegion() != null) {
				ReferenceEntry ref = (ReferenceEntry)object;
				list.add(new Match(ref, ref.getRegion().getOffset(), ref.getRegion().getLength()));
			}
		}

		final IFile file = editor.getFile();
		list = OpenDeclarationUtils.getMatchesFromTheFile(list, file);

		return list;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.jobs.Job#canceling()
	 */
	@Override
	protected void canceling() {
		this.editor = null;
		this.query = null;
		super.canceling();
	}

	/**
	 * Clears the attributes.
	 */
	public void clear() {
		this.editor = null;
		this.query = null;
	}

}
