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

import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;

/**
 * The AcceleoDocViewer is used to view the documentation in the AcceleoDoc view and in the AcceleoDoc hover
 * pop up.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoDocViewer extends SourceViewer {

	/**
	 * The constructor.
	 * 
	 * @param parent
	 *            The composite parent
	 * @param styles
	 *            The style
	 */
	public AcceleoDocViewer(final Composite parent, final int styles) {
		super(parent, null, styles);
		this.setEditable(false);

		// We want a yellow pop up like the Javadoc one
		Color background = parent.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND);
		Color foreground = parent.getDisplay().getSystemColor(SWT.COLOR_INFO_FOREGROUND);
		this.getTextWidget().setBackground(background);
		this.getTextWidget().setForeground(foreground);
	}
}
