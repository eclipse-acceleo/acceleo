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
package org.eclipse.acceleo.internal.ide.ui.views.proposals.patterns;

import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.views.proposals.patterns.IAcceleoPatternProposal;
import org.eclipse.acceleo.parser.cst.Module;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;

/**
 * A template completion proposal with dynamic variables. It creates a Template for each selected type of the
 * ProposalsBrowser view. The types are well ordered.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class NewTemplateForeachType implements IAcceleoPatternProposal {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.views.proposals.patterns.IAcceleoPatternProposal#getDescription()
	 */
	public String getDescription() {
		return "[template] for all selected types"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.views.proposals.patterns.IAcceleoPatternProposal#getImage()
	 */
	public Image getImage() {
		return AcceleoUIActivator.getDefault().getImage("icons/proposals/NewTemplateForeachType.gif"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.views.proposals.patterns.IAcceleoPatternProposal#isEnabled(java.lang.String,
	 *      int, org.eclipse.emf.ecore.EObject)
	 */
	public boolean isEnabled(String text, int offset, EObject cstNode) {
		return cstNode instanceof Module;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.views.proposals.patterns.IAcceleoPatternProposal#createTemplateProposal(java.util.List,
	 *      java.lang.String)
	 */
	public String createTemplateProposal(List<EClass> types, String indentTab) {
		StringBuilder buffer = new StringBuilder();
		for (EClass eClass : types) {
			buffer.append("[template public ${name}(${arg} : "); //$NON-NLS-1$
			buffer.append(eClass.getName());
			buffer.append(") ]\n"); //$NON-NLS-1$
			buffer.append("\t[comment TODO Auto-generated template stub/]\n"); //$NON-NLS-1$
			buffer.append("[/template]\n\n"); //$NON-NLS-1$
		}
		return buffer.toString();
	}

}
