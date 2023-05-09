/*******************************************************************************
 *  Copyright (c) 2020, 2021 Obeo. 
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
import org.eclipse.acceleo.query.ide.runtime.namespace.IResolverFactoryDescriptor;
import org.eclipse.acceleo.query.runtime.impl.namespace.JavaLoader;
import org.eclipse.acceleo.query.runtime.namespace.ILoader;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
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
		}

		/*
		 * (non-Javadoc)
		 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
		 */
		@Override
		public void stop(BundleContext context) throws Exception {
			super.stop(context);

			final IExtensionRegistry registry = Platform.getExtensionRegistry();
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
		 * @return the {@link IQualifiedNameResolver} for the given {@link IProject}
		 */
		public IQualifiedNameResolver createQualifiedNameResolver(ClassLoader classLoader, IProject project,
				String qualifierSeparator) {
			final IQualifiedNameResolver res;

			final List<IResolverFactoryDescriptor> factoryDescriptors;
			synchronized(RESOLVER_FACTORY_DESCRIPTORS) {
				factoryDescriptors = new ArrayList<IResolverFactoryDescriptor>(RESOLVER_FACTORY_DESCRIPTORS);
			}

			if (factoryDescriptors.isEmpty()) {
				res = new EclipseQualifiedNameResolver(classLoader, project, qualifierSeparator);
			} else {
				res = factoryDescriptors.get(0).getFactory().createResolver(classLoader, project,
						qualifierSeparator);
			}

			return res;
		}

		/**
		 * Creates a Java {@link ILoader} with the given qualified name separator.
		 * 
		 * @param qualifierSeparator
		 *            the qualifier name separator
		 * @return the created Java {@link ILoader}
		 */
		public ILoader createJavaLoader(String qualifierSeparator) {
			final ILoader res;

			final List<IResolverFactoryDescriptor> factoryDescriptors;
			synchronized(RESOLVER_FACTORY_DESCRIPTORS) {
				factoryDescriptors = new ArrayList<IResolverFactoryDescriptor>(RESOLVER_FACTORY_DESCRIPTORS);
			}

			if (factoryDescriptors.isEmpty()) {
				res = new JavaLoader(qualifierSeparator);
			} else {
				res = factoryDescriptors.get(0).getFactory().createJavaLoader(qualifierSeparator);
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

}
