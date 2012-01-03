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

import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.ISuspendResume;
import org.eclipse.debug.ui.actions.IRunToLineTarget;
import org.eclipse.debug.ui.actions.RunToLineHandler;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * Run to line target for the Acceleo debugger. An adapter for a "run to line" operation.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoRunToLineAdapter implements IRunToLineTarget {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.ui.actions.IRunToLineTarget#runToLine(org.eclipse.ui.IWorkbenchPart,
	 *      org.eclipse.jface.viewers.ISelection, org.eclipse.debug.core.model.ISuspendResume)
	 */
	public void runToLine(IWorkbenchPart part, ISelection selection, ISuspendResume target)
			throws CoreException {
		IEditorPart editorPart = (IEditorPart)part;
		ITextEditor textEditor = (ITextEditor)editorPart;
		ITextSelection textSelection = (ITextSelection)selection;
		int lineNumber = textSelection.getStartLine() + 1;
		int offset = textSelection.getOffset();
		int length = textSelection.getLength();
		if (lineNumber > 0 && textEditor instanceof AcceleoEditor && target instanceof IAdaptable) {
			IDebugTarget debugTarget = (IDebugTarget)((IAdaptable)target).getAdapter(IDebugTarget.class);
			if (debugTarget != null) {
				ASTNode astNode = AcceleoLineBreakpointAdapter.getBreakpointASTNodeAt(
						((AcceleoEditor)textEditor).getContent(), offset);
				if (astNode != null) {
					IFile resource = (IFile)textEditor.getEditorInput().getAdapter(IResource.class);
					IBreakpoint breakpoint = new AcceleoRunToLineBreakpoint(astNode, resource, lineNumber,
							offset, length);
					RunToLineHandler handler = new RunToLineHandler(debugTarget, target, breakpoint);
					handler.run(new NullProgressMonitor());
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.ui.actions.IRunToLineTarget#canRunToLine(org.eclipse.ui.IWorkbenchPart,
	 *      org.eclipse.jface.viewers.ISelection, org.eclipse.debug.core.model.ISuspendResume)
	 */
	public boolean canRunToLine(IWorkbenchPart part, ISelection selection, ISuspendResume target) {
		return target instanceof IDebugElement
				&& ((IDebugElement)target).getModelIdentifier().equals(
						AcceleoModelPresentation.ID_ACCELEO_DEBUG_MODEL);
	}
}
