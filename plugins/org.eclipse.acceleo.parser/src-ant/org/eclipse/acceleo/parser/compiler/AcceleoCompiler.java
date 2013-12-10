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
package org.eclipse.acceleo.parser.compiler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.StringTokenizer;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.eclipse.emf.ecore.EPackage;

/**
 * The Acceleo Compiler ANT Task.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 * @since 3.1
 */
@Deprecated
public class AcceleoCompiler extends Task {

	/**
	 * The packages to register.
	 */
	private String packagesToRegister;

	/**
	 * The compiler helper.
	 */
	private final AcceleoCompilerHelper helper = new AcceleoCompilerHelper();

	/**
	 * Sets the source folder to compile.
	 * 
	 * @param theSourceFolder
	 *            are the source folder to compile
	 */
	public void setSourceFolder(String theSourceFolder) {
		helper.setSourceFolder(theSourceFolder);
	}

	/**
	 * Sets the output folder.
	 * 
	 * @param theOutputFolder
	 *            The output folder.
	 */
	public void setOutputFolder(String theOutputFolder) {
		helper.setOutputFolder(theOutputFolder);
	}

	/**
	 * Sets the dependencies to load before to compile. They are separated by ';'.
	 * 
	 * @param allDependencies
	 *            are the dependencies identifiers
	 */
	public void setDependencies(String allDependencies) {
		helper.setDependencies(allDependencies);
	}

	/**
	 * Sets the binary resource attribute.
	 * 
	 * @param binaryResource
	 *            Indicates if we should use a binary resource.
	 */
	public void setBinaryResource(boolean binaryResource) {
		helper.setBinaryResource(binaryResource);
	}

	/**
	 * Sets the packages to register.
	 * 
	 * @param packages
	 *            the semicolon separated packages.
	 */
	public void setPackagesToRegister(String packages) {
		this.packagesToRegister = packages;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.tools.ant.Task#execute()
	 */
	@Override
	public void execute() throws BuildException {
		// CHECKSTYLE:OFF
		try {
			if (packagesToRegister != null) {
				StringTokenizer tokenizer = new StringTokenizer(packagesToRegister, ";"); //$NON-NLS-1$
				while (tokenizer.hasMoreElements()) {
					String nextPackage = tokenizer.nextToken();
					Class<?> packageClass = Class.forName(nextPackage);
					Field field = packageClass.getField("eINSTANCE"); //$NON-NLS-1$
					if (field != null) {
						Object packageInstance = field.get(packageClass);
						Method method = packageInstance.getClass().getMethod("getNsURI"); //$NON-NLS-1$
						Object packageNsUri = method.invoke(packageInstance);
						if (packageInstance instanceof EPackage && packageNsUri instanceof String) {
							EPackage ePackage = (EPackage)packageInstance;
							String uri = (String)packageNsUri;
							EPackage.Registry.INSTANCE.put(uri, ePackage);
						}
					}
				}
			}

			helper.execute();
		} catch (Throwable e) {
			log(e.getMessage(), Project.MSG_ERR);
			throw new BuildException(e.getMessage(), getLocation());
		}
		// CHECKSTYLE:ON
	}
}
