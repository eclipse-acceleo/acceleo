/*******************************************************************************
 * Copyright (c) 2011, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.preferences;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.editors.template.color.AcceleoColor;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.resource.StringConverter;

/**
 * Initializes the default preference for the Acceleo UI.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoPreferenceInitializer extends AbstractPreferenceInitializer {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IEclipsePreferences node = new DefaultScope().getNode(AcceleoUIActivator.PLUGIN_ID);

		for (AcceleoColor color : AcceleoColor.values()) {
			if (!"".equals(color.getPreferenceKey())) { //$NON-NLS-1$
				String rgbValue = StringConverter.asString(color.getDefault());
				node.put(color.getPreferenceKey(), rgbValue);
			}
		}
	}
}
