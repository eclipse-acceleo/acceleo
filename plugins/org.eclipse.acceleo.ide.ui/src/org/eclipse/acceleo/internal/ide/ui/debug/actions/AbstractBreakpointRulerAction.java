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
package org.eclipse.acceleo.internal.ide.ui.debug.actions;

import org.eclipse.acceleo.ide.ui.AcceleoUIActivator;
import org.eclipse.acceleo.internal.ide.ui.debug.model.AcceleoLineBreakpoint;
import org.eclipse.acceleo.internal.ide.ui.debug.model.AcceleoModelPresentation;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.AbstractMarkerAnnotationModel;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.IUpdate;

/**
 * An abstract breakpoint ruler action.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public abstract class AbstractBreakpointRulerAction extends Action implements IUpdate {

	/**
	 * A vertical ruler is a visual component which may serve text viewers as an annotation presentation area.
	 */
	protected IVerticalRulerInfo info;

	/**
	 * The text editor.
	 */
	protected ITextEditor textEditor;

	/**
	 * The breakpoint.
	 */
	private IBreakpoint breakpoint;

	/**
	 * Gets the breakpoint.
	 * 
	 * @return the breakpoint
	 */
	protected IBreakpoint getBreakpoint() {
		return breakpoint;
	}

	/**
	 * Sets the breakpoint.
	 * 
	 * @param aBreakpoint
	 *            is the breakpoint
	 */
	protected void setBreakpoint(IBreakpoint aBreakpoint) {
		breakpoint = aBreakpoint;
	}

	/**
	 * Gets the text editor.
	 * 
	 * @return the text editor
	 */
	protected ITextEditor getTextEditor() {
		return textEditor;
	}

	/**
	 * Sets the text editor.
	 * 
	 * @param aTextEditor
	 *            is text editor
	 */
	protected void setTextEditor(ITextEditor aTextEditor) {
		textEditor = aTextEditor;
	}

	/**
	 * Gets the vertical ruler which may serve text viewers as an annotation presentation area.
	 * 
	 * @return the vertical ruler
	 */
	protected IVerticalRulerInfo getInfo() {
		return info;
	}

	/**
	 * Sets the vertical ruler.
	 * 
	 * @param rulerInfo
	 *            is the vertical ruler
	 */
	protected void setInfo(IVerticalRulerInfo rulerInfo) {
		info = rulerInfo;
	}

	/**
	 * Returns the breakpoint.
	 * 
	 * @return the breakpoint
	 */
	protected IBreakpoint determineBreakpoint() {
		IBreakpoint[] breakpoints = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints(
				AcceleoModelPresentation.ID_ACCELEO_DEBUG_MODEL);
		for (int i = 0; i < breakpoints.length; i++) {
			if (breakpoints[i] instanceof AcceleoLineBreakpoint) {
				try {
					if (breakpointAtRulerLine((AcceleoLineBreakpoint)breakpoints[i])) {
						return breakpoints[i];
					}
				} catch (CoreException e) {
					AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
					continue;
				}
			}
		}
		return null;
	}

	/**
	 * Indicates if the given breakpoint is active at the ruler line.
	 * 
	 * @param aBreakpoint
	 *            is the breakpoint
	 * @return true if the given breakpoint is active
	 * @throws CoreException
	 *             when an issue occurs
	 */
	private boolean breakpointAtRulerLine(AcceleoLineBreakpoint aBreakpoint) throws CoreException {
		AbstractMarkerAnnotationModel model = getAnnotationModel();
		if (model != null) {
			Position position = model.getMarkerPosition(aBreakpoint.getMarker());
			if (position != null) {
				IDocumentProvider provider = getTextEditor().getDocumentProvider();
				IDocument doc = provider.getDocument(getTextEditor().getEditorInput());
				try {
					int markerLineNumber = doc.getLineOfOffset(position.getOffset());
					int rulerLine = getInfo().getLineOfLastMouseButtonActivity();
					if (rulerLine == markerLineNumber) {
						return !getTextEditor().isDirty()
								|| aBreakpoint.getLineNumber() == markerLineNumber + 1;
					}
				} catch (BadLocationException x) {
					// continue
					AcceleoUIActivator.log(x, true);
				}
			}
		}

		return false;
	}

	/**
	 * Returns the AbstractMarkerAnnotationModel of the editor's input.
	 * 
	 * @return the marker annotation model
	 */
	protected AbstractMarkerAnnotationModel getAnnotationModel() {
		IDocumentProvider provider = textEditor.getDocumentProvider();
		IAnnotationModel model = provider.getAnnotationModel(getTextEditor().getEditorInput());
		if (model instanceof AbstractMarkerAnnotationModel) {
			return (AbstractMarkerAnnotationModel)model;
		}
		return null;
	}

	/**
	 * Gets the resource for which to create the marker.
	 * 
	 * @return the resource for which to create the marker or <code>null</code>
	 */
	protected IResource getResource() {
		IEditorInput input = textEditor.getEditorInput();
		IResource resource = (IResource)input.getAdapter(IFile.class);
		if (resource == null) {
			resource = (IResource)input.getAdapter(IResource.class);
		}
		return resource;
	}

}
