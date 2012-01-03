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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.refactor.rename;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

/**
 * The Acceleo compare viewer allow the user to see a preview of the refactoring with the same syntax
 * highlighting as in the Acceleo editor.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoCompareViewer implements IViewerCreator {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.compare.IViewerCreator#createViewer(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.compare.CompareConfiguration)
	 */
	public Viewer createViewer(Composite parent, CompareConfiguration config) {
		return new AcceleoRefactoringPreviewViewer(parent, config);
	}

}
