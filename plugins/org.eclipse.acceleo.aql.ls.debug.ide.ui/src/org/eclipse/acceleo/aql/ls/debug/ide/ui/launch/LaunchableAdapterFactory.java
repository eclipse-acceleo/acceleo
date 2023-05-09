/*******************************************************************************
 * Copyright (c) 2017 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.debug.ide.ui.launch;

import org.eclipse.acceleo.aql.ide.AcceleoPlugin;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.debug.ui.actions.ILaunchable;

public class LaunchableAdapterFactory implements IAdapterFactory {

	/**
	 * A dummy {@link ILaunchable}.
	 */
	private static final ILaunchable DUMMY = new ILaunchable() {
	};

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		final IResource resource = Adapters.adapt(adaptableObject, IResource.class);
		if (adapterType.equals(ILaunchable.class) && AcceleoPlugin.isAcceleoMain(resource)) {
			return adapterType.cast(DUMMY);
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] {ILaunchable.class };
	}

}
