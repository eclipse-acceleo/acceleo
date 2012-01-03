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
package org.eclipse.acceleo.internal.ide.ui.editors.template;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateProposal;
import org.eclipse.swt.graphics.Image;

/**
 * A template completion proposal. It means a string template with variables like ${name} in the following
 * example : this.${name} = ${name}.${value}
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoCompletionTemplateProposal extends TemplateProposal {

	/**
	 * The additional proposal info.
	 */
	private String info;

	/**
	 * Constructor.
	 * 
	 * @param template
	 *            the template
	 * @param context
	 *            the context in which the template was requested
	 * @param region
	 *            the region this proposal is applied to
	 * @param image
	 *            the icon of the proposal
	 */
	public AcceleoCompletionTemplateProposal(Template template, TemplateContext context, IRegion region,
			Image image) {
		this(template, context, region, image, null);
	}

	/**
	 * Constructor.
	 * 
	 * @param template
	 *            the template
	 * @param context
	 *            the context in which the template was requested
	 * @param region
	 *            the region this proposal is applied to
	 * @param image
	 *            the icon of the proposal
	 * @param info
	 *            the additional proposal info
	 */
	public AcceleoCompletionTemplateProposal(Template template, TemplateContext context, IRegion region,
			Image image, String info) {
		super(template, context, region, image);
		this.info = info;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.templates.TemplateProposal#getDisplayString()
	 */
	@Override
	public String getDisplayString() {
		return getTemplate().getDescription();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.templates.TemplateProposal#getAdditionalProposalInfo()
	 */
	@Override
	public String getAdditionalProposalInfo() {
		return this.info;
	}

}
