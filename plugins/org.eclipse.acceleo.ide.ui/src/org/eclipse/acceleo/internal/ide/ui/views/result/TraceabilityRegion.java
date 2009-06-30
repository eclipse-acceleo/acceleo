/*******************************************************************************
 * Copyright (c) 2008, 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.acceleo.internal.ide.ui.views.result;

import org.eclipse.acceleo.model.mtl.Block;
import org.eclipse.acceleo.model.mtl.ModuleElement;
import org.eclipse.emf.ecore.EObject;

/**
 * The regions of the text synchronized with the current EObject. We store the offset and the length of the
 * region, but also, the template AST node used to generate that text.
 * 
 * @author <a href="mailto:jonathan.musset@obeo.fr">Jonathan Musset</a>
 */
public class TraceabilityRegion extends TraceabilityElement {

	/**
	 * The text region offset.
	 */
	private int targetFileOffset;

	/**
	 * The text region length.
	 */
	private int targetFileLength;

	/**
	 * The last template AST node used to create this text region.
	 */
	private Block astNode;

	/**
	 * Constructor.
	 * 
	 * @param targetFileOffset
	 *            the offset
	 * @param targetFileLength
	 *            the length
	 * @param astNode
	 *            the last template AST node used to create this text region
	 */
	public TraceabilityRegion(int targetFileOffset, int targetFileLength, Block astNode) {
		super();
		this.targetFileOffset = targetFileOffset;
		this.targetFileLength = targetFileLength;
		this.astNode = astNode;
	}

	/**
	 * Gets the text region offset.
	 * 
	 * @return text region offset
	 */
	public int getTargetFileOffset() {
		return targetFileOffset;
	}

	/**
	 * Gets the text region length.
	 * 
	 * @return text region length
	 */
	public int getTargetFileLength() {
		return targetFileLength;
	}

	/**
	 * Gets the last template AST node used to create this text region.
	 * 
	 * @return the last template AST node used to create this text region
	 */
	public Block getAstNode() {
		return astNode;
	}

	/**
	 * Increases the text region length.
	 * 
	 * @param length
	 *            is the length to add (targetFileLength += length)
	 */
	public void enlarge(int length) {
		targetFileLength += length;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (astNode != null) {
			String templateName = astNode.eResource().getURI().lastSegment();
			String templateDisplay;
			if (astNode instanceof ModuleElement) {
				templateDisplay = '/' + templateName + '/' + ((ModuleElement)astNode).getName();
			} else {
				ModuleElement eModuleElement = getModuleElement(astNode);
				if (eModuleElement != null) {
					templateDisplay = '/' + templateName + '/' + eModuleElement.getName() + '/'
							+ astNode.eClass().getName();
				} else {
					templateDisplay = '/' + templateName + '/' + astNode.eClass().getName();
				}
			}
			return "[" + targetFileOffset + "," + (targetFileOffset + targetFileLength) + "] by '" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ templateDisplay + "'"; //$NON-NLS-1$
		} else {
			return ""; //$NON-NLS-1$
		}
	}

	/**
	 * Gets the module element (template, query) that contains the given AST node.
	 * 
	 * @param currentAstNode
	 *            is the current AST node
	 * @return the ancestor of type ModuleElement, or null if it doesn't exist
	 */
	private ModuleElement getModuleElement(EObject currentAstNode) {
		EObject current = currentAstNode;
		while (current != null) {
			if (current instanceof ModuleElement) {
				return (ModuleElement)current;
			}
			current = current.eContainer();
		}
		return null;
	}

}
