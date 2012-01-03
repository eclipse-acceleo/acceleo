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

import java.io.File;

import org.eclipse.acceleo.engine.internal.debug.ASTFragment;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.LineBreakpoint;
import org.eclipse.ocl.utilities.ASTNode;

/**
 * Acceleo implementation of a line breakpoint.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoLineBreakpoint extends LineBreakpoint {

	/**
	 * The line breakpoint marker ID.
	 */
	public static final String MARKER_ID = "org.eclipse.acceleo.ide.ui.debug.markerType.lineBreakpoint"; //$NON-NLS-1$

	/**
	 * AST node breakpoint marker attribute. This marker attribute is used to get the URI fragment of the AST
	 * node associated with this breakpoint.
	 */
	public static final String AST_FRAGMENT = "astFragment"; //$NON-NLS-1$

	/**
	 * Default constructor is required for the breakpoint manager to re-create persisted breakpoints. After
	 * instantiating a breakpoint, the <code>setMarker(...)</code> method is called to restore this
	 * breakpoint's attributes.
	 */
	public AcceleoLineBreakpoint() {
		// Empty implementation, this allows reflective instantiation
	}

	/**
	 * Constructs a line breakpoint on the given resource at the given line number. The line number is 1-based
	 * (i.e. the first line of a file is line number 1). The Acceleo VM uses 0-based line numbers, so this
	 * line number translation is done at breakpoint install time.
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
	 * @throws CoreException
	 *             when an issue occurs
	 */
	public AcceleoLineBreakpoint(final ASTNode astNode, final IResource resource, final int lineNumber,
			final int offset, final int length) throws CoreException {
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				IMarker marker = resource.createMarker(MARKER_ID);
				setMarker(marker);
				marker.setAttribute(IBreakpoint.ENABLED, Boolean.TRUE);
				marker.setAttribute(IMarker.LINE_NUMBER, lineNumber);
				marker.setAttribute(IBreakpoint.ID, getModelIdentifier());
				marker.setAttribute(IMarker.CHAR_START, offset);
				marker.setAttribute(IMarker.CHAR_END, offset + length);
				marker.setAttribute(AST_FRAGMENT, new ASTFragment(astNode).toString());
				marker.setAttribute(
						IMarker.MESSAGE,
						AcceleoUIMessages
								.getString(
										"AcceleoLineBreakpoint.HoverText", new Object[] {astNode.eClass().getName(), resource.getName(), Integer.toString(lineNumber), })); //$NON-NLS-1$
			}
		};
		run(getMarkerRule(resource), runnable);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.IBreakpoint#getModelIdentifier()
	 */
	public String getModelIdentifier() {
		return AcceleoModelPresentation.ID_ACCELEO_DEBUG_MODEL;
	}

	/**
	 * Returns the current template file.
	 * 
	 * @return the current template file
	 */
	public File getFile() {
		File ret = null;
		if (getMarker().getResource() instanceof IFile) {
			ret = ((IFile)getMarker().getResource()).getLocation().toFile();
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.core.model.Breakpoint#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) throws CoreException {
		super.setEnabled(enabled);
		IBreakpointManager breakpointManager = DebugPlugin.getDefault().getBreakpointManager();
		if (breakpointManager != null) {
			breakpointManager.fireBreakpointChanged(this);
		}
	}

	/**
	 * Gets the URI fragment of the AST node associated with this breakpoint.
	 * 
	 * @return the URI fragment of the AST node
	 */
	public ASTFragment getASTFragment() {
		return new ASTFragment(getMarker().getAttribute(AST_FRAGMENT, "")); //$NON-NLS-1$
	}

}
