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

import org.eclipse.core.resources.IMarker;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolution2;

/**
 * A completion proposal used to present one proposed marker resolution to the user. The Acceleo correction
 * processor (IQuickAssistProcessor) needs this class to wrap a marker resolution
 * ("org.eclipse.ui.ide.markerResolution" extension point) and use it as a quickfix completion proposal.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class ProblemMarkerResolutionProposal implements ICompletionProposal {

	/**
	 * The marker resolution.
	 */
	private IMarkerResolution resolution;

	/**
	 * The marker.
	 */
	private IMarker marker;

	/**
	 * Constructor.
	 * 
	 * @param resolution
	 *            the marker resolution
	 * @param marker
	 *            the marker
	 */
	public ProblemMarkerResolutionProposal(IMarkerResolution resolution, IMarker marker) {
		this.resolution = resolution;
		this.marker = marker;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposal#apply(org.eclipse.jface.text.IDocument)
	 */
	public void apply(IDocument document) {
		resolution.run(marker);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposal#getAdditionalProposalInfo()
	 */
	public String getAdditionalProposalInfo() {
		if (resolution instanceof IMarkerResolution2) {
			return ((IMarkerResolution2)resolution).getDescription();
		}
		return marker.getAttribute(IMarker.MESSAGE, ""); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposal#getContextInformation()
	 */
	public IContextInformation getContextInformation() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposal#getDisplayString()
	 */
	public String getDisplayString() {
		return resolution.getLabel();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposal#getSelection(org.eclipse.jface.text.IDocument)
	 */
	public Point getSelection(IDocument document) {
		if (resolution instanceof ICompletionProposal) {
			return ((ICompletionProposal)resolution).getSelection(document);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.contentassist.ICompletionProposal#getImage()
	 */
	public Image getImage() {
		Image result;
		if (resolution instanceof IMarkerResolution2) {
			result = ((IMarkerResolution2)resolution).getImage();
		} else if (resolution instanceof ICompletionProposal) {
			result = ((ICompletionProposal)resolution).getImage();
		} else {
			result = null;
		}
		return result;
	}
}
