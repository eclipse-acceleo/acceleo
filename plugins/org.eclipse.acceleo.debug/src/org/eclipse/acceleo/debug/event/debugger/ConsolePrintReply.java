/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.debug.event.debugger;

/**
 * Reply sent when the debugger or a thread print to console.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class ConsolePrintReply extends AbstractThreadReply {

	/**
	 * The text to print to console.
	 */
	private final String text;

	/**
	 * Constructor for {@link org.eclipse.acceleo.debug.DebugTarget DebugTarget}.
	 */
	public ConsolePrintReply(String text) {
		this(null, text);
	}

	/**
	 * Constructor for {@link org.eclipse.acceleo.debug.Thread Thread}.
	 * 
	 * @param threadID
	 *            the {@link Thread#getId() ID}
	 * @param text
	 *            the text to print to console
	 */
	public ConsolePrintReply(Long threadID, String text) {
		super(threadID);
		this.text = text;
	}

	/**
	 * Gets the text to print to console.
	 * 
	 * @return the text to print to console
	 */
	public String getText() {
		return text;
	}

}
