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
	String ANT_RUNNER_TARGET_GENERATOR_URI = "antRunnerTarget.emtl"; //$NON-NLS-1$

	/**
	 * The name of the main template in the Ant runner generator.
	 */
	String ANT_RUNNER_TARGET_TEMPLATE_URI = "genAntRunnerTarget"; //$NON-NLS-1$

	/**
	 * The uri of the build.acceleo generator.
	 * 
	 * @deprecated PDE build not supported any more
	 */
	@Deprecated
	String BUILD_ACCELEO_GENERATOR_URI = "buildAcceleo.emtl"; //$NON-NLS-1$

	/**
	 * The name of the main template in the build.acceleo generator.
	 */
	String BUILD_ACCELEO_TEMPLATE_URI = "genBuildAcceleo"; //$NON-NLS-1$

	/**
	 * The uri of the .project generator.
	 */
	String PROJECT_DESCRIPTION_GENERATOR_URI = "projectDescription.emtl"; //$NON-NLS-1$

	/**
	 * The name of the main template in the .settings/org.eclipse.jdt.core.prefs generator.
	 */
	String PROJECT_DESCRIPTION_TEMPLATE_URI = "genProjectDescription"; //$NON-NLS-1$

	/**
	 * The uri of the .settings/org.eclipse.jdt.core.prefs generator.
	 */
	String PROJECT_SETTINGS_GENERATOR_URI = "projectSettings.emtl"; //$NON-NLS-1$

	/**
	 * The name of the main template in the .settings/org.eclipse.jdt.core.prefs generator.
	 */
	String PROJECT_SETTINGS_TEMPLATE_URI = "genProjectSettings"; //$NON-NLS-1$

	/**
	 * The uri of the .classpath generator.
	 */
	String PROJECT_CLASSPATH_GENERATOR_URI = "projectClasspath.emtl"; //$NON-NLS-1$

	/**
	 * The name of the main template in the .classpath generator.
	 */
	String PROJECT_CLASSPATH_TEMPLATE_URI = "genProjectClassPath"; //$NON-NLS-1$

	/**
	 * The uri of the MANIFEST.MF generator.
	 */
	String PROJECT_MANIFEST_GENERATOR_URI = "projectManifest.emtl"; //$NON-NLS-1$

	/**
	 * The name of the main template in the MANIFEST.MF generator.
	 */
	String PROJECT_MANIFEST_TEMPLATE_URI = "genManifestMF"; //$NON-NLS-1$

	/**
	 * The uri of the build.properties generator.
	 */
	String PROJECT_BUILD_GENERATOR_URI = "buildProperties.emtl"; //$NON-NLS-1$

	/**
	 * The name of the main template in the build.properties generator.
	 */
	String PROJECT_BUILD_TEMPLATE_URI = "genBuildProperties"; //$NON-NLS-1$

	/**
	 * The uri of the activator generator.
	 */
	String PROJECT_ACTIVATOR_GENERATOR_URI = "activator.emtl"; //$NON-NLS-1$

	/**
	 * The name of the main template in the activator generator.
	 */
	String PROJECT_ACTIVATOR_TEMPLATE_URI = "genActivator"; //$NON-NLS-1$

	/**
	 * The uri of the build.xml generator.
	 */
	String PROJECT_BUILD_XML_GENERATOR_URI = "antCompiler.emtl"; //$NON-NLS-1$

	/**
	 * The name of the main template in the build.xml generator.
	 */
	String PROJECT_BUILD_XML_TEMPLATE_URI = "genAntCompiler"; //$NON-NLS-1$

	/**
	 * The uri of the pom.xml generator.
	 */
	String PROJECT_POM_XML_GENERATOR_URI = "pom.emtl"; //$NON-NLS-1$

	/**
	 * The name of the main template in the pom.xml generator.
	 */
	String PROJECT_POM_XML_TEMPLATE_URI = "genPom"; //$NON-NLS-1$

	/**
	 * The uri of the pom.xml child generator.
	 */
	String PROJECT_POM_XML_CHILD_GENERATOR_URI = "pomChild.emtl"; //$NON-NLS-1$

	/**
	 * The name of the main template in the pom.xml child generator.
	 */
	String PROJECT_POM_XML_CHILD_TEMPLATE_URI = "genPom"; //$NON-NLS-1$

	/**
	 * The uri of the acceleo compiler generator.
	 */
	String PROJECT_ACCELEO_COMPILER_GENERATOR_URI = "acceleoCompiler.emtl"; //$NON-NLS-1$

	/**
	 * The name of the main template in the acceleo compiler generator.
	 */
	String PROJECT_ACCELEO_COMPILER_TEMPLATE_URI = "generateElement"; //$NON-NLS-1$

	/**
	 * The uri of the acceleo .project generator.
	 */
	String PROJECT_DOT_PROJECT_GENERATOR_URI = "dotProject.emtl"; //$NON-NLS-1$

	/**
	 * The name of the main template in the acceleo .project generator.
	 */
	String PROJECT_DOT_PROJECT_TEMPLATE_URI = "generateElement"; //$NON-NLS-1$

	/**
	 * The name of the acceleo pom.xml generator for the features.
	 */
	String POM_FEATURE_GENERATOR_URI = "pomFeature.emtl"; //$NON-NLS-1$

	/**
	 * The name of the main template.
	 */
	String POM_FEATURE_TEMPLATE_URI = "genPomFeature"; //$NON-NLS-1$

	/**
	 * The name of the Acceleo module.
	 */
	String POM_UPDATE_SITE_GENERATOR_URI = "pomUpdateSite.emtl"; //$NON-NLS-1$

	/**
	 * The name of the template.
	 */
	String POM_UPDATE_SITE_TEMPLATE_URI = "genPomUpdateSite"; //$NON-NLS-1$
}
