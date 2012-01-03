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
package org.eclipse.acceleo.ide.ui.views.proposals.patterns;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.graphics.Image;

/**
 * An internal extension point is defined to specify a new Pattern proposal in the current Acceleo template
 * completion. The extension point "org.eclipse.acceleo.ide.ui.proposal" requires a fully qualified name of a
 * Java class implementing this interface.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 * @since 3.0
 */
public interface IAcceleoPatternProposal {

	/**
	 * The identifier of the internal extension point specifying the implementation to use for creating a new
	 * Pattern proposal in the current Acceleo template completion.
	 */
	String PATTERN_PROPOSAL_EXTENSION_ID = "org.eclipse.acceleo.ide.ui.proposal"; //$NON-NLS-1$

	/**
	 * Gets the description of the pattern proposal (Displayed in the 'ProposalsBrowser' view and in the
	 * editor completion).
	 * 
	 * @return the description of the proposal
	 */
	String getDescription();

	/**
	 * Gets the image of the pattern proposal (Displayed in the 'ProposalsBrowser' view and in the editor
	 * completion).
	 * 
	 * @return the image of the proposal, it can be null
	 */
	Image getImage();

	/**
	 * Indicates if a template completion proposal will be available at the given offset.
	 * 
	 * @param text
	 *            is the text
	 * @param offset
	 *            is the offset in the text
	 * @param cstNode
	 *            is the current CST node at the given offset
	 * @return true if a template completion proposal will be available at the given offset
	 */
	boolean isEnabled(String text, int offset, EObject cstNode);

	/**
	 * Creates the template completion proposal. It means a string template with variables like ${name}.
	 * <p>
	 * Here is an example of result : this.${name} = ${name}.${value}
	 * </p>
	 * 
	 * @param types
	 *            are the available types to put in the template
	 * @param indentTab
	 *            is the current indentation
	 * @return the string template
	 */
	String createTemplateProposal(List<EClass> types, String indentTab);

}
