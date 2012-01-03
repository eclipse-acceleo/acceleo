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
package org.eclipse.acceleo.internal.ide.ui.wizards.module.example;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy;
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
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public final class AcceleoInitializationStrategyUtils {

	/**
	 * All the initialization strategies existing in the current Eclipse instance. An internal extension point
	 * is defined to specify multiple initialization strategies. It is often used to initialize automatically
	 * a template file from an example in the Acceleo project.
	 */
	private static List<IAcceleoInitializationStrategy> initializationStrategy;

	/**
	 * The constructor.
	 */
	private AcceleoInitializationStrategyUtils() {
		// prevent instantiation
	}

	/**
	 * Gets all the initialization strategies existing in the current Eclipse instance. An internal extension
	 * point is defined to specify multiple initialization strategies. It is often used to initialize
	 * automatically a template file from an example in the Acceleo project.
	 * 
	 * @return all the initialization strategies
	 */
	@SuppressWarnings("unchecked")
	public static List<IAcceleoInitializationStrategy> getInitializationStrategy() {
		if (initializationStrategy == null) {
			initializationStrategy = new ArrayList<IAcceleoInitializationStrategy>();
			IExtensionRegistry registry = Platform.getExtensionRegistry();
			IExtensionPoint extensionPoint = registry
					.getExtensionPoint(IAcceleoInitializationStrategy.INITIALIZATION_STRATEGY_EXTENSION_ID);
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
								Class<IAcceleoInitializationStrategy> c = (Class<IAcceleoInitializationStrategy>)bundle
										.loadClass(strategyClass);
								IAcceleoInitializationStrategy exampleStrategy = c.newInstance();
								initializationStrategy.add(exampleStrategy);
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
							String message = AcceleoUIMessages.getString(
									"AcceleoNewTemplatesWizard.MissingStrategyClass", //$NON-NLS-1$
									IAcceleoInitializationStrategy.INITIALIZATION_STRATEGY_EXTENSION_ID);
							IStatus status = new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID,
									IStatus.OK, message, null);
							AcceleoUIActivator.getDefault().getLog().log(status);
						}
					}
				}
			}
		}
		return initializationStrategy;
	}
}
