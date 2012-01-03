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
package org.eclipse.acceleo.internal.ide.ui.debug.model;

import java.util.Iterator;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.debug.core.StackInfo;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IRegisterGroup;
import org.eclipse.debug.core.model.IStackFrame;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.emf.ecore.EObject;

/**
 * The Acceleo stack frame. A stack frame represents an execution context in a suspended thread. A stack frame
 * contains variables representing visible locals and arguments at the current execution location.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoStackFrame extends AbstractDebugElement implements IStackFrame {
	/** Externalized name of the "self" OCL variable to avoid too many distinct uses. */
	private static final String SELF_VARIABLE_NAME = "self"; //$NON-NLS-1$

	/**
	 * The current Acceleo thread.
	 */
	private IThread thread;

	/**
	 * Current data of the debugger execution stack. The debugger stack is shown in the "Launch View".
	 */
	private StackInfo stackInfo;

	/**
	 * Constructor. It constructs a stack frame in the given thread with the given frame data.
	 * 
	 * @param thread
	 *            the Acceleo thread
	 * @param aStackInfo
	 *            the current data of the debugger execution stack
	 */
	public AcceleoStackFrame(IThread thread, StackInfo aStackInfo) {
		super(thread.getDebugTarget());
		this.thread = thread;
		stackInfo = aStackInfo;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getThread()
	 */
	public IThread getThread() {
		return thread;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getVariables()
	 */
	public IVariable[] getVariables() throws DebugException {
		IVariable[] ret = new AcceleoVariable[stackInfo.getVariables().keySet().size()];
		// Start at 1, index 0 will be used by self.
		int i = 1;
		for (Iterator<String> iterator = stackInfo.getVariables().keySet().iterator(); iterator.hasNext();) {
			String name = iterator.next();
			Object value = stackInfo.getVariables().get(name);
			AcceleoVariable var = new AcceleoVariable(this, name, value, AcceleoVariable.DEFAULT_TYPE);
			if (SELF_VARIABLE_NAME.equals(name)) {
				ret[0] = var;
			} else {
				ret[i++] = var;
			}
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#hasVariables()
	 */
	public boolean hasVariables() throws DebugException {
		return getVariables().length > 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getLineNumber()
	 */
	public int getLineNumber() throws DebugException {
		return stackInfo.getLine();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getCharStart()
	 */
	public int getCharStart() throws DebugException {
		return stackInfo.getCharStart();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getCharEnd()
	 */
	public int getCharEnd() throws DebugException {
		return stackInfo.getCharEnd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getName()
	 */
	public String getName() throws DebugException {
		return stackInfo.getFile().getName();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#getRegisterGroups()
	 */
	public IRegisterGroup[] getRegisterGroups() throws DebugException {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStackFrame#hasRegisterGroups()
	 */
	public boolean hasRegisterGroups() throws DebugException {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStep#canStepInto()
	 */
	public boolean canStepInto() {
		return getThread().canStepInto();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStep#canStepOver()
	 */
	public boolean canStepOver() {
		return getThread().canStepOver();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStep#canStepReturn()
	 */
	public boolean canStepReturn() {
		return getThread().canStepReturn();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStep#isStepping()
	 */
	public boolean isStepping() {
		return getThread().isStepping();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStep#stepInto()
	 */
	public void stepInto() throws DebugException {
		getThread().stepInto();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStep#stepOver()
	 */
	public void stepOver() throws DebugException {
		getThread().stepOver();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IStep#stepReturn()
	 */
	public void stepReturn() throws DebugException {
		getThread().stepReturn();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#canResume()
	 */
	public boolean canResume() {
		return getThread().canResume();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#canSuspend()
	 */
	public boolean canSuspend() {
		return getThread().canSuspend();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#isSuspended()
	 */
	public boolean isSuspended() {
		return getThread().isSuspended();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#resume()
	 */
	public void resume() throws DebugException {
		getThread().resume();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ISuspendResume#suspend()
	 */
	public void suspend() throws DebugException {
		getThread().suspend();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#canTerminate()
	 */
	public boolean canTerminate() {
		return getThread().canTerminate();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#isTerminated()
	 */
	public boolean isTerminated() {
		return getThread().isTerminated();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.ITerminate#terminate()
	 */
	public void terminate() throws DebugException {
		getThread().terminate();
	}

	/**
	 * Returns the name of the source file this stack frame is associated with.
	 * 
	 * @return the name of the source file this stack frame is associated with
	 */
	public String getSourceName() {
		if (stackInfo != null && stackInfo.getFile() != null) {
			return stackInfo.getFile().getName();
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * Returns a displayable String for the stack's AST node.
	 * 
	 * @return A displayable String for the stack's AST node
	 */
	public String getASTNodeDisplayString() {
		EObject containingModuleElement = stackInfo.getASTNode();
		while (!(containingModuleElement instanceof ModuleElement)) {
			containingModuleElement = containingModuleElement.eContainer();
		}
		Module containingModule = (Module)containingModuleElement.eContainer();

		return containingModule.getName() + '.' + containingModuleElement.toString();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AcceleoStackFrame) {
			AcceleoStackFrame sf = (AcceleoStackFrame)obj;
			try {
				return sf.getSourceName().equals(getSourceName()) && sf.getLineNumber() == getLineNumber()
						&& sf.getCharStart() == getCharStart() && sf.getCharEnd() == getCharEnd();
			} catch (DebugException e) {
				// continue
				AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			}
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getSourceName().hashCode();
	}

	/**
	 * Returns the debugger execution context. It means the current data of the debugger execution stack.
	 * 
	 * @return the debugger execution context
	 */
	public StackInfo getStackInfo() {
		return stackInfo;
	}

}
