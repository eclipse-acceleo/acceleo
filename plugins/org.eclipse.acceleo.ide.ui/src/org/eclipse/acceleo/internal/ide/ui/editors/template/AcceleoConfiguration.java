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
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.internal.ide.ui.editors.template.color.AcceleoColor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.color.AcceleoColorManager;
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
import org.eclipse.acceleo.internal.ide.ui.editors.template.scanner.AcceleoToken;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.OpenDeclarationUtils;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
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
import org.eclipse.swt.graphics.Color;
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

	/** The editor. */
	protected AcceleoEditor editor;

	/** The scanners of the source configuration. */
	private AbstractAcceleoScanner[] scanners;

	/** Order in which to look preferences up. Might be <code>null</code>. */
	private IEclipsePreferences[] lookupOrder;

	/**
	 * Instantiates our configuration independently of an {@link AcceleoEditor}.
	 * 
	 * @param preferenceStore
	 *            The preference store, can be read-only.
	 */
	public AcceleoConfiguration(IPreferenceStore preferenceStore) {
		super(preferenceStore);
	}

	/**
	 * We may need to change the default order in which the Acceleo preferences are sought; this constructor
	 * may be used in this event.
	 * 
	 * @param preferenceStore
	 *            The preference store, can be read-only.
	 * @param lookupOrder
	 *            {@link IEclipsePreferences} in the order in which we should look preferences up. The default
	 *            order is instanceScope, then defaultScope.
	 */
	public AcceleoConfiguration(IPreferenceStore preferenceStore, IEclipsePreferences[] lookupOrder) {
		this(preferenceStore);
		this.lookupOrder = lookupOrder;
	}

	/**
	 * Constructor.
	 * 
	 * @param editor
	 *            is the source editor
	 * @param preferenceStore
	 *            The preference store, can be read-only.
	 */
	public AcceleoConfiguration(AcceleoEditor editor, IPreferenceStore preferenceStore) {
		this(preferenceStore);
		this.editor = editor;
	}

	/**
	 * Gets the scanners of the source configuration.
	 * 
	 * @return the scanners
	 */
	protected AbstractAcceleoScanner[] getScanners() {
		if (scanners == null) {
			List<AbstractAcceleoScanner> list = new ArrayList<AbstractAcceleoScanner>();
			list.add(new AcceleoTemplateScanner(lookupOrder));
			list.add(new AcceleoQueryScanner(lookupOrder));
			list.add(new AcceleoMacroScanner(lookupOrder));
			list.add(new AcceleoForScanner(lookupOrder));
			list.add(new AcceleoIfScanner(lookupOrder));
			list.add(new AcceleoLetScanner(lookupOrder));
			list.add(new AcceleoProtectedAreaScanner(lookupOrder));
			list.add(new AcceleoCommentScanner(lookupOrder));
			list.add(new AcceleoDocumentationScanner(lookupOrder));
			list.add(new AcceleoBlockScanner(lookupOrder));
			list.add(new AcceleoDefaultScanner(lookupOrder));
			scanners = list.toArray(new AbstractAcceleoScanner[list.size()]);
		}
		return scanners;
	}

	/**
	 * This can be called to force the scanners to adapt to a new color or style as dictated by the
	 * preferences' {@link #lookupOrder}.
	 * 
	 * @param event
	 *            The event we are to adapt to.
	 */
	public void adaptToPreferenceChanges(PreferenceChangeEvent event) {
		String preferenceKey = event.getKey();
		if (preferenceKey != null && preferenceKey.endsWith(".color")) { //$NON-NLS-1$
			for (AbstractAcceleoScanner scanner : getScanners()) {
				List<AcceleoToken> affectedTokens = scanner.getAffectedToken(preferenceKey);
				for (AcceleoToken token : affectedTokens) {
					AcceleoColor color = AcceleoColor.getColor(preferenceKey);
					Color newColor = AcceleoColorManager.getColor(color, lookupOrder);
					token.update(newColor);
				}
			}
		}
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
				if (editor != null) {
					Point point = part.getSelectedRange();
					if (point != null) {
						int posBegin = point.x;
						int posEnd = point.y;
						editor.updateSelection(posBegin, posEnd);
					}
				}
			}

			// We cannot use the override annotation here : it would not compile in 3.4.
			// Suppressing "all" warnings as we cannot suppress only the missing override.
			@SuppressWarnings("all")
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
		IContentAssistProcessor processor = createContentAssistProcessor(sourceViewer);
		AbstractAcceleoScanner[] acceleoScanners = getScanners();
		for (int i = 0; i < acceleoScanners.length; i++) {
			AbstractAcceleoScanner scanner = acceleoScanners[i];
			if (!(scanner instanceof AcceleoCommentScanner)
					&& !(scanner instanceof AcceleoDocumentationScanner)) {
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
	 * This will be in charge of creating the actual completion processor called out by the content assistant.
	 * 
	 * @param sourceViewer
	 *            The viewer on which is displayed the expressions for which we need content assist.
	 * @return The completion processor that is to be used.
	 */
	public IContentAssistProcessor createContentAssistProcessor(ISourceViewer sourceViewer) {
		IContentAssistProcessor processor = null;
		if (editor != null) {
			processor = new AcceleoCompletionProcessor(editor.getContent());
		} else if (sourceViewer.getDocument() != null) {
			// Initialize a new source content
			AcceleoSourceContent content = new AcceleoSourceContent();
			content.init(new StringBuffer(sourceViewer.getDocument().get()));
			processor = new AcceleoCompletionProcessor(content);
		}
		return processor;
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
		if (editor != null) {
			return new AcceleoHover(editor);
		}
		return super.getAnnotationHover(sourceViewer);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.editors.text.TextSourceViewerConfiguration#getTextHover(org.eclipse.jface.text.source.ISourceViewer,
	 *      java.lang.String)
	 */
	@Override
	public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
		if (editor != null) {
			return new AcceleoTextHover(editor);
		}
		return super.getTextHover(sourceViewer, contentType);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getReconciler(org.eclipse.jface.text.source.ISourceViewer)
	 */
	@Override
	public IReconciler getReconciler(ISourceViewer sourceViewer) {
		if (editor != null) {
			return new MonoReconciler(new AcceleoTemplateReconcilingStrategy(editor), false);
		}
		return super.getReconciler(sourceViewer);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.editors.text.TextSourceViewerConfiguration#getHyperlinkDetectorTargets(org.eclipse.jface.text.source.ISourceViewer)
	 */
	@SuppressWarnings({"rawtypes", "unchecked" })
	@Override
	protected Map getHyperlinkDetectorTargets(ISourceViewer sourceViewer) {
		if (editor != null) {
			Map targets = super.getHyperlinkDetectorTargets(sourceViewer);
			targets.put("org.eclipse.acceleo.ide.ui.AcceleoTemplateSource", editor); //$NON-NLS-1$
			return targets;
		}
		return super.getHyperlinkDetectorTargets(sourceViewer);
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
