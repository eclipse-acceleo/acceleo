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
package org.eclipse.acceleo.internal.ide.ui.editors.template.hover;

import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.AcceleoUIDocumentationUtils;
import org.eclipse.acceleo.model.mtl.Documentation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.text.AbstractInformationControl;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IInformationControlExtension2;
import org.eclipse.jface.text.IInformationControlExtension3;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * The AcceleoDoc hover popup.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoHoverInformationControl extends AbstractInformationControl implements IInformationControlExtension2, IInformationControlExtension3 {

	/**
	 * Indicates if there is a documentation in input.
	 */
	private boolean inputIsDocumentation;

	/**
	 * Indicates if we should have scrolling in our viewer.
	 */
	private boolean withScrolling;

	/**
	 * The text area.
	 */
	private AcceleoDocViewer viewer;

	/**
	 * The text presentation.
	 */
	private TextPresentation presentation;

	/**
	 * The text without the HTML.
	 */
	private String computedText;

	/**
	 * Creates a AcceleoHoverInformationControl with the given shell as parent.
	 * 
	 * @param parent
	 *            the parent shell
	 */
	public AcceleoHoverInformationControl(final Shell parent) {
		super(parent, AcceleoUIMessages.getString("AcceleoDoc.Hover.AcceleoDocumentation")); //$NON-NLS-1$
		this.create();
	}

	/**
	 * Creates a AcceleoHoverInformationControl with the given shell as parent.
	 * 
	 * @param parent
	 *            the parent shell
	 * @param withToolbar
	 *            Indicates if there is a visible toolbar
	 */
	public AcceleoHoverInformationControl(final Shell parent, final boolean withToolbar) {
		super(parent, initToolBarManager());
		this.withScrolling = withToolbar;
		this.create();
	}

	/**
	 * Returns the toolbar manager.
	 * 
	 * @return The toolbar manager.
	 */
	private static ToolBarManager initToolBarManager() {
		ToolBarManager toolBarManager = new ToolBarManager();
		// TODO SBE Actions in the documentation popup will be put later
		// IAction openDeclaration = new OpenDeclarationAction();
		// IAction openInAcceleoDocView = new OpenInAcceleoDocViewAction();
		// IAction openInBrowser = new OpenInBrowserAction();
		//
		// toolBarManager.add(openDeclaration);
		// toolBarManager.add(openInAcceleoDocView);
		// toolBarManager.add(openInBrowser);
		return toolBarManager;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.AbstractInformationControl#createContent(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createContent(Composite parent) {
		int styles = SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION;
		if (this.withScrolling) {
			styles = styles | SWT.V_SCROLL | SWT.H_SCROLL;
		}

		this.viewer = new AcceleoDocViewer(parent, styles);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.AbstractInformationControl#computeTrim()
	 */
	@Override
	public Rectangle computeTrim() {
		if (this.withScrolling) {
			final int scrollbarSize = 22;

			Rectangle rect = super.computeTrim();
			rect.height += scrollbarSize;
			rect.width += scrollbarSize;
			return rect;
		}
		return super.computeTrim();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.AbstractInformationControl#setInformation(java.lang.String)
	 */
	@Override
	public void setInformation(String content) {
		this.computeTextPresentation(content);
		this.viewer.setDocument(this.createDocument(this.computedText));
		TextPresentation.applyTextPresentation(this.presentation, this.viewer.getTextWidget());
	}

	/**
	 * Computes the text presentation and the final text that will be displayed.
	 * 
	 * @param content
	 *            The input text.
	 */
	private void computeTextPresentation(String content) {
		int startBoldIndex = -1;
		int endBoldIndex = -1;

		this.presentation = new TextPresentation();
		String strTmp = content;

		startBoldIndex = strTmp.indexOf(AcceleoUIDocumentationUtils.HTML_BOLD_BEGIN);
		endBoldIndex = strTmp.indexOf(AcceleoUIDocumentationUtils.HTML_BOLD_END);
		while (startBoldIndex != -1 && endBoldIndex != -1 && startBoldIndex < endBoldIndex) {
			int start = startBoldIndex;
			int length = endBoldIndex
					- (startBoldIndex + AcceleoUIDocumentationUtils.HTML_BOLD_BEGIN.length());
			StyleRange styleRange = new StyleRange(start, length, null, null, SWT.BOLD);
			strTmp = strTmp.substring(0, startBoldIndex)
					+ strTmp.substring(startBoldIndex + AcceleoUIDocumentationUtils.HTML_BOLD_BEGIN.length(),
							endBoldIndex)
					+ strTmp.substring(endBoldIndex + AcceleoUIDocumentationUtils.HTML_BOLD_END.length());
			this.presentation.addStyleRange(styleRange);

			startBoldIndex = strTmp.indexOf(AcceleoUIDocumentationUtils.HTML_BOLD_BEGIN);
			endBoldIndex = strTmp.indexOf(AcceleoUIDocumentationUtils.HTML_BOLD_END);
		}

		this.computedText = strTmp;
	}

	/**
	 * Create a document from a string.
	 * 
	 * @param content
	 *            The content
	 * @return The document
	 */
	private IDocument createDocument(final String content) {
		IDocument document = new Document(content);
		IDocumentPartitioner partitioner = new FastPartitioner(new AcceleoDocPartitionScanner(),
				AcceleoDocPartitionScanner.TYPES);
		partitioner.connect(document);
		document.setDocumentPartitioner(partitioner);
		return document;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.AbstractInformationControl#computeSizeHint()
	 */
	@Override
	public Point computeSizeHint() {
		return getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IInformationControlExtension#hasContents()
	 */
	public boolean hasContents() {
		if (this.viewer.getDocument() != null) {
			return this.viewer.getDocument().getLength() > 0;
		} else {
			return this.viewer.getTextWidget().getText().length() > 0;
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.AbstractInformationControl#getInformationPresenterControlCreator()
	 */
	@Override
	public IInformationControlCreator getInformationPresenterControlCreator() {
		return new IInformationControlCreator() {
			public IInformationControl createInformationControl(Shell parent) {
				if (inputIsDocumentation) {
					// when we have the focus, show the toolbar
					return new AcceleoHoverInformationControl(parent, true);
				} else {
					return new AcceleoHoverInformationControl(parent);
				}

			}
		};
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IInformationControlExtension2#setInput(java.lang.Object)
	 */
	public void setInput(Object input) {
		if (input instanceof Documentation) {
			// TODO this might be dead code, to be checked
			this.inputIsDocumentation = true;
			this.setInformation(AcceleoUIDocumentationUtils.getTextFrom((Documentation)input));
		} else if (input instanceof EObject) {
			this.inputIsDocumentation = false;
			this.setInformation(AcceleoUIDocumentationUtils.getSignatureFrom((EObject)input));
		} else if (input instanceof String) {
			this.setInformation((String)input);
		}
	}

}
