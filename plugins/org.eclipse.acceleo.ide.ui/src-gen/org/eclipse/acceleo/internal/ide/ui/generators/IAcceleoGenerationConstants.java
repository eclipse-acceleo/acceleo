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
package org.eclipse.acceleo.internal.ide.ui.generators;

/**
 * Constants used by all the Acceleo wizards.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public interface IAcceleoGenerationConstants {

	/**
	 * The uri of the Acceleo module generator.
	 */
	String ACCELEO_MODULE_GENERATOR_URI = "acceleoModule.emtl"; //$NON-NLS-1$

	/**
	 * The name of the main template in the Acceleo module generator.
	 */
	String ACCELEO_MODULE_TEMPLATE_URI = "genAcceleoModule"; //$NON-NLS-1$

	/**
	 * The uri of the Acceleo java class generator.
	 */
	String ACCELEO_JAVA_CLASS_GENERATOR_URI = "acceleoJavaClassGenerator.emtl"; //$NON-NLS-1$

	/**
	 * The name of the main template in the Acceleo module generator.
	 */
	String ACCELEO_JAVA_CLASS_TEMPLATE_URI = "genAbstractAcceleoGenerator"; //$NON-NLS-1$

	/**
	 * The uri of the Ant runner generator.
	 */
	String ANT_RUNNER_GENERATOR_URI = "antRunner.emtl"; //$NON-NLS-1$

	/**
	 * The name of the main template in the Ant runner generator.
	 */
	String ANT_RUNNER_TEMPLATE_URI = "genAntRunner"; //$NON-NLS-1$

	/**
	 * The uri of the Ant runner read me generator.
	 */
	String ANT_RUNNER_READ_ME_GENERATOR_URI = "antRunnerReadMe.emtl"; //$NON-NLS-1$

	/**
	 * The name of the main template in the Ant runner generator.
	 */
	String ANT_RUNNER_READ_ME_TEMPLATE_URI = "genAntRunnerReadMe"; //$NON-NLS-1$

	/**
	 * The uri of the build.acceleo generator.
	 */
	String BUILD_ACCELEO_GENERATOR_URI = "buildAcceleo.emtl"; //$NON-NLS-1$

	/**
	 * The name of the main template in the build.acceleo generator.
	 */
	String BUILD_ACCELEO_TEMPLATE_URI = "genBuildAcceleo"; //$NON-NLS-1$

}
