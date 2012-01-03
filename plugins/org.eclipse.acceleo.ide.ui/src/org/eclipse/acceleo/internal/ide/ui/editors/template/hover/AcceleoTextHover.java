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
package org.eclipse.acceleo.internal.ide.ui.editors.template.hover;

import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.AcceleoUIDocumentationUtils;
import org.eclipse.acceleo.internal.ide.ui.editors.template.utils.OpenDeclarationUtils;
import org.eclipse.acceleo.model.mtl.Documentation;
import org.eclipse.acceleo.model.mtl.DocumentedElement;
import org.eclipse.acceleo.model.mtl.Module;
import org.eclipse.acceleo.model.mtl.ModuleDocumentation;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.acceleo.model.mtl.ModuleElementDocumentation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextHoverExtension2;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.ocl.ecore.IteratorExp;
import org.eclipse.ocl.ecore.Variable;
import org.eclipse.ocl.utilities.ASTNode;
import org.eclipse.swt.graphics.Point;

/**
 * The Acceleo Text Hover computes what will be shown in the hover popup.
 * 
 * @author <a href="mailto:stephane.begaudeau@obeo.fr">Stephane Begaudeau</a>
 */
public class AcceleoTextHover implements ITextHover, ITextHoverExtension2 {

	/**
	 * The Acceleo Editor.
	 */
	private AcceleoEditor editor;

	/**
	 * The constructor.
	 * 
	 * @param acceleoEditor
	 *            The Acceleo Editor
	 */
	public AcceleoTextHover(final AcceleoEditor acceleoEditor) {
		this.editor = acceleoEditor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.ITextHover#getHoverInfo(org.eclipse.jface.text.ITextViewer,
	 *      org.eclipse.jface.text.IRegion)
	 */
	@Deprecated
	public String getHoverInfo(final ITextViewer textViewer, final IRegion hoverRegion) {
		String result = null;
		if (hoverRegion != null && editor != null && editor.getContent() != null) {
			ASTNode astNode = editor.getContent().getResolvedASTNode(hoverRegion.getOffset(),
					hoverRegion.getOffset() + hoverRegion.getLength());
			if (astNode != null) {
				EObject eObject = OpenDeclarationUtils.findDeclarationFromAST(astNode);
				if (eObject instanceof IteratorExp && editor.getContent().getOCLEnvironment() != null) {
					eObject = OpenDeclarationUtils.findIteratorEOperation(editor.getContent()
							.getOCLEnvironment(), (IteratorExp)eObject);
				}
				if (eObject != null) {
					result = getInfo(eObject);
				}
			} else if (editor.getContent().isInModuleHeader(hoverRegion.getOffset(),
					hoverRegion.getOffset() + hoverRegion.getLength(), true)) {
				Module module = editor.getContent().getAST();
				result = getInfo(module);
			}
		}
		return result;
	}

	/**
	 * Gets the info text for the given eObject.
	 * 
	 * @param eObject
	 *            is an object of the MTL syntax
	 * @return the text
	 */
	private String getInfo(EObject eObject) {
		String result = ""; //$NON-NLS-1$
		if (eObject instanceof DocumentedElement) {
			result = AcceleoUIDocumentationUtils.getDocumentation((DocumentedElement)eObject);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.ITextHoverExtension2#getHoverInfo2(org.eclipse.jface.text.ITextViewer,
	 *      org.eclipse.jface.text.IRegion)
	 */
	public Object getHoverInfo2(final ITextViewer textViewer, final IRegion hoverRegion) {
		Object result = null;
		if (hoverRegion != null && editor != null && editor.getContent() != null) {
			ASTNode astNode = editor.getContent().getResolvedASTNode(hoverRegion.getOffset(),
					hoverRegion.getOffset() + hoverRegion.getLength());
			if (astNode != null) {
				EObject eObject = null;

				// If the user has selected the name of the variable
				if (astNode instanceof Variable
						&& ((Variable)astNode).getName() != null
						&& (hoverRegion.getOffset() < (((Variable)astNode).getStartPosition() + ((Variable)astNode)
								.getName().length()))) {
					eObject = astNode;
				} else {
					// If the user has selected the type of the variable or anything else...
					eObject = OpenDeclarationUtils.findDeclarationFromAST(astNode);
				}

				if (eObject instanceof IteratorExp && editor.getContent().getOCLEnvironment() != null) {
					eObject = OpenDeclarationUtils.findIteratorEOperation(editor.getContent()
							.getOCLEnvironment(), (IteratorExp)eObject);
				}
				if (eObject != null) {
					result = getObjectInfo(eObject);
				}
			} else if (editor.getContent().isInModuleHeader(hoverRegion.getOffset(),
					hoverRegion.getOffset() + hoverRegion.getLength(), true)) {
				Module module = editor.getContent().getAST();
				result = getObjectInfo(module);
			}
		}
		return result;
	}

	/**
	 * Returns the documentation object matching the EObject or the EObject if no documentation has been
	 * found.
	 * 
	 * @param eObject
	 *            The object
	 * @return The documentation if there is one, or the object
	 */
	private Object getObjectInfo(final EObject eObject) {
		Documentation documentation = null;
		if (eObject instanceof DocumentedElement) {
			DocumentedElement element = (DocumentedElement)eObject;
			documentation = element.getDocumentation();
			boolean fromFile = false;
			if (documentation == null) {
				// If our AST does not have any documentation for the current element, we will look in the
				// file
				fromFile = true;
			} else if ((element instanceof Module && !(documentation instanceof ModuleDocumentation))
					|| (element instanceof ModuleElement && !(documentation instanceof ModuleElementDocumentation))) {
				// If our AST has a documentation for the current element but if it is not resolved (not a
				// MODULE documentation for a module or a MODULE ELEMENT documentation for a module element
				// but just a Documentation), then we will look in the file
				fromFile = true;
			}

			if (fromFile) {
				documentation = AcceleoUIDocumentationUtils.getDocumentationFromFile(element);
			}
		}

		if (documentation != null) {
			String result = AcceleoUIDocumentationUtils.getTextFrom(documentation);

			/*
			 * [Doc unloading] unloading resource loaded from
			 * AcceleoUIDocumentationUtils#getDocumentationFromFile
			 */
			if (documentation.eResource() != null && documentation.eResource() != eObject.eResource()) {
				documentation.eResource().unload();
			}

			return result;
		}
		return eObject;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.text.ITextHover#getHoverRegion(org.eclipse.jface.text.ITextViewer, int)
	 */
	public IRegion getHoverRegion(final ITextViewer textViewer, final int offset) {
		Point selection = textViewer.getSelectedRange();
		if (selection.x <= offset && offset < selection.x + selection.y) {
			return new Region(selection.x, selection.y);
		}
		return new Region(offset, 0);
	}
}
