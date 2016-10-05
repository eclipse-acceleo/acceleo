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

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.swt.graphics.Image;

/**
 * Quick fix resolution on the Acceleo problem marker. To create a new query at the end of the file with the
 * marker information.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoCreateQueryResolutionAfterLastMember extends AbstractCreateModuleElementResolution {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IMarkerResolution2#getDescription()
	 */
	public String getDescription() {
		return AcceleoUIMessages.getString("AcceleoCreateQueryResolutionAfterLastMember.Description"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IMarkerResolution2#getImage()
	 */
	public Image getImage() {
		return AcceleoUIActivator.getDefault().getImage("icons/quickfix/QuickFixCreateQuery.gif"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IMarkerResolution#getLabel()
	 */
	public String getLabel() {
		return AcceleoUIMessages.getString("AcceleoCreateQueryResolutionAfterLastMember.Label"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.quickfix.AbstractCreateModuleElementResolution#append(StringBuilder,
	 *      String, String, String[], String[])
	 */
	@Override
	protected void append(StringBuilder newText, String lineDelimiter, String name, String[] paramTypes,
			String[] paramNames) {
		assert paramTypes.length == paramNames.length;
		newText.append("[query public " + name + " ("); //$NON-NLS-1$ //$NON-NLS-2$

		List<String> argList = new ArrayList<String>();
		for (int i = 0; i < paramTypes.length; i++) {
			argList.add(paramNames[i] + " : " + paramTypes[i]); //$NON-NLS-1$
		}

		newText.append(Joiner.on(", ").join(argList)); //$NON-NLS-1$
		newText.append(") : String = '' /]"); //$NON-NLS-1$
	}

}
