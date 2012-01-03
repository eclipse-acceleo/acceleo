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
package org.eclipse.acceleo.parser;

import java.io.File;

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.core.runtime.Path;

/**
 * This is an Acceleo file. It creates a mapping between an MTL java.io.File and an unique ID. This ID is the
 * full qualified module name. It is used to identify the module, like the NsURI feature in the EPackage
 * registry.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 * @since 3.0
 */
public final class AcceleoFile {

	/**
	 * The MTL input IO file.
	 */
	private File mtlFile;

	/**
	 * The full qualified module name. For example, 'org::eclipse::myGen' is the full module name of the
	 * 'MyProject/src/org/eclipse/myGen.mtl' file.
	 */
	private String fullModuleName;

	/**
	 * Constructor.
	 * 
	 * @param mtlFile
	 *            the MTL input IO file
	 * @param fullModuleName
	 *            the full qualified module name which can be computed with the following static methods of
	 *            this class : 'javaPackageToFullModuleName', 'simpleModuleName',
	 *            'relativePathToFullModuleName'...
	 */
	public AcceleoFile(File mtlFile, String fullModuleName) {
		super();
		this.mtlFile = mtlFile;
		this.fullModuleName = fullModuleName;
	}

	/**
	 * Computes the full qualified module name with the java package name and the module name. For example,
	 * 'org::eclipse::myGen' is the full module name for the 'org.eclipse' java package and the 'myGen' simple
	 * module name.
	 * 
	 * @param javaPackageName
	 *            is the name of the enclosing java package
	 * @param moduleName
	 *            is the simple name of the module (i.e the short name of the MTL file)
	 * @return a valid full qualified module name that you can use to create an AcceleoFile instance
	 */
	public static String javaPackageToFullModuleName(String javaPackageName, String moduleName) {
		if (javaPackageName != null && javaPackageName.length() > 0) {
			return javaPackageName.replaceAll("\\.", IAcceleoConstants.NAMESPACE_SEPARATOR) //$NON-NLS-1$
					+ IAcceleoConstants.NAMESPACE_SEPARATOR + moduleName;
		}
		return moduleName;

	}

	/**
	 * Computes the full qualified module name with the MTL IO file. The single information available is the
	 * file name. In this case, the module name isn't relative to the enclosing source folder.
	 * 
	 * @param mtlFile
	 *            is the MTL IO file
	 * @return a valid full qualified module name that you can use to create an AcceleoFile instance
	 */
	public static String simpleModuleName(File mtlFile) {
		return new Path(mtlFile.getName()).removeFileExtension().lastSegment();
	}

	/**
	 * Computes the full qualified module name with the given relative path. Let's take a simple MTL file with
	 * the following full path 'MyProject/src/org/eclipse/myGen.mtl'. The relative path of this MTL file must
	 * be 'org/eclipse/myGen.mtl'. In this case, the method returns 'org::eclipse::myGen' as the full module
	 * name of the MTL file.
	 * 
	 * @param relativePath
	 *            is the MTL file path, relative to the enclosing source folder
	 * @return a valid full qualified module name that you can use to create an AcceleoFile instance
	 */
	public static String relativePathToFullModuleName(String relativePath) {
		StringBuilder fullModuleName = new StringBuilder();
		String[] segments = new Path(relativePath).removeFileExtension().segments();
		for (int i = 0; i < segments.length; i++) {
			if (i != 0) {
				fullModuleName.append(IAcceleoConstants.NAMESPACE_SEPARATOR);
			}
			fullModuleName.append(segments[i]);
		}
		return fullModuleName.toString();
	}

	/**
	 * Gets the MTL input IO file.
	 * 
	 * @return the MTL input IO file
	 */
	public File getMtlFile() {
		return mtlFile;
	}

	/**
	 * Gets the full qualified module name. This ID is used to identify everywhere the module defined in this
	 * file, like the NsURI feature in the EPackage registry. This information is also stored in the root
	 * element of the EMTL resource (Module.nsURI).
	 * 
	 * @return the full qualified module name
	 */
	public String getFullModuleName() {
		return fullModuleName;
	}

}
