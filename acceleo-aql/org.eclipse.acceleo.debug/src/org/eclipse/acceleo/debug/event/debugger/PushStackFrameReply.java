/*******************************************************************************
 * Copyright (c) 2015, 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.debug.event.debugger;

import org.eclipse.emf.ecore.EObject;

/**
 * Reply sent when the thread has to push a stack frame.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class PushStackFrameReply extends AbstractThreadReply {

	/**
	 * The stack frame name.
	 */
	private final String name;

	/**
	 * The stack frame context.
	 */
	private final EObject context;

	/**
	 * The stack frame current instruction.
	 */
	private final EObject currentInstruction;

	/**
	 * Tells if we can step into the current instruction.
	 */
	private final boolean canStepInto;

	/**
	 * Constructor for {@link org.eclipse.acceleo.debug.Thread Thread}.
	 * 
	 * @param threadName
	 *            the {@link org.eclipse.acceleo.debug.Thread#getName() thread name}
	 * @param name
	 *            the stack frame name
	 * @param context
	 *            the stack frame context
	 * @param currentInstruction
	 *            the stack frame current instruction
	 * @param canStepInto
	 *            tells if we can step into the current instruction
	 */
	public PushStackFrameReply(String threadName, String name, EObject context, EObject currentInstruction,
			boolean canStepInto) {
		super(threadName);
		this.name = name;
		this.context = context;
		this.currentInstruction = currentInstruction;
		this.canStepInto = canStepInto;
	}

	/**
	 * Gets the stack frame name.
	 * 
	 * @return the stack frame name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the context of the stack frame.
	 * 
	 * @return the context of the stack frame
	 */
	public EObject getContext() {
		return context;
	}

	/**
	 * Gets the current instruction.
	 * 
	 * @return the current instruction
	 */
	public EObject getCurrentInstruction() {
		return currentInstruction;
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
