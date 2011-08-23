/*******************************************************************************
 * Copyright (c) 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.ui.interpreter.internal.view;

import org.eclipse.acceleo.ui.interpreter.view.Variable;
import org.eclipse.jface.viewers.LabelProvider;

/**
 * This will act as the label provider for the "variables" Tree Viewer.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class VariableLabelProvider extends LabelProvider {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		String text = ""; //$NON-NLS-1$
		if (element instanceof Variable) {
			text = ((Variable)element).getName();
		} else if (element != null) {
			text = element.toString();
		}
		return text;
	}
}
