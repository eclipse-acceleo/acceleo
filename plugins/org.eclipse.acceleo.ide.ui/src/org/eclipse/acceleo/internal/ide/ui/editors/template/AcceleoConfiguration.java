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
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.internal.ide.ui.editors.template.hover.AcceleoHover;
import org.eclipse.acceleo.internal.ide.ui.editors.template.hover.AcceleoHoverInformationControl;
import org.eclipse.acceleo.internal.ide.ui.editors.template.hover.AcceleoTextHover;
import org.eclipse.acceleo.internal.ide.ui.editors.template.quickfix.AcceleoQuickFixProcessor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AbstractAcceleoScanner;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoBlockScanner;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoCommentScanner;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoDefaultScanner;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoDocumentationScanner;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoForScanner;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoIfScanner;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoLetScanner;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoMacroScanner;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoPartitionScanner;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoProtectedAreaScanner;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoQueryScanner;
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoTemplateScanner;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.OpenDeclarationUtils;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.DefaultTextDoubleClickStrategy;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.quickassist.IQuickAssistAssistant;
import org.eclipse.jface.text.quickassist.QuickAssistAssistant;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

/**
 * This class bundles the configuration space of a Acceleo template editor.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoConfiguration extends TextSourceViewerConfiguration {

	/**
	 * The delay after which the content assistant is automatically invoked if the cursor is behind an auto
	 * activation character.
	 */
	private static final int COMPLETION_AUTO_ACTIVATION_DELAY = 1000;

	/**
	 * The editor.
	 */
	protected AcceleoEditor editor;

	/**
	 * The scanners of the source configuration.
	 */
	private AbstractAcceleoScanner[] scanners;

	/**
	 * Constructor.
	 * 
	 * @param editor
	 *            is the source editor
	 * @param preferenceStore
	 *            The preference store, can be read-only.
	 */
	public AcceleoConfiguration(AcceleoEditor editor, IPreferenceStore preferenceStore) {
		this.editor = editor;
		this.fPreferenceStore = preferenceStore;
	}

	/**
	 * Gets the scanners of the source configuration.
	 * 
	 * @return the scanners
	 */
	protected AbstractAcceleoScanner[] getScanners() {
		if (scanners == null) {
			List<AbstractAcceleoScanner> list = new ArrayList<AbstractAcceleoScanner>();
			list.add(new AcceleoTemplateScanner(editor.getColorManager()));
			list.add(new AcceleoQueryScanner(editor.getColorManager()));
			list.add(new AcceleoMacroScanner(editor.getColorManager()));
			list.add(new AcceleoForScanner(editor.getColorManager()));
			list.add(new AcceleoIfScanner(editor.getColorManager()));
			list.add(new AcceleoLetScanner(editor.getColorManager()));
			list.add(new AcceleoProtectedAreaScanner(editor.getColorManager()));
			list.add(new AcceleoCommentScanner(editor.getColorManager()));
			list.add(new AcceleoDocumentationScanner(editor.getColorManager()));
			list.add(new AcceleoBlockScanner(editor.getColorManager()));
			list.add(new AcceleoDefaultScanner(editor.getColorManager()));
			scanners = list.toArray(new AbstractAcceleoScanner[list.size()]);
		}
		return scanners;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getConfiguredContentTypes(org.eclipse.jface.text.source.ISourceViewer)
	 */
	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		AbstractAcceleoScanner[] acceleoScanners = getScanners();
		String[] result = new String[acceleoScanners.length];
		for (int i = 0; i < acceleoScanners.length; i++) {
			AbstractAcceleoScanner scanner = acceleoScanners[i];
			result[i] = scanner.getConfiguredContentType();
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getDoubleClickStrategy(org.eclipse.jface.text.source.ISourceViewer,
	 *      java.lang.String)
	 */
	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer, String contentType) {
		return new DefaultTextDoubleClickStrategy() {
			@Override
			public void doubleClicked(ITextViewer part) {
				super.doubleClicked(part);
				Point point = part.getSelectedRange();
				if (point != null) {
					int posBegin = point.x;
					int posEnd = point.y;
					editor.updateSelection(posBegin, posEnd);
				}
			}

			// FIXME JMU doesn't compile in 3.4
			protected IRegion findExtendedDoubleClickSelection(IDocument document, int offset) {
				IRegion region = OpenDeclarationUtils.findIdentifierRegion(document, offset);
				return region;
			}
		};
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getPresentationReconciler(org.eclipse.jface.text.source.ISourceViewer)
	 */
	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();
		AbstractAcceleoScanner[] acceleoScanners = getScanners();
		for (int i = 0; i < acceleoScanners.length; i++) {
			AbstractAcceleoScanner scanner = acceleoScanners[i];
			DefaultDamagerRepairer dr = new DefaultDamagerRepairer(scanner);
			reconciler.setDamager(dr, scanner.getConfiguredContentType());
			reconciler.setRepairer(dr, scanner.getConfiguredContentType());
		}
		return reconciler;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getContentAssistant(org.eclipse.jface.text.source.ISourceViewer)
	 */
	@Override
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant assistant = new ContentAssistant();
		assistant.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));
		IContentAssistProcessor processor = new AcceleoCompletionProcessor(editor.getContent());
		AbstractAcceleoScanner[] acceleoScanners = getScanners();
		for (int i = 0; i < acceleoScanners.length; i++) {
			AbstractAcceleoScanner scanner = acceleoScanners[i];
			if (!(scanner instanceof AcceleoCommentScanner)
					|| !(scanner instanceof AcceleoDocumentationScanner)) {
				assistant.setContentAssistProcessor(processor, scanner.getConfiguredContentType());
			}
		}
		assistant.enableAutoActivation(true);
		assistant.setAutoActivationDelay(COMPLETION_AUTO_ACTIVATION_DELAY);
		assistant.setProposalPopupOrientation(IContentAssistant.PROPOSAL_OVERLAY);
		assistant.setInformationControlCreator(getInformationControlCreator(sourceViewer));
		return assistant;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.editors.text.TextSourceViewerConfiguration#getQuickAssistAssistant(org.eclipse.jface.text.source.ISourceViewer)
	 */
	@Override
	public IQuickAssistAssistant getQuickAssistAssistant(ISourceViewer sourceViewer) {
		IQuickAssistAssistant assistant = new QuickAssistAssistant();
		assistant.setQuickAssistProcessor(new AcceleoQuickFixProcessor());
		assistant.setInformationControlCreator(getQuickAssistAssistantInformationControlCreator());
		return assistant;
	}

	/**
	 * Returns the information control creator for the quick assist assistant.
	 * 
	 * @return the information control creator
	 */
	private IInformationControlCreator getQuickAssistAssistantInformationControlCreator() {
		return new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell parent) {
				return new DefaultInformationControl(parent);
			}
		};
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getAnnotationHover(org.eclipse.jface.text.source.ISourceViewer)
	 */
	@Override
	public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
		return new AcceleoHover(editor);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.editors.text.TextSourceViewerConfiguration#getTextHover(org.eclipse.jface.text.source.ISourceViewer,
	 *      java.lang.String)
	 */
	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
		return new AcceleoTextHover(editor);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getReconciler(org.eclipse.jface.text.source.ISourceViewer)
	 */
	@Override
	public IReconciler getReconciler(ISourceViewer sourceViewer) {
		return new MonoReconciler(new AcceleoTemplateReconcilingStrategy(editor), false);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.editors.text.TextSourceViewerConfiguration#getHyperlinkDetectorTargets(org.eclipse.jface.text.source.ISourceViewer)
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	@Override
	protected Map getHyperlinkDetectorTargets(ISourceViewer sourceViewer) {
		Map targets = super.getHyperlinkDetectorTargets(sourceViewer);
		targets.put("org.eclipse.acceleo.ide.ui.AcceleoTemplateSource", editor); //$NON-NLS-1$
		return targets;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getInformationControlCreator(org.eclipse.jface.text.source.ISourceViewer)
	 */
	@Override
	public IInformationControlCreator getInformationControlCreator(ISourceViewer sourceViewer) {
		return new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell parent) {
				return new AcceleoHoverInformationControl(parent);
			}
		};
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getAutoEditStrategies(org.eclipse.jface.text.source.ISourceViewer,
	 *      java.lang.String)
	 */
	@Override
	public IAutoEditStrategy[] getAutoEditStrategies(ISourceViewer sourceViewer, String contentType) {
		if (AcceleoPartitionScanner.ACCELEO_DOCUMENTATION.equals(contentType)) {
			return new IAutoEditStrategy[] {new AcceleoDocAutoIndentStrategy(), };
		}
		return null;
	}
}
