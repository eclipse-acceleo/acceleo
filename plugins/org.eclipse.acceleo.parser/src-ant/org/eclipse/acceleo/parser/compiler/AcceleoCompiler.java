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
package org.eclipse.acceleo.parser.compiler;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.parser.AcceleoFile;
import org.eclipse.acceleo.parser.AcceleoParser;
import org.eclipse.acceleo.parser.AcceleoParserProblem;
import org.eclipse.acceleo.parser.AcceleoParserProblems;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.common.util.URI;

/**
 * The Acceleo Compiler ANT Task.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoCompiler extends Task {

	/**
	 * The source folders to compile.
	 */
	private List<File> sourceFolders = new ArrayList<File>();

	/**
	 * The MTL file properties.
	 * 
	 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
	 */
	private final class MTLFileInfo {

		/**
		 * The IO file.
		 */
		protected File mtlFile;

		/**
		 * The absolute URI.
		 */
		protected URI emtlAbsoluteURI;

		/**
		 * The full qualified module name.
		 */
		protected String fullModuleName;

		/**
		 * Constructor.
		 */
		protected MTLFileInfo() {
			// Hides constructor from anything other than AcceleoCompiler
		}

	}

	/**
	 * Sets the source folders to compile. They are separated by ';'.
	 * 
	 * @param allSourceFolders
	 *            are the source folders to compile
	 */
	public void setSourceFolders(String allSourceFolders) {
		sourceFolders.clear();
		StringTokenizer st = new StringTokenizer(allSourceFolders, ";"); //$NON-NLS-1$
		while (st.hasMoreTokens()) {
			String path = st.nextToken().trim();
			if (path.length() > 0) {
				File sourceFolder = new Path(path).toFile();
				sourceFolders.add(sourceFolder);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.tools.ant.Task#execute()
	 */
	@Override
	public void execute() throws BuildException {
		StringBuffer message = new StringBuffer();
		List<MTLFileInfo> fileInfos = new ArrayList<MTLFileInfo>();
		for (File sourceFolder : sourceFolders) {
			if (sourceFolder != null && sourceFolder.exists() && sourceFolder.isDirectory()) {
				fileInfos.addAll(computeFileInfos(sourceFolder));
			} else if (sourceFolder != null) {
				// The ANT Task localization doesn't work.
				message.append("The folder '"); //$NON-NLS-1$
				message.append(sourceFolder.getName());
				message.append('\'');
				message.append(" doesn't exist."); //$NON-NLS-1$
				message.append('\n');
			}
		}
		List<AcceleoFile> acceleoFiles = new ArrayList<AcceleoFile>();
		List<URI> emtlAbsoluteURIs = new ArrayList<URI>();
		for (MTLFileInfo mtlFileInfo : fileInfos) {
			acceleoFiles.add(new AcceleoFile(mtlFileInfo.mtlFile, mtlFileInfo.fullModuleName));
			emtlAbsoluteURIs.add(mtlFileInfo.emtlAbsoluteURI);
		}
		List<URI> dependenciesURIs = new ArrayList<URI>();
		AcceleoParser parser = new AcceleoParser();
		parser.parse(acceleoFiles, emtlAbsoluteURIs, dependenciesURIs, new BasicMonitor());
		for (Iterator<AcceleoFile> iterator = acceleoFiles.iterator(); iterator.hasNext();) {
			AcceleoFile acceleoFile = iterator.next();
			AcceleoParserProblems problems = parser.getProblems(acceleoFile);
			if (problems != null) {
				List<AcceleoParserProblem> list = problems.getList();
				if (!list.isEmpty()) {
					message.append(acceleoFile.getMtlFile().getName());
					message.append('\n');
					for (Iterator<AcceleoParserProblem> itProblems = list.iterator(); itProblems.hasNext();) {
						AcceleoParserProblem problem = itProblems.next();
						message.append(problem.getLine());
						message.append(':');
						message.append(problem.getMessage());
						message.append('\n');
					}
					message.append('\n');
				}
			}
		}
		if (message.length() > 0) {
			String log = message.toString();
			log(log, new BuildException(log), Project.MSG_ERR);
		}
	}

	/**
	 * Computes the properties of the MTL files of the given source folder.
	 * 
	 * @param sourceFolder
	 *            the current source folder
	 * @return the MTL files properties
	 */
	private List<MTLFileInfo> computeFileInfos(File sourceFolder) {
		List<MTLFileInfo> fileInfosOutput = new ArrayList<MTLFileInfo>();
		if (sourceFolder.exists()) {
			String sourceFolderAbsolutePath = sourceFolder.getAbsolutePath();
			List<File> mtlFiles = new ArrayList<File>();
			members(mtlFiles, sourceFolder, IAcceleoConstants.MTL_FILE_EXTENSION);
			for (File mtlFile : mtlFiles) {
				String mtlFileAbsolutePath = mtlFile.getAbsolutePath();
				if (mtlFileAbsolutePath != null) {
					String relativePath;
					if (mtlFileAbsolutePath.startsWith(sourceFolderAbsolutePath)) {
						relativePath = mtlFileAbsolutePath.substring(sourceFolderAbsolutePath.length());
					} else {
						relativePath = mtlFile.getName();
					}
					URI emtlAbsoluteURI = URI.createFileURI(new Path(mtlFileAbsolutePath)
							.removeFileExtension().addFileExtension(IAcceleoConstants.EMTL_FILE_EXTENSION)
							.toString());
					MTLFileInfo fileInfo = new MTLFileInfo();
					fileInfo.mtlFile = mtlFile;
					fileInfo.emtlAbsoluteURI = emtlAbsoluteURI;
					fileInfo.fullModuleName = AcceleoFile.relativePathToFullModuleName(relativePath);
					fileInfosOutput.add(fileInfo);
				}
			}
		}
		return fileInfosOutput;
	}

	/**
	 * Computes recursively the members of the given container that match the given file extension.
	 * 
	 * @param filesOutput
	 *            is the list to create
	 * @param container
	 *            is the container to browse
	 * @param extension
	 *            is the extension to match
	 */
	private void members(List<File> filesOutput, File container, String extension) {
		if (container != null && container.isDirectory()) {
			File[] children = container.listFiles();
			if (children != null) {
				for (File child : children) {
					if (child.isFile() && child.getName() != null
							&& (extension == null || child.getName().endsWith('.' + extension))) {
						filesOutput.add(child);
					} else {
						members(filesOutput, child, extension);
					}
				}
			}
		}
	}

}
