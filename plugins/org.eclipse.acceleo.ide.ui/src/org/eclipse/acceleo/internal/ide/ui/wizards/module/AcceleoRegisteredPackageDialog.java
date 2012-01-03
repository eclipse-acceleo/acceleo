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

import java.util.Arrays;
import java.util.Map;

import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.presentation.EcoreActionBarContributor.ExtendedLoadResourceAction.RegisteredPackageDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * The AcceleoRegisteredPackageDialog is a dialog that will display all the available packages in the
 * AcceleoPackageRegistry. That includes all the EPackages registered thanks to the dedicated EMF extension
 * point and all the EPackages from the ".ecore" files in the workspac
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoRegisteredPackageDialog extends RegisteredPackageDialog {

	/**
	 * The constructor.
	 * 
	 * @param shell
	 *            The shell
	 */
	public AcceleoRegisteredPackageDialog(Shell shell) {
		super(shell);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.ecore.presentation.EcoreActionBarContributor.ExtendedLoadResourceAction.RegisteredPackageDialog#updateElements()
	 */
	@Override
	protected void updateElements() {
		if (isDevelopmentTimeVersion) {
			Map<String, URI> ePackageNsURItoGenModelLocationMap = EcorePlugin
					.getEPackageNsURIToGenModelLocationMap();
			Object[] result = ePackageNsURItoGenModelLocationMap.keySet().toArray(
					new Object[ePackageNsURItoGenModelLocationMap.size()]);
			Arrays.sort(result);
			setListElements(result);
		} else {
			Object[] result = AcceleoPackageRegistry.INSTANCE.keySet().toArray(
					new Object[AcceleoPackageRegistry.INSTANCE.size()]);
			Arrays.sort(result);
			setListElements(result);
		}
	}
}
