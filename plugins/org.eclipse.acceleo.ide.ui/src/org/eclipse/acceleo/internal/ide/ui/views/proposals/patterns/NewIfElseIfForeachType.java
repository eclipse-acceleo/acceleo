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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.views.proposals.patterns.IAcceleoPatternProposal;
import org.eclipse.acceleo.parser.cst.TextExpression;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;

/**
 * A template completion proposal with dynamic variables. It creates an If-ElseIf block with a rule for each
 * selected type of the ProposalsBrowser view. The types are well ordered.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class NewIfElseIfForeachType implements IAcceleoPatternProposal {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.views.proposals.patterns.IAcceleoPatternProposal#getDescription()
	 */
	public String getDescription() {
		return "[if] for all selected types"; //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.views.proposals.patterns.IAcceleoPatternProposal#getImage()
	 */
	public Image getImage() {
		return AcceleoUIActivator.getDefault().getImage("icons/proposals/NewIfElseIfForeachType.gif"); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.views.proposals.patterns.IAcceleoPatternProposal#isEnabled(java.lang.String,
	 *      int, org.eclipse.emf.ecore.EObject)
	 */
	public boolean isEnabled(String text, int offset, EObject cstNode) {
		return cstNode instanceof TextExpression;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.ide.ui.views.proposals.patterns.IAcceleoPatternProposal#createTemplateProposal(java.util.List,
	 *      java.lang.String)
	 */
	public String createTemplateProposal(List<EClass> types, String indentTab) {
		Collections.sort(types, new Comparator<EClass>() {
			public int compare(EClass c0, EClass c1) {
				if (c0.isSuperTypeOf(c1)) {
					return 1;
				}
				return -1;
			}
		});
		StringBuilder buffer = new StringBuilder();
		if (types.size() > 0) {
			buffer.append("[if (${e}.oclIsTypeOf("); //$NON-NLS-1$
			buffer.append(types.get(0).getName());
			buffer.append("))]\n"); //$NON-NLS-1$
			buffer.append(indentTab);
			buffer.append("\t" + "[${e}/]\n"); //$NON-NLS-1$ //$NON-NLS-2$
			for (int i = 1; i < types.size(); i++) {
				EClass eClass = types.get(i);
				buffer.append(indentTab);
				buffer.append("[elseif (${e}.oclIsTypeOf("); //$NON-NLS-1$
				buffer.append(eClass.getName());
				buffer.append("))]\n"); //$NON-NLS-1$
				buffer.append(indentTab);
				buffer.append("\t[${e}/]\n"); //$NON-NLS-1$
			}
			buffer.append(indentTab);
			buffer.append("[else]\n"); //$NON-NLS-1$
			buffer.append(indentTab);
			buffer.append("\t[${e}/]\n"); //$NON-NLS-1$
			buffer.append(indentTab);
			buffer.append("[/if]"); //$NON-NLS-1$
		}
		return buffer.toString();
	}
}
