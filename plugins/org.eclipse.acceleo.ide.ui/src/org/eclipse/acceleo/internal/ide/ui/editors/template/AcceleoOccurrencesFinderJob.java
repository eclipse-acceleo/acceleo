/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.references.ReferenceEntry;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.references.ReferencesSearchQuery;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.references.ReferencesSearchResult;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ISynchronizable;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
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
		// At this moment the result is empty but it's necessary for the initialization of the search result.
		final ReferencesSearchResult result = (ReferencesSearchResult)query.getSearchResult();

		IStatus status = null;
		final int origPriority = Thread.currentThread().getPriority();
		try {
			Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
		} catch (SecurityException e) {
			// do nothing
		}
		try {
			status = this.query.run(monitor);
		} finally {
			try {
				Thread.currentThread().setPriority(origPriority);
			} catch (SecurityException e) {
				// do nothing
			}
		}

		final List<Match> list = this.listOfTheOccurencesInTheCurrentFile(result);
		final int length = list.size();

		final Map<Annotation, Position> annotationMap = new HashMap<Annotation, Position>(length);
		for (int i = 0; i < length; i++) {

			if (monitor.isCanceled()) {
				status = Status.CANCEL_STATUS;
			}

			if (this.editor.getDocumentProvider() == null
					|| this.editor.getDocumentProvider().getAnnotationModel(this.editor.getEditorInput()) == null) {
				status = Status.CANCEL_STATUS;
			}

			if (status == Status.CANCEL_STATUS) {
				return status;
			}

			final String description = ((ReferenceEntry)list.get(i).getElement()).getMatch().toString();

			final IRegion region = ((ReferenceEntry)list.get(i).getElement()).getRegion();
			final Position position = new Position(region.getOffset(), region.getLength());

			// Existing marker of the JDT do not modify !

			annotationMap.put(new Annotation(FIND_OCCURENCES_ANNOTATION_TYPE, false, description), position);
		}

		final IAnnotationModel annotationModel = this.editor.getDocumentProvider().getAnnotationModel(
				this.editor.getEditorInput());

		if (annotationModel != null) {
			synchronized(getLockObject(annotationModel)) {
				final Iterator<Entry<Annotation, Position>> iter = annotationMap.entrySet().iterator();
				while (iter.hasNext()) {
					final Map.Entry<Annotation, Position> mapEntry = (Map.Entry<Annotation, Position>)iter
							.next();
					annotationModel.addAnnotation((Annotation)mapEntry.getKey(), (Position)mapEntry
							.getValue());
				}
			}
		}

		return status;
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
		final List<Match> list = new ArrayList<Match>();
		final Object[] array = result.getElements();

		for (int i = 0; i < array.length; i++) {
			// if the file mentioned in the result is equal to the current file in the editor, we add that
			// value to our list.
			if (((ReferenceEntry)array[i]).getTemplateFile().equals(((AcceleoEditor)editor).getFile())) {
				if (((ReferenceEntry)array[i]).getRegion() != null) {
					list.add(new Match(array[i], ((ReferenceEntry)array[i]).getRegion().getOffset(),
							((ReferenceEntry)array[i]).getRegion().getLength()));
				}
			}
		}

		return list;
	}

}
