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
package org.eclipse.acceleo.internal.ide.ui.builders;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.parser.cst.utils.Sequence;
import org.eclipse.acceleo.parser.AcceleoFile;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * Provides utility methods for the builder and the refactoring.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public final class AcceleoBuilderUtils {

	/**
	 * The constructor.
	 */
	private AcceleoBuilderUtils() {
		// hides constructor.
	}

	/**
	 * Gets all the possible sequences to search when we use the given file in other files : '[import myGen',
	 * '[import org::eclipse::myGen', 'extends myGen', 'extends org::eclipse::myGen'...
	 * 
	 * @param acceleoProject
	 *            is the project
	 * @param file
	 *            is the MTL file
	 * @return all the possible sequences to search for the given file
	 */
	public static List<Sequence> getImportSequencesToSearch(AcceleoProject acceleoProject, IFile file) {
		List<Sequence> result = new ArrayList<Sequence>();
		String simpleModuleName = new Path(file.getName()).removeFileExtension().lastSegment();
		String[] tokens = new String[] {IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.IMPORT,
				simpleModuleName, };
		result.add(new Sequence(tokens));
		tokens = new String[] {IAcceleoConstants.EXTENDS, simpleModuleName, };
		result.add(new Sequence(tokens));
		String javaPackageName = acceleoProject.getPackageName(file);
		String fullModuleName = AcceleoFile.javaPackageToFullModuleName(javaPackageName, simpleModuleName);
		tokens = new String[] {IAcceleoConstants.DEFAULT_BEGIN, IAcceleoConstants.IMPORT, fullModuleName, };
		result.add(new Sequence(tokens));
		tokens = new String[] {IAcceleoConstants.EXTENDS, fullModuleName, };
		result.add(new Sequence(tokens));
		return result;
	}

	/**
	 * Returns a list of existing member files (that validate the file extension) in this resource. It doesn't
	 * browse the Java output folder.
	 * 
	 * @param filesOutput
	 *            an output parameter to get all the files
	 * @param container
	 *            is the container to browse
	 * @param extension
	 *            is the extension
	 * @param outputFolder
	 *            The output folder to ignore.
	 * @throws CoreException
	 *             contains a status object describing the cause of the exception
	 */
	public static void members(List<IFile> filesOutput, IContainer container, String extension,
			IPath outputFolder) throws CoreException {
		if (container != null) {
			IResource[] children = container.members();
			if (children != null) {
				for (int i = 0; i < children.length; ++i) {
					IResource resource = children[i];
					if (resource instanceof IFile && extension.equals(((IFile)resource).getFileExtension())) {
						filesOutput.add((IFile)resource);
					} else if (resource instanceof IContainer
							&& (outputFolder == null || !outputFolder.isPrefixOf(resource.getFullPath()))) {
						members(filesOutput, (IContainer)resource, extension, outputFolder);
					}
				}
			}
		}
	}

}
