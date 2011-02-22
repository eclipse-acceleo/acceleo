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
package org.eclipse.acceleo.internal.ide.ui.wizards.newfile.example;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;

/**
 * Utility class to find all the example strategies existing in the current Eclipse instance.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public final class AcceleoExampleStrategyUtils {

	/**
	 * All the example strategies existing in the current Eclipse instance. An internal extension point is
	 * defined to specify multiple example strategies. It is often used to initialize automatically a template
	 * file from an example in the Acceleo project.
	 */
	private static List<IAcceleoExampleStrategy> exampleStrategies;

	/**
	 * Constructor.
	 */
	private AcceleoExampleStrategyUtils() {
	}

	/**
	 * Gets all the example strategies existing in the current Eclipse instance. An internal extension point
	 * is defined to specify multiple example strategies. It is often used to initialize automatically a
	 * template file from an example in the Acceleo project.
	 * 
	 * @return all the example strategies
	 */
	@SuppressWarnings("unchecked")
	public static List<IAcceleoExampleStrategy> getExampleStrategies() {
		if (exampleStrategies == null) {
			exampleStrategies = new ArrayList<IAcceleoExampleStrategy>();
			IExtensionRegistry registry = Platform.getExtensionRegistry();
			IExtensionPoint extensionPoint = registry
					.getExtensionPoint(IAcceleoExampleStrategy.EXAMPLE_STRATEGY_EXTENSION_ID);
			if (extensionPoint != null && extensionPoint.getExtensions().length > 0) {
				IExtension[] extensions = extensionPoint.getExtensions();
				for (int i = 0; i < extensions.length; i++) {
					IExtension extension = extensions[i];
					IConfigurationElement[] members = extension.getConfigurationElements();
					for (int j = 0; j < members.length; j++) {
						IConfigurationElement member = members[j];
						String strategyClass = member.getAttribute("class"); //$NON-NLS-1$
						if (strategyClass != null) {
							try {
								Bundle bundle = Platform.getBundle(member.getNamespaceIdentifier());
								Class<IAcceleoExampleStrategy> c = bundle.loadClass(strategyClass);
								IAcceleoExampleStrategy exampleStrategy = c.newInstance();
								exampleStrategies.add(exampleStrategy);
							} catch (ClassNotFoundException e) {
								IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID,
										IStatus.OK, e.getMessage(), e);
								AcceleoUIActivator.getDefault().getLog().log(status);
							} catch (InstantiationException e) {
								IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID,
										IStatus.OK, e.getMessage(), e);
								AcceleoUIActivator.getDefault().getLog().log(status);
							} catch (IllegalAccessException e) {
								IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID,
										IStatus.OK, e.getMessage(), e);
								AcceleoUIActivator.getDefault().getLog().log(status);
							}
						} else {
							IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID,
									IStatus.OK, AcceleoUIMessages.getString(
											"AcceleoNewTemplatesWizard.MissingStrategyClass", //$NON-NLS-1$
											IAcceleoExampleStrategy.EXAMPLE_STRATEGY_EXTENSION_ID), null);
							AcceleoUIActivator.getDefault().getLog().log(status);
						}
					}
				}
			}
		}
		return exampleStrategies;
	}

}
