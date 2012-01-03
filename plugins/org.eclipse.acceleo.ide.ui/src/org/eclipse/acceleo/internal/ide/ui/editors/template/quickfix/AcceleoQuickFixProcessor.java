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
package org.eclipse.acceleo.internal.ide.ui.editors.template.quickfix;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.quickassist.IQuickAssistInvocationContext;
import org.eclipse.jface.text.quickassist.IQuickAssistProcessor;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.MarkerAnnotation;

/**
 * Acceleo correction processor used to show quick fixes for syntax problems.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoQuickFixProcessor implements IQuickAssistProcessor {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.quickassist.IQuickAssistProcessor#canAssist(org.eclipse.jface.text.quickassist.IQuickAssistInvocationContext)
	 */
	public boolean canAssist(IQuickAssistInvocationContext invocationContext) {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.quickassist.IQuickAssistProcessor#canFix(org.eclipse.jface.text.source.Annotation)
	 */
	public boolean canFix(Annotation annotation) {
		return annotation != null
				&& "org.eclipse.acceleo.ide.ui.annotation.problem".equals(annotation.getType()); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.quickassist.IQuickAssistProcessor#computeQuickAssistProposals(org.eclipse.jface.text.quickassist.IQuickAssistInvocationContext)
	 */
	public ICompletionProposal[] computeQuickAssistProposals(IQuickAssistInvocationContext invocationContext) {
		List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
		if (invocationContext != null) {
			ISourceViewer sourceViewer = invocationContext.getSourceViewer();
			int offset = invocationContext.getOffset();
			List<Annotation> annotations = findAnnotationsAt(sourceViewer, offset);
			for (Annotation annotation : annotations) {
				collectMarkerProposals(annotation, proposals);
			}
		}
		return proposals.toArray(new ICompletionProposal[proposals.size()]);
	}

	/**
	 * Returns all the annotations at the given offset.
	 * 
	 * @param sourceViewer
	 *            is the source viewer
	 * @param offset
	 *            is the offset
	 * @return all the annotations
	 */
	private List<Annotation> findAnnotationsAt(ISourceViewer sourceViewer, int offset) {
		List<Annotation> annotations = new ArrayList<Annotation>();
		if (sourceViewer != null && offset > -1) {
			IAnnotationModel annotationModel = sourceViewer.getAnnotationModel();
			if (annotationModel != null) {
				for (Iterator<?> it = annotationModel.getAnnotationIterator(); it.hasNext();) {
					Annotation annotation = (Annotation)it.next();
					Position position = annotationModel.getPosition(annotation);
					if (position != null && offset >= position.offset
							&& offset <= position.offset + position.length) {
						annotations.add(annotation);
					}
				}
			}
		}
		return annotations;
	}

	/**
	 * Collect the quick fixes proposals for the given annotation.
	 * 
	 * @param annotation
	 *            is the annotation
	 * @param proposals
	 *            are the proposals to create, it's an input/output parameter
	 */
	private static void collectMarkerProposals(Annotation annotation,
			Collection<ICompletionProposal> proposals) {
		if (annotation instanceof MarkerAnnotation) {
			IMarker marker = ((MarkerAnnotation)annotation).getMarker();
			if (marker != null) {
				IMarkerResolution[] res = IDE.getMarkerHelpRegistry().getResolutions(marker);
				if (res != null && res.length > 0) {
					for (int i = 0; i < res.length; i++) {
						proposals.add(new ProblemMarkerResolutionProposal(res[i], marker));
					}
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.quickassist.IQuickAssistProcessor#getErrorMessage()
	 */
	public String getErrorMessage() {
		return null;
	}

}
