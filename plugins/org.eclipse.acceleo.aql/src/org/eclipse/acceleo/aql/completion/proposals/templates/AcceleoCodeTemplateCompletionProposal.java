/*******************************************************************************
 * Copyright (c) 2020, 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.completion.proposals.templates;

import org.eclipse.acceleo.aql.completion.proposals.AcceleoCompletionProposal;
import org.eclipse.emf.ecore.EClass;

/**
 * Marker for when the inserted text of an {@link AcceleoCompletionProposal} is a code template.
 * 
 * @author Florent Latombe
 */
public class AcceleoCodeTemplateCompletionProposal extends AcceleoCompletionProposal {

	/**
	 * The constructor, with a default description.
	 * 
	 * @param label
	 *            the (non-{@code null}) label {@link String} of this proposal.
	 * @param text
	 *            the (non-{@code null}) text {@link String} of this proposal.
	 * @param acceleoType
	 *            the (non-{@code null}) {@link EClass Acceleo type} from which this proposal stems.
	 */
	public AcceleoCodeTemplateCompletionProposal(String label, String text, EClass acceleoType) {
		super(label, text, acceleoType);
	}

	/**
	 * The constructor.
	 * 
	 * @param label
	 *            the (non-{@code null}) label {@link String} of this proposal.
	 * @param description
	 *            the (non-{@code null}) description {@link String} of this proposal.
	 * @param text
	 *            the (non-{@code null}) text {@link String} of this proposal.
	 * @param acceleoType
	 *            the (non-{@code null}) {@link EClass Acceleo type} from which this proposal stems.
	 */
	public AcceleoCodeTemplateCompletionProposal(String label, String description, String text,
			EClass acceleoType) {
		super(label, description, text, acceleoType, null);
	}

}
