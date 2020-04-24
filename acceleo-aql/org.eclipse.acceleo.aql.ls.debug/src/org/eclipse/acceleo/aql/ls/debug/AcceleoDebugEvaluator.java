/*******************************************************************************
 * Copyright (c) 2020 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.aql.ls.debug;

import java.util.Map;

import org.eclipse.acceleo.aql.AcceleoEnvironment;
import org.eclipse.acceleo.aql.IAcceleoEnvironment;
import org.eclipse.acceleo.aql.evaluation.AcceleoEvaluator;
import org.eclipse.acceleo.aql.evaluation.writer.DefaultGenerationStrategy;
import org.eclipse.acceleo.debug.AbstractDSLDebugger;
import org.eclipse.acceleo.debug.DebugTarget;
import org.eclipse.acceleo.debug.IDSLDebugger;
import org.eclipse.acceleo.debug.event.IDSLDebugEventProcessor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

/**
 * Acceleo Debugger.
 * 
 * @author <a href="mailto:yvan.lussaud@obeo.fr">Yvan Lussaud</a>
 */
public class AcceleoDebugEvaluator extends AcceleoEvaluator {

	private class Debugger extends AbstractDSLDebugger {

		Debugger(IDSLDebugEventProcessor target) {
			super(target);
		}

		@Override
		public void start(boolean noDebug, Map<String, Object> arguments) {
			setNoDebug(noDebug);
			// TODO get uri from arguments
			final IAcceleoEnvironment environment = new AcceleoEnvironment(new DefaultGenerationStrategy(),
					URI.createURI(""));

			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO spawnRunningThread(Thread.currentThread().getName(), module);
					// generate(environment, module, variables);
					terminated();
				}
			}, "Acceleo Debug Thread").start();
		}

		@Override
		public void disconnect() {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean canStepInto(Long threadID, EObject instruction) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void updateState(Long threadID, EObject instruction) {
			// TODO Auto-generated method stub

		}

		@Override
		public DebugTarget getState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean validateVariableValue(Long threadID, String variableName, String value) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Object getVariableValue(Long threadID, String stackName, String variableName, String value) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void setVariableValue(Long threadID, String stackName, String variableName, Object value) {
			// TODO Auto-generated method stub

		}

		@Override
		public EObject getNextInstruction(Long threadID, EObject currentInstruction, Stepping stepping) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	/**
	 * The {@link IDSLDebugger}.
	 */
	private final IDSLDebugger debugger;

	/**
	 * Constructor.
	 * 
	 * @param environment
	 *            the {@link IllegalAccessError}
	 * @param target
	 *            the {@link IDSLDebugEventProcessor}
	 */
	public AcceleoDebugEvaluator(IAcceleoEnvironment environment, IDSLDebugEventProcessor target) {
		super(environment);
		this.debugger = new Debugger(target);
	}

	@Override
	public Object doSwitch(EObject eObject) {
		if (!debugger.control(Thread.currentThread().getId(), eObject)) {
			Thread.currentThread().interrupt();
		}
		return super.doSwitch(eObject);
	}

	/**
	 * Gets the {@link IDSLDebugger}.
	 * 
	 * @return the {@link IDSLDebugger}
	 */
	public IDSLDebugger getDebugger() {
		return debugger;
	}

}
