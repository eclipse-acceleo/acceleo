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
package org.eclipse.acceleo.maven;

import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;

/**
 * The Acceleo Parser MOJO is used to call the Acceleo Parser from Maven.
 * @goal acceleo-compile
 * @phase compile
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 * @since 3.2
 */
public class AcceleoParserMojo extends AbstractMojo {
	
	/**
     * The Maven Project.
     *
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;
	
	/**
	 * Indicates if we are compiling the Acceleo modules as binary resources.
	 * 
	 * @parameter expression = "${maven.binaryResource}"
	 * @required
	 */
	private boolean useBinaryResources;
	
	/**
	 * The list of packages to register.
	 * @parameter expression = "${maven.packagesToRegister}"
	 * @required
	 */
	private List<String> packagesToRegister;
	
	/**
	 * The Acceleo project that should be built.
	 * @parameter expression = "${maven.acceleoProject}"
	 * @required
	 */
	private AcceleoProject acceleoProject;
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.maven.plugin.AbstractMojo#execute()
	 */
	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().info("Acceleo Parser !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		getLog().info("Binary Resources: " + useBinaryResources);
		getLog().info("Maven Project: " + project);
		getLog().info("Acceleo Project: " + acceleoProject);
		getLog().info("Packages to register: " + packagesToRegister);
	}

}
