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
package org.eclipse.acceleo.internal.ide.ui.wizards.module;

import org.eclipse.core.runtime.IStatus;

/**
 * The Acceleo module composite listener.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public interface IAcceleoModuleCompositeListener {

	/**
	 * Applies the status to the status line of a dialog page.
	 * 
	 * @param status
	 *            the status to apply
	 */
	void applyToStatusLine(IStatus status);
}
