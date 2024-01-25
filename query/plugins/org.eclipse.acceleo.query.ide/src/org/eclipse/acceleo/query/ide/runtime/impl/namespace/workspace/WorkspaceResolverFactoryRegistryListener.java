/*******************************************************************************
 *  Copyright (c) 2023 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.runtime.impl.namespace.workspace;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.acceleo.query.ide.QueryPlugin;
import org.eclipse.acceleo.query.ide.runtime.namespace.workspace.IWorkspaceQualifiedNameResolverFactory;
import org.eclipse.acceleo.query.ide.runtime.namespace.workspace.IWorkspaceResolverFactoryDescriptor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.Platform;

/**
 * Listener that registers resolver factory that are declared through an extension.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class WorkspaceResolverFactoryRegistryListener implements IRegistryEventListener {

	/**
	 * The class constant.
	 */
	public static final String CLASS = "class";

	/**
	 * {@link IWorkspaceQualifiedNameResolverFactory} extension point to parse for extensions.
	 */
	public static final String CLASS_PROVIDER_EXTENSION_POINT = "org.eclipse.acceleo.query.ide.workspaceresolverfactory";

	/**
	 * {@link IWorkspaceQualifiedNameResolverFactory} tag.
	 */
	public static final String CLASS_PROVIDER_TAG_EXTENSION = "workspaceresolverfactory";

	/**
	 * The {@link IWorkspaceQualifiedNameResolverFactory} extension point class attribute.
	 */
	public static final String CLASS_PROVIDER_ATTRIBUTE_CLASS = CLASS;

	/**
	 * The mapping from the class name to the {@link IWorkspaceResolverFactoryDescriptor}.
	 */
	private final Map<String, IWorkspaceResolverFactoryDescriptor> descriptors = new HashMap<String, IWorkspaceResolverFactoryDescriptor>();

	/**
	 * An {@link IWorkspaceResolverFactoryDescriptor} for an extension point.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	public static class ResolverFactoryDescriptor implements IWorkspaceResolverFactoryDescriptor {

		/**
		 * The {@link IConfigurationElement}.
		 */
		private final IConfigurationElement element;

		/**
		 * The {@link IWorkspaceQualifiedNameResolverFactory}.
		 */
		private IWorkspaceQualifiedNameResolverFactory factory;

		/**
		 * Constructor.
		 * 
		 * @param element
		 *            the {@link IConfigurationElement}
		 */
		public ResolverFactoryDescriptor(IConfigurationElement element) {
			this.element = element;
		}

		@Override
		public IWorkspaceQualifiedNameResolverFactory getFactory() {
			if (factory == null) {
				try {
					factory = (IWorkspaceQualifiedNameResolverFactory)element.createExecutableExtension(
							CLASS_PROVIDER_ATTRIBUTE_CLASS);
				} catch (CoreException e) {
					QueryPlugin.log(e, false);
				}
			}

			return factory;
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtension[])
	 */
	public void added(IExtension[] extensions) {
		for (IExtension extension : extensions) {
			if (CLASS_PROVIDER_EXTENSION_POINT.equals(extension.getUniqueIdentifier())) {
				parseClassProviderExtension(extension);
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

		for (IExtension extension : registry.getExtensionPoint(CLASS_PROVIDER_EXTENSION_POINT)
				.getExtensions()) {
			parseClassProviderExtension(extension);
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
				if (CLASS_PROVIDER_TAG_EXTENSION.equals(element.getName())) {
					final IWorkspaceResolverFactoryDescriptor descriptor = descriptors.remove(element
							.getAttribute(CLASS_PROVIDER_ATTRIBUTE_CLASS));
					if (descriptor != null) {
						QueryPlugin.unregisterWorkspaceResolverFactory(descriptor);
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
	 * Parses a single {@link IWorkspaceQualifiedNameResolverFactory} extension contribution.
	 * 
	 * @param extension
	 *            Parses the given extension and adds its contribution to the registry
	 */
	private void parseClassProviderExtension(IExtension extension) {
		final IConfigurationElement[] configElements = extension.getConfigurationElements();
		for (IConfigurationElement element : configElements) {
			if (CLASS_PROVIDER_TAG_EXTENSION.equals(element.getName())) {
				final IWorkspaceResolverFactoryDescriptor descriptor = new ResolverFactoryDescriptor(element);
				descriptors.put(element.getAttribute(CLASS_PROVIDER_ATTRIBUTE_CLASS), descriptor);
				QueryPlugin.registerWorkspaceResolverFactory(descriptor);
			}
		}
	}

}
