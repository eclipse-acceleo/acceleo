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

import java.io.File;
import java.util.List;

public class AcceleoProject {
	
	private File root;
	
	private Classpath classpath;
	
	private List<String> jars;
	
	private List<AcceleoProject> dependencies;

	public AcceleoProject(File root, Classpath classpath, List<String> jars, List<AcceleoProject> dependencies) {
		this.root = root;
		this.classpath = classpath;
		this.jars = jars;
		this.dependencies = dependencies;
	}
	
	public Classpath getClasspath() {
		return classpath;
	}
	
	public List<AcceleoProject> getDependencies() {
		return dependencies;
	}
	
	public List<String> getJars() {
		return jars;
	}
	
	public File getRoot() {
		return root;
	}
}
