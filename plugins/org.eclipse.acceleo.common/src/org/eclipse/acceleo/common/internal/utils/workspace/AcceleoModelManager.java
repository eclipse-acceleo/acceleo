/*******************************************************************************
 * Copyright (c) 2008, 2012 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.common.internal.utils.workspace;

import com.google.common.io.Closeables;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.acceleo.common.AcceleoCommonMessages;
import org.eclipse.acceleo.common.AcceleoCommonPlugin;
import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ISaveContext;
import org.eclipse.core.resources.ISaveParticipant;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

/**
 * This class, inspired by the JavaModelManager of the JDt will be used to save the state of the workspace
 * when a build is completed in order to prevent useless build from Acceleo.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public final class AcceleoModelManager implements ISaveParticipant {

	/**
	 * The sole instance of the manager.
	 */
	private static final AcceleoModelManager MANAGER = new AcceleoModelManager();

	/**
	 * Cache of the project informations.
	 */
	private Map<IProject, AcceleoProjectInfo> acceleoProjectInfos = new HashMap<IProject, AcceleoProjectInfo>();

	/**
	 * The constructor.
	 */
	private AcceleoModelManager() {
		// prevent instantiation
	}

	/**
	 * Returns the sole instance of the manager.
	 * 
	 * @return The sole instance of the manager.
	 */
	public static AcceleoModelManager getManager() {
		return MANAGER;
	}

	/**
	 * Initialization of the Acceleo Model Manager.
	 */
	public void startup() {
		try {
			// Force the creation of the plugin meta-data location.
			AcceleoCommonPlugin.getDefault().getStateLocation();

			// process deltas since last activated in indexer thread so that indexes are up-to-date.
			final IWorkspace workspace = ResourcesPlugin.getWorkspace();

			Job processSavedState = new Job(AcceleoCommonMessages
					.getString("AcceleoModelManager.ProcessingAcceleoChanges")) { //$NON-NLS-1$
				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						// add save participant and process delta atomically
						workspace.run(new IWorkspaceRunnable() {
							public void run(IProgressMonitor progress) throws CoreException {
								workspace.addSaveParticipant(AcceleoCommonPlugin.PLUGIN_ID,
										AcceleoModelManager.this);
							}
						}, monitor);
					} catch (CoreException e) {
						return e.getStatus();
					}
					return Status.OK_STATUS;
				}
			};
			processSavedState.setSystem(true);
			processSavedState.setPriority(Job.SHORT); // process ASAP!
			processSavedState.schedule();

			// CHECKSTYLE:OFF the JDT does it, let's do it too
		} catch (RuntimeException e) {
			// CHECKSTYLE:ON

			this.shutdown();
			throw e;
		}

	}

	/**
	 * This operation is called before the shutdown of the platform.
	 */
	public void shutdown() {
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.removeSaveParticipant(AcceleoCommonPlugin.PLUGIN_ID);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.resources.ISaveParticipant#prepareToSave(org.eclipse.core.resources.ISaveContext)
	 */
	public void prepareToSave(ISaveContext context) throws CoreException {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.resources.ISaveParticipant#saving(org.eclipse.core.resources.ISaveContext)
	 */
	public void saving(ISaveContext context) throws CoreException {
		IProject savedProject = context.getProject();
		if (savedProject != null) {
			if (savedProject.isAccessible() && savedProject.hasNature(IAcceleoConstants.ACCELEO_NATURE_ID)) {
				AcceleoProjectInfo info = this.getAcceleoProjectInfo(savedProject, true);
				saveState(info, context);
			}
		}

		Set<Entry<IProject, AcceleoProjectInfo>> entrySet = this.acceleoProjectInfos.entrySet();
		for (Entry<IProject, AcceleoProjectInfo> entry : entrySet) {
			IProject project = entry.getKey();
			if (project != null) {
				if (project.isAccessible() && project.hasNature(IAcceleoConstants.ACCELEO_NATURE_ID)) {
					AcceleoProjectInfo info = entry.getValue();
					saveState(info, context);
				}
			}
		}
	}

	/**
	 * Returns the project information for the given project.
	 * 
	 * @param project
	 *            The project
	 * @param create
	 *            Indicates if the information should be created if not found
	 * @return The project information for the given project
	 */
	private AcceleoProjectInfo getAcceleoProjectInfo(IProject project, boolean create) {
		synchronized(this.acceleoProjectInfos) { // use the perProjectInfo collection as its own lock
			AcceleoProjectInfo info = this.acceleoProjectInfos.get(project);
			if (info == null && create) {
				info = new AcceleoProjectInfo(project);
				this.acceleoProjectInfos.put(project, info);
			}
			return info;
		}
	}

	/**
	 * Save the project information in the given context.
	 * 
	 * @param info
	 *            The project information
	 * @param context
	 *            The save context
	 */
	private void saveState(AcceleoProjectInfo info, ISaveContext context) {
		if (ISaveContext.SNAPSHOT == context.getKind()) {
			return;
		}
		if (info.hasBeenRead()) {
			this.saveBuiltState(info);
		}
	}

	/**
	 * Sets the state of the given project.
	 * 
	 * @param project
	 *            The given project.
	 * @param state
	 *            The state of the project.
	 */
	public void setProjectState(IProject project, AcceleoProjectState state) {
		try {
			if (project.isAccessible() && project.hasNature(IAcceleoConstants.ACCELEO_NATURE_ID)) {
				AcceleoProjectInfo info = this.getAcceleoProjectInfo(project, true);
				info.markAsRead();
				info.setSavedState(state);
			}
			if (state == null) {
				try {
					File file = this.getSerializationFile(project);
					if (file != null && file.exists()) {
						file.delete();
					}
				} catch (SecurityException se) {
					// could not delete file: cannot do much more...
				}
			}
		} catch (CoreException e) {
			AcceleoCommonPlugin.log(e, true);
		}
	}

	/**
	 * Save the given project information.
	 * 
	 * @param info
	 *            The project information
	 */
	private void saveBuiltState(AcceleoProjectInfo info) {
		File file = this.getSerializationFile(info.getProject());
		if (file == null) {
			return;
		}
		try {
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			try {
				out.writeUTF(AcceleoCommonPlugin.PLUGIN_ID);
				out.writeUTF("STATE"); //$NON-NLS-1$
				if (info.getSavedState() == null) {
					out.writeBoolean(false);
				} else {
					out.writeBoolean(true);
					AcceleoModelManager.writeState(info.getSavedState(), out);
				}
			} finally {
				Closeables.closeQuietly(out);
			}
			// CHECKSTYLE:OFF
		} catch (RuntimeException e) {
			// CHECKSTYLE:ON
			try {
				file.delete();
			} catch (SecurityException se) {
				// could not delete file: cannot do much more
			}
		} catch (IOException e) {
			try {
				file.delete();
			} catch (SecurityException se) {
				// could not delete file: cannot do much more
			}
		}
	}

	/**
	 * Returns the last built state for the given project.
	 * 
	 * @param project
	 *            The given project
	 * @param monitor
	 *            The progress monitor
	 * @return The last build state for the given project
	 */
	public AcceleoProjectState getLastBuiltState(IProject project, IProgressMonitor monitor) {
		try {
			if (project.isAccessible() && project.hasNature(IAcceleoConstants.ACCELEO_NATURE_ID)) {
				AcceleoProjectInfo info = this.getAcceleoProjectInfo(project, true);
				if (!info.hasBeenRead()) {
					info.markAsRead();
					info.setSavedState(this.readBuiltState(project));
				}
				return info.getSavedState();
			}
		} catch (CoreException e) {
			AcceleoCommonPlugin.log(e, true);
		}

		return null;
	}

	/**
	 * Reads the state of the project.
	 * 
	 * @param project
	 *            The given project
	 * @return The state of the project
	 */
	private AcceleoProjectState readBuiltState(IProject project) {
		File file = this.getSerializationFile(project);
		if (file != null && file.exists()) {
			try {
				DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
				try {
					String pluginID = in.readUTF();
					if (!pluginID.equals(AcceleoCommonPlugin.PLUGIN_ID)) {
						AcceleoCommonPlugin.log(AcceleoCommonMessages
								.getString("AcceleoModelManager.WrongFileFormat"), true); //$NON-NLS-1$
					}
					String kind = in.readUTF();
					if (!"STATE".equals(kind)) { //$NON-NLS-1$
						AcceleoCommonPlugin.log(AcceleoCommonMessages
								.getString("AcceleoModelManager.WrongFileFormat"), true); //$NON-NLS-1$
					}
					if (in.readBoolean()) {
						return AcceleoModelManager.readState(project, in);
					}
				} finally {
					in.close();
				}
				// CHECKSTYLE:OFF
			} catch (Exception e) {
				// CHECKSTYLE:ON
				AcceleoCommonPlugin.log(e, true);
			}
		}
		return null;
	}

	/**
	 * Writes the saved state thanks to the data output stream.
	 * 
	 * @param savedState
	 *            The saved state to write
	 * @param out
	 *            The data output stream to use
	 */
	public static void writeState(AcceleoProjectState savedState, DataOutputStream out) {
		try {
			savedState.write(out);
		} catch (IOException e) {
			AcceleoCommonPlugin.log(e, true);
		}
	}

	/**
	 * Reads the last saved state of the given project.
	 * 
	 * @param project
	 *            The project for which we are looking for the state
	 * @param in
	 *            The data input stream used to read the state
	 * @return The last saved state.
	 */
	public static AcceleoProjectState readState(IProject project, DataInputStream in) {
		try {
			return AcceleoProjectState.read(project, in);
		} catch (IOException e) {
			AcceleoCommonPlugin.log(e, true);
		}
		return null;
	}

	/**
	 * Returns the File to use for saving and restoring the last built state for the given project.
	 * 
	 * @param project
	 *            The given project.
	 * @return The File to use for saving and restoring the last built state for the given project.
	 */
	private File getSerializationFile(IProject project) {
		if (!project.exists()) {
			return null;
		}
		IPath workingLocation = project.getWorkingLocation(AcceleoCommonPlugin.PLUGIN_ID);
		return workingLocation.append("acceleo.state.dat").toFile(); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.resources.ISaveParticipant#doneSaving(org.eclipse.core.resources.ISaveContext)
	 */
	public void doneSaving(ISaveContext context) {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.resources.ISaveParticipant#rollback(org.eclipse.core.resources.ISaveContext)
	 */
	public void rollback(ISaveContext context) {

	}

}
