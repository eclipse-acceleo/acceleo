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
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoSourceContent;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.acceleo.parser.cst.ModuleElement;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.graphics.Image;

/**
 * Quick fix resolution on the Acceleo problem marker. To create a new query before the next member with the
 * marker information.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoCreateQueryResolutionBeforeNextMember extends AbstractCreateModuleElementResolution {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IMarkerResolution2#getDescription()
	 */
	public String getDescription() {
		return AcceleoUIMessages.getString("AcceleoCreateQueryResolutionBeforeNextMember.Description"); //$NON-NLS-1$
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
		return AcceleoUIMessages.getString("AcceleoCreateQueryResolutionBeforeNextMember.Label"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.quickfix.AbstractCreateModuleElementResolution#append(java.lang.StringBuilder,
	 *      java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	protected void append(StringBuilder newText, String name, String paramType, String paramName) {
		newText.append("[query public " + name + " ("); //$NON-NLS-1$ //$NON-NLS-2$
		newText.append(paramName);
		newText.append(" : "); //$NON-NLS-1$
		newText.append(paramType);
		newText.append(") : String = '' /]"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.internal.ide.ui.editors.template.quickfix.AbstractCreateModuleElementResolution#newOffset(org.eclipse.jface.text.IDocument,
	 *      org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoSourceContent, int)
	 */
	@Override
	protected int newOffset(IDocument document, AcceleoSourceContent content, int offset) {
		int result = document.getLength();
		CSTNode currentNode = content.getCSTNode(offset, offset);
		if (currentNode instanceof ModuleElement) {
			result = ((ModuleElement)currentNode).getEndPosition();
		} else if (currentNode != null) {
			currentNode = content.getCSTParent(currentNode, ModuleElement.class);
			if (currentNode instanceof ModuleElement) {
				result = ((ModuleElement)currentNode).getEndPosition();
			}
		}
		return result;
	}

}
