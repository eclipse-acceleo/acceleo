/*******************************************************************************
 *  Copyright (c) 2016, 2023 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.services.configurator;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.acceleo.query.AQLUtils;
import org.eclipse.acceleo.query.ide.QueryPlugin;
import org.eclipse.acceleo.query.services.configurator.IResourceSetConfigurator;
import org.eclipse.acceleo.query.services.configurator.IResourceSetConfiguratorDescriptor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.Platform;

/**
 * Listener that registers resource set configurators that are declared through an extension.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ResourceSetConfiguratorRegistryListener implements IRegistryEventListener {

	/**
	 * The class constant.
	 */
	public static final String CLASS = "class";

	/**
	 * {@link IResourceSetConfigurator} extension point to parse for extensions.
	 */
	public static final String SERVICES_CONFIGURATOR_EXTENSION_POINT = "org.eclipse.acceleo.query.ide.resourceSetConfigurator";

	/**
	 * {@link IResourceSetConfigurator} tag.
	 */
	public static final String SERVICES_CONFIGURATOR_TAG_EXTENSION = "resourceSetConfigurators";

	/**
	 * The {@link IResourceSetConfigurator} extension point class attribute.
	 */
	public static final String SERVICES_CONFIGURATOR_ATTRIBUTE_CLASS = CLASS;

	/**
	 * The mapping from the class name to the {@link IResourceSetConfiguratorDescriptor}.
	 */
	private final Map<String, IResourceSetConfiguratorDescriptor> descriptors = new HashMap<String, IResourceSetConfiguratorDescriptor>();

	/**
	 * An {@link IResourceSetConfiguratorDescriptor} for an extension point.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	public static class ExtensionServicesConfiguratorDescriptor implements IResourceSetConfiguratorDescriptor {

		/**
		 * The {@link IConfigurationElement}.
		 */
		private final IConfigurationElement element;

		/**
		 * The {@link IResourceSetConfigurator}.
		 */
		private IResourceSetConfigurator configurator;

		/**
		 * Constructor.
		 * 
		 * @param element
		 *            the {@link IConfigurationElement}
		 */
		public ExtensionServicesConfiguratorDescriptor(IConfigurationElement element) {
			this.element = element;
		}

		@Override
		public IResourceSetConfigurator getResourceSetConfigurator() {
			if (configurator == null) {
				try {
					configurator = (IResourceSetConfigurator)element.createExecutableExtension(
							SERVICES_CONFIGURATOR_ATTRIBUTE_CLASS);
				} catch (CoreException e) {
					QueryPlugin.log(e, false);
				}
			}

			return configurator;
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtension[])
	 */
	public void added(IExtension[] extensions) {
		for (IExtension extension : extensions) {
			if (SERVICES_CONFIGURATOR_EXTENSION_POINT.equals(extension.getUniqueIdentifier())) {
				parseServicesConfiguratorExtension(extension);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtensionPoint[])
	 */
	public void added(IExtensionPoint[] extensionPoints) {
		// no need to listen to this event
	}

	/**
	 * Though this listener reacts to the extension point changes, there could have been contributions before
	 * it's been registered. This will parse these initial contributions.
	 */
	public void parseInitialContributions() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();

		for (IExtension extension : registry.getExtensionPoint(SERVICES_CONFIGURATOR_EXTENSION_POINT)
				.getExtensions()) {
			parseServicesConfiguratorExtension(extension);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtension[])
	 */
	public void removed(IExtension[] extensions) {
		for (IExtension extension : extensions) {
			final IConfigurationElement[] configElements = extension.getConfigurationElements();
			for (IConfigurationElement element : configElements) {
				if (SERVICES_CONFIGURATOR_TAG_EXTENSION.equals(element.getName())) {
					final IResourceSetConfiguratorDescriptor descriptor = descriptors.remove(element
							.getAttribute(SERVICES_CONFIGURATOR_ATTRIBUTE_CLASS));
					if (descriptor != null) {
						AQLUtils.unregisterResourceSetConfigurator(descriptor);
					}
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtensionPoint[])
	 */
	public void removed(IExtensionPoint[] extensionPoints) {
		// no need to listen to this event
	}

	/**
	 * Parses a single {@link ILocation} extension contribution.
	 * 
	 * @param extension
	 *            Parses the given extension and adds its contribution to the registry
	 */
	private void parseServicesConfiguratorExtension(IExtension extension) {
		final IConfigurationElement[] configElements = extension.getConfigurationElements();
		for (IConfigurationElement element : configElements) {
			if (SERVICES_CONFIGURATOR_TAG_EXTENSION.equals(element.getName())) {
				final IResourceSetConfiguratorDescriptor descriptor = new ExtensionServicesConfiguratorDescriptor(
						element);
				descriptors.put(element.getAttribute(SERVICES_CONFIGURATOR_ATTRIBUTE_CLASS), descriptor);
				AQLUtils.registerResourceSetConfigurator(descriptor);
			}
		}
	}

}
