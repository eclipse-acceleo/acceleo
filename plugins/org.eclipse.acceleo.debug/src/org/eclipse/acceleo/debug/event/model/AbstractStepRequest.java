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
package org.eclipse.acceleo.debug.event.model;

import org.eclipse.emf.ecore.EObject;

/**
 * Request sent to step the execution of the debugger.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public abstract class AbstractStepRequest extends AbstractThreadRequest {

	/**
	 * The {@link EObject} representing the current instruction. NOTE: this instruction should only be used
	 * for synchronization checks.
	 */
	private final EObject instruction;

	/**
	 * Constructor.
	 * 
	 * @param threadID
	 *            the {@link Thread#getId() ID}
	 * @param instruction
	 *            the {@link EObject} representing the current instruction
	 */
	public AbstractStepRequest(Long threadID, EObject instruction) {
		super(threadID);
		this.instruction = instruction;
	}

	/**
	 * Gets the {@link EObject} representing the current instruction.
	 * 
	 * @return the {@link EObject} representing the current instruction
	 */
	public EObject getInstrcution() {
		return instruction;
	}

}
