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
package org.eclipse.acceleo.internal.ide.ui.launching;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.EnvironmentTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.debug.ui.sourcelookup.SourceLookupTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaClasspathTab;
import org.eclipse.jdt.debug.ui.launchConfigurations.JavaJRETab;

/**
 * Common function for Acceleo launch configuration javaArgumentsTab groups. Generally, a launch configuration
 * javaArgumentsTab group will subclass this class, and define a method to create and set the tabs in that
 * group.
 * <p>
 * Clients may subclass this class.
 * </p>
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoApplicationTabGroup extends AbstractLaunchConfigurationTabGroup {

	/**
	 * The launch configuration javaArgumentsTab that displays program arguments.
	 */
	private AcceleoJavaArgumentsTab javaArgumentsTab;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTabGroup#createTabs(org.eclipse.debug.ui.ILaunchConfigurationDialog,
	 *      java.lang.String)
	 */
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		javaArgumentsTab = new AcceleoJavaArgumentsTab();
		AcceleoMainTab mainTab = new AcceleoMainTab(javaArgumentsTab);
		AcceleoPropertiesFilesTab propertiesFilesTab = new AcceleoPropertiesFilesTab();
		ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] {mainTab, propertiesFilesTab,
				javaArgumentsTab, new JavaJRETab(), new JavaClasspathTab(), new SourceLookupTab(),
				new EnvironmentTab(), new CommonTab(), };
		setTabs(tabs);
	}

}
