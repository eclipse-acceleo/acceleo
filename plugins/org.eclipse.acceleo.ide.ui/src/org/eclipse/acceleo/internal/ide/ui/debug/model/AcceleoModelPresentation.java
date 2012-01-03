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

import org.eclipse.acceleo.common.IAcceleoConstants;
import org.eclipse.acceleo.engine.internal.debug.ASTFragment;
import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.AcceleoUIMessages;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.ILineBreakpoint;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IThread;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IValueDetailListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInput;

/**
 * Renders Acceleo debug elements.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class AcceleoModelPresentation extends LabelProvider implements IDebugModelPresentation {

	/**
	 * Unique identifier for the Acceleo debug model.
	 */
	public static final String ID_ACCELEO_DEBUG_MODEL = "org.eclipse.acceleo.ide.ui.debug.model.AcceleoModelPresentation"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.ui.IDebugModelPresentation#setAttribute(java.lang.String, java.lang.Object)
	 */
	public void setAttribute(String attribute, Object value) {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		Image result;
		if (element instanceof AcceleoVariable) {
			try {
				result = ((AcceleoValue)((AcceleoVariable)element).getValue()).getImage();
			} catch (DebugException e) {
				result = null;
				AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			}
		} else if (element instanceof AcceleoLineBreakpoint) {
			boolean isEnabled;
			try {
				isEnabled = ((AcceleoLineBreakpoint)element).isEnabled();
			} catch (CoreException e) {
				AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
				isEnabled = true;
			}
			ASTFragment astFragment = ((AcceleoLineBreakpoint)element).getASTFragment();
			if (isEnabled && astFragment != null && astFragment.getEObjectNameFilter() != null
					&& astFragment.getEObjectNameFilter().length() > 0) {
				result = AcceleoUIActivator.getDefault().getImage("icons/debug/filteredBrkp.gif"); //$NON-NLS-1$
			} else {
				result = null;
			}
		} else {
			result = null;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		String vRet = AcceleoUIMessages.getString("AcceleoModelPresentation.ElementText"); //$NON-NLS-1$
		if (element instanceof AcceleoLineBreakpoint) {
			AcceleoLineBreakpoint breakPoint = (AcceleoLineBreakpoint)element;
			try {
				String filePath;
				if (breakPoint.getFile() == null) {
					filePath = ""; //$NON-NLS-1$
				} else {
					filePath = breakPoint.getFile().getAbsolutePath();
				}
				if (filePath.lastIndexOf(File.separatorChar) != -1) {
					filePath = filePath.substring(filePath.lastIndexOf(File.separatorChar) + 1);
				}
				vRet = AcceleoUIMessages.getString("AcceleoModelPresentation.BreakpointText", new Object[] { //$NON-NLS-1$
						filePath, Integer.toString(breakPoint.getLineNumber()), });
			} catch (CoreException e) {
				AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			}
		} else if (element instanceof IProcess) {
			vRet = AcceleoUIMessages.getString("AcceleoModelPresentation.ProcessText"); //$NON-NLS-1$
		} else if (element instanceof IThread) {
			vRet = AcceleoUIMessages.getString("AcceleoModelPresentation.ThreadText"); //$NON-NLS-1$
		} else if (element instanceof IDebugTarget) {
			vRet = AcceleoUIMessages.getString("AcceleoModelPresentation.TargetText"); //$NON-NLS-1$
		} else if (element instanceof AcceleoStackFrame) {
			AcceleoStackFrame stackFrame = (AcceleoStackFrame)element;
			try {
				vRet = stackFrame.getASTNodeDisplayString() + " line : " + (stackFrame.getLineNumber()); //$NON-NLS-1$
			} catch (DebugException e) {
				AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			}
		}
		return vRet;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.ui.IDebugModelPresentation#computeDetail(org.eclipse.debug.core.model.IValue,
	 *      org.eclipse.debug.ui.IValueDetailListener)
	 */
	public void computeDetail(IValue value, IValueDetailListener listener) {
		String detail = ""; //$NON-NLS-1$
		try {
			detail = value.getValueString();
		} catch (DebugException e) {
			// continue
			AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
		}
		listener.detailComputed(value, detail);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.ui.ISourcePresentation#getEditorInput(java.lang.Object)
	 */
	public IEditorInput getEditorInput(Object element) {
		IEditorInput result;
		if (element instanceof File) {
			IFile workspaceFile = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(
					new Path(((File)element).getAbsolutePath()));
			if (workspaceFile != null && workspaceFile.isAccessible()) {
				result = new FileEditorInput(workspaceFile);
			} else {
				result = null;
			}

		} else if (element instanceof IFile) {
			result = new FileEditorInput((IFile)element);
		} else if (element instanceof ILineBreakpoint) {
			result = new FileEditorInput((IFile)((ILineBreakpoint)element).getMarker().getResource());
		} else {
			result = null;
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.debug.ui.ISourcePresentation#getEditorId(org.eclipse.ui.IEditorInput,
	 *      java.lang.Object)
	 */
	public String getEditorId(IEditorInput input, Object element) {
		String name;
		if (element instanceof File) {
			name = ((File)element).getName();
		} else if (element instanceof IFile) {
			name = ((IFile)element).getName();
		} else if (element instanceof ILineBreakpoint) {
			name = ((IFile)((ILineBreakpoint)element).getMarker().getResource()).getName();
		} else {
			name = ""; //$NON-NLS-1$
		}
		if (name.endsWith("." + IAcceleoConstants.MTL_FILE_EXTENSION)) { //$NON-NLS-1$
			return AcceleoEditor.ACCELEO_EDITOR_ID;
		}
		return null;
	}

}
