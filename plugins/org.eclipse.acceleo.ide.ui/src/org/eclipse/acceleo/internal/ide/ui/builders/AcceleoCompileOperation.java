/*******************************************************************************
 * Copyright (c) 2008, 2010 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.builders;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.common.utils.ModelUtils;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.builders.runner.CreateRunnableAcceleoOperation;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.acceleo.internal.parser.cst.utils.Sequence;
import org.eclipse.acceleo.parser.AcceleoFile;
import org.eclipse.acceleo.parser.AcceleoParser;
import org.eclipse.acceleo.parser.AcceleoParserProblem;
import org.eclipse.acceleo.parser.AcceleoParserProblems;
import org.eclipse.acceleo.parser.AcceleoSourceBuffer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.ocl.ecore.OperationCallExp;

/**
 * The operation that compiles the templates in a background task.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoCompileOperation implements IWorkspaceRunnable {

	/**
	 * The project.
	 */
	private IProject project;

	/**
	 * The templates to compile.
	 */
	private IFile[] files;

	/**
	 * Indicates if the current purpose of the builder is to clean the project.
	 */
	private boolean isClean;

	/**
	 * The compilation messages.
	 */
	private StringBuilder messages;

	/**
	 * Constructor.
	 * 
	 * @param project
	 *            is the project
	 * @param files
	 *            are the templates to compile
	 * @param isClean
	 *            indicates if the current purpose of the builder is to clean the project
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	public AcceleoCompileOperation(IProject project, IFile[] files, boolean isClean) throws CoreException {
		this.project = project;
		this.files = files;
		this.isClean = isClean;
		this.messages = new StringBuilder();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.resources.IWorkspaceRunnable#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void run(IProgressMonitor monitor) throws CoreException {
		messages = new StringBuilder();
		monitor.beginTask(AcceleoUIMessages.getString("AcceleoCompileOperation.Task.Compile"), files.length); //$NON-NLS-1$
		AcceleoProject acceleoProject = new AcceleoProject(project);
		for (int i = 0; i < files.length; i++) {
			monitor.subTask(AcceleoUIMessages.getString(
					"AcceleoCompileOperation.Task.Clean", files[0].getFullPath().toString())); //$NON-NLS-1$
			files[i].deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			IPath outputPath = acceleoProject.getOutputFilePath(files[i]);
			if (outputPath != null) {
				IFile outputFile = project.getFile(outputPath.removeFirstSegments(1));
				if (outputFile != null && outputFile.exists()) {
					outputFile.delete(true, monitor);
				}
			}
		}
		if (!isClean) {
			doCompileResources(monitor);
		}
		monitor.done();
	}

	/**
	 * Gets the compilation messages.
	 * 
	 * @return the compilation messages
	 */
	public String getMessages() {
		return messages.toString();
	}

	/**
	 * Compiles the templates. Creates an AST model from a list of Acceleo files, using a CST step. The
	 * dependencies are loaded before link resolution.
	 * 
	 * @param monitor
	 *            is the monitor
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	private void doCompileResources(IProgressMonitor monitor) throws CoreException {
		List<String> conflicts = new ArrayList<String>();
		AcceleoProject acceleoProject = new AcceleoProject(project);
		List<URI> dependenciesURIs = acceleoProject.getAccessibleOutputFiles();
		for (Iterator<URI> iterator = dependenciesURIs.iterator(); iterator.hasNext();) {
			URI uri = iterator.next();
			conflicts.add(new Path(uri.toString()).removeFileExtension().addFileExtension(
					IAcceleoConstants.MTL_FILE_EXTENSION).lastSegment());
		}
		AcceleoParser parser = new AcceleoParser();
		List<AcceleoFile> iFiles = new ArrayList<AcceleoFile>();
		List<URI> oURIs = new ArrayList<URI>();
		for (int i = 0; i < files.length; i++) {
			if (acceleoProject.getOutputFilePath(files[i]) != null) {
				if (conflicts.contains(files[i].getName())) {
					reportError(files[i], 0, 0, 0, AcceleoUIMessages.getString(
							"AcceleoCompileOperation.NameConflict", files[i].getName())); //$NON-NLS-1$
				} else {
					conflicts.add(files[i].getName());
				}
				IPath outputPath = acceleoProject.getOutputFilePath(files[i]);
				if (outputPath != null) {
					String javaPackageName = acceleoProject.getPackageName(files[i]);
					AcceleoFile acceleoFile = new AcceleoFile(files[i].getLocation().toFile(), AcceleoFile
							.javaPackageToFullModuleName(javaPackageName, new Path(files[i].getName())
									.removeFileExtension().lastSegment()));
					iFiles.add(acceleoFile);
					oURIs.add(URI.createPlatformResourceURI(outputPath.toString(), false));
				}
			}
		}
		parser.parse(iFiles, oURIs, dependenciesURIs, new BasicMonitor.EclipseSubProgress(monitor, 1));
		for (Iterator<AcceleoFile> iterator = iFiles.iterator(); iterator.hasNext();) {
			AcceleoFile iFile = iterator.next();
			AcceleoParserProblems problems = parser.getProblems(iFile);
			if (problems != null) {
				IFile workspaceFile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(
						new Path(iFile.getMtlFile().getAbsolutePath()));
				if (workspaceFile != null && workspaceFile.isAccessible()) {
					List<AcceleoParserProblem> list = problems.getList();
					for (Iterator<AcceleoParserProblem> itProblems = list.iterator(); itProblems.hasNext();) {
						AcceleoParserProblem problem = itProblems.next();
						reportError(workspaceFile, problem.getLine(), problem.getPosBegin(), problem
								.getPosEnd(), problem.getMessage());
					}
				}
			}
		}
		if (!monitor.isCanceled()) {
			List<IFile> filesWithMainTag = new ArrayList<IFile>();
			for (Iterator<AcceleoFile> iterator = iFiles.iterator(); iterator.hasNext();) {
				AcceleoFile iFile = iterator.next();
				IFile workspaceFile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(
						new Path(iFile.getMtlFile().getAbsolutePath()));
				if (workspaceFile != null && workspaceFile.isAccessible() && hasMainTag(workspaceFile)) {
					filesWithMainTag.add(workspaceFile);
				}
			}
			CreateRunnableAcceleoOperation createRunnableAcceleoOperation = new CreateRunnableAcceleoOperation(
					acceleoProject, filesWithMainTag);
			createRunnableAcceleoOperation.run(monitor);
		}
		AcceleoBuilderSettings settings = new AcceleoBuilderSettings(project);
		if (AcceleoBuilderSettings.BUILD_FULL_OMG_COMPLIANCE == settings.getCompliance()) {
			Iterator<AcceleoFile> itFiles = iFiles.iterator();
			for (Iterator<URI> itURIs = oURIs.iterator(); !monitor.isCanceled() && itURIs.hasNext()
					&& itFiles.hasNext();) {
				AcceleoFile iFile = itFiles.next();
				URI oURI = itURIs.next();
				checkFullOMGCompliance(iFile.getMtlFile(), oURI);
			}
		}
	}

	/**
	 * Check the '.emtl' file and report in the '.mtl' file some syntax errors when we use the non-standard
	 * library.
	 * 
	 * @param iFile
	 *            is the '.mtl' file
	 * @param oURI
	 *            is the URI of the '.emtl' file
	 */
	private void checkFullOMGCompliance(File iFile, URI oURI) {
		try {
			AcceleoSourceBuffer buffer = new AcceleoSourceBuffer(iFile);
			ResourceSet oResourceSet = new ResourceSetImpl();
			EObject oRoot = ModelUtils.load(oURI, oResourceSet);
			TreeIterator<EObject> oAllContents = oRoot.eAllContents();
			while (oAllContents.hasNext()) {
				EObject oNext = oAllContents.next();
				if (oNext instanceof OperationCallExp) {
					OperationCallExp oOperationCallExp = (OperationCallExp)oNext;
					if (oOperationCallExp.getReferredOperation() != null
							&& oOperationCallExp.getReferredOperation().getEAnnotation("MTL non-standard") != null) { //$NON-NLS-1$
						IFile workspaceFile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(
								new Path(iFile.getAbsolutePath()));
						if (workspaceFile != null && workspaceFile.isAccessible()
								&& oOperationCallExp.getStartPosition() > -1) {
							int line = buffer.getLineOfOffset(oOperationCallExp.getStartPosition());
							reportError(
									workspaceFile,
									line,
									oOperationCallExp.getStartPosition(),
									oOperationCallExp.getEndPosition(),
									AcceleoUIMessages
											.getString(
													"AcceleoCompileOperation.NotFullyCompliant", oOperationCallExp.getReferredOperation().getName())); //$NON-NLS-1$
						}
					}
				}
			}
		} catch (IOException e) {
			Status status = new Status(IStatus.WARNING, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e);
			AcceleoUIActivator.getDefault().getLog().log(status);
		} catch (CoreException e) {
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
	}

	/**
	 * Indicates if the given file contains a main annotation (@main).
	 * 
	 * @param file
	 *            is the template file
	 * @return true if the given file contains a main annotation (@main)
	 */
	private boolean hasMainTag(IFile file) {
		Sequence pattern = new Sequence(IAcceleoConstants.TAG_MAIN);
		try {
			InputStream inputStream = file.getContents();
			int available = inputStream.available();
			byte[] bytes = new byte[available];
			inputStream.read(bytes);
			StringBuffer contents = FileContent.getFileContent(file.getLocation().toFile());
			if (pattern.search(contents).b() > -1) {
				return true;
			}
		} catch (CoreException e) {
			AcceleoUIActivator.getDefault().getLog().log(
					new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
		} catch (IOException e) {
			AcceleoUIActivator.getDefault().getLog().log(
					new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e.getMessage(), e));
		}
		return false;
	}

	/**
	 * Creates an error marker on the given file.
	 * 
	 * @param file
	 *            is the file that contains a syntax error
	 * @param line
	 *            is the line of the problem
	 * @param posBegin
	 *            is the beginning position of the problem
	 * @param posEnd
	 *            is the ending position of the problem
	 * @param message
	 *            is the message of the problem, it is the message displayed when you're hover the marker
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	private void reportError(IFile file, int line, int posBegin, int posEnd, String message)
			throws CoreException {
		IMarker m = file.createMarker(AcceleoMarker.PROBLEM_MARKER);
		m.setAttribute(IMarker.LINE_NUMBER, line);
		m.setAttribute(IMarker.CHAR_START, posBegin);
		m.setAttribute(IMarker.CHAR_END, posEnd);
		m.setAttribute(IMarker.MESSAGE, message);
		m.setAttribute(IMarker.PRIORITY, IMarker.PRIORITY_HIGH);
		m.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
		messages.append(file.getFullPath().toString());
		messages.append(" line "); //$NON-NLS-1$
		messages.append(line);
		messages.append('\n');
		messages.append(message);
		messages.append('\n');
		messages.append('\n');
	}
}
