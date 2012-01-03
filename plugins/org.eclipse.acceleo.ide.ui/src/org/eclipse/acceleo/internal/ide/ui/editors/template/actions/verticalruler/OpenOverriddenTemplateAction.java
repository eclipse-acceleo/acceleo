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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.verticalruler;

import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.OpenDeclarationUtils;
import org.eclipse.acceleo.model.mtl.Template;
import org.eclipse.acceleo.parser.cst.CSTNode;
import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.ui.texteditor.MarkerUtilities;

/**
 * This action will open the overridden template when someone will click on the green arrow in the vertical
 * ruler.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class OpenOverriddenTemplateAction extends Action {

	/**
	 * The Acceleo editor.
	 */
	private AcceleoEditor editor;

	/**
	 * The marker.
	 */
	private IMarker marker;

	/**
	 * The constructor.
	 * 
	 * @param acceleoEditor
	 *            The Acceleo editor
	 * @param overrideMarker
	 *            The override marker
	 */
	public OpenOverriddenTemplateAction(final AcceleoEditor acceleoEditor, final IMarker overrideMarker) {
		this.editor = acceleoEditor;
		this.marker = overrideMarker;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		int start = MarkerUtilities.getCharStart(this.marker);
		int end = MarkerUtilities.getCharEnd(this.marker);

		EObject res = null;

		final ASTNode astNode = this.editor.getContent().getResolvedASTNode(start, end);
		if (astNode != null) {
			res = OpenDeclarationUtils.findDeclarationFromAST(astNode);
		}
		if (res == null) {
			final CSTNode cstNode = this.editor.getContent().getCSTNode(start, end);
			if (cstNode != null) {
				res = OpenDeclarationUtils.findDeclarationFromCST(this.editor, astNode, cstNode);
			}
		}

		if (res instanceof Template) {
			Template template = (Template)res;
			EList<Template> overrides = template.getOverrides();
			if (overrides.size() > 0) {
				Template overriddenTemplate = overrides.get(0);
				OpenDeclarationUtils.showEObject(this.editor.getSite().getPage(), this
						.getFileURI(overriddenTemplate), OpenDeclarationUtils
						.createRegion(overriddenTemplate), overriddenTemplate);
			}
		}
	}

	/**
	 * Get the file URI for the given EObject.
	 * 
	 * @param eObj
	 *            the EObject
	 * @return the file URI if any of null
	 */
	public URI getFileURI(EObject eObj) {
		URI res = null;
		if (eObj != null && eObj.eResource() != null) {
			res = eObj.eResource().getURI();
		}
		return res;
	}

}
