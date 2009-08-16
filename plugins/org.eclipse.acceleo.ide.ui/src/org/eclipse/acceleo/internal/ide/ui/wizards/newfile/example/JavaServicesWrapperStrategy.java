/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.wizards.newfile.example;

import java.lang.reflect.Method;
import java.util.StringTokenizer;

import org.eclipse.acceleo.common.internal.utils.workspace.AcceleoWorkspaceUtil;
import org.eclipse.acceleo.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

/**
 * A specific implementation of the "org.eclipse.acceleo.ide.ui.example" extension point. It is used to
 * initialize automatically a template file as a wrapper of a Java class, by creating a Query for each method
 * of the Java class.
 * 
 * @see IAcceleoExampleStrategy
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class JavaServicesWrapperStrategy implements IAcceleoExampleStrategy {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy#getDescription()
	 */
	public String getDescription() {
		return AcceleoUIMessages.getString("JavaServicesWrapperStrategy.Description"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy#getInitialFileNameFilter()
	 */
	public String getInitialFileNameFilter() {
		return "*.java"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy#forceMetamodelURI()
	 */
	public boolean forceMetamodelURI() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy#forceHasFile()
	 */
	public boolean forceHasFile() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy#forceHasMain()
	 */
	public boolean forceHasMain() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy#forceMetamodelType()
	 */
	public boolean forceMetamodelType() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.wizards.newfile.example.IAcceleoExampleStrategy#getContent(org.eclipse.core.resources.IFile,
	 *      java.lang.String, boolean, boolean, java.lang.String, java.lang.String)
	 */
	public String getContent(IFile exampleFile, String moduleName, boolean templateHasFileBlock,
			boolean templateIsMain, String metamodelURI, String metamodelFileType) {
		StringBuilder buffer = new StringBuilder(""); //$NON-NLS-1$
		buffer.append("[module " + moduleName + "('"); //$NON-NLS-1$ //$NON-NLS-2$
		StringTokenizer st = new StringTokenizer(metamodelURI, ","); //$NON-NLS-1$
		while (st.hasMoreTokens()) {
			buffer.append(st.nextToken().trim());
			if (st.hasMoreTokens()) {
				buffer.append("', '"); //$NON-NLS-1$
			}
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
				types = new IType[0];
			}
			for (int i = 0; i < types.length; i++) {
				String typeQualifiedName = types[i].getFullyQualifiedName();
				AcceleoWorkspaceUtil.INSTANCE.addWorkspaceContribution(exampleFile.getProject());
				try {
					final Class<?> javaClass = AcceleoWorkspaceUtil.INSTANCE.getClass(typeQualifiedName,
							false);
					if (javaClass != null) {
						Method[] javaMethods = javaClass.getDeclaredMethods();
						for (int j = 0; j < javaMethods.length; j++) {
							Method javaMethod = javaMethods[j];
							Class<?>[] javaParameters = javaMethod.getParameterTypes();
							createQuery(buffer, typeQualifiedName, javaMethod, javaParameters);
						}
					}
				} finally {
					AcceleoWorkspaceUtil.INSTANCE.reset();
				}
			}

		}
		return buffer.toString();
	}

	/**
	 * Put in the buffer the query that is able to invoke the given method of the selected java class.
	 * 
	 * @param buffer
	 *            is the buffer to fill, it is an input/output parameter
	 * @param typeQualifiedName
	 *            is the fully qualified name of the java class
	 * @param javaMethod
	 *            is the current method of the class
	 * @param javaParameters
	 *            are the parameters types of the method
	 */
	private void createQuery(StringBuilder buffer, String typeQualifiedName, Method javaMethod,
			Class<?>[] javaParameters) {
		buffer.append("[query public "); //$NON-NLS-1$
		buffer.append(javaMethod.getName());
		buffer.append("("); //$NON-NLS-1$
		for (int i = 0; i < javaParameters.length; i++) {
			if (i > 0) {
				buffer.append(',');
				buffer.append(' ');
			}
			buffer.append("arg"); //$NON-NLS-1$
			buffer.append(i);
			buffer.append(" : "); //$NON-NLS-1$
			buffer.append(getShortName(javaParameters[i]));
		}
		buffer.append(") : "); //$NON-NLS-1$
		buffer.append(getShortName(javaMethod.getReturnType()));
		buffer.append("\n\t= invoke('"); //$NON-NLS-1$
		buffer.append(typeQualifiedName);
		buffer.append("', '"); //$NON-NLS-1$
		buffer.append(javaMethod.getName());
		buffer.append("("); //$NON-NLS-1$
		for (int i = 0; i < javaParameters.length; i++) {
			if (i > 0) {
				buffer.append(',');
				buffer.append(' ');
			}
			buffer.append(javaParameters[i].getName());
		}
		buffer.append(")', Sequence{"); //$NON-NLS-1$
		for (int i = 0; i < javaParameters.length; i++) {
			if (i > 0) {
				buffer.append(", "); //$NON-NLS-1$
			}
			buffer.append("arg"); //$NON-NLS-1$
			buffer.append(i);
		}
		buffer.append("}) /]\n\n"); //$NON-NLS-1$
	}

	/**
	 * Gets the short name of the java class. It means the last segment of the fully qualified name.
	 * 
	 * @param javaClass
	 *            is the java class
	 * @return the last segment of the fully qualified name
	 */
	private String getShortName(Class<?> javaClass) {
		if (javaClass != null) {
			String type = javaClass.getName();
			int lastDot = type.lastIndexOf('.');
			if (lastDot > -1) {
				type = type.substring(lastDot + 1);
			}
			return type;
		} else {
			return "void"; //$NON-NLS-1$
		}
	}
}
