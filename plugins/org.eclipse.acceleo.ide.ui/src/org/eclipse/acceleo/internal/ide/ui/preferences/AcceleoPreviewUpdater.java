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
package org.eclipse.acceleo.internal.ide.ui.preferences;

import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.text.source.SourceViewer;

/**
 * Instances of this class will be used in order to update the "Preview" field of the Acceleo editor
 * preferences to react to changes in the color preferences.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoPreviewUpdater {
	/**
	 * Creates an updater for our source viewer so that it can react to changes made by the user to the syntax
	 * highlighting colors.
	 * 
	 * @param viewer
	 *            The viewer we are to update.
	 * @param preferenceStore
	 *            Preferences store which values we are to reflect.
	 */
	public AcceleoPreviewUpdater(SourceViewer viewer, IEclipsePreferences preferenceStore) {

	}
}
