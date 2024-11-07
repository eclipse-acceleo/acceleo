/*******************************************************************************
 * Copyright (c) 2020, 2024 Obeo.
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
import java.util.Objects;

import org.eclipse.acceleo.aql.ide.AcceleoPlugin;
import org.eclipse.acceleo.aql.ls.IAcceleoLanguageServerContext;
import org.eclipse.acceleo.aql.ls.services.workspace.AcceleoProject;
import org.eclipse.acceleo.aql.ls.services.workspace.AcceleoWorkspace;
import org.eclipse.acceleo.aql.parser.AcceleoParser;
import org.eclipse.acceleo.aql.parser.ModuleLoader;
import org.eclipse.acceleo.query.ide.QueryPlugin;
import org.eclipse.acceleo.query.ide.runtime.impl.namespace.workspace.Synchronizer;
import org.eclipse.acceleo.query.runtime.namespace.IQualifiedNameResolver;
import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryWorkspace;
import org.eclipse.acceleo.query.runtime.namespace.workspace.IQueryWorkspaceQualifiedNameResolver;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

/**
 * Eclipse-specific implementation of {@link IAcceleoLanguageServerContext}.
 * 
 * @author Florent Latombe
 */
public class EclipseAcceleoLanguageServerContext implements IAcceleoLanguageServerContext {

	/**
	 * The {@link Synchronizer} for Acceleo.
	 * 
	 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
	 */
	private static class AcceleoSynchronizer extends Synchronizer<AcceleoProject> {

		AcceleoSynchronizer(IWorkspace eclipseWorkspace, IQueryWorkspace<AcceleoProject> queryWorkspace) {
			super(eclipseWorkspace, queryWorkspace);
		}

		@Override
		protected AcceleoProject createProject(IQueryWorkspace<AcceleoProject> acceleoWorkspace,
				IProject eclipseProject) {
			return new AcceleoProject(eclipseProject.getName(), (AcceleoWorkspace)acceleoWorkspace);
		}

		public AcceleoWorkspace getAcceleoWorkspace() {
			return (AcceleoWorkspace)getQueryWorkspace();
		}

		@Override
		protected boolean shouldInitializationSynchronize(IProject project) {
			// TODO filter on Acceleo nature
			return true;
		}

	}

	/**
	 * The size of the buffer we use to read {@link IFile Acceleo documents}.
	 */
	private static final int BUFFER_SIZE = 8 * 1024;

	/**
	 * The client {@link IWorkspace}.
	 */
	private final IWorkspace clientWorkspace;

	/**
	 * The {@link AcceleoSynchronizer}.
	 */
	private final AcceleoSynchronizer synchronizer;

	/**
	 * The constructor.
	 * 
	 * @param clientWorkspace
	 *            the (non-{@code null}) contextual {@link IWorkspace}.
	 */
	public EclipseAcceleoLanguageServerContext(IWorkspace clientWorkspace) {
		this.clientWorkspace = Objects.requireNonNull(clientWorkspace);
		final AcceleoWorkspace acceleoWorkspace = new AcceleoWorkspace(getAcceleoWorkspaceName(
				clientWorkspace), this);
		synchronizer = new AcceleoSynchronizer(clientWorkspace, acceleoWorkspace);
		synchronizer.synchronize();
	}

	/**
	 * Disposes this context.
	 */
	public void dispose() {
		synchronizer.dispose();
	}

	@Override
	public AcceleoWorkspace getWorkspace() {
		return synchronizer.getAcceleoWorkspace();
	}

	@Override
	public String getResourceContents(URI resource) {
		final IFile file = clientWorkspace.getRoot().getFileForLocation(new Path(resource.getPath()
				.toString()));
		try {
			InputStream inputStream = file.getContents();
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[BUFFER_SIZE];
			int length;
			while ((length = inputStream.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}
			inputStream.close();
			return result.toString(file.getCharset());
		} catch (IOException | CoreException exception) {
			AcceleoUIPlugin.getDefault().getLog().log(new Status(IStatus.ERROR, AcceleoUIPlugin.PLUGIN_ID,
					"There was an issue while reading the contents of file " + file.getLocation().toString(),
					exception));
		}

		return null;
	}

	@Override
	public AcceleoProject getProject(AcceleoWorkspace workspace, URI resource) {
		final AcceleoProject res;

		synchronized(synchronizer) {
			final IFile file = clientWorkspace.getRoot().getFileForLocation(new Path(resource.getPath()
					.toString()));
			final IProject eclipseProject = file.getProject();
			final AcceleoProject existingProject = synchronizer.getProject(eclipseProject);
			if (existingProject != null) {
				res = existingProject;
			} else {
				synchronizer.add(file.getProject());
				res = synchronizer.getProject(eclipseProject);
			}
		}

		return res;
	}

	@Override
	public IQueryWorkspaceQualifiedNameResolver createResolver(AcceleoProject acceleoProject) {
		Objects.nonNull(acceleoProject);

		final IProject eclipseProject = synchronizer.getOrCreateProject(acceleoProject);
		final IQualifiedNameResolver resolver = QueryPlugin.getPlugin().createQualifiedNameResolver(
				AcceleoPlugin.getPlugin().getClass().getClassLoader(), eclipseProject,
				AcceleoParser.QUALIFIER_SEPARATOR, true);
		resolver.addLoader(new ModuleLoader(new AcceleoParser(), null));
		resolver.addLoader(QueryPlugin.getPlugin().createJavaLoader(AcceleoParser.QUALIFIER_SEPARATOR, true));

		return QueryPlugin.getPlugin().createWorkspaceQualifiedNameResolver(eclipseProject, resolver,
				synchronizer);
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

}
