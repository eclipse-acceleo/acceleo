/*******************************************************************************
 *  Copyright (c) 2020, 2023 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v2.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v20.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/

package org.eclipse.acceleo.query.ide;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.query.ide.runtime.impl.namespace.EclipseQualifiedNameResolver;
import org.eclipse.acceleo.query.ide.runtime.impl.namespace.ResolverFactoryRegistryListener;
import org.eclipse.acceleo.query.ide.runtime.impl.namespace.workspace.EclipseWorkspaceQualifiedNameResolver;
import org.eclipse.acceleo.query.ide.runtime.namespace.IResolverFactoryDescriptor;
import org.eclipse.acceleo.query.ide.runtime.namespace.workspace.IWorkspaceResolverFactoryDescriptor;
import org.eclipse.acceleo.query.ide.runtime.namespace.workspace.IWorkspaceResolverProvider;
import org.eclipse.acceleo.query.ide.services.configurator.ResourceSetConfiguratorRegistryListener;
import org.eclipse.acceleo.query.ide.services.configurator.ServicesConfiguratorRegistryListener;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.impl.namespace.JavaLoader;
import org.eclipse.acceleo.query.runtime.namespace.ILoader;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryWorkspaceQualifiedNameResolver;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.osgi.framework.BundleContext;

/**
 * Plugin's activator class.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class QueryPlugin extends EMFPlugin {

	/**
	 * Plugin's id.
	 */
	public static final String PLUGIN_ID = "org.eclipse.acceleo.query.ide"; //$NON-NLS-1$

	/**
	 * The shared instance.
	 */
	public static final QueryPlugin INSTANCE = new QueryPlugin();

	/**
	 * The implementation plugin for Eclipse.
	 */
	private static Implementation plugin;

	/**
	 * The {@link List} of {@link #registerResolverFactory(IResolverFactoryDescriptor) registered}
	 * {@link IResolverFactoryDescriptor}.
	 */
	private static final List<IResolverFactoryDescriptor> RESOLVER_FACTORY_DESCRIPTORS = new ArrayList<IResolverFactoryDescriptor>();

	/**
	 * The {@link List} of {@link #registerWorkspaceResolverFactory(IWorkspaceResolverFactoryDescriptor)
	 * registered} {@link IWorkspaceResolverFactoryDescriptor}.
	 */
	private static final List<IWorkspaceResolverFactoryDescriptor> WORKSPACE_RESOLVER_FACTORY_DESCRIPTORS = new ArrayList<IWorkspaceResolverFactoryDescriptor>();

	/**
	 * The constructor.
	 */
	public QueryPlugin() {
		super(new ResourceLocator[] {});
	}

	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}

	public static Implementation getPlugin() {
		return plugin;
	}

	/**
	 * Class implementing the EclipsePlugin instance, instanciated when the code is run in an OSGi context.
	 * 
	 * @author cedric
	 */
	public static class Implementation extends EclipsePlugin {

		/** The registry listener that will be used to listen to extension changes. */
		private final ResolverFactoryRegistryListener resolverFactoryListener = new ResolverFactoryRegistryListener();

		/** The service registry listener that will be used to listen to extension changes. */
		private ServicesConfiguratorRegistryListener serviceRegistryListener = new ServicesConfiguratorRegistryListener();

		/** The service registry listener that will be used to listen to extension changes. */
		private ResourceSetConfiguratorRegistryListener resourceSetConfiguratorRegistryListener = new ResourceSetConfiguratorRegistryListener();

		/**
		 * Create the Eclipse Implementation.
		 */
		public Implementation() {
			super();

			// Remember the static instance.
			//
			plugin = this;
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
		 */
		@Override
		public void start(BundleContext context) throws Exception {
			super.start(context);

			final IExtensionRegistry registry = Platform.getExtensionRegistry();
			registry.addListener(resolverFactoryListener,
					ResolverFactoryRegistryListener.CLASS_PROVIDER_EXTENSION_POINT);
			resolverFactoryListener.parseInitialContributions();

			registry.addListener(serviceRegistryListener,
					ServicesConfiguratorRegistryListener.SERVICES_CONFIGURATOR_EXTENSION_POINT);
			serviceRegistryListener.parseInitialContributions();

			registry.addListener(resourceSetConfiguratorRegistryListener,
					ResourceSetConfiguratorRegistryListener.SERVICES_CONFIGURATOR_EXTENSION_POINT);
			resourceSetConfiguratorRegistryListener.parseInitialContributions();
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
		 */
		@Override
		public void stop(BundleContext context) throws Exception {
			super.stop(context);

			final IExtensionRegistry registry = Platform.getExtensionRegistry();
			registry.removeListener(resourceSetConfiguratorRegistryListener);
			registry.removeListener(serviceRegistryListener);
			registry.removeListener(resolverFactoryListener);
		}

		/**
		 * Creates a {@link IQualifiedNameResolver} for the given {@link IProject}.
		 * 
		 * @param classLoader
		 *            the {@link ClassLoader}
		 * @param project
		 *            the {@link IProject}
		 * @param qualifierSeparator
		 *            the qualifier name separator
		 * @param forWorkspace
		 *            <code>true</code> for workspace use, local project resolution only
		 * @return the {@link IQualifiedNameResolver} for the given {@link IProject}
		 */
		public IQualifiedNameResolver createQualifiedNameResolver(ClassLoader classLoader, IProject project,
				String qualifierSeparator, boolean forWorkspace) {
			final IQualifiedNameResolver res;

			final List<IResolverFactoryDescriptor> factoryDescriptors;
			synchronized(RESOLVER_FACTORY_DESCRIPTORS) {
				factoryDescriptors = new ArrayList<IResolverFactoryDescriptor>(RESOLVER_FACTORY_DESCRIPTORS);
			}

			if (factoryDescriptors.isEmpty()) {
				res = new EclipseQualifiedNameResolver(classLoader, project, qualifierSeparator);
			} else {
				res = factoryDescriptors.get(0).getFactory().createResolver(classLoader, project,
						qualifierSeparator, forWorkspace);
			}

			return res;
		}

		/**
		 * Creates a {@link IQueryWorkspaceQualifiedNameResolver} for the given {@link IProject}.
		 * 
		 * @param project
		 *            the {@link IProject}
		 * @param resolver
		 *            the {@link IQualifiedNameResolver} for the {@link IProject}
		 * @param resolverProvider
		 *            the {@link IWorkspaceResolverProvider} to retrieve other {@link IProject} resolver.
		 * @return the {@link IQueryWorkspaceQualifiedNameResolver} for the given {@link IProject}
		 */
		public IQueryWorkspaceQualifiedNameResolver createWorkspaceQualifiedNameResolver(IProject project,
				IQualifiedNameResolver resolver, IWorkspaceResolverProvider resolverProvider) {
			final IQueryWorkspaceQualifiedNameResolver res;

			final List<IWorkspaceResolverFactoryDescriptor> factoryDescriptors;
			synchronized(WORKSPACE_RESOLVER_FACTORY_DESCRIPTORS) {
				factoryDescriptors = new ArrayList<IWorkspaceResolverFactoryDescriptor>(
						WORKSPACE_RESOLVER_FACTORY_DESCRIPTORS);
			}

			if (factoryDescriptors.isEmpty()) {
				res = new EclipseWorkspaceQualifiedNameResolver(project, resolver, resolverProvider);
			} else {
				res = factoryDescriptors.get(0).getFactory().createResolver(project, resolver,
						resolverProvider);
			}

			return res;
		}

		/**
		 * Creates a Java {@link ILoader} with the given qualified name separator.
		 * 
		 * @param qualifierSeparator
		 *            the qualifier name separator
		 * @param forWorkspace
		 *            tells if the {@link IService} will be used in a workspace
		 * @return the created Java {@link ILoader}
		 */
		public ILoader createJavaLoader(String qualifierSeparator, boolean forWorkspace) {
			final ILoader res;

			final List<IResolverFactoryDescriptor> factoryDescriptors;
			synchronized(RESOLVER_FACTORY_DESCRIPTORS) {
				factoryDescriptors = new ArrayList<IResolverFactoryDescriptor>(RESOLVER_FACTORY_DESCRIPTORS);
			}

			if (factoryDescriptors.isEmpty()) {
				res = new JavaLoader(qualifierSeparator, forWorkspace);
			} else {
				res = factoryDescriptors.get(0).getFactory().createJavaLoader(qualifierSeparator,
						forWorkspace);
			}

			return res;
		}

	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance.
	 */
	public static QueryPlugin getDefault() {
		return INSTANCE;
	}

	/**
	 * Logs the given exception as error or warning.
	 * 
	 * @param exception
	 *            The exception to log.
	 * @param blocker
	 *            <code>True</code> if the message must be logged as error, <code>False</code> to log it as a
	 *            warning.
	 */
	public static void log(Exception exception, boolean blocker) {
		int severity = IStatus.WARNING;
		if (blocker) {
			severity = IStatus.ERROR;
		}
		QueryPlugin.INSTANCE.log(new Status(severity, PLUGIN_ID, exception.getMessage(), exception));
	}

	/**
	 * Puts the given message in the error log view, as error or warning.
	 * 
	 * @param message
	 *            The message to put in the error log view.
	 * @param blocker
	 *            <code>True</code> if the message must be logged as error, <code>False</code> to log it as a
	 *            warning.
	 */
	public static void log(String message, boolean blocker) {
		int severity = IStatus.WARNING;
		if (blocker) {
			severity = IStatus.ERROR;
		}
		String errorMessage = message;
		if (errorMessage == null || "".equals(errorMessage)) { //$NON-NLS-1$
			errorMessage = "Logging null message should never happens."; //$NON-NLS-1$
		}
		QueryPlugin.INSTANCE.log(new Status(severity, PLUGIN_ID, errorMessage));
	}

	/**
	 * Registers the given {@link IResolverFactoryDescriptor}.
	 * 
	 * @param descriptor
	 *            the {@link IResolverFactoryDescriptor}
	 */
	public static void registerResolverFactory(IResolverFactoryDescriptor descriptor) {
		if (descriptor != null) {
			synchronized(RESOLVER_FACTORY_DESCRIPTORS) {
				RESOLVER_FACTORY_DESCRIPTORS.add(descriptor);
			}
		}
	}

	/**
	 * Unregisters the given {@link IResolverFactoryDescriptor}.
	 * 
	 * @param descriptor
	 *            the {@link IResolverFactoryDescriptor}
	 */
	public static void unregisterResolverFactory(IResolverFactoryDescriptor descriptor) {
		if (descriptor != null) {
			synchronized(RESOLVER_FACTORY_DESCRIPTORS) {
				RESOLVER_FACTORY_DESCRIPTORS.remove(descriptor);
			}
		}
	}

	/**
	 * Registers the given {@link IWorkspaceResolverFactoryDescriptor}.
	 * 
	 * @param descriptor
	 *            the {@link IWorkspaceResolverFactoryDescriptor}
	 */
	public static void registerWorkspaceResolverFactory(IWorkspaceResolverFactoryDescriptor descriptor) {
		if (descriptor != null) {
			synchronized(WORKSPACE_RESOLVER_FACTORY_DESCRIPTORS) {
				WORKSPACE_RESOLVER_FACTORY_DESCRIPTORS.add(descriptor);
			}
		}
	}

	/**
	 * Unregisters the given {@link IWorkspaceResolverFactoryDescriptor}.
	 * 
	 * @param descriptor
	 *            the {@link IWorkspaceResolverFactoryDescriptor}
	 */
	public static void unregisterWorkspaceResolverFactory(IWorkspaceResolverFactoryDescriptor descriptor) {
		if (descriptor != null) {
			synchronized(WORKSPACE_RESOLVER_FACTORY_DESCRIPTORS) {
				WORKSPACE_RESOLVER_FACTORY_DESCRIPTORS.remove(descriptor);
			}
		}
	}

}
