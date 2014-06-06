/*******************************************************************************
 * Copyright (c) 2008, 2014 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.launching;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.launching.strategy.IAcceleoLaunchingStrategy;
import org.eclipse.acceleo.internal.ide.ui.launching.strategy.AcceleoJavaLaunchingStrategy;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.osgi.framework.Bundle;

/**
 * To launch an Acceleo application in a Java standalone way, or in a full Eclipse way. This last way is
 * currently required for debugging an Acceleo file. You can also debug an Acceleo application by launching it
 * in a Java standalone way, but you will debug the Acceleo Java sources...
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoLaunchDelegate extends AcceleoLaunchDelegateStandalone {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jdt.launching.JavaLaunchDelegate#launch(org.eclipse.debug.core.ILaunchConfiguration,
	 *      java.lang.String, org.eclipse.debug.core.ILaunch, org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void launch(final ILaunchConfiguration configuration, String mode, ILaunch launch,
			IProgressMonitor monitor) throws CoreException {
		try {
			IAcceleoLaunchingStrategy strategy = getLaunchingStrategy(configuration);
			if (strategy instanceof AcceleoJavaLaunchingStrategy) {
				super.launch(configuration, mode, launch, monitor);
			} else if (strategy != null) {
				// Launches the given configuration in the current Eclipse thread.
				strategy.launch(configuration, mode, launch, monitor);
			}
		} finally {
			if (!launch.isTerminated()) {
				launch.terminate();
			}
		}
	}

	/**
	 * Returns the launching strategy specified by the given launch configuration, or <code>null</code> if
	 * none.
	 * 
	 * @param configuration
	 *            launch configuration
	 * @return the launching strategy, or null if none
	 */
	private IAcceleoLaunchingStrategy getLaunchingStrategy(final ILaunchConfiguration configuration) {
		String description;
		try {
			description = configuration.getAttribute(
					IAcceleoLaunchConfigurationConstants.ATTR_LAUNCHING_STRATEGY_DESCRIPTION, ""); //$NON-NLS-1$
		} catch (CoreException e) {
			description = ""; //$NON-NLS-1$
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
		return getLaunchingStrategy(description);
	}

	/**
	 * Gets the launching strategy that matches the given description. It is used to define a specific way of
	 * launching an Acceleo generation.
	 * 
	 * @param launchingID
	 *            is the description of the strategy to get in the current Eclipse instance
	 * @return the launching strategy that matches the given description, or null if it doesn't exist
	 */
	@SuppressWarnings("unchecked")
	private IAcceleoLaunchingStrategy getLaunchingStrategy(String launchingID) {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = registry
				.getExtensionPoint(IAcceleoLaunchingStrategy.LAUNCHING_STRATEGY_EXTENSION_ID);
		if (extensionPoint != null && extensionPoint.getExtensions().length > 0) {
			IExtension[] extensions = extensionPoint.getExtensions();
			for (int i = 0; i < extensions.length; i++) {
				IExtension extension = extensions[i];
				IConfigurationElement[] members = extension.getConfigurationElements();
				for (int j = 0; j < members.length; j++) {
					IConfigurationElement member = members[j];
					String description = member.getAttribute("description"); //$NON-NLS-1$
					String strategyClass = member.getAttribute("class"); //$NON-NLS-1$
					if (strategyClass != null && description != null && launchingID != null
							&& launchingID.startsWith(description)) {
						try {
							Bundle bundle = Platform.getBundle(member.getNamespaceIdentifier());
							@SuppressWarnings("cast")
							Class<IAcceleoLaunchingStrategy> c = (Class<IAcceleoLaunchingStrategy>)bundle
									.loadClass(strategyClass);
							return c.newInstance();
						} catch (ClassNotFoundException e) {
							AcceleoUIActivator.log(e, true);
						} catch (InstantiationException e) {
							AcceleoUIActivator.log(e, true);
						} catch (IllegalAccessException e) {
							AcceleoUIActivator.log(e, true);
						}
					}
				}
			}
		}
		return null;
	}

}
