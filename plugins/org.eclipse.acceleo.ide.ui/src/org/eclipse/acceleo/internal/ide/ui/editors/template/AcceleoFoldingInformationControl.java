/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.editors.template;

import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.jface.text.AbstractInformationControl;
import org.eclipse.jface.text.IInformationControlExtension2;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * The folding information control (pop up).
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class AcceleoFoldingInformationControl extends AbstractInformationControl implements IInformationControlExtension2 {

	/**
	 * The text area.
	 */
	private AcceleoFoldingViewer viewer;

	/**
	 * The constructor.
	 * 
	 * @param parent
	 *            The parent shell.
	 */
	public AcceleoFoldingInformationControl(final Shell parent) {
		super(parent, AcceleoUIMessages.getString("AcceleoEditor.FoldingInformationControl")); //$NON-NLS-1$
		this.create();
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
	 * @see org.eclipse.jface.text.IInformationControlExtension2#setInput(java.lang.Object)
	 */
	public void setInput(Object input) {
		this.viewer.setInput(input);
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
		styles = styles | SWT.V_SCROLL | SWT.H_SCROLL;

		this.viewer = new AcceleoFoldingViewer(parent, styles);
	}
}
