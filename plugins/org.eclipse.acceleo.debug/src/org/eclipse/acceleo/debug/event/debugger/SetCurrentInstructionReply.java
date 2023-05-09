/*******************************************************************************
 * Copyright (c) 2015, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.debug.event.debugger;

import org.eclipse.emf.ecore.EObject;

/**
 * Reply sent when the thread has to set the current instrcution.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class SetCurrentInstructionReply extends AbstractThreadReply {

	/**
	 * The current instruction.
	 */
	private final EObject instruction;

	/**
	 * Tells if we can step into the current instruction.
	 */
	private final boolean canStepInto;

	/**
	 * Constructor for {@link org.eclipse.acceleo.debug.Thread Thread}.
	 * 
	 * @param threadID
	 *            the {@link Thread#getId() ID}
	 * @param instruction
	 *            the current instruction
	 * @param canStepInto
	 *            tells if we can step into the current instruction
	 */
	public SetCurrentInstructionReply(Long threadID, EObject instruction, boolean canStepInto) {
		super(threadID);
		this.instruction = instruction;
		this.canStepInto = canStepInto;
	}

	/**
	 * Gets the current instruction.
	 * 
	 * @return the current instruction
	 */
	public EObject getInstruction() {
		return instruction;
	}

	/**
	 * Tells if we can step into the current instruction.
	 * 
	 * @return <code>true</code> if we can step into the current instruction, <code>false</code> otherwise
	 */
	public boolean isCanStepInto() {
		return canStepInto;
	}

}
