/*******************************************************************************
 * Copyright (c) 2020 Huawei.
 * All rights reserved.
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.profile.ide.ui.launch;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

/**
 * The profiling launch configuration tab group.
 * 
 * @author wpiers
 */
public class AcceleoProfilerTabGroup extends AbstractLaunchConfigurationTabGroup {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.debug.ui.ILaunchConfigurationTabGroup#createTabs(org.eclipse.debug.ui.ILaunchConfigurationDialog,
	 *      java.lang.String)
	 */
	@Override
	public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
		setTabs(new ILaunchConfigurationTab[] {new AcceleoProfilerMainTab(), new CommonTab() });
	}
}
