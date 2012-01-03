/*******************************************************************************
 * Copyright (c) 2010, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.interpreter;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.swt.graphics.Image;

/**
 * This implementation of a completion processor will allow us to maintain a gap between the text and the CST.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoInterpreterCompletionProcessor extends org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoCompletionProcessor {
	/**
	 * Delegates to the super constructor.
	 * 
	 * @param content
	 *            The Acceleo content for which completion should be displayed.
	 */
	public AcceleoInterpreterCompletionProcessor(
			org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoSourceContent content) {
		super(content);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoCompletionProcessor#createCompletionProposal(java.lang.String,
	 *      int, int, int, org.eclipse.swt.graphics.Image, java.lang.String,
	 *      org.eclipse.jface.text.contentassist.IContextInformation, java.lang.String)
	 */
	/*
	 * Deactivating checkstyle : we're overriding a superclass method with more than 7 parameters.
	 * CHECKSTYLE:OFF
	 */
	@Override
	protected ICompletionProposal createCompletionProposal(String replacementString, int replacementOffset,
			int replacementLength, int cursorPosition, Image image, String displayString,
			IContextInformation contextInformation, String additionalProposalInfo) {
		// CHECKSTYLE:ON
		int actualOffset = replacementOffset;
		if (content instanceof AcceleoInterpreterSourceContent) {
			actualOffset -= ((AcceleoInterpreterSourceContent)content).getGap();
		}
		return super.createCompletionProposal(replacementString, actualOffset, replacementLength,
				cursorPosition, image, displayString, contextInformation, additionalProposalInfo);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoCompletionProcessor#createTemplateProposal(java.lang.String,
	 *      int, int, int, org.eclipse.swt.graphics.Image, java.lang.String,
	 *      org.eclipse.jface.text.contentassist.IContextInformation, java.lang.String)
	 */
	/*
	 * Deactivating checkstyle : we're overriding a superclass method with more than 7 parameters.
	 * CHECKSTYLE:OFF
	 */
	@Override
	protected ICompletionProposal createTemplateProposal(String replacementString, int replacementOffset,
			int replacementLength, int cursorPosition, Image image, String displayString,
			IContextInformation contextInformation, String additionalProposalInfo) {
		// CHECKSTYLE:ON
		if (textViewer != null && textViewer.getDocument() != null
				&& content instanceof AcceleoInterpreterSourceContent) {
			int actualOffset = replacementOffset - ((AcceleoInterpreterSourceContent)content).getGap();
			return super.createTemplateProposal(replacementString, actualOffset, replacementLength,
					cursorPosition, image, displayString, contextInformation, additionalProposalInfo);
		}
		return super.createTemplateProposal(replacementString, replacementOffset, replacementLength,
				cursorPosition, image, displayString, contextInformation, additionalProposalInfo);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoCompletionProcessor#setCompletionOffset(int)
	 */
	@Override
	protected void setCompletionOffset(int newOffset) {
		if (textViewer == null || !(content instanceof AcceleoInterpreterSourceContent)) {
			super.setCompletionOffset(newOffset);
		} else {
			super.setCompletionOffset(newOffset + ((AcceleoInterpreterSourceContent)content).getGap());
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoCompletionProcessor#setText(org.eclipse.jface.text.ITextViewer)
	 */
	@Override
	protected void setText(ITextViewer viewer) {
		if (viewer == null || !(viewer instanceof AcceleoSourceViewer)) {
			super.setText(viewer);
		} else {
			text = ((AcceleoSourceViewer)viewer).getFullExpression();
		}
	}
}
