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

import org.eclipse.acceleo.engine.internal.debug.ASTFragment;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.ocl.utilities.ASTNode;

/**
 * A run to line breakpoint. The breakpoint for a "run to line" operation.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoRunToLineBreakpoint extends AcceleoLineBreakpoint {

	/**
	 * Constructs a run-to-line breakpoint in the given Acceleo file.
	 * 
	 * @param astNode
	 *            is the AST node to associate with the breakpoint.
	 * @param resource
	 *            file on which to set the breakpoint
	 * @param lineNumber
	 *            1-based line number of the breakpoint
	 * @param offset
	 *            offset of the breakpoint
	 * @param length
	 *            length of the breakpoint
	 * @exception DebugException
	 *                if unable to create the breakpoint
	 */
	public AcceleoRunToLineBreakpoint(final ASTNode astNode, final IFile resource, final int lineNumber,
			final int offset, final int length) throws DebugException {
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				IMarker marker = ResourcesPlugin.getWorkspace().getRoot().createMarker(MARKER_ID);
				setMarker(marker);
				marker.setAttribute(IBreakpoint.ENABLED, Boolean.TRUE);
				marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
				marker.setAttribute(IBreakpoint.ID, getModelIdentifier());
				marker.setAttribute(IMarker.CHAR_START, offset);
				marker.setAttribute(IMarker.CHAR_END, offset + length);
				marker.setAttribute(AST_FRAGMENT, new ASTFragment(astNode).toString());
				marker.setAttribute(IMarker.MESSAGE, AcceleoUIMessages.getString(
						"AcceleoLineBreakpoint.HoverText", new Object[] {astNode.eClass().getName(), //$NON-NLS-1$
								resource.getName(), Integer.toString(lineNumber), }));
				setRegistered(false);
			}
		};
		run(getMarkerRule(resource), runnable);
	}

	/**
	 * Returns whether this breakpoint is a run-to-line breakpoint.
	 * 
	 * @return whether this breakpoint is a run-to-line breakpoint
	 */
	public boolean isRunToLineBreakpoint() {
		return true;
	}

}
