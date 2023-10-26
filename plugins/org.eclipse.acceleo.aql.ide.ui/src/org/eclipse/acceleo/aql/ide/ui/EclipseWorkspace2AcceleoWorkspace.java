/*******************************************************************************
 * Copyright (c) 2017, 2023 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ide.ui;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.acceleo.aql.ide.AcceleoPlugin;
import org.eclipse.acceleo.aql.ls.services.textdocument.AcceleoTextDocument;
import org.eclipse.acceleo.aql.ls.services.workspace.AcceleoProject;
import org.eclipse.acceleo.aql.ls.services.workspace.AcceleoWorkspace;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.parser.ModuleLoader;
import org.eclipse.acceleo.query.ide.QueryPlugin;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
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
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;

/**
 * Transformation to create an {@link AcceleoWorkspace} from an {@link IWorkspace}. The created
 * {@link AcceleoWorkspace} is kept in sync with the {@link IWorkspace} thanks to a listener.
 * 
 * @author Florent Latombe
 */
public class EclipseWorkspace2AcceleoWorkspace {

	/**
	 * A {@link Map} to keep track of the created {@link AcceleoWorkspace} and their source
	 * {@link IWorkspace}.
	 */
	private final Map<AcceleoWorkspace, IWorkspace> workspaceTrace = new LinkedHashMap<>();

	/**
	 * A {@link Map} to keep track of the {@link SynchronizerEclipseWorkspace2AcceleoWorkspace} which
	 * synchronizes an {@link AcceleoWorkspace} with their originating {@link IWorkspace}.
	 */
	private final Map<AcceleoWorkspace, SynchronizerEclipseWorkspace2AcceleoWorkspace> synchronizerTrace = new LinkedHashMap<>();

	/**
	 * Creates an {@link AcceleoWorkspace} from an {@link IWorkspace}.
	 * 
	 * @param clientWorkspace
	 *            the (non-{@code null}) {@link IWorkspace}.
	 * @return the corresponding {@link AcceleoWorkspace}.
	 */
	public AcceleoWorkspace createAcceleoWorkspace(IWorkspace clientWorkspace) {
		// Step 1: create the AcceleoWorkspace.
		final AcceleoWorkspace createdAcceleoWorkspace = new AcceleoWorkspace(getAcceleoWorkspaceName(
				clientWorkspace));
		this.workspaceTrace.put(createdAcceleoWorkspace, clientWorkspace);

		// Step 2: create the synchronizer that will fill the AcceleoWorkspace depending on the contents of
		// the client Eclipse workspace.
		final SynchronizerEclipseWorkspace2AcceleoWorkspace synchronizer = new SynchronizerEclipseWorkspace2AcceleoWorkspace(
				createdAcceleoWorkspace, clientWorkspace);
		this.synchronizerTrace.put(createdAcceleoWorkspace, synchronizer);

		// Step 3: plug together
		try {
			// Fills the AcceleoWorkspace according to the current workspace state.
			clientWorkspace.getRoot().accept(synchronizer);

			// Keeping up-to-date with the workspace changes.
			clientWorkspace.addResourceChangeListener(synchronizer);

			return createdAcceleoWorkspace;
		} catch (CoreException coreException) {
			this.workspaceTrace.remove(createdAcceleoWorkspace);
			this.synchronizerTrace.remove(createdAcceleoWorkspace);
			throw new RuntimeException("There was an issue while trying to visit the workspace.",
					coreException);
		}
	}

	/**
	 * Provides the {@link String name} to use for the created {@link AcceleoWorkspace} corresponding to an
	 * {@link IWorkspace}.
	 * 
	 * @param clientWorkspace
	 *            the (non-{@code null}) source {@link IWorkspace}.
	 * @return the {@link String name} intended for the {@link AcceleoWorkspace} corresponding to
	 *         {@code clientWorkspace}.
	 */
	private static String getAcceleoWorkspaceName(IWorkspace clientWorkspace) {
		return "AcceleoWorkspace[" + clientWorkspace.getRoot().getLocationURI().toString() + "]";
	}

	/**
	 * Deleting an {@link AcceleoWorkspace} means disconnecting it from its source {@link IWorkspace}.
	 * 
	 * @param acceleoWorkspaceToDelete
	 *            the (non-{@code null}) {@link AcceleoWorkspace} to delete.
	 */
	public void deleteAcceleoWorkspace(AcceleoWorkspace acceleoWorkspaceToDelete) {
		acceleoWorkspaceToDelete.dispose();
		if (this.workspaceTrace.containsKey(acceleoWorkspaceToDelete)) {
			IWorkspace clientWorkspace = this.workspaceTrace.get(acceleoWorkspaceToDelete);
			SynchronizerEclipseWorkspace2AcceleoWorkspace synchronizer = this.synchronizerTrace.get(
					acceleoWorkspaceToDelete);
			clientWorkspace.removeResourceChangeListener(synchronizer);
		}
	}

	/**
	 * A synchronizer that maintains an {@link AcceleoWorkspace} consistent with regards to the
	 * {@link IWorkspace} it is based on.
	 * <ul>
	 * <li>By implementing {@link IResourceVisitor}, we can visit the whole {@link IWorkspace} once to fill
	 * the {@link AcceleoWorkspace} once at startup time.</li>
	 * <li>By implementing {@link IResourceChangeListener} and {@link IResourceDeltaVisitor}, we can listen to
	 * and interpret changes in the {@link IWorkspace} to udpate the {@link AcceleoWorkspace}
	 * accordingly.</li>
	 * </ul>
	 * The mapping is as follows:
	 * <ul>
	 * <li>Each {@link IProject} is represented as an {@link AcceleoProject}, even though it does not contain
	 * any Java or Acceleo files.</li>
	 * <li>Each {@link IFile} that corresponds to an Acceleo file (see
	 * {@link #workspaceFileIsAcceleoTextDocument(IFile)}) is represented as an
	 * {@link AcceleoTextDocument}.</li>
	 * </ul>
	 * 
	 * @author Florent Latombe
	 */
	private static class SynchronizerEclipseWorkspace2AcceleoWorkspace implements IResourceVisitor, IResourceChangeListener, IResourceDeltaVisitor {

		/**
		 * The java nature.
		 */
		private static final String JAVA_NATURE = "org.eclipse.jdt.core.javanature";

		/**
		 * The size of the buffer we use to read {@link IFile Acceleo documents}.
		 */
		private static final int BUFFER_SIZE = 1024;

		/**
		 * The {@link AcceleoWorkspace} to fill while visiting.
		 */
		private final AcceleoWorkspace acceleoWorkspace;

		/**
		 * The synchronized {@link IWorkspace}.
		 */
		private final IWorkspace workspace;

		/**
		 * The {@link Map} that traces the transformation of {@link IProject} into {@link AcceleoProject}.
		 */
		private final Map<IProject, AcceleoProject> projectsTrace = new LinkedHashMap<>();

		/**
		 * The {@link Map} that traces the transformation of {@link IFile} into {@link AcceleoTextDocument}.
		 */
		private final Map<IFile, AcceleoTextDocument> filesTrace = new LinkedHashMap<>();

		/**
		 * Constructor.
		 * 
		 * @param acceleoWorkspaceToFill
		 *            the (non-{@code null}) {@link AcceleoWorkspace} to fill while visiting.
		 * @param workspace
		 *            the (non-{@code null}) visited {@link IWorkspace}.
		 */
		SynchronizerEclipseWorkspace2AcceleoWorkspace(AcceleoWorkspace acceleoWorkspaceToFill,
				IWorkspace workspace) {
			this.acceleoWorkspace = acceleoWorkspaceToFill;
			this.workspace = workspace;
		}

		/**
		 * {@inheritDoc}<br/>
		 * This is called upon first filling the {@link AcceleoWorkspace} with {@link AcceleoProject} and
		 * {@link AcceleoTextDocument} instances based on the {@link IProject} and {@link IFile} found in the
		 * workspace.
		 *
		 * @see org.eclipse.core.resources.IResourceVisitor#visit(org.eclipse.core.resources.IResource)
		 */
		@Override
		public boolean visit(IResource resource) throws CoreException {
			if (resource instanceof IWorkspaceRoot || (resource.getProject().isOpen() && resource.getProject()
					.hasNature(JAVA_NATURE))) {
				this.synchronize(resource);
				return true;
			}
			return false;
		}

		/**
		 * {@inheritDoc}<br/>
		 * This is called whenever the workspace is modified (e.g. file system, operation, etc.) and we need
		 * to update the {@link AcceleoWorkspace} to reflect these changes.
		 *
		 * @see org.eclipse.core.resources.IResourceChangeListener#resourceChanged(org.eclipse.core.resources.IResourceChangeEvent)
		 */
		@Override
		public void resourceChanged(IResourceChangeEvent event) {
			if (event.getType() != IResourceChangeEvent.POST_CHANGE) {
				return;
			}
			IResourceDelta delta = event.getDelta();
			try {
				delta.accept(this);
			} catch (CoreException coreException) {
				throw new RuntimeException("There was an issue while updating " + this.acceleoWorkspace
						.toString() + " to react to changes in the client workspace.", coreException);
			}
		}

		@Override
		public boolean visit(IResourceDelta delta) throws CoreException {
			if (delta.getFlags() == IResourceDelta.MARKERS) {
				// Only markers have changed.
				// Do nothing.
			} else if (delta.getResource().isDerived(IResource.CHECK_ANCESTORS)) {
				IResource resource = delta.getResource();
				if (resource.getType() == IResource.PROJECT) {
					IProject workspaceProject = (IProject)resource;
					visitProjectDelta(delta, workspaceProject);
				} else if (resource.getType() == IResource.FILE) {
					IFile workspaceFile = (IFile)resource;
					visitFileDelta(delta, workspaceFile);
				}
			}

			return true;
		}

		/**
		 * When an {@link IFile} of the client workspace changes.
		 * 
		 * @param delta
		 *            the (non-{@code null}) {@link IResourceDelta}.
		 * @param workspaceFile
		 *            the (non-{@code null}) {@link IFile}.
		 */
		private void visitFileDelta(IResourceDelta delta, IFile workspaceFile) {
			if (delta.getKind() == IResourceDelta.CHANGED) {
				if ((delta.getFlags() & IResourceDelta.CONTENT) != 0) {
					// The contents of the IFile have changed.
					updateFileContents(workspaceFile);
				}
				if ((delta.getFlags() & IResourceDelta.ENCODING) != 0) {
					// The encoding of the IFile has changed which means its contents have changed.
					updateFileContents(workspaceFile);
				}
				if ((delta.getFlags() & IResourceDelta.MOVED_FROM) != 0) {
					// The location of the IFile has moved, maybe even changed container project, so
					// re-compute it.
					this.refresh(workspaceFile);
				}
				if ((delta.getFlags() & IResourceDelta.MOVED_TO) != 0) {
					// Do nothing, this is a "ghost" from a past IFile.
				}
				if ((delta.getFlags() & IResourceDelta.REPLACED) != 0) {
					// Re-compute the IFile->AcceleoTextDocument.
					this.refresh(workspaceFile);
				}
			} else if (delta.getKind() == IResourceDelta.ADDED) {
				this.synchronize(workspaceFile);
			} else if (delta.getKind() == IResourceDelta.REMOVED) {
				this.remove(workspaceFile);
			}
		}

		/**
		 * When an {@link IProject} of the client workspace changes.
		 * 
		 * @param delta
		 *            the (non-{@code null}) {@link IResourceDelta}.
		 * @param workspaceProject
		 *            the (non-{@code null}) {@link IProject}.
		 */
		private void visitProjectDelta(IResourceDelta delta, IProject workspaceProject) {
			if (delta.getKind() == IResourceDelta.CHANGED) {
				if ((delta.getFlags() & IResourceDelta.ENCODING) != 0) {
					// Changing the encoding of an IProject may affect the encoding of all its
					// contained
					// IFiles.
					this.refresh(workspaceProject);
				}
				if ((delta.getFlags() & IResourceDelta.MOVED_FROM) != 0) {
					// The location of the IProject has changed, which has an impact on its
					// corresponding
					// IAcceleoEnvironment.
					this.refresh(workspaceProject);
				}
				if ((delta.getFlags() & IResourceDelta.MOVED_TO) != 0) {
					// Do nothing, this is a "ghost" from a past IProject.
				}
				if ((delta.getFlags() & IResourceDelta.REPLACED) != 0) {
					// Re-compute the IProject->AcceleoProject.
					this.refresh(workspaceProject);
				}
			} else if (delta.getKind() == IResourceDelta.ADDED) {
				this.synchronize(workspaceProject);
			} else if (delta.getKind() == IResourceDelta.REMOVED) {
				this.remove(workspaceProject);
			}
		}

		/**
		 * Refreshes the {@link AcceleoWorkspace}'s knowledge about the given {@link IResource} by completely
		 * removing it (if it was represented) and re-creating a representation.
		 * 
		 * @param workspaceResource
		 *            the (non-{@code null}) {@link IResource}.
		 */
		private void refresh(IResource workspaceResource) {
			this.remove(workspaceResource);
			this.synchronize(workspaceResource);
		}

		/**
		 * Updates the {@link AcceleoWorkspace} to reflect the fact that the given {@link IResource} has been
		 * removed.
		 * 
		 * @param workspaceResource
		 *            the (non-{@code null}) removed {@link IResource}.
		 */
		private void remove(IResource workspaceResource) {
			Objects.requireNonNull(workspaceResource);
			if (workspaceResource.getType() == IResource.PROJECT) {
				IProject workspaceProject = (IProject)workspaceResource;
				this.remove(workspaceProject);
			} else if (workspaceResource.getType() == IResource.FILE) {
				IFile workspaceFile = (IFile)workspaceResource;
				this.remove(workspaceFile);
			}
		}

		/**
		 * Updates the {@link AcceleoWorkspace} as the given {@link IProject} no longer exists.
		 * 
		 * @param removedProject
		 *            the (non-{@code null}) removed {@link IProject}.
		 */
		private void remove(IProject removedProject) {
			if (this.projectsTrace.containsKey(removedProject)) {
				AcceleoProject projectToRemove = this.projectsTrace.get(removedProject);
				this.acceleoWorkspace.removeProject(projectToRemove);
				this.projectsTrace.remove(removedProject);
			}
		}

		/**
		 * Updates the {@link AcceleoWorkspace} as the given {@link IProject} no longer exists.
		 * 
		 * @param removedFile
		 *            the (non-{@code null}) removed {@link IFile}.
		 */
		private void remove(IFile removedFile) {
			final AcceleoTextDocument fileToRemove = this.filesTrace.get(removedFile);
			if (fileToRemove != null) {
				fileToRemove.getProject().removeTextDocument(fileToRemove);
				this.filesTrace.remove(removedFile);
			}
			notifyChange(removedFile.getLocationURI());
		}

		/**
		 * Synchronizes the given {@link IResource} by either creating or updating an element in the
		 * {@link AcceleoWorkspace}.
		 * 
		 * @param workspaceResource
		 *            the (non-{@code null}) {@link IResource} to synchronize.
		 */
		private void synchronize(IResource workspaceResource) {
			Objects.requireNonNull(workspaceResource);
			if (workspaceResource.getType() == IResource.PROJECT) {
				IProject workspaceProject = (IProject)workspaceResource;
				this.synchronize(workspaceProject);
			} else if (workspaceResource.getType() == IResource.FILE) {
				IFile workspaceFile = (IFile)workspaceResource;
				this.synchronize(workspaceFile);
			}
		}

		/**
		 * Synchronizes the given {@link IProject}.
		 * 
		 * @param workspaceProject
		 *            the (non-{@code null}) {@link IProject} to synchronize.
		 */
		private void synchronize(IProject workspaceProject) {
			if (!this.projectsTrace.containsKey(workspaceProject)) {
				// All workspace projects are represented as AcceleoProjects even though they do not have any
				// Acceleo files.
				this.createAcceleoProject(workspaceProject);
			} else {
				this.updateAcceleoProject(workspaceProject);
			}
			updateAllProjectResolvers();
		}

		/**
		 * Updates all project resolvers.
		 */
		private void updateAllProjectResolvers() {
			// FIXME we should pass a project to only update projects that depend (closure) on it.
			for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
				final AcceleoProject acceleoProject = projectsTrace.get(project);
				if (acceleoProject != null) {
					acceleoProject.setResolver(createResolver(acceleoProject.getResolver(), null, project));
				}
			}
		}

		/**
		 * Creates a new {@link AcceleoProject} corresponding to an {@link IProject}.
		 * 
		 * @param workspaceProject
		 *            the (non-{@code null}) source {@link IProject}.
		 */
		private void createAcceleoProject(IProject workspaceProject) {
			AcceleoProject acceleoProject = this.transform(workspaceProject);
			this.projectsTrace.put(workspaceProject, acceleoProject);
			this.acceleoWorkspace.addProject(acceleoProject);
		}

		/**
		 * Updates the {@link AcceleoProject} corresponding to an {@link IProject}.
		 * 
		 * @param workspaceProject
		 *            the (non-{@code null}) source {@link IProject}.
		 */
		private void updateAcceleoProject(IProject workspaceProject) {
			AcceleoProject acceleoProject = this.projectsTrace.get(workspaceProject);

			acceleoProject.setLabel(getAcceleoProjectLabelFor(workspaceProject));
			acceleoProject.setResolver(this.createResolver(acceleoProject.getResolver(), null,
					workspaceProject));

			// The contained documents are updated on their own.
		}

		/**
		 * Synchronizes the given {@link IFile}.
		 * 
		 * @param workspaceFile
		 *            the (non-{@code null}) {@link IFile} to synchronize.
		 */
		private void synchronize(IFile workspaceFile) {
			if (!this.filesTrace.containsKey(workspaceFile)) {
				if (!this.projectsTrace.containsKey(workspaceFile.getProject())) {
					throw new IllegalStateException(
							"Did not expect to synchronize a file from the workspace before its containing project.");
				} else {
					if (workspaceFileIsAcceleoTextDocument(workspaceFile)) {
						final AcceleoProject acceleoProject = projectsTrace.get(workspaceFile.getProject());
						// make sure the derived file exists
						if (acceleoProject.getResolver().getQualifiedName(workspaceFile
								.getLocationURI()) != null) {
							this.createAcceleoTextDocument(workspaceFile);
							notifyChange(workspaceFile.getLocationURI());
						}
					} else if (isDeivedAndModule(workspaceFile)) {
						final AcceleoProject acceleoProject = projectsTrace.get(workspaceFile.getProject());
						if (acceleoProject != null) {
							final String derivedQualifiedName = acceleoProject.getResolver().getQualifiedName(
									workspaceFile.getLocationURI());
							if (derivedQualifiedName != null) {
								final URI srcURI = acceleoProject.getResolver().getSourceURI(
										derivedQualifiedName);
								final IFile srcFile = workspace.getRoot().getFileForLocation(new Path(srcURI
										.getPath()));
								if (!this.filesTrace.containsKey(srcFile)) {
									this.createAcceleoTextDocument(srcFile);
									notifyChange(srcFile.getLocationURI());
								}
							}
						}
					} else if (isDeivedAndNotModule(workspaceFile)) {
						notifyChange(workspaceFile.getLocationURI());
					}
				}
			} else {
				this.updateFileContents(workspaceFile);
			}
		}

		/**
		 * Determines whether an {@link IFile} must be captured as an {@link AcceleoTextDocument} or not.
		 * 
		 * @param workspaceFile
		 *            the (non-{@code null}) candidate {@link IFile}.
		 * @return {@code true} if {@code workspaceFile} must be represented in the {@link AcceleoWorkspace}
		 *         as an {@link AcceleoTextDocument}.
		 */
		private static boolean workspaceFileIsAcceleoTextDocument(IFile workspaceFile) {
			return !workspaceFile.isDerived() && AcceleoParser.MODULE_FILE_EXTENSION.equals(workspaceFile
					.getFileExtension());
		}

		/**
		 * Creates the {@link AcceleoTextDocument} corresponding to an {@link IFile}.
		 * 
		 * @param workspaceFile
		 *            the (non-{@code null}) source {@link IFile}.
		 */
		private void createAcceleoTextDocument(IFile workspaceFile) {
			AcceleoTextDocument acceleoTextDocument = this.transform(workspaceFile);
			this.filesTrace.put(workspaceFile, acceleoTextDocument);
			AcceleoProject containerAcceleoProject = this.projectsTrace.get(workspaceFile.getProject());
			containerAcceleoProject.addTextDocument(acceleoTextDocument);
		}

		/**
		 * Updates the {@link AcceleoWorkspace} for an {@link IFile}.
		 * 
		 * @param workspaceFile
		 *            the (non-{@code null}) {@link IFile} to update.
		 */
		private void updateFileContents(IFile workspaceFile) {
			AcceleoTextDocument acceleoTextDocument = this.filesTrace.get(workspaceFile);
			if (acceleoTextDocument != null) {
				if (!workspaceFileIsAcceleoTextDocument(workspaceFile)) {
					// The workspace file is no longer a file we consider as an Acceleo text document, so we
					// want
					// to remove it from our workspace.
					acceleoTextDocument.getProject().removeTextDocument(acceleoTextDocument);
					this.filesTrace.remove(workspaceFile);
					notifyChange(workspaceFile.getLocationURI());
				} else {
					String workspaceFileContents = readWorkspaceFile(workspaceFile);
					acceleoTextDocument.setContents(workspaceFileContents);
					notifyChange(workspaceFile.getLocationURI());
				}
			} else if (isDeivedAndNotModule(workspaceFile)) {
				notifyChange(workspaceFile.getLocationURI());
			}
		}

		/**
		 * Tells if the given {@link IFile} is derived and not a {@link Module}.
		 * 
		 * @param workspaceFile
		 *            the workspace {@link IFile}
		 * @return <code>true</code> if the given {@link IFile} is derived and not a {@link Module},
		 *         <code>false</code> otherwise
		 */
		private boolean isDeivedAndNotModule(IFile workspaceFile) {
			return workspaceFile.isDerived() && !AcceleoParser.MODULE_FILE_EXTENSION.equals(workspaceFile
					.getFileExtension());
		}

		/**
		 * Tells if the given {@link IFile} is derived and a {@link Module}.
		 * 
		 * @param workspaceFile
		 *            the workspace {@link IFile}
		 * @return <code>true</code> if the given {@link IFile} is derived and a {@link Module},
		 *         <code>false</code> otherwise
		 */
		private boolean isDeivedAndModule(IFile workspaceFile) {
			return workspaceFile.isDerived() && AcceleoParser.MODULE_FILE_EXTENSION.equals(workspaceFile
					.getFileExtension());
		}

		/**
		 * Notifies a change for the given {@link URI}.
		 * 
		 * @param uri
		 *            the change {@link URI}
		 */
		private void notifyChange(URI uri) {
			// FIXME only update projects that depends (closure) on the project containing the given URI
			for (IProject workspaceProject : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
				final AcceleoProject acceleoProject = projectsTrace.get(workspaceProject);
				if (acceleoProject != null) {
					final String fileQualifiedName = acceleoProject.getResolver().getQualifiedName(uri);
					if (fileQualifiedName != null) {
						final Set<AcceleoTextDocument> dependOns = acceleoProject
								.getTextDocumentsThatDependOn(fileQualifiedName);
						for (AcceleoTextDocument dependOn : dependOns) {
							dependOn.getQueryEnvironment().getLookupEngine().clearContext(fileQualifiedName);
						}
						if (acceleoProject.getResolver().resolve(fileQualifiedName) instanceof Class<?>) {
							acceleoProject.setResolver(createResolver(acceleoProject.getResolver(),
									fileQualifiedName, workspaceProject));
						} else {
							acceleoProject.getResolver().clear(Collections.singleton(fileQualifiedName));

							final AcceleoTextDocument acceleoTextDocument = acceleoProject.getTextDocument(
									uri);
							if (acceleoTextDocument != null) {
								acceleoProject.getResolver().register(fileQualifiedName, acceleoTextDocument
										.getAcceleoAstResult().getModule());
							} else {
								acceleoProject.getResolver().resolve(fileQualifiedName);
							}
						}
						for (AcceleoTextDocument dependOn : dependOns) {
							dependOn.getQueryEnvironment().getLookupEngine().clearContext(fileQualifiedName);
							dependOn.validateAndPublishResults();
						}
					}
				}
			}
		}

		/**
		 * Creates a {@link String label} for an {@link AcceleoProject} corresponding to the given
		 * {@link IProject}.
		 * 
		 * @param workspaceProject
		 *            the (non-{@code null}) {@link IProject}.
		 * @return the {@link String label} for the corresponding {@link AcceleoProject}.
		 */
		private String getAcceleoProjectLabelFor(IProject workspaceProject) {
			return "AcceleoProject\"" + workspaceProject.getName() + "\"[" + workspaceProject.getLocationURI()
					+ "]";
		}

		/**
		 * Transforms an {@link IProject} into an {@link AcceleoProject}.
		 * 
		 * @param workspaceProject
		 *            the (non-{@code null}) {@link IProject} to transform.
		 * @return the corresponding {@link AcceleoProject}.
		 */
		private AcceleoProject transform(IProject workspaceProject) {
			return new AcceleoProject(getAcceleoProjectLabelFor(workspaceProject), this.createResolver(null,
					null, workspaceProject));
		}

		/**
		 * Creates a new {@link IQualifiedNameResolver} for the given {@link IProject}.
		 * 
		 * @param oldResolver
		 *            the old {@link IQualifiedNameResolver} if any, <code>null</code> otherwise
		 * @param updatedQualifiedName
		 *            the updated qualified name if any, <code>null</code> otherwise
		 * @param project
		 *            the (non-{@code null}) {@link IProject}.
		 * @return the corresponding {@link IQualifiedNameResolver}.
		 */
		public IQualifiedNameResolver createResolver(IQualifiedNameResolver oldResolver,
				String updatedQualifiedName, IProject project) {
			Objects.nonNull(project);
			final IQualifiedNameResolver resolver = QueryPlugin.getPlugin().createQualifiedNameResolver(
					AcceleoPlugin.getPlugin().getClass().getClassLoader(), project,
					AcceleoParser.QUALIFIER_SEPARATOR);
			resolver.addLoader(new ModuleLoader(new AcceleoParser(), null));
			resolver.addLoader(QueryPlugin.getPlugin().createJavaLoader(AcceleoParser.QUALIFIER_SEPARATOR));

			if (oldResolver != null) {
				final Set<String> resolvedQualifiedName = oldResolver.getResolvedQualifiedNames();
				resolvedQualifiedName.remove(updatedQualifiedName);
				for (String qualifiedName : resolvedQualifiedName) {
					final Object resolved = oldResolver.resolve(qualifiedName);
					if (resolved instanceof Class<?>) {
						// we reload class in the new classloader
						resolver.resolve(qualifiedName);
					} else {
						// we keep other resolved objects
						resolver.register(qualifiedName, resolved);
					}
				}
			}

			return resolver;
		}

		/**
		 * Transforms an {@link IFile} into an {@link AcceleoTextDocument}.
		 * 
		 * @param workspaceFile
		 *            the (non-{@code null}) {@link IFile} to transform.
		 * @return the corresponding {@link AcceleoTextDocument}.
		 */
		private AcceleoTextDocument transform(IFile workspaceFile) {
			URI textDocumentUri = workspaceFile.getLocationURI();
			String textDocumentContents = readWorkspaceFile(workspaceFile);
			AcceleoProject containerAcceleoProject = this.projectsTrace.get(workspaceFile.getProject());
			return new AcceleoTextDocument(textDocumentUri, textDocumentContents, containerAcceleoProject);
		}

		/**
		 * Reads the whole contents of an {@link IFile} as a {@link String} while conserving the line
		 * separators of the file.
		 * 
		 * @param workspaceFileToRead
		 *            the (non-{@code null}) {@link IFile} to read.
		 * @return the {@link String} of its contents.
		 */
		private static String readWorkspaceFile(IFile workspaceFileToRead) {
			try {
				InputStream inputStream = workspaceFileToRead.getContents();
				ByteArrayOutputStream result = new ByteArrayOutputStream();
				byte[] buffer = new byte[BUFFER_SIZE];
				int length;
				while ((length = inputStream.read(buffer)) != -1) {
					result.write(buffer, 0, length);
				}
				inputStream.close();
				return result.toString(workspaceFileToRead.getCharset());
			} catch (IOException | CoreException exception) {
				throw new RuntimeException("There was an issue while reading the contents of file "
						+ workspaceFileToRead.getLocation().toString(), exception);
			}
		}

	}

}
