/*******************************************************************************
 *  Copyright (c) 2017 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/

package org.eclipse.acceleo.aql.ide;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.Module;
import org.eclipse.acceleo.ModuleElement;
import org.eclipse.acceleo.Template;
import org.eclipse.acceleo.aql.ide.resolver.EclipseQualifiedNameResolver;
import org.eclipse.acceleo.aql.ide.resolver.IResolverFactoryDescriptor;
import org.eclipse.acceleo.aql.ide.resolver.ResolverFactoryRegistryListener;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.resolver.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
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
public class Activator extends EMFPlugin {

	/**
	 * Plugin's id.
	 */
	public static final String PLUGIN_ID = "org.eclipse.acceleo.aql.ide"; //$NON-NLS-1$

	/**
	 * The shared instance.
	 */
	public static final Activator INSTANCE = new Activator();

	/**
	 * The implementation plugin for Eclipse.
	 */
	private static Implementation plugin;

	/**
	 * The {@link List} of {@link #registerResolverFactory(IResolverFactoryDescriptor) registered}
	 * {@link IResolverFactoryDescriptor}.
	 */
	private static final List<IResolverFactoryDescriptor> RESOLVER_FACTORY_DESCRIPTORS = new ArrayList<>();

	/**
	 * The constructor.
	 */
	public Activator() {
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
		 * @param queryEnvironment
		 *            the {@link IReadOnlyQueryEnvironment}
		 * @param project
		 *            the {@link IProject}
		 * @return the {@link IQualifiedNameResolver} for the given {@link IProject}
		 */
		public IQualifiedNameResolver createQualifiedNameResolver(IReadOnlyQueryEnvironment queryEnvironment,
				IProject project) {
			final IQualifiedNameResolver res;

			final List<IResolverFactoryDescriptor> factoryDescriptors;
			synchronized(RESOLVER_FACTORY_DESCRIPTORS) {
				factoryDescriptors = new ArrayList<IResolverFactoryDescriptor>(RESOLVER_FACTORY_DESCRIPTORS);
			}

			if (factoryDescriptors.isEmpty()) {
				res = new EclipseQualifiedNameResolver(getClass().getClassLoader(), queryEnvironment,
						project);
			} else {
				res = factoryDescriptors.get(0).getFactory().createResolver(queryEnvironment, project);
			}

			return res;
		}

	}

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance.
	 */
	public static Activator getDefault() {
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
		Activator.INSTANCE.log(new Status(severity, PLUGIN_ID, exception.getMessage(), exception));
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
		Activator.INSTANCE.log(new Status(severity, PLUGIN_ID, errorMessage));
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
	 * Tells if the given resource is a Acceleo {@link Module} with a {@link Template#isMain() main template}.
	 * 
	 * @param resource
	 *            the {@link IResource}
	 * @return <code>true</code> if the given resource is a Acceleo {@link Module} with a
	 *         {@link Template#isMain() main template}, <code>false</code> otherwise
	 */
	public static boolean isAcceleoMain(IResource resource) {
		boolean res = false;

		final AcceleoParser parser = new AcceleoParser(Query.newEnvironment());
		final IFile file = (IFile)resource;
		try (InputStream contents = file.getContents()) {
			final Module module = parser.parse(contents, Charset.forName(file.getCharset()), "none")
					.getModule();
			for (ModuleElement element : module.getModuleElements()) {
				if (element instanceof Template && ((Template)element).isMain()) {
					res = true;
					break;
				}
			}
		} catch (IOException e) {
			Activator.getPlugin().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "couldn't parse module "
					+ resource.getFullPath(), e));
		} catch (CoreException e) {
			Activator.getPlugin().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "couldn't parse module "
					+ resource.getFullPath(), e));
		}

		return res;
	}

}