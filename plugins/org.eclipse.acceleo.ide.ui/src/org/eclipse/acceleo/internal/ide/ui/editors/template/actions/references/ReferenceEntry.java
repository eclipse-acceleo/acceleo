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
package org.eclipse.acceleo.internal.ide.ui.editors.template.actions.references;

import org.eclipse.acceleo.internal.ide.ui.editors.template.AcceleoEditor;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.ocl.utilities.ASTNode;

/**
 * This class store the reference position.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class ReferenceEntry implements IAdaptable {
	/**
	 * The template file.
	 */
	private IFile templateFile;

	/**
	 * The matching object.
	 */
	private EObject match;

	/**
	 * A message to display.
	 */
	private String message;

	/**
	 * The Acceleo editor.
	 */
	private AcceleoEditor editor;

	/**
	 * Constructor.
	 * 
	 * @param templateFile
	 *            the template file
	 * @param match
	 *            the matching object
	 * @param editor
	 *            the Acceleo editor
	 */
	public ReferenceEntry(IFile templateFile, EObject match, AcceleoEditor editor) {
		this(templateFile, match, editor, ""); //$NON-NLS-1$
	}

	/**
	 * Constructor.
	 * 
	 * @param templateFile
	 *            the template file
	 * @param match
	 *            the region
	 * @param editor
	 *            the Acceleo editor
	 * @param message
	 *            the message
	 */
	public ReferenceEntry(IFile templateFile, EObject match, AcceleoEditor editor, String message) {
		this.templateFile = templateFile;
		this.match = match;
		this.message = message;
		this.editor = editor;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class adapter) {
		if (IResource.class.equals(adapter)) {
			return templateFile;
		}
		return null;
	}

	/**
	 * Getter for the template file.
	 * 
	 * @return the template file
	 */
	public IFile getTemplateFile() {
		return templateFile;
	}

	/**
	 * Getter for the matching object.
	 * 
	 * @return the matching object
	 */
	public EObject getMatch() {
		return match;
	}

	/**
	 * Getter for the message.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Getter for the Acceleo editor.
	 * 
	 * @return the Acceleo editor
	 */
	public AcceleoEditor getEditor() {
		return editor;
	}

	/**
	 * Creates a region for the current match.
	 * 
	 * @return the region of the current match
	 */
	public IRegion getRegion() {
		if (match instanceof ASTNode) {
			return createRegion((ASTNode)match);
		}
		return new Region(0, 0);
	}

	/**
	 * Creates a region for the given module element.
	 * 
	 * @param eModuleElement
	 *            is the module element
	 * @return a region in the text
	 */
	private IRegion createRegion(ASTNode eModuleElement) {
		int b = eModuleElement.getStartPosition();
		if (b > -1) {
			int e = eModuleElement.getEndPosition();
			return new Region(b, e - b);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return templateFile.getName() + ": " + message; //$NON-NLS-1$
	}
}
