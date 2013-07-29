/*******************************************************************************
 * Copyright (c) 2009, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.common.internal.utils.AcceleoDynamicMetamodelResourceSetImpl;
import org.eclipse.acceleo.common.internal.utils.AcceleoLibrariesEclipseUtil;
import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
import org.eclipse.acceleo.common.internal.utils.AcceleoServicesEclipseUtil;
import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoModelManager;
import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.common.library.connector.ILibrary;
import org.eclipse.acceleo.common.preference.AcceleoPreferences;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AcceleoCommonPlugin extends Plugin {
	/** Name of the extension point to parse for other libraries. */
	public static final String LIBRARIES_EXTENSION_POINT = "org.eclipse.acceleo.common.libraries"; //$NON-NLS-1$

	/** Name of the extension point to parse for other languages queries. */
	public static final String LIBRARY_CONNECTORS_EXTENSION_POINT = "org.eclipse.acceleo.common.library.connectors"; //$NON-NLS-1$

	/** The plug-in ID. */
	public static final String PLUGIN_ID = "org.eclipse.acceleo.common"; //$NON-NLS-1$

	/**
	 * Name of the extension point to parse for service classes.
	 * 
	 * @deprecated this extension point has been deleted
	 */
	@Deprecated
	public static final String SERVICES_EXTENSION_POINT = "org.eclipse.acceleo.common.java.services"; //$NON-NLS-1$

	/** Exact name of the "class" tag of the extension point. */
	private static final String CLASS_TAG_NAME = "class"; //$NON-NLS-1$

	/** Exact name of the "fileExtension" tag of the extension point. */
	private static final String FILE_EXTENSION_TAG_NAME = "fileExtension"; //$NON-NLS-1$

	/** Exact name of the "file" tag of the extension point. */
	private static final String FILE_TAG_NAME = "file"; //$NON-NLS-1$

	/** This plug-in's shared instance. */
	private static AcceleoCommonPlugin plugin;

	/** Keeps a reference to this bundle's context. */
	private BundleContext context;

	/** The registry listener that will be used to listen to Acceleo library connector changes. */
	private final AcceleoLibraryConnectorsRegistryListener librariesConnectorListener = new AcceleoLibraryConnectorsRegistryListener();

	/** The registry listener that will be used to listen to Acceleo libraries changes. */
	private final AcceleoLibrariesRegistryListener librariesListener = new AcceleoLibrariesRegistryListener();

	/** Listener tracking changes with the workspace ecore files. */
	private final WorkspaceEcoreListener workspaceEcoreListener = new WorkspaceEcoreListener();

	/**
	 * Default constructor for the plugin.
	 */
	public AcceleoCommonPlugin() {
		plugin = this;
	}

	/**
	 * Returns the shared instance.
	 * 
	 * @return the shared instance
	 */
	public static AcceleoCommonPlugin getDefault() {
		return plugin;
	}

	/**
	 * Trace an Exception in the error log.
	 * 
	 * @param e
	 *            Exception to log.
	 * @param blocker
	 *            <code>True</code> if the exception must be logged as error, <code>False</code> to log it as
	 *            a warning.
	 */
	public static void log(Exception e, boolean blocker) {
		if (e == null) {
			throw new NullPointerException(AcceleoCommonMessages
					.getString("AcceleoCommonPlugin.LogNullException")); //$NON-NLS-1$
		}

		if (getDefault() == null) {
			// We are out of eclipse. Prints the stack trace on standard error.
			// CHECKSTYLE:OFF
			e.printStackTrace();
			// CHECKSTYLE:ON
		} else if (e instanceof CoreException) {
			log(((CoreException)e).getStatus());
		} else if (e instanceof NullPointerException) {
			int severity = IStatus.WARNING;
			if (blocker) {
				severity = IStatus.ERROR;
			}
			log(new Status(severity, PLUGIN_ID, severity, AcceleoCommonMessages
					.getString("AcceleoCommonPlugin.ElementNotFound"), e)); //$NON-NLS-1$
		} else {
			int severity = IStatus.WARNING;
			if (blocker) {
				severity = IStatus.ERROR;
			}
			log(new Status(severity, PLUGIN_ID, severity, e.getMessage(), e));
		}
	}

	/**
	 * Puts the given status in the error log view.
	 * 
	 * @param status
	 *            Error Status.
	 */
	public static void log(IStatus status) {
		// Eclipse platform displays NullPointer on standard error instead of throwing it.
		// We'll handle this by throwing it ourselves.
		if (status == null) {
			throw new NullPointerException(AcceleoCommonMessages
					.getString("AcceleoCommonPlugin.LogNullStatus")); //$NON-NLS-1$
		}

		if (getDefault() != null) {
			getDefault().getLog().log(status);
		} else {
			// We are out of eclipse. Prints the message on standard error.
			// CHECKSTYLE:OFF
			System.err.println(status.getMessage());
			status.getException().printStackTrace();
			// CHECKSTYLE:ON
		}
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
		if (getDefault() == null) {
			// We are out of eclipse. Prints the message on standard error.
			// CHECKSTYLE:OFF
			System.err.println(message);
			// CHECKSTYLE:ON
		} else {
			int severity = IStatus.WARNING;
			if (blocker) {
				severity = IStatus.ERROR;
			}
			String errorMessage = message;
			if (errorMessage == null || "".equals(errorMessage)) { //$NON-NLS-1$
				errorMessage = AcceleoCommonMessages.getString("AcceleoCommonPlugin.UnexpectedException"); //$NON-NLS-1$
			}
			log(new Status(severity, PLUGIN_ID, errorMessage));
		}
	}

	/**
	 * Traces an exception in the error log with the given log message.
	 * <p>
	 * This is a convenience method fully equivalent to using
	 * <code>log(new Status(int, PLUGIN_ID, message, cause)</code>.
	 * </p>
	 * 
	 * @param message
	 *            The message that is to be displayed in the error log view.
	 * @param cause
	 *            Exception that is to be logged.
	 * @param blocker
	 *            <code>True</code> if the exception must be logged as error, <code>False</code> to log it as
	 *            a warning.
	 * @since 0.8
	 */
	public static void log(String message, Exception cause, boolean blocker) {
		final int severity;
		if (blocker) {
			severity = IStatus.ERROR;
		} else {
			severity = IStatus.WARNING;
		}
		log(new Status(severity, PLUGIN_ID, message, cause));
	}

	/**
	 * Returns this bundle's context.
	 * 
	 * @return This bundle's context.
	 */
	public BundleContext getContext() {
		return context;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.Plugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(final BundleContext bundleContext) throws Exception {
		super.start(bundleContext);
		AcceleoWorkspaceUtil.INSTANCE.initialize();

		Bundle pdeCoreBundle = Platform.getBundle("org.eclipse.pde.core"); //$NON-NLS-1$
		if (pdeCoreBundle != null) {
			ResourcesPlugin.getWorkspace().addResourceChangeListener(
					workspaceEcoreListener,
					IResourceChangeEvent.PRE_CLOSE | IResourceChangeEvent.PRE_DELETE
							| IResourceChangeEvent.POST_CHANGE);
		}

		ResourcesPlugin.getWorkspace().getRoot().accept(new IResourceVisitor() {

			public boolean visit(IResource resource) throws CoreException {
				if (resource instanceof IFile) {
					if (resource instanceof IFile
							&& ((IFile)resource).getFileExtension() != null
							&& ((IFile)resource).getFileExtension().equals(
									IAcceleoConstants.ECORE_FILE_EXTENSION)) {
						URI uri = URI.createPlatformResourceURI(resource.getFullPath().toString(), true);
						AcceleoPackageRegistry.INSTANCE.registerEcorePackages(uri.toString(),
								AcceleoDynamicMetamodelResourceSetImpl.DYNAMIC_METAMODEL_RESOURCE_SET);
					}
				}
				return true;
			}
		});

		context = bundleContext;
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		registry.addListener(librariesConnectorListener, LIBRARY_CONNECTORS_EXTENSION_POINT);
		registry.addListener(librariesListener, LIBRARIES_EXTENSION_POINT);
		parseInitialContributions();

		AcceleoModelManager.getManager().startup();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(final BundleContext bundleContext) throws Exception {
		try {
			final IExtensionRegistry registry = Platform.getExtensionRegistry();
			registry.removeListener(librariesConnectorListener);
			registry.removeListener(librariesListener);

			Bundle pdeCoreBundle = Platform.getBundle("org.eclipse.pde.core"); //$NON-NLS-1$
			if (pdeCoreBundle != null) {
				ResourcesPlugin.getWorkspace().removeResourceChangeListener(workspaceEcoreListener);

			}
			AcceleoServicesEclipseUtil.clearRegistry();
			AcceleoLibraryConnectorsRegistry.INSTANCE.clearRegistry();
			AcceleoLibrariesEclipseUtil.clearRegistry();
			AcceleoWorkspaceUtil.INSTANCE.dispose();
			AcceleoPreferences.save();
			plugin = null;
			context = null;

			AcceleoModelManager.getManager().shutdown();
		} finally {
			super.stop(bundleContext);
		}
	}

	/**
	 * Though we have listeners on the provided extension points, there could have been contributions before
	 * this plugin got started and listeners installed. This will parse them.
	 */
	@SuppressWarnings("unchecked")
	private void parseInitialContributions() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		for (IExtension extension : registry.getExtensionPoint(LIBRARY_CONNECTORS_EXTENSION_POINT)
				.getExtensions()) {
			for (IConfigurationElement service : extension.getConfigurationElements()) {
				try {
					AcceleoLibraryConnectorsRegistry.INSTANCE.addLibraryConnector((Class<ILibrary>)service
							.createExecutableExtension(CLASS_TAG_NAME).getClass(), service
							.getAttribute(FILE_EXTENSION_TAG_NAME));
				} catch (CoreException e) {
					log(e, false);
				}
			}
		}

		for (IExtension extension : registry.getExtensionPoint(LIBRARIES_EXTENSION_POINT).getExtensions()) {
			for (IConfigurationElement library : extension.getConfigurationElements()) {
				String pathToFile = library.getAttribute(FILE_TAG_NAME);

				Class<ILibrary> libClass = AcceleoLibraryConnectorsRegistry.INSTANCE
						.getConnectorForResource(pathToFile);
				if (libClass != null) {
					try {
						ILibrary lib = libClass.newInstance();
						lib.setURI(URI.createFileURI(ResourcesPlugin.getWorkspace().getRoot().getProject(
								extension.getContributor().getName()).getFile(pathToFile).getLocation()
								.toString()));
						AcceleoLibrariesEclipseUtil.addLibrary(lib);
					} catch (InstantiationException e) {
						log(e, true);
					} catch (IllegalAccessException e) {
						log(e, true);
					}
				} else {
					log(AcceleoCommonMessages.getString("AcceleoCommonPlugin.MissingHandle", pathToFile), //$NON-NLS-1$
							false);
				}
			}
		}
	}

	/**
	 * This will allow us to be aware of changes of extension against the Acceleo library connector extension
	 * point.
	 */
	final class AcceleoLibrariesRegistryListener implements IRegistryEventListener {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtension[])
		 */
		public void added(IExtension[] extensions) {
			for (IExtension extension : extensions) {
				for (IConfigurationElement service : extension.getConfigurationElements()) {
					String pathToFile = service.getAttribute(FILE_TAG_NAME);
					Class<ILibrary> libClass = AcceleoLibraryConnectorsRegistry.INSTANCE
							.getConnectorForResource(pathToFile);
					if (libClass != null) {
						try {
							ILibrary lib = libClass.newInstance();
							lib.setURI(URI.createFileURI(ResourcesPlugin.getWorkspace().getRoot().getProject(
									extension.getContributor().getName()).getFile(pathToFile).getLocation()
									.toString()));
							AcceleoLibrariesEclipseUtil.addLibrary(lib);
						} catch (InstantiationException e) {
							log(e, true);
						} catch (IllegalAccessException e) {
							log(e, true);
						}
					} else {
						log(AcceleoCommonMessages.getString("AcceleoCommonPlugin.MissingHandle", pathToFile), //$NON-NLS-1$
								false);
					}
				}
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtensionPoint[])
		 */
		public void added(IExtensionPoint[] extensionPoints) {
			// no need to listen to this
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtension[])
		 */
		public void removed(IExtension[] extensions) {
			for (IExtension extension : extensions) {
				for (IConfigurationElement service : extension.getConfigurationElements()) {
					AcceleoLibrariesEclipseUtil.removeLibrary(service.getAttribute(FILE_TAG_NAME));
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
	}

	/**
	 * This will allow us to be aware of changes of extension against the Acceleo library connector extension
	 * point.
	 */
	final class AcceleoLibraryConnectorsRegistryListener implements IRegistryEventListener {
		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtension[])
		 */
		@SuppressWarnings("unchecked")
		public void added(IExtension[] extensions) {
			for (IExtension extension : extensions) {
				for (IConfigurationElement service : extension.getConfigurationElements()) {
					try {
						AcceleoLibraryConnectorsRegistry.INSTANCE
								.addLibraryConnector((Class<ILibrary>)service.createExecutableExtension(
										CLASS_TAG_NAME).getClass(), service
										.getAttribute(FILE_EXTENSION_TAG_NAME));
					} catch (CoreException e) {
						log(e, false);
					}
				}
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.core.runtime.IRegistryEventListener#added(org.eclipse.core.runtime.IExtensionPoint[])
		 */
		public void added(IExtensionPoint[] extensionPoints) {
			// no need to listen to this
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.core.runtime.IRegistryEventListener#removed(org.eclipse.core.runtime.IExtension[])
		 */
		public void removed(IExtension[] extensions) {
			for (IExtension extension : extensions) {
				for (IConfigurationElement service : extension.getConfigurationElements()) {
					AcceleoLibraryConnectorsRegistry.INSTANCE.removeLibraryConnector(service
							.getAttribute(CLASS_TAG_NAME));
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
	}

	/**
	 * Allows us to react to changes in the dynamic ecore models.
	 * 
	 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
	 */
	private class WorkspaceEcoreListener implements IResourceChangeListener {
		/**
		 * Default constructor.
		 */
		WorkspaceEcoreListener() {
			// Increases visibility
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
		 */
		public void resourceChanged(IResourceChangeEvent event) {
			switch (event.getType()) {
			/*
			 * Project closing and deletion must trigger the removal of its models from the dynamic registry.
			 * Model deletion must trigger its removal from the dynamic registry. Model change must trigger
			 * its removal and re-adding in the registry. Model creation must trigger its addition in the
			 * registry.
			 */
				case IResourceChangeEvent.PRE_CLOSE:
				case IResourceChangeEvent.PRE_DELETE:
					if (event.getResource() instanceof IProject) {
						try {
							List<IFile> ecoreFiles = members((IContainer)event.getResource(),
									IAcceleoConstants.ECORE_FILE_EXTENSION);
							if (!ecoreFiles.isEmpty()) {
								for (IFile ecoreFile : ecoreFiles) {
									AcceleoPackageRegistry.INSTANCE.unregisterEcorePackages(ecoreFile
											.getFullPath().toString());
								}
							}
						} catch (CoreException e) {
							AcceleoCommonPlugin.log(e, false);
						}
					}
					break;
				case IResourceChangeEvent.POST_CHANGE:
					IResource resource = null;
					if (event.getResource() != null) {
						resource = event.getResource();
					} else if (getResources(event.getDelta()).size() > 0) {
						List<IResource> resources = getResources(event.getDelta());
						resource = resources.get(0);
					}
					if (resource != null && resource.isAccessible() && resource.getFileExtension() != null
							&& resource.getFileExtension().endsWith(IAcceleoConstants.ECORE_FILE_EXTENSION)) {
						URI uri = URI.createPlatformResourceURI(resource.getFullPath().toString(), true);
						AcceleoPackageRegistry.INSTANCE.registerEcorePackages(uri.toString(),
								AcceleoDynamicMetamodelResourceSetImpl.DYNAMIC_METAMODEL_RESOURCE_SET);
					}
					break;
				default:
					// no default action
			}
		}

		/**
		 * Computes the resources available in a resource delta.
		 * 
		 * @param delta
		 *            the resource delta.
		 * @return The lsit of resources in a resource delta.
		 */
		private List<IResource> getResources(IResourceDelta delta) {
			List<IResource> resources = new ArrayList<IResource>();
			IResourceDelta[] affectedChildren = delta.getAffectedChildren();
			for (IResourceDelta iResourceDelta : affectedChildren) {
				IResource resource = iResourceDelta.getResource();
				if (resource instanceof IFile
						&& ((IFile)resource).getFileExtension() != null
						&& ((IFile)resource).getFileExtension()
								.equals(IAcceleoConstants.ECORE_FILE_EXTENSION)) {
					resources.add(resource);
				}

				resources.addAll(getResources(iResourceDelta));
			}
			return resources;
		}

		/**
		 * Returns a list of existing member files (that validate the file extension) in this resource.
		 * 
		 * @param container
		 *            The container to browse for files with the given extension.
		 * @param extension
		 *            The file extension to browse for.
		 * @return The List of files of the given extension contained by <code>container</code>.
		 * @throws CoreException
		 *             Thrown if we couldn't retrieve the children of <code>container</code>.
		 */
		private List<IFile> members(IContainer container, String extension) throws CoreException {
			List<IFile> output = new ArrayList<IFile>();
			if (container != null && container.isAccessible()) {
				IResource[] children = container.members();
				if (children != null) {
					for (int i = 0; i < children.length; ++i) {
						IResource resource = children[i];
						if (resource instanceof IFile
								&& extension.equals(((IFile)resource).getFileExtension())) {
							output.add((IFile)resource);
						} else if (resource instanceof IContainer) {
							output.addAll(members((IContainer)resource, extension));
						}
					}
				}
			}
			return output;
		}
	}
}
