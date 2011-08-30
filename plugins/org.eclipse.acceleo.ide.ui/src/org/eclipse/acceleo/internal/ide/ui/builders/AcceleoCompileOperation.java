/*******************************************************************************
 * Copyright (c) 2008, 2011 Obeo.
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
import org.eclipse.acceleo.parser.AcceleoParserInfo;
import org.eclipse.acceleo.parser.AcceleoParserInfos;
import org.eclipse.acceleo.parser.AcceleoParserProblem;
import org.eclipse.acceleo.parser.AcceleoParserProblems;
import org.eclipse.acceleo.parser.AcceleoParserWarning;
import org.eclipse.acceleo.parser.AcceleoParserWarnings;
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
import org.eclipse.core.runtime.OperationCanceledException;
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
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.resources.IWorkspaceRunnable#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	public void run(IProgressMonitor monitor) throws CoreException {
		monitor.beginTask(AcceleoUIMessages.getString("AcceleoCompileOperation.Task.Compile"), files.length); //$NON-NLS-1$
		AcceleoProject acceleoProject = new AcceleoProject(project);
		for (int i = 0; i < files.length; i++) {
			checkCanceled(monitor);
			monitor.subTask(AcceleoUIMessages.getString(
					"AcceleoCompileOperation.Task.Clean", files[0].getFullPath().toString())); //$NON-NLS-1$
			files[i].deleteMarkers(IMarker.PROBLEM, true, IResource.DEPTH_INFINITE);
			files[i].deleteMarkers(IMarker.TASK, true, IResource.DEPTH_INFINITE);
			files[i].deleteMarkers(AcceleoMarkerUtils.OVERRIDE_MARKER_ID, true, IResource.DEPTH_INFINITE);
			IPath outputPath = acceleoProject.getOutputFilePath(files[i]);
			if (outputPath != null) {
				IFile outputFile = project.getFile(outputPath.removeFirstSegments(1));
				if (outputFile != null && outputFile.exists()) {
					try {
						outputFile.delete(true, monitor);
					} catch (CoreException e) {
						// continue
						// do nothing because it occurs when we have locked the file to write it again
						AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
					}
				}
			}
		}
		if (!isClean) {
			doCompileResources(monitor);
		}
		monitor.done();
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
		AcceleoProject acceleoProject = new AcceleoProject(project);
		List<URI> dependenciesURIs = acceleoProject.getAccessibleOutputFiles();
		List<AcceleoFile> iFiles = new ArrayList<AcceleoFile>();
		List<URI> oURIs = new ArrayList<URI>();
		for (int i = 0; i < files.length; i++) {
			checkCanceled(monitor);
			if (acceleoProject.getOutputFilePath(files[i]) != null) {
				IPath outputPath = acceleoProject.getOutputFilePath(files[i]);
				if (outputPath != null) {
					String javaPackageName = acceleoProject.getPackageName(files[i]);
					AcceleoFile acceleoFile = new AcceleoFile(files[i].getLocation().toFile(), AcceleoFile
							.javaPackageToFullModuleName(javaPackageName, new Path(files[i].getName())
									.removeFileExtension().lastSegment()));
					iFiles.add(acceleoFile);
					URI platformURI = URI.createPlatformResourceURI(outputPath.toString(), false);
					oURIs.add(platformURI);

				}
			}
		}

		AcceleoParser parser = null;
		AcceleoBuilderSettings settings = new AcceleoBuilderSettings(project);
		String resourceKind = settings.getResourceKind();
		if (AcceleoBuilderSettings.BUILD_XMI_RESOURCE.equals(resourceKind)) {
			parser = new AcceleoParser(false, settings.isTrimmedPositions());
		} else {
			parser = new AcceleoParser(true, settings.isTrimmedPositions());
		}
		parser.parse(iFiles, oURIs, dependenciesURIs, new BasicMonitor.EclipseSubProgress(monitor, 1));

		for (Iterator<AcceleoFile> iterator = iFiles.iterator(); iterator.hasNext();) {
			AcceleoFile iFile = iterator.next();

			AcceleoParserProblems problems = parser.getProblems(iFile);
			AcceleoParserWarnings warnings = parser.getWarnings(iFile);
			AcceleoParserInfos infos = parser.getInfos(iFile);

			IFile workspaceFile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(
					new Path(iFile.getMtlFile().getAbsolutePath()));

			if (workspaceFile != null && workspaceFile.isAccessible()) {
				if (problems != null) {
					List<AcceleoParserProblem> list = problems.getList();
					for (Iterator<AcceleoParserProblem> itProblems = list.iterator(); itProblems.hasNext();) {
						AcceleoParserProblem problem = itProblems.next();
						AcceleoMarkerUtils.createMarkerOnFile(AcceleoMarkerUtils.PROBLEM_MARKER_ID,
								workspaceFile, problem.getLine(), problem.getPosBegin(), problem.getPosEnd(),
								problem.getMessage());
					}
				}
				if (warnings != null) {
					List<AcceleoParserWarning> list = warnings.getList();
					for (Iterator<AcceleoParserWarning> itWarnings = list.iterator(); itWarnings.hasNext();) {
						AcceleoParserWarning warning = itWarnings.next();
						AcceleoMarkerUtils.createMarkerOnFile(AcceleoMarkerUtils.WARNING_MARKER_ID,
								workspaceFile, warning.getLine(), warning.getPosBegin(), warning.getPosEnd(),
								warning.getMessage());
					}
				}
				if (infos != null) {
					List<AcceleoParserInfo> list = infos.getList();
					for (Iterator<AcceleoParserInfo> itInfos = list.iterator(); itInfos.hasNext();) {
						AcceleoParserInfo info = itInfos.next();
						AcceleoMarkerUtils.createMarkerOnFile(AcceleoMarkerUtils.INFO_MARKER_ID,
								workspaceFile, info.getLine(), info.getPosBegin(), info.getPosEnd(), info
										.getMessage());
					}
				}
			}
		}
		checkCanceled(monitor);
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

		settings = new AcceleoBuilderSettings(project);
		if (AcceleoBuilderSettings.BUILD_STRICT_MTL_COMPLIANCE == settings.getCompliance()) {
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
							AcceleoMarkerUtils.createMarkerOnFile(AcceleoMarkerUtils.PROBLEM_MARKER_ID,
									workspaceFile, line, oOperationCallExp.getStartPosition(),
									oOperationCallExp.getEndPosition(), AcceleoUIMessages.getString(
											"AcceleoCompileOperation.NotFullyCompliant", oOperationCallExp //$NON-NLS-1$
													.getReferredOperation().getName()));
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
		StringBuffer contents = FileContent.getFileContent(file.getLocation().toFile());
		if (pattern.search(contents).b() > -1) {
			return true;
		}
		return false;
	}

	/**
	 * Checks whether the given monitor has been canceled and throw and interrupted exception if so.
	 * 
	 * @param monitor
	 *            Monitor to check the state of.
	 */
	private void checkCanceled(IProgressMonitor monitor) {
		if (monitor.isCanceled()) {
			throw new OperationCanceledException();
		}
	}
}
