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
package org.eclipse.acceleo.internal.ide.ui.editors.template.quickfix;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator2;

/**
 * The quick fix resolutions on the Acceleo problem marker.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoProblemQuickFix implements IMarkerResolutionGenerator2 {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IMarkerResolutionGenerator#getResolutions(org.eclipse.core.resources.IMarker)
	 */
	public IMarkerResolution[] getResolutions(IMarker marker) {
		IMarkerResolution[] result = new IMarkerResolution[5];
		result[0] = new AcceleoCreateTemplateResolutionAfterLastMember();
		result[1] = new AcceleoCreateQueryResolutionAfterLastMember();
		result[2] = new AcceleoCreateTemplateResolutionBeforeNextMember();
		result[3] = new AcceleoCreateQueryResolutionBeforeNextMember();
		result[4] = new AcceleoCreateJavaServiceWrapperResolutionAfterLastMember();
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IMarkerResolutionGenerator2#hasResolutions(org.eclipse.core.resources.IMarker)
	 */
	public boolean hasResolutions(IMarker marker) {
		try {
			return "org.eclipse.acceleo.ide.ui.problem".equals(marker.getType()); //$NON-NLS-1$
		} catch (CoreException e) {
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			return false;
		}
	}

}
