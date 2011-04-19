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
package org.eclipse.acceleo.internal.ide.ui.editors.template.scanner;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.editors.template.color.AcceleoColor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.color.AcceleoColorManager;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.swt.graphics.Color;

/**
 * A rule based scanner. All the scanners of the template editor should extend this class.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public abstract class AbstractAcceleoScanner extends RuleBasedScanner {
	/** This will determine the order in which we'll look preferences up. */
	private IEclipsePreferences[] lookupOrder;

	/**
	 * Default constructor.
	 */
	public AbstractAcceleoScanner() {
		IEclipsePreferences defaultScope = new DefaultScope().getNode(AcceleoUIActivator.PLUGIN_ID);
		IEclipsePreferences instanceScope = new InstanceScope().getNode(AcceleoUIActivator.PLUGIN_ID);
		lookupOrder = new IEclipsePreferences[] {instanceScope, defaultScope, };
	}

	/**
	 * Returns the preference lookup order for this scanner.
	 * 
	 * @return The preference lookup order for this scanner.
	 */
	protected IEclipsePreferences[] getLookupOrder() {
		return lookupOrder;
	}

	/**
	 * Returns the SWT {@link Color} corresponding to the given {@link AcceleoColor} in the Acceleo UI
	 * preferences.
	 * 
	 * @param color
	 *            Color constant we seek an SWT color for.
	 * @return The SWT {@link Color} corresponding to the given {@link AcceleoColor} in the Acceleo UI
	 *         preferences.
	 */
	protected Color getColor(AcceleoColor color) {
		return AcceleoColorManager.getColor(color, getLookupOrder());
	}

	/**
	 * Returns the type of the tokens read by this scanner.
	 * 
	 * @return the type of the tokens
	 */
	public abstract String getConfiguredContentType();

}
