/*******************************************************************************
 * Copyright (c) 2015 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.ide;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.ide.authoring.AcceleoModelManager;
import org.eclipse.acceleo.common.internal.utils.AcceleoDynamicMetamodelResourceSetImpl;
import org.eclipse.acceleo.common.internal.utils.AcceleoPackageRegistry;
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
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Plugin activator for the common IDE plugin of Acceleo.
 * 
 * @author <a href="mailto:cedric.brun@obeo.fr">Cedric Brun</a>
 * @since 3.6
 */
public class AcceleoCommonIDEPlugin implements BundleActivator {

	/** Listener tracking changes with the workspace ecore files. */
	private final WorkspaceEcoreListener workspaceEcoreListener = new WorkspaceEcoreListener();

	@Override
	public void start(BundleContext context) throws Exception {
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
						if (mightBeInAcceleoScope((IFile)resource)) {
							org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI
									.createPlatformResourceURI(resource.getFullPath().toString(), true);
							AcceleoPackageRegistry.INSTANCE.registerEcorePackages(uri.toString(),
									AcceleoDynamicMetamodelResourceSetImpl.DYNAMIC_METAMODEL_RESOURCE_SET);
						}
					}
				}
				return true;
			}
		});

		AcceleoModelManager.getManager().startup();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		Bundle pdeCoreBundle = Platform.getBundle("org.eclipse.pde.core"); //$NON-NLS-1$
		if (pdeCoreBundle != null) {
			ResourcesPlugin.getWorkspace().removeResourceChangeListener(workspaceEcoreListener);
		}
		AcceleoModelManager.getManager().shutdown();
	}

	/**
	 * return true if the given ecore file might be used by an Acceleo generator meaning : it is at least in a
	 * plugin project.
	 * 
	 * @param ecoreFile
	 *            an IFile which is a .ecore.
	 * @return true if the given ecore file might be used by an Acceleo generator meaning : it is at least in
	 *         a plugin project.
	 */
	private boolean mightBeInAcceleoScope(IFile ecoreFile) {
		try {
			if (ecoreFile.getProject() != null && ecoreFile.getProject().getDescription() != null) {
				for (String nature : ecoreFile.getProject().getDescription().getNatureIds()) {
					if (IAcceleoConstants.PLUGIN_NATURE_ID.equals(nature)) {
						return true;
					}
				}
			}
		} catch (CoreException e) {
			/*
			 * If there is a problem accessing the project description we probably just want to ignore this
			 * file.
			 */
		}

		return false;
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
						if (resource instanceof IFile && mightBeInAcceleoScope((IFile)resource)) {
							org.eclipse.emf.common.util.URI uri = org.eclipse.emf.common.util.URI
									.createPlatformResourceURI(resource.getFullPath().toString(), true);
							AcceleoPackageRegistry.INSTANCE.registerEcorePackages(uri.toString(),
									AcceleoDynamicMetamodelResourceSetImpl.DYNAMIC_METAMODEL_RESOURCE_SET);
						}
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
