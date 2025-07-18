/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.query.ide.runtime.impl.namespace.workspace;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.acceleo.query.ide.QueryPlugin;
import org.eclipse.acceleo.query.ide.runtime.namespace.workspace.IWorkspaceResolverProvider;
import org.eclipse.acceleo.query.runtime.impl.namespace.JavaLoader;
import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryProject;
import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryWorkspace;
import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryWorkspaceQualifiedNameResolver;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ICoreRunnable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;

/**
 * Synchronizer that scan the initial state of the {@link IWorkspace} and listen to its changes.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class Synchronizer<P extends IQueryProject> implements IResourceVisitor, IResourceChangeListener, IResourceDeltaVisitor, IWorkspaceResolverProvider {

	/**
	 * Counts {@link IResource} in the workspace.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private final class InitialResourceCollector implements IResourceVisitor {

		/**
		 * The {@link List} of {@link IResource} to synchronize during initialization.
		 */
		private final List<IResource> resourcesToInitialize = new ArrayList<>();

		@Override
		public boolean visit(IResource resource) throws CoreException {
			final boolean res;

			final IProject project = resource.getProject();
			if (project == null || project.isOpen()) {
				res = shouldInitializationSynchronize(project);
				if (resource.getType() == IResource.FILE && extensions.contains(resource
						.getFileExtension())) {
					if (JavaLoader.CLASS.equals(resource.getFileExtension())) {
						// we register Java classes first to get a chance to register any EPackages generated
						// in the workspace
						resourcesToInitialize.add(0, resource);
					} else {
						resourcesToInitialize.add(resource);
					}
				} else if (resource.getType() == IResource.PROJECT) {
					resourcesToInitialize.add(resource);
				}
			} else {
				res = resource instanceof IWorkspaceRoot;
			}

			return res;
		}

		/**
		 * Gets the {@link List} of {@link IResource}
		 * 
		 * @return
		 */
		public List<IResource> getResourcesToInitialize() {
			return resourcesToInitialize;
		}
	}

	/**
	 * The Eclipse {@link IWorkspace}.
	 */
	private final IWorkspace eclipseWorkspace;

	/**
	 * The {@link IQueryWorkspace} to synchronize.
	 */
	private final IQueryWorkspace<P> queryWorkspace;

	/**
	 * The {@link Set} of resource extensions to check.
	 */
	private final Set<String> extensions;

	/**
	 * The mapping from {@link IProject} to project.
	 */
	private final Map<IProject, P> eclipseToProject = new HashMap<>();

	/**
	 * The mapping from project to {@link IProject}.
	 */
	private final Map<P, IProject> projectToEclipse = new HashMap<>();

	/**
	 * The {@link IProgressMonitor}.
	 */
	private IProgressMonitor monitor;

	/**
	 * @param queryWorkspace
	 */
	public Synchronizer(IWorkspace eclipseWorkspace, IQueryWorkspace<P> queryWorkspace) {
		this.eclipseWorkspace = eclipseWorkspace;
		this.queryWorkspace = queryWorkspace;
		extensions = queryWorkspace.getExtensions();
	}

	/**
	 * Tells if we should synchronize the given {@link IProject} at initialization.
	 * 
	 * @param project
	 *            the {@link IProject}
	 * @return <code>true</code> if we should synchronize the given {@link IProject} at initialization,
	 *         <code>false</code> otherwise
	 */
	protected abstract boolean shouldInitializationSynchronize(IProject project);

	/**
	 * Starts synchronizing.
	 */
	public void synchronize() {
		final Job synchronizeJob = Job.create("Synchronizing " + queryWorkspace.getName(),
				new ICoreRunnable() {

					@Override
					public void run(IProgressMonitor monitor) throws CoreException {
						try {
							for (IProject project : eclipseWorkspace.getRoot().getProjects()) {
								if (project.isAccessible()) {
									getOrCreateProject(getQueryWorkspace(), project);
								}
							}

							// Keeping up-to-date with the workspace changes.
							eclipseWorkspace.addResourceChangeListener(Synchronizer.this);

							// walk the workspace for current state

							final InitialResourceCollector initialResources = new InitialResourceCollector();
							eclipseWorkspace.getRoot().accept(initialResources);
							final List<IResource> resourcesToInitialize = initialResources
									.getResourcesToInitialize();
							final SubMonitor subMonitor = SubMonitor.convert(monitor, "Validating",
									resourcesToInitialize.size());
							Synchronizer.this.monitor = subMonitor;
							try {
								for (IResource resource : resourcesToInitialize) {
									Synchronizer.this.visit(resource);
									if (Synchronizer.this.monitor.isCanceled()) {
										break;
									}
								}
							} finally {
								Synchronizer.this.monitor.done();
								Synchronizer.this.monitor = null;
							}
						} catch (Exception e) {
							dispose();
							QueryPlugin.INSTANCE.log(new Status(IStatus.ERROR, getClass(),
									"can't visit the workspace current state: " + eclipseWorkspace.getRoot()
											.getLocationURI().toString(), e));
						}
					}
				});
		synchronizeJob.schedule();
	}

	/**
	 * Disposes the synchronizer.
	 */
	public void dispose() {
		eclipseWorkspace.removeResourceChangeListener(this);
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		if (event.getType() != IResourceChangeEvent.POST_CHANGE) {
			return;
		}
		IResourceDelta delta = event.getDelta();
		try {
			delta.accept(this);
		} catch (CoreException coreException) {
			final URI workspaceURI = this.eclipseWorkspace.getRoot().getLocationURI();
			QueryPlugin.INSTANCE.log(new Status(IStatus.ERROR, getClass(),
					"There was an issue while updating workspace" + workspaceURI, coreException));
		}
	}

	@Override
	public boolean visit(IResourceDelta delta) throws CoreException {
		if (delta.getFlags() == IResourceDelta.MARKERS) {
			// Only markers have changed.
			// Do nothing.
		} else {
			IResource resource = delta.getResource();
			if (resource.getType() == IResource.PROJECT) {
				IProject workspaceProject = (IProject)resource;
				visitProjectDelta(delta, workspaceProject);
			} else if (resource.getType() == IResource.FILE && extensions.contains(resource
					.getFileExtension())) {
				final IFile file = (IFile)resource;
				visitFileDelta(delta, file);
			}
		}

		return true;
	}

	/**
	 * When an {@link IFile} of the client workspace changes.
	 * 
	 * @param delta
	 *            the (non-{@code null}) {@link IResourceDelta}.
	 * @param file
	 *            the (non-{@code null}) {@link IFile}.
	 */
	private void visitFileDelta(IResourceDelta delta, IFile file) {
		if (delta.getKind() == IResourceDelta.CHANGED) {
			if ((delta.getFlags() & IResourceDelta.CONTENT) != 0) {
				// The contents of the IFile have changed.
				this.queryWorkspace.changeResource(getProject(file.getProject()), file.getLocationURI());
			}
			if ((delta.getFlags() & IResourceDelta.ENCODING) != 0) {
				// The encoding of the IFile has changed which means its contents have changed.
				this.queryWorkspace.changeResource(getProject(file.getProject()), file.getLocationURI());
			}
			if ((delta.getFlags() & IResourceDelta.MOVED_FROM) != 0) {
				// The location of the IFile has moved, maybe even changed container project, so
				// re-compute it.
				// TODO optimize move by calling moveResource() to limit validations
				this.queryWorkspace.changeResource(getProject(file.getProject()), file.getLocationURI());
			}
			if ((delta.getFlags() & IResourceDelta.MOVED_TO) != 0) {
				// Do nothing, this is a "ghost" from a past IFile.
			}
			if ((delta.getFlags() & IResourceDelta.REPLACED) != 0) {
				// Re-compute the IFile->AcceleoTextDocument.
				this.queryWorkspace.changeResource(getProject(file.getProject()), file.getLocationURI());
			}
		} else if (delta.getKind() == IResourceDelta.ADDED) {
			this.queryWorkspace.addResource(getProject(file.getProject()), file.getLocationURI());
		} else if (delta.getKind() == IResourceDelta.REMOVED) {
			this.queryWorkspace.removeResource(getProject(file.getProject()), file.getLocationURI());
		}
	}

	/**
	 * When an {@link IProject} of the client workspace changes.
	 * 
	 * @param delta
	 *            the (non-{@code null}) {@link IResourceDelta}.
	 * @param eclipseProject
	 *            the (non-{@code null}) {@link IProject}.
	 */
	private void visitProjectDelta(IResourceDelta delta, IProject eclipseProject) {
		if (delta.getKind() == IResourceDelta.CHANGED) {
			if ((delta.getFlags() & IResourceDelta.ENCODING) != 0) {
				// Changing the encoding of an IProject may affect the encoding of all its
				// contained
				// IFiles.
				this.queryWorkspace.removeProject(getProject(eclipseProject));
				this.queryWorkspace.addProject(getProject(eclipseProject));
			}
			if ((delta.getFlags() & IResourceDelta.MOVED_FROM) != 0) {
				// The location of the IProject has changed
				// TODO optimize this in QueryWorkspace like resource move
				this.queryWorkspace.removeProject(getProject(eclipseProject));
				this.queryWorkspace.addProject(getProject(eclipseProject));
			}
			if ((delta.getFlags() & IResourceDelta.MOVED_TO) != 0) {
				// Do nothing, this is a "ghost" from a past IProject.
			}
			if ((delta.getFlags() & IResourceDelta.REPLACED) != 0) {
				// Re-compute the IProject.
				this.queryWorkspace.removeProject(getProject(eclipseProject));
				this.queryWorkspace.addProject(getProject(eclipseProject));
			}
		} else if (delta.getKind() == IResourceDelta.ADDED) {
			synchronized(this) {
				getOrCreateProject(queryWorkspace, eclipseProject);
			}
		} else if (delta.getKind() == IResourceDelta.REMOVED) {
			final P removedProject = eclipseToProject.remove(eclipseProject);
			projectToEclipse.remove(removedProject);
			this.queryWorkspace.removeProject(removedProject);
		}
	}

	@Override
	public boolean visit(IResource resource) throws CoreException {
		final boolean res;

		final IProject project = resource.getProject();
		if (project == null || project.isOpen()) {
			final boolean hasMonitor = monitor != null;
			if (hasMonitor) {
				monitor.subTask(resource.getFullPath().toString());
			}
			try {
				add(resource);
			} finally {
				if (hasMonitor) {
					monitor.worked(1);
				}
			}
			res = true;
		} else {
			res = resource instanceof IWorkspaceRoot;
		}

		return res;
	}

	/**
	 * Synchronizes the given {@link IResource}.
	 * 
	 * @param resource
	 *            the (non-{@code null}) {@link IResource} to synchronize.
	 */
	public void add(IResource resource) {
		Objects.requireNonNull(resource);
		if (resource.getType() == IResource.PROJECT) {
			final IProject eclipseProject = (IProject)resource;
			synchronized(this) {
				getOrCreateProject(queryWorkspace, eclipseProject);
			}
		} else if (resource.getType() == IResource.FILE && extensions.contains(resource.getFileExtension())) {
			final IFile file = (IFile)resource;
			final URI uri = file.getLocationURI();
			synchronized(this) {
				this.queryWorkspace.addResource(getOrCreateProject(queryWorkspace, file.getProject()), uri);
			}
		}
	}

	@Override
	public IQueryWorkspaceQualifiedNameResolver getResolver(IProject project) {
		return queryWorkspace.getResolver(eclipseToProject.getOrDefault(project, getOrCreateProject(
				queryWorkspace, project)));
	}

	/**
	 * Gets the project for the given {@link IProject}.
	 * 
	 * @param project
	 *            the {@link IProject}
	 * @return the project
	 */
	public P getProject(IProject project) {
		return eclipseToProject.get(project);
	}

	/**
	 * Gets the {@link IProject} for the given project.
	 * 
	 * @param project
	 *            the project
	 * @return the {@link IProject}
	 */
	public IProject getProject(P project) {
		return projectToEclipse.get(project);
	}

	/**
	 * Gets the {@link IQueryWorkspace}.
	 * 
	 * @return the {@link IQueryWorkspace}
	 */
	public IQueryWorkspace<P> getQueryWorkspace() {
		return queryWorkspace;
	}

	/**
	 * Gets the Eclipse {@link IWorkspace}.
	 * 
	 * @return the Eclipse {@link IWorkspace}
	 */
	public IWorkspace getEclipseWorkspace() {
		return eclipseWorkspace;
	}

	public P getOrCreateProject(IQueryWorkspace<P> queryWorkspace, IProject eclipseProject) {
		final P res;

		if (eclipseToProject.containsKey(eclipseProject)) {
			res = eclipseToProject.get(eclipseProject);
		} else {
			res = createProject(queryWorkspace, eclipseProject);
			final P oldProject = eclipseToProject.put(eclipseProject, res);
			projectToEclipse.remove(oldProject);
			projectToEclipse.put(res, eclipseProject);
			queryWorkspace.addProject(res);
		}

		return res;
	}

	public IProject getOrCreateProject(P project) {
		final IProject res;

		if (projectToEclipse.containsKey(project)) {
			res = projectToEclipse.get(project);
		} else {
			res = eclipseWorkspace.getRoot().getProject(project.getName());
			final P oldProject = eclipseToProject.put(res, project);
			projectToEclipse.remove(oldProject);
			projectToEclipse.put(project, res);
		}

		return res;
	}

	/**
	 * Creates a project from the given {@link IProject}.
	 * 
	 * @param the
	 *            {@link IQueryWorkspace}
	 * @param eclipseProject
	 *            the {@link IProject} form Eclipse
	 * @return the created project from the given {@link IProject}
	 */
	protected abstract P createProject(IQueryWorkspace<P> queryWorkspace, IProject eclipseProject);

}
