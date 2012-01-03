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
package org.eclipse.acceleo.internal.ide.ui.editors.template.hover;

import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.text.AbstractInformationControl;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IInformationControlExtension2;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * The AcceleoDoc hover popup.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoHoverInformationControl extends AbstractInformationControl implements IInformationControlExtension2 {

	/**
	 * Indicates if there is a documentation in input.
	 */
	private static boolean inputIsDocumentation;

	/**
	 * Indicates if we should have scrolling in our viewer.
	 */
	private boolean withScrolling;

	/**
	 * The text area.
	 */
	private AcceleoDocViewer viewer;

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
		if (inputIsDocumentation) {
			// TODO SBE Actions in the documentation popup will be put later
			// IAction openDeclaration = new OpenDeclarationAction();
			// IAction openInAcceleoDocView = new OpenInAcceleoDocViewAction();
			// IAction openInBrowser = new OpenInBrowserAction();
			//
			// toolBarManager.add(openDeclaration);
			// toolBarManager.add(openInAcceleoDocView);
			// toolBarManager.add(openInBrowser);
		}
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
		styles = styles | SWT.WRAP;
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
		// do nothing
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
		return this.viewer.hasContent();
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
				// when we have the focus, show the toolbar
				return new AcceleoHoverInformationControl(parent, true);
			}
		};
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.IInformationControlExtension2#setInput(java.lang.Object)
	 */
	public void setInput(Object input) {
		this.viewer.setInput(input);
		inputIsDocumentation = this.viewer.inputIsDocumentation();
	}
}
