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
package org.eclipse.acceleo.internal.ide.ui.wizards.module.example;

import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.JavaServicesUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

/**
 * A specific implementation of the "org.eclipse.acceleo.ide.ui.initializatione" extension point. It is used
 * to initialize automatically a template file as a wrapper of a Java class, by creating a Query for each
 * method of the Java class.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.1
 */
public class JavaServicesWrapperInitializationStrategy implements IAcceleoInitializationStrategy {

	/**
	 * Indicates if the documentation should be generated.
	 */
	private boolean shouldGenerateDocumentation;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#getDescription()
	 */
	public String getDescription() {
		return AcceleoUIMessages.getString("JavaServicesWrapperStrategy.Description"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#getInitialFileNameFilter()
	 */
	public String getInitialFileNameFilter() {
		return "*.java"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#forceMetamodelURI()
	 */
	public boolean forceMetamodelURI() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#forceMetamodelType()
	 */
	public boolean forceMetamodelType() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#forceHasFile()
	 */
	public boolean forceHasFile() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#forceHasMain()
	 */
	public boolean forceHasMain() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#forceQuery()
	 */
	public boolean forceQuery() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#forceTemplate()
	 */
	public boolean forceTemplate() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#forceDocumentation()
	 */
	public boolean forceDocumentation() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#configure(java.lang.String,
	 *      boolean, boolean, boolean)
	 */
	public void configure(String moduleElementKind, boolean hasFileBlock, boolean isMain,
			boolean generateDocumentation) {
		this.shouldGenerateDocumentation = generateDocumentation;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.module.example.IAcceleoInitializationStrategy#getContent(org.eclipse.core.resources.IFile,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	public String getContent(IFile exampleFile, String moduleName, List<String> metamodelURI,
			String metamodelFileType) {
		StringBuilder buffer = new StringBuilder(""); //$NON-NLS-1$
		String defaultEncoding;
		try {
			if (exampleFile != null) {
				defaultEncoding = exampleFile.getCharset();
			} else {
				defaultEncoding = "UTF-8"; //$NON-NLS-1$
			}
		} catch (CoreException e) {
			defaultEncoding = "UTF-8"; //$NON-NLS-1$
		}
		buffer.append("[comment encoding = "); //$NON-NLS-1$
		buffer.append(defaultEncoding);
		buffer.append(" /]\n"); //$NON-NLS-1$
		if (shouldGenerateDocumentation) {
			buffer.append("[**\n * The documentation of the module.\n */]\n"); //$NON-NLS-1$
		}
		buffer.append("[module " + moduleName + "('"); //$NON-NLS-1$ //$NON-NLS-2$
		int cpt = 1;
		for (String uri : metamodelURI) {
			buffer.append(uri);
			if (cpt < metamodelURI.size()) {
				buffer.append("', '"); //$NON-NLS-1$
			}
			cpt++;
		}
		buffer.append("')/]\n\n"); //$NON-NLS-1$
		if (exampleFile != null && exampleFile.exists()) {
			buffer.append(createWrapper(exampleFile));
		}
		return buffer.toString();
	}

	/**
	 * Reads the java content of the example file. The java reflect API is used to create the wrapper.
	 * 
	 * @param exampleFile
	 *            is the java example file that contains the Java services
	 * @return the corresponding text of the MTL queries
	 */
	private String createWrapper(IFile exampleFile) {
		StringBuilder buffer = new StringBuilder();
		IJavaElement javaElement = JavaCore.create(exampleFile);
		if (javaElement instanceof ICompilationUnit) {
			ICompilationUnit classFile = (ICompilationUnit)javaElement;
			IType[] types;
			try {
				types = classFile.getTypes();
			} catch (JavaModelException e) {
				AcceleoUIActivator.log(e, true);
				types = new IType[0];
			}
			for (IType iType : types) {
				try {
					IMethod[] methods = iType.getMethods();
					for (IMethod iMethod : methods) {
						if (Flags.isPublic(iMethod.getFlags())) {
							buffer.append(JavaServicesUtils.createQuery(iType, iMethod,
									this.shouldGenerateDocumentation));
						}
					}
				} catch (JavaModelException e) {
					AcceleoUIActivator.log(e, true);
				}
			}
		}
		return buffer.toString();
	}

}
