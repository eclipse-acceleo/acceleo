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
package org.eclipse.acceleo.internal.ide.ui.debug.core;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.eclipse.acceleo.common.utils.CompactHashSet;
import org.eclipse.acceleo.engine.internal.debug.ASTFragment;
import org.eclipse.acceleo.engine.internal.debug.IDebugAST;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.ide.ui.resources.AcceleoProject;
import org.eclipse.acceleo.internal.ide.ui.resource.AcceleoUIResourceSet;
import org.eclipse.acceleo.internal.parser.cst.utils.FileContent;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.utilities.ASTNode;

/**
 * Template evaluation debugger to debug an Acceleo AST evaluation.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoDebugger implements IDebugAST, ITemplateDebugger {

	/**
	 * The current state of the debugger : RESULME or SUSPENDED.
	 */
	private int state = ITemplateDebugger.RESUMED;

	/**
	 * Indicates if the current action is to go into the next evaluation step.
	 */
	private boolean stepInto = true;

	/**
	 * Indicates if the current action is to go over the next evaluation step.
	 */
	private boolean stepOver = true;

	/**
	 * Indicates if the current action is to go to the parent evaluation step.
	 */
	private boolean stepReturn = true;

	/**
	 * Indicates if the thread is terminated.
	 */
	private boolean terminated;

	/**
	 * Contains the AST node fragments of all the available breakpoints.
	 */
	private Set<ASTFragment> breakpointsASTFragments = new CompactHashSet<ASTFragment>();

	/**
	 * Contains all the AST node fragments of the project to debug. The map value is the position of the AST
	 * node in the Acceleo file.
	 */
	private Map<ASTFragment, FileRegion> allASTFragments = new HashMap<ASTFragment, FileRegion>();

	/**
	 * Debugger events listeners during a template evaluation.
	 */
	private Set<ITemplateDebuggerListener> listeners = new CompactHashSet<ITemplateDebuggerListener>();

	/**
	 * The current stack of the debugger. It corresponds to the "Launch View" stack.
	 */
	private Stack<StackInfo> stackDebugger = new Stack<StackInfo>();

	/**
	 * The previous stack debugger size. It is useful to know if the stack grows or not.
	 */
	private int stackDebuggerSize;

	/**
	 * The position of the AST node in the Acceleo file : line, offset...
	 * 
	 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
	 */
	private final class FileRegion {

		/**
		 * The Acceleo file which contains the the AST node.
		 */
		File file;

		/**
		 * The line of the AST node offset in the Acceleo file.
		 */
		int line;

		/**
		 * The beginning index of the AST node.
		 */
		int start;

		/**
		 * The ending index of the AST node.
		 */
		int end;

		/**
		 * Constructor.
		 */
		public FileRegion() {
			super();
		}

	}

	/**
	 * Constructor.
	 * 
	 * @param project
	 *            is the project that contains the Acceleo Application to launch
	 */
	public AcceleoDebugger(IProject project) {
		init(project);
	}

	/**
	 * To initialize the debugger with every AST nodes of the accessible modules.
	 * 
	 * @param project
	 *            is the current Acceleo project
	 */
	private void init(IProject project) {
		Map<File, Module> mtlFiles = getInputOutputAcceleoFiles(project);
		breakpointsASTFragments.clear();
		allASTFragments.clear();
		Set<ASTFragment> conflicts = new CompactHashSet<ASTFragment>();
		Iterator<Map.Entry<File, Module>> mtlFilesIt = mtlFiles.entrySet().iterator();
		while (mtlFilesIt.hasNext()) {
			Map.Entry<File, Module> entry = mtlFilesIt.next();
			File file = entry.getKey();
			StringBuffer buffer = FileContent.getFileContent(file);
			Module eModule = entry.getValue();
			Iterator<EObject> eAllContents = eModule.eAllContents();
			while (eAllContents.hasNext()) {
				EObject eObject = eAllContents.next();
				if (eObject instanceof ASTNode) {
					ASTFragment fragment = new ASTFragment((ASTNode)eObject);
					if (!conflicts.contains(fragment)) {
						conflicts.add(fragment);
						int start = ((ASTNode)eObject).getStartPosition();
						int end = ((ASTNode)eObject).getEndPosition();
						int line = lineNumber(buffer, start);
						if (start > -1) {
							FileRegion region = new FileRegion();
							region.file = file;
							region.line = line;
							region.start = start;
							region.end = end;
							allASTFragments.put(fragment, region);
						}
					}
				}
			}
		}
		state = ITemplateDebugger.RESUMED;
		stepInto = false;
		stepOver = false;
		stepReturn = false;
		terminated = false;
		stackDebugger.clear();
	}

	/**
	 * Gets the Acceleo files of the given project and its dependencies. It also loads the corresponding AST
	 * model of each Acceleo file in the given resource set.
	 * 
	 * @param project
	 *            is the project that contains the Acceleo Application to launch
	 * @return a map of the accessible Acceleo files and their corresponding AST models
	 */
	private Map<File, Module> getInputOutputAcceleoFiles(IProject project) {
		Map<File, Module> result = new HashMap<File, Module>();
		Iterator<AcceleoProject> acceleoProjects = new AcceleoProject(project)
				.getRecursivelyAccessibleAcceleoProjects().iterator();
		while (acceleoProjects.hasNext()) {
			AcceleoProject acceleoProject = acceleoProjects.next();
			try {
				List<IFile> mtlFiles = acceleoProject.getInputFiles();
				for (Iterator<IFile> iterator = mtlFiles.iterator(); iterator.hasNext();) {
					IFile mtlFile = iterator.next();
					IPath outputFilePath = acceleoProject.getOutputFilePath(mtlFile);
					if (outputFilePath != null) {
						URI outputFileURI = URI.createPlatformResourceURI(outputFilePath.toString(), false);
						try {
							EObject root = AcceleoUIResourceSet.getResource(outputFileURI);
							if (root instanceof Module) {
								result.put(mtlFile.getLocation().toFile(), (Module)root);
							}
						} catch (IOException e1) {
							AcceleoUIActivator.getDefault().getLog().log(
									new Status(IStatus.ERROR, AcceleoUIActivator.PLUGIN_ID, e1.getMessage(),
											e1));
						}
					}
				}
			} catch (CoreException e2) {
				AcceleoUIActivator.getDefault().getLog().log(e2.getStatus());
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.debug.IDebugAST#startDebug(org.eclipse.acceleo.engine.internal.debug.ASTFragment)
	 */
	public void startDebug(ASTFragment astFragment) {
		pushStack(astFragment);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.debug.IDebugAST#stepDebugInput(org.eclipse.acceleo.engine.internal.debug.ASTFragment,
	 *      java.util.Map)
	 */
	public void stepDebugInput(ASTFragment astFragment, Map<String, Object> variables) {
		if (isBreakpoint(astFragment, false)) {
			waitForEvent(variables);
		} else {
			updateVariables(variables);
		}
	}

	/**
	 * It updates the given variables.
	 * 
	 * @param variables
	 *            are the variables to show
	 */
	public void updateVariables(Map<String, Object> variables) {
		if (variables != null && !stackDebugger.isEmpty()) {
			StackInfo stackInfo = stackDebugger.peek();
			for (Iterator<String> iterator = variables.keySet().iterator(); iterator.hasNext();) {
				String name = iterator.next();
				Object value = variables.get(name);
				stackInfo.addVariable(name, value);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.debug.IDebugAST#stepDebugOutput(org.eclipse.acceleo.engine.internal.debug.ASTFragment,
	 *      java.lang.Object, java.lang.Object)
	 */
	public void stepDebugOutput(ASTFragment astFragment, Object input, Object output) {
		if (astFragment != null && isBreakpoint(astFragment, true)) {
			final Map<String, Object> map = new HashMap<String, Object>();
			map.put("output", output); // $N//waitForEvent(map);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.internal.debug.IDebugAST#endDebug(org.eclipse.acceleo.engine.internal.debug.ASTFragment)
	 */
	public void endDebug(ASTFragment astFragment) {
		popStack(astFragment);
	}

	/**
	 * Returns the number of the line for the given offset.
	 * 
	 * @param buffer
	 *            is the buffer
	 * @param index
	 *            is the offset in the buffer
	 * @return the line
	 */
	private int lineNumber(final StringBuffer buffer, int index) {
		if (buffer != null && index < buffer.length()) {
			int line = 1;
			for (int i = index - 1; i >= 0; i--) {
				if (buffer.charAt(i) == '\n') {
					line++;
				}
			}
			return line;
		}
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.debug.ITemplateDebugger#addBreakpoint(org.eclipse.acceleo.engine.internal.debug.ASTFragment)
	 */
	public void addBreakpoint(ASTFragment astFragment) {
		if (!astFragment.isEmpty()) {
			breakpointsASTFragments.add(astFragment);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.debug.ITemplateDebugger#removeBreakpoint(org.eclipse.acceleo.engine.internal.debug.ASTFragment)
	 */
	public void removeBreakpoint(ASTFragment astFragment) {
		if (!astFragment.isEmpty()) {
			breakpointsASTFragments.remove(astFragment);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.debug.ITemplateDebugger#addListener(org.eclipse.acceleo.engine.debug.ITemplateDebuggerListener)
	 */
	public void addListener(ITemplateDebuggerListener aListener) {
		listeners.add(aListener);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.debug.ITemplateDebugger#getState()
	 */
	public int getState() {
		return state;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.debug.ITemplateDebugger#removeListener(org.eclipse.acceleo.engine.debug.ITemplateDebuggerListener)
	 */
	public void removeListener(ITemplateDebuggerListener aListener) {
		listeners.remove(aListener);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.debug.ITemplateDebugger#resume()
	 */
	public void resume() {
		stepInto = false;
		stepOver = false;
		stepReturn = false;
		state = ITemplateDebugger.RESUMED;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.debug.ITemplateDebugger#stepInto()
	 */
	public void stepInto() {
		stepInto = true;
		stepOver = false;
		stepReturn = false;
		state = ITemplateDebugger.RESUMED;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.debug.ITemplateDebugger#stepOver()
	 */
	public void stepOver() {
		stepInto = false;
		stepOver = true;
		stepReturn = false;
		state = ITemplateDebugger.RESUMED;
		stackDebuggerSize = stackDebugger.size();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.debug.ITemplateDebugger#stepReturn()
	 */
	public void stepReturn() {
		stepInto = false;
		stepOver = false;
		stepReturn = true;
		state = ITemplateDebugger.RESUMED;
		stackDebuggerSize = stackDebugger.size();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.debug.ITemplateDebugger#suspend()
	 */
	public void suspend() {
		stepInto = false;
		stepOver = false;
		stepReturn = false;
		state = ITemplateDebugger.SUSPENDED;

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.debug.ITemplateDebugger#start()
	 */
	public void start() {
		fireStartEvent();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.debug.ITemplateDebugger#end()
	 */
	public void end() {
		fireEndEvent();
	}

	/**
	 * Indicates if there is a breakpoint for the given AST node.
	 * 
	 * @param astFragment
	 *            is an AST node fragment
	 * @param isOutputStep
	 *            indicates if the debug step is an output step
	 * @return true if there is a breakpoint at the given position
	 */
	private boolean isBreakpoint(ASTFragment astFragment, boolean isOutputStep) {
		if (terminated) {
			return false;
		}

		boolean ret = false;
		if (stepInto) {
			ret = true;
		} else if (stepOver) {
			if (stackDebugger.size() <= stackDebuggerSize) {
				ret = true;
				stackDebuggerSize = stackDebugger.size();
			} else {
				ret = false;
			}
		} else if (stepReturn) {
			if (stackDebugger.size() < stackDebuggerSize) {
				ret = true;
				stackDebuggerSize = stackDebugger.size();
			} else {
				ret = false;
			}
		} else {
			if (astFragment.isEmpty() || isOutputStep) {
				ret = false;
			} else {
				ret = breakpointsASTFragments.contains(astFragment);
			}
		}
		if (ret) {
			state = SUSPENDED;
		}
		return ret;
	}

	/**
	 * Suspend on a breakpoint. Informs all debugger event listeners.
	 */
	private void fireSuspendedBreakpointEvent() {
		for (Iterator<ITemplateDebuggerListener> iterator = listeners.iterator(); iterator.hasNext();) {
			ITemplateDebuggerListener debugListener = iterator.next();
			debugListener.suspendBreakpoint();
		}
	}

	/**
	 * Resume after client action. Informs all debugger event listeners.
	 */
	private void fireResumedClientEvent() {
		for (Iterator<ITemplateDebuggerListener> iterator = listeners.iterator(); iterator.hasNext();) {
			ITemplateDebuggerListener debugListener = iterator.next();
			debugListener.resumeClient();
		}
	}

	/**
	 * Resume after one step. Informs all debugger event listeners.
	 */
	private void fireResumedStepEvent() {
		for (Iterator<ITemplateDebuggerListener> iterator = listeners.iterator(); iterator.hasNext();) {
			ITemplateDebuggerListener debugListener = iterator.next();
			debugListener.resumeStep();
		}
	}

	/**
	 * Suspend after a step. Informs all debugger event listeners.
	 */
	private void fireSuspendedStepEvent() {
		for (Iterator<ITemplateDebuggerListener> iterator = listeners.iterator(); iterator.hasNext();) {
			ITemplateDebuggerListener debugListener = iterator.next();
			debugListener.suspendStep();
		}
	}

	/**
	 * Beginning action. Informs all debugger event listeners.
	 */
	private void fireStartEvent() {
		for (Iterator<ITemplateDebuggerListener> iterator = listeners.iterator(); iterator.hasNext();) {
			ITemplateDebuggerListener debugListener = iterator.next();
			debugListener.start();
		}
	}

	/**
	 * Ending action. Informs all debugger event listeners.
	 */
	private void fireEndEvent() {
		for (Iterator<ITemplateDebuggerListener> iterator = listeners.iterator(); iterator.hasNext();) {
			ITemplateDebuggerListener debugListener = iterator.next();
			debugListener.end();
		}
	}

	/**
	 * It shows the given variables and it waits a user event.
	 * 
	 * @param variables
	 *            are the variables to show
	 */
	public void waitForEvent(Map<String, Object> variables) {
		if (state == SUSPENDED && !stackDebugger.isEmpty()) {
			if (variables != null) {
				StackInfo stackInfo = stackDebugger.peek();
				for (Iterator<String> iterator = variables.keySet().iterator(); iterator.hasNext();) {
					String name = iterator.next();
					Object value = variables.get(name);
					stackInfo.addVariable(name, value);
				}
			}
			if (stepInto || stepOver || stepReturn) {
				fireSuspendedStepEvent();
			} else {
				fireSuspendedBreakpointEvent();
			}
			while (state == SUSPENDED) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// continue
				}
			}
			if (stepInto || stepOver || stepReturn) {
				fireResumedClientEvent();
			} else {
				fireResumedClientEvent();
				fireResumedStepEvent();
			}
			try {
				final int sleep = 300;
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				// continue
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.debug.ITemplateDebugger#getStack()
	 */
	public StackInfo[] getStack() {
		StackInfo[] ret = new StackInfo[stackDebugger.size()];
		int i = 0;
		for (Iterator<StackInfo> iterator = stackDebugger.iterator(); iterator.hasNext();) {
			StackInfo current = iterator.next();
			ret[i] = current;
			++i;
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.debug.ITemplateDebugger#popStack(org.eclipse.acceleo.engine.internal.debug.ASTFragment)
	 */
	public void popStack(ASTFragment astFragment) {
		FileRegion fileRegion = allASTFragments.get(astFragment);
		if (fileRegion != null) {
			stackDebugger.pop();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.debug.ITemplateDebugger#pushStack(org.eclipse.acceleo.engine.internal.debug.ASTFragment)
	 */
	public void pushStack(ASTFragment astFragment) {
		FileRegion fileRegion = allASTFragments.get(astFragment);
		if (fileRegion != null) {
			StackInfo stackInfo = new StackInfo();
			stackInfo.setNode(astFragment.getASTNode());
			stackInfo.setFile(fileRegion.file);
			stackInfo.setLine(fileRegion.line);
			stackInfo.setCharStart(fileRegion.start);
			stackInfo.setCharEnd(fileRegion.end);
			stackDebugger.push(stackInfo);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.acceleo.engine.debug.ITemplateDebugger#terminate()
	 */
	public void terminate() {
		terminated = true;
		state = RESUMED;
	}

}
