/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.completion.proposals;

import java.util.Objects;

import org.eclipse.emf.ecore.EClass;

/**
 * Represents a completion proposal for the Acceleo language, usually displayed in a list when hitting
 * ctrl+space in the editor.
 * 
 * @author Florent Latombe
 */
public class AcceleoCompletionProposal {

	/**
	 * A new line in HTML.
	 */
	public static final String DESCRIPTION_NEWLINE = "<br/>";

	/**
	 * Opening tag in HTML for a code sample.
	 */
	public static final String DESCRIPTION_CODE_OPEN = "<pre>";

	/**
	 * Closing tag in HTML for a code sample.
	 */
	public static final String DESCRIPTION_CODE_CLOSE = "</pre>";

	/**
	 * Opening tag in HTML for a bold text.
	 */
	public static final String DESCRIPTION_BOLD_OPEN = "<b>";

	/**
	 * Closing tag in HTML for a bold text.
	 */
	public static final String DESCRIPTION_BOLD_CLOSE = "</b>";

	/**
	 * Opening tag in HTML for a list.
	 */
	public static final String DESCRIPTION_LIST_OPEN = "<ul>";

	/**
	 * Closing tag in HTML for a list.
	 */
	public static final String DESCRIPTION_LIST_CLOSE = "</ul>";

	/**
	 * Opening tag in HTML for a list item.
	 */
	public static final String DESCRIPTION_LIST_ITEM_OPEN = "<li>";

	/**
	 * Closing tag in HTML for a list item.
	 */
	public static final String DESCRIPTION_LIST_ITEM_CLOSE = "</li>";

	/**
	 * The text of the proposal, to insert in the source at the position the completion was requested.
	 */
	private final String text;

	/**
	 * A short description of this proposal.
	 */
	private final String description;

	/**
	 * A short label for this proposal.
	 */
	private final String label;

	/**
	 * The Acceleo {@link EClass} on which this proposal is based.
	 */
	private final EClass acceleoType;

	/**
	 * The constructor, with a default description that consists of the inserted text.
	 * 
	 * @param label
	 *            the (maybe-{@code null}) label of this proposal.
	 * @param text
	 *            the (non-{@code null}) text to insert in the Acceleo source at the completion location.
	 * @param acceleoType
	 *            the (maybe-{@code null}) {@link EClass Acceleo type} on which this proposal is based.
	 */
	public AcceleoCompletionProposal(String label, String text, EClass acceleoType) {
		this(label, "Inserts the following text: " + DESCRIPTION_CODE_OPEN + text + DESCRIPTION_CODE_CLOSE,
				text, acceleoType);
	}

	/**
	 * The constructor.
	 * 
	 * @param label
	 *            the (maybe-{@code null}) label of this proposal.
	 * @param description
	 *            the (non-{@code null}) description of this proposal. Supports HTML.
	 * @param text
	 *            the (non-{@code null}) text to insert in the Acceleo source at the completion location.
	 * @param acceleoType
	 *            the (maybe-{@code null}) {@link EClass Acceleo type} on which this proposal is based.
	 */
	public AcceleoCompletionProposal(String label, String description, String text, EClass acceleoType) {
		this.label = label;
		this.description = Objects.requireNonNull(description);
		this.text = Objects.requireNonNull(text);
		this.acceleoType = acceleoType;
	}

	/**
	 * Provides the label of this proposal.
	 * 
	 * @return the (maybe-{@code null}) {@link String} label of this proposal.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Provides the inserted text for this proposal.
	 * 
	 * @return the (non-{@code null}) {@link String} to insert at the completion location.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Provides the description of this proposal, which gives additional information about this proposal to
	 * the end user, possibly using HTML tags.
	 * 
	 * @return the (maybe-{@code null}) {@link String} description of this proposal.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Provides the {@link EClass Acceleo type} of this proposal.
	 * 
	 * @return the (maybe-{@code null}) {@link EClass} on which this proposal is based.
	 */
	public EClass getAcceleoType() {
		return acceleoType;
	}
}
