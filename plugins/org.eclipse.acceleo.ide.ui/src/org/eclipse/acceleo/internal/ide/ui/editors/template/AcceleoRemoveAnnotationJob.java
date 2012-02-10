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
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.ISynchronizable;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IAnnotationModelExtension;

/**
 * This job will clean the annotation when the Acceleo editor becomes dirty.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoRemoveAnnotationJob extends Job {

	/**
	 * The editor.
	 */
	private AcceleoEditor acceleoEditor;

	/**
	 * The constructor.
	 * 
	 * @param editor
	 *            The editor to clean.
	 */
	public AcceleoRemoveAnnotationJob(final AcceleoEditor editor) {
		super(AcceleoUIMessages.getString("AcceleoRemoveAnnotationJob.Name")); //$NON-NLS-1$
		this.acceleoEditor = editor;
		this.setSystem(true);
		this.setPriority(Job.DECORATE);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected IStatus run(IProgressMonitor monitor) {
		if (this.acceleoEditor != null) {
			final IAnnotationModel model = this.acceleoEditor.getDocumentProvider().getAnnotationModel(
					this.acceleoEditor.getEditorInput());

			if (model != null) {
				synchronized(getLockObject(model)) {
					List<Annotation> annotationsList = new ArrayList<Annotation>();

					Iterator<Annotation> annotations = model.getAnnotationIterator();
					while (annotations.hasNext()) {
						if (monitor.isCanceled()) {
							return Status.CANCEL_STATUS;
						}
						Annotation annotation = annotations.next();
						if (AcceleoOccurrencesFinderJob.FIND_OCCURENCES_ANNOTATION_TYPE.equals(annotation
								.getType())) {
							annotationsList.add(annotation);
						}
					}

					if (model instanceof IAnnotationModelExtension) {
						IAnnotationModelExtension annotationModelExtension = (IAnnotationModelExtension)model;
						annotationModelExtension.replaceAnnotations(annotationsList
								.toArray(new Annotation[annotationsList.size()]),
								new HashMap<Annotation, Position>());
					} else {
						for (Annotation annotation : annotationsList) {
							model.removeAnnotation(annotation);
						}
					}
					// We re-initialize this variable to allow the user to click on the same element again
					// and see the highlighting.
					this.acceleoEditor.offsetASTNodeURI = ""; //$NON-NLS-1$
				}
			}
		}
		return Status.OK_STATUS;
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
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.jobs.Job#canceling()
	 */
	@Override
	protected void canceling() {
		this.acceleoEditor = null;
		super.canceling();
	}

	/**
	 * Clears the attributes.
	 */
	public void clear() {
		this.acceleoEditor = null;
	}
}
