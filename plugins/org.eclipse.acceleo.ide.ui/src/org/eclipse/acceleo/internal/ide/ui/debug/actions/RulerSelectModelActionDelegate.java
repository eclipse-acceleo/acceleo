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
import org.eclipse.acceleo.internal.ide.ui.builders.AcceleoMarkerUtils;
import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.actions.verticalruler.OpenOverriddenTemplateAction;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.source.IVerticalRulerInfo;
import org.eclipse.ui.texteditor.AbstractRulerActionDelegate;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.MarkerUtilities;

/**
 * This class serves as an adapter for actions contributed to the vertical ruler's context menu.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class RulerSelectModelActionDelegate extends AbstractRulerActionDelegate {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.texteditor.AbstractRulerActionDelegate#createAction(org.eclipse.ui.texteditor.ITextEditor,
	 *      org.eclipse.jface.text.source.IVerticalRulerInfo)
	 */
	@Override
	protected IAction createAction(ITextEditor editor, IVerticalRulerInfo rulerInfo) {
		// If we click on a override marker (the green arrow in the left of the editor) we open the file in which the overridden template is defined.
		if (editor instanceof AcceleoEditor && ((AcceleoEditor)editor).getFile() != null) {
			AcceleoEditor acceleoEditor = (AcceleoEditor)editor;
			IFile file = acceleoEditor.getFile();
			try {
				IMarker[] markers = file.findMarkers(AcceleoMarkerUtils.OVERRIDE_MARKER_ID, false,
						IResource.DEPTH_INFINITE);
				for (IMarker iMarker : markers) {
					if ((rulerInfo.getLineOfLastMouseButtonActivity() + 1) == MarkerUtilities
							.getLineNumber(iMarker)) {
						return new OpenOverriddenTemplateAction(acceleoEditor, iMarker);
					}
				}
			} catch (CoreException e) {
				AcceleoUIActivator.getDefault().getLog().log(e.getStatus());
			}
		}
		return new BreakpointSelectModelRulerAction(editor, rulerInfo);
	}

}
