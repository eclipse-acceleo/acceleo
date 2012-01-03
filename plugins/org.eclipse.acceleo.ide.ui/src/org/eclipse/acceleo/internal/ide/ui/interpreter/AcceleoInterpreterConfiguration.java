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

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.source.ISourceViewer;

/**
 * This implementation of an Acceleo configuration will create its completion processor with our source
 * viewer.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoInterpreterConfiguration extends org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoConfiguration {
	/**
	 * Simply delegates to the super constructor.
	 * 
	 * @param preferenceStore
	 *            The preference store, can be read-only.
	 */
	public AcceleoInterpreterConfiguration(IPreferenceStore preferenceStore) {
		super(preferenceStore);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoConfiguration#createContentAssistProcessor(org.eclipse.jface.text.source.ISourceViewer)
	 */
	@Override
	public IContentAssistProcessor createContentAssistProcessor(ISourceViewer sourceViewer) {
		if (sourceViewer instanceof AcceleoSourceViewer) {
			return new AcceleoInterpreterCompletionProcessor(((AcceleoSourceViewer)sourceViewer).getContent());
		}
		return super.createContentAssistProcessor(sourceViewer);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoConfiguration#getContentAssistant(org.eclipse.jface.text.source.ISourceViewer)
	 */
	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant assistant = (ContentAssistant)super.getContentAssistant(sourceViewer);
		assistant.enableAutoActivation(false);
		return assistant;
	}
}
