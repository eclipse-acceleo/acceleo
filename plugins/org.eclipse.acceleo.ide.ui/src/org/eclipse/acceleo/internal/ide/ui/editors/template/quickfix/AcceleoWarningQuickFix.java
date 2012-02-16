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
import org.eclipse.acceleo.internal.parser.AcceleoParserMessages;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator2;
import org.eclipse.ui.texteditor.MarkerUtilities;

/**
 * The quick fix resolutions on the Acceleo warning marker.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoWarningQuickFix implements IMarkerResolutionGenerator2 {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IMarkerResolutionGenerator#getResolutions(org.eclipse.core.resources.IMarker)
	 */
	public IMarkerResolution[] getResolutions(IMarker marker) {
		String message = MarkerUtilities.getMessage(marker);
		String warning = AcceleoParserMessages
				.getString("CST2ASTConverterWithResolver.IncompatibleComparison"); //$NON-NLS-1$
		warning = warning.substring(0, warning.indexOf(":")); //$NON-NLS-1$

		if (message.startsWith(warning)) {
			IMarkerResolution[] result = new IMarkerResolution[1];
			result[0] = new AcceleoCreateComparisonResolution();
			return result;
		}
		return new IMarkerResolution[0];
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IMarkerResolutionGenerator2#hasResolutions(org.eclipse.core.resources.IMarker)
	 */
	public boolean hasResolutions(IMarker marker) {
		try {
			String message = MarkerUtilities.getMessage(marker);
			String warning = AcceleoParserMessages
					.getString("CST2ASTConverterWithResolver.IncompatibleComparison"); //$NON-NLS-1$
			warning = warning.substring(0, warning.indexOf(":")); //$NON-NLS-1$

			boolean validType = "org.eclipse.acceleo.ide.ui.warning".equals(marker.getType()); //$NON-NLS-1$
			boolean validMessage = message.startsWith(warning);
			return validType && validMessage;
		} catch (CoreException e) {
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			return false;
		}
	}

}
