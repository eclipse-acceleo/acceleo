/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.ui.viewer;

import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.validation.type.IType;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.viewers.ILabelProvider;

/**
 * AQL {@link SourceViewerConfiguration}.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AQLConfiguration extends SourceViewerConfiguration {

	/**
	 * The {@link AQLDoubleClickStrategy}.
	 */
	private final AQLDoubleClickStrategy doubleClickStrategy;

	/**
	 * The {@link AQLScanner}.
	 */
	private final AQLScanner scanner;

	/**
	 * The {@link IContentAssistant}.
	 */
	private final ContentAssistant assistant;

	/**
	 * Constructor.
	 * 
	 * @param colorManager
	 *            the {@link ColorManager}
	 * @param labelProvider
	 *            the {@link ILabelProvider}
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param variableTypes
	 *            the variables possible types
	 */
	public AQLConfiguration(ColorManager colorManager, ILabelProvider labelProvider,
			IReadOnlyQueryEnvironment queryEnvironment, Map<String, Set<IType>> variableTypes) {

		doubleClickStrategy = new AQLDoubleClickStrategy();

		scanner = new AQLScanner(colorManager);

		assistant = new ContentAssistant();
		assistant.setContentAssistProcessor(new AQLCompletionProcessor(labelProvider, queryEnvironment,
				variableTypes), IDocument.DEFAULT_CONTENT_TYPE);

	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getConfiguredContentTypes(org.eclipse.jface.text.source.ISourceViewer)
	 */
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] {IDocument.DEFAULT_CONTENT_TYPE, };
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getDoubleClickStrategy(org.eclipse.jface.text.source.ISourceViewer,
	 *      java.lang.String)
	 */
	public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer, String contentType) {
		return doubleClickStrategy;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getPresentationReconciler(org.eclipse.jface.text.source.ISourceViewer)
	 */
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		final DefaultDamagerRepairer dr = new DefaultDamagerRepairer(scanner);
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		return reconciler;
	}

	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		return assistant;
	}

}
